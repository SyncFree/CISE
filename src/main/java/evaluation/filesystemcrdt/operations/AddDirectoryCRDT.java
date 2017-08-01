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

package evaluation.filesystemcrdt.operations;

import application.Operation;
import com.microsoft.z3.*;
import evaluation.filesystemcrdt.filesystemcrdt;
import evaluation.filesystemcrdt.node;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddDirectoryCRDT implements Operation {
	
	private String name;

	private Expr  fatherId;
	private Expr  dirId;
	private Expr  nodeId;
	private Expr dir;
	private Expr dname;
    int index;
    node a;
    node b;
	public AddDirectoryCRDT(String name, Context ctx) {
		
		this.name=name;
		try {
   	 	
			Random rand=new Random();     
		    index = rand.nextInt((20 - 6) + 1) + 6;
		    
			this.dname=ctx.mkConst("name1", ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));
			this.fatherId=ctx.mkConst("dir3", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));
	   	 	this.nodeId=ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")));
	   	 	this.dirId=ctx.mkConst("dir1", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));
	   	 	
		    Expr[] nodeArgs =  new Expr[3];
		    nodeArgs[0]=nodeId;
		    nodeArgs[1]=ctx.mkConst("type", filesystemcrdt.type);
		    nodeArgs[2]=ctx.mkConst("name1", ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));
	   	 	this.dir=filesystemcrdt.Node.mkDecl().apply(nodeArgs);
	
	   	 	
	   	 	Expr s=ctx.mkConst("child_set", ctx.mkSetSort(filesystemcrdt.FSObject));
			a=new node(ctx);
		  // a.putChilds(s);
			b=new node(ctx);
			
			

		} catch (Z3Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getReplicaIndex() {
		return 0;
	}

	@Override
	public BoolExpr precondition(Context ctx) throws Z3Exception {
		
     	 Expr[] finalArgs =  new Expr[2];
     	 finalArgs[0]=filesystemcrdt.root;
     	 finalArgs[1]=fatherId;
     		
     	 Expr tbody= ctx.mkApp(filesystemcrdt.exists, finalArgs)  ;
    	 
 	     Expr[] arg= new Expr[3];
 	     arg[0]=fatherId;
 	     arg[1]=filesystemcrdt.root;	
 	     arg[2]=tbody;	
 		 Expr reachTuple=filesystemcrdt.Reachability.mkDecl().apply(arg);
 		 Expr reachable= ctx.mkSetMembership(reachTuple,filesystemcrdt.Reachable_set);
		 
		 Sort[] nodes = new Sort[1];
		 nodes[0] =ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject"));

		 Symbol[] namess = new Symbol[1];
		 namess[0] =  ctx.mkSymbol("node2");
		 
		 Expr nodeId=ctx.mkConst("node2", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")) );
		 
 		 Expr[] fatherindex =  new Expr[3];
    	 fatherindex[0]=fatherId;
		 fatherindex[1]=nodeId;
		 fatherindex[2]=ctx.mkConst("name2", ctx.mkUninterpretedSort(ctx.mkSymbol("Name")) );;
		 
	     Expr parentTuple=filesystemcrdt.Parent.mkDecl().apply(fatherindex);
	     
	 	 Expr fatherRelation=(BoolExpr) ctx.mkSetMembership(parentTuple, filesystemcrdt.Parent_set);
 	 	
		 Expr[] nodeArgs1 =  new Expr[3];
		 nodeArgs1[0]=nodeId;
		 nodeArgs1[1]=ctx.mkConst("type", filesystemcrdt.type);
		 nodeArgs1[2]=ctx.mkConst("name2", ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));

	 	 BoolExpr mustUniqueee= ctx.mkNot(ctx.mkExists(nodes, namess, ctx.mkAnd(new BoolExpr []{(BoolExpr) fatherRelation,ctx.mkEq(nodeArgs1[2],dname)}), 1, null, null, null,null));
		 
	     Expr dir=ctx.mkConst("dir3", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));
	     Expr node1=ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")) );
	     Expr name1=ctx.mkConst("name1", ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));
    	 
    	 Expr precondition=ctx.mkAnd(new BoolExpr [] { (BoolExpr) reachable, ctx.mkEq(node1, nodeId), ctx.mkEq(dname, name1), ctx.mkEq(dir, fatherId) });
    	    		  
		 return (BoolExpr) precondition;
	}

	@Override
	public Expr effect(Context ctx) throws Z3Exception {
		
		filesystemcrdt.Node_set= ctx.mkSetAdd(filesystemcrdt.Node_set,dir);
		filesystemcrdt.FSObject_set= ctx.mkSetAdd(filesystemcrdt.FSObject_set,nodeId);
		filesystemcrdt.Dir_set= ctx.mkSetAdd(filesystemcrdt.Dir_set,ctx.mkConst("dir1"+index, ctx.mkUninterpretedSort(ctx.mkSymbol("Dir"))));
		
	    Expr[] argParent= new Expr[3];
	    argParent[0]=fatherId;
	    argParent[1]=nodeId;	
	    argParent[2]=dname;
	    
		Expr parentTuple=filesystemcrdt.Parent.mkDecl().apply(argParent);
		filesystemcrdt.Parent_set= ctx.mkSetAdd(filesystemcrdt.Parent_set,parentTuple);
		
		
		 a.child_set=   ctx.mkSetAdd(a.child_set, nodeId);
		 
		// System.out.println("childsss"+a.child_set);
		
    	 Expr[] finalArgs =  new Expr[2];
    	 finalArgs[0]=filesystemcrdt.root;
    	 finalArgs[1]=ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));
    		
    	 Expr tbody= (BoolExpr) ctx.mkApp(filesystemcrdt.exists, finalArgs)  ;
    	 
 	     Expr[] arg= new Expr[3];
 	     arg[0]=dirId;
 	     arg[1]=filesystemcrdt.root;
 	     arg[2]=tbody;	
 		 Expr reachableTuple=filesystemcrdt.Reachability.mkDecl().apply(arg);
 		 filesystemcrdt.Reachable_set= ctx.mkSetAdd(filesystemcrdt.Reachable_set,reachableTuple);
 		 	
 	    
 		 filesystemcrdt.contentArray=ctx.mkStore(filesystemcrdt.contentArray,
				 ctx.mkConst("dir" + index, ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject"))), ctx.mkSetAdd(ctx.mkSelect(filesystemcrdt.contentArray,
						 ctx.mkConst("dir" + index, ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")))), nodeId));
 		
 		
 	     SetSort ff=ctx.mkSetSort(filesystemcrdt.FSObject);
 	     Expr child_set = ctx.mkConst("child_set",ff);


 		 filesystemcrdt.contentArray=ctx.mkStore(filesystemcrdt.contentArray, nodeId, child_set);

	     return filesystemcrdt.Parent_set ;

	}

	@Override
	public void putReplicaIndex(Context ctx, int r) throws Z3Exception {
		
	}

	@Override
	public Expr[] getArgs(String name) {
		
		Expr [] args={dname, fatherId, dirId, nodeId};
		return args;
	}

	@Override
	public BoolExpr getCondition(Context ctx, Operation op) throws Z3Exception {

        return ctx.mkTrue();
	}

	@Override
	public List<Operation> concurrentOps(Context ctx) {
		
		List<Operation> op=new ArrayList<Operation>();

		Operation o1=new AddFileCRDT("AddFile",ctx);
		Operation o2=new RemoveFileCRDT("RemoveFile",ctx);
		Operation o3=new UpdateFileCRDT("UpdateFile",ctx);
		Operation o4=new MoveFileCRDT("MoveFile",ctx);
		Operation o5=new AddDirectoryCRDT("AddDirectory",ctx);
		Operation o6=new RemoveDirectoryCRDT("RemoveDirectory",ctx);
		Operation o7=new MoveDirectoryCRDT("MoveDirectory", ctx);
		
	/*	op.add(o1);
		op.add(o2);
		op.add(o3);
		op.add(o4);
		op.add(o5);*/
		op.add(o6);		
		//op.add(o7);
		
		return op;
	}

	@Override
	public Expr getConditions(Context ctx, Operation op) throws Z3Exception {
		if (op.getName()=="RemoveFile"   )
			return  ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[2], nodeId));
		
		return ctx.mkTrue();
	}

	@Override
	public void putConcurrentOp(Context ctx, Operation op) throws Z3Exception {
		
	}

}
