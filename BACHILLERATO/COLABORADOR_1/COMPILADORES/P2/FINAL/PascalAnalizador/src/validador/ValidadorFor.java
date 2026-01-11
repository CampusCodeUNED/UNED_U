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

public final class ValidadorFor {

    // Códigos de error para FOR (900-999)
    public static final int E_FOR_FALTA_VARIABLE = 900;
    public static final int E_FOR_VARIABLE_NO_DECLARADA = 901;
    public static final int E_FOR_ASIGNACION_FORMATO = 902;
    public static final int E_FOR_FALTA_VALOR_INICIAL = 903;
    public static final int E_FOR_FALTA_TO = 904;
    public static final int E_FOR_FALTA_VALOR_FINAL = 905;
    public static final int E_FOR_FALTA_DO = 906;
    public static final int E_FOR_FALTA_BEGIN = 907;
    public static final int E_FOR_BEGIN_INDENTACION = 908;
    public static final int E_FOR_FALTA_SENTENCIA = 909;
    public static final int E_FOR_SENTENCIA_SIN_PUNTO_COMA = 910;
    public static final int E_FOR_SENTENCIA_INDENTACION = 911;
    public static final int E_FOR_FALTA_END = 912;
    public static final int E_FOR_END_SIN_PUNTO_COMA = 913;
    public static final int E_FOR_END_INDENTACION = 914;

    // Patrón mejorado para capturar la estructura completa del for
    private static final Pattern PATRON_FOR_LINEA = Pattern.compile(
            "^(\\s*)for\\s+([A-Za-z][A-Za-z_]*)\\s*(:=)\\s*(.+)$",
            Pattern.CASE_INSENSITIVE
    );

    public static final class Resultado {

        public final List<Diagnostico> diags;

        public Resultado(List<Diagnostico> diags) {
            this.diags = diags;
        }
    }
    // Método principal para validar los bloques 'for' en el código fuente
    public Resultado validar(Fuente fuente, int lineaBegin, int lineaEnd, Set<String> variablesDeclaradas) {
        List<Diagnostico> diags = new ArrayList<>();
        String[] lineas = fuente.getText().split("\\r?\\n", -1);

        if (lineaBegin <= 0 || lineaEnd <= 0) {
            return new Resultado(diags);
        }

        // Buscar líneas con 'for' dentro del bloque begin...end
        for (int i = lineaBegin; i < lineaEnd - 1; i++) {
            String lineaActual = lineas[i];
            String lineaLimpia = quitarComentarios(lineaActual);
            String lineaLimpiaLower = lineaLimpia.toLowerCase().trim();

            if (!lineaLimpiaLower.startsWith("for ")) {// No es una línea con 'for'
                continue;
            }

            int numeroLinea = i + 1;
            validarFor(lineas, i, numeroLinea, variablesDeclaradas, diags);
        }

        return new Resultado(diags);
    }

    private void validarFor(String[] lineas, int indiceFor, int numeroLineaFor,
            Set<String> variablesDeclaradas, List<Diagnostico> diags) {
        // Validar la estructura completa del 'for' a partir de la línea indicada
        // (variable := valorInicial to valorFinal do begin ... end;)
        String lineaFor = quitarComentarios(lineas[indiceFor]);

        // Extraer indentación (espacios al inicio)
        int indentacionCount = 0;
        for (int i = 0; i < lineaFor.length(); i++) {
            if (lineaFor.charAt(i) == ' ') {// Contar espacios al inicio para indentación
                indentacionCount++;
            } else {
                break;
            }
        }
        String indentacionFor = lineaFor.substring(0, indentacionCount);

        // Buscar el inicio de 'for' (case insensitive)
        String lineaLower = lineaFor.toLowerCase();
        int posFor = lineaLower.indexOf("for ");
        if (posFor < 0) {
            return;
        }

        String contenidoDespuesFor = lineaFor.substring(posFor + 4).trim();

        // 1. Extraer variable (hasta encontrar espacio o :=)
        StringBuilder varBuilder = new StringBuilder();
        int i = 0;
        while (i < contenidoDespuesFor.length()) {
            char c = contenidoDespuesFor.charAt(i);
            if (Character.isWhitespace(c) || c == ':') {// Detecta espacio o inicio de asignación
                break;
            }
            varBuilder.append(c);
            i++;
        }

        String variable = varBuilder.toString().trim();

        if (variable.isEmpty()) {
            diags.add(new Diagnostico(E_FOR_FALTA_VARIABLE, numeroLineaFor, null,
                    "Falta el identificador de variable después de 'for'"));
            return;
        }

        // Validar que la variable esté declarada
        if (!variablesDeclaradas.contains(variable)) {
            diags.add(new Diagnostico(E_FOR_VARIABLE_NO_DECLARADA, numeroLineaFor, null,
                    "La variable '" + variable + "' debe estar declarada correctamente antes de usarse en el for"));
        }

        // 2. Buscar y validar :=
        int posAsignacion = contenidoDespuesFor.indexOf(":=");
        if (posAsignacion < 0) {
            diags.add(new Diagnostico(E_FOR_ASIGNACION_FORMATO, numeroLineaFor, null,
                    "Falta el operador de asignación ':=' después de la variable"));
            return;
        }

        // Verificar espacios alrededor de :=
        String antesAsignacion = contenidoDespuesFor.substring(0, posAsignacion);
        boolean espacioAntes = antesAsignacion.endsWith(" ");

        boolean espacioDespues = false;
        if (posAsignacion + 2 < contenidoDespuesFor.length()) {
            char siguienteChar = contenidoDespuesFor.charAt(posAsignacion + 2);
            espacioDespues = Character.isWhitespace(siguienteChar);
        }

        if (!espacioAntes || !espacioDespues) {
            diags.add(new Diagnostico(E_FOR_ASIGNACION_FORMATO, numeroLineaFor, null,
                    "El operador ':=' debe tener un espacio antes y después"));
        }

        // 3. Extraer contenido después de :=
        String despuesAsignacion = contenidoDespuesFor.substring(posAsignacion + 2).trim();

        if (despuesAsignacion.isEmpty()) {
            diags.add(new Diagnostico(E_FOR_FALTA_VALOR_INICIAL, numeroLineaFor, null,
                    "Falta el valor inicial después de ':='"));
            return;
        }

        // 4. Buscar 'to' (con word boundary)
        String despuesAsignacionLower = despuesAsignacion.toLowerCase();
        Pattern patternTo = Pattern.compile("\\bto\\b");
        Matcher matcherTo = patternTo.matcher(despuesAsignacionLower);

        if (!matcherTo.find()) {
            diags.add(new Diagnostico(E_FOR_FALTA_TO, numeroLineaFor, null,
                    "Falta la palabra reservada 'to' después del valor inicial"));
            return;
        }

        int posTo = matcherTo.start();// Posición de 'to'
        String valorInicial = despuesAsignacion.substring(0, posTo).trim();

        if (valorInicial.isEmpty()) {
            diags.add(new Diagnostico(E_FOR_FALTA_VALOR_INICIAL, numeroLineaFor, null,
                    "Falta el valor inicial después de ':='"));
            return;
        }

        // 5. Extraer contenido después de 'to'
        String despuesTo = despuesAsignacion.substring(posTo + 2).trim();

        if (despuesTo.isEmpty()) {
            diags.add(new Diagnostico(E_FOR_FALTA_VALOR_FINAL, numeroLineaFor, null,
                    "Falta el valor final después de 'to'"));
            return;
        }

        // 6. Buscar 'do' (con word boundary)
        Pattern patternDo = Pattern.compile("\\bdo\\b");
        Matcher matcherDo = patternDo.matcher(despuesTo.toLowerCase());

        if (!matcherDo.find()) {
            diags.add(new Diagnostico(E_FOR_FALTA_DO, numeroLineaFor, null,
                    "Falta la palabra reservada 'do' después del valor final"));
            return;
        }

        int posDo = matcherDo.start();
        String valorFinal = despuesTo.substring(0, posDo).trim();

        if (valorFinal.isEmpty()) {
            diags.add(new Diagnostico(E_FOR_FALTA_VALOR_FINAL, numeroLineaFor, null,
                    "Falta el valor final después de 'to'"));
            return;
        }

        // 7. Validar 'begin' en la siguiente línea
        if (indiceFor + 1 >= lineas.length) {
            diags.add(new Diagnostico(E_FOR_FALTA_BEGIN, numeroLineaFor + 1, null,
                    "Falta 'begin' en la línea siguiente al 'for'"));
            return;
        }

        String lineaBegin = quitarComentarios(lineas[indiceFor + 1]);// Línea siguiente al for
        String lineaBeginTrim = lineaBegin.trim();

        if (!lineaBeginTrim.equalsIgnoreCase("begin")) {
            diags.add(new Diagnostico(E_FOR_FALTA_BEGIN, numeroLineaFor + 1, null,
                    "Debe haber un 'begin' en la línea siguiente al 'for'"));
            return;
        }

        // Validar indentación de begin (4 espacios adicionales respecto al for)
        String indentacionEsperadaBegin = indentacionFor + "    ";
        if (!lineaBegin.startsWith(indentacionEsperadaBegin + "begin")) {
            diags.add(new Diagnostico(E_FOR_BEGIN_INDENTACION, numeroLineaFor + 1, null,
                    "El 'begin' debe tener 4 espacios de indentación respecto al 'for'"));
        }

        // 8. Validar que exista al menos una sentencia
        if (indiceFor + 2 >= lineas.length) {
            diags.add(new Diagnostico(E_FOR_FALTA_SENTENCIA, numeroLineaFor + 2, null,
                    "Debe haber al menos una sentencia después de 'begin'"));
            return;
        }
        // Revisar la línea de la sentencia
        String lineaSentencia = quitarComentarios(lineas[indiceFor + 2]);
        String sentenciaTrim = lineaSentencia.trim();

        if (sentenciaTrim.isEmpty() || sentenciaTrim.equalsIgnoreCase("end")
                || sentenciaTrim.equalsIgnoreCase("end;")) {
            diags.add(new Diagnostico(E_FOR_FALTA_SENTENCIA, numeroLineaFor + 2, null,
                    "Debe haber al menos una sentencia entre 'begin' y 'end;'"));
            return;
        }

        // Validar punto y coma en la sentencia
        if (!sentenciaTrim.endsWith(";")) {
            diags.add(new Diagnostico(E_FOR_SENTENCIA_SIN_PUNTO_COMA, numeroLineaFor + 2, null,
                    "La sentencia dentro del 'for' debe terminar con punto y coma"));
        }

        // Validar indentación de la sentencia (4 espacios adicionales respecto a begin)
        String indentacionEsperadaSentencia = indentacionEsperadaBegin + "    ";
        if (!lineaSentencia.startsWith(indentacionEsperadaSentencia)) {
            diags.add(new Diagnostico(E_FOR_SENTENCIA_INDENTACION, numeroLineaFor + 2, null,
                    "La sentencia debe tener 4 espacios de indentación respecto al 'begin'"));
        }

        // 9. Validar 'end;' 
        if (indiceFor + 3 >= lineas.length) {// No hay línea para end;
            diags.add(new Diagnostico(E_FOR_FALTA_END, numeroLineaFor + 3, null,
                    "Falta 'end;' después de la sentencia"));
            return;
        }

        String lineaEnd = quitarComentarios(lineas[indiceFor + 3]);
        String lineaEndTrim = lineaEnd.trim();

        if (!lineaEndTrim.toLowerCase().startsWith("end")) {// Verificar que la línea comience con "end"
            diags.add(new Diagnostico(E_FOR_FALTA_END, numeroLineaFor + 3, null,
                    "Debe haber un 'end;' después de la sentencia"));
            return;
        }

        // Validar punto y coma en end
        if (!lineaEndTrim.equalsIgnoreCase("end;")) {
            diags.add(new Diagnostico(E_FOR_END_SIN_PUNTO_COMA, numeroLineaFor + 3, null,
                    "El 'end' del bucle 'for' debe terminar con punto y coma (end;)"));
        }

        // Validar indentación de end; (misma que begin)
        if (!lineaEnd.startsWith(indentacionEsperadaBegin + "end")) {
            diags.add(new Diagnostico(E_FOR_END_INDENTACION, numeroLineaFor + 3, null,
                    "El 'end;' debe tener la misma indentación que el 'begin' (4 espacios respecto al 'for')"));
        }
    }

    // Método para quitar comentarios de una línea
    private static String quitarComentarios(String linea) {
        String resultado = linea;
        // Quita comentarios simples de una línea (//, { }, (* *))
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
