package parsers;

import intterm.IntTerm;
import java.util.List;
import org.jparsec.Parser;
import org.jparsec.Terminals;
import qpl.BlockStatement;
import qpl.FuncDecl;
import qpl.QPLCommand;

import static utils.Syntax.*;

public class FuncDeclParser {

    private static FuncDeclParser procDeclParser;

    public static FuncDeclParser getFuncDeclParser(){
        if(procDeclParser==null)
            procDeclParser = new FuncDeclParser();

        return procDeclParser;
    }
    
    private FuncDeclParser() {
    }

    public Parser<FuncDecl> funcDeclparser(){
        Parser<QPLCommand> programparser = CommandParser.getCommandParser().getParser();
        Parser<IntTerm> inttermparser = IntTermParser.getIntTermParser().getParser();
        Parser<FuncDecl> result =
            Terminals.Identifier.PARSER.next(name ->
                OPERATORS.token("(").next(
                    Terminals.Identifier.PARSER.sepBy(OPERATORS.token(",")).next(varlist ->
                        OPERATORS.token(")").next(
                            OPERATORS.token("{").next(
                                programparser.sepBy(OPERATORS.token(";")).next(cl -> 
                                    OPERATORS.token(";").next(
                                        OPERATORS.token("return").next(
                                            inttermparser.next(ret -> 
                                                OPERATORS.token("}").retn(
                                                    new FuncDecl(name,varlist,new BlockStatement(cl),ret)
                                                ) 
                                            )
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            );
        return result;
    }

    public Parser<List<FuncDecl>> getParser(){
        return funcDeclparser().many();
    }
    
    public List<FuncDecl> parse(CharSequence source) {
        return getParser().from(TOKENIZER,SPACES).parse(source);
    }
}
