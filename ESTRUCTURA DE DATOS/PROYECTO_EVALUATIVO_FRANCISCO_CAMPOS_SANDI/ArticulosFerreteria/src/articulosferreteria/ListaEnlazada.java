package articulosferreteria;

public class ListaEnlazada {

    private Nodo cabeza;

    public ListaEnlazada() {
        this.cabeza = null;
    }

    // Inserta un nuevo artículo en la lista de manera ordenada por id
    public void insertar(Articulo articulo) {
        Nodo nuevoNodo = new Nodo(articulo);
        if (cabeza == null || cabeza.getArticulo().getId() > articulo.getId()) {
            nuevoNodo.setSiguiente(cabeza);
            cabeza = nuevoNodo;
        } else {
            Nodo actual = cabeza;
            while (actual.getSiguiente() != null && actual.getSiguiente().getArticulo().getId() < articulo.getId()) {
                actual = actual.getSiguiente();
            }
            nuevoNodo.setSiguiente(actual.getSiguiente());
            actual.setSiguiente(nuevoNodo);
        }
    }

    // Busca un artículo por su id en la lista
    public Articulo buscarPorId(int id) {
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.getArticulo().getId() == id) {
                return actual.getArticulo();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }
    // Lista todos los artículos en la lista

    public String listarArticulos() {
        StringBuilder sb = new StringBuilder();
        Nodo actual = cabeza;
        while (actual != null) {
            sb.append(actual.getArticulo().toString()).append("\n");
            actual = actual.getSiguiente();
        }
        return sb.toString();
    }

    // Obtiene la cabeza de la lista
    public Nodo getCabeza() {
        return cabeza;
    }
}
