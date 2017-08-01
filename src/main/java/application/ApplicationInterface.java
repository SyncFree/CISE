/*******************************************************************************
 * ===========================================================
 * This file is part of the CISE tool software.
 *
 * The CISE tool software contains proprietary and confidential information of Inria.
 * All rights reserved. Reproduction, adaptation or distribution, in whole or in part, is
 * forbidden except by express written permission of Inria.
 * Version V1.5.1., July 2017
 * Authors: Mahsa Najafzadeh, Michał Jabczyński, Marc Shapiro
 * Copyright (C) 2017, Inria
 * ===========================================================
 ******************************************************************************/

package application;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.Z3Exception;
import invariant.Invariant;

import java.util.List;

/**
 * Common interface for applications.
 * Every application  is well-defined with a set of operations and invariants.
 * Each operation has a precondition and an effect over the state of system.
 * The state  is defined as a set of pairs of  objects and their value.
 * 
 * @author Najafzadeh
 */


public interface ApplicationInterface {
	
    
    /**
     * @return The list of operations 
     */
    public  List<Operation> loadOperations(Context ctx) throws Z3Exception;
	
	/**
	 * @return The list of invariants
	 */
	public  List<Invariant> loadInvariants(Context ctx) throws Z3Exception;
	
	/**
	 * Define and initialize the set of operations and invariants in Z3 context.
	 * @param ctx  Z3 context
	 * @return an initial state which maps each object into an initial value(state) in z3 context           
	 * @throws Z3Exception  the exception base class for error reporting from Z3
	 */
	public  void initializeState(Context ctx) throws Z3Exception ;
	
	/**
	 * Apply the effect of an update into a state.
	 * @param ctx   Z3 context
	 * @param op   an update operation
	 * @return a new state implying the effect of the operation
	 * @throws Z3Exception the exception base class for error reporting from Z3
	 */
	public  Expr apply( Context ctx,Operation op) throws Z3Exception;
	
	
	/**
	 * Return the precondition of an operation.
	 * @param ctx Z3 context
	 * @param op  an  update operation
	 * @return the precondition of the operation in z3 context
	 * @throws Z3Exception  the exception base class for error reporting from Z3
	 */
	public  BoolExpr preCondition(Context ctx, Operation op) throws Z3Exception ;
	 

	/**
	 * Check whether a  state holds an invariant.
	 * @param ctx Z3 context
	 * @param inv an application invariant 
	 * @return true if the state preserves the invariant 
	 * @throws Z3Exception  the exception base class for error reporting from Z3
	 */
	public  BoolExpr getInvariant(Context ctx, Invariant inv) throws Z3Exception ;


	public  BoolExpr getCompoisteInvariant(Context ctx) throws Z3Exception ;
    
}
