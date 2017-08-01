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
import evaluation.courseware.courseObject;
import evaluation.courseware.courseware;

import java.util.ArrayList;
import java.util.List;



public class AddCourse  implements Operation  {

	private String name;
	private Expr course;
	private IntExpr capacityIndex;
	private IntExpr capacity;

	
		
	public AddCourse(String name, Context ctx) {
		this.name=name;
		try {
			courseObject c=new courseObject(ctx);
			this.course=c.getCourse();
			this.capacityIndex=(IntExpr) c.getIndex();
		//	this.capacity=c.getCapacity();
			this.capacity= (IntExpr) c.getCapacity();

		} catch (Z3Exception e) {
			e.printStackTrace();
		}
	
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public Expr[] getArgs(String name) {
		Expr [] args={this.course};
        return args;	
	}
	
	@Override
	public BoolExpr getCondition(Context ctx, Operation op) throws Z3Exception {	
	    return ctx.mkTrue();
	}

	/**
    * The precondition for adding a course is simply true.
	* @param ctx Z3 context
	* @return true 
	* @throws Z3Exception
	*/
	@Override
	public BoolExpr precondition(Context ctx) throws Z3Exception {
        return(ctx.mkTrue());
	}
	
	/**
	 * Add a course into the course list.
	 * @param ctx Z3 context
	 * @return  an expression in Z3 context which models the course list
	 * @throws Z3Exception
	 */
	@Override
	public Expr effect(Context ctx) throws Z3Exception {
		courseware.Course_set= ctx.mkSetAdd(courseware.Course_set,course);
		
		this.capacity=(IntExpr) ctx.mkITE(ctx.mkGe((ArithExpr) capacity, ctx.mkInt("1")), capacity,ctx.mkAdd(new ArithExpr[]
				{ctx.mkMul(new ArithExpr[] {capacity, ctx.mkInt("-1")}), ctx.mkInt("1")} ));

		
		System.out.println("adding new course"+	this.capacity);
		
		courseware.capacityArray=ctx.mkStore(courseware.capacityArray, capacityIndex, capacity);
	//	ctx.mkITE(ctx.mkGe((ArithExpr) capacity, ctx.mkInt("1")),ctx.mkEq(courseware.capacityArray,
		//		ctx.mkStore(courseware.capacityArray, capacityIndex, capacity)), ctx.mkTrue());
		
	

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
	public void putReplicaIndex(Context ctx, int rreplica) throws Z3Exception {
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
