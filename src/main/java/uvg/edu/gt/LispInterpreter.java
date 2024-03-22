package uvg.edu.gt;

/**
 * Esta clase representa un intérprete simple para el lenguaje de programación Lisp.
 * Permite interpretar expresiones Lisp, definir variables y funciones, y obtener valores de variables y funciones definidas.
 */
public class LispInterpreter {
    private final LispParser parser; // Parser para analizar las expresiones Lisp
    private final ExpressionEvaluator evaluator; // Evaluador para evaluar las expresiones Lisp
    private final Environment environment; // Ambiente que mantiene las variables y funciones definidas

    /**
     * Constructor para crear un nuevo intérprete Lisp.
     * Crea un nuevo ambiente, parser y evaluador.
     */
    public LispInterpreter() {
        this.environment = new Environment();
        this.parser = new LispParser();
        this.evaluator = new ExpressionEvaluator(environment);
    }

    /**
     * Interpreta una expresión Lisp dada.
     * 
     * @param expression La expresión Lisp a interpretar.
     * @return El resultado de la interpretación de la expresión.
     */
    public Object interpret(String expression) {
        try {
            // Parsea la expresión y la evalúa en el ambiente actual
            List<Object> parsedExpression = parser.parse(expression);
            return evaluator.evaluate(parsedExpression, environment);
        } catch (IllegalArgumentException e) {
            return "Syntax Error: " + e.getMessage();
        } catch (ArithmeticException e) {
            return "Math Error: " + e.getMessage();
        } catch (Exception e) {
            // Captura cualquier excepción no anticipada durante la interpretación
            return "Interpreter Error: " + e.getMessage();
        }
    }
    
    /**
     * Define una variable en el ambiente actual.
     * 
     * @param name  El nombre de la variable a definir.
     * @param value El valor de la variable a definir.
     */
    public void defineVariable(String name, Object value) {
        environment.defineVariable(name, value);
    }

    /**
     * Obtiene el valor de una variable definida en el ambiente actual o en los ámbitos superiores.
     * 
     * @param name El nombre de la variable cuyo valor se quiere obtener.
     * @return El valor de la variable especificada.
     */
    public Object getVariableValue(String name) {
        return environment.lookupVariable(name);
    }

    /**
     * Define una función en el ambiente actual.
     * 
     * @param name     El nombre de la función a definir.
     * @param function La definición de la función.
     */
    public void defineFunction(String name, FunctionDefinition function) {
        environment.defineFunction(name, function);
    }

    /**
     * Obtiene la definición de una función del ambiente actual o de los ámbitos superiores.
     * 
     * @param name El nombre de la función cuya definición se quiere obtener.
     * @return La definición de la función especificada.
     */
    public FunctionDefinition getFunction(String name) {
        return environment.lookupFunction(name);
    }
}
