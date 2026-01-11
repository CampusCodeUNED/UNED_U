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
public class Analizar {
    static String delimitador=" <>(){};";
    
    public void AnalizaLinea(String TxtLinea, ControlesTotales crtlTotal,int lineaNumero)
    {
        if (TxtLinea == null || TxtLinea.trim().isEmpty()) {
            return;
        }
        ArrayList<String> LineaProcesada = new ArrayList<String>();
        ArrayList<String> LineaReal = new ArrayList<String>();  
        
        StringTokenizer segmentos = new StringTokenizer(TxtLinea,delimitador,true);
        
        while (segmentos.hasMoreTokens())
        {
            String parte = segmentos.nextToken();
            
            if (!parte.isBlank())
            {
                String encontrado="NO";
                String tipoDato="";

                for ( TabladeSimbolos.Tipos comparaTOKENS : TabladeSimbolos.Tipos.values())
                {   
                    if(encontrado=="NO")
                    {
                        if (parte.toUpperCase().matches(comparaTOKENS.patron))
                        {
                            switch (comparaTOKENS) {
                                case Reservada:
                                    //System.out.println(parte+"  Es una palabra reservada");
                                    encontrado="SI";
                                    tipoDato="Reservada";
                                    break;
                                case Numeros:
                                    //System.out.println(parte+"  Es un n�mero");
                                    encontrado="SI";
                                    tipoDato="Numeros";
                                    break;
                                case Variable:
                                    //System.out.println(parte+"  Es una variable del usuario");
                                    encontrado="SI";
                                    tipoDato="Variable";
                                    break;
                                case Operadores:
                                    //System.out.println(parte+"  Es un operador");
                                    encontrado="SI";
                                    tipoDato="Operadores";
                                    break;
                                case Parentesis:     
                                    //System.out.println(parte+"  Es un par�ntesis");
                                    encontrado="SI";
                                    tipoDato="Parentesis";
                                    break;
                                case Separadores:     
                                    //System.out.println(parte+"  Es un separador");
                                    encontrado="SI";
                                    tipoDato="Separadores";
                                    break;
                                case TipoVar:     
                                    encontrado="SI";
                                    tipoDato="TIPO";
                                    break;
                                case Asignacion:     
                                    encontrado="SI";
                                    tipoDato="Asignacion";
                                    break;
                            }
                        }
                    }
                }
                if (tipoDato=="Reservada" || tipoDato=="Separadores" || tipoDato=="Parentesis"||tipoDato=="Asignacion")
                    LineaProcesada.add(parte);
                else
                    LineaProcesada.add(tipoDato);
                LineaReal.add(parte);
                
            }
        
        }
        System.out.println(Colores.ANSI_YELLOW+"VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV");
        System.out.println(Colores.ANSI_CYAN+" L�nea obtenida -->> "+ TxtLinea);
        System.out.println(Colores.ANSI_PURPLE+"<<<<---------PROCESADO----------->>>"+Colores.ANSI_RESET);
        System.out.println(Colores.ANSI_CYAN+" L�nea obtenida seperada-->> "+ LineaReal);
        System.out.println(LineaProcesada);
        System.out.println(Colores.ANSI_PURPLE+"<<<<---------PROCESADO----------->>>"+Colores.ANSI_RESET);
        
    VerificaSintaxis comprueba = new VerificaSintaxis();
    if (comprueba.revisaSintaxis(LineaProcesada,LineaReal,crtlTotal,lineaNumero)==true){
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
