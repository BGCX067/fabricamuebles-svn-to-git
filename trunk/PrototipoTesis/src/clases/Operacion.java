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
public class Operacion implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String nombre;
//    private LinkedList <Pieza>piezasQueIntervienen;
//    private LinkedList <Madera> maderaQueSeUtiliza;
//    private LinkedList <Insumo> requerimientoInsumos;
//    private LinkedList <Herramienta> requerimientoHerramientas;
//    private LinkedList <Maquina> requerimientoMaquinas;
//    private LinkedList <CategoriaEmpleado> categoriasEmpleados;
    
    public Operacion()
    {
        nombre="";
    }
    
    public Operacion(String nom)
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
