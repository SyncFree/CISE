package z3fol.xpr;

import z3fol.model.Operation;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Op.Multiple.class)
public @interface Op {
    Class<? extends Operation> value();

    @Retention(RetentionPolicy.RUNTIME)
    @interface Multiple {
        Op[] value();
    }
}
