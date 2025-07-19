/*
UNIVERSIDAD ESTATAL A DISTANCIA
Curso:Programación Intermedia
Código: 0824
Proyecto #1: AutopartesCR
Tutor:  Wilfrido Castilla Salas
Grupo: 07
Estudiante: Francisco Campos Sandi
Cédula: 114750560
II Cuatrimestre 2024 */
package autopartescr;

import java.util.Scanner;

public class AutopartesCR {

    private static final Scanner scanner = new Scanner(System.in); // Para el Scanner para leer todas las entradas de los métodos, tomado de:https://coderanch.com/wiki/678613/Don-close-Scanner-tied-System                                                         

    //Main
    public static void main(String[] args) {
        Inventario inventario = new Inventario(); // Crea una instancia de la clase Inventario
        String nombre;
        String marca;
        String categoria;
        String respuesta; // Variable para almacenar la respuesta del usuario
        double precioSinImpuestos;
        double impuesto;
        double precioConImpuestos;
        do {
            if (inventarioLleno(inventario)) { // Verifica si el inventario esta lleno 
                break; // Sale del bucle 
            }
            nombre = ingresarNombre(); // Ingresa el nombre del repuesto
            marca = ingresarMarca(); // Ingresa la marca del repuesto
            precioSinImpuestos = ingresarPrecio(); // Ingresa el precio sin impuestos
            categoria = ingresarCategoria(); // Ingresa la categoría del repuesto
            Repuesto repuesto = new Repuesto(nombre, marca, precioSinImpuestos, categoria); // Crea una instancia de Repuesto
            impuesto = CalculadoraImpuestos.precioSinImpuesto(precioSinImpuestos);// Calcula el impuesto y el precio con impuesto usando la clase CalculadoraImpuestos
            precioConImpuestos = CalculadoraImpuestos.precioConImpuesto(precioSinImpuestos);
            repuesto.setImpuesto(impuesto);// Establece los valores calculados en el objeto repuesto
            repuesto.setPrecioConImpuestos(precioConImpuestos);
            inventario.agregarRepuesto(repuesto); // Agrega el repuesto al inventario
            mostrarNumeroRepuestosIngresados(inventario);// Muestra el número de repuestos ingresados
            respuesta = validarSiNo(); // Pregunta al usuario si desea ingresar más registros y se valida que solo se ingrese S o Ns
        } while (respuesta.equals("S")); // Repite si la respuesta es "S"
        // Muestra los reportes finales del inventario
        inventario.mostrarInventarioCompleto();
        inventario.mostrarRepuestosPorCategoria();
        inventario.mostrarPromedioPreciosConImpuestosPorCategoria();
        inventario.mostrarCategoriaConImpuestoPromedioMasBajo();
        inventario.mostrarValorTotalInventarioConImpuestos();
    }

    /*    Métodos para el main, lo cuales serán usados para ir validando el funcionamiento del programa
    
     */
    // Método para validar si el inventario tiene las 20 categías
    public static boolean inventarioLleno(Inventario inventario) {
        if (inventario.getContador() >= 20) { // Verifica si el inventario está lleno
            System.out.println("El inventario está lleno. No se pueden agregar más repuestos.");
            return true; // Indica que el inventario está lleno
        }
        return false; // Indica que el inventario no está lleno
    }

    //       Método para validar el nombre del repuesto
    public static boolean validarNombre(String nombre) {
        return nombre != null && nombre.length() > 0 && nombre.length() <= 50;
    }

    // Método para ingresar el nombre del repuesto
    public static String ingresarNombre() {
        String nombre = "";
        do {             //ejemplo base para realizar las excepciones tipo tomado de: IllegalArgumentException https://geekflare.com/es/exception-handling-java/
            try {
                System.out.println("Ingrese el nombre del repuesto (máximo 50 caracteres):");
                nombre = scanner.nextLine().toUpperCase(); // Lee el nombre del repuesto
                if (!validarNombre(nombre)) {
                    throw new IllegalArgumentException("El nombre no puede ser nulo y debe tener un máximo de 50 caracteres.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!validarNombre(nombre)); // Valida el nombre y asegura que no esté vacío

        return nombre;
    }
    // Método para validar la marca del repuesto

    public static boolean validarMarca(String marca) {
        return marca != null && marca.length() > 0 && marca.length() <= 20;
    }

    // Método para ingresar la marca del repuesto
    public static String ingresarMarca() {
        String marca = "";
        do {        //ejemplo base para realizar las excepciones tipo tomado de: IllegalArgumentException https://geekflare.com/es/exception-handling-java/
            try {
                System.out.println("Ingrese la marca del repuesto (máximo 20 caracteres):");
                marca = scanner.nextLine().toUpperCase(); // Lee la marca del repuesto
                if (!validarMarca(marca)) {
                    throw new IllegalArgumentException("Marca inválida. Intente de nuevo.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!validarMarca(marca)); // Valida la marca

        return marca;
    }

    // Método para ingresar el precio del repuesto
    public static double ingresarPrecio() {
        double precioSinImpuestos = 0;
        boolean entradaValida = false;
        do {                  //ejemplo base para realizar las excepciones tipo tomado de: IllegalArgumentException https://geekflare.com/es/exception-handling-java/

            try {
                System.out.println("Ingrese el precio sin impuestos del repuesto (valor no negativo):");
                String cadenaPrecio = scanner.nextLine(); // Lee la entrada del usuario como una cadena
                precioSinImpuestos = Double.parseDouble(cadenaPrecio); // Convierte la cadena a un número double
                if (precioSinImpuestos < 0) { // Verifica si el precio es no negativo
                    throw new IllegalArgumentException("El precio no puede ser negativo. Intente de nuevo.");
                }
                entradaValida = true; // Marcar la entrada como válida si no se lanzó ninguna excepción
            } catch (NumberFormatException e) {//si se ingresa un caracter que no es un número atrapa la excepción página 464 del libro del curso
                System.out.println("Entrada inválida. Por favor, ingrese un número válido.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!entradaValida); // Continúa solicitando entrada hasta que sea válida

        return precioSinImpuestos;
    }
    // Método para validar la categoría del repuesto

    public static boolean validarCategoria(String categoria) {
        return categoria != null && categoria.length() > 0 && categoria.length() <= 20;
    }

    // Método para ingresar la categoría del repuesto
    public static String ingresarCategoria() {
        String categoria = "";
        do {                  //ejemplo base para realizar las excepciones tipo tomado de: IllegalArgumentException https://geekflare.com/es/exception-handling-java/
            try {
                System.out.println("Ingrese la categoría del repuesto (máximo 20 caracteres):");
                categoria = scanner.nextLine().toUpperCase(); // Lee la categoría del repuesto
                if (!validarCategoria(categoria)) {
                    throw new IllegalArgumentException("Categoría inválida. Intente de nuevo.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!validarCategoria(categoria)); // Valida la categoría

        return categoria;
    }

    // Método para validar si/no (S/N)
    public static String validarSiNo() {
        String respuesta = "";
        do {
            System.out.println("¿Desea ingresar más registros? (S/N):");
            respuesta = scanner.nextLine().toUpperCase();
            if (!respuesta.equals("S") && !respuesta.equals("N")) {
                System.out.println("Entrada no válida. Por favor, ingrese 'S' o 'N'.");
            }
        } while (!respuesta.equals("S") && !respuesta.equals("N"));
        return respuesta;
    }

    // Método para mostrar el número de repuestos ingresados
    public static void mostrarNumeroRepuestosIngresados(Inventario inventario) {
        System.out.println("Número de Repuestos Ingresados: " + inventario.getContador());
    }

}