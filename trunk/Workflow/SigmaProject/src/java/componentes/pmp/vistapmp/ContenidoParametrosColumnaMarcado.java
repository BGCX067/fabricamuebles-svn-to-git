/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes.pmp.vistapmp;

import componentes.actividad.VistaActividad;
import componentes.pmp.VistaPMP;
import entidades.MuebleTipo;
import entidades.PMPDetalle;
import entidades.PedidoDetalle;
import java.util.Collection;
import javax.faces.context.FacesContext;

/**
 *
 * @author Zaba
 */
public class ContenidoParametrosColumnaMarcado {

    private final DetalleTreeTablePMPPorTipoYModeloYPedidoDetalle detalle;
    private Boolean esSeleccionable;
    private boolean soloAlgunosHabilitados = false;

    public ContenidoParametrosColumnaMarcado(DetalleTreeTablePMPPorTipoYModeloYPedidoDetalle detalle) {
        this.detalle = detalle;
        this.esSeleccionable = esSeleccionable();
    }

    public String getAjax() {
        if (esSeleccionable) {
            return "true";
        } else {
            return "false";
        }
    }

    public String getUpdate() {
        if (esSeleccionable) {
            return "itemMarcado panelTablaOP botonesAbajo";
        } else {
            return null;
        }
    }

    public String getTitle() {
        if (esSeleccionable && !this.soloAlgunosHabilitados) {
            return "Marcar para asignar";
        } else if (esSeleccionable && this.soloAlgunosHabilitados) {
            return "Marcar para asignar - Solo se procesarán aquellos modelos que tengan una estructura definida.";
        } else if (detalle.getObjeto() instanceof MuebleTipo) {
            return "Ninguno de los modelos de mueble de este tipo tiene una estructura definida.";
        } else {
            return "El modelo no tiene una estructura definida. Haga click aquí para definir una.";
        }
    }

    public String getAction() {
        if (esSeleccionable) {
            return null;
        } else if (detalle.getObjeto() instanceof MuebleTipo) {
            return null;
        } else {
            return ("registrarDatosEstructura?faces-redirect=true");
        }
    }

    public String getGraphicImageName() {
        if (esSeleccionable) {
            return "accept-icon.png";
        } else {
            return "warning-icon.png";
        }
    }

    public void setPropertyActionListenerTarget(DetalleTreeTablePMPPorTipoYModeloYPedidoDetalle detalle) {
        if (esSeleccionable) {
            VistaPMP vistaPMP = (VistaPMP) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaPMP");
            vistaPMP.setDetalleArbolSeleccionado(detalle);
        } else {
            VistaActividad vistaActividad = (VistaActividad) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaActividad");
            if (vistaActividad == null) {
                vistaActividad = new VistaActividad();
                vistaActividad.instanciarGestor();
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("vistaActividad", vistaActividad);
            }
            try {
                vistaActividad.setMuebleSeleccionado(((PMPDetalle) detalle.getObjeto()).getMuebleModelo());
            } catch (ClassCastException ex) {
                try {
                    vistaActividad.setMuebleSeleccionado(((PedidoDetalle) detalle.getObjeto()).getMuebleModelo());
                } catch (ClassCastException e) {
                }
            }
        }
    }

    private boolean esSeleccionable() {
        if (detalle instanceof DetalleTreeTablePMPPorTipoYModeloYPedidoDetalleMuebleTipo) {
            MuebleTipo muebleTipo = (MuebleTipo) detalle.getObjeto();
            VistaPMP vistaPMP = (VistaPMP) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaPMP");
            Collection<PMPDetalle> pmpDetalles = vistaPMP.devolverPMPDetallesMueblesTipo(muebleTipo);
            boolean algunoEsSeleccionable = false;
            for (PMPDetalle pmpDetalle : pmpDetalles) {
                if (pmpDetalle.getMuebleModelo().tieneEstructuraProductoDefinida()) {
                    algunoEsSeleccionable = true;
                    break;
                }
            }
            if (!algunoEsSeleccionable) {
                return false;
            }
            this.soloAlgunosHabilitados = true;
            // aca se tendría que dejar seleccionar, y pasar solo los que
        } else if (detalle instanceof DetalleTreeTablePMPPorTipoYModeloYPedidoDetallePMPDetalle) {
            PMPDetalle pmpDetalle = (PMPDetalle) detalle.getObjeto();
            if (!pmpDetalle.getMuebleModelo().tieneEstructuraProductoDefinida()) {
                return false;
            }
        } else if (detalle instanceof DetalleTreeTablePMPPorTipoYModeloYPedidoDetallePedidoDetalle) {
            PedidoDetalle pedidoDetalle = (PedidoDetalle) detalle.getObjeto();
            if (!pedidoDetalle.getMuebleModelo().tieneEstructuraProductoDefinida()) {
                return false;
            }
        }
        return true;
    }
}
