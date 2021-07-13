package qpl;

import java.util.List;
import states.State;
import utils.QPLExecutionException;

import static utils.StringUtil.replicate;

public class BlockStatement extends QPLCommand {

    private final List<QPLCommand> block;

    public BlockStatement(List<QPLCommand> block){
        this.block = block;
    }

    @Override
    public State execute(State state) throws QPLExecutionException {
        for(QPLCommand p : block) {
            state = p.execute(state);
        }
        return state;
    }
    
    public String toStringBody(int indent) {
        String result = "";
        for(int i=0; i<block.size(); i++) {
            result += block.get(i).toStringIndent(indent+1);
            if (i==block.size()-1) {
                result += "\n";
            } else {
                result += ";\n";
            }
        }
        return result;
    }

    @Override
    public String toStringIndent(int indent) {
        String result = "";
        if (!block.isEmpty()) {
            result += "{\n" + toStringBody(indent) + replicate('\t',indent) + "}";
        }
        return result;
    }
}
