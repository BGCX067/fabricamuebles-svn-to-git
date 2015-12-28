/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Zaba
 */
@Entity
public class PresupuestoDetalle implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private BigDecimal cantidad;
    @NotNull
    private BigDecimal precioUnitario;

    @ManyToOne
    @JoinColumn(name="idMuebleModelo")
    @NotNull
    private MuebleModelo muebleModelo;

    public MuebleModelo getMuebleModelo() {
        return muebleModelo;
    }

    public void setMuebleModelo(MuebleModelo muebleModelo) {
        this.muebleModelo = muebleModelo;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal calcularSubTotal() {
        return this.precioUnitario.multiply(this.cantidad).setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PresupuestoDetalle other = (PresupuestoDetalle) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
