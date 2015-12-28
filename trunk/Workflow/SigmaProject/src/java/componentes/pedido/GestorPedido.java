/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package componentes.pedido;

import entidades.*;
import java.util.*;
import javax.ejb.Stateful;
import javax.persistence.*;
import org.hibernate.ejb.EntityManagerImpl;

/**
 *
 * @author Zaba
 */

@Stateful
public class GestorPedido {
    @PersistenceContext
    private EntityManager em;

    public Collection<Pedido> devolverPedidos() {
        return em.createQuery("select p from Pedido p").getResultList();
    }
    public Collection<Pedido>devolverPedidos(Cliente cliente){
         return em.createQuery("select p from Pedido p where p.entidad = :entidad")
                .setParameter("entidad", cliente).getResultList();
    }

    public void confirmarPedido(Pedido pedidoSeleccionado) {
        pedidoSeleccionado.calcularTotal();
        for (PedidoDetalle pedidoDetalle : pedidoSeleccionado.getDetalles()) {
            pedidoDetalle.setPedido(pedidoSeleccionado);
        }
        // Antes de guardar, me fijo que el numero previamente calculado no exista, si existe le asigno otro que esté disponible
        Integer numero = devolverNumeroDisponibleParaPedido();
        if (!pedidoSeleccionado.getNumero().equals(numero)) {
            pedidoSeleccionado.setNumero(numero);
        }
        em.persist(pedidoSeleccionado);
    }

    public void actualizarPedido(Pedido pedido) {
        em.merge(pedido);
    }

    public Integer devolverNumeroDisponibleParaPedido() {
        Integer numero = (Integer) em.createQuery("select max(p.numero) from Pedido p").getSingleResult();
        if (numero == null) {
            numero = 1;
        } else {
            numero++;
        }
        return numero;
    }
}
