
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

package evaluation.filesystem.operations;

import application.Operation;
import com.microsoft.z3.*;
import evaluation.filesystem.filesystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddFileCRDT implements Operation  {

	private String name;

	private Expr file;
	private Expr dirId;
	private Expr fileId;
	private Expr fname;
	int index;
	
	
	public AddFileCRDT(String name, Context ctx) {
		
		this.name=name;
		try {

		 	Random rand=new Random();
		 	index = rand.nextInt((20 - 6) + 1) + 6;
		 	
			dirId=ctx.mkConst(ctx.mkSymbol("dir3"+index), ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));
	   	 	fileId=ctx.mkConst("node1"+index, ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")));
	   	 	fname=ctx.mkConst("name1"+dirId, ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));
		    Expr[] nodeArgs1 =  new Expr[3];
		    nodeArgs1[0]=fileId;
		    nodeArgs1[1]=filesystem.type.getConsts()[1];
		    nodeArgs1[2]=fname;
	   	 	this.file=filesystem.Node.mkDecl().apply(nodeArgs1);

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
    	 finalArgs[0]=filesystem.root;
    	 finalArgs[1]=dirId; 		
    	 Expr tbody= ctx.mkApp(filesystem.exists, finalArgs)  ;
   	 
	     Expr[] arg= new Expr[3];
	     arg[0]=dirId;
	     arg[1]=filesystem.root;	
	     arg[2]=tbody;
		 Expr reachTuple=filesystem.Reachability.mkDecl().apply(arg);
		 Expr reachable= ctx.mkSetMembership(reachTuple,filesystem.Reachable_set);
		 
		 Expr node1=ctx.mkConst("node2", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")) );
		 Expr dir=ctx.mkConst("dir3", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")) );;
		 
 		 Expr[] fatherindex =  new Expr[3];
    	 fatherindex[0]=dirId;
		 fatherindex[1]=node1;
		 fatherindex[2]=ctx.mkConst("name2", ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));
		 
	     Expr parentTuple=filesystem.Parent.mkDecl().apply(fatherindex);
	 	 Expr fatherRelation=(BoolExpr) ctx.mkSetMembership(parentTuple, filesystem.Parent_set);
  	 	
		 Expr[] nodeArgs1 =  new Expr[3];
		 nodeArgs1[0]=node1;
		 nodeArgs1[1]=ctx.mkConst("type2", filesystem.type);
		 nodeArgs1[2]=ctx.mkConst("name2", ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));

		 Expr node=ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")) );

		 Sort[] argNodes = new Sort[1];
		 argNodes[0] =ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject"));

		 Symbol[] namesArgs = new Symbol[1];
		 namesArgs[0] =  ctx.mkSymbol("node2");

	 	 BoolExpr mustUnique= ctx.mkNot(ctx.mkExists(argNodes, namesArgs, ctx.mkAnd(new BoolExpr []{(BoolExpr) fatherRelation,ctx.mkEq(nodeArgs1[2],fname)}), 1, null, null, null,null));

		 Expr name=ctx.mkConst("name1", ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));;

	 	 Expr precondition=ctx.mkAnd(new BoolExpr [] {(BoolExpr) reachable, ctx.mkEq(node, fileId),  ctx.mkEq(dir, dirId), ctx.mkEq(fname, name)});

		 return (BoolExpr) precondition;
	}

	@Override
	public Expr effect(Context ctx) throws Z3Exception {
		
		filesystem.FSObject_set= ctx.mkSetAdd(filesystem.FSObject_set,fileId);
			
		filesystem.Node_set= ctx.mkSetAdd(filesystem.Node_set,file);
		
	    Expr[] argParent= new Expr[3];
	    argParent[0]=dirId;
	    argParent[1]=fileId;
	    argParent[2]=fname;	
		Expr parentTuple=filesystem.Parent.mkDecl().apply(argParent);
		filesystem.Parent_set= ctx.mkSetAdd(filesystem.Parent_set,parentTuple);
	    return   filesystem.Parent_set;
	}

	@Override
	public void putReplicaIndex(Context ctx, int r) throws Z3Exception {

	}

	@Override
	public Expr[] getArgs(String name) {
		Expr [] args={fname, dirId, fileId};
		return args;
	}

	@Override
	public BoolExpr getCondition(Context ctx, Operation op) throws Z3Exception {
		
		if (op.getName()=="AddFile") {
        	return ctx.mkOr(new BoolExpr[]{ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], fname)),
					ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[1], ctx.mkConst("dir3", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")))))});
		}
		else if (op.getName()=="MoveFile") {
			return ctx.mkOr(new BoolExpr[]{ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], fname)),
					ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[1], dirId))});
		}		
		else if (op.getName()=="AddDirectory") {
			return ctx.mkOr(new BoolExpr[]{ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], fname)), ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[1], dirId))});
			
		}
		
		else if (op.getName()=="RemoveDirectory") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.dirId));
		}
		else if (op.getName()=="MoveDirectory") {
			return ctx.mkOr(new BoolExpr[]{ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], fname)), ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[1], dirId))});
		}

		else return ctx.mkTrue();
	}

	@Override
	public List<Operation> concurrentOps(Context ctx) {
		
		List<Operation> op=new ArrayList<Operation>();

		Operation o1=new AddFile("AddFile",ctx);
		Operation o2=new RemoveFile("RemoveFile",ctx);
		Operation o3=new UpdateFile("UpdateFile",ctx);
		Operation o4=new MoveFile("MoveFile",ctx);
		Operation o5=new AddDirectory("AddDirectory",ctx);
		Operation o6=new RemoveDirectory("RemoveDirectory",ctx);
		Operation o7=new MoveDirectory("MoveDirectory", ctx);
		
		op.add(o1);
		op.add(o2);
		op.add(o3);
		op.add(o4);
		op.add(o5);
		op.add(o6);		
		op.add(o7);	
		
		return op;
	}

	@Override
	public Expr getConditions(Context ctx, Operation op) throws Z3Exception {
		
		if (op.getName()=="MoveDirectory" || op.getName()=="RemoveFile" || op.getName()=="AddFile" || op.getName()=="MoveFile"  )
			return ctx.mkTrue();
		
		Expr args = ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[2], fileId));
		return args;
	}

	@Override
	public void putConcurrentOp(Context ctx, Operation op) throws Z3Exception {
		
	}

}
