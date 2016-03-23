package evaluation.bank;

import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Z3Exception;


public class AccountObject {

	public Expr account;
	public Expr customer;
	public IntExpr limitArrayIndex;
	public IntExpr balance;
	public IntExpr creditLimit;

	
	public  AccountObject(Context ctx) throws Z3Exception {
		
		this.account=ctx.mkConst("account", ctx.mkUninterpretedSort(ctx.mkSymbol("AccountID")));
		this.customer=ctx.mkConst("customer", ctx.mkUninterpretedSort(ctx.mkSymbol("Customer")));
		this.limitArrayIndex=(IntExpr) ctx.mkConst("balanceIndex",ctx.mkIntSort());
		this.balance=(IntExpr) ctx.mkConst("balance",ctx.mkIntSort());;
		this.creditLimit=(IntExpr) ctx.mkConst("creditLimit",ctx.mkIntSort());;
		
	}
	
	public  AccountObject(Context ctx, int i) throws Z3Exception {
		
    	this.account=ctx.mkConst("account"+i, ctx.mkUninterpretedSort(ctx.mkSymbol("AccountID")));
    	this.customer=ctx.mkConst("customer"+i, ctx.mkUninterpretedSort(ctx.mkSymbol("Customer")));
		this.limitArrayIndex=(IntExpr) ctx.mkConst("balanceIndex"+i,ctx.mkIntSort());
		this.balance=(IntExpr) ctx.mkConst("balance"+i,ctx.mkIntSort());;
		this.creditLimit=(IntExpr) ctx.mkConst("creditLimit",ctx.mkIntSort());;
	
		
	}

	public Expr getAccount() {
		return this.account;
	}
	
	public Expr getCustomer() {
		return this.customer;
	}
	
	
	public Expr getIndex() {
		return this.limitArrayIndex;
	}
	
	public Expr getBalance() {
		return this.balance;
	}
	
	public Expr getCreditLimit() {
		return this.creditLimit;
	}
	
	
	public void putBalance(IntExpr creditLimit) {
		 this.balance=creditLimit;
	}
	
}