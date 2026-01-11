/*
UNIVERSIDAD ESTATAL A DISTANCIA
Curso: Compiladores
Código: 03307
Proyecto #2: Analizador Pascal
Tutor:  CHACÓN CHINCHILLA CARLOS
Grupo: 10
Estudiante: Francisco Campos Sandi
Cédula: 114750560
III Cuatrimestre 2025 */
package PascalAnalizador.Proyecto2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

// Clase que mantiene las palabras reservadas específicas para las estructuras de control en Pascal
public final class PalabrasReservadasP2 {
    // Convierte un conjunto de palabras a minúsculas y elimina duplicados
    private static List<String> conjuntoMinusculas(String... palabras) {
        ArrayList<String> s = new ArrayList<>();
        for (String palabra : palabras) {
            if (palabra != null && !s.contains(palabra.toLowerCase(Locale.ROOT))) {
                s.add(palabra.toLowerCase(Locale.ROOT));
            }
        }
        return Collections.unmodifiableList(s);
    }

    // Palabras reservadas específicas para las estructuras de control en Pascal
    private static final List<String> RESERVADAS = conjuntoMinusculas(
            // Estructura del bucle FOR:
            // for variable := valor_inicial to valor_final do
            //     begin
            //         sentencia;
            //     end;
            "for",      // Inicio del bucle for
            "to",       // Separador entre valor inicial y final
            "do",       // Final de la declaración del for
            
            // Estructura del IF-THEN-ELSE:
            // if condicion then
            //     begin
            //         sentencia;
            //     end
            // else
            //     begin
            //         sentencia;
            //     end;
            "if",       // Inicio de la estructura if
            "then",     // Después de la condición
            "else",     // Inicio de la rama alternativa
            
            // Estructura REPEAT-UNTIL:
            // repeat
            //     begin
            //         sentencia;
            //     end;
            // until condicion;
            "repeat",   // Inicio del bucle (sin punto y coma, nada más en la línea)
            "until",    // Después de end, debe estar alineado con repeat
            
            // Palabras comunes
            "begin",    // Inicio de bloque (requiere tabulación de 4 espacios)
            "end"       // Final de bloque (con punto y coma para REPEAT)
    );

    // Símbolos y operadores para estructuras de control
    private static final List<String> EXPRESIONES_CONTROL = conjuntoMinusculas(
            // Operador de asignación (para FOR)
            ":=",       // Debe tener espacio antes y después
            
            // Punto y coma
            ";"         // - Requerido al final de cada sentencia
                       // - Requerido después de end en else
                       // - NO requerido después de end en if (antes del else)
    );

    // Verifica si una palabra es una palabra reservada de las estructuras de control
    public static boolean esReservada(String s) {
        return s != null && RESERVADAS.contains(s.toLowerCase(Locale.ROOT));
    }

    // Verifica si una palabra es válida en expresiones de control
    public static boolean esExpresionControl(String s) {
        return s != null && EXPRESIONES_CONTROL.contains(s.toLowerCase(Locale.ROOT));
    }

    // Constructor privado para evitar instancias de esta clase de utilidad
    private PalabrasReservadasP2() {
    }
}