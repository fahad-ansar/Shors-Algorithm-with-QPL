package intterm;

import states.State;
import utils.QPLExecutionException;

public class IntVar extends IntTerm {

    private final String name;

    public IntVar(String name) {
        this.name = name;
    }

    @Override
    public int value(State state) throws QPLExecutionException {
        int result = 0;
        try {
            result = state.getStore(name);
        } catch (NullPointerException e) {
            throw new QPLExecutionException(name + " unkown");
        }
        return result;
    }

    @Override
    public String toStringPrec(int prec) {
        return name;
    }
}
