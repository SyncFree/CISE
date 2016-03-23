package evaluation.auction.operations;

import application.Operation;
import com.microsoft.z3.*;
import evaluation.auction.auctionapp;

import java.util.ArrayList;
import java.util.List;

public class CreateAuction  implements Operation  {

	private String name;
	private Expr seller;
	private Expr auction;

	
	public CreateAuction(String name, Context ctx) {
		this.name=name;
		try {
			this.seller = ctx.mkConst("seller", ctx.mkUninterpretedSort(ctx.mkSymbol("SellerID")));
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
		
		Sort[] args =  new Sort[1];		   	    
		args[0] =ctx.mkIntSort();
		 
	    Symbol[] argsname = new Symbol[1];
	    argsname[0] =  ctx.mkSymbol("auctionLimit");
		
		Expr[] argSeller = new Expr[2];
		argSeller[0]=seller;
		argSeller[1]=(IntExpr) ctx.mkConst("auctionLimit", ctx.mkIntSort());
			
		Expr sellerTuple=auctionapp.Seller.mkDecl().apply(argSeller);
			
		Expr existSeller=ctx.mkSetMembership(sellerTuple, auctionapp.Seller_set);
				
		Expr[] argAuction = new Expr[2];
		argAuction[0]=auction;
		argAuction[1]=auctionapp.state.getConsts()[1];
	
	    Expr auctionTuple=auctionapp.Auction.mkDecl().apply(argAuction);
	    
		Expr[] argAuction2 = new Expr[2];
		argAuction2[0]=auction;
		argAuction2[1]=auctionapp.state.getConsts()[2];

	    Expr auctionTuple2=auctionapp.Auction.mkDecl().apply(argAuction2);
	
	    	
	   Expr noActive =ctx.mkNot((BoolExpr) ctx.mkSetMembership(auctionTuple, auctionapp.Auction_set));
	        
	   Expr noclosed =ctx.mkNot((BoolExpr) ctx.mkSetMembership(auctionTuple2, auctionapp.Auction_set));
 
	   Expr noHigherState=ctx.mkAnd(new BoolExpr[]{(BoolExpr) noActive, (BoolExpr) noclosed});
		    

	   Expr precondition=ctx.mkAnd(new BoolExpr[]{(BoolExpr) existSeller, (BoolExpr) noHigherState});
		
	    return (BoolExpr) precondition;
		
	}

	@Override
	public Expr effect(Context ctx) throws Z3Exception {
		
		auctionapp.AuctionID_set= ctx.mkSetAdd(auctionapp.AuctionID_set, auction);
		
		Expr[] argAuction = new Expr[2];
		argAuction[0]=auction;
		argAuction[1]=auctionapp.state.getConsts()[0];
		
	    Expr auctionTuple=auctionapp.Auction.mkDecl().apply(argAuction);
	    auctionapp.Auction_set= ctx.mkSetAdd(auctionapp.Auction_set, auctionTuple);
	    
        Expr[] argPromoter = new Expr[2];
        argPromoter[0]=auction;
        argPromoter[1]=seller;

		
        Expr promoter =auctionapp.Promoter.mkDecl().apply(argPromoter);
        auctionapp.Promoter_set =  ctx.mkSetAdd(auctionapp.Promoter_set, promoter);
        
		Sort[] arg = new Sort[1];
		arg[0]=ctx.mkUninterpretedSort(ctx.mkSymbol("Product"));

		Symbol[] namesb = new Symbol[1];
		namesb[0] =  ctx.mkSymbol("product");

	    return  auctionapp.Auction_set ;
	    
	}

	@Override
	public Expr [] getArgs(String name) {
		Expr args[]= {this.auction, this.seller};
		return args;
	}

	@Override
	public BoolExpr getCondition(Context ctx, Operation op) throws Z3Exception {
		if (op.getName()=="CloseAuction") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.auction));
		}
		else if (op.getName()=="StartAuction") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.auction));
		}
		else if (op.getName()=="UnregisterSeller") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.seller));
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
