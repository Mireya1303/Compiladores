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

import practicacuatro.AnalizadorSintactico;
import practicauno.AFD;
import practicauno.AFN;
import practicauno.TablaDeTransicion;
import utileria.Alfabeto;
import utileria.EntradaSalida;

/**
 *
 * @author kdelaof
 */
public class ConvertidorAFNtoAFD {
    private Alfabeto alfabeto;
    private String expresion;
    public ConvertidorAFNtoAFD(Alfabeto alfabeto, String expresion) {
        this.alfabeto = alfabeto;
        this.expresion = expresion;
    }
    public void convertir(String path) throws Exception{
        EntradaSalida inOut = new EntradaSalida();
        StringBuilder builder = new StringBuilder();
        //Alfabeto alfa = new Alfabeto("abc");
        //String er = "(a|b)*a+c?";
        AnalizadorSintactico as = new AnalizadorSintactico(alfabeto, expresion);
         
        /*
         *  CONVERSION REGEX -> AFN
         *  ALGORITMO DE THOMPSON
         */ 
       
        AFN salida = as.analizar();
        System.out.printf("AFN:\n%s", salida);
        builder.append("AFN:\n").append(salida).append("\n");
        
               
        /* Imprimir la Tabla transicion del AFN */
        System.out.println();
        TablaDeTransicion tabla = salida.getTablaTransicion();
        
               
        for (int i=0; i < tabla.getColumnCount(); i++) {
            System.out.printf("%s\t", tabla.getColumnName(i));
            builder.append(tabla.getColumnName(i)).append("\t");
            }
        builder.append("\n");
        System.out.println();
        for (int i=0; i < tabla.getRowCount(); i++) {
            for (int j=0; j < tabla.getColumnCount(); j++) {
                System.out.printf("%s\t", tabla.getValueAt(i, j));
                builder.append(tabla.getValueAt(i, j)).append("\t");
                }
            System.out.println();
            builder.append("\n");
        }
       
        System.out.printf("\nDerivaciones:\n%s", as.getLog());
        builder.append("\nDerivaciones:\n").append(as.getLog());
        
        
        /*
         *  CONVERSION AFN -> AFD
         *  ALGORITMO DE SUBCONJUNTOS
         */
        
        System.out.println();
        builder.append("\n");
        AFD afd = Subconjuntos.getAFD(salida);
        System.out.printf("AFD:\n%s", afd);
        builder.append("AFD:\n").append(afd);
        System.out.printf("\nEstados AFD:\n%s", afd.estadosAFDtoString());
        builder.append("\nEstados AFD:\n").append(afd.estadosAFDtoString());
       
        /* Imprimir la Tabla transicion del AFD */
        System.out.println();
        builder.append("\n");
        TablaDeTransicion tabla2 = afd.getTablaTransicion();
       
        for (int i=0; i < tabla2.getColumnCount(); i++) {
            System.out.printf("%s\t\t", tabla2.getColumnName(i));
            builder.append(tabla2.getColumnName(i)).append("\t\t");
            }
       
        System.out.println();
        builder.append("\n");
        for (int i=0; i < tabla2.getRowCount(); i++) {
            for (int j=0; j < tabla2.getColumnCount(); j++) {
                System.out.printf("%s\t\t", tabla2.getValueAt(i, j));
                builder.append(tabla2.getValueAt(i, j)).append("\t\t");
            }
            System.out.println();
            builder.append("\n");
        }
        
        System.out.printf(
                "\nConjuntos estados producidos:\n%s", Subconjuntos.getLog());
        builder.append("\nConjuntos estados producidos:\n"
                        ).append(Subconjuntos.getLog());
        GeneradorCodigo gen = new GeneradorCodigo(afd);
        String codigo= gen.generaCodigo();
       
        /*
         *  CONVERSION AFD -> AFD MINIMO
         *  ALGORITMO DE MINIMIZACION 
         */
       
        
        AFDMin afdMin = Minimizacion.getAFDminimo(afd);
        System.out.printf("AFD Original:\n%s\n", afdMin.getAfdOriginal());
        builder.append(
                "AFD Original:\n").append(afdMin.getAfdOriginal()).append("\n");
        String resultado = afdMin.inalcanzablesEliminados() ? "<>" : "=="; 
        System.out.printf("AFD Post Inalcanzables (%s):\n%s\n", resultado, 
                afdMin.getAfdPostInalcanzables() );
        builder.append(
                "AFD Post Inalcanzables (").append(resultado).append("):\n");
        builder.append(afdMin.getAfdPostInalcanzables()).append("\n");
        System.out.printf("AFD Post Minimizacion:\n%s\n", 
                afdMin.getAfdPostMinimizacion());
        builder.append("AFD Post Minimizacion:\n").append(
                afdMin.getAfdPostMinimizacion());
        builder.append("\n");
        resultado = afdMin.identidadesEliminados() ? "<>" : "==";
        System.out.printf("AFD Post Identidades (%s):\n%s\n", 
                resultado, afdMin.getAfdPostIdentidades());
        builder.append("AFD Post Identidades (").append(resultado);
        builder.append("):\n").append(afdMin.getAfdPostIdentidades());
        builder.append("\n");
        System.out.printf("\nParticiones:\n%s", Minimizacion.getLog());
        builder.append("\nParticiones:\n").append(Minimizacion.getLog());
        inOut.escribirArchivo(path + "salida.txt", builder.toString());
    }
}
