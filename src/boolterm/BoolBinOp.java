package boolterm;

import states.State;
import utils.BoolOperations;
import utils.QPLExecutionException;

public class BoolBinOp extends BoolTerm {
    
    private final BoolOperations op;
    private final BoolTerm left;
    private final BoolTerm right;

    public BoolBinOp(BoolOperations op, BoolTerm left, BoolTerm right){
        this.op = op;
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean value(State state) throws QPLExecutionException {
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
