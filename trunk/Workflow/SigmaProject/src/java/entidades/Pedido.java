/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.NaturalId;

/**
 *
 * @author Zaba
 */
@Entity
public class Pedido implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NaturalId
    private Integer numero;

    @OneToMany(mappedBy = "pedido", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    private Collection<PedidoDetalle> detalles;
    public final static String ESTADO_REGISTRADO = "REGISTRADO";
    public final static String ESTADO_EN_PRODUCCION = "EN PRODUCCIÓN";
    public final static String ESTADO_FINALIZADO = "FINALIZADO";
    public final static String ESTADO_CANCELADO = "CANCELADO";
    public final static String ESTADO_EN_PRODUCCION_CANCELADO_POR_CLIENTE = "EN PRODUCCIÓN - CANCELADO POR EL CLIENTE";
    public final static String ESTADO_EN_PRODUCCION_ENTREGA_PARCIAL = "EN PRODUCCIÓN - ENTREGA PARCIAL";
    public final static String ESTADO_ENTREGADO = "ENTREGADO";
    public final static String ESTADO_ENTREGADO_PARCIAL = "ENTREGADO PARCIALMENTE";
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaCreacion;
    @NotNull
    private BigDecimal importeTotal;
    @ManyToOne
    @JoinColumn(name="idEntidad")
    @NotNull
    private Entidad entidad;
    private String estado = ESTADO_REGISTRADO;
    private Integer prioridad;

    public Pedido() {
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    public void calcularTotal() {
        BigDecimal total = new BigDecimal(0).setScale(2);
        for (PedidoDetalle detalle : this.detalles) {
            total = total.add(detalle.calcularSubTotal());
        }
        this.importeTotal = total;
    }

    public Integer getId() {
        return id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }

    public Collection<PedidoDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(Collection<PedidoDetalle> detalles) {
        this.detalles = detalles;
    }

    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pedido other = (Pedido) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
