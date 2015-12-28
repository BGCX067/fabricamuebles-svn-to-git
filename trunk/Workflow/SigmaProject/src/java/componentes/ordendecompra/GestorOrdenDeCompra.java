/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes.ordendecompra;

import componentes.ordendeproduccion.VistaOrdenDeProduccion;
import entidades.*;
import entidades.ordenamiento.ComparadorOrdenDeCompraPorEstado;
import java.math.BigDecimal;
import java.util.*;
import javax.ejb.Stateful;;
import javax.faces.context.FacesContext;
import javax.persistence.*;

/**
 *
 * @author Zaba
 */
@Stateful
public class GestorOrdenDeCompra {

    @PersistenceContext
    private EntityManager em;

    public void actualizarEstadoOrdenesDeCompra(OrdenDeCompra[] ordenesDeCompraSeleccionadas, Remito remito) {
        Arrays.sort(ordenesDeCompraSeleccionadas, new ComparadorOrdenDeCompraPorEstado());
        // Actualizo el estado de los detalles
        Collection<RemitoDetalle> remitoDetalles = remito.getDetalles();
        for (RemitoDetalle remitoDetalle : remitoDetalles) {
            BigDecimal cantidadRecibidoRemitoAAsignar = remitoDetalle.getCantidad();
            for (OrdenDeCompra ordenDeCompra : ordenesDeCompraSeleccionadas) {
                if (!ordenDeCompra.getEstado().equalsIgnoreCase(OrdenDeCompra.ESTADO_RECIBIDO_COMPLETO)) {
                    Collection<OrdenDeCompraDetalle> ordenDeCompraDetalles = ordenDeCompra.getDetalles();
                    for (OrdenDeCompraDetalle ordenDeCompraDetalle : ordenDeCompraDetalles) {
                        if ((ordenDeCompraDetalle.getCantidadRecibida() != null && ordenDeCompraDetalle.getCantidad().compareTo(ordenDeCompraDetalle.getCantidadRecibida()) > 0)
                                || ordenDeCompraDetalle.getCantidadRecibida() == null) {
                            if (((RemitoDetalleCompra) remitoDetalle).getPresentacion().equals(ordenDeCompraDetalle.getPresentacion())) {
                                BigDecimal aAsignar;
                                if (ordenDeCompraDetalle.getCantidadRecibida() != null) {
                                    aAsignar = ordenDeCompraDetalle.getCantidad().subtract(ordenDeCompraDetalle.getCantidadRecibida());
                                } else {
                                    aAsignar = ordenDeCompraDetalle.getCantidad();
                                }
                                if (aAsignar.compareTo(cantidadRecibidoRemitoAAsignar) <= 0) {
                                    ordenDeCompraDetalle.setCantidadRecibida(ordenDeCompraDetalle.getCantidad());
                                    cantidadRecibidoRemitoAAsignar = cantidadRecibidoRemitoAAsignar.subtract(aAsignar);
                                    em.merge(ordenDeCompraDetalle);
                                    if (tieneOrdenDeProduccionAsociada(ordenDeCompra)) {
                                        actualizarEstadoAbastecimientoOrdenDeProduccion(ordenDeCompra, ordenDeCompraDetalle, aAsignar);
                                    }
                                    if (cantidadRecibidoRemitoAAsignar.compareTo(BigDecimal.ZERO) == 0) {
                                        break;
                                    }
                                } else {
                                    if (ordenDeCompraDetalle.getCantidadRecibida() != null) {
                                        ordenDeCompraDetalle.setCantidadRecibida(cantidadRecibidoRemitoAAsignar.add(ordenDeCompraDetalle.getCantidadRecibida()));
                                    } else {
                                        ordenDeCompraDetalle.setCantidadRecibida(cantidadRecibidoRemitoAAsignar);
                                    }
                                    em.merge(ordenDeCompraDetalle);
                                    if (tieneOrdenDeProduccionAsociada(ordenDeCompra)) {
                                        actualizarEstadoAbastecimientoOrdenDeProduccion(ordenDeCompra, ordenDeCompraDetalle, cantidadRecibidoRemitoAAsignar);
                                    }
                                    cantidadRecibidoRemitoAAsignar = BigDecimal.ZERO;
                                    break;
                                }
                            }
                        }
                    }
                }
                if (cantidadRecibidoRemitoAAsignar.compareTo(BigDecimal.ZERO) == 0) {
                    break;
                }
            }
        }
        // Actualizo el estado de las ordenes de compra si han cambiado
        for (OrdenDeCompra ordenDeCompra : ordenesDeCompraSeleccionadas) {
            if (!ordenDeCompra.getEstado().equalsIgnoreCase(OrdenDeCompra.ESTADO_RECIBIDO_COMPLETO)) {
                Collection<OrdenDeCompraDetalle> ordenDeCompraDetalles = ordenDeCompra.getDetalles();
                // Prueba Facturado Completo
                Boolean esCompleto = true;
                for (OrdenDeCompraDetalle ordenDeCompraDetalle : ordenDeCompraDetalles) {
                    if ((ordenDeCompraDetalle.getCantidadRecibida() != null && ordenDeCompraDetalle.getCantidadRecibida().compareTo(ordenDeCompraDetalle.getCantidad()) < 0)
                            || ordenDeCompraDetalle.getCantidadRecibida() == null) {
                        esCompleto = false;
                        break;
                    }
                }
                if (!esCompleto) {
                    if (ordenDeCompra.getEstado().equalsIgnoreCase(OrdenDeCompra.ESTADO_NO_RECIBIDO)) {
                        // Es NO_RECIBIDO, el parcial no se volvió completo, así que no cambia su estado
                        // Prueba NO_RECIBIDO a parcial
                        Boolean esParcial = false;
                        for (OrdenDeCompraDetalle ordenDeCompraDetalle : ordenDeCompraDetalles) {
                            if (ordenDeCompraDetalle.getCantidadRecibida() != null && ordenDeCompraDetalle.getCantidadRecibida().compareTo(BigDecimal.ZERO) > 0) {
                                esParcial = true;
                                break;
                            }
                        }
                        if (esParcial) {
                            // Se recibió parcialmente
                            ordenDeCompra.setEstado(OrdenDeCompra.ESTADO_RECIBIDO_PARCIAL);
                            em.merge(ordenDeCompra);
                        }
                    }
                } else {
                    // Se recibió todo
                    ordenDeCompra.setEstado(OrdenDeCompra.ESTADO_RECIBIDO_COMPLETO);
                    em.merge(ordenDeCompra);
                }
            }
        }
    }

    private List<ComprobanteRelaciones> devolverRelacionesOrdenDeCompra(OrdenDeCompra ordenDeCompra) {
        return em.createQuery("select cr from ComprobanteRelaciones cr where cr.ordenDeCompra = :ordenDeCompra").setParameter("ordenDeCompra", ordenDeCompra).getResultList();
    }

    private Boolean tieneOrdenDeProduccionAsociada(OrdenDeCompra ordenDeCompra) {
        List<ComprobanteRelaciones> relacionesOrdenDeCompra = devolverRelacionesOrdenDeCompra(ordenDeCompra);
        if (relacionesOrdenDeCompra != null && relacionesOrdenDeCompra.size() > 0) {
            for (ComprobanteRelaciones comprobanteRelaciones : relacionesOrdenDeCompra) {
                if (comprobanteRelaciones.getOrdenDeProduccion() != null) {
                    return true;
                }
            }
        }
        return false;
    }

    private void actualizarEstadoAbastecimientoOrdenDeProduccion(OrdenDeCompra ordenDeCompra, OrdenDeCompraDetalle ordenDeCompraDetalle, BigDecimal aAsignar) {
        VistaOrdenDeProduccion vistaOrdenDeProduccion = (VistaOrdenDeProduccion) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaOrdenDeProduccion");
        if (vistaOrdenDeProduccion == null) {
            //FacesContext.getCurrentInstance().getELContext().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "vistaOrdenDeProduccion");
            vistaOrdenDeProduccion = new VistaOrdenDeProduccion();
            vistaOrdenDeProduccion.instanciarGestor();
            vistaOrdenDeProduccion = (VistaOrdenDeProduccion) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("vistaOrdenDeProduccion", vistaOrdenDeProduccion);
            vistaOrdenDeProduccion = (VistaOrdenDeProduccion) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaOrdenDeProduccion");
        }
        vistaOrdenDeProduccion.actualizarEstadoAbastecimientoOrdenDeProduccion(ordenDeCompra, ordenDeCompraDetalle, aAsignar, devolverRelacionesOrdenDeCompra(ordenDeCompra));
    }
}
