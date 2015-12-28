/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes.actividad;

import entidades.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.ejb.Stateful;
import javax.persistence.*;

/**
 *
 * @author Zaba
 */
@Stateful
public class GestorActividad {

    @PersistenceContext
    private EntityManager em;

    public Collection<ActividadEspecifica> devolverActividadesFabricableSeleccionado(Fabricable fabricable) {
        if (fabricable instanceof MuebleModelo) {
            return em.createQuery("select mm.actividades from MuebleModelo mm where mm = :muebleModelo").setParameter("muebleModelo", fabricable).getResultList();
        } else {
            return new ArrayList<ActividadEspecifica>();
        }
    }

    /**
     * Devuelve una lista de requeribles que no esten ya siendo requeridos por la actividad pasada por parametro
     * @param actividadEspecificaSeleccionada actividad a consultar requerimientos que no estan ya requeridos por la misma
     * @return lista de requeribles válidos para la actividad en cuestión
     */
    public Collection<Requerible> devolverRequeriblesAAgregar(MuebleModelo muebleModelo) {
        if (muebleModelo != null) {
            Collection<RequerimientoMaterial> requerimientosMaterial = muebleModelo.getRequerimientosMateriales();
            StringBuilder sbMadera = new StringBuilder();
            StringBuilder sbInsumo = new StringBuilder();
            if (requerimientosMaterial != null) {
                Iterator<RequerimientoMaterial> it = requerimientosMaterial.iterator();
                int i = 1;
                while (it.hasNext()) {
                    RequerimientoMaterial rm = it.next();
                    if (rm.getItemRequerible() instanceof Madera) {
                        sbMadera.append(":m").append(i).append(", ");
                    } else if (rm.getItemRequerible() instanceof Insumo) {
                        sbInsumo.append(":i").append(i).append(", ");
                    }
                    i++;
                }
                if (sbMadera.length() > 0) {
                    sbMadera.deleteCharAt(sbMadera.length() - 1);
                    sbMadera.deleteCharAt(sbMadera.length() - 1);
                }
                if (sbInsumo.length() > 0) {
                    sbInsumo.deleteCharAt(sbInsumo.length() - 1);
                    sbInsumo.deleteCharAt(sbInsumo.length() - 1);
                }
                System.out.println(sbMadera.toString());
                System.out.println(sbInsumo.toString());
            }
            Query queryMadera = null;
            if (sbMadera.length() > 0) {
                queryMadera = em.createQuery("select m from Madera m where m not in (" + sbMadera.toString() + ")");
            } else {
                queryMadera = em.createQuery("select m from Madera m");
            }
            Query queryInsumo = null;
            if (sbInsumo.length() > 0) {
                queryInsumo = em.createQuery("select i from Insumo i where i not in (" + sbInsumo.toString() + ")");
            } else {
                queryInsumo = em.createQuery("select i from Insumo i");
            }
            if (requerimientosMaterial != null) {
                Iterator<RequerimientoMaterial> it = requerimientosMaterial.iterator();
                int i = 1;
                while (it.hasNext()) {
                    RequerimientoMaterial rm = it.next();
                    if (rm.getItemRequerible() instanceof Madera) {
                        queryMadera.setParameter("m" + i, rm.getItemRequerible());
                        System.out.println("Seteo param: " + "m" + i);
                    } else if (rm.getItemRequerible() instanceof Insumo) {
                        queryInsumo.setParameter("i" + i, rm.getItemRequerible());
                        System.out.println("Seteo param: " + "i" + i);
                    }
                    i++;
                }
            }
            Collection<Requerible> requeribles = new ArrayList<Requerible>();
            requeribles.addAll(queryMadera.getResultList());
            requeribles.addAll(queryInsumo.getResultList());
            return requeribles;
        }
        return new ArrayList<Requerible>();
    }

    public Collection<ActividadGenerica> devolverActividadesGenericas() {
        return em.createQuery("select ag from ActividadGenerica ag where ag.class='ActividadGenerica'").getResultList();
    }

    public Collection<Herramienta> devolverHerramientasTipoAAgregar(ActividadEspecifica actividadEspecificaSeleccionada) {
        if (actividadEspecificaSeleccionada != null) {
            StringBuilder sb = new StringBuilder();
            if (actividadEspecificaSeleccionada.getHerramientasTipo() != null) {
                int i = 1;
                for (HerramientaTipo herramientaTipo : actividadEspecificaSeleccionada.getHerramientasTipo()) {
                    sb.append(":ht").append(i++).append(", ");
                }
                if (sb.length() > 0) {
                    sb.deleteCharAt(sb.length() - 1);
                    sb.deleteCharAt(sb.length() - 1);
                }

            }
            Query query = null;
            if (sb.length() > 0) {
                query = em.createQuery("select ht from HerramientaTipo ht where ht not in (" + sb.toString() + ")");
                int i = 1;
                for (HerramientaTipo herramientaTipo : actividadEspecificaSeleccionada.getHerramientasTipo()) {
                    query.setParameter("ht" + i, herramientaTipo);
                    i++;
                }
            } else {
                query = em.createQuery("select ht from HerramientaTipo ht");
            }
            return query.getResultList();
        }
        return new ArrayList<Herramienta>();
    }

    public Collection<MaquinaTipo> devolverMaquinasTipo() {
        return em.createQuery("select mt from MaquinaTipo mt").getResultList();
    }
}
