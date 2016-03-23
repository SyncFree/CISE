package evaluation.courseware;

import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Z3Exception;

public class courseObject {

	public Expr course;
	public IntExpr capacityArrayIndex;
	public IntExpr capacity;
	
	public  courseObject(Context ctx) throws Z3Exception {
		
		this.course=ctx.mkConst("course", ctx.mkUninterpretedSort(ctx.mkSymbol("Course")));
		this.capacityArrayIndex=(IntExpr) ctx.mkConst("capacityIndex",ctx.mkIntSort());
		this.capacity=(IntExpr) ctx.mkConst("capacity",ctx.mkIntSort());;
		
	}
	
	public  courseObject(Context ctx, int i) throws Z3Exception {
		
    	this.course=ctx.mkConst("course"+i, ctx.mkUninterpretedSort(ctx.mkSymbol("Course")));
		this.capacityArrayIndex=(IntExpr) ctx.mkConst("capacityIndex"+i,ctx.mkIntSort());
		
	}

	public Expr getCourse() {
		return this.course;
	}
	
	
	public Expr getIndex() {
		return this.capacityArrayIndex;
	}
	
	public Expr getCapacity() {
		return this.capacity;
	}
	
	public void putCapacity(IntExpr capacity) {
		 this.capacity=capacity;
	}
	
}
