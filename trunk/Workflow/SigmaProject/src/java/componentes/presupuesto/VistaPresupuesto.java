/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes.presupuesto;

import entidades.*;
import java.math.BigDecimal;
import java.util.*;
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
public class VistaPresupuesto implements java.io.Serializable {

    @EJB
    private GestorPresupuesto gestorPresupuesto;

    private Presupuesto presupuestoSeleccionado;
    private MuebleModelo muebleModeloSeleccionado;
    private PresupuestoDetalle presupuestoDetalleSeleccionado;
    private BigDecimal cantidad;
    private String error;

    public void instanciarGestor() {
        if (this.gestorPresupuesto == null) {
            try {
                InitialContext ic = new InitialContext();
                this.gestorPresupuesto = (GestorPresupuesto) ic.lookup("java:global/SigmaProject/GestorPresupuesto");
            } catch (NamingException ex) {
                Logger.getLogger(VistaPresupuesto.class.getName()).log(Level.SEVERE, null, ex);
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
        if (presupuestoSeleccionado.getDetalles() == null) {
            presupuestoSeleccionado.setDetalles(new ArrayList<PresupuestoDetalle>());
        }
        PresupuestoDetalle presupuestoDetalle = new PresupuestoDetalle();
        presupuestoDetalle.setCantidad(cantidad);
        presupuestoDetalle.setMuebleModelo(muebleModeloSeleccionado);
        presupuestoDetalle.setPrecioUnitario(muebleModeloSeleccionado.getPrecioVenta());
        presupuestoSeleccionado.getDetalles().add(presupuestoDetalle);
        this.error = "";
    }

    public void quitarDetalle(PresupuestoDetalle presupuestoDetalle) {
        this.presupuestoSeleccionado.getDetalles().remove(presupuestoDetalle);
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public PresupuestoDetalle getPresupuestoDetalleSeleccionado() {
        return presupuestoDetalleSeleccionado;
    }

    public void setPresupuestoDetalleSeleccionado(PresupuestoDetalle presupuestoDetalleSeleccionado) {
        this.presupuestoDetalleSeleccionado = presupuestoDetalleSeleccionado;
    }

    public void accionAgregar() {
        this.presupuestoDetalleSeleccionado = null;
        this.cantidad = null;
    }

    public void seleccionDetalle(PresupuestoDetalle presupuestoDetalle) {
        this.presupuestoDetalleSeleccionado = presupuestoDetalle;
    }

    public MuebleModelo getMuebleModeloSeleccionado() {
        return muebleModeloSeleccionado;
    }

    public void setMuebleModeloSeleccionado(MuebleModelo muebleModeloSeleccionado) {
        this.muebleModeloSeleccionado = muebleModeloSeleccionado;
    }

    public Collection<Presupuesto> devolverPresupuestos() {
        return gestorPresupuesto.devolverPresupuestos();
    }

    public Collection<Presupuesto> devolverPresupuestosNoUsados() {
        return gestorPresupuesto.devolverPresupuestosNoUsados();
    }

    public Collection<Presupuesto> devolverPresupuestosCliente(Cliente cliente) {
        return gestorPresupuesto.devolverPresupuestosCliente(cliente);
    }

    public Collection<Presupuesto> devolverPresupuestosClienteVigentesNoUsados(Cliente cliente) {
        return gestorPresupuesto.devolverPresupuestosClienteVigentesNoUsados(cliente);
    }

    public Presupuesto getPresupuestoSeleccionado() {
        return presupuestoSeleccionado;
    }

    public void setPresupuestoSeleccionado(Presupuesto presupuestoSeleccionado) {
        this.presupuestoSeleccionado = presupuestoSeleccionado;
    }

    public void abrirPresupuesto(Entidad entidad) {
        this.presupuestoSeleccionado = new Presupuesto();
        this.presupuestoSeleccionado.setEntidad(entidad);
    }

    public void confirmarPresupuesto() {
        gestorPresupuesto.confirmarPresupuesto(presupuestoSeleccionado);
    }
    public void actualizarPresupuesto(){
        gestorPresupuesto.actualizarPresupuesto(presupuestoSeleccionado);
    }

    public String siguienteSeleccionarMuebles() {
        if (presupuestoSeleccionado.getDetalles() != null && !presupuestoSeleccionado.getDetalles().isEmpty()) {
            this.presupuestoSeleccionado.calcularTotal();
            this.error = "";
            return "confirmarPresupuesto?faces-redirect=true";
        } else {
            this.error = "Por favor, agregue por lo menos un detalle al presupuesto.";
            return null;
        }
    }
}
