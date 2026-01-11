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

import java.util.*;

public final class ValidadorComentarios {

    // Códigos de error para comentarios
    public static final int E_COMENTARIO_SLASH_UNICO = 800; // solo un / en lugar de //
    public static final int E_COMENTARIO_SLASH_CON_ESPACIO = 801; // / / con espacio entre slashes
    public static final int E_COMENTARIO_LLAVE_SIN_CERRAR = 802; // { sin }
    public static final int E_COMENTARIO_MULTILINEA = 803; // comentario multilínea con {}
    public static final int E_COMENTARIO_DESPUES_PUNTO_COMA = 804; // comentario después de ;
    public static final int E_COMENTARIO_ANTES_PROGRAM = 805; // comentario antes de program
    public static final int E_COMENTARIO_DESPUES_END = 806; // comentario después de end.

    public static final class Resultado {

        public final List<Diagnostico> diags;

        public Resultado(List<Diagnostico> diags) {
            this.diags = diags;
        }
    }

    // Verifica los comentarios en el código fuente y devuelve una lista de diagnósticos
    public Resultado verificar(Fuente fuente) {
        List<Diagnostico> diags = new ArrayList<>();
        String[] lineas = fuente.getTexto().split("\\r?\\n", -1);

        int lineaProgram = encontrarLineaProgram(lineas);
        int lineaEnd = encontrarUltimaLineaEnd(lineas);

        for (int i = 0; i < lineas.length; i++) {
            String linea = lineas[i];
            int numeroLinea = i + 1;

            // Remover numeración si existe para el análisis
            String contenido = quitarNumeroLinea(linea);

            // Validar comentarios en esta línea
            validarComentariosEnLinea(contenido, numeroLinea, lineaProgram, lineaEnd, diags);
        }

        return new Resultado(diags);
    }

    // Valida todos los tipos de comentarios en una línea
    private void validarComentariosEnLinea(String linea, int numeroLinea, int lineaProgram, int lineaEnd, List<Diagnostico> diags) {
        // Buscar comentarios //
        validarComentariosSlash(linea, numeroLinea, lineaProgram, lineaEnd, diags);

        // Buscar comentarios {}
        validarComentariosLlave(linea, numeroLinea, lineaProgram, lineaEnd, diags);

        // Validar posición respecto a semicolons
        validarComentarioDespuesPuntoComa(linea, numeroLinea, diags);
    }

    // Valida los comentarios de tipo // en una línea
    private void validarComentariosSlash(String linea, int numeroLinea, int lineaProgram, int lineaEnd, List<Diagnostico> diags) {
        boolean enCadena = false;

        for (int i = 0; i < linea.length() - 1; i++) {
            char c = linea.charAt(i);

            // Manejar strings para evitar false positives
            if (c == '\'' && !enCadena) {
                enCadena = true;
                continue;
            } else if (c == '\'' && enCadena) {
                if (i + 1 < linea.length() && linea.charAt(i + 1) == '\'') {
                    i++;
                    continue;
                }
                enCadena = false;
                continue;
            }

            if (enCadena) {
                continue; // ignorar todo dentro de strings
            }
            char c1 = linea.charAt(i);
            char c2 = linea.charAt(i + 1);

            if (c1 == '/' && c2 == '/') {
                // Comentario // válido
                validarPosicionComentario(numeroLinea, lineaProgram, lineaEnd, diags);
                break; // resto de la línea es comentario
            } else if (c1 == '/' && c2 != '/') {
                // Verificar si hay / seguido de espacio y luego /
                if (i + 2 < linea.length() && Character.isWhitespace(c2) && linea.charAt(i + 2) == '/') {
                    diags.add(new Diagnostico(E_COMENTARIO_SLASH_CON_ESPACIO, numeroLinea, null,
                            "El comentario con slash debe ser // sin espacios entre las barras"));
                    return;
                }

                // Solo un slash - verificar si parece intento de comentario (al final de línea)
                if (pareceIntentoComentario(linea, i)) {
                    diags.add(new Diagnostico(E_COMENTARIO_SLASH_UNICO, numeroLinea, null,
                            "Para comentarios use // (doble slash sin espacios)"));
                    return;
                }
            }
        }
    }

    // Valida los comentarios de tipo {} en una línea
    private void validarComentariosLlave(String linea, int numeroLinea, int lineaProgram, int lineaEnd, List<Diagnostico> diags) {
        int llaveAbierta = -1;
        boolean enCadena = false;

        for (int i = 0; i < linea.length(); i++) {
            char c = linea.charAt(i);

            // Manejar strings para evitar false positives
            if (c == '\'' && !enCadena) {
                enCadena = true;
                continue;
            } else if (c == '\'' && enCadena) {
                if (i + 1 < linea.length() && linea.charAt(i + 1) == '\'') {
                    i++;
                    continue;
                }
                enCadena = false;
                continue;
            }

            if (enCadena) {
                continue;
            }

            if (c == '{') {
                if (llaveAbierta >= 0) {
                    continue;
                }
                llaveAbierta = i;
                validarPosicionComentario(numeroLinea, lineaProgram, lineaEnd, diags);
            } else if (c == '}') {
                if (llaveAbierta < 0) {
                    continue;
                }
                llaveAbierta = -1;
            }
        }

        if (llaveAbierta >= 0) {
            diags.add(new Diagnostico(E_COMENTARIO_LLAVE_SIN_CERRAR, numeroLinea, null,
                    "Comentario con llaves debe cerrarse en la misma línea: falta }"));
        }
    }

    // Valida que no haya comentarios después de punto y coma
    private void validarComentarioDespuesPuntoComa(String linea, int numeroLinea, List<Diagnostico> diags) {
        // Buscar directamente si hay ; seguido de comentario
        for (int i = 0; i < linea.length(); i++) {
            if (linea.charAt(i) == ';') {
                // Ver qué hay después del semicolon
                if (i + 1 < linea.length()) {
                    String despues = linea.substring(i + 1);

                    // Si contiene // o {, es error
                    if (despues.contains("//") || despues.contains("{")) {
                        diags.add(new Diagnostico(E_COMENTARIO_DESPUES_PUNTO_COMA, numeroLinea, null,
                                "No deben aparecer comentarios después de punto y coma"));
                        return;
                    }
                }
            }
        }
    }

    // Valida que los comentarios no estén antes de program ni después de end
    private void validarPosicionComentario(int numeroLinea, int lineaProgram, int lineaEnd, List<Diagnostico> diags) {
        if (lineaProgram > 0 && numeroLinea < lineaProgram) {
            diags.add(new Diagnostico(E_COMENTARIO_ANTES_PROGRAM, numeroLinea, null,
                    "No deben aparecer comentarios antes de program"));
        }

        if (lineaEnd > 0 && numeroLinea > lineaEnd) {
            diags.add(new Diagnostico(E_COMENTARIO_DESPUES_END, numeroLinea, null,
                    "No deben aparecer comentarios después de end."));
        }
    }

    // Determina si un caracter / parece ser un intento de comentario
    private boolean pareceIntentoComentario(String linea, int posicionSlash) {
        // Solo considerar como intento de comentario si el slash está al final de la línea
        // y es seguido inmediatamente por texto que parece comentario
        if (posicionSlash >= linea.length() - 1) {
            return false;
        }

        String despuesSlash = linea.substring(posicionSlash + 1).trim();

        // Si después del / hay solo espacios o números, probablemente no es comentario
        if (despuesSlash.isEmpty() || despuesSlash.matches("\\d+")) {
            return false;
        }

        // Si hay operadores matemáticos cerca, probablemente no es comentario
        String antesSlash = (posicionSlash > 0) ? linea.substring(Math.max(0, posicionSlash - 3), posicionSlash) : "";
        if (antesSlash.matches(".*\\d\\s*$") && despuesSlash.matches("^\\s*\\d.*")) {
            return false; // probablemente división como 5/2
        }

        // Solo si parece realmente un intento de comentario (letras después del /)
        // y está al final de la línea
        String restoLinea = linea.substring(posicionSlash + 1);
        return restoLinea.trim().length() > 2 && restoLinea.trim().matches(".*[A-Za-z].*");
    }

    // Determina si una línea es completamente un comentario
    public static boolean esLineaComentario(String linea) {
        if (linea == null) {
            return false;
        }

        // Remover numeración si existe
        String contenido = quitarNumeroLinea(linea).trim();
        if (contenido.isEmpty()) {
            return false;
        }

        // Línea que empieza con // es completamente comentario
        if (contenido.startsWith("//")) {
            return true;
        }

        // Línea que empieza con { y termina con } es completamente comentario
        if (contenido.startsWith("{") && contenido.endsWith("}")) {
            return true;
        }

        return false;
    }

    // Métodos auxiliares
    // Quita el número de línea si la línea está numerada
    private static String quitarNumeroLinea(String linea) {
        if (linea == null || linea.length() < 5) {
            return linea;
        }
        if (linea.matches("^\\d{4} .*")) {
            return linea.substring(5);
        }
        return linea;
    }

    // Encuentra la línea donde aparece la palabra program
    private static int encontrarLineaProgram(String[] lineas) {
        for (int i = 0; i < lineas.length; i++) {
            String contenido = quitarNumeroLinea(lineas[i]).toLowerCase().trim();
            if (contenido.startsWith("program ")) {
                return i + 1;
            }
        }
        return -1;
    }

    // Encuentra la última línea donde aparece end.
    private static int encontrarUltimaLineaEnd(String[] lineas) {
        for (int i = lineas.length - 1; i >= 0; i--) {
            String contenido = quitarNumeroLinea(lineas[i]).toLowerCase().trim();
            if (contenido.equals("end.")) {
                return i + 1;
            }
        }
        return -1;
    }
}
