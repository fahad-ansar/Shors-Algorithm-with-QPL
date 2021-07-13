package boolterm;

import states.State;
import utils.QPLExecutionException;

public class BoolConst extends BoolTerm {
    
    private final boolean value;

    public BoolConst(boolean value){
        this.value = value;
    }

    @Override
    public boolean value(State state) throws QPLExecutionException {
        return value;
    }

    @Override
    public String toStringPrec(int prec) {
        return String.valueOf(value);
    }
}
