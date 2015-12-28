/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades.ordenamiento;

import entidades.Remito;
import java.util.*;

/**
 *
 * @author Zaba
 */
public class ComparadorRemitoPorEstado implements Comparator<Remito> {

    public int compare(Remito o1, Remito o2) {
        if (o1 != null && o2 != null) {
            if (o1.getEstado().equalsIgnoreCase(o2.getEstado())) {
                return 0;
            } else if (o1.getEstado().equalsIgnoreCase(Remito.ESTADO_FACTURADO_COMPLETO)) {
                return -1;
            } else if (o1.getEstado().equalsIgnoreCase(Remito.ESTADO_FACTURADO_PARCIAL)
                    && o2.getEstado().equalsIgnoreCase(Remito.ESTADO_NO_FACTURADO)) {
                return -1;
            } else if (o1.getEstado().equalsIgnoreCase(Remito.ESTADO_FACTURADO_PARCIAL)
                    && o2.getEstado().equalsIgnoreCase(Remito.ESTADO_FACTURADO_COMPLETO)) {
                return 1;
            }
            return 1;
        } else {
            throw new IllegalArgumentException("Se han pasado remitos nulos como referencia...");
        }
    }
}
