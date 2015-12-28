/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.*;

/**
 *
 * @author Zaba
 */
@Entity
public class OrdenDeProduccionDetalle implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="muebleModelo")
    private MuebleModelo muebleModelo;
    private BigDecimal cantidad = BigDecimal.ZERO;
    @OneToMany(mappedBy = "ordenDeProduccionDetallePadre", fetch=FetchType.EAGER)
    private Collection<PedidoDetalle> pedidoDetalles;

    public Integer getId() {
        return id;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
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

    public void pasarDePmpDetalleAOrdenDeProduccionDetalle(PedidoDetalle pedidoDetalle) {
        if (!this.muebleModelo.equals(pedidoDetalle.getMuebleModelo())) {
            throw new AssertionError("Se ingresó un PedidoDetalle de tipo de mueble distinto al del detalle de la orden de producción.");
        }
        this.cantidad = this.cantidad.add(pedidoDetalle.getCantidad());
        this.pedidoDetalles.add(pedidoDetalle);
        pedidoDetalle.setOrdenDeProduccionDetallePadre(this);
        pedidoDetalle.setEstadoProduccion();
        pedidoDetalle.setPmpDetallePadre(null);
    }

    /**
     * agrega los PedidoDetalle a los detalles pedido de esta clase. A cada uno les asigna null a su pmpDetallePadre, y les asigna esta instancia como ordenDeProduccionDetallePadre
     * @param pedidoDetalles
     */
    public void pasarDePmpDetalleAOrdenDeProduccionDetalle(Collection<PedidoDetalle> pedidoDetalles) {
        for (PedidoDetalle pedidoDetalle : pedidoDetalles) {
            if (!this.muebleModelo.equals(pedidoDetalle.getMuebleModelo())) {
                throw new AssertionError("Se ingresó un PedidoDetalle de modelo de mueble distinto al del detalle de la orden de producción.");
            }
        }
        for (PedidoDetalle pedidoDetalle : pedidoDetalles) {
            this.cantidad = this.cantidad.add(pedidoDetalle.getCantidad());
            if (this.pedidoDetalles == null) {
                this.pedidoDetalles = new ArrayList<PedidoDetalle>();
            }
            this.pedidoDetalles.add(pedidoDetalle);
            pedidoDetalle.setEstadoProduccion();
            pedidoDetalle.setOrdenDeProduccionDetallePadre(this);
            pedidoDetalle.setPmpDetallePadre(null);
        }
    }

    /**
     * agrega los PedidoDetalle a los detalles pedido de esta clase. No realiza cambios en los pedidoDetalles. Esto puede ser util para crear una OrdenDeProduccion en memoria
     * para cálculos de requerimientos de materiales. El objeto luego puede morir y todo queda como estaba (salvo este OrdenDeProduccionDetalle, al cual se le agregan los pedidos detalle).
     * @param pedidoDetalles
     */
    public void copiarDePmpDetalleAOrdenDeProduccionDetalle(Collection<PedidoDetalle> pedidoDetalles) {
        for (PedidoDetalle pedidoDetalle : pedidoDetalles) {
            if (!this.muebleModelo.equals(pedidoDetalle.getMuebleModelo())) {
                throw new AssertionError("Se ingresó un PedidoDetalle de modelo de mueble distinto al del detalle de la orden de producción.");
            }
        }
        for (PedidoDetalle pedidoDetalle : pedidoDetalles) {
            this.cantidad = this.cantidad.add(pedidoDetalle.getCantidad());
            if (this.pedidoDetalles == null) {
                this.pedidoDetalles = new ArrayList<PedidoDetalle>();
            }
            this.pedidoDetalles.add(pedidoDetalle);
        }
    }
}
