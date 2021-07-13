package intterm;

import states.State;
import utils.IntOperations;
import utils.QPLExecutionException;

public class IntNegate extends IntTerm {

    private final IntTerm body;

    public IntNegate(IntTerm body){
        this.body = body;
    }

    @Override
    public int value(State state) throws QPLExecutionException {
        return -body.value(state);
    }

    @Override
    public String toStringPrec(int prec) {
        return "-" + body.toStringPrec(100);
    }
}

