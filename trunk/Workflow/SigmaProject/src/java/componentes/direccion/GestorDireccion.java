/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes.direccion;

import entidades.Departamento;
import entidades.Direccion;
import entidades.Entidad;
import entidades.Localidad;
import entidades.Provincia;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Zaba
 */
@Stateful
public class GestorDireccion {

    @PersistenceContext
    EntityManager em;
    private Set<Direccion> direccionesABorrar = new HashSet<Direccion>();
    private Collection<Direccion> direccionesAAgregar = new ArrayList<Direccion>();

    public Collection<Provincia> devolverProvincias() {
        return em.createQuery("select p from Provincia p order by p.nombre").getResultList();
    }

    public Collection<Departamento> devolverDepartamentos(Provincia p) {
        return em.createQuery("select d from Departamento d where d.provincia = :p order by d.nombre").setParameter("p", p).getResultList();
    }

    public Collection<Localidad> devolverLocalidades(Departamento d) {
        return em.createQuery("select l from Localidad l where l.departamento = :d order by l.nombre").setParameter("d", d).getResultList();
    }

    public void quitarDireccion(Direccion direccion) {
        direccionesABorrar.add(direccion);
        direccion.getEntidad().getDirecciones().remove(direccion);
    }
    
    public void agregarProvincia(String nombre) {
        Provincia p = new Provincia();
        p.setNombre(nombre.toUpperCase());
        em.persist(p);
    }

    public void agregarDepartamento(String nombre, Provincia provincia) {
        Departamento d = new Departamento();
        d.setNombre(nombre.toUpperCase());
        d.setProvincia(provincia);
        em.persist(d);
    }

    public void agregarLocalidad(String nombre, Departamento departamento) {
        Localidad l = new Localidad();
        l.setNombre(nombre.toUpperCase());
        l.setDepartamento(departamento);
        em.persist(l);
    }

    public Set<Direccion> getDireccionesABorrar() {
        return direccionesABorrar;
    }

    public Collection<Direccion> getDireccionesAAgregar() {
        return direccionesAAgregar;
    }

    public void borrarDireccionesABorrar() {
        for (Direccion d : direccionesABorrar) {
            em.remove(em.merge(d));
        }
    }

    public void reiniciarDireccionesABorrar() {
        this.direccionesABorrar.clear();
    }
    
    public void agregarDireccionesAAgregar() {
        for (Direccion d : direccionesAAgregar) {
            em.persist(em.merge(d));
        }
    }

    public void reiniciarDireccionesAAgregar() {
        this.direccionesAAgregar.clear();
    }

    public void agregarDireccion(Direccion direccion) {
        if (direccion.getEntidad().getDirecciones() == null) {
            direccion.getEntidad().setDirecciones(new ArrayList<Direccion>());
        }
        direccion.getEntidad().getDirecciones().add(direccion);
        this.direccionesAAgregar.add(direccion);
    }

    public void reiniciar() {
        this.direccionesAAgregar.clear();
        this.direccionesABorrar.clear();
    }
}
