package intterm;

import states.State;
import utils.QPLExecutionException;

public class IntRandom extends IntTerm{

    private final IntTerm lowerBound;
    private final IntTerm upperBound;

    public IntRandom (IntTerm lowerBound, IntTerm upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    @Override
    public int value(State state) throws QPLExecutionException {
        int lowerB = lowerBound.value(state);
        int upperB = upperBound.value(state);
        if (upperB < lowerB) {
            throw new QPLExecutionException();
        }
        return (int) (Math.random() * (upperB - lowerB + 1) + lowerB);
    }

    @Override
    public String toStringPrec(int prec) {
        return "random(" + lowerBound + "," + upperBound + ")";
    }
}
