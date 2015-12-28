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
public class PiezaConCantidad implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Pieza actual;
    private int cantidad;
    
    public PiezaConCantidad()
    {
        actual=null;
        cantidad=0;
    }

    /**
     * @return the actual
     */
    public Pieza getActual() {
        return actual;
    }

    /**
     * @param actual the actual to set
     */
    public void setActual(Pieza actual) {
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
