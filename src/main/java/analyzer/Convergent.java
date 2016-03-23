package analyzer;


import application.Application;
import application.Operation;
import com.microsoft.z3.*;
import evaluation.filesystem.invariant.IdUniqness;
import invariant.Invariant;

import java.lang.reflect.Method;
import java.util.*;

public class Convergent {
	
	 @SuppressWarnings("serial")
    class TestFailedException extends Exception {
	       public TestFailedException() {
	            super("Check FAILED");
	       }
	  };
 
	static  List<Operation> op=new ArrayList<Operation>();
    static List<Invariant> invariants=new ArrayList<Invariant>();
    static Expr matrix [][];
    
    static LinkedList<Expr> state1=new LinkedList<Expr>();
    static LinkedList<Expr> state2=new LinkedList<Expr>();
	 
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
	    System.out.println("the number of operations:"+op.size());
	    
        Method m3=app.getDeclaredMethod("loadInvariants",Context.class);
	    invariants=(List<Invariant>) m3.invoke(obj,ctx);
	    
	//   Method m4=app.getDeclaredMethod("getConcurrenyMatrix",Context.class);
	//    matrix= (Expr[][]) m4.invoke(obj,ctx);

    }
	
    @SuppressWarnings("unchecked")
	public static void main(String[] args)  {
    	Convergent st = new Convergent();
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
	        
	        System.out.println("the number of operations:"+op.size());
	        System.out.println("the number of invariants:"+invariants.size());
	        
	        long startTime = System.currentTimeMillis();
   			Method m3=c.getDeclaredMethod("initializeState",Context.class);

   			Method m4 = c.getDeclaredMethod("getState",Context.class);	 

            BoolExpr compositeInvariant=ctx.mkTrue();
   			for(Invariant inv:invariants)
   				compositeInvariant=ctx.mkAnd(new BoolExpr []{compositeInvariant,(BoolExpr) inv.getInv(ctx)});
   			
		    for(int i=0;i<op.size();i++)  {	
		    	System.out.println("##############################################");
		    	System.out.println("##############################################");
		    	System.out.println("convergent checking for:  "+op.get(i).getName());

    			List<String> conflictingOps=new ArrayList<String>();
    			Map<String,List<String>> set= new HashMap<String,List<String>>(); 
    			List<Operation> concurrentOps=new ArrayList<Operation>();
    			concurrentOps=op.get(i).concurrentOps(ctx);
    			
    		    for(int k=0;k<concurrentOps.size();k++) {
    		    	
    		    	 m3=c.getDeclaredMethod("initializeState",Context.class);
    		    	 m3.invoke(obj,ctx);
    		    	
    		    	 System.out.println("**************************************************************************");
				     System.out.println("Compare with:"+concurrentOps.get(k).getName()); 

				     
	 		   	     BoolExpr preOld1= app.preCondition(ctx, op.get(i));
	 		   	     BoolExpr preOld2= app.preCondition(ctx, concurrentOps.get(k));
		    	     
	 		   	     op.get(i).putConcurrentOp(ctx, concurrentOps.get(k));
	 		   	 
					 System.out.println("\n");
					 System.out.println("Applying op: "+ op.get(i).effect(ctx));
					 

				     System.out.println("\n");
					 
					 concurrentOps.get(k).putConcurrentOp(ctx, op.get(i));
					 
					 System.out.println("\n");
					 
					 concurrentOps.get(k).putConcurrentOp(ctx, op.get(i));
				     System.out.println("Applying shadowOp: "+concurrentOps.get(k).effect(ctx));


				     System.out.println("\n");
			   			
			   	     state1= (LinkedList<Expr>) m4.invoke(obj,ctx);

			   	 	System.out.println("state1");
			   	 	
			   	     for (int p=0;p<state1.size();p++ )
			   	    	System.out.println(state1.get(p));

    		         m3.invoke(obj,ctx);
    		         
    		    	 System.out.println("**************************************************************************");
					 
					 System.out.println("\n");
					 
					 concurrentOps.get(k).putConcurrentOp(ctx, op.get(i));
					 
				     System.out.println("Applying shadowOp: "+ concurrentOps.get(k).effect(ctx) );

				     System.out.println("\n");

					 System.out.println("\n");
				     System.out.println("Applying op: "+op.get(i).effect(ctx));

				     System.out.println("\n");
    		         
			   	     state2= (LinkedList<Expr>) m4.invoke(obj,ctx);
			   	     

			 	 	 System.out.println("state2");
			   	     for (int p=0;p<state2.size();p++ )
			   	    	System.out.println(state2.get(p));
			   	     
			   	     BoolExpr compare=ctx.mkTrue();
			   	     
			   	     for (int p=0;p<state1.size();p++ )
			   	    	compare=ctx.mkAnd(new BoolExpr [] {compare, ctx.mkEq(state1.get(p), state2.get(p))});
			   	     
			   	     
					 System.out.println("\n");
					 BoolExpr condition=(BoolExpr) op.get(i).getConditions(ctx, concurrentOps.get(k));
				//	 BoolExpr condition=ctx.mkTrue();
					 System.out.println("Concurrency condition:"+condition); 
		 
					 
				//	 BoolExpr cond= ctx.mkEq(ss1, ss2);
					 
					 IdUniqness ie=new IdUniqness("IdUniqness");;
		 
					 BoolExpr er= ctx.mkImplies(ctx.mkAnd(new BoolExpr [] {preOld1, preOld2,condition }), compare);

			 	     Solver s3 = ctx.mkSolver();
				     s3.add(ctx.mkNot(compare));
			         Status status=s3.check();
			         System.out.println("Assertion formula:"+s3.getAssertions()[0]);
			         System.out.println("\n");
			         Model model = st.Check(ctx,ctx.mkNot(compare), Status.SATISFIABLE);
					 System.out.println("counter example:"+model);

			        
			         System.out.println("result:"+status);
			          if(status==Status.SATISFIABLE) {
			            	conflictingOps.add(concurrentOps.get(k).getName()) ;		            
			        	 //   break;
			          }
			            
			      }
    		      m3.invoke(obj,ctx);
			      set.put(op.get(i).getName(), conflictingOps);
			     
			      System.out.println("check finishes for operation "+ op.get(i).getName()+ ": \n "+ "Conflicting Operations:"+set.get(op.get(i).getName()));
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



