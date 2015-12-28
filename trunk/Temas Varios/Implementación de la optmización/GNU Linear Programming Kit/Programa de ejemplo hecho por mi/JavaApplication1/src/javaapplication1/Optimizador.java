/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplication1;

import java.util.Map;

/**
 *
 * @author Zaba
 */
public interface Optimizador {

    public Map<Map<Double, Integer>, Integer> resolver(Map<Double, Integer> terminos, Integer largo);

}
