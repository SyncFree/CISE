package evaluation.auction;


import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Z3Exception;

public class productObj {

	public Expr product;
	public IntExpr stockArrayIndex;
	public IntExpr stock;
	
	public  productObj(Context ctx) throws Z3Exception {
		
		this.product=ctx.mkConst("product", ctx.mkUninterpretedSort(ctx.mkSymbol("Product")));
		this.stockArrayIndex=(IntExpr) ctx.mkConst("stockIndex",ctx.mkIntSort());
		this.stock=(IntExpr) ctx.mkConst("stock",ctx.mkIntSort());;
		
	}
	
	public  productObj(Context ctx, int i) throws Z3Exception {
		
    	this.product=ctx.mkConst("product"+i, ctx.mkUninterpretedSort(ctx.mkSymbol("Product")));
		this.stockArrayIndex=(IntExpr) ctx.mkConst("stockIndex"+i,ctx.mkIntSort());
		
	}

	public Expr getProduct() {
		return this.product;
	}
	
	
	public Expr getIndex() {
		return this.stockArrayIndex;
	}
	
	public Expr getStock() {
		return this.stock;
	}
	
	public void putStock(IntExpr stock) {
		 this.stock=stock;
	}
	
}