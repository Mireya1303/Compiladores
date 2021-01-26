/*
 *   INSTITUTO POLITÉCNICO NACIONAL
 *   ESCUELA SUPERIOR DE CÓMPUTO
 *   COMPILADORES
 *   ALUMNA:    DE LA O FLORES KARLA MIREYA - 3CV6
 *   PRACTICA:  #6
 *   PROFESOR:  NORMAN RAFAEL SAUCEDO DELGADO
 *   
 */
package practicaseis;

import java.util.ArrayList;
import practicaseis.lr1parser.gramatica.Simbolo;


/**
 *
 * @author kdelaof
 */
public class Par {
    public Simbolo key;
    // Una lista de simbolor en Principio
    public ArrayList<Simbolo> listaDeSimbolos; 
    
    public Par(Simbolo e, ArrayList<Simbolo> f){
        key = e; 
        listaDeSimbolos = f;
    }
    public Par(Simbolo e){ 
        key = e;
    }
    
    public Simbolo key(){
        return key;
    }
    public ArrayList<Simbolo> value(){
        return listaDeSimbolos;
    }
    
    @Override
    public String toString(){
        return "CONJUNTO(" + key + ") : " + listaDeSimbolos.toString() + "\n";                
    }
    @Override
    public boolean equals(Object objeto){
        Par pair = (Par)objeto;
        return (this.key.equals(pair.key));
    }
    
}
