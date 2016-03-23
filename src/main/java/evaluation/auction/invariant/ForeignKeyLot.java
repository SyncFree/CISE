package evaluation.auction.invariant;

import com.microsoft.z3.*;
import evaluation.auction.auctionapp;
import invariant.Invariant;

public class ForeignKeyLot implements Invariant  {

	String name;
	
	public ForeignKeyLot(String name) {
		
		this.name=name;
	}
	
	public ForeignKeyLot() {
		
	}


	@Override
	public String getName() {
		
		return this.name;
	}

	@Override
	public BoolExpr getInv(Context ctx) throws Z3Exception {
		
		Expr seller = ctx.mkConst("seller", ctx.mkUninterpretedSort(ctx.mkSymbol("SellerID")));
		Expr auction = ctx.mkConst("auction", ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID")));
		Expr lot = ctx.mkConst("lot", ctx.mkUninterpretedSort(ctx.mkSymbol("LotID")));
		Expr product = ctx.mkConst("product", ctx.mkUninterpretedSort(ctx.mkSymbol("Product")));
	
		Expr[] argLot = new Expr[4];
		argLot[0]=lot;
		argLot[1]=auction;
		argLot[2]=product;
		argLot[3]=ctx.mkConst("lotSize", ctx.mkIntSort());

	    Expr lotTuple=auctionapp.Lot.mkDecl().apply(argLot);
	    
		Expr[] argOwner = new Expr[2];
		argOwner[0]= product;
		argOwner[1]=seller;

	    Expr Owner=auctionapp.Owner.mkDecl().apply(argOwner);
  
	    
		Expr[] argPromoter = new Expr[2];
		argPromoter[0]= auction;
		argPromoter[1]=seller;

	    Expr Promoter=auctionapp.Promoter.mkDecl().apply(argPromoter);
	    
	 	Expr[] argSeller = new Expr[2];
		argSeller[0]= seller;
		argSeller[1]=ctx.mkConst("auctionLimit", ctx.mkIntSort());

	    Expr Seller=auctionapp.Seller.mkDecl().apply(argSeller);
	    
	    Expr limit=ctx.mkLe((ArithExpr) argLot[3], (ArithExpr) argSeller[1]);
		
		
		Expr lotForiegn =  ctx.mkAnd( new BoolExpr[]{(BoolExpr) ctx.mkSetMembership(seller, auctionapp.SellerID_set), (BoolExpr) ctx.mkSetMembership(Seller, auctionapp.Seller_set),
				(BoolExpr) ctx.mkSetMembership(Owner, auctionapp.Owner_set),
				(BoolExpr) ctx.mkSetMembership(Promoter, auctionapp.Promoter_set), (BoolExpr) limit});
		
	    
		   
	    Sort[] arg = new Sort[1];
	    arg[0]=ctx.mkUninterpretedSort(ctx.mkSymbol("SellerID"));
	    

		Symbol[] names = new Symbol[1];
    	names[0] =  ctx.mkSymbol("seller");

		
		Expr sellerForiegn = ctx.mkExists(arg, names, lotForiegn, 1, null, null, null, null);
	

	    Expr lotOwnership =ctx.mkImplies( (BoolExpr)ctx.mkSetMembership(lotTuple, auctionapp.Lot_set), (BoolExpr) sellerForiegn );
	    	
	    
		Sort[] arg2 = new Sort[4];
    	arg2[0]=ctx.mkUninterpretedSort(ctx.mkSymbol("LotID"));
    	arg2[1]=ctx.mkUninterpretedSort(ctx.mkSymbol("Product"));
    	arg2[2]=ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID"));
    	arg2[3]=ctx.mkIntSort();

		Symbol[] namesb = new Symbol[4];
    	namesb[0] =  ctx.mkSymbol("lot");
    	namesb[1] =  ctx.mkSymbol("product");
    	namesb[2] =  ctx.mkSymbol("auction");
    	namesb[3] =  ctx.mkSymbol("lotSize");

	    
		Expr foriegn = ctx.mkForall(arg2, namesb, lotOwnership, 1, null, null, null, null);
		
	   System.out.println("Lot Foriegn key:"+foriegn);

		return (BoolExpr) foriegn;
	}

}
