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

public final class Diagnostico {
    
    public final int codigo;     // Ej: 200
    public final int linea;     // 1-based; 0 si no aplica
    public final Integer columna; // opcional (puede ser null)
    public final String mensaje;
    
    public Diagnostico(int codigo, int linea, Integer columna, String mensaje) {
        this.codigo = codigo;
        this.linea = linea;
        this.columna = columna;
        this.mensaje = mensaje;
    }
    
    /** Formato requerido (con opción de línea). */
    public String formatWithLine() {
        // Línea con 4 dígitos, si linea > 0
        String lineaFormateada = (linea > 0) ? String.format(" Línea %04d.", linea) : "";
        return String.format("Error %d.%s %s", codigo, lineaFormateada, mensaje);
    }
}