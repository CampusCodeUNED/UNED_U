package articulosferreteria;

public class Nodo {
    private Articulo articulo;
    private Nodo siguiente;

    public Nodo(Articulo articulo) {
        this.articulo = articulo;
        this.siguiente = null;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }
}

