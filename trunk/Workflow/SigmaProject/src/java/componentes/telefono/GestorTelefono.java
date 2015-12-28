/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes.telefono;

import entidades.Departamento;
import entidades.Direccion;
import entidades.Localidad;
import entidades.Provincia;
import entidades.Telefono;
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
public class GestorTelefono {

    @PersistenceContext
    EntityManager em;
    private Set<Telefono> telefonosABorrar = new HashSet<Telefono>();
    private Collection<Telefono> telefonosAAgregar = new ArrayList<Telefono>();

    public void quitarTelefono(Telefono telefono) {
        telefonosABorrar.add(telefono);
        telefono.getEntidad().getTelefonos().remove(telefono);
    }

    public Set<Telefono> getTelefonosABorrar() {
        return telefonosABorrar;
    }

    public Collection<Telefono> getTelefonosAAgregar() {
        return telefonosAAgregar;
    }

    public void borrarTelefonosABorrar() {
        for (Telefono t : telefonosABorrar) {
            em.remove(em.merge(t));
        }
    }

    public void reiniciarTelefonosABorrar() {
        this.telefonosABorrar.clear();
    }
    
    public void agregarTelefonosAAgregar() {
        for (Telefono t : telefonosAAgregar) {
            em.persist(em.merge(t));
        }
    }

    public void reiniciarTelefonosAAgregar() {
        this.telefonosAAgregar.clear();
    }

    public void agregarTelefono(Telefono telefono) {
        if (telefono.getEntidad().getTelefonos() == null) {
            telefono.getEntidad().setTelefonos(new ArrayList<Telefono>());
        }
        telefono.getEntidad().getTelefonos().add(telefono);
        this.telefonosAAgregar.add(telefono);
    }

    public void reiniciar() {
        this.telefonosAAgregar.clear();
        this.telefonosABorrar.clear();
    }
}
