package parsers;

import intterm.IntTerm;
import org.jparsec.Parser;
import org.jparsec.Terminals;
import quantumoperations.*;

import static utils.Syntax.*;

public class QOperationParser {

    private static QOperationParser QOpParser;

    public static QOperationParser getQOpParser() {
        if (QOpParser == null) {
            QOpParser = new QOperationParser();
        }
        return QOpParser;
    }

    private QOperationParser() {
    }

    public Parser<Qoperations> getParser() {
        Parser<IntTerm> intTermParser = IntTermParser.getIntTermParser().getParser();
        Parser.Reference<Qoperations> ref = Parser.newReference();
        Parser<Qoperations> atoms = 
            OPERATORS.token("W").next(
                OPERATORS.token("[").next(
                    intTermParser.next(n -> 
                        OPERATORS.token("]").retn(
                            (Qoperations) (new Hadamard(n))
                        )
                    )
                )
            )
            .or(OPERATORS.token("QFT").next(
                OPERATORS.token("[").next(
                    intTermParser.next(
                            n ->OPERATORS.token("]").retn(
                                    (new QFT(n))
                            )
                    )
                )
            ))
            .or(OPERATORS.token("INV").next(
                OPERATORS.token("[").next(
                    ref.lazy().next(m -> 
                        OPERATORS.token("]").retn(
                            (Qoperations) (new InverseOp(m))
                        )
                    )
                )
            ))
            .or(OPERATORS.token("CL").next(
                OPERATORS.token("[").next(
                    Terminals.Identifier.PARSER.next(var -> 
                        OPERATORS.token(",").next(
                            intTermParser.next(in_lower -> 
                                OPERATORS.token(",").next(
                                    intTermParser.next(in_upper ->
                                        OPERATORS.token(",").next( 
                                            intTermParser.next(out_lower -> 
                                                OPERATORS.token(",").next(
                                                    intTermParser.next(out_upper ->
                                                        OPERATORS.token(",").next( 
                                                            intTermParser.next(body -> 
                                                                OPERATORS.token("]").retn(
                                                                    new ClassicalOp(var,in_lower,in_upper,out_lower,out_upper,body)
                                                                )
                                                            )
                                                        )
                                                    )
                                                )
                                            )
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

    public Qoperations parse(CharSequence source) {
        return getParser().from(TOKENIZER,SPACES).parse(source);
    }
}
