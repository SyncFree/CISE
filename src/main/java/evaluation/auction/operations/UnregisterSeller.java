/*******************************************************************************
 * ===========================================================
 * This file is part of the CISE tool software.
 *
 * The CISE tool software contains proprietary and confidential information of Inria.
 * All rights reserved. Reproduction, adaptation or distribution, in whole or in part, is
 * forbidden except by express written permission of Inria.
 * Version V1.5.1., July 2017
 * Authors: Mahsa Najafzadeh, Michał Jabczyński, Marc Shapiro
 * Copyright (C) 2017, Inria
 * ===========================================================
 ******************************************************************************/

package evaluation.auction.operations;

import application.Operation;
import com.microsoft.z3.*;
import evaluation.auction.auctionapp;

import java.util.ArrayList;
import java.util.List;

public class UnregisterSeller  implements Operation {

	
	private String name;
	private Expr seller;

	
	public UnregisterSeller(String name, Context ctx) {
		this.name=name;
		try {
			this.seller = ctx.mkConst("seller", ctx.mkUninterpretedSort(ctx.mkSymbol("SellerID")));
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
		auctionarg[0]=ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID"));
		Symbol[] namesa = new Symbol[1];
		namesa[0] =  ctx.mkSymbol("auction");
		
        Expr[] argPromoter = new Expr[2];
        argPromoter[0]=auction;
        argPromoter[1]=seller;	
		
        Expr promoter =auctionapp.Promoter.mkDecl().apply(argPromoter);
        Expr noPormoter = ctx.mkNot((BoolExpr) ctx.mkSetMembership(promoter, auctionapp.Promoter_set));
		Expr existAuction = ctx.mkForall(auctionarg, namesa, noPormoter, 1, null, null,
				null, null);
		
		Expr product = ctx.mkConst("product", ctx.mkUninterpretedSort(ctx.mkSymbol("Product")));

		Sort[] productarg = new Sort[1];
		productarg[0]=ctx.mkUninterpretedSort(ctx.mkSymbol("Product"));
		Symbol[] namesp = new Symbol[1];
		namesp[0] =  ctx.mkSymbol("product");
		
        Expr[] argOnwer = new Expr[2];
        argOnwer[0]=product;
        argOnwer[1]=seller;

        Expr owner =auctionapp.Owner.mkDecl().apply(argOnwer);
        Expr noOwner = ctx.mkNot((BoolExpr) ctx.mkSetMembership(owner, auctionapp.Owner_set));
		Expr existProduct = ctx.mkForall(productarg, namesp, noOwner, 1, null, null,
				null, null);
		
		Expr precondition=ctx.mkAnd(new BoolExpr[]{(BoolExpr) existAuction, (BoolExpr) existProduct});
				
  	    return (BoolExpr) precondition;
	}

	@Override
	public Expr effect(Context ctx) throws Z3Exception {
		auctionapp.SellerID_set= ctx.mkSetDel(auctionapp.SellerID_set, seller);
	    
	    Expr limit = ctx.mkConst("auctionLimit", ctx.mkIntSort());

		Sort[] limitarg = new Sort[1];
		limitarg[0]=ctx.mkIntSort();
		Symbol[] namesa = new Symbol[1];
		namesa[0] =  ctx.mkSymbol("auctionLimit");
		
		Expr[] argSeller = new Expr[2];
		argSeller[0]=seller;
		argSeller[1]=limit;		
	    Expr sellerTuple =auctionapp.Seller.mkDecl().apply(argSeller);
	    auctionapp.Seller_set=ctx.mkSetDel(auctionapp.Seller_set, sellerTuple);
	    

	  	// return  auctionapp.Owner_set ;

  	    return (auctionapp.SellerID_set);
	}

	@Override
	public Expr [] getArgs(String name) {
		Expr args[]={this.seller};
		return  args;
	}

	@Override
	public BoolExpr getCondition(Context ctx, Operation op) throws Z3Exception {
		
		if (op.getName()=="AddProduct") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.seller));
		}
		else if (op.getName()=="CreateAuction") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[1], this.seller));
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
