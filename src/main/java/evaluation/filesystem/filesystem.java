package evaluation.filesystem;

import application.Application;
import application.Operation;
import com.microsoft.z3.*;
import evaluation.filesystem.invariant.*;
import evaluation.filesystem.operations.*;
import invariant.Invariant;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class filesystem extends Application{
	
	
	public static Sort FSObject; 
	public static Expr FSObject_set;
	public static Sort Dir; 
	public static Expr Dir_set;
	public static Sort File;
	public static Expr File_set;
	public static Expr UpdatedFile_set;
	public static Expr UpdatedDir_set;
	
	
	public static TupleSort Parent;
	public static TupleSort Node;
	public static TupleSort UpdatedNode;
	public static TupleSort Reachability;	
	public static EnumSort type;
	
	public List<Operation> op=new ArrayList<Operation>();
	static List<Invariant> inv=new ArrayList<Invariant>();

	public static FuncDecl father,exists ;
	public static Expr rootNode; 
	public static Expr root; 
	
	public static Expr Parent_set;
	public static Expr Node_set;	
	public static Expr Reachable_set;
	
	public static  Sort Name;
	public static Expr Name_set;
	
	public static LinkedList<Expr> state;
	

	public List<Operation> loadOperations(Context ctx) throws Z3Exception {

		Operation o1=new AddFile("AddFile",ctx);
		Operation o2=new RemoveFile("RemoveFile",ctx);
		Operation o3=new UpdateFile("UpdateFile",ctx);
		Operation o4=new MoveFile("MoveFile",ctx);
		Operation o5=new AddDirectory("AddDirectory",ctx);
		Operation o6=new RemoveDirectory("RemoveDirectory",ctx);
		Operation o7=new MoveDirectory("MoveDirectory", ctx);
		Operation o8=new RRemoveDirectory("RecursiveRemove", ctx);

		op.add(o1);
		op.add(o2);
		op.add(o3);
		op.add(o4);
		op.add(o5);
		op.add(o6);
		op.add(o7);
		op.add(o8);
		return op;
		
	}

	@Override
	public List<Invariant> loadInvariants(Context ctx) throws Z3Exception {
		
		Invariant inv1=new  ReachabilityInv("Reachable",ctx);
		Invariant inv2=new NoCycleInv("Nocylce");
		Invariant inv3=new Uniqueness("Uniqness");
		Invariant inv4=new UniqueRoot("UniqueRoot");
		Invariant inv5=new CausalityRelation("CausalityRelation");
	   // Invariant inv6=new Parentship("Parentship");
		
       inv.add(inv1);		
      inv.add(inv2);
           inv.add(inv3);
        inv.add(inv4);
           inv.add(inv5); 
   //   inv.add(inv6);

        return inv;
	}
	
	
	@Override
	public void initializeState(Context ctx) throws Z3Exception {
		
		FSObject = ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject"));
		
	    SetSort ff=ctx.mkSetSort(FSObject);
	    FSObject_set = ctx.mkConst("FSObject_set",ff);
	
		Dir = ctx.mkUninterpretedSort(ctx.mkSymbol("Dir"));
		
	    SetSort dd=ctx.mkSetSort(Dir);
	    Dir_set = ctx.mkConst("Dir_set",dd);


	    
		File = ctx.mkUninterpretedSort("Dir");
		
	    SetSort fl=ctx.mkSetSort(File);
	    File_set = ctx.mkConst("File_set",fl);

	    Symbol name = ctx.mkSymbol("type");
		Symbol[] args={ ctx.mkSymbol("Dir"), ctx.mkSymbol("File")} 	;
		type= ctx.mkEnumSort(name, args);
 
		Name = ctx.mkUninterpretedSort("Name");
		
	    SetSort nn=ctx.mkSetSort(Name);
	    Name_set = ctx.mkConst("Name_set",nn);
	    
	    Node= ctx.mkTupleSort(ctx.mkSymbol("mk_Node_tuple"), // name of
		  	      new Symbol[] { ctx.mkSymbol("first"), ctx.mkSymbol("second") , ctx.mkSymbol("third")}, // names
		  	      new Sort[] { ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")), type ,  ctx.mkUninterpretedSort(ctx.mkSymbol("Name"))} );
  	    
	    SetSort os=ctx.mkSetSort(Node);
	    Node_set = ctx.mkConst("Node_set",os);

	    SetSort uf=ctx.mkSetSort(Node);

	    UpdatedFile_set = ctx.mkConst("UpdatedFile_set",uf);
	    
	    SetSort ud=ctx.mkSetSort(FSObject);

	    UpdatedDir_set = ctx.mkConst("UpdatedDir_set",ud);

	    root=ctx.mkConst("root", ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));

	    filesystem.Dir_set= ctx.mkSetAdd(filesystem.Dir_set,root);
	    
	    Parent= ctx.mkTupleSort(ctx.mkSymbol("mk_Parent_tuple"), // name of
		  	      new Symbol[] { ctx.mkSymbol("first"), ctx.mkSymbol("second") , ctx.mkSymbol("third")  }, // names
		  	      new Sort[] { 	ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")), ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")) ,
		  	      ctx.mkUninterpretedSort(ctx.mkSymbol("Name"))} );
	    
	    SetSort ps=ctx.mkSetSort(Parent);
	    Parent_set = ctx.mkConst("Parent_set",ps);
	    
	    
	    Reachability= ctx.mkTupleSort(ctx.mkSymbol("mk_Reachability_tuple"), // name of
		  	      new Symbol[] { ctx.mkSymbol("first"), ctx.mkSymbol("second"), ctx.mkSymbol("third") }, // names
		  	      new Sort[] { ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")), ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")), ctx.mkBoolSort()  } );
	    
	    SetSort re=ctx.mkSetSort(Reachability);
	    Reachable_set = ctx.mkConst("Reachable_set",re);

		father= ctx.mkFuncDecl("father", new Sort[] { Node ,Node}, ctx.mkBoolSort());
	    exists = ctx.mkFuncDecl("reachable", new Sort[] { ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")) ,ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")) }, ctx.mkBoolSort());
	    
	    state=new LinkedList<Expr>();

	}
	

	@Override
	public BoolExpr getCompoisteInvariant(Context ctx) throws Z3Exception {
		
		BoolExpr composite=ctx.mkTrue();
		for (Invariant i:inv )
		    composite=	ctx.mkAnd(new BoolExpr []{composite, (BoolExpr) i.getInv(ctx)});
		return composite;
	}
	
	
	@Override
	public LinkedList<Expr>  getState(Context ctx) throws Z3Exception {
		
   state.add(Dir_set);
	//   state.add(FSObject_set);
	//   state.add(Node_set)		;
	// state.add(Parent_set);
	//   state.add(Reachable_set);
  //  state.add(UpdatedDir_set);
	//  state.add(UpdatedFile_set);
		
	   return state;
	}

}
