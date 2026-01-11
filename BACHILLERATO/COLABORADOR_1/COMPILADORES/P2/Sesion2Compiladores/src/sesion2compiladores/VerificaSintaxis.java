/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sesion2compiladores;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author cchac
 */
public class VerificaSintaxis {
    public boolean revisaSintaxis(ArrayList<String> lineaProcesada,ArrayList<String> lineaReal,ControlesTotales ctrlTotales, String rawLine, int lineaNumero, BufferedWriter bw)
     {
         // Si la linea esta vacia, verificar si se esperaba un BEGIN inmediatamente despues de un FOR
         if (lineaProcesada==null || lineaProcesada.size()==0) {
             if (ctrlTotales!=null && ctrlTotales.Estructuras!=null && ctrlTotales.Estructuras.size()>0){
                 ControlEstructuras last = ctrlTotales.Estructuras.get(ctrlTotales.Estructuras.size()-1);
                 if ("FOR".equals(last.getIniComando()) && "EXPECT_BEGIN".equals(last.getCierreComando())){
                     reportError(331, lineaNumero, "Se esperaba BEGIN en la línea siguiente al FOR", bw);
                     // marcar como error y consumir
                     last.setCierreComando("ERROR_EXPECT_BEGIN");
                     return false;
                 }
             }
             return false;
         }
         String token0 = lineaProcesada.get(0).toUpperCase();
         // Si habiamos dejado un FOR esperando BEGIN en la linea siguiente, forzar que el token sea BEGIN
         if (ctrlTotales!=null && ctrlTotales.Estructuras!=null && ctrlTotales.Estructuras.size()>0){
             ControlEstructuras last = ctrlTotales.Estructuras.get(ctrlTotales.Estructuras.size()-1);
             if ("FOR".equals(last.getIniComando()) && "EXPECT_BEGIN".equals(last.getCierreComando()) && !"BEGIN".equals(token0)){
                 reportError(331, lineaNumero, "Se esperaba BEGIN en la línea siguiente al FOR", bw);
                 last.setCierreComando("ERROR_EXPECT_BEGIN");
                 return false;
             }
         }
         // Sólo validamos la sintaxis relacionada con FOR/BEGIN/END según la especificación del usuario.
         // Para cualquier otra línea no emitimos errores (se ignoran).
         if ("FOR".equals(token0) || "CICLO".equals(token0)){
             return procesaCiclo(lineaProcesada,lineaReal,ctrlTotales, rawLine, lineaNumero, bw);
         }
         if ("BEGIN".equals(token0)){
             return procesaBegin(lineaProcesada,lineaReal,ctrlTotales, rawLine, lineaNumero, bw);
         }
         if ("END".equals(token0)){
             return procesaEnd(lineaProcesada,lineaReal,ctrlTotales, rawLine, lineaNumero, bw);
         }
         // Ignorar todo lo demás (no validar otras construcciones)
        // Sin embargo, si estamos dentro de un FOR que ya tuvo su BEGIN, esta linea puede ser
        // la sentencia obligatoria del FOR: validarla aquí (termina en ';' y tiene la indentacion correcta).
        if (ctrlTotales!=null && ctrlTotales.Estructuras!=null && !ctrlTotales.Estructuras.isEmpty()){
            ControlEstructuras last = ctrlTotales.Estructuras.get(ctrlTotales.Estructuras.size()-1);
            if ("FOR".equals(last.getIniComando()) && "BEGIN_SEEN".equals(last.getCierreComando())){
                // verificar que la linea termine en ;
                if (lineaReal==null || lineaReal.size()==0 || !lineaReal.get(lineaReal.size()-1).equals(";")){
                    reportError(311, lineaNumero, "La sentencia del FOR debe terminar en punto y coma ';'", bw);
                    return false;
                }
                int baseIndent = 0;
                try{ baseIndent = Integer.parseInt(last.getComando()); }catch(Exception e){ baseIndent=0; }
                int expectedSentenceIndent = baseIndent + 8;
                int actualIndent=0;
                for (int i=0;i<rawLine.length();i++){ if (rawLine.charAt(i)!=' ') break; actualIndent++; }
                if (actualIndent != expectedSentenceIndent){
                    reportError(321, lineaNumero, "Indentacion de la sentencia incorrecta. Se esperaban "+expectedSentenceIndent+" espacios.", bw);
                    return false;
                }
                // marcar que ya vimos la sentencia
                last.setCierreComando("SENTENCE_SEEN");
                return true;
            }
        }
        return true;
     } 

    private void reportError(int code, int lineaNumero, String msg, BufferedWriter bw){
        String formatted = "Error "+code+". Linea "+lineaNumero+". "+msg;
        System.out.println(formatted);
        if (bw!=null){
            try{
                bw.write(formatted);
                bw.newLine();
                bw.flush();
            }catch(IOException ex){
                System.out.println("No se pudo escribir en archivo de errores: "+ex.getMessage());
            }
        }
    }
    
    public boolean procesaCiclo(ArrayList<String> datosxProcesar,ArrayList<String> lineaReal,ControlesTotales ctrlTotales, String rawLine, int lineaNumero, BufferedWriter bw)
    {
        boolean Correcto=true;
        System.out.println(Colores.ANSI_CYAN+"-----------------------------------------------"+Colores.ANSI_RESET);
        System.out.println(Colores.ANSI_YELLOW+"               CICLO/ FOR DETECTADO"+Colores.ANSI_RESET);
        System.out.println(Colores.ANSI_CYAN+"-----------------------------------------------"+Colores.ANSI_RESET);
        System.out.println("");

        String tipo = datosxProcesar.get(0).toUpperCase();
        // Si es FOR en estilo Pascal
        if (tipo.equals("FOR"))
        {
            // Verificar que no haya un FOR ya abierto (no se permite anidamiento)
            if (ctrlTotales!=null && ctrlTotales.Estructuras!=null && ctrlTotales.Estructuras.size()>0){
                ControlEstructuras lastOpen = ctrlTotales.Estructuras.get(ctrlTotales.Estructuras.size()-1);
                if ("FOR".equals(lastOpen.getIniComando()) && !"CLOSED".equals(lastOpen.getCierreComando())){
                    reportError(335, lineaNumero, "Anidamiento de ciclos FOR no permitido", bw);
                    return false;
                }
            }

            // Esperamos: FOR Variable ":=" Numeros TO Numeros DO
            if (datosxProcesar.size() < 7)
            {
                reportError(301, lineaNumero, "Faltan elementos en la estructura FOR (se esperaba: for v := inicio to fin do)", bw);
                return false;
            }
            boolean ok=true;
            if (!datosxProcesar.get(1).equals("Variable")){
                reportError(302, lineaNumero, "Se esperaba una variable despues de FOR, se encontro: "+datosxProcesar.get(1), bw);
                ok=false;
            }
            if (!datosxProcesar.get(2).equals(":=")){
                reportError(303, lineaNumero, "Asignacion debe ser ':=' sin espacios separados (token 3). Encontrado: "+datosxProcesar.get(2), bw);
                ok=false;
            }
            // aceptar numero literal o identificador (constante/variable) como valor inicial
            if (!(datosxProcesar.get(3).equals("Numeros") || datosxProcesar.get(3).equals("Variable"))){
                reportError(304, lineaNumero, "Valor inicial debe existir y ser un numero o identificador (token 4).", bw);
                ok=false;
            }
            if (!datosxProcesar.get(4).toUpperCase().equals("TO")){
                reportError(305, lineaNumero, "Se esperaba 'to' despues del valor inicial (token 5).", bw);
                ok=false;
            }
            // aceptar numero literal o identificador (constante/variable) como valor final
            if (!(datosxProcesar.get(5).equals("Numeros") || datosxProcesar.get(5).equals("Variable"))){
                reportError(306, lineaNumero, "Valor final debe existir y ser un numero o identificador (token 6).", bw);
                ok=false;
            }
            if (!datosxProcesar.get(6).toUpperCase().equals("DO")){
                reportError(307, lineaNumero, "Se esperaba 'do' despues del valor final (token 7).", bw);
                ok=false;
            }

            if (!ok) return false;

            // Requerir un espacio antes y despues de ':=' en la linea cruda (el usuario exige 'i := 1' y no 'i:=1')
            if (rawLine != null && !rawLine.contains(" := ")){
                reportError(333, lineaNumero, "Asignacion ':=' debe tener espacios antes y despues (ej. 'i := 1')", bw);
                return false;
            }

            // No se verifica la declaracion de variables aquí: la validación se limita al formato del FOR

            // Guardar control de estructura con indentacion base
            int baseIndent = 0;
            for (int i=0;i<rawLine.length();i++){
                if (rawLine.charAt(i)!=' ') break;
                baseIndent++;
            }
            ControlEstructuras agregaCiclo = new ControlEstructuras();
            agregaCiclo.setIniComando("FOR");
            // uso Comando para guardar la indentacion base como numero (string)
            agregaCiclo.setComando(Integer.toString(baseIndent));
            // indicar que en la siguiente linea se debe ver un BEGIN
            agregaCiclo.setCierreComando("EXPECT_BEGIN");
            ctrlTotales.Estructuras.add(agregaCiclo);

            return true;
        }

        // Si no es FOR, conserva comportamiento anterior (CICLO estilo original)
        for (int i=0; i< TabladeSintaxis.SintaxisCiclo.length;i++)
        {
            if (datosxProcesar.size()<i+1){
                reportError(309, lineaNumero, "Se omite --> "+TabladeSintaxis.SintaxisCiclo[i]+" <--", bw);
                Correcto=false;
            }else{
                if (datosxProcesar.get(i).trim().toString().equals(TabladeSintaxis.SintaxisCiclo[i].trim().toString()))
                {}
                else
                {
                    reportError(300, lineaNumero, "La linea presenta un error en posicion "+(i+1)+" en el dato >>> "+datosxProcesar.get(i)+" <<< Se esperaba --> "+TabladeSintaxis.SintaxisCiclo[i]+" <--", bw);
                    Correcto=false;
                }
            }
        }
        if (Correcto==false)
        {
            encuentraErrorCiclo(datosxProcesar, lineaNumero, bw);
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
                    reportError(308, lineaNumero, "La variable "+lineaReal.get(2)+" no ha sido declarada", bw);
                    Correcto=false;
                }                
            }
            else
            {
                if (buscarVariable(ctrlTotales,Var1)==false)
                {
                    reportError(308, lineaNumero, "La variable "+lineaReal.get(2)+" no ha sido declarada", bw);
                    Correcto=false;
                }
                if (buscarVariable(ctrlTotales,Var2)==false)
                {
                    reportError(308, lineaNumero, "La variable "+lineaReal.get(6)+" no ha sido declarada", bw);
                    Correcto=false;
                }
                if (buscarVariable(ctrlTotales,Var3)==false)
                {
                    reportError(308, lineaNumero, "La variable "+lineaReal.get(10)+" no ha sido declarada", bw);
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
     
    public boolean procesaFinCiclo(ArrayList<String> datosxProcesar, int lineaNumero, BufferedWriter bw)
    {
        boolean Correcto=true;
        System.out.println(Colores.ANSI_CYAN+"-----------------------------------------------"+Colores.ANSI_RESET);
        System.out.println(Colores.ANSI_YELLOW+"           FIN DE CICLO DETECTADO"+Colores.ANSI_RESET);
        System.out.println(Colores.ANSI_CYAN+"-----------------------------------------------"+Colores.ANSI_RESET);
        System.out.println("");
        for (int i=0; i< TabladeSintaxis.SintaxisFinCiclo.length;i++)
        {
            if (datosxProcesar.size()<i+1){
                reportError(309, lineaNumero, "Se omite --> "+TabladeSintaxis.SintaxisFinCiclo[i]+" <--", bw);
                Correcto=false;
            }else{
                if (datosxProcesar.get(i).trim().toString().equals(TabladeSintaxis.SintaxisFinCiclo[i].trim().toString()))
                {}
                else
                {
                    reportError(300, lineaNumero, "La linea presenta un error en posicion "+(i+1)+" en el dato >>> "+datosxProcesar.get(i)+" <<< Se esperaba --> "+TabladeSintaxis.SintaxisFinCiclo[i]+" <--", bw);
                    Correcto=false;
                }
            }
        }
        return Correcto;
    } 
     
    public boolean procesaDeclara(ArrayList<String> datosxProcesar, ArrayList<String> lineaReal,ControlesTotales ctrlTotales, int lineaNumero, BufferedWriter bw)
    {
        boolean Correcto=true;
        System.out.println(Colores.ANSI_CYAN+"-----------------------------------------------"+Colores.ANSI_RESET);
        System.out.println(Colores.ANSI_YELLOW+"           DECLARACION DE VARIABLE"+Colores.ANSI_RESET);
        System.out.println(Colores.ANSI_CYAN+"-----------------------------------------------"+Colores.ANSI_RESET);
        System.out.println("");
        for (int i=0; i< TabladeSintaxis.SintaxisDeclara.length;i++)
        {
            if (datosxProcesar.size()<i+1){
                reportError(309, lineaNumero, "Se omite --> "+TabladeSintaxis.SintaxisDeclara[i]+" <--", bw);
                Correcto=false;
            }else{
                if (datosxProcesar.get(i).trim().toString().equals(TabladeSintaxis.SintaxisDeclara[i].trim().toString()))
                {}
                else
                {
                    reportError(300, lineaNumero, "La linea presenta un error en posicion "+(i+1)+" en el dato >>> "+datosxProcesar.get(i)+" <<< Se esperaba --> "+TabladeSintaxis.SintaxisDeclara[i]+" <--", bw);
                    Correcto=false;
                }
            }
        }
            if (Correcto){
                boolean incluirVar=true;
                if (buscarVariable(ctrlTotales,lineaReal.get(2).trim().toString()))
                {
                    incluirVar=false;
                    reportError(310, lineaNumero, "La variable "+lineaReal.get(2).trim().toString()+" ya ha sido declarada", bw);
                }
                if (incluirVar)
                {
                    ControlVariablesDeclaradas agregaVar = new ControlVariablesDeclaradas();
                    agregaVar.setNombreVariable(lineaReal.get(2).trim().toString());
                    agregaVar.setTipoVariable(lineaReal.get(1).trim().toString());
                    ctrlTotales.Variables.add(agregaVar);
                }
            }
        return Correcto;
    }  
    
    public boolean procesaInicializaVar(ArrayList<String> datosxProcesar, ArrayList<String> lineaReal,ControlesTotales ctrlTotales, String rawLine, int lineaNumero, BufferedWriter bw)
    {
        boolean Correcto=true;
        System.out.println(Colores.ANSI_CYAN+"-----------------------------------------------"+Colores.ANSI_RESET);
        System.out.println(Colores.ANSI_YELLOW+"           INICIALIZA VARIABLE"+Colores.ANSI_RESET);
        System.out.println(Colores.ANSI_CYAN+"-----------------------------------------------"+Colores.ANSI_RESET);
        System.out.println(""); 
    for (int i=0; i< TabladeSintaxis.SintaxisInicializaVarInt.length;i++)
        {
            if (datosxProcesar.size()<i+1){
                reportError(309, lineaNumero, "Se omite --> "+TabladeSintaxis.SintaxisInicializaVarInt[i]+" <--", bw);
                Correcto=false;
            }else{
                if (datosxProcesar.get(i).trim().toString().equals(TabladeSintaxis.SintaxisInicializaVarInt[i].trim().toString()))
                {}
                else
                {
                    reportError(300, lineaNumero, "La linea presenta un error en posicion "+(i+1)+" en el dato >>> "+datosxProcesar.get(i)+" <<< Se esperaba --> "+TabladeSintaxis.SintaxisInicializaVarInt[i]+" <--", bw);
                    Correcto=false;
                }
            }
        }
        // Si estamos dentro de un FOR que ya tuvo su BEGIN, validar sentencia: al menos una linea y terminar en ; y indentacion
        if (ctrlTotales!=null && ctrlTotales.Estructuras!=null && ctrlTotales.Estructuras.size()>0){
            ControlEstructuras last = ctrlTotales.Estructuras.get(ctrlTotales.Estructuras.size()-1);
            if (last.getIniComando()!=null && last.getIniComando().equals("FOR") && "BEGIN_SEEN".equals(last.getCierreComando())){
                // verificar que la linea termine en ;
                if (lineaReal.size()==0 || !lineaReal.get(lineaReal.size()-1).equals(";")){
                    reportError(311, lineaNumero, "La sentencia del FOR debe terminar en punto y coma ';'", bw);
                    Correcto=false;
                }
                // verificar indentacion de la sentencia: debe ser baseIndent + 8
                int baseIndent = 0;
                try{ baseIndent = Integer.parseInt(last.getComando()); }catch(Exception e){ baseIndent=0; }
                int expectedSentenceIndent = baseIndent + 8;
                int actualIndent=0;
                for (int i=0;i<rawLine.length();i++){ if (rawLine.charAt(i)!=' ') break; actualIndent++; }
                if (actualIndent != expectedSentenceIndent){
                    reportError(321, lineaNumero, "Indentacion de la sentencia incorrecta. Se esperaban "+expectedSentenceIndent+" espacios.", bw);
                    Correcto=false;
                }
                if (Correcto){
                    last.setCierreComando("SENTENCE_SEEN");
                }
            }
        }
        return Correcto;          
    }  

    // Manejar declaraciones estilo Pascal: "identificador : tipo;" dentro de la seccion var
    public boolean procesaDeclaraPascal(ArrayList<String> datosxProcesar, ArrayList<String> lineaReal, ControlesTotales ctrlTotales, int lineaNumero, BufferedWriter bw)
    {
        // Esperamos al menos: nombre ':' tipo
        if (lineaReal==null || lineaReal.size()<3) return false;
        // formato ejemplo: [i, :, integer, ;]
        String segundo = lineaReal.get(1).trim();
        if (!":".equals(segundo)) return false;

        String nombre = lineaReal.get(0).trim();
        String tipo = lineaReal.get(2).trim();

        if (buscarVariable(ctrlTotales,nombre)){
            reportError(310, lineaNumero, "La variable "+nombre+" ya ha sido declarada", bw);
            return false;
        }
        ControlVariablesDeclaradas agregaVar = new ControlVariablesDeclaradas();
        agregaVar.setNombreVariable(nombre);
        agregaVar.setTipoVariable(tipo);
    ctrlTotales.Variables.add(agregaVar);
        return true;
    }

    public boolean procesaBegin(ArrayList<String> datosxProcesar, ArrayList<String> lineaReal,ControlesTotales ctrlTotales, String rawLine, int lineaNumero, BufferedWriter bw)
    {
        // Sólo validar BEGIN si existe un FOR abierto; si no, ignorar (no validar otras estructuras)
        if (ctrlTotales==null || ctrlTotales.Estructuras==null || ctrlTotales.Estructuras.size()==0){
            return true; // ignorar
        }
        ControlEstructuras last = ctrlTotales.Estructuras.get(ctrlTotales.Estructuras.size()-1);
        if (!"FOR".equals(last.getIniComando())){
            return true; // BEGIN no relacionado con FOR -> ignorar
        }
        int baseIndent=0;
        try{ baseIndent = Integer.parseInt(last.getComando()); }catch(Exception e){ baseIndent=0; }
        int expectedBeginIndent = baseIndent + 4;
        int actualIndent=0;
        for (int i=0;i<rawLine.length();i++){ if (rawLine.charAt(i)!=' ') break; actualIndent++; }
        if (actualIndent != expectedBeginIndent){
            reportError(325, lineaNumero, "Indentacion de BEGIN incorrecta. Se esperaban "+expectedBeginIndent+" espacios.", bw);
            return false;
        }
        // marcar que BEGIN fue visto y ahora esperamos la sentencia
        last.setCierreComando("BEGIN_SEEN");
        return true;
    }

    public boolean procesaEnd(ArrayList<String> datosxProcesar, ArrayList<String> lineaReal,ControlesTotales ctrlTotales, String rawLine, int lineaNumero, BufferedWriter bw)
    {
        // Sólo validar END si existe un FOR abierto; si no, ignorar
        if (ctrlTotales==null || ctrlTotales.Estructuras==null || ctrlTotales.Estructuras.size()==0){
            return true;
        }
        ControlEstructuras last = ctrlTotales.Estructuras.get(ctrlTotales.Estructuras.size()-1);
        if (!"FOR".equals(last.getIniComando())){
            return true;
        }
        if (!"SENTENCE_SEEN".equals(last.getCierreComando())){
            reportError(328, lineaNumero, "Falta la sentencia dentro del FOR antes del END;", bw);
            return false;
        }
        // verificar indentacion de END: debe ser baseIndent+4 y terminar en ;
        int baseIndent=0;
        try{ baseIndent = Integer.parseInt(last.getComando()); }catch(Exception e){ baseIndent=0; }
        int expectedEndIndent = baseIndent + 4;
        int actualIndent=0;
        for (int i=0;i<rawLine.length();i++){ if (rawLine.charAt(i)!=' ') break; actualIndent++; }
        if (actualIndent != expectedEndIndent){
            reportError(329, lineaNumero, "Indentacion de END incorrecta. Se esperaban "+expectedEndIndent+" espacios.", bw);
            return false;
        }
        if (lineaReal.size()==0 || !lineaReal.get(lineaReal.size()-1).equals(";")){
            reportError(330, lineaNumero, "END debe terminar en punto y coma ';'", bw);
            return false;
        }
        // cerrar estructura
        last.setCierreComando("CLOSED");
        return true;
    }

    public boolean procesaInicio(ArrayList<String> datosxProcesar, int lineaNumero, BufferedWriter bw)
    {
        boolean Correcto=true;
        
        System.out.println(Colores.ANSI_CYAN+"-----------------------------------------------"+Colores.ANSI_RESET);
        System.out.println(Colores.ANSI_YELLOW+"               INICIO DETECTADO"+Colores.ANSI_RESET);
        System.out.println(Colores.ANSI_CYAN+"-----------------------------------------------"+Colores.ANSI_RESET);
        System.out.println("");
        
        for (int i=0; i< TabladeSintaxis.SintaxisInicio.length;i++)
        {
            if (datosxProcesar.size()<i+1){
                reportError(309, lineaNumero, "Se omite --> "+TabladeSintaxis.SintaxisInicio[i]+" <--", bw);
                Correcto=false;
            }else{
                if (datosxProcesar.get(i).trim().toString().equals(TabladeSintaxis.SintaxisInicio[i].trim().toString()))
                {}
                else
                {
                    reportError(300, lineaNumero, "La linea presenta un error en posicion "+(i+1)+" en el dato >>> "+datosxProcesar.get(i)+" <<< Se esperaba --> "+TabladeSintaxis.SintaxisInicio[i]+" <--", bw);
                    Correcto=false;
                }
            }
        }
        return Correcto;    
    }   
    
    
    public void encuentraErrorCiclo(ArrayList<String> datosxProcesar, int lineaNumero, BufferedWriter bw){
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
                reportError(300, lineaNumero, "En la estructura del ciclo hace falta "+TabladeSintaxis.SintaxisCiclo[i], bw);
            }
        }
    } 
     
     
}
