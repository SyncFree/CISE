package evaluation.auction.operations;

import application.Operation;
import com.microsoft.z3.*;
import evaluation.auction.auctionapp;
import evaluation.auction.productObj;

import java.util.ArrayList;
import java.util.List;

public class RemoveFromLot  implements Operation {
		
	private String name;
	private Expr product;
	private Expr auction;
	private Expr stockIndex;
	private Expr lot;

	public RemoveFromLot(String name, Context ctx) {
		this.name=name;
		try {
			productObj p=new productObj (ctx);
	        this.product=p.getProduct();;
	        this.stockIndex=p.getIndex();
			this.auction = ctx.mkConst("auction", ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID")));
			this.lot= ctx.mkConst("lot", ctx.mkUninterpretedSort(ctx.mkSymbol("LotID")));
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
		
		Expr[] argAuction = new Expr[2];
		argAuction[0]=auction;
		argAuction[1]= auctionapp.state.getConsts()[1];

	    Expr auctionTuple=auctionapp.Auction.mkDecl().apply(argAuction);
	    
	    BoolExpr existsActiveAuction=  (BoolExpr) ctx.mkSetMembership(auctionTuple, auctionapp.Auction_set);
	    		
		Expr[] argAuction2 = new Expr[2];
		argAuction2[0]=auction;
		argAuction2[1]= auctionapp.state.getConsts()[2];

	    Expr auctionTuple2=auctionapp.Auction.mkDecl().apply(argAuction2);
	    
	    BoolExpr existsClosedAuction=  (BoolExpr) ctx.mkSetMembership(auctionTuple2, auctionapp.Auction_set);

	    Expr precondition=ctx.mkNot(ctx.mkOr(new BoolExpr[]{existsClosedAuction, existsActiveAuction}));
	    		 
	    return (BoolExpr) precondition;
	}

	@Override
	public Expr effect(Context ctx) throws Z3Exception {
		
		IntExpr lotSize=(IntExpr) ctx.mkConst("lotSize", ctx.mkIntSort());
		Expr[] lotArg = new Expr[4];
		lotArg[0]=lot;
		lotArg[1]=auction;
		lotArg[2]=product;
		lotArg[3]=lotSize;
			
	     Expr lotTuple =auctionapp.Lot.mkDecl().apply(lotArg);
	
	     auctionapp.Lot_set = ctx.mkSetDel(auctionapp.Lot_set, lotTuple);
	     
	     Expr stock= ctx.mkSelect(auctionapp.stockArray, stockIndex);
	      
	    stock=(IntExpr) ctx.mkITE(ctx.mkGe((ArithExpr) lotSize, ctx.mkInt("1")), ctx.mkAdd(new ArithExpr[]{(ArithExpr) stock, (ArithExpr) lotSize}),
				ctx.mkAdd(new ArithExpr[]{(ArithExpr) stock, ctx.mkMul(new ArithExpr[]{lotSize, ctx.mkInt("-1")})}));

		auctionapp.stockArray=ctx.mkStore(auctionapp.stockArray, stockIndex, stock);
    
		 return  auctionapp.Lot_set;
	}

	@Override
	public Expr [] getArgs(String name) {
		Expr args[]={this.auction};
		return args;
	}

	@Override
	public BoolExpr getCondition(Context ctx, Operation op) throws Z3Exception {
		
		if (op.getName()=="StartAuction") {
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
