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

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;
import practicaseis.Item;
import practicaseis.Par;
import practicaseis.Estado;
import practicaseis.Util;
import practicaseis.lr1parser.gramatica.Produccion;
import practicaseis.lr1parser.gramatica.Simbolo;


/**
 *
 * @author kdelaof
 */
public class Parser {
    Util asistente = new Util();
    ArrayList<Estado> automata;
    ArrayList<Produccion> listaDeProducciones;
    ArrayList<Simbolo> alfabeto;
    ArrayList<Par> primero;
    ArrayList<Par> sigueme;
    ArrayList<Estado> estados;
    Pila<Estado> pila;
    // Parseando tabla ( numeroDeEstado, simbolo, accionString )
    Table<Integer, Simbolo, String> tabla = HashBasedTable.create(); 
    int numeroDeProduccionesInicial = 1;
    
    
    public void leer() throws IOException{
        String linea;
        listaDeProducciones = new ArrayList<>();
        alfabeto = new ArrayList<>();
        try {
            String path = 
                "C:/Users/mirey/Documents/GitHub/Compiladores/src/practicaseis/";
            //System.out.println( "A VER" );
            BufferedReader bufferreader = 
                    new BufferedReader(
                            new FileReader(path + "gramatica.txt"));
            //System.out.println( "PASO" );
            // lee la primera liena (indicando el numero del simbolo inicial)
            // de las producciones ) 
            linea = bufferreader.readLine(); 
            numeroDeProduccionesInicial = Integer.parseInt(linea);
            // empieza leyendo la primera produccion hasta el final del archivo
            linea = bufferreader.readLine(); 
            
            while (linea != null) {     
                listaDeProducciones.add(
                        new Produccion(linea.split(":")[0], 
                                linea.split(":")[1]));
                linea = bufferreader.readLine();
            }           
            // agrega todos los simbolos en el alfabeto
            for(Produccion prod: listaDeProducciones){ 
                if(!alfabeto.contains(prod.lhs)) alfabeto.add(prod.lhs);
                for(Simbolo symb: prod.rhs){
                    if(!alfabeto.contains(symb)) alfabeto.add(symb);
                }
            }
            // agrega EPSILON ( # ) en caso de que la gramatica no especifique # s
            if(!alfabeto.contains(new Simbolo("#)"))) {
                alfabeto.add(new Simbolo("#"));
                } 
                                                                                    

        } catch (IOException ex) {} // atrapa algo            
        
    }        
    
    public Estado irHacia(Estado estado, Simbolo simbolo){
        
        ArrayList<Item> init = new ArrayList<>();
        
        for(Item item: estado.items){
            if(item.posicionPuntual + 1 <= item.produccion.rhs.size()){
                if(item.produccion.rhs.get(
                        item.posicionPuntual).equals(simbolo)){
                    init.add(new Item(item.produccion, 
                            item.posicionPuntual + 1, item.cabecera));
                }
            }
        }

        return cerrar(init);
    }
    
    public Estado cerrar(ArrayList<Item> inicio){
        ArrayList<Item> conjuntoDeItems = new ArrayList<>();
        conjuntoDeItems.addAll(inicio);
        
        for(int i=0;i<conjuntoDeItems.size();i++){
            Item item = conjuntoDeItems.get(i);
            // For an initial item A -> a.Bc
            for(Produccion prod: listaDeProducciones){
             
                if(item.posicionPuntual + 1 <= item.produccion.rhs.size()){
                    // si existe B -> e 
                    if(item.produccion.rhs.get(
                            item.posicionPuntual).equals(prod.lhs)){ 

                        // calcula FIRST(c) para la cabecera
                       List<Simbolo> listaParticionada = 
                               item.produccion.rhs.subList(
                                       item.posicionPuntual + 1, 
                                       item.produccion.rhs.size());

                       ArrayList<Simbolo> listaArregloParticionada = 
                               new ArrayList<>();
                       listaArregloParticionada.addAll(listaParticionada);
                       listaArregloParticionada.add(new Simbolo(item.cabecera));

                       // FIRST(c) 
                       ArrayList<Simbolo> primeroEnParticion = 
                               encontraPrimeroRHS(listaArregloParticionada);

                       for(Simbolo symb: primeroEnParticion){
                           Item newItem = new Item(prod, 0, symb);
                           
                           if(!conjuntoDeItems.contains(newItem)){
                               conjuntoDeItems.add(newItem);
                           }
                       }
                        
                    }


                }
            }
        }
        return new Estado(conjuntoDeItems);
    }   
    
    public boolean esProduccionNula(Produccion produccion){
        if(produccion.rhs.size() == 
                1 && produccion.rhs.contains(new Simbolo("#")))
            return true;
        
        boolean igualado = false;
        // si este simbolo puede igualar a uno de  lhs de las producciones, 
        // entonces se igualado es true, de otro modo
        // produccion.rhs contiene todos los terminales y no es nulo
        for(Simbolo simbolo: produccion.rhs){ 
            for(Produccion estaProduccion: listaDeProducciones){
                // si uno de los simbolos en rhs es terminal entonces no es nulo 
                if(asistente.esTerminal(simbolo) && 
                        !simbolo.equals(new Simbolo("#"))) return false;
                
                if(estaProduccion.lhs.equals(simbolo)){
                    // senial que prod.rhs no contiene todos los terminales 
                    igualado = true; 
                    // devuelve false inmediatamente cuando uno de los simbolos
                    // en rhs no esta
                    if(!esProduccionNula(estaProduccion)) 
                        return false;                   // nulo
                }                    
            }                
        }
        // either matched is false and prod is not nullable, or else matched is 
        // true and subsequent calls to nullableProduction(thisProd) will 
        // return false if all symbols of prod.rhs is not nullable, if no call 
        // to nullableProduction(thisProd) returns false, prod is nullable in 
        // which case matched is turned on. Therefore we just have to return 
        // 'matched'
        return igualado; 
    }
    
    public void calcularAlPrimero(){
        primero = new ArrayList<>();         
        for(Simbolo symb: alfabeto){ 
            // inicializa FIRST(X) donde X es un terminal en si mismo                    
            if(asistente.esTerminal(symb)) {
                primero.add(
                        new Par(symb, new ArrayList<>(Arrays.asList(symb))));                
                }
            else{                
                noEsTerminalElPrimero(symb);
            }
        }        
    }
    //symb is a non-terminal,
    public void noEsTerminalElPrimero(Simbolo simbolo){  
        // since we already initialized all FIRST of terminals to themselves
        if(primero.contains(new Par(simbolo))){ // Ignora parametros terminales     
            return;
        }
        // val represents values of FIRST(symb)  
        ArrayList<Simbolo> val = new ArrayList<>();  
        
        for(Produccion prod: listaDeProducciones){
            if(prod.lhs.equals(simbolo)){ 
                // If there is a Production X → ε
                if(esProduccionNula(prod)){  
                    Simbolo tmp = new Simbolo("#");
                    // then add ε to first(X) 
                    if(!val.contains(tmp))
                        val.add(tmp); 
                }
                // firstOfRHS computes FIRST of an ArrayList<Symbol> rhs
                if(!val.containsAll(encontrarPrimeroRHS(prod.rhs))) 
                val.addAll(encontrarPrimeroRHS(prod.rhs));
                
            }
        }
        // Finally add the pair into FIRST
        primero.add(new Par(simbolo, val)); 
    }
    // similar to FirstOfRHS without having to call nonTermFirst
    public ArrayList<Simbolo> encontraPrimeroRHS(ArrayList<Simbolo> rhs){ 
        ArrayList<Simbolo> valores = new ArrayList<>();
        
        for(int i=0;i<rhs.size();i++){ 
            Simbolo rhsSymb = rhs.get(i);
            // if rhs[0] it's a terminal then return that terminal only
            if(i == 0 && asistente.esTerminal(rhsSymb)){ 
                valores.add(new Simbolo(rhsSymb));           
                return valores;                             
            }
            // FIRST(rhsSymb) 
            ArrayList<Simbolo> firstOfRhsSymb = 
                    primero.get(primero.indexOf(
                            new Par(new Simbolo(rhsSymb)))).listaDeSimbolos;
            // if rhs[0] is a non-terminal and
            if(i == 0 ){        
                //  FIRST(rhsSymb)doesnt contain epsilon
                if(!firstOfRhsSymb.contains(new Simbolo("#"))){ 
                    // then FIRST(symb) = FIRST(rhsSymb)
                    for(Simbolo tmp: firstOfRhsSymb)              
                       if(!valores.contains(tmp))    
                           valores.add(tmp);               
                   return valores;
                }  
            }   // otherwise, keep on considering rhs[i] 
                for(Simbolo tmp: firstOfRhsSymb)  
                       if(!valores.contains(tmp))    
                           valores.add(tmp);              
                        
        }
        return valores;
    }
    // return FIRST(rhs)
    public ArrayList<Simbolo> encontrarPrimeroRHS(ArrayList<Simbolo> rhs){ 
        
        ArrayList<Simbolo> val = new ArrayList<>();
        boolean nullable = true;
        // If there is a Production X → Y1Y2..Yk
        for(int i=0;i< rhs.size();i++){           
            Simbolo rhsSymb = rhs.get(i);

            // compute FIRST(Yi) if Yi is not a terminal
            if(!asistente.esTerminal(rhsSymb)) noEsTerminalElPrimero(rhsSymb); 
            else {          
                // But if Y1 is a terminal, then add Y1 to FIRST(X) and done
                if(i==0 && !val.contains(rhsSymb))
                    val.add(rhsSymb);
                return val;
            }                    

            // rhsSymbFirst holds FIRST(Yi)
            ArrayList<Simbolo> rhsSymbFirst = 
                    primero.get(primero.indexOf(
                            new Par(new Simbolo(rhsSymb)))).listaDeSimbolos;                                  
           
            
            if(i==0){ // if Y1 is not a non-terminal and     
                //if First(Y1) doesn't contain ε
               if(!rhsSymbFirst.contains(new Simbolo("#"))){   
                   //  then FIRST(X) = FIRST(Y1) and done
                   for(Simbolo rhsSymbFirstSymb: rhsSymbFirst)    
                       if(!val.contains(rhsSymbFirstSymb))    
                           val.add(rhsSymbFirstSymb);               
                   return val;
               }
            }                                             
            // for the remaining Yi
            // , add all FIRST(Yi) to FIRST(X)
            for(Simbolo rhsSymbFirstSymb: rhsSymbFirst)  
                // but consider FIRST(Y(i+1)Y(i+2)....Yk) too
                if(!val.contains(rhsSymbFirstSymb))   
                           val.add(rhsSymbFirstSymb);
            if(!rhsSymbFirst.contains(new Simbolo("#")))
                nullable = false;
        }
        if(nullable) val.add(new Simbolo("#"));
        return val;
    }
        
    public void calcularSiguiente(){
        sigueme = new ArrayList<>();
        ArrayList<Produccion> tmpProd = new ArrayList<>();
        
        // Start symbol always have epsilon followed
        sigueme.add(new Par(new Simbolo(alfabeto.get(0)), 
                new ArrayList<>(Arrays.asList(new Simbolo("#")))));
        
        // Two iterations over the alphabet to compute FOLLOW set 
        for(Simbolo symb: alfabeto){
            for(Produccion prod: listaDeProducciones){
                   encontrarSimboloEnMedio(symb, prod);
            }                                   
        }  
        
        for(Simbolo symb: alfabeto){
            for(Produccion prod: listaDeProducciones){
                   encontrarSimboloAlFinal(symb, prod);
            }                                   
        } 
    }
    
    // Compute FOLLOW set for B such that B is at the end of some productions 
    // A -> aB or A -> aBb where FIRST(b) = e
    public void encontrarSimboloAlFinal(Simbolo simbolo, 
            Produccion produccion ) {
        ArrayList<Simbolo> rhs = produccion.rhs;
        
         for(Simbolo rhsSymbol: rhs){
            if(!asistente.esTerminal(rhsSymbol) &&  rhsSymbol.equals(simbolo)){
                
                // Partition from symb till the end
                List<Simbolo> partitionList = 
                        rhs.subList(
                                rhs.indexOf(
                                    new Simbolo(simbolo)) + 1 ,rhs.size());
                ArrayList<Simbolo> partitionArrayList = new ArrayList<>();
                partitionArrayList.addAll(partitionList);                 
                
                // FOLLOW( partitionArrayList ) 
                ArrayList<Simbolo> partitionFollow = 
                        encontraPrimeroRHS(partitionArrayList); 
                
                // if either symb is at the end or FIRST(partition from symb on) 
                // contains epsilon 
                if((rhs.indexOf(new Simbolo(rhsSymbol)) == rhs.size() - 1) || 
                        partitionFollow.contains(new Simbolo("#"))){
                    Simbolo lhs = produccion.lhs;
                    
                    // FOLLOW(lhs)
                    ArrayList<Simbolo> lhsFollow = 
                            sigueme.get(
                                sigueme.indexOf(
                                    new Par(
                                        new Simbolo(lhs)))).listaDeSimbolos;    
                    
                    
                    // FOLLOW(rhsSymbol)
                    ArrayList<Simbolo> rhsSymbolFollow = 
                            sigueme.get(
                                sigueme.indexOf(
                                    new Par(
                                        new Simbolo(
                                                rhsSymbol)))).listaDeSimbolos;                    
                    
                    // FOLLOW(rhsSymbol) = U {FOLLOW(lhs)}
                    for(Simbolo temp: lhsFollow){
                        if(!rhsSymbolFollow.contains(temp))
                             rhsSymbolFollow.add(temp);
                    }
                                      
                }
            }
         }
    }
    
    // Compute FOLLOW set for B such that B is in the midle of rhs of some 
    // productions A -> aBb
    public void encontrarSimboloEnMedio(Simbolo symb, Produccion produccion ){        
        ArrayList<Simbolo> rhs = produccion.rhs;
        
        for(Simbolo rhsSymbol: rhs){
            if(!asistente.esTerminal(symb) && rhsSymbol.equals(symb)){
                
                // Partition from symb till the end
                List<Simbolo> partitionList = 
                        rhs.subList(rhs.indexOf(
                                new Simbolo(symb)) + 1 ,rhs.size());
                ArrayList<Simbolo> partitionArrayList = new ArrayList<>();
                partitionArrayList.addAll(partitionList);                 
                
                // FOLLOW( partitionArrayList ) 
                ArrayList<Simbolo> partitionFollow = 
                        encontraPrimeroRHS(partitionArrayList); 
                
                // Either create a new entry in FOLLOW or append to the existing 
                // FOLLOW entry
                if(!sigueme.contains(new Par(new Simbolo(symb)))){
                    sigueme.add(new Par(new Simbolo(symb), partitionFollow));
                }else{
                    ArrayList<Simbolo> curFollowOfSymb = 
                            sigueme.get(
                                sigueme.indexOf(
                                    new Par(
                                        new Simbolo(symb)))).listaDeSimbolos;
                    for(Simbolo pFSymb: partitionFollow){
                        if(!curFollowOfSymb.contains(new Simbolo(pFSymb)))
                            curFollowOfSymb.add(new Simbolo(pFSymb));
                    }
                }                                
            }
        }             
                     
    }
    
    public void inicializar() throws IOException {    
        leer();
        calcularAlPrimero();
        calcularSiguiente();
        
        ArrayList<Item> conjuntoDeItemsInicial = new ArrayList<>();
       
        for(int i=0;i<numeroDeProduccionesInicial;i++){
           conjuntoDeItemsInicial.add(
                   new Item(listaDeProducciones.get(i), 0 , new Simbolo("#")));       
        }
        Estado initState = new Estado(cerrar(conjuntoDeItemsInicial));
        initState.Stamp(0);
        
        estados = new ArrayList<>();        
        estados.add(initState);
        
        boolean cambio = true;
        
        while(cambio){ // fixed-point algorithm to build the LR(1) automata
            
            int tamanioAnterior = estados.size();
            for(int i=0;i<estados.size();i++){
                
                if(estados.get(i).visitado == false){
                    
                    estados.get(i).visitado = true;
                    Estado desdeEstado = estados.get(i);                   
                    
                    for(Simbolo simbolo: alfabeto){
                        
                        String accionATomar = "";

                        Estado haciaEstado = irHacia(desdeEstado, simbolo);     
                          
                        if(!haciaEstado.isNull() && 
                                !estados.contains(haciaEstado)) {       
                            haciaEstado.Stamp(estados.size());
                            estados.add(haciaEstado);                                                                                      
                        }
                                                
                        if(!haciaEstado.isNull()){                            
                            
                            if(haciaEstado.aceptado){
                                
                                ArrayList<Item> finalItems = new ArrayList<>();
                                finalItems = estadoAlFinalItem(haciaEstado);        
                                
                                for(Item finalItem: finalItems){
                                    int prodParaReducirPorID = 
                                            itemAlFinalProduccion(finalItem);                                    
                                    accionATomar = "r" + prodParaReducirPorID;
                                    tabla.put(haciaEstado.indice, 
                                            finalItem.cabecera, accionATomar); 
                                    if(tabla.contains(haciaEstado.indice, 
                                            finalItem.cabecera)){

                                    }
                                }                                 
                            }
                            
                            if(asistente.esTerminal(simbolo)){
                                accionATomar = "s" + haciaEstado.indice;                                
                            }else{
                                accionATomar = "" + haciaEstado.indice;
                            }
                            // Fill in the action for the entry
                            tabla.put(desdeEstado.indice, simbolo , 
                                    accionATomar); 
                        }
                      
                        
                        
                    }
                }
            }
            
            cambio = (estados.size() != tamanioAnterior);
        }
        // first state and start symbol signals sucessful parsing
        tabla.put(initState.indice, alfabeto.get(0), "aceptado"); 

        for(Estado state: estados){
            state.Display();
        }
        
    }
    
    public void parsear( ArrayList<Simbolo> entrada ){
        
        pila = new Pila<>(); // initialize the stack
        pila.empujar(estados.get(0));
        
        int i=0; // index of input
        
        while( i < entrada.size() ){
            
            String accion ="ninguna";
            Estado top = pila.ojear();
            
            if(top.aceptado){
                boolean needLookahead = true;
                Simbolo lookahead = entrada.get(i+1);                
                for(Item item: estadoAlFinalItem(top)){
                    if(item.cabecera.equals(new Simbolo("#"))){
                        accion = tabla.get(top.indice, new Simbolo("#"));
                        needLookahead = false;
                        break;
                    }
                }
                // extract productionID to reduce
                if(needLookahead)
                     accion = tabla.get(top.indice, lookahead); 
                
                int prodToReduceByID = 
                        Character.getNumericValue(accion.charAt(1));
                // extraction production to reduce
                Produccion prodToReduceBy = 
                        listaDeProducciones.get(prodToReduceByID); 
                
                // pop states corresponding to the production's rhs
                for (Simbolo rh : prodToReduceBy.rhs) {          
                    pila.extraer();
                }
                // fromState being the state after popping
                Estado fromState = pila.ojear();         
                // lookahead is replaced by lhs of the production reduced
                lookahead = new Simbolo(prodToReduceBy.lhs);  
                // accepting states trigger 2 actions, this is the 2nd  
                accion = tabla.get(fromState.indice, lookahead);   
                
                if(accion.endsWith("aceptado"))break;                               
                Estado toState = estados.get(Integer.parseInt(accion)); 
                // if ACCEPT then break out of the loop
                if(tabla.get(toState.indice, 
                        new Simbolo("#")).equals("aceptado")) break;                
                // Pushing the new state which correspond to
                pila.empujar(toState);                 
                
            }else{
                Simbolo curSymb = entrada.get(i);
                
                Estado toState;
                accion = tabla.get(top.indice, curSymb);
                // if it's a non-terminal
                if(!asistente.esTerminal(curSymb)) 
                    // then action has only one digit
                    toState = estados.get(Integer.parseInt(accion)); 
                else{                            
                    // if it's a terminal
                    // then action is of "s" + number
                    int integer = Integer.valueOf(accion.substring(1)); 
                    
                    toState = estados.get(integer);                     
                }
                
                pila.empujar(toState);
                i++;
            }
            
        }
        
        if(pila.ojear().indice == 0){
            System.out.println("Parseo terminado \n\t" + entrada.toString() + 
                    " pertenece a la gramatica descrita en gramatica.txt");
        }else{
            System.out.println(
                "no se puede reconocer esta sentencia, estado superior es " +
                            pila.ojear().indice);
        }
    }
    
    // Given an accepting state, return a final item
    public ArrayList<Item> estadoAlFinalItem(Estado state){ 
        if(!state.aceptado)
            return null;        
        
        ArrayList<Item> ret = new ArrayList<>();
        for(Item item: state.items){
            if(item.posicionPuntual == item.produccion.rhs.size()){
                ret.add(item);
            }
        }       
        return ret;
    }
    // given an item, return the number of production matches that item
    public int itemAlFinalProduccion(Item item){ 
        if(item==null) return -1;
        
        for(Produccion prod: listaDeProducciones){
            if(prod.lhs.equals(item.produccion.lhs) && 
                    prod.rhs.equals(item.produccion.rhs))
                return listaDeProducciones.indexOf(prod);
        }     
        return -1;
    }
    
    public void desplegar(List<?> list){        
        System.out.println(Arrays.toString(list.toArray()));
    }

   
}
