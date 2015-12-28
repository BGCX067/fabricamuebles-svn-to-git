/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Zaba
 */
@IdClass(ArticuloPresentacionPrecioProveedorPk.class)
@Entity
public class ArticuloPresentacionPrecioProveedor implements Serializable {
    @Id
    private ArticuloPresentacion presentacion;
    @Id
    private Proveedor proveedor;

    @NotNull
    private BigDecimal precio;

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public ArticuloPresentacion getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(ArticuloPresentacion presentacion) {
        this.presentacion = presentacion;
    }

    public Proveedor getProveedor() {
        return this.proveedor;
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
        final ArticuloPresentacionPrecioProveedor other = (ArticuloPresentacionPrecioProveedor) obj;
        if (this.presentacion != other.presentacion && (this.presentacion == null || !this.presentacion.equals(other.presentacion))) {
            return false;
        }
        if (this.proveedor != other.proveedor && (this.proveedor == null || !this.proveedor.equals(other.proveedor))) {
            return false;
        }
        if (this.precio != other.precio && (this.precio == null || !this.precio.equals(other.precio))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.presentacion != null ? this.presentacion.hashCode() : 0);
        hash = 79 * hash + (this.proveedor != null ? this.proveedor.hashCode() : 0);
        hash = 79 * hash + (this.precio != null ? this.precio.hashCode() : 0);
        return hash;
    }
}
