package evaluation.auction.invariant;

import com.microsoft.z3.*;
import evaluation.auction.auctionapp;
import invariant.Invariant;

public class ForeignKeyOwner implements Invariant  {

	String name;
	
	public ForeignKeyOwner(String name) {
		
		this.name=name;
	}

	public ForeignKeyOwner() {
	}

	@Override
	public String getName() {
		
		return this.name;
	}

	@Override
	public BoolExpr getInv(Context ctx) throws Z3Exception {
	
		Expr seller = ctx.mkConst("seller", ctx.mkUninterpretedSort(ctx.mkSymbol("SellerID")));
		Expr product = ctx.mkConst("product", ctx.mkUninterpretedSort(ctx.mkSymbol("Product")));
	
		Expr[] argOwner = new Expr[2];
		argOwner[0]=product;;
		argOwner[1]=seller;

	    Expr owner=auctionapp.Owner.mkDecl().apply(argOwner);
	    
		Expr[] argSeller = new Expr[2];
		argSeller[0]=seller;
		argSeller[1]=ctx.mkConst("auctionLimit", ctx.mkIntSort());;

	    seller=auctionapp.Seller.mkDecl().apply(argSeller);

		Expr exist= ctx.mkAnd(new BoolExpr[] { (BoolExpr) ctx.mkSetMembership(seller, auctionapp.Seller_set) ,
				   (BoolExpr) ctx.mkSetMembership(product, auctionapp.Product_set)});
		
		Sort[] arg = new Sort[2];
		arg[0]=ctx.mkUninterpretedSort(ctx.mkSymbol("Product"));
		arg[1]=ctx.mkUninterpretedSort(ctx.mkSymbol("SellerID"));
		
		Symbol[] namesb = new Symbol[2];
		namesb[0] =  ctx.mkSymbol("product");
		namesb[1] =  ctx.mkSymbol("seller");
		
		Expr owenrExist =ctx.mkImplies( (BoolExpr)ctx.mkSetMembership(owner, auctionapp.Owner_set),  (BoolExpr)exist);
	    
		Expr ownerForiegn = ctx.mkForall(arg, namesb, owenrExist, 1, null, null,
				null, null);
		
		System.out.println("Owner Foriegn key:"+ownerForiegn);
		
		return (BoolExpr) ownerForiegn;
	}

}
