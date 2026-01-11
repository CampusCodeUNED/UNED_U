package sesion2compiladores;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Logger simple para escribir errores en el archivo `calendario-errores.err`.
 * Se abre en modo append y se asegura que cada llamada quede en una nueva línea.
 */
public class ErrorLogger {
    private static final String DEFAULT_FILE = "calendario-errores.err";
    private static final Object lock = new Object();
    private static final String HEADER = "============== ERRORES ENCONTRADOS ==============";

    /**
     * Registra un error ya formateado (lo escribe tal cual en el archivo).
     */
    public static void log(String texto) {
        writeLineWithHeader(texto);
    }

    /**
     * Registra un error con código, línea y descripción.
     * Formato: "Error <codigo>. Línea <linea>. <descripcion>"
     * Si linea es menor que 0 se escribe "Línea N/A".
     */
    public static void logFormatted(int codigo, int linea, String descripcion) {
        String lineaStr;
        if (linea >= 0) {
            lineaStr = String.format("%04d", linea);
        } else {
            lineaStr = "N/A";
        }
        String texto = String.format("Error %d. Línea %s. %s", codigo, lineaStr, descripcion);
        writeLineWithHeader(texto);
    }

    private static void writeLineWithHeader(String texto) {
        synchronized (lock) {
            File f = new File(DEFAULT_FILE);
            PrintWriter out = null;
            try {
                // crear path si no existe
                File parent = f.getAbsoluteFile().getParentFile();
                if (parent != null && !parent.exists()) {
                    parent.mkdirs();
                }
                boolean needHeader = true;
                if (f.exists()) {
                    // comprobar si ya contiene el header
                    try (java.io.BufferedReader r = new java.io.BufferedReader(new java.io.FileReader(f))) {
                        String line;
                        while ((line = r.readLine()) != null) {
                            if (line.trim().equals(HEADER)) {
                                needHeader = false;
                                break;
                            }
                        }
                    }
                }

                out = new PrintWriter(new BufferedWriter(new FileWriter(f, true)));
                if (needHeader) {
                    out.println();
                    out.println(HEADER);
                }
                out.println(texto);
            } catch (IOException ex) {
                // En caso de fallo al escribir, intentar escribir por consola (no lanzar excepción para no romper flujo)
                System.err.println("No se pudo escribir en " + DEFAULT_FILE + ": " + ex.getMessage());
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        }
    }
}
