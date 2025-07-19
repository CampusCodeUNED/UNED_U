/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ejemplolistasdoblementeenlazadas;

/**
 *
 * @author javalver
 */
public class EjemploListasDoblementeEnlazadas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        DoublyLinkedList<Integer> lista = new DoublyLinkedList();
        DoublyLinkedListIterator<Integer> elem;
        elem = lista.zeroth();
        
//        p.current = lista.first().current;
//        Persona pers = new Persona();
//        pers.setId("108250553");
//        pers.setPrimerApellido("Valverde");
//        pers.setSegundoApellido("Cerdas");
//        pers.setNombre("Jose");
        lista.insert(1, elem);
        System.out.println(lista.first().current.element);
        lista.insertAfter(2, 1);
        System.out.println(lista.first().current.element);
        lista.insertAfter(3, 2);
        lista.insertBefore(5,1);
        lista.insertBefore(6,3);
        System.out.println("La lista tiene estos elementos: ");
        lista.printList(lista);

    }
    
}
