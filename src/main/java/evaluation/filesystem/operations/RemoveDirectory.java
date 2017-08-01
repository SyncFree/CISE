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

public class RemoveDirectory implements Operation  {
	
	private String name;
	private Expr dir1;
	
 	private Expr dir;
	private Expr fatherId;
	private Expr dirId;
	private Expr dirNode;

	Operation concurrentOp;
	int index;

	public RemoveDirectory(String name, Context ctx) {
		
		this.name=name;
		try {
			
			Random rand=new Random();
		    index=rand.nextInt((20 - 6) + 1) + 6;
		    
	   	 	this.fatherId=ctx.mkConst("dir"+index, ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));
		    Expr[] nodeArgs1 =  new Expr[3];
		    nodeArgs1[0]=ctx.mkConst("dir"+index, ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")));
		    nodeArgs1[1]=filesystem.type.getConsts()[0];
		    nodeArgs1[2]=ctx.mkConst("name1"+index, ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));
		    
	   		dir1=filesystem.Node.mkDecl().apply(nodeArgs1);
		     
			dirId=ctx.mkConst("dir3"+index+fatherId, ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));
		    Expr[] nodeArgs =  new Expr[3];
		    nodeArgs[0]=ctx.mkConst("node3"+index+fatherId, ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")));;
		    nodeArgs[1]=filesystem.type.getConsts()[0];
		    nodeArgs[2]=ctx.mkConst("name3"+index+fatherId, ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));
		    
	   	 	this.dir=filesystem.Node.mkDecl().apply(nodeArgs);
	   		 dirNode=ctx.mkConst("node3"+index+fatherId, ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")));
		
		} catch (Z3Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getReplicaIndex() {
		return 0;
	}

	@Override
	public BoolExpr precondition(Context ctx) throws Z3Exception {
	
	    Expr[] nodeArgs1 =  new Expr[3];
	    nodeArgs1[0]=ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")));
	    nodeArgs1[1]=ctx.mkConst("type", filesystem.type);
	    nodeArgs1[2]=ctx.mkConst("name1", ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));
   	 
   	 	Expr child=filesystem.Node.mkDecl().apply(nodeArgs1);

	    Expr[] argParent= new Expr[3];
	    argParent[0]=dirId;
	    argParent[1]= nodeArgs1[0];	
	    argParent[2]=  nodeArgs1[2];
		Expr parentTuple=filesystem.Parent.mkDecl().apply(argParent);
		
		Expr empty=ctx.mkAnd(new BoolExpr []{(BoolExpr) ctx.mkSetMembership(nodeArgs1[0], filesystem.FSObject_set), (BoolExpr) ctx.mkSetMembership(child, filesystem.Node_set),
				 (BoolExpr)ctx.mkSetMembership(parentTuple,filesystem.Parent_set )});
		
		Sort[] nodearg = new Sort[1];
		nodearg[0]=ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject"));
		Symbol[] namesp = new Symbol[1];
		namesp[0] =  ctx.mkSymbol("node1");
		
		Expr isEmpty=ctx.mkNot(ctx.mkExists(nodearg, namesp, (BoolExpr)ctx.mkSetMembership(parentTuple,filesystem.Parent_set ), 1, null, null,null, null));

		Expr precondition=ctx.mkAnd(new BoolExpr []{(BoolExpr) isEmpty, ctx.mkNot(ctx.mkEq(filesystem.root, dirId)) })	;

  	    return (BoolExpr) precondition;
	}

	@Override
	public Expr effect(Context ctx) throws Z3Exception {
		
		Expr[] argParent= new Expr[3];
		argParent[0]=fatherId;
		argParent[1]=dirNode;
		argParent[2]=ctx.mkConst("name3"+index+fatherId, ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));
		Expr parentTuple=filesystem.Parent.mkDecl().apply(argParent);
		
		Expr[] finalArgs =  new Expr[2];
		finalArgs[0]=filesystem.root ;
		finalArgs[1]=dirId;
		
		Expr tbody= (BoolExpr) ctx.mkApp(filesystem.exists, finalArgs)  ;
	 
		Expr[] arg= new Expr[3];
		arg[0]=dirId;
		arg[1]=filesystem.root;
		arg[2]=tbody;	
		Expr parentTuple3=filesystem.Reachability.mkDecl().apply(arg);
		
		
		System.out.println("concurrent op:");
		
		if (concurrentOp!=null && (concurrentOp.getName()=="AddDirectory" || concurrentOp.getName()=="MoveDirectory" ) ) {

			filesystem.UpdatedFile_set=ctx.mkITE(ctx.mkOr(new BoolExpr[]{ctx.mkEq(concurrentOp.getArgs(this.name)[1], dirId), ctx.mkEq(concurrentOp.getArgs(this.name)[3], dirNode)}),
					filesystem.UpdatedFile_set, ctx.mkSetDel(filesystem.UpdatedFile_set, dir));
			
			filesystem.Node_set=ctx.mkITE(ctx.mkOr(new BoolExpr[]{ctx.mkEq(concurrentOp.getArgs(this.name)[1], dirId), ctx.mkEq(concurrentOp.getArgs(this.name)[3], dirNode)}),
					filesystem.Node_set, ctx.mkSetDel(filesystem.Node_set, dir));
			
			filesystem.Dir_set=ctx.mkITE(ctx.mkOr(new BoolExpr[]{ctx.mkEq(concurrentOp.getArgs(this.name)[1], dirId), ctx.mkEq(concurrentOp.getArgs(this.name)[2], dirId)})
					, filesystem.Dir_set, ctx.mkSetDel(filesystem.Dir_set, dirId));
			
			filesystem.FSObject_set=ctx.mkITE(ctx.mkOr(new BoolExpr[]{ctx.mkEq(concurrentOp.getArgs(this.name)[1], dirId), ctx.mkEq(concurrentOp.getArgs(this.name)[3], dirNode)}),

					filesystem.FSObject_set, ctx.mkSetDel(filesystem.FSObject_set, ctx.mkConst("node3" + index + fatherId, ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")))));
			
			filesystem.Parent_set=ctx.mkITE(ctx.mkOr(new BoolExpr[]{ctx.mkEq(concurrentOp.getArgs(this.name)[1], dirId), ctx.mkEq(concurrentOp.getArgs(this.name)[3], dirNode)}),
					filesystem.Parent_set, ctx.mkSetDel(filesystem.Parent_set, parentTuple));
			
			filesystem.Reachable_set=ctx.mkITE(ctx.mkOr(new BoolExpr[]{ctx.mkEq(concurrentOp.getArgs(this.name)[1], dirId), ctx.mkEq(concurrentOp.getArgs(this.name)[2], dirId)}),
					filesystem.Reachable_set, ctx.mkSetDel(filesystem.Reachable_set, parentTuple3));
		}
		
		else if (concurrentOp!=null &&  ( concurrentOp.getName()=="AddFile" || concurrentOp.getName()=="MoveFile" )) {

			filesystem.UpdatedFile_set=ctx.mkITE(ctx.mkEq(concurrentOp.getArgs(this.name)[1], dirId), filesystem.UpdatedFile_set, ctx.mkSetDel(filesystem.UpdatedFile_set, dir));
			filesystem.Node_set=ctx.mkITE(ctx.mkEq(concurrentOp.getArgs(this.name)[1], dirId), filesystem.Node_set, ctx.mkSetDel(filesystem.Node_set, dir));
			filesystem.Dir_set=ctx.mkITE(ctx.mkEq(concurrentOp.getArgs(this.name)[1], dirId), filesystem.Dir_set, ctx.mkSetDel(filesystem.Dir_set, dirId));
			
			filesystem.FSObject_set=ctx.mkITE(ctx.mkEq(concurrentOp.getArgs(this.name)[1], dirId), filesystem.FSObject_set,
					ctx.mkSetDel(filesystem.FSObject_set, ctx.mkConst("node3" + index + fatherId, ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")))));
			
			filesystem.Parent_set=ctx.mkITE(ctx.mkEq(concurrentOp.getArgs(this.name)[1], dirId), filesystem.Parent_set, ctx.mkSetDel(filesystem.Parent_set, parentTuple));
			filesystem.Reachable_set=ctx.mkITE(ctx.mkEq(concurrentOp.getArgs(this.name)[1], dirId), filesystem.Reachable_set, ctx.mkSetDel(filesystem.Reachable_set, parentTuple3));
		}
		
		else {
			
			filesystem.UpdatedFile_set= ctx.mkSetDel(filesystem.UpdatedFile_set,dir);
			filesystem.Node_set= ctx.mkSetDel(filesystem.Node_set,dir);
			filesystem.Dir_set= ctx.mkSetDel(filesystem.Dir_set,dirId);
			filesystem.FSObject_set= ctx.mkSetDel(filesystem.FSObject_set,ctx.mkConst("node3"+index+fatherId, ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject"))));

			filesystem.Parent_set= ctx.mkSetDel(filesystem.Parent_set,parentTuple);
			filesystem.Reachable_set= ctx.mkSetDel(filesystem.Reachable_set,parentTuple3);
 		
		} 
	    return   filesystem.Reachable_set ;
	}

	@Override
	public void putReplicaIndex(Context ctx, int r) throws Z3Exception {
		
	}

	@Override
	public Expr[] getArgs(String name) {
		Expr [] args={dirId,dir, dirNode};
		return args;
	}

	@Override
	public BoolExpr getCondition(Context ctx, Operation op) throws Z3Exception {

		if (op.getName()=="AddFile") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[1], this.dirId));
		}
		else if (op.getName()=="MoveFile") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[1], this.dirId));
		}
		
		else if (op.getName()=="AddDirectory") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[1], this.dirId));
		}
		
		else if (op.getName()=="MoveDirectory") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[1], this.dirId));
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

		if ( op.getName()=="AddFile" || op.getName()=="MoveFile"  )
			return  ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[2], dirNode));
		
		return ctx.mkTrue();
	}
	@Override
	public void putConcurrentOp(Context ctx, Operation op) throws Z3Exception {
		this.concurrentOp=op;
		
	}

}
