/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes.pmp;

import componentes.articuloproveedor.VistaArticuloProveedor;
import componentes.ordendeproduccion.VistaOrdenDeProduccion;
import componentes.pedido.VistaPedido;
import componentes.pmp.vistapmp.DetalleTreeTablePMPPorTipoYModeloYPedidoDetalle;
import componentes.pmp.vistapmp.DetalleTreeTablePMPPorTipoYModeloYPedidoDetallePMPDetalle;
import componentes.pmp.vistapmp.DetalleTreeTablePMPPorTipoYModeloYPedidoDetalleMuebleTipo;
import componentes.pmp.vistapmp.DetalleTreeTablePMPPorTipoYModeloYPedidoDetallePedidoDetalle;
import entidades.*;
import entidades.ordenamiento.ComparadorMuebleTipoPorNombre;
import entidades.ordenamiento.ComparadorOrdenDeProduccionPorFechaInicioEstimada;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.logging.*;
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.naming.*;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.model.*;

/**
 *
 * @author Zaba
 */
@ManagedBean
@SessionScoped
public class VistaPMP implements java.io.Serializable {

    @EJB
    private GestorPMP gestorPMP;
    private DetalleTreeTablePMPPorTipoYModeloYPedidoDetalle detalleArbolSeleccionado;
    private Fabrica fabricaSeleccionada;
    private OrdenDeProduccion ordenDeProduccionSeleccionada;
    private String script;
    private CommandButton botonCrearOP;
    private CommandButton botonAsignarOP;
    private Date fechaInicioEstimadaNuevaOrdenDeProduccion;
    private Date fechaFinEstimadaNuevaOrdenDeProduccion;
    private Date fechaInicioEstimadaAsignacionOrdenDeProduccion;
    private Date fechaFinEstimadaAsignacionOrdenDeProduccion;
    private Date fechaInicioEstimadaOrdenDeProduccion;
    private Date fechaFinEstimadaOrdenDeProduccion;
    private Date fechaMinimaOrdenDeProduccionAEditar;
    private Date fechaMaximaOrdenDeProduccionAEditar;
    private Boolean arrastrarOrdenesDeProduccionSiguientes;
    private Collection<RequerimientoMaterialOrdenDeProduccion> requerimientoMateriales;

    public void instanciarGestor() {
        if (this.gestorPMP == null) {
            try {
                InitialContext ic = new InitialContext();
                this.gestorPMP = (GestorPMP) ic.lookup("java:global/SigmaProject/GestorPMP");
            } catch (NamingException ex) {
                Logger.getLogger(VistaPMP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Collection<RequerimientoMaterialOrdenDeProduccion> getRequerimientoMateriales() {
        return requerimientoMateriales;
    }

    public void setRequerimientoMateriales(Collection<RequerimientoMaterialOrdenDeProduccion> requerimientoMateriales) {
        this.requerimientoMateriales = requerimientoMateriales;
    }

    public Boolean getArrastrarOrdenesDeProduccionSiguientes() {
        return arrastrarOrdenesDeProduccionSiguientes;
    }

    public void setArrastrarOrdenesDeProduccionSiguientes(Boolean arrastrarOrdenesDeProduccionSiguientes) {
        this.arrastrarOrdenesDeProduccionSiguientes = arrastrarOrdenesDeProduccionSiguientes;
        if (fechaMaximaOrdenDeProduccionAEditar != null) {
            if (!this.arrastrarOrdenesDeProduccionSiguientes && this.fechaFinEstimadaOrdenDeProduccion.after(fechaMaximaOrdenDeProduccionAEditar)) {
                this.fechaFinEstimadaOrdenDeProduccion = this.fechaMaximaOrdenDeProduccionAEditar;
            }
        }
    }

    public Date getFechaFinEstimadaOrdenDeProduccion() {
        return fechaFinEstimadaOrdenDeProduccion;
    }

    public void setFechaFinEstimadaOrdenDeProduccion(Date fechaFinEstimadaOrdenDeProduccion) {
        this.fechaFinEstimadaOrdenDeProduccion = fechaFinEstimadaOrdenDeProduccion;
    }

    public Date getFechaInicioEstimadaOrdenDeProduccion() {
        return fechaInicioEstimadaOrdenDeProduccion;
    }

    public void setFechaInicioEstimadaOrdenDeProduccion(Date fechaInicioEstimadaOrdenDeProduccion) {
        this.fechaInicioEstimadaOrdenDeProduccion = fechaInicioEstimadaOrdenDeProduccion;
    }

    public String getFechaMaximaOrdenDeProduccionAEditar() {
        if (this.arrastrarOrdenesDeProduccionSiguientes != null) {
            if (this.arrastrarOrdenesDeProduccionSiguientes) {
                return null;
            } else if (fechaMaximaOrdenDeProduccionAEditar != null) {
                return new SimpleDateFormat("dd/MM/yyyy").format(fechaMaximaOrdenDeProduccionAEditar);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public String getFechaMinimaOrdenDeProduccionAEditar() {
        if (this.fechaMinimaOrdenDeProduccionAEditar != null) {
            return new SimpleDateFormat("dd/MM/yyyy").format(fechaMinimaOrdenDeProduccionAEditar);
        } else {
            return null;
        }
    }

    public CommandButton getBotonAsignarOP() {
        return botonAsignarOP;
    }

    public void setBotonAsignarOP(CommandButton botonAsignarOP) {
        this.botonAsignarOP = botonAsignarOP;
    }

    public CommandButton getBotonCrearOP() {
        return botonCrearOP;
    }

    public void setBotonCrearOP(CommandButton botonCrearOP) {
        this.botonCrearOP = botonCrearOP;
    }

    public Date getFechaFinEstimadaAsignacionOrdenDeProduccion() {
        return fechaFinEstimadaAsignacionOrdenDeProduccion;
    }

    public void setFechaFinEstimadaAsignacionOrdenDeProduccion(Date fechaFinEstimadaAsignacionOrdenDeProduccion) {
        this.fechaFinEstimadaAsignacionOrdenDeProduccion = fechaFinEstimadaAsignacionOrdenDeProduccion;
    }

    public Date getFechaInicioEstimadaAsignacionOrdenDeProduccion() {
        return fechaInicioEstimadaAsignacionOrdenDeProduccion;
    }

    public void setFechaInicioEstimadaAsignacionOrdenDeProduccion(Date fechaInicioEstimadaAsignacionOrdenDeProduccion) {
        this.fechaInicioEstimadaAsignacionOrdenDeProduccion = fechaInicioEstimadaAsignacionOrdenDeProduccion;
    }

    public Date getFechaFinEstimadaNuevaOrdenDeProduccion() {
        return fechaFinEstimadaNuevaOrdenDeProduccion;
    }

    public void setFechaFinEstimadaNuevaOrdenDeProduccion(Date fechaFinEstimadaNuevaOrdenDeProduccion) {
        this.fechaFinEstimadaNuevaOrdenDeProduccion = fechaFinEstimadaNuevaOrdenDeProduccion;
    }

    public Date getFechaInicioEstimadaNuevaOrdenDeProduccion() {
        return fechaInicioEstimadaNuevaOrdenDeProduccion;
    }

    public String getFechaMinimaCalendarioNuevaOP() {
        if (this.fechaInicioEstimadaNuevaOrdenDeProduccion != null) {
            return new SimpleDateFormat("dd/MM/yyyy").format(this.fechaInicioEstimadaNuevaOrdenDeProduccion);
        } else {
            return null;
        }
    }

    public String getFechaMinimaCalendarioAsignacionOP() {
        if (this.fechaFinEstimadaAsignacionOrdenDeProduccion != null) {
            return new SimpleDateFormat("dd/MM/yyyy").format(this.fechaFinEstimadaAsignacionOrdenDeProduccion);
        } else {
            return null;
        }
    }

    public String getFechaMinimaCalendarioOP() {
        if (this.fechaMinimaOrdenDeProduccionAEditar != null) {
            return new SimpleDateFormat("dd/MM/yyyy").format(this.fechaMinimaOrdenDeProduccionAEditar);
        } else {
            return null;
        }
    }

    public String getFechaMaximaCalendarioOP() {
        if (this.fechaMaximaOrdenDeProduccionAEditar != null) {
            return new SimpleDateFormat("dd/MM/yyyy").format(this.fechaMaximaOrdenDeProduccionAEditar);
        } else {
            return null;
        }
    }

    public void setFechaInicioEstimadaNuevaOrdenDeProduccion(Date fechaInicioEstimadaNuevaOrdenDeProduccion) {
        this.fechaInicioEstimadaNuevaOrdenDeProduccion = fechaInicioEstimadaNuevaOrdenDeProduccion;
    }

    public DetalleTreeTablePMPPorTipoYModeloYPedidoDetalle getDetalleArbolSeleccionado() {
        System.out.println("getDetalle");
        return detalleArbolSeleccionado;
    }

    public void setDetalleArbolSeleccionado(DetalleTreeTablePMPPorTipoYModeloYPedidoDetalle detalleArbolSeleccionado) {
        this.detalleArbolSeleccionado = detalleArbolSeleccionado;
        this.ordenDeProduccionSeleccionada = null;
        System.out.println("setDetalle");
        actualizarBotones();
    }

    public OrdenDeProduccion getOrdenDeProduccionSeleccionada() {
        return ordenDeProduccionSeleccionada;
    }

    public void setOrdenDeProduccionSeleccionada(OrdenDeProduccion ordenDeProduccionSeleccionada) {
        this.ordenDeProduccionSeleccionada = ordenDeProduccionSeleccionada;
        actualizarBotones();
    }

    public String getScript() {
        return script;
    }

    public Fabrica getFabricaSeleccionada() {
        return fabricaSeleccionada;
    }

    public void setFabricaSeleccionada(Fabrica fabricaSeleccionada) {
        this.fabricaSeleccionada = fabricaSeleccionada;
        ordenDeProduccionSeleccionada = null;
        actualizarBotones();
    }
    private HashMap<MuebleTipo, ArrayList<PMPDetalle>> hashMapTipoPMPDetalle;

    public TreeNode getTreeNodePorTipoYModeloYDetallePedido() {
        Collection<PMPDetalle> detalles = gestorPMP.devolverPMPDetalles();
        hashMapTipoPMPDetalle = new HashMap<MuebleTipo, ArrayList<PMPDetalle>>();
        // Se va agregando para cada mueble, los detalles que le correspondan del PMP
        for (PMPDetalle detalle : detalles) {
            MuebleTipo muebleTipo = detalle.getMuebleModelo().getMuebleTipo();
            if (!hashMapTipoPMPDetalle.containsKey(muebleTipo)) {
                hashMapTipoPMPDetalle.put(muebleTipo, new ArrayList<PMPDetalle>());
            }
            hashMapTipoPMPDetalle.get(muebleTipo).add(detalle);
        }
        MuebleTipo[] muebleTipos = hashMapTipoPMPDetalle.keySet().toArray(new MuebleTipo[0]);
        // Ordenamos por nombre del tipo de mueble
        Arrays.sort(muebleTipos, new ComparadorMuebleTipoPorNombre());
        TreeNode root = new DefaultTreeNode("root", null);
        // Para cada tipo mueble, creamos un hijo del nodo raiz, asi que el prime nivel del arbol que se
        // visualize serán los tipos de muebles
        for (MuebleTipo muebleTipo : muebleTipos) {
            TreeNode nodoTipo = new DefaultTreeNode("muebleTipo", new DetalleTreeTablePMPPorTipoYModeloYPedidoDetalleMuebleTipo(muebleTipo), root);
            ArrayList<PMPDetalle> pmpDetalles = hashMapTipoPMPDetalle.get(muebleTipo);
            // Para cada tipo mueble, agregamos nodos con los muebles de ese tipo
            for (PMPDetalle pmpDetalle : pmpDetalles) {
                TreeNode nodoModelo = new DefaultTreeNode("muebleModelo", new DetalleTreeTablePMPPorTipoYModeloYPedidoDetallePMPDetalle(pmpDetalle), nodoTipo);
                // y para cada mueble, agregamos los detalles de los pedidos que lo conforman
                for (PedidoDetalle pedidoDetalle : pmpDetalle.getPedidoDetalles()) {
                    TreeNode nodoPedidoDetalle = new DefaultTreeNode("pedidoDetalle", new DetalleTreeTablePMPPorTipoYModeloYPedidoDetallePedidoDetalle(pedidoDetalle), nodoModelo);
                }
            }
        }
        return root;
    }

    private void actualizarBotones() {
        if (detalleArbolSeleccionado != null && fabricaSeleccionada != null) {
            botonCrearOP.setDisabled(false);
            if (ordenDeProduccionSeleccionada != null) {
                botonAsignarOP.setDisabled(false);
            } else {
                botonAsignarOP.setDisabled(true);
            }
        } else {
            botonCrearOP.setDisabled(true);
            botonAsignarOP.setDisabled(true);
        }
    }

    public void validarYCalcularFechasNuevaOrdenDeProduccion() {
        MuebleTipo muebleTipo = null;
        if (detalleArbolSeleccionado instanceof DetalleTreeTablePMPPorTipoYModeloYPedidoDetallePMPDetalle) {
            PMPDetalle pmpDetalle = (PMPDetalle) detalleArbolSeleccionado.getObjeto();
            muebleTipo = pmpDetalle.getMuebleModelo().getMuebleTipo();
        } else if (detalleArbolSeleccionado instanceof DetalleTreeTablePMPPorTipoYModeloYPedidoDetalleMuebleTipo) {
            muebleTipo = (MuebleTipo) detalleArbolSeleccionado.getObjeto();
        } else if (detalleArbolSeleccionado instanceof DetalleTreeTablePMPPorTipoYModeloYPedidoDetallePedidoDetalle) {
            PedidoDetalle pedidoDetalle = (PedidoDetalle) detalleArbolSeleccionado.getObjeto();
            muebleTipo = pedidoDetalle.getMuebleModelo().getMuebleTipo();
        }
        if (this.fabricaSeleccionada.devolverCapacidadMaximaPara(muebleTipo) != null) {
            if (Integer.parseInt(detalleArbolSeleccionado.getCantidadPorAsignar()) <= this.fabricaSeleccionada.devolverCapacidadMaximaPara(muebleTipo)) {
                this.fechaInicioEstimadaNuevaOrdenDeProduccion = null;
                this.fechaFinEstimadaNuevaOrdenDeProduccion = null;
                if (this.detalleArbolSeleccionado != null && this.fabricaSeleccionada != null) {
                    VistaOrdenDeProduccion vistaOrdenDeProduccion = devolverVistaOrdenDeProduccion();
                    Collection<OrdenDeProduccion> ordenesDeProduccion = vistaOrdenDeProduccion.devolverOrdenesDeProduccionPlanificadas(fabricaSeleccionada);
                    Date ultimaFechaFinEstimada = new Date();
                    for (OrdenDeProduccion ordenDeProduccion : ordenesDeProduccion) {
                        if (ordenDeProduccion.getFechaFinEstimada().after(ultimaFechaFinEstimada)) {
                            ultimaFechaFinEstimada = ordenDeProduccion.getFechaFinEstimada();
                        }
                    }
                    Calendar calendario = Calendar.getInstance();
                    calendario.setTime(ultimaFechaFinEstimada);
                    calendario.add(Calendar.DATE, 1); // agrego un día
                    this.fechaInicioEstimadaNuevaOrdenDeProduccion = calendario.getTime(); // y asigno

                    // Creo una orden de producción sin guardar, con el fin de calcular la fecha minima posible para iniciarla
                    crearYAsignarAOrdenDeProduccion(false);
                    if (this.ordenDeProduccionSeleccionada == null) {
                        throw new AssertionError("Error al crear la Orden de Producción en memoria para el calculo de fecha minima");
                    }
                    this.requerimientoMateriales = this.ordenDeProduccionSeleccionada.generarRequerimientosMateriales();
                    // Si el tiempo calculado es después al de la orden de produccion anterior, lo asigno como tiempo mínimo de inicio
                    Date fechaMinimaEnBaseARequerimientosMaterialesYTiemposProveedor = devolverFechaMinimaEnBaseARequerimientosMaterialesYTiemposProveedor();
                    if (fechaMinimaEnBaseARequerimientosMaterialesYTiemposProveedor.after(this.fechaInicioEstimadaNuevaOrdenDeProduccion)) {
                        this.fechaInicioEstimadaNuevaOrdenDeProduccion = fechaMinimaEnBaseARequerimientosMaterialesYTiemposProveedor;
                    }

                    calendario.setTime(this.fechaInicioEstimadaNuevaOrdenDeProduccion);
                    if (calendario.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                        calendario.add(Calendar.DATE, 2);
                    } else if (calendario.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                        calendario.add(Calendar.DATE, 1);
                    }
                    this.fechaInicioEstimadaNuevaOrdenDeProduccion = calendario.getTime();

                    Integer tiempoNecesarioProduccionEnMinutos = devolverTiempoNecesarioProduccionEnMinutos();
                    Float tiempoHoras = new Float(tiempoNecesarioProduccionEnMinutos.floatValue() / 60.0);
                    Integer diasTotal = (Integer) tiempoHoras.intValue() / 9;
                    if (diasTotal * 9 < tiempoHoras) {
                        diasTotal++;
                    }
                    Integer cantidadSemanasAAgregar = 0;
                    if (diasTotal > 5) {
                        cantidadSemanasAAgregar = diasTotal / 5;
                        diasTotal = diasTotal % 5;
                    }
                    calendario.setTime(this.fechaInicioEstimadaNuevaOrdenDeProduccion);
                    calendario.add(Calendar.DATE, cantidadSemanasAAgregar * 7);
                    calendario.add(Calendar.DATE, diasTotal);
                    if (calendario.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                        calendario.add(Calendar.DATE, 2);
                    } else if (calendario.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                        calendario.add(Calendar.DATE, 1);
                    }
                    this.fechaFinEstimadaNuevaOrdenDeProduccion = calendario.getTime();

                    this.ordenDeProduccionSeleccionada = null;

                    this.script = "dialogoFechasEstimadasNuevaOP.show();";
                } else {
                    this.script = "alert('Debe seleccionar un item y una fábrica para poder crear una Orden de Producción.');";
                }
            } else {
                this.script = "alert('La fábrica seleccionada no tiene una capacidad de producción suficiente como para asignarle este item.');";
            }
        } else {
            this.script = "alert('La fábrica seleccionada no tiene una capacidad de producción asignada para el tipo de mueble seleccionado.');";
        }
    }

    public void crearYAsignarAOrdenDeProduccion() {
        crearYAsignarAOrdenDeProduccion(true);
    }

    public void crearYAsignarAOrdenDeProduccion(Boolean guardar) {
        if ((this.detalleArbolSeleccionado != null && this.fabricaSeleccionada != null && this.fechaInicioEstimadaNuevaOrdenDeProduccion != null && this.fechaFinEstimadaNuevaOrdenDeProduccion != null && guardar.booleanValue() == true)
                || (this.detalleArbolSeleccionado != null && this.fabricaSeleccionada != null && this.fechaInicioEstimadaNuevaOrdenDeProduccion != null && guardar.booleanValue() == false)) {
            ordenDeProduccionSeleccionada = new OrdenDeProduccion();
            ordenDeProduccionSeleccionada.setFabrica(fabricaSeleccionada);
            VistaOrdenDeProduccion vistaOrdenDeProduccion = devolverVistaOrdenDeProduccion();
            Collection<OrdenDeProduccion> ordenesDeProduccion = vistaOrdenDeProduccion.devolverOrdenesDeProduccionPlanificadas(fabricaSeleccionada);
            Integer menorPrioridad = 0;
            for (OrdenDeProduccion ordenDeProduccion : ordenesDeProduccion) {
                if (ordenDeProduccion.getPrioridad() != null && ordenDeProduccion.getPrioridad() > menorPrioridad) {
                    menorPrioridad = ordenDeProduccion.getPrioridad();
                }
            }
            ordenDeProduccionSeleccionada.setPrioridad(menorPrioridad == null ? null : menorPrioridad + 1);
            ordenDeProduccionSeleccionada.setFechaInicioEstimada(this.fechaInicioEstimadaNuevaOrdenDeProduccion);
            ordenDeProduccionSeleccionada.setFechaFinEstimada(this.fechaFinEstimadaNuevaOrdenDeProduccion);
            if (detalleArbolSeleccionado instanceof DetalleTreeTablePMPPorTipoYModeloYPedidoDetallePMPDetalle) {
                PMPDetalle pmpDetalle = (PMPDetalle) detalleArbolSeleccionado.getObjeto();
                ordenDeProduccionSeleccionada.setMuebleTipo(pmpDetalle.getMuebleModelo().getMuebleTipo());
            } else if (detalleArbolSeleccionado instanceof DetalleTreeTablePMPPorTipoYModeloYPedidoDetalleMuebleTipo) {
                MuebleTipo muebleTipo = (MuebleTipo) detalleArbolSeleccionado.getObjeto();
                ordenDeProduccionSeleccionada.setMuebleTipo(muebleTipo);
            } else if (detalleArbolSeleccionado instanceof DetalleTreeTablePMPPorTipoYModeloYPedidoDetallePedidoDetalle) {
                PedidoDetalle pedidoDetalle = (PedidoDetalle) detalleArbolSeleccionado.getObjeto();
                ordenDeProduccionSeleccionada.setMuebleTipo(pedidoDetalle.getMuebleModelo().getMuebleTipo());
            }
            if (guardar) {
                vistaOrdenDeProduccion.guardarOrdenDeProduccion(ordenDeProduccionSeleccionada);
            }
            pasarDePMPAOrdenDeProduccion(guardar);
        }
    }

    public void pasarDePMPAOrdenDeProduccion() {
        pasarDePMPAOrdenDeProduccion(true);
    }

    public void pasarDePMPAOrdenDeProduccion(Boolean guardar) {
        if (this.detalleArbolSeleccionado != null && this.fabricaSeleccionada != null && this.ordenDeProduccionSeleccionada != null) {
            // Si tiene capacidad disponible
            if ((this.detalleArbolSeleccionado instanceof DetalleTreeTablePMPPorTipoYModeloYPedidoDetalleMuebleTipo
                    && this.ordenDeProduccionSeleccionada.tieneCapacidadPara(hashMapTipoPMPDetalle.get((MuebleTipo) this.detalleArbolSeleccionado.getObjeto())))
                    || ((this.detalleArbolSeleccionado instanceof DetalleTreeTablePMPPorTipoYModeloYPedidoDetallePMPDetalle
                    || this.detalleArbolSeleccionado instanceof DetalleTreeTablePMPPorTipoYModeloYPedidoDetallePedidoDetalle)
                    && this.ordenDeProduccionSeleccionada.tieneCapacidadPara((Asignable) this.detalleArbolSeleccionado.getObjeto()))) {
                Collection<PMPDetalle> pmpDetalles = devolverPMPDetallesDeDetalleArbolSeleccionado();
                pasarDePMPAOrdenDeProduccion(pmpDetalles, guardar);
                if (guardar) {
                    this.detalleArbolSeleccionado = null;
                    this.ordenDeProduccionSeleccionada = null;
                    actualizarBotones();
                }
            }
        }
    }

    private void pasarDePMPAOrdenDeProduccion(Collection<PMPDetalle> pmpDetalles) {
        pasarDePMPAOrdenDeProduccion(pmpDetalles, true);
    }

    private void pasarDePMPAOrdenDeProduccion(Collection<PMPDetalle> pmpDetalles, Boolean guardar) {
        VistaOrdenDeProduccion vistaOrdenDeProduccion = devolverVistaOrdenDeProduccion();
        for (PMPDetalle pmpDetalle : pmpDetalles) {
            // Si la órden de producción no tenía un detalle con el mueble modelo seleccionado, lo creo y lo agrego
            if (!ordenDeProduccionSeleccionada.contieneDetalleMuebleModelo(pmpDetalle.getMuebleModelo())) {
                ordenDeProduccionSeleccionada.devolverDetalleMuebleModeloNuevo(pmpDetalle.getMuebleModelo());
                if (guardar) {
                    this.ordenDeProduccionSeleccionada = vistaOrdenDeProduccion.actualizarOrdenDeProduccion(ordenDeProduccionSeleccionada);
                }
            }
            OrdenDeProduccionDetalle ordenDeProduccionDetalle = ordenDeProduccionSeleccionada.devolverDetalleMuebleModeloExistente(pmpDetalle.getMuebleModelo());
            Collection<PedidoDetalle> pedidoDetalles = pmpDetalle.getPedidoDetalles();
            if (guardar) {
                pmpDetalle.setPedidoDetalles(null);
            }
            PMPDetalle pmpDetalleGuardado = null; // solo se usa en caso de que pmpDetalle sea temporal
            if (pmpDetalle.getId() == null) {
                pmpDetalleGuardado = pedidoDetalles.iterator().next().getPmpDetallePadre();
            }
            if (guardar) {
                ordenDeProduccionDetalle.pasarDePmpDetalleAOrdenDeProduccionDetalle(pedidoDetalles);
            } else {
                ordenDeProduccionDetalle.copiarDePmpDetalleAOrdenDeProduccionDetalle(pedidoDetalles);
            }
            if (guardar) {
                VistaPedido vistaPedido = devolverVistaPedido();
                for (PedidoDetalle pedidoDetalle : pedidoDetalles) {
                    vistaPedido.actualizarPedido(pedidoDetalle.getPedido());
                }
                // Si no es temporal, es decir si existe, lo quito
                if (pmpDetalle.getId() != null) {
                    gestorPMP.quitarPmpDetalle(pmpDetalle);
                } // Si era temporal, era solo un PedidoDetalle. Hay que verificar que si no quedan más PedidoDetalle para su pmpdetalle, hay que quitar este último
                else {
                    pmpDetalleGuardado.quitarPedidoDetalle(pedidoDetalles.iterator().next());
                    if (pmpDetalleGuardado.getPedidoDetalles() == null || (pmpDetalleGuardado.getPedidoDetalles() != null && pmpDetalleGuardado.getPedidoDetalles().isEmpty())) {
                        gestorPMP.quitarPmpDetalle(pmpDetalleGuardado);
                    }
                }
                vistaOrdenDeProduccion.actualizarOrdenDeProduccion(ordenDeProduccionSeleccionada);
            }
        }
        if (guardar) {
            ordenDeProduccionSeleccionada.generarYGuardarRequerimientosMateriales();
            vistaOrdenDeProduccion.actualizarOrdenDeProduccion(ordenDeProduccionSeleccionada);
        }
    }

    public void asignarAOrdenDeProduccion() {
        if (this.fechaFinEstimadaAsignacionOrdenDeProduccion.after(this.ordenDeProduccionSeleccionada.getFechaFinEstimada())) {
            Calendar calendario = Calendar.getInstance();
            calendario.setTime(fechaFinEstimadaAsignacionOrdenDeProduccion);
            Integer diaFinNuevo = calendario.get(Calendar.DAY_OF_YEAR);
            calendario.setTime(this.ordenDeProduccionSeleccionada.getFechaFinEstimada());
            Integer diaFinAnterior = calendario.get(Calendar.DAY_OF_YEAR);
            VistaOrdenDeProduccion vistaOrdenDeProduccion = devolverVistaOrdenDeProduccion();
            List<OrdenDeProduccion> ordenesDeProduccion = (List<OrdenDeProduccion>) vistaOrdenDeProduccion.devolverOrdenesDeProduccionPlanificadas(fabricaSeleccionada);
            Collections.sort(ordenesDeProduccion, new ComparadorOrdenDeProduccionPorFechaInicioEstimada());
            Integer diasAPostergar = diaFinNuevo - diaFinAnterior;
            for (OrdenDeProduccion ordenDeProduccion : ordenesDeProduccion) {
                if (ordenDeProduccion.getFechaInicioEstimada().after(this.ordenDeProduccionSeleccionada.getFechaInicioEstimada())) {
                    calendario.setTime(ordenDeProduccion.getFechaInicioEstimada());
                    calendario.add(Calendar.DATE, diasAPostergar);
                    ordenDeProduccion.setFechaInicioEstimada(calendario.getTime());
                    calendario.setTime(ordenDeProduccion.getFechaFinEstimada());
                    calendario.add(Calendar.DATE, diasAPostergar);
                    ordenDeProduccion.setFechaFinEstimada(calendario.getTime());
                    devolverVistaOrdenDeProduccion().actualizarOrdenDeProduccion(ordenDeProduccion);
                }
            }
        }
        this.ordenDeProduccionSeleccionada.setFechaFinEstimada(this.fechaFinEstimadaAsignacionOrdenDeProduccion);
        if (this.ordenDeProduccionSeleccionada.getRequerimientoMateriales() == null
                || (this.ordenDeProduccionSeleccionada.getRequerimientoMateriales() != null && this.ordenDeProduccionSeleccionada.getRequerimientoMateriales().isEmpty())) {
            this.ordenDeProduccionSeleccionada.generarYGuardarRequerimientosMateriales();
        } else {
            this.ordenDeProduccionSeleccionada.generarYGuardarActualizacionRequerimientosMateriales();
        }
        devolverVistaOrdenDeProduccion().actualizarOrdenDeProduccion(this.ordenDeProduccionSeleccionada);
        pasarDePMPAOrdenDeProduccion();
        this.detalleArbolSeleccionado = null;
        this.ordenDeProduccionSeleccionada = null;
        actualizarBotones();
    }

    public void calcularLimitesFechasOrdenDeProduccion(OrdenDeProduccion ordenDeProduccion) {
        List<OrdenDeProduccion> ordenesDeProduccionPlanificadas = (List<OrdenDeProduccion>) devolverVistaOrdenDeProduccion().devolverOrdenesDeProduccionPlanificadas(fabricaSeleccionada);
        if (ordenesDeProduccionPlanificadas.size() > 1) {
            Collections.sort(ordenesDeProduccionPlanificadas, new ComparadorOrdenDeProduccionPorFechaInicioEstimada());
            OrdenDeProduccion ordenDeProduccionAnteriorMasCercana = null;
            for (OrdenDeProduccion orden : ordenesDeProduccionPlanificadas) {
                if (orden.getFechaInicioEstimada().before(ordenDeProduccion.getFechaInicioEstimada()) && !orden.equals(ordenDeProduccion)) {
                    ordenDeProduccionAnteriorMasCercana = orden;
                } else if (orden.equals(ordenDeProduccion)) {
                    break;
                }
            }
            Calendar calendario = Calendar.getInstance();
            if (ordenDeProduccionAnteriorMasCercana != null) {
                calendario.setTime(ordenDeProduccionAnteriorMasCercana.getFechaFinEstimada());
                calendario.add(Calendar.DATE, 1);
                this.fechaMinimaOrdenDeProduccionAEditar = calendario.getTime();
            } else {
                this.fechaMinimaOrdenDeProduccionAEditar = null;
            }
            OrdenDeProduccion ordenDeProduccionSiguienteMasCercana = null;
            for (OrdenDeProduccion orden : ordenesDeProduccionPlanificadas) {
                if (orden.getFechaInicioEstimada().after(ordenDeProduccion.getFechaInicioEstimada())) {
                    ordenDeProduccionSiguienteMasCercana = orden;
                    break;
                }
            }
            if (ordenDeProduccionSiguienteMasCercana != null) {
                calendario.setTime(ordenDeProduccionSiguienteMasCercana.getFechaInicioEstimada());
                calendario.add(Calendar.DATE, -1);
                this.fechaMaximaOrdenDeProduccionAEditar = calendario.getTime();
            } else {
                this.fechaMaximaOrdenDeProduccionAEditar = null;
            }
        }
        Date fechaMinimaEnBaseARequerimientosMaterialesYTiemposProveedor = devolverFechaMinimaEnBaseARequerimientosMaterialesYTiemposProveedor(ordenDeProduccion);
        if (this.fechaMinimaOrdenDeProduccionAEditar == null
                || (this.fechaMinimaOrdenDeProduccionAEditar != null && this.fechaMinimaOrdenDeProduccionAEditar.before(fechaMinimaEnBaseARequerimientosMaterialesYTiemposProveedor))) {
            this.fechaMinimaOrdenDeProduccionAEditar = fechaMinimaEnBaseARequerimientosMaterialesYTiemposProveedor;
        }

        this.fechaInicioEstimadaOrdenDeProduccion = ordenDeProduccion.getFechaInicioEstimada();
        this.fechaFinEstimadaOrdenDeProduccion = ordenDeProduccion.getFechaFinEstimada();
        this.ordenDeProduccionSeleccionada = ordenDeProduccion;
        this.script = null;
    }

    public void editarOrdenDeProduccion() {
        if (this.arrastrarOrdenesDeProduccionSiguientes == true && fechaMaximaOrdenDeProduccionAEditar != null && fechaFinEstimadaOrdenDeProduccion.after(fechaMaximaOrdenDeProduccionAEditar)) {
            Calendar calendario = Calendar.getInstance();
            calendario.setTime(fechaFinEstimadaOrdenDeProduccion);
            Integer diaFinNuevo = calendario.get(Calendar.DAY_OF_YEAR);
            calendario.setTime(this.ordenDeProduccionSeleccionada.getFechaFinEstimada());
            Integer diaFinAnterior = calendario.get(Calendar.DAY_OF_YEAR);
            VistaOrdenDeProduccion vistaOrdenDeProduccion = devolverVistaOrdenDeProduccion();
            List<OrdenDeProduccion> ordenesDeProduccion = (List<OrdenDeProduccion>) vistaOrdenDeProduccion.devolverOrdenesDeProduccionPlanificadas(fabricaSeleccionada);
            Collections.sort(ordenesDeProduccion, new ComparadorOrdenDeProduccionPorFechaInicioEstimada());
            Integer diasAPostergar = diaFinNuevo - diaFinAnterior;
            for (OrdenDeProduccion ordenDeProduccion : ordenesDeProduccion) {
                if (ordenDeProduccion.getFechaInicioEstimada().after(this.ordenDeProduccionSeleccionada.getFechaInicioEstimada())) {
                    calendario.setTime(ordenDeProduccion.getFechaInicioEstimada());
                    calendario.add(Calendar.DATE, diasAPostergar);
                    ordenDeProduccion.setFechaInicioEstimada(calendario.getTime());
                    calendario.setTime(ordenDeProduccion.getFechaFinEstimada());
                    calendario.add(Calendar.DATE, diasAPostergar);
                    ordenDeProduccion.setFechaFinEstimada(calendario.getTime());
                    devolverVistaOrdenDeProduccion().actualizarOrdenDeProduccion(ordenDeProduccion);
                }
            }
        }
        this.ordenDeProduccionSeleccionada.setFechaInicioEstimada(this.fechaInicioEstimadaOrdenDeProduccion);
        this.ordenDeProduccionSeleccionada.setFechaFinEstimada(this.fechaFinEstimadaOrdenDeProduccion);
        devolverVistaOrdenDeProduccion().actualizarOrdenDeProduccion(this.ordenDeProduccionSeleccionada);
    }

    public void quitarOrdenDeProduccion(OrdenDeProduccion ordenDeProduccion) {
    }

    public void agregarPedidosDetalleAPMP(Collection<PedidoDetalle> pedidoDetalles) {
        gestorPMP.agregarPedidosDetalleAPMP(pedidoDetalles);
    }

    public Collection<PMPDetalle> devolverPMPDetallesMueblesTipo(MuebleTipo muebleTipo) {
        return gestorPMP.devolverPMPDetallesMueblesTipo(muebleTipo);
    }

    public PMPDetalle devolverPMPDetalleMueblesModelo(MuebleModelo muebleModelo) {
        return gestorPMP.devolverPMPDetalleMueblesModelo(muebleModelo);
    }

    private VistaOrdenDeProduccion devolverVistaOrdenDeProduccion() {
        VistaOrdenDeProduccion vistaOrdenDeProduccion = (VistaOrdenDeProduccion) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaOrdenDeProduccion");
        if (vistaOrdenDeProduccion == null) {
            vistaOrdenDeProduccion = new VistaOrdenDeProduccion();
            vistaOrdenDeProduccion.instanciarGestor();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("vistaOrdenDeProduccion", vistaOrdenDeProduccion);
        }
        return vistaOrdenDeProduccion;
    }

    private VistaPedido devolverVistaPedido() {
        VistaPedido vistaPedido = (VistaPedido) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaPedido");
        if (vistaPedido == null) {
            vistaPedido = new VistaPedido();
            vistaPedido.instanciarGestor();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("vistaPedido", vistaPedido);
        }
        return vistaPedido;
    }

    public void setearFechaInicioLimiteParaAsignacion() {
        if (detalleArbolSeleccionado != null && fabricaSeleccionada != null) {
            if (ordenDeProduccionSeleccionada != null) {
                if (detalleArbolSeleccionado instanceof DetalleTreeTablePMPPorTipoYModeloYPedidoDetallePMPDetalle) {
                    PMPDetalle pmpDetalle = (PMPDetalle) detalleArbolSeleccionado.getObjeto();
                    if (!ordenDeProduccionSeleccionada.getMuebleTipo().equals(pmpDetalle.getMuebleModelo().getMuebleTipo())) {
                        script = "alert('La orden de producción elegida no corresponde al tipo de mueble elegido.');";
                        actualizarBotones();
                        return;
                    }
                } else if (detalleArbolSeleccionado instanceof DetalleTreeTablePMPPorTipoYModeloYPedidoDetalleMuebleTipo) {
                    MuebleTipo muebleTipo = (MuebleTipo) detalleArbolSeleccionado.getObjeto();
                    if (!ordenDeProduccionSeleccionada.getMuebleTipo().equals(muebleTipo)) {
                        script = "alert('La orden de producción elegida no corresponde al tipo de mueble elegido.');";
                        actualizarBotones();
                        return;
                    }
                } else if (detalleArbolSeleccionado instanceof DetalleTreeTablePMPPorTipoYModeloYPedidoDetallePedidoDetalle) {
                    PedidoDetalle pedidoDetalle = (PedidoDetalle) detalleArbolSeleccionado.getObjeto();
                    if (!ordenDeProduccionSeleccionada.getMuebleTipo().equals(pedidoDetalle.getMuebleModelo().getMuebleTipo())) {
                        script = "alert('La orden de producción elegida no corresponde al tipo de mueble elegido.');";
                        actualizarBotones();
                        return;
                    }
                }
                if ((detalleArbolSeleccionado instanceof DetalleTreeTablePMPPorTipoYModeloYPedidoDetalleMuebleTipo
                        && ordenDeProduccionSeleccionada.tieneCapacidadPara(hashMapTipoPMPDetalle.get((MuebleTipo) detalleArbolSeleccionado.getObjeto())))
                        || ((detalleArbolSeleccionado instanceof DetalleTreeTablePMPPorTipoYModeloYPedidoDetallePMPDetalle
                        || detalleArbolSeleccionado instanceof DetalleTreeTablePMPPorTipoYModeloYPedidoDetallePedidoDetalle)
                        && ordenDeProduccionSeleccionada.tieneCapacidadPara((Asignable) this.detalleArbolSeleccionado.getObjeto()))) {
                    script = "dialogoFechasEstimadasAsignacionOP.show();";
                    pasarDePMPAOrdenDeProduccion(false);
                    Date fechaMinimaEnBaseARequerimientosMaterialesYTiemposProveedor = devolverFechaMinimaEnBaseARequerimientosMaterialesYTiemposProveedor();
                    if (this.ordenDeProduccionSeleccionada.getFechaInicioEstimada().after(fechaMinimaEnBaseARequerimientosMaterialesYTiemposProveedor)) {
                        this.fechaInicioEstimadaAsignacionOrdenDeProduccion = this.ordenDeProduccionSeleccionada.getFechaInicioEstimada();
                    } else {
                        this.fechaInicioEstimadaAsignacionOrdenDeProduccion = fechaMinimaEnBaseARequerimientosMaterialesYTiemposProveedor;
                    }

                    Calendar calendario = Calendar.getInstance();
                    calendario.setTime(this.fechaInicioEstimadaAsignacionOrdenDeProduccion);
                    if (calendario.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                        calendario.add(Calendar.DATE, 2);
                    } else if (calendario.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                        calendario.add(Calendar.DATE, 1);
                    }
                    this.fechaInicioEstimadaAsignacionOrdenDeProduccion = calendario.getTime();

                    Integer tiempoNecesarioProduccionEnMinutos = devolverTiempoNecesarioProduccionEnMinutos();
                    Float tiempoHoras = new Float(tiempoNecesarioProduccionEnMinutos.floatValue() / 60.0);
                    Integer diasTotal = (Integer) tiempoHoras.intValue() / 9;
                    if (diasTotal * 9 < tiempoHoras) {
                        diasTotal++;
                    }
                    Integer cantidadSemanasAAgregar = 0;
                    if (diasTotal > 5) {
                        cantidadSemanasAAgregar = diasTotal / 5;
                        diasTotal = diasTotal % 5;
                    }
                    calendario.setTime(this.fechaInicioEstimadaAsignacionOrdenDeProduccion);
                    calendario.add(Calendar.DATE, cantidadSemanasAAgregar * 7);
                    calendario.add(Calendar.DATE, diasTotal);
                    if (calendario.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                        calendario.add(Calendar.DATE, 2);
                    } else if (calendario.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                        calendario.add(Calendar.DATE, 1);
                    }
                    this.fechaFinEstimadaAsignacionOrdenDeProduccion = calendario.getTime();

                    //this.fechaFinEstimadaAsignacionOrdenDeProduccion = this.ordenDeProduccionSeleccionada.getFechaFinEstimada();
                    // TODO mmm aca faltaría que lo de reservado, tenga en cuenta si ya se había reservado antes..
                    // por ejemplo, si antes había reservado 10 de a1, y ahora puedo reservar 5, en total voy a tener reservado 15, no 5
                    this.requerimientoMateriales = this.ordenDeProduccionSeleccionada.generarActualizacionRequerimientosMateriales();

                    // Dejo la ordenDeProduccionSeleccionada como estaba antes
                    this.ordenDeProduccionSeleccionada = devolverVistaOrdenDeProduccion().actualizarOrdenDeProduccionDesdeBD(this.ordenDeProduccionSeleccionada);
                } else {
                    script = "alert('La orden de producción elegida no tiene capacidad disponible de producción para el item elegido.');";
                }
            }
        } else {
            script = "alert('Por favor, seleccione un item para asiganr a una Orden de Producción.');";
        }
    }

    private VistaArticuloProveedor devolverVistaArticuloPrecioProveedor() {
        VistaArticuloProveedor vistaArticuloProveedor = (VistaArticuloProveedor) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaArticuloProveedor");
        if (vistaArticuloProveedor == null) {
            vistaArticuloProveedor = new VistaArticuloProveedor();
            vistaArticuloProveedor.instanciarGestor();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("vistaArticuloProveedor", vistaArticuloProveedor);
        }
        return vistaArticuloProveedor;
    }

    private Collection<PMPDetalle> devolverPMPDetallesDeDetalleArbolSeleccionado() {
        Collection<PMPDetalle> pmpDetalles = null;
        if (detalleArbolSeleccionado instanceof DetalleTreeTablePMPPorTipoYModeloYPedidoDetallePMPDetalle) {
            pmpDetalles = new ArrayList<PMPDetalle>();
            pmpDetalles.add((PMPDetalle) detalleArbolSeleccionado.getObjeto());
        } else if (detalleArbolSeleccionado instanceof DetalleTreeTablePMPPorTipoYModeloYPedidoDetalleMuebleTipo) {
            pmpDetalles = hashMapTipoPMPDetalle.get((MuebleTipo) detalleArbolSeleccionado.getObjeto());
            Iterator<PMPDetalle> iterator = pmpDetalles.iterator();

            // No se tienen que procesar aquellos que no tengan la estructura de producto definida
            while (iterator.hasNext()) {
                PMPDetalle pmpDetalle = iterator.next();
                if (!pmpDetalle.getMuebleModelo().tieneEstructuraProductoDefinida()) {
                    iterator.remove();
                }
            }
        } else if (detalleArbolSeleccionado instanceof DetalleTreeTablePMPPorTipoYModeloYPedidoDetallePedidoDetalle) {
            PedidoDetalle pedidoDetalle = (PedidoDetalle) detalleArbolSeleccionado.getObjeto();
            // Creo uno temporal, para pasar este PedidoDetalle solo
            PMPDetalle pmpDetalle = new PMPDetalle();
            pmpDetalle.setMuebleModelo(pedidoDetalle.getMuebleModelo());
            pmpDetalle.setCantidadPorAsignar(pedidoDetalle.getCantidad());
            Collection<PedidoDetalle> lista = new ArrayList<PedidoDetalle>();
            lista.add(pedidoDetalle);
            pmpDetalle.setPedidoDetalles(lista);
            pmpDetalles = new ArrayList<PMPDetalle>();
            pmpDetalles.add(pmpDetalle);
        }
        return pmpDetalles;
    }

    private Date devolverFechaMinimaEnBaseARequerimientosMaterialesYTiemposProveedor() {
        return devolverFechaMinimaEnBaseARequerimientosMaterialesYTiemposProveedor(this.ordenDeProduccionSeleccionada);
    }

    private Date devolverFechaMinimaEnBaseARequerimientosMaterialesYTiemposProveedor(OrdenDeProduccion ordenDeProduccion) {
        Collection<RequerimientoMaterialOrdenDeProduccion> requerimientosMateriales = ordenDeProduccion.generarRequerimientosMateriales();
        Integer cantidadDiasQueMasSeVaATardar = 0;
        for (RequerimientoMaterial rm : requerimientosMateriales) {
            Articulo articulo = (Articulo) rm.getItemRequerible();
            Collection<Proveedor> proveedoresArticulo = devolverVistaArticuloPrecioProveedor().devolverProveedoresArticulo(articulo);
            for (Proveedor proveedor : proveedoresArticulo) {
                if (proveedor.getHabilitado()) {
                    if (proveedor.getTiempoEntregaEnDias() != null && proveedor.getTiempoEntregaEnDias() > cantidadDiasQueMasSeVaATardar) {
                        cantidadDiasQueMasSeVaATardar = proveedor.getTiempoEntregaEnDias();
                    }
                }
            }
        }
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(new Date());
        calendario.add(Calendar.DATE, cantidadDiasQueMasSeVaATardar); // aca le sumo al dia de hoy, el tiempo de tardanza mayor encontrado
        return calendario.getTime();
    }

    private Integer devolverTiempoNecesarioProduccionEnMinutos() {
        if (detalleArbolSeleccionado == null) {
            throw new AssertionError("No hay un detalle del arbol seleccionado");
        }
        HashMap<MuebleModelo, BigDecimal> hashMap = new HashMap<MuebleModelo, BigDecimal>();
        if (detalleArbolSeleccionado instanceof DetalleTreeTablePMPPorTipoYModeloYPedidoDetallePMPDetalle) {
            PMPDetalle pmpDetalle = (PMPDetalle) detalleArbolSeleccionado.getObjeto();
            hashMap.put(pmpDetalle.getMuebleModelo(), pmpDetalle.getCantidadPorAsignar());
        } else if (detalleArbolSeleccionado instanceof DetalleTreeTablePMPPorTipoYModeloYPedidoDetalleMuebleTipo) {
            ArrayList<PMPDetalle> pmpDetalles = hashMapTipoPMPDetalle.get((MuebleTipo) detalleArbolSeleccionado.getObjeto());
            Iterator<PMPDetalle> iterator = pmpDetalles.iterator();

            // No se tienen que procesar aquellos que no tengan la estructura de producto definida
            while (iterator.hasNext()) {
                PMPDetalle pmpDetalle = iterator.next();
                hashMap.put(pmpDetalle.getMuebleModelo(), pmpDetalle.getCantidadPorAsignar());
            }
        } else if (detalleArbolSeleccionado instanceof DetalleTreeTablePMPPorTipoYModeloYPedidoDetallePedidoDetalle) {
            PedidoDetalle pedidoDetalle = (PedidoDetalle) detalleArbolSeleccionado.getObjeto();
            // Creo uno temporal, para pasar este PedidoDetalle solo
            PMPDetalle pmpDetalle = new PMPDetalle();
            pmpDetalle.setMuebleModelo(pedidoDetalle.getMuebleModelo());
            pmpDetalle.setCantidadPorAsignar(pedidoDetalle.getCantidad());
            Collection<PedidoDetalle> lista = new ArrayList<PedidoDetalle>();
            lista.add(pedidoDetalle);
            pmpDetalle.setPedidoDetalles(lista);
            hashMap.put(pmpDetalle.getMuebleModelo(), pmpDetalle.getCantidadPorAsignar());
        }
        Set<Entry<MuebleModelo, BigDecimal>> entrySet = hashMap.entrySet();
        Integer totalMinutos = 0;
        for (Entry<MuebleModelo, BigDecimal> entrada : entrySet) {
            MuebleModelo muebleModelo = entrada.getKey();
            totalMinutos += muebleModelo.calcularCantidadMinutosProduccion() * entrada.getValue().intValue();
        }
        return totalMinutos;
    }
}
