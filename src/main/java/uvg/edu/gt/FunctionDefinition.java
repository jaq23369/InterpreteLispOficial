package uvg.edu.gt;

import java.util.List;

public class FunctionDefinition {
    private final List<String> parameters;
    private final List<Object> body;

    public FunctionDefinition(List<String> parameters, List<Object> body) {
        this.parameters = parameters;
        this.body = body;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public List<Object> getBody() {
        return body;
    }
}

