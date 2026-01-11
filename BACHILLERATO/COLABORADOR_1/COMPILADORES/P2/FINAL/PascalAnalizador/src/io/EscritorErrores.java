/*
UNIVERSIDAD ESTATAL A DISTANCIA
Curso:Compiladores
Código: 03307
Proyecto #2: Analizador Pascal
Tutor:  CHACÓN CHINCHILLA CARLOS
Grupo: 10
Estudiante: Francisco Campos Sandi
Cédula: 114750560
III Cuatrimestre 2025 */
package io;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import modelo.Diagnostico;
import modelo.Fuente;
/**
 * Escribe el archivo "nombre-errores.err" cumpliendo:
 * 1) Contenido ORIGINAL del .pas, preservado, pero con 4 dígitos al inicio de CADA línea.
 * 2) Al final del archivo, una lista de errores en el formato (SIN línea en blanco antes):
 */
public final class EscritorErrores {
    
    //Genera el archivo de errores.
    public void write(File archivoError, Fuente fuente, List<Diagnostico> diagnosticos) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(archivoError, false), StandardCharsets.UTF_8))) {
            // 1) Volcar el código fuente con numeración de 4 dígitos al inicio de CADA línea
            String texto = fuente.getText();
            int numeroLinea = 1;
            int inicio = 0;
            
            for (int i = 0; i < texto.length(); i++) {
                if (texto.charAt(i) == '\n') {
                    // línea [inicio, i] 
                    String linea = texto.substring(inicio, i); // sin \n
                    
                    if (linea.endsWith("\r")) {
                        linea = linea.substring(0, linea.length() - 1);
                    }
                    
                    bw.write(String.format("%04d %s", numeroLinea, linea));
                    bw.newLine(); // escribir \n
                    numeroLinea++;
                    inicio = i + 1;
                }
            }
            
            // última línea 
            if (inicio < texto.length()) {
                String cola = texto.substring(inicio);
                if (!cola.isEmpty()) {
                    // Eliminar \r si existe
                    if (cola.endsWith("\r")) {
                        cola = cola.substring(0, cola.length() - 1);
                    }
                    bw.write(String.format("%04d %s", numeroLinea, cola));
                }
            }
            
            if (diagnosticos != null && !diagnosticos.isEmpty()) {
                bw.newLine(); // Terminar la última línea del código

                // Ordenar diagnósticos por número de línea ascendente.
                // Diagnósticos con linea == 0 (sin línea) se colocan al final.
                List<Diagnostico> copia = new ArrayList<>(diagnosticos);
                Collections.sort(copia, new Comparator<Diagnostico>() {
                    @Override
                    public int compare(Diagnostico d1, Diagnostico d2) {
                        int l1 = (d1.linea <= 0) ? Integer.MAX_VALUE : d1.linea;
                        int l2 = (d2.linea <= 0) ? Integer.MAX_VALUE : d2.linea;
                        if (l1 != l2) return Integer.compare(l1, l2);
                        // Si mismas líneas, ordenar por código de error
                        return Integer.compare(d1.codigo, d2.codigo);
                    }
                });

                for (Diagnostico d : copia) {
                    bw.write(d.formatWithLine());
                    bw.newLine();
                }
            } else {
                // Si no hay errores, terminar con newLine
                bw.newLine();
            }
        }
    }
}