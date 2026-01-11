/*
UNIVERSIDAD ESTATAL A DISTANCIA
Curso:Compiladores
Código: 03307
Proyecto #1: Analizador Pascal
Tutor:  CHACÓN CHINCHILLA CARLOS
Grupo: 10
Estudiante: Francisco Campos Sandi
Cédula: 114750560
III Cuatrimestre 2025 */
package PascalAnalizador;

public final class ReglasIdentificadores {

    // ^[A-Za-z][A-Za-z_]*$  (sin dígitos)
    // Verifica que un identificador siga las reglas del formulario (inicia con letra y solo contiene letras o guiones bajos)
    public static boolean esValidoIdentificadorFormulario(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        if (!Character.isLetter(s.charAt(0))) {
            return false;
        }
        for (int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!(Character.isLetter(c) || c == '_')) {
                return false;
            }
        }
        return true;
    }

    // Verifica si una palabra es reservada en Pascal
    public static boolean esPalabraReservada(String s) {
        return PalabrasReservadas.esReservada(s);
    }
}
