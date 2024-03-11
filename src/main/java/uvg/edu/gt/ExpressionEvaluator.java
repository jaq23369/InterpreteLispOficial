package uvg.edu.gt;

import java.util.List;

public class ExpressionEvaluator {

    private Environment environment;

    public ExpressionEvaluator(Environment environment) {
        this.environment = environment;
    }

    public Object evaluate(List<Object> expression) {
        if (expression.isEmpty()) {
            throw new IllegalArgumentException("Empty expression");
        }

        String operator = (String) expression.get(0);
        switch (operator) {
            case "+":
                return evaluateArithmetic(expression.subList(1, expression.size()), operator);
            case "-":
                return evaluateArithmetic(expression.subList(1, expression.size()), operator);
            case "*":
                return evaluateArithmetic(expression.subList(1, expression.size()), operator);
            case "/":
                return evaluateArithmetic(expression.subList(1, expression.size()), operator);
            default:
                // Here we would handle other types of expressions (e.g., variable lookup, function calls)
                return null;
        }
    }

    private double evaluateArithmetic(List<Object> operands, String operator) {
        double result = operator.equals("+") || operator.equals("-") ? 0 : 1;
        for (Object obj : operands) {
            double operand = Double.parseDouble(obj.toString()); // Simplification for example purposes
            switch (operator) {
                case "+":
                    result += operand;
                    break;
                case "-":
                    result -= operand;
                    break;
                case "*":
                    result *= operand;
                    break;
                case "/":
                    if (operand == 0) throw new ArithmeticException("Division by zero");
                    result /= operand;
                    break;
            }
        }
        return result;
    }
}