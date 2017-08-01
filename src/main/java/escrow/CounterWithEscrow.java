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

package escrow;

import application.Application;
import application.Operation;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Z3Exception;
import escrow.invariants.BoundedInv;
import escrow.operations.AcquireEscrow;
import escrow.operations.Decrement;
import escrow.operations.DenoteEscrow;
import escrow.operations.Increment;
import invariant.Invariant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CounterWithEscrow extends Application {

	public static IntExpr counter;
	public static IntExpr globalEscrow;	
	public List<Operation> op=new ArrayList<Operation>();
	static List<Invariant> inv=new ArrayList<Invariant>();
	public static IntExpr value;	
	public static Map<IntExpr,IntExpr> localEcrow= new HashMap<IntExpr,IntExpr>();

	public static Map<Integer,IntExpr> localEcrowTable= new HashMap<Integer,IntExpr>();
	
    /**
     * The constructor initializes the application with adding operations and invariants
     */
    public CounterWithEscrow() {
	    		 
    }
 	
	@Override
	public List<Operation> loadOperations(Context ctx) throws Z3Exception {
		Operation o1=new AcquireEscrow("AcquireEscrow",value);
		Operation o2=new DenoteEscrow("DenoteEscrow",value);
		Operation o3=new Increment("Increment", value);
		Operation o4=new Decrement("Decrement",value);
		op.add(o1);
		op.add(o2);
		op.add(o3);
		op.add(o4);		
		return op;
	}

	@Override
	public List<Invariant> loadInvariants(Context ctx) throws Z3Exception {
		Invariant inv1=new BoundedInv("bounded");
        inv.add(inv1);
        return inv;
	}

	@Override
	public void initializeState(Context ctx) throws Z3Exception {
	    counter=(IntExpr) ctx.mkConst("counter", ctx.mkIntSort());
	    globalEscrow=(IntExpr) ctx.mkConst("globalEscrow", ctx.mkIntSort());
	    value=(IntExpr) ctx.mkConst("value", ctx.mkIntSort());
	    
	    localEcrowTable.put(1, (IntExpr) ctx.mkConst("localescrow1", ctx.mkIntSort()));
	    localEcrowTable.put(2, (IntExpr) ctx.mkConst("localescrow2", ctx.mkIntSort()));
	    localEcrowTable.put(3, (IntExpr) ctx.mkConst("localescrow3", ctx.mkIntSort()));
	    
	}

	@Override
	public BoolExpr getCompoisteInvariant(Context ctx) throws Z3Exception {
		return null;
	}


}
