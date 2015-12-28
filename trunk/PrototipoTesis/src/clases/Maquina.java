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
public class Maquina implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String nombre;
    
    public Maquina()
    {
        nombre="";
    }
    
    public Maquina(String nom)
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
}
