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
public class Madera implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String nombre;
    private float largoCM;
    private float anchoCM;
    private int espesorMM;
    
    public Madera()
    {
        nombre="";
    }
    
    public Madera(String nom)
    {
        nombre=nom;
        largoCM=0.0f;
        anchoCM=0.0f;
        espesorMM=0;
    }
    
    public Madera(String nom, float largo, float ancho, int espesor)
    {
        nombre=nom;
        largoCM=largo;
        anchoCM=ancho;
        espesorMM=espesor;
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
}
