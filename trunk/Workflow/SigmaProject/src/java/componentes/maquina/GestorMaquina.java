/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package componentes.maquina;

import entidades.Maquina;
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
public class GestorMaquina {
    @PersistenceContext
    private EntityManager em;
    Maquina nueva;

    public GestorMaquina() {
        nueva = new Maquina();
    }

    public void registrarMaquina() {
        em.persist(nueva);
    }

    public Maquina getNuevaMaquina() {
        return nueva;
    }

    public Collection<Maquina> devolverMaquinas() {
        return em.createQuery("select p from Maquina p").getResultList();
    }

    public void descartarCambiosMaquina(Maquina maquina) {
        ((EntityManagerImpl) em.getDelegate()).getSession().refresh(maquina);
    }

    public void actualizarMaquina(Maquina maquina) {
        em.merge(maquina);
    }

}
