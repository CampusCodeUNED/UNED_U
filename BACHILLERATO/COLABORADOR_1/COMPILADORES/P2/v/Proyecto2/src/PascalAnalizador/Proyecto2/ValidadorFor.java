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
import PascalAnalizador.Proyecto1.ParClaveValor;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Validador de la estructura for..to..do .. begin .. end; en Pascal.
 * Verifica que la estructura for cumpla con la sintaxis correcta:
 * for variable := valorInicial to valorFinal do
 *   begin
 *     sentencia;
 *   end;
 */
public final class ValidadorFor {

    public static final int E_FOR_SINTAXIS_INCORRECTA = 900;
    public static final int E_FOR_FALTA_TO = 901;
    public static final int E_FOR_VARIABLE_NO_DECLARADA = 902;
    public static final int E_FOR_VALOR_INICIAL_FALTANTE = 903;
    public static final int E_FOR_VALOR_FINAL_FALTANTE = 904;
    public static final int E_FOR_FALTA_BEGIN = 905;
    public static final int E_FOR_FALTA_END = 906;
    public static final int E_FOR_SIN_SENTENCIAS = 907;
    public static final int E_FOR_TABULACION_INCORRECTA = 908;
    public static final int E_FOR_FALTA_PUNTO_COMA = 909;

    private static final Pattern PATRON_BEGIN = Pattern.compile("^(\\s*)begin\\s*$", Pattern.CASE_INSENSITIVE);
    private static final Pattern PATRON_END = Pattern.compile("^(\\s*)end(;?)\\s*$", Pattern.CASE_INSENSITIVE);

    /**
     * Resultado de la validación de la estructura for
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
     * Valida la sintaxis de las estructuras for-to-do en el código fuente.
     * 
     * @param fuente el código fuente a analizar
     * @param variablesDeclaradas mapa de variables declaradas en el programa
     * @return un objeto Resultado con los diagnósticos y las líneas de inicio y fin
     */
    public Resultado validar(Fuente fuente, ParClaveValor variablesDeclaradas) {
        List<Diagnostico> diags = new ArrayList<>();
        String[] lineas = fuente.getTexto().split("\\r?\\n", -1);
        int ultimaLineaInicio = -1;
        int ultimaLineaFin = -1;

        for (int i = 0; i < lineas.length; i++) {
            String lineaTrimLower = lineas[i].trim().toLowerCase();
            if (lineaTrimLower.startsWith("for ")) {
                // Validar que "for" sea una palabra reservada válida
                if (!PalabrasReservadasP2.esReservada("for")) {
                    diags.add(new Diagnostico(E_FOR_SINTAXIS_INCORRECTA, i + 1, null,
                            "'for' debe ser una palabra reservada válida en Pascal"));
                    continue;
                }
                
                int lineaInicio = i + 1;
                ultimaLineaInicio = lineaInicio;

                // Preparar variables para parseo
                String indentacion = obtenerIndentacion(lineas[i]);
                String variable = null;
                String valorInicial = null;
                String valorFinal = null;

                // Parsear la cabecera asumiendo que está en una sola línea
                String lineaTrim = lineas[i].trim();
                try {
                    // Validar formato básico: "for ... do"
                    if (!lineaTrim.toLowerCase().startsWith("for ") || !lineaTrim.toLowerCase().endsWith(" do")) {
                        diags.add(new Diagnostico(E_FOR_SINTAXIS_INCORRECTA, lineaInicio, null,
                                "Sintaxis incorrecta en la declaración del for"));
                        continue;
                    }

                    // Extraer el contenido entre for y do
                    String inner = lineaTrim.substring(3, lineaTrim.length() - 3).trim();
                    // Buscar el operador de asignación ':='
                    int idxAssign = inner.indexOf(":=");
                    if (idxAssign == -1) {
                        diags.add(new Diagnostico(E_FOR_SINTAXIS_INCORRECTA, lineaInicio, null,
                                "Sintaxis incorrecta en la declaración del for: falta ':='"));
                        continue;
                    }

                    // Separar la variable (lado izquierdo) y el resto (lado derecho)
                    variable = inner.substring(0, idxAssign).trim();
                    String resto = inner.substring(idxAssign + 2).trim();

                    // Validar que "to" sea una palabra reservada válida
                    if (!PalabrasReservadasP2.esReservada("to")) {
                        diags.add(new Diagnostico(E_FOR_SINTAXIS_INCORRECTA, lineaInicio, null,
                                "'to' debe ser una palabra reservada válida en Pascal"));
                        continue;
                    }
                    
                    // Separar por la palabra reservada 'to' (ignorando mayúsculas/minúsculas)
                    String[] partesTo = resto.split("(?i)\\bto\\b");
                    if (partesTo.length < 2) {
                        diags.add(new Diagnostico(E_FOR_FALTA_TO, lineaInicio, null,
                                "Falta la palabra 'to' en la declaración del for"));
                        continue;
                    }

                    // Extraer valor inicial y valor final
                    valorInicial = partesTo[0].trim();
                    valorFinal = partesTo[1].trim();
                    // Si valorFinal contiene palabras extras (por ejemplo '5 do'), eliminar 'do' final si existe
                    if (valorFinal.toLowerCase().endsWith(" do")) {
                        valorFinal = valorFinal.substring(0, valorFinal.length() - 3).trim();
                    }
                } catch (Exception ex) {
                    diags.add(new Diagnostico(E_FOR_SINTAXIS_INCORRECTA, lineaInicio, null,
                            "Sintaxis incorrecta en la declaración del for"));
                    continue;
                }

                // --- Validaciones en orden lógico ---
                // Validar que la variable exista y esté declarada
                if (variable == null || variable.isEmpty()) {
                    diags.add(new Diagnostico(E_FOR_SINTAXIS_INCORRECTA, lineaInicio, null,
                            "Variable del for vacía o inválida"));
                } else if (!variablesDeclaradas.contieneClave(variable)) {
                    diags.add(new Diagnostico(E_FOR_VARIABLE_NO_DECLARADA, lineaInicio, null,
                            "La variable '" + variable + "' no ha sido declarada en el bucle for"));
                }

                // Validar espaciado y formato de la línea
                String lineaOriginal = lineas[i].trim();
                // Validar espacio después de for
                if (!lineaOriginal.matches("^for\\s+.*")) {
                    diags.add(new Diagnostico(E_FOR_SINTAXIS_INCORRECTA, lineaInicio, null,
                            "Debe haber un espacio después de 'for'"));
                }
                // Validar que ":=" sea una expresión de control válida
                if (!PalabrasReservadasP2.esExpresionControl(":=")) {
                    diags.add(new Diagnostico(E_FOR_SINTAXIS_INCORRECTA, lineaInicio, null,
                            "':=' debe ser un operador válido en Pascal"));
                }
                
                // Validar espacio antes y después de :=
                if (!lineaOriginal.contains(" := ")) {
                    diags.add(new Diagnostico(E_FOR_SINTAXIS_INCORRECTA, lineaInicio, null,
                            "Debe haber un espacio antes y después de ':='"));
                }
                // Validar que "do" sea una palabra reservada válida
                if (!PalabrasReservadasP2.esReservada("do")) {
                    diags.add(new Diagnostico(E_FOR_SINTAXIS_INCORRECTA, lineaInicio, null,
                            "'do' debe ser una palabra reservada válida en Pascal"));
                }
                
                // Validar que termine exactamente con "do"
                if (!lineaOriginal.endsWith(" do")) {
                    diags.add(new Diagnostico(E_FOR_SINTAXIS_INCORRECTA, lineaInicio, null,
                            "Debe haber un espacio antes de 'do' y nada después"));
                }

                // Validar valor inicial: debe existir y ser un número (según la especificación)
                if (valorInicial == null || valorInicial.isEmpty()) {
                    diags.add(new Diagnostico(E_FOR_VALOR_INICIAL_FALTANTE, lineaInicio, null,
                            "El valor inicial está vacío o inválido"));
                } else if (!valorInicial.matches("\\d+")) {
                    // No es número entero
                    diags.add(new Diagnostico(E_FOR_VALOR_INICIAL_FALTANTE, lineaInicio, null,
                            "El valor inicial debe ser un número entero"));
                }

                // Validar valor final: debe existir y ser un número (según la especificación)
                if (valorFinal == null || valorFinal.isEmpty()) {
                    diags.add(new Diagnostico(E_FOR_VALOR_FINAL_FALTANTE, lineaInicio, null,
                            "El valor final está vacío o inválido"));
                } else if (!valorFinal.matches("\\d+")) {
                    // No es número entero
                    diags.add(new Diagnostico(E_FOR_VALOR_FINAL_FALTANTE, lineaInicio, null,
                            "El valor final debe ser un número entero"));
                }

                // Buscar begin después del for
                boolean encontroBegin = false;
                int lineaBegin = -1;
                // Validar que "begin" sea una palabra reservada válida
                if (!PalabrasReservadasP2.esReservada("begin")) {
                    diags.add(new Diagnostico(E_FOR_SINTAXIS_INCORRECTA, lineaInicio, null,
                            "'begin' debe ser una palabra reservada válida en Pascal"));
                }
                
                // Según especificación, 'begin' debe venir en la siguiente línea inmediatamente
                int siguiente = i + 1;
                if (siguiente < lineas.length && PATRON_BEGIN.matcher(lineas[siguiente]).matches()) {
                    encontroBegin = true;
                    lineaBegin = siguiente + 1;
                    String indentacionBegin = obtenerIndentacion(lineas[siguiente]);
                    if (!indentacionBegin.equals(indentacion + "    ")) {
                        diags.add(new Diagnostico(E_FOR_TABULACION_INCORRECTA, lineaBegin, null,
                                "La línea 'begin' debe tener una tabulación de 4 espacios respecto a la posición del for"));
                    }
                }

                if (!encontroBegin) {
                    diags.add(new Diagnostico(E_FOR_FALTA_BEGIN, lineaInicio, null,
                            "Después de 'do' debe venir 'begin' en la siguiente línea"));
                } else {
                    boolean encontroEnd = false;
                    boolean encontroSentencia = false;
                    for (int j = lineaBegin; j < lineas.length; j++) {
                        String lineaActual = lineas[j].trim();
                        // Prohibir 'for' anidados según la especificación
                        String lineaLower = lineaActual.toLowerCase();
                        if (lineaLower.startsWith("for ") || lineaLower.matches("^for\\b.*")) {
                            diags.add(new Diagnostico(E_FOR_SINTAXIS_INCORRECTA, j + 1, null,
                                    "No se permiten bucles 'for' anidados"));
                        }
                        if (!encontroSentencia && !lineaActual.isEmpty() && !PATRON_END.matcher(lineas[j]).matches()) {
                            encontroSentencia = true;
                            
                            // Verificar que la sentencia termine con punto y coma
                            if (!lineaActual.endsWith(";")) {
                                diags.add(new Diagnostico(E_FOR_FALTA_PUNTO_COMA, j + 1, null,
                                        "La sentencia dentro del bucle for debe terminar con punto y coma"));
                            }
                            
                            // Verificar tabulación de la sentencia (4 espacios más que el begin)
                            String indentacionSentencia = obtenerIndentacion(lineas[j]);
                            if (!indentacionSentencia.equals(indentacion + "        ")) {
                                diags.add(new Diagnostico(E_FOR_TABULACION_INCORRECTA, j + 1, null,
                                        "La sentencia debe tener una tabulación de 4 espacios respecto a la posición del begin"));
                            }
                        }

                        // Validar que "end" sea una palabra reservada válida
                        if (!PalabrasReservadasP2.esReservada("end")) {
                            diags.add(new Diagnostico(E_FOR_SINTAXIS_INCORRECTA, j + 1, null,
                                    "'end' debe ser una palabra reservada válida en Pascal"));
                        }
                        
                        if (PATRON_END.matcher(lineas[j]).matches()) {
                            encontroEnd = true;
                            int lineaFin = j + 1;
                            ultimaLineaFin = lineaFin;

                            // Verificar que end termine con punto y coma
                            String lineaEnd = lineas[j].trim();
                            if (!lineaEnd.endsWith(";")) {
                                diags.add(new Diagnostico(E_FOR_FALTA_PUNTO_COMA, lineaFin, null,
                                        "La línea 'end' debe terminar con punto y coma"));
                            }
                            
                            String indentacionEnd = obtenerIndentacion(lineas[j]);
                            if (!indentacionEnd.equals(indentacion + "    ")) {
                                diags.add(new Diagnostico(E_FOR_TABULACION_INCORRECTA, lineaFin, null,
                                        "La línea 'end;' debe tener una tabulación de 4 espacios respecto a la posición del for"));
                            }

                            i = j; // avanzar índice principal
                            break;
                        }
                    }
                        // Validar que se haya encontrado al menos una sentencia entre begin y end
                    if (!encontroSentencia && encontroEnd) {
                        diags.add(new Diagnostico(E_FOR_SIN_SENTENCIAS, lineaBegin, null,
                                "El bucle for debe contener al menos una línea de código"));
                    }

                    if (!encontroEnd) {
                        diags.add(new Diagnostico(E_FOR_FALTA_END, lineaBegin, null,
                                "El bucle for debe terminar con 'end;'"));
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