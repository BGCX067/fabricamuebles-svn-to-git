/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes.pedido;

import componentes.mueblemodelos.VistaMuebleModelo;
import componentes.pmp.VistaPMP;
import entidades.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.logging.*; 
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.naming.*;

/**
 *
 * @author Zaba
 */
@ManagedBean
@SessionScoped
public class VistaPedido implements java.io.Serializable {

    @EJB
    private GestorPedido gestorPedido;

    private Pedido pedidoSeleccionado;
    private MuebleModelo muebleModeloSeleccionado;
    private PedidoDetalle pedidoDetalleSeleccionado;
    private BigDecimal cantidad;
    private String error;

    public void instanciarGestor() {
        if (this.gestorPedido == null) {
            try {
                InitialContext ic = new InitialContext();
                this.gestorPedido = (GestorPedido) ic.lookup("java:global/SigmaProject/GestorPedido");
            } catch (NamingException ex) {
                Logger.getLogger(VistaPedido.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void agregarDetalle() {
        if (pedidoSeleccionado.getDetalles() == null) {
            pedidoSeleccionado.setDetalles(new ArrayList<PedidoDetalle>());
        }
        PedidoDetalle pedidoDetalle = new PedidoDetalle();
        pedidoDetalle.setCantidad(cantidad);
        pedidoDetalle.setMuebleModelo(muebleModeloSeleccionado);
        pedidoDetalle.setPrecioUnitario(muebleModeloSeleccionado.getPrecioVenta());
        pedidoSeleccionado.getDetalles().add(pedidoDetalle);
        this.error = "";
    }

    public void quitarDetalle(PedidoDetalle PedidoDetalle) {
        this.pedidoSeleccionado.getDetalles().remove(PedidoDetalle);
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public PedidoDetalle getPedidoDetalleSeleccionado() {
        return pedidoDetalleSeleccionado;
    }

    public void setPedidoDetalleSeleccionado(PedidoDetalle PedidoDetalleSeleccionado) {
        this.pedidoDetalleSeleccionado = PedidoDetalleSeleccionado;
    }

    public void accionAgregar() {
        this.pedidoDetalleSeleccionado = null;
        this.cantidad = null;
    }

    public void seleccionDetalle(PedidoDetalle PedidoDetalle) {
        this.pedidoDetalleSeleccionado = PedidoDetalle;
    }

    public MuebleModelo getMuebleModeloSeleccionado() {
        return muebleModeloSeleccionado;
    }

    public void setMuebleModeloSeleccionado(MuebleModelo muebleModeloSeleccionado) {
        this.muebleModeloSeleccionado = muebleModeloSeleccionado;
    }

    public Collection<Pedido> devolverPedidos() {
        return gestorPedido.devolverPedidos();
    }
    public Collection<Pedido>devolverPedidos(Cliente cliente){
        return gestorPedido.devolverPedidos(cliente);
    }

    public Pedido getPedidoSeleccionado() {
        return pedidoSeleccionado;
    }

    public void setPedidoSeleccionado(Pedido PedidoSeleccionado) {
        this.pedidoSeleccionado = PedidoSeleccionado;
    }

    public void abrirPedido(Entidad entidad) {
        this.pedidoSeleccionado = new Pedido();
        this.pedidoSeleccionado.setEntidad(entidad);
    }

    public void abrirPedidoAPartirDePresupuesto(Presupuesto presupuesto) {
        this.pedidoSeleccionado = new Pedido();
        this.pedidoSeleccionado.setEntidad(presupuesto.getEntidad());
        ArrayList<PedidoDetalle> detalles = new ArrayList<PedidoDetalle>();
        for (PresupuestoDetalle presupuestoDetalle : presupuesto.getDetalles()) {
            PedidoDetalle pedidoDetalle = new PedidoDetalle();
            pedidoDetalle.setCantidad(presupuestoDetalle.getCantidad());
            pedidoDetalle.setMuebleModelo(presupuestoDetalle.getMuebleModelo());
            pedidoDetalle.setPrecioUnitario(presupuestoDetalle.getPrecioUnitario());
            detalles.add(pedidoDetalle);
        }
        this.pedidoSeleccionado.setDetalles(detalles);
    }

    public String confirmarPedido() {
        gestorPedido.confirmarPedido(pedidoSeleccionado);
        VistaPMP vistaPMP = (VistaPMP) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaPMP");
        if (vistaPMP == null) {
            vistaPMP = new VistaPMP();
            vistaPMP.instanciarGestor();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("vistaPMP", vistaPMP);
        }
        vistaPMP.agregarPedidosDetalleAPMP(pedidoSeleccionado.getDetalles());
        return "informarPedidoRegistrado?faces-redirect=true";
    }

    public String siguienteSeleccionarMuebles() {
        if (pedidoSeleccionado.getDetalles() != null && !pedidoSeleccionado.getDetalles().isEmpty()) {
            this.error = "";
            pedidoSeleccionado.setFechaCreacion(new Date());
            pedidoSeleccionado.setNumero(gestorPedido.devolverNumeroDisponibleParaPedido());
            pedidoSeleccionado.calcularTotal();
            return "confirmarPedido?faces-redirect=true";
        } else {
            this.error = "Por favor, agregue por lo menos un detalle al Pedido.";
            return null;
        }
    }

    public void actualizarPedido(Pedido pedido) {
        gestorPedido.actualizarPedido(pedido);
    }

    public Collection<MuebleModelo> devolverMueblesModelo() {
           Collection<MuebleModelo> mm = new ArrayList<MuebleModelo>();
           if (this.pedidoSeleccionado.getDetalles() == null) {
            this.pedidoSeleccionado.setDetalles(new ArrayList<PedidoDetalle>());
           }
           for (PedidoDetalle detalle : this.pedidoSeleccionado.getDetalles()){
               mm.add(detalle.getMuebleModelo());
           }

         VistaMuebleModelo vistaMuebleModelo = (VistaMuebleModelo) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaMuebleModelo");
         if(vistaMuebleModelo == null){
             vistaMuebleModelo = new VistaMuebleModelo();
         }
         vistaMuebleModelo.instanciarGestor();
         return vistaMuebleModelo.devolverMueblesModeloAgregar(mm);

    }
}
