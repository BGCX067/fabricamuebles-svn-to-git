/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplication1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Zaba
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Map<Double, Integer> mapa = new LinkedHashMap<Double, Integer>();
        mapa.put(new Double(1), new Integer(50));
        mapa.put(new Double(0.7), new Integer(30));
        mapa.put(new Double(0.45), new Integer(40));
        mapa.put(new Double(0.3), new Integer(100));
        mapa.put(new Double(0.15), new Integer(200));
        mapa.put(new Double(0.90), new Integer(1000));
        Optimizador o = new OptimizadorMIP(0.001);
        Map<Map<Double, Integer>, Integer> resultados = o.resolver(mapa, 10);
        /*for (Map<Double, Integer> resultado : resultados) {
        Iterator<Entry<Double, Integer>> iterator = resultado.entrySet().iterator();
        Double acumulador = 0.0;
        while (iterator.hasNext()) {
        Entry<Double, Integer> entrada = iterator.next();
        acumulador += entrada.getKey() * entrada.getValue();
        System.out.print(entrada.getKey() + " x " + entrada.getValue());
        if (iterator.hasNext()) {
        System.out.print(" + ");
        } else {
        System.out.print(" = ");
        }
        }
        System.out.println(acumulador);
        }*/
        Iterator<Entry<Map<Double, Integer>, Integer>> iterator = resultados.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<Map<Double, Integer>, Integer> entrada = iterator.next();
            System.out.println(entrada.getKey() + ". Veces: " + entrada.getValue());
        }
    }
}
