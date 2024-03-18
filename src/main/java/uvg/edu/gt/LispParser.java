package uvg.edu.gt;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class LispParser {

    public List<Object> parse(String expression) {
        Stack<Object> stack = new Stack<>();
        stack.push(new ArrayList<>());

        String token = "";
        System.out.println("Parsing expression: " + expression); // Depuración

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
            // } else if (c == '-' || c == '*' || c == '/') {
            //     if (!token.isEmpty()) {
            //         addTokenToStack(stack, token);
            //         token = "";
            //     }
            //     // Aquí es importante agregar el operador como token directamente al top de la stack
            //     addTokenToStack(stack, Character.toString(c));
            } else {
                System.out.println("aca");
                token += c;
            }
        }
        
        if (!token.isEmpty()) {
            addTokenToStack(stack, token);
        }
        
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Syntax Error: Unbalanced parentheses or incomplete expression.");
        }
        
        List<Object> result = castToList(stack.pop());
        System.out.println("Parsed result: " + result); // Depuración
        return result;
    }
        

        
    
    private void addTokenToStack(Stack<Object> stack, String token) {
        List<Object> top = castToList(stack.peek());
        top.add(token);
    }

    private List<Object> castToList(Object obj) {
        if (!(obj instanceof List)) {
            throw new IllegalStateException("Expected a List but found: " + obj);
        }
        return (List<Object>) obj;
    }

    
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


