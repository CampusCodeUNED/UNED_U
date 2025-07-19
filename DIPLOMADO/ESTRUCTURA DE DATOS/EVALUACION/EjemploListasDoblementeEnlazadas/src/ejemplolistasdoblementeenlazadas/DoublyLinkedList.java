/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejemplolistasdoblementeenlazadas;

/**
 *
 * @author javalver
 * @param <AnyType>
 */
public class DoublyLinkedList <AnyType> {

       
    private DoublyLinkedListNode<AnyType> header;
    
    private DoublyLinkedListNode<AnyType> tail;

    public DoublyLinkedList() {
        header = new DoublyLinkedListNode<AnyType>(null);
        tail = new DoublyLinkedListNode<AnyType>(null);
        header.next = tail;
        tail.prev = header;
    }
    
    public boolean isEmpty() {
        return header.next == tail;
    }
    
    public void makeEmpty() {
        header.next = tail;
        tail.prev = header;
    }
    
    public DoublyLinkedListIterator<AnyType> zeroth() {
        return new DoublyLinkedListIterator<AnyType>( header );
    }
    
    public DoublyLinkedListIterator<AnyType> last() {
        return new DoublyLinkedListIterator<AnyType>(tail.prev);
    }
    
    public DoublyLinkedListIterator<AnyType> first() {
        return new DoublyLinkedListIterator<AnyType> (header.next);
    }
    
    public void insert(AnyType x, DoublyLinkedListIterator<AnyType>  p) {
        if(p!=null && p.current != null) {
            p.current.next = new DoublyLinkedListNode(x);
        }
    }
    
    public void insertAfter(AnyType x, AnyType y) {
        DoublyLinkedListIterator<AnyType> p = find(y);
        DoublyLinkedListNode newNode = new DoublyLinkedListNode(x);
        newNode.prev = p.current;
        newNode.next = p.current.next;
        if (p.current.next != null) {
            p.current.next.prev = newNode;
        }
        p.current.next = newNode;
    }
    
    public void insertBefore(AnyType x, AnyType y) {
        DoublyLinkedListIterator<AnyType> p = find(y);
        DoublyLinkedListNode newNode = new DoublyLinkedListNode(x);
        newNode.prev = p.current.prev;
        newNode.next = p.current;
        if (p.current.prev != null) {
            newNode.prev.next = newNode;
        }
        p.current.prev = newNode;
    }

    
    public DoublyLinkedListIterator<AnyType> find (AnyType x) {
        DoublyLinkedListNode<AnyType> itr = header.next;
        while(itr != tail && !itr.element.equals(x)) {
            itr = itr.next;
        }
        return new DoublyLinkedListIterator<AnyType>(itr);
        
    }
    
    public DoublyLinkedListIterator<AnyType> findPrevious(AnyType x) {
        DoublyLinkedListNode<AnyType> itr = header;
        while(itr.next != tail && !itr.next.element.equals(x)) {
            itr = itr.next;
        }
        return new DoublyLinkedListIterator<AnyType>(itr);
    }
    
    public void remove (AnyType x) {
        DoublyLinkedListIterator<AnyType> p = find(x);
        if (p.current.next != tail) {
            p.current.next.prev = p.current.prev;
            p.current.prev.next = p.current.next;
            p.current = header;
        }        
    }
    
    public int size(){
        // Devuelve el tamaño de la lista
        int contador = 0;
        // TODO
        return contador;
    }
    
    public void printList(DoublyLinkedList<AnyType> theList) {
        if (theList.isEmpty()){
            System.out.println("Lista vacía");
        } else {
            DoublyLinkedListIterator<AnyType>  itr = theList.first();
            for (; itr.isValid();itr.advance()){
                System.out.print(itr.retrieve()+ " ");
            }
        }
            
    }
}

class DoublyLinkedListNode <AnyType>{
    
    public AnyType element;
    public DoublyLinkedListNode prev;
    public DoublyLinkedListNode next;
    
    public DoublyLinkedListNode (AnyType theElement) {
        this (theElement, null, null);
    }
    public DoublyLinkedListNode(AnyType theElement, DoublyLinkedListNode<AnyType> p, DoublyLinkedListNode<AnyType> n) {
        element = theElement;
        prev = p;
        next = n;
    }
}


//        else {
//            this.header.next = newNode;
//        }
