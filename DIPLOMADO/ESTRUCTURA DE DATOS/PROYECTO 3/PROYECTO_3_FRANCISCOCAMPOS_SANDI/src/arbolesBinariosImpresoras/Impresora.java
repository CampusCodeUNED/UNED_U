/*
UNIVERSIDAD ESTATAL A DISTANCIA
Curso:Estructuras de datos
Código: 0825
Proyecto #3: Gestión de Árbol Binario de Impresoras
Tutor:Steve Brenes Torres
Grupo:03
Estudiante: Francisco Campos Sandi
Cédula: 114750560
II Cuatrimestre 2024
 */
package arbolesBinariosImpresoras;

public class Impresora {

    private int id;
    private String marca;
    private String tipo;
    private double peso;

    public Impresora(int id, String marca, String tipo, double peso) {
        this.id = id;
        this.marca = marca;
        this.tipo = tipo;
        this.peso = peso;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Marca: " + marca + ", Tipo: " + tipo + ", Peso: " + peso;
    }
}
