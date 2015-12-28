/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Fetch;

/**
 *
 * @author Zaba
 */
@Entity
public class OrdenDeProduccion implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaInicioEstimada;
    @NotNull
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaFinEstimada;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaInicioReal;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaFinReal;
    @ManyToOne
    @JoinColumn(name = "idMuebleTipo")
    @NotNull
    private MuebleTipo muebleTipo;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "idOrdenDeProduccion")
    @Fetch(value = FetchMode.SELECT)
    private Collection<OrdenDeProduccionDetalle> detalles;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "idOrdenDeProduccion")
    @Fetch(value = FetchMode.SELECT)
    private Collection<RequerimientoMaterialOrdenDeProduccion> requerimientoMateriales;
    private Integer prioridad;
    @ManyToOne
    @JoinColumn(name = "idFabrica")
    @NotNull
    private Fabrica fabrica;
    private String estado = ESTADO_PLANIFICADA;
    public final static String ESTADO_PLANIFICADA = "PLANIFICADA";
    public final static String ESTADO_EN_PRODUCCION = "EN PRODUCCIÓN";
    public final static String ESTADO_FINALIZADA = "FINALIZADA";
    public final static String ESTADO_EN_ABASTECIMIENTO = "EN ABASTECIMIENTO";
    public final static String ESTADO_ABASTECIDA = "ABASTECIDA";
    public final static String ESTADO_CANCELADA = "CANCELADA";

    public Boolean esAbastecida() {
        return this.estado.equalsIgnoreCase(ESTADO_ABASTECIDA);
    }

    public Boolean quedaAlgoPorPedir() {
        if (this.requerimientoMateriales == null ||
                (this.requerimientoMateriales != null && this.requerimientoMateriales.isEmpty())) {
            throw new AssertionError("Se llamó a quedaAlgoPorPedir cuando la Orden de Producción no tiene req. materiales registrados");
        }
        Boolean b = false;
        for (RequerimientoMaterialOrdenDeProduccion rm : requerimientoMateriales) {
            if (rm.cantidadAPedir().compareTo(BigDecimal.ZERO) > 0) {
                b = true;
                break;
            }
        }
        return b;
    }

    public Integer getId() {
        return id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Collection<OrdenDeProduccionDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(Collection<OrdenDeProduccionDetalle> detalles) {
        this.detalles = detalles;
    }

    public Collection<RequerimientoMaterialOrdenDeProduccion> getRequerimientoMateriales() {
        return requerimientoMateriales;
    }

    public void setRequerimientoMateriales(Collection<RequerimientoMaterialOrdenDeProduccion> requerimientoMateriales) {
        this.requerimientoMateriales = requerimientoMateriales;
    }

    public Date getFechaFinEstimada() {
        return fechaFinEstimada;
    }

    public void setFechaFinEstimada(Date fechaFinEstimada) {
        this.fechaFinEstimada = fechaFinEstimada;
    }

    public Date getFechaFinReal() {
        return fechaFinReal;
    }

    public void setFechaFinReal(Date fechaFinReal) {
        this.fechaFinReal = fechaFinReal;
    }

    public Date getFechaInicioEstimada() {
        return fechaInicioEstimada;
    }

    public void setFechaInicioEstimada(Date fechaInicioEstimada) {
        this.fechaInicioEstimada = fechaInicioEstimada;
    }

    public Date getFechaInicioReal() {
        return fechaInicioReal;
    }

    public void setFechaInicioReal(Date fechaInicioReal) {
        this.fechaInicioReal = fechaInicioReal;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    public Fabrica getFabrica() {
        return fabrica;
    }

    public void setFabrica(Fabrica fabrica) {
        this.fabrica = fabrica;
    }

    public MuebleTipo getMuebleTipo() {
        return muebleTipo;
    }

    public void setMuebleTipo(MuebleTipo muebleTipo) {
        this.muebleTipo = muebleTipo;
    }

    public String getTipo() {
        if (this.fabrica.getInterna()) {
            return "INTERNA";
        } else {
            return "EXTERNA";
        }
    }

    public BigDecimal getCantidadAsignada() {
        BigDecimal cantidadAsignada = BigDecimal.ZERO;
        for (OrdenDeProduccionDetalle ordenDeProduccionDetalle : detalles) {
            cantidadAsignada = cantidadAsignada.add(ordenDeProduccionDetalle.getCantidad());
        }
        return cantidadAsignada;
    }

    public String getDescripcion() {
        return new StringBuilder("Tipo: ").append(muebleTipo.getNombre()).append(". Fecha Estimada Inicio: ").append(new SimpleDateFormat("dd/MM/yyyy").format(fechaInicioEstimada)).append(". Fecha Estimada Fin: ").append(new SimpleDateFormat("dd/MM/yyyy").format(fechaFinEstimada)).append(". Cantidad: ").append(devolverCapacidadOcupada()).toString();
    }

    public Boolean tieneCapacidadPara(ArrayList<PMPDetalle> pmpDetalles) {
        BigDecimal cantidadTotalAIngresar = BigDecimal.ZERO;
        for (PMPDetalle pmpDetalle : pmpDetalles) {
            cantidadTotalAIngresar = cantidadTotalAIngresar.add(pmpDetalle.getCantidadPorAsignar());
            if (!pmpDetalle.getMuebleModelo().getMuebleTipo().equals(this.muebleTipo)) {
                throw new AssertionError("Se encontró un PMPdetalle de tipo de mueble distinto al de la orden de producción.");
            }
        }
        Integer capacidadDisponible = devolverCapacidadDisponible();
        return cantidadTotalAIngresar.intValue() <= capacidadDisponible;
    }

    public Boolean tieneCapacidadPara(Asignable asignable) {
        ArrayList<PMPDetalle> lista = new ArrayList<PMPDetalle>();
        if (asignable instanceof PedidoDetalle) {
            // Creo un PMPDetalle temporal, y despues una lista con ese para pasarselo a la funcion que recibe una lista de PMPDetalle
            PedidoDetalle pedidoDetalle = (PedidoDetalle) asignable;
            PMPDetalle pmpDetalle = new PMPDetalle();
            pmpDetalle.setMuebleModelo(pedidoDetalle.getMuebleModelo());
            pmpDetalle.setCantidadPorAsignar(pedidoDetalle.getCantidad());
            lista.add(pmpDetalle);
        } else if (asignable instanceof PMPDetalle) {
            lista.add((PMPDetalle) asignable);
        }
        return tieneCapacidadPara(lista);
    }

    public Integer devolverCapacidadDisponible() {
        Integer capacidadMaxima = fabrica.devolverCapacidadMaximaPara(muebleTipo);
        Integer capacidadOcupada = devolverCapacidadOcupada();
        return capacidadMaxima - capacidadOcupada;
    }

    public Integer devolverCapacidadOcupada() {
        Integer capacidadOcupada = 0;
        if (this.detalles != null) {
            for (OrdenDeProduccionDetalle detalle : this.detalles) {
                capacidadOcupada += detalle.getCantidad().intValue();
            }
        }
        return capacidadOcupada;
    }

    /**
     * Crea un detalle de orden de producción nuevo,
     * lo agrega a los detalles propios y lo retorna
     * @param muebleModelo
     * @return
     */
    public OrdenDeProduccionDetalle devolverDetalleMuebleModeloNuevo(MuebleModelo muebleModelo) {
        OrdenDeProduccionDetalle detalleRetorno = null;
        if (this.detalles != null) {
            for (OrdenDeProduccionDetalle ordenDeProduccionDetalle : this.detalles) {
                if (ordenDeProduccionDetalle.getMuebleModelo().equals(muebleModelo)) {
                    detalleRetorno = ordenDeProduccionDetalle;
                    break;
                }
            }
        }
        if (detalleRetorno == null) {
            detalleRetorno = new OrdenDeProduccionDetalle();
            detalleRetorno.setMuebleModelo(muebleModelo);
            if (this.detalles == null) {
                this.detalles = new ArrayList<OrdenDeProduccionDetalle>();
            }
            this.detalles.add(detalleRetorno);
        } else {
            throw new AssertionError("Se llamó a devolverDetalleMuebleModeloNuevo cuando este ya existía");
        }
        return detalleRetorno;
    }

    /**
     * Devuelve el detalle de orden de producción ya existente del mueblemodelo indicado.
     * @param muebleModelo
     * @return
     */
    public OrdenDeProduccionDetalle devolverDetalleMuebleModeloExistente(MuebleModelo muebleModelo) {
        OrdenDeProduccionDetalle detalleRetorno = null;
        if (this.detalles != null) {
            for (OrdenDeProduccionDetalle ordenDeProduccionDetalle : this.detalles) {
                if (ordenDeProduccionDetalle.getMuebleModelo().equals(muebleModelo)) {
                    detalleRetorno = ordenDeProduccionDetalle;
                    break;
                }
            }
        }
        if (detalleRetorno == null) {
            throw new AssertionError("Se llamó a devolverDetalleMuebleModeloExistente cuando este no existía");
        }
        return detalleRetorno;
    }

    public Boolean contieneDetalleMuebleModelo(MuebleModelo muebleModelo) {
        OrdenDeProduccionDetalle detalle = null;
        if (this.detalles != null) {
            for (OrdenDeProduccionDetalle ordenDeProduccionDetalle : this.detalles) {
                if (ordenDeProduccionDetalle.getMuebleModelo().equals(muebleModelo)) {
                    detalle = ordenDeProduccionDetalle;
                    break;
                }
            }
        }
        return detalle != null;
    }

    public void generarYGuardarRequerimientosMateriales() {
        HashMap<Articulo, RequerimientoMaterialOrdenDeProduccion> mapa = new HashMap<Articulo, RequerimientoMaterialOrdenDeProduccion>();
        if (this.detalles != null) {
            for (OrdenDeProduccionDetalle detalle : this.detalles) {
                Collection<RequerimientoMaterial> rmDetalle = detalle.getMuebleModelo().devolverRequerimientosMaterialesAComprar(detalle.getCantidad());
                if (rmDetalle != null && rmDetalle.size() > 0) {
                    for (RequerimientoMaterial rm : rmDetalle) {
                        if (!mapa.containsKey((Articulo) rm.getItemRequerible())) {
                            RequerimientoMaterialOrdenDeProduccion rmm = null;
                            if (fabrica.getInterna()) {
                                rmm = new RequerimientoMaterialOrdenDeProduccion();
                            } else {
                                rmm = new RequerimientoMaterialOrdenDeProduccionExterna();
                            }
                            rmm.setItemRequerible((Articulo) rm.getItemRequerible());
                            mapa.put((Articulo) rm.getItemRequerible(), rmm);
                        }
                        RequerimientoMaterial rmm = mapa.get((Articulo) rm.getItemRequerible());
                        rmm.agregarCantidadRequerida(rm.getCantidadRequerida());
                    }
                }
            }
            Collection<RequerimientoMaterialOrdenDeProduccion> listaRequerimientosMateriales = new ArrayList<RequerimientoMaterialOrdenDeProduccion>();
            for (RequerimientoMaterialOrdenDeProduccion req : mapa.values()) {
                listaRequerimientosMateriales.add(req);
            }
            Iterator<RequerimientoMaterialOrdenDeProduccion> iterator = listaRequerimientosMateriales.iterator();
            while (iterator.hasNext()) {
                RequerimientoMaterialOrdenDeProduccion requerimientoMaterialOrdenDeProduccion = iterator.next();
                Articulo articulo = (Articulo) requerimientoMaterialOrdenDeProduccion.getItemRequerible();
                if (requerimientoMaterialOrdenDeProduccion.getCantidadRequerida().compareTo(articulo.getFamilia().getStockDisponible()) <= 0) {
                    articulo.getFamilia().reservar(requerimientoMaterialOrdenDeProduccion.getCantidadRequerida()); // reservo todo, por lo que ya no es un requerimiento a comprar
                    requerimientoMaterialOrdenDeProduccion.agregarCantidadReservada(requerimientoMaterialOrdenDeProduccion.getCantidadRequerida());
                    //iterator.remove(); // quito el requerimiento
                } else {
                    BigDecimal cantidadAReservar = articulo.getFamilia().getStockDisponible();
                    articulo.getFamilia().reservar(cantidadAReservar); // reservo todo
                    requerimientoMaterialOrdenDeProduccion.agregarCantidadReservada(cantidadAReservar);
                }
            }
            this.requerimientoMateriales = listaRequerimientosMateriales;
        }
    }

    public Collection<RequerimientoMaterialOrdenDeProduccion> generarRequerimientosMateriales() {
        HashMap<Articulo, RequerimientoMaterialOrdenDeProduccion> mapa = new HashMap<Articulo, RequerimientoMaterialOrdenDeProduccion>();
        if (this.detalles != null) {
            for (OrdenDeProduccionDetalle detalle : this.detalles) {
                Collection<RequerimientoMaterial> rmDetalle = detalle.getMuebleModelo().devolverRequerimientosMaterialesAComprar(detalle.getCantidad());
                if (rmDetalle != null && rmDetalle.size() > 0) {
                    for (RequerimientoMaterial rm : rmDetalle) {
                        // si no existe lo agrega ya con la cantidad calculada
                        if (!mapa.containsKey((Articulo) rm.getItemRequerible())) {
                            RequerimientoMaterialOrdenDeProduccion rmop = new RequerimientoMaterialOrdenDeProduccion();
                            rmop.setCantidadRequerida(rm.getCantidadRequerida());
                            rmop.setItemRequerible(rm.getItemRequerible());
                            mapa.put((Articulo) rm.getItemRequerible(), rmop);
                        }
                        // si existe, le agrega la cantidad
                        else {
                            RequerimientoMaterial rmm = mapa.get((Articulo) rm.getItemRequerible());
                            rmm.agregarCantidadRequerida(rm.getCantidadRequerida());
                        }
                    }
                }
            }
            Collection<RequerimientoMaterialOrdenDeProduccion> listaRequerimientosMateriales = new ArrayList<RequerimientoMaterialOrdenDeProduccion>();
            for (RequerimientoMaterialOrdenDeProduccion req : mapa.values()) {
                listaRequerimientosMateriales.add(req);
            }
            Iterator<RequerimientoMaterialOrdenDeProduccion> iterator = listaRequerimientosMateriales.iterator();
            while (iterator.hasNext()) {
                RequerimientoMaterialOrdenDeProduccion requerimientoMaterialOrdenDeProduccion = iterator.next();
                Articulo articulo = (Articulo) requerimientoMaterialOrdenDeProduccion.getItemRequerible();
                if (requerimientoMaterialOrdenDeProduccion.getCantidadRequerida().compareTo(articulo.getFamilia().getStockDisponible()) <= 0) {
                    requerimientoMaterialOrdenDeProduccion.agregarCantidadReservada(requerimientoMaterialOrdenDeProduccion.getCantidadRequerida());
                } else {
                    BigDecimal cantidadAReservar = articulo.getFamilia().getStockDisponible();
                    requerimientoMaterialOrdenDeProduccion.agregarCantidadReservada(cantidadAReservar);
                }
            }
            return listaRequerimientosMateriales;
        }
        return null;
    }

    public Collection<RequerimientoMaterialOrdenDeProduccion> generarActualizacionRequerimientosMateriales() {
        if (this.requerimientoMateriales == null ||
                (this.requerimientoMateriales != null && this.requerimientoMateriales.isEmpty())) {
            throw new AssertionError("Se llamó a generarActualizacionRequerimientosMateriales() pero la op no tenía asignado los requerimientos materiales");
        }
        HashMap<Articulo, RequerimientoMaterialOrdenDeProduccion> mapa = new HashMap<Articulo, RequerimientoMaterialOrdenDeProduccion>();
        if (this.detalles != null) {
            for (OrdenDeProduccionDetalle detalle : this.detalles) {
                Collection<RequerimientoMaterial> rmDetalle = detalle.getMuebleModelo().devolverRequerimientosMaterialesAComprar(detalle.getCantidad());
                if (rmDetalle != null && rmDetalle.size() > 0) {
                    for (RequerimientoMaterial rm : rmDetalle) {
                        // si no existe lo agrega ya con la cantidad calculada
                        if (!mapa.containsKey((Articulo) rm.getItemRequerible())) {
                            RequerimientoMaterialOrdenDeProduccion rmop = new RequerimientoMaterialOrdenDeProduccion();
                            rmop.setCantidadRequerida(rm.getCantidadRequerida());
                            rmop.setItemRequerible(rm.getItemRequerible());
                            mapa.put((Articulo) rm.getItemRequerible(), rmop);
                        }
                        // si existe, le agrega la cantidad
                        else {
                            RequerimientoMaterial rmm = mapa.get((Articulo) rm.getItemRequerible());
                            rmm.agregarCantidadRequerida(rm.getCantidadRequerida());
                        }
                    }
                }
            }
            Collection<RequerimientoMaterialOrdenDeProduccion> listaRequerimientosMateriales = new ArrayList<RequerimientoMaterialOrdenDeProduccion>();
            for (RequerimientoMaterialOrdenDeProduccion req : mapa.values()) {
                listaRequerimientosMateriales.add(req);
            }
            Iterator<RequerimientoMaterialOrdenDeProduccion> iterator = listaRequerimientosMateriales.iterator();
            while (iterator.hasNext()) {
                RequerimientoMaterialOrdenDeProduccion requerimientoMaterialOrdenDeProduccion = iterator.next();
                for (RequerimientoMaterialOrdenDeProduccion rm : this.requerimientoMateriales) {
                    if (rm.getItemRequerible().equals(requerimientoMaterialOrdenDeProduccion.getItemRequerible())) {
                        if (rm.getCantidadReservada().compareTo(BigDecimal.ZERO) > 0) {
                            requerimientoMaterialOrdenDeProduccion.setCantidadReservada(rm.getCantidadReservada());
                        }
                    }
                }
            }
            iterator = listaRequerimientosMateriales.iterator();
            while (iterator.hasNext()) {
                RequerimientoMaterialOrdenDeProduccion requerimientoMaterialOrdenDeProduccion = iterator.next();
                Articulo articulo = (Articulo) requerimientoMaterialOrdenDeProduccion.getItemRequerible();
                BigDecimal cantidadAAgregar = requerimientoMaterialOrdenDeProduccion.getCantidadRequerida().subtract(requerimientoMaterialOrdenDeProduccion.getCantidadReservada());
                if (cantidadAAgregar.compareTo(articulo.getFamilia().getStockDisponible()) <= 0) {
                    requerimientoMaterialOrdenDeProduccion.agregarCantidadReservada(cantidadAAgregar);
                } else {
                    BigDecimal cantidadAReservar = articulo.getFamilia().getStockDisponible();
                    requerimientoMaterialOrdenDeProduccion.agregarCantidadReservada(cantidadAReservar);
                }
            }
            return listaRequerimientosMateriales;
        }
        return null;
    }

    public void generarYGuardarActualizacionRequerimientosMateriales() {
        if (this.requerimientoMateriales == null ||
                (this.requerimientoMateriales != null && this.requerimientoMateriales.isEmpty())) {
            throw new AssertionError("Se llamó a generarActualizacionRequerimientosMateriales() pero la op no tenía asignado los requerimientos materiales");
        }
        HashMap<Articulo, RequerimientoMaterialOrdenDeProduccion> mapa = new HashMap<Articulo, RequerimientoMaterialOrdenDeProduccion>();
        if (this.detalles != null) {
            for (OrdenDeProduccionDetalle detalle : this.detalles) {
                Collection<RequerimientoMaterial> rmDetalle = detalle.getMuebleModelo().devolverRequerimientosMaterialesAComprar(detalle.getCantidad());
                if (rmDetalle != null && rmDetalle.size() > 0) {
                    for (RequerimientoMaterial rm : rmDetalle) {
                        // si no existe lo agrega ya con la cantidad calculada
                        if (!mapa.containsKey((Articulo) rm.getItemRequerible())) {
                            RequerimientoMaterialOrdenDeProduccion rmop = new RequerimientoMaterialOrdenDeProduccion();
                            rmop.setCantidadRequerida(rm.getCantidadRequerida());
                            rmop.setItemRequerible(rm.getItemRequerible());
                            mapa.put((Articulo) rm.getItemRequerible(), rmop);
                        }
                        // si existe, le agrega la cantidad
                        else {
                            RequerimientoMaterial rmm = mapa.get((Articulo) rm.getItemRequerible());
                            rmm.agregarCantidadRequerida(rm.getCantidadRequerida());
                        }
                    }
                }
            }
            Collection<RequerimientoMaterialOrdenDeProduccion> listaRequerimientosMateriales = new ArrayList<RequerimientoMaterialOrdenDeProduccion>();
            for (RequerimientoMaterialOrdenDeProduccion req : mapa.values()) {
                listaRequerimientosMateriales.add(req);
            }
            Iterator<RequerimientoMaterialOrdenDeProduccion> iterator = listaRequerimientosMateriales.iterator();
            while (iterator.hasNext()) {
                RequerimientoMaterialOrdenDeProduccion requerimientoMaterialOrdenDeProduccion = iterator.next();
                for (RequerimientoMaterialOrdenDeProduccion rm : this.requerimientoMateriales) {
                    if (rm.getItemRequerible().equals(requerimientoMaterialOrdenDeProduccion.getItemRequerible())) {
                        if (rm.getCantidadReservada().compareTo(BigDecimal.ZERO) > 0) {
                            requerimientoMaterialOrdenDeProduccion.setCantidadReservada(rm.getCantidadReservada());
                        }
                    }
                }
            }
            iterator = listaRequerimientosMateriales.iterator();
            while (iterator.hasNext()) {
                RequerimientoMaterialOrdenDeProduccion requerimientoMaterialOrdenDeProduccion = iterator.next();
                Articulo articulo = (Articulo) requerimientoMaterialOrdenDeProduccion.getItemRequerible();
                BigDecimal cantidadAAgregar = requerimientoMaterialOrdenDeProduccion.getCantidadRequerida().subtract(requerimientoMaterialOrdenDeProduccion.getCantidadReservada());
                if (cantidadAAgregar.compareTo(articulo.getFamilia().getStockDisponible()) <= 0) {
                    requerimientoMaterialOrdenDeProduccion.agregarCantidadReservada(cantidadAAgregar);
                    articulo.getFamilia().reservar(cantidadAAgregar); // reservo todo, por lo que ya no es un requerimiento a comprar
                } else {
                    BigDecimal cantidadAReservar = articulo.getFamilia().getStockDisponible();
                    requerimientoMaterialOrdenDeProduccion.agregarCantidadReservada(cantidadAReservar);
                    articulo.getFamilia().reservar(cantidadAReservar); // reservo todo, por lo que ya no es un requerimiento a comprar
                }
            }
            this.requerimientoMateriales = listaRequerimientosMateriales;
        }
    }
}
