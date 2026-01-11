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
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import modelo.Fuente;

public final class LectorFuente {
    //Carga el archivo fuente .pas en un objeto Fuente.
    public Fuente load(File file) throws Exception {
        byte[] bytes;
        try (FileInputStream fis = new FileInputStream(file)) {
            bytes = fis.readAllBytes();
        }
        // Convertir bytes a String usando UTF-8
        String text = new String(bytes, StandardCharsets.UTF_8);
        return new Fuente(text);
    }
}
