package boolterm;

import states.State;
import utils.QPLExecutionException;

public class BoolNot extends BoolTerm {
    
    private final BoolTerm body;

    public BoolNot(BoolTerm body){
        this.body = body;
    }

    @Override
    public boolean value(State state) throws QPLExecutionException {
        return !body.value(state);
    }

    @Override
    public String toStringPrec(int prec) {
        return "!" + body.toStringPrec(1);
    }
}
