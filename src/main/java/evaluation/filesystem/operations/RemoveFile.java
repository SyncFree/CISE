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
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.Z3Exception;
import evaluation.filesystem.filesystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RemoveFile implements Operation {

	private String name;
	
	private Expr file;
	private Expr fileId;
	private Expr fname;	
	private Expr fatherId;
	Operation concurrentOp;
	int index;
	
	public RemoveFile(String name, Context ctx) {
		
		this.name=name;
		try {

			Random rand=new Random();
		    index=rand.nextInt((20 - 6) + 1) + 6;

			fatherId=ctx.mkConst("dir" + index, ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));
		    
	   	 	this.fileId=ctx.mkConst("node1" + index + fatherId, ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")));
		    Expr[] nodeArgs =  new Expr[3];
		    nodeArgs[0]=this.fileId;
		    nodeArgs[1]=filesystem.type.getConsts()[1];
		    nodeArgs[2]=ctx.mkConst("name3" + index + fatherId, ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));
		    fname=nodeArgs[2];
		    
	   	 	this.file=filesystem.Node.mkDecl().apply(nodeArgs);
		
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BoolExpr precondition(Context ctx) throws Z3Exception {
		// TODO Auto-generated method stub
		return ctx.mkTrue();
	}

	@Override
	public Expr effect(Context ctx) throws Z3Exception {
		
		Expr[] argParent= new Expr[3];
		argParent[0]=fatherId;
		argParent[1]=fileId;
		argParent[2]=fname;	
		Expr parentTuple=filesystem.Parent.mkDecl().apply(argParent);
		
		if (concurrentOp!=null && concurrentOp.getName()=="UpdateFile" ){
			//return filesystem.Parent_set ;

			filesystem.FSObject_set=ctx.mkITE(ctx.mkEq(concurrentOp.getArgs(this.name)[3], file), filesystem.FSObject_set, ctx.mkSetDel(filesystem.FSObject_set, fileId));
			filesystem.Node_set=ctx.mkITE(ctx.mkEq(concurrentOp.getArgs(this.name)[3], file), filesystem.Node_set, ctx.mkSetDel(filesystem.Node_set, file));
			filesystem.UpdatedFile_set=ctx.mkITE(ctx.mkEq(concurrentOp.getArgs(this.name)[3], file), filesystem.UpdatedFile_set, ctx.mkSetDel(filesystem.UpdatedFile_set, file));
			
			filesystem.Parent_set=ctx.mkITE(ctx.mkEq(concurrentOp.getArgs(this.name)[3], file), filesystem.Parent_set, ctx.mkSetDel(filesystem.Parent_set, parentTuple));
			
		}
		else if (concurrentOp!=null   && ( (concurrentOp.getName()=="AddFile" ) || (concurrentOp.getName()=="MoveFile" ) ))  {
			//return filesystem.Parent_set ;

			filesystem.FSObject_set=ctx.mkITE(ctx.mkEq(concurrentOp.getArgs(this.name)[2], fileId), filesystem.FSObject_set, ctx.mkSetDel(filesystem.FSObject_set, fileId));
			filesystem.Node_set=ctx.mkITE(ctx.mkEq(concurrentOp.getArgs(this.name)[2], fileId), filesystem.Node_set, ctx.mkSetDel(filesystem.Node_set, file));
			filesystem.UpdatedFile_set=ctx.mkITE(ctx.mkEq(concurrentOp.getArgs(this.name)[2], fileId), filesystem.UpdatedFile_set, ctx.mkSetDel(filesystem.UpdatedFile_set, file));
			filesystem.Parent_set=ctx.mkITE(ctx.mkEq(concurrentOp.getArgs(this.name)[2], fileId), filesystem.Parent_set, ctx.mkSetDel(filesystem.Parent_set, parentTuple));
			
		}	
		else  {
			
			filesystem.FSObject_set= ctx.mkSetDel(filesystem.FSObject_set, fileId);
			filesystem.Node_set= ctx.mkSetDel(filesystem.Node_set, file);
			filesystem.UpdatedFile_set= ctx.mkSetDel(filesystem.UpdatedFile_set, file);
			filesystem.Parent_set= ctx.mkSetDel(filesystem.Parent_set, parentTuple);
		}

	    return filesystem.Parent_set  ;
	}

	@Override
	public void putReplicaIndex(Context ctx, int r) throws Z3Exception {

	}

	@Override
	public Expr[] getArgs(String name) {
		Expr args [] ={fname,fatherId, fileId};
		return args;
	}

	@Override
	public BoolExpr getCondition(Context ctx, Operation op) throws Z3Exception {
		
		concurrentOp=op;		
		return ctx.mkTrue();
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
		
		if (op.getName()=="MoveDirectory"  )
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[3], fileId));
		
		else if (op.getName()=="AddDirectory"  )
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[2], fileId));

		return ctx.mkTrue();

	}
	@Override
	public void putConcurrentOp(Context ctx, Operation op) throws Z3Exception {
		this.concurrentOp=op;
		
	}

}
