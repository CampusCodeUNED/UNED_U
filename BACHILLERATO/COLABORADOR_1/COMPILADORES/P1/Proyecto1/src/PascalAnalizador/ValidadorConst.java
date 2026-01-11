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
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ValidadorConst {

    // const simple (misma línea): const <id> = <integer|string> ;
    private static final Pattern DECL_CONST_SIMPLE
            = Pattern.compile(
                    "^\\s*const\\s+([A-Za-z][A-Za-z_]*)\\s*=\\s*(?:\\d+|'([^']|'')*')\\s*;\\s*$",
                    Pattern.CASE_INSENSITIVE
            );

    // const de una sola línea cualquiera: const <id> ... = ... ;
    private static final Pattern DECL_CONST_SINGLE_ANY
            = Pattern.compile("^\\s*const\\s+([A-Za-z][A-Za-z_]*)\\b.*=.+;\\s*$", Pattern.CASE_INSENSITIVE);

    // const multi-línea: "const <id> ... =" (opcionalmente con "(")
    private static final Pattern DECL_CONST_START_ANY
            = Pattern.compile("^\\s*const\\s+([A-Za-z][A-Za-z_]*)\\b.*=\\s*(\\(\\s*)?$", Pattern.CASE_INSENSITIVE);

    // Códigos de error para constantes (punto 5: 500-599)
    public static final int E_CONST_BAD_FORMAT = 500;
    public static final int E_CONST_MISSING_EQUALS = 501;
    public static final int E_CONST_MISSING_SEMI = 502;
    public static final int E_CONST_AFTER_BEGIN = 503;
    public static final int E_CONST_WRONG_ORDER = 504;

    public static final class Resultado {

        public final ParClaveValor lineaDeclarada;
        public final List<Diagnostico> diags;

        public Resultado(ParClaveValor lineaDeclarada, List<Diagnostico> diags) {
            this.lineaDeclarada = lineaDeclarada;
            this.diags = diags;
        }
    }

    // Analiza la sección de constantes en el código fuente y devuelve diagnósticos
    public Resultado analizar(Fuente fuente) {
        List<Diagnostico> diags = new ArrayList<>();
        ParClaveValor declared = new ParClaveValor();

        String text = fuente.getTexto();
        String[] lines = text.split("\\r?\\n", -1);
        int n = lines.length;

        // Encontrar líneas de referencia para validar orden
        int programLine = encontrarLinea(lines, "program");
        int usesLine = encontrarLinea(lines, "uses");
        int varLine = encontrarLinea(lines, "var");
        int beginLine = encontrarLinea(lines, "begin");

        // Estado para const multi-línea
        String pendingConstId = null;
        int pendingConstLine = -1;

        for (int i = 0; i < n; i++) {
            String line = lines[i];
            String low = line.toLowerCase(Locale.ROOT);
            int lineNo = i + 1;

            // Validar posición: const no puede aparecer después de begin
            if (low.trim().startsWith("const") && beginLine > 0 && lineNo > beginLine) {
                diags.add(new Diagnostico(E_CONST_AFTER_BEGIN, lineNo, null,
                        "Las constantes no pueden declararse después de begin"));
                continue;
            }

            // Validar orden: const debe ir después de program y uses, antes de var
            if (low.trim().startsWith("const")) {
                if (programLine > 0 && lineNo < programLine) {
                    diags.add(new Diagnostico(E_CONST_WRONG_ORDER, lineNo, null,
                            "Las constantes deben declararse después de program"));
                }
                if (usesLine > 0 && lineNo < usesLine) {
                    diags.add(new Diagnostico(E_CONST_WRONG_ORDER, lineNo, null,
                            "Las constantes deben declararse después de uses"));
                }
                if (varLine > 0 && lineNo > varLine) {
                    diags.add(new Diagnostico(E_CONST_WRONG_ORDER, lineNo, null,
                            "Las constantes deben declararse antes de var"));
                }
            }

            // Cerrar const multilínea si estábamos dentro
            if (pendingConstId != null) {
                if (low.contains(";")) {
                    declared.poner(pendingConstId, pendingConstLine);
                    pendingConstId = null;
                    pendingConstLine = -1;
                }
                continue;
            }

            // Procesar solo líneas que contengan 'const'
            if (!low.contains("const")) {
                continue;
            }

            // const simple: const <id> = <entero|string> ;
            Matcher mSimple = DECL_CONST_SIMPLE.matcher(line);
            if (mSimple.matches()) {
                String cid = mSimple.group(1);
                validarIdentificadorConst(cid, lineNo, diags);
                declared.poner(cid, lineNo);
                continue;
            }

            // const de una sola línea "cualquiera": válida si termina en ';' (arrays, etc.)
            Matcher mSingleAny = DECL_CONST_SINGLE_ANY.matcher(line);
            if (mSingleAny.matches()) {
                String cid = mSingleAny.group(1);
                validarIdentificadorConst(cid, lineNo, diags);
                declared.poner(cid, lineNo);
                continue;
            }

            // Inicio de const multi-línea (arrays complejos)
            Matcher mStart = DECL_CONST_START_ANY.matcher(line);
            if (mStart.matches() && !low.contains(";")) {
                String cid = mStart.group(1);
                boolean nameOk = validarIdentificadorConst(cid, lineNo, diags);
                if (nameOk) {
                    pendingConstId = cid;
                    pendingConstLine = lineNo;
                }
                continue;
            }

            // Si la línea empieza con 'const' pero no coincide con ningún patrón válido
            if (low.trim().startsWith("const")) {
                if (!low.contains(";") && !mStart.matches()) {
                    diags.add(new Diagnostico(E_CONST_BAD_FORMAT, lineNo, null,
                            "Declaración de constante inválida. Se espera: const <identificador> = <valor>;"));
                } else if (low.contains(";") && !mSimple.matches() && !mSingleAny.matches()) {
                    diags.add(new Diagnostico(E_CONST_BAD_FORMAT, lineNo, null,
                            "Declaración de constante inválida. Se espera: const <identificador> = <valor>;"));
                }

                if (!line.trim().endsWith(";") && !mStart.matches()) {
                    diags.add(new Diagnostico(E_CONST_MISSING_SEMI, lineNo, null,
                            "La declaración de constante debe finalizar con punto y coma"));
                }

                // Validar que tenga signo igual
                if (!line.contains("=")) {
                    diags.add(new Diagnostico(E_CONST_MISSING_EQUALS, lineNo, null,
                            "La declaración de constante debe incluir el signo igual (=)"));
                }
            }
        }

        return new Resultado(declared, diags);
    }

    // Encuentra la línea donde aparece una palabra clave
    private static int encontrarLinea(String[] lines, String keyword) {
        for (int i = 0; i < lines.length; i++) {
            String low = lines[i].toLowerCase().trim();
            if (low.startsWith(keyword + " ") || low.equals(keyword)) {
                return i + 1;
            }
        }
        return -1;
    }

    // Valida que el identificador de constante cumpla las reglas y no sea una palabra reservada
    private boolean validarIdentificadorConst(String id, int lineNum, List<Diagnostico> diags) {
        if (!ReglasIdentificadores.esValidoIdentificadorFormulario(id)) {
            diags.add(new Diagnostico(AnalizadorVarSeccion.E_ID_FORMATO, lineNum, null,
                    "Identificador inválido '" + id + "'. Debe iniciar con letra y solo contener letras o guiones bajos"));
            return false;
        } else if (ReglasIdentificadores.esPalabraReservada(id)) {
            diags.add(new Diagnostico(AnalizadorVarSeccion.E_ID_RESERVADO, lineNum, null,
                    "Uso de palabra reservada como identificador: '" + id + "'"));
            return false;
        }
        return true;
    }
}
