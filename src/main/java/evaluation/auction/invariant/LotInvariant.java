package evaluation.auction.invariant;

import com.microsoft.z3.*;
import evaluation.auction.auctionapp;
import invariant.Invariant;

public class LotInvariant  implements Invariant   {
	
	String name;
	
	public LotInvariant(String name) {
		
		this.name=name;
	}
	
	public LotInvariant() {		
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public BoolExpr getInv(Context ctx) throws Z3Exception {
			
		Expr auction = ctx.mkConst("auction", ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID")));
		Expr product = ctx.mkConst("product", ctx.mkUninterpretedSort(ctx.mkSymbol("Product")));
		Expr lot = ctx.mkConst("lot", ctx.mkUninterpretedSort(ctx.mkSymbol("LotID")));
			
		Sort[] arg = new Sort[2];
		arg[0]=ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID"));
		arg[1]=ctx.mkUninterpretedSort(ctx.mkSymbol("LotID"));

		Symbol[] namesb = new Symbol[2];
		namesb[0] =  ctx.mkSymbol("auction");
		namesb[1] =  ctx.mkSymbol("lot");

	    
		Expr[] argAuctionLot = new Expr[2];
		argAuctionLot[0]=auction;
		argAuctionLot[1]=lot;
		
	    Expr auctionLotTuple=auctionapp.auctionLot.mkDecl().apply(argAuctionLot);
	    
	    Expr autionLotExist=ctx.mkSetMembership(auctionLotTuple,auctionapp.auctionLot_set);

		Expr[] argLot = new Expr[4];
		argLot[0]=lot;
		argLot[1]=auction;
		argLot[2]=product;
		argLot[3]= ctx.mkConst("lotSize", ctx.mkIntSort());
		
	    Expr lotTuple=auctionapp.Lot.mkDecl().apply(argLot);
	    
	    Expr existsLot=ctx.mkSetMembership(lotTuple, auctionapp.Lot_set);

		Expr[] argAuction = new Expr[2];
		argAuction[0]=auction;
		argAuction[1]=auctionapp.state.getConsts()[1];
	
	    Expr auctionTuple=auctionapp.Auction.mkDecl().apply(argAuction);
	    
	    Expr existsActiveAuction=ctx.mkSetMembership(auctionTuple,auctionapp.Auction_set);
	    
	    
		Expr[] argAuction2 = new Expr[2];
		argAuction2[0]=auction;
		argAuction2[1]=auctionapp.state.getConsts()[2];
	
	    Expr auctionTuple2=auctionapp.Auction.mkDecl().apply(argAuction2);
	    
	    Expr existsClosedAuction=ctx.mkSetMembership(auctionTuple2,auctionapp.Auction_set);
	    
	    Expr activeState=ctx.mkOr(new BoolExpr [] {(BoolExpr) existsActiveAuction,(BoolExpr) existsClosedAuction });
	    
	    Expr exprLot=ctx.mkAnd(new BoolExpr [] {(BoolExpr) activeState,(BoolExpr) existsLot });
	    	    
	    Expr autionLotOwnership =ctx.mkImplies( (BoolExpr)exprLot,(BoolExpr) autionLotExist );
	    	    
		Expr autionLot = ctx.mkForall(arg, namesb, autionLotOwnership, 1, null, null,null, null);
		
		System.out.println("Auction Lot Invariant:"+autionLot);
		
		return (BoolExpr) autionLot;
	}


}
