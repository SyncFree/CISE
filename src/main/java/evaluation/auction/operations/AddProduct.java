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
import evaluation.auction.productObj;

import java.util.ArrayList;
import java.util.List;

public class AddProduct  implements Operation  {
	
	private String name;
	private Expr seller;
	private Expr product;
	private IntExpr stock;
	private IntExpr stockIndex;
	
	public AddProduct(String name, Context ctx) {
		this.name=name;
		try {
			this.seller = ctx.mkConst("seller", ctx.mkUninterpretedSort(ctx.mkSymbol("SellerID")));
			productObj p=new productObj(ctx);
			this.product=p.getProduct();
			this.stockIndex=(IntExpr) p.getIndex();
			this.stock=(IntExpr) p.getStock();			
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
			
		 Expr sellerExist=ctx.mkSetMembership(sellerTuple, auctionapp.Seller_set);
		
		 return (BoolExpr) sellerExist;
	}

	@Override
	public Expr effect(Context ctx) throws Z3Exception {
		
		auctionapp.Product_set= ctx.mkSetAdd(auctionapp.Product_set, product);
		ctx.mkITE(ctx.mkGe((ArithExpr) stock, ctx.mkInt("1")), ctx.mkEq(auctionapp.stockArray,
				ctx.mkStore(auctionapp.stockArray, stockIndex, stock)), ctx.mkTrue());
		
        Expr[] argOnwer = new Expr[2];
        argOnwer[0]=product;
        argOnwer[1]=seller;
		
        Expr owner =auctionapp.Owner.mkDecl().apply(argOnwer);
        auctionapp.Owner_set = ctx.mkSetAdd(auctionapp.Owner_set, owner);
	    return  auctionapp.Product_set ;
	}

	@Override
	public Expr[] getArgs(String name) {
        Expr args[]={this.seller};
		return args;
	}

	@Override
	public BoolExpr getCondition(Context ctx, Operation op) throws Z3Exception {
		if (op.getName()=="UnregisterSeller") {
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
		return null;
	}

	@Override
	public void putConcurrentOp(Context ctx, Operation op) throws Z3Exception {

	}


}
