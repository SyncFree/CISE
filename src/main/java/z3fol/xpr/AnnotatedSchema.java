package z3fol.xpr;

import z3fol.model.Operation;

import java.util.List;

public abstract class AnnotatedSchema extends XPRSchema {

    @Override
    public List<String> getModelXPRs() {
        return AnnotationUtils.findXPRs(this.getClass(), XPR.Type.MODEL);
    }

    @Override
    protected List<String> getInvariantXPRs() {
        return AnnotationUtils.findXPRs(this.getClass(), XPR.Type.INVARIANT);
    }

    @Override
    public List<Operation> getOperations() {
        return AnnotationUtils.findOps(this.getClass());
    }



}
