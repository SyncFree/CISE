package z3fol.xpr;

import com.google.common.collect.Lists;
import com.microsoft.z3.*;

import java.util.*;

public class Z3Utils {
    // Singleton
    private static Context ctx;

    @SuppressWarnings("serial")
    class TestFailedException extends Exception {
        public TestFailedException() {
            super("Check FAILED");
        }
    };


    public static Context ctx() {
        if (ctx == null) {
            Global.ToggleWarningMessages(true);
            Log.open("test.log");
            Map<String, String> cfg = new HashMap<>();
            cfg.put("model", "true");
            ctx = new Context(cfg);
        }
        return ctx;
    }


    public static Status check(BoolExpr... conditions) {
        return check(Lists.newArrayList(conditions));
    }

    public static Status check(List<BoolExpr> conditions) {
        Solver s = ctx().mkSolver();
        s.add(and(conditions));
        return s.check();
    }


    public static Model getModel(BoolExpr... conditions)  {
        Model m;
        try {
            return getModel(Lists.newArrayList(conditions));
        }  catch (TestFailedException e) {
            return null;
        }
    }


    public static Model getModel(List<BoolExpr> conditions) throws TestFailedException {
        Solver s = ctx().mkSolver();
        s.add(and(conditions));
        if (s.check()  == Status.SATISFIABLE)
            return s.getModel();
        else
            return null;
    }


    @SafeVarargs
    public static BoolExpr and(List<BoolExpr>... expressions) {
        BoolExpr[] array = Arrays.stream(expressions).flatMap(Collection::stream).toArray(Z3Utils::boolExprAllocator);
        if (array.length == 0) return ctx().mkTrue();
        if (array.length == 1) return array[0];
        return ctx().mkAnd(array);
    }


    public static Sort[] sortAllocator(int size) {
        return new Sort[size];
    }

    public static Symbol[] symbolAllocator(int size) {
        return new Symbol[size];
    }

    public static Expr[] exprAllocator(int size) {
        return new Expr[size];
    }

    public static BoolExpr[] boolExprAllocator(int size) {
        return new BoolExpr[size];
    }

    public static ArithExpr[] arithExprAllocator(int size) {
        return new ArithExpr[size];
    }
}
