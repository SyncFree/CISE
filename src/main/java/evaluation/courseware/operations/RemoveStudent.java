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

public class RemoveStudent implements Operation {
		
    private String name;
    public  Expr student;
	
	public RemoveStudent(String name, Context ctx) {
		 try {
			student = ctx.mkConst("student", ctx.mkUninterpretedSort(ctx.mkSymbol("Student")));;
		} catch (Z3Exception e) {
			e.printStackTrace();
		}
		this.name=name;
	}
	
	@Override
	public String getName() {
		return this.name;
	}	
	
	@Override
	public BoolExpr getCondition(Context ctx, Operation op) throws Z3Exception {
		if (op.getName()=="Enroll") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.getName())[0], this.student));
		}
		else
	        return ctx.mkTrue();
	}
	
	@Override
	public Expr [] getArgs(String  name) {
		Expr args[]={this.student};
		return args;
	}
	
	/**
	 * To remove a student, we have to check  that this student
	 * is not enrolled in  any courses.
	 * @param ctx Z3 context
	 * @return a bool expression in z3 context which models the percondition
	 * @throws Z3Exception
	 */
	@Override
	public BoolExpr precondition(Context ctx) throws Z3Exception {
		Expr course = ctx.mkConst("course", ctx.mkUninterpretedSort(ctx.mkSymbol("Course")));
	 	
    	//setting the name for the course
		Sort[] Courses = new Sort[1];
		Courses[0]=ctx.mkUninterpretedSort(ctx.mkSymbol("Course"));
		Symbol[] namess = new Symbol[1];
		namess[0] =  ctx.mkSymbol("course");
		
        Expr[] arg = new Expr[2];
		arg[0]=course;
		arg[1]=student;
        Expr enroll = ctx.mkApp(courseware.mkpair.ConstructorDecl(), arg);
        Expr noEnrolled = ctx.mkNot((BoolExpr) ctx.mkSetMembership(enroll, courseware.Enroll_set));
		Expr existEnroll= ctx.mkForall(Courses, namess, noEnrolled, 1, null, null,
				null, null);
  	    return (BoolExpr) existEnroll;
	}

	/**
	 * Remove a student from the student list.
	 * @param ctx Z3 context
	 * @return an expression in z3 context which models the student list 
	 * @throws Z3Exception
	 */
	@Override
	public Expr effect(Context ctx) throws Z3Exception {
		courseware.Student_set = ctx.mkSetDel(courseware.Student_set, this.student);
	    return courseware.Student_set;	
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
