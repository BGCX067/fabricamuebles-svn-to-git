/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author Zaba
 */
@Entity
@DiscriminatorValue(value="PPExterno")
public class RequerimientoMaterialOrdenDeProduccionExterna extends RequerimientoMaterialOrdenDeProduccion {
    private BigDecimal cantidadEntregada = new BigDecimal(0);

    public BigDecimal getCantidadEntregada() {
        return cantidadEntregada;
    }

    public void setCantidadEntregada(BigDecimal cantidadEntregada) {
        this.cantidadEntregada = cantidadEntregada;
    }

    @Override
    public String getTipo() {
        return "Externo";
    }
}
