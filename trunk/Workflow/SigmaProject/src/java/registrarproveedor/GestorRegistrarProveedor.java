/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package registrarproveedor;

import entidades.Direccion;
import entidades.Proveedor;
import entidades.Telefono;
import java.util.Collection;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Cristian
 */
@Stateful
//@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class GestorRegistrarProveedor {

    @PersistenceContext//(type=PersistenceContextType.EXTENDED)
    EntityManager em;
    private Boolean modo = null;
    private Proveedor nuevo;

    public GestorRegistrarProveedor() {
        nuevo = new Proveedor();
    }

    public void descartarCambiosProveedor() {
        nuevo = null;
        //((EntityManagerImpl)em.getDelegate()).getSession().refresh(this.proveedorElegido);
    }

    public Proveedor getNuevoProveedor() {

        return nuevo;
    }

    public void registrarProveedor() {
        Collection<Direccion> direcciones = nuevo.getDirecciones();
        Collection<Telefono> telefonos = nuevo.getTelefonos();
        nuevo.setDirecciones(null);
        nuevo.setTelefonos(null);
        em.persist(nuevo);
        nuevo.setDirecciones(direcciones);
        nuevo.setTelefonos(telefonos);
        em.merge(nuevo);
    }

    public void reiniciar() {
        this.modo = null;
        this.nuevo = null;
        this.nuevo = new Proveedor();
    }

    public void setNuevoProveedor(Proveedor p) {
        this.nuevo = p;
    }
}
