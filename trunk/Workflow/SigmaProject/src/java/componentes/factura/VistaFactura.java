/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes.factura;

import componentes.articuloproveedor.VistaArticuloProveedor;
import componentes.comprobanterelaciones.VistaComprobanteRelaciones;
import componentes.mueblemodelos.VistaMuebleModelo;
import componentes.presupuesto.VistaPresupuesto;
import componentes.remito.VistaRemito;
import entidades.*;
import java.math.*;
import java.util.*;
import java.util.logging.*;
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Cristian
 */
@ManagedBean
@SessionScoped
public class VistaFactura implements java.io.Serializable {

    @EJB
    private GestorFactura gestorFactura;
    private String error;
    private Collection<FacturaDetalle> facturaDetallesAgregados;
    private FacturaDetalle facturaDetalleActual;
    private Proveedor proveedorSeleccionado;
    private Remito[] remitosSeleccionados;
    private Factura facturaSeleccionada;
    private MuebleModelo muebleModeloSeleccionado;
    private BigDecimal cantidad;
    private Presupuesto presupuesto;
    private Pedido pedido;
    private Collection<FacturaDetalle> facturaDetallesEliminados;
    private FacturaDetalle facturaDetalleSeleccionado;
    private String nroFactura;
    private boolean yaAgregado;

    public void instanciarGestor() {
        if (this.gestorFactura == null) {
            try {
                InitialContext ic = new InitialContext();
                this.gestorFactura = (GestorFactura) ic.lookup("java:global/SigmaProject/GestorFactura");
            } catch (NamingException ex) {
                Logger.getLogger(VistaFactura.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Factura getFacturaSeleccionada() {

        return facturaSeleccionada;

    }

    public void setFacturaSeleccionada(Factura facturaSeleccionada) {
        this.facturaSeleccionada = facturaSeleccionada;
    }

    public Collection<Factura> devolverFacturasCompra() {
        return gestorFactura.devolverFacturasCompra();
    }

    public Collection<Factura> devolverFacturasVenta() {
        return gestorFactura.devolverFacturasVenta();
    }

    public Collection<Factura> devolverFacturasVentaNoEntregada() {
        return gestorFactura.devolverFacturasVentaNoEntregada();
    }

    public Remito[] getRemitosSeleccionados() {
        return remitosSeleccionados;
    }

    public void setRemitosSeleccionados(Remito[] remitosSeleccionados) {
        this.remitosSeleccionados = remitosSeleccionados;
    }

    public Boolean reiniciar() {
        this.error = "";
        this.facturaDetalleActual = null;
        this.facturaDetallesAgregados = null;
        this.gestorFactura.reiniciar();
        return false;
    }

    public String getError() {
        if (error == null) {
            error = "";
        }
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void agregarArticulo(ArticuloPresentacionPrecioProveedor app) {
        facturaDetalleActual = new FacturaDetalleCompra();
        ((FacturaDetalleCompra) facturaDetalleActual).setPresentacion(app.getPresentacion());
        facturaDetalleActual.setPrecio(app.getPrecio());

    }

    public void cargarArticuloCantidad(FacturaDetalleCompra detalle) {
        Iterator<FacturaDetalle> iterator = facturaDetallesAgregados.iterator();
        while (iterator.hasNext()) {
            FacturaDetalle next = iterator.next();
            if (next.equals(detalle)) {
                facturaDetalleActual = next;
                break;
            }
        }
    }

    public void quitarArticuloAgregado(FacturaDetalle detalle) {
        facturaDetallesAgregados.remove(detalle);
    }

    public BigDecimal getSetearCantidadYAgregar() {
        return null;
    }

    public void setSetearCantidadYAgregar(BigDecimal cantidad) {
        facturaDetalleActual.setCantidad(cantidad);
        if (facturaDetallesAgregados == null) {
            facturaDetallesAgregados = new LinkedList<FacturaDetalle>();
        }
        facturaDetallesAgregados.add(facturaDetalleActual);
    }

    public BigDecimal getSetearCantidadYGuardar() {
        return null;
    }

    public void setSetearCantidadYGuardar(BigDecimal cantidad) {
        facturaDetalleActual.setCantidad(cantidad);
        if (facturaDetallesAgregados == null) {
            facturaDetallesAgregados = new LinkedList<FacturaDetalle>();
        }
        int pos = ((LinkedList<FacturaDetalle>) facturaDetallesAgregados).indexOf(facturaDetalleActual);
        facturaDetallesAgregados.remove(facturaDetalleActual);

        ((LinkedList<FacturaDetalle>) facturaDetallesAgregados).add(pos, facturaDetalleActual);
    }

    public void setSetearPrecioYGuardar(BigDecimal precio) {
        facturaDetalleActual.setPrecio(precio);
        if (facturaDetallesAgregados == null) {
            facturaDetallesAgregados = new LinkedList<FacturaDetalle>();
        }
        int pos = ((LinkedList<FacturaDetalle>) facturaDetallesAgregados).indexOf(facturaDetalleActual);
        facturaDetallesAgregados.remove(facturaDetalleActual);

        ((LinkedList<FacturaDetalle>) facturaDetallesAgregados).add(pos, facturaDetalleActual);
    }

    public BigDecimal getSetearPrecioYGuardar() {
        return null;
    }

    public Collection<FacturaDetalle> getDetallesAgregados() {
        return facturaDetallesAgregados;
    }

    public void setDetallesAgregados(Collection<FacturaDetalle> facturaDetallesAgregados) {
        this.facturaDetallesAgregados = facturaDetallesAgregados;
    }

    public OrdenDeCompra getOrdenDeCompraSeleccionada() {
        return gestorFactura.getOrdenDeCompraSeleccionada();
    }

    public void setOrdenDeCompraSeleccionada(OrdenDeCompra ordenDeCompraSeleccionada) {
        gestorFactura.setOrdenDeCompraSeleccionada(ordenDeCompraSeleccionada);
    }

    public Proveedor getProveedorSeleccionado() {
        return proveedorSeleccionado;
    }

    public void setProveedorSeleccionado(Proveedor proveedorSeleccionado) {
        this.proveedorSeleccionado = proveedorSeleccionado;
    }

    public Collection<ArticuloPresentacionPrecioProveedor> devolverArticulosProveedor() {
        return gestorFactura.devolverArticulosProveedor();
    }

    public String seleccionarArticulosSiguiente() {
        if (facturaDetallesAgregados != null && !facturaDetallesAgregados.isEmpty()) {
            this.error = "";
            gestorFactura.tomarDetallesFactura(facturaDetallesAgregados);
            return "confirmarFactura?faces-redirect=true";
        } else {
            this.error = "Por favor, agregue por lo menos un artículo a la Orden de Compra.";
            return null;
        }
    }

    public String confirmarFactura() {
        actualizarPrecioArtProveedor();
        gestorFactura.tomarConfirmacionDatosFactura();
        if (remitosSeleccionados != null && remitosSeleccionados.length > 0) {
            VistaRemito vistaRemito = (VistaRemito) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaRemito");
            if (vistaRemito == null) {
                vistaRemito = new VistaRemito();
                vistaRemito.instanciarGestor();
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("vistaRemito", vistaRemito);
                vistaRemito = (VistaRemito) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaRemito");
            }
            vistaRemito.actualizarEstadoRemitos(remitosSeleccionados, getFacturaNoConfirmada());
            VistaComprobanteRelaciones comprobanteRelaciones = (VistaComprobanteRelaciones) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaComprobanteRelaciones");
            if (comprobanteRelaciones == null) {
                comprobanteRelaciones = new VistaComprobanteRelaciones();
                comprobanteRelaciones.instanciarGestor();
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("vistaComprobanteRelaciones", comprobanteRelaciones);
                comprobanteRelaciones = (VistaComprobanteRelaciones) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaComprobanteRelaciones");
            }
            comprobanteRelaciones.relacionarFacturaCompraConRemitos(getFacturaNoConfirmada(), remitosSeleccionados);
        }
        return "informarFacturaRegistrada?faces-redirect=true";
    }

    /**
     * Se genera la factura a partir de uno o más remitos, y se pasa a la página
     * de modificación de detalles
     * @return
     */
    public String facturaAPartirDeRemitos() {
        if (remitosSeleccionados != null && remitosSeleccionados.length > 0) {
            gestorFactura.crearFactura(this.proveedorSeleccionado);
            for (Remito remito : remitosSeleccionados) {
                gestorFactura.copiarDetallesRemitoAFactura(remito);
            }
            this.facturaDetallesAgregados = gestorFactura.getFacturaNoConfirmada().getDetalles();
            this.error = "";
            return "seleccionarArticulos?faces-redirect=true";
        } else {
            this.error = "Por favor, seleccione por lo menos un Remito.";
            return null;
        }
    }

    /**
     * Se crea la factura con el proveedorSeleccionado
     * @return
     */
    public String facturaSinReferencia() {
        gestorFactura.crearFactura(this.proveedorSeleccionado);
        this.facturaDetallesAgregados = gestorFactura.getFacturaNoConfirmada().getDetalles();
        this.error = "";
        return "seleccionarArticulos?faces-redirect=true";
    }

    public String seleccionarProveedorSiguiente() {
        if (this.proveedorSeleccionado != null) {
            this.error = "";
            return "mostrarDatosProveedor?faces-redirect=true";
        } else {
            this.error = "Por favor, seleccione un Proveedor.";
            return null;
        }
    }

    public Factura getFacturaNoConfirmada() {
        return gestorFactura.getFacturaNoConfirmada();
    }

    private void actualizarPrecioArtProveedor() {
        ArticuloPresentacionPrecioProveedor a;
        Factura f = gestorFactura.getFacturaNoConfirmada();
        VistaArticuloProveedor vistaArticuloProveedor = (VistaArticuloProveedor) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaArticuloProveedor");
        for (FacturaDetalle detalle : f.getDetalles()) {
            if (detalle instanceof FacturaDetalleCompra) {
                //lo siguiente se hace para actualizar el precio del articulo, en cada pasada del for, para el proveedor seleccionado
                a = gestorFactura.detalleSeleccionadoAArticuloPrecioProveedor((FacturaDetalleCompra) detalle);
                // el 2do paramatro se usa solo cuando se trata de una madera, cuando es un insumo no cambia nada
                vistaArticuloProveedor.actualizarArticuloPrecioProveedor(a, VistaArticuloProveedor.ACTUALIZAR_PRECIO_PIE_CUBICO);


            }
        }
    }

    public void actualizarEstadoFacturaAEntregada(Factura factura) {
        gestorFactura.actualizarEstadoFacturaAEntregada(factura);
    }

    ////////////////////////////////
    public MuebleModelo getMuebleModeloSeleccionado() {
        return muebleModeloSeleccionado;
    }

    public void abrirFactura(Entidad entidad) {
        this.facturaSeleccionada = new Factura();
        this.facturaSeleccionada.setEntidad(entidad);
    }

    public void setMuebleModeloSeleccionado(MuebleModelo muebleModeloSeleccionado) {

        this.muebleModeloSeleccionado = muebleModeloSeleccionado;
        if (this.muebleModeloSeleccionado != null) {
        }
    }

    public void seleccionDetalle(FacturaDetalle facturaDetalle) {
        this.facturaDetalleActual = facturaDetalle;
    }

    public void quitarDetalle(FacturaDetalle facturaDetalle) {
        this.facturaSeleccionada.getDetalles().remove(facturaDetalle);
        if (this.pedido != null) {
            facturaDetallesEliminados.add(facturaDetalle);
        }
    }

    public void accionAgregar() {
        this.facturaDetalleActual = null;
        this.cantidad = null;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;

    }

    public void agregarDetalleVenta() {
        yaAgregado = false;

        if (facturaSeleccionada.getDetalles() == null) {
            facturaSeleccionada.setDetalles(new ArrayList<FacturaDetalle>());
        }
        FacturaDetalle facturaDetalle = new FacturaDetalleVenta();
        facturaDetalle.setCantidad(cantidad);
        System.out.print("el nobre del mueble es " + muebleModeloSeleccionado.getNombre());
        ((FacturaDetalleVenta) facturaDetalle).setMuebleModelo(muebleModeloSeleccionado);
        ((FacturaDetalleVenta) facturaDetalle).setPrecio(muebleModeloSeleccionado.getPrecioVenta());
        for (FacturaDetalle detalle : this.facturaSeleccionada.getDetalles()) {
            if (((FacturaDetalleVenta) detalle).getMuebleModelo().getNombre().equalsIgnoreCase(((FacturaDetalleVenta) facturaDetalle).getMuebleModelo().getNombre())) {
                yaAgregado = true;
            }
        }
        if (yaAgregado == false) {
            facturaSeleccionada.getDetalles().add(facturaDetalle);

        }
        this.error = "";


    }

    public FacturaDetalle getFacturaDetalleSeleccionado() {
        return facturaDetalleActual;

    }

    public String siguienteSeleccionarMuebles() {
        if (facturaSeleccionada.getDetalles() != null && !facturaSeleccionada.getDetalles().isEmpty()) {
            this.error = "";
            this.facturaSeleccionada.calcularTotal();
            this.nroFactura = this.gestorFactura.devolverNumeroDisponibleParaFactura();
            return "confirmarVenta?faces-redirect=true";
        } else {
            this.error = "Por favor, agregue por lo menos un detalle la venta.";
            return null;
        }
    }

    public String devolverNroFactura() {
        return this.nroFactura;
    }

    public Collection<MuebleModelo> devolverMueblesModelo() {
        Collection<MuebleModelo> mm = new ArrayList<MuebleModelo>();
        if (facturaSeleccionada.getDetalles() == null) {
            facturaSeleccionada.setDetalles(new ArrayList<FacturaDetalle>());
        }
        for (FacturaDetalle detalle : this.facturaSeleccionada.getDetalles()) {
            mm.add(((FacturaDetalleVenta) detalle).getMuebleModelo());

        }

        VistaMuebleModelo vistaMuebleModelo = (VistaMuebleModelo) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaMuebleModelo");
        if (vistaMuebleModelo == null) {
            vistaMuebleModelo = new VistaMuebleModelo();
        }
        vistaMuebleModelo.instanciarGestor();
        return vistaMuebleModelo.devolverMueblesModeloAgregar(mm);
        //return this.gestorFactura.devolverMueblesModeloAgregar(mm);

    }

    public void confirmarFacturaVenta() {
        if (this.presupuesto != null) {
            this.presupuesto.setUsado(true);
            this.presupuesto.actualizarEstado();
            VistaPresupuesto vistaPresupuesto = (VistaPresupuesto) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaPresupuesto");
            vistaPresupuesto.setPresupuestoSeleccionado(this.presupuesto);
            vistaPresupuesto.actualizarPresupuesto();
        }
        if (this.pedido != null) {
            actualizarPedido();
        }
        this.facturaSeleccionada.setNumero(this.nroFactura);
        gestorFactura.confirmarFacturaVenta(this.facturaSeleccionada);
    }

    public void abrirFacturaAPartirDePresupuesto(Presupuesto presupuesto) {
        this.presupuesto = presupuesto;
        this.facturaSeleccionada = new Factura();
        this.facturaSeleccionada.setEntidad(presupuesto.getEntidad());
        ArrayList<FacturaDetalle> detalles = new ArrayList<FacturaDetalle>();
        for (PresupuestoDetalle presupuestoDetalle : presupuesto.getDetalles()) {
            FacturaDetalle facturaDetalle = new FacturaDetalleVenta();
            if (presupuestoDetalle.getMuebleModelo().getStockDisponible().compareTo(presupuestoDetalle.getCantidad()) > 0) {
                facturaDetalle.setCantidad(presupuestoDetalle.getCantidad());
            } else {
                facturaDetalle.setCantidad(presupuestoDetalle.getMuebleModelo().getStockDisponible());
            }
            ((FacturaDetalleVenta) facturaDetalle).setMuebleModelo(presupuestoDetalle.getMuebleModelo());
            facturaDetalle.setPrecio(presupuestoDetalle.getPrecioUnitario());
            detalles.add(facturaDetalle);
        }

        this.facturaSeleccionada.setDetalles(detalles);
    }

    public void abrirFacturaAPartirDePedido(Pedido pedido) {
        this.facturaDetallesEliminados = new ArrayList<FacturaDetalle>();
        this.pedido = pedido;
        this.facturaSeleccionada = new Factura();
        this.facturaSeleccionada.setEntidad(pedido.getEntidad());
        ArrayList<FacturaDetalle> detalles = new ArrayList<FacturaDetalle>();
        for (PedidoDetalle pedidoDetalle : pedido.getDetalles()) {
            FacturaDetalle facturaDetalle = new FacturaDetalleVenta();
            facturaDetalle.setCantidad(pedidoDetalle.getCantidad());

            ((FacturaDetalleVenta) facturaDetalle).setMuebleModelo(pedidoDetalle.getMuebleModelo());
            facturaDetalle.setPrecio(pedidoDetalle.getPrecioUnitario());
            detalles.add(facturaDetalle);
        }

        this.facturaSeleccionada.setDetalles(detalles);
    }

    ////////////Funciones para editar el detalle de factura a partir de pedido
    public FacturaDetalle getPedidoDetalleSeleccionado() {
        return facturaDetalleSeleccionado;
    }

    public void setPedidoDetalleSeleccionado(FacturaDetalle facturaDetalleSeleccionado) {
        this.facturaDetalleSeleccionado = facturaDetalleSeleccionado;
    }

    public Collection<FacturaDetalle> facturaDetallesEliminados() {
        return this.facturaDetallesEliminados;
    }

    public void agregarDetalleEliminado() {

        if (this.facturaDetalleSeleccionado != null) {

            this.facturaDetalleSeleccionado.setCantidad(cantidad);
            this.facturaSeleccionada.getDetalles().add(facturaDetalleSeleccionado);
            this.facturaDetallesEliminados.remove(facturaDetalleSeleccionado);
        }
    }

    public void actualizarPedido() {//TODO terminar esto
        this.facturaDetallesEliminados.isEmpty();
        ArrayList<PedidoDetalle> detallesPedido = new ArrayList<PedidoDetalle>();
        for (FacturaDetalle facturaDetalle : facturaSeleccionada.getDetalles()) {
            PedidoDetalle pedidoDetalle = new PedidoDetalle();
            pedidoDetalle.setCantidadFacturada(facturaDetalle.getCantidad());

            pedidoDetalle.setEstadoFacturadoNoEntregado();
        }
        ////
        ArrayList<FacturaDetalle> detalles = new ArrayList<FacturaDetalle>();
        for (PedidoDetalle pedidoDetalle : pedido.getDetalles()) {
            FacturaDetalle facturaDetalle = new FacturaDetalleVenta();
            facturaDetalle.setCantidad(pedidoDetalle.getCantidad());

            ((FacturaDetalleVenta) facturaDetalle).setMuebleModelo(pedidoDetalle.getMuebleModelo());
            facturaDetalle.setPrecio(pedidoDetalle.getPrecioUnitario());
            detalles.add(facturaDetalle);
        }

        this.facturaSeleccionada.setDetalles(detalles);

    }
}
