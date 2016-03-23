package Analyzers;

import com.microsoft.z3.BoolExpr;
import z3fol.model.State;
import z3fol.xpr.Processor;
import java.util.List;


public class ParserTest {
    public static void main(String[] args) {

        State s = new State();

        List<BoolExpr> result = Processor.process(s, "¬ (Exists Int x, Int y: x <= y)");

        System.out.println(result.get(0));

    }

}
