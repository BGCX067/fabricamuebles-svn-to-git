/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author Zaba
 */
@Entity
public class MuebleParte implements Serializable, Requerible, Fabricable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String descripcion;
    @OneToMany(fetch=FetchType.EAGER)
    @Fetch(value=FetchMode.SELECT)
    @JoinColumn(name="idMuebleParte")
    @OrderBy(value="secuencia")
    private Collection<ActividadEspecifica> actividades;
    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name="idMueblePartePadre")
    @Fetch(value=FetchMode.SELECT)
    private Collection<RequerimientoMaterial> requerimientosMateriales;
    //private Collection<MuebleParteXCorte> muebleParteXCorte;
    @NotNull
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return "Parte";
    }

    public Collection<RequerimientoMaterial> getRequerimientosMateriales() {
        return requerimientosMateriales;
    }

    public void setRequerimientosMateriales(Collection<RequerimientoMaterial> requerimientosMateriales) {
        this.requerimientosMateriales = requerimientosMateriales;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Long getId() {
        return id;
    }

    public Collection<ActividadEspecifica> getActividades() {
        return this.actividades;
    }

    public void setActividades(Collection<ActividadEspecifica> actividadEspecificas) {
        this.actividades = actividadEspecificas;
    }
}
