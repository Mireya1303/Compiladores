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

import utileria.Alfabeto;


/**
 * Clase que representa la abstraccion para un Automata Finito
 * No deterministico (AFN). Un AFN es contruido a partir de una
 * expresion regular a traves de las construcciones de Thompson.
 * 
 * @author kdelaof
 */
public class AFN extends AutomataFinito {
       
    /**
     * Constructor por defecto.
     */
    public AFN() {
       super();
    }
   
    /**
     * Construye un AFN con un determinado Alfabeto
     * y una determinada expresion regular.
     * @param alfabeto El Alfabeto de este AFN.
     * @param exprReg La expresion regular para este AFN.
     */
    public AFN(Alfabeto alfabeto, String exprReg) {
        super(alfabeto, exprReg);
    }
   
    /**
     * Retorna la tabla de transicion de estados.
     * @return La tabla de transicion de estados.
     */
    @Override
    public TablaDeTransicion getTablaTransicion() {
        int cantFil = getEstados().cantidad();
        int cantCol = getAlfabeto().getCantidad() + 2;
       
        return cargarTablaTransicion(cantFil, cantCol, 0);
    }
}

