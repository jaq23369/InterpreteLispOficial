package uvg.edu.gt;

import java.util.List;

/**
 * Esta clase representa la definición de una función en el lenguaje de programación.
 * Contiene los parámetros de la función y el cuerpo de la función.
 */
public class FunctionDefinition {
    private final List<String> parameters; // Lista de parámetros de la función
    private final List<Object> body; // Cuerpo de la función

    /**
     * Constructor para crear una nueva definición de función.
     * 
     * @param parameters Lista de parámetros de la función.
     * @param body       Cuerpo de la función.
     */
    public FunctionDefinition(List<String> parameters, List<Object> body) {
        this.parameters = parameters;
        this.body = body;
    }

    /**
     * Obtiene la lista de parámetros de la función.
     * 
     * @return La lista de parámetros de la función.
     */
    public List<String> getParameters() {
        return parameters;
    }

    /**
     * Obtiene el cuerpo de la función.
     * 
     * @return El cuerpo de la función.
     */
    public List<Object> getBody() {
        return body;
    }
}
