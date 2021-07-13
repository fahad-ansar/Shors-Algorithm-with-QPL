package parsers;

import boolterm.*;
import org.jparsec.OperatorTable;
import org.jparsec.Parser;
import utils.BoolOperations;

import static utils.BoolCompOp.*;
import static utils.Syntax.*;

public class BoolTermParser {
    
    private static BoolTermParser boolTermParser;

    public static BoolTermParser getBoolTermParser(){
        if(boolTermParser == null){
            boolTermParser = new BoolTermParser();
        }
        return boolTermParser;
    }

    private BoolTermParser() {
    }

    public Parser<BoolTerm> getParser(){
        Parser.Reference<BoolTerm> ref= Parser.newReference();
        IntTermParser intTermParser = IntTermParser.getIntTermParser();
        Parser<BoolTerm> atoms = 
            ref.lazy().between(OPERATORS.token("("),OPERATORS.token(")"))
            .or(OPERATORS.token("true").retn(new BoolConst(true)))
            .or(OPERATORS.token("false").retn(new BoolConst(false)))
            .or(intTermParser.getParser().next(t1 ->
                OPERATORS.token(">").next(intTermParser.getParser().map(t2 -> new BoolCompare(GREATERTHAN,t1,t2)))
                .or(OPERATORS.token("<").next(intTermParser.getParser().map(t2 -> new BoolCompare(LESSTHAN,t1,t2))))
                .or(OPERATORS.token(">=").next(intTermParser.getParser().map(t2 -> new BoolCompare(GREATEROREQUALTHAN,t1,t2))))
                .or(OPERATORS.token("<=").next(intTermParser.getParser().map(t2 -> new BoolCompare(LESSOREQUALTHAN,t1,t2))))
                .or(OPERATORS.token("==").next(intTermParser.getParser().map(t2 -> new BoolCompare(EQUAL,t1,t2))))
                .or(OPERATORS.token("!=").next(intTermParser.getParser().map(t2 -> new BoolCompare(NOTEQUAL,t1,t2))))
            ));
        Parser<BoolTerm> result = new OperatorTable<BoolTerm>()
            .infixl(OPERATORS.token("&&").retn((l,r) -> new BoolBinOp(BoolOperations.AND,l,r)),BoolOperations.AND.precedence())
            .infixl(OPERATORS.token("||").retn((l,r) -> new BoolBinOp(BoolOperations.OR,l,r)),BoolOperations.OR.precedence())
            .prefix(OPERATORS.token("!").retn(b -> new BoolNot(b)),100)
            .build(atoms);
        ref.set(result);
        return result;
    }

    public BoolTerm parse(CharSequence source){
        return getParser().from(TOKENIZER,SPACES).parse(source);
    }
}
