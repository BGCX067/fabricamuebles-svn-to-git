/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package componentes.fabrica;

import entidades.Direccion;
import entidades.Fabrica;
import entidades.Telefono;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.ejb.EntityManagerImpl;

/**
 *
 * @author Cristian
 */
@Stateless
public class GestorFabrica {
    @PersistenceContext
    EntityManager em;

    public void registrarNuevaFabrica(Fabrica fabricaSeleciconada){
        Collection<Direccion> direcciones = fabricaSeleciconada.getDirecciones();
        Collection<Telefono> telefonos = fabricaSeleciconada.getTelefonos();
        fabricaSeleciconada.setDirecciones(null);
        fabricaSeleciconada.setTelefonos(null);
        em.persist(fabricaSeleciconada);
        fabricaSeleciconada.setDirecciones(direcciones);
        fabricaSeleciconada.setTelefonos(telefonos);
        em.merge(fabricaSeleciconada);
    }
    public void actualizarFabricaSeleccionada(Fabrica fabricaSeleccionada){
        em.merge(fabricaSeleccionada);
    }
    public void descartarCambiosFabrica(Fabrica fabrica) {
        ((EntityManagerImpl) em.getDelegate()).getSession().refresh(fabrica);
    }
    public Collection<Fabrica> devolverFabricas() {
        return em.createQuery("select f from Fabrica f").getResultList();
    }
    

}
