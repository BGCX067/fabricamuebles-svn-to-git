/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Zaba
 */
@Entity
public class MaderaTipoPrecioProveedor implements Serializable {

    @EmbeddedId
    private MaderaTipoPrecioProveedorPk id;
    @NotNull
    private BigDecimal precioPieCubico;

    public MaderaTipoPrecioProveedorPk getId() {
        return id;
    }

    public void setId(MaderaTipoPrecioProveedorPk id) {
        this.id = id;
    }

    public BigDecimal getPrecioPieCubico() {
        return precioPieCubico;
    }

    public void setPrecioPieCubico(BigDecimal precioPieCubico) {
        this.precioPieCubico = precioPieCubico;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MaderaTipoPrecioProveedor other = (MaderaTipoPrecioProveedor) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
