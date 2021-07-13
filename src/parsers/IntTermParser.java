package parsers;

import intterm.IntBinOp;
import intterm.IntConst;
import intterm.IntFCall;
import intterm.IntNegate;
import intterm.IntRandom;
import intterm.IntTerm;
import intterm.IntVar;
import org.jparsec.OperatorTable;
import org.jparsec.Parser;
import org.jparsec.Parsers;
import org.jparsec.Terminals;

import static utils.IntOperations.*;
import static utils.Syntax.*;


public class IntTermParser {
        
    private static IntTermParser intTermParser;
    
    public static IntTermParser getIntTermParser() {
        if (intTermParser == null) {
            intTermParser = new IntTermParser();
        }
        return intTermParser;
    }
    
    private IntTermParser() {
    }
    
    public Parser<IntTerm> getParser() {
        Parser.Reference<IntTerm> ref = Parser.newReference();
        Parser<IntTerm> atoms = 
            ref.lazy().between(OPERATORS.token("("),OPERATORS.token(")"))
            .or(Terminals.IntegerLiteral.PARSER.map(s -> new IntConst(Integer.valueOf(s))))
            .or(Terminals.Identifier.PARSER.next(name -> 
                OPERATORS.token("(").next(s -> {
                    if (name.equals("random")) {
                        return ref.lazy().next(lB ->
                                OPERATORS.token(",").next(
                                    ref.lazy().next(uB ->
                                        OPERATORS.token(")").retn(
                                            new IntRandom(lB,uB)
                                        )
                                    )
                                ));
                    } else {
                        return ref.lazy().sepBy(OPERATORS.token(",")).next(pl ->
                                OPERATORS.token(")").retn(
                                    (IntTerm) new IntFCall(name,pl)
                                ));
                    }
                })
                .or(Parsers.always().retn(new IntVar(name))) 
            ));
        Parser<IntTerm> result = new OperatorTable<IntTerm>()
            .prefix(OPERATORS.token("-").retn(n -> new IntNegate(n)),100)
            .infixl(OPERATORS.token("+").retn((l,r) -> new IntBinOp(PLUS,l,r)),PLUS.precedence())
            .infixl(OPERATORS.token("-").retn((l,r) -> new IntBinOp(MINUS,l,r)),MINUS.precedence())
            .infixl(OPERATORS.token("*").retn((l,r) -> new IntBinOp(MULT,l,r)),MULT.precedence())
            .infixl(OPERATORS.token("/").retn((l,r) -> new IntBinOp(DIV,l,r)),DIV.precedence())
            .infixl(OPERATORS.token("%").retn((l,r) -> new IntBinOp(MOD,l,r)),MOD.precedence())
            .build(atoms);
        ref.set(result);
        return result;
    }
    
    public IntTerm parse(CharSequence source) {
        return getParser().from(TOKENIZER,SPACES).parse(source);
    }
} 

