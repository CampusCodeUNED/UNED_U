/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sesion2compiladores;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
            try {
                ArchivoMemoria = new BufferedReader(new FileReader(nombreArchivo));
                Analizar realizaAnalisis = new Analizar();
                
                while ((linea = ArchivoMemoria.readLine()) != null)
                {
                    realizaAnalisis.AnalizaLinea(linea, ctrlTotal);
                }
            }
            catch (FileNotFoundException ex) {                
                System.out.println("Archivo no encontrado!!");
            } catch (IOException ex) {
                System.out.println("Archivo no encontrado o no se pudo abrir!!");                
            }
            finally 
            {
              ArchivoMemoria.close();
              // limpiar todos los objetos
            }           
        }
        else
        {
            System.out.println("No se indicó nombre de archivo a analizar!!!");        
        }
    }
    
}
