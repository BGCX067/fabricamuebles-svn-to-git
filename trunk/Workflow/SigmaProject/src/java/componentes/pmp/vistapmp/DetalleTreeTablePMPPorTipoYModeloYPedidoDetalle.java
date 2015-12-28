/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package componentes.pmp.vistapmp;

/**
 *
 * @author Zaba
 */
public interface DetalleTreeTablePMPPorTipoYModeloYPedidoDetalle {

    public String getNombre();
    public String getCantidadPorAsignar();
    public String getFechaPedido();
    public String getPrioridadAsignada();
    public String getCliente();
    public Object getObjeto();
    public ContenidoParametrosColumnaMarcado getContenidoParametrosColumnaMarcado();

}
