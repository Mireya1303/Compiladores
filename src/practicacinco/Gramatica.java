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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Clase para almacenar la gramatica de contexto libre
 * 
 * @author kdelaot
 */
public class Gramatica {
    /**
     * Los terminales de la gramatica
     */
    private ArrayList<String> terminales;
    /**
     * Los no terminales de la gramatica
     */
    private ArrayList<String> noTerminales;
    /**
     * El inicial de la gramatica
     */
    private String nTInicial;
    /**
     * El mapa de producciones de la gramatica
     */
    private HashMap<String, ArrayList<String>> producciones;
    /**
     * Constructor de clase de gramatica leyendo la misma desde un archivo
     * 
     * @param archivo el archivo desde donde se lee la gramatica
     * @throws FileNotFoundException por si no se encuentra el archivo
     * @throws IOException por si ocurre un error en la lectura
     */
    public Gramatica(File archivo) throws FileNotFoundException, IOException {

        this.terminales = new ArrayList<>();
        this.noTerminales = new ArrayList<>();
        this.producciones = new HashMap<>();

        FileReader fr = new FileReader(archivo);
        BufferedReader gramatica = new BufferedReader(fr);

        String linea;
        while ((linea = gramatica.readLine()) != null) {
            buscarTerminales(linea);
            buscarNoTerminales(linea);
            buscarProducciones(linea);
        }

        this.nTInicial = this.noTerminales.get(0);
    }
    /**
     * Construye la gramatica desde una cadena
     * 
     * @param gramatica la cadena donde esta la gramatica
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public Gramatica(String gramatica) 
            throws FileNotFoundException, IOException {

        this.terminales = new ArrayList<>();
        this.noTerminales = new ArrayList<>();
        this.producciones = new HashMap<>();

        String[] lineas = gramatica.split("\n");

        for (String linea : lineas) {
            buscarTerminales(linea);
            buscarNoTerminales(linea);
            buscarProducciones(linea);
        }

        this.nTInicial = this.noTerminales.get(0);
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
     * Devuelve los no terminales
     * 
     * @return devuelve el conjunto de cadenas no terminales
     */
    public ArrayList<String> getNoTerminales() {
        return noTerminales;
    }
    /**
     * Devuelve el no inicial
     * @return 
     */
    public String getnTInicial() {
        return nTInicial;
    }

    public HashMap<String, ArrayList<String>> getProducciones() {
        return producciones;
    }
    /**
     * Devuelve los resultados
     * 
     * @return devuelve los resultados
     */
    public String resultados() {
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
            builder.append(noTerminal).append("->").
                    append(produccion).append("\n");
        });
        System.out.println();
        builder.append("\n");
        return builder.toString();
    }

    private void buscarTerminales(String linea) {
        int indiceProduce = linea.indexOf(">");
        String cadenaTerminales = 
                linea.substring(indiceProduce + 1, linea.length());
        cadenaTerminales = cadenaTerminales.replaceAll("([A-Z])", "");
        for (int i = 0; i < cadenaTerminales.length(); i++) {
            String simbolo = cadenaTerminales.substring(i, i + 1);
            if (!simbolo.equals("&") && !this.terminales.contains(simbolo)) {
                this.terminales.add(simbolo);
            }
        }
    }

    private void buscarNoTerminales(String linea) throws IOException {
        int indiceNTerminal = linea.indexOf("-");
        String cadenaNTerminales = linea.substring(0, indiceNTerminal);
        if (!this.noTerminales.contains(cadenaNTerminales)) {
            this.noTerminales.add(cadenaNTerminales);
        }
    }

    private void buscarProducciones(String linea) throws IOException {
        String[] expresiones = linea.split("->");
        if (!this.producciones.containsKey(expresiones[0])) {
            this.producciones.put(expresiones[0], new ArrayList<>());
            this.producciones.get(expresiones[0]).add(expresiones[1]);
            } 
        else {
            this.producciones.get(expresiones[0]).add(expresiones[1]);
            }
    }
}
