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


public class SequentialCheck {
	
	 @SuppressWarnings("serial")
     class TestFailedException extends Exception {
	       public TestFailedException() {
	            super("Check FAILED");
	       }
	  };
 
	static  List<Operation> op=new ArrayList<Operation>();
    static List<Invariant> invariants=new ArrayList<Invariant>();

	
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
	  public static void main(String[] args)  {
	    
    	  SequentialCheck t =new SequentialCheck();
		  
	     try {
	          Global.ToggleWarningMessages(true);
		      Log.open("test.log");

		      System.out.print("Z3 Major Version: ");
		      System.out.println(Version.getMajor());
		      System.out.print("Z3 Full Version: ");
		      System.out.println(Version.getString());

              HashMap<String, String> cfg = new HashMap<String, String>();
              cfg.put("proof", "true");
              cfg.put("auto-config", "false");
           //   Context ctx = new Context(cfg);
		      Context ctx = new Context();

	          int num_replicas=1;
	          String app_name = "";
	          for (int i = 0; i < args.length; i++) {
	              String arg = args[i];
	              if (arg.equalsIgnoreCase("-r")) {
	    			  if ((i + 1) != args.length) {
	    				  try {
	    				      num_replicas = Integer.parseInt(args[i + 1].trim());
	    				  } catch (Exception e) {
	    					  System.out.println("[ERROR:] An error occurred when parsing the number of replicas");
	    					  return;
	    				  }
	    				  i++;
	    			   } else {
	    					System.out.println("[ERROR:] replica number option doesn't contain the associated parameter");
	    					return;
	    			   }
	    		  }

	              else if (arg.equalsIgnoreCase("-w")) {
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
	            t.LoadApplication(c,ctx,obj);
	            System.out.println("the number of invariants:"+invariants.size());
	            long startTime = System.currentTimeMillis();
	    	    Application app = (Application) obj;

			    for(Invariant inv:invariants)
					System.out.println("stuff/invariant" +inv.getInv(ctx));

			    for(int i=0;i<op.size();i++)  {
					Method m3=c.getDeclaredMethod("initializeState",Context.class);
					m3.invoke(obj,ctx);

					BoolExpr compositeInvariant=ctx.mkTrue();
					for(Invariant inv:invariants)
						compositeInvariant=ctx.mkAnd(new BoolExpr[]{compositeInvariant, (BoolExpr) inv.getInv(ctx)});
				    	
					System.out.println("*********************************************");
					System.out.println("*********************************************");
					System.out.println("*********************************************");
					System.out.println("Operation:"+op.get(i).getName() );
					System.out.println("#############################################");
					for(int k=0;k<num_replicas;k++) {
						m3.invoke(obj,ctx);

						BoolExpr pre=app.preCondition(ctx, op.get(i));
     
						BoolExpr expr=ctx.mkAnd(new BoolExpr[]{pre, compositeInvariant});
						System.out.println("precondition:"+pre);
						System.out.println("#############################################");
						Expr effect=app.apply(ctx, op.get(i));
						System.out.println("effect:"+effect);
						System.out.println("#############################################");
						BoolExpr compositeInvariant2=app.getCompoisteInvariant(ctx);

						BoolExpr correct=ctx.mkImplies(expr, compositeInvariant2);

						Solver s = ctx.mkSolver();

						s.add(ctx.mkNot(correct));
						Status status=s.check();
			              
						System.out.println("assertion: \n"+s.getAssertions()[0]);
						try {
							Model m = t.Check(ctx,ctx.mkNot(correct), Status.SATISFIABLE);
							System.out.println("model:"+m);
						} catch (TestFailedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			         
						System.out.println("result:"+status);

					}
				}
				
				System.out.println("#############################################");
				System.out.println("#############################################");
				System.out.println("#############################################");
				
	            long stopTime = System.currentTimeMillis();
	            long elapsedTime = stopTime - startTime;
	            System.out.println("elapsedTime"+elapsedTime);	        
	            Log.close();
                if (Log.isOpen())
                    System.out.println("Log is still open!");
             } catch (Z3Exception ex)  {
                   System.out.println("Z3 Managed Exception: " + ex.getMessage());
                   System.out.println("Stack trace: ");
                   ex.printStackTrace(System.out);
              } catch (Exception ex)  {
                   System.out.println("Unknown Exception: " + ex.getMessage());
                   System.out.println("Stack trace: ");
                   ex.printStackTrace(System.out);
              }
	   }
}
