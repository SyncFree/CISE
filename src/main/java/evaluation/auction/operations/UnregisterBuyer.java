package evaluation.auction.operations;

import application.Operation;
import com.microsoft.z3.*;
import evaluation.auction.auctionapp;

import java.util.ArrayList;
import java.util.List;

public class UnregisterBuyer  implements Operation {

	private String name;
	private Expr buyer;
	
	public UnregisterBuyer(String name, Context ctx) {
		this.name=name;
		try {
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
		
		Expr auction = ctx.mkConst("auction", ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID")));

		Sort[] auctionarg = new Sort[1];
		auctionarg[0]=ctx.mkUninterpretedSort(ctx.mkSymbol("Auction"));
		Symbol[] namesa = new Symbol[1];
		namesa[0] =  ctx.mkSymbol("a");
		
        Expr[] argBid = new Expr[3];
        argBid[0]=auction;
        argBid[1]=buyer;
        argBid[2]=(IntExpr) ctx.mkConst("bid", ctx.mkIntSort());
		
        Expr bidTuple =auctionapp.Bid.mkDecl().apply(argBid);
        Expr noBid = ctx.mkNot((BoolExpr) ctx.mkSetMembership(bidTuple, auctionapp.Bid_set));
		Expr existBid = ctx.mkForall(auctionarg, namesa, noBid, 1, null, null,
				null, null);
		
        Expr[] argWinner = new Expr[2];
        argWinner[0]=auction;
        argWinner[1]=buyer;
     
        Expr winner =auctionapp.Winner.mkDecl().apply(argWinner);
        Expr noWinner = ctx.mkNot((BoolExpr) ctx.mkSetMembership(winner, auctionapp.Winner_set));
		Expr existWinner = ctx.mkForall(auctionarg, namesa, noWinner, 1, null, null,
				null, null);
		
		Expr precondition= ctx.mkAnd(new BoolExpr[]{(BoolExpr) existBid, (BoolExpr) existWinner});
		
  	    return (BoolExpr) precondition;
	}

	@Override
	public Expr effect(Context ctx) throws Z3Exception {
		auctionapp.Buyer_set= ctx.mkSetDel(auctionapp.Buyer_set, buyer);
		return auctionapp.Buyer_set;
	}

	@Override
	public Expr [] getArgs(String name) {
		Expr args[]={this.buyer};
		return  args;
	}

	@Override
	public BoolExpr getCondition(Context ctx, Operation op) throws Z3Exception {
		
		if (op.getName()=="AddBid") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[1], this.buyer));
		}
		else if (op.getName()=="CloseAuction") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[1], this.buyer));
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
