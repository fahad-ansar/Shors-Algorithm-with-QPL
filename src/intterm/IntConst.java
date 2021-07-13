package intterm;

import states.State;
import utils.QPLExecutionException;

public class IntConst extends IntTerm {
    
    private final int val;

    public IntConst(int value){
        this.val = value;
    }

    @Override
    public int value(State state) throws QPLExecutionException {
        return val;
    }

    @Override
    public String toStringPrec(int prec) {
        return String.valueOf(val);
    }
}
