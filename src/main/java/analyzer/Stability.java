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


public class Stability {
	
	 @SuppressWarnings("serial")
    class TestFailedException extends Exception {
	       public TestFailedException() {
	            super("Check FAILED");
	       }
	 };
 
	static  List<Operation> op=new ArrayList<Operation>();
    static List<Invariant> invariants=new ArrayList<Invariant>();
    static Expr matrix [][];
	 
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
   
	@SuppressWarnings("unchecked")
	public void LoadApplication(Class app,Context ctx,Object obj) throws ClassNotFoundException, Exception {
		
    	Method m1=app.getDeclaredMethod("initializeState",Context.class);
        m1.invoke(obj,ctx);
        
		Method m2=app.getDeclaredMethod("loadOperations" , Context.class);
	    op=(List<Operation>) m2.invoke(obj,ctx);
	  //  System.out.println("the number of operations:"+op.size());
	    
	    Method m3=app.getDeclaredMethod("loadInvariants",Context.class);
	    invariants=(List<Invariant>) m3.invoke(obj,ctx);
	    
	//   Method m4=app.getDeclaredMethod("getConcurrenyMatrix",Context.class);
	//    matrix= (Expr[][]) m4.invoke(obj,ctx);

    }
	
    @SuppressWarnings("unchecked")
	public static void main(String[] args)  {
    	Stability st = new Stability();
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
	      
	        
	        Application app = (Application) obj;
	        st.LoadApplication(c,ctx,obj);
	       // System.out.println("the number of operations:"+op.size());
	       // System.out.println("the number of invariants:"+invariants.size());
	        
	        long startTime = System.currentTimeMillis();
   			Method m3=c.getDeclaredMethod("initializeState",Context.class);
   			
            BoolExpr compositeInvariant=ctx.mkTrue();
   			for(Invariant inv:invariants)
   				compositeInvariant=ctx.mkAnd(new BoolExpr []{compositeInvariant,(BoolExpr) inv.getInv(ctx)});
 		 
   			
   		/*	for(int i=0; i<4; i++) {
   				for(int j=0; j<4; j++)
   					System.out.print(matrix[i][j] + " ");
   				System.out.println();
   			}*/
   				
   			
		    for(int i=0;i<op.size();i++)  {	
		    	System.out.println("##############################################");
		    	System.out.println("##############################################");
		    	System.out.print("stability checking for  "+op.get(i).getName());

    			List<String> conflictingOps=new ArrayList<String>();
    			Map<String,List<String>> set= new HashMap<String,List<String>>(); 
    			List<Operation> concurrentOps=new ArrayList<Operation>();
    			concurrentOps=op.get(i).concurrentOps(ctx);
    			
    		    for(int k=0;k<concurrentOps.size();k++) {
    		    	
    		    	 m3=c.getDeclaredMethod("initializeState",Context.class);
    		    	
    		    	// System.out.println("**************************************************************************");
				     System.out.print("  versus "+concurrentOps.get(k).getName());
    		   	     BoolExpr preOld= app.preCondition(ctx, op.get(i));
    		   	     System.out.println("\n");
		    	     System.out.println("Precondition before applying effector:"+preOld);
		    	     
		    	//     BoolExpr I1= (BoolExpr) invariants.get(0).getInv(ctx);		
		    	//     BoolExpr I2= (BoolExpr) invariants.get(1).getInv(ctx);	
		    	     
					 
					 System.out.println("\n");
				     BoolExpr condition=(BoolExpr) op.get(i).getCondition(ctx, concurrentOps.get(k));
					// BoolExpr condition=ctx.mkTrue();
					 System.out.println("Concurrency control:"+condition);
	    	     
					 BoolExpr exprOld=ctx.mkAnd(new BoolExpr[] { preOld,compositeInvariant, condition});
	    	     
					 
					 concurrentOps.get(k).putConcurrentOp(ctx, op.get(i));
					 
					 System.out.println("\n");
				     System.out.println("Applying shadowOp: "+concurrentOps.get(k).effect(ctx));
				     System.out.println("\n");
					 BoolExpr preNew=app.preCondition(ctx, op.get(i));
					 System.out.println("Precondition after receiving shadowOp:"+preNew);
					 System.out.println("\n");
					 
					 BoolExpr stabilityCheck=ctx.mkImplies(exprOld,preNew);
			 	     
			 	//     
			 	//   BoolExpr condition=(BoolExpr) matrix[i+1][k+1];

			 	//   BoolExpr correct=ctx.mkImplies(condition, stabilityCheck);
			 	     
			 	     Solver s3 = ctx.mkSolver();
				     s3.add(ctx.mkNot(stabilityCheck));
			         Status status=s3.check();
			         System.out.println("Assertion formula:"+s3.getAssertions()[0]);
			         System.out.println("\n");
			         Model model = st.Check(ctx,ctx.mkNot(stabilityCheck), Status.SATISFIABLE);
					 System.out.println("counter example:"+model);
			       
			         m3.invoke(obj,ctx);
			        // System.out.println("result:"+status);
			          if(status==Status.SATISFIABLE) {
			            	conflictingOps.add(concurrentOps.get(k).getName()) ;		            
			        	 //   break;
			          }
			            
			      }
    		      m3.invoke(obj,ctx);
			      set.put(op.get(i).getName(), conflictingOps);
			     
			      System.out.println("check finishes, " /*+ op.get(i).getName()+ */+ "Conflicting Operations:"+set.get(op.get(i).getName()));
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



