/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package componentes.muebleTipo;

import entidades.MuebleTipo;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Cristian
 */
@Stateless
public class GestorMuebleTipo {
    @PersistenceContext
    private EntityManager em;
    
    public Collection<MuebleTipo> devolverMueblesTipo() {
        return em.createQuery("select mt from MuebleTipo mt").getResultList();
    }
    public void registrarMuebleTipo(MuebleTipo muebleTipo) {
        em.persist(muebleTipo);
    }
    public void actualizarMuebleTipo(MuebleTipo muebleTipo) {
        em.merge(muebleTipo);
    }
    

}
