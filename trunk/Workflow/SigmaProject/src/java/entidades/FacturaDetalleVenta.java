/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.math.*;
import javax.persistence.*;

/**
 *
 * @author Cristian
 */
@Entity
@DiscriminatorValue(value = "FacturaDetalleVenta")
public class FacturaDetalleVenta extends FacturaDetalle implements Serializable {

    @ManyToOne
    private MuebleModelo muebleModelo;

    public String getTipo() {
        return "FacturaDetalleVenta";
    }

    public BigDecimal calcularSubTotal() {
        return this.precio.multiply(this.cantidad).setScale(2, RoundingMode.HALF_EVEN);
    }

     public void setMuebleModelo(MuebleModelo muebleModelo) {
        this.muebleModelo = muebleModelo;
    }
     public MuebleModelo getMuebleModelo(){
     return this.muebleModelo;
     }
}
