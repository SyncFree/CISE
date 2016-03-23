package evaluation.filesystem.invariant;

import com.microsoft.z3.*;
import evaluation.filesystem.filesystem;
import invariant.Invariant;


public class Uniqueness implements Invariant {
	
	String name;
	
	public Uniqueness(String name){
		this.name=name;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public BoolExpr getInv(Context ctx) throws Z3Exception {

		Sort[] nodes = new Sort[3];
	    nodes[0] =ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject"));
		nodes[1] =ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject"));
		nodes[2] =ctx.mkUninterpretedSort(ctx.mkSymbol("Dir"));
		 
		 //setting names
		 Symbol[] namess = new Symbol[3];
		 namess[0] =  ctx.mkSymbol("node1");
		 namess[1] =  ctx.mkSymbol("node2");
		 namess[2] =  ctx.mkSymbol("dir3");
		 
         
         Expr node1=ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")) );
         Expr node2=ctx.mkConst("node2", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")) );
         
         Expr name2=ctx.mkConst("name2", ctx.mkUninterpretedSort(ctx.mkSymbol("Name")) );
         Expr name1=ctx.mkConst("name1", ctx.mkUninterpretedSort(ctx.mkSymbol("Name")) );
         
         
         Expr node3=ctx.mkConst("dir3", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")) );

         
     	 Expr[] finalArgs =  new Expr[3];
     	 finalArgs[0]=node1;
     	 finalArgs[1]=ctx.mkConst("type", filesystem.type);
     	 finalArgs[2]=name1;
     		
     	 Expr Node11= filesystem.Node.mkDecl().apply(finalArgs);
     	 
     	 Expr existNode11= ctx.mkSetMembership(Node11,filesystem.Node_set);

 		 Expr[] fatherindex =  new Expr[3];
    	 fatherindex[0]=node3;
		 fatherindex[1]=node1;
		 fatherindex[2]=name1;
		 
	     Expr parentTuple=filesystem.Parent.mkDecl().apply(fatherindex);
	     
	 	 Expr father1=(BoolExpr) ctx.mkSetMembership(parentTuple, filesystem.Parent_set);
	     
 		 Expr[] fatherindex2 =  new Expr[3];
    	 fatherindex2[0]=node3;
		 fatherindex2[1]=node2;
		 fatherindex2[2]=name2;
		 
	     Expr parentTuple2=filesystem.Parent.mkDecl().apply(fatherindex2);
	     
	     Expr father2=(BoolExpr) ctx.mkSetMembership(parentTuple2, filesystem.Parent_set);
	     
	     Expr eq=ctx.mkEq(name1, name2);

	     Expr same=ctx.mkAnd(new BoolExpr [] {  (BoolExpr) eq, (BoolExpr) father2, (BoolExpr) father1
	    		 			 });

	     
	     Expr rt=ctx.mkImplies((BoolExpr) same,ctx.mkEq(node1, node2) );

	     
		 Sort[] nodes2 = new Sort[1];
		 nodes2[0] =ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject"));

		 //setting names
		 Symbol[] namess2 = new Symbol[1];
		 namess2[0] =  ctx.mkSymbol("node2");

         
         BoolExpr mustUniqueee2 = ctx.mkForall(nodes, namess, rt, 1, null, null, null, null);
         
         ////////////////////////////////////
	     BoolExpr uniqueNode =ctx.mkEq( node1, node2);
	        
	     Expr uniqueName=ctx.mkImplies( ctx.mkAnd(new BoolExpr []{  (BoolExpr) father2 , ctx.mkEq(name1, name2) }),uniqueNode  );
	        
		 Sort[] nodeArg = new Sort[1];
		 nodeArg[0] =ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject"));

	    //setting names
		 Symbol[] names = new Symbol[1];
		 names[0] =  ctx.mkSymbol("node2");

		 Expr exprUniqueNode=ctx.mkForall(nodeArg, names, uniqueName, 1, null, null,null, null);
	          
	     Expr oneFather= ctx.mkAnd(new BoolExpr[] {(BoolExpr) father1,(BoolExpr) exprUniqueNode });
	        
	     Sort[] buyerarg = new Sort[1];
		 buyerarg[0] =ctx.mkUninterpretedSort(ctx.mkSymbol("Dir"));

			 //setting names
		 Symbol[] namesbuyer = new Symbol[1];
		 namesbuyer[0] =  ctx.mkSymbol("dir3");
	        
		 Expr siblings = ctx.mkExists(buyerarg, namesbuyer, oneFather, 1, null, null,null, null);
         
		 Expr nodeExist=ctx.mkAnd(new BoolExpr[] {(BoolExpr) existNode11, (BoolExpr) ctx.mkSetMembership( node1, filesystem.FSObject_set) });
					 
		 Expr main=ctx.mkImplies( 	 (BoolExpr) nodeExist,  (BoolExpr) siblings);

		 Sort[] nodeArgs = new Sort[1];
		 nodeArgs[0] =ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject"));

		 //setting names
		 Symbol[] namess22 = new Symbol[1];
		 namess22[0] =  ctx.mkSymbol("node1");

         BoolExpr inv = ctx.mkForall(nodeArgs, namess22, main, 1, null, null, null, null);

         System.out.println("Quantifier Uniqueness: " + inv.toString());
  
		 return (BoolExpr) inv;
	}

}