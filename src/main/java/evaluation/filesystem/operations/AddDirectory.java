package evaluation.filesystem.operations;

import application.Operation;
import com.microsoft.z3.*;
import evaluation.filesystem.filesystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddDirectory implements Operation {
	
	private String name;

	private Expr  fatherId;
	private Expr  dirId;
	private Expr  nodeId;
	private Expr dir;
	private Expr dname;
    int index;
	
	public AddDirectory(String name, Context ctx) {
		
		this.name=name;
		try {
   	 	
			Random rand=new Random();     
		    index = rand.nextInt((20 - 6) + 1) + 6;
		    
			this.dname=ctx.mkConst("name1" + index, ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));
			this.fatherId=ctx.mkConst("dir" + index, ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));
	   	 	this.nodeId=ctx.mkConst("node1" + index, ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")));
	   	 	this.dirId=ctx.mkConst("dir1" + index, ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));
	   	 	
		    Expr[] nodeArgs =  new Expr[3];
		    nodeArgs[0]=nodeId;
		    nodeArgs[1]=ctx.mkConst("type", filesystem.type);
		    nodeArgs[2]=ctx.mkConst("name1" + index, ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));
	   	 	this.dir=filesystem.Node.mkDecl().apply(nodeArgs);
		
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
     	 finalArgs[1]=fatherId;
     		
     	 Expr tbody= ctx.mkApp(filesystem.exists, finalArgs)  ;
    	 
 	     Expr[] arg= new Expr[3];
 	     arg[0]=fatherId;
 	     arg[1]=filesystem.root;	
 	     arg[2]=tbody;	
 		 Expr reachTuple=filesystem.Reachability.mkDecl().apply(arg);
 		 Expr reachable= ctx.mkSetMembership(reachTuple, filesystem.Reachable_set);
		 
		 Sort[] nodes = new Sort[1];
		 nodes[0] =ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject"));

		 Symbol[] namess = new Symbol[1];
		 namess[0] =  ctx.mkSymbol("node2");
		 
		 Expr nodeId=ctx.mkConst("node2", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")));
		 
 		 Expr[] fatherindex =  new Expr[3];
    	 fatherindex[0]=fatherId;
		 fatherindex[1]=nodeId;
		 fatherindex[2]=ctx.mkConst("name2", ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));;
		 
	     Expr parentTuple=filesystem.Parent.mkDecl().apply(fatherindex);
	     
	 	 Expr fatherRelation=(BoolExpr) ctx.mkSetMembership(parentTuple, filesystem.Parent_set);
 	 	
		 Expr[] nodeArgs1 =  new Expr[3];
		 nodeArgs1[0]=nodeId;
		 nodeArgs1[1]=ctx.mkConst("type", filesystem.type);
		 nodeArgs1[2]=ctx.mkConst("name2", ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));

	 	 BoolExpr mustUniqueee= ctx.mkNot(ctx.mkExists(nodes, namess, ctx.mkAnd(new BoolExpr[]{(BoolExpr) fatherRelation, ctx.mkEq(nodeArgs1[2], dname)}), 1, null, null, null, null));
		 
	     Expr dir=ctx.mkConst("dir3", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));
	     Expr node1=ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")));
	     Expr name1=ctx.mkConst("name1", ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));
    	 
    	 Expr precondition=ctx.mkAnd(new BoolExpr[]{mustUniqueee, (BoolExpr) reachable, ctx.mkEq(node1, nodeId), ctx.mkEq(dname, name1), ctx.mkEq(dir, fatherId)});
    	    		  
		 return (BoolExpr) precondition;
	}

	@Override
	public Expr effect(Context ctx) throws Z3Exception {
		
		filesystem.Node_set= ctx.mkSetAdd(filesystem.Node_set, dir);
		filesystem.FSObject_set= ctx.mkSetAdd(filesystem.FSObject_set, nodeId);
		filesystem.Dir_set= ctx.mkSetAdd(filesystem.Dir_set, ctx.mkConst("dir1" + index, ctx.mkUninterpretedSort(ctx.mkSymbol("Dir"))));
		
	    Expr[] argParent= new Expr[3];
	    argParent[0]=fatherId;
	    argParent[1]=nodeId;	
	    argParent[2]=dname;



		Expr parentTuple=filesystem.Parent.mkDecl().apply(argParent);
		filesystem.Parent_set= ctx.mkSetAdd(filesystem.Parent_set, parentTuple);
		
    	 Expr[] finalArgs =  new Expr[2];
    	 finalArgs[0]=filesystem.root;
    	 finalArgs[1]=ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));
    		
    	 Expr tbody= (BoolExpr) ctx.mkApp(filesystem.exists, finalArgs)  ;
    	 
 	     Expr[] arg= new Expr[3];
 	     arg[0]=dirId;
 	     arg[1]=filesystem.root;
 	     arg[2]=tbody;	
 		 Expr reachableTuple=filesystem.Reachability.mkDecl().apply(arg);
 		 filesystem.Reachable_set= ctx.mkSetAdd(filesystem.Reachable_set, reachableTuple);

	     return filesystem.Parent_set ;

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

		if (op.getName()=="AddFile") {
			return ctx.mkOr(new BoolExpr[]{ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], dname)), ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[1], fatherId))});
		}
		else if (op.getName()=="MoveFile") {
			return ctx.mkOr(new BoolExpr[]{ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], dname)), ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[1], fatherId))});
		}
		
		else if (op.getName()=="AddDirectory") {
			return ctx.mkOr(new BoolExpr[]{ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], dname)), ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[1], fatherId))});
		}		
		else if (op.getName()=="RemoveDirectory") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.fatherId));
		}
		else if (op.getName()=="MoveDirectory") {
			return ctx.mkOr(new BoolExpr[]{ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], dname)), ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[1], fatherId))});
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
		if (op.getName()=="RemoveFile"   )
			return  ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[2], nodeId));
		
		return ctx.mkTrue();
	}

	@Override
	public void putConcurrentOp(Context ctx, Operation op) throws Z3Exception {
		
	}

}
