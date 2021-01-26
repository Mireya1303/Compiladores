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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sdelaot
 */
public class EntradaSalida {
    private String archivo;
    private FileReader reader;
    private BufferedReader buferReader;
    private FileWriter writer;
    private BufferedWriter buferWriter;
    public EntradaSalida() {
        this.archivo = "";
    }
    private void abrirArchivoLectura() {
        try {
            reader = new FileReader(archivo);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(
                    EntradaSalida.class.getName()).log(Level.SEVERE, null, ex);
        }
      buferReader = new BufferedReader(reader);
    }
    public String leerArchivo(String archivo) {
        this.archivo = archivo;
        abrirArchivoLectura();
        String cadena = "";
        String entrada = "";
        try {
            while((cadena = buferReader.readLine())!=null) {
                System.out.println(cadena);
                entrada += cadena;
            }
        } catch (IOException ex) {
            Logger.getLogger(
                    EntradaSalida.class.getName()).log(Level.SEVERE, null, ex);
        }
        cerrarArchivoLectura();
        return entrada;
    }
    private void cerrarArchivoLectura() {
        try {
            buferReader.close();
        } catch (IOException ex) {
            Logger.getLogger(
                    EntradaSalida.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void abrirArchivoEscritura() {
        try {
            writer = new FileWriter(archivo);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(
                    EntradaSalida.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(
                    EntradaSalida.class.getName()).log(Level.SEVERE, null, ex);
        }
      buferWriter = new BufferedWriter(writer);
      
    }
    public void escribirArchivo(String archivo, String cadena) {
        this.archivo = archivo;
        abrirArchivoEscritura();
        try {
            buferWriter.write(cadena);
        } catch (IOException ex) {
            Logger.getLogger(
                    EntradaSalida.class.getName()).log(Level.SEVERE, null, ex);
        }
        cerrarArchivoEscritura();
    }
    private void cerrarArchivoEscritura() {
        try {
            buferWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(
                    EntradaSalida.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
