package invariant;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Z3Exception;

public interface Invariant {
	
    public abstract String getName();
 
    public abstract BoolExpr getInv(Context ctx ) throws Z3Exception ;
  
}
