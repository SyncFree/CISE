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

package evaluation.courseware.operations;

import application.Operation;
import com.microsoft.z3.*;
import evaluation.courseware.courseware;

import java.util.ArrayList;
import java.util.List;

public class RemoveCourse implements Operation  {
	
    private String name;
    private Expr course ;
	
	public RemoveCourse(String name, Context ctx) {
		this.name=name;
		try {
			this.course = ctx.mkConst("course", ctx.mkUninterpretedSort(ctx.mkSymbol("Course")));
		} catch (Z3Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getName() {
		return this.name;
	}
		
	public Expr getCourse() {
		return this.course;
	}
	
	@Override
	public BoolExpr getCondition(Context ctx, Operation op) throws Z3Exception {
		if (op.getName()=="Enroll") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.getName())[1], this.getCourse()));
		}
		else
	        return ctx.mkTrue();
	}
	
	@Override
	public Expr [] getArgs(String name) {
		Expr args []={this.getCourse()};
		return args;
	}
	
	/**
	 * To remove a course, we need to ensure that no student
	 * is  enrolled in the course.
	 * @param ctx Z3 context
	 * @return a boolean expression in z3 context which models the precondition
	 * @throws Z3Exception
	 */
	@Override
	public BoolExpr precondition(Context ctx) throws Z3Exception {

		Expr student = ctx.mkConst("student", ctx.mkUninterpretedSort(ctx.mkSymbol("Student")));

		//setting the name for the student
		Sort[] Students = new Sort[1];
		Students[0]=ctx.mkUninterpretedSort(ctx.mkSymbol("Student"));
		Symbol[] namess = new Symbol[1];
		namess[0] =  ctx.mkSymbol("student");
		
        Expr[] arg = new Expr[2];
		arg[0]=course;
		arg[1]=student;
        Expr enroll = ctx.mkApp(courseware.mkpair.ConstructorDecl(), arg);
        Expr noEnrolled = ctx.mkNot((BoolExpr) ctx.mkSetMembership(enroll, courseware.Enroll_set));
		Expr existEnroll = ctx.mkForall(Students, namess, noEnrolled, 1, null, null,
				null, null);
  	    return (BoolExpr) existEnroll;
	}

	/**
	 * Remove a course from the course list.
	 * @param ctx Z3 context
	 * @return an expression in z3 which models the course list
	 * @throws Z3Exception
	 */
	@Override
	public Expr effect(Context ctx) throws Z3Exception {
		courseware.Course_set = ctx.mkSetDel(courseware.Course_set, this.course);
		
	    return courseware.Course_set;	
	}
	
	@Override
	public List<Operation> concurrentOps(Context ctx) {
		List<Operation> ops=new ArrayList();
		Operation o1=new AddStudent("AddStudent",ctx);
		Operation o2=new AddCourse("AddCourse",ctx);
		Operation o3=new Enroll("Enroll",ctx);
		Operation o4=new RemoveStudent("RemoveStudent",ctx);
		Operation o5=new RemoveCourse("RemoveCourse",ctx);
		Operation o6=new Disenroll("Disenroll",ctx);
		ops.add(o1);
		ops.add(o2);
		ops.add(o3);
		ops.add(o4);
		ops.add(o5);
		ops.add(o6);	
		return ops;
		
	}
    @Override
    public int getReplicaIndex() {
	    return 0;
    }
    
    @Override
    public void putReplicaIndex(Context ctx, int replica) throws Z3Exception {
	// TODO Auto-generated method stub
	
    }

	@Override
	public Expr getConditions(Context ctx, Operation op) throws Z3Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putConcurrentOp(Context ctx, Operation op) throws Z3Exception {

	}

}
