/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package componentes.herramienta;

import entidades.Herramienta;
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
public class GestorHerramienta {
    @PersistenceContext
    private EntityManager em;
    Herramienta nueva;

    public GestorHerramienta() {
        nueva = new Herramienta();
    }

    public void registrarHerramienta() {
        em.persist(nueva);
    }

    public Herramienta getNuevaHerramienta() {
        return nueva;
    }

    public Collection<Herramienta> devolverHerramientas() {
        return em.createQuery("select p from Herramienta p").getResultList();
    }

    public void descartarCambiosHerramienta(Herramienta herramienta) {
        ((EntityManagerImpl) em.getDelegate()).getSession().refresh(herramienta);
    }

    public void actualizarHerramienta(Herramienta herramienta) {
        em.merge(herramienta);
    }

}
