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

public class ForeignInv implements Invariant{

	String name;
	
	public ForeignInv(String name) {
		this.name=name;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoolExpr getInv(Context ctx) throws Z3Exception {
		Expr course = ctx.mkConst("course", ctx.mkUninterpretedSort(ctx.mkSymbol("Course")));
		Expr student = ctx.mkConst("student", ctx.mkUninterpretedSort(ctx.mkSymbol("Student")));
	    Expr e1=ctx.mkSetMembership(student, courseware.Student_set);
		Expr e2=ctx.mkSetMembership(course, courseware.Course_set);
			
	    Expr[] arg = new Expr[2];
		arg[0]=course;
		arg[1]=student;
			
		Sort[] studentCourse = new Sort[2];
		studentCourse[0] =ctx.mkUninterpretedSort(ctx.mkSymbol("Course"));
		studentCourse[1] =ctx.mkUninterpretedSort(ctx.mkSymbol("Student"));
		//setting names
		Symbol[] userNames = new Symbol[2];
		userNames[0] =  ctx.mkSymbol("course");
		userNames[1] =  ctx.mkSymbol("student");
		Expr ee = ctx.mkApp(courseware.mkpair.ConstructorDecl(), arg);
	    Expr enrolled = (BoolExpr) ctx.mkSetMembership(ee,courseware.Enroll_set );
	    Expr body=ctx.mkImplies((BoolExpr) enrolled, ctx.mkAnd(new BoolExpr[] { (BoolExpr) e1,(BoolExpr) e2}));
	        
		Expr Enrolledd = ctx.mkForall(studentCourse, userNames, body, 1, null, null,
		null, null);      
		System.out.println(" foriegn key invariant: " + Enrolledd.toString());
	  	return (BoolExpr) Enrolledd;
		    
	}

}
