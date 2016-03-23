package evaluation.filesystem.operations;

import application.Operation;
import com.microsoft.z3.*;
import evaluation.filesystem.filesystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MoveDirectory implements Operation  {

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
	
	
	public MoveDirectory(String name, Context ctx) {
		
		this.name=name;
		try {

			Random rand=new Random();
		    index1=rand.nextInt((20 - 6) + 1) + 6;
		    
			father1Id=ctx.mkConst("dir" + index1, ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));
		    Expr[] nodeArgs1 =  new Expr[3];
		    nodeArgs1[0]=father1Id;
		    nodeArgs1[1]=filesystem.type.getConsts()[0];
		    nodeArgs1[2]=ctx.mkConst("name5" + index1, ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));
	   	 	
		    index2=rand.nextInt((20 - 6) + 1) + 6;

	   	 	father2Id=ctx.mkConst("dir" + index2, ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));
	   	 	
		    Expr[] nodeArgs2 =  new Expr[3];
		    nodeArgs2[0]=father2Id;
		    nodeArgs2[1]=filesystem.type.getConsts()[0];
		    nodeArgs2[2]=ctx.mkConst("name" + index2, ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));
	   	 
		    index=rand.nextInt();
		    
	   	 	id=ctx.mkConst("node1" + index, ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));
	    	dname=ctx.mkConst("name1" + index, ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));;
	    	nodeId=ctx.mkConst("node1" + index, ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")));
	   	   	 	
		    Expr[] nodeArgs =  new Expr[3];
		    nodeArgs[0]=ctx.mkConst("node1" + index, ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")));;
		    nodeArgs[1]=filesystem.type.getConsts()[0];
		    nodeArgs[2]=dname;	    
	   	 	this.dir=filesystem.Node.mkDecl().apply(nodeArgs);
		
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
		finalArgs[0]=filesystem.root;
		finalArgs[1]=father2Id;
		Expr tbody= ctx.mkApp(filesystem.exists, finalArgs)  ;
  	 
	    Expr[] arg= new Expr[3];
	    arg[0]=father2Id;
	    arg[1]=filesystem.root;
	    arg[2]=tbody;	
		Expr reachTuple=filesystem.Reachability.mkDecl().apply(arg);
		Expr reachable= ctx.mkSetMembership(reachTuple, filesystem.Reachable_set);

		Expr node=ctx.mkConst("node2", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")));
		Expr name=ctx.mkConst("name2", ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));
		 
 		Expr[] fatherindex =  new Expr[3];
    	fatherindex[0]=father2Id;
		fatherindex[1]=node;
		fatherindex[2]=name;
		 
	    Expr parentTuple1=filesystem.Parent.mkDecl().apply(fatherindex);
	     
	 	Expr fatherRealtion=(BoolExpr) ctx.mkSetMembership(parentTuple1, filesystem.Parent_set);
	 	 
		Sort[] nodes = new Sort[1];
		nodes[0] =ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject"));

		Symbol[] namess = new Symbol[1];
		namess[0] =  ctx.mkSymbol("node2");

		 BoolExpr mustUniqueee= ctx.mkNot(ctx.mkExists(nodes, namess, ctx.mkAnd(new BoolExpr[]{(BoolExpr) fatherRealtion, ctx.mkEq(name, dname)}), 1, null, null, null, null));

 		 Expr[] finalArgs1 =  new Expr[2];
 		 finalArgs1[0]=id;
 		 finalArgs1[1]=father2Id; ;
    	 	
 		 Expr tbody1= (BoolExpr) ctx.mkApp(filesystem.exists, finalArgs1)  ;
 		
 	     Expr[] arg1= new Expr[3];
 	     arg1[0]=father2Id; 
 	     arg1[1]=id;
 	     arg1[2]=tbody1;	
 		 Expr reachableTuple=filesystem.Reachability.mkDecl().apply(arg1);
 		 Expr notAncestor=ctx.mkSetMembership(reachableTuple, filesystem.Reachable_set);
         
         Expr dir1=ctx.mkConst("dir3", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));
         Expr node1=ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")));
         Expr name1=ctx.mkConst("name1", ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));

         Expr precondition=ctx.mkAnd(new BoolExpr[]{(BoolExpr) mustUniqueee, (BoolExpr) reachable,
				 ctx.mkNot(ctx.mkEq(filesystem.root, id)), ctx.mkNot((BoolExpr) notAncestor), (BoolExpr)
				 ctx.mkSetMembership(dir, filesystem.Node_set), ctx.mkEq(node1, ctx.mkConst("node1" + index, ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")))), ctx.mkEq(dname, name1), ctx.mkEq(dir1, father2Id)});
		 
		 return (BoolExpr)  precondition ;
	}

	@Override
	public Expr effect(Context ctx) throws Z3Exception {
		
	    Expr[] argParent= new Expr[3];
	    argParent[0]=father1Id;
	    argParent[1]=ctx.mkConst("node1" + index, ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")));
	    argParent[2]=dname;
		Expr parentTuple=filesystem.Parent.mkDecl().apply(argParent);
		filesystem.Parent_set= ctx.mkSetDel(filesystem.Parent_set, parentTuple);
		
	    Expr[] argParent2= new Expr[3];
	    argParent2[0]=father2Id;
	    argParent2[1]=ctx.mkConst("node1" + index, ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")));
	    argParent2[2]=dname;	
		Expr parentTuple2=filesystem.Parent.mkDecl().apply(argParent2);
		filesystem.Parent_set= ctx.mkSetAdd(filesystem.Parent_set, parentTuple2);
		
		Expr[] finalArgs =  new Expr[2];
		finalArgs[0]=father2Id;
		finalArgs[1]=id;
   		
		 Expr tbody= (BoolExpr) ctx.mkApp(filesystem.exists, finalArgs)  ;
		
	     Expr[] arg= new Expr[3];
	     arg[0]=id;
	     arg[1]=father2Id;
	     arg[2]=tbody;	
		 Expr parentTuple3=filesystem.Reachability.mkDecl().apply(arg);
		 filesystem.Reachable_set= ctx.mkSetAdd(filesystem.Reachable_set, parentTuple3);
	     filesystem.UpdatedFile_set= ctx.mkSetAdd(filesystem.UpdatedFile_set, dir);

		return filesystem.Parent_set;
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

		
		if (op.getName()=="AddFile") {
			return ctx.mkOr(new BoolExpr[]{ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], dname)), ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[1], father2Id))});
		}
		else if (op.getName()=="MoveFile") {
			return ctx.mkOr(new BoolExpr[]{ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], dname)), ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[1], father2Id))});
		}
		
		else if (op.getName()=="AddDirectory") {
			return ctx.mkOr(new BoolExpr[]{ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], dname)), ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[1], father2Id))});
		}
		
		else if (op.getName()=="RemoveDirectory") {		
	       return ( ctx.mkAnd(new BoolExpr[]{ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[1], dir))
				   , ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.father2Id))}));
		}
		
		else if (op.getName()=="MoveDirectory") {			
		    return ctx.mkAnd(new BoolExpr[]{ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], dname)), ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[2], father2Id))});
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
		
		if (op.getName()=="RemoveFile" )
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[2], nodeId));

		return ctx.mkTrue();
	}

	@Override
	public void putConcurrentOp(Context ctx, Operation op) throws Z3Exception {
		
	}

}
