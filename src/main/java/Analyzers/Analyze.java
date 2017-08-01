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
