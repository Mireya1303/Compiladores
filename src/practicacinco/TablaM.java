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


import java.util.HashMap;

/**
 *
 * @author kdelaot
 */
public class TablaM {

    private HashMap<String, HashMap<String, String>> tablaM;

    public TablaM(GSVicio gSVicio, Primero primeros, Siguiente siguientes) {
        this.tablaM = new HashMap<>();

        construirTablaM(gSVicio);

        calcularTablaM(primeros, siguientes);
    }
    
    public String resultados() {
        StringBuilder builder = new StringBuilder();
        System.out.println("Tabla M:");
        builder.append("Tabla M:\n");
        this.tablaM.forEach((noTerminal,conjunto)->{
            System.out.println(noTerminal + ": " + conjunto);
            builder.append(noTerminal).append(": ").append(conjunto);
            builder.append("\n");
        });
        System.out.println();
        builder.append( "\n" );
        return builder.toString();
    }
    
    public HashMap<String, HashMap<String, String>> getTablaM() {
        return tablaM;
    }

    public String getTablaM(String noTerminal, String terminal) {
        return tablaM.get(noTerminal).get(terminal);
    }
    
    private void construirTablaM(GSVicio gSVicio) {
        for (String noTerminal : gSVicio.getNoTerminales()) {
            this.tablaM.put(noTerminal, new HashMap<>());
            for (String terminal : gSVicio.getTerminales()) {
                this.tablaM.get(noTerminal).put(terminal, "");
            }
            this.tablaM.get(noTerminal).put("$", "");
        }
    }

    private void calcularTablaM(Primero primeros, Siguiente siguientes) {
        primeros.getPrimeros().forEach((noTerminal, terminales) -> {
            for (String terminal : terminales) {
                primeros.getValoresM().get(noTerminal).forEach(
                        (produccion, produce) -> {
                    if (produce.contains("&")) {
                        for (String simbolo : 
                                siguientes.getSiguientes().get(noTerminal)) {
                            String valor = noTerminal + "->" + produccion;
                            this.tablaM.get(noTerminal).put(simbolo, valor);
                        }
                    }
                    if (!produccion.equals("&")) {
                        if (produce.contains(terminal)) {
                            String valor = noTerminal + "->" + produccion;
                            this.tablaM.get(noTerminal).put(terminal, valor);
                        }
                    } else {
                        for (String vacio : 
                                siguientes.getSiguientes().get(noTerminal)) {
                            String valor = noTerminal + "->&";
                            this.tablaM.get(noTerminal).put(vacio, valor);
                        }
                    }
                });
            }
        });
    }
}
