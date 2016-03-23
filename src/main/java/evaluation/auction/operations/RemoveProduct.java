package evaluation.auction.operations;

import application.Operation;
import com.microsoft.z3.*;
import evaluation.auction.auctionapp;

import java.util.ArrayList;
import java.util.List;

public class RemoveProduct  implements Operation  {
	
	
	private String name;
	private Expr product;
	
	public RemoveProduct(String name, Context ctx) {
		this.name=name;
		try {
			this.product = ctx.mkConst("product", ctx.mkUninterpretedSort(ctx.mkSymbol("Product")));
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
		Expr lotId = ctx.mkConst("lot", ctx.mkUninterpretedSort(ctx.mkSymbol("LotID")));
	
		Sort[] auctionarg = new Sort[1];
		auctionarg[0]=ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID"));
		Symbol[] namesa = new Symbol[1];
		namesa[0] =  ctx.mkSymbol("auction");
		
        Expr[] argLot = new Expr[4];
        argLot[0]=lotId;
        argLot[1]=auction;
        argLot[2]=product;
        argLot[3]=(IntExpr) ctx.mkConst("lotSize", ctx.mkIntSort());
		
        Expr lotTuple =auctionapp.Lot.mkDecl().apply(argLot);
        Expr nolot = (BoolExpr) ctx.mkSetMembership(lotTuple, auctionapp.Lot_set);
		Expr existLot = ctx.mkExists(auctionarg, namesa, nolot, 1, null, null,
				null, null);
		
		Expr precondition=(BoolExpr) ctx.mkNot((BoolExpr) existLot);
  	    return (BoolExpr) precondition;
	}

	@Override
	public Expr effect(Context ctx) throws Z3Exception {
	  	auctionapp.Product_set= ctx.mkSetDel(auctionapp.Product_set, product);
	
		 Expr seller = ctx.mkConst("seller", ctx.mkUninterpretedSort(ctx.mkSymbol("SellerID")));

	     Sort[] arg = new Sort[1];
		 arg[0]=ctx.mkUninterpretedSort(ctx.mkSymbol("SellerID"));
		 Symbol[] namesa = new Symbol[1];
		 namesa[0] =  ctx.mkSymbol("seller");
			
		 Expr[] argProduct = new Expr[2];
		 argProduct[0]=product;
		 argProduct[1]=seller;			
		 Expr ownerTuple =auctionapp.Owner.mkDecl().apply(argProduct);
	     auctionapp.Owner_set= ctx.mkSetDel(auctionapp.Owner_set, ownerTuple);
	  	 return  auctionapp.Owner_set ;
		
	}

	@Override
	public Expr [] getArgs(String name) {
		Expr args[]={this.product};
		return args;
	}

	@Override
	public BoolExpr getCondition(Context ctx, Operation op) throws Z3Exception {
		
		if (op.getName()=="AddToLot") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[2], this.product));
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
