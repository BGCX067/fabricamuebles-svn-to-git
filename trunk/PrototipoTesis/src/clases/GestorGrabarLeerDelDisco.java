/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author DanielRH
 */
public class GestorGrabarLeerDelDisco {
    
    public GestorGrabarLeerDelDisco()
    {
        
    }
    
    public boolean grabar(Object objeto)
    {
        boolean yaExiste=false;
        String nombreClase=objeto.getClass().getSimpleName();
        crearArchivoSiNoExiste(nombreClase);
        LinkedList lista;
        lista=obtenerLista(objeto);
        
        
        if(existeEnLista(lista,objeto))
        {
            yaExiste=true;
        }
        
        if(yaExiste==false)
        {
            lista.add(objeto);
            try
            {
                FileOutputStream fileOut=new FileOutputStream(nombreClase+"s.dan");
                ObjectOutputStream salida=new ObjectOutputStream(fileOut);
                salida.writeObject(lista);
                salida.close();
                fileOut.close();
            }catch(Exception e){System.out.println("Error en Grabar: "+e.toString());}
        }
        return !yaExiste;
    }
    
    public LinkedList obtenerLista(Object objeto)
    {
        LinkedList datos=null;
        String nombreClase=objeto.getClass().getSimpleName();
        try
        {
            FileInputStream fileIn=new FileInputStream(nombreClase+"s.dan");
            ObjectInputStream entrada=new ObjectInputStream(fileIn);
            datos=(LinkedList)entrada.readObject();
            entrada.close();
            fileIn.close();
        }catch(Exception e){System.out.println("Error en Obtener Lista: "+e.toString());}
        return datos;
    }
    
    private void crearArchivoSiNoExiste(String arch)
    {
        File archivo = new File(arch+"s.dan");
        if(archivo.exists()==false)
        {
            LinkedList lista=new LinkedList();
            try
            {
                FileOutputStream fileOut=new FileOutputStream(arch+"s.dan");
                ObjectOutputStream salida=new ObjectOutputStream(fileOut);
                salida.writeObject(lista);
                salida.close();
                fileOut.close();
            }catch(Exception e){System.out.println("Error en Crear Archivo: "+e.toString());}
        }
    }
    
    public void grabarLista(LinkedList lista)
    {
        String arch=lista.element().getClass().getSimpleName();//no funciona si no hay elementos
        try
        {
            FileOutputStream fileOut=new FileOutputStream(arch+"s.dan");
            ObjectOutputStream salida=new ObjectOutputStream(fileOut);
            salida.writeObject(lista);
            salida.close();
            fileOut.close();
        }catch(Exception e){System.out.println("Error en Grabar Lista: "+e.toString());}
    }
    
    public void grabarLista(LinkedList lista, String nombreClase)
    {
        String arch=nombreClase;
        try
        {
            FileOutputStream fileOut=new FileOutputStream(arch+"s.dan");
            ObjectOutputStream salida=new ObjectOutputStream(fileOut);
            salida.writeObject(lista);
            salida.close();
            fileOut.close();
        }catch(Exception e){System.out.println("Error en Grabar Lista: "+e.toString());}
    }
    
    public void eliminarObjetoGrabarLista(LinkedList lista, Object objeto)
    {
        String arch=objeto.getClass().getSimpleName();
        lista.remove(objeto);
        try
        {
            FileOutputStream fileOut=new FileOutputStream(arch+"s.dan");
            ObjectOutputStream salida=new ObjectOutputStream(fileOut);
            salida.writeObject(lista);
            salida.close();
            fileOut.close();
        }catch(Exception e){System.out.println("Error en Grabar Lista: "+e.toString());}
    }
    
    private boolean existeEnLista(LinkedList lista, Object objeto)
    {
        boolean respuesta=false;
        Iterator iter = lista.iterator();
        while(iter.hasNext())
        {
            Object leido=iter.next();
            if(objeto.getClass().getSimpleName().compareTo("Pieza")==0)
            {
                Pieza aux = (Pieza)leido;
                Pieza aux2 = (Pieza)objeto;
                if(aux.getNombre().compareToIgnoreCase(aux2.getNombre())==0)
                {
                    respuesta=true;
                }
            }
            if(objeto.getClass().getSimpleName().compareTo("MuebleEstructura")==0)
            {
                MuebleEstructura aux = (MuebleEstructura)leido;
                MuebleEstructura aux2 = (MuebleEstructura)objeto;
                if(aux.getNombre().compareToIgnoreCase(aux2.getNombre())==0)
                {
                    respuesta=true;
                }
            }
            if(objeto.getClass().getSimpleName().compareTo("Operacion")==0)
            {
                Operacion aux = (Operacion)leido;
                Operacion aux2 = (Operacion)objeto;
                if(aux.getNombre().compareToIgnoreCase(aux2.getNombre())==0)
                {
                    respuesta=true;
                }
            }
            if(objeto.getClass().getSimpleName().compareTo("Herramienta")==0)
            {
                Herramienta aux = (Herramienta)leido;
                Herramienta aux2 = (Herramienta)objeto;
                if(aux.getNombre().compareToIgnoreCase(aux2.getNombre())==0)
                {
                    respuesta=true;
                }
            }
            if(objeto.getClass().getSimpleName().compareTo("Madera")==0)
            {
                Madera aux = (Madera)leido;
                Madera aux2 = (Madera)objeto;
                if(aux.getNombre().compareToIgnoreCase(aux2.getNombre())==0)
                {
                    respuesta=true;
                }
            }
            if(objeto.getClass().getSimpleName().compareTo("Maquina")==0)
            {
                Maquina aux = (Maquina)leido;
                Maquina aux2 = (Maquina)objeto;
                if(aux.getNombre().compareToIgnoreCase(aux2.getNombre())==0)
                {
                    respuesta=true;
                }
            }
            if(objeto.getClass().getSimpleName().compareTo("Insumo")==0)
            {
                Insumo aux = (Insumo)leido;
                Insumo aux2 = (Insumo)objeto;
                if(aux.getNombre().compareToIgnoreCase(aux2.getNombre())==0)
                {
                    respuesta=true;
                }
            }
        }
        return respuesta;
    }
    
    public boolean eliminarDeLista(LinkedList lista, Object objeto)
    {
        boolean respuesta=false;
        Object aEliminar=null;
        Iterator iter = lista.iterator();
        while(iter.hasNext())
        {
            Object leido=iter.next();
            if(objeto.getClass().getSimpleName().compareTo("Pieza")==0)
            {
                Pieza aux = (Pieza)leido;
                Pieza aux2 = (Pieza)objeto;
                if(aux.getNombre().compareToIgnoreCase(aux2.getNombre())==0)
                {
                    aEliminar=leido;
                }
            }
            if(objeto.getClass().getSimpleName().compareTo("MuebleEstructura")==0)
            {
                MuebleEstructura aux = (MuebleEstructura)leido;
                MuebleEstructura aux2 = (MuebleEstructura)objeto;
                if(aux.getNombre().compareToIgnoreCase(aux2.getNombre())==0)
                {
                    aEliminar=leido;
                }
            }
            if(objeto.getClass().getSimpleName().compareTo("Operacion")==0)
            {
                Operacion aux = (Operacion)leido;
                Operacion aux2 = (Operacion)objeto;
                if(aux.getNombre().compareToIgnoreCase(aux2.getNombre())==0)
                {
                    aEliminar=leido;
                }
            }
            if(objeto.getClass().getSimpleName().compareTo("Herramienta")==0)
            {
                Herramienta aux = (Herramienta)leido;
                Herramienta aux2 = (Herramienta)objeto;
                if(aux.getNombre().compareToIgnoreCase(aux2.getNombre())==0)
                {
                    aEliminar=leido;
                }
            }
            if(objeto.getClass().getSimpleName().compareTo("Madera")==0)
            {
                Madera aux = (Madera)leido;
                Madera aux2 = (Madera)objeto;
                if(aux.getNombre().compareToIgnoreCase(aux2.getNombre())==0)
                {
                    aEliminar=leido;
                }
            }
            if(objeto.getClass().getSimpleName().compareTo("Maquina")==0)
            {
                Maquina aux = (Maquina)leido;
                Maquina aux2 = (Maquina)objeto;
                if(aux.getNombre().compareToIgnoreCase(aux2.getNombre())==0)
                {
                    aEliminar=leido;
                }
            }
            if(objeto.getClass().getSimpleName().compareTo("Insumo")==0)
            {
                Insumo aux = (Insumo)leido;
                Insumo aux2 = (Insumo)objeto;
                if(aux.getNombre().compareToIgnoreCase(aux2.getNombre())==0)
                {
                    aEliminar=leido;
                }
            }
        }
        if(aEliminar!=null)
        {
            lista.remove(aEliminar);
            respuesta=true;
        }
        return respuesta;
    }
    
    public boolean eliminarObjeto(Object objeto)
    {
        boolean respuesta=false;
        String nombreClase=objeto.getClass().getSimpleName();
        LinkedList lista=obtenerLista(objeto);
        respuesta=eliminarDeLista(lista, objeto);
        if(respuesta==true)
        {
            grabarLista(lista,nombreClase);
        }
        return respuesta;
    }
}
