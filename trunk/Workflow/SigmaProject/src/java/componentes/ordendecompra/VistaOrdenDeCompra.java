/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes.ordendecompra;

import entidades.*;
import java.util.logging.*;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Zaba
 */
@ManagedBean
@SessionScoped
public class VistaOrdenDeCompra implements java.io.Serializable {

    @EJB
    private GestorOrdenDeCompra gestorOrdenDeCompra;

    public void instanciarGestor() {
        if (this.gestorOrdenDeCompra == null) {
            try {
                InitialContext ic = new InitialContext();
                this.gestorOrdenDeCompra = (GestorOrdenDeCompra) ic.lookup("java:global/SigmaProject/GestorOrdenDeCompra");
            } catch (NamingException ex) {
                Logger.getLogger(VistaOrdenDeCompra.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void actualizarEstadoOrdenesDeCompra(OrdenDeCompra[] ordenesDeCompraSeleccionadas, Remito remito) {
        if (ordenesDeCompraSeleccionadas != null && ordenesDeCompraSeleccionadas.length > 0 && remito != null) {
            gestorOrdenDeCompra.actualizarEstadoOrdenesDeCompra(ordenesDeCompraSeleccionadas, remito);
        }
    }
}
