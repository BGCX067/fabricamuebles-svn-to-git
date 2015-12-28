/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package generico;

import entidades.Direccion;
import entidades.OrdenDeCompra;
import entidades.Presupuesto;
import entidades.Proveedor;
import entidades.Remito;
import java.util.Collection;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.NamingException;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Zaba
 */
@ManagedBean(name = "generico")
@SessionScoped
public class VistaGenerico implements java.io.Serializable {

    @EJB
    private GestorGenerico gestorGenerico;

    /** Creates a new instance of VistaGenerico */
    public VistaGenerico() {
    }

    public Collection<OrdenDeCompra> devolverOrdenesDeCompra() {
        return gestorGenerico.devolverOrdenesDeCompra();
    }

    public Collection<OrdenDeCompra> devolverOrdenesDeCompraNoCompletas(Proveedor proveedor) {
        return gestorGenerico.devolverOrdenesDeCompraNoCompletas(proveedor);
    }

    public Collection<Presupuesto> devolverPresupuestos() {
        return gestorGenerico.devolverPresupuestos();
    }

    public Collection<Proveedor> devolverProveedores() {
        return gestorGenerico.devolverProveedores();
    }

    public void setOrdenDeCompraSeleccionada(OrdenDeCompra odc) {
        gestorGenerico.setOrdenDeCompraSeleccionada(odc);
    }

    public OrdenDeCompra getOrdenDeCompraSeleccionada() {
        return gestorGenerico.getOrdenDeCompraSeleccionada();
    }

    public void setPresupuestoSeleccionado(Presupuesto p) {
        gestorGenerico.setPresupuestoSeleccionado(p);
    }

    public Presupuesto getPresupuestoSeleccionado() {
        return gestorGenerico.getPresupuestoSeleccionado();
    }

    public void setProveedorSeleccionado(Proveedor p) {
        gestorGenerico.setProveedorSeleccionado(p);
    }

    public Proveedor getProveedorSeleccionado() {
        return gestorGenerico.getProveedorSeleccionado();
    }

    public String seleccionPresupuesto(SelectEvent evento) {
        return "mostrarDatosPresupuesto?faces-redirect=true";
    }

    public String seleccionOrdenDeCompra(SelectEvent evento) {
        return "mostrarDatosOrdenDeCompra?faces-redirect=true";
    }

    public String seleccionProveedor(SelectEvent evento) {
        return "mostrarDatosProveedor?faces-redirect=true";
    }

    public String descartarCambiosProveedor() {
        gestorGenerico.descartarCambiosProveedor();
        return "/compras/Proveedores/Listado/mostrarDatosProveedor?faces-redirect=true";
    }

    public Boolean reiniciar() {
        gestorGenerico.reiniciar();
        return false;
    }

    public String actualizarProveedor() {
        gestorGenerico.actualizarProveedor();
        return "/compras/Proveedores/Listado/mostrarDatosProveedor?faces-redirect=true";
    }    
    public String inhabilitarProveedor(){
        gestorGenerico.inhabilitarProveedor();
        return "verListado?faces-redirect=true";
    }
    public String habilitarProveedor(){
        gestorGenerico.habilitarProveedor();
        return "verListado?faces-redirect=true";
    }
}
