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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MoveDirectoryCRDT implements Operation  {

	private String name;
	
	private Expr dir;
	private Expr  id;
	private Expr  father1Id;
	private Expr  father2Id;	
	private Expr nodeId;
	private Expr dname;
	int index1;
	int index2;
	int index;
	
	
	public MoveDirectoryCRDT(String name, Context ctx) {
		
		this.name=name;
		try {

			Random rand=new Random();
		    index1=rand.nextInt((6 - 0) + 0);
		    
			father1Id=ctx.mkConst("dir_s1", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));
		    Expr[] nodeArgs1 =  new Expr[3];
		    nodeArgs1[0]=father1Id;
		    nodeArgs1[1]=filesystemcrdt.type.getConsts()[0];
		    nodeArgs1[2]=ctx.mkConst("name_s1", ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));
	   	 	
		    index2=rand.nextInt((6 - 0) + 0) + 0;

	   	 	father2Id=ctx.mkConst("dir_d1", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));
	   	 	
		    Expr[] nodeArgs2 =  new Expr[3];
		    nodeArgs2[0]=father2Id;
		    nodeArgs2[1]=filesystemcrdt.type.getConsts()[0];
		    nodeArgs2[2]=ctx.mkConst("name_d1", ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));
	   	 
		    index=rand.nextInt((10 - 8) + 0) + 0;;
		    
	   	 	id=ctx.mkConst("dir1", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));
	    	dname=ctx.mkConst("name1", ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));;
	    	nodeId=ctx.mkConst("dir1", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")));
	   	   	 	
		    Expr[] nodeArgs =  new Expr[3];
		    nodeArgs[0]=ctx.mkConst("dir1", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")));;
		    nodeArgs[1]=filesystemcrdt.type.getConsts()[0];
		    nodeArgs[2]=dname;	    
	   	 	this.dir=filesystemcrdt.Node.mkDecl().apply(nodeArgs);
		
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
		
		Expr[] finalArgs =  new Expr[2];
		finalArgs[0]=filesystemcrdt.root;
		finalArgs[1]=father2Id;
		Expr tbody= ctx.mkApp(filesystemcrdt.exists, finalArgs)  ;
  	 
	    Expr[] arg= new Expr[3];
	    arg[0]=father2Id;
	    arg[1]=filesystemcrdt.root;
	    arg[2]=tbody;	
		Expr reachTuple=filesystemcrdt.Reachability.mkDecl().apply(arg);
		Expr reachable= ctx.mkSetMembership(reachTuple,filesystemcrdt.Reachable_set);

		Expr node=ctx.mkConst("node2", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")) );
		Expr name=ctx.mkConst("name2", ctx.mkUninterpretedSort(ctx.mkSymbol("Name")) );
		 
 		Expr[] fatherindex =  new Expr[3];
    	fatherindex[0]=father2Id;
		fatherindex[1]=node;
		fatherindex[2]=name;
		 
	    Expr parentTuple1=filesystemcrdt.Parent.mkDecl().apply(fatherindex);
	     
	 	Expr fatherRealtion=(BoolExpr) ctx.mkSetMembership(parentTuple1, filesystemcrdt.Parent_set);
	 	 
		Sort[] nodes = new Sort[1];
		nodes[0] =ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject"));

		Symbol[] namess = new Symbol[1];
		namess[0] =  ctx.mkSymbol("node2");

		 BoolExpr mustUniqueee= ctx.mkNot(ctx.mkExists(nodes, namess, ctx.mkAnd(new BoolExpr []{(BoolExpr) fatherRealtion,ctx.mkEq(name,dname)}), 1, null, null, null,null));

 		 Expr[] finalArgs1 =  new Expr[2];
 		 finalArgs1[0]=id;
 		 finalArgs1[1]=father2Id; ;
    	 	
 		 Expr tbody1= (BoolExpr) ctx.mkApp(filesystemcrdt.exists, finalArgs1)  ;
 		
 	     Expr[] arg1= new Expr[3];
 	     arg1[0]=father2Id; 
 	     arg1[1]=id;
 	     arg1[2]=tbody1;	
 		 Expr reachableTuple=filesystemcrdt.Reachability.mkDecl().apply(arg1);
 		 Expr notAncestor=ctx.mkSetMembership(reachableTuple, filesystemcrdt.Reachable_set);
         
       //  Expr dir1=ctx.mkConst("dir3", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));
      //   Expr node1=ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")) );
       //  Expr name1=ctx.mkConst("name1", ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));

         Expr precondition=ctx.mkAnd(new BoolExpr [] {(BoolExpr) reachable,
        		 ctx.mkNot(ctx.mkEq(filesystemcrdt.root, id)), ctx.mkNot((BoolExpr) notAncestor), /* (BoolExpr)
        		/* ctx.mkSetMembership(dir, filesystemcrdt.Node_set), */}); /*
        		  ctx.mkEq(node1, ctx.mkConst("node1"+index, ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject"))))*/
				 /*ctx.mkEq(dname, name1), ctx.mkEq(dir1, father2Id) */
		 
		 return (BoolExpr)  precondition ;
	}

	@Override
	public Expr effect(Context ctx) throws Z3Exception {
		
	    Expr[] argParent= new Expr[3];
	    argParent[0]=father1Id;
	    argParent[1]=ctx.mkConst("dir1", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")));
	    argParent[2]=dname;
		Expr parentTuple=filesystemcrdt.Parent.mkDecl().apply(argParent);
		filesystemcrdt.Parent_set= ctx.mkSetDel(filesystemcrdt.Parent_set,parentTuple);
		
	    Expr[] argParent2= new Expr[3];
	    argParent2[0]=father2Id;
	    argParent2[1]=ctx.mkConst("dir1", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")));
	    argParent2[2]=dname;	
		Expr parentTuple2=filesystemcrdt.Parent.mkDecl().apply(argParent2);
		filesystemcrdt.Parent_set= ctx.mkSetAdd(filesystemcrdt.Parent_set,parentTuple2);
		
		Expr[] finalArgs =  new Expr[2];
		finalArgs[0]=father2Id;
		finalArgs[1]=id;
   		
		 Expr tbody= (BoolExpr) ctx.mkApp(filesystemcrdt.exists, finalArgs)  ;
		
	     Expr[] arg= new Expr[3];
	     arg[0]=id;
	     arg[1]=father2Id;
	     arg[2]=tbody;	
		 Expr parentTuple3=filesystemcrdt.Reachability.mkDecl().apply(arg);
		 filesystemcrdt.Reachable_set= ctx.mkSetAdd(filesystemcrdt.Reachable_set,parentTuple3);
	     filesystemcrdt.UpdatedFile_set= ctx.mkSetAdd(filesystemcrdt.UpdatedFile_set,dir);

		return filesystemcrdt.Parent_set;
	}

	@Override
	public void putReplicaIndex(Context ctx, int r) throws Z3Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Expr[] getArgs(String name) {
		Expr [] args={dname, father2Id, id, nodeId};
		return args;
	}

	@Override
	public BoolExpr getCondition(Context ctx, Operation op) throws Z3Exception {


	   if (op.getName()=="MoveDirectory") {			
		    
		   	Expr node=ctx.mkConst("Dir", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));
		   
	 		 Expr[] finalArgs1 =  new Expr[2];
	 		 finalArgs1[0]=op.getArgs(this.name)[2];;
	 		 finalArgs1[1]=father2Id; ;
	    	 	
	 		 Expr tbody1= (BoolExpr) ctx.mkApp(filesystemcrdt.exists, finalArgs1)  ;
		   
	 	     Expr[] arg1= new Expr[3];
	 	     arg1[0]=father2Id;
	 	     arg1[1]=op.getArgs(this.name)[2];
	 	     arg1[2]=tbody1;	
	 		 Expr reachableTuple=filesystemcrdt.Reachability.mkDecl().apply(arg1);
	 		 Expr ancestors=ctx.mkSetMembership(reachableTuple, filesystemcrdt.Reachable_set);
	 		 
	 		 Expr noAncestro=ctx.mkAnd(new BoolExpr [] {(BoolExpr) ancestors, ctx.mkEq(node, father2Id)});
	 		 
	 		 
	 		 Expr node2=ctx.mkConst("node2", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));
			   
		 	 Expr[] finalArgs12 =  new Expr[2];
		     finalArgs12[0]=id;
		 	 finalArgs12[1]= op.getArgs(this.name)[1] ;
		    	 	
		 	 Expr tbody12= (BoolExpr) ctx.mkApp(filesystemcrdt.exists, finalArgs12)  ;
			   
		 	 Expr[] arg12= new Expr[3];
		 	 arg12[0]=op.getArgs(this.name)[1];
		 	 arg12[1]=id;
		 	 arg12[2]=tbody12;	
		     Expr reachableTuple2=filesystemcrdt.Reachability.mkDecl().apply(arg12);
		     Expr ancestors2=ctx.mkSetMembership(reachableTuple2, filesystemcrdt.Reachable_set);
		 		 
		     Expr noAncestro2=ctx.mkAnd(new BoolExpr [] {(BoolExpr) ancestors2, ctx.mkEq(node2, op.getArgs(this.name)[1])});
		 		 
	 		 Expr con=ctx.mkAnd(new BoolExpr [] {(BoolExpr) ancestors , (BoolExpr) ancestors2 });
	 		 Expr con2=ctx.mkAnd(new BoolExpr [] {(BoolExpr) ctx.mkEq(op.getArgs(this.name)[2], father2Id) , (BoolExpr) ctx.mkEq(id, op.getArgs(this.name)[1]) });
	 		 
	 		 Expr c=ctx.mkNot(ctx.mkOr( new BoolExpr [] {(BoolExpr) con , (BoolExpr) con2 }));
		   
		 //   return ctx.mkAnd( new BoolExpr [] {ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], dname)), (BoolExpr) c});

		    return (BoolExpr) c;
		}

		else return ctx.mkTrue();
	}

	@Override
	public List<Operation> concurrentOps(Context ctx) {
		
		List<Operation> op=new ArrayList<Operation>();

		/*Operation o1=new AddFileCRDT("AddFile",ctx);
		Operation o2=new RemoveFileCRDT("RemoveFile",ctx);
		Operation o3=new UpdateFileCRDT("UpdateFile",ctx);
		Operation o4=new MoveFileCRDT("MoveFile",ctx);
		Operation o5=new AddDirectoryCRDT("AddDirectory",ctx);
		Operation o6=new RemoveDirectoryCRDT("RemoveDirectory",ctx);*/
		Operation o7=new MoveDirectoryCRDT2("MoveDirectory", ctx);
		
		/*op.add(o1);
		op.add(o2);
		op.add(o3);
		op.add(o4);
		op.add(o5);
		op.add(o6);		*/
		op.add(o7);	
		
		return op;
	}

	@Override
	public Expr getConditions(Context ctx, Operation op) throws Z3Exception {
		
		if (op.getName()=="RemoveFile" )
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[2], nodeId));

		return ctx.mkTrue();
	}

	@Override
	public void putConcurrentOp(Context ctx, Operation op) throws Z3Exception {
		
	}

}
