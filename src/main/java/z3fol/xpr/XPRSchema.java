package z3fol.xpr;

import com.microsoft.z3.BoolExpr;
import z3fol.model.Schema;
import z3fol.model.State;

import java.util.List;

/**
 * Describes the complete structure of the database and operations
 */
public abstract class XPRSchema implements Schema {

    protected abstract List<String> getModelXPRs();
    protected abstract List<String> getInvariantXPRs();

    private final State state;

    public XPRSchema() {
        state = new State();
        Processor.process(state, getModelXPRs());
    }

    @Override
    public State getState() {
        return state.copy();
    }

    @Override
    public List<BoolExpr> getInvariants(State state) {
        return Processor.process(state.copy(), getInvariantXPRs());
    }

}
