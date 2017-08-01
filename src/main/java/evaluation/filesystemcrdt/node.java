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

package evaluation.filesystemcrdt;


import com.microsoft.z3.*;


public class node {

	public Expr id;
	public Expr child_set;
	
	
	public IntExpr limitArrayIndex;
	public IntExpr balance;
	public IntExpr creditLimit;

	
	public  node(Context ctx) throws Z3Exception {
		
	//	this.id=ctx.mkConst("node", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")));
		
	    SetSort ff=ctx.mkSetSort(filesystemcrdt.FSObject);
	     child_set = ctx.mkConst("child_set",ff);
		
		//this.child_set= ctx.mkConst("child_set",ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")));
		
		this.limitArrayIndex=(IntExpr) ctx.mkConst("balanceIndex",ctx.mkIntSort());
		
	}
	
	public  node(Context ctx, int i) throws Z3Exception {
		
		this.id=ctx.mkConst("node"+i, ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")));
		
		this.child_set= ctx.mkConst("child_set"+i,ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")));
		
		this.limitArrayIndex=(IntExpr) ctx.mkConst("balanceIndex"+i,ctx.mkIntSort());

	}

	public Expr getChilds() {
		return this.child_set;
	}
	
	
	public Expr getIndex() {
		return this.limitArrayIndex;
	}
	

	public void putChilds(Expr set_node) {
		 this.child_set=set_node;
	}
	
}