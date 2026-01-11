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
package PascalAnalizador;

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
            String texto = fuente.getTexto();
            int numeroLinea = 1;
            int inicio = 0;
            for (int i = 0; i < texto.length(); i++) {
                if (texto.charAt(i) == '\n') {
                    // línea [inicio, i] inclusive \n
                    String linea = texto.substring(inicio, i); // sin \n
                    bw.write(String.format("%04d %s", numeroLinea, linea));
                    bw.newLine(); // escribir \n
                    numeroLinea++;
                    inicio = i + 1;
                }
            }
            // última línea (si el archivo no termina con \n)
            if (inicio <= texto.length()) {
                String cola = texto.substring(inicio);
                if (!cola.isEmpty()) {
                    bw.write(String.format("%04d %s", numeroLinea, cola));
                    bw.newLine();
                } else if (texto.endsWith("\n")) {
                    // archivo terminaba con \n exacto; ya numerado
                }
            }
            // 2) Sección de errores al final (si hay)
            if (diagnosticos != null && !diagnosticos.isEmpty()) {
                bw.newLine();
                for (Diagnostico d : diagnosticos) {
                    bw.write(d.formatearConLinea());
                    bw.newLine();
                }
            }
        }
    }
}
