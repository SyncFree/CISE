package escrow.operations;

import application.Operation;
import com.microsoft.z3.*;
import escrow.CounterWithEscrow;

import java.util.List;

public class AcquireEscrow  implements Operation  {

	private String name;
	private IntExpr value;
	int replica;
	CounterWithEscrow c=new CounterWithEscrow() ;
	
	public AcquireEscrow(String name, IntExpr value) {
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
		BoolExpr e2=ctx.mkGe(CounterWithEscrow.globalEscrow, value);
		BoolExpr pre= ctx.mkAnd(new BoolExpr[] {  e1, e2});
		return pre;
	}

	@Override
	public Expr effect(Context ctx) throws Z3Exception {
		CounterWithEscrow.globalEscrow=(IntExpr) ctx.mkSub(new ArithExpr[]{CounterWithEscrow.globalEscrow, value});
	//	CounterWithEscrow.localEcrowTable.put(replica,ctx.MkIntConst(ctx.mkSymbol("localescrow")));
	    CounterWithEscrow.localEcrowTable.put(replica, (IntExpr) ctx.mkAdd(new ArithExpr[]{CounterWithEscrow.localEcrowTable.get(replica), value}));
		return CounterWithEscrow.globalEscrow;
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
