package parsers;

import boolterm.BoolTerm;
import intterm.IntTerm;
import org.jparsec.Parser;
import org.jparsec.Terminals;
import qpl.Assignment;
import qpl.BlockStatement;
import qpl.IfThenElse;
import qpl.QApply;
import qpl.QInitialize;
import qpl.QMeasure;
import qpl.QPLCommand;
import qpl.Skip;
import qpl.While;

import static utils.Syntax.*;

public class CommandParser {

    private static CommandParser programparser;
    
    public static CommandParser getCommandParser(){
        if(programparser==null) {
            programparser = new CommandParser();
        }
        return programparser;
    }

    private CommandParser() {
    };

    public Parser<QPLCommand> getParser(){
        Parser<BoolTerm> boolTermParser = BoolTermParser.getBoolTermParser().getParser();
        Parser<IntTerm> intTermParser = IntTermParser.getIntTermParser().getParser();
        QOperationParser qOpParser = QOperationParser.getQOpParser();
        Parser.Reference<QPLCommand> ref = Parser.newReference();
        Parser<QPLCommand> atoms =
            OPERATORS.token("skip").retn((QPLCommand) new Skip())
            .or(Terminals.Identifier.PARSER.next(s -> 
                OPERATORS.token("=").next(OPERATORS.token("QMeasure").next(OPERATORS.token("(").next(intTermParser.next(l -> 
                                OPERATORS.token(",").next(intTermParser.next(u -> 
                                        OPERATORS.token(")").retn((QPLCommand) new QMeasure(s,l,u)
                                        )
                                    )
                                )
                            )
                        )
                    )
                    .or(intTermParser.map(t -> 
                        (QPLCommand) new Assignment(s,t))
                    )
                )
            ))
            .or(OPERATORS.token("if").next(
                OPERATORS.token("(").next(
                    boolTermParser.next(b -> 
                        OPERATORS.token(")").next(
                            ref.lazy().next(p1 -> 
                                OPERATORS.token("else").next(
                                    ref.lazy().map(p2 -> 
                                        new IfThenElse(b,p1,p2)
                                    )
                                )
                            )
                        )
                    )
                )
            ))
            .or(OPERATORS.token("while").next(
                OPERATORS.token("(").next(
                    boolTermParser.next(cnd -> 
                        OPERATORS.token(")").next(  
                            ref.lazy().map(bd -> 
                                new While(cnd,bd)
                            ) 
                        )
                    )
                )
            ))
            .or(ref.lazy().sepBy(OPERATORS.token(";")).between(OPERATORS.token("{"),OPERATORS.token("}")).map(l -> new BlockStatement(l)))
            .or(OPERATORS.token("QInitialize").next(
                OPERATORS.token("(").next(
                    intTermParser.next(n ->
                        OPERATORS.token(")").retn(
                            new QInitialize(n)
                        )
                    )
                )
            ))
            .or(OPERATORS.token("QApply").next(
                OPERATORS.token("(").next(
                    QOperationParser.getQOpParser().getParser().next(q -> 
                        OPERATORS.token(",").next(
                            intTermParser.next(l -> 
                                OPERATORS.token(",").next(
                                    intTermParser.next(u -> 
                                        OPERATORS.token(")").retn(
                                            new QApply(q,l,u)
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            ));
        ref.set(atoms);
        return atoms;
    }

    public QPLCommand parse(CharSequence source) {
        return getParser().from(TOKENIZER,SPACES).parse(source);
    }
}
