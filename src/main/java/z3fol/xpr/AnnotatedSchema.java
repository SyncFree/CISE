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

public abstract class AnnotatedSchema extends XPRSchema {

    @Override
    public List<String> getModelXPRs() {
        return AnnotationUtils.findXPRs(this.getClass(), XPR.Type.MODEL);
    }

    @Override
    protected List<String> getInvariantXPRs() {
        return AnnotationUtils.findXPRs(this.getClass(), XPR.Type.INVARIANT);
    }

    @Override
    public List<Operation> getOperations() {
        return AnnotationUtils.findOps(this.getClass());
    }



}
