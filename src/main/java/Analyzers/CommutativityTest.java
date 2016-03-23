package Analyzers;

import com.microsoft.z3.Status;
import com.microsoft.z3.Model;
import z3fol.analyzer.CommutativityCheck;
import z3fol.xpr.*;

import java.util.List;
import java.util.Map;
import z3fol.model.Operation;
import applications.Account;
/**
 * Created by najafzad on 20/10/15.
 */
public class CommutativityTest {
    public static void main(String[] args)  {
    AnnotatedSchema c=new Account();
    CommutativityCheck.check(c);
    }

}
