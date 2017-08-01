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

package evaluation.bank;

import application.Application;
import application.Operation;
import com.microsoft.z3.*;
import evaluation.bank.invariant.BoundedInv;
import evaluation.bank.operations.*;
import invariant.Invariant;

import java.util.ArrayList;
import java.util.List;

public class bank extends Application {
		
	public static Sort Customer; 
	public static Sort AccountID; 	
	public static TupleSort Account;
	
	public static Sort Operation; 
	

	public static Expr Customer_set;
	public static Expr AccountID_set;
	public static Expr Account_set; 
	
	public static Expr Operation_set;
	
	public static List<Operation> op=new ArrayList<Operation>();
	public static List<Invariant> inv=new ArrayList<Invariant>();
    public static Sort array_type ;
    public static ArrayExpr capacityArray;
    
    public static  Expr[][] matrix;

	@Override
	public BoolExpr getCompoisteInvariant(Context ctx) throws Z3Exception {
		
		BoolExpr composite=ctx.mkTrue();
		for (Invariant i:inv )
		 composite=	ctx.mkAnd(new BoolExpr []{composite, (BoolExpr) i.getInv(ctx)});
		return composite;
	}

	@Override
	public List<Operation> loadOperations(Context ctx) throws Z3Exception {
		
		Operation o1=new Deposit("Deposit",ctx);
		Operation o2=new Debit("Debit",ctx);
		Operation o3=new Transit("Transit",ctx);
		Operation o4=new AddAccount("AddAccount",ctx);
		Operation o5=new Interest("Interest",ctx);
	
		op.add(o1);
		op.add(o2);
		op.add(o3);
		op.add(o4);
		op.add(o5);

		return op;
	}

	@Override
	public List<Invariant> loadInvariants(Context ctx) throws Z3Exception {
		
		Invariant inv1=new BoundedInv("BoundedInv");

        inv.add(inv1);
       
        return inv;
	}

	@Override
	public void initializeState(Context ctx) throws Z3Exception {
		
		Customer = ctx.mkUninterpretedSort("Customer");
	    AccountID = ctx.mkUninterpretedSort("AccountID");

	    SetSort cc=ctx.mkSetSort(Customer);
	    Customer_set = ctx.mkConst("Customer_set",cc);
	    
	    Operation = ctx.mkUninterpretedSort("Operation");
	    SetSort oo=ctx.mkSetSort(Operation);
	    Operation_set = ctx.mkConst("Operation_set",oo);

	    SetSort aa=ctx.mkSetSort(AccountID);
	    AccountID_set = ctx.mkConst("AccountID_set",aa);

		Account= ctx.mkTupleSort(ctx.mkSymbol("mk_Account_tuple"), // name of
			  	    new Symbol[] { ctx.mkSymbol("first"), ctx.mkSymbol("second"),  ctx.mkSymbol("third") }, // names
			  	    new Sort[] { ctx.mkUninterpretedSort(ctx.mkSymbol("AccountID")), ctx.mkUninterpretedSort(ctx.mkSymbol("Customer")), ctx.mkIntSort()} );
			  	    
		SetSort at=ctx.mkSetSort(Account);
		Account_set= ctx.mkConst("Account_set",at);
	
        array_type = ctx.mkArraySort(ctx.mkIntSort(), ctx.mkIntSort());
        capacityArray = (ArrayExpr) ctx.mkConst("capacityArray", array_type);
        
        



	}
	
	
	public Expr [][] getConcurrenyMatrix (Context ctx) throws Z3Exception {
		matrix = new Expr[6][6];
		System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
		matrix[0][0]=ctx.mkConst("Operations", ctx.mkUninterpretedSort(ctx.mkSymbol("Operation")));
		matrix[0][1]= ctx.mkConst("Deposit", ctx.mkUninterpretedSort(ctx.mkSymbol("Operation")));
		matrix[0][2]= ctx.mkConst("Debit", ctx.mkUninterpretedSort(ctx.mkSymbol("Operation")));
		matrix[0][3]= ctx.mkConst("Transit", ctx.mkUninterpretedSort(ctx.mkSymbol("Operation")));
		matrix[0][4]= ctx.mkConst("AddAccount", ctx.mkUninterpretedSort(ctx.mkSymbol("Operation")));
		matrix[0][5]= ctx.mkConst("Interest", ctx.mkUninterpretedSort(ctx.mkSymbol("Operation")));
		matrix[1][0]= ctx.mkConst("Deposit", ctx.mkUninterpretedSort(ctx.mkSymbol("Operation")));
		matrix[2][0]= ctx.mkConst("Debit", ctx.mkUninterpretedSort(ctx.mkSymbol("Operation")));
		matrix[3][0]= ctx.mkConst("Transit", ctx.mkUninterpretedSort(ctx.mkSymbol("Operation")));
		matrix[4][0]= ctx.mkConst("AddAccount", ctx.mkUninterpretedSort(ctx.mkSymbol("Operation")));
		matrix[5][0]= ctx.mkConst("Interest", ctx.mkUninterpretedSort(ctx.mkSymbol("Operation")));
    

    
		for(int line=1 ; line < 6; line++){
			for(int column = 1; column < 6 ; column ++){
				//matrix[line][column]=op.get(line-1).getPreConditions(ctx,op.get(0).concurrentOps(ctx).get(column-1))[column-1];
				throw new IllegalStateException("qweqweqwe");
			}
		}
	
		for(int i=0; i<6; i++) {
			for(int j=0; j<6; j++)
				System.out.print(matrix[i][j] + " |");
			System.out.println();
			
		}
		System.out.println("-----------------------------------------------------------------------------------------------------------------------------");	
        return matrix;
     
	}
	
	
	public static void main(String[] args)  throws Z3Exception {
		
		bank b=new bank();
		
		Context ctx=new Context();
		
		Operation o1=new Deposit("Deposit",ctx);
		Operation o2=new Debit("Debit",ctx);
		Operation o3=new Transit("Transit",ctx);
		
		op.add(o1);
		op.add(o2);
		op.add(o3);  
		
		b.initializeState(ctx);
		
		
	}
	

}
