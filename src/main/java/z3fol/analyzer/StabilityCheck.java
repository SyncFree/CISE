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
import z3fol.xpr.Processor;
import z3fol.xpr.Z3Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by najafzad on 08/10/15.
 */
public class StabilityCheck {

    public static void check(Schema schema) {

        List<Operation> op=schema.getOperations();
        Map<String,List<String>> set= new HashMap<String,List<String>>();

        for(int i=0;i<op.size();i++) {
            System.out.println("##############################################");
            System.out.println("##############################################");


            List<String> conflictingOps=new ArrayList<String>();
            List<Operation> opCon = schema.getOperations();

            for (int k = 0; k < opCon.size(); k++) {
                State stateBefore = schema.getState();
                System.out.print("stability checking for " + op.get(i).getName());
                System.out.println(" versus " + opCon.get(k).getName());
                op.get(i).putArguments(stateBefore);
                op.get(i).instantiateArguments(stateBefore);

                List<BoolExpr> invariantsBefore = schema.getInvariants(stateBefore);

                List<BoolExpr> preconditionsBefore = op.get(i).getPreConditions(stateBefore);
                BoolExpr before = Z3Utils.and(preconditionsBefore);
                System.out.println("Preconditions Before: " + before);

                State stateCopy = stateBefore.copy();

                List<BoolExpr> tokens=  op.get(i).getTokens(stateBefore);
                BoolExpr lock = Z3Utils.and(tokens);


                BoolExpr o=Z3Utils.ctx().mkEq(lock, Z3Utils.ctx().mkTrue());

                opCon.get(k).applyEffect(stateBefore);
                Expr balance=  stateBefore.getExpr("balance");
                stateBefore.putExpr("Token", Z3Utils.ctx().mkConst("token",Z3Utils.ctx().mkBoolSort()));
             /*  if(op.get(i).getName().equals("Debit") &&
              //          opCon.get(k).getName().equals("Debit")) {

                  //  stateBefore.putExpr("token", Z3Utils.ctx().mkTrue());
                    System.out.println("Concurrency control: token " );
                    Expr new_balance= stateCopy.getExpr("balance");

                    Expr condition = Z3Utils.ctx().mkITE((BoolExpr) stateBefore.getExpr("Token"), new_balance, balance);
                    stateBefore.putExpr("balance",condition);
                }
                else */
                   stateBefore.putExpr("balance",balance);

                System.out.println("Concurrent Effect: " + stateBefore.getExpr("balance"));
                List<BoolExpr> conditions = op.get(i).getConditions(stateBefore);

                BoolExpr before1 = Z3Utils.and( preconditionsBefore,conditions);

            //    if(op.get(i).getName().equals("Debit") &&
             //          opCon.get(k).getName().equals("Debit"))
             //  before1 = Z3Utils.ctx().mkAnd(before1, Z3Utils.ctx().
                 //      mkEq(stateBefore.getExpr("Token"), Z3Utils.ctx().mkTrue()));
                List<BoolExpr> preconditionsAfter = op.get(i).getPreConditions(stateBefore);

                BoolExpr after = Z3Utils.and(preconditionsAfter);
                System.out.println("Preconditions After: " + after);

                BoolExpr implication = Z3Utils.ctx().mkNot(Z3Utils.ctx().mkImplies(before1, after));

                System.out.println("Assertion: \n" + implication);

                Status status=Z3Utils.check(implication);
               // System.out.println("Result:" + status);

                Model m=Z3Utils.getModel(implication);
                System.out.println("counter example:"+m);

                if(status==Status.SATISFIABLE) {
                    conflictingOps.add(opCon.get(k).getName()) ;
                }

            }
            set.put(op.get(i).getName(), conflictingOps);
            System.out.println("check finishes, "+
                    "Conflicting Operations:"+set.get(op.get(i).getName()));
        }

    }

}
