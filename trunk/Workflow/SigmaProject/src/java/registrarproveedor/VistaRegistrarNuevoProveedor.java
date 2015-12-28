/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package registrarproveedor;


import entidades.OrdenDeCompraDetalle;
import entidades.Proveedor;
import java.util.Collection;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Cristian
 */
@ManagedBean(name = "VistaRegistrarNuevoProveedor")
@SessionScoped
public class VistaRegistrarNuevoProveedor implements java.io.Serializable {

    @EJB
    private GestorRegistrarProveedor gestorRegistrarProveedor;
    private String error;
    private Collection<OrdenDeCompraDetalle> detallesAgregados;
    private OrdenDeCompraDetalle detalleActual;

    /** Creates a new instance of VistaRegistrarOrdenDeCompra */
    public VistaRegistrarNuevoProveedor() {
    }

    /** hago que devuelva String para que pueda ser llamado por cualquier h:outputText
     *
     * @return nada...
     */
    public Boolean reiniciar() {
        this.error = "";
        this.detalleActual = null;
        this.detallesAgregados = null;
        this.gestorRegistrarProveedor.reiniciar();
        return false;
    }

    public String getError() {
        if (error == null) {
            error = "";
        }
        return error;
    }

  
    /*
    public Proveedor getProveedorSeleccionado() {
    return gestorRegistrarOrdenDeCompra.getProveedorSeleccionado();
    }

     */

    public void setNuevoProveedor(Proveedor p) {
        gestorRegistrarProveedor.setNuevoProveedor(p);
    }

    public Proveedor getNuevoProveedor() {
        return gestorRegistrarProveedor.getNuevoProveedor();
    }

   
    public String confirmarDatosProveedor() {
        return "seleccionarArticulos?faces-redirect=true";
    }

   
    public String registrarProveedor() {
        gestorRegistrarProveedor.registrarProveedor();
        return "/index?faces-redirect=true";
    }
/////////////////////////////

    public String descartarCambiosProveedor() {
        //gestorRegistrarProveedor.descartarCambiosProveedor();
        return "/index?faces-redirect=true";

    }
}
