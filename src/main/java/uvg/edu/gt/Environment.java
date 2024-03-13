package uvg.edu.gt;
import java.util.HashMap;
import java.util.Map;

public class Environment {
    private final Map<String, Object> variables = new HashMap<>();
    private final Map<String, FunctionDefinition> functions = new HashMap<>();
    private final Environment parent;

    public Environment() {
        this.parent = null;
    }

    private Environment(Environment parent) {
        this.parent = parent;
    }

    // Este método comienza un nuevo ámbito
    public Environment beginScope() {
        return new Environment(this);
    }

    // Este método finaliza el ámbito actual y devuelve el ámbito padre
    public Environment endScope() {
        return this.parent;
    }

    public void defineVariable(String variablename, Object value) {
        variables.put(variablename, value);
    }

    // Método modificado para buscar en el ámbito actual y luego en los padres
    public Object lookupVariable(String name) {
        if (variables.containsKey(name)) {
            return variables.get(name);
        } else if (parent != null) {
            return parent.lookupVariable(name);
        } else {
            throw new IllegalArgumentException("Variable '" + name + "' is not defined.");
        }
    }

    public void defineFunction(String name, FunctionDefinition function) {
        functions.put(name, function);
    }

   // Método modificado para buscar en el ámbito actual y luego en los padres
   public FunctionDefinition lookupFunction(String name) {
    if (functions.containsKey(name)) {
        return functions.get(name);
    } else if (parent != null) {
        return parent.lookupFunction(name);
    } else {
        throw new IllegalArgumentException("Function '" + name + "' is not defined.");
    }
}

// Verifica si una variable está definida en cualquier ámbito
public boolean isVariableDefined(String variableName) {
    return variables.containsKey(variableName) || (parent != null && parent.isVariableDefined(variableName));
}

// Obtiene el valor de una variable definida en cualquier ámbito
public Object getVariableValue(String variableName) {
    if (variables.containsKey(variableName)) {
        return variables.get(variableName);
    } else if (parent != null) {
        return parent.getVariableValue(variableName);
    } else {
        throw new IllegalArgumentException("Variable '" + variableName + "' is not defined.");
    }
}

}