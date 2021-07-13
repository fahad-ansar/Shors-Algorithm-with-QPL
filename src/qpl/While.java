package qpl;

import boolterm.BoolTerm;
import states.State;
import utils.QPLExecutionException;

import static utils.StringUtil.replicate;

public class While extends QPLCommand {
    
    private final BoolTerm cond;
    private final QPLCommand body;

    public While(BoolTerm cond, QPLCommand body){
        this.cond = cond;
        this.body = body;
    }

    @Override
    public State execute(State state) throws QPLExecutionException {
        while (cond.value(state)){
            state = body.execute(state);
        }
        return state;
    }

    @Override
    public String toStringIndent(int indent) {
        String tabs = replicate('\t',indent);
        String result = tabs + "while (" + cond + ") ";
        if (body instanceof BlockStatement) {
            result += body.toStringIndent(indent);
        } else {
            result += '\n' + body.toStringIndent(indent+1);
        }
        return result;
    }
}
