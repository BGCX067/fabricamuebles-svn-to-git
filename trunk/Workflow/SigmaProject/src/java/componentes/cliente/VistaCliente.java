/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package componentes.cliente;

import entidades.Cliente;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.faces.bean.*;

/**
 *
 * @author Cristian
 */
@ManagedBean
@SessionScoped

public class VistaCliente implements java.io.Serializable {
    @EJB
    private GestorCliente gestorCliente;
    private Cliente clienteSeleccionado;

     public void instanciarGestor() {
        if (gestorCliente == null) {
            try {
                InitialContext ic = new InitialContext();
                gestorCliente = (GestorCliente) ic.lookup("java:global/SigmaProject/GestorCliente");
            } catch (NamingException ex) {
                Logger.getLogger(VistaCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Cliente getClienteSeleccionado() {
        return clienteSeleccionado;
    }

    public void setClienteSeleccionado(Cliente clienteSeleccionado) {
        this.clienteSeleccionado = clienteSeleccionado;
    }

    public Collection<Cliente> devolverClientes() {
        return gestorCliente.devolverClientes();
    }

    public void descartarCambiosCliente(Cliente cliente) {
        gestorCliente.descartarCambiosCliente(cliente);
    }

    public void actualizarCliente(Cliente cliente) {
        gestorCliente.actualizarCliente(cliente);
    }
     public void registrarCliente() {
       gestorCliente.registrarCliente();
    }
    public Cliente getNuevoCliente(){
        return gestorCliente.getNuevoCliente();
    }
}
