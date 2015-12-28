/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades.ordenamiento;

import entidades.ActividadEspecifica;
import java.util.Comparator;

/**
 *
 * @author Zaba
 */
public class ComparadorActividadEspecificaPorSecuencia implements Comparator<ActividadEspecifica> {

    public int compare(ActividadEspecifica o1, ActividadEspecifica o2) {
        return o1.getSecuencia() - o2.getSecuencia();
    }

}
