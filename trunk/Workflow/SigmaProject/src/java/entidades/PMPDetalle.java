/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import javax.persistence.*;
import org.hibernate.annotations.NaturalId;

/**
 *
 * @author Zaba
 */
@Entity
public class PMPDetalle implements Serializable, Asignable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @NaturalId
    @JoinColumn(name="idMuebleModelo", nullable=false)
    @OneToOne
    private MuebleModelo muebleModelo;
    @OneToMany(mappedBy = "pmpDetallePadre")
    private Collection<PedidoDetalle> pedidoDetalles;
    private BigDecimal cantidadPorAsignar = BigDecimal.ZERO;

    public Integer getId() {
        return id;
    }

    public BigDecimal getCantidadPorAsignar() {
        return cantidadPorAsignar;
    }

    public void setCantidadPorAsignar(BigDecimal cantidadPorAsignar) {
        this.cantidadPorAsignar = cantidadPorAsignar;
    }

    public MuebleModelo getMuebleModelo() {
        return muebleModelo;
    }

    public void setMuebleModelo(MuebleModelo muebleModelo) {
        this.muebleModelo = muebleModelo;
    }

    public Collection<PedidoDetalle> getPedidoDetalles() {
        return pedidoDetalles;
    }

    public void setPedidoDetalles(Collection<PedidoDetalle> pedidoDetalles) {
        this.pedidoDetalles = pedidoDetalles;
    }

    public void quitarPedidoDetalle(PedidoDetalle pedidoDetalle) {
        if (this.pedidoDetalles != null) {
            if (!this.pedidoDetalles.remove(pedidoDetalle)) {
                throw new AssertionError("Se llamó a quitarPedidoDetalle, pero el PedidoDetalle a quitar no existe en la colección");
            }
        }
    }
}
