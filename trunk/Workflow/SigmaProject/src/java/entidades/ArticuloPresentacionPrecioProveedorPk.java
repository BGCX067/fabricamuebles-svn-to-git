/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Zaba
 */

public class ArticuloPresentacionPrecioProveedorPk implements Serializable {
    @ManyToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, optional=false)
    @JoinColumn(name="idArticuloPresentacion")
    @NotNull
    private ArticuloPresentacion presentacion;

    @ManyToOne(fetch=FetchType.LAZY, cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, optional=false)
    @JoinColumn(name="idProveedor")
    @NotNull
    private Proveedor proveedor;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ArticuloPresentacionPrecioProveedorPk other = (ArticuloPresentacionPrecioProveedorPk) obj;
        if (this.presentacion != other.presentacion && (this.presentacion == null || !this.presentacion.equals(other.presentacion))) {
            return false;
        }
        if (this.proveedor != other.proveedor && (this.proveedor == null || !this.proveedor.equals(other.proveedor))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this.presentacion != null ? this.presentacion.hashCode() : 0);
        hash = 47 * hash + (this.proveedor != null ? this.proveedor.hashCode() : 0);
        return hash;
    }

    public ArticuloPresentacion getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(ArticuloPresentacion presentacion) {
        this.presentacion = presentacion;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
}
