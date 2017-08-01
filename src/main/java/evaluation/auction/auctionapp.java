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

package evaluation.auction;

import application.Application;
import application.Operation;
import com.microsoft.z3.*;
import evaluation.auction.invariant.*;
import evaluation.auction.operations.*;
import invariant.Invariant;

import java.util.ArrayList;
import java.util.List;

public class auctionapp extends Application  {

	public static Sort Buyer; 
	public static Sort SellerID;
	public static Sort Product; 
	public static Sort AuctionID; 
	public static Sort BidID; 
	public static Sort LotID; 
	
	public static TupleSort Seller;
	public static TupleSort Auction;
	public static TupleSort Lot;
	public static TupleSort Bid;
	public static TupleSort Winner;
	public static TupleSort Owner;
	public static TupleSort Promoter;
	public static TupleSort Bidder;
	public static TupleSort auctionLot;
	
	public static EnumSort state;
	
	public static DatatypeSort pair;
	  
	public static Constructor mkpair;
	
	public static Expr Buyer_set;
	public static Expr SellerID_set;
	public static Expr Seller_set;
	public static Expr Product_set; 
	public static Expr Auction_set;
	public static Expr AuctionID_set;
	public static Expr Lot_set;
	public static Expr Bid_set;
	public static Expr BidID_set;
	public static Expr LotID_set;	
	public static Expr Winner_set;
	public static Expr Owner_set;
	public static Expr Promoter_set;
	public static Expr Bidder_set;
	public static Expr auctionLot_set;
	
    public static Sort array_type ;
    public static ArrayExpr stockArray;
    public static ArrayExpr sizeArray;
	
	public static List<Operation> op=new ArrayList<Operation>();
	static List<Invariant> inv=new ArrayList<Invariant>();
  
	
	@Override
	public List<Operation> loadOperations(Context ctx) throws Z3Exception {
		
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
	public List<Invariant> loadInvariants(Context ctx) throws Z3Exception {
		Invariant inv1=new ForeignKeyBidder("ForeignKeyBidder");
		Invariant inv2=new ForeignKeyLot("ForeignKeyLot");
		Invariant inv3=new ForeignKeyOwner("ForeignKeyOwner");
		Invariant inv4=new ForeignKeyPromoter("ForeignKeyPromoter");
		Invariant inv5=new ForeignKeyWinner("ForeignKeyWinner");
		Invariant inv6=new LotExist("LotExist");
		Invariant inv7=new ValidAuction("ValidAuction");
		Invariant inv8=new StockLimit("StockLimit");
		Invariant inv9=new WinnerRule1("WinnerRule1");
		Invariant inv10=new WinnerRule2("WinnerRule2");
		Invariant inv11= new LotInvariant("lotsInvarinat");
		Invariant inv12= new StateInvariant("StateInvariant");
				
        inv.add(inv1);
        inv.add(inv2);
        inv.add(inv3);
        inv.add(inv4);;
        inv.add(inv5);
        inv.add(inv6); 
        inv.add(inv7);
        inv.add(inv8);
        inv.add(inv9);
        inv.add(inv10);  
        inv.add(inv11);     
        inv.add(inv12);
        
        return inv;
	}

	@Override
	public void initializeState(Context ctx) throws Z3Exception {
		
		
		Symbol name = ctx.mkSymbol("state");
				
		Symbol[] args={ ctx.mkSymbol("prep"),
                ctx.mkSymbol("active"), ctx.mkSymbol("close")} ;
		
		state= ctx.mkEnumSort(name, args);
		
		Buyer = ctx.mkUninterpretedSort("Buyer");
		Product = ctx.mkUninterpretedSort("Product");
	    SellerID = ctx.mkUninterpretedSort("SellerID");
	    AuctionID = ctx.mkUninterpretedSort("AuctionID");

	    BidID=ctx.mkUninterpretedSort("BidID");
	    
	    LotID=ctx.mkUninterpretedSort("LotID");
	    
	    SetSort bb=ctx.mkSetSort(Buyer);
	    Buyer_set = ctx.mkConst("Buyer_set",bb);
	    
	    SetSort ss=ctx.mkSetSort(SellerID);
	    SellerID_set = ctx.mkConst("SellerID_set",ss);
	    
	    SetSort aa=ctx.mkSetSort(AuctionID);
	    AuctionID_set = ctx.mkConst("AuctionID_set",aa);
	    
	    SetSort pp=ctx.mkSetSort(Product);
	    Product_set = ctx.mkConst("Product_set",pp);
	    
	    SetSort ii=ctx.mkSetSort(BidID);
	    BidID_set = ctx.mkConst("BidID_set",ii);
	    
    
	    Seller= ctx.mkTupleSort(ctx.mkSymbol("mk_Seller_tuple"), // name of
	  	      new Symbol[] { ctx.mkSymbol("first"), ctx.mkSymbol("second") }, // names
	  	      new Sort[] { ctx.mkUninterpretedSort(ctx.mkSymbol("SellerID")), ctx.mkIntSort()  } );
	  	    
	  	 SetSort st=ctx.mkSetSort(Seller);
	  	 Seller_set = ctx.mkConst("Seller_set",st);
	  	 
	  	 
		 Lot= ctx.mkTupleSort(ctx.mkSymbol("mk_Lot_tuple"), // name of
		  	    new Symbol[] { ctx.mkSymbol("first"), ctx.mkSymbol("second") ,  ctx.mkSymbol("third"),  ctx.mkSymbol("forth") }, // names
		  	    new Sort[] { ctx.mkUninterpretedSort(ctx.mkSymbol("LotID")),  ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID")),
			 ctx.mkUninterpretedSort(ctx.mkSymbol("Product")),ctx.mkIntSort()  } );
		  	    
		 SetSort lt=ctx.mkSetSort(Lot);
		  Lot_set = ctx.mkConst("Lot_set",lt);
		 
		  
		 auctionLot=ctx.mkTupleSort(ctx.mkSymbol("mk_AuctionLot_tuple"), // name of
			  	    new Symbol[] { ctx.mkSymbol("first"), ctx.mkSymbol("second")  }, // names
			  	    new Sort[] { ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID")), ctx.mkUninterpretedSort(ctx.mkSymbol("LotID")) } );
			  	    
	     SetSort al=ctx.mkSetSort(auctionLot);
	     auctionLot_set= ctx.mkConst("auctionLot_set",al);
	  
		  
		  
		  
		  Bid= ctx.mkTupleSort(ctx.mkSymbol("mk_Bid_tuple"), // name of
			  	    new Symbol[] { ctx.mkSymbol("first"), ctx.mkSymbol("second") ,  ctx.mkSymbol("third") }, // names
			  	    new Sort[] { ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID")), ctx.mkUninterpretedSort(ctx.mkSymbol("Buyer")),ctx.mkIntSort()  } );
			  	    
		  SetSort bt=ctx.mkSetSort(Bid);
		  Bid_set= ctx.mkConst("Bid_set",bt);
		  
		   
			  
		  Auction= ctx.mkTupleSort(ctx.mkSymbol("mk_Auction_tuple"), // name of
				  	    new Symbol[] { ctx.mkSymbol("first"), ctx.mkSymbol("second")  }, // names
				  	    new Sort[] { ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID")),state } );
				  	    
		  SetSort at=ctx.mkSetSort(Auction);
		  Auction_set= ctx.mkConst("Auction_set",at);
		  
		
		  
		  Winner= ctx.mkTupleSort(ctx.mkSymbol("mk_Winner_tuple"), // name of
			  	    new Symbol[] { ctx.mkSymbol("first"), ctx.mkSymbol("second") }, // names
			  	    new Sort[] { ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID")), ctx.mkUninterpretedSort(ctx.mkSymbol("Buyer"))  } );
	  	    
          SetSort wt=ctx.mkSetSort(Winner);
          Winner_set= ctx.mkConst("Winner_set",wt);
          
          
		  Owner= ctx.mkTupleSort(ctx.mkSymbol("mk_Owner_tuple"), // name of
			  	    new Symbol[] { ctx.mkSymbol("first"), ctx.mkSymbol("second") }, // names
			  	    new Sort[] { ctx.mkUninterpretedSort(ctx.mkSymbol("Product")), ctx.mkUninterpretedSort(ctx.mkSymbol("SellerID")) } );
	  	    
          SetSort ow=ctx.mkSetSort(Owner);
          Owner_set= ctx.mkConst("Owner_set",ow);
        
		  Promoter= ctx.mkTupleSort(ctx.mkSymbol("mk_Promoter_tuple"), // name of
			  	    new Symbol[] { ctx.mkSymbol("first"), ctx.mkSymbol("second") }, // names
			  	    new Sort[] { ctx.mkUninterpretedSort(ctx.mkSymbol("AuctionID")), ctx.mkUninterpretedSort(ctx.mkSymbol("SellerID")) } );
	  	    
          SetSort pr=ctx.mkSetSort(Promoter);
          Promoter_set= ctx.mkConst("Promoter_set",pr);
      
		  Bidder= ctx.mkTupleSort(ctx.mkSymbol("mk_Bidder_tuple"), // name of
			  	    new Symbol[] { ctx.mkSymbol("first"), ctx.mkSymbol("second") }, // names
			  	    new Sort[] { ctx.mkUninterpretedSort(ctx.mkSymbol("BidID")), ctx.mkUninterpretedSort(ctx.mkSymbol("SellerID")) } );
	  	    
         SetSort bi=ctx.mkSetSort(Bidder);
         Bidder_set= ctx.mkConst("Bidder_set",bi);
        
         array_type = ctx.mkArraySort(ctx.mkIntSort(), ctx.mkIntSort());
         stockArray = (ArrayExpr) ctx.mkConst("stockArray", array_type);
         sizeArray = (ArrayExpr) ctx.mkConst("sizeArray", array_type);
		  	  	 	
	}

	@Override
	public BoolExpr getCompoisteInvariant(Context ctx) throws Z3Exception {
		
		BoolExpr composite=ctx.mkTrue();
		for (Invariant i:inv )
		 composite=	ctx.mkAnd(new BoolExpr []{composite, (BoolExpr) i.getInv(ctx)});
		return composite;
	}
	

}
