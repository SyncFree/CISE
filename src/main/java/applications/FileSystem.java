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



@XPR("Map Dir")
@XPR("Dir root")
@XPR(value = "tree invariant ", type = XPR.Type.INVARIANT)
@Op(FileSystem.moveDir.class)
public class FileSystem extends AnnotatedSchema {


    @XPR(value = {"Name name", "Dir dir","Dir Dir_s","Dir Dir_d"},
            type = XPR.Type.ARGUMENT)
    @XPR(value ="{reachable(root,Dir_d),!reachable(dir,Dir_d)}",
            type = XPR.Type.PRECONDITION)
    @XPR(value = "{(name, dir) in Dir_d and !((name, dir) in Dir_s)}",
            type = XPR.Type.EFFECT)
    @XPR(value = "token == true", type = XPR.Type.Token)
    public static class moveDir extends AnnotatedOperation { }


}