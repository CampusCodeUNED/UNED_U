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

public class Nodo {

    private Impresora impresora;
    private Nodo izquierda;
    private Nodo derecha;

    public Nodo(Impresora impresora) {
        this.impresora = impresora;
        this.izquierda = null;
        this.derecha = null;
    }

    // Getters y Setters
    public Impresora getImpresora() {
        return impresora;
    }

    public void setImpresora(Impresora impresora) {
        this.impresora = impresora;
    }

    public Nodo getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(Nodo izquierda) {
        this.izquierda = izquierda;
    }

    public Nodo getDerecha() {
        return derecha;
    }

    public void setDerecha(Nodo derecha) {
        this.derecha = derecha;
    }
}
