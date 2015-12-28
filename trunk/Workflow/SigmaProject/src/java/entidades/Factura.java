/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NaturalId;

/**
 *
 * @author Cristian
 */
@Entity
public class Factura implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @NaturalId
    private String numero;
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @Fetch (value=FetchMode.SELECT)
    @JoinColumn(name="idFactura")
    private Collection<FacturaDetalle> detalles;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaCreacion;
    @NotNull
    private BigDecimal importeTotal;
    @ManyToOne
    @JoinColumn(name="idEntidad")
    @NotNull
    private Entidad entidad;
    private String tipo;
    private String estado = ESTADO_NO_ENTREGADO;
    public final static String ESTADO_NO_ENTREGADO= "No Entregado";
    public final static String ESTADO_ENTREGADO = "Entregado";
    public final static String TIPO_COMPRA = "Compra";
    public final static String TIPO_VENTA = "Venta";

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void calcularTotal() {
        BigDecimal total = new BigDecimal(0).setScale(2);
        for (FacturaDetalle detalle : this.detalles) {
            total = total.add(detalle.calcularSubTotal());
        }
        this.importeTotal = total;
    }

    public Integer getId() {
        return id;
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
    
    public void setEstado(String estado){
        this.estado = estado;
    }
    public String getEstado() {
        return this.estado;
    }

    public Collection<FacturaDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(Collection<FacturaDetalle> detalles) {
        this.detalles = detalles;
    }
    
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaEmision) {
        this.fechaCreacion = fechaEmision;
    }

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }

    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }
   

    

}
