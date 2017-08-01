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

package z3fol.model;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Expr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Operation {


    List<Expr> exprs = new ArrayList<>();
    String getName();
    List<BoolExpr> getPreConditions(State state);
    List<BoolExpr> getConditions(State state);
    List<BoolExpr> getTokens(State state);
    void instantiateArguments(State state);
    void applyEffect(State state);
    void putArguments(State state) ;

   // List<BoolExpr> getConcConditions(State state);

}
