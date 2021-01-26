/*
 *   INSTITUTO POLITÉCNICO NACIONAL
 *   ESCUELA SUPERIOR DE CÓMPUTO
 *   COMPILADORES
 *   ALUMNA:    DE LA O FLORES KARLA MIREYA - 3CV6
 *   PRACTICA:  #6
 *   PROFESOR:  NORMAN RAFAEL SAUCEDO DELGADO
 *   
 */
package practicaseis.interfaceprimaria;

import java.util.ArrayList;
import java.util.EmptyStackException;

/**
 *
 * @author kdelaof
 */
public class Pila<E> {

    public ArrayList<E> al;

    public Pila() {
        al = new ArrayList<E>();
    }

    public void empujar(E item) {
        al.add(item);
    }

    public E extraer() {
        if (!estaVacia()) {
            return al.remove(verificarTamanio() - 1);
        } else {
            throw new EmptyStackException();
        }
    }

    public boolean estaVacia() {
        return (al.size() == 0);
    }

    public E ojear() {
        if (!estaVacia()) {
            return al.get(verificarTamanio() - 1);
        } else {
            throw new EmptyStackException();
        }
    }

    public int verificarTamanio() {
        return al.size();
    }

    @Override
    public String toString() {
        return "MyStack [al=" + al.toString() + "]";

    }
}
