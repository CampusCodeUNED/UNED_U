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
    public boolean revisaSintaxis(ArrayList<String> lineaProcesada,ArrayList<String> lineaReal,ControlesTotales ctrlTotales)
    {
       switch (lineaProcesada.get(0))
        {
           case "CICLO": return(procesaCiclo(lineaProcesada,lineaReal,ctrlTotales));
                         //break;
           case "FINCICLO": return(procesaFinCiclo(lineaProcesada));
                   
           case "INICIO": return(procesaInicio(lineaProcesada));
                      //break;
           case "DECLARAVAR": return(procesaDeclara(lineaProcesada,lineaReal,ctrlTotales));
           
           case "Variable": return(procesaInicializaVar(lineaProcesada));
           default: return false;
        }
       //return false;
    } 
    
    public boolean procesaCiclo(ArrayList<String> datosxProcesar,ArrayList<String> lineaReal,ControlesTotales ctrlTotales)
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
                    System.out.println(Colores.ANSI_RED+"La línea presenta un error en posición "+Colores.ANSI_BLUE+(i+1)+Colores.ANSI_RED+" en el dato >>> "
                            +Colores.ANSI_BLUE+datosxProcesar.get(i)
                            +Colores.ANSI_RED+" <<< Se esperaba  --> "+Colores.ANSI_BLUE+TabladeSintaxis.SintaxisCiclo[i]+Colores.ANSI_RED+" <--"+Colores.ANSI_RESET);
                    Correcto=false;
                }
            }
        }
        if (Correcto==false)
        {
            encuentraErrorCiclo(datosxProcesar);
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
                    System.out.println(Colores.ANSI_RED+"La variable "
                                +Colores.ANSI_BLUE+lineaReal.get(2)
                                +Colores.ANSI_RED+" <No> ha sido declarada"+Colores.ANSI_RESET);
                    Correcto=false;
                }                
            }
            else
            {
                if (buscarVariable(ctrlTotales,Var1)==false)
                {
                    System.out.println(Colores.ANSI_RED+"La variable "
                                +Colores.ANSI_BLUE+lineaReal.get(2)
                                +Colores.ANSI_RED+" <No> ha sido declarada"+Colores.ANSI_RESET);
                    Correcto=false;
                }
                if (buscarVariable(ctrlTotales,Var2)==false)
                {
                    System.out.println(Colores.ANSI_RED+"La variable "
                                +Colores.ANSI_BLUE+lineaReal.get(6)
                                +Colores.ANSI_RED+" <No> ha sido declarada"+Colores.ANSI_RESET);
                    Correcto=false;
                }
                if (buscarVariable(ctrlTotales,Var3)==false)
                {
                    System.out.println(Colores.ANSI_RED+"La variable "
                                +Colores.ANSI_BLUE+lineaReal.get(10)
                                +Colores.ANSI_RED+" <No> ha sido declarada"+Colores.ANSI_RESET);
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
     
    public boolean procesaFinCiclo(ArrayList<String> datosxProcesar)
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
                    System.out.println(Colores.ANSI_RED+"La línea presenta un error en posición "+Colores.ANSI_BLUE+(i+1)+Colores.ANSI_RED+" en el dato >>> "
                            +Colores.ANSI_BLUE+datosxProcesar.get(i)
                            +Colores.ANSI_RED+" <<< Se esperaba  --> "+Colores.ANSI_BLUE+TabladeSintaxis.SintaxisFinCiclo[i]+Colores.ANSI_RED+" <--"+Colores.ANSI_RESET);
                    Correcto=false;
                }
            }
        }
        return Correcto;
    } 
     
    public boolean procesaDeclara(ArrayList<String> datosxProcesar, ArrayList<String> lineaReal,ControlesTotales ctrlTotales)
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
                    System.out.println(Colores.ANSI_RED+"La línea presenta un error en posición "+Colores.ANSI_BLUE+(i+1)+Colores.ANSI_RED+" en el dato >>> "
                            +Colores.ANSI_BLUE+datosxProcesar.get(i)
                            +Colores.ANSI_RED+" <<< Se esperaba  --> "+Colores.ANSI_BLUE+TabladeSintaxis.SintaxisDeclara[i]+Colores.ANSI_RED+" <--"+Colores.ANSI_RESET);
                    Correcto=false;
                }
            }
        }
        if (Correcto=true){
            boolean incluirVar=true;
            if (buscarVariable(ctrlTotales,lineaReal.get(2).trim().toString()))
            {
                incluirVar=false;
                System.out.println(Colores.ANSI_RED+"La variable "
                            +Colores.ANSI_BLUE+lineaReal.get(2).trim().toString()
                            +Colores.ANSI_RED+" ya ha sido declarada"+Colores.ANSI_RESET);
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
    
    public boolean procesaInicializaVar(ArrayList<String> datosxProcesar)
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
                    System.out.println(Colores.ANSI_RED+"La línea presenta un error en posición "+Colores.ANSI_BLUE+(i+1)+Colores.ANSI_RED+" en el dato >>> "
                            +Colores.ANSI_BLUE+datosxProcesar.get(i)
                            +Colores.ANSI_RED+" <<< Se esperaba  --> "+Colores.ANSI_BLUE+TabladeSintaxis.SintaxisInicializaVarInt[i]+Colores.ANSI_RED+" <--"+Colores.ANSI_RESET);
                    Correcto=false;
                }
            }
        }
        return Correcto;          
    }  

    public boolean procesaInicio(ArrayList<String> datosxProcesar)
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
                    System.out.println(Colores.ANSI_RED+"La línea presenta un error en posición "+Colores.ANSI_BLUE+(i+1)+Colores.ANSI_RED+" en el dato >>> "
                            +Colores.ANSI_BLUE+datosxProcesar.get(i)
                            +Colores.ANSI_RED+" <<< Se esperaba  --> "+Colores.ANSI_BLUE+TabladeSintaxis.SintaxisInicio[i]+Colores.ANSI_RED+" <--"+Colores.ANSI_RESET);
                    Correcto=false;
                }
            }
        }
        return Correcto;    
    }   
    
    
    public void encuentraErrorCiclo(ArrayList<String> datosxProcesar){
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
                System.out.println("Error : En la estructura del ciclo hace falta "+TabladeSintaxis.SintaxisCiclo[i]);
            }
        }
    } 
     
     
}
