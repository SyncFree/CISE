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

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(XPR.All.class)
public @interface XPR {
    String[] value();
    Type type() default Type.MODEL;

    @Retention(RetentionPolicy.RUNTIME)
    @interface All {
        XPR[] value();
    }

    enum Type {
        MODEL,
        INVARIANT,
        ARGUMENT,
        PRECONDITION,
        CONDITIONS,
        EFFECT,
        Token,

    }
}
