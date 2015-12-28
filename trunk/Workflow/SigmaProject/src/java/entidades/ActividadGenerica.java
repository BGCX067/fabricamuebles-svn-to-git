/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades;

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
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="Tipo")
@DiscriminatorValue(value="Generica")
public class ActividadGenerica implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String descripcion;
    @NotNull
    private String nombre;
    @ManyToOne
    @JoinColumn(name="idMaquina")
    private MaquinaTipo maquinaTipo;
    @ManyToMany(fetch=FetchType.EAGER)
    @Fetch(value=FetchMode.SELECT)
    private Collection<HerramientaTipo> herramientasTipo;

    public Long getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Collection<HerramientaTipo> getHerramientasTipo() {
        return herramientasTipo;
    }

    public void setHerramientasTipo(Collection<HerramientaTipo> herramientasTipo) {
        this.herramientasTipo = herramientasTipo;
    }

    public MaquinaTipo getMaquinaTipo() {
        return maquinaTipo;
    }

    public void setMaquinaTipo(MaquinaTipo maquinaTipo) {
        this.maquinaTipo = maquinaTipo;
    }
}
