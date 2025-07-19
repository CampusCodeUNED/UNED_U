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

/**
 * Clase que representa un Árbol Binario de Búsqueda (ABB). Proporciona métodos
 * para insertar, eliminar, buscar y recorrer nodos en el árbol.
 */
public class ArbolBinarioBusqueda {

    // Nodo raíz del árbol binario de búsqueda
    private Nodo raiz;

    /**
     * Constructor de la clase. Inicializa el árbol vacío.
     */
    public ArbolBinarioBusqueda() {
        raiz = null;
    }

    /**
     * Devuelve el nodo raíz del árbol.
     *
     * @return Nodo raíz del árbol
     */
    public Nodo getRaiz() {
        return raiz;
    }

    /**
     * Inserta una nueva impresora en el árbol.
     *
     * @param impresora Objeto Impresora que se desea insertar
     */
    public void insertar(Impresora impresora) {
        raiz = insertarRecursivo(raiz, impresora);
    }

    /**
     * Método recursivo para insertar una impresora en el árbol.
     *
     * @param actual Nodo actual en el recorrido de inserción
     * @param impresora Objeto Impresora que se desea insertar
     * @return Nodo actualizado después de la inserción
     */
    private Nodo insertarRecursivo(Nodo actual, Impresora impresora) {
        if (actual == null) {
            return new Nodo(impresora);
        }

        if (impresora.getId() < actual.getImpresora().getId()) {
            actual.setIzquierda(insertarRecursivo(actual.getIzquierda(), impresora));
        } else if (impresora.getId() > actual.getImpresora().getId()) {
            actual.setDerecha(insertarRecursivo(actual.getDerecha(), impresora));
        } else {
            throw new IllegalArgumentException("El ID ya existe en el árbol");
        }

        return actual;
    }

    /**
     * Elimina un nodo con el ID especificado del árbol.
     *
     * @param id ID del nodo a eliminar
     * @return Mensaje que indica el resultado de la eliminación
     */
    public String eliminar(int id) {
        boolean[] errorMensaje = new boolean[1];
        errorMensaje[0] = false;
        raiz = eliminarRecursivo(raiz, id, errorMensaje);

        // Retorna un mensaje basado en el resultado
        if (errorMensaje[0]) {
            return "No se puede eliminar el nodo porque tiene dos hijos.";
        } else {
            return "Nodo eliminado correctamente.";
        }
    }

    /**
     * Método recursivo para eliminar un nodo con el ID especificado.
     *
     * @param actual Nodo actual en el recorrido de eliminación
     * @param id ID del nodo a eliminar
     * @param errorMensaje Array de tamaño 1 que indica si hubo un error
     * @return Nodo actualizado después de la eliminación
     */
    private Nodo eliminarRecursivo(Nodo actual, int id, boolean[] errorMensaje) {
        if (actual == null) {
            return null; // Nodo no encontrado
        }

        if (id == actual.getImpresora().getId()) {
            // Caso 1: Nodo sin hijos (hoja)
            if (actual.getIzquierda() == null && actual.getDerecha() == null) {
                return null;
            }

            // Caso 2: Nodo con un solo hijo
            if (actual.getIzquierda() == null) {
                return actual.getDerecha();
            }
            if (actual.getDerecha() == null) {
                return actual.getIzquierda();
            }

            // Caso 3: Nodo con dos hijos
            errorMensaje[0] = true; // Indica que hay un problema
            return actual;
        }

        if (id < actual.getImpresora().getId()) {
            actual.setIzquierda(eliminarRecursivo(actual.getIzquierda(), id, errorMensaje));
        } else {
            actual.setDerecha(eliminarRecursivo(actual.getDerecha(), id, errorMensaje));
        }

        return actual;
    }

    /**
     * Busca una impresora con el ID especificado en el árbol.
     *
     * @param id ID de la impresora a buscar
     * @return Objeto Impresora correspondiente al ID, o null si no se encuentra
     */
    public Impresora buscar(int id) {
        return buscarRecursivo(raiz, id);
    }

    /**
     * Método recursivo para buscar una impresora en el árbol.
     *
     * @param actual Nodo actual en el recorrido de búsqueda
     * @param id ID de la impresora a buscar
     * @return Objeto Impresora correspondiente al ID, o null si no se encuentra
     */
    private Impresora buscarRecursivo(Nodo actual, int id) {
        if (actual == null) {
            return null;
        }

        if (id == actual.getImpresora().getId()) {
            return actual.getImpresora();
        }

        return id < actual.getImpresora().getId() ? buscarRecursivo(actual.getIzquierda(), id) : buscarRecursivo(actual.getDerecha(), id);
    }

    /**
     * Realiza un recorrido en pre-orden del árbol.
     *
     * @return Cadena con los IDs de los nodos en pre-orden separados por
     * guiones
     */
    public String preOrden() {
        StringBuilder recorrido = new StringBuilder();
        preOrdenRecursivo(raiz, recorrido);
        return recorrido.toString().trim().replace(" ", "-");
    }

    /**
     * Método recursivo para realizar un recorrido en pre-orden del árbol.
     *
     * @param nodo Nodo actual en el recorrido
     * @param recorrido StringBuilder para construir la cadena de recorrido
     */
    private void preOrdenRecursivo(Nodo nodo, StringBuilder recorrido) {
        if (nodo != null) {
            recorrido.append(nodo.getImpresora().getId()).append(" ");
            preOrdenRecursivo(nodo.getIzquierda(), recorrido);
            preOrdenRecursivo(nodo.getDerecha(), recorrido);
        }
    }

    /**
     * Realiza un recorrido en in-orden del árbol.
     *
     * @return Cadena con los IDs de los nodos en in-orden separados por guiones
     */
    public String inOrden() {
        StringBuilder recorrido = new StringBuilder();
        inOrdenRecursivo(raiz, recorrido);
        return recorrido.toString().trim().replace(" ", "-");
    }

    /**
     * Método recursivo para realizar un recorrido en in-orden del árbol.
     *
     * @param nodo Nodo actual en el recorrido
     * @param recorrido StringBuilder para construir la cadena de recorrido
     */
    private void inOrdenRecursivo(Nodo nodo, StringBuilder recorrido) {
        if (nodo != null) {
            inOrdenRecursivo(nodo.getIzquierda(), recorrido);
            recorrido.append(nodo.getImpresora().getId()).append(" ");
            inOrdenRecursivo(nodo.getDerecha(), recorrido);
        }
    }

    /**
     * Realiza un recorrido en post-orden del árbol.
     *
     * @return Cadena con los IDs de los nodos en post-orden separados por
     * guiones
     */
    public String postOrden() {
        StringBuilder recorrido = new StringBuilder();
        postOrdenRecursivo(raiz, recorrido);
        return recorrido.toString().trim().replace(" ", "-");
    }

    /**
     * Método recursivo para realizar un recorrido en post-orden del árbol.
     *
     * @param nodo Nodo actual en el recorrido
     * @param recorrido StringBuilder para construir la cadena de recorrido
     */
    private void postOrdenRecursivo(Nodo nodo, StringBuilder recorrido) {
        if (nodo != null) {
            postOrdenRecursivo(nodo.getIzquierda(), recorrido);
            postOrdenRecursivo(nodo.getDerecha(), recorrido);
            recorrido.append(nodo.getImpresora().getId()).append(" ");
        }
    }
}
