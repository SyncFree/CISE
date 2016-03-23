package z3fol.xpr;

import z3fol.model.Operation;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AnnotationUtils {

    public static List<String> findXPRs(Class<?> clazz, XPR.Type type) {
        return Arrays
                .stream(clazz.getAnnotationsByType(XPR.class))
                .filter(x -> x.type() == type)
                .flatMap(x -> Arrays.stream(x.value()))
                .collect(Collectors.toList());
    }

    public static List<Operation> findOps(Class<?> clazz) {
        return Arrays
                .stream(clazz.getAnnotationsByType(Op.class))
                .map(Op::value)
                .map(AnnotationUtils::instantiate)
                .collect(Collectors.toList());
    }

    private static Operation instantiate(Class<? extends Operation> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalStateException("Annotation referenced operations must have a no-arg constructor");
        }
    }

}
