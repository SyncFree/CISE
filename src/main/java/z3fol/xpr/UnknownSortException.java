package z3fol.xpr;

public class UnknownSortException extends XPRException {
    public UnknownSortException(String sortName) {
        super("Unknown sort: " + sortName);
    }
}
