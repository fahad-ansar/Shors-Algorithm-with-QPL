import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import parsers.ProgramParser;
import qpl.QPLProgram;
import states.State;
import utils.QPLExecutionException;

public class Main {
    
    private static String readFile(String file) {
        String result = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while(line != null) {
                result += line +"\n";
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(0);
        }
        return result;
    }
    
    public static void main(String[] args) {
        ProgramParser programParser = ProgramParser.getProgramParser();
        String progText = readFile("ShorQ.qpl");
        QPLProgram prog = programParser .parse(progText);
        System.out.println(prog);
        try {
            State state = prog.execute();
            System.out.println(state);
        } catch (QPLExecutionException ex) {
            ex.printStackTrace();
        }
    }
}