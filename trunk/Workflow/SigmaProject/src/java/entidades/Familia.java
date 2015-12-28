/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 *
 * @author Zaba
 */
@Entity
public class Familia implements Serializable, Requerible {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private String unidadProduccion;
    private BigDecimal stockActual;
    private BigDecimal stockReservado;
    private BigDecimal cantidadEstandarProduccion;
    
    public void incrementarStockActual(BigDecimal cantidad) {
        this.stockActual = this.stockActual.add(cantidad);
    }
    
    public void disminuirStockActual(BigDecimal cantidad) {
        this.stockActual = this.stockActual.subtract(cantidad);
    }
    
    public void reservar(BigDecimal cantidad) {
        if (cantidad.compareTo(getStockDisponible()) > 0) {
            throw new AssertionError("Se está tratando de reservar una cantidad mayor a la dispojible.");
        } else {
            this.stockReservado = this.stockReservado.add(cantidad);
        }
    }
    
    public void consumirReserva(BigDecimal cantidad) {
        this.stockActual = this.stockActual.subtract(cantidad);
        this.stockReservado = this.stockReservado.subtract(cantidad);
    }
    
    public void anularReserva(BigDecimal cantidad) {
        this.stockReservado = this.stockReservado.subtract(cantidad);
    }
    
    public BigDecimal getStockDisponible() {
        return this.stockActual.subtract(this.stockReservado);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getCantidadEstandarProduccion() {
        return cantidadEstandarProduccion;
    }

    public void setCantidadEstandarProduccion(BigDecimal cantidadEstandarProduccion) {
        this.cantidadEstandarProduccion = cantidadEstandarProduccion;
    }

    public BigDecimal getStockActual() {
        return stockActual;
    }

    public void setStockActual(BigDecimal stockActual) {
        this.stockActual = stockActual;
    }

    public BigDecimal getStockReservado() {
        return stockReservado;
    }

    public void setStockReservado(BigDecimal stockReservado) {
        this.stockReservado = stockReservado;
    }

    public String getUnidadProduccion() {
        return unidadProduccion;
    }

    public void setUnidadProduccion(String unidadProduccion) {
        this.unidadProduccion = unidadProduccion;
    }

    public Long getId() {
        return id;
    }

    public String getTipo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
