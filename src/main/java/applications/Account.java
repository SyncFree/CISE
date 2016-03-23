package applications;

import z3fol.xpr.AnnotatedOperation;
        import z3fol.xpr.AnnotatedSchema;
        import z3fol.xpr.Op;
        import z3fol.xpr.XPR;


@XPR("Int balance")
@XPR(value = "balance >= 0", type = XPR.Type.INVARIANT)
@Op(Account.Deposit.class)
@Op(Account.Debit.class)
public class Account extends AnnotatedSchema {

    @XPR(value ={"Int amount","Int balance" },type =XPR.Type.ARGUMENT)
    @XPR(value ="amount >= 0",type = XPR.Type.PRECONDITION)
    @XPR(value ="balance := balance + amount",type =XPR.Type.EFFECT)
    public static class Deposit extends AnnotatedOperation { }

    @XPR(value ={"Int amount","Int balance"},type =XPR.Type.ARGUMENT)
    @XPR(value ="amount >= 0",type = XPR.Type.PRECONDITION)
    @XPR(value ="balance := balance - amount",type =XPR.Type.EFFECT)
    public static class Debit extends AnnotatedOperation { }
}