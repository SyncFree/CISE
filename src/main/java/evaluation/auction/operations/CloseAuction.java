package evaluation.auction.operations;

import application.Operation;
import com.microsoft.z3.*;
import evaluation.auction.auctionapp;

import java.util.ArrayList;
import java.util.List;

public class CloseAuction  implements Operation {
	
	private String name;
	private Expr auction;
	private Expr buyer;

	public CloseAuction(String name, Context ctx) {
		this.name=name;
		try {
			this.auction= ctx.mkConst("auction", ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID")));
			this.buyer = ctx.mkConst("buyer", ctx.mkUninterpretedSort(ctx.mkSymbol("Buyer")));
		} catch (Z3Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public BoolExpr precondition(Context ctx) throws Z3Exception {
	
		
		Sort[] buyerarg = new Sort[1];
		buyerarg[0]=ctx.mkUninterpretedSort(ctx.mkSymbol("Buyer"));
		Symbol[] namesb = new Symbol[1];
		namesb[0] =  ctx.mkSymbol("buyer2");
		
        Expr[] argBid = new Expr[3];
        argBid[0]=auction;
        argBid[1]=buyer;
        argBid[2]=ctx.mkConst("bid", ctx.mkIntSort());
		
        Expr bidTuple =auctionapp.Bid.mkDecl().apply(argBid);
        Expr existBid =  (BoolExpr) ctx.mkSetMembership(bidTuple, auctionapp.Bid_set);
      
	    Expr bidPrice=argBid[2];
        
        Expr buyer2 = ctx.mkConst("buyer2", ctx.mkUninterpretedSort(ctx.mkSymbol("Buyer")));
        
		Sort[] buyerarg2 = new Sort[1];
		buyerarg2[0]=auctionapp.Bid;
		Symbol[] namesb2 = new Symbol[1];
		namesb2[0] =  ctx.mkSymbol("bidTuple");
		
        Expr[] argBid2 = new Expr[3];
        argBid2[0]=auction;
        argBid2[1]=buyer2;
        argBid2[2]=ctx.mkConst("bid2", ctx.mkIntSort());
		
        Expr bidTuple2 =auctionapp.Bid.mkDecl().apply(argBid2);
        Expr existBid2 =   ctx.mkSetMembership(bidTuple2, auctionapp.Bid_set);
        
	    Expr bidPrice2=argBid2[2];
	    
        BoolExpr bidCompare =ctx.mkLt((ArithExpr) bidPrice2, (ArithExpr) bidPrice);
        Expr existBuyer2 =ctx.mkSetMembership(buyer2, auctionapp.Buyer_set);
        
        Expr noMoreWinner=ctx.mkImplies((BoolExpr) existBid2, bidCompare);
        
        Expr existBuyer1 =ctx.mkSetMembership(buyer, auctionapp.Buyer_set);
    
		Expr existBids =ctx.mkForall(buyerarg, namesb, noMoreWinner, 1, null, null,
				null, null);
//ctx.mkNot(ctx.mkExists(buyerarg, namesb, noMoreWinner, 1, null, null,
	//	null, null));	
		
		Expr winner= ctx.mkAnd(new BoolExpr[]{(BoolExpr) existBuyer1, (BoolExpr) existBids, (BoolExpr) existBid});
		
		Expr state = ctx.mkConst("state", auctionapp.state);
		
		Expr[] argAuction = new Expr[2];
		argAuction[0]=auction;
		argAuction[1]=auctionapp.state.getConsts()[1];

	    Expr auctionTuple=auctionapp.Auction.mkDecl().apply(argAuction);

	    Expr existAuction=ctx.mkSetMembership(auctionTuple, auctionapp.Auction_set);
	    
	    Expr activeAuctionExist=ctx.mkAnd(new BoolExpr[]{(BoolExpr) existAuction, ctx.mkEq(state, auctionapp.state.getConsts()[1])});

        
		Expr product = ctx.mkConst("product", ctx.mkUninterpretedSort(ctx.mkSymbol("Product")));
		Expr lot = ctx.mkConst("lot", ctx.mkUninterpretedSort(ctx.mkSymbol("LotID")));
			
		Sort[] argl = new Sort[1];
		argl[0]=ctx.mkUninterpretedSort(ctx.mkSymbol("LotID"));

		Symbol[] namesl = new Symbol[1];
		namesl[0] =  ctx.mkSymbol("lot");

	    
		Expr[] argAuctionLot = new Expr[2];
		argAuctionLot[0]=auction;
		argAuctionLot[1]=lot;
		
	    Expr auctionLotTuple=auctionapp.auctionLot.mkDecl().apply(argAuctionLot);
	    
	    Expr autionLotExist=ctx.mkSetMembership(auctionLotTuple, auctionapp.auctionLot_set);

		Expr[] argLot = new Expr[4];
		argLot[0]=lot;
		argLot[1]=auction;
		argLot[2]=product;
		argLot[3]= ctx.mkConst("lotSize", ctx.mkIntSort());
		
	    Expr lotTuple=auctionapp.Lot.mkDecl().apply(argLot);
	    
	    Expr existsLot=ctx.mkSetMembership(lotTuple, auctionapp.Lot_set);
	    
	    Expr autionLotOwnership =ctx.mkImplies((BoolExpr) existsLot, (BoolExpr) autionLotExist);

	    Expr lotClosed=ctx.mkExists(argl, namesl, autionLotOwnership, 1, null, null, null, null);
	    
	    BoolExpr precondition=ctx.mkAnd(new BoolExpr[]{(BoolExpr) winner, (BoolExpr) existAuction});
	    
	    return (BoolExpr) precondition;
	}

	@Override
	public Expr effect(Context ctx) throws Z3Exception {
		

		Expr[] argAuction = new Expr[2];
		argAuction[0]=auction;
		argAuction[1]=auctionapp.state.getConsts()[2];

	    Expr auctionTuple=auctionapp.Auction.mkDecl().apply(argAuction);
	    auctionapp.Auction_set= ctx.mkSetAdd(auctionapp.Auction_set, auctionTuple);
	    
	  
	    Expr[] argAuction2 = new Expr[2];
		argAuction2[0]=auction;
		argAuction2[1]= auctionapp.state.getConsts()[1];
	    
	    Expr auctionTuple2=auctionapp.Auction.mkDecl().apply(argAuction2);
	    auctionapp.Auction_set= ctx.mkSetDel(auctionapp.Auction_set, auctionTuple2);
	        
	    Expr[] argWinner = new Expr[2];
	    argWinner[0]=auction;
	    argWinner[1]= buyer;
	    
	    Expr winnerTuple=auctionapp.Winner.mkDecl().apply(argWinner);
	    auctionapp.Winner_set= ctx.mkSetAdd(auctionapp.Winner_set, winnerTuple);
	    
	    return  auctionapp.Auction_set ;
	}

	@Override
	public Expr [] getArgs(String name) {
		Expr args[]= {this.auction, this.buyer};
		return  args;
	}

	@Override
	public BoolExpr getCondition(Context ctx, Operation op) throws Z3Exception {
		
		if (op.getName()=="CloseAuction") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.auction));
		}
		else if (op.getName()=="RemoveBid") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.auction));
		}
		
		else if (op.getName()=="RemoveAuction") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.auction));
		}
		
		else if (op.getName()=="UnregisterBuyer") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.buyer));
		}
		else if (op.getName()=="AddBid") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.auction));
		}

		else return ctx.mkTrue();
	}

	@Override
	public List<Operation> concurrentOps(Context ctx) {
		Operation o1=new AddBid("AddBid",ctx);
		Operation o2=new AddProduct("AddProduct",ctx);
		Operation o3=new AddToLot("AddToLot",ctx);
		Operation o4=new CloseAuction("CloseAuction",ctx);
		Operation o5=new CreateAuction("CreateAuction",ctx);
		Operation o6=new RegisterBuyer("RegisterBuyer",ctx);
		Operation o7=new RegisterSeller("RegisterSeller",ctx);
		Operation o8=new RemoveAuction("RemoveAuction",ctx);
		Operation o9=new RemoveBid("RemoveBid",ctx);
		Operation o10=new RemoveFromLot("RemoveFromLot",ctx);
		Operation o11=new RemoveProduct("RemoveProduct",ctx);
		Operation o12=new StartAuction("StartAuction",ctx);
		Operation o13=new UnregisterBuyer("UnregisterBuyer",ctx);
		Operation o14=new UnregisterSeller("UnregisterSeller",ctx);
		
		List<Operation> op=new ArrayList<Operation>();
		op.add(o1);
		op.add(o2);
		op.add(o3);
		op.add(o4);
		op.add(o5);
		op.add(o6);	
		op.add(o7);
		op.add(o8);
		op.add(o9);
		op.add(o10);
		op.add(o11);
		op.add(o12);	
		op.add(o13);
		op.add(o14);
		
		return op;
	}

	@Override
	public void putReplicaIndex(Context ctx, int replica) throws Z3Exception {
		
	}

	@Override
	public int getReplicaIndex() {
		return 0;
	}

	@Override
	public Expr getConditions(Context ctx, Operation op) throws Z3Exception {
		return null;
	}

	@Override
	public void putConcurrentOp(Context ctx, Operation op) throws Z3Exception {

	}

}
