/*
 *   INSTITUTO POLITÉCNICO NACIONAL
 *   ESCUELA SUPERIOR DE CÓMPUTO
 *   COMPILADORES
 *   ALUMNA:    DE LA O FLORES KARLA MIREYA - 3CV6
 *   PRACTICA:  Utilerias para las practicas
 *   PROFESOR:  NORMAN RAFAEL SAUCEDO DELGADO
 *   
 */
package utileria;

import java.util.Vector;

/**
 *
 * @author kdelaot
 */
public class Log {

    /**
     * Lista de cadenas que se van
     * agregando al Log.
     */
    private Vector<String> cadenas;
   
    /**
     * Construye un nuevo Log que
     * no contiene ningun texto.
     */
    public Log() {
        cadenas = new Vector<String>();
    }
   
    /**
     * Agrega una linea al Log.
     * @param linea La linea agregada al Log.
     * @return this (para encadenamiento de metodos).
     */
    public Log agregar(String linea) {
        cadenas.add(linea);
        return this;
    }
   
    /**
     * Agrega un caracter de fin de linea al Log.
     * @return this (para encadenamiento de metodos).
     */
    public Log nuevaLinea() {
        cadenas.add("\n");
        return this;
    }
   
    /**
     * Vacia las cadenas de este Log.
     * @return this (para encadenamiento de metodos).
     */
    public Log vaciar() {
        cadenas.clear();
        return this;
    }
   
    @Override
    public String toString() {
        StringBuffer str = new StringBuffer();

        for (String s : cadenas)
            str.append(s);
       
        return str.toString();
    }
}
