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

public class ValidAuction implements Invariant  {

	String name;
	
	public ValidAuction(String name) {
		this.name=name;
	}

	public ValidAuction() {
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public BoolExpr getInv(Context ctx) throws Z3Exception {
		
		Expr auction = ctx.mkConst("auction", ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID")));
	    	   
		Expr[] argAuction = new Expr[2];
		argAuction[0]=auction;
		argAuction[1]=auctionapp.state.getConsts()[0];

	    Expr auctionTuple=auctionapp.Auction.mkDecl().apply(argAuction);
	    
 	   
	    Expr[] argAuction2 = new Expr[2];
	    argAuction2[0]=auction;
	    argAuction2[1]=auctionapp.state.getConsts()[1];

	    Expr auctionTuple2=auctionapp.Auction.mkDecl().apply(argAuction2);
	    
	    Expr[] argAuction3 = new Expr[2];
	    argAuction3[0]=auction;
	    argAuction3[1]=auctionapp.state.getConsts()[2];

	    Expr auctionTuple3=auctionapp.Auction.mkDecl().apply(argAuction3);

		Sort[] arg = new Sort[1];
		arg[0]=ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID"));
		
		Symbol[] namesb = new Symbol[1];
		namesb[0] =  ctx.mkSymbol("auction");

		Expr existAuction=ctx.mkOr(new BoolExpr[] {(BoolExpr)ctx.mkSetMembership(auctionTuple, auctionapp.Auction_set), (BoolExpr)ctx.mkSetMembership(auctionTuple2, auctionapp.Auction_set),
				(BoolExpr)ctx.mkSetMembership(auctionTuple3, auctionapp.Auction_set)});
	   
	    Expr auctionValidity =ctx.mkImplies( (BoolExpr)ctx.mkSetMembership(auctionTuple3, auctionapp.Auction_set), (BoolExpr) ctx.mkSetMembership(auction, auctionapp.AuctionID_set));
	    
	    Expr auctionForiegn = ctx.mkForall(arg, namesb, auctionValidity, 1, null, null,null, null);
	   
	    System.out.println("Auction Foriegn key:"+auctionForiegn);
		
	    return (BoolExpr) auctionForiegn;
	}
}