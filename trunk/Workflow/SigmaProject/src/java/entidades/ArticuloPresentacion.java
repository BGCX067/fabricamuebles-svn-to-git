/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Zaba
 */

@Entity
public class ArticuloPresentacion implements java.io.Serializable {
    @Id
    private Integer id;
    @ManyToOne
    private Articulo articulo;
    private BigDecimal cantidad; // Por ej. 500 para 500 ml

    public String getDescripcion() {
        return cantidad + " " + articulo.getFamilia().getUnidadProduccion();
    }

    public Integer getId() {
        return id;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }
}
