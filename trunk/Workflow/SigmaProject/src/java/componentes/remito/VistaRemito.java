/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes.remito;

import componentes.comprobanterelaciones.VistaComprobanteRelaciones;
import componentes.factura.VistaFactura;
import componentes.ordendecompra.VistaOrdenDeCompra;
import componentes.ordendeproduccion.VistaOrdenDeProduccion;
import entidades.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.logging.*;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Zaba
 */
@ManagedBean
@SessionScoped
public class VistaRemito implements java.io.Serializable {

    @EJB
    private GestorRemito gestorRemito;
    private Proveedor proveedorSeleccionado;
    private String error;
    private RemitoDetalle remitoDetalleActual;
    private Collection<RemitoDetalle> remitoDetallesAgregados;
    private OrdenDeCompra[] ordenesDeCompraSeleccionadas;
    private Remito remitoSeleccionado;
    private OrdenDeProduccion ordenDeProduccionSeleccionada;
    private Factura facturaSeleccionada;

    public void instanciarGestor() {
        if (this.gestorRemito == null) {
            try {
                InitialContext ic = new InitialContext();
                this.gestorRemito = (GestorRemito) ic.lookup("java:global/SigmaProject/GestorRemito");
            } catch (NamingException ex) {
                Logger.getLogger(VistaOrdenDeCompra.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Factura getFacturaSeleccionada() {
        return facturaSeleccionada;
    }

    public void setFacturaSeleccionada(Factura facturaSeleccionada) {
        this.facturaSeleccionada = facturaSeleccionada;
    }

    public OrdenDeProduccion getOrdenDeProduccionSeleccionada() {
        return ordenDeProduccionSeleccionada;
    }

    public void setOrdenDeProduccionSeleccionada(OrdenDeProduccion ordenDeProduccionSeleccionada) {
        this.ordenDeProduccionSeleccionada = ordenDeProduccionSeleccionada;
    }

    public Remito getRemitoSeleccionado() {
        return remitoSeleccionado;
    }

    public void setRemitoSeleccionado(Remito remitoSeleccionado) {
        this.remitoSeleccionado = remitoSeleccionado;
    }

    public OrdenDeCompra[] getOrdenesDeCompraSeleccionadas() {
        return ordenesDeCompraSeleccionadas;
    }

    public void setOrdenesDeCompraSeleccionadas(OrdenDeCompra[] ordenesDeCompraSeleccionadas) {
        this.ordenesDeCompraSeleccionadas = ordenesDeCompraSeleccionadas;
    }

    public String getError() {
        if (error == null) {
            error = "";
        }
        return error;
    }

    public void agregarArticulo(ArticuloPresentacionPrecioProveedor app) {
        if (ordenesDeCompraSeleccionadas != null && ordenesDeCompraSeleccionadas.length > 0) {
            remitoDetalleActual = new RemitoDetalleCompra();
            ((RemitoDetalleCompra) remitoDetalleActual).setPresentacion(app.getPresentacion());
        } else if (ordenDeProduccionSeleccionada != null) {
            remitoDetalleActual = new RemitoDetalleProduccion();
            ((RemitoDetalleProduccion) remitoDetalleActual).setFamilia(app.getPresentacion().getArticulo().getFamilia());
        }
    }

    public void agregarArticulo(MuebleModelo muebleModelo) {
        if (facturaSeleccionada != null) {
            remitoDetalleActual = new RemitoDetalleVenta();
            ((RemitoDetalleVenta) remitoDetalleActual).setMuebleModelo(muebleModelo);
        }
    }

    public void cargarArticuloCantidad(RemitoDetalle detalle) {
        Iterator<RemitoDetalle> iterator = remitoDetallesAgregados.iterator();
        while (iterator.hasNext()) {
            RemitoDetalle next = iterator.next();
            if (next.equals(detalle)) {
                remitoDetalleActual = next;
                break;
            }
        }
    }

    public void quitarArticuloAgregado(RemitoDetalle detalle) {
        remitoDetallesAgregados.remove(detalle);
    }

    public BigDecimal getSetearCantidadYAgregar() {
        return null;
    }

    public void setSetearCantidadYAgregar(BigDecimal cantidad) {
        remitoDetalleActual.setCantidad(cantidad);
        if (remitoDetallesAgregados == null) {
            remitoDetallesAgregados = new LinkedList<RemitoDetalle>();
        }
        remitoDetallesAgregados.add(remitoDetalleActual);
    }

    public BigDecimal getSetearCantidadYGuardar() {
        return null;
    }

    public void setSetearCantidadYGuardar(BigDecimal cantidad) {
        remitoDetalleActual.setCantidad(cantidad);
        if (remitoDetallesAgregados == null) {
            remitoDetallesAgregados = new LinkedList<RemitoDetalle>();
        }
        int pos = ((LinkedList<RemitoDetalle>) remitoDetallesAgregados).indexOf(remitoDetalleActual);
        remitoDetallesAgregados.remove(remitoDetalleActual);

        ((LinkedList<RemitoDetalle>) remitoDetallesAgregados).add(pos, remitoDetalleActual);
    }

    public Collection<RemitoDetalle> getDetallesAgregados() {
        return remitoDetallesAgregados;
    }

    public void setDetallesAgregados(Collection<RemitoDetalle> remitoDetallesAgregados) {
        this.remitoDetallesAgregados = remitoDetallesAgregados;
    }

    public Proveedor getProveedorSeleccionado() {
        return proveedorSeleccionado;
    }

    public void setProveedorSeleccionado(Proveedor proveedorSeleccionado) {
        this.proveedorSeleccionado = proveedorSeleccionado;
    }

    public String seleccionarArticulosSiguiente() {
        if (remitoDetallesAgregados != null && !remitoDetallesAgregados.isEmpty()) {
            this.error = "";
            gestorRemito.tomarDetallesRemito(remitoDetallesAgregados);
            return "confirmarRemito?faces-redirect=true";
        } else {
            this.error = "Por favor, agregue por lo menos un artículo al Remito.";
            return null;
        }
    }

    public String remitoAPartirDeOrdenesDeCompra() {
        if (ordenesDeCompraSeleccionadas != null && ordenesDeCompraSeleccionadas.length > 0) {
            gestorRemito.crearRemito(this.proveedorSeleccionado);
            for (OrdenDeCompra ordenDeCompra : ordenesDeCompraSeleccionadas) {
                gestorRemito.copiarDetallesOrdenDeCompraARemito(ordenDeCompra);
            }
            this.remitoDetallesAgregados = gestorRemito.getRemitoNoConfirmado().getDetalles();
            this.error = "";
            return "seleccionarArticulos?faces-redirect=true";
        } else {
            this.error = "Por favor, seleccione por lo menos una Orden de Compra.";
            return null;
        }
    }

    // TODO pasar a String
    public void remitoAPartirDeFactura() {
        if (facturaSeleccionada != null) {
            gestorRemito.crearRemito(this.facturaSeleccionada.getEntidad());
            gestorRemito.copiarDetallesFacturaARemito(this.facturaSeleccionada);
            this.remitoDetallesAgregados = gestorRemito.getRemitoNoConfirmado().getDetalles();
            this.error = "";
            //return "/stock/Muebles/nuevo/informarRegistroEntregaMuebles?faces-redirect=true";
        } else {
            this.error = "Por favor, seleccione una Factura.";
            //return null;
        }
    }

    public String remitoAPartirDeOrdenDeProduccion() {
        if (ordenDeProduccionSeleccionada != null) {
            gestorRemito.crearRemito(ordenDeProduccionSeleccionada.getFabrica());
            gestorRemito.copiarRequerimientosMaterialesARemito(this.ordenDeProduccionSeleccionada);
            this.remitoDetallesAgregados = gestorRemito.getRemitoNoConfirmado().getDetalles();
            this.error = "";
            return "seleccionarArticulos?faces-redirect=true";
        } else {
            this.error = "Por favor, seleccione una Orden de Producción Planificada Externa.";
            return null;
        }
    }

    public Collection<Remito> devolverRemitosCompra() {
        return gestorRemito.devolverRemitosCompra();
    }
   

    public Collection<Remito> devolverRemitosCompra(Proveedor proveedor) {
        return gestorRemito.devolverRemitosCompra(proveedor);
    }
     public Collection<Remito> devolverRemitosVenta() {
        return gestorRemito.devolverRemitosVenta();
    }
      public Collection<Remito> devolverRemitosVenta(Cliente cliente) {
        return gestorRemito.devolverRemitosVenta(cliente);
    }

    public Collection<Remito> devolverRemitosCompraAFacturar() {
        return gestorRemito.devolverRemitosCompraAFacturar();
    }

    public Collection<Remito> devolverRemitosCompraAFacturar(Proveedor proveedor) {
        return gestorRemito.devolverRemitosCompraAFacturar(proveedor);
    }

    public Boolean reiniciar() {
        gestorRemito.reiniciar();
        return false;
    }

    public void actualizarEstadoRemitos(Remito[] remitosSeleccionados, Factura facturaNoConfirmada) {
        if (remitosSeleccionados != null) {
            gestorRemito.actualizarEstadoRemitosCompra(remitosSeleccionados, facturaNoConfirmada);
        }
    }

    public String confirmarRemito() {
        gestorRemito.tomarConfirmacionDatosRemito();
        if (ordenesDeCompraSeleccionadas != null && ordenesDeCompraSeleccionadas.length > 0) {
            VistaOrdenDeCompra vistaOrdenDeCompra = (VistaOrdenDeCompra) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaOrdenDeCompra");
            if (vistaOrdenDeCompra == null) {
                vistaOrdenDeCompra = new VistaOrdenDeCompra();
                vistaOrdenDeCompra.instanciarGestor();
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("vistaOrdenDeCompra", vistaOrdenDeCompra);
                vistaOrdenDeCompra = (VistaOrdenDeCompra) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaOrdenDeCompra");
            }
            vistaOrdenDeCompra.actualizarEstadoOrdenesDeCompra(ordenesDeCompraSeleccionadas, getRemitoNoConfirmado());
            VistaComprobanteRelaciones comprobanteRelaciones = (VistaComprobanteRelaciones) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaComprobanteRelaciones");
            if (comprobanteRelaciones == null) {
                comprobanteRelaciones = new VistaComprobanteRelaciones();
                comprobanteRelaciones.instanciarGestor();
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("vistaComprobanteRelaciones", comprobanteRelaciones);
                comprobanteRelaciones = (VistaComprobanteRelaciones) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaComprobanteRelaciones");
            }
            comprobanteRelaciones.relacionarRemitoCompraConOrdenesDeCompra(getRemitoNoConfirmado(), ordenesDeCompraSeleccionadas);
        } else if (facturaSeleccionada != null) {
            VistaFactura vistaFactura = (VistaFactura) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaFactura");
            if (vistaFactura == null) {
                vistaFactura = new VistaFactura();
                vistaFactura.instanciarGestor();
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("vistaFactura", vistaFactura);
            }
            vistaFactura.actualizarEstadoFacturaAEntregada(facturaSeleccionada);
            VistaComprobanteRelaciones comprobanteRelaciones = (VistaComprobanteRelaciones) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaComprobanteRelaciones");
            if (comprobanteRelaciones == null) {
                comprobanteRelaciones = new VistaComprobanteRelaciones();
                comprobanteRelaciones.instanciarGestor();
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("vistaComprobanteRelaciones", comprobanteRelaciones);
                comprobanteRelaciones = (VistaComprobanteRelaciones) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaComprobanteRelaciones");
            }
            comprobanteRelaciones.relacionarRemitoConFacturaVenta(facturaSeleccionada, getRemitoNoConfirmado());
        } else if (ordenDeProduccionSeleccionada != null) {
            VistaOrdenDeProduccion vistaOrdenDeProduccion = (VistaOrdenDeProduccion) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaOrdenDeProduccion");
            if (vistaOrdenDeProduccion == null) {
                vistaOrdenDeProduccion = new VistaOrdenDeProduccion();
                vistaOrdenDeProduccion.instanciarGestor();
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("vistaOrdenDeProduccion", vistaOrdenDeProduccion);
            }
            vistaOrdenDeProduccion.actualizarEstadoAbastecimientoExternoOrdenDeProduccion(this.ordenDeProduccionSeleccionada, getRemitoNoConfirmado());
            VistaComprobanteRelaciones comprobanteRelaciones = (VistaComprobanteRelaciones) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaComprobanteRelaciones");
            if (comprobanteRelaciones == null) {
                comprobanteRelaciones = new VistaComprobanteRelaciones();
                comprobanteRelaciones.instanciarGestor();
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("vistaComprobanteRelaciones", comprobanteRelaciones);
                comprobanteRelaciones = (VistaComprobanteRelaciones) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaComprobanteRelaciones");
            }
            comprobanteRelaciones.relacionarRemitoConOrdenDeProduccion(getRemitoNoConfirmado(), this.ordenDeProduccionSeleccionada);
        }
        return "informarRemitoRegistrado?faces-redirect=true";
    }

    public Remito getRemitoNoConfirmado() {
        return gestorRemito.getRemitoNoConfirmado();
    }
}
