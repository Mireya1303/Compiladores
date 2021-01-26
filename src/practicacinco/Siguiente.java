/*
 *   INSTITUTO POLITÉCNICO NACIONAL
 *   ESCUELA SUPERIOR DE CÓMPUTO
 *   COMPILADORES
 *   ALUMNA:    DE LA O FLORES KARLA MIREYA - 3CV6
 *   PRACTICA:  #5
 *   PROFESOR:  NORMAN RAFAEL SAUCEDO DELGADO
 *   
 */
package practicacinco;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 *
 * @author kdelaot
 */
public class Siguiente {

    private HashMap<String, Set<String>> siguientes;
    private HashMap<String, ArrayList<String>> producciones;
    private ArrayList<String> noTerminales;
    private HashMap<String, Set<String>> nTSiguientes;

    public Siguiente(GSVicio gSVicio, Primero primeros) {
        this.siguientes = new HashMap<>();
        this.nTSiguientes = new HashMap<>();
        this.producciones = gSVicio.getProducciones();
        this.noTerminales = gSVicio.getNoTerminales();

        construirNTSiguientes(gSVicio);

        construirSiguiente(gSVicio, primeros);

        calcularSiguiente(gSVicio);
    }
    
    public String resultados() {
        StringBuilder builder = new StringBuilder();
        this.siguientes.forEach((noTerminal, conjunto) -> {
            System.out.println("SIGUIENTE(" + noTerminal + ") = " + conjunto);
            builder.append(
                "SIGUIENTE(").append(noTerminal).append(
                        ") = ").append(conjunto);
            builder.append("\n");
        });
        System.out.println();
        builder.append( "\n" );
        return builder.toString();
    }

    public HashMap<String, Set<String>> getSiguientes() {
        return siguientes;
    }

    private Set<String> construirProducciones(String produccion, String B, 
            String A, Primero primeros) {
        Set<String> prodConstr = new HashSet<>();
        while (produccion.contains(B)) {
            produccion = produccion.substring(produccion.indexOf(B) + 1, 
                    produccion.length());
            if (produccion.equals("")) {
                this.nTSiguientes.get(B).add(A);
                break;
            }
            prodConstr.add(produccion);
        }
        return prodConstr;
    }

    private void construirNTSiguientes(GSVicio gSVicio) {
        for (String noTerminal : gSVicio.getNoTerminales()) {
            this.nTSiguientes.put(noTerminal, new HashSet<>());
            this.siguientes.put(noTerminal, new HashSet<>());
        }
        String estadoInicial = gSVicio.getNoTerminales().get(0);
        this.siguientes.get(estadoInicial).add("$");
    }

    private void construirSiguiente(GSVicio gSVicio, Primero primeros) {
        for (String noTerminal : gSVicio.getNoTerminales()) {
            for (String noTermAux : gSVicio.getNoTerminales()) {
                ArrayList<String> producciones = 
                        gSVicio.getProducciones().get(noTermAux);
                for (String produccion : producciones) {
                    Set<String> prodConstr = construirProducciones(produccion, 
                            noTerminal, noTermAux, primeros);
                    if (!prodConstr.isEmpty()) {
                        for (String prod : prodConstr) {
                            for (int i = 0; i < prod.length(); i++) {
                                String simbolo = prod.substring(i, i + 1);
                                if (esTerminal(simbolo)) {
                                    this.siguientes.get(
                                            noTerminal).add(simbolo);
                                    break;
                                } else {
                                    Set<String> conjPrimero = 
                                            primeros.getPrimeros().get(simbolo);
                                    if (!conjPrimero.contains("&")) {
                                        this.siguientes.get(
                                                noTerminal).addAll(conjPrimero);
                                        break;
                                    } else {
                                        if (prod.length() == 1) {
                                            this.siguientes.get(
                                                    noTerminal).addAll(
                                                            conjPrimero);
                                            this.nTSiguientes.get(
                                                    noTerminal).add(
                                                            noTermAux);
                                        } else {
                                            this.siguientes.get(
                                                    noTerminal).addAll(
                                                            conjPrimero);
                                            if (i == prod.length() - 1) {
                                                this.nTSiguientes.get(
                                                        noTerminal).add(
                                                                noTermAux);
                                            }
                                        }
                                        this.siguientes.get(
                                                noTerminal).remove("&");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void calcularSiguiente(GSVicio gSVicio) {
        for (int j = 0; j < 2; j++) {
            for (String noTerminal : gSVicio.getNoTerminales()) {
                Set<String> ciclo = this.nTSiguientes.get(noTerminal);
                Set<String> union = new HashSet<>();
                Set<String> A = this.siguientes.get(noTerminal);
                union.addAll(A);
                for (String simbolo : ciclo) {
                    Set<String> B = this.siguientes.get(simbolo);
                    union.addAll(B);
                }
                this.siguientes.get(noTerminal).addAll(union);
            }
        }
    }

    private boolean esTerminal(String cadena) {
        return Pattern.matches("[A-Z]", cadena) ? false : true;
    }
}
