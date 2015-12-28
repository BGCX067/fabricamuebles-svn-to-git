/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package registrarordendecompra;

import entidades.*;
import java.util.*;
import javax.ejb.Stateful;
import javax.persistence.*;
import org.hibernate.ejb.EntityManagerImpl;

/**
 *
 * @author Zaba
 */
@Stateful
//@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class GestorRegistrarOrdenDeCompra {

    @PersistenceContext//(type=PersistenceContextType.EXTENDED)
    EntityManager em;
    private Proveedor proveedorElegido;
    private Collection<OrdenDeCompraDetalle> detallesAgregados;
    private OrdenDeCompra ordenDeCompra;

    public GestorRegistrarOrdenDeCompra() {
    }

    public Collection<Proveedor> devolverProveedores() {
        List<Proveedor> res = em.createQuery("Select p from Proveedor p").getResultList();
        return res;
    }

    public Collection<String> devolverTiposDocumento() {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("DNI");
        arrayList.add("CUIT");
        arrayList.add("OTRO");
        return arrayList;
    }

    public void setProveedorSeleccionado(Proveedor p) {
        this.proveedorElegido = p;
    }

    public Proveedor getProveedorSeleccionado() {
        return this.proveedorElegido;
    }

    public Collection<ArticuloPresentacionPrecioProveedor> devolverArticulosProveedor() {
        return em.createQuery("SELECT app from ArticuloPrecioProveedor app WHERE app.id.proveedor = :proveedor ").setParameter("proveedor", this.proveedorElegido).getResultList();
    }

    public void tomarDetallesOrdenDeCompra(Collection<OrdenDeCompraDetalle> detallesAgregados) {
        this.detallesAgregados = detallesAgregados;
        this.ordenDeCompra = new OrdenDeCompra();
        ordenDeCompra.setDetalles(detallesAgregados);
        ordenDeCompra.calcularTotal();
        ordenDeCompra.setProveedor(proveedorElegido);
        ordenDeCompra.setNumero(devolverNumeroDisponibleParaOrdenDeCompra());
        ordenDeCompra.setFechaCreacion(new Date());
    }

    private Integer devolverNumeroDisponibleParaOrdenDeCompra() {
        Integer numero = (Integer) em.createQuery("select max(oc.numero) from OrdenDeCompra oc").getSingleResult();
        if (numero == null) {
            numero = 1;
        } else {
            numero++;
        }
        return numero;
    }

    public void tomarConfirmacionDatosOrdenDeCompra() {
        this.ordenDeCompra.setFechaCreacion(new Date(System.currentTimeMillis()));
        guardarOrdenDeCompra();
    }

    private void guardarOrdenDeCompra() {
        for (OrdenDeCompraDetalle detalle : this.ordenDeCompra.getDetalles()) {
            detalle.setPresentacion(em.merge(detalle.getPresentacion()));
        }
        em.persist(this.ordenDeCompra);
    }

    public Collection<Presupuesto> devolverPresupuestosVigentes() {
        List<Presupuesto> res = em.createQuery("select p from Presupuesto p where p.fechaVigencia >= :fechaActual")
                .setParameter("fechaActual", new Date(System.currentTimeMillis()))
                .getResultList();
        return res;
    }

    public OrdenDeCompra getOrdenDeCompraNoConfirmada() {
        return this.ordenDeCompra;
    }

    public void reiniciar() {
        this.detallesAgregados = null;
        this.proveedorElegido = null;
        this.ordenDeCompra = null;
    }

    public void actualizarProveedor() {
        this.proveedorElegido = em.merge(this.proveedorElegido);
    }

    public void descartarCambiosProveedor() {
        ((EntityManagerImpl)em.getDelegate()).getSession().refresh(this.proveedorElegido);
    }

    public void confirmarOrdenDeCompraAPartirDePresupuesto() {
        this.ordenDeCompra.setFechaCreacion(new Date(System.currentTimeMillis()));
        this.ordenDeCompra.calcularTotal();
        guardarOrdenDeCompra();
    }

    public void tomarDetallesOrdenDeCompraAPartirDePresupuesto(Collection<OrdenDeCompraDetalle> detallesAgregados) {
        this.ordenDeCompra.setDetalles(detallesAgregados);
    }

    public Collection<ArticuloPresentacionPrecioProveedor> devolverArticulosProveedorPorNecesidadStock() {
        return em.createQuery("SELECT app from ArticuloPrecioProveedor app " +
                "WHERE app.id.proveedor = :proveedor " +
                "ORDER BY (app.articulo.stockActual - app.articulo.stockReposicion)")
                .setParameter("proveedor", this.proveedorElegido)
                .getResultList();
    }

    public void asociarOrdenDeCompraAOrdenDeProduccion(OrdenDeProduccion ordendeProduccion) {
        ComprobanteRelaciones comprobanteRelaciones = new ComprobanteRelaciones();
        comprobanteRelaciones.setOrdenDeCompra(em.merge(this.ordenDeCompra));
        comprobanteRelaciones.setOrdenDeProduccion(em.merge(ordendeProduccion));
        em.persist(comprobanteRelaciones);
    }
}
