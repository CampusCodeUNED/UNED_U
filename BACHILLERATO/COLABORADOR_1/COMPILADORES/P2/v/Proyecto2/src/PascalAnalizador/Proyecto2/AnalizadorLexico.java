package PascalAnalizador.Proyecto2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalizadorLexico {
    private final String codigo;
    private final List<Token> tokens;
    private int posicion;
    private int linea;
    private int columna;
    private static final Map<String, TipoToken> palabrasReservadas;

    static {
        palabrasReservadas = new HashMap<>();
        // Palabras reservadas específicas para estructuras de control
        palabrasReservadas.put("if", TipoToken.IF);
        palabrasReservadas.put("then", TipoToken.THEN);
        palabrasReservadas.put("else", TipoToken.ELSE);
        palabrasReservadas.put("for", TipoToken.FOR);
        palabrasReservadas.put("to", TipoToken.TO);
        palabrasReservadas.put("do", TipoToken.DO);
        palabrasReservadas.put("repeat", TipoToken.REPEAT);
        palabrasReservadas.put("until", TipoToken.UNTIL);
        // Delimitadores básicos
        palabrasReservadas.put("begin", TipoToken.BEGIN);
        palabrasReservadas.put("end", TipoToken.END);
    }

    public AnalizadorLexico(String codigo) {
        this.codigo = codigo;
        this.tokens = new ArrayList<>();
        this.posicion = 0;
        this.linea = 1;
        this.columna = 1;
    }

    public List<Token> analizarCodigo() {
        while (posicion < codigo.length()) {
            char caracterActual = codigo.charAt(posicion);
            int columnaAnterior = columna;
            
            if (Character.isWhitespace(caracterActual)) {
                manejarEspaciosEnBlanco();
            } else if (Character.isLetter(caracterActual)) {
                analizarIdentificadorOPalabraReservada();
            } else if (Character.isDigit(caracterActual)) {
                analizarNumero();
            } else if (caracterActual == '\'') {
                analizarCadena();
            } else if (caracterActual == '{') {
                analizarComentario();
            } else {
                analizarOperadorODelimitador();
            }
            
            // Guardar información de espacios
            if (!tokens.isEmpty()) {
                Token ultimoToken = tokens.get(tokens.size() - 1);
                ultimoToken.setTieneEspacioAntes(columnaAnterior > 1);
                ultimoToken.setTieneEspacioDespues(caracterActual == ' ');
            }
        }
        
        return tokens;
    }

    private void manejarEspaciosEnBlanco() {
        char c = codigo.charAt(posicion);
        if (c == '\n') {
            linea++;
            columna = 1;
        } else {
            columna++;
        }
        posicion++;
    }

    private void analizarIdentificadorOPalabraReservada() {
        StringBuilder builder = new StringBuilder();
        int columnaInicial = columna;

        while (posicion < codigo.length() && 
               (Character.isLetterOrDigit(codigo.charAt(posicion)) || 
                codigo.charAt(posicion) == '_')) {
            builder.append(codigo.charAt(posicion));
            columna++;
            posicion++;
        }

        String palabra = builder.toString().toLowerCase();
        TipoToken tipo = palabrasReservadas.getOrDefault(palabra, TipoToken.IDENTIFIER);
        tokens.add(new Token(tipo, builder.toString(), linea, columnaInicial));
    }

    private void analizarNumero() {
        StringBuilder builder = new StringBuilder();
        int columnaInicial = columna;

        while (posicion < codigo.length() && Character.isDigit(codigo.charAt(posicion))) {
            builder.append(codigo.charAt(posicion));
            columna++;
            posicion++;
        }

        tokens.add(new Token(TipoToken.INTEGER_LITERAL, builder.toString(), linea, columnaInicial));
    }

    private void analizarCadena() {
        StringBuilder builder = new StringBuilder();
        int columnaInicial = columna;
        posicion++; // Saltar la comilla inicial
        columna++;

        while (posicion < codigo.length() && codigo.charAt(posicion) != '\'') {
            builder.append(codigo.charAt(posicion));
            columna++;
            posicion++;
        }

        if (posicion < codigo.length() && codigo.charAt(posicion) == '\'') {
            posicion++; // Saltar la comilla final
            columna++;
            // Las cadenas literales no son relevantes para el análisis de estructuras de control
        } else {
            tokens.add(new Token(TipoToken.ERROR, "Cadena no cerrada", linea, columnaInicial));
        }
    }

    private void analizarComentario() {
        int columnaInicial = columna;
        posicion++; // Saltar el {
        columna++;

        while (posicion < codigo.length() && codigo.charAt(posicion) != '}') {
            if (codigo.charAt(posicion) == '\n') {
                linea++;
                columna = 1;
            } else {
                columna++;
            }
            posicion++;
        }

        if (posicion < codigo.length() && codigo.charAt(posicion) == '}') {
            posicion++; // Saltar el }
            columna++;
        } else {
            tokens.add(new Token(TipoToken.ERROR, "Comentario no cerrado", linea, columnaInicial));
        }
    }

    private void analizarOperadorODelimitador() {
        char caracterActual = codigo.charAt(posicion);
        int columnaInicial = columna;
        
        switch (caracterActual) {
            case ';':
                tokens.add(new Token(TipoToken.SEMICOLON, ";", linea, columnaInicial));
                break;
            case ':':
                if (siguienteCaracterEs('=')) {
                    tokens.add(new Token(TipoToken.ASSIGN, ":=", linea, columnaInicial));
                    posicion++;
                    columna++;
                } else {
                    tokens.add(new Token(TipoToken.ERROR, "Operador no válido", linea, columnaInicial));
                }
                break;
            case '<':
                if (siguienteCaracterEs('=')) {
                    tokens.add(new Token(TipoToken.LESS_EQUAL, "<=", linea, columnaInicial));
                    posicion++;
                    columna++;
                } else if (siguienteCaracterEs('>')) {
                    tokens.add(new Token(TipoToken.NOT_EQUAL, "<>", linea, columnaInicial));
                    posicion++;
                    columna++;
                } else {
                    tokens.add(new Token(TipoToken.LESS, "<", linea, columnaInicial));
                }
                break;
            case '>':
                if (siguienteCaracterEs('=')) {
                    tokens.add(new Token(TipoToken.GREATER_EQUAL, ">=", linea, columnaInicial));
                    posicion++;
                    columna++;
                } else {
                    tokens.add(new Token(TipoToken.GREATER, ">", linea, columnaInicial));
                }
                break;
            case '=':
                tokens.add(new Token(TipoToken.EQUAL, "=", linea, columnaInicial));
                break;
            default:
                tokens.add(new Token(TipoToken.ERROR, 
                    "Carácter no reconocido: " + caracterActual, linea, columnaInicial));
                break;
        }
        
        posicion++;
        columna++;
    }

    private boolean siguienteCaracterEs(char esperado) {
        return posicion + 1 < codigo.length() && codigo.charAt(posicion + 1) == esperado;
    }
}