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
package validador;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import modelo.Diagnostico;
import modelo.Fuente;

public final class ValidadorIf {

    // Códigos de error para IF (1000-1099)
    public static final int E_IF_FALTA_CONDICION = 1000;
    public static final int E_IF_FALTA_THEN = 1001;
    public static final int E_IF_FALTA_BEGIN_THEN = 1002;
    public static final int E_IF_BEGIN_THEN_INDENTACION = 1003;
    public static final int E_IF_FALTA_SENTENCIA_THEN = 1004;
    public static final int E_IF_SENTENCIA_THEN_SIN_PUNTO_COMA = 1005;
    public static final int E_IF_SENTENCIA_THEN_INDENTACION = 1006;
    public static final int E_IF_FALTA_END_THEN = 1007;
    public static final int E_IF_END_THEN_CON_PUNTO_COMA = 1008;
    public static final int E_IF_END_THEN_INDENTACION = 1009;
    public static final int E_IF_FALTA_ELSE = 1010;
    public static final int E_IF_ELSE_INDENTACION = 1011;
    public static final int E_IF_FALTA_BEGIN_ELSE = 1012;
    public static final int E_IF_BEGIN_ELSE_INDENTACION = 1013;
    public static final int E_IF_FALTA_SENTENCIA_ELSE = 1014;
    public static final int E_IF_SENTENCIA_ELSE_SIN_PUNTO_COMA = 1015;
    public static final int E_IF_SENTENCIA_ELSE_INDENTACION = 1016;
    public static final int E_IF_FALTA_END_ELSE = 1017;
    public static final int E_IF_END_ELSE_SIN_PUNTO_COMA = 1018;
    public static final int E_IF_END_ELSE_INDENTACION = 1019;

    // Patrón para detectar línea if
    private static final Pattern PATRON_IF = Pattern.compile(
            "^(\\s*)if\\s+(.+)$",
            Pattern.CASE_INSENSITIVE
    );
    
    // Analizador de estructura if (para errores de if)
    public static final class Result {

        public final List<Diagnostico> diags;

        public Result(List<Diagnostico> diags) {
            this.diags = diags;
        }
    }
    // Método principal para validar estructuras if en un bloque dado
    public Result check(Fuente fuente, int lineaBegin, int lineaEnd) {
        List<Diagnostico> diags = new ArrayList<>();
        String[] lineas = fuente.getText().split("\\r?\\n", -1);

        if (lineaBegin <= 0 || lineaEnd <= 0) {
            return new Result(diags);
        }

        // Buscar líneas con 'if' dentro del bloque begin...end
        for (int i = lineaBegin; i < lineaEnd - 1; i++) {
            String lineaActual = lineas[i];
            String lineaLimpia = quitarComentarios(lineaActual);
            String lineaLimpiaLower = lineaLimpia.toLowerCase().trim();

            if (!lineaLimpiaLower.startsWith("if ")) {
                continue;
            }

            int numeroLinea = i + 1;
            validarIf(lineas, i, numeroLinea, diags);
        }

        return new Result(diags);
    }
    // Método para quitar comentarios de una línea
    private void validarIf(String[] lineas, int indiceIf, int numeroLineaIf, List<Diagnostico> diags) {
        // Validar la estructura del 'if' (condición then begin ... end else begin ... end)
        String lineaIf = quitarComentarios(lineas[indiceIf]);

        // Extraer indentación del if
        int indentacionCount = 0;
        for (int i = 0; i < lineaIf.length(); i++) {
            if (lineaIf.charAt(i) == ' ') {
                indentacionCount++;
            } else {
                break;
            }
        }
        String indentacionIf = lineaIf.substring(0, indentacionCount);

        // Buscar 'if' (case insensitive)
        String lineaLower = lineaIf.toLowerCase();
        int posIf = lineaLower.indexOf("if ");
        if (posIf < 0) {
            return;
        }

        String contenidoDespuesIf = lineaIf.substring(posIf + 3).trim();// después de 'if'

        if (contenidoDespuesIf.isEmpty()) {
            diags.add(new Diagnostico(E_IF_FALTA_CONDICION, numeroLineaIf, null,
                    "Falta la condición después de 'if'"));
            return;
        }

        // Buscar 'then' (con word boundary)
        Pattern patternThen = Pattern.compile("\\bthen\\b");
        Matcher matcherThen = patternThen.matcher(contenidoDespuesIf.toLowerCase());

        if (!matcherThen.find()) {
            diags.add(new Diagnostico(E_IF_FALTA_THEN, numeroLineaIf, null,
                    "Falta la palabra reservada 'then' después de la condición"));
            return;
        }

        int posThen = matcherThen.start();// posición de 'then' en contenidoDespuesIf
        String condicion = contenidoDespuesIf.substring(0, posThen).trim();

        if (condicion.isEmpty()) {
            diags.add(new Diagnostico(E_IF_FALTA_CONDICION, numeroLineaIf, null,
                    "Falta la condición después de 'if'"));
            return;
        }

        // Validar bloque THEN
        int indiceSiguiente = indiceIf + 1;

        // 1. Validar begin después del then
        if (indiceSiguiente >= lineas.length) {
            diags.add(new Diagnostico(E_IF_FALTA_BEGIN_THEN, numeroLineaIf + 1, null,
                    "Falta 'begin' en la línea siguiente al 'if-then'"));
            return;
        }

        String lineaBeginThen = quitarComentarios(lineas[indiceSiguiente]);
        String lineaBeginThenTrim = lineaBeginThen.trim();

        if (!lineaBeginThenTrim.equalsIgnoreCase("begin")) {
            diags.add(new Diagnostico(E_IF_FALTA_BEGIN_THEN, numeroLineaIf + 1, null,
                    "Debe haber un 'begin' en la línea siguiente al 'if-then'"));
            return;
        }

        // Validar indentación de begin (4 espacios adicionales respecto al if)
        String indentacionEsperadaBeginThen = indentacionIf + "    ";
        if (!lineaBeginThen.startsWith(indentacionEsperadaBeginThen + "begin")) {
            diags.add(new Diagnostico(E_IF_BEGIN_THEN_INDENTACION, numeroLineaIf + 1, null,
                    "El 'begin' debe tener 4 espacios de indentación respecto al 'if'"));
        }

        // 2. Validar sentencia dentro del then
        indiceSiguiente++;
        if (indiceSiguiente >= lineas.length) {
            diags.add(new Diagnostico(E_IF_FALTA_SENTENCIA_THEN, numeroLineaIf + 2, null,
                    "Debe haber al menos una sentencia después de 'begin' en el bloque then"));
            return;
        }

        String lineaSentenciaThen = quitarComentarios(lineas[indiceSiguiente]);
        String sentenciaThenTrim = lineaSentenciaThen.trim();

        if (sentenciaThenTrim.isEmpty() || sentenciaThenTrim.equalsIgnoreCase("end")
                || sentenciaThenTrim.equalsIgnoreCase("end;")) {
            diags.add(new Diagnostico(E_IF_FALTA_SENTENCIA_THEN, numeroLineaIf + 2, null,
                    "Debe haber al menos una sentencia entre 'begin' y 'end' en el bloque then"));
            return;
        }

        // Validar punto y coma en la sentencia
        if (!sentenciaThenTrim.endsWith(";")) {
            diags.add(new Diagnostico(E_IF_SENTENCIA_THEN_SIN_PUNTO_COMA, numeroLineaIf + 2, null,
                    "La sentencia dentro del bloque then debe terminar con punto y coma"));
        }

        // Validar indentación de la sentencia (4 espacios adicionales respecto a begin)
        String indentacionEsperadaSentenciaThen = indentacionEsperadaBeginThen + "    ";
        if (!lineaSentenciaThen.startsWith(indentacionEsperadaSentenciaThen)) {
            diags.add(new Diagnostico(E_IF_SENTENCIA_THEN_INDENTACION, numeroLineaIf + 2, null,
                    "La sentencia debe tener 4 espacios de indentación respecto al 'begin'"));
        }

        // 3. Validar end del bloque then (SIN punto y coma)
        indiceSiguiente++;
        if (indiceSiguiente >= lineas.length) {
            diags.add(new Diagnostico(E_IF_FALTA_END_THEN, numeroLineaIf + 3, null,
                    "Falta 'end' después de la sentencia en el bloque then"));
            return;
        }

        String lineaEndThen = quitarComentarios(lineas[indiceSiguiente]);
        String lineaEndThenTrim = lineaEndThen.trim();

        if (!lineaEndThenTrim.toLowerCase().startsWith("end")) {
            diags.add(new Diagnostico(E_IF_FALTA_END_THEN, numeroLineaIf + 3, null,
                    "Debe haber un 'end' después de la sentencia en el bloque then"));
            return;
        }

        // Validar que NO tenga punto y coma
        if (lineaEndThenTrim.equals("end;")) {
            diags.add(new Diagnostico(E_IF_END_THEN_CON_PUNTO_COMA, numeroLineaIf + 3, null,
                    "El 'end' del bloque then NO debe terminar con punto y coma"));
        }

        // Validar indentación de end (misma que begin)
        if (!lineaEndThen.startsWith(indentacionEsperadaBeginThen + "end")) {
            diags.add(new Diagnostico(E_IF_END_THEN_INDENTACION, numeroLineaIf + 3, null,
                    "El 'end' debe tener la misma indentación que el 'begin' (4 espacios respecto al 'if')"));
        }

        // 4. Validar ELSE
        indiceSiguiente++;
        if (indiceSiguiente >= lineas.length) {
            diags.add(new Diagnostico(E_IF_FALTA_ELSE, numeroLineaIf + 4, null,
                    "Falta la palabra reservada 'else' después del bloque then"));
            return;
        }

        String lineaElse = quitarComentarios(lineas[indiceSiguiente]);
        String lineaElseTrim = lineaElse.trim();

        if (!lineaElseTrim.equalsIgnoreCase("else")) {
            diags.add(new Diagnostico(E_IF_FALTA_ELSE, numeroLineaIf + 4, null,
                    "Debe haber un 'else' después del bloque then"));
            return;
        }

        // Validar indentación de else (misma que if)
        if (!lineaElse.startsWith(indentacionIf + "else")) {
            diags.add(new Diagnostico(E_IF_ELSE_INDENTACION, numeroLineaIf + 4, null,
                    "El 'else' debe tener la misma indentación que el 'if'"));
        }

        // 5. Validar begin después del else
        indiceSiguiente++;
        if (indiceSiguiente >= lineas.length) {
            diags.add(new Diagnostico(E_IF_FALTA_BEGIN_ELSE, numeroLineaIf + 5, null,
                    "Falta 'begin' en la línea siguiente al 'else'"));
            return;
        }

        String lineaBeginElse = quitarComentarios(lineas[indiceSiguiente]);
        String lineaBeginElseTrim = lineaBeginElse.trim();

        if (!lineaBeginElseTrim.equalsIgnoreCase("begin")) {
            diags.add(new Diagnostico(E_IF_FALTA_BEGIN_ELSE, numeroLineaIf + 5, null,
                    "Debe haber un 'begin' en la línea siguiente al 'else'"));
            return;
        }

        // Validar indentación de begin del else (4 espacios adicionales respecto al else)
        String indentacionEsperadaBeginElse = indentacionIf + "    ";
        if (!lineaBeginElse.startsWith(indentacionEsperadaBeginElse + "begin")) {
            diags.add(new Diagnostico(E_IF_BEGIN_ELSE_INDENTACION, numeroLineaIf + 5, null,
                    "El 'begin' debe tener 4 espacios de indentación respecto al 'else'"));
        }

        // 6. Validar sentencia dentro del else
        indiceSiguiente++;
        if (indiceSiguiente >= lineas.length) {
            diags.add(new Diagnostico(E_IF_FALTA_SENTENCIA_ELSE, numeroLineaIf + 6, null,
                    "Debe haber al menos una sentencia después de 'begin' en el bloque else"));
            return;
        }

        String lineaSentenciaElse = quitarComentarios(lineas[indiceSiguiente]);
        String sentenciaElseTrim = lineaSentenciaElse.trim();

        if (sentenciaElseTrim.isEmpty() || sentenciaElseTrim.equalsIgnoreCase("end")
                || sentenciaElseTrim.equalsIgnoreCase("end;")) {
            diags.add(new Diagnostico(E_IF_FALTA_SENTENCIA_ELSE, numeroLineaIf + 6, null,
                    "Debe haber al menos una sentencia entre 'begin' y 'end;' en el bloque else"));
            return;
        }

        // Validar punto y coma en la sentencia
        if (!sentenciaElseTrim.endsWith(";")) {
            diags.add(new Diagnostico(E_IF_SENTENCIA_ELSE_SIN_PUNTO_COMA, numeroLineaIf + 6, null,
                    "La sentencia dentro del bloque else debe terminar con punto y coma"));
        }

        // Validar indentación de la sentencia (4 espacios adicionales respecto a begin)
        String indentacionEsperadaSentenciaElse = indentacionEsperadaBeginElse + "    ";
        if (!lineaSentenciaElse.startsWith(indentacionEsperadaSentenciaElse)) {
            diags.add(new Diagnostico(E_IF_SENTENCIA_ELSE_INDENTACION, numeroLineaIf + 6, null,
                    "La sentencia debe tener 4 espacios de indentación respecto al 'begin'"));
        }

        // 7. Validar end; del bloque else (CON punto y coma)
        indiceSiguiente++;
        if (indiceSiguiente >= lineas.length) {
            diags.add(new Diagnostico(E_IF_FALTA_END_ELSE, numeroLineaIf + 7, null,
                    "Falta 'end;' después de la sentencia en el bloque else"));
            return;
        }

        String lineaEndElse = quitarComentarios(lineas[indiceSiguiente]);
        String lineaEndElseTrim = lineaEndElse.trim();

        if (!lineaEndElseTrim.toLowerCase().startsWith("end")) {
            diags.add(new Diagnostico(E_IF_FALTA_END_ELSE, numeroLineaIf + 7, null,
                    "Debe haber un 'end;' después de la sentencia en el bloque else"));
            return;
        }

        // Validar que SÍ tenga punto y coma
        if (!lineaEndElseTrim.equalsIgnoreCase("end;")) {
            diags.add(new Diagnostico(E_IF_END_ELSE_SIN_PUNTO_COMA, numeroLineaIf + 7, null,
                    "El 'end' del bloque else debe terminar con punto y coma (end;)"));
        }

        // Validar indentación de end; (misma que begin del else)
        if (!lineaEndElse.startsWith(indentacionEsperadaBeginElse + "end")) {
            diags.add(new Diagnostico(E_IF_END_ELSE_INDENTACION, numeroLineaIf + 7, null, // ← CORRECTO
                    "El 'end;' debe tener la misma indentación que el 'begin' (4 espacios respecto al 'else')"));
        }
    }
    
    // Método para quitar comentarios de una línea
    private static String quitarComentarios(String linea) {
        String resultado = linea;

        int pos = resultado.indexOf("//");
        if (pos >= 0) {
            resultado = resultado.substring(0, pos);
        }

        pos = resultado.indexOf("{");
        if (pos >= 0) {
            resultado = resultado.substring(0, pos);
        }

        pos = resultado.indexOf("(*");
        if (pos >= 0) {
            resultado = resultado.substring(0, pos);
        }

        return resultado;
    }
}
