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

import practicauno.AFD;

/**
 * Clase que representa la abstraccion para un Automata Finito
 * Deterministico con estados minimos (AFDMin), en cuanto a
 * cantidad de estados se refiere.<br><br>
 * Un AFDMin es generado a partir de un AFD a traves del algoritmo
 * de Minimizacion de Estados.<br><br>
 * Antes del dicho algoritmo, deben eliminarse los estados inalcanzables
 * realizando un recorrido DFS o BFS a partir del estado inicial
 * del AFD. Luego de dicho algoritmo deben eliminarse aquellos estados
 * identidades que no sean estados finales.<br><br>
 * El AFDMin contiene tres instancias de AFD que representan a los
 * AFDs tras cada uno de estos algoritmos.
 * 
 * @author kdelaof
 */
public class AFDMin {
    /**
     * AFD original.
     */
    private AFD afdOriginal;
   
    /**
     * AFD resultante luego de aplicar la
     * eliminacion de estados inalcanzables.
     */
    private AFD afdPostInalcanzables;
   
    /**
     * AFD resultante luego de aplicar el
     * algoritmo de minimizacion.
     */
    private AFD afdPostMinimizacion;
   
    /**
     * AFD resultante luego de aplicar la
     * eliminacion de estados identidades
     * no finales.
     */
    private AFD afdPostIdentidades;
   
    /**
     * Construye un AFDMin.
     * @param afdOriginal El AFD a partir del cual fue construido este AFDMin.
     * @param afdPostInalcanzables El AFD resultante de la eliminacion de 
     * estados inalcanzables.
     * @param afdPostMinimizacion El AFD resultante del proceso de minimizacion.
     * @param afdPostIdentidades El AFD resultante de la eliminacion de 
     * estados identidades.
     */
    public AFDMin(AFD afdOriginal, AFD afdPostInalcanzables, 
            AFD afdPostMinimizacion, AFD afdPostIdentidades) {
        this.afdOriginal          = afdOriginal;
        this.afdPostInalcanzables = afdPostInalcanzables;
        this.afdPostMinimizacion  = afdPostMinimizacion;
        this.afdPostIdentidades   = afdPostIdentidades;
    }
   
    /**
     * Obtiene el AFD a partir del cual fue construido este AFDMin.
     * @return El AFD a partir del cual fue construido este AFDMin.
     */
    public AFD getAfdOriginal() {
        return afdOriginal;
    }

    /**
     * Obtiene el AFD resultante de la eliminacion de estados inalcanzables.
     * @return El AFD resultante de la eliminacion de estados inalcanzables.
     */
    public AFD getAfdPostInalcanzables() {
        return afdPostInalcanzables;
    }

    /**
     * Obtiene el AFD resultante del proceso de minimizacion.
     * @return El AFD resultante del proceso de minimizacion.
     */
    public AFD getAfdPostMinimizacion() {
        return afdPostMinimizacion;
    }

    /**
     * Obtiene el AFD resultante de la eliminacion de estados identidades.
     * @return El AFD resultante de la eliminacion de estados identidades.
     */
    public AFD getAfdPostIdentidades() {
        return afdPostIdentidades;
    }
   
    /**
     * Verifica si la eliminacion de estados inalcanzables produjo algún
     * cambio sobre el AFD original.
     * @return true si la eliminacion de estados inalcanzables
     * produjo algún cambio sobre el AFD original, false
     * en caso contrario.
     */
    public boolean inalcanzablesEliminados() {
        if (afdOriginal.toString().equals(afdPostInalcanzables.toString()))
            return false;
        else
            return true;
    }
   
    /**
     * Verifica si la eliminacion de estados identidades produjo algún
     * cambio sobre el AFD resultante de la minimizacion.
     * @return true si la eliminacion de estados identidades
     * produjo algún cambio sobre el AFD resultante de la
     * minimizacion, false en caso contrario.
     */
    public boolean identidadesEliminados() {
        if (afdPostMinimizacion.toString().equals(
                afdPostIdentidades.toString()))
            return false;
        else
            return true;
    }
}
