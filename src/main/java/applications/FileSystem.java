package applications;

import z3fol.xpr.AnnotatedOperation;
import z3fol.xpr.AnnotatedSchema;
import z3fol.xpr.Op;
import z3fol.xpr.XPR;



@XPR("Map Dir")
@XPR("Dir root")
@XPR(value = "tree invariant ", type = XPR.Type.INVARIANT)
@Op(FileSystem.moveDir.class)
public class FileSystem extends AnnotatedSchema {


    @XPR(value = {"Name name", "Dir dir","Dir Dir_s","Dir Dir_d"},
            type = XPR.Type.ARGUMENT)
    @XPR(value ="{reachable(root,Dir_d),!reachable(dir,Dir_d)}",
            type = XPR.Type.PRECONDITION)
    @XPR(value = "{(name, dir) in Dir_d and !((name, dir) in Dir_s)}",
            type = XPR.Type.EFFECT)
    @XPR(value = "token == true", type = XPR.Type.Token)
    public static class moveDir extends AnnotatedOperation { }


}