/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package componentes.articulo;

import entidades.*;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.*;
import org.hibernate.ejb.EntityManagerImpl;

/**
 *
 * @author Zaba
 */
@Stateless
public class GestorArticulo {
    @PersistenceContext
    private EntityManager em;

    public Collection<Articulo> devolverArticulos() {
        return em.createQuery("select a from Articulo a").getResultList();
    }

    public void guardarCambiosArticulo(Articulo articulo) {
        em.merge(articulo);
    }

    public void recuperarArticulo(Articulo articulo) {
        ((EntityManagerImpl)em.getDelegate()).getSession().refresh(articulo);
    }

    public Collection<MaderaTipo> devolverMaderaTipos() {
        return em.createQuery("select mt from MaderaTipo mt").getResultList();
    }

    public Boolean tieneProveedoresAsignados(Articulo articulo) {
        return em.createQuery("select app from ArticuloPrecioProveedor app where app.articulo = :articulo").setParameter("articulo", articulo).getResultList().size() > 0;
    }

    public Collection<ArticuloPresentacionPrecioProveedor> devolverProveedoresPrecios(Articulo articulo) {
        return em.createQuery("select app from ArticuloPrecioProveedor app where app.articulo = :articulo").setParameter("articulo", articulo).getResultList();
    }

    public void registrarArticulo(Articulo articulo) {
        em.persist(articulo);
    }

    public void recuperarMaderaTipo(MaderaTipo maderaTipoSeleccionada) {
        ((EntityManagerImpl)em.getDelegate()).getSession().refresh(maderaTipoSeleccionada);
    }

    public void guardarCambiosMaderaTipo(MaderaTipo maderaTipoSeleccionada) {
        em.merge(maderaTipoSeleccionada);
    }

    public void registrarMaderaTipo(MaderaTipo maderaTipoSeleccionada) {
        em.persist(maderaTipoSeleccionada);
    }
}
