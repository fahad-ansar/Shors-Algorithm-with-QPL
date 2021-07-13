package intterm;

import states.State;
import utils.QPLExecutionException;

public abstract class IntTerm {

    public abstract int value(State state) throws QPLExecutionException;

    public abstract String toStringPrec(int prec);
    
    @Override
    public String toString() {
        return toStringPrec(0);
    }
}
