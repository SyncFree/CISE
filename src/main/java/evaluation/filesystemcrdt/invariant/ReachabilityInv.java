package evaluation.filesystemcrdt.invariant;


import com.microsoft.z3.*;
import evaluation.filesystemcrdt.filesystemcrdt;
import invariant.Invariant;

public class ReachabilityInv implements Invariant {

	private String name; 
	public ReachabilityInv(String string, Context ctx) {
		
	}

	@Override
	public String getName() {
		return name;
	}

	public Expr reachable(Context ctx) throws Z3Exception{
		
		Expr nodeID=ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")));
	    Expr root=filesystemcrdt.root;

     	Expr[] finalArgs =  new Expr[2];
     	finalArgs[0]=root;
     	finalArgs[1]=ctx.mkConst(ctx.mkSymbol("dir3"), filesystemcrdt.Dir);
     		
     	Expr exist= ctx.mkApp(filesystemcrdt.exists, finalArgs)  ;

  	    Expr[] arg= new Expr[3];
  	    arg[0]=ctx.mkConst(ctx.mkSymbol("dir3"), filesystemcrdt.Dir);
  	    arg[1]=root;
  	    arg[2]=exist;	
  		Expr reachabilityTuple=filesystemcrdt.Reachability.mkDecl().apply(arg);

      	Expr reachability=(BoolExpr) ctx.mkSetMembership(reachabilityTuple, filesystemcrdt.Reachable_set);

     	Expr[] fatherArgs =  new Expr[3];
     	fatherArgs[0]=ctx.mkConst(ctx.mkSymbol("dir3"), filesystemcrdt.Dir);
     	fatherArgs[1]=nodeID;
        fatherArgs[2]=ctx.mkConst("name1", ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));;

     	Expr parentTuple2=filesystemcrdt.Parent.mkDecl().apply(fatherArgs);

   		Expr parentReachable=ctx.mkImplies((BoolExpr) ctx.mkSetMembership(parentTuple2, filesystemcrdt.Parent_set), (BoolExpr) reachability );

    	Sort[] nodesw =  new Sort[1];   	   	    
   	    nodesw[0] = ctx.mkUninterpretedSort(ctx.mkSymbol("Dir"));
   		 
   	    Symbol[] nnamesw = new Symbol[1];
   		nnamesw[0] =  ctx.mkSymbol("dir3");

    	Expr based= ctx.mkExists(nodesw, nnamesw, parentReachable, 1, null, null,null,null);

    	Expr dirId=ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));

    	Expr main=ctx.mkOr(new BoolExpr[]{ (BoolExpr) ctx.mkEq(dirId, filesystemcrdt.root),(BoolExpr) based	   });
  
  		Expr expr=ctx.mkImplies( (BoolExpr) ctx.mkSetMembership(nodeID, filesystemcrdt.FSObject_set)
  		    			, (BoolExpr)main);
 
		Sort[] nodesr =  new Sort[1];		    
    	nodesr[0]	=	 ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject"));

    	Symbol[] nnamesr = new Symbol[1];
    	nnamesr[0]=ctx.mkSymbol("node1");
    	
	    Expr inv = ctx.mkForall(nodesr, nnamesr, expr, 1, null, null,null, null);

       // System.out.println("reachability invariant:"+inv);
	       
	    return inv;

	}
	
	@Override
	public BoolExpr getInv(Context ctx) throws Z3Exception {
		return (BoolExpr) reachable(ctx);
	}

}
