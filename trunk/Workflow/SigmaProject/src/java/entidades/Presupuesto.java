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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.NaturalId;

/**
 *
 * @author Zaba
 */
@Entity
public class Presupuesto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @NaturalId
    private Integer numero;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "idPresupuesto")
    private Collection<PresupuestoDetalle> detalles;
    public final static String ESTADO_NO_VIGENTE_USADO = "NO VIGENTE - USADO";
    public final static String ESTADO_NO_VIGENTE_NO_USADO = "NO VIGENTE - NO USADO";
    public final static String ESTADO_VIGENTE_USADO = "VIGENTE - USADO";
    public final static String ESTADO_VIGENTE_NO_USADO = "VIGENTE - NO USADO";
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaCreacion;
    // TODO ver aca abjo, fechaEntregaEstimada en Presupuesto no tendría que estar, tendría que estar solo en el pedido...
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaEntregaEstimada;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaVigencia;
    @NotNull
    private BigDecimal importeTotal;
    @ManyToOne
    @JoinColumn(name = "idEntidad")
    @NotNull
    private Entidad entidad;
    private String estado;
    private Boolean usado = false;

    @PrePersist
    @PreUpdate
    public void actualizarEstado() {
        if (this.fechaVigencia != null && this.fechaVigencia.after(new Date())) {
            if (usado) {
                this.estado = ESTADO_VIGENTE_USADO;
            } else {
                this.estado = ESTADO_VIGENTE_NO_USADO;
            }
        } else if (this.fechaVigencia != null && this.fechaVigencia.before(new Date())) {
            if (usado) {
                this.estado = ESTADO_NO_VIGENTE_USADO;
            } else {
                this.estado = ESTADO_NO_VIGENTE_NO_USADO;
            }
        }
    }

    public Presupuesto() {
    }

    public void calcularTotal() {
        BigDecimal total = new BigDecimal(0).setScale(2);
        for (PresupuestoDetalle detalle : this.detalles) {
            total = total.add(detalle.calcularSubTotal());
        }
        this.importeTotal = total;
    }

    public Integer getId() {
        return id;
    }

    public String getEstado() {
        if (this.fechaVigencia != null && this.fechaVigencia.after(new Date())) {
            if (usado) {
                return ESTADO_VIGENTE_USADO;
            } else {
                return ESTADO_VIGENTE_NO_USADO;
            }
        } else if (this.fechaVigencia != null && this.fechaVigencia.before(new Date())) {
            if (usado) {
                return ESTADO_NO_VIGENTE_USADO;
            } else {
                return ESTADO_NO_VIGENTE_NO_USADO;
            }
        }
        return "";
    }

    public Boolean getUsado() {
        return usado;
    }

    public void setUsado(Boolean usado) {
        this.usado = usado;
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

    public Collection<PresupuestoDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(Collection<PresupuestoDetalle> detalles) {
        this.detalles = detalles;
    }

    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

    public Date getFechaEntregaEstimada() {
        return fechaEntregaEstimada;
    }

    public void setFechaEntregaEstimada(Date fechaEntregaEstimada) {
        this.fechaEntregaEstimada = fechaEntregaEstimada;
    }

    public Date getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(Date fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
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
        final Presupuesto other = (Presupuesto) obj;
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
