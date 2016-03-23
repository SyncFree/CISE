package Analyzers;

import z3fol.xpr.AnnotatedOperation;
import z3fol.xpr.AnnotatedSchema;
import z3fol.xpr.Op;
import z3fol.xpr.XPR;

/**
 * Created by najafzad on 08/10/15.
 */
public class Analyze {

    public static void main(String[] args) {

      //  Map<String, Status> Analyze = SequentialCheck.check();
     //   System.out.println(Analyze.get("Add"));
      //  System.out.println(Analyze.get("Del"));
    }


    @XPR("Int counter")
    @XPR(value = "counter >= 0", type = XPR.Type.INVARIANT)
    @Op(Counter.Add.class)
    @Op(Counter.Del.class)
    public static class Counter extends AnnotatedSchema {

        @XPR(value = "Int value", type = XPR.Type.ARGUMENT)
        @XPR(value = "value >= 0", type = XPR.Type.PRECONDITION)
        @XPR(value = "counter := counter + value", type = XPR.Type.EFFECT)
        public static class Add extends AnnotatedOperation { }

        @XPR(value = "Int value", type = XPR.Type.ARGUMENT)
        @XPR(value = {"counter >= value", "value >= 0"}, type = XPR.Type.PRECONDITION)
        @XPR(value = "counter := counter - value", type = XPR.Type.EFFECT)
        public static class Del extends AnnotatedOperation { }
    }
}
