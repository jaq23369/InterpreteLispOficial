package uvg.edu.gt;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import uvg.edu.gt.ExpressionEvaluator;
import uvg.edu.gt.Environment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExpressionEvaluatorTests {

    @Test
    public void testEvaluateArithmetic() {
        ExpressionEvaluator evaluator = new ExpressionEvaluator(new Environment());
        List<Object> expression = Arrays.asList("2 + 2");
        Object result = evaluator.evaluate(expression);
        assertEquals(4, result);
    }

    @Test
    public void testEvaluateBoolean() {
        ExpressionEvaluator evaluator = new ExpressionEvaluator(new Environment());
        List<Object> expression = Arrays.asList("2 > 1");
        Object result = evaluator.evaluate(expression);
        assertEquals(true, result);
    }

    @Test
    public void testEvaluateComparison() {
        ExpressionEvaluator evaluator = new ExpressionEvaluator(new Environment());
        List<Object> expression = Arrays.asList("2 == 2");
        Object result = evaluator.evaluate(expression);
        assertEquals(true, result);
    }

    @Test
    public void testEvaluateVariable() {
        Environment env = new Environment();
        env.put("x", 2);
        ExpressionEvaluator evaluator = new ExpressionEvaluator(env);
        List<Object> expression = Arrays.asList("x");
        Object result = evaluator.evaluate(expression);
        assertEquals(2, result);
    }

    @Test
    public void testEvaluateFunction() {
        ExpressionEvaluator evaluator = new ExpressionEvaluator(new Environment());
        List<Object> expression = Arrays.asList("sqrt(4)");
        Object result = evaluator.evaluate(expression);
        assertEquals(2.0, result);
    }

    @Test
    public void testEvaluateComplex() {
        Environment env = new Environment();
        env.put("x", 2);
        ExpressionEvaluator evaluator = new ExpressionEvaluator(env);
        List<Object> expression = Arrays.asList("sqrt(x + 2) * (10 - power(2, 2))");
        Object result = evaluator.evaluate(expression);
        assertEquals(-12.0, result);
    }

    @Test
    public void testEvaluateList() {
        ExpressionEvaluator evaluator = new ExpressionEvaluator(new Environment());
        List<Object> expression = Arrays.asList("list(1, 2, 3)");
        Object result = evaluator.evaluate(expression);
        List<Object> expected = Arrays.asList(1, 2, 3);
        assertEquals(expected, result);
    }

    @Test
    public void testEvaluateIf() {
        Environment env = new Environment();
        env.put("x", 2);
        ExpressionEvaluator evaluator = new ExpressionEvaluator(env);
        List<Object> expression = Arrays.asList("if(x > 1, x, 1)");
        Object result = evaluator.evaluate(expression);
        assertEquals(2, result);
    }

    @Test
    public void testEvaluateLet() {
        ExpressionEvaluator evaluator = new ExpressionEvaluator(new Environment());
        List<Object> expression = Arrays.asList("let(x = 2, y = 3, x + y)");
        Object result = evaluator.evaluate(expression);
        assertEquals(5, result);
    }

    @Test
    public void testEvaluateLambda() {
        ExpressionEvaluator evaluator = new ExpressionEvaluator(new Environment());
        List<Object> expression = Arrays.asList("lambda(x, y, x + y)(2, 3)");
        Object result = evaluator.evaluate(expression);
        assertEquals(5, result);
    }
}
