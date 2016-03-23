package escrow.operations;

import application.Operation;
import com.microsoft.z3.*;
import escrow.CounterWithEscrow;

import java.util.List;

public class Decrement  implements Operation {

	private String name;
	private IntExpr value;
	int  replica;
	
	CounterWithEscrow c=new CounterWithEscrow() ;
	
	public Decrement(String name, IntExpr value) {
		this.name=name;
		this.value=value;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public BoolExpr precondition(Context ctx) throws Z3Exception {
		BoolExpr e1=ctx.mkGe(value, ctx.mkInt(0));
		
	//	CounterWithEscrow.localEcrow.put(site,(IntExpr) ctx.mkConst("localescrow", ctx.mkIntSort()));
		BoolExpr e2=ctx.mkGe(CounterWithEscrow.localEcrowTable.get(replica), value);
		//System.out.println("predddf"+CounterWithEscrow.localEcrow.get(site) );
		return ctx.mkAnd(new BoolExpr[] {  e1,e2});
	}

	@Override
	public Expr effect(Context ctx) throws Z3Exception {
		
		System.out.println("sitepost"+replica);
		CounterWithEscrow.counter=(IntExpr) ctx.mkSub(new ArithExpr[]{CounterWithEscrow.counter, value});
		//site=ctx.MkIntConst(ctx.mkSymbol("r1"));
	//	CounterWithEscrow.localEcrow.put(site,(IntExpr) ctx.mkConst("localescrow", ctx.mkIntSort()));
		System.out.println("predddf"+ CounterWithEscrow.localEcrow);
		
		CounterWithEscrow.localEcrowTable.put(replica, (IntExpr) ctx.mkSub(new ArithExpr[]{CounterWithEscrow.localEcrowTable.get(replica), value}));
		System.out.println("predddf"+ CounterWithEscrow.localEcrow.get(replica) );
		return CounterWithEscrow.localEcrow.get(replica);
	}

	@Override
	public Expr[] getArgs(String name) {
		return new Expr[0];
	}

	@Override
	public BoolExpr getCondition(Context ctx, Operation op) throws Z3Exception {
		return null;
	}

	@Override
	public List<Operation> concurrentOps(Context ctx) {
		return null;
	}


	@Override
	public void putReplicaIndex(Context ctx,int r) throws Z3Exception {		
		replica=r;
	}
	
	
	
	@Override
	public int getReplicaIndex() {
		return replica;
	}

	@Override
	public Expr getConditions(Context ctx, Operation op) throws Z3Exception {
		return null;
	}

	@Override
	public void putConcurrentOp(Context ctx, Operation op) throws Z3Exception {

	}

}
