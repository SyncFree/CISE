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

package evaluation.courseware;

import analyzer.Pair;
import application.Application;
import application.Operation;
import com.microsoft.z3.*;
import evaluation.courseware.invariant.BoundedInv;
import evaluation.courseware.invariant.ForeignInv;
import evaluation.courseware.operations.*;
import invariant.Invariant;

import java.util.*;


/**
 *  Model a courseware applicatio system.
 *  Each  student registers in the system 
 *   and she can enroll or dis-enroll in courses.
 *   The list of courses is updated by 
 *   adding a new course or removing an existing course.
 *   There are global invariants that must be preserved. 
 *   Constraint I1 is a simple bounded constraint on course;
 *   it states that the number of students enrolled in 
 *   a course must not exceed its capacity.
 *   Constraint I2 is a unique key constraint,
 *   stating that students should have unique identifiers.
 *   Invariant I3 implies the referential constraint, stating 
 *   students must be enrolled in an existing course.
 * 
 * @author Najafzadeh
 *
 */
public  class courseware extends Application {
	
	public static Sort Student; 
	public static Sort Course;
	public static Expr Student_set;
	public static Expr Course_set;
	public static SetSort ss;
	public static LinkedList<Pair<Expr,Expr>> enroll;
	public static DatatypeSort pair;
	public static Expr Enroll_set;  
	public static Constructor mkpair;
	public static Constructor mkpair2;
	public static Map<String, Operation> opList=new HashMap();
	public static List<Operation> op=new ArrayList<Operation>();
	public static List<Invariant> inv=new ArrayList<Invariant>();
    public static Sort array_type ;
    public static ArrayExpr capacityArray;
    
	public static DatatypeSort nat;
	
	 public static Sort array_type2 ;
	 public static ArrayExpr capacityArray2;
    
    /**
     * The constructor initializes the application with adding operations and invariants
     */
    public  courseware() {
	    		 
    }
    
	public  List<Operation> loadOperations(Context ctx) throws Z3Exception {
		
		Operation o1=new AddStudent("AddStudent",ctx);
		Operation o2=new AddCourse("AddCourse",ctx);
		Operation o3=new Enroll("Enroll",ctx);
		Operation o4=new RemoveStudent("RemoveStudent",ctx);
		Operation o5=new RemoveCourse("RemoveCourse",ctx);
		Operation o6=new Disenroll("Disenroll",ctx);
		op.add(o1);
		op.add(o2);
		op.add(o3);
		op.add(o4);
		op.add(o5);
		op.add(o6);	
		
		return op;
	}

	public List<Invariant> loadInvariants(Context ctx) throws Z3Exception  {
		Invariant inv1=new BoundedInv("bounded");
		Invariant inv2=new ForeignInv("referential");
        inv.add(inv1);
        inv.add(inv2);
        return inv;
	}
		
	public void initializeState(Context ctx) throws Z3Exception {
		
	    Student = ctx.mkUninterpretedSort("Student");
	    Course = ctx.mkUninterpretedSort("Course");
	   // capacity=(IntExpr) ctx.mkConst("capacity", ctx.mkIntSort());
	    ss=ctx.mkSetSort(Student);
		SetSort cc=ctx.mkSetSort(Course);
	    Student_set = ctx.mkConst("Student_set",ss);
	    Course_set = ctx.mkConst("Course_set",cc);
		//enroll=new LinkedList<Pair<Expr,Expr>>();
	    String[] argnames = new String[]{"first","second"};
	    Sort[] argsorts = new Sort[]{ ctx.mkUninterpretedSort(ctx.mkSymbol("Course")),  ctx.mkUninterpretedSort(ctx.mkSymbol("Student"))};
	    mkpair = ctx.mkConstructor("mkpair", "ispair", argnames, argsorts, null);
	    Constructor[] cons=new Constructor[]{mkpair};
	    DatatypeSort pair = ctx.mkDatatypeSort("pair", cons);
	    SetSort setOfPairs = ctx.mkSetSort(pair);
	    Enroll_set=ctx.mkConst("Enroll_set", setOfPairs);
 
        array_type = ctx.mkArraySort(ctx.mkIntSort(), ctx.mkIntSort());
        ctx.mkBound(1, array_type);
        capacityArray = (ArrayExpr) ctx.mkConst("capacityArray", array_type);
        
        array_type2 = ctx.mkArraySort(ctx.mkIntSort(), ctx.mkBitVecSort(32));
        capacityArray2 = (ArrayExpr) ctx.mkConst("capacityArray", array_type2);
	    
	}

	@Override
	public BoolExpr getCompoisteInvariant(Context ctx) throws Z3Exception {
		
		BoolExpr composite=ctx.mkTrue();
		for (Invariant i:inv )
		 composite=	ctx.mkAnd(new BoolExpr []{composite, (BoolExpr) i.getInv(ctx)});
		return composite;
	}

	


}
	
	
	

