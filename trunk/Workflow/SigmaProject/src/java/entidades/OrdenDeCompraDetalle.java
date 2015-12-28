/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades;

import java.io.Serializable;
import java.math.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Zaba
 */
@Entity
public class OrdenDeCompraDetalle implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name="idArticulo")
    @NotNull
    private ArticuloPresentacion presentacion;

    @NotNull
    private BigDecimal cantidad;
    private BigDecimal cantidadRecibida;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaRecepcion;
    @NotNull
    private BigDecimal precio;

    public ArticuloPresentacion getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(ArticuloPresentacion presentacion) {
        this.presentacion = presentacion;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getCantidadRecibida() {
        return cantidadRecibida;
    }

    public void setCantidadRecibida(BigDecimal cantidadRecibida) {
        this.cantidadRecibida = cantidadRecibida;
    }

    public Date getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal calcularSubTotal() {
        return this.precio.multiply(this.cantidad).setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OrdenDeCompraDetalle other = (OrdenDeCompraDetalle) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.presentacion != other.presentacion && (this.presentacion == null || !this.presentacion.equals(other.presentacion))) {
            return false;
        }
        if (this.cantidad != other.cantidad && (this.cantidad == null || !this.cantidad.equals(other.cantidad))) {
            return false;
        }
        if (this.cantidadRecibida != other.cantidadRecibida && (this.cantidadRecibida == null || !this.cantidadRecibida.equals(other.cantidadRecibida))) {
            return false;
        }
        if (this.fechaRecepcion != other.fechaRecepcion && (this.fechaRecepcion == null || !this.fechaRecepcion.equals(other.fechaRecepcion))) {
            return false;
        }
        if (this.precio != other.precio && (this.precio == null || !this.precio.equals(other.precio))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 23 * hash + (this.presentacion != null ? this.presentacion.hashCode() : 0);
        hash = 23 * hash + (this.cantidad != null ? this.cantidad.hashCode() : 0);
        hash = 23 * hash + (this.cantidadRecibida != null ? this.cantidadRecibida.hashCode() : 0);
        hash = 23 * hash + (this.fechaRecepcion != null ? this.fechaRecepcion.hashCode() : 0);
        hash = 23 * hash + (this.precio != null ? this.precio.hashCode() : 0);
        return hash;
    }

    /*

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OrdenDeCompraDetalle other = (OrdenDeCompraDetalle) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
     *
     *
     */
}
