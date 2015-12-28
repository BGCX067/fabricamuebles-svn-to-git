/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades;

import javax.persistence.*;

/**
 *
 * @author Zaba
 */
@Entity
public class Herramienta implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private HerramientaTipo herramientaTipo;

    private String nombre;
    private String descripcion;

    public Integer getId() {
        return id;
    }

    public HerramientaTipo getHerramientaTipo() {
        return herramientaTipo;
    }

    public void setHerramientaTipo(HerramientaTipo herramientaTipo) {
        this.herramientaTipo = herramientaTipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
