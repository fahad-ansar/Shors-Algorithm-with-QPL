package quantumoperations;

import complexmath.UnitaryMatrix;
import intterm.IntTerm;
import states.State;
import utils.QPLExecutionException;

import static complexmath.UnitaryMatrix.HADAMARD;

public class Hadamard implements Qoperations {
    
    private final IntTerm numBits;

    public Hadamard(IntTerm noOfBits){
        this.numBits = noOfBits;
    }
    
    @Override
    public int getNumBits(State state) throws QPLExecutionException {
        return numBits.value(state);
    }

    @Override
    public UnitaryMatrix getUnitaryMatrix(State state) throws QPLExecutionException {
        int n = getNumBits(state);
        if (n<1) {
            throw new QPLExecutionException();
        }
        return HADAMARD(n);
    }
    
    @Override
    public String toString() {
        return "W[" + numBits + ']';
    }
}
