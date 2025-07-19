/*
UNIVERSIDAD ESTATAL A DISTANCIA
Curso:Programaci�n Intermedia
C�digo: 0824
Proyecto #1: AutopartesCR
Tutor:  Wilfrido Castilla Salas
Grupo: 07
Estudiante: Francisco Campos Sandi
C�dula: 114750560
II Cuatrimestre 2024 */
package autopartescr;

import java.util.Scanner;

public class AutopartesCR {

    private static final Scanner scanner = new Scanner(System.in); // Para el Scanner para leer todas las entradas de los m�todos, tomado de:https://coderanch.com/wiki/678613/Don-close-Scanner-tied-System                                                         

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
            categoria = ingresarCategoria(); // Ingresa la categor�a del repuesto
            Repuesto repuesto = new Repuesto(nombre, marca, precioSinImpuestos, categoria); // Crea una instancia de Repuesto
            impuesto = CalculadoraImpuestos.precioSinImpuesto(precioSinImpuestos);// Calcula el impuesto y el precio con impuesto usando la clase CalculadoraImpuestos
            precioConImpuestos = CalculadoraImpuestos.precioConImpuesto(precioSinImpuestos);
            repuesto.setImpuesto(impuesto);// Establece los valores calculados en el objeto repuesto
            repuesto.setPrecioConImpuestos(precioConImpuestos);
            inventario.agregarRepuesto(repuesto); // Agrega el repuesto al inventario
            mostrarNumeroRepuestosIngresados(inventario);// Muestra el n�mero de repuestos ingresados
            respuesta = validarSiNo(); // Pregunta al usuario si desea ingresar m�s registros y se valida que solo se ingrese S o Ns
        } while (respuesta.equals("S")); // Repite si la respuesta es "S"
        // Muestra los reportes finales del inventario
        inventario.mostrarInventarioCompleto();
        inventario.mostrarRepuestosPorCategoria();
        inventario.mostrarPromedioPreciosConImpuestosPorCategoria();
        inventario.mostrarCategoriaConImpuestoPromedioMasBajo();
        inventario.mostrarValorTotalInventarioConImpuestos();
    }

    /*    M�todos para el main, lo cuales ser�n usados para ir validando el funcionamiento del programa
    
     */
    // M�todo para validar si el inventario tiene las 20 categ�as
    public static boolean inventarioLleno(Inventario inventario) {
        if (inventario.getContador() >= 20) { // Verifica si el inventario est� lleno
            System.out.println("El inventario est� lleno. No se pueden agregar m�s repuestos.");
            return true; // Indica que el inventario est� lleno
        }
        return false; // Indica que el inventario no est� lleno
    }

    //       M�todo para validar el nombre del repuesto
    public static boolean validarNombre(String nombre) {
        return nombre != null && nombre.length() > 0 && nombre.length() <= 50;
    }

    // M�todo para ingresar el nombre del repuesto
    public static String ingresarNombre() {
        String nombre = "";
        do {             //ejemplo base para realizar las excepciones tipo tomado de: IllegalArgumentException https://geekflare.com/es/exception-handling-java/
            try {
                System.out.println("Ingrese el nombre del repuesto (m�ximo 50 caracteres):");
                nombre = scanner.nextLine().toUpperCase(); // Lee el nombre del repuesto
                if (!validarNombre(nombre)) {
                    throw new IllegalArgumentException("El nombre no puede ser nulo y debe tener un m�ximo de 50 caracteres.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!validarNombre(nombre)); // Valida el nombre y asegura que no est� vac�o

        return nombre;
    }
    // M�todo para validar la marca del repuesto

    public static boolean validarMarca(String marca) {
        return marca != null && marca.length() > 0 && marca.length() <= 20;
    }

    // M�todo para ingresar la marca del repuesto
    public static String ingresarMarca() {
        String marca = "";
        do {        //ejemplo base para realizar las excepciones tipo tomado de: IllegalArgumentException https://geekflare.com/es/exception-handling-java/
            try {
                System.out.println("Ingrese la marca del repuesto (m�ximo 20 caracteres):");
                marca = scanner.nextLine().toUpperCase(); // Lee la marca del repuesto
                if (!validarMarca(marca)) {
                    throw new IllegalArgumentException("Marca inv�lida. Intente de nuevo.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!validarMarca(marca)); // Valida la marca

        return marca;
    }

    // M�todo para ingresar el precio del repuesto
    public static double ingresarPrecio() {
        double precioSinImpuestos = 0;
        boolean entradaValida = false;
        do {                  //ejemplo base para realizar las excepciones tipo tomado de: IllegalArgumentException https://geekflare.com/es/exception-handling-java/

            try {
                System.out.println("Ingrese el precio sin impuestos del repuesto (valor no negativo):");
                String cadenaPrecio = scanner.nextLine(); // Lee la entrada del usuario como una cadena
                precioSinImpuestos = Double.parseDouble(cadenaPrecio); // Convierte la cadena a un n�mero double
                if (precioSinImpuestos < 0) { // Verifica si el precio es no negativo
                    throw new IllegalArgumentException("El precio no puede ser negativo. Intente de nuevo.");
                }
                entradaValida = true; // Marcar la entrada como v�lida si no se lanz� ninguna excepci�n
            } catch (NumberFormatException e) {//si se ingresa un caracter que no es un n�mero atrapa la excepci�n p�gina 464 del libro del curso
                System.out.println("Entrada inv�lida. Por favor, ingrese un n�mero v�lido.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!entradaValida); // Contin�a solicitando entrada hasta que sea v�lida

        return precioSinImpuestos;
    }
    // M�todo para validar la categor�a del repuesto

    public static boolean validarCategoria(String categoria) {
        return categoria != null && categoria.length() > 0 && categoria.length() <= 20;
    }

    // M�todo para ingresar la categor�a del repuesto
    public static String ingresarCategoria() {
        String categoria = "";
        do {                  //ejemplo base para realizar las excepciones tipo tomado de: IllegalArgumentException https://geekflare.com/es/exception-handling-java/
            try {
                System.out.println("Ingrese la categor�a del repuesto (m�ximo 20 caracteres):");
                categoria = scanner.nextLine().toUpperCase(); // Lee la categor�a del repuesto
                if (!validarCategoria(categoria)) {
                    throw new IllegalArgumentException("Categor�a inv�lida. Intente de nuevo.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!validarCategoria(categoria)); // Valida la categor�a

        return categoria;
    }

    // M�todo para validar si/no (S/N)
    public static String validarSiNo() {
        String respuesta = "";
        do {
            System.out.println("�Desea ingresar m�s registros? (S/N):");
            respuesta = scanner.nextLine().toUpperCase();
            if (!respuesta.equals("S") && !respuesta.equals("N")) {
                System.out.println("Entrada no v�lida. Por favor, ingrese 'S' o 'N'.");
            }
        } while (!respuesta.equals("S") && !respuesta.equals("N"));
        return respuesta;
    }

    // M�todo para mostrar el n�mero de repuestos ingresados
    public static void mostrarNumeroRepuestosIngresados(Inventario inventario) {
        System.out.println("N�mero de Repuestos Ingresados: " + inventario.getContador());
    }

}