﻿# InterpreteLispOficial
README
Intérprete LISP
Este es un intérprete LISP básico desarrollado en Java. Permite evaluar expresiones LISP, definir variables y definir funciones.

Ejecución
Para ejecutar el intérprete LISP, sigue estos pasos:

Clona este repositorio en tu máquina local.
Abre el proyecto en tu IDE preferido que tenga soporte para Java, como IntelliJ IDEA o Eclipse.
Ejecuta la clase Main.java para iniciar el intérprete LISP.
Sigue las instrucciones en la consola para evaluar expresiones, definir variables o definir funciones.
Uso
El intérprete LISP permite las siguientes operaciones:

Evaluar expresión: Ingresa una expresión LISP para evaluarla y obtener el resultado.
Definir variable: Define una variable y asigna un valor a la misma.
Definir función: Define una función LISP con parámetros y cuerpo.

Explicación de las clases del proyecto:

LispParser: Se encarga de analizar sintácticamente (parsear) las expresiones del lenguaje LISP que recibe como entradas en forma de String. El proceso de parseo convierte la cadena de texto de la expresión LISP en una estructura de datos más manejable para la posterior evaluación, típicamente una lista de listas y símbolos.

FunctionDefinition:  Es una representación sencilla de una función en el contexto de tu interpretador LISP. Esta clase actúa como una especie de contenedor o modelo para las funciones definidas por el usuario dentro del lenguaje.

LispInterpreter: Es el núcleo del intérprete LISP que has estado construyendo. Actúa como la fachada que los usuarios utilizarán para interactuar con el intérprete y ejecutar expresiones LISP.

ExpressionEvaluator: Es el corazón de la evaluación de expresiones en tu intérprete LISP. Esta clase toma una estructura de datos parseada (una lista de objetos que representa una expresión LISP) y realiza operaciones basadas en esta estructura para evaluar la expresión.

Enviroment: Es un componente crucial en tu intérprete LISP que gestiona el contexto de ejecución. Aquí es donde se almacenan las variables y las definiciones de funciones, permitiendo que tu intérprete tenga características como el ámbito y la capacidad de definir y buscar variables y funciones.

Main:  Es el punto de entrada del intérprete LISP. Aquí es donde comienza la ejecución del programa y donde se interactúa con el usuario a través de la consola. La clase maneja el bucle principal que permite al usuario evaluar expresiones LISP, definir variables y definir funciones.


Cosas que se lograron:
-Que el programa corriera
-Que evaluara expresiones aplicando cambios en LispParser
-Definir variables
-Definir funciones

Cosas que no se lograron:
-Que se pudieran hacer todas las operaciones juntas
-La lectura de archivos porque solo se prueba una por una las operaciones básicas
-Probar la funcionalidad de las funciones
-El funcionamiento y prueba de la recursividad
