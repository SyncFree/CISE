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

package z3fol.analyzer;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Expr;
import com.microsoft.z3.Model;
import com.microsoft.z3.Status;
import z3fol.model.Operation;
import z3fol.model.Schema;
import z3fol.model.State;
import z3fol.xpr.Z3Utils;

import java.util.*;

/**
 * Created by najafzad on 08/10/15.
 */
public class CommutativityCheck {

    public static void check(Schema schema) {

        List<Operation> op=schema.getOperations();

        Map<String,List<String>> set= new HashMap<String,List<String>>();

        for(int i=0;i<op.size();i++) {
            System.out.println("##############################################");
            System.out.println("##############################################");


            List<String> conflictingOps=new ArrayList<String>();
            List<Operation> opCon = schema.getOperations();

            for (int k = 0; k < opCon.size(); k++) {

                List<Expr> right=new ArrayList<Expr>();
                List<Expr> left=new ArrayList<Expr>();

                State stateBefore = schema.getState();
                System.out.print("Commutativity checking for  " + op.get(i).getName());

                System.out.println("   versus " + opCon.get(k).getName());
                op.get(i).instantiateArguments(stateBefore);

                State stateAfter1 = stateBefore.copy();
                op.get(i).applyEffect(stateAfter1);

                State stateAfter2 = stateAfter1.copy();
                opCon.get(k).applyEffect(stateAfter2);


                State stateAfter3 = stateBefore.copy();
                opCon.get(k).applyEffect(stateAfter3);

                State stateAfter4 = stateAfter3.copy();
                op.get(i).applyEffect(stateAfter4);

                BoolExpr equlity=Z3Utils.ctx().mkNot(stateAfter4.compareTo(stateAfter2));
                System.out.println("Assertion: \n" + equlity);

                Status status=Z3Utils.check(equlity);
              //  System.out.println("Result:" + status);
                Model m=Z3Utils.getModel(equlity);
                System.out.println("counter example:"+m);
                if(status==Status.SATISFIABLE) {
                    conflictingOps.add(opCon.get(k).getName()) ;
                }
                System.out.println("----------------------------------------");
            }
            set.put(op.get(i).getName(), conflictingOps);
            System.out.println("check finishes,  " +
                    "Conflicting Operations:"+set.get(op.get(i).getName()));
        }

    }

}
