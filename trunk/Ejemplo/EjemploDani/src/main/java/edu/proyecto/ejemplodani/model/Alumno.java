/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.proyecto.ejemplodani.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author DanielRH
 */
@Entity
public class Alumno implements Serializable, Academico, Evaluable {
    @Id
    @GeneratedValue
    private Long id;
    private String nombre;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaInicio;
    @ManyToOne(cascade= CascadeType.ALL)
    private Categoria categoria;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public Categoria getCategoria() {
        return categoria;
    }
    
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public void evaluar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
