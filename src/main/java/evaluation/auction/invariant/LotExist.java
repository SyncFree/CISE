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

package evaluation.auction.invariant;

import com.microsoft.z3.*;
import evaluation.auction.auctionapp;
import invariant.Invariant;

public class LotExist implements Invariant {

	String name;
	
	public LotExist(String name) {
        this.name=name;
	}
	
	public LotExist() {
	}


	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public BoolExpr getInv(Context ctx) throws Z3Exception {
		
		Expr auction = ctx.mkConst("auction", ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID")));
		Expr product = ctx.mkConst("product", ctx.mkUninterpretedSort(ctx.mkSymbol("Product")));
		Expr lot = ctx.mkConst("lot", ctx.mkUninterpretedSort(ctx.mkSymbol("LotID")));
		
		Sort[] arg = new Sort[1];
		arg[0]=ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID"));

		Symbol[] namesb = new Symbol[1];
		namesb[0] =  ctx.mkSymbol("auction");
	    
		Expr[] argLot = new Expr[4];
		argLot[0]=lot;
		argLot[1]=auction;
		argLot[2]=product;
		argLot[3]=ctx.mkConst("lotSize", ctx.mkIntSort());;

	    Expr lotTuple=auctionapp.Lot.mkDecl().apply(argLot);
	    
		Expr[] argAuction = new Expr[2];
		argAuction[0]=auction;
		argAuction[1]=auctionapp.state.getConsts()[1];

	    Expr auctionTuple=auctionapp.Auction.mkDecl().apply(argAuction);
	    
	    Expr  activeState=ctx.mkSetMembership(auctionTuple, auctionapp.Auction_set);
	    	    	    
		Expr[] argAuction2 = new Expr[2];
		argAuction2[0]=auction;
		argAuction2[1]=auctionapp.state.getConsts()[2];

	    Expr auctionTuple2=auctionapp.Auction.mkDecl().apply(argAuction2);
	    
	    Expr closedState=ctx.mkSetMembership(auctionTuple2, auctionapp.Auction_set);
	    
	//   activeState= ctx.mkAnd(new BoolExpr[] {(BoolExpr) activeState, closedState});
	    	    
	    Expr limit=ctx.mkImplies((BoolExpr) ctx.mkOr(new BoolExpr[]{(BoolExpr) closedState,
				(BoolExpr) activeState}), (BoolExpr) ctx.mkSetMembership(lotTuple, auctionapp.Lot_set));
	       
		Expr lotExist = ctx.mkForall(arg, namesb, limit, 1, null, null, null, null);
		
		System.out.println("Lot Exists:"+lotExist);
	
		return (BoolExpr) lotExist;
	}

}
