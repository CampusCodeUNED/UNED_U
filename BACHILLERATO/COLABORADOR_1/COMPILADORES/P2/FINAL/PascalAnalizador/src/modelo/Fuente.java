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
package modelo;

import java.util.ArrayList;
import java.util.List;

public final class Fuente {

    private final String texto;
    private final int[] indicesInicioLinea;
    // Constructor que recibe el texto fuente y calcula los inicios de línea
    public Fuente(String texto) {
        this.texto = texto;
        this.indicesInicioLinea = calcularIniciosLinea(texto);
    }
    // Obtiene el texto fuente completo
    public String getText() {
        return texto;
    }
    
    // Obtiene la longitud del texto
    public int length() {
        return texto.length();
    }
    
    // Obtiene la cantidad de líneas en el texto
    public int getLineCount() {
        return indicesInicioLinea.length;
    }
     // Obtiene el índice de inicio de la línea dada (0-based)
    public int getLineStart(int indiceLinea0) {
        return indicesInicioLinea[indiceLinea0];
    }
    // Calcula los índices de inicio de cada línea en el texto
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
