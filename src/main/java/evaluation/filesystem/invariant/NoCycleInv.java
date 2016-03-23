package evaluation.filesystem.invariant;

import com.microsoft.z3.*;
import evaluation.filesystem.filesystem;
import invariant.Invariant;

public class NoCycleInv implements Invariant  {

	private String name; 
	
	public NoCycleInv(String name) {
		this.name=name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public BoolExpr getInv(Context ctx) throws Z3Exception {

		 Expr dirId1=ctx.mkConst("dir1", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));
		 Expr dirId2=ctx.mkConst("dir3", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));

     	 Expr[] finalArgs =  new Expr[2];
     	 finalArgs[0]=dirId2;
     	 finalArgs[1]=dirId1;
     	 	
     	 Expr exist= ctx.mkApp(filesystem.exists, finalArgs)  ;
     	      	 
  	     Expr[] argreachable= new Expr[3];
  	     argreachable[0]=dirId1;
  	     argreachable[1]=dirId2;
  	     argreachable[2]=exist;	
  		 Expr reachableTuple=filesystem.Reachability.mkDecl().apply(argreachable);
     	 
     	 Expr reachability=(BoolExpr) ctx.mkSetMembership(reachableTuple, filesystem.Reachable_set);

     	
    	 Expr[] existArgs1 =  new Expr[2];
    	 existArgs1[0]=dirId1;
    	 existArgs1[1]=dirId2;
    		
    	 Expr exist1= ctx.mkApp(filesystem.exists, existArgs1)  ;

 	     Expr[] argreachable1= new Expr[3];
 	     argreachable1[0]=dirId2;
 	     argreachable1[1]=dirId1;
 	     argreachable1[2]=exist1;	
 		 Expr reachableTuple1=filesystem.Reachability.mkDecl().apply(argreachable1);

    	 Expr rechability1=(BoolExpr) ctx.mkSetMembership(reachableTuple1, filesystem.Reachable_set);

    	 Expr cycle=ctx.mkImplies(ctx.mkAnd(new BoolExpr [] { (BoolExpr) reachability,  (BoolExpr) rechability1}), (BoolExpr) ctx.mkEq(dirId2, dirId1));
    	
		 Sort[] nodes = new Sort[2];
		 nodes[0] =ctx.mkUninterpretedSort(ctx.mkSymbol("Dir"));
		 nodes[1] =ctx.mkUninterpretedSort(ctx.mkSymbol("Dir"));

		 //setting names
		 Symbol[] namess = new Symbol[2];
		 namess[0] =  ctx.mkSymbol("dir1");
		 namess[1] =  ctx.mkSymbol("dir3");
        
         BoolExpr inv =ctx.mkForall(nodes, namess, cycle, 1, null, null,  null, null);

		 System.out.println("cycle invariant:"+inv); 
     
		 return (BoolExpr) inv;


		 
	}

}

