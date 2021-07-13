package qpl;

import intterm.IntTerm;
import states.State;
import utils.QPLExecutionException;

import static utils.StringUtil.replicate;

public class QInitialize extends QPLCommand  {

    IntTerm numBits;

    public QInitialize(IntTerm n){
        this.numBits = n;
    }

    @Override
    public State execute(State state) throws QPLExecutionException {
        state.initQRegister(numBits.value(state));
        return state;
    }

    @Override
    public String toStringIndent(int indent) {
        return replicate('\t',indent) + "QInitialize(" + numBits + ")";
    }
}
