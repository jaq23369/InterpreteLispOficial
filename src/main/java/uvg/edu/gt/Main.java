package uvg.edu.gt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Esta clase representa el punto de entrada principal del programa.
 * Proporciona un menú interactivo para interactuar con el intérprete de Lisp.
 */
public class Main {
    /**
     * Método principal que inicia la ejecución del programa.
     * Proporciona un menú interactivo para interactuar con el intérprete de Lisp.
     * 
     * @param args Los argumentos de línea de comandos (no se utilizan en este programa).
     */
    public static void main(String[] args) {
        LispInterpreter interpreter = new LispInterpreter(); // Crear un intérprete de Lisp
        Scanner scanner = new Scanner(System.in); // Crear un objeto Scanner para leer la entrada del usuario

        // Mostrar el mensaje de bienvenida y las opciones del menú
        System.out.println("Intérprete LISP - Universidad del Valle de Guatemala");
        System.out.println("Escribe 'salir' para terminar.");
        System.out.println("Opciones:");
        System.out.println("1. Evaluar expresión");
        System.out.println("2. Definir variable");
        System.out.println("3. Definir función");
        System.out.println("4. Leer y evaluar expresión desde archivo");

        // Ciclo principal del programa que muestra el menú y espera la entrada del usuario
        while (true) {
            System.out.print("Selecciona una opción: ");
            String option = scanner.nextLine(); // Leer la opción seleccionada por el usuario

            switch (option) {
                case "1":
                    // Opción para evaluar una expresión ingresada por el usuario
                    System.out.print("Ingresa tu expresión LISP: ");
                    String expression = scanner.nextLine();
                    try {
                        // Interpretar y evaluar la expresión y mostrar el resultado
                        Object result = interpreter.interpret(expression);
                        System.out.println("Resultado: " + result);
                    } catch (Exception e) {
                        // Capturar cualquier error durante la interpretación o evaluación de la expresión
                        System.out.println("Error al interpretar la expresión: " + e.getMessage());
                    }
                    break;
                case "2":
                    // Opción para definir una variable ingresada por el usuario
                    System.out.print("Ingresa el nombre de la variable: ");
                    String varName = scanner.nextLine();
                    System.out.print("Ingresa el valor de la variable: ");
                    String varValue = scanner.nextLine();
                    try {
                        // Definir la variable con el valor especificado por el usuario
                        interpreter.defineVariable(varName, Integer.parseInt(varValue));
                        System.out.println("Variable '" + varName + "' definida con éxito.");
                    } catch (NumberFormatException e) {
                        // Capturar el error si el valor ingresado no es un número entero
                        System.out.println("Error: El valor debe ser un número entero.");
                    }
                    break;
                case "3":
                    // Opción para definir una función ingresada por el usuario
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
                case "4":
                    // Opción para leer y evaluar una expresión desde un archivo
                    System.out.print("Ingresa el nombre del archivo con la expresión LISP: ");
                    String filePath = scanner.nextLine();
                    try {
                        // Leer el contenido del archivo y evaluar la expresión
                        String content = new String(Files.readAllBytes(Paths.get(filePath)));
                        Object result = interpreter.interpret(content);
                        System.out.println("Resultado: " + result);
                    } catch (IOException e) {
                        // Capturar cualquier error al leer el archivo
                        System.out.println("Error al leer el archivo: " + e.getMessage());
                    }
                    break;
                case "salir":
                    // Opción para salir del programa
                    System.out.println("Saliendo del intérprete LISP. ¡Hasta luego!");
                    scanner.close(); // Cerrar el Scanner antes de salir
                    return; // Salir del método main y terminar la ejecución del programa
                default:
                    // Opción para manejar entradas no válidas del usuario
                    System.out.println("Opción no reconocida. Intenta de nuevo.");
                    break;
            }

            System.out.println(); // Agregar espacio en blanco para mejorar la legibilidad del menú
        }
    }
}
