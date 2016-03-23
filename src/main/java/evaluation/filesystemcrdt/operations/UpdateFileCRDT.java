package evaluation.filesystemcrdt.operations;

import application.Operation;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.Z3Exception;
import evaluation.filesystemcrdt.filesystemcrdt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UpdateFileCRDT implements Operation {


	private String name;

	private Expr file;
	private Expr dirId;
	private Expr fileId;
	private Expr fname;
	int index;
	
	public UpdateFileCRDT(String name, Context ctx) {
		
		this.name=name;
		try {

			 Random rand=new Random();
		     index=rand.nextInt();
		     
			 this.dirId=ctx.mkConst("dir3"+index, ctx.mkUninterpretedSort(ctx.mkSymbol("Dir")));
	   	 	 this.fileId=ctx.mkConst("node"+index, ctx.mkUninterpretedSort(ctx.mkSymbol("FSObject")));
	   	 	
		     Expr[] nodeArgs1 =  new Expr[3];
		     nodeArgs1[0]=fileId;
		     nodeArgs1[1]=filesystemcrdt.type.getConsts()[1];
		     nodeArgs1[2]=ctx.mkConst("name1"+index, ctx.mkUninterpretedSort(ctx.mkSymbol("Name")));
		     fname=nodeArgs1[2];	   	 
	   	 	 this.file=filesystemcrdt.Node.mkDecl().apply(nodeArgs1);
	   	 	
		
		} catch (Z3Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getReplicaIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BoolExpr precondition(Context ctx) throws Z3Exception {
		
		return  (BoolExpr) ctx.mkSetMembership(file, filesystemcrdt.Node_set);
	}

	@Override
	public Expr effect(Context ctx) throws Z3Exception {
		
		
	    Expr[] argParent= new Expr[2];
	    argParent[0]=dirId;
	    argParent[1]=fileId;	
		filesystemcrdt.UpdatedFile_set= ctx.mkSetAdd(filesystemcrdt.UpdatedFile_set,file);
		return filesystemcrdt.UpdatedFile_set;
	}


	@Override
	public void putReplicaIndex(Context ctx, int r) throws Z3Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Expr[] getArgs(String name) {
		Expr [] args={fname, dirId, fileId, file};
		return args;
	}

	@Override
	public BoolExpr getCondition(Context ctx, Operation op) throws Z3Exception {
		
		 return ctx.mkTrue();
	}

	@Override
	public List<Operation> concurrentOps(Context ctx) {
		
		List<Operation> op=new ArrayList<Operation>();

		Operation o1=new AddFileCRDT("AddFile",ctx);
		Operation o2=new RemoveFileCRDT("RemoveFile",ctx);
		Operation o3=new UpdateFileCRDT("UpdateFile",ctx);
		Operation o4=new MoveFileCRDT("MoveFile",ctx);
		Operation o5=new AddDirectoryCRDT("AddDirectory",ctx);
		Operation o6=new RemoveDirectoryCRDT("RemoveDirectory",ctx);
		Operation o7=new MoveDirectoryCRDT("MoveDirectory", ctx);
		
		op.add(o1);
		op.add(o2);
		op.add(o3);
		op.add(o4);
		op.add(o5);
		op.add(o6);		
		op.add(o7);	
		
		return op;
	}

	@Override
	public Expr getConditions(Context ctx, Operation op) throws Z3Exception {
		// TODO Auto-generated method stub
		return ctx.mkTrue();
	}

	@Override
	public void putConcurrentOp(Context ctx, Operation op) throws Z3Exception {
		// TODO Auto-generated method stub
		
	}

}
