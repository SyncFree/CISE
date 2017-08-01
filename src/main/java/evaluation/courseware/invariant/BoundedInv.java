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
import evaluation.courseware.courseObject;
import evaluation.courseware.courseware;
import invariant.Invariant;

public class BoundedInv implements Invariant {
	
	String name;

	public BoundedInv(String name) {
		this.name=name;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public BoolExpr getInv(Context ctx) throws Z3Exception {
		
		
		 Expr[] capacityindex =  new Expr[1];
		 capacityindex[0]=ctx.mkConst("course", ctx.mkUninterpretedSort(ctx.mkSymbol("Course")));;
	
	     
	     courseObject c1=new courseObject(ctx,1);
	     courseObject c2=new courseObject(ctx,2);

	     IntExpr capacityIndex1=(IntExpr) c1.getIndex();
	     IntExpr capacityIndex2=(IntExpr) c2.getIndex();
	     
	     Expr course1= c1.getCourse();
	     Expr course2= c2.getCourse();
	      
	     courseObject c=new courseObject(ctx);
		 
		 Expr capacity= ctx.mkSelect(courseware.capacityArray, capacityIndex1);
	
		 Expr capacityBound=ctx.mkGe((ArithExpr) capacity, ctx.mkInt("0"));
			
			
		  BoolExpr antecedent = ctx.mkNot(ctx.mkEq(capacityIndex1, capacityIndex2));
			
		  Expr distinctCapacity=ctx.mkImplies(ctx.mkNot(ctx.mkEq(course1, course2)), antecedent);
		  
		  Expr inv=ctx.mkAnd(new BoolExpr[]{(BoolExpr) distinctCapacity,(BoolExpr) capacityBound});
			
	 
          Sort[] indexbase = new Sort[1];
          indexbase[0] =ctx.mkUninterpretedSort(ctx.mkSymbol("Course"));
          
		 Symbol[] indexCNames = new Symbol[1];
		 indexCNames[0] =  ctx.mkSymbol("course");
				 
		 Expr bounded = ctx.mkForall(indexbase, indexCNames, inv, 1, null, null,null, null);
		 
         Sort[] indexbase1 = new Sort[2];
         indexbase1[0] =ctx.mkUninterpretedSort(ctx.mkSymbol("Course"));
         indexbase1[1] =ctx.mkUninterpretedSort(ctx.mkSymbol("Course"));
         
		 Symbol[] indexCNames1 = new Symbol[2];
		 indexCNames1[0] =  ctx.mkSymbol("course1");
		 indexCNames1[1] =  ctx.mkSymbol("course2");
		 
		 Expr bounded2 = ctx.mkForall(indexbase1, indexCNames1, inv, 1, null, null,null, null);
 
		 System.out.println(" bounded invariant: " + bounded2.toString());
		 
		 return (BoolExpr) bounded2;
	}


}
