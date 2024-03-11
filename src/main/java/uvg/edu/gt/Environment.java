package uvg.edu.gt;
import java.util.HashMap;
import java.util.Map;

public class Environment {
    private final Map<String, Object> variables;
    private final Map<String, FunctionDefinition> functions;

    public Environment() {
        this.variables = new HashMap<>();
        this.functions = new HashMap<>();
    }

    public void defineVariable(String name, Object value) {
        variables.put(name, value);
    }

    public Object lookupVariable(String name) {
        if (!variables.containsKey(name)) {
            throw new IllegalArgumentException("Variable '" + name + "' is not defined.");
        }
        return variables.get(name);
    }

    public void defineFunction(String name, FunctionDefinition function) {
        functions.put(name, function);
    }

    public FunctionDefinition lookupFunction(String name) {
        if (!functions.containsKey(name)) {
            throw new IllegalArgumentException("Function '" + name + "' is not defined.");
        }
        return functions.get(name);
    }
}