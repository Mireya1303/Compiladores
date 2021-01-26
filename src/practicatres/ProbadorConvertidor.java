/*
 *   INSTITUTO POLITÉCNICO NACIONAL
 *   ESCUELA SUPERIOR DE CÓMPUTO
 *   COMPILADORES
 *   ALUMNA:    DE LA O FLORES KARLA MIREYA - 3CV6
 *   PRACTICA:  #3
 *   PROFESOR:  NORMAN RAFAEL SAUCEDO DELGADO
 *   
 */
package practicatres;

import java.util.logging.Level;
import java.util.logging.Logger;
import utileria.Alfabeto;
import utileria.EntradaSalida;

/**
 *
 * @author kdelaof
 */
public class ProbadorConvertidor {
    public static void main(String[] args) {
        EntradaSalida inOut = new EntradaSalida();
        String path = 
            "/Users/sdelaot/NetbeansProjects/compilador/src/practicatres/";
        String lectura = inOut.leerArchivo(path + "entrada.txt");
        String [] lecturas = lectura.split(",");
        System.out.println("Abecedario: " + lecturas[0]);
        System.out.println("Expersion :" + lecturas[1]);
        Alfabeto alfabeto = new Alfabeto(lecturas[0]);
        String expresion = lecturas[1];
        ConvertidorAFNtoAFD convertidor = 
                new ConvertidorAFNtoAFD(alfabeto, expresion);
        try {
            convertidor.convertir(path);
        } catch (Exception ex) {
            Logger.getLogger(
                    ProbadorConvertidor.class.getName()).log(
                            Level.SEVERE, null, ex);
        }
    }
}
