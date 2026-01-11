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
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ValidadorEncabezadoProgram {

    // Errores del punto 4 (400-499)
    public static final int E_PROG_FALTA_PRIMERO = 400;
    public static final int E_PROG_ANTES_CONTENIDO = 410;
    public static final int E_PROG_NOMBRE_NO_COINCIDE = 420;
    public static final int E_PROG_FALTA_PUNTO_COMA = 430;
    public static final int E_PROG_REPETIDO = 440;
    public static final int E_USES_FALTANTE = 450;
    public static final int E_USES_FORMATO_MALO = 451;
    public static final int E_USES_NO_INMEDIATO = 452;

    // Patrón que funciona con o sin numeración
    private static final Pattern LINEA_PROGRAM
            = Pattern.compile("^(?:\\d{4}\\s+)?program\\s+(.+?)\\s*;\\s*$", Pattern.CASE_INSENSITIVE);

    private static final Pattern LINEA_USES
            = Pattern.compile("^(?:\\d{4}\\s+)?uses\\s+.+;\\s*$", Pattern.CASE_INSENSITIVE);

    // Verifica que el encabezado del programa siga las reglas de Pascal
    public List<Diagnostico> verificar(Fuente fuente, String raizArchivo) {
        List<Diagnostico> diags = new ArrayList<>();

        String[] lineas = fuente.getTexto().split("\\r?\\n", -1);
        if (lineas.length == 0) {
            diags.add(new Diagnostico(E_PROG_FALTA_PRIMERO, 1, null, "No encuentra la palabra inicial program"));
            return diags;
        }

        int numeroLineaProgram = -1;
        String lineaProgram = null;

        for (int i = 0; i < lineas.length; i++) {
            String linea = lineas[i];
            String contenidoLinea = quitarNumeroLinea(linea).trim();
            if (contenidoLinea.toLowerCase().startsWith("program")) {
                numeroLineaProgram = i + 1;
                lineaProgram = linea;
                break;
            }
        }

        if (numeroLineaProgram == -1 || lineaProgram == null) {
            diags.add(new Diagnostico(E_PROG_FALTA_PRIMERO, 1, null, "No encuentra la palabra inicial program"));
            return diags;
        }

        // 1) Validar línea program
        // Verificación básica: debe contener "program" y terminar con ";"
        if (!lineaProgram.trim().endsWith(";")) {
            diags.add(new Diagnostico(E_PROG_FALTA_PUNTO_COMA, numeroLineaProgram, null, "La sentencia program debe finalizar con punto y coma ';'"));
            return diags;
        }

        // Aplicar regex que funciona con o sin numeración
        Matcher comparadorProgram = LINEA_PROGRAM.matcher(lineaProgram);
        String nombrePrograma = null;

        if (comparadorProgram.matches()) {
            nombrePrograma = comparadorProgram.group(1).trim();
        } else {
            String contenidoSinNumero = quitarNumeroLinea(lineaProgram).trim();
            // Buscar patrón: program <nombre> ;
            Pattern nombreSimple = Pattern.compile("program\\s+([^\\s;]+)", Pattern.CASE_INSENSITIVE);
            Matcher coincidenciaSimple = nombreSimple.matcher(contenidoSinNumero);
            if (coincidenciaSimple.find()) {
                nombrePrograma = coincidenciaSimple.group(1);
            } else {
                diags.add(new Diagnostico(E_PROG_FALTA_PRIMERO, numeroLineaProgram, null, "Formato de línea program inválido (se espera: program <identificador>;)"));
                return diags;
            }
        }

        // 2) Verificar coincidencia de nombres 
        if (nombrePrograma != null && raizArchivo != null && !raizArchivo.trim().isEmpty()) {
            String raiz = raizArchivo.toLowerCase(Locale.ROOT);
            if (!nombrePrograma.toLowerCase(Locale.ROOT).equals(raiz)) {
                diags.add(new Diagnostico(E_PROG_NOMBRE_NO_COINCIDE, numeroLineaProgram, null,
                        "El nombre del programa '" + nombrePrograma + "' no coincide con el nombre del archivo '" + raiz + "'"));
            }
        }

        // 3) Verificar que program no se repita
        for (int i = 0; i < lineas.length; i++) {
            if (i + 1 == numeroLineaProgram) {
                continue;
            }
            String limpia = quitarComentariosYCadenas(lineas[i]);
            if (contienePalabra(limpia, "program")) {
                diags.add(new Diagnostico(E_PROG_REPETIDO, i + 1, null,
                        "La palabra 'program' no debe repetirse fuera de la primera línea"));
            }
        }

        // 4) Validar línea uses (debe estar después de program)
        int numeroLineaUses = -1;
        String lineaUses = null;

        for (int i = numeroLineaProgram; i < lineas.length; i++) {
            String linea = lineas[i];
            String contenidoLinea = quitarNumeroLinea(linea).trim();
            if (contenidoLinea.toLowerCase().startsWith("uses")) {
                numeroLineaUses = i + 1;
                lineaUses = linea;
                break;
            }
        }

        if (numeroLineaUses == -1) {
            diags.add(new Diagnostico(E_USES_FALTANTE, numeroLineaProgram + 1, null, "Después de program debe venir la palabra uses"));
            return diags;
        }

        // Verificar que uses venga inmediatamente después de program (sin líneas intermedias con contenido)
        boolean tieneContenidoEntremedias = false;
        for (int i = numeroLineaProgram; i < numeroLineaUses - 1; i++) {
            String contenidoLinea = quitarNumeroLinea(lineas[i]).trim();
            if (!contenidoLinea.isEmpty() && !esSoloComentario(contenidoLinea)) {
                tieneContenidoEntremedias = true;
                break;
            }
        }

        if (tieneContenidoEntremedias) {
            diags.add(new Diagnostico(E_USES_NO_INMEDIATO, numeroLineaUses, 1,
                    "Entre program y uses no debe haber espacios, comentarios, líneas vacías o tabs; uses debe iniciar en columna 1"));
        }

        // Validar formato completo de uses
        if (!LINEA_USES.matcher(lineaUses).matches()) {
            diags.add(new Diagnostico(E_USES_FORMATO_MALO, numeroLineaUses, null, "La sentencia uses debe tener contenido y finalizar con ';'"));
        }

        return diags;
    }

    // Métodos auxiliares simplificados
    // Quita el número de línea si la línea está numerada
    private static String quitarNumeroLinea(String linea) {
        if (linea == null || linea.length() < 5) {
            return linea;
        }
        // Si empieza con 4 dígitos y un espacio, quitarlos
        if (linea.matches("^\\d{4} .*")) {
            return linea.substring(5);
        }
        return linea;
    }

    // Determina si una línea solo contiene un comentario
    private static boolean esSoloComentario(String linea) {
        String recortada = linea.trim();
        return recortada.startsWith("//") || recortada.startsWith("{") || recortada.startsWith("(*");
    }

    // Elimina comentarios y cadenas de texto de una línea
    private static String quitarComentariosYCadenas(String linea) {
        if (linea == null) {
            return "";
        }

        StringBuilder resultado = new StringBuilder();
        boolean enCadena = false;

        for (int i = 0; i < linea.length(); i++) {
            char c = linea.charAt(i);

            if (enCadena) {
                if (c == '\'') {
                    if (i + 1 < linea.length() && linea.charAt(i + 1) == '\'') {
                        i++;
                    } else {
                        enCadena = false;
                    }
                }
                continue;
            }

            if (c == '\'') {
                enCadena = true;
                resultado.append(' ');
                continue;
            }

            if (c == '{' || (c == '/' && i + 1 < linea.length() && linea.charAt(i + 1) == '/')) {
                break;
            }

            if (c == '(' && i + 1 < linea.length() && linea.charAt(i + 1) == '*') {
                int posicionCierre = linea.indexOf("*)", i + 2);
                if (posicionCierre >= 0) {
                    i = posicionCierre + 1;
                    resultado.append(' ');
                    continue;
                } else {
                    break;
                }
            }

            resultado.append(c);
        }

        return resultado.toString();
    }

    // Verifica si una cadena contiene una palabra completa (con límites de palabra)
    private static boolean contienePalabra(String s, String palabra) {
        if (s == null || s.trim().isEmpty()) {
            return false;
        }
        String minuscula = s.toLowerCase(Locale.ROOT);
        String w = palabra.toLowerCase(Locale.ROOT);

        String patron = "\\b" + w + "\\b";
        return minuscula.matches(".*" + patron + ".*");
    }
}
