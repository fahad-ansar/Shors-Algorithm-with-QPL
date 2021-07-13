package qpl;

import intterm.IntTerm;
import states.State;
import utils.QPLExecutionException;

import static utils.StringUtil.replicate;

public class QMeasure extends QPLCommand{
    
    private final String var;
    private final IntTerm lowerRange;
    private final IntTerm upperRange;

    public QMeasure(String var, IntTerm lowerRange, IntTerm upperRange){
        this.lowerRange = lowerRange;
        this.upperRange = upperRange;
        this.var = var;
    }

    @Override
    public State execute(State state) throws QPLExecutionException {
        state.putStore(var,state.measureQRegister(lowerRange.value(state),upperRange.value(state)));
        return state;
    }

    @Override
    public String toStringIndent(int indent) {
        return replicate('\t',indent) + var + " = QMeasure(" + lowerRange + "," + upperRange +")";
    }
}
