package qpl;

import intterm.IntTerm;
import states.State;
import utils.QPLExecutionException;

import static utils.StringUtil.replicate;

public class Assignment extends QPLCommand {
    
    private final String var;
    private final IntTerm intTerm;

    public Assignment(String var, IntTerm intTerm){
        this.var = var;
        this.intTerm = intTerm;
    }

    @Override
    public State execute(State state) throws QPLExecutionException {
        state.putStore(var,intTerm.value(state));
        return state;
    }

    @Override
    public String toStringIndent(int indent) {
        String result = replicate('\t',indent) + var + " = " + intTerm;
        return result;
    }
}

