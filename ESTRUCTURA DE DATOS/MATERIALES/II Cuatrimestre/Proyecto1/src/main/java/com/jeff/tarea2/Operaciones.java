/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jeff.tarea2;

import java.util.Random;
import javax.swing.JOptionPane;

public class Operaciones {

    
    private Persona [] p = new Persona[50];
    
    private Persona[] menor;
    
    private Persona[] mayor;
    
    private Persona[] anciano;
    
       /**
     * @return the menor
     */
    //obtiene todos los menores
    public Persona[] getMenor() {
        return menor;
    }

    /**
     * @return the mayor
     */
    //obtiene todos los mayores
    public Persona[] getMayor() {
        return mayor;
    }

    /**
     * @return the anciano
     */
    //obtiene todos los adultos mayores
    public Persona[] getAnciano() {
        return anciano;
    }
    
    //genera las 50 personas de manera aleatoria.
    public Persona [] generar(){
        
        Random rnd = new Random();
        boolean seguir = true;
        
        for(int i = 0; i< p.length; i++){
            
            do{
                int id = 100000000 + rnd.nextInt(900000000);

                if(!duplicado(id, i)){
                    int edad = 1 + rnd.nextInt(101);
                    
                    Persona person = new Persona();
                    person.setId(id);
                    person.setEdad(edad);
                    
                    p[i] = person;

                    seguir = false;
                }
            }while(seguir);  
        }
        
        return p;
    }
    
    //verifica que el id no esté duplicado
    public boolean duplicado(int id, int cont){
        
        for(int i =0; i < cont; i++){
            if(p[i].getId() == id)
                return true;                
        }

        return false;
    }
    
    //selecciona los menores y los retorna
    public Persona[] menores(){
        menor = new Persona[findMenores()];
        
        int cont = cuentaMenores()-1;
        if(cont < 0)
            cont = 0;
        
        for(int i=0; i<p.length; i++){
            if(p[i].getEdad() <18){
                menor[cont] = p[i];
                cont--;
            }          
        }
        
        return getMenor();
    }
    
    //selecciona los mayores y los muestra
    public Persona[] mayores(){
        mayor = new Persona[findMayores()];

        int cont = cuentaMayores()-1;
        if(cont < 0)
            cont = 0;
        
        for(int i=0; i<p.length; i++){
            if(p[i].getEdad() >=18 && p[i].getEdad() < 65){
                mayor[cont] = p[i];
                cont--;
            }          
        }

        return getMayor();
    }
    
    //selecciona los adultos mayores y los retorna
    public Persona[] ancianos(){
        anciano = new Persona[findAncianos()];

        int cont = cuentaAncianos()-1;
        if(cont < 0)
            cont = 0;
        
        for(int i=0; i<p.length; i++){
            if(p[i].getEdad() >= 65){
                anciano[cont] = p[i];
                cont--;
            }          
        }

        return getAnciano();
    }
    
    //retorna la cantidad de menores
    public int cuentaMenores(){
        return getMenor().length;
    }
    //retorna la cantidad de mayores
    public int cuentaMayores(){
        return getMayor().length;
    }
    //cuenta la cantidad de adultos mayores
    public int cuentaAncianos(){
        return getAnciano().length;
    }
    
    //retorna la cantidad de menores y deuelve el número
    public int findMenores() {
        int cont = 0;

        for (Persona per : p) {
            if (per.getEdad() < 18) {
                cont++;
            }
        }
        return cont;
    }
    
    public int findMayores(){
        int cont = 0;
        
        for(Persona per:p){
            if(per.getEdad() >= 18 && per.getEdad() < 65)
                cont++;
        }
        return cont;
    }
    
    public int findAncianos(){
        int cont = 0;
        
        for(Persona per:p){
            if(per.getEdad() >= 65)
                cont++;
        }
        return cont;
    }
    
    //EXTRAER los menores
    public Persona[] popMenores(){
        
        Persona[] nuevo;
        
        try{  
             nuevo = new Persona[getMenor().length-1];
             
            Persona personaEliminada = getMenor()[0];
        
            JOptionPane.showMessageDialog(null, String.format("Persona eliminada: " + personaEliminada.getId() + 
                    " | Edad: " + personaEliminada.getEdad()),"Remover persona", JOptionPane.INFORMATION_MESSAGE);
        
            for(int i = 0; i < getMenor().length-1; i++)
                nuevo[i] = getMenor()[i+1];
        
            menor = nuevo;
            
            return nuevo;
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "No quedan más elementos en la pila","ERROR", JOptionPane.WARNING_MESSAGE);
        }         
        
        //se devuelve null en caso de que no hayan personas
        
        return null;
    }
    
    public Persona[] popMayores(){
        
        Persona[] nuevo;
        
        try{  
             nuevo = new Persona[getMayor().length-1];
             
            Persona personaEliminada = getMayor()[0];
        
            JOptionPane.showMessageDialog(null, String.format("Persona eliminada: " + personaEliminada.getId() + 
                    " | Edad: " + personaEliminada.getEdad()),"Remover persona", JOptionPane.INFORMATION_MESSAGE);
        
            for(int i = 0; i < getMayor().length-1; i++)
                nuevo[i] = getMayor()[i+1];
        
            mayor = nuevo;
            
            return nuevo;
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "No quedan más elementos en la pila","ERROR", JOptionPane.WARNING_MESSAGE);
        }         
        
        //si ya no existen personas
        return null;
    }
    
    public Persona[] popAncianos(){
        
        Persona[] nuevo;
        
        try{  
             nuevo = new Persona[getAnciano().length-1];
             
            Persona personaEliminada = getAnciano()[0];
        
            JOptionPane.showMessageDialog(null, String.format("Persona eliminada: " + personaEliminada.getId() + 
                    " | Edad: " + personaEliminada.getEdad()),"Remover persona", JOptionPane.INFORMATION_MESSAGE);
        
            for(int i = 0; i < getAnciano().length-1; i++)
                nuevo[i] = getAnciano()[i+1];
        
            anciano = nuevo;
            
            return nuevo;
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "No quedan más elementos en la pila","ERROR", JOptionPane.WARNING_MESSAGE);
        }         
        
        //si ya no existen personas
        return null;
    }
    
    public void vaciarMenores(){
        menor = new Persona[0];
    }
    
    public void vaciarMayores(){
        mayor = new Persona[0];
    }
    
    public void vaciarAncianos(){
        anciano = new Persona[0];
    }
}
