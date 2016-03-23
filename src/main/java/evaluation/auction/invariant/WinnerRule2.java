package evaluation.auction.invariant;

import com.microsoft.z3.*;
import evaluation.auction.auctionapp;
import invariant.Invariant;

public class WinnerRule2 implements Invariant {
	
	String name;
	
	public WinnerRule2(String name) {
		this.name=name;
	}
	
	public WinnerRule2() {
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
	    
	    Expr auctionExist=ctx.mkSetMembership(auctionTuple, auctionapp.Auction_set);
	    	    		
	    Expr closedState= ctx.mkEq(argAuction[1], auctionapp.state.getConsts()[2]);
	    
		Expr[] argWinner = new Expr[2];
		argWinner[0]=auction;
		argWinner[1]=buyer;
		
	    Expr winnerTuple=auctionapp.Winner.mkDecl().apply(argWinner);
	    
	    Expr winner=(BoolExpr)ctx.mkSetMembership(winnerTuple, auctionapp.Winner_set);
	    	    
	    Expr winnerRule=ctx.mkImplies((BoolExpr) closedState, (BoolExpr) winner);
		
		Sort[] buyerarg = new Sort[2];
		buyerarg[0]=ctx.mkUninterpretedSort(ctx.mkSymbol("Buyer"));
		buyerarg[1]=ctx.mkIntSort();
		
		Symbol[] namesbuyer = new Symbol[2];
		namesbuyer[0] =  ctx.mkSymbol("buyer");
		namesbuyer[1] =  ctx.mkSymbol("bid");
		
        Expr[] argBid = new Expr[3];
        argBid[0]=auction;
        argBid[1]=buyer;
        argBid[2]=ctx.mkConst("bid", ctx.mkIntSort());
		
        Expr bidTuple =auctionapp.Bid.mkDecl().apply(argBid);
        Expr existBid =  (BoolExpr) ctx.mkSetMembership(bidTuple, auctionapp.Bid_set);
        
        Expr buyer2 = ctx.mkConst("buyer2", ctx.mkUninterpretedSort(ctx.mkSymbol("Buyer")));
        
		Sort[] buyerarg2 = new Sort[1];
		buyerarg2[0]=ctx.mkUninterpretedSort(ctx.mkSymbol("Buyer"));
		Symbol[] namesbuyer2 = new Symbol[1];
		namesbuyer2[0] =  ctx.mkSymbol("buyer2");
		
        Expr[] argBid2 = new Expr[3];
        argBid2[0]=auction;
        argBid2[1]=buyer2;
        argBid2[2]=ctx.mkConst("bid2", ctx.mkIntSort());
		
        Expr bidTuple2 =auctionapp.Bid.mkDecl().apply(argBid2);
        Expr existBid2 =   ctx.mkSetMembership(bidTuple2, auctionapp.Bid_set);
        
        BoolExpr bidCompare =ctx.mkGt((ArithExpr) argBid[2], (ArithExpr) argBid2[2]);
        
        Expr maxBid=ctx.mkImplies( ctx.mkAnd(new BoolExpr []{  (BoolExpr) existBid2  }),  bidCompare);
        	    
        Expr exprMaxBidder=ctx.mkForall(buyerarg2, namesbuyer2, maxBid, 1, null, null, null, null);
        		//ctx.mkNot(ctx.mkExists(buyerarg2, namesbuyer2, maxBid, 1, null, null,null, null));
        
        Expr existBuyer=  ctx.mkSetMembership(buyer, auctionapp.Buyer_set);
         
        Expr exprBidder= ctx.mkAnd(new BoolExpr[] {(BoolExpr) existBuyer,(BoolExpr) existBid, (BoolExpr) winner, (BoolExpr) exprMaxBidder });
         
        
		Expr existBids = ctx.mkExists(buyerarg, namesbuyer, exprBidder, 1, null, null,
				null, null);
	    
		Expr implication =ctx.mkImplies( (BoolExpr)auctionExist,  (BoolExpr) existBids );
	    
		Expr invariant = ctx.mkForall(arg, namesb, implication, 1, null, null,
				null, null);
		
		System.out.println(" Winner Constraint2:"+invariant);
		
		return (BoolExpr) invariant;
	}

}
