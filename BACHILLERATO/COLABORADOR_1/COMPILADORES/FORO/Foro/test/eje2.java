/**
 * Clase que demuestra la coerción implícita de tipos
 * (int a double) realizada por el Analizador Semántico (AS) de Java.
 */
public class eje2 {
    
    // Declaración de variables a nivel de clase
    int a = 10;
    double b = 3.14;
    // Esta línea es válida. Java convierte 'a' a double antes de la suma.
    double resultado = a + b; 

    public static void main(String[] args) {
        // Creamos una instancia para acceder a las variables
        eje2 miObjeto = new eje2();
        
        System.out.println("--- Demostración de Coerción de Tipos ---");
        System.out.println("Variable int (a): " + miObjeto.a);
        System.out.println("Variable double (b): " + miObjeto.b);
        System.out.println("----------------------------------------");
        
        // El resultado es un double, confirmando la conversión
        System.out.println("Resultado (a + b): " + miObjeto.resultado);
    }
}