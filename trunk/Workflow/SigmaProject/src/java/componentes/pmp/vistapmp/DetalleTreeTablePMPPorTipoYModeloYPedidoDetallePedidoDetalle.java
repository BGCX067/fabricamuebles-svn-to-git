/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package componentes.pmp.vistapmp;

import entidades.PedidoDetalle;
import java.text.DateFormat;
import java.util.Locale;

/**
 *
 * @author Zaba
 */
public class DetalleTreeTablePMPPorTipoYModeloYPedidoDetallePedidoDetalle implements DetalleTreeTablePMPPorTipoYModeloYPedidoDetalle {
    private PedidoDetalle pedidoDetalle;

    public DetalleTreeTablePMPPorTipoYModeloYPedidoDetallePedidoDetalle(PedidoDetalle pedidoDetalle) {
        this.pedidoDetalle = pedidoDetalle;
    }

    public String getNombre() {
        return "-";
    }

    public String getCantidadPorAsignar() {
        return pedidoDetalle.getCantidad().toString();
    }

    public String getFechaPedido() {
        return DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()).format(pedidoDetalle.getPedido().getFechaCreacion());
    }

    public String getPrioridadAsignada() {
        if (pedidoDetalle.getPedido().getPrioridad() != null) {
            return pedidoDetalle.getPedido().getPrioridad().toString();
        }
        return "-";
    }

    public String getCliente() {
        return pedidoDetalle.getPedido().getEntidad().getNombre();
    }

    @Override
    public String toString() {
        return new StringBuilder("Modelo: ").append(pedidoDetalle.getMuebleModelo().getNombre()).append(". Cliente: ").append(getCliente()).append(". Fecha pedido: ").append(getFechaPedido()).append(". Cantidad: ").append(getCantidadPorAsignar()).toString();
    }

    public Object getObjeto() {
        return this.pedidoDetalle;
    }

    public ContenidoParametrosColumnaMarcado getContenidoParametrosColumnaMarcado() {
        return new ContenidoParametrosColumnaMarcado(this);
    }

}
