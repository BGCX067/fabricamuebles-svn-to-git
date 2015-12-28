/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes.comprobanterelaciones;

import entidades.Factura;
import entidades.OrdenDeCompra;
import entidades.OrdenDeProduccion;
import entidades.Remito;
import java.util.logging.*;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Zaba
 */
@ManagedBean
@SessionScoped
public class VistaComprobanteRelaciones implements java.io.Serializable {

    @EJB
    private GestorComprobanteRelaciones gestorComprobanteRelaciones;

    public void instanciarGestor() {
        if (gestorComprobanteRelaciones == null) {
            try {
                InitialContext ic = new InitialContext();
                this.gestorComprobanteRelaciones = (GestorComprobanteRelaciones) ic.lookup("java:global/SigmaProject/GestorComprobanteRelaciones");
            } catch (NamingException ex) {
                Logger.getLogger(VistaComprobanteRelaciones.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void relacionarFacturaCompraConRemitos(Factura factura, Remito[] remitos) {
        gestorComprobanteRelaciones.relacionarFacturaCompraConRemitos(factura, remitos);
    }

    public void relacionarRemitoCompraConOrdenesDeCompra(Remito remitoNoConfirmado, OrdenDeCompra[] ordenesDeCompraSeleccionadas) {
        gestorComprobanteRelaciones.relacionarRemitoCompraConOrdenesDeCompra(remitoNoConfirmado, ordenesDeCompraSeleccionadas);
    }

    public void relacionarRemitoConFacturaVenta(Factura factura, Remito remito) {
        gestorComprobanteRelaciones.relacionarRemitoConFacturaVenta(factura, remito);
    }

    public void relacionarRemitoConOrdenDeProduccion(Remito remito, OrdenDeProduccion ordenDeProduccion) {
        gestorComprobanteRelaciones.relacionarRemitoConOrdenDeProduccion(remito, ordenDeProduccion);
    }
}
