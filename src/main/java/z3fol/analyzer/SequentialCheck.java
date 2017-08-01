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
import com.microsoft.z3.Status;
import com.microsoft.z3.Model;
import z3fol.model.Operation;
import z3fol.model.Schema;
import z3fol.model.State;
import z3fol.xpr.Z3Utils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SequentialCheck {

    public static Map<String, Status> check(Schema schema) {

        return schema.getOperations().stream().collect(Collectors.toMap(Operation::getName, (op) -> {
            State stateBefore = schema.getState();
            op.instantiateArguments(stateBefore);

            List<BoolExpr> invariantsBefore = schema.getInvariants(stateBefore);
            List<BoolExpr> preconditionsBefore = op.getPreConditions(stateBefore);

            List<BoolExpr> conditions = op.getConditions(stateBefore);

            State stateAfter = stateBefore.copy();
            op.applyEffect(stateAfter);
            List<BoolExpr> invariantsAfter = schema.getInvariants(stateAfter);

            BoolExpr before = Z3Utils.and(invariantsBefore, preconditionsBefore,conditions);
            BoolExpr after = Z3Utils.and(invariantsAfter);
            BoolExpr implication = Z3Utils.ctx().mkNot(Z3Utils.ctx().mkImplies(before, after));

            return Z3Utils.check(implication);

        }));

    }

    public static Model model(Schema schema, Operation op) {

            State stateBefore = schema.getState();
            op.instantiateArguments(stateBefore);

            List<BoolExpr> invariantsBefore = schema.getInvariants(stateBefore);
            List<BoolExpr> preconditionsBefore = op.getPreConditions(stateBefore);

            List<BoolExpr> conditions = op.getConditions(stateBefore);
            State stateAfter = stateBefore.copy();
            op.applyEffect(stateAfter);
            List<BoolExpr> invariantsAfter = schema.getInvariants(stateAfter);

            BoolExpr before = Z3Utils.and(invariantsBefore, preconditionsBefore,conditions);
            BoolExpr after = Z3Utils.and(invariantsAfter);
            BoolExpr implication = Z3Utils.ctx().mkNot(Z3Utils.ctx().mkImplies(before, after));

            return Z3Utils.getModel(implication);

    }
}
