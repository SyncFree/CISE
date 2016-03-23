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
public class StabilityCheckArgument {
	
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
     * @param app  the class of application
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
		   System.out.println("post"+e);
		//Method m2=c.getDeclaredMethod("preCondition",Context.class,Operation.class);
	//	expr= (BoolExpr) m2.invoke( obj,ctx, Op);
		   expr=app.preCondition(ctx, Op);
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
    	StabilityCheckArgument st = new StabilityCheckArgument();
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
   		    System.out.println("the number of operationssss:"+op.size());
	        System.out.println("the number of invariants:"+invariants.size());
	        List<Operation> opCom=new ArrayList<Operation>();
	        
	        Class c2 = Class.forName(app_name);
	        Object obj2=c2.newInstance();
	    	Method m1=c2.getDeclaredMethod("loadOperations" , Context.class);
	    	opCom=(List<Operation>) m1.invoke(obj2,ctx);


	    	for(int k=0;k<opCom.size();k++) {
	    		opCom.get(k).putReplicaIndex(ctx,2);
	    		op.get(k).putReplicaIndex(ctx,1);
	    	}
	    	
	        
		    for(int i=0;i<op.size();i++)  {	
		    	System.out.println("##############################################");
		    	System.out.println("##############################################");
		    	System.out.println("stability checking for:  "+op.get(i).getName());

    			List<String> conflictingOps=new ArrayList<String>();
    			Map<String,List<String>> set= new HashMap<String,List<String>>(); 
    		    
    			op.get(i).putReplicaIndex(ctx,1);
    	
    		    for(int k=0;k<opCom.size();k++) {
    		    	 m3=c.getDeclaredMethod("initializeState",Context.class);
				     System.out.println("compare with:"+op.get(k).getName()); 
				     Application app = (Application) obj;
			 		
				     System.out.println("siteeee:"+opCom.get(k).getReplicaIndex()); 
				     System.out.println("siteeee:"+op.get(i).getReplicaIndex()); 
				     
				     BoolExpr pre=app.preCondition(ctx, op.get(i));
				     
					// Method m1=c.getDeclaredMethod("preCondition",Context.class,Operation.class);
			    	// BoolExpr pre= (BoolExpr) m1.invoke( obj,ctx, op.get(i));
			    	 
			    	 System.out.println("pre"+pre);				    	
				 //  Method m2 = c.getDeclaredMethod("CheckInvariants",Context.class,String.class,Map.class);
				 //  BoolExpr I= (BoolExpr) ( m2.invoke( obj, ctx,invariants.get(j),state));				    	
				 //  BoolExpr expr=ctx.mkAnd(new BoolExpr[] { pre,I})
			    	
			 	     BoolExpr correct=ctx.mkImplies(pre,st.stabilityCheck(ctx,op.get(i),opCom.get(k),app,obj));
			 	     System.out.println("correct"+correct);
			 		 Solver s3 = ctx.mkSolver();
			 		 
			 	
			 		// BoolExpr correct1=ctx.mkImplies(ctx.mkNot(ctx.mkEq(op.get(i).getReplicaIndex(),op.get(k).getReplicaIndex())),correct);
				     s3.add(ctx.mkNot(correct));
			         Status status=s3.check();
			         System.out.println("assertion"+s3.getAssertions()[0]);
			         Model m = st.Check(ctx,ctx.mkNot(correct), Status.SATISFIABLE);
					 System.out.println("modeellll"+m);
			       
			         m3.invoke(obj,ctx);
			         System.out.println("result:"+status);
			          if(status==Status.SATISFIABLE) {
			            	conflictingOps.add(op.get(k).getName()) ;
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



