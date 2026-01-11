/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sesion2compiladores;

import java.util.ArrayList;

/**
 *
 * @author cchac
 */
public class VerificaSintaxis {
     public boolean revisaSintaxis(ArrayList<String> lineaProcesada,ArrayList<String> lineaReal,ControlesTotales ctrlTotales,int lineaNumero)
     {
         switch (lineaProcesada.get(0))
          {
              case "CICLO": return(procesaCiclo(lineaProcesada,lineaReal,ctrlTotales,lineaNumero));
                                 //break;
              case "FINCICLO": return(procesaFinCiclo(lineaProcesada,lineaNumero));
                   
              case "INICIO": return(procesaInicio(lineaProcesada,lineaNumero));
                             //break;
              case "DECLARAVAR": return(procesaDeclara(lineaProcesada,lineaReal,ctrlTotales,lineaNumero));
           
              case "Variable": return(procesaInicializaVar(lineaProcesada,lineaNumero));
              default: return false;
          }
         //return false;
     } 
    
    public boolean procesaCiclo(ArrayList<String> datosxProcesar,ArrayList<String> lineaReal,ControlesTotales ctrlTotales,int lineaNumero)
    {
        boolean Correcto=true;
        
        System.out.println(Colores.ANSI_CYAN+"-----------------------------------------------"+Colores.ANSI_RESET);
        System.out.println(Colores.ANSI_YELLOW+"               CICLO DETECTADO"+Colores.ANSI_RESET);
        System.out.println(Colores.ANSI_CYAN+"-----------------------------------------------"+Colores.ANSI_RESET);
        System.out.println("");
        for (int i=0; i< TabladeSintaxis.SintaxisCiclo.length;i++)
        {
            if (datosxProcesar.size()<i+1){
                System.out.println(Colores.ANSI_RED+"Se omite --> "+Colores.ANSI_BLUE+TabladeSintaxis.SintaxisCiclo[i]+Colores.ANSI_CYAN+" <--"+Colores.ANSI_RESET);
                Correcto=false;
            }else{
                if (datosxProcesar.get(i).trim().toString().equals(TabladeSintaxis.SintaxisCiclo[i].trim().toString()))
                {}
                else
                {
                    String msgCiclo = "La linea presenta un error en posicion " + (i+1) + " en el dato >>> "
                            + datosxProcesar.get(i)
                            + " <<< Se esperaba --> " + TabladeSintaxis.SintaxisCiclo[i] + " <--";
                    System.out.println(Colores.ANSI_RED+msgCiclo+Colores.ANSI_RESET);
                    // Registrar en archivo de errores: código 100 = sintaxis en ciclo
                    ErrorLogger.logFormatted(100, lineaNumero, "Posicion " + (i+1) + ": " + msgCiclo);
                    Correcto=false;
                }
            }
        }
        if (Correcto==false)
        {
            encuentraErrorCiclo(datosxProcesar,lineaNumero);
        }else
        {
            ControlEstructuras agregaCiclo = new ControlEstructuras();
            agregaCiclo.setIniComando(lineaReal.get(0).trim().toString());
            agregaCiclo.setCierreComando("");
            ctrlTotales.Estructuras.add(agregaCiclo);
            
            String Var1 = lineaReal.get(2).trim().toString();
            String Var2 = lineaReal.get(6).trim().toString();
            String Var3 = lineaReal.get(10).trim().toString();
            
            if (Var1.equals(Var2) && Var2.equals(Var3))
            {
                if (buscarVariable(ctrlTotales,Var1)==false)
                {
                        String msgVar = "La variable " + lineaReal.get(2) + " no ha sido declarada";
                        System.out.println(Colores.ANSI_RED+msgVar+Colores.ANSI_RESET);
                        // Registrar en archivo de errores: código 200 = variable no declarada
                        ErrorLogger.logFormatted(200, -1, msgVar + " (posicion/linea no disponible)");
                    Correcto=false;
                }                
            }
            else
            {
                if (buscarVariable(ctrlTotales,Var1)==false)
                {
                        String msgVar1 = "La variable " + lineaReal.get(2) + " no ha sido declarada";
                        System.out.println(Colores.ANSI_RED+msgVar1+Colores.ANSI_RESET);
                        ErrorLogger.logFormatted(200, -1, msgVar1 + " (posicion/linea no disponible)");
                    Correcto=false;
                }
                if (buscarVariable(ctrlTotales,Var2)==false)
                {
                        String msgVar2 = "La variable " + lineaReal.get(6) + " no ha sido declarada";
                        System.out.println(Colores.ANSI_RED+msgVar2+Colores.ANSI_RESET);
                        ErrorLogger.logFormatted(200, -1, msgVar2 + " (posicion/linea no disponible)");
                    Correcto=false;
                }
                if (buscarVariable(ctrlTotales,Var3)==false)
                {
                        String msgVar3 = "La variable " + lineaReal.get(10) + " no ha sido declarada";
                        System.out.println(Colores.ANSI_RED+msgVar3+Colores.ANSI_RESET);
                        ErrorLogger.logFormatted(200, -1, msgVar3 + " (posicion/linea no disponible)");
                    Correcto=false;
                }
            }
            
        }
        return Correcto;    
    }    
    
     public boolean buscarVariable(ControlesTotales ctrlTotales,String nombVar)
    {
        boolean encontrado=false;
        if (ctrlTotales.Variables != null){
             for (int i=0; i<ctrlTotales.Variables.size();i++)
            {
                if (nombVar.equals(ctrlTotales.Variables.get(i).getNombreVariable()))
                {
                    encontrado=true;
                }
            }
        }
        return encontrado;
    }
     
    public boolean procesaFinCiclo(ArrayList<String> datosxProcesar,int lineaNumero)
    {
        boolean Correcto=true;
        System.out.println(Colores.ANSI_CYAN+"-----------------------------------------------"+Colores.ANSI_RESET);
        System.out.println(Colores.ANSI_YELLOW+"           FIN DE CICLO DETECTADO"+Colores.ANSI_RESET);
        System.out.println(Colores.ANSI_CYAN+"-----------------------------------------------"+Colores.ANSI_RESET);
        System.out.println("");
        for (int i=0; i< TabladeSintaxis.SintaxisFinCiclo.length;i++)
        {
            if (datosxProcesar.size()<i+1){
                System.out.println(Colores.ANSI_RED+"Se omite --> "+Colores.ANSI_BLUE+TabladeSintaxis.SintaxisFinCiclo[i]+Colores.ANSI_CYAN+" <--"+Colores.ANSI_RESET);
                Correcto=false;
            }else{
                if (datosxProcesar.get(i).trim().toString().equals(TabladeSintaxis.SintaxisFinCiclo[i].trim().toString()))
                {}
                else
                {
                    String msgFin = "La linea presenta un error en posicion " + (i+1) + " en el dato >>> "
                            + datosxProcesar.get(i)
                            + " <<< Se esperaba --> " + TabladeSintaxis.SintaxisFinCiclo[i] + " <--";
                    System.out.println(Colores.ANSI_RED+msgFin+Colores.ANSI_RESET);
                    ErrorLogger.logFormatted(101, lineaNumero, "Posicion " + (i+1) + ": " + msgFin);
                    Correcto=false;
                }
            }
        }
        return Correcto;
    } 
     
    public boolean procesaDeclara(ArrayList<String> datosxProcesar, ArrayList<String> lineaReal,ControlesTotales ctrlTotales,int lineaNumero)
    {
        boolean Correcto=true;
        System.out.println(Colores.ANSI_CYAN+"-----------------------------------------------"+Colores.ANSI_RESET);
        System.out.println(Colores.ANSI_YELLOW+"           DECLARACION DE VARIABLE"+Colores.ANSI_RESET);
        System.out.println(Colores.ANSI_CYAN+"-----------------------------------------------"+Colores.ANSI_RESET);
        System.out.println("");
        for (int i=0; i< TabladeSintaxis.SintaxisDeclara.length;i++)
        {
            if (datosxProcesar.size()<i+1){
                System.out.println(Colores.ANSI_RED+"Se omite --> "+Colores.ANSI_BLUE+TabladeSintaxis.SintaxisDeclara[i]+Colores.ANSI_CYAN+" <--"+Colores.ANSI_RESET);
                Correcto=false;
            }else{
                if (datosxProcesar.get(i).trim().toString().equals(TabladeSintaxis.SintaxisDeclara[i].trim().toString()))
                {}
                else
                {
                    String msgDecl = "La linea presenta un error en posicion " + (i+1) + " en el dato >>> "
                            + datosxProcesar.get(i)
                            + " <<< Se esperaba --> " + TabladeSintaxis.SintaxisDeclara[i] + " <--";
                    System.out.println(Colores.ANSI_RED+msgDecl+Colores.ANSI_RESET);
                    ErrorLogger.logFormatted(102, lineaNumero, "Posicion " + (i+1) + ": " + msgDecl);
                    Correcto=false;
                }
            }
        }
            if (Correcto=true){
            boolean incluirVar=true;
            if (buscarVariable(ctrlTotales,lineaReal.get(2).trim().toString()))
            {
                incluirVar=false;
                    String msgYa = "La variable " + lineaReal.get(2).trim().toString() + " ya ha sido declarada";
                    System.out.println(Colores.ANSI_RED+msgYa+Colores.ANSI_RESET);
                    ErrorLogger.logFormatted(400, lineaNumero, msgYa);
            }
            if (incluirVar=true)
            {
                ControlVariablesDeclaradas agregaVar = new ControlVariablesDeclaradas();
                agregaVar.setNombreVariable(lineaReal.get(2).trim().toString());
                agregaVar.setTipoVariable(lineaReal.get(1).trim().toString());
                ctrlTotales.Variables.add(agregaVar);
            }
        }
        return Correcto;
    }  
    
    public boolean procesaInicializaVar(ArrayList<String> datosxProcesar,int lineaNumero)
    {
        boolean Correcto=true;
        System.out.println(Colores.ANSI_CYAN+"-----------------------------------------------"+Colores.ANSI_RESET);
        System.out.println(Colores.ANSI_YELLOW+"           INICIALIZA VARIABLE"+Colores.ANSI_RESET);
        System.out.println(Colores.ANSI_CYAN+"-----------------------------------------------"+Colores.ANSI_RESET);
        System.out.println(""); 
        for (int i=0; i< TabladeSintaxis.SintaxisInicializaVarInt.length;i++)
        {
            if (datosxProcesar.size()<i+1){
                System.out.println(Colores.ANSI_RED+"Se omite --> "+Colores.ANSI_BLUE+TabladeSintaxis.SintaxisInicializaVarInt[i]+Colores.ANSI_CYAN+" <--"+Colores.ANSI_RESET);
                Correcto=false;
            }else{
                if (datosxProcesar.get(i).trim().toString().equals(TabladeSintaxis.SintaxisInicializaVarInt[i].trim().toString()))
                {}
                else
                {
                    String msgInit = "La linea presenta un error en posicion " + (i+1) + " en el dato >>> "
                            + datosxProcesar.get(i)
                            + " <<< Se esperaba --> " + TabladeSintaxis.SintaxisInicializaVarInt[i] + " <--";
                    System.out.println(Colores.ANSI_RED+msgInit+Colores.ANSI_RESET);
                    ErrorLogger.logFormatted(103, lineaNumero, "Posicion " + (i+1) + ": " + msgInit);
                    Correcto=false;
                }
            }
        }
        return Correcto;          
    }  

    public boolean procesaInicio(ArrayList<String> datosxProcesar,int lineaNumero)
    {
        boolean Correcto=true;
        
        System.out.println(Colores.ANSI_CYAN+"-----------------------------------------------"+Colores.ANSI_RESET);
        System.out.println(Colores.ANSI_YELLOW+"               INICIO DETECTADO"+Colores.ANSI_RESET);
        System.out.println(Colores.ANSI_CYAN+"-----------------------------------------------"+Colores.ANSI_RESET);
        System.out.println("");
        
        for (int i=0; i< TabladeSintaxis.SintaxisInicio.length;i++)
        {
            if (datosxProcesar.size()<i+1){
                System.out.println(Colores.ANSI_RED+"Se omite --> "+Colores.ANSI_BLUE+TabladeSintaxis.SintaxisInicio[i]+Colores.ANSI_CYAN+" <--"+Colores.ANSI_RESET);
                Correcto=false;
            }else{
                if (datosxProcesar.get(i).trim().toString().equals(TabladeSintaxis.SintaxisInicio[i].trim().toString()))
                {}
                else
                {
                    String msgIni = "La linea presenta un error en posicion " + (i+1) + " en el dato >>> "
                            + datosxProcesar.get(i)
                            + " <<< Se esperaba --> " + TabladeSintaxis.SintaxisInicio[i] + " <--";
                    System.out.println(Colores.ANSI_RED+msgIni+Colores.ANSI_RESET);
                    ErrorLogger.logFormatted(104, lineaNumero, "Posicion " + (i+1) + ": " + msgIni);
                    Correcto=false;
                }
            }
        }
        return Correcto;    
    }   
    
    
    public void encuentraErrorCiclo(ArrayList<String> datosxProcesar,int lineaNumero){
        for (int i=0; i<TabladeSintaxis.SintaxisCiclo.length;i++)
        {
            boolean encontrado=false;
            for (int j=0; j<datosxProcesar.size();j++)
            {
                if (TabladeSintaxis.SintaxisCiclo[i].trim().toString().equals(datosxProcesar.get(j).trim().toString()))
                {
                    encontrado=true;
                }
            }
            if (encontrado==false)
            {
                String msg = "En la estructura del ciclo hace falta " + TabladeSintaxis.SintaxisCiclo[i];
                System.out.println("Error : " + msg);
                ErrorLogger.logFormatted(300, lineaNumero, msg);
            }
        }
    } 
     
     
}
