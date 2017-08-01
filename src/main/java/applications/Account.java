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


@XPR("Int balance")
@XPR(value = "balance >= 0", type = XPR.Type.INVARIANT)
@Op(Account.Deposit.class)
@Op(Account.Debit.class)
public class Account extends AnnotatedSchema {

    @XPR(value ={"Int amount","Int balance", "Bool token"},type =XPR.Type.ARGUMENT)
    @XPR(value ="amount >= 0",type = XPR.Type.PRECONDITION)
    @XPR(value ="balance := balance + amount",type =XPR.Type.EFFECT)
    public static class Deposit extends AnnotatedOperation { }

    @XPR(value ={"Int amount","Int balance", "Bool token"},type =XPR.Type.ARGUMENT)
    @XPR(value ={"amount >= 0", "amount<=balance"},type = XPR.Type.PRECONDITION)
    @XPR(value ="balance := balance - amount",type =XPR.Type.EFFECT)
    @XPR(value ="token==true", type=XPR.Type.Token)
    public static class Debit extends AnnotatedOperation { }
}