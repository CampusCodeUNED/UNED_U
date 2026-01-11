package PascalAnalizador.Proyecto2;

import PascalAnalizador.Proyecto1.Diagnostico;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ValidadorTabulacionAvanzada {
    
    public static List<Diagnostico> validarTabulacionesCompletas(String codigo) {
        List<Diagnostico> errores = new ArrayList<>();
        String[] lineas = codigo.split("\\r?\\n", -1);
        Stack<TabulacionContext> stack = new Stack<>();
        
        for (int i = 0; i < lineas.length; i++) {
            String linea = lineas[i];
            String lineaTrim = linea.trim().toLowerCase();
            String indentacion = obtenerIndentacion(linea);
            int numIndent = indentacion.length();
            
            // Detectar BEGIN
            if (lineaTrim.startsWith("begin")) {
                stack.push(new TabulacionContext("begin", numIndent, i + 1));
            }
            
            // Detectar END
            else if (lineaTrim.startsWith("end")) {
                if (!stack.isEmpty()) {
                    TabulacionContext ctx = stack.peek();
                    
                    // Si el tipo es "if" o "for", verificar si el END está al mismo nivel
                    // En Pascal, IF/FOR se cierran implícitamente cuando termina su begin...end
                    if ((ctx.tipo.equals("if") || ctx.tipo.equals("for")) && numIndent == ctx.indentacion) {
                        // El END cierra el IF o FOR
                        stack.pop();
                        // No reportar error, es correcto
                    } else {
                        // Para otros tipos (begin, repeat, case), hacer pop y validar
                        ctx = stack.pop();
                        if (numIndent != ctx.indentacion) {
                            errores.add(new Diagnostico(999, i + 1, null,
                                "La línea 'end' debe tener la misma tabulación que '" + ctx.tipo + "' " +
                                "(esperado: " + ctx.indentacion + " espacios, encontrado: " + numIndent + ")"));
                        }
                    }
                } else {
                    errores.add(new Diagnostico(999, i + 1, null,
                        "END sin BEGIN correspondiente"));
                }
            }
            
            // Detectar IF
            else if (lineaTrim.startsWith("if ")) {
                stack.push(new TabulacionContext("if", numIndent, i + 1));
            }
            
            // Detectar THEN (debe estar al mismo nivel que IF)
            else if (lineaTrim.contains(" then")) {
                if (!stack.isEmpty() && stack.peek().tipo.equals("if")) {
                    // THEN debe estar en la misma línea o indentado igual
                    TabulacionContext ifCtx = stack.peek();
                    if (numIndent != ifCtx.indentacion) {
                        errores.add(new Diagnostico(324, i + 1, null,
                            "El 'then' debe tener la misma tabulación que el 'if'"));
                    }
                }
            }
            
            // Detectar ELSE
            else if (lineaTrim.startsWith("else")) {
                if (!stack.isEmpty() && stack.peek().tipo.equals("if")) {
                    TabulacionContext ifCtx = stack.peek();
                    if (numIndent != ifCtx.indentacion) {
                        errores.add(new Diagnostico(324, i + 1, null,
                            "El 'else' debe tener la misma tabulación que el 'if'"));
                    }
                }
            }
            
            // Detectar FOR
            else if (lineaTrim.startsWith("for ")) {
                stack.push(new TabulacionContext("for", numIndent, i + 1));
            }
            
            // Detectar REPEAT
            else if (lineaTrim.startsWith("repeat")) {
                stack.push(new TabulacionContext("repeat", numIndent, i + 1));
            }
            
            // Detectar UNTIL
            else if (lineaTrim.startsWith("until")) {
                if (!stack.isEmpty() && stack.peek().tipo.equals("repeat")) {
                    TabulacionContext repeatCtx = stack.pop();
                    if (numIndent != repeatCtx.indentacion) {
                        errores.add(new Diagnostico(999, i + 1, null,
                            "El 'until' debe tener la misma tabulación que el 'repeat'"));
                    }
                }
            }
            
            // Detectar CASE
            else if (lineaTrim.startsWith("case ")) {
                stack.push(new TabulacionContext("case", numIndent, i + 1));
            }
        }
        
        return errores;
    }
    
    private static String obtenerIndentacion(String linea) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < linea.length(); i++) {
            if (Character.isWhitespace(linea.charAt(i)) && linea.charAt(i) != '\n' && linea.charAt(i) != '\r') {
                sb.append(linea.charAt(i));
            } else {
                break;
            }
        }
        return sb.toString();
    }
    
    static class TabulacionContext {
        String tipo;
        int indentacion;
        
        TabulacionContext(String tipo, int indentacion, int lineaInicio) {
            this.tipo = tipo;
            this.indentacion = indentacion;
        }
    }
}
