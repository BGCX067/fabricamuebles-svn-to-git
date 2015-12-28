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
public class Maquina implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private MaquinaTipo maquinaTipo;

    private String nombre;
    private String descripcion;
    private String estado;

    public Integer getId() {
        return id;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public MaquinaTipo getMaquinaTipo() {
        return maquinaTipo;
    }

    public void setMaquinaTipo(MaquinaTipo maquinaTipo) {
        this.maquinaTipo = maquinaTipo;
    }
}
