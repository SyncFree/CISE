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

import z3fol.model.Operation;

import java.util.List;

public abstract class AnnotatedOperation implements XPROperation {

    @Override
    public List<String> getArgumentsXPR() {
        return AnnotationUtils.findXPRs(this.getClass(), XPR.Type.ARGUMENT);
    }

    @Override
    public List<String> getPreConditionsXPR() {
        return AnnotationUtils.findXPRs(this.getClass(), XPR.Type.PRECONDITION);
    }

    @Override
    public List<String> getTokensXPR() {
        return AnnotationUtils.findXPRs(this.getClass(), XPR.Type.Token);
    }

    @Override
    public List<String> getConditionsXPR() {
        return AnnotationUtils.findXPRs(this.getClass(), XPR.Type.CONDITIONS);
    }

    @Override
    public List<String> getEffectsXPR() {
        return AnnotationUtils.findXPRs(this.getClass(), XPR.Type.EFFECT);
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }


}
