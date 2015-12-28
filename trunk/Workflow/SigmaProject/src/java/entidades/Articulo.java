/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import com.google.common.collect.Collections2;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import optimizacion.SeleccionadorDePresentacionesParaMinimoDesperdicio;

/**
 *
 * @author Zaba
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TipoArticulo", discriminatorType = DiscriminatorType.STRING)
public abstract class Articulo implements Serializable, Requerible {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "articulo")
    private Collection<ArticuloPresentacion> presentaciones;
    @ManyToOne(optional=false)
    private Familia familia;

    public Long getId() {
        return id;
    }

    public abstract String getNombre();

    public abstract String getTipo();

    public Collection<ArticuloPresentacion> getPresentaciones() {
        return presentaciones;
    }

    public void setPresentaciones(Collection<ArticuloPresentacion> presentaciones) {
        this.presentaciones = presentaciones;
    }

    public Familia getFamilia() {
        return familia;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Articulo other = (Articulo) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
