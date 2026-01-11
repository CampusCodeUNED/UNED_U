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
import reglas.ReglasIdentificadores;

public final class AnalizadorVarSeccion {

    private static final Set<String> TIPOS_PERMITIDOS
            = new HashSet<>(Arrays.asList("integer", "string", "word"));

    // var <id> : <tipo...> ; (todo en una línea)
    // Patrón para detectar declaraciones de variables en la misma línea que 'var'
    private static final Pattern DECL_VAR_MISMA_LINEA
            = Pattern.compile("^\\s*var\\s+([A-Za-z][A-Za-z_]*)\\s*:\\s*(.+?)\\s*;\\s*$");

    // Solo identificador : tipo ; (sin 'var' al inicio)
    // Patrón para detectar declaraciones de variables sin 'var'
    private static final Pattern DECL_VAR_SIN_KEYWORD
            = Pattern.compile("^\\s*([A-Za-z][A-Za-z_]*)\\s*:\\s*(.+?)\\s*;\\s*$");

    // Patrón para detectar asignaciones (no declaraciones)
    private static final Pattern PATRON_ASIGNACION
            = Pattern.compile("^\\s*([A-Za-z][A-Za-z_0-9]*)\\s*:=");

    // Códigos de error específicos para punto 3 (300-399)
    public static final int E_ID_FORMATO = 300;
    public static final int E_ID_RESERVADO = 301;
    public static final int E_FALTA_PUNTO_COMA = 310;
    public static final int E_TIPO_INVALIDO = 320;
    public static final int E_ESPACIADO_DOS_PUNTOS = 330;
    public static final int E_VAR_POSICION_INCORRECTA = 340;
    public static final int E_USADO_NO_DECLARADO = 350;

    public static final class Result {

        public final Map<String, Integer> lineaDeclarada;
        public final List<Diagnostico> diags;
        
        Result(Map<String, Integer> lineaDeclarada, List<Diagnostico> diags) {
            this.lineaDeclarada = lineaDeclarada;
            this.diags = diags;
        }
    }

    // Método principal para analizar la sección var
    public Result analyze(Fuente fuente) {
        List<Diagnostico> diags = new ArrayList<>();
        Map<String, Integer> declaradas = new HashMap<>();

        String texto = fuente.getText();
        String[] lineas = texto.split("\\r?\\n", -1);
        int n = lineas.length;

        // Encontrar líneas de referencia para validar posición
        int lineaProgram = encontrarLinea(lineas, "program");
        int lineaUses = encontrarLinea(lineas, "uses");
        int lineaConst = encontrarLinea(lineas, "const");
        int lineaBegin = encontrarLinea(lineas, "begin");

        // Encontrar dónde empieza la sección var
        int lineaVarInicio = -1;
        for (int i = 0; i < n; i++) {
            String recortada = lineas[i].trim().toLowerCase(Locale.ROOT);
            if (recortada.equals("var") || recortada.startsWith("var ")) {
                lineaVarInicio = i;
                break;
            }
        }
        // Si no hay sección var, retornar
        if (lineaVarInicio < 0) {
            return new Result(declaradas, diags); // No hay sección var
        }

        // Validar posición de var
        int numeroLineaVar = lineaVarInicio + 1;
        if (lineaProgram > 0 && numeroLineaVar < lineaProgram) {
            diags.add(new Diagnostico(E_VAR_POSICION_INCORRECTA, numeroLineaVar, null,
                    "Las variables deben declararse después de program"));
        }
        if (lineaUses > 0 && numeroLineaVar < lineaUses) {
            diags.add(new Diagnostico(E_VAR_POSICION_INCORRECTA, numeroLineaVar, null,
                    "Las variables deben declararse después de uses"));
        }
        if (lineaConst > 0 && numeroLineaVar < lineaConst) {
            diags.add(new Diagnostico(E_VAR_POSICION_INCORRECTA, numeroLineaVar, null,
                    "Las variables deben declararse después de const"));
        }
        if (lineaBegin > 0 && numeroLineaVar >= lineaBegin) {
            diags.add(new Diagnostico(E_VAR_POSICION_INCORRECTA, numeroLineaVar, null,
                    "Las variables deben declararse antes de begin"));
        }

        // Procesar declaraciones var
        // Caso 1: var en la misma línea que la declaración: var x : integer;
        String lineaVar = lineas[lineaVarInicio];
        Matcher mMismaLinea = DECL_VAR_MISMA_LINEA.matcher(lineaVar);

        if (mMismaLinea.matches()) {
            // Declaración completa en la misma línea del var
            procesarDeclaracion(lineaVar, numeroLineaVar, declaradas, diags);
        } else {
            // Caso 2: var solo, las declaraciones vienen en las siguientes líneas
            String recortada = lineaVar.trim().toLowerCase(Locale.ROOT);
            if (recortada.equals("var")) {
                // Procesar líneas siguientes hasta encontrar begin, const u otra palabra clave
                for (int i = lineaVarInicio + 1; i < n; i++) {
                    String cruda = lineas[i];
                    String limpia = quitarComentariosEnLinea(cruda).trim();

                    if (limpia.isEmpty()) {
                        continue; // Líneas vacías
                    }
                    String minuscula = limpia.toLowerCase(Locale.ROOT);

                    // Si encontramos otra palabra clave, terminar
                    if (minuscula.equals("begin") || minuscula.startsWith("begin ")
                            || minuscula.equals("const") || minuscula.startsWith("const ")
                            || minuscula.equals("type") || minuscula.startsWith("type ")) {
                        break;
                    }

                    // Ignorar asignaciones
                    if (PATRON_ASIGNACION.matcher(cruda).find()) {
                        continue;
                    }

                    int numeroLinea = i + 1;
                    procesarDeclaracion(cruda, numeroLinea, declaradas, diags);
                }
            }
        }

        return new Result(declaradas, diags);
    }

    // Método para encontrar usos de identificadores no declarados
    private void procesarDeclaracion(String linea, int numeroLinea,
            Map<String, Integer> declaradas,
            List<Diagnostico> diags) {
        String limpia = quitarComentariosEnLinea(linea);
        boolean tienePuntoYComa = limpia.trim().endsWith(";");

        // Validar espaciado alrededor de ':' solo en declaraciones (no en asignaciones :=)
        int dosPuntos = limpia.indexOf(':');
        if (dosPuntos >= 0 && !limpia.contains(":=")) {
            boolean okAntes = dosPuntos > 0 && Character.isWhitespace(limpia.charAt(dosPuntos - 1));
            boolean okDespues = dosPuntos + 1 < limpia.length()
                    && Character.isWhitespace(limpia.charAt(dosPuntos + 1));
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
            return;
        }

        // Intentar match con patrón completo (con o sin 'var')
        Matcher m1 = DECL_VAR_MISMA_LINEA.matcher(limpia);
        Matcher m2 = DECL_VAR_SIN_KEYWORD.matcher(limpia);

        Matcher m = m1.matches() ? m1 : (m2.matches() ? m2 : null);

        if (m == null) {
            String candidatoId = extraerCandidatoId(limpia);
            if (candidatoId != null) {
                validarIdentificador(candidatoId, numeroLinea, diags);
            }
            return;
        }

        String id = m.group(1);
        String tipo = m.group(2).trim().toLowerCase(Locale.ROOT);

        // Validar identificador
        validarIdentificador(id, numeroLinea, diags);

        // Registrar variable declarada
        declaradas.put(id, numeroLinea);

        // Validar tipo de dato
        if (!TIPOS_PERMITIDOS.contains(tipo)) {
            diags.add(new Diagnostico(E_TIPO_INVALIDO, numeroLinea, null,
                    "Tipo no permitido '" + tipo + "'. Tipos válidos: integer, string, word"));
        }

        // Manejo de múltiples variables en una línea: x, y : integer;
        if (dosPuntos > 0) {
            String antesDosPuntos = limpia.substring(0, dosPuntos);
            String despuesVar = antesDosPuntos.replaceFirst("(?i)^\\s*var\\s+", "").trim();

            if (despuesVar.contains(",")) {
                String[] ids = despuesVar.split(",");
                for (String idCrudo : ids) {
                    String idVariable = idCrudo.trim();
                    if (!idVariable.isEmpty() && !idVariable.equals(id)) {
                        validarIdentificador(idVariable, numeroLinea, diags);
                        declaradas.put(idVariable, numeroLinea);
                    }
                }
            }
        }
    }

    private static int encontrarLinea(String[] lineas, String palabraClave) {
        for (int i = 0; i < lineas.length; i++) {
            String minuscula = lineas[i].toLowerCase().trim();
            if (minuscula.startsWith(palabraClave + " ") || minuscula.equals(palabraClave)) {
                return i + 1;
            }
        }
        return -1;
    }

    private void validarIdentificador(String id, int numeroLinea, List<Diagnostico> diags) {
        if (!ReglasIdentificadores.esValidoIdentificadorFormulario(id)) {
            diags.add(new Diagnostico(E_ID_FORMATO, numeroLinea, null,
                    "Identificador inválido '" + id + "'. Debe iniciar con letra y solo contener letras o guiones bajos"));
        } else if (ReglasIdentificadores.esPalabraReservada(id)) {
            diags.add(new Diagnostico(E_ID_RESERVADO, numeroLinea, null,
                    "Uso de palabra reservada como identificador: '" + id + "'"));
        }
    }

    // Método para encontrar usos de identificadores no declarados
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

    // Método para encontrar usos de identificadores no declarados
    private static String extraerCandidatoId(String limpia) {
        String minuscula = limpia.toLowerCase(Locale.ROOT).trim();

        // Si empieza con 'var', extraer después de var
        if (minuscula.startsWith("var ")) {
            limpia = limpia.substring(minuscula.indexOf("var") + 3).trim();
        }

        // Extraer hasta el primer espacio o ':'
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < limpia.length(); i++) {
            char c = limpia.charAt(i);
            if (Character.isWhitespace(c) || c == ':') {
                break;
            }
            sb.append(c);
        }

        String candidato = sb.toString().trim();// Extrae el posible identificador
        return candidato.isEmpty() ? null : candidato;
    }
}
