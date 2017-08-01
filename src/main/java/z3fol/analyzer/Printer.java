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
