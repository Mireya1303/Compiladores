/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaseis.lr1parser.gramatica;

import java.util.Enumeration;
import java.util.Objects;
import java.util.Vector;

/**
 *
 * @author kdelaof
 */
public class Simbolo {

    String content;
    
    public Simbolo(Simbolo symbol){
        this.content = symbol.content;
    }
    
    public Simbolo(String param){            
        content = param;
    }
    
    String type(){
        return "Symbol";
    }
    public void Display(){
        System.out.print(content);
    }
    
    @Override
    public boolean equals(Object object){
        Simbolo symb = (Simbolo)object;
        return (this.content.equals(symb.content));        
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.content);
        return hash;
    }
    
    @Override
    public String toString() {
        return content;
    }
   
}
