package evaluation.bank.operations;

import application.Operation;
import com.microsoft.z3.*;
import evaluation.bank.AccountObject;
import evaluation.bank.bank;

import java.util.ArrayList;
import java.util.List;


public class AddAccount  implements Operation  {
	
	private String name;
	private Expr accountId;
	private Expr customer;
	private Expr creditLimit;
	private Expr value;
	private Expr accountIndex;
	
	public AddAccount(String name, Context ctx) {
		this.name=name;
		try {		
			AccountObject a=new AccountObject(ctx);
			this.accountId=a.getAccount();
			this.customer=a.getCustomer();
			this.creditLimit=a.getCreditLimit();
			this.accountIndex=a.getIndex();
			this.value=a.getBalance();

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
		
		 return ctx.mkTrue();
	}

	@Override
	public Expr effect(Context ctx) throws Z3Exception {
		
		bank.AccountID_set= ctx.mkSetAdd(bank.AccountID_set, accountId);
		
		value=(IntExpr) ctx.mkITE(ctx.mkGe((ArithExpr) value, (ArithExpr) this.creditLimit), value,
				ctx.mkAdd(new ArithExpr[]{ctx.mkAdd(new ArithExpr[]{(ArithExpr) value, ctx.mkSub(new ArithExpr[]{(ArithExpr) this.creditLimit, (ArithExpr) value})})}));
		
		bank.capacityArray=ctx.mkStore(bank.capacityArray, accountIndex, value);
		
        Expr[] argAccount = new Expr[3];
        argAccount[0]=accountId;
        argAccount[1]=customer;
        argAccount[2]=creditLimit;
		
        Expr account =bank.Account.mkDecl().apply(argAccount);
        bank.Account_set = ctx.mkSetAdd(bank.Account_set,account );
	    return  bank.Account_set ;
	}

	@Override
	public Expr[] getArgs(String name) {
		Expr args []={accountIndex,value};
		return args;
	}

	@Override
	public BoolExpr getCondition(Context ctx, Operation op) throws Z3Exception {
		return ctx.mkTrue();
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
		//Expr conditions []={ctx.mkTrue(), ctx.mkTrue() ,ctx.mkTrue(), ctx.mkTrue(), ctx.mkTrue()};
		//return conditions;
		return null;
	}

	@Override
	public void putConcurrentOp(Context ctx, Operation op) throws Z3Exception {

	}


}
