package parsers;

import java.util.List;
import org.jparsec.Parser;
import qpl.FuncDecl;
import qpl.QPLCommand;
import qpl.QPLProgram;

import static utils.Syntax.*;

public class ProgramParser {

    private static ProgramParser programParser;

    public static ProgramParser getProgramParser(){
        if(programParser==null)
            programParser = new ProgramParser();

        return programParser;
    }
    
    private ProgramParser() {
    }

    public Parser<QPLProgram> getParser() {
        Parser<QPLCommand> commandParser = CommandParser.getCommandParser().getParser();
        Parser<List<FuncDecl>> funcDeclParser = FuncDeclParser.getFuncDeclParser().getParser();
        Parser<QPLProgram> result =
            funcDeclParser.next(fl ->
                commandParser.map(com ->
                    new QPLProgram(fl,com)
                )    
            );
        return result;
    }

    public QPLProgram parse(CharSequence source){
        return getParser().from(TOKENIZER,SPACES).parse(source);
    }
}
