package z3fol.model;

import com.microsoft.z3.BoolExpr;

import java.util.List;

public interface Schema {
    State getState();

    List<Operation> getOperations();

    List<BoolExpr> getInvariants(State state);
}
