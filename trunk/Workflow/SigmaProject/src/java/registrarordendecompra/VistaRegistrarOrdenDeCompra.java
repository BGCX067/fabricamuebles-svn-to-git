/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package registrarordendecompra;

import componentes.articuloproveedor.VistaArticuloProveedor;
import componentes.ordendeproduccion.VistaOrdenDeProduccion;
import entidades.*;
import java.math.BigDecimal;
import java.util.*;
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;

/**
 *
 * @author Zaba
 */
@ManagedBean(name = "VistaRegistrarOrdenDeCompra")
@SessionScoped
public class VistaRegistrarOrdenDeCompra implements java.io.Serializable {

    @EJB
    private GestorRegistrarOrdenDeCompra gestorRegistrarOrdenDeCompra;
    private String error;
    private Collection<OrdenDeCompraDetalle> detallesAgregados;
    private OrdenDeCompraDetalle detalleActual;

    /** Creates a new instance of VistaRegistrarOrdenDeCompra */
    public VistaRegistrarOrdenDeCompra() {
    }

    /** hago que devuelva Boolean para que pueda ser llamado por cualquier h:outputText en rendered
     *
     * @return nada...
     */
    public Boolean reiniciar() {
        this.error = "";
        this.detalleActual = null;
        this.detallesAgregados = null;
        this.gestorRegistrarOrdenDeCompra.reiniciar();
        return false;
    }

    public String getError() {
        if (error == null) {
            error = "";
        }
        return error;
    }

    public void agregarArticulo(ArticuloPresentacionPrecioProveedor app) {
        detalleActual = new OrdenDeCompraDetalle();
        detalleActual.setPresentacion(app.getPresentacion());
        detalleActual.setPrecio(app.getPrecio());
    }

    public void cargarArticuloCantidad(OrdenDeCompraDetalle detalle) {
        Iterator<OrdenDeCompraDetalle> iterator = detallesAgregados.iterator();
        while (iterator.hasNext()) {
            OrdenDeCompraDetalle next = iterator.next();
            if (next.equals(detalle)) {
                detalleActual = next;
                break;
            }
        }
    }

    public void quitarArticuloAgregado(OrdenDeCompraDetalle detalle) {
        detallesAgregados.remove(detalle);
    }

    public BigDecimal getSetearCantidadYAgregar() {
        return null;
    }

    public void setSetearCantidadYAgregar(BigDecimal cantidad) {
        detalleActual.setCantidad(cantidad);
        if (detallesAgregados == null) {
            detallesAgregados = new LinkedList<OrdenDeCompraDetalle>();
        }
        detallesAgregados.add(detalleActual);
    }

    public BigDecimal getSetearCantidadYGuardar() {
        return null;
    }

    public void setSetearCantidadYGuardar(BigDecimal cantidad) {
        detalleActual.setCantidad(cantidad);
        if (detallesAgregados == null) {
            detallesAgregados = new LinkedList<OrdenDeCompraDetalle>();
        }
        int pos = ((LinkedList<OrdenDeCompraDetalle>) detallesAgregados).indexOf(detalleActual);
        detallesAgregados.remove(detalleActual);
        ((LinkedList<OrdenDeCompraDetalle>) detallesAgregados).add(pos, detalleActual);
    }

    public Collection<OrdenDeCompraDetalle> getDetallesAgregados() {
        return detallesAgregados;
    }

    public void setDetallesAgregados(Collection<OrdenDeCompraDetalle> detallesAgregados) {
        this.detallesAgregados = detallesAgregados;
    }

    public Proveedor getProveedorSeleccionado() {
        return gestorRegistrarOrdenDeCompra.getProveedorSeleccionado();
    }

    public void setProveedorSeleccionado(Proveedor proveedorSeleccionado) {
        gestorRegistrarOrdenDeCompra.setProveedorSeleccionado(proveedorSeleccionado);
    }

    public OrdenDeCompra getOrdenDeCompraNoConfirmada() {
        return gestorRegistrarOrdenDeCompra.getOrdenDeCompraNoConfirmada();
    }

    public Collection<Presupuesto> devolverPresupuestos() {
        return gestorRegistrarOrdenDeCompra.devolverPresupuestosVigentes();
    }

    public Collection<Proveedor> devolverProveedores() {
        return gestorRegistrarOrdenDeCompra.devolverProveedores();
    }

    public Collection<String> getTiposDocumento() {
        return gestorRegistrarOrdenDeCompra.devolverTiposDocumento();
    }

    public Collection<ArticuloPresentacionPrecioProveedor> devolverArticulosProveedor() {
        return gestorRegistrarOrdenDeCompra.devolverArticulosProveedor();
    }

    public Collection<ArticuloPresentacionPrecioProveedor> devolverArticulosProveedorPorNecesidadStock() {
        return gestorRegistrarOrdenDeCompra.devolverArticulosProveedorPorNecesidadStock();
    }

    // Métodos navegabilidad y/o seteo de error
    public String seleccionarProveedorSiguiente() {
        if (gestorRegistrarOrdenDeCompra.getProveedorSeleccionado() != null) {
            this.error = "";
            return "mostrarDatosProveedor?faces-redirect=true";
        } else {
            this.error = "Por favor, seleccione un Proveedor.";
            return null;
        }
    }

    public String confirmarDatosProveedor() {
        return "seleccionarArticulos?faces-redirect=true";
    }

    public String seleccionarArticulosSiguiente() {
        if (detallesAgregados != null && !detallesAgregados.isEmpty()) {
            this.error = "";
            gestorRegistrarOrdenDeCompra.tomarDetallesOrdenDeCompra(detallesAgregados);
            return "confirmarOrdenDeCompra?faces-redirect=true";
        } else {
            this.error = "Por favor, agregue por lo menos un artículo a la Orden de Compra.";
            return null;
        }
    }

    public String confirmarOrdenDeCompra() {
        gestorRegistrarOrdenDeCompra.tomarConfirmacionDatosOrdenDeCompra();
        return "informarOrdenDeCompraRegistrada?faces-redirect=true";
    }

    public String confirmarOrdenDeCompraParaOrdenDeProduccion() {
        gestorRegistrarOrdenDeCompra.tomarConfirmacionDatosOrdenDeCompra();
        ((VistaOrdenDeProduccion) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaOrdenDeProduccion")).actualizarEstadoPedidoRequerimientoMateriales(
                gestorRegistrarOrdenDeCompra.getOrdenDeCompraNoConfirmada());
        gestorRegistrarOrdenDeCompra.asociarOrdenDeCompraAOrdenDeProduccion(((VistaOrdenDeProduccion) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaOrdenDeProduccion")).getOrdenDeProduccionSeleccionada());
        return "informarOrdenDeCompraRegistrada?faces-redirect=true";
    }

    public String actualizarProveedor() {
        gestorRegistrarOrdenDeCompra.actualizarProveedor();
        return "mostrarDatosProveedor?faces-redirect=true";
    }

    public String descartarCambiosProveedor() {
        gestorRegistrarOrdenDeCompra.descartarCambiosProveedor();
        return "mostrarDatosProveedor?faces-redirect=true";
    }

    /**
     * Cuando se confirma habiendo modificado los detalles
     * @return
     */
    public String confirmarOrdenDeCompraAPartirDePresupuestoModificado() {
        gestorRegistrarOrdenDeCompra.confirmarOrdenDeCompraAPartirDePresupuesto();
        return "informarOrdenDeCompraRegistrada?faces-redirect=true";
    }

    /**
     * Reinicia el backing bean para poder seguir con otra orden de compra nueva como si fuera desde cero
     * @return
     */
    public String seguirConOtraOrdenAPartirDeOrdenDeProduccion() {
        reiniciar();
        return "seleccionarProveedor?faces-redirect=true";
    }

    public String confirmarDatosProveedorParaAbastecerOrdenDeProduccion() {
        VistaOrdenDeProduccion vistaOrdenDeProduccion = devolverVistaOrdenDeProduccion();
        Collection<RequerimientoMaterialOrdenDeProduccion> requerimientoMateriales = vistaOrdenDeProduccion.getOrdenDeProduccionSeleccionada().getRequerimientoMateriales();
        for (RequerimientoMaterialOrdenDeProduccion requerimientoMaterial : requerimientoMateriales) {
            if (requerimientoMaterial.cantidadAPedir().compareTo(BigDecimal.ZERO) > 0) {
                Articulo articulo = (Articulo) requerimientoMaterial.getItemRequerible();
                Set<ArticuloPresentacionPrecioProveedor> precioArticulos = devolverVistaArticuloProveedor().devolverPrecioArticulos(getProveedorSeleccionado());
                ArticuloPresentacionPrecioProveedor articuloPrecioProveedor = null;
                Iterator<ArticuloPresentacionPrecioProveedor> iterator = precioArticulos.iterator();
                while (iterator.hasNext()) {
                    ArticuloPresentacionPrecioProveedor app = iterator.next();
                    if (app.getPresentacion().getArticulo().equals(articulo)) {
                        articuloPrecioProveedor = app;
                        break;
                    }
                }
                if (articuloPrecioProveedor != null) {
                    agregarArticulo(articuloPrecioProveedor);
                    setSetearCantidadYAgregar(requerimientoMaterial.cantidadAPedir());
                }
            }
        }
        return "seleccionarArticulos?faces-redirect=true";
    }

    private VistaOrdenDeProduccion devolverVistaOrdenDeProduccion() {
        VistaOrdenDeProduccion vistaOrdenDeProduccion = (VistaOrdenDeProduccion) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaOrdenDeProduccion");
        if (vistaOrdenDeProduccion == null) {
            vistaOrdenDeProduccion = new VistaOrdenDeProduccion();
            vistaOrdenDeProduccion.instanciarGestor();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("vistaOrdenDeProduccion", vistaOrdenDeProduccion);
        }
        return vistaOrdenDeProduccion;
    }

    private VistaArticuloProveedor devolverVistaArticuloProveedor() {
        VistaArticuloProveedor vistaArticuloProveedor = (VistaArticuloProveedor) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaArticuloProveedor");
        if (vistaArticuloProveedor == null) {
            vistaArticuloProveedor = new VistaArticuloProveedor();
            vistaArticuloProveedor.instanciarGestor();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("vistaArticuloProveedor", vistaArticuloProveedor);
        }
        return vistaArticuloProveedor;
    }
}
