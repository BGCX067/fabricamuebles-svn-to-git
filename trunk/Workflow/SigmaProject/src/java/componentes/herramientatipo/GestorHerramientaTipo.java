/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package componentes.herramientatipo;

import entidades.HerramientaTipo;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.ejb.EntityManagerImpl;

/**
 *
 * @author DanielH
 */
@Stateless
public class GestorHerramientaTipo {
    @PersistenceContext
    private EntityManager em;

    public void registrarHerramientaTipo(HerramientaTipo herramientaTipo) {
        em.persist(herramientaTipo);
    }

    public Collection<HerramientaTipo> devolverHerramientasTipo() {
        return em.createQuery("select mt from HerramientaTipo mt").getResultList();
    }

    public void descartarCambiosHerramientaTipo(HerramientaTipo herramientaTipo) {
        ((EntityManagerImpl) em.getDelegate()).getSession().refresh(herramientaTipo);
    }

    public void actualizarHerramientaTipo(HerramientaTipo herramientaTipo) {
        em.merge(herramientaTipo);
    }

}
