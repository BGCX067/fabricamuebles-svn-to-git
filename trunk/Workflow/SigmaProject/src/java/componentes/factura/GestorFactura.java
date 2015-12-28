/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes.factura;

import entidades.*;
import java.math.BigDecimal;
import java.util.*;
import javax.ejb.Stateless;
import javax.persistence.*;

/**
 *
 * @author Cristian
 */
@Stateless
public class GestorFactura {

    @PersistenceContext//(type=PersistenceContextType.EXTENDED)
    EntityManager em;
    private Proveedor proveedorElegido;
    private OrdenDeCompra ordenDeCompraElegida;
    private Factura factura;

    public OrdenDeCompra getOrdenDeCompraSeleccionada() {
        return this.ordenDeCompraElegida;
    }

    public Collection<ArticuloPresentacionPrecioProveedor> devolverArticulosProveedor() {
        return em.createQuery("SELECT app from ArticuloPrecioProveedor app WHERE app.id.proveedor = :proveedor ").setParameter("proveedor", this.proveedorElegido).getResultList();
    }
    public Collection<MuebleModelo> devolverMueblesModelo() {
        return em.createQuery("SELECT mm from MuebleModelo mm", MuebleModelo.class).getResultList();
    }

    public void setOrdenDeCompraSeleccionada(OrdenDeCompra ordenDeCompraSeleccionada) {
        this.ordenDeCompraElegida = ordenDeCompraSeleccionada;
    }

    public void copiarOrdenDeCompraAFactura(OrdenDeCompra ordenDeCompraSeleccionada) {
        this.ordenDeCompraElegida = ordenDeCompraSeleccionada;
        this.factura = new Factura();
        Collection<FacturaDetalle> detalles = new LinkedList<FacturaDetalle>();
        for (OrdenDeCompraDetalle detalle : this.ordenDeCompraElegida.getDetalles()) {
            OrdenDeCompraDetalle detalleCompra = (OrdenDeCompraDetalle) detalle;
            FacturaDetalle facturaDetalle = new FacturaDetalleCompra();
            ((FacturaDetalleCompra)facturaDetalle).setPresentacion(em.merge(detalleCompra.getPresentacion()));
            facturaDetalle.setCantidad(detalleCompra.getCantidad());
            facturaDetalle.setPrecio(detalleCompra.getPrecio());
            facturaDetalle.calcularSubTotal();
            detalles.add(facturaDetalle);
        }
        this.factura.setDetalles(detalles);
    }

    public ArticuloPresentacionPrecioProveedor detalleSeleccionadoAArticuloPrecioProveedor(FacturaDetalleCompra detalle) {
        ArticuloPresentacionPrecioProveedor artPrecioProv = new ArticuloPresentacionPrecioProveedor();
        artPrecioProv.setPresentacion(em.merge(detalle.getPresentacion()));
        artPrecioProv.setPrecio(detalle.getPrecio());
        artPrecioProv.setProveedor((Proveedor)this.factura.getEntidad());
        
        return artPrecioProv;

    }

    public void crearFactura(Entidad entidad) {
        this.factura = new Factura();
        this.factura.setEntidad(entidad);
    }

    public void copiarDetallesRemitoAFactura(Remito remito) {
        Collection<FacturaDetalle> detalles;
        if (this.factura.getDetalles() == null) {
            detalles = new LinkedList<FacturaDetalle>();
        } else {
            detalles = this.factura.getDetalles();
        }
        Collection<RemitoDetalle> remitoDetalles = remito.getDetalles();
        Proveedor p = (Proveedor) remito.getEntidad();
        for (RemitoDetalle detalle : remitoDetalles) {
            // Solo se agrega el detalle que tenga cantidad pendiente de facturar
            if ((((RemitoDetalleCompra)detalle).getCantidadFacturada() != null && detalle.getCantidad().compareTo(((RemitoDetalleCompra)detalle).getCantidadFacturada()) > 0) ||
                    ((RemitoDetalleCompra)detalle).getCantidadFacturada() == null) {
                FacturaDetalle facturaDetalle = new FacturaDetalleCompra();
                ((FacturaDetalleCompra)facturaDetalle).setPresentacion(((RemitoDetalleCompra) detalle).getPresentacion());
                facturaDetalle.setCantidad(((RemitoDetalleCompra)detalle).getCantidadPendientePorFacturar());
                ArticuloPresentacionPrecioProveedorPk id = new ArticuloPresentacionPrecioProveedorPk();
                id.setPresentacion(((RemitoDetalleCompra) detalle).getPresentacion());
                id.setProveedor(p);
                ArticuloPresentacionPrecioProveedor app = em.find(ArticuloPresentacionPrecioProveedor.class, id);
                // Precio individual
                facturaDetalle.setPrecio(app.getPrecio());
                detalles.add(facturaDetalle);
            }
        }
        this.factura.setDetalles(detalles);
    }

    public void confirmarFacturaAPartirDeOrdenDeCompra() {
        this.factura.setFechaCreacion(new Date(System.currentTimeMillis()));
        this.factura.calcularTotal();
        guardarFactura();
        this.ordenDeCompraElegida.setEstado("Facturada"); // Algo que diga que la orden de compra ya ha generado una Factura
        this.ordenDeCompraElegida = em.merge(this.ordenDeCompraElegida);
    }

    public void confirmarFacturaAPartirDeRemito() {
        this.factura.setFechaCreacion(new Date(System.currentTimeMillis()));
        this.factura.calcularTotal();
        guardarFactura();
        /*
        this.ordenDeCompraElegida.setEstado("Facturada"); // Algo que diga que la orden de compra ya ha generado una Factura
        this.ordenDeCompraElegida = em.merge(this.ordenDeCompraElegida);
         */
    }

    private void guardarFactura() {
         
        Boolean tipoSeteado = false;
        for (FacturaDetalle detalle : this.factura.getDetalles()) {
            if (detalle instanceof FacturaDetalleCompra) {
                if (!tipoSeteado) {
                    tipoSeteado = true;
                    this.factura.setTipoCompra();
                   
                    
                }
                ((FacturaDetalleCompra)detalle).setPresentacion(em.merge(((FacturaDetalleCompra) detalle).getPresentacion()));
               // vistaArticuloProveedor.setArticuloPrecioProveedorSeleccionado(detalleSeleccionadoAArticuloPrecioProveedor((FacturaDetalleCompra)detalle));
            } else if (detalle instanceof FacturaDetalleVenta) {
                // TODO
            }
        }
        em.persist(this.factura);
    }

    public Factura getFacturaNoConfirmada() {
        return this.factura;
    }

    public void tomarConfirmacionDatosFactura() {
        this.factura.setFechaCreacion(new Date(System.currentTimeMillis()));
        guardarFactura();
    }

    public void tomarDetallesFactura(Collection<FacturaDetalle> detallesAgregados) {
        factura.setDetalles(detallesAgregados);
        factura.calcularTotal();
    }

    public void reiniciar() {
        this.factura = null;
        this.ordenDeCompraElegida = null;
        this.proveedorElegido = null;
    }

    public Collection<Factura> devolverFacturasCompra() {
        return em.createQuery("select f from Factura f where f.tipo = 'Compra'").getResultList();
    }
    public Collection<Factura> devolverFacturasVenta() {
        return em.createQuery("select f from Factura f where f.tipo = 'Venta'", Factura.class).getResultList();
    }
    public Collection<Factura> devolverFacturasVentaNoEntregada() {
        return em.createQuery("select f from Factura f where f.tipo = 'Venta' and f.estado = 'No Entregado'", Factura.class).getResultList();
    }

    public void actualizarEstadoFacturaAEntregada(Factura factura) {
        factura.setEstado(Factura.ESTADO_ENTREGADO);
        em.merge(factura);
    }



    public void confirmarFacturaVenta(Factura factura) {
        Collection<FacturaDetalle> facturaDetalles = factura.getDetalles();
        for (FacturaDetalle facturaDetalle : facturaDetalles) {
            MuebleModelo muebleModelo = ((FacturaDetalleVenta)facturaDetalle).getMuebleModelo();
            muebleModelo.setStockReservado(facturaDetalle.getCantidad());
            em.merge(muebleModelo);
            
        }
        factura.setTipoVenta();
        factura.setFechaCreacion(new Date());
        //factura.setNumero("2");
        em.persist(factura);
    }



     public Collection<MuebleModelo> devolverMueblesModeloAgregar(Collection <MuebleModelo> mueblesModelo) {
            if (mueblesModelo != null) {

                StringBuilder sbMuebleModelo = new StringBuilder();
                Iterator<MuebleModelo> it = mueblesModelo.iterator();
                int i = 1;
                
                for (MuebleModelo detalle : mueblesModelo)
                {
                  System.out.print(i);
                  sbMuebleModelo.append(":m").append(i).append(", ");
                  i++;
                  }
                if (sbMuebleModelo.length() > 0) {
                        sbMuebleModelo.deleteCharAt(sbMuebleModelo.length() - 1);
                        sbMuebleModelo.deleteCharAt(sbMuebleModelo.length() - 1);
                    }

                    System.out.println(sbMuebleModelo.toString());


                Query queryMuebleModelo = null;
                if (sbMuebleModelo.length() > 0) {
                    queryMuebleModelo =  em.createQuery("select m from MuebleModelo m where m not in (" + sbMuebleModelo.toString() + ")");
                } else {
                    queryMuebleModelo =  em.createQuery("select m from MuebleModelo m");
                }

                if (mueblesModelo != null) {
                    Iterator<MuebleModelo> it1 = mueblesModelo.iterator();
                    int j = 1;
                    for (MuebleModelo fd : mueblesModelo)
                    {
                       queryMuebleModelo.setParameter("m" + j, fd);
                       System.out.println("Seteo param: " + "m" + j + fd.getNombre());
                       j++;
                    }
                }
                Collection<MuebleModelo> muebleModelo = new ArrayList<MuebleModelo>();
                muebleModelo.addAll(queryMuebleModelo.getResultList());
                
                return muebleModelo;
            }
            return new ArrayList<MuebleModelo>();
        }
     public String devolverNumeroDisponibleParaFactura() {
        Collection<String> num = em.createQuery("select f.numero from Factura f where f.tipo = 'Venta')").getResultList();
        Integer mayor = -1;
        String cabecera = "";
        String nroGenerado = "";
        if(num != null){
            for(String actual : num){
                if(mayor < Integer.parseInt(actual.substring(5, 13))){
                mayor = Integer.parseInt(actual.substring(5, 13));
                cabecera = actual.substring(0, 4);
              }
            }
            mayor = mayor + 1;
            String ceros = "";
            mayor.toString().length();
            for (int i = 0; i < 8-mayor.toString().length(); i++) {
                ceros = ceros + "0";

            }
            nroGenerado = cabecera + "-" + ceros +  mayor;

        }else {
            nroGenerado = "0001-00000001";
        }

     return nroGenerado;
    }

   
}
