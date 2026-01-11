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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import modelo.Diagnostico;
import modelo.Fuente;

public final class ValidadorBeginEnd {

    public static final int E_BEGIN_FALTANTE = 600;
    public static final int E_BEGIN_ORDEN = 601;
    public static final int E_BEGIN_BASURA_LINEA = 602;
    public static final int E_END_FALTANTE = 610;
    public static final int E_END_FORMATO_MALO = 611;
    public static final int E_END_BASURA_LINEA = 612;
    public static final int E_CONTENIDO_DESPUES_END = 613;

    public List<Diagnostico> check(Fuente fuente) {
        List<Diagnostico> diags = new ArrayList<>();
        String[] lineas = fuente.getText().split("\\r?\\n", -1);

        int n = lineas.length;
        int lineaVar = primeraLineaCoincidente(lineas, "^\\s*var\\b.*", true);
        int lineaBegin = encontrarLineaBegin(lineas);
        int ultimaNoVacia = ultimaLineaNoVacia(lineas);

        // --- Validar BEGIN ---
        // Buscar línea con BEGIN
        if (lineaBegin <= 0) {
            diags.add(new Diagnostico(E_BEGIN_FALTANTE, 1, null, "No se encontró la palabra 'begin'"));
        } else {
            String cruda = lineas[lineaBegin - 1];

            // Validar si tiene basura (comentarios u otro contenido)
            if (!esSoloPalabraSinComentarios(cruda, "begin")) {
                diags.add(new Diagnostico(E_BEGIN_BASURA_LINEA, lineaBegin, null,
                        "La línea con 'begin' no debe tener otros tokens ni comentarios"));
            }

            // Validar orden
            if (lineaVar <= 0 || lineaBegin <= lineaVar) {
                diags.add(new Diagnostico(E_BEGIN_ORDEN, lineaBegin, null,
                        "La palabra 'begin' debe aparecer después de la sección 'var'"));
            }
        }

        // --- Validar END. ---
        // Buscar línea con END.
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
        // Validar si se encontró END.
        if (numeroLineaEnd == -1) {
            diags.add(new Diagnostico(E_END_FALTANTE, ultimaNoVacia > 0 ? ultimaNoVacia : 1, null,
                    "No se encontró la sentencia final 'end.'"));
            return diags;
        }
        // Validar contenido de la línea END.
        String endRecortado = quitarNumeroLinea(contenidoLineaEnd).trim();
        String endMinuscula = endRecortado.toLowerCase(Locale.ROOT);

        // Validar formato de end
        if (!endMinuscula.equals("end.")) {
            if (endMinuscula.startsWith("end")) {
                diags.add(new Diagnostico(E_END_FORMATO_MALO, numeroLineaEnd, null,
                        "El fin del programa debe ser exactamente 'end.' (con punto)"));
            } else {
                diags.add(new Diagnostico(E_END_FALTANTE, numeroLineaEnd, null,
                        "No se encontró la sentencia final 'end.'"));
            }
        }

        // Validar comentarios en la línea de end (siempre, tenga o no punto)
        if (tieneComentarioEnLinea(contenidoLineaEnd)) {
            diags.add(new Diagnostico(E_END_BASURA_LINEA, numeroLineaEnd, null,
                    "La línea 'end.' no debe tener comentarios ni texto adicional"));
        }

        // SIEMPRE verificar contenido después de end (tenga o no punto)
        for (int i = numeroLineaEnd; i < lineas.length; i++) {
            String lineaDespues = lineas[i];
            String contenidoDespues = quitarNumeroLinea(lineaDespues).trim();

            if (!contenidoDespues.isEmpty()) {
                diags.add(new Diagnostico(E_CONTENIDO_DESPUES_END, i + 1, null,
                        "No debe haber líneas ni comentarios después de 'end.'"));
                break; // Solo reportar la primera línea con contenido
            }
        }

        return diags;
    }

    // Encuentra la línea que contiene la palabra 'begin'
    private static int encontrarLineaBegin(String[] lineas) {
        for (int i = 0; i < lineas.length; i++) {
            String contenido = quitarNumeroLinea(lineas[i]);
            String sinComentarios = quitarComentariosSimple(contenido).trim().toLowerCase();

            if (sinComentarios.equals("begin")
                    || sinComentarios.matches("^begin\\s+.*")
                    || contenido.trim().toLowerCase().startsWith("begin ")
                    || contenido.trim().toLowerCase().equals("begin")) {
                return i + 1;
            }
        }
        return -1;
    }
    
    // Quita comentarios simples de una línea (//, { }, (* *))
    private static String quitarComentariosSimple(String linea) {
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

    // Quita el número de línea al inicio de una línea si existe (formato "NNNN texto")
    private static String quitarNumeroLinea(String linea) {
        if (linea == null || linea.length() < 5) {
            return linea;
        }
        if (linea.matches("^\\d{4} .*")) {
            return linea.substring(5);
        }
        return linea;
    }

    // Obtiene la primera línea que coincide con un patrón regex, opcionalmente ignorando mayúsculas
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
    
    // Obtiene la última línea no vacía
    private static int ultimaLineaNoVacia(String[] lineas) {
        for (int i = lineas.length - 1; i >= 0; i--) {
            String contenido = quitarNumeroLinea(lineas[i]).trim();
            if (!contenido.isEmpty()) {
                return i + 1;
            }
        }
        return -1;
    }

    // Verifica si una línea contiene solo una palabra específica sin comentarios
    private static boolean esSoloPalabraSinComentarios(String cruda, String palabra) {
        if (tieneComentarioEnLinea(cruda)) {
            return false;
        }
        String contenido = quitarNumeroLinea(cruda).trim();
        return contenido.equalsIgnoreCase(palabra);
    }

    // Verifica si una línea contiene comentarios en línea
    private static boolean tieneComentarioEnLinea(String cruda) {
        String s = quitarNumeroLinea(cruda);
        int p1 = s.indexOf("//");
        int p2 = s.indexOf("{");
        int p3 = s.indexOf("(*");
        return (p1 >= 0) || (p2 >= 0) || (p3 >= 0);
    }
}
