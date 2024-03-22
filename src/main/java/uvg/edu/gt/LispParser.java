package uvg.edu.gt;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Esta clase representa un analizador/parser para el lenguaje de programación Lisp.
 * Permite analizar una cadena de texto que contiene una expresión Lisp y convertirla en una estructura de datos adecuada.
 */
public class LispParser {

    /**
     * Analiza una expresión Lisp dada y la convierte en una lista de objetos que representa la estructura de la expresión.
     * 
     * @param expression La expresión Lisp a analizar.
     * @return Una lista de objetos que representa la estructura de la expresión Lisp.
     * @throws IllegalArgumentException Si hay un error de sintaxis en la expresión Lisp.
     */
    public List<Object> parse(String expression) throws IllegalArgumentException {
        Stack<Object> stack = new Stack<>();
        stack.push(new ArrayList<>());

        String token = "";

        for (char c : expression.toCharArray()) {
            if (c == '(') {
                if (!token.isEmpty()) {
                    addTokenToStack(stack, token);
                    token = "";
                }
                stack.push(new ArrayList<>());
            } else if (c == ')') {
                if (!token.isEmpty()) {
                    addTokenToStack(stack, token);
                    token = "";
                }
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Syntax Error: Unbalanced parentheses.");
                }
                List<Object> subExpr = castToList(stack.pop());
                checkIfListAndAdd(stack, subExpr);
            } else if (c == ' ') {
                if (!token.isEmpty()) {
                    addTokenToStack(stack, token);
                    token = "";
                }
            } else if (c == '-' || c == '*' || c == '/') {
                if (!token.isEmpty()) {
                    addTokenToStack(stack, token);
                    token = "";
                }
                // Aquí es importante agregar el operador como token directamente al top de la stack
                addTokenToStack(stack, Character.toString(c));
            } else {
                token += c;
            }
        }
        
        if (!token.isEmpty()) {
            addTokenToStack(stack, token);
        }
        
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Syntax Error: Unbalanced parentheses or incomplete expression.");
        }
        
        return castToList(stack.pop());
    }

    // Método privado para agregar un token a la stack como parte de la expresión analizada
    private void addTokenToStack(Stack<Object> stack, String token) {
        List<Object> top = castToList(stack.peek());
        top.add(token);
    }

    // Método privado para asegurarse de que un objeto sea una lista
    @SuppressWarnings("unchecked")
    private List<Object> castToList(Object obj) {
        if (!(obj instanceof List)) {
            throw new IllegalStateException("Expected a List but found: " + obj);
        }
        return (List<Object>) obj;
    }

    // Método privado para verificar si una subexpresión es una lista y agregarla adecuadamente a la stack
    private void checkIfListAndAdd(Stack<Object> stack, List<Object> subExpr) {
        List<Object> top = castToList(stack.peek());
        if (!subExpr.isEmpty() && subExpr.get(0).equals("*")) {
            // Aplanamos si el primer elemento es el operador '+'.
            top.addAll(subExpr);
        } else {
            top.add(subExpr);
        }
    }
}
