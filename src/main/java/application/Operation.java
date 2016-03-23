package application;


import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.Z3Exception;

import java.util.List;

public interface Operation {	
	
	
	/**
	 * @return the name of operation
	 */
	public abstract String getName(); 	

    /**
     * Compute the precondition of the operation in z3 context.
     * @param ctx Z3 context
     * @return a boolean expression in Z3 context which represents precondition of operation 
     * @throws Z3Exception 
     */
    public abstract BoolExpr precondition(Context ctx) throws Z3Exception ;

    /**
     * Compute the effect of the operation in z3 context.
     * @param ctx Z3 context
     * @return an expression in Z3 context which represents the effect of operation 
     * @throws Z3Exception
     */
    public abstract Expr effect(Context ctx) throws Z3Exception;  
    
	/**
	 * @param name 
	 * @return an expression in Z3 context which represents the arguments of operation 
	 */
	public abstract Expr[] getArgs(String name);

	/**
	 * @param ctx Z3 context
	 * @param op an operation 
	 * @return an expression in Z3 which represents  the conditions  required for concurrently execution the operation with op
	 * @throws Z3Exception
	 */
	public abstract BoolExpr getCondition(Context ctx, Operation op) throws Z3Exception;


	/**
	 * @param ctx Z3 context
	 * @return a list of possible operations,  which can be concurrently executed with this operation
	 */
	public abstract List<Operation> concurrentOps(Context ctx);


    /**
     * This method is used for bounded concurrency
     * @param ctx 
     * @param replica assign a source replica into the operation
     * @throws Z3Exception
     */
    public abstract void putReplicaIndex(Context ctx, int replica) throws Z3Exception;
	
    /**
     * @return the id of the source replica for this operation
     */
    public abstract int getReplicaIndex();

    public abstract Expr getConditions(Context ctx, Operation op) throws Z3Exception;
	
    public abstract void putConcurrentOp(Context ctx, Operation op) throws Z3Exception; 


 }