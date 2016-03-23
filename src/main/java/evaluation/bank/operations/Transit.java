package evaluation.bank.operations;

import application.Operation;
import com.microsoft.z3.*;
import evaluation.bank.AccountObject;
import evaluation.bank.bank;

import java.util.ArrayList;
import java.util.List;

public class Transit implements Operation {
	
	private String name;
	private Expr account1;
	private Expr account2;
	private Expr value;
	private Expr limitIndex1;
	private Expr creditLimit1;
	private Expr limitIndex2;
	private Expr customer1;
	private Expr customer2;


	
	public Transit(String name, Context ctx) {
		this.name=name;
		try {
			AccountObject a1=new AccountObject(ctx,1);
			this.account1=a1.getAccount();
			this.limitIndex1=(IntExpr) a1.getIndex();
			this.creditLimit1= (IntExpr) a1.getCreditLimit();	
			this.customer1=a1.getCustomer();

			AccountObject a2=new AccountObject(ctx,2);
			this.account2=a2.getAccount();
			this.limitIndex2=(IntExpr) a2.getIndex();	
			this.value=ctx.mkConst("value", ctx.mkIntSort());
			this.customer2=a2.getCustomer();

		} catch (Z3Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public BoolExpr precondition(Context ctx) throws Z3Exception {

		Expr newBalance= ctx.mkSub(new ArithExpr []{(ArithExpr) ctx.mkSelect(bank.capacityArray, limitIndex1), (ArithExpr) value });
		
		Expr precondition=ctx.mkGe((ArithExpr)newBalance,(ArithExpr) creditLimit1);
		
		return (BoolExpr) precondition;
	}

	@Override
	public Expr effect(Context ctx) throws Z3Exception {
	     
		 Expr balance1= ctx.mkSelect(bank.capacityArray, limitIndex1);
		// balance1=(IntExpr) ctx.mkSub(new ArithExpr[] {(ArithExpr) balance1,(ArithExpr) value});
		 
		 Expr[] argAccount = new Expr[3];
		 argAccount[0]=account1;
		 argAccount[1]=customer1;
		 argAccount[2]=balance1;

		 Expr accountTuple =bank.Account.mkDecl().apply(argAccount);
		 bank.Account_set = ctx.mkSetDel(bank.Account_set, accountTuple );
		 
		 balance1=ctx.mkITE( ctx.mkGe((ArithExpr) value, ctx.mkInt(0)), (IntExpr) ctx.mkSub(new ArithExpr[] {(ArithExpr) balance1,(ArithExpr) value}), balance1);
		 
	     bank.capacityArray=ctx.mkStore(bank.capacityArray, limitIndex1, balance1);
	     
		 Expr[] argAccountNew1 = new Expr[3];
		 argAccountNew1[0]=account1;
		 argAccountNew1[1]=customer1;
		 argAccountNew1[2]=balance1;

		 Expr accountTupleNew1 =bank.Account.mkDecl().apply(argAccountNew1);
		 bank.Account_set = ctx.mkSetAdd(bank.Account_set, accountTupleNew1 );

		 Expr balance2= ctx.mkSelect(bank.capacityArray, limitIndex2);
	//	 balance2=(IntExpr) ctx.mkAdd(new ArithExpr[] {(ArithExpr) balance2,(ArithExpr) value});
		 Expr[] argAccount2 = new Expr[3];
		 argAccount2[0]=account2;
		 argAccount2[1]=customer2;
		 argAccount2[2]=balance2;

		 Expr accountTuple2 =bank.Account.mkDecl().apply(argAccount2);
		 bank.Account_set = ctx.mkSetDel(bank.Account_set, accountTuple2 );
		 
		 balance2=ctx.mkITE( ctx.mkGe((ArithExpr) value, ctx.mkInt(0)), (IntExpr) ctx.mkAdd(new ArithExpr[] {(ArithExpr) balance2,(ArithExpr) value}), balance2);
	     bank.capacityArray=ctx.mkStore(bank.capacityArray, limitIndex2, balance2);
	     
		 
		 Expr[] argAccountNew2 = new Expr[3];
		 argAccountNew2[0]=account2;
		 argAccountNew2[1]=customer2;
		 argAccountNew2[2]=balance2;

		 Expr accountTupleNew2 =bank.Account.mkDecl().apply(argAccountNew2);
		 bank.Account_set = ctx.mkSetAdd(bank.Account_set, accountTupleNew2 );
	     
	     return bank.Account_set;
	}

	@Override
	public Expr[] getArgs(String name) {
		Expr args[]={limitIndex1};
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
//		Expr conditions [] = {ctx.mkTrue(), ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.limitIndex1)), ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.limitIndex1)),
//				ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.limitIndex1)), ctx.mkTrue(), ctx.mkTrue() };
	//	ctx.mkNot(ctx.mkEq(op.getArgs(this.name)[0], this.limitIndex1))
//		return conditions;
		return ctx.mkTrue();
	}

	@Override
	public void putConcurrentOp(Context ctx, Operation op) throws Z3Exception {

	}

}
