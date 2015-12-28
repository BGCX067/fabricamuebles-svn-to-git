/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades;

import java.util.Collection;
import javax.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author Zaba
 */

@Entity
@DiscriminatorValue(value="Fabrica")
public class Fabrica extends Entidad {

    @ManyToOne
    @JoinColumn(name="idEntidad")
    private Entidad encargado;

    @OneToMany(mappedBy="fabrica", fetch=FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true)
    @Fetch(value=FetchMode.SELECT)
    private Collection<FabricaMuebleTipoCapacidadMaxima> muebleTipoCapacidadesMaximas;
    private Boolean esInterna = false;

    public Entidad getEncargado() {
        return encargado;
    }

    public void setEncargado(Entidad encargado) {
        this.encargado = encargado;
    }

    public Boolean getInterna() {
        return this.esInterna;
    }

    public void setInterna(Boolean esInterna) {
        this.esInterna = esInterna;
    }

    public Collection<FabricaMuebleTipoCapacidadMaxima> getMuebleTipoCapacidadesMaximas() {
        return muebleTipoCapacidadesMaximas;
    }

    public void setMuebleTipoCapacidadesMaximas(Collection<FabricaMuebleTipoCapacidadMaxima> muebleTipoCapacidadesMaximas) {
        this.muebleTipoCapacidadesMaximas = muebleTipoCapacidadesMaximas;
    }

    public Integer devolverCapacidadMaximaPara(MuebleTipo muebleTipo) {
        Integer ret = null;
        if (this.muebleTipoCapacidadesMaximas != null) {
            for (FabricaMuebleTipoCapacidadMaxima muebleTipoCapacidadMaxima : this.muebleTipoCapacidadesMaximas) {
                if (muebleTipoCapacidadMaxima.getMuebleTipo().equals(muebleTipo)) {
                    ret = muebleTipoCapacidadMaxima.getCapacidadMaxima();
                    break;
                }
            }
        }
        return ret;
    }
}
