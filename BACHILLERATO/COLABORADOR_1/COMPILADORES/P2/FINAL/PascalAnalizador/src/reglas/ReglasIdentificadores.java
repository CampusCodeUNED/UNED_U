/*
UNIVERSIDAD ESTATAL A DISTANCIA
Curso:Compiladores
Código: 03307
Proyecto #2: Analizador Pascal
Tutor:  CHACÓN CHINCHILLA CARLOS
Grupo: 10
Estudiante: Francisco Campos Sandi
Cédula: 114750560
III Cuatrimestre 2025 */
package reglas;

public final class ReglasIdentificadores {

    // ^[A-Za-z][A-Za-z_]*$  (sin dígitos)
    // Verifica si una cadena es un identificador válido 
    public static boolean esValidoIdentificadorFormulario(String s) {
        if (s == null || s.isEmpty()) return false;
        if (!Character.isLetter(s.charAt(0))) return false;
        for (int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!(Character.isLetter(c) || c == '_')) return false;
        }
        return true;
    }
    // Verifica si una cadena es una palabra reservada
    public static boolean esPalabraReservada(String s) {
        return PalabrasReservadas.isReserved(s);
    }
}
