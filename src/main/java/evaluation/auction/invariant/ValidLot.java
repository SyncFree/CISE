

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

public class ValidLot implements Invariant  {

	String name;
	
	public ValidLot(String name) {
	
		this.name=name;
	}
	
	public ValidLot() {	
	}


	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public BoolExpr getInv(Context ctx) throws Z3Exception {
		Expr seller = ctx.mkConst("seller", ctx.mkUninterpretedSort(ctx.mkSymbol("SellerID")));
		Expr auction = ctx.mkConst("auction", ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID")));
		Expr lot = ctx.mkConst("lot", ctx.mkUninterpretedSort(ctx.mkSymbol("LotID")));
		Expr product = ctx.mkConst("product", ctx.mkUninterpretedSort(ctx.mkSymbol("Product")));
		
		Sort[] arg = new Sort[2];
		arg[0]=ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID"));
		arg[1]=ctx.mkUninterpretedSort(ctx.mkSymbol("LotID"));

		Symbol[] namesb = new Symbol[2];
		namesb[0] =  ctx.mkSymbol("auction");
		namesb[1] =  ctx.mkSymbol("lot");
		
		Expr[] argAuction = new Expr[2];
		argAuction[0]=auction;
		argAuction[1]=ctx.mkConst("state", auctionapp.state);

	    Expr auctionTuple=auctionapp.Auction.mkDecl().apply(argAuction);

		Expr[] argAuctionLot = new Expr[2];
		argAuctionLot[0]=auction;
		argAuctionLot[1]=lot;

	    Expr auctioLlotTuple=auctionapp.auctionLot.mkDecl().apply(argAuctionLot);
	    		   
	    Sort[] arg2 = new Sort[1];
	    arg2[0]=ctx.mkUninterpretedSort(ctx.mkSymbol("SellerID"));

		Symbol[] namesb2 = new Symbol[1];
    	namesb2[0] =  ctx.mkSymbol("seller");
	    
		Expr[] argLot = new Expr[3];
		argLot[0]= lot;
		argLot[1]=product;
		argLot[2]=ctx.mkConst("lotSize", ctx.mkIntSort());;

	    Expr lotTuple=auctionapp.Lot.mkDecl().apply(argLot);
	    
	    
	    Expr lotExist=(BoolExpr) ctx.mkSetMembership(lotTuple, auctionapp.Lot_set);
	    Expr auctionExist= (BoolExpr) ctx.mkSetMembership(auctionTuple, auctionapp.Auction_set);
	    
		Expr[] argPromoter = new Expr[2];
		argPromoter[0]= auction;
		argPromoter[1]=seller;

	    Expr Promoter=auctionapp.Promoter.mkDecl().apply(argPromoter);
	        
		Expr[] argOwner = new Expr[2];
		argOwner[0]= product;
		argOwner[1]=seller;

	    Expr Owner=auctionapp.Owner.mkDecl().apply(argOwner);
	    
		Expr foriegn2 = ctx.mkExists(arg2, namesb2, ctx.mkAnd(new BoolExpr []{(BoolExpr) ctx.mkSetMembership(Promoter, auctionapp.Promoter_set),
				(BoolExpr) ctx.mkSetMembership(Owner, auctionapp.Owner_set)}), 1, null, null,
		null, null);	  
		
		
	    Expr rr= ctx.mkAnd(new BoolExpr []{(BoolExpr) lotExist,(BoolExpr) auctionExist,(BoolExpr) foriegn2});
	    
		Expr foriegn3 =  ctx.mkAnd( new BoolExpr[]{(BoolExpr) ctx.mkSetMembership(auction, auctionapp.AuctionID_set),
				(BoolExpr) ctx.mkSetMembership(product, auctionapp.Product_set)});
	

	    Expr ownership2 =ctx.mkImplies( (BoolExpr)ctx.mkSetMembership(lot, auctionapp.auctionLot_set), (BoolExpr) rr );
	    		
	    
		Expr foriegn = ctx.mkForall(arg, namesb, ownership2, 1, null, null,
		null, null);	

		System.out.println("Valid Lot"+foriegn);
		
		return (BoolExpr) foriegn;
	}

}
