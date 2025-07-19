package autopartescr;

import java.util.ArrayList;

public class Inventario {

    private Repuesto[] repuestos; // Vector de repuestos con capacidad para 20 elementos
    private int contador; // Contador para llevar la cuenta del número de repuestos añadidos

    // Constructor que inicializa el arreglo y el contador
    public Inventario() {
        this.repuestos = new Repuesto[20]; // Inicializa el vector de repuestos
        this.contador = 0; // Inicializa el contador en 0
    }

    public int getContador() {
        return contador;
    }

    // Método para agregar un repuesto al inventario
    public void agregarRepuesto(Repuesto repuesto) {
        if (contador < 20) { // Verifica que el inventario no está lleno
            repuestos[contador] = repuesto; // Añade el repuesto al vector
            contador++; // Incrementa el contador
        } else {
            System.out.println("El inventario está lleno."); // Mensaje si el inventario está lleno
        }
    }

    // Método para mostrar todos los repuestos en el inventario
    public void mostrarInventarioCompleto() {
        System.out.println("\n|-----------------------------------------------------------------------------------------------------------------------------------------------|");
        System.out.println("|                                                           Inventario completo                                                                 |");
        System.out.println("|-----------------------------------------------------------------------------------------------------------------------------------------------|");
        for (int i = 0; i < contador; i++) { // Recorre el vector de repuestos hasta el contador
            System.out.println(repuestos[i].toString()); // Imprime cada repuesto
            System.out.println("|-----------------------------------------------------------------------------------------------------------------------------------------------|");
        }
        System.out.println("************************************************************ FIN DE INVENTARIO *****************************************************************");
    }

    // Método para mostrar los repuestos por categorí­a y calcular el total de precios sin impuestos por categorí­a
    public void mostrarRepuestosPorCategoria() {
        ArrayList<String> categorias = new ArrayList<>(); // Lista para almacenar las categorías
        ArrayList<Double> totalPorCategoria = new ArrayList<>(); // Lista para almacenar los totales por categorí­a
        String categoria;
        int indice;
        double precioSinImpuestos;
        System.out.println("\n|--------------------------------------------------------------------------|");
        System.out.println("|           Repuestos por categoría (precios sin impuestos)                |");
        System.out.println("|--------------------------------------------------------------------------|");

        for (int i = 0; i < contador; i++) { // Recorre el vector de repuestos
            Repuesto repuesto = repuestos[i]; // Obtiene el repuesto actual
            categoria = repuesto.getCategoria(); // Obtiene la categoría del repuesto
            precioSinImpuestos = repuesto.getPrecioSinImpuestos(); // Obtiene el precio sin impuestos del repuesto

            if (categorias.contains(categoria)) { // Verifica si la categoría ya está en la lista
                indice = categorias.indexOf(categoria); // Obtiene el í­ndice de la categorí­a  
                totalPorCategoria.set(indice, totalPorCategoria.get(indice) + precioSinImpuestos); // Suma el precio sin impuestos al total de la categorí­a
            } else {
                categorias.add(categoria); // Añade la nueva categorí­a a la lista
                totalPorCategoria.add(precioSinImpuestos); // Añade el precio sin impuestos al total de la nueva categorí­a
            }
        }
        for (int i = 0; i < categorias.size(); i++) { // Recorre la lista de categorí­as
            System.out.printf("|Categoría: %-20s  Total sin impuestos : %-20.2f\n", categorias.get(i), totalPorCategoria.get(i)); // Imprime el total por categorí­a
            System.out.println("|--------------------------------------------------------------------------|");
        }
        System.out.println("*********************************** FIN ***********************************");
    }

    // Método para mostrar el promedio de los precios con impuestos por categorí­a
    public void mostrarPromedioPreciosConImpuestosPorCategoria() {
        ArrayList<String> categorias = new ArrayList<>(); // Lista para almacenar las categorí­as
        ArrayList<Double> totalPorCategoria = new ArrayList<>(); // Lista para almacenar los totales por categorí­a
        ArrayList<Integer> contadorPorCategoria = new ArrayList<>(); // Lista para contar los repuestos por categorí­a
        int indice;
        String categoria;
        System.out.println("\n|--------------------------------------------------------------------------|");
        System.out.println("|           Promedio de precios con impuestos por categoría                |");
        System.out.println("|--------------------------------------------------------------------------|");

        for (int i = 0; i < contador; i++) { // Recorre el vector de repuestos
            Repuesto repuesto = repuestos[i]; // Obtiene el repuesto actual
            categoria = repuesto.getCategoria(); // Obtiene la categorí­a del repuesto
            double precioConImpuestos = repuesto.getPrecioConImpuestos(); // Obtiene el precio con impuestos del repuesto

            if (categorias.contains(categoria)) { // Verifica si la categorí­a ya está en la lista
                indice = categorias.indexOf(categoria); // Obtiene el índice de la categorí­a
                totalPorCategoria.set(indice, totalPorCategoria.get(indice) + precioConImpuestos); // Suma el precio con impuestos al total de la categoría
                contadorPorCategoria.set(indice, contadorPorCategoria.get(indice) + 1); // Incrementa el contador de la categorí­a
            } else {
                categorias.add(categoria); // Añade la nueva categorí­a a la lista
                totalPorCategoria.add(precioConImpuestos); // Añade el precio con impuestos al total de la nueva categorí­a
                contadorPorCategoria.add(1); // Inicializa el contador de la nueva categorí­a
            }
        }

        for (int i = 0; i < categorias.size(); i++) { // Recorre la lista de categorí­as
            double promedio = totalPorCategoria.get(i) / contadorPorCategoria.get(i); // Calcula el promedio de precios con impuestos por categorí­a
            System.out.printf("|Categoría: %-20s  Promedio precio con impuestos: %-20.2f\n", categorias.get(i), promedio); // Imprime el promedio por categoría
            System.out.println("|--------------------------------------------------------------------------|");
        }
        System.out.println("*********************************** FIN ***********************************");

    }

    // Método para mostrar la categorí­a con el impuesto promedio más bajo
    public void mostrarCategoriaConImpuestoPromedioMasBajo() {
        ArrayList<String> categorias = new ArrayList<>(); // Lista para almacenar las categorí­as
        ArrayList<Double> totalImpuestoPorCategoria = new ArrayList<>(); // Lista para almacenar los totales de impuestos por categorí­a
        ArrayList<Integer> contadorPorCategoria = new ArrayList<>(); // Lista para contar los repuestos por categorí­a
        int indice;
        double impuesto;
        double impuestoPromedioMasBajo;
        String categoria;
        double promedio;
        String categoriaMasBaja;
        System.out.println("\n|--------------------------------------------------------------------------|");
        System.out.println("|               Categoría con el impuesto promedio más bajo:               |");
        System.out.println("|--------------------------------------------------------------------------|");

        for (int i = 0; i < contador; i++) { // Recorre el vector de repuestos
            Repuesto repuesto = repuestos[i]; // Obtiene el repuesto actual
            categoria = repuesto.getCategoria(); // Obtiene la categorí­a del repuesto
            impuesto = repuesto.getPrecioConImpuestos() - repuesto.getPrecioSinImpuestos(); // Calcula el impuesto del repuesto

            if (categorias.contains(categoria)) { // Verifica si la categorí­a ya está en la lista
                indice = categorias.indexOf(categoria); // Obtiene el índice de la categorí­a
                totalImpuestoPorCategoria.set(indice, totalImpuestoPorCategoria.get(indice) + impuesto); // Suma el impuesto al total de la categorí­a
                contadorPorCategoria.set(indice, contadorPorCategoria.get(indice) + 1); // Incrementa el contador de la categorí­a
            } else {
                categorias.add(categoria); // Añade la nueva categorí­a a la lista
                totalImpuestoPorCategoria.add(impuesto); // Añade el impuesto al total de la nueva categorí­a
                contadorPorCategoria.add(1); // Inicializa el contador de la nueva categorí­a
            }
        }

        categoriaMasBaja = null; // Variable para almacenar la categorí­a con el impuesto promedio más bajo
        impuestoPromedioMasBajo = Double.MAX_VALUE; // Inicializa el impuesto promedio más bajo con el valor máximo tomado de:https://www.delftstack.com/es/howto/java/double-max-value-in-java/

        for (int i = 0; i < categorias.size(); i++) { // Recorre la lista de categorí­as
            promedio = totalImpuestoPorCategoria.get(i) / contadorPorCategoria.get(i); // Calcula el promedio de impuestos por categorí­a
            if (promedio < impuestoPromedioMasBajo) { // Verifica si el promedio actual es el mÃ¡s bajo
                impuestoPromedioMasBajo = promedio; // Actualiza el impuesto promedio más bajo
                categoriaMasBaja = categorias.get(i); // Actualiza la categorí­a con el impuesto promedio más bajo
            }
        }

        if (categoriaMasBaja != null) { // Verifica si se encuntra una categoría
            System.out.printf("|Categoría: %-20s   Promedio: %-20.2f\n", categoriaMasBaja, impuestoPromedioMasBajo);// Imprime la categoría con el impuesto promedio más bajo
            System.out.println("|--------------------------------------------------------------------------|");
        }
    }

    // Método para mostrar el valor total del inventario con impuestos
    public void mostrarValorTotalInventarioConImpuestos() {
        double total = 0; // Inicializa el total del inventario con impuestos
        System.out.println("\n|--------------------------------------------------------------------------|");
        System.out.println("|                Valor total del inventario con impuestos                  |");
        System.out.println("|--------------------------------------------------------------------------|");
        for (int i = 0; i < contador; i++) { // Recorre el vector de repuestos
            total += repuestos[i].getPrecioConImpuestos(); // Suma el precio con impuestos de cada repuesto al total
        }
        System.out.println(String.format("|Valor total con impuestos: %.2f", total)); // Imprime el valor total del inventario con impuestos
        System.out.println("|--------------------------------------------------------------------------|");
        System.out.println("\nFINALIZANDO PROGRAMA...");
        System.out.println("\n");
    }

}
