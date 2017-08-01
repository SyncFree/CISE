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

import com.microsoft.z3.Status;
import com.microsoft.z3.Model;
import z3fol.analyzer.CommutativityCheck;
import z3fol.xpr.*;

import java.util.List;
import java.util.Map;
import z3fol.model.Operation;
import applications.Account;
/**
 * Created by najafzad on 20/10/15.
 */
public class CommutativityTest {
    public static void main(String[] args)  {
    AnnotatedSchema c=new Account();
    CommutativityCheck.check(c);
    }

}
