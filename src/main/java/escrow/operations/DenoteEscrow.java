package escrow.operations;

import application.Operation;
import com.microsoft.z3.*;
import escrow.CounterWithEscrow;

import java.util.List;

public class DenoteEscrow  implements Operation {

	private String name;
	private IntExpr value;
	int replica1;
	int replica2;
	CounterWithEscrow c=new CounterWithEscrow() ;
	
	public DenoteEscrow(String name, IntExpr value) {
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
	//	CounterWithEscrow.localEcrowTable.put(replica1,ctx.MkIntConst(ctx.mkSymbol("localescrow1")));
		BoolExpr e2=ctx.mkGe(CounterWithEscrow.localEcrowTable.get(replica1), value);
		return ctx.mkAnd(new BoolExpr[] {  e1, e2});
	}

	@Override
	public Expr effect(Context ctx) throws Z3Exception {
		  
		CounterWithEscrow.localEcrowTable.put(replica1,(IntExpr) ctx.mkSub(new ArithExpr[]{CounterWithEscrow.localEcrowTable.get(replica1), value}));
		CounterWithEscrow.localEcrowTable.put(replica2, (IntExpr) ctx.mkAdd(new ArithExpr[]{CounterWithEscrow.localEcrowTable.get(replica2), value}));
		return CounterWithEscrow.localEcrowTable.get(replica1);
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
	public void putReplicaIndex(Context ctx ,int replica) throws Z3Exception {	
		replica1=replica;
		replica2=3;
	}
	

	@Override
	public int getReplicaIndex() {
		return replica1;
	}

	@Override
	public Expr getConditions(Context ctx, Operation op) throws Z3Exception {
		return null;
	}

	@Override
	public void putConcurrentOp(Context ctx, Operation op) throws Z3Exception {

	}

}

