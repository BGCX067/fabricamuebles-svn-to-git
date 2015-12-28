/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Cristian
 */
@Entity
@DiscriminatorValue(value = "Compra")
public class RemitoDetalleCompra extends RemitoDetalle implements Serializable {

    private String descripcion;
    @ManyToOne
    @JoinColumn(name = "idArticulo")
    @NotNull
    private ArticuloPresentacion presentacion;
    private BigDecimal cantidadFacturada = BigDecimal.ZERO;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ArticuloPresentacion getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(ArticuloPresentacion presentacion) {
        this.presentacion = presentacion;
    }

    public BigDecimal getCantidadFacturada() {
        return cantidadFacturada;
    }

    public void setCantidadFacturada(BigDecimal cantidadFacturada) {
        this.cantidadFacturada = cantidadFacturada;
    }
    
    public BigDecimal getCantidadPendientePorFacturar() {
        return getCantidad().subtract(this.cantidadFacturada);
    }

    public String getTipo() {
        return "Compra";
    }
}
