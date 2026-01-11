/*
UNIVERSIDAD ESTATAL A DISTANCIA
Curso:Compiladores
Código: 03307
Proyecto #1: Analizador Pascal
Tutor:  CHACÓN CHINCHILLA CARLOS
Grupo: 10
Estudiante: Francisco Campos Sandi
Cédula: 114750560
III Cuatrimestre 2025 */
package PascalAnalizador.Proyecto1;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

// Escribe el archivo "<nombre>-errores.err" cumpliendo: 1) Contenido ORIGINAL
// del .pas, preservado, pero con 4 dígitos al inicio de CADA línea. 2) Al final
// del archivo, una lista de errores en el formato especificado.
public final class EscritorErrores {

    // Genera el archivo de errores.
    public void escribir(File archivoError, Fuente fuente, List<Diagnostico> diagnosticos) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(archivoError, false), StandardCharsets.UTF_8))) {
            // 1) Volcar el código fuente con numeración de 4 dígitos al inicio de CADA línea
            
            // Normalizar el texto primero, reemplazando todos los finales de línea por \n
            String texto = fuente.getTexto().replaceAll("\r\n", "\n");
            
            // Dividir el texto en líneas usando solo \n como separador
            String[] lineas = texto.split("\n", -1);
            
            // Numerar y escribir cada línea
            for (int i = 0; i < lineas.length; i++) {
                bw.write(String.format("%04d %s", i + 1, lineas[i]));
                bw.newLine();
            }
            
            // Si el archivo original terminaba sin nueva línea, la última ya fue escrita
            // 2) Sección de errores al final (si hay)
            if (diagnosticos != null && !diagnosticos.isEmpty()) {
                // Añadir un separador con mensaje sin línea en blanco previa
                bw.write("============== ERRORES ENCONTRADOS ==============");
                bw.newLine();
                
                // Ordenar los diagnósticos por número de línea antes de escribirlos
                diagnosticos.sort((d1, d2) -> Integer.compare(d1.linea, d2.linea));
                
                // Construir todos los errores en un solo String
                StringBuilder errores = new StringBuilder();
                for (Diagnostico d : diagnosticos) {
                    errores.append(d.formatearConLinea());
                    errores.append(System.lineSeparator());
                }
                // Eliminar el último salto de línea si existe
                String contenidoErrores = errores.toString();
                if (contenidoErrores.endsWith(System.lineSeparator())) {
                    contenidoErrores = contenidoErrores.substring(0, contenidoErrores.length() - System.lineSeparator().length());
                }
                // Escribir todo de una vez
                bw.write(contenidoErrores);
            }
        }
    }
}
