package evaluation.bank.invariant;

import com.microsoft.z3.*;
import evaluation.bank.AccountObject;
import evaluation.bank.bank;
import invariant.Invariant;

public class BoundedInv implements Invariant {
	
	String name;
	
	public BoundedInv(String name) {
		this.name=name;
	}
	
	@Override
	public String getName() {

		return this.name;
	}

	@Override
	public BoolExpr getInv(Context ctx) throws Z3Exception {

		
		 Expr[] capacityindex =  new Expr[1];
		 capacityindex[0]=ctx.mkConst("account", ctx.mkUninterpretedSort(ctx.mkSymbol("AccountID")));;
	     
	     AccountObject a1=new AccountObject(ctx,1);
	     AccountObject a2=new AccountObject(ctx,2);

	     IntExpr limitIndex1=(IntExpr) a1.getIndex();
	     IntExpr limitIndex2=(IntExpr) a2.getIndex();
	     
	     Expr creditLimit=a1.getCreditLimit();
	     
	     Expr account1= a1.getAccount();
	     Expr account2= a2.getAccount();
    
	     AccountObject a=new AccountObject(ctx);

	     IntExpr limitIndex=(IntExpr) a.getIndex();

		 
		 Expr limit= ctx.mkSelect(bank.capacityArray, limitIndex);
	
		 Expr capacityBound=ctx.mkGe( (ArithExpr) limit , (ArithExpr) creditLimit);
			
			
		  BoolExpr antecedent = ctx.mkNot(ctx.mkEq(limitIndex1, limitIndex2));
			
		  Expr distinctCapacity=ctx.mkImplies(ctx.mkNot(ctx.mkEq(account1, account2)), antecedent);
		  
		  Expr inv=ctx.mkAnd(new BoolExpr[]{(BoolExpr) distinctCapacity,(BoolExpr) capacityBound});

		 
        Sort[] indexbase1 = new Sort[3];
        indexbase1[0] =ctx.mkUninterpretedSort(ctx.mkSymbol("AccountID"));
        indexbase1[1] =ctx.mkUninterpretedSort(ctx.mkSymbol("AccountID"));
        indexbase1[2] =ctx.mkUninterpretedSort(ctx.mkSymbol("AccountID"));
        
		 Symbol[] indexCNames1 = new Symbol[3];
		 indexCNames1[0] =  ctx.mkSymbol("account1");
		 indexCNames1[1] =  ctx.mkSymbol("account2");
		 indexCNames1[2] =  ctx.mkSymbol("account");
		 
		 Expr bounded2 = ctx.mkForall(indexbase1, indexCNames1, inv, 1, null, null,null, null);

		 System.out.println(" bounded invariant: " + bounded2.toString());
		 
		 return (BoolExpr) bounded2;
	}
	
	public static void main(String[] args)  throws Z3Exception {
		
		BoundedInv b=new BoundedInv("bounded");
		
		Context ctx=new Context();
		
		System.out.println(b.getInv(ctx));
		
		
	}
	

}
