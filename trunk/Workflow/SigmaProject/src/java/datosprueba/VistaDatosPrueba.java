/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datosprueba;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Zaba
 */
@ManagedBean
public class VistaDatosPrueba implements java.io.Serializable {
    @EJB
    private GestorDatosPrueba gestorDatosPrueba;

		// TODO Gian: SEGUIR CON PRODUCCION!!!
    // TODO Listado de Remitos de Venta
    // TODO Remitos de entrada de muebles terminados, elegir solo a partir de p.p. externas, terminadas. Si la pp fue a partir de un pedido, incrementar tmb la cantidad reservada. Siempre se incrementa el stock real
    // TODO Implementar estado ESTADO_REMITO_SIN_NECESIDAD, ESTADO_REMITO_NO_REMITIDO, ESTADO_REMITO_PARCIAL, ESTADO_REMITO_COMPLETO a las fact de venta
    // TODO Gian: pedido, alta (a partir de presupuesto, directo), y listado
    // TODO fact venta, alta(pedido finalizado, (a partir de presupuesto, directo) -> siempre que haya stock disponible. posibilidad con/sin necesidad remito. Actualizar stock si es sin remito) y listado

    public void cargarDatosPrueba() {
        gestorDatosPrueba.cargarDatosPrueba2();
    }
    public void cargarDatosPrueba2(){
        gestorDatosPrueba.cargarDatosPrueba2();
    }
}
