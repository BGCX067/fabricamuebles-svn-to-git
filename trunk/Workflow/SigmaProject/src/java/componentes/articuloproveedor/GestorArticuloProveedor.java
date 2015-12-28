/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes.articuloproveedor;

import entidades.Articulo;
import entidades.ArticuloPresentacionPrecioProveedor;
import entidades.Insumo;
import entidades.Madera;
import entidades.MaderaTipoPrecioProveedor;
import entidades.Proveedor;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Zaba
 */
@Stateless
public class GestorArticuloProveedor {

    @PersistenceContext
    private EntityManager em;

    public void quitarArticuloProveedor(ArticuloPresentacionPrecioProveedor articuloPrecioProveedor) {
        em.remove(em.merge(articuloPrecioProveedor));
    }

    public void actualizarArticuloProveedorSeleccionado(ArticuloPresentacionPrecioProveedor articuloPrecioProveedorSeleccionado) {
        em.merge(articuloPrecioProveedorSeleccionado);
    }

    public Collection<ArticuloPresentacionPrecioProveedor> devolverArticulosProveedor(Proveedor proveedorSeleccionado) {
        return em.createQuery("select app from ArticuloPrecioProveedor app where app.proveedor = :proveedor").setParameter("proveedor", proveedorSeleccionado).getResultList();
    }

    public Collection<Insumo> devolverInsumosNoProveidos(Proveedor proveedor) {
        return em.createQuery("select i from Insumo i where i not in "
                + "(select app.articulo from ArticuloPrecioProveedor app where app.proveedor = :proveedor)").setParameter("proveedor", proveedor).getResultList();
    }

    public Collection<Madera> devolverMaderasNoProveidas(Proveedor proveedor) {
        return em.createQuery("select m from Madera m where m not in "
                + "(select app.articulo from ArticuloPrecioProveedor app where app.proveedor = :proveedor)").setParameter("proveedor", proveedor).getResultList();
    }

    public void crearArticuloProveedorSeleccionado(ArticuloPresentacionPrecioProveedor articuloPrecioProveedorSeleccionado) {
        articuloPrecioProveedorSeleccionado.setPresentacion(em.merge(articuloPrecioProveedorSeleccionado.getPresentacion()));
        articuloPrecioProveedorSeleccionado.setProveedor(em.merge(articuloPrecioProveedorSeleccionado.getProveedor()));
        em.persist(articuloPrecioProveedorSeleccionado);
    }

    public void actualizarPrecioPieCubicoProveedorSeleccionado(MaderaTipoPrecioProveedor maderaTipoPrecioProveedorSeleccionado) {
        em.merge(maderaTipoPrecioProveedorSeleccionado);
        List<ArticuloPresentacionPrecioProveedor> preciosMaderaTipoActualizado = em.createQuery("select app from ArticuloPrecioProveedor app "
                + "where app.proveedor = :proveedor and "
                + "app.articulo.class = 'Madera'").setParameter("proveedor", maderaTipoPrecioProveedorSeleccionado.getId().getProveedor()).getResultList();
        for (ArticuloPresentacionPrecioProveedor app : preciosMaderaTipoActualizado) {
            em.createQuery("update ArticuloPrecioProveedor app set app.precio = :precio "
                    + "where app.proveedor = :proveedor and "
                    + " app.articulo = :articulo").setParameter("precio", ((Madera) app.getPresentacion().getArticulo()).calcularPiesCubicos().multiply(maderaTipoPrecioProveedorSeleccionado.getPrecioPieCubico())).setParameter("proveedor", app.getProveedor()).setParameter("articulo", app.getPresentacion().getArticulo()).executeUpdate();
        }
    }

    /**
     * Use así porque me tira un LazyInitializationException desde la vista
     * @param p El proveedor a consultar
     * @return La lista de precios de tipos de madera para este proveedor
     */
    public Set<MaderaTipoPrecioProveedor> devolverPrecioTiposMaderas(Proveedor p) {
        Proveedor proveedor = em.merge(p);
        Set<MaderaTipoPrecioProveedor> precioTiposMadera = proveedor.getPrecioTiposMadera();
        precioTiposMadera.size();
        return precioTiposMadera;
    }

    /**
     * Use así porque me tira un LazyInitializationException desde la vista
     * @param p El proveedor a consultar
     * @return La lista de precios de tipos de madera para este proveedor
     */
    public Set<ArticuloPresentacionPrecioProveedor> devolverPrecioArticulos(Proveedor p) {
        Proveedor proveedor = em.merge(p);
        Set<ArticuloPresentacionPrecioProveedor> precioArticulos = proveedor.getPrecioArticulos();
        precioArticulos.size();
        return precioArticulos;
    }

    public void crearMaderaTipoProveedor(MaderaTipoPrecioProveedor maderaTipoPrecioProveedor) {
        maderaTipoPrecioProveedor.getId().setMaderaTipo(em.merge(maderaTipoPrecioProveedor.getId().getMaderaTipo()));
        maderaTipoPrecioProveedor.getId().setProveedor(em.merge(maderaTipoPrecioProveedor.getId().getProveedor()));
        em.persist(maderaTipoPrecioProveedor);
    }

    public Collection<Proveedor> devolverProveedores() {
        return em.createQuery("select p from Proveedor p").getResultList();
    }

    public Collection<Proveedor> devolverProveedoresArticulo(Articulo articulo) {
        return em.createQuery("select app.proveedor from ArticuloPrecioProveedor app where app.articulo = :articulo").setParameter("articulo", articulo).getResultList();
    }

    public void reiniciar() {
        
    }
}
