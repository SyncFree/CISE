package evaluation.auction.invariant;

import com.microsoft.z3.*;
import evaluation.auction.auctionapp;
import invariant.Invariant;

public class ForeignKeyWinner implements Invariant {

	String name;
	
	public ForeignKeyWinner(String name) {
		
        this.name=name;
	}

	public ForeignKeyWinner() {
	}

	@Override
	public String getName() {

		return this.name;
	}

	@Override
	public BoolExpr getInv(Context ctx) throws Z3Exception {
		
		Expr auction = ctx.mkConst("auction", ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID")));
		Expr buyer = ctx.mkConst("buyer", ctx.mkUninterpretedSort(ctx.mkSymbol("Buyer")));
		
		Sort[] arg = new Sort[2];
		arg[0]=ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID"));
		arg[1]=ctx.mkUninterpretedSort(ctx.mkSymbol("Buyer"));
		Symbol[] namesb = new Symbol[2];
		namesb[0] =  ctx.mkSymbol("auction");
		namesb[1] =  ctx.mkSymbol("buyer");
		
		Expr[] argWinner = new Expr[2];
		argWinner[0]=auction;
		argWinner[1]=buyer;

	    Expr winner=auctionapp.Winner.mkDecl().apply(argWinner);
	    	    
	    Expr winnerExist =ctx.mkImplies( (BoolExpr)ctx.mkSetMembership(winner, auctionapp.Winner_set), (BoolExpr) ctx.mkSetMembership(buyer, auctionapp.Buyer_set));
	    
		Expr winnerForiegn = ctx.mkForall(arg, namesb, winnerExist, 1, null, null, null, null);
		
		System.out.println("Winner Foriegn key:"+winnerForiegn);
			
		return (BoolExpr) winnerForiegn;
	}

}
