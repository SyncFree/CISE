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

package evaluation.filesystemcrdt.invariant;

import com.microsoft.z3.*;
import evaluation.filesystemcrdt.filesystemcrdt;
import invariant.Invariant;

public class UniqueRoot implements Invariant {

	String name;
	
	public UniqueRoot(String name){
		this.name=name;
	}
	
	@Override
	public String getName() {
		return this.name;
	}


	@Override
	public BoolExpr getInv(Context ctx) throws Z3Exception {
		
		  Sort[] nodes = new Sort[1];
		  nodes[0] =ctx.mkUninterpretedSort(ctx.mkSymbol("Dir"));
		 //setting names
		  Symbol[] namess = new Symbol[1];
		  namess[0] =  ctx.mkSymbol("dir1");

          Expr node1=ctx.mkConst("dir1", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")) );

		  Expr[] existArgs =  new Expr[2];
	      existArgs[0]=ctx.mkConst("dir1", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));
	      existArgs[1]=filesystemcrdt.root;
	    		
	      Expr exist= (BoolExpr) ctx.mkApp(filesystemcrdt.exists, existArgs)  ;
	    	 
	 	  Expr[] arg= new Expr[3];
	 	  arg[0]=filesystemcrdt.root;
	 	  arg[1]=ctx.mkConst("dir1", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));
	 	  arg[2]=exist;	
	 	  Expr reachabilityTuple=filesystemcrdt.Reachability.mkDecl().apply(arg);
	 	  Expr rootReachability= ctx.mkSetMembership(reachabilityTuple,filesystemcrdt.Reachable_set);
 
          BoolExpr inv =ctx.mkForall(nodes, namess, ctx.mkAnd(new BoolExpr []
	    		  {(BoolExpr)  ctx.mkSetMembership(filesystemcrdt.root,filesystemcrdt.Dir_set), ctx.mkNot(ctx.mkEq(filesystemcrdt.root,node1 )),
        		  ctx.mkNot((BoolExpr) rootReachability) }), 1, null, null,  null, null);
        
       //   System.out.println("Root Uniqueness: " + inv.toString());

		  return inv;
	
	}

}
