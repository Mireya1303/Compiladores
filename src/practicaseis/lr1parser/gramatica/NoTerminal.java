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
public class NoTerminal extends Simbolo {
    public boolean error = false;
        
    public NoTerminal(Simbolo sym){ // copy constructor
        this(sym.toString());               
    } 
     
    public NoTerminal(String nonTerm){
        super(nonTerm);
        
        if(nonTerm.charAt(0)=='[' && nonTerm.charAt(nonTerm.length()-1)==']'){ // nonTerminal has to within square brackets
            content = nonTerm;
        }else{
            error = true;
        }
    }
    
    @Override
    String type(){
        return "Nonterminal";
    }
}
