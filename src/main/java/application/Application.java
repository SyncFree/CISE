package application;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.Z3Exception;
import invariant.Invariant;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * @author najafzadeh
 *
 */
public  abstract  class Application implements ApplicationInterface  {
	
    List<Operation> op=new ArrayList<Operation>();
    List<Invariant> inv=new ArrayList<Invariant>();
    
    public Application() {  
    }
      
    public abstract List<Operation> loadOperations(Context ctx) throws Z3Exception;
	
	public abstract List<Invariant> loadInvariants(Context ctx) throws Z3Exception ;
	
	public abstract void initializeState( Context ctx) throws Z3Exception ;
	
	public  Expr apply( Context ctx,Operation op) throws Z3Exception {
		return (op.effect(ctx));		
	}
	
	public BoolExpr preCondition(Context ctx, Operation op) throws Z3Exception {
		return(op.precondition(ctx));	
	}

	 
	public  BoolExpr getInvariant(Context ctx, Invariant inv) throws Z3Exception {
		return  inv.getInv(ctx);
	}

	public LinkedList<Expr> getState(Context ctx) throws Z3Exception {
		// TODO Auto-generated method stub
		return null;
	}


  
	
}