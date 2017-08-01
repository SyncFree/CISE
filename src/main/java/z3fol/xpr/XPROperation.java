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

package z3fol.xpr;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Expr;
import z3fol.model.Operation;
import z3fol.model.State;

import java.util.List;

public interface XPROperation extends Operation {

    List<String> getArgumentsXPR();
    List<String> getPreConditionsXPR();
    List<String> getConditionsXPR();
    List<String> getTokensXPR();
    List<String> getEffectsXPR();

    @Override
    default List<BoolExpr> getPreConditions(State state) {
        return Processor.process(state.copy(), getPreConditionsXPR());
    }

    @Override
    default List<BoolExpr> getConditions(State state) {
        return Processor.process(state.copy(), getConditionsXPR());
    }

    @Override
    default List<BoolExpr> getTokens(State state) {
        return Processor.process(state.copy(), getTokensXPR());
    }

    @Override
    default void instantiateArguments(State state) {
        Processor.process(state, getArgumentsXPR());
    }


    @Override
    default void putArguments(State state) {
         Processor.process(state, getArgumentsXPR());
    }


    @Override
    default void applyEffect(State state) {
        Processor.process(state, getEffectsXPR());
    }


}
