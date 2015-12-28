/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package generico;

import componentes.direccion.VistaDireccion;
import entidades.Direccion;
import entidades.OrdenDeCompra;
import entidades.Presupuesto;
import entidades.Proveedor;
import entidades.Remito;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateful;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.ejb.EntityManagerImpl;

/**
 *
 * @author Zaba
 */
@Stateful
public class GestorGenerico {
    @PersistenceContext
    EntityManager em;

    private OrdenDeCompra ordenDeCompraSeleccionada;
    private Presupuesto presupuestoSeleccionado;
    private Proveedor proveedorSeleccionado;
    private Remito remitoSeleccionado;

    public GestorGenerico() {
    }

    public Collection<OrdenDeCompra> devolverOrdenesDeCompra() {
        return em.createQuery("select odc from OrdenDeCompra odc").getResultList();
    }

    public Collection<Presupuesto> devolverPresupuestos() {
        return em.createQuery("select p from Presupuesto p").getResultList();
    }

    public Collection<Proveedor> devolverProveedores() {
        return em.createQuery("select p from Proveedor p").getResultList();
    }

    public void setOrdenDeCompraSeleccionada(OrdenDeCompra odc) {
        this.ordenDeCompraSeleccionada = odc;
    }

    public OrdenDeCompra getOrdenDeCompraSeleccionada() {
        return this.ordenDeCompraSeleccionada;
    }    

    public void setPresupuestoSeleccionado(Presupuesto p) {
        this.presupuestoSeleccionado = p;
    }

    public Presupuesto getPresupuestoSeleccionado() {
        return this.presupuestoSeleccionado;
    }

    public void setProveedorSeleccionado(Proveedor p) {
        this.proveedorSeleccionado = p;
    }

    public Proveedor getProveedorSeleccionado() {
        return this.proveedorSeleccionado;
    }

    public void reiniciar() {
        this.ordenDeCompraSeleccionada = null;
        this.presupuestoSeleccionado = null;
        this.proveedorSeleccionado = null;
        this.remitoSeleccionado = null;
    }
    public void descartarCambiosProveedor() {
        VistaDireccion vistaDireccion = (VistaDireccion) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaDireccion");
        vistaDireccion.getGestorDireccion().reiniciarDireccionesABorrar();
        vistaDireccion.getGestorDireccion().reiniciarDireccionesAAgregar();
        ((EntityManagerImpl)em.getDelegate()).getSession().refresh(this.proveedorSeleccionado);
    }
    public void actualizarProveedor() {
        VistaDireccion vistaDireccion = (VistaDireccion) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaDireccion");
        vistaDireccion.getGestorDireccion().borrarDireccionesABorrar();
        this.proveedorSeleccionado = em.merge(this.proveedorSeleccionado);
    }

    public void inhabilitarProveedor(){
        this.proveedorSeleccionado.setHabilitado(false);
        this.proveedorSeleccionado = em.merge(this.proveedorSeleccionado);
    }

    public void habilitarProveedor(){
        this.proveedorSeleccionado.setHabilitado(true);
        this.proveedorSeleccionado = em.merge(this.proveedorSeleccionado);
    }

    public Collection<OrdenDeCompra> devolverOrdenesDeCompraNoCompletas(Proveedor proveedor) {
        return em.createQuery("select odc from OrdenDeCompra odc where odc.proveedor = :proveedor and odc.estado <> :estadoRecibido")
                .setParameter("proveedor", proveedor)
                .setParameter("estadoRecibido", OrdenDeCompra.ESTADO_RECIBIDO_COMPLETO).getResultList();
    }
}
