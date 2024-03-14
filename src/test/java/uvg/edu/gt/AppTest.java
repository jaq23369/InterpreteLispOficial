import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class AppTest {

    @Test
    public void testSimpleAddition() {
        LispInterpreter interpreter = new LispInterpreter();
        Object result = interpreter.interpret("(+ 2 3)");
        Assertions.assertEquals(5, result);
    }

    @Test
    public void testVariableDefinition() {
        LispInterpreter interpreter = new LispInterpreter();
        interpreter.defineVariable("x", 10);
        Object result = interpreter.getVariableValue("x");
        Assertion.assertEquals(10, result);
    }

    @Test
    public void testFunctionDefinitionAndEvaluation() {
        LispInterpreter interpreter = new LispInterpreter();
        interpreter.defineFunction("cuadrado", new FunctionDefinition(Arrays.asList("x"), Arrays.asList("(* x x)")));
        Object result = interpreter.interpret("(cuadrado 5)");
        Assertion.assertEquals(25, result);
    }
}
