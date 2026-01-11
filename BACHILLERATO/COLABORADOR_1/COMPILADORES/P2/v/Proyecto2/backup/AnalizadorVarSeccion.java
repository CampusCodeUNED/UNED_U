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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class AnalizadorVarSeccion {

    // Tipos de variables permitidos
    private static final List<String> TIPOS_PERMITIDOS
            = Arrays.asList("integer", "string", "word");

    // var <id> : <tipo...> ;
    private static final Pattern DECL_VAR_CUALQUIER_TIPO
            = Pattern.compile("^\\s*var\\s+([A-Za-z][A-Za-z_]*)\\s*:\\s*(.+?)\\s*;\\s*$");

    // Patrón para detectar asignaciones (no declaraciones)
    private static final Pattern PATRON_ASIGNACION
            = Pattern.compile("^\\s*([A-Za-z][A-Za-z_0-9]*)\\s*:=");

    // Códigos de error específicos para punto 3 (300-399)
    public static final int E_ID_FORMATO = 300;           // Identificador con formato inválido
    public static final int E_ID_RESERVADO = 301;       // Palabra reservada usada como identificador
    public static final int E_FALTA_PUNTO_COMA = 310; // Falta punto y coma en declaración
    public static final int E_TIPO_INVALIDO = 320;      // Tipo de variable no permitido
    public static final int E_ESPACIADO_DOS_PUNTOS = 330;     // Espaciado incorrecto alrededor de ':'
    public static final int E_VAR_POSICION_INCORRECTA = 340; // Variable declarada en lugar incorrecto
    public static final int E_USADO_NO_DECLARADO = 350;     // Variable usada pero no declarada
    // Resultado que contiene las variables declaradas y los diagnósticos

    public static final class Resultado {

        public final ParClaveValor lineaDeclarada;
        public final List<Diagnostico> diags;

        Resultado(ParClaveValor lineaDeclarada, List<Diagnostico> diags) {
            this.lineaDeclarada = lineaDeclarada;
            this.diags = diags;
        }
    }

    // Analiza la sección de variables en el código fuente y devuelve un objeto con las variables declaradas y diagnósticos
    public Resultado analizar(Fuente fuente) {
        List<Diagnostico> diags = new ArrayList<>();
        ParClaveValor declaradas = new ParClaveValor();

        String texto = fuente.getTexto();
        String[] lineas = texto.split("\\r?\\n", -1);
        int n = lineas.length;

        // Encontrar líneas de referencia para validar posición
        int lineaProgram = encontrarLinea(lineas, "program");
        int lineaUses = encontrarLinea(lineas, "uses");
        int lineaConst = encontrarLinea(lineas, "const");
        int lineaBegin = encontrarLinea(lineas, "begin");

        // Procesar declaraciones var
        for (int i = 0; i < n; i++) {
            String cruda = lineas[i];
            String recortadaMinuscula = cruda.trim().toLowerCase(Locale.ROOT);

            // Ignorar asignaciones (:=)
            if (PATRON_ASIGNACION.matcher(cruda).find()) {
                continue;
            }

            // Solo procesar líneas que empiecen con 'var'
            if (!recortadaMinuscula.startsWith("var")) {
                continue;
            }

            int numeroLinea = i + 1;

            // Validar posición de var (punto 3 de la rúbrica)
            if (lineaProgram > 0 && numeroLinea < lineaProgram) {
                diags.add(new Diagnostico(E_VAR_POSICION_INCORRECTA, numeroLinea, null,
                        "Las variables deben declararse después de program"));
            }
            if (lineaUses > 0 && numeroLinea < lineaUses) {
                diags.add(new Diagnostico(E_VAR_POSICION_INCORRECTA, numeroLinea, null,
                        "Las variables deben declararse después de uses"));
            }
            if (lineaConst > 0 && numeroLinea < lineaConst) {
                diags.add(new Diagnostico(E_VAR_POSICION_INCORRECTA, numeroLinea, null,
                        "Las variables deben declararse después de const"));
            }
            if (lineaBegin > 0 && numeroLinea >= lineaBegin) {
                diags.add(new Diagnostico(E_VAR_POSICION_INCORRECTA, numeroLinea, null,
                        "Las variables deben declararse antes de begin"));
            }

            String limpia = quitarComentariosEnLinea(cruda);
            boolean tienePuntoYComa = limpia.trim().endsWith(";");

            // Validar espaciado alrededor de ':' solo en declaraciones var
            int dosPuntos = limpia.indexOf(':');
            if (dosPuntos >= 0 && !limpia.contains(":=")) {
                boolean okAntes = dosPuntos > 0 && Character.isWhitespace(limpia.charAt(dosPuntos - 1));
                boolean okDespues = dosPuntos + 1 < limpia.length() && Character.isWhitespace(limpia.charAt(dosPuntos + 1));
                if (!okAntes || !okDespues) {
                    diags.add(new Diagnostico(E_ESPACIADO_DOS_PUNTOS, numeroLinea, null,
                            "Debe haber un espacio antes y después de ':' en la declaración de variable"));
                }
            }

            // Validar punto y coma obligatorio
            if (!tienePuntoYComa) {
                diags.add(new Diagnostico(E_FALTA_PUNTO_COMA, numeroLinea, null,
                        "La declaración de variable debe finalizar con ';'"));
                String candidatoId = extraerCandidatoId(limpia);
                if (candidatoId != null) {
                    validarIdentificador(candidatoId, numeroLinea, diags);
                }
                continue;
            }

            // Validar formato completo: var <id> : <tipo> ;
            Matcher m = DECL_VAR_CUALQUIER_TIPO.matcher(limpia);
            if (!m.matches()) {
                String candidatoId = extraerCandidatoId(limpia);
                if (candidatoId != null) {
                    validarIdentificador(candidatoId, numeroLinea, diags);
                }
                continue;
            }

            String id = m.group(1);
            String tipo = m.group(2).trim().toLowerCase(Locale.ROOT);

            // Validar identificador
            validarIdentificador(id, numeroLinea, diags);

            // Registrar variable declarada 
            declaradas.poner(id, numeroLinea);

            // Validar tipo de dato
            if (!TIPOS_PERMITIDOS.contains(tipo)) {
                diags.add(new Diagnostico(E_TIPO_INVALIDO, numeroLinea, null,
                        "Tipo no permitido '" + tipo + "'. Tipos válidos: integer, string, word"));
            }

            // Manejo de múltiples variables en una línea: var x, y : integer;
            String antesDosPuntos = limpia.substring(0, dosPuntos);
            String despuesVar = antesDosPuntos.replaceFirst("(?i)^\\s*var\\s+", "");
            if (despuesVar.contains(",")) {
                String[] ids = despuesVar.split(",");
                for (String idCrudo : ids) {
                    String idVariable = idCrudo.trim();
                    if (!idVariable.equals(id)) {
                        validarIdentificador(idVariable, numeroLinea, diags);
                        declaradas.poner(idVariable, numeroLinea);
                    }
                }
            }
        }

        return new Resultado(declaradas, diags);
    }

    // Encuentra el número de línea donde aparece una palabra clave
    private static int encontrarLinea(String[] lineas, String palabraClave) {
        for (int i = 0; i < lineas.length; i++) {
            String minuscula = lineas[i].toLowerCase().trim();
            if (minuscula.startsWith(palabraClave + " ") || minuscula.equals(palabraClave)) {
                return i + 1;
            }
        }
        return -1;
    }

    // Valida que el identificador cumpla las reglas y no sea una palabra reservada
    private void validarIdentificador(String id, int numeroLinea, List<Diagnostico> diags) {
        if (!ReglasIdentificadores.esValidoIdentificadorFormulario(id)) {
            diags.add(new Diagnostico(E_ID_FORMATO, numeroLinea, null,
                    "Identificador inválido '" + id + "'. Debe iniciar con letra y solo contener letras o guiones bajos"));
        } else if (ReglasIdentificadores.esPalabraReservada(id)) {
            diags.add(new Diagnostico(E_ID_RESERVADO, numeroLinea, null,
                    "Uso de palabra reservada como identificador: '" + id + "'"));
        }
    }

    // Elimina los comentarios de una línea de código
    private static String quitarComentariosEnLinea(String linea) {
        String s = linea;
        int p;

        p = s.indexOf("//");
        if (p >= 0) {
            s = s.substring(0, p);
        }

        p = s.indexOf("{");
        if (p >= 0) {
            s = s.substring(0, p);
        }

        p = s.indexOf("(*");
        if (p >= 0) {
            s = s.substring(0, p);
        }

        return s;
    }

    // Extrae un posible identificador de una línea que contiene 'var'
    private static String extraerCandidatoId(String limpia) {
        String minuscula = limpia.toLowerCase(Locale.ROOT);
        int posicion = minuscula.indexOf("var");
        if (posicion < 0) {
            return null;
        }
        posicion += 3;
        while (posicion < limpia.length() && Character.isWhitespace(limpia.charAt(posicion))) {
            posicion++;
        }
        if (posicion >= limpia.length()) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        while (posicion < limpia.length()) {
            char c = limpia.charAt(posicion);
            if (Character.isWhitespace(c) || c == ':') {
                break;
            }
            sb.append(c);
            posicion++;
        }
        String candidato = sb.toString().trim();
        return candidato.isEmpty() ? null : candidato;
    }
}
