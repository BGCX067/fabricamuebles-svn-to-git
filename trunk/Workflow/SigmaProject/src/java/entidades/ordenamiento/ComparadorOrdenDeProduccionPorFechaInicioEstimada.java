/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades.ordenamiento;

import entidades.OrdenDeProduccion;
import java.util.Comparator;

/**
 *
 * @author Zaba
 */
public class ComparadorOrdenDeProduccionPorFechaInicioEstimada implements Comparator<OrdenDeProduccion> {

    public int compare(OrdenDeProduccion o1, OrdenDeProduccion o2) {
        if (o1.getFechaInicioEstimada().after(o2.getFechaInicioEstimada())) {
            return -1;
        } else {
            return 1;
        }
    }
}
