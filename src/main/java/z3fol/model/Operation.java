package z3fol.model;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Expr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Operation {


    List<Expr> exprs = new ArrayList<>();
    String getName();
    List<BoolExpr> getPreConditions(State state);
    List<BoolExpr> getConditions(State state);
    List<BoolExpr> getTokens(State state);
    void instantiateArguments(State state);
    void applyEffect(State state);
    void putArguments(State state) ;

   // List<BoolExpr> getConcConditions(State state);

}
