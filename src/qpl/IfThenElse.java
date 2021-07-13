package qpl;

import boolterm.BoolTerm;
import states.State;
import utils.QPLExecutionException;

import static utils.StringUtil.replicate;

public class IfThenElse extends QPLCommand {
    
    private final BoolTerm cond;
    private final QPLCommand thenCase;
    private final QPLCommand elseCase;

    public IfThenElse(BoolTerm cond, QPLCommand thenCase, QPLCommand elseCase){
        this.cond = cond;
        this.elseCase = elseCase;
        this.thenCase = thenCase;
    }

    @Override
    public State execute(State state) throws QPLExecutionException {
        if(cond.value(state)){
            state = thenCase.execute(state);
        }else {
            state = elseCase.execute(state);
        }
        return state;
    }

    @Override
    public String toStringIndent(int indent) {
        String tabs = replicate('\t', indent);
        String result = tabs + "if (" + cond + ") ";
        if (thenCase instanceof BlockStatement) {
            result += thenCase.toStringIndent(indent) + " ";
        } else {
            result += '\n' + thenCase.toStringIndent(indent+1) + '\n';
        }
        result += tabs + "else ";
        if (elseCase instanceof BlockStatement) {
            result += elseCase.toStringIndent(indent);
        } else {
            result += '\n' + elseCase.toStringIndent(indent+1) + '\n';
        }
        return result;
    }
}
