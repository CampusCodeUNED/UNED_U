package autopartescr;

public class CalculadoraImpuestos {

    // Método para calcular el impuesto basado en el precio sin impuestos del enunciado
    public static double precioSinImpuesto(double precioSinImpuestos) {
        double impuesto2P = 0.02;// Impuesto del 2%
        double impuesto5P = 0.05;// Impuesto del 5%
        double impuesto10P = 0.10;// Impuesto del 10%
        double impuesto15P = 0.15;// Impuesto del 15%
        double precio1 = 10000;
        double precio2 = 50000;
        double precio3 = 100000;
        if (precioSinImpuestos <= precio1) {
            return precioSinImpuestos * impuesto2P;
        } else if (precioSinImpuestos <= precio2) {
            return precioSinImpuestos * impuesto5P;
        } else if (precioSinImpuestos <= precio3) {
            return precioSinImpuestos * impuesto10P;
        } else {
            return precioSinImpuestos * impuesto15P;
        }
    }

    // Método para calcular el precio con impuestos basado en el precio sin impuestos
    public static double precioConImpuesto(double precioSinImpuestos) {
        return precioSinImpuestos + precioSinImpuesto(precioSinImpuestos);
    }

}
