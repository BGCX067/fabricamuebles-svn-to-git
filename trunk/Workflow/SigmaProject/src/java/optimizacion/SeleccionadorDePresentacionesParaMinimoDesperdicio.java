/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package optimizacion;

import entidades.ArticuloPresentacion;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Zaba
 */
public interface SeleccionadorDePresentacionesParaMinimoDesperdicio {
    public Map<ArticuloPresentacion, Integer> devolverSeleccionesOptimas(BigDecimal cantidadTotalNecesaria, List<ArticuloPresentacion> presentacionesDisponibles);
}
