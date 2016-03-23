package z3fol.analyzer;

import com.microsoft.z3.BoolExpr;
import z3fol.model.Operation;
import z3fol.model.Schema;
import z3fol.model.State;

import java.util.List;

public class Printer {

    public static void print(Schema schema) {
        State state = schema.getState();

        System.out.println("-- State:");
        System.out.println(state);

        System.out.println("-- Invariants:");
        schema.getInvariants(state).forEach(System.out::println);

        for(Operation op: schema.getOperations()) {
            System.out.println("-- Operation: " + op.getClass().getSimpleName());

            State newState = state.copy();

            op.instantiateArguments(newState);
            List<BoolExpr> conditions = op.getPreConditions(newState);
            System.out.println("----- Conditions:");
            System.out.println(conditions);

            op.applyEffect(newState);
            System.out.println("----- State after:");
            System.out.println(newState);

        }
    }
}
