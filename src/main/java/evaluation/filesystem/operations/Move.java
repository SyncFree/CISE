
package evaluation.filesystem.operations;

import application.Operation;
import com.microsoft.z3.*;
import evaluation.filesystem.filesystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Move implements Operation  {

	private String name;
	
	private Expr dir;
	private Expr  id;
	private Expr  dirId1;
	private Expr dir1;
	private Expr  dirId2;
	private Expr dir2;
	
	private Expr dname;
	
	
	public Move(String name, Context ctx) {
		
		this.name=name;
		try {
   	 	
			dirId1=ctx.mkConst("dir5", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));
			
		    Expr[] nodeArgs1 =  new Expr[3];
		    nodeArgs1[0]=dirId1;
		    nodeArgs1[1]=filesystem.type.getConsts()[0];
		    nodeArgs1[2]=ctx.mkConst("name5", ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));
	   	 
	   	 	//this.dir1=filesystem.Node.mkDecl().apply(nodeArgs1);
	   	 	
	   	 	dirId2=ctx.mkConst("dir3", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));
	   	 	
		    Expr[] nodeArgs2 =  new Expr[3];
		    nodeArgs2[0]=dirId2;
		    nodeArgs2[1]=filesystem.type.getConsts()[0];
		    nodeArgs2[2]=ctx.mkConst("name3", ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));
	   	 
	   	 	//this.dir2=filesystem.Node.mkDecl().apply(nodeArgs2);
	   	 	
	   	 	
	   	 	id=ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));
	   	 	
	 	 	Random bi=new Random();
	   	 	int i=bi.nextInt();
	   	 	
		    Expr[] nodeArgs =  new Expr[3];
		    nodeArgs[0]=id;
		    nodeArgs[1]=filesystem.type.getConsts()[0];
		    nodeArgs[2]=ctx.mkConst("name1"+i, ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));
		    
		    dname=nodeArgs[2];
	   	 
	   	 	//this.dir=filesystem.Node.mkDecl().apply(nodeArgs);
		
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

		
		Expr[] finalArgs =  new Expr[2];
		finalArgs[0]=filesystem.root;
		finalArgs[1]=dirId2;
   		
		Expr tbody= ctx.mkApp(filesystem.exists, finalArgs)  ;
  	 
	     Expr[] arg= new Expr[3];
	     arg[0]=dirId2;
	     arg[1]=filesystem.root;
	     arg[2]=tbody;	
		 Expr reachTuple=filesystem.Reachability.mkDecl().apply(arg);
		 Expr reachable= ctx.mkSetMembership(reachTuple,filesystem.Reachable_set);
		 
		 
		 Expr node1=ctx.mkConst("node2", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")) );
		 
 		 Expr[] fatherindex =  new Expr[2];
    	 fatherindex[0]=dirId2;
		 fatherindex[1]=node1;
		 
	     Expr parentTuple1=filesystem.Parent.mkDecl().apply(fatherindex);
	     
	 	 Expr father1=(BoolExpr) ctx.mkSetMembership(parentTuple1, filesystem.Parent_set);
	 	 
		 Sort[] nodes = new Sort[1];
		 nodes[0] =ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject"));

		 Symbol[] namess = new Symbol[1];
		 namess[0] =  ctx.mkSymbol("node2");
  	 	
		 Expr[] nodeArgs1 =  new Expr[3];
		 nodeArgs1[0]=node1;
		 nodeArgs1[1]=ctx.mkConst("type", filesystem.type);
		 nodeArgs1[2]=ctx.mkConst("name2", ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));
		 
		 Expr eq=ctx.mkEq(dname,  nodeArgs1[2]);
	   	 
	   	 Expr node=filesystem.Node.mkDecl().apply(nodeArgs1);
	     
	     Expr body=ctx.mkAnd(new BoolExpr [] {   (BoolExpr) ctx.mkSetMembership(node, filesystem.Node_set), (BoolExpr)father1 , (BoolExpr)  eq});
                
         BoolExpr mustUniqueee = ctx.mkNot(ctx.mkExists(nodes, namess, body, 1, null, null,
         null, null));
         
 		
 		
 		Expr[] finalArgs1 =  new Expr[2];
 		finalArgs1[0]=id;
 		finalArgs1[1]=dirId2; ;
    		
 		Expr tbody1= (BoolExpr) ctx.mkApp(filesystem.exists, finalArgs1)  ;
 		
 	     Expr[] arg1= new Expr[3];
 	     arg1[0]=dirId2; 
 	     arg1[1]=id;
 	     arg1[2]=tbody1;	
 		 Expr parentTuple3=filesystem.Reachability.mkDecl().apply(arg1);
 		 Expr notAncestor=ctx.mkSetMembership(parentTuple3, filesystem.Reachable_set);
         
         
         Expr precondition=ctx.mkAnd(new BoolExpr [] {(BoolExpr) mustUniqueee,(BoolExpr) reachable,
        		 ctx.mkNot(ctx.mkEq(filesystem.root, id)), ctx.mkNot((BoolExpr) notAncestor)});
		 
		 return (BoolExpr) precondition;
	}

	@Override
	public Expr effect(Context ctx) throws Z3Exception {
		
	    Expr[] argParent= new Expr[2];
	    argParent[0]=dirId1;
	    argParent[1]=ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")));
		Expr parentTuple=filesystem.Parent.mkDecl().apply(argParent);
		filesystem.Parent_set= ctx.mkSetDel(filesystem.Parent_set,parentTuple);
		
	    Expr[] argParent2= new Expr[2];
	    argParent2[0]=dirId2;
	    argParent2[1]=ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")));
		Expr parentTuple2=filesystem.Parent.mkDecl().apply(argParent2);
		filesystem.Parent_set= ctx.mkSetAdd(filesystem.Parent_set,parentTuple2);
		
		
		
		Expr[] finalArgs =  new Expr[2];
		finalArgs[0]=dirId2;
		finalArgs[1]=id;
   		
		Expr tbody= (BoolExpr) ctx.mkApp(filesystem.exists, finalArgs)  ;
		
	     Expr[] arg= new Expr[3];
	     arg[0]=id;
	     arg[1]=dirId2;
	     arg[2]=tbody;	
		 Expr parentTuple3=filesystem.Reachability.mkDecl().apply(arg);
		 filesystem.Reachable_set= ctx.mkSetAdd(filesystem.Reachable_set,parentTuple3);
		 
		
		return filesystem.Reachable_set;
	}

	@Override
	public void putReplicaIndex(Context ctx, int r) throws Z3Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Expr[] getArgs(String name) {
		Expr [] args={dname, dirId2, id};
		return args;
	}

	@Override
	public BoolExpr getCondition(Context ctx, Operation op) throws Z3Exception {

		
		if (op.getName()=="AddFile") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.dname));
		}
		else if (op.getName()=="MoveFile") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.dname));
		}
		
		else if (op.getName()=="AddDirectory") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.dname));
			//Expr [] expr={op.getArgs(this.name)[1], this.dirId2};
			///return ctx.MkDistinct(expr);
		}
		
		else if (op.getName()=="RemoveDirectory") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.dirId2));
		}
		
		else if (op.getName()=="MoveDirectory") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.dname));
			
		//	return ctx.mkAnd(new BoolExpr [] {ctx.mkEq(op.getArgs(this.name)[1], dirId2), ctx.mkEq(op.getArgs(this.name)[2], id)});
			//return ctx.mkTrue();
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putConcurrentOp(Context ctx, Operation op) throws Z3Exception {
		// TODO Auto-generated method stub
		
	}
	
	

}
