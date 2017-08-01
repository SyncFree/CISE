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

package analyzer;


import application.Application;
import application.Operation;
import com.microsoft.z3.*;
import invariant.Invariant;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Implement the proof rule for  stability of preconditions.
 * The precondition of each operation is
 *  checked against the effect of other 
 * concurrent operations. If the solver finds the 
 * a counter example where the precondition is 
 * not stable after receiving  the effect of other operations,
 * it means that the operation cannot be executed concurrently.
 * @author Najafzadeh
 *
 */
public class StabilityCheck {
	
	 @SuppressWarnings("serial")
    class TestFailedException extends Exception {
	       public TestFailedException() {
	            super("Check FAILED");
	       }
	  };
 
	static  List<Operation> op=new ArrayList<Operation>();
    static List<Invariant> invariants=new ArrayList<Invariant>();
	
	 
    public  Model Check(Context ctx, BoolExpr f, Status sat) throws Z3Exception,
      TestFailedException {
        Solver s = ctx.mkSolver();
        s.add(f);
        if (s.check() != sat)
       	 return null;
        if (sat == Status.SATISFIABLE)
           return s.getModel();
       else
          return null;
    }

    
	/**
	 * Initialize an application with adding its operations and 
	 * invariants.
	 * @param app  the class of application
	 * @param ctx Z3 context 
	 * @param obj an instance of the application 
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void LoadApplication(Class app,Context ctx,Object obj) throws ClassNotFoundException, Exception {
		
    	Method m3=app.getDeclaredMethod("initializeState",Context.class);
        m3.invoke(obj,ctx);
        
		Method m1=app.getDeclaredMethod("loadOperations" , Context.class);
	    op=(List<Operation>) m1.invoke(obj,ctx);
	    System.out.println("the number of operations:"+op.size());
	    
	    Method m2=app.getDeclaredMethod("loadInvariants",Context.class);
	    invariants=(List<Invariant>) m2.invoke(obj,ctx);

    }


	
    /**
     * Check whether the precondition of a operation is 
     * stable under the effect of another 
     * concurrent  update operation.
     * @param ctx Z3 context
     * @param Op an update operation
     * @param shadowOp  the effect of a specified update
     * @param obj an instance of the application
     * @return true if the precondition is stable under the effect of a concurrent update, otherwise it returns false
     * @throws Z3Exception
     * @throws TestFailedException
     */
    @SuppressWarnings("unchecked")
	public BoolExpr stabilityCheck(Context ctx,Operation Op,Operation shadowOp, Application app , Object obj) throws Z3Exception, TestFailedException {
 	   BoolExpr expr=null;
	  
	   try {
		//   m1 = c.getDeclaredMethod("apply",Context.class,Operation.class);;
		//  m1.invoke( obj, ctx,shadowOp);
		   Expr e=app.apply(ctx, shadowOp);
		   System.out.print("effect"+e);
		
		//Method m2=c.getDeclaredMethod("preCondition",Context.class,Operation.class);
	//	expr= (BoolExpr) m2.invoke( obj,ctx, Op);
		   expr=app.preCondition(ctx, Op);
		  // expr=ctx.mkAnd(new BoolExpr[] {expr,(BoolExpr) e});
		   System.out.println("preeee"+expr);
	} catch (IllegalArgumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      catch (SecurityException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   
//	   Method m2=c.getDeclaredMethod("preCondition",Context.class,Operation.class);
	//   System.out.println("operation"+Op.getName());
//	   expr= (BoolExpr) m2.invoke( obj,ctx, Op);
//	   expr=  ((Application) obj).preCondition(ctx,Op);	    	
	   return expr;
    }	 
	
    @SuppressWarnings("unchecked")
	public static void main(String[] args)  {
    	StabilityCheck st = new StabilityCheck();
        try {
            Global.ToggleWarningMessages(true);
            Log.open("test.log");

            System.out.print("Z3 Major Version: ");
            System.out.println(Version.getMajor());
            System.out.print("Z3 Full Version: ");
            System.out.println(Version.getString());
            Context ctx = new Context();

	          String app_name = "";
	          for (int i = 0; i < args.length; i++) {
	              String arg = args[i];	                
	              if (arg.equalsIgnoreCase("-w")) {
 				     if ((i + 1) != args.length) {
 					    app_name = args[i + 1].trim().toLowerCase();
 					    i++;
 				  } else {
 					   System.out.println("[ERROR:] application alias option doesn't contain associated parameter");
 					   return;
 				    }
 			     }
	           }
	            
	       @SuppressWarnings("rawtypes")
		    Class c = Class.forName(app_name);
	        Object obj=c.newInstance();
	        st.LoadApplication(c,ctx,obj);
	    //    System.out.println("the number of operations:"+op.size());
	     //   System.out.println("the number of invariants:"+invariants.size());
	        long startTime = System.currentTimeMillis();
   			Method m3=c.getDeclaredMethod("initializeState",Context.class);
   		    System.out.println("the number of operations:"+op.size());
	        System.out.println("the number of invariants:"+invariants.size());
	        
	        
	     //   List<Operation> opCom=new ArrayList<Operation>();
	        
	        Object obj2=c.newInstance();
	
	        
	        
		    for(int i=0;i<1;i++)  {	
		    	System.out.println("##############################################");
		    	System.out.println("##############################################");
		    	System.out.println("stability checking for:  "+op.get(i).getName());

    			List<String> conflictingOps=new ArrayList<String>();
    			List<Operation> opCom=new ArrayList<Operation>();
    			
    			Map<String,List<String>> set= new HashMap<String,List<String>>(); 

		    	 Method m1=c.getDeclaredMethod("loadOperations" , Context.class);
		    	 opCom=(List<Operation>) m1.invoke(obj,ctx);
		    	 System.out.println("size"+opCom);
    		    for(int k=0;k<opCom.size();k++) {
    
    		    	  m3=c.getDeclaredMethod("initializeState",Context.class);
				     System.out.println("compare with:"+opCom.get(k).getName()); 
				     Application app = (Application) obj;
				     
				     BoolExpr pre=app.preCondition(ctx, op.get(i));
				     
					// Method m1=c.getDeclaredMethod("preCondition",Context.class,Operation.class);
			    	// BoolExpr pre= (BoolExpr) m1.invoke( obj,ctx, op.get(i));
			    	 
			    	 System.out.println("pre"+pre);				    	
				 //  Method m2 = c.getDeclaredMethod("CheckInvariants",Context.class,String.class,Map.class);
				 //  BoolExpr I= (BoolExpr) ( m2.invoke( obj, ctx,invariants.get(j),state));				    	
				 //  BoolExpr expr=ctx.mkAnd(new BoolExpr[] { pre,I})

			  	     Expr e=app.apply(ctx, opCom.get(k));
				     System.out.println("effect"+e);
				
				//Method m2=c.getDeclaredMethod("preCondition",Context.class,Operation.class);
			//	expr= (BoolExpr) m2.invoke( obj,ctx, Op);
		
				      BoolExpr expr=ctx.mkAnd(new BoolExpr[]{(BoolExpr) e, pre})	;
			    	 
				      BoolExpr pre2=app.preCondition(ctx, op.get(i));
				      
			 	  //   BoolExpr correct=ctx.mkImplies(pre,st.stabilityCheck(ctx,op.get(i),op.get(k),app,obj));
				     BoolExpr correct=ctx.mkImplies(expr, pre2);
			 	 //    System.out.println("stability without adding condition"+correct);
			 		 Solver s3 = ctx.mkSolver();
				     s3.add(ctx.mkNot(correct));
			         Status status=s3.check();
			         System.out.println("assertion"+s3.getAssertions()[0]);
			         Model m = st.Check(ctx,ctx.mkNot(correct), Status.SATISFIABLE);
					 System.out.println("modeellll"+m);
			       
			         m3.invoke(obj,ctx);
			         System.out.println("result:"+status);
			          if(status==Status.SATISFIABLE) {
			            	conflictingOps.add(opCom.get(k).getName()) ;
			        	 //   break;
			          }
			            
			      }
    		      m3.invoke(obj,ctx);
			      set.put(op.get(i).getName(), conflictingOps);
			      System.out.println("check finishes:"+set.get(op.get(i).getName()));
			}
			
	   
			
           long stopTime = System.currentTimeMillis();
           long elapsedTime = stopTime - startTime;
           System.out.println("elapsedTime:"+elapsedTime);
        
           Log.close();
           if (Log.isOpen())
               System.out.println("Log is still open!");
    } catch (Z3Exception ex) {
        System.out.println("Z3 Managed Exception: " + ex.getMessage());
        System.out.println("Stack trace: ");
        ex.printStackTrace(System.out);
    } catch (Exception ex) {
        System.out.println("Unknown Exception: " + ex.getMessage());
        System.out.println("Stack trace: ");
        ex.printStackTrace(System.out);
    }
 }
    

}



