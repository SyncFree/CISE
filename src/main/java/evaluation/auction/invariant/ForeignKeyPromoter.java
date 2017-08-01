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

public class ForeignKeyPromoter implements Invariant  {

	String name;
	
	public ForeignKeyPromoter(String name) {
		this.name=name;
	}

	public ForeignKeyPromoter() {
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public BoolExpr getInv(Context ctx) throws Z3Exception {
		
		Expr seller = ctx.mkConst("seller", ctx.mkUninterpretedSort(ctx.mkSymbol("SellerID")));
		Expr auction = ctx.mkConst("auction", ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID")));
		
		Expr[] argPromoter = new Expr[2];
		argPromoter[0]=auction;
		argPromoter[1]=seller;

	    Expr promoter=auctionapp.Promoter.mkDecl().apply(argPromoter);
	    
		Expr[] argSeller = new Expr[2];
		argSeller[0]=seller;
		argSeller[1]=ctx.mkConst("auctionLimit", ctx.mkIntSort());;

	    Expr sellerTuple=auctionapp.Seller.mkDecl().apply(argSeller);
	    	   
		Expr[] argAuction = new Expr[2];
		argAuction[0]=auction;
		argAuction[1]=ctx.mkConst("state", auctionapp.state);

	 // Expr auctionTuple=auctionapp.Auction.mkDecl().apply(argAuction);

		Sort[] arg = new Sort[2];
		arg[0]=ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID"));
		arg[1]=ctx.mkUninterpretedSort(ctx.mkSymbol("SellerID"));

		
		Symbol[] namesb = new Symbol[2];
		namesb[0] =  ctx.mkSymbol("auction");
		namesb[1] =  ctx.mkSymbol("seller");
		
	    Expr exist= ctx.mkAnd(new BoolExpr[] { (BoolExpr) ctx.mkSetMembership(sellerTuple, auctionapp.Seller_set) ,
			   (BoolExpr) ctx.mkSetMembership(auction, auctionapp.AuctionID_set)});
	   
	    Expr promoterExist =ctx.mkImplies( (BoolExpr)ctx.mkSetMembership(promoter, auctionapp.Promoter_set), (BoolExpr) exist);
	    
	    Expr promoterForiegn = ctx.mkForall(arg, namesb, promoterExist, 1, null, null, null, null);
	   
	    System.out.println("Promoter Foriegn key:"+promoterForiegn);
		
	    return (BoolExpr) promoterForiegn;
	}

}
