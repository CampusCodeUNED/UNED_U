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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ValidadorUso {

    private static final Pattern PALABRA = Pattern.compile("[A-Za-z_]+"); // sin dígitos

    public List<Diagnostico> encontrarUsosNoDeclarados(Fuente fuente,
            int lineaBegin,
            int lineaEnd,
            List<String> idsDeclarados,
            int codigoError) {
        List<Diagnostico> salida = new ArrayList<>();
        String[] lineas = fuente.getTexto().split("\\r?\\n", -1);

        if (lineaBegin <= 0) {
            return salida;
        }
        int ultima = (lineaEnd > 0 ? Math.min(lineaEnd, lineas.length) : lineas.length);

        // Estado para comentarios de bloque que cruzan líneas
        boolean enComentarioLlave = false;    // { ... }
        boolean enComentarioEstrella = false;    // (* ... *)

        for (int i = lineaBegin; i <= ultima; i++) {
            String cruda = lineas[i - 1];
            String limpia = quitarCadenasYComentarios(cruda, new int[]{enComentarioLlave ? 1 : 0, enComentarioEstrella ? 1 : 0});
            enComentarioLlave = limpia.startsWith("#ESTADO_LLAVE_ACTIVO#");
            enComentarioEstrella = limpia.startsWith("#ESTADO_ESTRELLA_ACTIVO#");
            // quitar marcadores si los hubiera
            limpia = limpia.replace("#ESTADO_LLAVE_ACTIVO#", "").replace("#ESTADO_ESTRELLA_ACTIVO#", "");

            Matcher m = PALABRA.matcher(limpia);
            while (m.find()) {
                String w = m.group();

                // Palabras que no cuentan como identificadores de usuario
                if (PalabrasReservadas.esReservada(w) || PalabrasReservadas.esIntegrada(w)) {
                    continue;
                }

                if (ReglasIdentificadores.esValidoIdentificadorFormulario(w) && !idsDeclarados.contains(w)) {
                    salida.add(new Diagnostico(codigoError, i, m.start() + 1,
                            "Variable '" + w + "' usada pero no declarada en la sección var"));
                }
            }
        }
        return salida;
    }

    // Quita cadenas y comentarios de una línea
    private static String quitarCadenasYComentarios(String linea, int[] estadoComentario) {
        boolean llaveActiva = estadoComentario[0] == 1;
        boolean estrellaActiva = estadoComentario[1] == 1;

        StringBuilder salida = new StringBuilder();
        int i = 0;
        while (i < linea.length()) {
            if (llaveActiva) {
                int cierre = linea.indexOf('}', i);
                if (cierre < 0) {
                    // toda la línea sigue en comentario
                    return "#ESTADO_LLAVE_ACTIVO#";
                } else {
                    llaveActiva = false;
                    i = cierre + 1;
                    continue;
                }
            }
            if (estrellaActiva) {
                int cierre = linea.indexOf("*)", i);
                if (cierre < 0) {
                    return "#ESTADO_ESTRELLA_ACTIVO#";
                } else {
                    estrellaActiva = false;
                    i = cierre + 2;
                    continue;
                }
            }

            char c = linea.charAt(i);

            // Comentario de línea //
            if (c == '/' && i + 1 < linea.length() && linea.charAt(i + 1) == '/') {
                break; // resto de línea es comentario
            }
            // Comentario { ... }
            if (c == '{') {
                int cierre = linea.indexOf('}', i + 1);
                if (cierre < 0) {
                    llaveActiva = true;
                    return "#ESTADO_LLAVE_ACTIVO#" + salida.toString();
                } else {
                    i = cierre + 1;
                    continue;
                }
            }
            // Comentario (* ... *)
            if (c == '(' && i + 1 < linea.length() && linea.charAt(i + 1) == '*') {
                int cierre = linea.indexOf("*)", i + 2);
                if (cierre < 0) {
                    estrellaActiva = true;
                    return "#ESTADO_ESTRELLA_ACTIVO#" + salida.toString();
                } else {
                    i = cierre + 2;
                    continue;
                }
            }
            // Cadena '...'/char, con '' como escape
            if (c == '\'') {
                salida.append(' '); // reemplazar por espacio para no juntar palabras
                i++;
                while (i < linea.length()) {
                    char d = linea.charAt(i);
                    if (d == '\'') {
                        if (i + 1 < linea.length() && linea.charAt(i + 1) == '\'') {
                            i += 2; // '' -> comilla escapada dentro de cadena
                            continue;
                        } else {
                            i++; // fin de cadena
                            break;
                        }
                    }
                    i++;
                }
                continue;
            }

            salida.append(c);//agregar el caracter a la salida
            i++;
        }
        // Si hay un comentario de bloque activo, devolver el estado correspondiente
        if (llaveActiva) {
            return "#ESTADO_LLAVE_ACTIVO#" + salida.toString();
        }
        if (estrellaActiva) {
            return "#ESTADO_ESTRELLA_ACTIVO#" + salida.toString();
        }
        return salida.toString();
    }
}
