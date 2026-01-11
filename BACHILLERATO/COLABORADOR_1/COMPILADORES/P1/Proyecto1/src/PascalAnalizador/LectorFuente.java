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

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

// Clase para leer archivos y cargarlos como objetos Fuente
public final class LectorFuente {

    // Carga un archivo desde el sistema de archivos y lo convierte en un objeto Fuente
    public Fuente cargar(File file) throws Exception {
        byte[] bytes;
        try (FileInputStream fis = new FileInputStream(file)) {
            bytes = fis.readAllBytes();
        }

        String text = new String(bytes, StandardCharsets.UTF_8);
        return new Fuente(text);
    }
}
