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

import java.util.ArrayList;
import java.util.List;

// Clase que representa el contenido de un archivo fuente de Pascal
public final class Fuente {

    private final String texto;
    private final int[] indicesInicioLinea;

    // Constructor que recibe el texto del archivo fuente
    public Fuente(String texto) {
        this.texto = texto;
        this.indicesInicioLinea = calcularIniciosLinea(texto);
    }

    // Devuelve el texto completo del archivo
    public String getTexto() {
        return texto;
    }

    // Devuelve la longitud total del texto
    public int length() {
        return texto.length();
    }

    // Devuelve la cantidad de líneas en el texto
    public int getCantidadLineas() {
        return indicesInicioLinea.length;
    }

    // Devuelve el índice de inicio de una línea específica
    public int getInicioLinea(int indiceLinea0) {
        return indicesInicioLinea[indiceLinea0];
    }

    // Calcula los índices de inicio de todas las líneas en el texto
    private static int[] calcularIniciosLinea(String s) {
        List<Integer> inicios = new ArrayList<>();
        inicios.add(0);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\n') {
                inicios.add(i + 1);
            }
        }
        return inicios.stream().mapToInt(Integer::intValue).toArray();
    }
}
