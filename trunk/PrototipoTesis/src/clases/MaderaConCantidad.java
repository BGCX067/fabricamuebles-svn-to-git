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
public class MaderaConCantidad implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Madera actual;
    private int cantidad;
    
    public MaderaConCantidad()
    {
        actual=null;
        cantidad=0;
    }

    /**
     * @return the actual
     */
    public Madera getActual() {
        return actual;
    }

    /**
     * @param actual the actual to set
     */
    public void setActual(Madera actual) {
        this.actual = actual;
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
