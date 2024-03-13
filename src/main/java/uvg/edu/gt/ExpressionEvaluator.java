package uvg.edu.gt;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExpressionEvaluator {

    private final Environment environment;

    public ExpressionEvaluator(Environment environment) {
        this.environment = environment;
    }

    public Object evaluate(List<Object> expression, Environment currentEnvironment) {        if (expression.isEmpty()) {
            throw new IllegalArgumentException("Empty expression");
        }

        List<Object> modifiableExpression = new ArrayList<>(expression);

    Object firstElement = modifiableExpression.get(0);
    if (firstElement instanceof String && currentEnvironment.isVariableDefined((String) firstElement)) {
        firstElement = currentEnvironment.getVariableValue((String) firstElement);
        modifiableExpression.set(0, firstElement);
    }

    if (!(firstElement instanceof String)) {
        throw new IllegalArgumentException("The first element of the expression must be a string representing an operator or a function name.");
    }

    String operator = (String) firstElement;
    List<Object> rawOperands = modifiableExpression.subList(1, modifiableExpression.size());
    
    // Aquí es donde vamos a procesar los operandos antes del switch.
    List<Object> operands = rawOperands.stream()
        .map(operand -> {
            if (operand instanceof List) {
                // Es una subexpresión, evalúala.
                return evaluate((List<Object>) operand, currentEnvironment);
            } else if (operand instanceof String && currentEnvironment.isVariableDefined((String) operand)) {
                // Es una variable, obtén su valor.
                return currentEnvironment.getVariableValue((String) operand);
            } else {
                // De lo contrario, devuelve el operando como está.
                return operand;
            }
        })
        .collect(Collectors.toList());

        switch (operator) {
            case "+":
                return evaluateArithmetic(operands, operator, currentEnvironment);
            case "-":
                return evaluateArithmetic(operands, operator, currentEnvironment);
            case "*":
                return evaluateArithmetic(operands, operator, currentEnvironment);
            case "/":
                return evaluateArithmetic(operands, operator, currentEnvironment);
            case "cond":
                return evaluateCond(operands, currentEnvironment);
            case "car":
                return car( operands, currentEnvironment);
            case "cdr":
                return cdr(operands, currentEnvironment);
            
            default:
                FunctionDefinition function = currentEnvironment.lookupFunction(operator);

                if (function == null) {
                    throw new IllegalArgumentException("Function '" + operator + "' is not defined.");
                }

                List<Object> arguments = evaluateArguments(operands, currentEnvironment);
                return evaluateFunctionCall(operator, arguments, currentEnvironment);
        }
    }

    private Object evaluateFunctionCall(String operator, List<Object> operands, Environment currentEnvironment) {
        FunctionDefinition function = currentEnvironment.lookupFunction(operator);
        if (function == null) {
            throw new IllegalArgumentException("Function '" + operator + "' is not defined.");
        }
        List<Object> arguments = evaluateArguments(operands, currentEnvironment);
        return evaluateFunction(function, arguments, currentEnvironment);
    }

    private List<Object> evaluateArguments(List<Object> args, Environment env) {
        // Evaluar cada argumento y devolver los resultados como una lista
        // Esto es necesario para manejar las expresiones pasadas como argumentos
        return args.stream()
        .map(arg -> {
            if (arg instanceof List) {
                // Es seguro realizar el cast porque hemos comprobado el tipo
                @SuppressWarnings("unchecked") // Suprime la advertencia de unchecked cast
                List<Object> castedArg = (List<Object>) arg;
                return evaluate(castedArg, env);
            } else {
                // No es una lista, podría ser un literal como un número o una cadena
                return arg;
            }
        })
        .collect(Collectors.toList());
    }

    private Object evaluateFunction(FunctionDefinition function, List<Object> arguments, Environment env) {
        // Crear un nuevo ámbito para la ejecución de la función
        Environment localEnvironment = env.beginScope();

        // Definir los parámetros en el nuevo ámbito
        List<String> parameters = function.getParameters();
        if (parameters.size() != arguments.size()) {
            throw new IllegalArgumentException("Incorrect number of arguments passed to function.");
        }

        for (int i = 0; i < parameters.size(); i++) {
            localEnvironment.defineVariable(parameters.get(i), arguments.get(i));
        }

        // Evaluar el cuerpo de la función
        Object result = null;
        for (Object expr : function.getBody()) {
            if (expr instanceof List<?>) { // Comprueba si es una lista de cualquier tipo
                List<?> rawListExpr = (List<?>) expr;
                // Aquí necesitas asegurarte de que listExpr es una List<Object> antes de castear
                @SuppressWarnings("unchecked")
                List<Object> listExpr = (List<Object>) rawListExpr;
                 result = evaluate(listExpr, localEnvironment);
            } else {
                throw new IllegalArgumentException("Function body must be a list of expressions.");
            }
        }

        //devolver el resultado
        return result;
    }

    private double evaluateArithmetic(List<Object> operands, String operator, Environment env) {
        double result = operator.equals("+") || operator.equals("-") ? 0 : 1;
        for (Object obj : operands) {
            double operand;
            try {
                operand = Double.parseDouble(obj.toString());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid operand for arithmetic operation: " + obj, e);
            }
            System.out.println("Performing operation: " + operator + " with operand " + operand); // Mensaje de depuración
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
        System.out.println("Arithmetic operation result: " + result); // Mensaje de depuración
        return result;
    }
    
    

    private Object evaluateCond(List<Object> clauses, Environment env) {
        for (Object clause : clauses) {
            if (!(clause instanceof List)) {
                throw new IllegalArgumentException("Each cond clause must be a pair of test and result.");
            }
            List<?> clauseList = (List<?>) clause;
            if (clauseList.size() != 2) { // Necesitarás una función para determinar si un resultado es verdadero en el contexto de Lisp
                throw new IllegalArgumentException("Each cond clause must be a pair of test and result.");
            }

            Object test = clauseList.get(0);
            Object resultClause = clauseList.get(1);
    
            // Asumimos que 'test' es una subexpresión que necesita ser evaluada para obtener un valor booleano
            @SuppressWarnings("unchecked")
            Object testResult = evaluate((List<Object>) test, env);

            if (isTrue(testResult)) {
                return evaluate((List<Object>) resultClause, env);
            }
        }
        return null; // o el valor que represente 'false' en tu interpretación de Lisp
    }

    private boolean isTrue(Object result) {
        // La implementación dependerá de cómo representes valores booleanos en tu intérprete.
        // Por ejemplo, si 'nil' representa 'false' y cualquier otra cosa representa 'true':
        return result != null; // Suponiendo que 'null' representa 'nil' en tu implementación.
    }

    private Object car(List<Object> args, Environment env) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("car expects a single list argument.");
        }
        // Evalúa el primer argumento para obtener la lista real sobre la que actuar.
        // Asumimos que args.get(0) es una lista de objetos porque car espera una lista como argumento.
        // Es decir, el car debería recibir algo como (car (quote (1 2 3))) y (quote (1 2 3)) se evaluará a una lista [1, 2, 3].
        @SuppressWarnings("unchecked")
        Object arg = evaluate((List<Object>) args.get(0), env);

        // Asegurarse de que el argumento evaluado es una lista
        if (!(arg instanceof List<?>)) {
            throw new IllegalArgumentException("Argument to car is not a list.");
        }

        // Realizar un cast seguro después de comprobar el tipo
        @SuppressWarnings("unchecked")
        List<Object> listArg = (List<Object>) arg;
    
        // Comprobar si la lista está vacía
        if (listArg.isEmpty()) {
            return null; // o cómo manejes listas vacías en tu interpretación de Lisp
        }

        // Devolver el primer elemento de la lista
        return listArg.get(0);
    }

    private Object cdr(List<Object> args, Environment env) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("cdr expects a single list argument.");
        }
        @SuppressWarnings("unchecked")
        Object arg = evaluate((List<Object>) args.get(0), env);
        if (!(arg instanceof List<?>)) {
            throw new IllegalArgumentException("Argument to cdr is not a list.");
        }
        @SuppressWarnings("unchecked")
        List<Object> listArg = (List<Object>) arg;
        if (listArg.isEmpty()) {
            return null; // o cómo manejes listas vacías en tu interpretación de Lisp
        }
        return new ArrayList<>(listArg.subList(1, listArg.size())); // Devuelve una nueva lista sin el primer elemento.
    }
}