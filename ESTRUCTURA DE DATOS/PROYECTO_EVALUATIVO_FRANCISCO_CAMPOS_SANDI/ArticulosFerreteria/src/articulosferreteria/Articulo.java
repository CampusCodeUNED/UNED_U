/*
UNIVERSIDAD ESTATAL A DISTANCIA
Curso:Estructuras de datos
Código: 0825
Proyecto evaluativo:  Artículos ferretería
Tutor:Steve Brenes Torres
Grupo:03
Estudiante: Francisco Campos Sandi
Cédula: 114750560
II Cuatrimestre 2024
*/


package articulosferreteria;

public class Articulo {
     // Atributos
    private int id;
    private String nombre;
    private double precio;
    // Constructor para inicializar
    public Articulo(int id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }
    // Métodos getter
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nombre: " + nombre + ", Precio: " + precio;
    }
}

