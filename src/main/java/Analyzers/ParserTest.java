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
import z3fol.model.State;
import z3fol.xpr.Processor;
import java.util.List;


public class ParserTest {
    public static void main(String[] args) {

        State s = new State();

        List<BoolExpr> result = Processor.process(s, "¬ (Exists Int x, Int y: x <= y)");

        System.out.println(result.get(0));

    }

}
