package autopartescr;

import java.util.ArrayList;

public class Inventario {

    private Repuesto[] repuestos; // Vector de repuestos con capacidad para 20 elementos
    private int contador; // Contador para llevar la cuenta del n�mero de repuestos a�adidos

    // Constructor que inicializa el arreglo y el contador
    public Inventario() {
        this.repuestos = new Repuesto[20]; // Inicializa el vector de repuestos
        this.contador = 0; // Inicializa el contador en 0
    }

    public int getContador() {
        return contador;
    }

    // M�todo para agregar un repuesto al inventario
    public void agregarRepuesto(Repuesto repuesto) {
        if (contador < 20) { // Verifica que el inventario no est� lleno
            repuestos[contador] = repuesto; // A�ade el repuesto al vector
            contador++; // Incrementa el contador
        } else {
            System.out.println("El inventario est� lleno."); // Mensaje si el inventario est� lleno
        }
    }

    // M�todo para mostrar todos los repuestos en el inventario
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

    // M�todo para mostrar los repuestos por categor�a y calcular el total de precios sin impuestos por categor�a
    public void mostrarRepuestosPorCategoria() {
        ArrayList<String> categorias = new ArrayList<>(); // Lista para almacenar las categor�as
        ArrayList<Double> totalPorCategoria = new ArrayList<>(); // Lista para almacenar los totales por categor�a
        String categoria;
        int indice;
        double precioSinImpuestos;
        System.out.println("\n|--------------------------------------------------------------------------|");
        System.out.println("|           Repuestos por categor�a (precios sin impuestos)                |");
        System.out.println("|--------------------------------------------------------------------------|");

        for (int i = 0; i < contador; i++) { // Recorre el vector de repuestos
            Repuesto repuesto = repuestos[i]; // Obtiene el repuesto actual
            categoria = repuesto.getCategoria(); // Obtiene la categor�a del repuesto
            precioSinImpuestos = repuesto.getPrecioSinImpuestos(); // Obtiene el precio sin impuestos del repuesto

            if (categorias.contains(categoria)) { // Verifica si la categor�a ya est� en la lista
                indice = categorias.indexOf(categoria); // Obtiene el �ndice de la categor�a  
                totalPorCategoria.set(indice, totalPorCategoria.get(indice) + precioSinImpuestos); // Suma el precio sin impuestos al total de la categor�a
            } else {
                categorias.add(categoria); // A�ade la nueva categor�a a la lista
                totalPorCategoria.add(precioSinImpuestos); // A�ade el precio sin impuestos al total de la nueva categor�a
            }
        }
        for (int i = 0; i < categorias.size(); i++) { // Recorre la lista de categor�as
            System.out.printf("|Categor�a: %-20s  Total sin impuestos : %-20.2f\n", categorias.get(i), totalPorCategoria.get(i)); // Imprime el total por categor�a
            System.out.println("|--------------------------------------------------------------------------|");
        }
        System.out.println("*********************************** FIN ***********************************");
    }

    // M�todo para mostrar el promedio de los precios con impuestos por categor�a
    public void mostrarPromedioPreciosConImpuestosPorCategoria() {
        ArrayList<String> categorias = new ArrayList<>(); // Lista para almacenar las categor�as
        ArrayList<Double> totalPorCategoria = new ArrayList<>(); // Lista para almacenar los totales por categor�a
        ArrayList<Integer> contadorPorCategoria = new ArrayList<>(); // Lista para contar los repuestos por categor�a
        int indice;
        String categoria;
        System.out.println("\n|--------------------------------------------------------------------------|");
        System.out.println("|           Promedio de precios con impuestos por categor�a                |");
        System.out.println("|--------------------------------------------------------------------------|");

        for (int i = 0; i < contador; i++) { // Recorre el vector de repuestos
            Repuesto repuesto = repuestos[i]; // Obtiene el repuesto actual
            categoria = repuesto.getCategoria(); // Obtiene la categor�a del repuesto
            double precioConImpuestos = repuesto.getPrecioConImpuestos(); // Obtiene el precio con impuestos del repuesto

            if (categorias.contains(categoria)) { // Verifica si la categor�a ya est� en la lista
                indice = categorias.indexOf(categoria); // Obtiene el �ndice de la categor�a
                totalPorCategoria.set(indice, totalPorCategoria.get(indice) + precioConImpuestos); // Suma el precio con impuestos al total de la categor�a
                contadorPorCategoria.set(indice, contadorPorCategoria.get(indice) + 1); // Incrementa el contador de la categor�a
            } else {
                categorias.add(categoria); // A�ade la nueva categor�a a la lista
                totalPorCategoria.add(precioConImpuestos); // A�ade el precio con impuestos al total de la nueva categor�a
                contadorPorCategoria.add(1); // Inicializa el contador de la nueva categor�a
            }
        }

        for (int i = 0; i < categorias.size(); i++) { // Recorre la lista de categor�as
            double promedio = totalPorCategoria.get(i) / contadorPorCategoria.get(i); // Calcula el promedio de precios con impuestos por categor�a
            System.out.printf("|Categor�a: %-20s  Promedio precio con impuestos: %-20.2f\n", categorias.get(i), promedio); // Imprime el promedio por categor�a
            System.out.println("|--------------------------------------------------------------------------|");
        }
        System.out.println("*********************************** FIN ***********************************");

    }

    // M�todo para mostrar la categor�a con el impuesto promedio m�s bajo
    public void mostrarCategoriaConImpuestoPromedioMasBajo() {
        ArrayList<String> categorias = new ArrayList<>(); // Lista para almacenar las categor�as
        ArrayList<Double> totalImpuestoPorCategoria = new ArrayList<>(); // Lista para almacenar los totales de impuestos por categor�a
        ArrayList<Integer> contadorPorCategoria = new ArrayList<>(); // Lista para contar los repuestos por categor�a
        int indice;
        double impuesto;
        double impuestoPromedioMasBajo;
        String categoria;
        double promedio;
        String categoriaMasBaja;
        System.out.println("\n|--------------------------------------------------------------------------|");
        System.out.println("|               Categor�a con el impuesto promedio m�s bajo:               |");
        System.out.println("|--------------------------------------------------------------------------|");

        for (int i = 0; i < contador; i++) { // Recorre el vector de repuestos
            Repuesto repuesto = repuestos[i]; // Obtiene el repuesto actual
            categoria = repuesto.getCategoria(); // Obtiene la categor�a del repuesto
            impuesto = repuesto.getPrecioConImpuestos() - repuesto.getPrecioSinImpuestos(); // Calcula el impuesto del repuesto

            if (categorias.contains(categoria)) { // Verifica si la categor�a ya est� en la lista
                indice = categorias.indexOf(categoria); // Obtiene el �ndice de la categor�a
                totalImpuestoPorCategoria.set(indice, totalImpuestoPorCategoria.get(indice) + impuesto); // Suma el impuesto al total de la categor�a
                contadorPorCategoria.set(indice, contadorPorCategoria.get(indice) + 1); // Incrementa el contador de la categor�a
            } else {
                categorias.add(categoria); // A�ade la nueva categor�a a la lista
                totalImpuestoPorCategoria.add(impuesto); // A�ade el impuesto al total de la nueva categor�a
                contadorPorCategoria.add(1); // Inicializa el contador de la nueva categor�a
            }
        }

        categoriaMasBaja = null; // Variable para almacenar la categor�a con el impuesto promedio m�s bajo
        impuestoPromedioMasBajo = Double.MAX_VALUE; // Inicializa el impuesto promedio m�s bajo con el valor m�ximo tomado de:https://www.delftstack.com/es/howto/java/double-max-value-in-java/

        for (int i = 0; i < categorias.size(); i++) { // Recorre la lista de categor�as
            promedio = totalImpuestoPorCategoria.get(i) / contadorPorCategoria.get(i); // Calcula el promedio de impuestos por categor�a
            if (promedio < impuestoPromedioMasBajo) { // Verifica si el promedio actual es el más bajo
                impuestoPromedioMasBajo = promedio; // Actualiza el impuesto promedio m�s bajo
                categoriaMasBaja = categorias.get(i); // Actualiza la categor�a con el impuesto promedio m�s bajo
            }
        }

        if (categoriaMasBaja != null) { // Verifica si se encuntra una categor�a
            System.out.printf("|Categor�a: %-20s   Promedio: %-20.2f\n", categoriaMasBaja, impuestoPromedioMasBajo);// Imprime la categor�a con el impuesto promedio m�s bajo
            System.out.println("|--------------------------------------------------------------------------|");
        }
    }

    // M�todo para mostrar el valor total del inventario con impuestos
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
