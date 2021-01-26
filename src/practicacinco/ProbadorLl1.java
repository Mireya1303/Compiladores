/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicacinco;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import utileria.EntradaSalida;

/**
 *
 * @author kdelaot
 */
public class ProbadorLl1 {
    public static void main(String[] args) {
        EntradaSalida inOut = new EntradaSalida();
        String path = 
            "/Users/sdelaot/NetbeansProjects/compilador/src/practicacinco/";
        System.out.println( "Entrada: " );
        String entrada = inOut.leerArchivo( path + "entrada.txt" );
        
        Ll1 ll1 = null;
        try {
            ll1 = new Ll1(entrada);
        } catch (IOException ex) {
            Logger.getLogger(
                    ProbadorLl1.class.getName()).log(Level.SEVERE, null, ex);
        }
        ll1.resultados(path);
//        while (true) {
//            leer = new Scanner(System.in);
//
//            System.out.println("Cadena a verificar:");
//            String cadena = leer.nextLine();
//
//            ll1.verificarCadena(cadena);
//
//            System.out.println("¿Verificar otra cadena?\n1. Si\n2. No");
//            int opCad = leer.nextInt();
//            if (opCad == 2) {
//                break;
//            }
    }
}
