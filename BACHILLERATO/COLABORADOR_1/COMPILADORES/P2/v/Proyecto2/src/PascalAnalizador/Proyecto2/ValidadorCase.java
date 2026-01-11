/*
UNIVERSIDAD ESTATAL A DISTANCIA
Curso:Compiladores
Código: 03307
Proyecto #2: Analizador Pascal Extendido
Tutor:  CHACÓN CHINCHILLA CARLOS
Grupo: 10
Estudiante: Francisco Campos Sandi
Cédula: 114750560
III Cuatrimestre 2025 */
package PascalAnalizador.Proyecto2;

import PascalAnalizador.Proyecto1.Diagnostico;
import PascalAnalizador.Proyecto1.Fuente;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public final class ValidadorCase {
    
    public static final int E_CASE_END_TABULACION = 322;
    public static final int E_CASE_END_PUNTO_COMA = 326;

    public List<Diagnostico> verificar(Fuente fuente) {
        List<Diagnostico> diags = new ArrayList<>();
        String[] lineas = fuente.getTexto().split("\\r?\\n", -1);
        Stack<Integer> pilaCase = new Stack<>();

        // Recorrer líneas buscando 'case' y sus correspondientes 'end'
        for (int i = 0; i < lineas.length; i++) {
            String lineaActual = quitarNumeroLinea(lineas[i]);
            String lineaTrim = lineaActual.trim().toLowerCase();

            // Detectar case
            if (lineaTrim.startsWith("case ")) {
                int espaciosCase = contarEspaciosIniciales(lineaActual);
                pilaCase.push(espaciosCase);
            }
            // Detectar end que cierra case
            else if (lineaTrim.equals("end") || lineaTrim.equals("end;")) {
                if (!pilaCase.isEmpty()) {
                    int espaciosCase = pilaCase.pop();
                    int espaciosEnd = contarEspaciosIniciales(lineaActual);

                    // Validar tabulación
                    if (espaciosEnd != espaciosCase) {
                        diags.add(new Diagnostico(E_CASE_END_TABULACION, i + 1, null,
                            "La línea 'end' del case debe tener la misma tabulación que el case"));
                    }

                    // Validar punto y coma
                    if (!lineaTrim.endsWith(";")) {
                        diags.add(new Diagnostico(E_CASE_END_PUNTO_COMA, i + 1, null,
                            "El end que cierra un case debe terminar con punto y coma"));
                    }
                }
            }
        }

        return diags;
    }

    private static String quitarNumeroLinea(String linea) {
        if (linea == null || linea.length() < 5) {
            return linea;
        }
        if (linea.matches("^\\d{4} .*")) {
            return linea.substring(5);
        }
        return linea;
    }

    private static int contarEspaciosIniciales(String linea) {
        int count = 0;
        for (char c : linea.toCharArray()) {
            if (c == ' ') count++;
            else if (c == '\t') count += 4;
            else break;
        }
        return count;
    }
}