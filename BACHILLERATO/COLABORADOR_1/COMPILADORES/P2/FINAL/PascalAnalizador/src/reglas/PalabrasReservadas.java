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

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

public final class PalabrasReservadas {
    private static Set<String> lowerSet(String... words) {
        LinkedHashSet<String> s = new LinkedHashSet<>();
        for (String w : words) if (w != null) s.add(w.toLowerCase(Locale.ROOT));
        return Collections.unmodifiableSet(s);
    }
     // palabras reservadas del lenguaje Pascal
    private static final Set<String> RESERVED = lowerSet(
        "absolute","downto","begin","destructor","mod",
        "and","else","case","external","not",
        "array","end","const","div","packed",
        "asm","file","constructor","do","procedure",
        "for","forward","function","goto","record",
        "if","in","or","private","until",
        "program","repeat","string","then","var",
        "while","xor","with","type","of",
        "uses","set","object","to"
    );

    // builtins/procs/funcs que no deben marcarse como “variables no declaradas”
    private static final Set<String> BUILTINS = lowerSet(
        "integer","string","word",
        "writeln","write","readln","read",
        "true","false","nil",
        "begin","end","const","uses","program","var","type","of",
        "clrscr","readkey","getdate","ord","inc","dec"
    );
    // Verifica si una cadena es una palabra reservada
    public static boolean isReserved(String s) {
        return s != null && RESERVED.contains(s.toLowerCase(Locale.ROOT));
    }
    // Verifica si una cadena es una función o procedimiento incorporado
    public static boolean isBuiltin(String s) {
        return s != null && BUILTINS.contains(s.toLowerCase(Locale.ROOT));
    }
    private PalabrasReservadas() {}
}
