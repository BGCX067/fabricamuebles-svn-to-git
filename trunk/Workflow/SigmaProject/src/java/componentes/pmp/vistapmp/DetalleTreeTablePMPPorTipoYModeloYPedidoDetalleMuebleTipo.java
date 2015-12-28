/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package componentes.pmp.vistapmp;

import componentes.pmp.VistaPMP;
import entidades.MuebleTipo;
import entidades.PMPDetalle;
import java.math.BigDecimal;
import java.util.Collection;
import javax.faces.context.FacesContext;

/**
 *
 * @author Zaba
 */
public class DetalleTreeTablePMPPorTipoYModeloYPedidoDetalleMuebleTipo implements DetalleTreeTablePMPPorTipoYModeloYPedidoDetalle {
    private MuebleTipo muebleTipo;
    private BigDecimal cantidadPorAsignar;

    public DetalleTreeTablePMPPorTipoYModeloYPedidoDetalleMuebleTipo(MuebleTipo muebleTipo) {
        this.muebleTipo = muebleTipo;
        VistaPMP vistaPMP = (VistaPMP) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaPMP");
        if (vistaPMP == null) {
            vistaPMP = new VistaPMP();
            vistaPMP.instanciarGestor();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("vistaPMP", vistaPMP);
        }
        Collection<PMPDetalle> pmpDetallesTipo = vistaPMP.devolverPMPDetallesMueblesTipo(muebleTipo);
        cantidadPorAsignar = BigDecimal.ZERO;
        for (PMPDetalle pmpDetalle : pmpDetallesTipo) {
            if (pmpDetalle.getMuebleModelo().tieneEstructuraProductoDefinida()) {
                cantidadPorAsignar = cantidadPorAsignar.add(pmpDetalle.getCantidadPorAsignar());
            }
        }
    }

    public String getNombre() {
        return muebleTipo.getNombre();
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
        return new StringBuilder("Tipo: ").append(muebleTipo.getNombre()).append(". Cantidad: ").append(cantidadPorAsignar).toString();
    }

    public Object getObjeto() {
        return this.muebleTipo;
    }

    public ContenidoParametrosColumnaMarcado getContenidoParametrosColumnaMarcado() {
        return new ContenidoParametrosColumnaMarcado(this);
    }

}
