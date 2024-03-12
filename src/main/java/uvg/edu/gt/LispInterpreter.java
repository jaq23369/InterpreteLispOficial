package uvg.edu.gt;


import java.util.List;

public class LispInterpreter {

    private final LispParser parser;
    private final ExpressionEvaluator evaluator;
    private final Environment environment;

    public LispInterpreter() {
        this.environment = new Environment();
        this.parser = new LispParser();
        this.evaluator = new ExpressionEvaluator(environment);
    }

    public Object interpret(String expression) {
        try {
            List<Object> parsedExpression = parser.parse(expression);
            return evaluator.evaluate(parsedExpression, environment);
        } catch (IllegalArgumentException e) {
            return "Syntax Error: " + e.getMessage();
        } catch (ArithmeticException e) {
            return "Math Error: " + e.getMessage();
        } catch (Exception e) {
            // Este es un capturador genérico para cualquier otro tipo de excepción no anticipada.
            return "Interpreter Error: " + e.getMessage();
        }
    }
    
    public void defineVariable(String name, Object value) {
        environment.defineVariable(name, value);
    }

    public Object getVariableValue(String name) {
        return environment.lookupVariable(name);
    }

    public void defineFunction(String name, FunctionDefinition function) {
        environment.defineFunction(name, function);
    }

    public FunctionDefinition getFunction(String name) {
        return environment.lookupFunction(name);
    }
}

