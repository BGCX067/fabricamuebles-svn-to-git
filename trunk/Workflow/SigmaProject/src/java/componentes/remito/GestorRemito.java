/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes.remito;

import entidades.*;
import entidades.ordenamiento.ComparadorRemitoPorEstado;
import java.math.BigDecimal;
import java.util.*;
import javax.ejb.Stateful;
import javax.persistence.*;

/**
 *
 * @author Zaba
 */
@Stateful
public class GestorRemito {

    @PersistenceContext
    private EntityManager em;
    private Remito remito;

    public Collection<Remito> devolverRemitosCompra() {
        return em.createQuery("select rc from Remito rc where rc.tipo = 'Compra'").getResultList();
    }


    public void reiniciar() {
    }

    public Collection<Remito> devolverRemitosCompra(Proveedor proveedor) {
        return em.createQuery("select rc from Remito rc where rc.tipo = 'Compra' and rc.entidad = :proveedor").setParameter("proveedor", proveedor).getResultList();
    }
    public Collection<Remito> devolverRemitosVenta(){
        return em.createQuery("select rv from Remito rv where rv.tipo = 'Venta'").getResultList();
    }
    public Collection<Remito> devolverRemitosVenta(Cliente cliente){
        return em.createNamedQuery("select rv from Remito rv where rv.tipo = 'Venta' and rv.entidad = :cliente").setParameter("cliente", cliente).getResultList();
    }

    /**
     * Actualización de los remitos pasados por parámetro, según la factura
     * @param remitosSeleccionados los remitos seleccionados como referencia
     * @param facturaNoConfirmada la factura recién generada
     */
    public void actualizarEstadoRemitosCompra(Remito[] remitosSeleccionados, Factura facturaNoConfirmada) {
        // Ordeno los remito según si estado, desde los facturados_completo hasta los no_facturados
        // de tal forma que se completen primero los más próximos a completarse (los parciales)
        Arrays.sort(remitosSeleccionados, new ComparadorRemitoPorEstado());
        Collection<FacturaDetalle> facturaDetalles = facturaNoConfirmada.getDetalles();
        // Actualizo la cantidad facturada de cada detalle remito, empezando por cada detalle factura
        for (FacturaDetalle facturaDetalle : facturaDetalles) {
            BigDecimal cantidadFacturaDetallePorAsignar = facturaDetalle.getCantidad();
            for (Remito r : remitosSeleccionados) {
                if (!r.getEstado().equalsIgnoreCase(Remito.ESTADO_FACTURADO_COMPLETO)) {
                    Collection<RemitoDetalle> remitoDetalles = r.getDetalles();
                    for (RemitoDetalle rd : remitoDetalles) {
                        RemitoDetalleCompra remitoDetalleCompra = (RemitoDetalleCompra) rd;
                        if ((remitoDetalleCompra.getCantidadFacturada() != null && remitoDetalleCompra.getCantidad().compareTo(remitoDetalleCompra.getCantidadFacturada()) > 0)
                                || remitoDetalleCompra.getCantidadFacturada() == null) {
                            if (remitoDetalleCompra.getPresentacion().equals(((FacturaDetalleCompra) facturaDetalle).getPresentacion())) {
                                BigDecimal aAsignar;
                                if (remitoDetalleCompra.getCantidadFacturada() != null) {
                                    aAsignar = remitoDetalleCompra.getCantidad().subtract(remitoDetalleCompra.getCantidadFacturada());
                                } else {
                                    aAsignar = remitoDetalleCompra.getCantidad();
                                }
                                if (aAsignar.compareTo(cantidadFacturaDetallePorAsignar) <= 0) {
                                    cantidadFacturaDetallePorAsignar = cantidadFacturaDetallePorAsignar.subtract(aAsignar);
                                    remitoDetalleCompra.setCantidadFacturada(remitoDetalleCompra.getCantidad()); // Se facturó todo el remitoDetalle
                                    em.merge(remitoDetalleCompra);
                                    if (cantidadFacturaDetallePorAsignar.compareTo(BigDecimal.ZERO) == 0) {
                                        break;
                                    }
                                } else {
                                    if (remitoDetalleCompra.getCantidadFacturada() != null) {
                                        remitoDetalleCompra.setCantidadFacturada(remitoDetalleCompra.getCantidadFacturada().add(cantidadFacturaDetallePorAsignar));
                                    } else {
                                        remitoDetalleCompra.setCantidadFacturada(cantidadFacturaDetallePorAsignar);
                                    }
                                    em.merge(remitoDetalleCompra);
                                    cantidadFacturaDetallePorAsignar = BigDecimal.ZERO; // Ya se asignó todo el facturaDetalle
                                    break;
                                }
                            }
                        }
                    }
                }
                if (cantidadFacturaDetallePorAsignar.compareTo(BigDecimal.ZERO) == 0) {
                    break;
                }
            }
        }
        // Una vez tengo los detalles remitos actualizados, veo si hace falta actualizar el estado de cada remito
        for (Remito r : remitosSeleccionados) {
            
            if (!r.getEstado().equalsIgnoreCase(Remito.ESTADO_FACTURADO_COMPLETO)) {
                Collection<RemitoDetalle> detalles = r.getDetalles();
                // Prueba Facturado Completo
                Boolean esCompleto = true;
                for (RemitoDetalle rd : detalles) {
                    RemitoDetalleCompra remitoDetalleCompra = (RemitoDetalleCompra) rd;
                    if ((remitoDetalleCompra.getCantidadFacturada() != null && remitoDetalleCompra.getCantidadFacturada().compareTo(remitoDetalleCompra.getCantidad()) < 0)
                            || remitoDetalleCompra.getCantidadFacturada() == null) {
                        esCompleto = false;
                        break;
                    }
                }
                if (!esCompleto) {
                    if (r.getEstado().equalsIgnoreCase(Remito.ESTADO_NO_FACTURADO)) {
                        // Es NO_FACTURADO, el parcial no se volvió completo, así que no cambia su estado
                        // Prueba NO_FACTURADO a parcial
                        Boolean esParcial = false;
                        for (RemitoDetalle rd : detalles) {
                            RemitoDetalleCompra remitoDetalleCompra = (RemitoDetalleCompra) rd;
                            if (remitoDetalleCompra.getCantidadFacturada() != null && remitoDetalleCompra.getCantidadFacturada().compareTo(BigDecimal.ZERO) > 0) {
                                esParcial = true;
                                break;
                            }
                        }
                        if (esParcial) {
                            // Se facturó parcialmente
                            r.setEstado(Remito.ESTADO_FACTURADO_PARCIAL);
                            em.merge(r);
                        }
                    }
                } else {
                    // Se facturó todo
                    r.setEstado(Remito.ESTADO_FACTURADO_COMPLETO);
                    em.merge(r);
                }
            }
        }
    }

    public Collection<Remito> devolverRemitosCompraAFacturar(Proveedor proveedor) {
        return em.createQuery("select rc from Remito rc where rc.tipo = 'Compra' and rc.entidad = :proveedor and rc.estado <> :estadoFacturado").setParameter("proveedor", proveedor).setParameter("estadoFacturado", Remito.ESTADO_FACTURADO_COMPLETO).getResultList();
    }

    public void tomarDetallesRemito(Collection<RemitoDetalle> remitoDetallesAgregados) {
        this.remito.setDetalles(remitoDetallesAgregados);
        this.remito.setNumero(devolverSiguienteNumeroParaRemitoCompra());
        this.remito.setFechaEmision(new Date());
    }

    public void crearRemito(Entidad entidad) {
        this.remito = new Remito();
        this.remito.setEntidad(entidad);
    }

    public void copiarDetallesOrdenDeCompraARemito(OrdenDeCompra ordenDeCompra) {
        Collection<RemitoDetalle> detalles;
        if (this.remito.getDetalles() == null) {
            detalles = new LinkedList<RemitoDetalle>();
        } else {
            detalles = this.remito.getDetalles();
        }
        Collection<OrdenDeCompraDetalle> ordenDeCompraDetalles = ordenDeCompra.getDetalles();
        for (OrdenDeCompraDetalle detalle : ordenDeCompraDetalles) {
            RemitoDetalle remitoDetalle = new RemitoDetalleCompra();
            ((RemitoDetalleCompra) remitoDetalle).setPresentacion(detalle.getPresentacion());
            if (detalle.getCantidadRecibida() != null) {
                remitoDetalle.setCantidad(detalle.getCantidad().subtract(detalle.getCantidadRecibida()));
            } else {
                remitoDetalle.setCantidad(detalle.getCantidad());
            }
            detalles.add(remitoDetalle);
        }
        this.remito.setDetalles(detalles);
    }

    public void copiarDetallesFacturaARemito(Factura factura) {
        Collection<RemitoDetalle> detalles;
        if (this.remito.getDetalles() == null) {
            detalles = new LinkedList<RemitoDetalle>();
        } else {
            detalles = this.remito.getDetalles();
        }
        Collection<FacturaDetalle> facturaDetalles = factura.getDetalles();
        for (FacturaDetalle detalle : facturaDetalles) {
            FacturaDetalleVenta detalleVenta = (FacturaDetalleVenta) detalle;
            RemitoDetalle remitoDetalle = new RemitoDetalleVenta();
            ((RemitoDetalleVenta) remitoDetalle).setMuebleModelo(detalleVenta.getMuebleModelo());
            remitoDetalle.setCantidad(detalle.getCantidad());
            detalles.add(remitoDetalle);
        }
        this.remito.setDetalles(detalles);
    }

    public void copiarRequerimientosMaterialesARemito(OrdenDeProduccion ordenDeProduccion) {
        Collection<RemitoDetalle> detalles;
        if (this.remito.getDetalles() == null) {
            detalles = new LinkedList<RemitoDetalle>();
        } else {
            detalles = this.remito.getDetalles();
        }
        Collection<RequerimientoMaterialOrdenDeProduccion> requerimientoMateriales = ordenDeProduccion.getRequerimientoMateriales();
        for (RequerimientoMaterialOrdenDeProduccion requerimiento : requerimientoMateriales) {
            RequerimientoMaterialOrdenDeProduccionExterna requerimientoMaterialExterno = (RequerimientoMaterialOrdenDeProduccionExterna) requerimiento;
            RemitoDetalle remitoDetalle = new RemitoDetalleProduccion();
            // TODO preguntar al alfre si se mandan los envases enteros o se pueden mandar por unidad de produccion
            ((RemitoDetalleProduccion) remitoDetalle).setFamilia(((Articulo) requerimientoMaterialExterno.getItemRequerible()).getFamilia());
            if (requerimientoMaterialExterno.getCantidadEntregada() != null) {
                remitoDetalle.setCantidad(requerimientoMaterialExterno.getCantidadRequerida().subtract(requerimientoMaterialExterno.getCantidadEntregada()));
            } else {
                remitoDetalle.setCantidad(requerimientoMaterialExterno.getCantidadRequerida());
            }
            if (remitoDetalle.getCantidad().compareTo(((RemitoDetalleProduccion)remitoDetalle).getFamilia().getStockActual()) > 0) {
                remitoDetalle.setCantidad(((RemitoDetalleProduccion)remitoDetalle).getFamilia().getStockActual());
            }
            if (remitoDetalle.getCantidad().compareTo(BigDecimal.ZERO) > 0) {
                detalles.add(remitoDetalle);
            }
        }
        this.remito.setDetalles(detalles);
    }

    public Remito getRemitoNoConfirmado() {
        return this.remito;
    }

    public void tomarConfirmacionDatosRemito() {
        guardarRemito();
        actualizarStock();
    }

    private void guardarRemito() {
        Boolean tipoSeteado = false;
        for (RemitoDetalle detalle : this.remito.getDetalles()) {
            if (detalle instanceof RemitoDetalleCompra) {
                if (!tipoSeteado) {
                    tipoSeteado = true;
                    this.remito.setTipoCompra();
                }
                ((RemitoDetalleCompra)detalle).setPresentacion(em.merge(((RemitoDetalleCompra) detalle).getPresentacion()));
            } else if (detalle instanceof RemitoDetalleVenta) {
                if (!tipoSeteado) {
                    tipoSeteado = true;
                    this.remito.setTipoVenta();
                }
                ((RemitoDetalleVenta)detalle).setMuebleModelo(em.merge(((RemitoDetalleVenta) detalle).getMuebleModelo()));
            } else if (detalle instanceof RemitoDetalleProduccion) {
                if (!tipoSeteado) {
                    tipoSeteado = true;
                    this.remito.setTipoProduccion();
                }
                ((RemitoDetalleProduccion)detalle).setFamilia(em.merge(((RemitoDetalleProduccion) detalle).getFamilia()));
            }
        }
        
        em.persist(this.remito);
    }

    private void actualizarStock() {
        Collection<RemitoDetalle> remitoDetalles = this.remito.getDetalles();
        for (RemitoDetalle remitoDetalle : remitoDetalles) {
            if (this.remito.getTipo().equalsIgnoreCase(Remito.TIPO_COMPRA)) {
                Familia familia = ((RemitoDetalleCompra) remitoDetalle).getPresentacion().getArticulo().getFamilia();
                familia.incrementarStockActual(remitoDetalle.getCantidad());
                em.merge(familia);
                // em.createQuery("update Articulo a set a.stockActual = :stockActual where a = :articulo").setParameter("stockActual", articulo.getStockActual()).setParameter("articulo", articulo).executeUpdate();
            } else if (this.remito.getTipo().equalsIgnoreCase(Remito.TIPO_VENTA)) {
                MuebleModelo muebleModelo = ((RemitoDetalleVenta)remitoDetalle).getMuebleModelo();
                BigDecimal stockActual = muebleModelo.getStockActual();
                if (stockActual != null) {
                    muebleModelo.setStockActual(stockActual.subtract(remitoDetalle.getCantidad()));
                } else {
                    muebleModelo.setStockActual(BigDecimal.ZERO.subtract(remitoDetalle.getCantidad())); // TODO corregir, agregar validación de que no se tiene stock disponible
                }
                em.merge(muebleModelo);
            } else if (this.remito.getTipo().equalsIgnoreCase(Remito.TIPO_PRODUCCION)) {
                ((RemitoDetalleProduccion) remitoDetalle).getFamilia().consumirReserva(remitoDetalle.getCantidad());
                em.merge(((RemitoDetalleProduccion) remitoDetalle).getFamilia());
            }
        }
    }

    public Collection<Remito> devolverRemitosCompraAFacturar() {
        return em.createQuery("select rc from Remito rc where rc.tipo = 'Compra' and rc.estado <> :estadoFacturado").setParameter("estadoFacturado", Remito.ESTADO_FACTURADO_COMPLETO).getResultList();
    }

    private String devolverSiguienteNumeroParaRemitoCompra() {
        List<String> sNumeros = em.createQuery("select r.numero from Remito r where r.tipo = 'Compra'").getResultList();
        Integer ret = 0;
        for (String numero : sNumeros) {
            if (Integer.parseInt(numero) > ret) {
                ret = Integer.parseInt(numero);
            }
        }
        ret++;
        return ret.toString();
    }

}
