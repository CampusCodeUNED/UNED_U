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

public final class ValidadorRepeat {

    // Códigos de error para REPEAT (1100-1199)
    public static final int E_REPEAT_CONTENIDO_EN_LINEA = 1100;
    public static final int E_REPEAT_FALTA_BEGIN = 1101;
    public static final int E_REPEAT_BEGIN_INDENTACION = 1102;
    public static final int E_REPEAT_FALTA_SENTENCIA = 1103;
    public static final int E_REPEAT_SENTENCIA_SIN_PUNTO_COMA = 1104;
    public static final int E_REPEAT_SENTENCIA_INDENTACION = 1105;
    public static final int E_REPEAT_FALTA_END = 1106;
    public static final int E_REPEAT_END_SIN_PUNTO_COMA = 1107;
    public static final int E_REPEAT_END_INDENTACION = 1108;
    public static final int E_REPEAT_FALTA_UNTIL = 1109;
    public static final int E_REPEAT_UNTIL_FALTA_CONDICION = 1110;
    public static final int E_REPEAT_UNTIL_SIN_PUNTO_COMA = 1111;
    public static final int E_REPEAT_UNTIL_INDENTACION = 1112;

    // Patrón para detectar línea repeat
    private static final Pattern PATRON_REPEAT = Pattern.compile(
            "^(\\s*)repeat\\s*(.*)$",
            Pattern.CASE_INSENSITIVE
    );
    // Resultado del análisis de repeat
    public static final class Result {

        public final List<Diagnostico> diags;

        public Result(List<Diagnostico> diags) {
            this.diags = diags;
        }
    }
    // Método principal para analizar bloques repeat...until
    public Result check(Fuente fuente, int lineaBegin, int lineaEnd) {
        List<Diagnostico> diags = new ArrayList<>();
        String[] lineas = fuente.getText().split("\\r?\\n", -1);

        if (lineaBegin <= 0 || lineaEnd <= 0) {
            return new Result(diags);
        }

        // Buscar líneas con 'repeat' dentro del bloque begin...end
        for (int i = lineaBegin; i < lineaEnd - 1; i++) {
            String lineaActual = lineas[i];
            String lineaLimpia = quitarComentarios(lineaActual);
            String lineaLimpiaLower = lineaLimpia.toLowerCase().trim();

            if (!lineaLimpiaLower.equals("repeat") && !lineaLimpiaLower.startsWith("repeat ")) {
                continue;
            }

            int numeroLinea = i + 1;
            validarRepeat(lineas, i, numeroLinea, diags);
        }

        return new Result(diags);
    }
    // Validar un bloque repeat...until comenzando en la línea dada
    private void validarRepeat(String[] lineas, int indiceRepeat, int numeroLineaRepeat,
            List<Diagnostico> diags) {
        String lineaRepeat = quitarComentarios(lineas[indiceRepeat]);

        // Extraer indentación del repeat
        int indentacionCount = 0;
        for (int i = 0; i < lineaRepeat.length(); i++) {
            if (lineaRepeat.charAt(i) == ' ') {
                indentacionCount++;
            } else {
                break;
            }
        }
        String indentacionRepeat = lineaRepeat.substring(0, indentacionCount);

        // Validar que repeat no tenga contenido adicional en la misma línea
        Matcher matcher = PATRON_REPEAT.matcher(lineaRepeat);
        if (matcher.matches()) {
            String contenidoDespues = matcher.group(2).trim();
            if (!contenidoDespues.isEmpty()) {
                diags.add(new Diagnostico(E_REPEAT_CONTENIDO_EN_LINEA, numeroLineaRepeat, null,
                        "La palabra 'repeat' no debe tener contenido adicional en la misma línea"));
            }
        }

        // 1. Validar begin en la siguiente línea
        int indiceSiguiente = indiceRepeat + 1;
        if (indiceSiguiente >= lineas.length) {
            diags.add(new Diagnostico(E_REPEAT_FALTA_BEGIN, numeroLineaRepeat + 1, null,
                    "Falta 'begin' en la línea siguiente al 'repeat'"));
            return;
        }

        String lineaBegin = quitarComentarios(lineas[indiceSiguiente]);
        String lineaBeginTrim = lineaBegin.trim();

        if (!lineaBeginTrim.equalsIgnoreCase("begin")) {
            diags.add(new Diagnostico(E_REPEAT_FALTA_BEGIN, numeroLineaRepeat + 1, null,
                    "Debe haber un 'begin' en la línea siguiente al 'repeat'"));
            return;
        }

        // Validar indentación de begin (4 espacios adicionales respecto a repeat)
        String indentacionEsperadaBegin = indentacionRepeat + "    ";
        if (!lineaBegin.startsWith(indentacionEsperadaBegin + "begin")) {
            diags.add(new Diagnostico(E_REPEAT_BEGIN_INDENTACION, numeroLineaRepeat + 1, null,
                    "El 'begin' debe tener 4 espacios de indentación respecto al 'repeat'"));
        }

        // 2. Validar sentencia
        indiceSiguiente++;
        if (indiceSiguiente >= lineas.length) {
            diags.add(new Diagnostico(E_REPEAT_FALTA_SENTENCIA, numeroLineaRepeat + 2, null,
                    "Debe haber al menos una sentencia después de 'begin'"));
            return;
        }

        String lineaSentencia = quitarComentarios(lineas[indiceSiguiente]);
        String sentenciaTrim = lineaSentencia.trim();

        if (sentenciaTrim.isEmpty() || sentenciaTrim.equalsIgnoreCase("end")
                || sentenciaTrim.equalsIgnoreCase("end;")) {
            diags.add(new Diagnostico(E_REPEAT_FALTA_SENTENCIA, numeroLineaRepeat + 2, null,
                    "Debe haber al menos una sentencia entre 'begin' y 'end;'"));
            return;
        }

        // Validar punto y coma en la sentencia
        if (!sentenciaTrim.endsWith(";")) {
            diags.add(new Diagnostico(E_REPEAT_SENTENCIA_SIN_PUNTO_COMA, numeroLineaRepeat + 2, null,
                    "La sentencia dentro del 'repeat' debe terminar con punto y coma"));
        }

        // Validar indentación de la sentencia (4 espacios adicionales respecto a begin)
        String indentacionEsperadaSentencia = indentacionEsperadaBegin + "    ";
        if (!lineaSentencia.startsWith(indentacionEsperadaSentencia)) {
            diags.add(new Diagnostico(E_REPEAT_SENTENCIA_INDENTACION, numeroLineaRepeat + 2, null,
                    "La sentencia debe tener 4 espacios de indentación respecto al 'begin'"));
        }

        // 3. Validar end;
        indiceSiguiente++;
        if (indiceSiguiente >= lineas.length) {
            diags.add(new Diagnostico(E_REPEAT_FALTA_END, numeroLineaRepeat + 3, null,
                    "Falta 'end;' después de la sentencia"));
            return;
        }

        String lineaEnd = quitarComentarios(lineas[indiceSiguiente]);
        String lineaEndTrim = lineaEnd.trim();

        if (!lineaEndTrim.toLowerCase().startsWith("end")) {
            diags.add(new Diagnostico(E_REPEAT_FALTA_END, numeroLineaRepeat + 3, null,
                    "Debe haber un 'end;' después de la sentencia"));
            return;
        }

        // Validar que tenga punto y coma
        if (!lineaEndTrim.equalsIgnoreCase("end;")) {
            diags.add(new Diagnostico(E_REPEAT_END_SIN_PUNTO_COMA, numeroLineaRepeat + 3, null,
                    "El 'end' del bucle 'repeat' debe terminar con punto y coma (end;)"));
        }

        // Validar indentación de end; (misma que begin)
        if (!lineaEnd.startsWith(indentacionEsperadaBegin + "end")) {
            diags.add(new Diagnostico(E_REPEAT_END_INDENTACION, numeroLineaRepeat + 3, null,
                    "El 'end;' debe tener la misma indentación que el 'begin' (4 espacios respecto al 'repeat')"));
        }

        // 4. Validar until condición;
        indiceSiguiente++;
        if (indiceSiguiente >= lineas.length) {
            diags.add(new Diagnostico(E_REPEAT_FALTA_UNTIL, numeroLineaRepeat + 4, null,
                    "Falta 'until' después de 'end;'"));
            return;
        }

        String lineaUntil = quitarComentarios(lineas[indiceSiguiente]);
        String lineaUntilTrim = lineaUntil.trim();
        String lineaUntilLower = lineaUntilTrim.toLowerCase();

        // Verificar si existe la palabra until
        if (!lineaUntilLower.startsWith("until")) {
            diags.add(new Diagnostico(E_REPEAT_FALTA_UNTIL, numeroLineaRepeat + 4, null,
                    "Debe haber un 'until' después de 'end;'"));
            return;
        }

        // Extraer contenido después de until
        String contenidoDespuesUntil = "";
        if (lineaUntilLower.length() > 5) {
            contenidoDespuesUntil = lineaUntilTrim.substring(5).trim();
            // Quitar el punto y coma para verificar si hay condición
            if (contenidoDespuesUntil.endsWith(";")) {
                contenidoDespuesUntil = contenidoDespuesUntil.substring(0, contenidoDespuesUntil.length() - 1).trim();
            }
        }

        // Validar que tenga condición
        if (contenidoDespuesUntil.isEmpty()) {
            diags.add(new Diagnostico(E_REPEAT_UNTIL_FALTA_CONDICION, numeroLineaRepeat + 4, null,
                    "La sentencia 'until' debe tener una condición"));
            return;
        }

        // Validar que termine con punto y coma
        if (!lineaUntilTrim.endsWith(";")) {
            diags.add(new Diagnostico(E_REPEAT_UNTIL_SIN_PUNTO_COMA, numeroLineaRepeat + 4, null,
                    "La sentencia 'until' debe terminar con punto y coma"));
        }

        // Validar indentación de until (misma que repeat)
        if (!lineaUntil.startsWith(indentacionRepeat + "until")) {
            diags.add(new Diagnostico(E_REPEAT_UNTIL_INDENTACION, numeroLineaRepeat + 4, null,
                    "El 'until' debe tener la misma indentación que el 'repeat'"));
        }
    }
    // Busca la línea donde aparece una palabra clave específica
    private static String quitarComentarios(String linea) {
        String resultado = linea;
        // Quitar comentarios simples de una línea (//, { }, (* *))
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
