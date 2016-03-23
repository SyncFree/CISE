package evaluation.auction.operations;

import application.Operation;
import com.microsoft.z3.*;
import evaluation.auction.auctionapp;
import evaluation.auction.productObj;

import java.util.ArrayList;
import java.util.List;

public class AddToLot  implements Operation {
	
	private String name;
	private Expr product;
	private Expr seller;
	private Expr auction;
	private Expr quantity;
	private Expr stockIndex;
	private Expr lot;

	public AddToLot(String name, Context ctx) {
		this.name=name;
		try {
			this.seller = ctx.mkConst("seller", ctx.mkUninterpretedSort(ctx.mkSymbol("SellerID")));
			this.auction = ctx.mkConst("auction", ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID")));
			this.lot = ctx.mkConst("lot", ctx.mkUninterpretedSort(ctx.mkSymbol("LotID")));
			this.quantity=(IntExpr) ctx.mkConst("lotSize", ctx.mkIntSort());
			productObj p=new productObj (ctx);
	        this.product=p.getProduct();
	        this.stockIndex=(IntExpr) p.getIndex();			
		} catch (Z3Exception e) {
			e.printStackTrace();
		}
	}

	public AddToLot() {

   }

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public BoolExpr precondition(Context ctx) throws Z3Exception {
	
	 	Expr existProduct=ctx.mkSetMembership(product, auctionapp.Product_set);
			
		Expr stock= ctx.mkSelect(auctionapp.stockArray, stockIndex);
		Expr stockLimit=ctx.mkGe((ArithExpr) stock, ctx.mkInt("1"));
	 	
	    Expr productLimit=ctx.mkAnd(new BoolExpr[]{(BoolExpr) existProduct, (BoolExpr) stockLimit});
		
	
	    Expr[] argSeller = new Expr[2];
	    argSeller[0]=seller;
	    argSeller[1]=ctx.mkConst("auctionLimit", ctx.mkIntSort());
	    
		Expr sellerTuple=auctionapp.Seller.mkDecl().apply(argSeller);
	   	
		Expr existSeller=ctx.mkSetMembership(sellerTuple, auctionapp.Seller_set);
		    
	 	Expr limitExpr=ctx.mkGt((ArithExpr)argSeller[1],(ArithExpr) quantity);
	 	
	 	Expr sellerConst=ctx.mkAnd(new BoolExpr[]{(BoolExpr) existSeller, (BoolExpr) limitExpr, (BoolExpr) ctx.mkSetMembership(seller, auctionapp.SellerID_set)});
	   
		Expr[] argOwner = new Expr[2];
		argOwner[0]= product;
		argOwner[1]=seller;

	    Expr Owner=auctionapp.Owner.mkDecl().apply(argOwner);
      
		Expr[] argPromoter = new Expr[2];
		argPromoter[0]= auction;;
		argPromoter[1]=seller;

	    Expr Promoter=auctionapp.Promoter.mkDecl().apply(argPromoter);
	    
	    Expr existOwner=ctx.mkSetMembership(Owner, auctionapp.Owner_set);
	    Expr existPromoter=ctx.mkSetMembership(Promoter, auctionapp.Promoter_set);
	    
	 	Expr[] argAuction = new Expr[2];
	 	argAuction[0]= auction;;
	 	argAuction[1]=auctionapp.state.getConsts()[2];

	 	Expr auctionTuple=auctionapp.Auction.mkDecl().apply(argAuction);
	 	
	 	Expr[] argAuction2 = new Expr[2];
	 	argAuction2[0]= auction;;
	 	argAuction2[1]=auctionapp.state.getConsts()[1];

	 	Expr auctionTuple2=auctionapp.Auction.mkDecl().apply(argAuction2);
	 	
	 //	Expr activeState=ctx.mkOr(new BoolExpr [] {ctx.mkEq(argAuction[1], auctionapp.state.getConsts()[1]),ctx.mkEq(argAuction[1], auctionapp.state.getConsts()[2])});
	 		 	
	 	Expr activeState=ctx.mkNot((BoolExpr) ctx.mkSetMembership(auctionTuple, auctionapp.Auction_set));
	 	Expr closeState=ctx.mkNot((BoolExpr) ctx.mkSetMembership(auctionTuple2, auctionapp.Auction_set));
	    		
	 	BoolExpr precondition=ctx.mkAnd(new BoolExpr[]{(BoolExpr) existOwner, (BoolExpr) existPromoter, (BoolExpr) sellerConst,
				(BoolExpr) productLimit, (BoolExpr) activeState, (BoolExpr) closeState});
	 	
		return   precondition ;
	}
	
	@Override
	public Expr effect(Context ctx) throws Z3Exception {
		
		Expr[] argLot = new Expr[4];
		argLot[0]=lot;
		argLot[1]=auction;
		argLot[2]=product;
		argLot[3]=quantity;
			
	    Expr lotTuple =auctionapp.Lot.mkDecl().apply(argLot);
	    auctionapp.Lot_set = ctx.mkSetAdd(auctionapp.Lot_set, lotTuple);
	     
	    Expr stock= ctx.mkSelect(auctionapp.stockArray, stockIndex);
	    stock=(IntExpr) ctx.mkSub(new ArithExpr[]{(ArithExpr) stock, ctx.mkInt("1")});
	    auctionapp.stockArray=ctx.mkStore(auctionapp.stockArray, stockIndex, stock);

		return  auctionapp.Lot_set ;
	}

	@Override
	public Expr [] getArgs(String name) {
		Expr args[]={this.auction, this.seller, this.product};
		return args;
	}

	@Override
	public BoolExpr getCondition(Context ctx, Operation op) throws Z3Exception {
		
		if (op.getName()=="StartAuction") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.auction));
		}
		else if (op.getName()=="RemoveAuction") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.auction));
		}
		else if (op.getName()=="UnregisterSeller") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.seller));
		}
		
		else if (op.getName()=="RemoveProduct") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.product));
		}
		
		else if (op.getName()=="AddToLot") {
			return ctx.mkAnd(new BoolExpr[]{ctx.mkNot((BoolExpr) ctx.mkEq(op.getArgs(this.name)[1], this.seller)),
					(BoolExpr) ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.auction))});
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
