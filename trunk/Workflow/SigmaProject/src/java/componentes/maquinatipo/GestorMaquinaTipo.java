/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package componentes.maquinatipo;

import entidades.MaquinaTipo;
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
public class GestorMaquinaTipo {
    @PersistenceContext
    private EntityManager em;

    public Collection<MaquinaTipo> devolverMaquinasTipo() {
        return em.createQuery("select mt from MaquinaTipo mt").getResultList();
    }

    public void descartarCambiosMaquinaTipo(MaquinaTipo maquinaTipo) {
        ((EntityManagerImpl) em.getDelegate()).getSession().refresh(maquinaTipo);
    }

    public void actualizarMaquinaTipo(MaquinaTipo maquinaTipo) {
        em.merge(maquinaTipo);
    }

    public void registrarMaquinaTipo(MaquinaTipo maquinaTipoSeleccionada) {
        em.persist(maquinaTipoSeleccionada);
    }

}
