package Analyzers;

import com.microsoft.z3.Status;
import com.microsoft.z3.Model;
import z3fol.analyzer.SequentialCheck;
import z3fol.model.State;
import z3fol.xpr.*;

import java.util.List;
import java.util.Map;
import z3fol.model.Operation;
import applications.Account;

public class SequentialTest {
    public static void main(String[] args) {

        AnnotatedSchema c=new Account();
        List<Operation> op=c.getOperations();
        Map<String, Status> result = SequentialCheck.check(c);

        for(int i=0;i<op.size();i++ ) {
            System.out.println("=======================================  ");
            System.out.println("Operation  "+ op.get(i).getName()+ ": ");
          //  System.out.println("Result:   "+result.get(op.get(i).getName()));
            System.out.println("counter-example:");
            Model model = SequentialCheck.model(c,op.get(i) );
            System.out.println(model);

        }

    }

}
