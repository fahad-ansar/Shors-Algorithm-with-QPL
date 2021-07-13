package quantumoperations;

import complexmath.UnitaryMatrix;
import states.State;
import utils.QPLExecutionException;

public class InverseOp implements Qoperations{
    
    private final Qoperations qoperation;

    public InverseOp(Qoperations qoperation){
        this.qoperation = qoperation;
    }
    
    @Override
    public int getNumBits(State state) throws QPLExecutionException {
        return qoperation.getNumBits(state);
    }
    
    @Override
    public UnitaryMatrix getUnitaryMatrix(State state) throws QPLExecutionException {
        UnitaryMatrix inner = qoperation.getUnitaryMatrix(state);
        return inner.inverse();
    }

    @Override
    public String toString() {
        return "INV[" + qoperation + ']';
    }
}
