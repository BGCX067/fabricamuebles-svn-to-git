/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Zaba
 */
@Embeddable
public class MaderaTipoPrecioProveedorPk implements Serializable {

    @ManyToOne(cascade=CascadeType.ALL, optional=false)
    @JoinColumn(name="idProveedor")
    private Proveedor proveedor;
    
    @ManyToOne(cascade=CascadeType.ALL, optional=false)
    @JoinColumn(name="idMaderaTipo")
    private MaderaTipo maderaTipo;

    public MaderaTipo getMaderaTipo() {
        return maderaTipo;
    }

    public void setMaderaTipo(MaderaTipo maderaTipo) {
        this.maderaTipo = maderaTipo;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MaderaTipoPrecioProveedorPk other = (MaderaTipoPrecioProveedorPk) obj;
        if (this.proveedor != other.proveedor && (this.proveedor == null || !this.proveedor.equals(other.proveedor))) {
            return false;
        }
        if (this.maderaTipo != other.maderaTipo && (this.maderaTipo == null || !this.maderaTipo.equals(other.maderaTipo))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + (this.proveedor != null ? this.proveedor.hashCode() : 0);
        hash = 67 * hash + (this.maderaTipo != null ? this.maderaTipo.hashCode() : 0);
        return hash;
    }
}
