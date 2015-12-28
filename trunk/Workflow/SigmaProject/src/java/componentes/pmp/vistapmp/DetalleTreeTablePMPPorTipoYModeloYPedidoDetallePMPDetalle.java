/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package componentes.pmp.vistapmp;

import componentes.pmp.VistaPMP;
import entidades.PMPDetalle;
import java.math.BigDecimal;
import javax.faces.context.FacesContext;

/**
 *
 * @author Zaba
 */
public class DetalleTreeTablePMPPorTipoYModeloYPedidoDetallePMPDetalle implements DetalleTreeTablePMPPorTipoYModeloYPedidoDetalle {
    private PMPDetalle pmpDetalle;
    private BigDecimal cantidadPorAsignar;

    public DetalleTreeTablePMPPorTipoYModeloYPedidoDetallePMPDetalle(PMPDetalle pmpDetalle) {
        this.pmpDetalle = pmpDetalle;
        VistaPMP vistaPMP = (VistaPMP) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaPMP");
        if (vistaPMP == null) {
            vistaPMP = new VistaPMP();
            vistaPMP.instanciarGestor();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("vistaPMP", vistaPMP);
        }
        this.cantidadPorAsignar = pmpDetalle.getCantidadPorAsignar();
    }

    public String getNombre() {
        return pmpDetalle.getMuebleModelo().getNombre();
    }

    public String getCantidadPorAsignar() {
        return cantidadPorAsignar.toString();
    }

    public String getFechaPedido() {
        return "-";
    }

    public String getPrioridadAsignada() {
        return "-";
    }

    public String getCliente() {
        return "-";
    }

    @Override
    public String toString() {
        return new StringBuilder("Modelo: ").append(pmpDetalle.getMuebleModelo().getNombre()).append(". Cantidad: ").append(cantidadPorAsignar).toString();
    }

    public Object getObjeto() {
        return this.pmpDetalle;
    }

    public ContenidoParametrosColumnaMarcado getContenidoParametrosColumnaMarcado() {
        return new ContenidoParametrosColumnaMarcado(this);
    }
}
