package intterm;

import qpl.FuncDecl;
import states.State;
import utils.QPLExecutionException;

import java.util.List;

public class IntFCall extends IntTerm{

    private final String name;
    private final List<IntTerm> actualParams;

    public IntFCall (String name, List<IntTerm> params) {
        this.name = name;
        this.actualParams = params;
    }

    @Override
    public int value(State state) throws QPLExecutionException {
        FuncDecl funcDecl = state.getDecl(name);
        State newState = state.getNew();
        List<String> formalParams = funcDecl.getParams();
        if (actualParams.size() != formalParams.size()) {
            throw new QPLExecutionException();
        }
        for(int i=0; i<actualParams.size(); i++) {
            newState.putStore(formalParams.get(i),actualParams.get(i).value(state));
        }
        newState = funcDecl.getBody().execute(newState);
        return funcDecl.getRetTerm().value(newState);
    }

    @Override
    public String toStringPrec(int prec) {
        String result = name + "(";
        for(int i=0; i<actualParams.size(); i++) {
            result += actualParams.get(i);
            if (i != actualParams.size()-1) {
                result += ",";
            }
        }
        return result + ")";
    }
}
