/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package componentes.proveedor;

import entidades.Proveedor;
import java.util.Collection;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.ejb.EntityManagerImpl;

/**
 *
 * @author Zaba
 */
@Stateful
public class GestorProveedor {
    @PersistenceContext
    EntityManager em;

    public Collection<Proveedor> devolverProveedores() {
        return em.createQuery("select p from Proveedor p").getResultList();
    }

    public void actualizarProveedor(Proveedor proveedor) {
        em.merge(proveedor);
    }

    public void descartarCambiosProveedor(Proveedor proveedor) {
        ((EntityManagerImpl)em.getDelegate()).getSession().refresh(proveedor);
    }
    
}
