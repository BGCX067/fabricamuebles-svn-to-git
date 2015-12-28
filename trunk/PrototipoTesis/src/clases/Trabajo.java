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
public class Trabajo implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String nombre;
    private Operacion operacion;
    private LinkedList <Trabajo>trabajosPrevios;
    private int duracionEstimada1Mueble;
    private LinkedList <MaderaConCantidad> requerimientoMadera;
    private LinkedList <PiezaConCantidad>piezasQueIntervienen;
    private LinkedList <InsumoConCantidad> requerimientosInsumos;
    private LinkedList <HerramientaConCantidad> requerimientoHerramientas;
    private LinkedList <Maquina> requerimientoMaquinas;
    
    public Trabajo()
    {
        nombre="";
        operacion=null;
        trabajosPrevios=new LinkedList();
        duracionEstimada1Mueble=0;
        requerimientoMadera=new LinkedList();
        piezasQueIntervienen=new LinkedList();
        requerimientosInsumos=new LinkedList();
        requerimientoHerramientas=new LinkedList();
        requerimientoMaquinas=new LinkedList();
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
     * @return the operacion
     */
    public Operacion getOperacion() {
        return operacion;
    }

    /**
     * @param operacion the operacion to set
     */
    public void setOperacion(Operacion operacion) {
        this.operacion = operacion;
    }

    /**
     * @return the trabajosPrevios
     */
    public LinkedList <Trabajo> getTrabajosPrevios() {
        return trabajosPrevios;
    }

    /**
     * @param trabajosPrevios the trabajosPrevios to set
     */
    public void setTrabajosPrevios(LinkedList <Trabajo> trabajosPrevios) {
        this.trabajosPrevios = trabajosPrevios;
    }

    /**
     * @return the duracionEstimada1Mueble
     */
    public int getDuracionEstimada1Mueble() {
        return duracionEstimada1Mueble;
    }

    /**
     * @param duracionEstimada1Mueble the duracionEstimada1Mueble to set
     */
    public void setDuracionEstimada1Mueble(int duracionEstimada1Mueble) {
        this.duracionEstimada1Mueble = duracionEstimada1Mueble;
    }

    /**
     * @return the requerimientoMadera
     */
    public LinkedList <MaderaConCantidad> getRequerimientoMadera() {
        return requerimientoMadera;
    }

    /**
     * @param requerimientoMadera the requerimientoMadera to set
     */
    public void setRequerimientoMadera(LinkedList <MaderaConCantidad> requerimientoMadera) {
        this.requerimientoMadera = requerimientoMadera;
    }

    /**
     * @return the piezasQueIntervienen
     */
    public LinkedList <PiezaConCantidad> getPiezasQueIntervienen() {
        return piezasQueIntervienen;
    }

    /**
     * @param piezasQueIntervienen the piezasQueIntervienen to set
     */
    public void setPiezasQueIntervienen(LinkedList <PiezaConCantidad> piezasQueIntervienen) {
        this.piezasQueIntervienen = piezasQueIntervienen;
    }

    /**
     * @return the requerimientosInsumos
     */
    public LinkedList <InsumoConCantidad> getRequerimientosInsumos() {
        return requerimientosInsumos;
    }

    /**
     * @param requerimientosInsumos the requerimientosInsumos to set
     */
    public void setRequerimientosInsumos(LinkedList <InsumoConCantidad> requerimientosInsumos) {
        this.requerimientosInsumos = requerimientosInsumos;
    }

    /**
     * @return the requerimientoHerramientas
     */
    public LinkedList <HerramientaConCantidad> getRequerimientoHerramientas() {
        return requerimientoHerramientas;
    }

    /**
     * @param requerimientoHerramientas the requerimientoHerramientas to set
     */
    public void setRequerimientoHerramientas(LinkedList <HerramientaConCantidad> requerimientoHerramientas) {
        this.requerimientoHerramientas = requerimientoHerramientas;
    }

    /**
     * @return the requerimientoMaquinas
     */
    public LinkedList <Maquina> getRequerimientoMaquinas() {
        return requerimientoMaquinas;
    }

    /**
     * @param requerimientoMaquinas the requerimientoMaquinas to set
     */
    public void setRequerimientoMaquinas(LinkedList <Maquina> requerimientoMaquinas) {
        this.requerimientoMaquinas = requerimientoMaquinas;
    }
    
}
