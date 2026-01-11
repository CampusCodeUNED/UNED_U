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

public final class ValidadorWrite {
// Códigos de error para write/writeln

    public static final int E_WRITE_SIN_PARENTESIS = 700; // write;  (sin paréntesis)
    public static final int E_WRITE_PARENTESIS_VACIOS = 701; // write();
    public static final int E_WRITE_PARENTESIS_SIN_CERRAR = 702; // falta ')'
    public static final int E_WRITE_COMILLA_SIN_CERRAR = 703; // '...  sin cerrar
    public static final int E_WRITE_TEXTO_SIN_COMILLAS = 704; // texto sin comillas
    public static final int E_WRITE_ID_NO_DECLARADO = 705; // identificador no declarado
    public static final int E_WRITE_FALTA_PUNTO_COMA = 706; // falta ';'
    public static final int E_WRITE_MALA_POSICION = 707; // fuera de begin..end

    // Detecta inicio de sentencia write/writeln (ignora espacios al inicio)
    private static final Pattern INICIO_WRITE
            = Pattern.compile("^\\s*(write|writeln)\\b", Pattern.CASE_INSENSITIVE);

    // Extrae identificadores estilo Pascal (sin dígitos)
    private static final Pattern TOKEN_ID
            = Pattern.compile("\\b([A-Za-z][A-Za-z_]*)\\b");

    // Algunas rutinas/procs conocidas que no son variables usuario
    private static final List<String> FUNCIONES_INTEGRADAS = Arrays.asList(
            "write", "writeln", "ord", "readkey", "inc", "dec", "clrscr", "getdate", "mod", "div"
    );

    public static final class Resultado {

        public final List<Diagnostico> diags;
        // Líneas donde se reportó ID no declarado 
        public final List<Integer> lineasConErroresId;

        public Resultado(List<Diagnostico> diags, List<Integer> lineasConErroresId) {
            this.diags = diags;
            this.lineasConErroresId = lineasConErroresId;
        }
    }

    // Verifica las sentencias write/writeln en el código fuente y devuelve un objeto con los diagnósticos y las líneas de cada sentencia
    public Resultado verificar(Fuente fuente, int lineaBegin, int lineaEnd, List<String> idsDeclarados) {
        List<Diagnostico> diags = new ArrayList<>();
        List<Integer> lineasConErroresId = new ArrayList<>();

        String[] lineas = fuente.getTexto().split("\\r?\\n", -1);
        int n = lineas.length;

        // Recorremos todo el archivo y cuando veamos write/writeln, juntamos la sentencia
        for (int i = 0; i < n; i++) {
            String cruda = lineas[i];

            // Ignorar líneas de comentario completas según requisito
            if (ValidadorComentarios.esLineaComentario(cruda)) {
                continue;
            }

            if (!INICIO_WRITE.matcher(cruda).find()) {
                continue;
            }

            int lineaInicioSentencia = i + 1;

            // Posición: debe estar entre begin y end
            if (!(lineaBegin > 0 && lineaEnd > 0 && lineaInicioSentencia > lineaBegin && lineaInicioSentencia < lineaEnd)) {
                diags.add(new Diagnostico(E_WRITE_MALA_POSICION, lineaInicioSentencia, null,
                        "Las sentencias write/writeln deben ubicarse después de 'begin' y antes de 'end.'"));
            }

            // Armar sentencia completa (puede partirse en varias líneas) hasta ';'
            StringBuilder sentencia = new StringBuilder(cruda);
            int j = i;
            while (!recortarSinComentarios(sentencia.toString()).trim().endsWith(";") && j + 1 < n) {
                j++;
                sentencia.append("\n").append(lineas[j]);
            }

            String completa = sentencia.toString();
            String completaSinCom = recortarSinComentarios(completa).trim();

            // ';' final obligatorio
            if (!completaSinCom.endsWith(";")) {
                diags.add(new Diagnostico(E_WRITE_FALTA_PUNTO_COMA, lineaInicioSentencia, null,
                        "La sentencia write/writeln debe finalizar con ';'"));
            }

            // Verificar si tiene paréntesis
            int indiceApertura = indiceDePareApertura(completaSinCom);
            if (indiceApertura < 0) {
                diags.add(new Diagnostico(E_WRITE_SIN_PARENTESIS, lineaInicioSentencia, null,
                        "La sentencia write/writeln debe llevar paréntesis de apertura y cierre"));
                i = j; // avanzar
                continue;
            }
            // Verificar si hay un paréntesis de cierre correspondiente
            int indiceCierre = encontrarPareCierreCorrespondiente(completaSinCom, indiceApertura);
            if (indiceCierre < 0) {
                // Verificar si hay una comilla sin cerrar
                String despuesApertura = completaSinCom.substring(indiceApertura + 1).trim();
                if (despuesApertura.startsWith("'") && !esLiteralCadenaValidaPascal(despuesApertura)) {
                    diags.add(new Diagnostico(E_WRITE_COMILLA_SIN_CERRAR, lineaInicioSentencia, null,
                            "Cadena no válida: si inicia con comillas debe cerrar comillas (use '' para comilla literal)"));
                } else {
                    diags.add(new Diagnostico(E_WRITE_PARENTESIS_SIN_CERRAR, lineaInicioSentencia, null,
                            "Falta paréntesis de cierre ')' en write/writeln"));
                }
                i = j;
                continue;
            }

            // Contenido entre paréntesis
            String interior = completaSinCom.substring(indiceApertura + 1, indiceCierre).trim();
            if (interior.isEmpty()) {
                diags.add(new Diagnostico(E_WRITE_PARENTESIS_VACIOS, lineaInicioSentencia, null,
                        "Los paréntesis de write/writeln no pueden estar vacíos"));
                i = j;
                continue;
            }

            // Análisis del contenido
            if (interior.startsWith("'")) {
                // Dividir por comas para manejar múltiples parámetros
                String[] parametros = dividirParametros(interior);
                for (String parametro : parametros) {
                    parametro = parametro.trim();
                    if (parametro.startsWith("'")) {
                        // Es un string literal - validar que esté bien formado
                        if (!esLiteralCadenaValidaPascal(parametro)) {
                            diags.add(new Diagnostico(E_WRITE_COMILLA_SIN_CERRAR, lineaInicioSentencia, null,
                                    "Cadena no válida: si inicia con comillas debe cerrar comillas (use '' para comilla literal)"));
                        }
                    } else if (!parametro.isEmpty()) {
                        // Es un identificador o expresión
                        validarIdentificadoresEnParametro(parametro, lineaInicioSentencia, idsDeclarados, diags, lineasConErroresId);
                    }
                }
            } else {
                // No es string literal - dividir por comas y procesar cada parámetro
                String[] parametros = dividirParametros(interior);
                for (String parametro : parametros) {
                    parametro = parametro.trim();
                    if (parametro.startsWith("'")) {
                        // Es un string literal
                        if (!esLiteralCadenaValidaPascal(parametro)) {
                            diags.add(new Diagnostico(E_WRITE_COMILLA_SIN_CERRAR, lineaInicioSentencia, null,
                                    "Cadena no válida: si inicia con comillas debe cerrar comillas (use '' para comilla literal)"));
                        }
                    } else if (pareceTextoSinComillas(parametro)) {
                        diags.add(new Diagnostico(E_WRITE_TEXTO_SIN_COMILLAS, lineaInicioSentencia, null,
                                "Texto literal en write/writeln debe ir entre comillas simples"));
                        lineasConErroresId.add(lineaInicioSentencia);
                    } else {
                        // Validar identificadores en este parámetro
                        validarIdentificadoresEnParametro(parametro, lineaInicioSentencia, idsDeclarados, diags, lineasConErroresId);
                    }
                }
            }

            i = j; // continuar después de la sentencia
        }

        return new Resultado(diags, lineasConErroresId);
    }

    // ---- Métodos auxiliares ----
    private static String recortarSinComentarios(String s) {
        String[] lineas = s.split("\\r?\\n", -1);
        StringBuilder salida = new StringBuilder();
        for (int i = 0; i < lineas.length; i++) {
            String x = lineas[i];
            int p = x.indexOf("//");
            if (p >= 0) {
                x = x.substring(0, p);
            }
            p = x.indexOf("{");
            if (p >= 0) {
                x = x.substring(0, p);
            }
            p = x.indexOf("(*");
            if (p >= 0) {
                x = x.substring(0, p);
            }
            salida.append(x);
            if (i + 1 < lineas.length) {
                salida.append('\n');
            }
        }
        return salida.toString();
    }

    // Encuentra el índice de la apertura del paréntesis de write/writeln
    private static int indiceDePareApertura(String s) {
        Matcher m = Pattern.compile("\\b(write|writeln)\\s*\\(", Pattern.CASE_INSENSITIVE).matcher(s);
        if (m.find()) {
            return m.end() - 1; // posición del '('
        }
        return -1;
    }

    // Encuentra el índice del cierre del paréntesis de write/writeln
    private static int encontrarPareCierreCorrespondiente(String s, int indiceApertura) {
        int profundidad = 0;
        boolean enCadena = false;
        for (int i = indiceApertura; i < s.length(); i++) {
            char c = s.charAt(i);
            if (enCadena) {
                if (c == '\'') {
                    if (i + 1 < s.length() && s.charAt(i + 1) == '\'') {
                        i++;
                        continue;
                    } // '' escape
                    enCadena = false;
                }
                continue;
            }
            if (c == '\'') {
                enCadena = true;
                continue;
            }
            if (c == '(') {
                profundidad++;
            } else if (c == ')') {
                profundidad--;
                if (profundidad == 0) {
                    return i;
                }
            }
        }
        return -1;
    }

    // Verifica si una cadena es un literal de cadena válido en Pascal
    private static boolean esLiteralCadenaValidaPascal(String s) {
        if (!s.startsWith("'")) {
            return false;
        }

        // Si es solo una comilla, es inválido
        if (s.length() == 1) {
            return false;
        }

        int i = 1;
        while (i < s.length()) {
            char c = s.charAt(i);
            if (c == '\'') {
                if (i + 1 < s.length() && s.charAt(i + 1) == '\'') {
                    i += 2; // saltar '' escape
                    continue;
                }
                // debe ser el final
                return i == s.length() - 1;
            }
            i++;
        }
        return false; // no cerró
    }

    // Divide el contenido entre paréntesis en parámetros individuales
    private static String[] dividirParametros(String interior) {
        List<String> parametros = new ArrayList<>();
        StringBuilder actual = new StringBuilder();
        boolean enCadena = false;
        int profundidadParen = 0;

        for (int i = 0; i < interior.length(); i++) {
            char c = interior.charAt(i);

            if (enCadena) {
                actual.append(c);
                if (c == '\'') {
                    if (i + 1 < interior.length() && interior.charAt(i + 1) == '\'') {
                        i++;
                        actual.append('\'');
                    } else {
                        enCadena = false;
                    }
                }
            } else {
                if (c == '\'') {
                    enCadena = true;
                    actual.append(c);
                } else if (c == '(') {
                    profundidadParen++;
                    actual.append(c);
                } else if (c == ')') {
                    profundidadParen--;
                    actual.append(c);
                } else if (c == ',' && profundidadParen == 0) {
                    // Solo dividir por comas fuera de paréntesis y strings
                    parametros.add(actual.toString());
                    actual.setLength(0);
                } else {
                    actual.append(c);
                }
            }
        }

        if (actual.length() > 0) {
            parametros.add(actual.toString());
        }

        return parametros.toArray(new String[0]);
    }

    // Valida los identificadores en un parámetro
    private static void validarIdentificadoresEnParametro(String parametro, int lineaInicioSentencia,
            List<String> idsDeclarados,
            List<Diagnostico> diags,
            List<Integer> lineasConErroresId) {
        Matcher m = TOKEN_ID.matcher(parametro);
        while (m.find()) {
            String id = m.group(1);
            String minuscula = id.toLowerCase(Locale.ROOT);
            if (FUNCIONES_INTEGRADAS.contains(minuscula)) {
                continue;
            }
            if (ReglasIdentificadores.esPalabraReservada(id)) {
                continue;
            }
            if (!idsDeclarados.contains(id)) {
                diags.add(new Diagnostico(E_WRITE_ID_NO_DECLARADO, lineaInicioSentencia, null,
                        "Variable '" + id + "' usada pero no declarada en la sección var"));
                lineasConErroresId.add(lineaInicioSentencia);
            }
        }
    }

    // Verifica si un contenido parece ser texto sin comillas
    private static boolean pareceTextoSinComillas(String interior) {
        // Si contiene solo letras y espacios 
        // pero no es un identificador válido único
        interior = interior.trim();

        // Si contiene espacios Y letras, probablemente es texto plano
        if (interior.contains(" ") && interior.matches(".*[A-Za-z].*")) {
            return true;
        }

        // Si es una secuencia de palabras separadas por espacios
        String[] palabras = interior.split("\\s+");
        if (palabras.length > 1) {
            boolean todasPalabrasParecedTexto = true;
            for (String palabra : palabras) {
                // Si es un número o un operador, no es texto plano
                if (palabra.matches("\\d+") || palabra.matches("[+\\-*/:=<>]+")) {
                    todasPalabrasParecedTexto = false;
                    break;
                }
            }
            if (todasPalabrasParecedTexto) {
                return true;
            }
        }

        return false;
    }
}
