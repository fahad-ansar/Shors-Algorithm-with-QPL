package boolterm;

import states.State;
import utils.QPLExecutionException;

public abstract class BoolTerm {

    public abstract boolean value(State state) throws QPLExecutionException;

    public abstract String toStringPrec(int prec);

    @Override
    public String toString() {
        return toStringPrec(0);
    }
}
