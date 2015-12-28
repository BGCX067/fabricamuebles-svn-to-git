/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NaturalId;

/**
 *
 * @author Zaba
 */
@Entity
public class MuebleModelo implements Serializable, Fabricable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal costoPropio;
    private BigDecimal costoTerciarizado;
    private String descripcion;
    @OneToOne
    private Foto foto;
    @NaturalId
    @NotNull
    private String nombre;
    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "idMuebleModelo")
    @OrderBy("secuencia")
    private Collection<ActividadEspecifica> actividades;
    private BigDecimal precioVenta;
    private BigDecimal stockActual = BigDecimal.ZERO;
    private BigDecimal stockReservado = BigDecimal.ZERO;
    private BigDecimal stockLotes = BigDecimal.ZERO;
    @ManyToOne(optional = false)
    @JoinColumn(name = "idMuebleTipo")
    private MuebleTipo muebleTipo;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "idMuebleModelo")
    @Fetch(value = FetchMode.SELECT)
    private Collection<RequerimientoMaterial> requerimientosMateriales;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Collection<RequerimientoMaterial> getRequerimientosMateriales() {
        return requerimientosMateriales;
    }

    public void setRequerimientosMateriales(Collection<RequerimientoMaterial> requerimientosMateriales) {
        this.requerimientosMateriales = requerimientosMateriales;
    }

    /**
     * Procesa todo el arbol de Requerimientos Materiales, devolviendo solo los artículos necesarios, con su cantidad calculada, para
     * un solo modelo mueble de este tipo.
     * @return
     */
    public Collection<RequerimientoMaterial> devolverRequerimientosMaterialesAComprar() {
        return devolverRequerimientosMaterialesAComprar(BigDecimal.ONE);
    }

    /**
     * Procesa todo el arbol de Requerimientos Materiales, devolviendo solo los artículos necesarios, con su cantidad calculada, para
     * la cantidad especificada de muebles modelo de este tipo.
     * @return
     */
    public Collection<RequerimientoMaterial> devolverRequerimientosMaterialesAComprar(BigDecimal cantidadACalcular) {
        HashMap<Articulo, RequerimientoMaterial> mapa = new HashMap<Articulo, RequerimientoMaterial>();
        agregarRequerimientosMaterialesAComprarAMapa(mapa, this.requerimientosMateriales, cantidadACalcular);
        return mapa.values();
    }

    private void agregarRequerimientosMaterialesAComprarAMapa(HashMap<Articulo, RequerimientoMaterial> mapaALlenar, Collection<RequerimientoMaterial> listaOrigen, BigDecimal cantidadNecesaria) {
        if (listaOrigen != null && listaOrigen.size() > 0) {
            for (RequerimientoMaterial requerimientoMaterial : listaOrigen) {
                if (requerimientoMaterial.getItemRequerible() instanceof MuebleParte) {
                    MuebleParte muebleParte = (MuebleParte) requerimientoMaterial.getItemRequerible();
                    agregarRequerimientosMaterialesAComprarAMapa(mapaALlenar, muebleParte.getRequerimientosMateriales(), requerimientoMaterial.getCantidadRequerida().multiply(cantidadNecesaria));
                } else {
                    if (!mapaALlenar.containsKey((Articulo) requerimientoMaterial.getItemRequerible())) {
                        RequerimientoMaterial requerimiento = new RequerimientoMaterial();
                        requerimiento.setCantidadRequerida(BigDecimal.ZERO);
                        requerimiento.setItemRequerible(requerimientoMaterial.getItemRequerible());
                        mapaALlenar.put((Articulo) requerimientoMaterial.getItemRequerible(), requerimiento);
                    }
                    RequerimientoMaterial rm = mapaALlenar.get((Articulo) requerimientoMaterial.getItemRequerible());
                    rm.agregarCantidadRequerida(requerimientoMaterial.getCantidadRequerida().multiply(cantidadNecesaria));
                }
            }
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public BigDecimal getStockActual() {
        return stockActual;
    }

    public void setStockActual(BigDecimal stockActual) {
        this.stockActual = stockActual;
    }

    public BigDecimal getStockReservado() {
        return stockReservado;
    }

    public void setStockReservado(BigDecimal stockReservado) {
        this.stockReservado = stockReservado;
    }

    public BigDecimal getStockDisponible() {
        return this.stockActual.subtract(this.stockReservado);
    }

    public Long getId() {
        return id;
    }

    public Collection<ActividadEspecifica> getActividades() {
        return actividades;
    }

    public void setActividades(Collection<ActividadEspecifica> actividades) {
        this.actividades = actividades;
    }

    public void setMuebleTipo(MuebleTipo muebleTipo) {
        this.muebleTipo = muebleTipo;
    }

    public MuebleTipo getMuebleTipo() {
        return this.muebleTipo;
    }

    public Integer calcularCantidadMinutosProduccion() {
        if (actividades == null) {
            throw new AssertionError("Se llamó a calcularCantidadMinutosProduccion y no se tienen actividades definidas.");
        }
        Integer cantidad = 0;
        for (ActividadEspecifica actividad : actividades) {
            cantidad += actividad.getDuracionEnMinutos();
        }
        return cantidad;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MuebleModelo other = (MuebleModelo) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    public Boolean tieneEstructuraProductoDefinida() {
        return this.requerimientosMateriales != null && !this.requerimientosMateriales.isEmpty() && this.actividades != null && !this.actividades.isEmpty();
    }
}
