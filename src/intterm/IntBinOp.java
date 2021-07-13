package intterm;

import states.State;
import utils.IntOperations;
import utils.QPLExecutionException;

public class IntBinOp extends IntTerm {

    private final IntOperations op;
    private final IntTerm left;
    private final IntTerm right;

    public IntBinOp(IntOperations op, IntTerm left, IntTerm right){
        this.left = left;
        this.right = right;
        this.op =op;
    }

    @Override
    public int value(State state) throws QPLExecutionException {
        return op.apply(left.value(state),right.value(state));
    }

    @Override
    public String toStringPrec(int prec) {
        int nprec = op.precedence();
        String result = left.toStringPrec(nprec) + " " + op + " " + right.toStringPrec(nprec);
        if (nprec < prec) {
            result = "(" + result + ")";
        }
        return result;
    }
}
