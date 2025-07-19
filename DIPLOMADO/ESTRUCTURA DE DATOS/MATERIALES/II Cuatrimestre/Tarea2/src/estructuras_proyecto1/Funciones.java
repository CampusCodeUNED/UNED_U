/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructuras_proyecto1;

/**
 *
 * @author Jeffrey
 */
public class Funciones {
    
    public float [] burbuja(float [] arreglo){
        int tamanio = arreglo.length;
        //for externo que recorre todo el arreglo
        for (int i = 0; i < tamanio-1; i++){
            //for interno que recorre y compara los demás valores
            for (int j = 0; j < tamanio-i-1; j++){
                 //si el valor actual es mayor al que sigue
                if (arreglo[j] > arreglo[j+1])
                {
                    //se guarda el valor en una variable temporal
                    //se cambian los valores (el valor siguiente será el actual)
                    //y el que era el valor sigiente se le pasa la variable temporal
                    float temp = arreglo[j];
                    arreglo[j] = arreglo[j+1];
                    arreglo[j+1] = temp;
                }
            }
               
        }
        
        //se retorna el arreglo ordenado
        return arreglo;
            
    }
    
    //comienza a ordenar de forma recursiva
    public float [] quickSort(float [] arreglo, int bajo, int alto){
         if (bajo < alto) { 
            //obtiene el pivote para comenzar a ordenar
            int pivote = particion(arreglo, bajo, alto); 
   
            // ordena cada partición de forma recursiva 
            quickSort(arreglo, bajo, pivote-1); 
            quickSort(arreglo, pivote+1, alto); 
        } 
         //devuelve el arreglo ordenado
         return arreglo;
    }
    
    public int particion(float[] arreglo, int bajo, int alto){
        float pivote = arreglo[alto];  
        int i = (bajo-1); // índice del elemento más bajo  
        for (int j=bajo; j<alto; j++) { 
            // verifica que el elemento sea menor o igual al pivote 
            if (arreglo[j] <= pivote) { 
                i++; 
                // cambia arreglo[i] por arreglo[j] 
                float temp = arreglo[i]; 
                arreglo[i] = arreglo[j]; 
                arreglo[j] = temp; 
            } 
        } 
   
        // cambia arreglo[i+1] y arreglo[alto] (o pivote) 
        float temp = arreglo[i+1]; 
        arreglo[i+1] = arreglo[alto]; 
        arreglo[alto] = temp; 
   
        return i+1; 
    }
    
    public float [] shellSort(float [] arreglo){
        float aux;
        int  salto, i;
        boolean cambios;
  
        for (salto = arreglo.length / 2; salto != 0; salto /= 2) {
            cambios = true;
            // Mientras se intercambie algún elemento 
            while (cambios) {
                //no hay cambio aún
                cambios = false;
                // se recorre el resto de elementos
                for (i = salto; i < arreglo.length; i++)   
                {
                    //si están desordenados se reordenan
                    if (arreglo[i - salto] > arreglo[i]) {       
                        aux = arreglo[i];                  
                        arreglo[i] = arreglo[i - salto];
                        arreglo[i - salto] = aux;
                        // y se indica que se realizó el cambio
                        cambios = true;                                                 
                    }
                }
            }
        }
        //se retorna el arreglo ordenado
        return arreglo;
    }
}
