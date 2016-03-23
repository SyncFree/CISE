package evaluation.auction.invariant;

import com.microsoft.z3.*;
import evaluation.auction.auctionapp;
import invariant.Invariant;
 
public class WinnerRule1 implements Invariant {

	String name;
	
	public WinnerRule1(String name) {
		this.name=name;
	}
	
	public WinnerRule1() {
		
	}

	@Override
	public String getName() {

		return this.name;
	}

	@Override
	public BoolExpr getInv(Context ctx) throws Z3Exception {
		
		Expr auction = ctx.mkConst("auction", ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID")));
		Expr buyer = ctx.mkConst("buyer", ctx.mkUninterpretedSort(ctx.mkSymbol("Buyer")));
		
		Sort[] arg = new Sort[1];
		arg[0]=ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID"));

		Symbol[] namesb = new Symbol[1];
		namesb[0] =  ctx.mkSymbol("auction");
	    
		Expr[] argAuction = new Expr[2];
		argAuction[0]=auction;
		argAuction[1]=auctionapp.state.getConsts()[2];
		
	    Expr auctionTuple=auctionapp.Auction.mkDecl().apply(argAuction);

	    Expr closedAuctionExist=ctx.mkSetMembership(auctionTuple, auctionapp.Auction_set);
	    
		Expr[] argWinner = new Expr[2];
		argWinner[0]=auction;
		argWinner[1]=buyer;

	    Expr winner=auctionapp.Winner.mkDecl().apply(argWinner);
	    
	    Expr ownership =ctx.mkImplies( (BoolExpr)ctx.mkSetMembership(winner, auctionapp.Winner_set),(BoolExpr) closedAuctionExist );
	    	    
		Expr winnerConst = ctx.mkForall(arg, namesb, ownership, 1, null, null,
				null, null);
		
		System.out.println("Winner Constraint1:"+winnerConst);
		
		return (BoolExpr) winnerConst;
	}

}
