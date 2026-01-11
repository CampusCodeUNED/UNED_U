/**
 * Clase que demuestra la coerción implícita de tipos
 * (int a double) realizada por el Analizador Semántico (AS) de Java.
 */
public class Foro {
    
 public static void simularGeneracionCodigo(String variableInt, String variableDouble, double constante) {
        
        System.out.println("--- Expresión Fuente Analizada ---");
        System.out.println("Fuente: " + variableDouble + " = " + variableInt + " * " + constante);
        System.out.println("\n--- Código Intermedio (C3D) Generado ---");

        String temp1 = "t1";
        String operacionCoercion = "inttodouble(" + variableInt + ")";
        
        System.out.println(temp1 + " = " + operacionCoercion);

        String temp2 = "t2";
        System.out.println(temp2 + " = " + temp1 + " * " + constante);

        System.out.println(variableDouble + " = " + temp2);
        
        System.out.println("----------------------------------------");
    }

    public static void main(String[] args) {
        
        int a = 5;
        
        System.out.println("Valores de entrada: a = " + a);
        
        simularGeneracionCodigo("a", "b", 2.5);
    }
}