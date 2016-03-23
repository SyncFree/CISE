package z3fol.xpr;

import com.microsoft.z3.Sort;

public class XPRTypeMismatchException extends XPRException {
    public XPRTypeMismatchException(Sort expected, Sort obtained) {
        super("Type " + expected + " expected, obtained " + obtained);
    }
}
