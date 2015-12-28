/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades.ordenamiento;

import entidades.MuebleTipo;
import java.util.Comparator;

/**
 *
 * @author Zaba
 */
public class ComparadorMuebleTipoPorNombre implements Comparator<MuebleTipo> {

    public int compare(MuebleTipo o1, MuebleTipo o2) {
        return o1.getNombre().compareToIgnoreCase(o2.getNombre());
    }

}
