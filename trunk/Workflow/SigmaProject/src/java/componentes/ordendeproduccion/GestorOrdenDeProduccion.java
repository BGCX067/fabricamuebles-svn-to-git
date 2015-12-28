/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package componentes.ordendeproduccion;

import entidades.*;
import java.math.BigDecimal;
import java.util.*;
import javax.ejb.Stateful;
import javax.persistence.*;

/**
 *
 * @author Zaba
 */
@Stateful
public class GestorOrdenDeProduccion {
    @PersistenceContext
    private EntityManager em;

    public Collection<OrdenDeProduccion> devolverOrdenesDeProduccionPlanificadas(Fabrica fabrica) {
        return em.createQuery("select op from OrdenDeProduccion op where op.estado = :ESTADO_PLANIFICADA and op.fabrica = :fabrica order by op.fechaInicioEstimada").setParameter("ESTADO_PLANIFICADA", OrdenDeProduccion.ESTADO_PLANIFICADA).setParameter("fabrica", fabrica).getResultList();
    }

    public void guardarOrdenDeProduccion(OrdenDeProduccion ordenDeProduccion) {
        em.persist(ordenDeProduccion);
    }

    public OrdenDeProduccion actualizarOrdenDeProduccion(OrdenDeProduccion ordenDeProduccion) {
        return em.merge(ordenDeProduccion);
    }

    public List<OrdenDeProduccion> devolverOrdenesDeProduccion() {
        return em.createQuery("select op from OrdenDeProduccion op").getResultList();
    }

    public List<OrdenDeProduccion> devolverOrdenesDeProduccionExterna() {
        return em.createQuery("select op from OrdenDeProduccion op where op.fabrica.interna = false").getResultList();
    }

    public void actualizarEstadoAbastecimientoExternoOrdenDeProduccion(OrdenDeProduccion ordenDeProduccion, Remito remito) {
        Collection<RequerimientoMaterialOrdenDeProduccion> requerimientoMateriales = ordenDeProduccion.getRequerimientoMateriales();
        Collection<RemitoDetalle> remitoDetalles = remito.getDetalles();
        Iterator<RequerimientoMaterialOrdenDeProduccion> iteratorRequerimientoMateriales = requerimientoMateriales.iterator();
        while (iteratorRequerimientoMateriales.hasNext()) {
            RequerimientoMaterialOrdenDeProduccionExterna requerimientoMaterial = (RequerimientoMaterialOrdenDeProduccionExterna) iteratorRequerimientoMateriales.next();
            Iterator<RemitoDetalle> iteratorDetalles = remitoDetalles.iterator();
            while (iteratorDetalles.hasNext()) {
                RemitoDetalleProduccion remitoDetalle = (RemitoDetalleProduccion) iteratorDetalles.next();
                if (requerimientoMaterial.getItemRequerible().equals(remitoDetalle.getFamilia())) {
                    BigDecimal cantidadEntregada = requerimientoMaterial.getCantidadEntregada();
                    if (cantidadEntregada != null) {
                        requerimientoMaterial.setCantidadEntregada(cantidadEntregada.add(remitoDetalle.getCantidad()));
                    } else {
                        requerimientoMaterial.setCantidadEntregada(remitoDetalle.getCantidad());
                    }
                    em.merge(requerimientoMaterial);
                }
            }
        }
        Boolean setearEstadoAbastecida = true;
        Boolean setearEstadoNoAbastecida = true;
        iteratorRequerimientoMateriales = requerimientoMateriales.iterator();
        while (iteratorRequerimientoMateriales.hasNext()) {
            RequerimientoMaterialOrdenDeProduccionExterna requerimientoMaterial = (RequerimientoMaterialOrdenDeProduccionExterna) iteratorRequerimientoMateriales.next();
            if (!requerimientoMaterial.getCantidadEntregada().equals(requerimientoMaterial.getCantidadRequerida())) {
                setearEstadoAbastecida = false;
            }
            if (requerimientoMaterial.getCantidadEntregada().compareTo(BigDecimal.ZERO) > 0) {
                setearEstadoNoAbastecida = false;
            }
        }
        if (setearEstadoAbastecida) {
            ordenDeProduccion.setEstado(OrdenDeProduccion.ESTADO_ABASTECIDA);
        } else if (setearEstadoNoAbastecida) {
            ordenDeProduccion.setEstado(OrdenDeProduccion.ESTADO_PLANIFICADA);
        } else {
            ordenDeProduccion.setEstado(OrdenDeProduccion.ESTADO_EN_ABASTECIMIENTO);
        }
        em.merge(ordenDeProduccion);
    }

    public void actualizarEstadoPedidoRequerimientoMateriales(OrdenDeProduccion ordenDeProduccion, OrdenDeCompra ordenDeCompra) {
        Collection<RequerimientoMaterialOrdenDeProduccion> requerimientoMateriales = ordenDeProduccion.getRequerimientoMateriales();
        Collection<OrdenDeCompraDetalle> ordenDeCompraDetalles = ordenDeCompra.getDetalles();
        Iterator<RequerimientoMaterialOrdenDeProduccion> iteratorRequerimientoMateriales = requerimientoMateriales.iterator();
        while (iteratorRequerimientoMateriales.hasNext()) {
            RequerimientoMaterialOrdenDeProduccion requerimientoMaterial = iteratorRequerimientoMateriales.next();
            Iterator<OrdenDeCompraDetalle> iteratorDetalles = ordenDeCompraDetalles.iterator();
            while (iteratorDetalles.hasNext()) {
                OrdenDeCompraDetalle ordenDeCompraDetalle = iteratorDetalles.next();
                if (requerimientoMaterial.getItemRequerible().equals(ordenDeCompraDetalle.getPresentacion().getArticulo())) {
                    if (ordenDeCompraDetalle.getCantidad().compareTo(requerimientoMaterial.cantidadAPedir()) > 0) {
                        requerimientoMaterial.setCantidadPedida(requerimientoMaterial.cantidadAPedir());
                    } else {
                        requerimientoMaterial.setCantidadPedida(ordenDeCompraDetalle.getCantidad());
                    }
                    em.merge(requerimientoMaterial);
                }
            }
        }
        Boolean setearEstadoAbastecida = true;
        Boolean setearEstadoNoAbastecida = true;
        iteratorRequerimientoMateriales = requerimientoMateriales.iterator();
        while (iteratorRequerimientoMateriales.hasNext()) {
            RequerimientoMaterialOrdenDeProduccion requerimientoMaterial = iteratorRequerimientoMateriales.next();
            if (!requerimientoMaterial.getCantidadPedida().equals(requerimientoMaterial.getCantidadRequerida())) {
                setearEstadoAbastecida = false;
            }
            if (requerimientoMaterial.getCantidadPedida().compareTo(BigDecimal.ZERO) > 0) {
                setearEstadoNoAbastecida = false;
            }
        }
        if (setearEstadoAbastecida) {
            ordenDeProduccion.setEstado(OrdenDeProduccion.ESTADO_ABASTECIDA);
        } else if (setearEstadoNoAbastecida) {
            ordenDeProduccion.setEstado(OrdenDeProduccion.ESTADO_PLANIFICADA);
        } else {
            ordenDeProduccion.setEstado(OrdenDeProduccion.ESTADO_EN_ABASTECIMIENTO);
        }
        em.merge(ordenDeProduccion);
    }

    public void actualizarEstadoAbastecimientoOrdenDeProduccion(OrdenDeCompra ordenDeCompra, OrdenDeCompraDetalle ordenDeCompraDetalle, BigDecimal aAsignar, Collection<ComprobanteRelaciones> relacionesOrdenDeCompra) {
        // Actualizo la cantidadAbastecida de la orden de producción planificada
        if (relacionesOrdenDeCompra != null && relacionesOrdenDeCompra.size() > 0) {
            for (ComprobanteRelaciones comprobanteRelaciones : relacionesOrdenDeCompra) {
                if (comprobanteRelaciones.getOrdenDeProduccion() != null) {
                    Collection<RequerimientoMaterialOrdenDeProduccion> requerimientoMateriales = comprobanteRelaciones.getOrdenDeProduccion().getRequerimientoMateriales();
                    for (RequerimientoMaterialOrdenDeProduccion requerimientoMaterial : requerimientoMateriales) {
                        if ((requerimientoMaterial.getCantidadReservada() != null && requerimientoMaterial.getCantidadReservada().compareTo(requerimientoMaterial.getCantidadPedida()) < 0)
                                || requerimientoMaterial.getCantidadReservada() == null) {
                            if (requerimientoMaterial.getItemRequerible().equals(ordenDeCompraDetalle.getPresentacion().getArticulo())) {
                                if (requerimientoMaterial.getCantidadPedida().compareTo(aAsignar) <= 0) {
                                    requerimientoMaterial.agregarCantidadReservada(requerimientoMaterial.getCantidadPedida());
                                    requerimientoMaterial.setCantidadPedida(BigDecimal.ZERO);
                                    aAsignar = aAsignar.subtract(requerimientoMaterial.getCantidadPedida());
                                    em.merge(requerimientoMaterial);
                                    if (aAsignar.compareTo(BigDecimal.ZERO) == 0) {
                                        break;
                                    }
                                } else {
                                    requerimientoMaterial.quitarCantidadReservada(aAsignar);
                                    requerimientoMaterial.setCantidadPedida(requerimientoMaterial.getCantidadPedida().subtract(aAsignar));
                                    aAsignar = BigDecimal.ZERO;
                                    em.merge(requerimientoMaterial);
                                    break;
                                }
                            }
                        }
                    }
                }
                if (aAsignar.compareTo(BigDecimal.ZERO) == 0) {
                    break;
                }
            }
        }
        // Ahora me fijo si debo cambiar el estado de abastecimiento de la orden de producción
        if (relacionesOrdenDeCompra != null && relacionesOrdenDeCompra.size() > 0) {
            for (ComprobanteRelaciones comprobanteRelaciones : relacionesOrdenDeCompra) {
                if (comprobanteRelaciones.getOrdenDeProduccion() != null && !comprobanteRelaciones.getOrdenDeProduccion().getEstado().equalsIgnoreCase(OrdenDeProduccion.ESTADO_ABASTECIDA)) {
                    Collection<RequerimientoMaterialOrdenDeProduccion> requerimientoMateriales = comprobanteRelaciones.getOrdenDeProduccion().getRequerimientoMateriales();
                    // Prueba completo
                    Boolean esCompleto = true;
                    for (RequerimientoMaterialOrdenDeProduccion requerimientoMaterial : requerimientoMateriales) {
                        if ((requerimientoMaterial.getCantidadReservada() != null && requerimientoMaterial.getCantidadReservada().compareTo(requerimientoMaterial.getCantidadRequerida()) < 0)
                                || requerimientoMaterial.getCantidadReservada() == null) {
                            esCompleto = false;
                            break;
                        }
                    }
                    if (!esCompleto) {
                        if (comprobanteRelaciones.getOrdenDeProduccion().getEstado().equalsIgnoreCase(OrdenDeProduccion.ESTADO_PLANIFICADA)) {
                            // No se volvió completo, así que si es parcial no cambiará de estado.
                            // Prueba NO_INICIADO a parcial
                            Boolean esParcial = false;
                            for (RequerimientoMaterialOrdenDeProduccion requerimientoMaterial : requerimientoMateriales) {
                                if (requerimientoMaterial.getCantidadReservada() != null && requerimientoMaterial.getCantidadReservada().compareTo(BigDecimal.ZERO) > 0) {
                                    esParcial = true;
                                    break;
                                }
                            }
                            if (esParcial) {
                                // Se abasteció parcialmente
                                comprobanteRelaciones.getOrdenDeProduccion().setEstado(OrdenDeProduccion.ESTADO_EN_ABASTECIMIENTO);
                                em.merge(comprobanteRelaciones.getOrdenDeProduccion());
                            }
                        }
                    } else {
                        // Se abasteció por completo
                        comprobanteRelaciones.getOrdenDeProduccion().setEstado(OrdenDeProduccion.ESTADO_ABASTECIDA);
                        em.merge(comprobanteRelaciones.getOrdenDeProduccion());
                    }
                }
            }
        }
    }

    public OrdenDeProduccion actualizarOrdenDeProduccionDesdeBD(OrdenDeProduccion ordenDeProduccion) {
        return em.find(OrdenDeProduccion.class, ordenDeProduccion.getId());
    }

    public List<OrdenDeProduccion> devolverOrdenesDeProduccionNoFinalizadas() {
        return em.createQuery("select op from OrdenDeProduccion op where op.estado <> :ESTADO_FINALIZADA").setParameter("ESTADO_FINALIZADA", OrdenDeProduccion.ESTADO_FINALIZADA).getResultList();
    }

    public List<OrdenDeProduccion> devolverOrdenesDeProduccionNoFinalizadas(Fabrica fabrica) {
        return em.createQuery("select op from OrdenDeProduccion op where op.estado <> :ESTADO_FINALIZADA and op.fabrica = :fabrica").setParameter("ESTADO_FINALIZADA", OrdenDeProduccion.ESTADO_FINALIZADA).setParameter("fabrica", fabrica).getResultList();
    }
}
