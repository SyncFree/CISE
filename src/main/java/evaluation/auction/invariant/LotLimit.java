package evaluation.auction.invariant;

import com.microsoft.z3.*;
import evaluation.auction.auctionapp;
import invariant.Invariant;

public class LotLimit implements Invariant  {
	
	String name;

	public LotLimit(String name) {
		this.name=name;
	}
	
	public LotLimit() {

	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public BoolExpr getInv(Context ctx) throws Z3Exception {
		
		Expr seller = ctx.mkConst("seller", ctx.mkUninterpretedSort(ctx.mkSymbol("SellerID")));
		Expr auction = ctx.mkConst("auction", ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID")));
		Expr product = ctx.mkConst("product", ctx.mkUninterpretedSort(ctx.mkSymbol("Product")));
		
		Expr lot = ctx.mkConst("lot", ctx.mkUninterpretedSort(ctx.mkSymbol("LotID")));
		
		Sort[] arg = new Sort[2];
		arg[0]=ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID"));
		arg[1]=ctx.mkUninterpretedSort(ctx.mkSymbol("SellerID"));

		Symbol[] namesb = new Symbol[2];
		namesb[0] =  ctx.mkSymbol("auction");
		namesb[1] =  ctx.mkSymbol("seller");
	    
		Expr[] argLot = new Expr[4];
		argLot[0]=lot;
		argLot[1]=auction;
		argLot[2]=product;
		argLot[3]=ctx.mkConst("lotSize", ctx.mkIntSort());;

	    Expr lotTuple=auctionapp.Lot.mkDecl().apply(argLot);
	    
	    Expr lotExist= ctx.mkSetMembership(lotTuple, auctionapp.Lot_set);
	    
		Expr[] argSeller = new Expr[2];
		argSeller[0]=seller;
		argSeller[1]=ctx.mkConst("auctionLimit", ctx.mkIntSort());;

	    Expr sellerTuple=auctionapp.Seller.mkDecl().apply(argSeller);
	    
	    Expr limit=ctx.mkLe((ArithExpr) argLot[3], (ArithExpr) argSeller[1]);
	    
		Expr[] argPromoter = new Expr[2];
		argPromoter[0]=auction;
		argPromoter[1]=seller;

	    Expr promoter = auctionapp.Promoter.mkDecl().apply(argPromoter);
			    
		Expr[] argAuction = new Expr[2];
		argAuction[0]=auction;
		argAuction[1]=auctionapp.state.getConsts()[2];

	    Expr auctionTuple = auctionapp.Auction.mkDecl().apply(argAuction);
	    	    	    
	    Expr validLot= ctx.mkAnd(new BoolExpr []{(BoolExpr)ctx.mkSetMembership(promoter, auctionapp.Promoter_set), (BoolExpr) lotExist  ,
	    		(BoolExpr)ctx.mkSetMembership(sellerTuple, auctionapp.Seller_set)});
	    
	    Expr boundedImplication =ctx.mkImplies( (BoolExpr)validLot, (BoolExpr) limit );
	  		
		Expr invariant2 = ctx.mkForall(arg, namesb, boundedImplication, 1, null, null,null, null);
		
		return (BoolExpr) invariant2;
	}

}
