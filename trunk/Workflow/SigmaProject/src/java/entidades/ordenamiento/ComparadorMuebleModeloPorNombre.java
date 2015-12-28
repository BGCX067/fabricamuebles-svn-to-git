/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades.ordenamiento;

import entidades.MuebleModelo;
import java.util.Comparator;

/**
 *
 * @author Zaba
 */
public class ComparadorMuebleModeloPorNombre implements Comparator<MuebleModelo> {

    public int compare(MuebleModelo o1, MuebleModelo o2) {
        return o1.getNombre().compareToIgnoreCase(o2.getNombre());
    }

}
