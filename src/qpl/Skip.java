package qpl;

import states.State;
import utils.QPLExecutionException;

import static utils.StringUtil.replicate;

public class Skip extends QPLCommand {
    
    @Override
    public State execute(State state) throws QPLExecutionException {
        return state;
    }

    @Override
    public String toStringIndent(int indent) {
        return replicate('\t',indent) + "skip\n";
    }
}
