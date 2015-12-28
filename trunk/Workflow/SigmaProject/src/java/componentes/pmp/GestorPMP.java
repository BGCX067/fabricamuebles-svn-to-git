/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes.pmp;

import entidades.*;
import java.util.*;
import java.util.ArrayList;
import javax.ejb.Stateful;
import javax.persistence.*;
import org.hibernate.ejb.EntityManagerImpl;

/**
 *
 * @author Zaba
 */
@Stateful
public class GestorPMP {

    @PersistenceContext
    private EntityManager em;

    public Collection<PMPDetalle> devolverPMPDetalles() {
        Collection<PMPDetalle> list = em.createQuery("select pmpd from PMPDetalle pmpd").getResultList();
        for (PMPDetalle pmpDetalle : list) {
            if (pmpDetalle.getPedidoDetalles() != null) {
                pmpDetalle.getPedidoDetalles().size(); // para que no me tire exception lazy
            }
        }
        return list;
    }

    public void agregarPedidosDetalleAPMP(Collection<PedidoDetalle> pedidoDetalles) {
        Collection<PMPDetalle> pmpDetalles = devolverPMPDetalles();
        HashMap<MuebleModelo, PMPDetalle> hashMapModeloPMPDetalle = new HashMap<MuebleModelo, PMPDetalle>();
        for (PMPDetalle pmpDetalle : pmpDetalles) {
            hashMapModeloPMPDetalle.put(pmpDetalle.getMuebleModelo(), pmpDetalle);
        }
        for (PedidoDetalle pedidoDetalle : pedidoDetalles) {
            if (!hashMapModeloPMPDetalle.containsKey(pedidoDetalle.getMuebleModelo())) {
                PMPDetalle pmpDetalle = new PMPDetalle();
                pmpDetalle.setMuebleModelo(pedidoDetalle.getMuebleModelo());
                em.persist(pmpDetalle);
                hashMapModeloPMPDetalle.put(pedidoDetalle.getMuebleModelo(), pmpDetalle);
            }
            PMPDetalle pmpDetalle = hashMapModeloPMPDetalle.get(pedidoDetalle.getMuebleModelo());
            pmpDetalle.setCantidadPorAsignar(pmpDetalle.getCantidadPorAsignar().add(pedidoDetalle.getCantidad()));
            if (pmpDetalle.getPedidoDetalles() == null) {
                pmpDetalle.setPedidoDetalles(new ArrayList<PedidoDetalle>());
            }
            pmpDetalle.getPedidoDetalles().add(pedidoDetalle);
            em.merge(pmpDetalle);
            pedidoDetalle.setPmpDetallePadre(pmpDetalle);
            em.merge(pedidoDetalle);
        }
    }

    public Collection<PMPDetalle> devolverPMPDetallesMueblesTipo(MuebleTipo muebleTipo) {
        return em.createQuery("select pmpd from PMPDetalle pmpd where pmpd.muebleModelo.muebleTipo = :muebleTipo").setParameter("muebleTipo", muebleTipo).getResultList();
    }

    public PMPDetalle devolverPMPDetalleMueblesModelo(MuebleModelo muebleModelo) {
        return (PMPDetalle) em.createQuery("select pmpd from PMPDetalle pmpd where pmpd.muebleModelo = :muebleModelo").setParameter("muebleModelo", muebleModelo).getSingleResult();
    }

    public void actualizarPmpDetalle(PMPDetalle pmpDetalle) {
        em.merge(pmpDetalle);
    }

    public void quitarPmpDetalle(PMPDetalle pmpDetalleModelo) {
        em.remove(em.merge(pmpDetalleModelo));
    }
}
