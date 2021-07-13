package qpl;

import java.util.List;
import states.State;
import utils.QPLExecutionException;

public class QPLProgram {
    
    private final List<FuncDecl> funcDecls;
    private final QPLCommand main;
    
    public QPLProgram(List<FuncDecl> funcDecls, QPLCommand main) {
        this.funcDecls = funcDecls;
        this.main = main;
    }
    
    public State execute() throws QPLExecutionException {
        State state = new State();
        for(FuncDecl fc : funcDecls) {
            state.putDecl(fc.getName(),fc);
        }
        return main.execute(state);
    }   

    @Override
    public String toString() {
        String result = "";
        for(FuncDecl fc : funcDecls) {
            result += fc;
        }
        return result + main;
    }
}