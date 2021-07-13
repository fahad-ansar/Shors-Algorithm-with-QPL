package utils;

import org.jparsec.Parser;
import org.jparsec.Scanners;
import org.jparsec.Terminals;

public class Syntax {
    
    private static final String[] SYMBOLS = {
        ";",",","(",")","{","}","[","]","/*","*/",
        "+","-","*","/","%","true","false","||","&&","!","==","!=",">=","<=","<",">",
        "=","skip","if","then","else","while",
        "QInitialize","QApply","QMeasure",
        "W","INV","QFT","return","CL"
    };

    public static final Terminals OPERATORS = Terminals.operators(SYMBOLS);
    
    public static final Parser<?> TOKENIZER = OPERATORS.tokenizer().cast().or(Terminals.Identifier.TOKENIZER).or(Terminals.IntegerLiteral.TOKENIZER);

    public static final Parser<Void> SPACES = (Scanners.WHITESPACES.or(Scanners.blockComment("/*","*/"))).skipMany();
}
