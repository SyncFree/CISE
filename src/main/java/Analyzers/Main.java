/*******************************************************************************
 * ===========================================================
 * This file is part of the CISE tool software.
 *
 * The CISE tool software contains proprietary and confidential information of Inria.
 * All rights reserved. Reproduction, adaptation or distribution, in whole or in part, is
 * forbidden except by express written permission of Inria.
 * Version V1.5.1., July 2017
 * Authors: Mahsa Najafzadeh, Michał Jabczyński, Marc Shapiro
 * Copyright (C) 2017, Inria
 * ===========================================================
 ******************************************************************************/

package Analyzers;

import com.microsoft.z3.BoolExpr;
import z3fol.analyzer.CommutativityCheck;
import z3fol.model.State;
import z3fol.xpr.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        State s = new State();
        s.putExpr("myBool", Z3Utils.ctx().mkTrue());

        List<BoolExpr> result = Processor.process(s, "¬ (Exists Int x, Int y: x >= y)");

        Processor.process(s,
                "type AccountID;",
                "AccountID{} ids;");

        Processor.process(s, "AccountID s;");


        Processor.process(s, "AccountID s;", "ids := ids ∪ {s};");

        System.out.println(s.getExpr("ids"));

        result = Processor.process(s, "{s} ⊂ ids");

        System.out.println(result.get(0));

      // //AnnotatedSchema c=new Account();
      //  CommutativityCheck.check(c);
     //   List<Operation> op=c.getOperations();

   /*   Map<String, Status> result = SequentialCheck.check(c);


        for(int i=0;i<op.size();i++ ) {
            System.out.println("operation  "+ op.get(i).getName()+ ": ");
            System.out.println(result.get(op.get(i).getName()));
            System.out.println("counter-example:");
            Model model = SequentialCheck.model(c,op.get(i) );

            System.out.println(model);
        } //

          StabilityCheck.check(c);

     /*   for(int i=0;i<op.size();i++ ) {
            System.out.println("operation  "+ op.get(i).getName()+ ": ");
            System.out.println(result2.get(op.get(i).getName()));
            System.out.println("counter-example:");
            Model model = StabilityCheck.model(c,op.get(i) );

            System.out.println(model);
        }*/
    }

  /*  @XPR("Int counter")
    @XPR(value = "counter >= 0", type = XPR.Type.INVARIANT)
    @Op(Account.Add.class)
    @Op(Account.Del.class)
    public static class Account extends AnnotatedSchema {

        @XPR(value = "Int value", type = XPR.Type.ARGUMENT)
        @XPR(value = "value >= 0", type = XPR.Type.PRECONDITION)
        @XPR(value = "counter := counter + value", type = XPR.Type.EFFECT)
        public static class Add extends AnnotatedOperation { }

        @XPR(value = "Int value", type = XPR.Type.ARGUMENT)
        @XPR(value = "value >= 0", type = XPR.Type.PRECONDITION)
        @XPR(value = {"counter >= value", "value >= 0"}, type = XPR.Type.INVARIANT)
        @XPR(value = "counter := counter - value", type = XPR.Type.EFFECT)
        public static class Del extends AnnotatedOperation { }
    } */
}
