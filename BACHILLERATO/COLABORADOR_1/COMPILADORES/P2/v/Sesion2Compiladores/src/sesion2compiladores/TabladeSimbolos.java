/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sesion2compiladores;

/**
 *
 * @author cchac
 */
public class TabladeSimbolos {
    enum Tipos {     
        Reservada("(CICLO|INCREMENTA|FINCICLO|SI|ENTONCES|FINSI|INICIO|DECLARAVAR)"),
        TipoVar("(ENTERO|TEXTO|DECIMAL)"),
        Parentesis("(\\(|\\)|\\{|\\}|\\[|\\])"),
        Operadores("(<|>|={2})"),
        Asignacion("(=)"),
        Numeros("[0-9]*"),
        Separadores("(;|,)"),
        Variable("[A-Za-z0-9]*"); 
        
        public final String patron;
        Tipos(String s){
            this.patron = s;
        }        
    }     
}
