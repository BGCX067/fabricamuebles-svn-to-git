/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package componentes.presupuesto;

import entidades.*;
import java.util.*;
import javax.ejb.Stateful;
import javax.persistence.*;

/**
 *
 * @author Zaba
 */

@Stateful
public class GestorPresupuesto {
    @PersistenceContext
    private EntityManager em;

    public Collection<Presupuesto> devolverPresupuestos() {
        return em.createQuery("select p from Presupuesto p").getResultList();
    }

    public void confirmarPresupuesto(Presupuesto presupuestoSeleccionado) {
        presupuestoSeleccionado.calcularTotal();
        em.persist(presupuestoSeleccionado);
    }
    public void actualizarPresupuesto(Presupuesto presupuestoSeleccionado){
        em.merge(presupuestoSeleccionado);
    }

    public Collection<Presupuesto> devolverPresupuestosClienteVigentesNoUsados(Cliente cliente) {
        return em.createQuery("select p from Presupuesto p where p.entidad = :entidad and p.estado = :estado_no_tomado")
                .setParameter("entidad", cliente).setParameter("estado_no_tomado", Presupuesto.ESTADO_VIGENTE_NO_USADO).getResultList();
    }

    public Collection<Presupuesto> devolverPresupuestosCliente(Cliente cliente) {
        return em.createQuery("select p from Presupuesto p where p.entidad = :entidad")
                .setParameter("entidad", cliente).getResultList();
    }

    public Collection<Presupuesto> devolverPresupuestosNoUsados() {
        return em.createQuery("select p from Presupuesto p where p.estado = :estado_no_tomado")
                .setParameter("estado_no_tomado", Presupuesto.ESTADO_VIGENTE_NO_USADO).getResultList();
    }
}
