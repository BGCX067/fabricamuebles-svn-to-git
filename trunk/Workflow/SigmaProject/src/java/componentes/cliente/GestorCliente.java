/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes.cliente;

import entidades.Cliente;
import entidades.Direccion;
import entidades.Telefono;
import java.util.Collection;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.ejb.EntityManagerImpl;

/**
 *
 * @author Cristian
 */
@Stateful
public class GestorCliente {

    @PersistenceContext
    EntityManager em;
    Cliente nuevo;

    public GestorCliente() {
        nuevo = new Cliente();
    }

    public Collection<Cliente> devolverClientes() {
        return em.createQuery("select p from Cliente p").getResultList();
    }

    public void actualizarCliente(Cliente cliente) {
        em.merge(cliente);
    }

    public void descartarCambiosCliente(Cliente cliente) {
        ((EntityManagerImpl) em.getDelegate()).getSession().refresh(cliente);
    }

    public void registrarCliente() {
        Collection<Direccion> direcciones = nuevo.getDirecciones();
        Collection<Telefono> telefonos = nuevo.getTelefonos();
        nuevo.setDirecciones(null);
        nuevo.setTelefonos(null);
        em.persist(nuevo);
        nuevo.setDirecciones(direcciones);
        nuevo.setTelefonos(telefonos);
        em.merge(nuevo);
    }

    public Cliente getNuevoCliente() {
        return nuevo;
    }
}
