package evaluation.filesystemcrdt.operations;

import application.Operation;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.Z3Exception;
import evaluation.filesystemcrdt.filesystemcrdt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RRemoveDirectoryCRDT implements Operation  {
	
	private String name;
	private Expr dir1;
	
 	private Expr dir;
	private Expr fatherId;
	private Expr dirId;
	private Expr dirNode;

	Operation concurrentOp;
	int index;

	public RRemoveDirectoryCRDT(String name, Context ctx) {
		
		this.name=name;
		try {
			
			Random rand=new Random();
		    index=rand.nextInt((20 - 6) + 1) + 6;
		    
	   	 	this.fatherId=ctx.mkConst("dir"+index, ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));
		    Expr[] nodeArgs1 =  new Expr[3];
		    nodeArgs1[0]=ctx.mkConst("dir"+index, ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")));
		    nodeArgs1[1]=filesystemcrdt.type.getConsts()[0];
		    nodeArgs1[2]=ctx.mkConst("name1"+index, ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));
		    
	   		dir1=filesystemcrdt.Node.mkDecl().apply(nodeArgs1);
		     
			dirId=ctx.mkConst("dir3"+index+fatherId, ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));
		    Expr[] nodeArgs =  new Expr[3];
		    nodeArgs[0]=ctx.mkConst("node3"+index+fatherId, ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")));;
		    nodeArgs[1]=filesystemcrdt.type.getConsts()[0];
		    nodeArgs[2]=ctx.mkConst("name3"+index+fatherId, ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));
		    
	   	 	this.dir=filesystemcrdt.Node.mkDecl().apply(nodeArgs);
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
		
  	    return (BoolExpr) ctx.mkNot(ctx.mkEq(filesystemcrdt.root, dirId));
	}

	@Override
	public Expr effect(Context ctx) throws Z3Exception {
		
		Expr[] argParent= new Expr[3];
		argParent[0]=fatherId;
		argParent[1]=dirNode;
		argParent[2]=ctx.mkConst("name3"+index+fatherId, ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));
		Expr parentTuple=filesystemcrdt.Parent.mkDecl().apply(argParent);
		
		Expr[] finalArgs =  new Expr[2];
		finalArgs[0]=filesystemcrdt.root ;
		finalArgs[1]=dirId;
		
		Expr tbody= (BoolExpr) ctx.mkApp(filesystemcrdt.exists, finalArgs)  ;
	 
		Expr[] arg= new Expr[3];
		arg[0]=dirId;
		arg[1]=filesystemcrdt.root;
		arg[2]=tbody;	
		Expr reachableTuple=filesystemcrdt.Reachability.mkDecl().apply(arg);
		
		Expr[] argParent1= new Expr[3];
		argParent1[0]=dirId;
		argParent1[1]=ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")));
		argParent1[2]=ctx.mkConst("name1", ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));
		Expr parentTuple1=filesystemcrdt.Parent.mkDecl().apply(argParent1);

		Expr parentship=ctx.mkSetMembership(parentTuple1, filesystemcrdt.Parent_set);
		
		Expr[] finalArgs1 =  new Expr[2];
		finalArgs1[0]=filesystemcrdt.root ;
		finalArgs1[1]=ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));;
		
		Expr tbody1= (BoolExpr) ctx.mkApp(filesystemcrdt.exists, finalArgs1)  ;
	 
		Expr[] arg1= new Expr[3];
		arg1[0]=ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));;;
		arg1[1]=filesystemcrdt.root;
		arg1[2]=tbody1;	
		Expr reachableTuple1=filesystemcrdt.Reachability.mkDecl().apply(arg1);
		
		Expr isEmpty = null;
		
		System.out.println("concurrent op:");
		
		if (concurrentOp!=null && (concurrentOp.getName()=="AddDirectory" || concurrentOp.getName()=="MoveDirectory" ) ) {

			filesystemcrdt.UpdatedFile_set=ctx.mkITE(ctx.mkOr(new BoolExpr[]{ctx.mkEq(concurrentOp.getArgs(this.name)[1], dirId), ctx.mkEq(concurrentOp.getArgs(this.name)[3], dirNode)}),
					filesystemcrdt.UpdatedFile_set, ctx.mkSetDel(filesystemcrdt.UpdatedFile_set, dir));
			
			filesystemcrdt.Node_set=ctx.mkITE(ctx.mkOr(new BoolExpr[]{ctx.mkEq(concurrentOp.getArgs(this.name)[1], dirId), ctx.mkEq(concurrentOp.getArgs(this.name)[3], dirNode)}),
					filesystemcrdt.Node_set, ctx.mkSetDel(filesystemcrdt.Node_set, dir));
			
			filesystemcrdt.Dir_set=ctx.mkITE(ctx.mkOr(new BoolExpr[]{ctx.mkEq(concurrentOp.getArgs(this.name)[1], dirId), ctx.mkEq(concurrentOp.getArgs(this.name)[2], dirId)})
					, filesystemcrdt.Dir_set, ctx.mkSetDel(filesystemcrdt.Dir_set, dirId));
			
			filesystemcrdt.FSObject_set=ctx.mkITE(ctx.mkOr(new BoolExpr[]{ctx.mkEq(concurrentOp.getArgs(this.name)[1], dirId), ctx.mkEq(concurrentOp.getArgs(this.name)[3], dirNode)}),

					filesystemcrdt.FSObject_set, ctx.mkSetDel(filesystemcrdt.FSObject_set, ctx.mkConst("node3" + index + fatherId, ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")))));
			
			filesystemcrdt.Parent_set=ctx.mkITE(ctx.mkOr(new BoolExpr[]{ctx.mkEq(concurrentOp.getArgs(this.name)[1], dirId), ctx.mkEq(concurrentOp.getArgs(this.name)[3], dirNode)}),
					filesystemcrdt.Parent_set, ctx.mkSetDel(filesystemcrdt.Parent_set, parentTuple));
			
			filesystemcrdt.Reachable_set=ctx.mkITE(ctx.mkOr(new BoolExpr[]{ctx.mkEq(concurrentOp.getArgs(this.name)[1], dirId), ctx.mkEq(concurrentOp.getArgs(this.name)[2], dirId)}),
					filesystemcrdt.Reachable_set, ctx.mkSetDel(filesystemcrdt.Reachable_set, reachableTuple));
			/////

		    filesystemcrdt.Dir_set =ctx.mkITE(ctx.mkAnd(new BoolExpr[]{(BoolExpr) parentship,
					ctx.mkNot(ctx.mkEq(concurrentOp.getArgs(this.name)[3], ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")))))}), ctx.mkSetDel(filesystemcrdt.Dir_set,
					ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")))), filesystemcrdt.Dir_set);
			
		    filesystemcrdt.Parent_set =ctx.mkITE(ctx.mkAnd(new BoolExpr[]{(BoolExpr) parentship,
					ctx.mkNot(ctx.mkEq(concurrentOp.getArgs(this.name)[3], ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")))))}), ctx.mkSetDel(filesystemcrdt.Parent_set, parentTuple1), filesystemcrdt.Parent_set);
		    
		    
		    filesystemcrdt.FSObject_set=ctx.mkITE(ctx.mkAnd(new BoolExpr[]{(BoolExpr) parentship, ctx.mkNot(ctx.mkEq(concurrentOp.getArgs(this.name)[3],
							ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")))))}), ctx.mkSetDel(filesystemcrdt.FSObject_set, ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")))),
					filesystemcrdt.FSObject_set);
			
			filesystemcrdt.Reachable_set= ctx.mkITE(ctx.mkAnd(new BoolExpr[]{(BoolExpr) parentship, ctx.mkNot(ctx.mkEq(concurrentOp.getArgs(this.name)[3],
							ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")))))}),
					ctx.mkSetDel(filesystemcrdt.Reachable_set, reachableTuple1),
					filesystemcrdt.Reachable_set);
			
		}
		
		else if (concurrentOp!=null &&  ( concurrentOp.getName()=="AddFile" || concurrentOp.getName()=="MoveFile" )) {

			filesystemcrdt.UpdatedFile_set=ctx.mkITE(ctx.mkEq(concurrentOp.getArgs(this.name)[1], dirId), filesystemcrdt.UpdatedFile_set, ctx.mkSetDel(filesystemcrdt.UpdatedFile_set, dir));
			filesystemcrdt.Node_set=ctx.mkITE(ctx.mkEq(concurrentOp.getArgs(this.name)[1], dirId), filesystemcrdt.Node_set, ctx.mkSetDel(filesystemcrdt.Node_set, dir));
			filesystemcrdt.Dir_set=ctx.mkITE(ctx.mkEq(concurrentOp.getArgs(this.name)[1], dirId), filesystemcrdt.Dir_set, ctx.mkSetDel(filesystemcrdt.Dir_set, dirId));
			
			filesystemcrdt.FSObject_set=ctx.mkITE(ctx.mkEq(concurrentOp.getArgs(this.name)[1], dirId), filesystemcrdt.FSObject_set,
					ctx.mkSetDel(filesystemcrdt.FSObject_set, ctx.mkConst("node3" + index + fatherId, ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")))));
			
			filesystemcrdt.Parent_set=ctx.mkITE(ctx.mkEq(concurrentOp.getArgs(this.name)[1], dirId), filesystemcrdt.Parent_set, ctx.mkSetDel(filesystemcrdt.Parent_set, parentTuple));
			filesystemcrdt.Reachable_set=ctx.mkITE(ctx.mkEq(concurrentOp.getArgs(this.name)[1], dirId), filesystemcrdt.Reachable_set, ctx.mkSetDel(filesystemcrdt.Reachable_set, reachableTuple));
				
			/////
			Expr[] finalArgs11 =  new Expr[2];
			finalArgs11[0]=filesystemcrdt.root ;
			finalArgs11[1]=ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));;
			
			Expr tbody11= (BoolExpr) ctx.mkApp(filesystemcrdt.exists, finalArgs11)  ;
		 
			Expr[] arg11= new Expr[3];
			arg11[0]=ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));;;
			arg11[1]=dirId;
			arg11[2]=tbody11;	
			Expr reachableTuple11=filesystemcrdt.Reachability.mkDecl().apply(arg11);
			
			//////
		    filesystemcrdt.Parent_set =ctx.mkITE(ctx.mkAnd(new BoolExpr[]{(BoolExpr) parentship,
					ctx.mkNot(ctx.mkEq(concurrentOp.getArgs(this.name)[2], ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")))))}), ctx.mkSetDel(filesystemcrdt.Parent_set, parentTuple1), filesystemcrdt.Parent_set);
		    
		    
		    filesystemcrdt.FSObject_set=ctx.mkITE(ctx.mkAnd(new BoolExpr[]{(BoolExpr) parentship, ctx.mkNot(ctx.mkEq(concurrentOp.getArgs(this.name)[2],
							ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")))))}), ctx.mkSetDel(filesystemcrdt.FSObject_set, ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")))),
					filesystemcrdt.FSObject_set);
			
			filesystemcrdt.Reachable_set= ctx.mkITE(ctx.mkAnd(new BoolExpr[]{(BoolExpr) parentship, ctx.mkNot(ctx.mkEq(concurrentOp.getArgs(this.name)[2],
							ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")))))}),
					ctx.mkSetDel(filesystemcrdt.Reachable_set, reachableTuple1),
					filesystemcrdt.Reachable_set);
			///////
		    
		}
		
		else {
			
			
			Expr node1=ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")));
			Expr node2=ctx.mkConst("node2", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")));
			Expr po=ctx.mkSelect(filesystemcrdt.contentArray,
					dirNode);
			
			
		    filesystemcrdt.Dir_set =ctx.mkITE(ctx.mkAnd(new BoolExpr[]{(BoolExpr) parentship,
							ctx.mkNot(ctx.mkEq(filesystemcrdt.root, ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")))))}),
					ctx.mkSetDel(filesystemcrdt.Dir_set, ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")))), filesystemcrdt.Dir_set);
		 
		    
	 	    
	 		 filesystemcrdt.FSObject_set= ctx.mkITE((BoolExpr) ctx.mkSetMembership(node1, po),
					 ctx.mkITE((BoolExpr) ctx.mkSetMembership(node2, ctx.mkSelect(filesystemcrdt.contentArray,
							 node1)), ctx.mkSetDel(filesystemcrdt.FSObject_set, node2), ctx.mkSetDel(filesystemcrdt.FSObject_set, node1)),
					 ctx.mkSetDel(filesystemcrdt.FSObject_set, dirNode));
	 		 
	 		Expr[] argParent2= new Expr[3];
	 		argParent2[0]=ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));;
	 		argParent2[1]=ctx.mkConst("node2", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")));
	 		argParent2[2]=ctx.mkConst("name2", ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));
			Expr parentTuple2=filesystemcrdt.Parent.mkDecl().apply(argParent2);
	 		 
		 	    
		 		 filesystemcrdt.Parent_set= ctx.mkITE((BoolExpr) ctx.mkSetMembership(node1, po),
						 ctx.mkITE((BoolExpr) ctx.mkSetMembership(node2, ctx.mkSelect(filesystemcrdt.contentArray,
								 node1)), ctx.mkSetDel(filesystemcrdt.Parent_set, parentTuple2), ctx.mkSetDel(filesystemcrdt.Parent_set, parentTuple1)),
						 ctx.mkSetDel(filesystemcrdt.Parent_set, parentTuple));
	 		 
	 		 
		    
/*			filesystemcrdt.UpdatedFile_set= ctx.mkSetDel(filesystemcrdt.UpdatedFile_set,dir);
			filesystemcrdt.Node_set= ctx.mkSetDel(filesystemcrdt.Node_set,dir);
			
			filesystemcrdt.Dir_set= ctx.mkSetDel(filesystemcrdt.Dir_set,dirId);
			
		//	filesystemcrdt.FSObject_set= ctx.mkSetDel(filesystemcrdt.FSObject_set,ctx.mkConst("node3"+index+fatherId, ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject"))));

			filesystemcrdt.Parent_set= ctx.mkSetDel(filesystemcrdt.Parent_set,parentTuple);
			filesystemcrdt.Reachable_set= ctx.mkSetDel(filesystemcrdt.Reachable_set,reachableTuple);
			
			Sort[] nodearg = new Sort[1];
			nodearg[0]=ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject"));
			Symbol[] namesp = new Symbol[1];
			namesp[0] =  ctx.mkSymbol("node1");
			
			Expr ex=ctx.mkAnd(new BoolExpr [] {
					ctx.mkNot((BoolExpr) ctx.mkSetMembership(ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject"))),filesystemcrdt.FSObject_set ))});
			Expr body= ctx.mkImplies( (BoolExpr)parentship,(BoolExpr)ex);
			
			 isEmpty=ctx.mkForall(nodearg, namesp, (BoolExpr)body, 1, null, null,null, null);



		 		 

		    filesystemcrdt.Parent_set =ctx.mkITE((BoolExpr) parentship, ctx.mkSetDel(filesystemcrdt.Parent_set,parentTuple1),filesystemcrdt.Parent_set );
		//    filesystemcrdt.FSObject_set=ctx.mkITE((BoolExpr) parentship, ctx.mkSetDel(filesystemcrdt.FSObject_set,ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")))),
		 //   		filesystemcrdt.FSObject_set );

			filesystemcrdt.Reachable_set= ctx.mkITE((BoolExpr) parentship, ctx.mkSetDel(filesystemcrdt.Reachable_set,reachableTuple1),
		    		filesystemcrdt.Reachable_set );
		    
		//    filesystem.UpdatedFile_set=ctx.mkITE((BoolExpr) parentship, ctx.mkSetDel(filesystem.FSObject_set,ctx.mkConst("node2", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")))),
		  //  		filesystem.UpdatedFile_set );
*/
			

		} 
	    return   filesystemcrdt.FSObject_set ;
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

		Operation o1=new AddFileCRDT("AddFile",ctx);
		Operation o2=new RemoveFileCRDT("RemoveFile",ctx);
		Operation o3=new UpdateFileCRDT("UpdateFile",ctx);
		Operation o4=new MoveFileCRDT("MoveFile",ctx);
		Operation o5=new AddDirectoryCRDT("AddDirectory",ctx);
	//	Operation o6=new RemoveDirectory("RemoveDirectory",ctx);
		Operation o7=new MoveDirectoryCRDT("MoveDirectory", ctx);
		Operation o8=new RRemoveDirectoryCRDT("RecursiveRemove",ctx);
		
		op.add(o1);
		op.add(o2);
		op.add(o3);
		op.add(o4);
		op.add(o5);
	//	op.add(o6);		
		op.add(o7);	
		op.add(o8);
		
		return op;
	}

	@Override
	public Expr getConditions(Context ctx, Operation op) throws Z3Exception {

		if ( op.getName()=="AddFile" || op.getName()=="MoveFile" )
			return  ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[2], dirNode));
		
		return ctx.mkTrue();
	}
	@Override
	public void putConcurrentOp(Context ctx, Operation op) throws Z3Exception {
		this.concurrentOp=op;
		
	}

}
