package uvg.edu.gt;


import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LispInterpreter interpreter = new LispInterpreter();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Intérprete LISP - Universidad del Valle de Guatemala");
        System.out.println("Escribe 'salir' para terminar.");
        System.out.println("Opciones:");
        System.out.println("1. Evaluar expresión");
        System.out.println("2. Definir variable");
        System.out.println("3. Definir función");

        while (true) {
            System.out.print("Selecciona una opción: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    System.out.print("Ingresa tu expresión LISP: ");
                    String expression = scanner.nextLine();
                    try {
                        Object result = interpreter.interpret(expression);
                        System.out.println("Resultado: " + result);
                    } catch (Exception e) {
                        System.out.println("Error al interpretar la expresión: " + e.getMessage());
                    }
                    break;
                case "2":
                    System.out.print("Ingresa el nombre de la variable: ");
                    String varName = scanner.nextLine();
                    System.out.print("Ingresa el valor de la variable: ");
                    String varValue = scanner.nextLine();
                    try {
                        interpreter.defineVariable(varName, Integer.parseInt(varValue));
                        System.out.println("Variable '" + varName + "' definida con éxito.");
                    } catch (NumberFormatException e) {
                        System.out.println("Error: El valor debe ser un número entero.");
                    }
                    break;
                case "3":
                    System.out.print("Ingresa el nombre de la función: ");
                    String funcName = scanner.nextLine();
                    System.out.print("Ingresa los parámetros de la función separados por espacio (ej. x y): ");
                    String params = scanner.nextLine();
                    List<String> parameters = Arrays.asList(params.split("\\s+"));
                    System.out.print("Ingresa el cuerpo de la función (ej. (+ x y)): ");
                    String body = scanner.nextLine();
                    List<Object> bodyAsList = Arrays.asList((Object)body);
                    // Aquí se simplifica asumiendo que el cuerpo es una única expresión
                    FunctionDefinition funcDef = new FunctionDefinition(parameters, bodyAsList);
                    interpreter.defineFunction(funcName, funcDef);
                    System.out.println("Función '" + funcName + "' definida con éxito.");
                    break;
                case "salir":
                    System.out.println("Saliendo del intérprete LISP. ¡Hasta luego!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción no reconocida. Intenta de nuevo.");
                    break;
            }

            System.out.println(); // Espacio en blanco para mejorar legibilidad
        }
    }
}

