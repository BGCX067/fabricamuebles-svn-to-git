/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Zaba
 */
@Entity
public class PedidoDetalle implements Serializable, Asignable {

    @ManyToOne
    @JoinColumn(name = "idPedido")
    private Pedido pedido;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private BigDecimal cantidad;
    @NotNull
    private BigDecimal precioUnitario;
    @ManyToOne
    private PMPDetalle pmpDetallePadre;
    @ManyToOne
    private OrdenDeProduccionDetalle ordenDeProduccionDetallePadre;
    @ManyToOne
    @JoinColumn(name = "idMuebleModelo")
    @NotNull
    private MuebleModelo muebleModelo;
    public final static String ESTADO_REGISTRADO = "REGISTRADO";
    public final static String ESTADO_EN_PRODUCCION = "EN PRODUCCIÓN";
    public final static String ESTADO_FINALIZADO = "FINALIZADO";
    public final static String ESTADO_ENTREGADO_NO_FACTURADO = "ENTREGADO NO FACTURADO";
    public final static String ESTADO_ENTREGADO_FACTURADO = "ENTREGADO FACTURADO";
    public final static String ESTADO_FACTURADO_NO_ENTREGADO = "FACTURADO NO ENTREGADO";
    private String estado = ESTADO_REGISTRADO;
    private BigDecimal cantidadEntregada;
    private BigDecimal candidadFacturada;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaEntregaEstimada;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaEntregaReal;

    public void setEstadoProduccion() {
        this.estado = ESTADO_EN_PRODUCCION;
    }

    public void setEstadoFinalizado() {
        this.estado = ESTADO_FINALIZADO;
    }

    public void setEstadoEntregadoNoFacturado() {
        this.estado = ESTADO_ENTREGADO_NO_FACTURADO;
    }

    public void setEstadoEntregadoFacturado() {
        this.estado = ESTADO_ENTREGADO_FACTURADO;
    }

    public void setEstadoFacturadoNoEntregado() {
        this.estado = ESTADO_FACTURADO_NO_ENTREGADO;
    }

    public String getEstado() {
        return this.estado;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public OrdenDeProduccionDetalle getOrdenDeProduccionDetallePadre() {
        return ordenDeProduccionDetallePadre;
    }

    public void setOrdenDeProduccionDetallePadre(OrdenDeProduccionDetalle ordenDeProduccionDetallePadre) {
        this.ordenDeProduccionDetallePadre = ordenDeProduccionDetallePadre;
    }

    public MuebleModelo getMuebleModelo() {
        return muebleModelo;
    }

    public void setMuebleModelo(MuebleModelo muebleModelo) {
        this.muebleModelo = muebleModelo;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getCantidadEntregada() {
        return cantidadEntregada;
    }

    public void setCantidadEntregada(BigDecimal cantidadEntregada) {
        this.cantidadEntregada = cantidadEntregada;
    }

    public BigDecimal getCantidadFacturada() {
        return this.candidadFacturada;
    }

    public void setCantidadFacturada(BigDecimal cantidadFacturada) {
        this.candidadFacturada = cantidadFacturada;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal calcularSubTotal() {
        return this.precioUnitario.multiply(this.cantidad).setScale(2, RoundingMode.HALF_EVEN);
    }

    public PMPDetalle getPmpDetallePadre() {
        return pmpDetallePadre;
    }

    public void setPmpDetallePadre(PMPDetalle pmpDetallePadre) {
        this.pmpDetallePadre = pmpDetallePadre;
    }

    public BigDecimal getCandidadFacturada() {
        return candidadFacturada;
    }

    public void setCandidadFacturada(BigDecimal candidadFacturada) {
        this.candidadFacturada = candidadFacturada;
    }

    public Date getFechaEntregaEstimada() {
        return fechaEntregaEstimada;
    }

    public void setFechaEntregaEstimada(Date fechaEntregaEstimada) {
        this.fechaEntregaEstimada = fechaEntregaEstimada;
    }

    public Date getFechaEntregaReal() {
        return fechaEntregaReal;
    }

    public void setFechaEntregaReal(Date fechaEntregaReal) {
        this.fechaEntregaReal = fechaEntregaReal;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PedidoDetalle other = (PedidoDetalle) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
