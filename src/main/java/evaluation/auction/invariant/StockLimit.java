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
import evaluation.auction.productObj;
import invariant.Invariant;

public class StockLimit implements Invariant  {

	
	String name;
	
	public StockLimit(String name) {
		
		this.name=name;
	}
	
	public StockLimit() {
		
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public BoolExpr getInv(Context ctx) throws Z3Exception {
		
		Sort[] arg = new Sort[1];
		arg[0]=ctx.mkUninterpretedSort(ctx.mkSymbol("Product"));

		Symbol[] namesb = new Symbol[1];
		namesb[0] =  ctx.mkSymbol("product");

		Expr[] stockindex =  new Expr[1];
		stockindex[0]=ctx.mkConst("product", ctx.mkUninterpretedSort(ctx.mkSymbol("Product")));;
		     
	     productObj p1=new productObj(ctx,1);
	     productObj p2=new productObj(ctx,2);

	     IntExpr stockIndex1=(IntExpr) p1.getIndex();
	     IntExpr stockIndex2=(IntExpr) p2.getIndex();
	     
	     Expr product1= p1.getProduct();
	     Expr product2= p2.getProduct();
		 
		 Expr stock= ctx.mkSelect(auctionapp.stockArray, stockIndex1);
	
		 Expr productBound=ctx.mkGe((ArithExpr) stock, ctx.mkInt("0"));
			
		 BoolExpr antecedent = ctx.mkNot(ctx.mkEq(stockIndex1, stockIndex2));
			
		 Expr distinctStock=ctx.mkImplies(ctx.mkNot(ctx.mkEq(product1, product2)), antecedent);
		  
		 Expr inv=ctx.mkAnd(new BoolExpr[]{(BoolExpr) distinctStock,(BoolExpr) productBound});
	    
		 Expr stockLimit = ctx.mkForall(arg, namesb, inv, 1, null, null,
				 null, null);
		
		 System.out.println("Stock Limit:"+stockLimit);
		
		 return (BoolExpr) stockLimit;
	}

}
