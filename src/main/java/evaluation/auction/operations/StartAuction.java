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

public class StartAuction  implements Operation {
	
	
	private String name;
	private Expr auction;
	private Expr lot;

	public StartAuction(String name, Context ctx) {
		this.name=name;
		try {
			this.auction = ctx.mkConst("auction", ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID")));
			 this.lot = ctx.mkConst("lot", ctx.mkUninterpretedSort(ctx.mkSymbol("LotID")));
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
             
   	    Expr lotSizeBound= ctx.mkGe((ArithExpr) argLot[3], ctx.mkInt("0")) ;
   	    Expr lotExists= (BoolExpr) ctx.mkSetMembership(lotTuple, auctionapp.Lot_set);
        
		Expr existLots = ctx.mkExists(productarg, namesa, lotSizeBound, 1, null, null,
				null, null);

	    Expr auctionId = ctx.mkConst("auction1", ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID")));
	    
		Sort[] auctions = new Sort[1];
		auctions[0]=ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID"));
		Symbol[] namess = new Symbol[1];
		namess[0] =  ctx.mkSymbol("auction1");
		
		Expr[] argAuction = new Expr[2];
		argAuction[0]=auctionId;
		argAuction[1]=auctionapp.state.getConsts()[1];
		
	    Expr auctionTuple=auctionapp.Auction.mkDecl().apply(argAuction);

    
     //   Expr noclosed =ctx.mkAnd(new BoolExpr [] {
    //    		(BoolExpr) ctx.mkEq(auction, auctionId), (BoolExpr) ctx.mkSetMembership(auctionTuple, auctionapp.Auction_set)
     //   });
        
	//	Expr existAuction1 = ctx.mkNot(ctx.mkExists(auctions, namess, noclosed, 1, null, null,
	//	null, null));	
		
	    
		Expr[] argAuction2 = new Expr[2];
		argAuction2[0]=auction;
		argAuction2[1]=auctionapp.state.getConsts()[2];

	    Expr auctionTuple2=auctionapp.Auction.mkDecl().apply(argAuction2);
	
	    	
	   Expr noActive =ctx.mkNot((BoolExpr) ctx.mkSetMembership(auctionTuple, auctionapp.Auction_set));
	        
	   Expr noclosed =ctx.mkNot((BoolExpr) ctx.mkSetMembership(auctionTuple2, auctionapp.Auction_set));
 
	   Expr noHigherState=ctx.mkAnd(new BoolExpr[]{(BoolExpr) noActive, (BoolExpr) noclosed});
		
	
	    BoolExpr precondition=ctx.mkAnd(new BoolExpr[]{(BoolExpr) lotExists, (BoolExpr) noHigherState});
	    
	    return  (BoolExpr) precondition;
	    
	}

	@Override
	public Expr effect(Context ctx) throws Z3Exception {
			
		Expr[] argAuction = new Expr[2];
		argAuction[0]=auction;
		argAuction[1]=auctionapp.state.getConsts()[1];
	

	    Expr auctionTuple=auctionapp.Auction.mkDecl().apply(argAuction);
	    auctionapp.Auction_set= ctx.mkSetAdd(auctionapp.Auction_set, auctionTuple);
	    
		Expr[] argAuction2 = new Expr[2];
		argAuction2[0]=auction;
		argAuction2[1]=auctionapp.state.getConsts()[0];;
	    
	    Expr auctionTuple2=auctionapp.Auction.mkDecl().apply(argAuction2);
	    auctionapp.Auction_set= ctx.mkSetDel(auctionapp.Auction_set, auctionTuple2);
	    
		Expr[] argAuctionLot = new Expr[2];
		argAuctionLot[0]=auction;
		argAuctionLot[1]=lot;
	    
	    Expr auctionLotTuple=auctionapp.auctionLot.mkDecl().apply(argAuctionLot);

	    auctionapp.auctionLot_set= ctx.mkSetAdd(auctionapp.auctionLot_set, auctionLotTuple);

	    return  auctionapp.Auction_set ;
	}

	@Override
	public Expr [] getArgs(String name) {
		Expr args[]={this.auction};
		return args ;
	}

	@Override
	public BoolExpr getCondition(Context ctx, Operation op) throws Z3Exception {
		
		if (op.getName()=="RemoveFromLot") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.auction));
		}
		else if (op.getName()=="CloseAuction") {
			return ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.auction));
		}		
		else if (op.getName()=="StartAuction") {
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getReplicaIndex() {
		// TODO Auto-generated method stub
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
