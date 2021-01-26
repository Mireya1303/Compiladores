/*
 *   INSTITUTO POLITÉCNICO NACIONAL
 *   ESCUELA SUPERIOR DE CÓMPUTO
 *   COMPILADORES
 *   ALUMNA:    DE LA O FLORES KARLA MIREYA - 3CV6
 *   PRACTICA:  #6
 *   PROFESOR:  NORMAN RAFAEL SAUCEDO DELGADO
 *   
 */
package practicaseis;

import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author kdelaot
 */
public class Estado {
    public int indice;
    public boolean aceptado = false;
    /**
     * Empleada para calcular el cierre
     */
    public boolean visitado = false; 
    public ArrayList<Item> items = new ArrayList<>();
    
    public Estado(Estado estado){
        
        this.items.addAll(estado.items);
        this.aceptado = estado.aceptado;
    }
    
    public Estado(ArrayList<Item> conjuntoDeEstados){
     
        items.addAll(conjuntoDeEstados);  // los parametros
        
        // verifica si el estado esta en aceptado
        items.stream().filter((item) -> (item.posicionPuntual == item.produccion.rhs.size())).forEachOrdered((_item) -> {
            aceptado = true;
        });
    }
    
    public void Stamp(int i){indice = i;}
    

    public void Display(){
                
        System.out.println("Estado " + indice + " : ");
        for(Item item: items){
            System.out.printf("%50s \n", item.toString()); // right align
        }               
        
    }
    
    public boolean isNull(){
        return items.isEmpty();
    }
    
    @Override      
    public boolean equals(Object object)
    {
        Estado estado = (Estado)object;
        if(this.items.size() != estado.items.size())
            return false;
        else{
            for(int i=0;i<estado.items.size();i++)
            {
                if(!this.items.contains(estado.items.get(i)))
                    return false;
            }
        }
        
        return true;
    }
    
}
