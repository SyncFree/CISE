package evaluation.filesystem.invariant;

import com.microsoft.z3.*;
import evaluation.filesystem.filesystem;
import invariant.Invariant;

public class CausalityRelation implements Invariant{

	
	private String name;
	
	public CausalityRelation(String name) {
		this.name=name;
	}

	@Override
	public String getName() {

		return this.name;
	}

	@Override
	public BoolExpr getInv(Context ctx) throws Z3Exception {
				
		Expr node = ctx.mkConst("node1", ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")));
		
		Sort[] arg = new Sort[1];
		arg[0]=ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject"));

		Symbol[] namesb = new Symbol[1];
		namesb[0] =  ctx.mkSymbol("node1");
		
		Expr[] argFile = new Expr[3];
		argFile[0]=node;
		argFile[1]=filesystem.type.getConsts()[1];
		argFile[2]=ctx.mkConst("name1", ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));

	    Expr fileTuple=filesystem.Node.mkDecl().apply(argFile);
	    Expr fileExist=(BoolExpr) ctx.mkSetMembership(fileTuple, filesystem.Node_set);
		
		Expr[] argDir = new Expr[3];
		argDir[0]=node;
		argDir[1]=filesystem.type.getConsts()[0];
		argDir[2]=ctx.mkConst("name1", ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));

	    Expr dirTuple=filesystem.Node.mkDecl().apply(argDir);
	    Expr dirExist=(BoolExpr) ctx.mkSetMembership(dirTuple, filesystem.Node_set);

	    Expr updatedFile =ctx.mkImplies( (BoolExpr)ctx.mkSetMembership(fileTuple, filesystem.UpdatedFile_set),
	    		 (BoolExpr) fileExist );
	   
	    Expr updatedDir =ctx.mkImplies( (BoolExpr)ctx.mkSetMembership(dirTuple, filesystem.UpdatedFile_set),
	    		 (BoolExpr) dirExist );
	    
	    Expr updatedNode= ctx.mkAnd(new BoolExpr [] {(BoolExpr) updatedFile,(BoolExpr) updatedDir } );
	     
	    Expr inv = ctx.mkForall(arg, namesb, updatedNode, 1, null, null,null, null);

	    System.out.println("Causality Relation"+inv);
		
		return (BoolExpr) inv;
	}

}
