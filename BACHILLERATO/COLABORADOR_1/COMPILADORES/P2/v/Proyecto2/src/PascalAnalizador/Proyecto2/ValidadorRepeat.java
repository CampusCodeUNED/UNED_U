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
 * Validador de la sintaxis de la estructura repeat-until en Pascal.
 * Verifica que la estructura repeat cumpla con la sintaxis correcta:
 * repeat                   (palabra reservada, no lleva punto y coma ni nada en la misma línea)
 *     begin                (palabra reservada, tabulación de 4 espacios respecto a repeat)
 *         sentencia;       (al menos una sentencia, tabulación de 4 espacios respecto a begin, termina con punto y coma)
 *     end;                 (palabra reservada, tabulación de 4 espacios respecto a repeat, termina con punto y coma)
 * until condición;         (palabra reservada, alineada con repeat, debe tener condición y terminar con punto y coma)
 */
public final class ValidadorRepeat {

    // Códigos de error para la validación de la estructura repeat
    public static final int E_REPEAT_CONTENIDO_EXTRA = 330;  // Cuando hay contenido extra en la línea del repeat
    public static final int E_REPEAT_FALTA_BEGIN = 331;      // Cuando falta el begin después del repeat
    public static final int E_REPEAT_BEGIN_TABULACION = 332; // Tabulación incorrecta del begin (debe ser 4 espacios)
    public static final int E_REPEAT_SIN_SENTENCIAS = 333;   // No hay sentencias dentro del bloque
    public static final int E_REPEAT_SENTENCIA_TABULACION = 334; // Tabulación incorrecta de las sentencias
    public static final int E_REPEAT_SENTENCIA_PUNTO_COMA = 335; // Falta punto y coma en la sentencia
    public static final int E_REPEAT_FALTA_END = 336;        // Falta el end
    public static final int E_REPEAT_END_TABULACION = 337;   // Tabulación incorrecta del end
    public static final int E_REPEAT_END_PUNTO_COMA = 338;   // Falta punto y coma en el end
    public static final int E_REPEAT_FALTA_UNTIL = 339;      // Falta el until
    public static final int E_REPEAT_UNTIL_TABULACION = 340; // Until no alineado con repeat
    public static final int E_REPEAT_UNTIL_CONDICION = 341;  // Falta condición en el until
    public static final int E_REPEAT_UNTIL_PUNTO_COMA = 342; // Falta punto y coma en el until

    // Patrones para detectar las diferentes partes de la estructura
    private static final Pattern PATRON_REPEAT = 
            Pattern.compile("^(\\s*)repeat\\s*(.+?)?\\s*(;?)\\s*$", Pattern.CASE_INSENSITIVE);
    
    private static final Pattern PATRON_BEGIN = 
            Pattern.compile("^(\\s*)begin\\s*$", Pattern.CASE_INSENSITIVE);
    
    private static final Pattern PATRON_END = 
            Pattern.compile("^(\\s*)end(;?)\\s*$", Pattern.CASE_INSENSITIVE); // Captura 'end' y punto y coma opcional
    
    // Patrón para detectar: espacios + until + espacio + cualquier condición + punto y coma opcional + espacios
    private static final Pattern PATRON_UNTIL = 
            Pattern.compile("^(\\s*)until\\s+([^;]+)(;?)\\s*$", Pattern.CASE_INSENSITIVE);

    /**
     * Resultado de la validación de la estructura repeat
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
     * Valida la sintaxis de las estructuras repeat-until en el código fuente
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
            // Buscar línea de inicio del repeat
            Matcher matcherRepeat = PATRON_REPEAT.matcher(lineas[i]);
            if (matcherRepeat.matches()) {
                // Validar que "repeat" sea una palabra reservada válida
                if (!PalabrasReservadasP2.esReservada("repeat")) {
                    diags.add(new Diagnostico(999, i + 1, null,
                            "'repeat' debe ser una palabra reservada válida en Pascal"));
                    continue;
                }
                
                int lineaInicio = i + 1;
                ultimaLineaInicio = lineaInicio;
                String indentacionRepeat = matcherRepeat.group(1);
                String contenidoExtra = matcherRepeat.group(2);
                String puntoComa = matcherRepeat.group(3);

                // Validar que repeat no tenga contenido extra ni punto y coma
                if (contenidoExtra != null && (!contenidoExtra.trim().isEmpty() || (puntoComa != null && !puntoComa.isEmpty()))) {
                    diags.add(new Diagnostico(E_REPEAT_CONTENIDO_EXTRA, lineaInicio, null,
                            "La palabra 'repeat' no debe llevar contenido adicional ni punto y coma en la misma línea"));
                }

                // Validar que después del repeat venga un begin
                boolean encontroBegin = false;
                int lineaBegin = -1;
                // Validar que "begin" sea una palabra reservada válida
                if (!PalabrasReservadasP2.esReservada("begin")) {
                    diags.add(new Diagnostico(999, lineaInicio, null,
                            "'begin' debe ser una palabra reservada válida en Pascal"));
                }
                
                for (int j = i + 1; j < lineas.length && j <= i + 3; j++) {
                    if (PATRON_BEGIN.matcher(lineas[j]).matches()) {
                        encontroBegin = true;
                        lineaBegin = j + 1;
                        // Validar tabulación del begin (debe tener indentación del repeat + 4 espacios)
                        String indentacionBegin = obtenerIndentacion(lineas[j]);
                        if (!indentacionBegin.equals(indentacionRepeat + "    ")) {
                                diags.add(new Diagnostico(E_REPEAT_BEGIN_TABULACION, lineaBegin, null,
                                        "La línea 'begin' debe tener una tabulación de 4 espacios respecto a 'repeat'"));
                        }
                        break;
                    }
                }

                if (!encontroBegin) {
                    diags.add(new Diagnostico(E_REPEAT_FALTA_BEGIN, lineaInicio, null,
                            "Después de 'repeat' debe venir 'begin' en la siguiente línea"));
                } else {
                    // Buscar end después del begin
                    boolean encontroEnd = false;
                    boolean encontroSentencia = false;
                    
                    for (int j = lineaBegin; j < lineas.length; j++) {
                        // Verificar si hay al menos una sentencia y validar su formato
                        String lineaTrimmed = lineas[j].trim();
                        if (!lineaTrimmed.isEmpty() && !PATRON_END.matcher(lineas[j]).matches()) {
                            encontroSentencia = true;
                            
                            // Validar tabulación de la sentencia (4 espacios desde begin, que es 8 desde repeat)
                            String indentacionSentencia = obtenerIndentacion(lineas[j]);
                            if (!indentacionSentencia.equals(indentacionRepeat + "        ")) {
                                diags.add(new Diagnostico(E_REPEAT_SENTENCIA_TABULACION, j + 1, null,
                                        "La sentencia debe tener una tabulación de 4 espacios respecto al begin"));
                            }
                            
                            // Validar punto y coma al final de cada sentencia (requisito obligatorio)
                            if (!lineaTrimmed.endsWith(";")) {
                                diags.add(new Diagnostico(E_REPEAT_SENTENCIA_PUNTO_COMA, j + 1, null,
                                        "La sentencia debe terminar con punto y coma"));
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
                            
                            // Validar tabulación del end (4 espacios desde repeat, igual que begin)
                            String indentacionEnd = obtenerIndentacion(lineas[j]);
                            if (!indentacionEnd.equals(indentacionRepeat + "    ")) {
                                diags.add(new Diagnostico(E_REPEAT_END_TABULACION, lineaEnd, null,
                                        "La línea 'end' debe tener una tabulación de 4 espacios respecto a 'repeat'"));
                            }
                            
                            // Validar que el end termine con punto y coma
                            String puntoComaEnd = matcherEnd.group(2);
                            if (puntoComaEnd == null || !puntoComaEnd.equals(";")) {
                                diags.add(new Diagnostico(E_REPEAT_END_PUNTO_COMA, lineaEnd, null,
                                        "El 'end' debe terminar con punto y coma"));
                            }
                            
                            // Validar que después del end venga un until
                            boolean encontroUntil = false;
                            for (int k = j + 1; k < lineas.length && k <= j + 3; k++) {
                                // Validar que "until" sea una palabra reservada válida
                                if (!PalabrasReservadasP2.esReservada("until")) {
                                    diags.add(new Diagnostico(999, k + 1, null,
                                            "'until' debe ser una palabra reservada válida en Pascal"));
                                }
                                
                                Matcher matcherUntil = PATRON_UNTIL.matcher(lineas[k]);
                                if (matcherUntil.matches()) {
                                    encontroUntil = true;
                                    int lineaUntil = k + 1;
                                    ultimaLineaFin = lineaUntil;
                                    String indentacionUntil = matcherUntil.group(1);
                                    String condicion = matcherUntil.group(2);
                                    String puntoComaUntil = matcherUntil.group(3);
                                    
                                    // Validar que la indentación del until sea igual a la del repeat
                                    if (!indentacionUntil.equals(indentacionRepeat)) {
                                        diags.add(new Diagnostico(E_REPEAT_UNTIL_TABULACION, lineaUntil, null,
                                                "La línea 'until' debe estar alineada con 'repeat'"));
                                    }
                                    
                                    // Solo validar que exista alguna condición (sin importar su contenido)
                                    if (condicion == null || condicion.trim().isEmpty()) {
                                        diags.add(new Diagnostico(E_REPEAT_UNTIL_CONDICION, lineaUntil, null,
                                                "Después de 'until' debe venir una condición"));
                                    }
                                    
                                    // Validar que el until termine con punto y coma
                                    if (puntoComaUntil == null || !puntoComaUntil.equals(";")) {
                                        diags.add(new Diagnostico(E_REPEAT_UNTIL_PUNTO_COMA, lineaUntil, null,
                                                "La línea 'until condición' debe terminar con punto y coma"));
                                    }
                                    
                                    // Actualizar el índice principal para continuar después del repeat-until completo
                                    i = k;
                                    break;
                                }
                            }
                            
                            if (!encontroUntil) {
                                // Usar j+1 como número de línea, ya que lineaEnd es local al bloque anterior
                                diags.add(new Diagnostico(E_REPEAT_FALTA_UNTIL, j + 1, null,
                                        "Después del 'end;' debe venir 'until condición;' en la siguiente línea"));
                            }
                            
                            break;
                        }
                    }

                    if (!encontroSentencia && encontroEnd) {
                        diags.add(new Diagnostico(E_REPEAT_SIN_SENTENCIAS, lineaBegin, null,
                                "El bloque repeat debe contener al menos una línea de código"));
                    }

                    if (!encontroEnd) {
                        diags.add(new Diagnostico(E_REPEAT_FALTA_END, lineaBegin, null,
                                "El bloque 'begin' del repeat debe cerrarse con 'end;'"));
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
