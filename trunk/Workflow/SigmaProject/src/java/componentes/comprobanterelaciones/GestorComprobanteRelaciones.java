/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes.comprobanterelaciones;

import entidades.ComprobanteRelaciones;
import entidades.Factura;
import entidades.OrdenDeCompra;
import entidades.OrdenDeProduccion;
import entidades.Remito;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Zaba
 */
@Stateless
public class GestorComprobanteRelaciones {

    @PersistenceContext
    private EntityManager em;

    public void relacionarFacturaCompraConRemitos(Factura factura, Remito[] remitos) {
        for (Remito remito : remitos) {
            ComprobanteRelaciones comprobanteRelaciones = new ComprobanteRelaciones();
            comprobanteRelaciones.setFacturaCompra(em.merge(factura));
            comprobanteRelaciones.setRemitoCompra(em.merge(remito));
            em.persist(comprobanteRelaciones);
        }
    }

    public void relacionarRemitoCompraConOrdenesDeCompra(Remito remitoNoConfirmado, OrdenDeCompra[] ordenesDeCompraSeleccionadas) {
        for (OrdenDeCompra ordenDeCompra : ordenesDeCompraSeleccionadas) {
            ComprobanteRelaciones comprobanteRelaciones = new ComprobanteRelaciones();
            comprobanteRelaciones.setRemitoCompra(em.merge(remitoNoConfirmado));
            comprobanteRelaciones.setOrdenDeCompra(em.merge(ordenDeCompra));
            em.persist(comprobanteRelaciones);
        }
    }

    public void relacionarRemitoConFacturaVenta(Factura factura, Remito remito) {
        ComprobanteRelaciones comprobanteRelaciones = new ComprobanteRelaciones();
        comprobanteRelaciones.setRemitoVenta(em.merge(remito));
        comprobanteRelaciones.setFacturaVenta(em.merge(factura));
        em.persist(comprobanteRelaciones);
    }

    public void relacionarRemitoConOrdenDeProduccion(Remito remito, OrdenDeProduccion ordenDeProduccion) {
        ComprobanteRelaciones comprobanteRelaciones = new ComprobanteRelaciones();
        comprobanteRelaciones.setRemitoProduccion(em.merge(remito));
        comprobanteRelaciones.setOrdenDeProduccion(em.merge(ordenDeProduccion));
        em.persist(comprobanteRelaciones);
    }
}
