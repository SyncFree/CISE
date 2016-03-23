package Analyzers;

import applications.Account;
import z3fol.analyzer.CommutativityCheck;
import z3fol.analyzer.StabilityCheck;
import z3fol.xpr.AnnotatedSchema;

/**
 * Created by najafzad on 20/10/15.
 */

public class StabilityTest {
    public static void main(String[] args)  {
        AnnotatedSchema c=new Account();
        StabilityCheck.check(c);
    }

}
