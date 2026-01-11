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

import java.util.ArrayList;
import java.util.List;

// Clase que mantiene un conjunto de pares clave-valor
public final class ParClaveValor {

    private final List<String> claves;
    private final List<Integer> valores;

    public ParClaveValor() {
        claves = new ArrayList<>();
        valores = new ArrayList<>();
    }

    // Agrega un par clave-valor. Si la clave ya existe, se sobrescribe el valor.
    public void poner(String clave, Integer valor) {
        int indice = buscarIndice(clave);
        if (indice >= 0) {
            // Si ya existe, actualizar el valor
            valores.set(indice, valor);
        } else {
            // Si no existe, agregar nuevo par
            claves.add(clave);
            valores.add(valor);
        }
    }

    // Obtiene el valor de una clave
    public Integer obtener(String clave) {
        int indice = buscarIndice(clave);
        if (indice >= 0) {
            return valores.get(indice);
        }
        return null;
    }

    // Verifica si existe una clave en la colección
    public boolean contieneClave(String clave) {
        return buscarIndice(clave) >= 0;
    }

    // Obtiene todas las claves como una List
    public List<String> claves() {
        return new ArrayList<>(claves);
    }

    // Agrega todos los pares clave-valor de otro ParClaveValor a este
    public void ponerTodo(ParClaveValor otro) {
        for (int i = 0; i < otro.claves.size(); i++) {
            poner(otro.claves.get(i), otro.valores.get(i));
        }
    }

    // Busca el índice de una clave en la lista de claves
    private int buscarIndice(String clave) {
        for (int i = 0; i < claves.size(); i++) {
            if (claves.get(i).equals(clave)) {
                return i;
            }
        }
        return -1;
    }
}
