package z3fol.xpr;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(XPR.All.class)
public @interface XPR {
    String[] value();
    Type type() default Type.MODEL;

    @Retention(RetentionPolicy.RUNTIME)
    @interface All {
        XPR[] value();
    }

    enum Type {
        MODEL,
        INVARIANT,
        ARGUMENT,
        PRECONDITION,
        CONDITIONS,
        EFFECT,
        Token,

    }
}
