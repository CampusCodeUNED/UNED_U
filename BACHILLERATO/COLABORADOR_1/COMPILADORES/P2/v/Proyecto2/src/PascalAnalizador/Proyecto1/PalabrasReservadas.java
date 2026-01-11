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
package PascalAnalizador.Proyecto1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

// Clase que mantiene las palabras reservadas e integradas del lenguaje Pascal
public final class PalabrasReservadas {

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
    // Palabras reservadas de Pascal
    private static final List<String> RESERVADAS = conjuntoMinusculas(
            "absolute", "downto", "begin", "destructor", "mod",
            "and", "else", "case", "external", "not",
            "array", "end", "const", "div", "packed",
            "asm", "file", "constructor", "do", "procedure",
            "for", "forward", "function", "goto", "record",
            "if", "in", "or", "private", "until",
            "program", "repeat", "string", "then", "var",
            "while", "xor", "with", "type", "of",
            "uses", "set", "object", "to"
    );

    // Palabras integradas o builtins/procs/funcs que no deben marcarse como "variables no declaradas"
    private static final List<String> INTEGRADAS = conjuntoMinusculas(
            "integer", "string", "word",
            "writeln", "write", "readln", "read",
            "true", "false", "nil",
            "begin", "end", "const", "uses", "program", "var", "type", "of",
            "clrscr", "readkey", "getdate", "ord", "inc", "dec"
    );

    // Verifica si una palabra es una palabra reservada de Pascal
    public static boolean esReservada(String s) {
        return s != null && RESERVADAS.contains(s.toLowerCase(Locale.ROOT));
    }

    // Verifica si una palabra es una palabra integrada o builtin de Pascal
    public static boolean esIntegrada(String s) {
        return s != null && INTEGRADAS.contains(s.toLowerCase(Locale.ROOT));
    }

    // Constructor privado para evitar instancias de esta clase de utilidad
    private PalabrasReservadas() {
    }
}
