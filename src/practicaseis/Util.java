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
import java.util.Arrays;
import java.util.List;
import practicaseis.lr1parser.gramatica.Simbolo;
import practicaseis.lr1parser.gramatica.Terminal;

/**
 *
 * @author kdelaof
 */
public class Util {
    public boolean esTerminal(Simbolo simbolo){
        Terminal terminal = new Terminal(simbolo);
        return !terminal.error;
    }
    public String convertirListaDeSimbolosAString(
            List<Simbolo> listaDeSimbolos){
        String resultado="";
        for(int i=0;i<listaDeSimbolos.size();i++){
            resultado = resultado +  listaDeSimbolos.get(i).toString() + " ";
        }
        return resultado;
    }
    
    public ArrayList<String> convertirStringAListaDeSimbolos(String str){
        return new ArrayList<>(Arrays.asList(str.split(" ")));              
        
    }



}
