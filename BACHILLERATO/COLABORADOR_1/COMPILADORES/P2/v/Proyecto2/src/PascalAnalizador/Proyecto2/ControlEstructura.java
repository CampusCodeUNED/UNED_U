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
package PascalAnalizador.Proyecto2;

/**
 * Clase para control de estructuras de control en Pascal (if, for, repeat).
 * Permite rastrear el estado y ubicación de cada estructura encontrada.
 */
public class ControlEstructura {
    // Tipo de estructura
    private final String estructura;        // "if", "for", "repeat"
    private final int lineaEstructura;      // Línea donde inicia la estructura
    private String apertura;          // "begin"
    private int lineaApertura;        // Línea del begin
    private String cierra;            // "end"
    private int lineaCierre;          // Línea del end
    private String finEstructura;     // "until" para repeat, null para otros
    private int lineaFin;            // Línea del until o del end final
    
    // Constructor
    public ControlEstructura(String estructura, int lineaEstructura) {
        this.estructura = estructura;
        this.lineaEstructura = lineaEstructura;
        this.apertura = "";
        this.lineaApertura = -1;
        this.cierra = "";
        this.lineaCierre = -1;
        this.finEstructura = "";
        this.lineaFin = -1;
    }
    
    // Getters y setters
    public String getEstructura() {
        return estructura;
    }
    
    public int getLineaEstructura() {
        return lineaEstructura;
    }
    
    public void setApertura(String apertura, int linea) {
        this.apertura = apertura;
        this.lineaApertura = linea;
    }
    
    public String getApertura() {
        return apertura;
    }
    
    public int getLineaApertura() {
        return lineaApertura;
    }
    
    public void setCierra(String cierra, int linea) {
        this.cierra = cierra;
        this.lineaCierre = linea;
    }
    
    public String getCierra() {
        return cierra;
    }
    
    public int getLineaCierre() {
        return lineaCierre;
    }
    
    public void setFinEstructura(String fin, int linea) {
        this.finEstructura = fin;
        this.lineaFin = linea;
    }
    
    public String getFinEstructura() {
        return finEstructura;
    }
    
    public int getLineaFin() {
        return lineaFin;
    }
    
    /**
     * Verifica si la estructura está completa
     * @return true si todos los elementos requeridos están presentes
     */
    public boolean estaCompleta() {
        if (estructura == null || lineaEstructura == -1) {
            return false;
        }
        
        // Validación básica para todas las estructuras
        if (apertura.isEmpty() || lineaApertura == -1 ||
            cierra.isEmpty() || lineaCierre == -1) {
            return false;
        }
        
        // Validación específica para REPEAT
        if (estructura.equalsIgnoreCase("repeat")) {
            return !finEstructura.isEmpty() && lineaFin != -1;
        }
        
        return true;
    }
    
    @Override
    public String toString() {
        return String.format("Estructura: %s (línea %d)%n" +
                           "Apertura: %s (línea %d)%n" +
                           "Cierre: %s (línea %d)%n" +
                           "Fin: %s (línea %d)",
                           estructura, lineaEstructura,
                           apertura, lineaApertura,
                           cierra, lineaCierre,
                           finEstructura, lineaFin);
    }
}