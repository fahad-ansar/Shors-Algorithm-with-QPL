package qpl;

import utils.Printable;
import states.State;
import utils.QPLExecutionException;

public abstract class QPLCommand extends Printable {

    public abstract State execute(State state) throws QPLExecutionException;

}
