package uvg.edu.gt;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class LispParser {

    public List<Object> parse(String expression) {
        List<Object> tokens = new ArrayList<>();
        String token = "";
        Stack<Object> stack = new Stack<>();

        for (char c : expression.toCharArray()) {
            switch (c) {
                case '(':
                    if (!token.isEmpty()) {
                        stack.push(token);
                        token = "";
                    }
                    stack.push(c);
                    break;
                case ')':
                    if (!token.isEmpty()) {
                        stack.push(token);
                        token = "";
                    }
                    List<Object> subList = new ArrayList<>();
                    while (!stack.isEmpty() && stack.peek() instanceof String) {
                        subList.add(0, stack.pop());
                    }
                    if (!stack.isEmpty() && stack.peek().equals('(')) {
                        stack.pop(); // Remove the '('
                        stack.push(subList);
                    }
                    break;
                case ' ':
                    if (!token.isEmpty()) {
                        stack.push(token);
                        token = "";
                    }
                    break;
                default:
                    token += c;
                    break;
            }
        }

        // In case there's a token left at the end (without closing parentheses)
        if (!token.isEmpty()) {
            stack.push(token);
        }

        // Flatten the stack into the tokens list
        while (!stack.isEmpty()) {
            tokens.add(0, stack.pop());
        }

        return tokens;
    }
}
