/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sesion2compiladores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author cchac
 */
public class Sesion2Compiladores {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        String  nombreArchivo="";
        String linea;
        BufferedReader ArchivoMemoria = null;
        
        ControlesTotales ctrlTotal = new ControlesTotales();
        
        if(args.length>0)
        {
            nombreArchivo=args[0];
            BufferedWriter errorWriter = null;
            try {
                // Abrir archivos usando la codificación Cp1252 para mantener acentos correctamente
                Charset charset = Charset.forName("Cp1252");
                ArchivoMemoria = new BufferedReader(new InputStreamReader(new FileInputStream(nombreArchivo), charset));
                // crear archivo de errores con misma ruta + .err
                errorWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(nombreArchivo + ".err"), charset));
                Analizar.setErrorWriter(errorWriter);
                Analizar realizaAnalisis = new Analizar();

                int lineaNumero = 1;
                while ((linea = ArchivoMemoria.readLine()) != null)
                {
                    realizaAnalisis.AnalizaLinea(linea, ctrlTotal, lineaNumero);
                    lineaNumero++;
                }
            }
            catch (FileNotFoundException ex) {
                System.out.println("Archivo no encontrado!!");
            } catch (IOException ex) {
                System.out.println("Archivo no encontrado o no se pudo abrir!!");
            }
            finally 
            {
              try { if (ArchivoMemoria!=null) ArchivoMemoria.close(); } catch(Exception e) {}
              try { if (errorWriter!=null) errorWriter.close(); } catch(Exception e) {}
              // limpiar todos los objetos
            }           
            // Post-procesar el archivo .err para escribir primero el código numerado y luego la lista de errores
            try {
                File errFile = new File(nombreArchivo + ".err");
                if (errFile.exists()) {
                    // leer código fuente
                    List<String> srcLines = new ArrayList<>();
                    Charset charset = Charset.forName("Cp1252");
                    try (BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(nombreArchivo), charset))) {
                        String l;
                        while ((l = r.readLine()) != null) srcLines.add(l);
                    }
                    // leer errores actuales
                    List<String> rawErrors = new ArrayList<>();
                    try (BufferedReader er = new BufferedReader(new InputStreamReader(new FileInputStream(errFile), charset))) {
                        String e;
                        while ((e = er.readLine()) != null) rawErrors.add(e);
                    }
                    // reescribir .err con el formato solicitado
                    try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(errFile, false), charset))) {
                        for (int i = 0; i < srcLines.size(); i++) {
                            out.write(String.format("%04d %s", i + 1, srcLines.get(i)));
                            out.newLine();
                        }
                        out.newLine();
                        out.write("============== ERRORES ENCONTRADOS ==============");
                        out.newLine();
                        Pattern p = Pattern.compile("^Error\\s+(\\d+)\\.\\s*[Ll]inea\\s+(\\d+)\\.\\s*(.*)$");
                        for (String re : rawErrors) {
                            Matcher m = p.matcher(re);
                            if (m.find()) {
                                String code = m.group(1);
                                int ln = 0;
                                try { ln = Integer.parseInt(m.group(2)); } catch (Exception ex) { ln = 0; }
                                String msg = m.group(3);
                                out.write(String.format("Error %s. Línea %04d. %s", code, ln, msg));
                            } else {
                                out.write(re);
                            }
                            out.newLine();
                        }
                        out.flush();
                    }
                }
            } catch (Exception ex) {
                System.out.println("No se pudo reescribir archivo .err: " + ex.getMessage());
            }
        }
        else
        {
            System.out.println("No se indicó nombre de archivo a analizar!!!");        
        }
    }
    
}
