package quantumoperations;

import complexmath.UnitaryMatrix;
import states.State;
import utils.QPLExecutionException;

public interface Qoperations {
    
    public UnitaryMatrix getUnitaryMatrix(State state) throws QPLExecutionException;

    public int getNumBits(State state) throws QPLExecutionException;
}
