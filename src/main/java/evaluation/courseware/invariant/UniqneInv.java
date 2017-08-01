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

package evaluation.courseware.invariant;

import com.microsoft.z3.*;
import evaluation.courseware.courseware;
import invariant.Invariant;

public class UniqneInv implements Invariant {

	
	String name;
	
	public UniqneInv(String name){
		this.name=name;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public BoolExpr getInv(Context ctx) throws Z3Exception {
		 courseware.Student = ctx.mkUninterpretedSort("Student");
         Sort[] students = new Sort[1];
         students[0] =ctx.mkUninterpretedSort(ctx.mkSymbol("Student"));
    
         Expr[] arg = new Expr[2];
		 arg[0]=ctx.mkConst("s1", ctx.mkUninterpretedSort(ctx.mkSymbol("Student")));;
		 arg[1]=ctx.mkConst("s2", ctx.mkUninterpretedSort(ctx.mkSymbol("Student")));;
		
		 Sort[] studentset = new Sort[2];
		 studentset[0] =ctx.mkUninterpretedSort(ctx.mkSymbol("Student"));
		 studentset[1] =ctx.mkUninterpretedSort(ctx.mkSymbol("Student"));
		 //setting names
		 Symbol[] namess = new Symbol[2];
		 namess[0] =  ctx.mkSymbol("s1");
		 namess[1] =  ctx.mkSymbol("s2");
         Expr body = ctx.mkNot(ctx.mkEq(arg[0], arg[1]));
         BoolExpr mustUniqueee = ctx.mkForall(studentset, namess, body, 1, null, null,
         null, null);
         System.out.println("Quantifier Uniqueness: " + mustUniqueee.toString());
		 return mustUniqueee;
	}

}
