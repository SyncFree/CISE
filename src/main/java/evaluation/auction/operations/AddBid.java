package evaluation.auction.operations;

import application.Operation;
import com.microsoft.z3.*;
import evaluation.auction.auctionapp;

import java.util.ArrayList;
import java.util.List;

public class AddBid   implements Operation {
	
	private String name;
	private Expr auction;
	private Expr buyer;
	private IntExpr quantity;


	public AddBid(String name, Context ctx) {
		
		this.name=name;
		try {
			this.auction = ctx.mkConst("auction3", ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID")));
			this.buyer = ctx.mkConst("buyer3", ctx.mkUninterpretedSort(ctx.mkSymbol("Buyer")));
			this.quantity=(IntExpr) ctx.mkConst("bid3", ctx.mkIntSort());
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
		
		Sort[] args =  new Sort[1];		   	    
		args[0] =ctx.mkTupleSort(ctx.mkSymbol("mk_Auction_tuple"), // name of
				new Symbol[]{ctx.mkSymbol("first"), ctx.mkSymbol("second"), ctx.mkSymbol("third"), ctx.mkSymbol("forth")}, // names
				new Sort[]{ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID")), ctx.mkUninterpretedSort(ctx.mkSymbol("SellerID")), ctx.mkIntSort(),
						ctx.mkUninterpretedSort(ctx.mkSymbol("BuyerID"))});
		 
	    Symbol[] argsname = new Symbol[1];
	    argsname[0] =  ctx.mkSymbol("auction");
		
	    Expr[] argAuction = new Expr[2];
	    argAuction[0]=auction;
	    argAuction[1]=auctionapp.state.getConsts()[1];
	    	
	    Expr auctionTuple=auctionapp.Auction.mkDecl().apply(argAuction);
	    		   		 
		Expr auctionExist=ctx.mkSetMembership(auctionTuple, auctionapp.Auction_set);
		
	 	Expr[] argAuction2 = new Expr[2];
	 	argAuction2[0]= auction;;
	 	argAuction2[1]=auctionapp.state.getConsts()[2];

	 	Expr auctionTuple2=auctionapp.Auction.mkDecl().apply(argAuction2);
	 	
	 		 	
	 	Expr state=ctx.mkNot((BoolExpr) ctx.mkSetMembership(auctionTuple2, auctionapp.Auction_set));
		
		Expr buyerExist=ctx.mkSetMembership(buyer, auctionapp.Buyer_set);
		
		
		Sort[] arg = new Sort[1];
		arg[0]=auctionapp.Auction;

		Symbol[] namesb = new Symbol[1];
		namesb[0] =  ctx.mkSymbol("auction");
		
		Expr[] argLot = new Expr[4];
		argLot[0]=lot;
		argLot[1]=auction;
		argLot[2]=product;
		argLot[3]=ctx.mkConst("lotSize", ctx.mkIntSort());;

	    Expr lotTuple=auctionapp.Lot.mkDecl().apply(argLot);
	    
	    Expr lotExist= (BoolExpr) ctx.mkSetMembership(lotTuple, auctionapp.Lot_set);
			
	    Expr precondition=ctx.mkAnd(new BoolExpr[]{(BoolExpr) buyerExist, (BoolExpr) auctionExist, (BoolExpr) lotExist, (BoolExpr) state});
		
		return (BoolExpr) precondition;
	}

	@Override
	public Expr effect(Context ctx) throws Z3Exception {

	    Expr[] argBid = new Expr[3];
	    argBid[0]=auction;
	    argBid[1]=buyer;
	    argBid[2]=quantity;	
		Expr bidTuple=auctionapp.Bid.mkDecl().apply(argBid);
	    auctionapp.Bid_set= ctx.mkSetAdd(auctionapp.Bid_set, bidTuple);
	    return  auctionapp.Bid_set ;
	}

	@Override
	public Expr[] getArgs(String name) {
		Expr [] args={this.auction, this.buyer};
		return args;
	}

	@Override
	public BoolExpr getCondition(Context ctx, Operation op) throws Z3Exception {
		
		if (op.getName()=="CloseAuction") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.auction));
		}
		else if (op.getName()=="RemoveAuction") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.auction));
		}
		else if (op.getName()=="UnregisterBuyer") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.buyer));
		}
		else if (op.getName()=="RemoveFromLot") {
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
