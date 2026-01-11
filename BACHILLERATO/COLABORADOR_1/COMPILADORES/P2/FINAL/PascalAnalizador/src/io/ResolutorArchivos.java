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

import java.io.File;
import java.util.Locale;

public final class ResolutorArchivos {
    // Verifica si el nombre tiene extensión .pas (mayúsculas o minúsculas)
    private static boolean tieneExtensionPas(String nombre) {
        int punto = nombre.lastIndexOf('.');
        if (punto < 0) return false;
        String extension = nombre.substring(punto + 1);
        return "pas".equalsIgnoreCase(extension);
    }
    // Resuelve el archivo .pas en el directorio actual
    public File resolverEnDirectorio(String nombreSolicitado) {
        if (nombreSolicitado == null || nombreSolicitado.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe indicar un nombre de archivo .pas.");
        }
        if (!tieneExtensionPas(nombreSolicitado)) {
            throw new IllegalArgumentException("La extensión debe ser .pas (se recibió: " + nombreSolicitado + ").");
        }
        if (nombreSolicitado.contains("/") || nombreSolicitado.contains("\\")) {
            throw new IllegalArgumentException(
                "No se admiten rutas. Solo el NOMBRE del archivo .pas ubicado junto al .jar."
            );
        }
        File directorioTrabajo = new File(".").getAbsoluteFile();
        File archivoPas = new File(directorioTrabajo, nombreSolicitado);
        if (!archivoPas.exists() || !archivoPas.isFile()) {
            throw new IllegalArgumentException("No se encontró el archivo en el directorio actual: " + nombreSolicitado);
        }
        return archivoPas;
    }
    
    // Genera el nombre del archivo de errores basado en el archivo .pas
    public String nombreErrores(File archivoPas) {
        String base = archivoPas.getName();
        int punto = base.lastIndexOf('.');
        String raiz = (punto >= 0 ? base.substring(0, punto) : base);
        return raiz + "-errores.err";
    }
    // Genera un nombre por defecto para el archivo de errores si no se proporciona uno
    public String NombreErroresDefecto(String nombreSolicitado) {
        int punto = nombreSolicitado.toLowerCase(Locale.ROOT).lastIndexOf(".pas");
        String raiz = (punto >= 0 ? nombreSolicitado.substring(0, punto) : nombreSolicitado);
        if (raiz.isEmpty()) raiz = "output";
        return raiz + "-errores.err";
    }
}