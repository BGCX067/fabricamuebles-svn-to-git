/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.math.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Cristian
 */
@Entity
@DiscriminatorValue(value = "FacturaDetalleCompra")
public class FacturaDetalleCompra extends FacturaDetalle implements Serializable {

    @ManyToOne
    @JoinColumn(name = "idArticuloPresentacion")
    @NotNull
    private ArticuloPresentacion presentacion;

    public ArticuloPresentacion getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(ArticuloPresentacion presentacion) {
        this.presentacion = presentacion;
    }

    public BigDecimal calcularSubTotal() {
        return this.precio.multiply(this.cantidad).setScale(2, RoundingMode.HALF_EVEN);
    }

    public String getTipo() {
        return "FacturaDetalleCompra";
    }
}
