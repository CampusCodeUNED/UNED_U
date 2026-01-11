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
package PascalAnalizador.Proyecto2;

import PascalAnalizador.Proyecto1.Diagnostico;
import PascalAnalizador.Proyecto1.Fuente;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validador de la sintaxis de la estructura if-then-else en Pascal.
 * Verifica que la estructura if cumpla con la sintaxis correcta:
 * if condición then
 *   begin
 *     sentencia;
 *   end;
 * [else
 *   begin
 *     sentencia;
 *   end;]
 */
public final class ValidadorIf {

    // Códigos de error para la validación de la estructura if
    public static final int E_IF_FALTA_CONDICION = 310;
    public static final int E_IF_FALTA_THEN = 311;
    public static final int E_IF_FALTA_BEGIN = 312;
    public static final int E_IF_BEGIN_TABULACION_INCORRECTA = 313;
    public static final int E_IF_SIN_SENTENCIAS = 314;
    public static final int E_IF_FALTA_END = 315;
    public static final int E_IF_END_TABULACION_INCORRECTA = 316;
    public static final int E_IF_END_TIENE_PUNTO_COMA = 317;  // Cambiado: el end del if NO debe tener punto y coma
    public static final int E_IF_ELSE_BEGIN_TABULACION_INCORRECTA = 318;
    public static final int E_IF_ELSE_SIN_SENTENCIAS = 319;
    public static final int E_IF_ELSE_FALTA_END = 320;
    public static final int E_IF_ELSE_END_TABULACION_INCORRECTA = 321;
    public static final int E_IF_ELSE_END_FALTA_PUNTO_COMA = 322;
    public static final int E_IF_SENTENCIA_TABULACION_INCORRECTA = 323;  // Nuevo: para validar tabulación de sentencias
    public static final int E_IF_ELSE_TABULACION_INCORRECTA = 324;  // Nuevo: para validar tabulación del else
    public static final int E_IF_FALTA_PUNTO_COMA = 325;  // Nuevo: para validar punto y coma en sentencias

    // Patrón para detectar la línea de inicio del if
    private static final Pattern PATRON_IF_INICIO = Pattern.compile("^(\\s*)if\\s+(.+?)\\s+then\\s*$", Pattern.CASE_INSENSITIVE);
    
    // Patrón para detectar la línea de else
    private static final Pattern PATRON_ELSE = Pattern.compile("^(\\s*)else\\s*$", Pattern.CASE_INSENSITIVE);
    
    // Patrón para detectar la línea de begin (captura cualquier contenido adicional después de begin)
    private static final Pattern PATRON_BEGIN = Pattern.compile("^(\\s*)begin(\\s*)(.*?)\\s*$", Pattern.CASE_INSENSITIVE);
    
    // Patrón para detectar la línea de end
    private static final Pattern PATRON_END = Pattern.compile("^(\\s*)end(;?)\\s*$", Pattern.CASE_INSENSITIVE);

    /**
     * Resultado de la validación de la estructura if
     */
    public static final class Resultado {
        public final List<Diagnostico> diags;
        public final int lineaInicio;
        public final int lineaFin;

        public Resultado(List<Diagnostico> diags, int lineaInicio, int lineaFin) {
            this.diags = diags;
            this.lineaInicio = lineaInicio;
            this.lineaFin = lineaFin;
        }
    }

    /**
     * Valida la sintaxis de las estructuras if-then-else en el código fuente
     * 
     * @param fuente el código fuente a analizar
     * @return un objeto Resultado con los diagnósticos y las líneas de inicio y fin
     */
    public Resultado validar(Fuente fuente) {
        List<Diagnostico> diags = new ArrayList<>();
        String[] lineas = fuente.getTexto().split("\\r?\\n", -1);
        int ultimaLineaInicio = -1;
        int ultimaLineaFin = -1;

        for (int i = 0; i < lineas.length; i++) {
            String lineaLimpia = lineas[i].trim().toLowerCase();
            if (lineaLimpia.startsWith("if")) {
                // Validar que "if" sea una palabra reservada
                if (!PalabrasReservadasP2.esReservada("if")) {
                    diags.add(new Diagnostico(999, i + 1, null,
                            "'if' debe ser una palabra reservada válida en Pascal"));
                    continue;
                }

                // Buscar línea de inicio del if
                Matcher matcherIf = PATRON_IF_INICIO.matcher(lineas[i]);
                if (!matcherIf.matches()) {
                    diags.add(new Diagnostico(999, i + 1, null,
                            "Sintaxis incorrecta en la declaración del if"));
                    continue;
                }

                int lineaInicio = i + 1;
                ultimaLineaInicio = lineaInicio;
                String indentacionIf = matcherIf.group(1);
                String condicion = matcherIf.group(2);

                // Validar que "then" sea una palabra reservada
                if (!PalabrasReservadasP2.esReservada("then")) {
                    diags.add(new Diagnostico(999, i + 1, null,
                            "'then' debe ser una palabra reservada válida en Pascal"));
                    continue;
                }

                // Validar que haya una condición
                if (condicion == null || condicion.isEmpty() || condicion.trim().isEmpty()) {
                    diags.add(new Diagnostico(E_IF_FALTA_CONDICION, lineaInicio, null,
                            "Falta la condición después del 'if'"));
                } else {
                    // Validar que los paréntesis estén balanceados en la condición
                    int contadorParentesis = 0;
                    for (int j = 0; j < condicion.length(); j++) {
                        if (condicion.charAt(j) == '(') {
                            contadorParentesis++;
                        } else if (condicion.charAt(j) == ')') {
                            contadorParentesis--;
                            if (contadorParentesis < 0) {
                                // Hay un paréntesis de cierre sin su correspondiente apertura
                                diags.add(new Diagnostico(E_IF_FALTA_CONDICION, lineaInicio, null,
                                        "Paréntesis no balanceados en la condición: hay un paréntesis de cierre sin su correspondiente apertura"));
                                break;
                            }
                        }
                    }
                    
                    if (contadorParentesis > 0) {
                        // Falta paréntesis de cierre
                        diags.add(new Diagnostico(E_IF_FALTA_CONDICION, lineaInicio, null,
                                "Paréntesis no balanceados en la condición: falta paréntesis de cierre"));
                    }
                }

                // Validar que "begin" sea una palabra reservada válida
                if (!PalabrasReservadasP2.esReservada("begin")) {
                    diags.add(new Diagnostico(999, lineaInicio, null,
                            "'begin' debe ser una palabra reservada válida en Pascal"));
                }
                
                // Validar que después del if venga un begin
                boolean encontroBegin = false;
                int lineaBegin = -1;
                for (int j = i + 1; j < lineas.length && j <= i + 3; j++) {
                    Matcher beginMatcher = PATRON_BEGIN.matcher(lineas[j]);
                    if (beginMatcher.matches()) {
                        encontroBegin = true;
                        lineaBegin = j + 1;
                        // Validar tabulación del begin (debe tener indentación del if + 4 espacios)
                        String indentacionBegin = obtenerIndentacion(lineas[j]);
                        if (!indentacionBegin.equals(indentacionIf + "    ")) {
                            diags.add(new Diagnostico(E_IF_BEGIN_TABULACION_INCORRECTA, lineaBegin, null,
                                    "La línea 'begin' debe tener una tabulación de 4 espacios respecto al 'if'"));
                        }
                        
                        // Verificar que no haya contenido adicional después de begin
                        String contenidoAdicional = beginMatcher.group(3);
                        if (contenidoAdicional != null && !contenidoAdicional.isEmpty()) {
                            diags.add(new Diagnostico(E_IF_BEGIN_TABULACION_INCORRECTA, lineaBegin, null,
                                    "La línea 'begin' no debe tener contenido adicional"));
                        }
                        break;
                    }
                }

                if (!encontroBegin) {
                    diags.add(new Diagnostico(E_IF_FALTA_BEGIN, lineaInicio, null,
                            "Después de 'then' debe venir 'begin' en la siguiente línea"));
                } else {
                    // Buscar end después del begin
                    boolean encontroEnd = false;
                    boolean encontroSentencia = false;
                    for (int j = lineaBegin; j < lineas.length; j++) {
                        // Verificar si hay al menos una sentencia y su tabulación
                        if (!encontroSentencia && !lineas[j].trim().isEmpty() && 
                            !PATRON_END.matcher(lineas[j]).matches()) {
                            encontroSentencia = true;
                            String lineaTrim = lineas[j].trim();
                            
                            // Validar que la sentencia termine con punto y coma
                            if (!lineaTrim.endsWith(";")) {
                                diags.add(new Diagnostico(E_IF_FALTA_PUNTO_COMA, j + 1, null,
                                        "La sentencia dentro del bloque if debe terminar con punto y coma"));
                            }
                            
                            // Validar tabulación de la sentencia (4 espacios más que el begin)
                            String indentacionSentencia = obtenerIndentacion(lineas[j]);
                            if (!indentacionSentencia.equals(indentacionIf + "        ")) {
                                diags.add(new Diagnostico(E_IF_SENTENCIA_TABULACION_INCORRECTA,
                                        j + 1, null,
                                        "La sentencia debe tener una tabulación de 4 espacios con respecto al begin"));
                            }
                        }

                        // Validar que "end" sea una palabra reservada válida
                        if (!PalabrasReservadasP2.esReservada("end")) {
                            diags.add(new Diagnostico(999, j + 1, null,
                                    "'end' debe ser una palabra reservada válida en Pascal"));
                        }
                        
                        Matcher matcherEnd = PATRON_END.matcher(lineas[j]);
                        if (matcherEnd.matches()) {
                            encontroEnd = true;
                            int lineaEnd = j + 1;
                            ultimaLineaFin = lineaEnd; // Actualizar la línea de fin para el resultado
                            
                            // Validar tabulación del end (debería tener la misma indentación que begin)
                            String indentacionEnd = obtenerIndentacion(lineas[j]);
                            if (!indentacionEnd.equals(indentacionIf + "    ")) {
                                diags.add(new Diagnostico(E_IF_END_TABULACION_INCORRECTA, lineaEnd, null,
                                        "La línea 'end;' debe tener la misma tabulación que 'begin'"));
                            }
                            
                            // Validar que el end NO termine con punto y coma en el if
                            String puntoComa = matcherEnd.group(2);
                            if (puntoComa != null && puntoComa.equals(";")) {
                                diags.add(new Diagnostico(E_IF_END_TIENE_PUNTO_COMA, lineaEnd, null,
                                        "El 'end' del if no debe llevar punto y coma"));
                            }

                            // Buscar posible else después del end
                            boolean encontroElse = false;
                            for (int k = j + 1; k < lineas.length && k <= j + 3; k++) {
                                // Validar que "else" sea una palabra reservada válida
                                if (!PalabrasReservadasP2.esReservada("else")) {
                                    diags.add(new Diagnostico(999, k + 1, null,
                                            "'else' debe ser una palabra reservada válida en Pascal"));
                                }
                                
                                Matcher matcherElse = PATRON_ELSE.matcher(lineas[k]);
                                if (matcherElse.matches()) {
                                    encontroElse = true;
                                    
                                    // Validar que el else tenga la misma tabulación que el if
                                    String indentacionElse = obtenerIndentacion(lineas[k]);
                                    if (!indentacionElse.equals(indentacionIf)) {
                                        diags.add(new Diagnostico(E_IF_ELSE_TABULACION_INCORRECTA,
                                                k + 1, null,
                                                "El 'else' debe tener la misma tabulación que el 'if'"));
                                    }
                                    
                                    // Validar estructura del else
                                    boolean encontroBeginElse = false;
                                    int lineaBeginElse = -1;
                                    
                                    // Buscar begin después del else
                                    for (int l = k + 1; l < lineas.length && l <= k + 3; l++) {
                                        // Validar que "begin" en el else sea una palabra reservada válida
                                        if (!PalabrasReservadasP2.esReservada("begin")) {
                                            diags.add(new Diagnostico(999, l + 1, null,
                                                    "'begin' debe ser una palabra reservada válida en Pascal"));
                                        }
                                        
                                        Matcher beginElseMatcher = PATRON_BEGIN.matcher(lineas[l]);
                                        if (beginElseMatcher.matches()) {
                                            encontroBeginElse = true;
                                            lineaBeginElse = l + 1;
                                            
                                            // Validar tabulación del begin del else
                                            String indentacionBeginElse = obtenerIndentacion(lineas[l]);
                                            if (!indentacionBeginElse.equals(indentacionIf + "    ")) {
                                                diags.add(new Diagnostico(E_IF_ELSE_BEGIN_TABULACION_INCORRECTA, 
                                                        lineaBeginElse, null,
                                                        "La línea 'begin' del else debe tener la misma tabulación que el begin del if"));
                                            }
                                            
                                            // Verificar que no haya contenido adicional después de begin
                                            String contenidoAdicional = beginElseMatcher.group(3);
                                            if (contenidoAdicional != null && !contenidoAdicional.isEmpty()) {
                                                diags.add(new Diagnostico(E_IF_ELSE_BEGIN_TABULACION_INCORRECTA, lineaBeginElse, null,
                                                        "La línea 'begin' no debe tener contenido adicional"));
                                            }
                                            break;
                                        }
                                    }
                                    
                                    if (encontroBeginElse) {
                                        // Buscar end después del begin del else
                            boolean encontroEndElse = false;
                            boolean encontroSentenciaElse = false;                                        for (int l = lineaBeginElse; l < lineas.length; l++) {
                                            // Validar si hay al menos una sentencia en el else y su tabulación
                                            if (!encontroSentenciaElse && !lineas[l].trim().isEmpty() && 
                                                !PATRON_END.matcher(lineas[l]).matches()) {
                                                encontroSentenciaElse = true;
                                                String lineaTrim = lineas[l].trim();
                                                
                                                // Validar que la sentencia termine con punto y coma
                                                if (!lineaTrim.endsWith(";")) {
                                                    diags.add(new Diagnostico(E_IF_FALTA_PUNTO_COMA, l + 1, null,
                                                            "La sentencia dentro del bloque else debe terminar con punto y coma"));
                                                }
                                                
                                                // Validar tabulación de la sentencia (4 espacios más que el begin)
                                                String indentacionSentencia = obtenerIndentacion(lineas[l]);
                                                if (!indentacionSentencia.equals(indentacionIf + "        ")) {
                                                    diags.add(new Diagnostico(E_IF_SENTENCIA_TABULACION_INCORRECTA,
                                                            l + 1, null,
                                                            "La sentencia debe tener una tabulación de 4 espacios con respecto al begin"));
                                                }
                                            }

                                            // Validar que "end" en el else sea una palabra reservada válida
                                            if (!PalabrasReservadasP2.esReservada("end")) {
                                                diags.add(new Diagnostico(999, l + 1, null,
                                                        "'end' debe ser una palabra reservada válida en Pascal"));
                                            }
                                            
                                            Matcher matcherEndElse = PATRON_END.matcher(lineas[l]);
                                            if (matcherEndElse.matches()) {
                                    encontroEndElse = true;
                                    int lineaEndElse = l + 1;
                                    ultimaLineaFin = lineaEndElse;                                                // Validar tabulación del end del else
                                            String indentacionEndElse = obtenerIndentacion(lineas[l]);
                                            if (!indentacionEndElse.equals(indentacionIf + "    ")) {
                                                    diags.add(new Diagnostico(E_IF_ELSE_END_TABULACION_INCORRECTA, 
                                                            lineaEndElse, null,
                                                            "La línea 'end;' del else debe tener la misma tabulación que el begin"));
                                                }
                                                
                                                // Validar que el end del else termine con punto y coma
                                                String puntoComaElse = matcherEndElse.group(2);
                                                if (puntoComaElse == null || !puntoComaElse.equals(";")) {
                                                    diags.add(new Diagnostico(E_IF_ELSE_END_FALTA_PUNTO_COMA, 
                                                            lineaEndElse, null,
                                                            "El 'end' del else debe terminar con punto y coma"));
                                                }
                                                
                                                // No actualizamos el índice principal aquí porque estamos dentro de múltiples bucles anidados
                                                break;
                                            }
                                        }
                                        
                                        if (!encontroSentenciaElse && encontroEndElse) {
                                            diags.add(new Diagnostico(E_IF_ELSE_SIN_SENTENCIAS, lineaBeginElse, null,
                                                    "El bloque else debe contener al menos una línea de código"));
                                        }
                                        
                                        if (!encontroEndElse) {
                                            diags.add(new Diagnostico(E_IF_ELSE_FALTA_END, lineaBeginElse, null,
                                                    "El bloque else debe cerrarse con 'end;'"));
                                        }
                                    }
                                    
                                    break;
                                }
                            }
                            
                            if (!encontroElse) {
                                ultimaLineaFin = lineaEnd;
                            }
                            i = j; // Actualizar el índice principal si no hay else
                            break;
                        }
                    }

                    if (!encontroSentencia && encontroEnd) {
                        diags.add(new Diagnostico(E_IF_SIN_SENTENCIAS, lineaBegin, null,
                                "El bloque if debe contener al menos una línea de código"));
                    }

                    if (!encontroEnd) {
                        diags.add(new Diagnostico(E_IF_FALTA_END, lineaBegin, null,
                                "El bloque 'begin' del if debe cerrarse con 'end;'"));
                    }
                }
            }
        }

        return new Resultado(diags, ultimaLineaInicio, ultimaLineaFin);
    }
    
    /**
     * Obtiene la indentación de una línea (espacios al inicio)
     * @param linea la línea de texto
     * @return la cadena de indentación
     */
    private String obtenerIndentacion(String linea) {
        StringBuilder indentacion = new StringBuilder();
        for (int i = 0; i < linea.length(); i++) {
            if (Character.isWhitespace(linea.charAt(i))) {
                indentacion.append(linea.charAt(i));
            } else {
                break;
            }
        }
        return indentacion.toString();
    }
}
