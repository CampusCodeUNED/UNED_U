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

public final class ValidadorBeginEnd {

    public static final int E_BEGIN_FALTANTE = 600;
    public static final int E_BEGIN_ORDEN = 601;
    public static final int E_BEGIN_BASURA_LINEA = 602;
    public static final int E_END_FALTANTE = 610;
    public static final int E_END_FORMATO_MALO = 611;
    public static final int E_END_BASURA_LINEA = 612;
    public static final int E_CONTENIDO_DESPUES_END = 613;

    // Verifica la correcta estructura y posición de las palabras begin y end en el código fuente
    public List<Diagnostico> verificar(Fuente fuente) {
        List<Diagnostico> diags = new ArrayList<>();
        String[] lineas = fuente.getTexto().split("\\r?\\n", -1);

        int lineaVar = primeraLineaCoincidente(lineas, "^\\s*var\\b.*", true);
        int lineaBegin = primeraLineaBegin(lineas); // solo 'begin' (con/sin espacios a los lados)
        int ultimaNoVacia = ultimaLineaNoVacia(lineas);

        // --- Validar BEGIN ---
        if (lineaBegin <= 0) {
            diags.add(new Diagnostico(E_BEGIN_FALTANTE, 1, null, "No se encontró la palabra 'begin'"));
        } else {
            String cruda = lineas[lineaBegin - 1];
            if (!esSoloPalabraSinComentarios(cruda, "begin")) {
                diags.add(new Diagnostico(E_BEGIN_BASURA_LINEA, lineaBegin, null,
                        "La línea con 'begin' no debe tener otros tokens ni comentarios"));
            }
            if (lineaVar <= 0 || lineaBegin <= lineaVar) {
                diags.add(new Diagnostico(E_BEGIN_ORDEN, lineaBegin, null,
                        "La palabra 'begin' debe aparecer después de la sección 'var'"));
            }
        }

        // --- Validar END. ---
        int numeroLineaEnd = -1;
        String contenidoLineaEnd = null;

        for (int i = lineas.length - 1; i >= 0; i--) {
            String linea = lineas[i];
            String contenido = quitarNumeroLinea(linea).trim().toLowerCase();

            if (contenido.equals("end.")) {
                numeroLineaEnd = i + 1;
                contenidoLineaEnd = linea;
                break;
            } else if (contenido.startsWith("end.") || contenido.startsWith("end ") || contenido.equals("end")) {
                numeroLineaEnd = i + 1;
                contenidoLineaEnd = linea;
                break;
            }
        }

        if (numeroLineaEnd == -1) {
            // Buscar si hay alguna línea que tenga la palabra "end" sin el punto
            boolean encontroEndSinPunto = false;
            int lineaEndSinPunto = -1;
            for (int i = lineas.length - 1; i >= 0; i--) {
                String linea = quitarNumeroLinea(lineas[i]).trim().toLowerCase();
                if (linea.matches("^\\s*end\\b.*")) {
                    encontroEndSinPunto = true;
                    lineaEndSinPunto = i + 1;
                    break;
                }
            }
            
            if (encontroEndSinPunto) {
                diags.add(new Diagnostico(E_END_FORMATO_MALO, lineaEndSinPunto, null,
                        "La sentencia final debe ser exactamente 'end.' (falta el punto)"));
            } else {
                diags.add(new Diagnostico(E_END_FALTANTE, ultimaNoVacia > 0 ? ultimaNoVacia : 1, null,
                        "No se encontró la sentencia final 'end.'"));
            }
            return diags;
        }

        String endRecortado = quitarNumeroLinea(contenidoLineaEnd).trim();
        String endMinuscula = endRecortado.toLowerCase(Locale.ROOT);

        if (!endMinuscula.equals("end.")) {
            if (endMinuscula.startsWith("end")) {
                diags.add(new Diagnostico(E_END_FORMATO_MALO, numeroLineaEnd, null,
                        "El fin del programa debe ser exactamente 'end.' (con punto)"));
            } else {
                diags.add(new Diagnostico(E_END_FALTANTE, numeroLineaEnd, null,
                        "No se encontró la sentencia final 'end.'"));
            }
        } else {
            if (tieneComentarioEnLinea(contenidoLineaEnd)) {
                diags.add(new Diagnostico(E_END_BASURA_LINEA, numeroLineaEnd, null,
                        "La línea 'end.' no debe tener comentarios ni texto adicional"));
            }

            boolean tieneContenidoDespues = false;
            for (int i = numeroLineaEnd; i < lineas.length; i++) {
                String lineaDespues = lineas[i];
                String contenidoDespues = quitarNumeroLinea(lineaDespues).trim();

                if (!contenidoDespues.isEmpty()) {
                    tieneContenidoDespues = true;
                    diags.add(new Diagnostico(E_CONTENIDO_DESPUES_END, i + 1, null,
                            "No debe haber líneas ni comentarios después de 'end.'"));
                    break;
                }
            }

            if (!tieneContenidoDespues && numeroLineaEnd < lineas.length) {
                for (int i = numeroLineaEnd; i < lineas.length; i++) {
                    if (!lineas[i].trim().isEmpty() || !quitarNumeroLinea(lineas[i]).trim().isEmpty()) {
                        diags.add(new Diagnostico(E_CONTENIDO_DESPUES_END, i + 1, null,
                                "No debe haber líneas vacías después de 'end.'"));
                        break;
                    }
                }
            }
        }

        return diags;
    }

    // --- Métodos auxiliares ---
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

    // Encuentra la primera línea que coincide con una expresión regular
    private static int primeraLineaCoincidente(String[] lineas, String regex, boolean ignorarMayusculas) {
        for (int i = 0; i < lineas.length; i++) {
            String s = quitarNumeroLinea(lineas[i]);
            if (ignorarMayusculas) {
                if (s.toLowerCase(Locale.ROOT).matches(regex.toLowerCase(Locale.ROOT))) {
                    return i + 1;
                }
            } else {
                if (s.matches(regex)) {
                    return i + 1;
                }
            }
        }
        return -1;
    }

    // Encuentra la primera línea que contiene exactamente una palabra
    private static int primeraLineaBegin(String[] lineas) {
        for (int i = 0; i < lineas.length; i++) {
            String s = quitarNumeroLinea(lineas[i]).trim().toLowerCase();
            // Verificar si la línea contiene solo la palabra begin sin más texto
            if (s.matches("^\\s*begin\\s*(\\{.*\\}|\\(\\*.*\\*\\)|//.*)?\\s*$")) {
                return i + 1;
            }
            // Si hay texto adicional después de begin que no es un comentario, es un error
            if (s.startsWith("begin") && !s.equals("begin")) {
                return -(i + 1); // Retorna negativo para indicar error
            }
        }
        return -1;
    }

    // Encuentra la última línea no vacía del código fuente
    private static int ultimaLineaNoVacia(String[] lineas) {
        for (int i = lineas.length - 1; i >= 0; i--) {
            String contenido = quitarNumeroLinea(lineas[i]).trim();
            if (!contenido.isEmpty()) {
                return i + 1;
            }
        }
        return -1;
    }

    // Verifica si una línea contiene solamente una palabra específica sin comentarios
    private static boolean esSoloPalabraSinComentarios(String cruda, String palabra) {
        // Debe ser exactamente el 'palabra' (ignorando espacios a los lados) y sin comentarios en la misma línea.
        if (tieneComentarioEnLinea(cruda)) {
            return false;
        }
        String contenido = quitarNumeroLinea(cruda).trim();
        return contenido.equalsIgnoreCase(palabra);
    }

    // Determina si una línea contiene algún tipo de comentario
    private static boolean tieneComentarioEnLinea(String cruda) {
        String s = quitarNumeroLinea(cruda);
        int p1 = s.indexOf("//");
        int p2 = s.indexOf("{");
        int p3 = s.indexOf("(*");
        // Si hay cualquiera de estos marcadores en la línea, se considera comentario a la par.
        return (p1 >= 0) || (p2 >= 0) || (p3 >= 0);
    }
}
