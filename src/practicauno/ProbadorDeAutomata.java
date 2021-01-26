/*
 *   INSTITUTO POLITÉCNICO NACIONAL
 *   ESCUELA SUPERIOR DE CÓMPUTO
 *   COMPILADORES
 *   ALUMNA:    DE LA O FLORES KARLA MIREYA - 3CV6
 *   PRACTICA:  #1
 *   PROFESOR:  NORMAN RAFAEL SAUCEDO DELGADO
 *   
 */
package practicauno;

import java.util.logging.Level;
import java.util.logging.Logger;
import practicacuatro.AnalizadorSintactico;
import utileria.Alfabeto;

/*
 La funcion cargar desde cargará un autómata
 desde un archivo.
 recibe:  string ruta
 regresa: bool:exito
 (verdadero si se puede cargar)
 (falso si no se puede cargar)
            
 El formato del autómata
 contenido del archivo será:

 inicio:estado1                  // estado inicial
 finales:estado1,estado2,...     // estados finales
 inicio->fin,simbolo             // transiciones
 inicio->fin,simbolo
 inicio->fin,simbolo
 ...

 por ejemplo:

 inicial:1
 finales:11
 1->2,E
 1->8,E
 2->3,E
 2->5,E
 3->4,a
 5->6,b
 6->7,E
 4->7,E
 7->2,E
 7->8,E
 8->9,a
 9->10,b
 11->11,b
 */
/**
 *
 * @author sdelaot
 */
public class ProbadorDeAutomata {
    public static void main(String[] args) {
        String expresion = "(a|b)*a+c?";
        
        Alfabeto alfabeto = new Alfabeto( "abc" );
        AnalizadorSintactico as = new AnalizadorSintactico(alfabeto, expresion);
        AFN afn = null;
        try {
            afn = as.analizar();
        } catch (Exception ex) {
            Logger.getLogger(ProbadorDeAutomata.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println( afn );
    }
}
