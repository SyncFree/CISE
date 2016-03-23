package evaluation.auction.invariant;

import com.microsoft.z3.*;
import evaluation.auction.auctionapp;
import invariant.Invariant;

public class StateInvariant implements Invariant {
	
	String name;
	
	public StateInvariant(String name ) {
		
		this.name=name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public BoolExpr getInv(Context ctx) throws Z3Exception {
		
		Expr auction = ctx.mkConst("auction", ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID")));
		Expr anotherAuction = ctx.mkConst("auction2", ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID")));
	
		Sort[] arg = new Sort[1];
		arg[0]=ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID"));
		
		Symbol[] namesb = new Symbol[1];
		namesb[0] =  ctx.mkSymbol("auction");
	   
	    
		Expr[] argClosedAuction = new Expr[2];
		argClosedAuction[0]=auction;
		argClosedAuction[1]= auctionapp.state.getConsts()[2];
	
	    Expr closedAuctionTuple=auctionapp.Auction.mkDecl().apply(argClosedAuction);
			    
		Expr[] argPreparedAuction = new Expr[2];
		argPreparedAuction[0]=anotherAuction;
		argPreparedAuction[1]= auctionapp.state.getConsts()[0];
	
	    Expr preparedAuctionTuple=auctionapp.Auction.mkDecl().apply(argPreparedAuction);
	    
		Expr[] argActiveAuction = new Expr[2];
		argActiveAuction[0]=auction;
		argActiveAuction[1]=auctionapp.state.getConsts()[1];
		
	    Expr activeAuctionTuple=auctionapp.Auction.mkDecl().apply(argActiveAuction);
		
	    
		Expr[] argActiveAuction2 = new Expr[2];
		argActiveAuction2[0]=anotherAuction;
		argActiveAuction2[1]=auctionapp.state.getConsts()[1];
		
	    Expr activeAuctionTuple2=auctionapp.Auction.mkDecl().apply(argActiveAuction2);
	    
  
	    Expr lowerState=ctx.mkAnd(new BoolExpr [] {ctx.mkEq(anotherAuction, auction), (BoolExpr) ctx.mkSetMembership(closedAuctionTuple, auctionapp.Auction_set)  });
	   
		Expr noLowerState =ctx.mkAnd(new BoolExpr[] {(BoolExpr) ctx.mkSetMembership(preparedAuctionTuple, auctionapp.Auction_set),
				(BoolExpr) ctx.mkSetMembership(activeAuctionTuple2, auctionapp.Auction_set)});
			

		Expr existClosedState= ctx.mkImplies(  (BoolExpr)lowerState , (BoolExpr)ctx.mkNot((BoolExpr) noLowerState));

	    	   
		Expr activeAuction=ctx.mkAnd(new BoolExpr [] {ctx.mkEq(anotherAuction, auction), (BoolExpr) ctx.mkSetMembership(activeAuctionTuple, auctionapp.Auction_set)});
		
	 //   Expr noPreparedAuction =ctx.mkNot( ctx.mkExists(arg, namesb, preparedAuction, 1, null, null,
	//		null, null));	
			
	    Expr existActiveState= ctx.mkImplies(  (BoolExpr) activeAuction, (BoolExpr)ctx.mkNot((BoolExpr) ctx.mkSetMembership(preparedAuctionTuple, auctionapp.Auction_set)));
	    
		Sort[] argAll = new Sort[2];
		argAll[0]=ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID"));
		argAll[1]=ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID"));
		
		Symbol[] namesbAll = new Symbol[2];
		namesbAll[0] =  ctx.mkSymbol("auction");
		namesbAll[1] =  ctx.mkSymbol("auction2");
					   		    
		Expr stateChange = ctx.mkForall(argAll, namesbAll, ctx.mkAnd(new BoolExpr [] {(BoolExpr) existActiveState, (BoolExpr) existClosedState}), 1, null, null,
			null, null);	
		
		System.out.println("Auction State Invariant"+stateChange);
					
		return (BoolExpr) stateChange;
	}

}
