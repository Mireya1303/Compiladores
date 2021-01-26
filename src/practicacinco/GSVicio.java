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
import java.util.Iterator;
import java.util.Set;

/**
 * Clase de servicio para almacenar los terminales, no terminales, el inicial y
 * las producciones
 * 
 * @author kdelaot
 */
public class GSVicio {
    /**
     * Lista de Arreglo de terminales
     */
    private ArrayList<String> terminales;
    /**
     * Lista de Arreglo de no terminales
     */
    private ArrayList<String> noTerminales;
    /**
     * Inicial
     */
    private String nTInicial;
    /**
     * Mapa de producciones con key de tipo String
     */
    private HashMap<String, ArrayList<String>> producciones;
    /**
     * Constructor de la clase
     * 
     * @param gramatica la gramatica que se procesa
     */
    public GSVicio(Gramatica gramatica) {
        this.terminales = gramatica.getTerminales();
        this.nTInicial = gramatica.getnTInicial();
        this.noTerminales = gramatica.getNoTerminales();
        this.producciones = new HashMap<>();

        gramatica.getProducciones().forEach((noTerminal, producciones) -> {
            if (esRecursivo(noTerminal, producciones)) {
                quitarRecursividad(noTerminal, producciones);
            } else {
                this.producciones.put(noTerminal, producciones);
            }
        });

        llamarLaFactorizacion();

        construirTerminales();
    }
    /**
     * Resultados del procesamiento
     * 
     * @return devuelve los resultados
     */
    public String mostrarResultados() {
        StringBuilder builder = new StringBuilder();
        System.out.println("No Terminal inicial: " + this.nTInicial);
        builder.append("No Terminal inicial: ").append(this.nTInicial);
        builder.append("\n");
        System.out.println("Terminales: " + this.terminales);
        builder.append("Terminales: ").append(this.terminales);
        builder.append("\n");
        System.out.println("No terminales: " + this.noTerminales);
        builder.append("No terminales: ").append(this.noTerminales);
        builder.append("\n");
        System.out.println("Producciones:\n");
        builder.append("Producciones:\n\n");
        this.producciones.forEach((noTerminal, produccion) -> {
            System.out.println(noTerminal + "->" + produccion);
            builder.append(noTerminal).append("->").append(produccion);
            builder.append("\n");
        });
        System.out.println();
        builder.append("\n");
        return builder.toString();
    }
    /**
     * Asigna un nuevo terminal
     * 
     * @return devuelve un noTerminal si esta contenido en la lista de arreglo
     * de los no terminales
     */
    private String asignarNuevoNTerminal() {
        for (char A = 'A'; A <= 'Z'; A++) {
            String noTerminal = Character.toString(A);
            if (!this.noTerminales.contains(noTerminal)) {
                return noTerminal;
            }
        }
        return "A";
    }
    /**
     * Construye los terminales
     */
    private void construirTerminales() {
        this.terminales = new ArrayList<>();
        for (String noTerminal : this.noTerminales) {
            ArrayList<String> gramatica = this.producciones.get(noTerminal);
            for (String expresion : gramatica) {
                String cadenaTerminales = expresion.replaceAll("([A-Z]'*)", "");
                for (int i = 0; i < cadenaTerminales.length(); i++) {
                    String simbolo = cadenaTerminales.substring(i, i + 1);
                    if (!simbolo.equals("&") && 
                            !this.terminales.contains(simbolo)) {
                        this.terminales.add(simbolo);
                    }
                }
            }
        }
    }
    /**
     * Verifica si es recursivo
     * 
     * @param noTerminal el no terminal
     * @param producciones las producciones
     * @return devuelve true si es recursiivo y no en otro caso
     */
    private boolean esRecursivo(String noTerminal, 
            ArrayList<String> producciones) {
        int i = 0;
        while (i < producciones.size()) {
            if (producciones.get(i).substring(0, 1).equals(noTerminal)) {
                return true;
            }
            i++;
        }
        return false;
    }
    /**
     * Quita la recursividad
     * 
     * @param A el terminal
     * @param producciones la lista de arreglos de las producciones que se 
     * verifican para quitar la recursividad
     */
    private void quitarRecursividad(String A, ArrayList<String> producciones) {
        ArrayList<String> alfa = new ArrayList<>();
        ArrayList<String> beta = new ArrayList<>();
        for (String produccion : producciones) {
            if (A.equals(produccion.substring(0, 1))) {
                int tamañoProd = produccion.length();
                alfa.add(produccion.substring(1, tamañoProd));
            } else {
                beta.add(produccion);
            }
        }
        asignarNRecursivos(A, alfa, beta);
    }
    /**
     * Asigna los N elementos recursivos
     * 
     * @param A El elemento a poner en la produccion
     * @param alfa la lista de arreglos alfa para comprobar si van a noRA
     * @param beta la lista de arreglos beta para comprobar si van a noRAP
     */
    private void asignarNRecursivos(String A, ArrayList<String> alfa, 
            ArrayList<String> beta) {
        String AP = asignarNuevoNTerminal();
        ArrayList<String> noRA = new ArrayList<>();
        for (String produccion : beta) {
            noRA.add(produccion + AP);
        }
        if (beta.isEmpty()) {
            noRA.add(AP);
        }
        ArrayList<String> noRAP = new ArrayList<>();
        for (String produccion : alfa) {
            noRAP.add(produccion + AP);
        }
        noRAP.add("&");
        this.producciones.put(A, noRA);
        this.producciones.put(AP, noRAP);
        int indiceA = this.noTerminales.indexOf(A);
        this.noTerminales.add(indiceA + 1, AP);
    }
    /**
     * Realiza la factorizacion de los no terminales, las producciones
     */
    private void llamarLaFactorizacion() {
        boolean factorizado = false;
        for (String noTerminal : this.noTerminales) {
            ArrayList<String> producciones = this.producciones.get(noTerminal);
            Set<Integer> indicesFact = new HashSet<>();
            do {
                indicesFact = esFactorizable(noTerminal, producciones);
                if (!indicesFact.isEmpty()) {
                    factorizar(noTerminal, producciones, indicesFact);
                    factorizado = true;
                    break;
                } else {
                    factorizado = false;
                }
            } while (!indicesFact.isEmpty());
            if (factorizado) {
                break;
            }
        }
        if (factorizado) {
            llamarLaFactorizacion();
        }
    }
    /**
     * Pregunta si es factorizable
     * 
     * @param A el elemento a comparar
     * @param producciones la lista de producciones
     * 
     * @return devuelve el conjunto de enteros factorizables de las
     * producciones
     */
    private Set<Integer> esFactorizable(String A, 
            ArrayList<String> producciones) {
        Set<Integer> indicesFact = new HashSet<>();
        int i = 0;
        while (i < producciones.size()) {
            indicesFact.add(i);
            for (int j = 0; j < producciones.size(); j++) {
                if (j == i) {
                    continue;
                }
                String primeroI = producciones.get(i).substring(0, 1);
                String primeroJ = producciones.get(j).substring(0, 1);
                if (primeroI.equals(primeroJ)) {
                    indicesFact.add(j);
                }
            }
            if (indicesFact.size() > 1) {
                break;
            } else {
                indicesFact.removeAll(indicesFact);
            }
            i++;
        }
        return indicesFact;
    }
    /**
     * Factoriza las producciones, con el conjunto de indices que indican si es
     * factorizable
     * 
     * @param A se envia para asignar los factores
     * @param producciones las producciones a factorizar
     * @param indicesFact el conjunto de indices a factorizar
     */
    private void factorizar(String A, ArrayList<String> 
            producciones, Set<Integer> indicesFact) {
        Iterator iter = indicesFact.iterator();
        Integer primeraPos = (Integer) iter.next();
        String primerProd = producciones.get(primeraPos);
        String cadenaMax = "";
        int i = 0;
        boolean cadenaNoTer = true;
        while (cadenaNoTer) {
            int iguales = 0;
            for (Integer indice : indicesFact) {
                if (i >= primerProd.length() || 
                        i >= producciones.get(indice).length()) {
                    break;
                }
                if (primerProd.equals(producciones.get(indice))) {
                    continue;
                }
                String compararP = primerProd.substring(0, i + 1);
                String compararS = producciones.get(indice).substring(0, i + 1);
                if (compararP.equals(compararS)) {
                    iguales++;
                }
            }
            if (iguales == indicesFact.size() - 1) {
                cadenaMax = primerProd.substring(0, i + 1);
            } else {
                cadenaNoTer = false;
            }
            i++;
        }
        asignarFactores(A, producciones, indicesFact, cadenaMax);
    }
    /**
     * Asigna los factores a las producciones
     * 
     * @param A el elemento clave para asignar las nuevas producciones
     * @param producciones las producciones a consultar
     * @param indicesFact los indices de factoorizacion
     * @param cadenaMax la cadena maxima
     */
    private void asignarFactores(String A, ArrayList<String> producciones, 
            Set<Integer> indicesFact, String cadenaMax) {
        String AP = asignarNuevoNTerminal();
        ArrayList<String> prodANueva = new ArrayList<>();
        prodANueva.add(cadenaMax + AP);
        ArrayList<String> prodAPNueva = new ArrayList<>();
        for (int j = 0; j < producciones.size(); j++) {
            if (!indicesFact.contains(j)) {
                prodANueva.add(producciones.get(j));
            } else {
                producciones.set(j, producciones.get(j).replace(cadenaMax, ""));
                if (producciones.get(j).equals("")) {
                    producciones.set(j, "&");
                }
                prodAPNueva.add(producciones.get(j));
            }
        }
        this.producciones.put(A, prodANueva);
        this.producciones.put(AP, prodAPNueva);
        int indiceA = this.noTerminales.indexOf(A);
        this.noTerminales.add(indiceA + 1, AP);
    }
    /**
     * Devuelve los terminales
     * 
     * @return devuelve el conjunto de cadenas terminales
     */
    public ArrayList<String> getTerminales() {
        return terminales;
    }
    /**
     * devuelve los no terminales
     * 
     * @return devuelve el conjunto de cadenas no terminales
     */
    public ArrayList<String> getNoTerminales() {
        return noTerminales;
    }
    /**
     * Devuelve el inicial
     * 
     * @return devuelve la cadena inicial
     */
    public String getnTInicial() {
        return nTInicial;
    }
    /**
     * Devuelve el mapa de producciones
     * 
     * @return devuelve el conjunto hash de la producciones encontradas
     */
    public HashMap<String, ArrayList<String>> getProducciones() {
        return producciones;
    }

}
