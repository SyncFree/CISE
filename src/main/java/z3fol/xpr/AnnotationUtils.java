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

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AnnotationUtils {

    public static List<String> findXPRs(Class<?> clazz, XPR.Type type) {
        return Arrays
                .stream(clazz.getAnnotationsByType(XPR.class))
                .filter(x -> x.type() == type)
                .flatMap(x -> Arrays.stream(x.value()))
                .collect(Collectors.toList());
    }

    public static List<Operation> findOps(Class<?> clazz) {
        return Arrays
                .stream(clazz.getAnnotationsByType(Op.class))
                .map(Op::value)
                .map(AnnotationUtils::instantiate)
                .collect(Collectors.toList());
    }

    private static Operation instantiate(Class<? extends Operation> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalStateException("Annotation referenced operations must have a no-arg constructor");
        }
    }

}
