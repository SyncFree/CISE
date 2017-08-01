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

import applications.Account;
import z3fol.analyzer.CommutativityCheck;
import z3fol.analyzer.StabilityCheck;
import z3fol.xpr.AnnotatedSchema;

/**
 * Created by najafzad on 20/10/15.
 */

public class StabilityTest {
    public static void main(String[] args)  {
        AnnotatedSchema c=new Account();
        StabilityCheck.check(c);
    }

}
