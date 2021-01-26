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

import practicaseis.lr1parser.gramatica.Produccion;
import practicaseis.lr1parser.gramatica.Simbolo;

/**
 *
 * @author kdelaot
 */
public class Item {
    public Produccion produccion;
    public Simbolo cabecera;
    public int posicionPuntual;
    
    public Item(Item param){
        this.produccion = param.produccion;
        this.posicionPuntual = param.posicionPuntual;
        this.cabecera = param.cabecera;
    }
    public Item(Produccion produccion,int posicionPuntual, Simbolo cabecera){
        this.produccion = new Produccion(produccion);
        this.posicionPuntual = posicionPuntual;
        this.cabecera = cabecera;
    }
    
    public Item(Produccion produccion, int posicionPuntual){
        this.produccion = new Produccion(produccion);
        this.posicionPuntual = posicionPuntual;
        this.cabecera = new Simbolo("#");
    }
    
    public boolean isNull(){
        return (produccion.isNull());
    }
    
    public void Display(){
        System.out.printf("(" + produccion.toString()+ "\t posicionPuntual = " + this.posicionPuntual + ", " + this.cabecera.toString() + ")");
    }
    
    @Override
    public boolean equals(Object object)
    {
        if(object==null) return false;
        
        Item item = (Item)object;
        return this.posicionPuntual == item.posicionPuntual && this.cabecera.equals(item.cabecera) && this.produccion.equals(item.produccion);
    }    
    
    @Override
    public String toString(){
        return "( " + produccion.toString()+ "\t posicionPuntual =" + this.posicionPuntual + ", " + this.cabecera.toString() + " )";
    }

}
