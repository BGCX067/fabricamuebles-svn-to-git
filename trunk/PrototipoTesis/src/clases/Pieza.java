/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;

/**
 *
 * @author DanielRH
 */
public class Pieza implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String nombre;
    private float largoCM;
    private float anchoCM;
    private int espesorMM;
    private int cantidad;
    
    public Pieza()
    {
        nombre="";
    }
    
    public Pieza(String nom)
    {
        nombre=nom;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the largoCM
     */
    public float getLargoCM() {
        return largoCM;
    }

    /**
     * @param largoCM the largoCM to set
     */
    public void setLargoCM(float largoCM) {
        this.largoCM = largoCM;
    }

    /**
     * @return the anchoCM
     */
    public float getAnchoCM() {
        return anchoCM;
    }

    /**
     * @param anchoCM the anchoCM to set
     */
    public void setAnchoCM(float anchoCM) {
        this.anchoCM = anchoCM;
    }

    /**
     * @return the espesorMM
     */
    public int getEspesorMM() {
        return espesorMM;
    }

    /**
     * @param espesorMM the espesorMM to set
     */
    public void setEspesorMM(int espesorMM) {
        this.espesorMM = espesorMM;
    }

    /**
     * @return the cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
}
