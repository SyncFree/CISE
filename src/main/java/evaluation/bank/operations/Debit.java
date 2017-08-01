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

package evaluation.bank.operations;

import application.Operation;
import com.microsoft.z3.*;
import evaluation.bank.AccountObject;
import evaluation.bank.bank;

import java.util.ArrayList;
import java.util.List;

public class Debit implements Operation {
		
	private String name;
	private Expr account;
	private Expr value;
	private IntExpr limitIndex;
	private Expr creditLimit;
	private Expr customer;

	
	public Debit(String name, Context ctx) {
		this.name=name;
		try {
			AccountObject a=new AccountObject(ctx);
			this.account=a.getAccount();
			this.limitIndex=(IntExpr)a.getIndex();
			this.creditLimit= (IntExpr) a.getCreditLimit();		
			this.value=ctx.mkConst("value", ctx.mkIntSort());
			this.customer=a.getCustomer();


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

		Expr newBalance= ctx.mkSub(new ArithExpr []{(ArithExpr) ctx.mkSelect(bank.capacityArray, limitIndex), (ArithExpr) value });
	
		Expr precondition=ctx.mkGe((ArithExpr)newBalance,(ArithExpr) creditLimit);
		
		return (BoolExpr) precondition;
	}

	@Override
	public Expr effect(Context ctx) throws Z3Exception {

	    Expr balance= ctx.mkSelect(bank.capacityArray, limitIndex);
	 //   balance=(IntExpr) ctx.mkSub(new ArithExpr[] {(ArithExpr) balance,(ArithExpr) value});
		Expr[] argAccount = new Expr[3];
		argAccount[0]=account;
		argAccount[1]=customer;
		argAccount[2]=balance;

	    Expr accountTuple =bank.Account.mkDecl().apply(argAccount);
	    bank.Account_set = ctx.mkSetDel(bank.Account_set, accountTuple );
	    
	    balance=ctx.mkITE( ctx.mkGe((ArithExpr) value, ctx.mkInt(0)), (IntExpr) ctx.mkSub(new ArithExpr[] {(ArithExpr) balance,(ArithExpr) value}), balance);
		bank.capacityArray=ctx.mkStore(bank.capacityArray, limitIndex, balance);
	
		Expr[] argAccountNew = new Expr[3];
		argAccountNew[0]=account;
		argAccountNew[1]=customer;
		argAccountNew[2]=balance;

	    Expr accountTupleNew =bank.Account.mkDecl().apply(argAccountNew);
	    bank.Account_set = ctx.mkSetAdd(bank.Account_set, accountTupleNew );
	     
	     return bank.Account_set;
	}

	@Override
	public Expr[] getArgs(String name) {
		Expr args [] ={limitIndex};
		return args;
	}

	@Override
	public BoolExpr getCondition(Context ctx, Operation op) throws Z3Exception {

		return null;
	}

	@Override
	public List<Operation> concurrentOps(Context ctx) {
			
		List<Operation> op=new ArrayList<Operation>();
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
	public void putReplicaIndex(Context ctx, int replica) throws Z3Exception {

	}

	@Override
	public int getReplicaIndex() {

		return 0;
	}

	@Override
	public Expr getConditions(Context ctx, Operation op) throws Z3Exception {
//		Expr conditions [] = {ctx.mkTrue(),ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.limitIndex)), ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.limitIndex)),
//				ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.limitIndex)), ctx.mkTrue() };
//		return conditions;
		return null;
	}

	@Override
	public void putConcurrentOp(Context ctx, Operation op) throws Z3Exception {

	}
	// ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.limitIndex))

}
