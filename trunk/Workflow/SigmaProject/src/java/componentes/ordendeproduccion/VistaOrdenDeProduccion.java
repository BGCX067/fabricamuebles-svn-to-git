/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package componentes.ordendeproduccion;

import entidades.ComprobanteRelaciones;
import entidades.Fabrica;
import entidades.OrdenDeCompra;
import entidades.OrdenDeCompraDetalle;
import entidades.OrdenDeProduccion;
import entidades.Remito;
import entidades.RequerimientoMaterial;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.logging.*;
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.*;

/**
 *
 * @author Zaba
 */
@ManagedBean
@SessionScoped
public class VistaOrdenDeProduccion implements java.io.Serializable {
    @EJB
    private GestorOrdenDeProduccion gestorOrdenDeProduccion;
    private OrdenDeProduccion ordenDeProduccionSeleccionada;
    private RequerimientoMaterial requerimientoMaterialSeleccionado;

    public void instanciarGestor() {
        if (this.gestorOrdenDeProduccion == null) {
            try {
                InitialContext ic = new InitialContext();
                this.gestorOrdenDeProduccion = (GestorOrdenDeProduccion) ic.lookup("java:global/SigmaProject/GestorOrdenDeProduccion");
            } catch (NamingException ex) {
                Logger.getLogger(VistaOrdenDeProduccion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public RequerimientoMaterial getRequerimientoMaterialSeleccionado() {
        return requerimientoMaterialSeleccionado;
    }

    public void setRequerimientoMaterialSeleccionado(RequerimientoMaterial requerimientoMaterialSeleccionado) {
        this.requerimientoMaterialSeleccionado = requerimientoMaterialSeleccionado;
    }

    public Collection<OrdenDeProduccion> devolverOrdenesDeProduccionPlanificadas(Fabrica fabrica) {
        return gestorOrdenDeProduccion.devolverOrdenesDeProduccionPlanificadas(fabrica);
    }

    public List<OrdenDeProduccion> devolverOrdenesDeProduccion() {
        return gestorOrdenDeProduccion.devolverOrdenesDeProduccion();
    }

    public List<OrdenDeProduccion> devolverOrdenesDeProduccionNoFinalizadas() {
        return gestorOrdenDeProduccion.devolverOrdenesDeProduccionNoFinalizadas();
    }

    public List<OrdenDeProduccion> devolverOrdenesDeProduccionNoFinalizadas(Fabrica fabrica) {
        return gestorOrdenDeProduccion.devolverOrdenesDeProduccionNoFinalizadas(fabrica);
    }

    public List<OrdenDeProduccion> devolverOrdenesDeProduccionExterna() {
        return gestorOrdenDeProduccion.devolverOrdenesDeProduccionExterna();
    }

    public void actualizarEstadoPedidoRequerimientoMateriales(OrdenDeCompra ordenDeCompra) {
        gestorOrdenDeProduccion.actualizarEstadoPedidoRequerimientoMateriales(this.ordenDeProduccionSeleccionada, ordenDeCompra);
    }

    public void guardarOrdenDeProduccion(OrdenDeProduccion ordenDeProduccion) {
        gestorOrdenDeProduccion.guardarOrdenDeProduccion(ordenDeProduccion);
    }

    public OrdenDeProduccion actualizarOrdenDeProduccion(OrdenDeProduccion ordenDeProduccion) {
        return gestorOrdenDeProduccion.actualizarOrdenDeProduccion(ordenDeProduccion);
    }

    public OrdenDeProduccion actualizarOrdenDeProduccionDesdeBD(OrdenDeProduccion ordenDeProduccion) {
        return gestorOrdenDeProduccion.actualizarOrdenDeProduccionDesdeBD(ordenDeProduccion);
    }

    public void actualizarEstadoAbastecimientoExternoOrdenDeProduccion(OrdenDeProduccion ordenDeProduccion, Remito remito) {
        gestorOrdenDeProduccion.actualizarEstadoAbastecimientoExternoOrdenDeProduccion(ordenDeProduccion, remito);
    }

    public OrdenDeProduccion getOrdenDeProduccionSeleccionada() {
        return this.ordenDeProduccionSeleccionada;
    }

    public void setOrdenDeProduccionSeleccionada(OrdenDeProduccion ordenDeProduccionSeleccionada) {
        this.ordenDeProduccionSeleccionada = ordenDeProduccionSeleccionada;
    }

    public void actualizarEstadoAbastecimientoOrdenDeProduccion(OrdenDeCompra ordenDeCompra, OrdenDeCompraDetalle ordenDeCompraDetalle, BigDecimal aAsignar, Collection<ComprobanteRelaciones> relacionesOrdenDeCompra) {
        gestorOrdenDeProduccion.actualizarEstadoAbastecimientoOrdenDeProduccion(ordenDeCompra, ordenDeCompraDetalle, aAsignar, relacionesOrdenDeCompra);
    }
}
