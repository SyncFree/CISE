package escrow.invariants;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Z3Exception;
import escrow.CounterWithEscrow;
import invariant.Invariant;

public class BoundedInv implements Invariant  {
	
	String name;

	public BoundedInv(String name) {
		this.name=name;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public BoolExpr getInv(Context ctx) throws Z3Exception {
		BoolExpr expr=ctx.mkGe(CounterWithEscrow.counter, ctx.mkInt("0"));
		return expr;
	}



}
