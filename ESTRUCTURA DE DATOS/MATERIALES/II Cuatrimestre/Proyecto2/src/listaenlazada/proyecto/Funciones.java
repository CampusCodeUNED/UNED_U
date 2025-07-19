/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listaenlazada.proyecto;


public class Funciones {
  
  Nodo head;
  
  //método para insertar un nuevo elemento
  public void insertar(int data)
  {
    Nodo nodo = new Nodo();
    nodo.data = data; //se le pasa la info al nodo
    nodo.siguiente = null; //queda apuntando hacia un nodo null
    
    //si la raiz es nula, el nuevo dato va a ser la raíz
    if(head == null)
    {
      head = nodo;
    }
    else
    {
      //se pasa el nuevo dato al nodo que esté apuntando a null
      Nodo n = head;
      while(n.siguiente != null)
      {
        n = n.siguiente;
      }
      n.siguiente = nodo;
    }   
  }
  
  //método para borrar un elemento
  public String eliminar()
  {
    //Nodo temporal que albargará el nodo a eliminar
      Nodo nodoEliminar = null;
      nodoEliminar = head; //se obtiene el nodo a eliminar para retornarlo
      head = head.siguiente;
      return String.valueOf(nodoEliminar.data);
    
  }
  
  
//  //método para mostrar todos los elementos - DEBUGGING
//  public void mostrarTodos()
//  {
//    Nodo nodo = head;
//    
//    while(nodo.siguiente != null)
//    {
//      System.out.println(nodo.data);
//      nodo = nodo.siguiente;
//    }
//    System.out.println(nodo.data);
//  }
  
  //MOSTRAR ELEMENTO DEL ÍNDICE INDICADO
  public String mostrarEn(int indice)
  {
    Nodo nodo = head;
    int contador = 1;
    
    //recorre todos los nodos hasta coincidir conel índice
    while(nodo.siguiente != null)
    {
      if(contador == indice){
        return String.valueOf(nodo.siguiente.data); 
      }
       nodo = nodo.siguiente;
       contador++;
    }
    
    //si el índice es 0 se muestra el nodo respectivo
    if(indice == 0)
        return String.valueOf(head.data);
    
    //si no hay datos que coincidan, se devuelve nulo
    return null;
  }
  
  //elimina todos los nodos de la lista
  public void borrarCola(){
      Nodo nodoRespaldo = null;
      
      //se recorre nodo por nodo y se le asigna el valor null
      while(head != null){
          nodoRespaldo = head.siguiente;
          head = null;
          head = nodoRespaldo;
      }
  }
  
  //envía el tamaño de la cola
  public int tamanioCola(){
    
    Nodo nodo = head;
    
    if(head == null){
        return 0;
    }
    
    int contador = 1;
    //recorre cada nodo y suma al contador por cada uno de estos
    while(nodo.siguiente != null)
    {
      nodo = nodo.siguiente;
      contador++;
    }
    
    return contador;
  }
  
}
