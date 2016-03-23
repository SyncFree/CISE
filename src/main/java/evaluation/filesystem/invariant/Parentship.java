
package evaluation.filesystem.invariant;

import com.microsoft.z3.*;
import evaluation.filesystem.filesystem;
import invariant.Invariant;

public class Parentship implements Invariant {

	String name;
	
	public Parentship(String name){
		this.name=name;
	}
	
	@Override
	public String getName() {
		return this.name;
	}


	@Override
	public BoolExpr getInv(Context ctx) throws Z3Exception {
		
		 Sort[] nodes = new Sort[4];
		 nodes[0] =ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject"));
		 nodes[1] =ctx.mkUninterpretedSort(ctx.mkSymbol("Dir"));
		 nodes[2] =ctx.mkUninterpretedSort(ctx.mkSymbol("Dir"));
		 nodes[3] =ctx.mkUninterpretedSort(ctx.mkSymbol("Name"));
		 
		 
		 //setting names
		 Symbol[] namess = new Symbol[4];
		 namess[0] =  ctx.mkSymbol("node1");
		 namess[1] =  ctx.mkSymbol("dir1");
		 namess[2] =  ctx.mkSymbol("dir2");
		 namess[3] =  ctx.mkSymbol("name1");

        
        Expr node1=ctx.mkConst("dir1", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")) );
        Expr node2=ctx.mkConst("dir2", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")) );
        
        Expr node3=ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")) );

      //  Expr existNode1= ctx.mkSetMembership(node2,filesystem.FSObject_set);
     
		 Expr[] fatherindex =  new Expr[3];
      	 fatherindex[0]=node1;
		 fatherindex[1]=node3;
		 fatherindex[2]=ctx.mkConst("name1", ctx.mkUninterpretedSort(ctx.mkSymbol("Name")) );;
		 
	     Expr parentTuple=filesystem.Parent.mkDecl().apply(fatherindex);
	     
	     
			 Expr[] fatherindex1 =  new Expr[3];
	      	 fatherindex1[0]=node2;
			 fatherindex1[1]=node3;
			 fatherindex1[2]=ctx.mkConst("name1", ctx.mkUninterpretedSort(ctx.mkSymbol("Name")) );;;
			 
			 
			 
		     Expr parentTuple1=filesystem.Parent.mkDecl().apply(fatherindex1);
		     
		     
	     
	 	 Expr father1=ctx.mkAnd(new BoolExpr [] {(BoolExpr) ctx.mkSetMembership(parentTuple, filesystem.Parent_set),
	 			 (BoolExpr) ctx.mkSetMembership(parentTuple1, filesystem.Parent_set)});

	 	 Expr body=ctx.mkImplies((BoolExpr )father1, (BoolExpr )ctx.mkEq(node1, node2));
	   	  
		 
          BoolExpr mustUniqueee =ctx.mkForall(nodes, namess, body, 1, null, null,  null, null);
        
          System.out.println("Correct Parent relation: " + mustUniqueee.toString());
        
        
		 return mustUniqueee;
	
	}

}
