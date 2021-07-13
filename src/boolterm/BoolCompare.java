package boolterm;

import intterm.IntTerm;
import states.State;
import utils.BoolCompOp;
import utils.QPLExecutionException;

public class BoolCompare extends BoolTerm {
    
    private final BoolCompOp op;
    private final IntTerm left;
    private final IntTerm right;

    public BoolCompare(BoolCompOp op, IntTerm left, IntTerm right){
        this.op = op;
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean value(State state) throws QPLExecutionException {
        return op.apply(left.value(state), right.value(state));
    }

    @Override
    public String toStringPrec(int prec) {
        return left + " " + op + " " + right;
    }
}
