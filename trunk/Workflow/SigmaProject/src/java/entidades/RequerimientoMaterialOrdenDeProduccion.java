/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Zaba
 */
@Entity
@DiscriminatorValue(value="PPInterno")
public class RequerimientoMaterialOrdenDeProduccion extends RequerimientoMaterial implements Serializable {

    private BigDecimal cantidadPedida = new BigDecimal(0);
    private BigDecimal cantidadReservada = new BigDecimal(0);
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaMaximaParaHacerElPedido;

    public BigDecimal getCantidadReservada() {
        return cantidadReservada;
    }

    public void setCantidadReservada(BigDecimal cantidadReservada) {
        this.cantidadReservada = cantidadReservada;
    }

    public BigDecimal getCantidadPedida() {
        return cantidadPedida;
    }

    public void setCantidadPedida(BigDecimal cantidadPedida) {
        this.cantidadPedida = cantidadPedida;
    }

    public Date getFechaMaximaParaHacerElPedido() {
        return fechaMaximaParaHacerElPedido;
    }

    public void setFechaMaximaParaHacerElPedido(Date fechaMaximaParaHacerElPedido) {
        this.fechaMaximaParaHacerElPedido = fechaMaximaParaHacerElPedido;
    }

    public void agregarCantidadReservada(BigDecimal cantidadAReservar) {
        if (cantidadReservada == null) {
            cantidadReservada = new BigDecimal(0);
        }
        cantidadReservada = cantidadReservada.add(cantidadAReservar);
    }

    public void quitarCantidadReservada(BigDecimal cantidadAQuitar) {
        if (cantidadReservada == null) {
            cantidadReservada = new BigDecimal(0);
        }
        if (cantidadReservada.subtract(cantidadAQuitar).signum() == -1) {
            throw new AssertionError("Se trató de quitar una cantidad reservada y el resultado dio menor a cero.");
        }
        cantidadReservada = cantidadReservada.subtract(cantidadAQuitar);
    }

    public BigDecimal cantidadAPedir() {
        return getCantidadRequerida().subtract(this.cantidadReservada).subtract(this.cantidadPedida);
    }

    public String getTipo() {
        return "Interno";
    }
}
