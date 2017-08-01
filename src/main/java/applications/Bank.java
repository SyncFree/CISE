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

package applications;

import z3fol.xpr.AnnotatedOperation;
import z3fol.xpr.AnnotatedSchema;
import z3fol.xpr.Op;
import z3fol.xpr.XPR;

/**
 * Created by najafzad on 08/10/15.
 */

@XPR("Int counter")
@XPR(value = "counter >= 0", type = XPR.Type.INVARIANT)
@Op(Bank.AddAccount.class)
@Op(Bank.Deposit.class)
@Op(Bank.Debit.class)
@Op(Bank.Interest.class)
@Op(Bank.Transit.class)

public class Bank extends AnnotatedSchema {

    @XPR(value = "Int value", type = XPR.Type.ARGUMENT)
    @XPR(value = "value >= 0", type = XPR.Type.PRECONDITION)
    @XPR(value = "counter := counter + value", type = XPR.Type.EFFECT)
    public static class AddAccount extends AnnotatedOperation { }

    @XPR(value = "Int value", type = XPR.Type.ARGUMENT)
    @XPR(value = "value >= 0", type = XPR.Type.PRECONDITION)
    @XPR(value = {"counter >= value", "value >= 0"}, type = XPR.Type.INVARIANT)
    @XPR(value = "counter := counter - value", type = XPR.Type.EFFECT)
    public static class Deposit extends AnnotatedOperation { }

    @XPR(value = "Int value", type = XPR.Type.ARGUMENT)
    @XPR(value = "value >= 0", type = XPR.Type.PRECONDITION)
    @XPR(value = {"counter >= value", "value >= 0"}, type = XPR.Type.INVARIANT)
    @XPR(value = "counter := counter - value", type = XPR.Type.EFFECT)
    public static class Debit extends AnnotatedOperation { }

    @XPR(value = "Int value", type = XPR.Type.ARGUMENT)
    @XPR(value = "value >= 0", type = XPR.Type.PRECONDITION)
    @XPR(value = {"counter >= value", "value >= 0"}, type = XPR.Type.INVARIANT)
    @XPR(value = "counter := counter - value", type = XPR.Type.EFFECT)
    public static class Interest extends AnnotatedOperation { }

    @XPR(value = "Int value", type = XPR.Type.ARGUMENT)
    @XPR(value = "value >= 0", type = XPR.Type.PRECONDITION)
    @XPR(value = {"counter >= value", "value >= 0"}, type = XPR.Type.INVARIANT)
    @XPR(value = "counter := counter - value", type = XPR.Type.EFFECT)
    public static class Transit extends AnnotatedOperation { }

}