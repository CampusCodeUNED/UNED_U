package PascalAnalizador.Proyecto2;

import PascalAnalizador.Proyecto1.Diagnostico;
import java.util.List;

/**
 * AnalizadorPrincipal utiliza tokens para detectar errores léxicos
 * específicos en estructuras de control (FOR, IF, REPEAT).
 */
public class AnalizadorPrincipal {
    public static List<Diagnostico> analizarEstructurasConTokens(String codigo) {
        List<Diagnostico> errores = new java.util.ArrayList<>();
        AnalizadorLexico lexer = new AnalizadorLexico(codigo);
        List<Token> tokens = lexer.analizarCodigo();

        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            
            switch (token.getTipo()) {
                case FOR:
                    validarForConTokens(tokens, i, errores);
                    break;
                case IF:
                    validarIfConTokens(tokens, i, errores);
                    break;
                case REPEAT:
                    validarRepeatConTokens(tokens, i, errores);
                    break;
                default:
                    break;
            }
        }
        
        return errores;
    }

    private static void validarForConTokens(List<Token> tokens, int pos, List<Diagnostico> errores) {
        // Validar espacios alrededor del operador :=
        for (int i = pos; i < Math.min(pos + 10, tokens.size()); i++) {
            if (tokens.get(i).getTipo() == TipoToken.ASSIGN) {
                if (!tokens.get(i).tieneEspacioAntes() || !tokens.get(i).tieneEspacioDespues()) {
                    errores.add(new Diagnostico(900, tokens.get(i).getLinea(), null,
                        "Debe haber un espacio antes y después de ':='"));
                }
                break;
            }
        }
    }

    private static void validarIfConTokens(List<Token> tokens, int pos, List<Diagnostico> errores) {
        Token tokenIf = tokens.get(pos);
        boolean encontroThen = false;
        
        // Buscar THEN dentro de los siguientes 30 tokens
        for (int i = pos + 1; i < tokens.size() && i < pos + 30; i++) {
            if (tokens.get(i).getTipo() == TipoToken.THEN) {
                encontroThen = true;
                break;
            }
        }
        
        if (!encontroThen) {
            errores.add(new Diagnostico(911, tokenIf.getLinea(), null,
                "Falta THEN en la estructura IF"));
        }
    }

    private static void validarRepeatConTokens(List<Token> tokens, int pos, List<Diagnostico> errores) {
        Token tokenRepeat = tokens.get(pos);
        boolean encontroUntil = false;
        
        for (int i = pos + 1; i < tokens.size(); i++) {
            if (tokens.get(i).getTipo() == TipoToken.UNTIL) {
                encontroUntil = true;
                break;
            }
        }
        
        if (!encontroUntil) {
            errores.add(new Diagnostico(922, tokenRepeat.getLinea(), null,
                "Falta UNTIL para cerrar REPEAT"));
        }
    }
}