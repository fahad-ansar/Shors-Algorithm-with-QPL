package qpl;

import complexmath.UnitaryMatrix;
import intterm.IntTerm;
import java.util.Arrays;
import quantumoperations.Qoperations;
import states.State;
import utils.QPLExecutionException;

import static utils.StringUtil.replicate;

public class QApply extends QPLCommand {

    private final IntTerm lowerRange;
    private final IntTerm upperRange;
    private final Qoperations qoperation;

    public QApply(Qoperations qOps, IntTerm lowerRange, IntTerm upperRange){
        this.lowerRange = lowerRange;
        this.upperRange = upperRange;
        this.qoperation = qOps;
    }

    @Override
    public State execute(State state) throws QPLExecutionException {
        int lower = lowerRange.value(state);
        int upper = upperRange.value(state);
        int opNumBits = qoperation.getNumBits(state);
        int regNumBits = state.getNumQBits();
        UnitaryMatrix matrix = qoperation.getUnitaryMatrix(state);
        if (lower<0 || upper<lower || opNumBits != upper-lower+1 || upper >= regNumBits) {
            throw new QPLExecutionException();
        }
        int idSize = lower;
        if (idSize > 0) {
            matrix = matrix.tensor(UnitaryMatrix.IDENTITY(idSize));
        }
        idSize = regNumBits-1-upper;
        if (idSize > 0) {
            matrix = UnitaryMatrix.IDENTITY(idSize).tensor(matrix);
        }
        state.applyToQRegister(matrix);
        return state;
    }
    
    @Override
    public String toStringIndent(int indent) {
        return replicate('\t',indent) + "QApply(" + qoperation + "," + lowerRange + "," + upperRange + ")";
    }
}
