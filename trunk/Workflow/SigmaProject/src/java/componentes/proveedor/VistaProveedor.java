/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package componentes.proveedor;

import entidades.Proveedor;
import java.util.Collection;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Zaba
 */
@ManagedBean
@SessionScoped
public class VistaProveedor implements java.io.Serializable {
    @EJB
    private GestorProveedor gestorProveedor;

    public Collection<Proveedor> devolverProveedores() {
        return gestorProveedor.devolverProveedores();
    }

    public void descartarCambiosProveedor(Proveedor proveedor) {
        gestorProveedor.descartarCambiosProveedor(proveedor);
    }

    public void actualizarProveedor(Proveedor proveedor) {
        gestorProveedor.actualizarProveedor(proveedor);
    }
}
