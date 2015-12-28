/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades;

import java.math.BigDecimal;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author Zaba
 */

@Entity
@DiscriminatorValue(value="Especifica")
public class ActividadEspecifica extends ActividadGenerica {

    @OneToMany(fetch=FetchType.EAGER)
    @Fetch(value=FetchMode.SELECT)
    @JoinColumn(name="idActividad")
    private Collection<RequerimientoMaterial> requerimientosMaterial;
    @NotNull
    @Min(value=1)
    private Integer secuencia;
    @NotNull
    @Min(value=1)
    private Integer duracionEnMinutos;

    public Integer getDuracionEnMinutos() {
        return duracionEnMinutos;
    }

    public void setDuracionEnMinutos(Integer duracionEnMinutos) {
        this.duracionEnMinutos = duracionEnMinutos;
    }

    public Collection<RequerimientoMaterial> getRequerimientosMaterial() {
        return requerimientosMaterial;
    }

    public void setRequerimientosMaterial(Collection<RequerimientoMaterial> requerimientosMaterial) {
        this.requerimientosMaterial = requerimientosMaterial;
    }

    public Integer getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(Integer secuencia) {
        this.secuencia = secuencia;
    }
}
