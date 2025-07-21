/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jeff.recursive;

/**
 *
 * @author jeff
 */
public class Operaciones {
       
    
   //operaciones
    public int suma(int num)
    {
        if(num == 0){
            return 0;
        }
        else
            return (num%10 + suma(num/10));
    }
    
    public int invertir(int num, String aux)
    {
        if(num < 10){
            aux += String.format("%d", num);
            int result = Integer.parseInt(aux);
            return result;
        }
        else{
            aux += String.format("%d", (num%10));
            return invertir(num / 10, aux);
        }
    }
    
    public int mayor(int[] arreglo, int indice, int indiceMayor)
    {
        if(indice < 0){
                           
            return indiceMayor;
        }
        else{
            if(arreglo[indice] > arreglo[indiceMayor]){
                indiceMayor = indice;
                return mayor(arreglo, (indice -1), indiceMayor);
            }
            
            return mayor(arreglo, (indice-1), indiceMayor);
        }
    }
    
    public int menor(int[] arreglo, int indice, int indiceMenor){
          if(indice < 0){
                           
            return indiceMenor;
        }
        else{
            if(arreglo[indice] < arreglo[indiceMenor]){
                indiceMenor = indice;
                return menor(arreglo, (indice -1), indiceMenor);
            }
            
            return menor(arreglo, (indice-1), indiceMenor);
        }
    }
    
    public int cuentaPares(int[] arreglo, int indice, int contador)
    {
        if(indice < 0){
            return contador;
        }
        
        if(arreglo[indice]%2 == 0){
            contador++;
        }
        
        return cuentaPares(arreglo, indice-1, contador);
    }
    
    public int cuentaImpares(int[] arreglo, int indice, int contador)
    {
        if(indice < 0){
            return contador;
        }
        
        if(arreglo[indice] % 2 != 0){
            contador ++;
        }
        
        return cuentaImpares(arreglo, indice-1, contador);
    }
}
