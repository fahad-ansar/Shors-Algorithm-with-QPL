package quantumoperations;

import complexmath.UnitaryMatrix;
import intterm.IntTerm;
import states.State;
import utils.QPLExecutionException;

import static complexmath.UnitaryMatrix.FROMCLASSICAL;
import java.util.Arrays;

public class ClassicalOp implements Qoperations {

    private final String var;
    private final IntTerm in_lower;
    private final IntTerm in_upper;
    private final IntTerm out_lower;
    private final IntTerm out_upper;
    private final IntTerm body;
    
    public ClassicalOp(String var, IntTerm in_lower, IntTerm in_upper, IntTerm out_lower, IntTerm out_upper, IntTerm body) {
        this.var = var;
        this.in_lower = in_lower;
        this.in_upper = in_upper;
        this.out_lower = out_lower;
        this.out_upper = out_upper;
        this.body = body;
    }
    
    @Override
    public int getNumBits(State state) throws QPLExecutionException {
        int in_lowerB = in_lower.value(state);
        int in_upperB = in_upper.value(state);
        int out_lowerB = out_lower.value(state);
        int out_upperB = out_upper.value(state);
        if (in_upperB < in_lowerB || out_upperB < out_lowerB || (in_upperB+1 != out_lowerB && out_upperB+1 != in_lowerB)) {
            throw new QPLExecutionException();
        }
        return (out_upperB+in_upperB-(out_lowerB+in_lowerB)+2);
    }
    
    @Override
    public UnitaryMatrix getUnitaryMatrix(State state) throws QPLExecutionException {
        int in_lowerB = in_lower.value(state);
        int in_upperB = in_upper.value(state);
        int out_lowerB = out_lower.value(state);
        int out_upperB = out_upper.value(state);
        if (in_upperB < in_lowerB || out_upperB < out_lowerB || (in_upperB+1 != out_lowerB && out_upperB+1 != in_lowerB)) {
            throw new QPLExecutionException();
        };
        int in_size = (int) Math.pow(2,in_upperB-in_lowerB+1);
        int out_size = (int) Math.pow(2,out_upperB-out_lowerB+1);
        int[] operation = new int[in_size];
        Integer save = state.getStore(var);
        for (int i=0; i<in_size; i++) {
            state.putStore(var,i);
            operation[i] = body.value(state) % out_size;
        }
        if (save!=null) {
            state.putStore(var,save);
        } else {
            state.removeStore(var);
        }
        return FROMCLASSICAL(in_upperB-in_lowerB+1,out_upperB-out_lowerB+1,operation,in_upperB+1==out_lowerB);
    }
    
    @Override
    public String toString() {
        return "CL[" + var + ',' + in_lower + ',' + in_upper + ',' + out_lower + ',' + out_upper + ',' + body + ']';
    }
}
