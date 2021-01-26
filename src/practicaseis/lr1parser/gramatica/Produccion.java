/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaseis.lr1parser.gramatica;

import java.util.ArrayList;
import practicaseis.Util;
import practicaseis.Util;

/**
 *
 * @author kdelaof
 */
public class Produccion {
    public NoTerminal lhs;
    public ArrayList<Simbolo> rhs;
    public Util assistant = new Util();
    
    public Produccion(Produccion prod){ // copy constructor
        this.lhs = prod.lhs;
        this.rhs = prod.rhs;
    }
    
    public Produccion(String lhs, String rhs){ // Constructor receives 2 strings of rhs and lhs 
        
        ArrayList<String> listOfSymbols = assistant.convertirStringAListaDeSimbolos(rhs); // Split rhs string into arrayList
        
        this.lhs = new NoTerminal(lhs); // initialize this.lhs
        this.rhs = new ArrayList<>();            
        listOfSymbols.forEach((rh) -> {
            this.rhs.add(new Simbolo(rh)); // initialize this.rhs
        });
    }
    
    public Produccion(Simbolo lhs, ArrayList<Simbolo> rhs){ // Constructor receives 2 strings of rhs and lhs 
        NoTerminal term = new NoTerminal(lhs);
        this.lhs = term; 
        this.rhs = rhs;           
    }
    
    public boolean isNull(){
        return(this.lhs == null || this.rhs == null);
    }
    public void Display(){
        System.out.println(this.lhs + " -> " + assistant.convertirListaDeSimbolosAString(this.rhs));
    }    
    
    @Override
    public boolean equals(Object object){
        Produccion prod = (Produccion)object;
        return (this.lhs.equals(prod.lhs) && this.rhs.equals(prod.rhs));
    }
    
    @Override
    public String toString(){
        return this.lhs + " -> " + assistant.convertirListaDeSimbolosAString(this.rhs);
    }


}
