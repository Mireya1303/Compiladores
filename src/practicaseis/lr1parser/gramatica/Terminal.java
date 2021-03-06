/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaseis.lr1parser.gramatica;

/**
 *
 * @author kdelaof
 */
public class Terminal extends Simbolo {
        public boolean error = false;
        
    public Terminal(Simbolo sym){ // copy constructor
        this(sym.toString());               
    }    
    
    public Terminal(String term){
        super(term);
        if(term.charAt(0)== '[' && term.charAt(term.length() - 1 )==']'){ // Terminal cannot be within square brackets
              error = true;
        }else{
              content = term;
        }
    }
    
    @Override
    public String type(){
       return "Terminal";
    }
}
