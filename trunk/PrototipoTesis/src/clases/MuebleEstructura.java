/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author DanielRH
 */
public class MuebleEstructura implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String nombre;
    private LinkedList <Pieza>partes;
    private LinkedList <Trabajo>trabajos;
    
    public MuebleEstructura(String nom)
    {
        nombre=nom;
        partes=new LinkedList();
        trabajos=new LinkedList();
    }

    public MuebleEstructura()
    {
        nombre="";
        partes=new LinkedList();
        trabajos=new LinkedList();
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
     * @return the partes
     */
    public LinkedList <Pieza> getPartes() {
        return partes;
    }

    /**
     * @param partes the partes to set
     */
    public void setPartes(LinkedList <Pieza> partes) {
        this.partes = partes;
    }

    /**
     * @return the trabajos
     */
    public LinkedList <Trabajo> getTrabajos() {
        return trabajos;
    }

    /**
     * @param trabajos the trabajos to set
     */
    public void setTrabajos(LinkedList <Trabajo> trabajos) {
        this.trabajos = trabajos;
    }
    
}
