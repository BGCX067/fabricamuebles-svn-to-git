/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package componentes.fabricamuebletipocapacidadmaxima;

import entidades.Fabrica;
import entidades.FabricaMuebleTipoCapacidadMaxima;
import entidades.MuebleTipo;
import java.util.Collection;
import java.util.logging.*;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Cristian
 */
@Stateless
public class GestorFabricaMuebleTipoCapacidadMaxima {
    @PersistenceContext
    EntityManager em;


    public Collection<FabricaMuebleTipoCapacidadMaxima> getFabricasMueblesTipoCapacidadMaxima (){
        return em.createQuery("select fmtc from FabricaMuebleTipoCapacidadMaxima fmtc").getResultList();
    }
    public Collection<FabricaMuebleTipoCapacidadMaxima> getFabricasMueblesTipoCapacidadMaximaPorFabrica (Fabrica fabricaSeleccionada){
         return em.createQuery("select fmtc from FabricaMuebleTipoCapacidadMaxima fmtc where fmtc.fabrica = :fabrica").setParameter("fabrica", fabricaSeleccionada).getResultList();
    }
    public void registrarFabricaMuebleTipoCapacidadMaxima (FabricaMuebleTipoCapacidadMaxima fabricaMuebleTipoCapacidadMaximaSeleccionada){
        em.persist(fabricaMuebleTipoCapacidadMaximaSeleccionada);
    }
    public void actualizarFabricaMuebleTipoCapacidadMaxima(FabricaMuebleTipoCapacidadMaxima fabricaMuebleTipoCapacidadMaximaSeleccionada){
        em.merge(fabricaMuebleTipoCapacidadMaximaSeleccionada);
    }

    public Integer devolverCapacidadMaximaParaFabricaYTipo(Fabrica fabrica, MuebleTipo muebleTipo) {
        try {
            return (Integer) em.createQuery("select fmtcm.capacidadMaxima from FabricaMuebleTipoCapacidadMaxima fmtcm where fmtcm.fabrica = :fabrica and fmtcm.muebleTipo = :muebleTipo").setParameter("fabrica", fabrica).setParameter("muebleTipo", muebleTipo).getSingleResult();
        } catch (NoResultException ex) {
            Logger.getLogger(GestorFabricaMuebleTipoCapacidadMaxima.class.getName()).log(Level.INFO, "No existe una capacidad máxima asignada para la fabrica y mueble tipo pasados por parámetro.");
        }
        return null;
    }

}
