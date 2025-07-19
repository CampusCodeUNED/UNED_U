/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejemplolistasdoblementeenlazadas;

/**
 *
 * @author javalver
 */
public class DoublyLinkedListIterator <AnyType> {
    
    DoublyLinkedListNode<AnyType> current;
    
    DoublyLinkedListIterator(DoublyLinkedListNode<AnyType> theNode) {
        current = theNode;
    }
    
    public boolean isValid() {
        return current != null;
    }
    
    public AnyType retrieve() {
        return isValid() ? current.element : null;
    }
    
    public void advance() {
        if (isValid()) {
            current = current.next;
        }
    }
    
}
