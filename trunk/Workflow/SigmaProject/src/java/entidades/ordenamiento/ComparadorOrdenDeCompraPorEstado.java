/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades.ordenamiento;

import entidades.OrdenDeCompra;
import java.util.*;

/**
 *
 * @author Zaba
 */
public class ComparadorOrdenDeCompraPorEstado implements Comparator<OrdenDeCompra> {

    public int compare(OrdenDeCompra o1, OrdenDeCompra o2) {
        if (o1 != null && o2 != null) {
            if (o1.getEstado().equalsIgnoreCase(o2.getEstado())) {
                return 0;
            } else if (o1.getEstado().equalsIgnoreCase(OrdenDeCompra.ESTADO_RECIBIDO_COMPLETO)) {
                return -1;
            } else if (o1.getEstado().equalsIgnoreCase(OrdenDeCompra.ESTADO_RECIBIDO_PARCIAL)
                    && o2.getEstado().equalsIgnoreCase(OrdenDeCompra.ESTADO_NO_RECIBIDO)) {
                return -1;
            } else if (o1.getEstado().equalsIgnoreCase(OrdenDeCompra.ESTADO_RECIBIDO_PARCIAL)
                    && o2.getEstado().equalsIgnoreCase(OrdenDeCompra.ESTADO_RECIBIDO_COMPLETO)) {
                return 1;
            }
            return 1;
        } else {
            throw new IllegalArgumentException("Se han pasado remitos nulos como referencia...");
        }
    }
}
