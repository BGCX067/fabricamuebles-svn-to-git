/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes.mueblemodelos;

import entidades.ActividadEspecifica;
import entidades.MuebleModelo;
import entidades.MuebleParte;
import entidades.MuebleTipo;
import entidades.RequerimientoMaterial;
import java.util.*;
import javax.ejb.Stateless;
//import javax.management.Query;
/*import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ejb.Stateful;*/
import javax.persistence.*;

/**
 *
 * @author Cristian
 */
@Stateless
public class GestorMuebleModelo {

    @PersistenceContext
    private EntityManager em;

    public Collection<MuebleModelo> devolverMueblesModelo() {
        return em.createQuery("select mm from MuebleModelo mm").getResultList();
    }

    public Collection<MuebleModelo> devolverMueblesModeloConCantidadDisponible() {
        return em.createQuery("select mm from MuebleModelo mm where (mm.stockActual - mm.stockReservado) > 0").getResultList();
    }

    public void registrarEstructuraMuebleModelo(MuebleModelo muebleModelo) {
        if (muebleModelo != null) {
            Collection<ActividadEspecifica> actividades = muebleModelo.getActividades();
            if (actividades != null) {
                for (ActividadEspecifica actividad : actividades) {
                    em.persist(actividad);
                    if (actividad.getRequerimientosMaterial() != null) {
                        for (RequerimientoMaterial requerimientoMaterial : actividad.getRequerimientosMaterial()) {
                            guardarRequerimiento(requerimientoMaterial);
                        }
                    }
                    // Las herramientas tiene una relación ManyToMany, con cascada Persist, así que ya se grabó
                }
            }
            em.merge(muebleModelo);
        }
    }

    private void guardarRequerimiento(RequerimientoMaterial requerimientoMaterial) {
        if (requerimientoMaterial != null) {
            if (requerimientoMaterial.getItemRequerible() instanceof MuebleParte) {
                MuebleParte muebleParte = (MuebleParte) requerimientoMaterial.getItemRequerible();
                Collection<ActividadEspecifica> actividades = muebleParte.getActividades();
                if (actividades != null) {
                    for (ActividadEspecifica actividad : actividades) {
                        em.persist(actividad);
                        if (actividad.getRequerimientosMaterial() != null) {
                            for (RequerimientoMaterial requerimiento : actividad.getRequerimientosMaterial()) {
                                guardarRequerimiento(requerimiento);
                            }
                        }
                        // Las herramientas tiene una relación ManyToMany, con cascada Persist, así que ya se grabó
                    }
                }
            }
            em.persist(requerimientoMaterial);
        }
    }

    public Collection<MuebleModelo> devolverMueblesModeloSinEstructura() {
        List<MuebleModelo> muebleModelos = em.createQuery("select mm from MuebleModelo mm").getResultList();
        Iterator<MuebleModelo> iterator = muebleModelos.iterator();
        while (iterator.hasNext()) {
            MuebleModelo muebleModelo = iterator.next();
            if (muebleModelo.tieneEstructuraProductoDefinida()) {
                iterator.remove();
            }
        }
        return muebleModelos;
    }

    public Collection<MuebleModelo> devolverMueblesModeloConEstructura() {
        List<MuebleModelo> muebleModelos = em.createQuery("select mm from MuebleModelo mm").getResultList();
        Iterator<MuebleModelo> iterator = muebleModelos.iterator();
        while (iterator.hasNext()) {
            MuebleModelo muebleModelo = iterator.next();
            if (!muebleModelo.tieneEstructuraProductoDefinida()) {
                iterator.remove();
            }
        }
        return muebleModelos;
    }

    public Collection<MuebleModelo> devolverMueblesModeloPorTipo(MuebleTipo muebleTipoSeleccionado) {
        return em.createQuery("select mm from MuebleModelo mm where mm.muebleTipo = :muebleTipo").setParameter("muebleTipo", muebleTipoSeleccionado).getResultList();
    }

    /** Este metodo se crea para que en el caso de registrarce una factura de compra u presupuesto o cualquier otro
     * comprobante que incluya en su detalle objetos del tipo MuebleModelo se filtren los Muebles Modelo que ya estan
     * incluidos en dicho detalle, evitando que se agregue en el mismo detalle dos instancias del mismo Mueble Modelo
     */
    public Collection<MuebleModelo> devolverMueblesModeloAgregar(Collection<MuebleModelo> mueblesModelo) {
        if (mueblesModelo != null) {

            StringBuilder sbMuebleModelo = new StringBuilder();
            Iterator<MuebleModelo> it = mueblesModelo.iterator();
            int i = 1;

            for (MuebleModelo detalle : mueblesModelo) {

                sbMuebleModelo.append(":m").append(i).append(", ");
                i++;
            }
            if (sbMuebleModelo.length() > 0) {
                sbMuebleModelo.deleteCharAt(sbMuebleModelo.length() - 1);
                sbMuebleModelo.deleteCharAt(sbMuebleModelo.length() - 1);
            }

            Query queryMuebleModelo = null;
            if (sbMuebleModelo.length() > 0) {
                queryMuebleModelo = em.createQuery("select m from MuebleModelo m where m not in (" + sbMuebleModelo.toString() + ")");
            } else {
                queryMuebleModelo = em.createQuery("select m from MuebleModelo m");
            }

            if (mueblesModelo != null) {
                Iterator<MuebleModelo> it1 = mueblesModelo.iterator();
                int j = 1;
                for (MuebleModelo fd : mueblesModelo) {
                    queryMuebleModelo.setParameter("m" + j, fd);
                    j++;
                }
            }
            Collection<MuebleModelo> muebleModelo = new ArrayList<MuebleModelo>();
            muebleModelo.addAll(queryMuebleModelo.getResultList());

            return muebleModelo;
        }
        return new ArrayList<MuebleModelo>();
    }
}
