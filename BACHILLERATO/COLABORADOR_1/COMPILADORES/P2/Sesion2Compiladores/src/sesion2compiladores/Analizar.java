/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sesion2compiladores;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author cchac
 */
import java.io.BufferedWriter;
import java.io.IOException;

public class Analizar {
    static String delimitador=" <>(){};";
    private static BufferedWriter errorWriter = null;
    
    public static void setErrorWriter(BufferedWriter bw){
        errorWriter = bw;
    }

    public void AnalizaLinea(String TxtLinea, ControlesTotales crtlTotal, int lineaNumero)
    {
        ArrayList<String> LineaProcesada = new ArrayList<String>();
        ArrayList<String> LineaReal = new ArrayList<String>();  
        // Nuevo tokenizador: agrupa literales entre comillas, reconoce ':=' y separadores
        ArrayList<String> tokens = new ArrayList<String>();
        StringBuilder cur = new StringBuilder();
        for (int i = 0; i < TxtLinea.length(); i++) {
            char c = TxtLinea.charAt(i);
            // manejar literales entre comillas simples como un solo token
            if (c == '\'') {
                if (cur.length() > 0) { tokens.add(cur.toString()); cur.setLength(0); }
                StringBuilder lit = new StringBuilder();
                lit.append(c);
                i++;
                while (i < TxtLinea.length()) {
                    char d = TxtLinea.charAt(i);
                    lit.append(d);
                    if (d == '\'') { break; }
                    i++;
                }
                tokens.add(lit.toString());
                continue;
            }
            // reconocer ':=' como token
            if (c == ':' && i + 1 < TxtLinea.length() && TxtLinea.charAt(i + 1) == '=') {
                if (cur.length() > 0) { tokens.add(cur.toString()); cur.setLength(0); }
                tokens.add(":=");
                i++;
                continue;
            }
            // separadores: espacios y signos de puntuacion
            if ("(){}[];,:".indexOf(c) >= 0) {
                if (cur.length() > 0) { tokens.add(cur.toString()); cur.setLength(0); }
                // no incluir espacios
                if (c != ' ') tokens.add(Character.toString(c));
                continue;
            }
            if (Character.isWhitespace(c)) {
                if (cur.length() > 0) { tokens.add(cur.toString()); cur.setLength(0); }
                continue;
            }
            cur.append(c);
        }
        if (cur.length() > 0) tokens.add(cur.toString());

        // clasificar tokens usando TabladeSimbolos (respetando literales)
        for (String parte : tokens) {
            if (parte == null || parte.isBlank()) continue;
            String tipoDato = "";
            // si es literal entre comillas, lo dejamos como Literal
            if (parte.length() >= 2 && parte.charAt(0) == '\'' && parte.charAt(parte.length() - 1) == '\'') {
                tipoDato = "Literal";
            } else {
                for (TabladeSimbolos.Tipos comparaTOKENS : TabladeSimbolos.Tipos.values()) {
                    if (parte.toUpperCase().matches(comparaTOKENS.patron)) {
                        switch (comparaTOKENS) {
                            case Reservada:
                                tipoDato = "Reservada";
                                break;
                            case Numeros:
                                tipoDato = "Numeros";
                                break;
                            case Variable:
                                tipoDato = "Variable";
                                break;
                            case Operadores:
                                tipoDato = "Operadores";
                                break;
                            case Parentesis:
                                tipoDato = "Parentesis";
                                break;
                            case Separadores:
                                tipoDato = "Separadores";
                                break;
                            case TipoVar:
                                tipoDato = "TIPO";
                                break;
                            case Asignacion:
                                tipoDato = "Asignacion";
                                break;
                            default:
                                tipoDato = "";
                        }
                        break;
                    }
                }
            }
            if ("Reservada".equals(tipoDato) || "Separadores".equals(tipoDato) || "Parentesis".equals(tipoDato) || "Asignacion".equals(tipoDato))
                LineaProcesada.add(parte);
            else if (!tipoDato.isEmpty())
                LineaProcesada.add(tipoDato);
            else
                LineaProcesada.add(parte);
            LineaReal.add(parte);
        }
        System.out.println(Colores.ANSI_YELLOW+"VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV");
    System.out.println(Colores.ANSI_CYAN+" Línea obtenida -->> "+ TxtLinea);
        System.out.println(Colores.ANSI_PURPLE+"<<<<---------PROCESADO----------->>>"+Colores.ANSI_RESET);
    System.out.println(Colores.ANSI_CYAN+" Línea obtenida separada-->> "+ LineaReal);
        System.out.println(LineaProcesada);
        System.out.println(Colores.ANSI_PURPLE+"<<<<---------PROCESADO----------->>>"+Colores.ANSI_RESET);
        
    VerificaSintaxis comprueba = new VerificaSintaxis();
    if (comprueba.revisaSintaxis(LineaProcesada,LineaReal,crtlTotal, TxtLinea, lineaNumero, errorWriter)==true){
            System.out.println(Colores.ANSI_GREEN+"-----------------"+Colores.ANSI_RESET);
            System.out.println(Colores.ANSI_GREEN+"LINEA CON SINTAXIS CORRECTA!!!"+Colores.ANSI_RESET);
            System.out.println(Colores.ANSI_GREEN+"-----------------"+Colores.ANSI_RESET);
            //System.out.println("V");
        }
        else{
            System.out.println(Colores.ANSI_BLUE+"-----------------"+Colores.ANSI_RESET);
            System.out.println(Colores.ANSI_RED+"LINEA CON SINTAXIS ERRONEA"+Colores.ANSI_RESET);
            System.out.println(Colores.ANSI_BLUE+"-----------------"+Colores.ANSI_RESET);

        }
    }
    
}
