/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
 * @author Cristian
 */
@Entity
public class Remito implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String numero;
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="idRemito")
    private Collection<RemitoDetalle> detalles;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaEmision;
    @ManyToOne
    @JoinColumn(name="idEntidad")
    @NotNull
    private Entidad entidad;
    private String tipo;
    private String estado = ESTADO_NO_FACTURADO;
    public final static String ESTADO_NO_FACTURADO = "No facturado";
    public final static String ESTADO_FACTURADO_PARCIAL = "Facturado parcialmente";
    public final static String ESTADO_FACTURADO_COMPLETO = "Facturado";
    public final static String TIPO_COMPRA = "Compra";
    public final static String TIPO_VENTA = "Venta";
    public final static String TIPO_PRODUCCION = "Produccion";

    public Integer getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipoCompra() {
        this.tipo = TIPO_COMPRA;
    }

    public void setTipoVenta() {
        this.tipo = TIPO_VENTA;
    }

    public void setTipoProduccion() {
        this.tipo = TIPO_PRODUCCION;
    }

    public Collection<RemitoDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(Collection<RemitoDetalle> detalles) {
        this.detalles = detalles;
    }


    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }


    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Remito other = (Remito) obj;
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
