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

import com.google.common.base.Charsets;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import java.io.File;
import practicaseis.interfaceprimaria.Parser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import practicaseis.lr1parser.gramatica.Simbolo;



/**
 *
 * @author kdelaof
 */
public class LR1Parser {

    public static void main(String[] args) throws IOException {
            
        Parser parser = new Parser();
        parser.inicializar();
        String path = 
                "C:/Users/mirey/Documents/GitHub/Compiladores/src/practicaseis/";
        ArrayList<Simbolo> input =  read( path + "entrada.txt");  // Read the input file\        
        parser.parsear(input);  
    }
    
    public static ArrayList<Simbolo> read(String name) throws IOException {
    
        File file = new File(name);
        CharSource source;             
        source = Files.asCharSource(file, Charsets.UTF_8);
        String result = source.read().replaceAll("\r\n"," "); // replace all end line or new line with space            
        ArrayList<Simbolo> res = new ArrayList<>();        
        ArrayList<String> temp = new ArrayList<>(Arrays.asList(result.split(" ")));
        
        for(String str: temp){
            res.add(new Simbolo(str));
        }
   
        return res;
    }   
    
}
