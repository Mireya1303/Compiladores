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
 * Clase que representa la abstraccion para un Automata Finito Deterministico 
 * AFD. Un AFD es generado a partir de un AFN a traves del algoritmo de 
 * Subconjuntos.
 * 
 * @author kdelaof
 */
public class AFD extends AutomataFinito {
   
    /**
     * Conjunto de estados del AFN contenidos
     * en cada uno de los estados de este AFD.
     */
    private Conjunto<Conjunto<Estado>> estadosAFD;
   
    /**
     * Constructor por defecto.
     */
    public AFD() {
       this(null, "");
    }
   
    /**
     * Construye un AFD con un determinado Alfabeto y una determinada expresion 
     * regular.
     * 
     * @param alfabeto El Alfabeto del AFD.
     * @param exprReg La expresion regular para el AFD.
     */
    public AFD(Alfabeto alfabeto, String exprReg) {
        super(alfabeto, exprReg);
        estadosAFD = null;
    }
   
    /**
     * Obtiene el Conjunto de Estados del AFN contenidos en cada uno de los
     * Estados del AFD.
     *
     * @return El Conjunto de Estados contenidos en cada uno de los Estados de
     * este AFD.
     */
    public Conjunto<Conjunto<Estado>> getEstadosAFD() {
        return estadosAFD;
    }

    /**
     * Establece el Conjunto de Estados
     * del AFN contenidos en cada uno de los
     * Estados de este AFD.
     * 
     * @param estadosAFD El nuevo Conjunto de Estado (s) contenidos en cada uno 
     * de los Estados del AFD.
     */
    public void setEstadosAFD(Conjunto<Conjunto<Estado>> estadosAFD) {
        this.estadosAFD = estadosAFD;
    }
   
    public String estadosAFDtoString() {
        String str = "";
       
        for (int i=0; i < estadosAFD.cantidad(); i++) {
            Conjunto<Estado> conj = estadosAFD.obtener(i);
            Estado actual = getEstado(i);
           
            str += actual + " --> " + conj + "\n";
        }
       
        return str;
    }
   
    /**
     * Devuelve la tabla de transicion de estados.
     * 
     * @return La tabla de transicion de estados.
     */
    @Override
    public TablaDeTransicion getTablaTransicion() {
        TablaDeTransicion tabla;
       
        if (getEstadosAFD() != null) {
            int cantFil = getEstados().cantidad();
            int cantCol = getAlfabeto().getCantidad() + 2;

            tabla = cargarTablaTransicion(cantFil, cantCol, 1);
            tabla.setHeaderAt("Estados del AFN", 0);

            for (int i=0; i < estadosAFD.cantidad(); i++)
                tabla.setValueAt(estadosAFD.obtener(i), i, 0);
        }
        else {
            int cantFil = getEstados().cantidad();
            int cantCol = getAlfabeto().getCantidad() + 1;

            tabla = cargarTablaTransicion(cantFil, cantCol, 0);
        }
       
        return tabla;
    }
}

