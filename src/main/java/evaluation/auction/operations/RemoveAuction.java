package evaluation.auction.operations;

import application.Operation;
import com.microsoft.z3.*;
import evaluation.auction.auctionapp;

import java.util.ArrayList;
import java.util.List;

public class RemoveAuction  implements Operation  {
	
	private String name;
	private Expr auction;
	
	public RemoveAuction(String name, Context ctx) {
		this.name=name;
		try {
			this.auction = ctx.mkConst("auction", ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID")));
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
		
		Expr product = ctx.mkConst("product", ctx.mkUninterpretedSort(ctx.mkSymbol("Product")));

		Expr lot = ctx.mkConst("lot", ctx.mkUninterpretedSort(ctx.mkSymbol("LotID")));

		Sort[] productarg = new Sort[1];
		productarg[0]=ctx.mkUninterpretedSort(ctx.mkSymbol("Product"));
		Symbol[] namesa = new Symbol[1];
		namesa[0] =  ctx.mkSymbol("product");
		
        Expr[] argLot = new Expr[4];
        argLot[0]=lot;
        argLot[1]=auction;
        argLot[2]=product;
        argLot[3]=ctx.mkConst("lotSize", ctx.mkIntSort());
		
        Expr lotTuple =auctionapp.Lot.mkDecl().apply(argLot);

	    Expr lotSizeBound= ctx.mkEq((ArithExpr) argLot[3], ctx.mkInt("0")) ;

		Expr NoExistLots = ctx.mkForall(productarg, namesa, lotSizeBound, 1, null, null,
				null, null);
		
		Sort[] lotArg = new Sort[1];
		lotArg[0]=ctx.mkUninterpretedSort(ctx.mkSymbol("Lot"));
		Symbol[] nameslot = new Symbol[1];
		nameslot[0] =  ctx.mkSymbol("lot");
		 
        Expr noLot = ctx.mkNot((BoolExpr) ctx.mkSetMembership(lotTuple, auctionapp.Lot_set));
		Expr existLot = ctx.mkForall(lotArg, nameslot, noLot, 1, null, null,
				null, null);

		Expr buyer = ctx.mkConst("buyer", ctx.mkUninterpretedSort(ctx.mkSymbol("Buyer")));
		
		Sort[] buyerarg = new Sort[1];
		buyerarg[0]=ctx.mkUninterpretedSort(ctx.mkSymbol("Buyer"));
		Symbol[] namesb = new Symbol[1];
		namesb[0] =  ctx.mkSymbol("buyer");

		Expr[] argBid = new Expr[3];
		argBid[0]=auction;
		argBid[1]=buyer;
		argBid[2]=ctx.mkConst("bid", ctx.mkIntSort());

	    Expr bidTuple=auctionapp.Bid.mkDecl().apply(argBid);
	    Expr NoBidder =ctx.mkNot((BoolExpr) ctx.mkSetMembership(bidTuple, auctionapp.Bid_set));
	        
		Expr NoExistBid = ctx.mkForall(buyerarg, namesb, NoBidder, 1, null, null,
				null, null);
		
		Expr[] argAuction = new Expr[2];
		argAuction[0]=auction;
		argAuction[1]=auctionapp.state.getConsts()[2];
	
	    Expr auctionTuple=auctionapp.Auction.mkDecl().apply(argAuction);
	    
	    Expr closedAuctionExist=ctx.mkSetMembership(auctionTuple, auctionapp.Auction_set);
	    
		
	    BoolExpr precondition=ctx.mkAnd(new BoolExpr[]{(BoolExpr) existLot, (BoolExpr) NoExistBid, (BoolExpr) ctx.mkNot((BoolExpr) closedAuctionExist)});
	    
	    return (BoolExpr) precondition;
	}

	@Override
	public Expr effect(Context ctx) throws Z3Exception {
		
		auctionapp.AuctionID_set= ctx.mkSetDel(auctionapp.AuctionID_set, auction);
		
		Expr seller = ctx.mkConst("seller", ctx.mkUninterpretedSort(ctx.mkSymbol("SellerID")));
		
		Expr[] argAuction = new Expr[2];
		argAuction[0]=auction;
		argAuction[1]=ctx.mkConst("state", auctionapp.state);

	    Expr auctionTuple=auctionapp.Auction.mkDecl().apply(argAuction);
	    auctionapp.Auction_set= ctx.mkSetDel(auctionapp.Auction_set, auctionTuple);
	    
		Expr[] argPromoter = new Expr[2];
		argPromoter[0]=auction;
		argPromoter[1]=seller;

	    Expr promoterTuple=auctionapp.Promoter.mkDecl().apply(argPromoter);
	    auctionapp.Promoter_set= ctx.mkSetDel(auctionapp.Promoter_set, promoterTuple);
	    
	    return  auctionapp.Auction_set ;
	}

	@Override
	public Expr [] getArgs(String name) { 
		Expr args[]={this.auction};
		return args;
	}

	@Override
	public BoolExpr getCondition(Context ctx, Operation op) throws Z3Exception {
		if (op.getName()=="AddBid") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.auction));
		}
		else if (op.getName()=="AddToLot") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.auction));
		}
		else if (op.getName()=="CloseAuction") {
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putConcurrentOp(Context ctx, Operation op) throws Z3Exception {

	}

}
