package quantumoperations;

import complexmath.UnitaryMatrix;
import intterm.IntTerm;
import states.State;
import utils.QPLExecutionException;

import static complexmath.UnitaryMatrix.QFT;

public class QFT implements Qoperations {
    
    private final IntTerm numbits;
    
    public QFT(IntTerm numbits) {
        this.numbits = numbits;
    }
    
    @Override
    public int getNumBits(State state) throws QPLExecutionException {
        return numbits.value(state);
    }
    
    @Override
    public UnitaryMatrix getUnitaryMatrix(State state) throws QPLExecutionException {
        int n = getNumBits(state);
        if (n<1) {
            throw new QPLExecutionException();
        }
        return QFT(n);
    }
    
    @Override
    public String toString() {
        return "QFT[" + numbits + "]";
    }   
}
