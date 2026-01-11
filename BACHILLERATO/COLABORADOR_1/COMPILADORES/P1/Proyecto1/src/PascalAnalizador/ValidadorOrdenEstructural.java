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

import java.util.*;

public final class ValidadorOrdenEstructural {

    // Códigos de error para orden estructural
    public static final int E_ORDEN_ESTRUCTURAL = 200;
    public static final int E_VAR_DESPUES_BEGIN = 201;

    public static final class Resultado {

        public final List<Diagnostico> diags;
        public final int lineaProgram, lineaUses, lineaConst, lineaVar, lineaBegin;

        public Resultado(List<Diagnostico> diags, int lineaProgram, int lineaUses,
                int lineaConst, int lineaVar, int lineaBegin) {
            this.diags = diags;
            this.lineaProgram = lineaProgram;
            this.lineaUses = lineaUses;
            this.lineaConst = lineaConst;
            this.lineaVar = lineaVar;
            this.lineaBegin = lineaBegin;
        }
    }

    // Analiza el orden estructural del programa Pascal y devuelve un objeto con los diagnósticos y las líneas de cada sección
    public Resultado analizar(Fuente fuente) {
        List<Diagnostico> diags = new ArrayList<>();
        String texto = fuente.getTexto();
        String[] lineas = texto.split("\\r?\\n", -1);
        int n = lineas.length;
        int lProgram = -1, lUses = -1, lConst = -1, lVar = -1, lBegin = -1;
        // Encontrar líneas de cada sección
        for (int i = 0; i < n; i++) {
            String linea = lineas[i];
            String minuscula = linea.toLowerCase(Locale.ROOT);
            int numeroLinea = i + 1;
            if (lProgram < 0 && minuscula.contains("program")) {
                lProgram = numeroLinea;
            }
            if (lUses < 0 && minuscula.contains("uses")) {
                lUses = numeroLinea;
            }
            if (lConst < 0 && minuscula.contains("const")) {
                lConst = numeroLinea;
            }
            if (lVar < 0 && minuscula.matches("\\s*var\\b.*")) {
                lVar = numeroLinea;
            }
            if (lBegin < 0 && minuscula.contains("begin")) {
                lBegin = numeroLinea;
            }
            // Validar que const no aparezca después de begin
            if (lBegin > 0 && minuscula.trim().startsWith("const")) {
                if (numeroLinea > lBegin) {
                    diags.add(new Diagnostico(E_ORDEN_ESTRUCTURAL, numeroLinea, null,
                            "const no puede estar después de begin"));
                }
            }
        }

        // Validar orden estructural (sin incluir var, que se valida en VarSectionParser)
        if (lVar >= 0) {
            if (lConst > 0 && lConst > lBegin) {
                diags.add(new Diagnostico(E_ORDEN_ESTRUCTURAL, lConst, null,
                        "const no puede estar después de begin"));
            }
        }
        return new Resultado(diags, lProgram, lUses, lConst, lVar, lBegin);
    }
}
