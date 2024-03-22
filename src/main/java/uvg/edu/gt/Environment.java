package uvg.edu.gt;

import java.util.HashMap;
import java.util.Map;

/**
 * The Environment class represents an environment for variable and function bindings.
 * It supports defining variables and functions, looking up variable and function values,
 * and managing scopes.
 */
public class Environment {
    private final Map<String, Object> variables = new HashMap<>();
    private final Map<String, FunctionDefinition> functions = new HashMap<>();
    private final Environment parent;

    /**
     * Constructs a new environment with no parent.
     */
    public Environment() {
        this.parent = null;
    }

    /**
     * Constructs a new environment with a given parent environment.
     *
     * @param parent The parent environment.
     */
    private Environment(Environment parent) {
        this.parent = parent;
    }

    /**
     * Begins a new scope by creating a child environment.
     *
     * @return The new child environment.
     */
    public Environment beginScope() {
        return new Environment(this);
    }

    /**
     * Ends the current scope and returns the parent environment.
     *
     * @return The parent environment.
     */
    public Environment endScope() {
        return this.parent;
    }

    /**
     * Defines a variable in the current environment.
     *
     * @param variablename The name of the variable.
     * @param value        The value of the variable.
     */
    public void defineVariable(String variablename, Object value) {
        variables.put(variablename, value);
    }

    /**
     * Looks up the value of a variable in the current and parent environments.
     *
     * @param name The name of the variable to look up.
     * @return The value of the variable.
     * @throws IllegalArgumentException If the variable is not defined.
     */
    public Object lookupVariable(String name) {
        if (variables.containsKey(name)) {
            return variables.get(name);
        } else if (parent != null) {
            return parent.lookupVariable(name);
        } else {
            throw new IllegalArgumentException("Variable '" + name + "' is not defined.");
        }
    }

    /**
     * Defines a function in the current environment.
     *
     * @param name     The name of the function.
     * @param function The function definition.
     */
    public void defineFunction(String name, FunctionDefinition function) {
        functions.put(name, function);
    }

    /**
     * Looks up the definition of a function in the current and parent environments.
     *
     * @param name The name of the function to look up.
     * @return The function definition.
     * @throws IllegalArgumentException If the function is not defined.
     */
    public FunctionDefinition lookupFunction(String name) {
        if (functions.containsKey(name)) {
            return functions.get(name);
        } else if (parent != null) {
            return parent.lookupFunction(name);
        } else {
            throw new IllegalArgumentException("Function '" + name + "' is not defined.");
        }
    }

    /**
     * Checks if a variable is defined in the current or parent environments.
     *
     * @param variableName The name of the variable to check.
     * @return True if the variable is defined, false otherwise.
     */
    public boolean isVariableDefined(String variableName) {
        return variables.containsKey(variableName) || (parent != null && parent.isVariableDefined(variableName));
    }

    /**
     * Gets the value of a variable defined in the current or parent environments.
     *
     * @param variableName The name of the variable.
     * @return The value of the variable.
     * @throws IllegalArgumentException If the variable is not defined.
     */
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
