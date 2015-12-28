/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datosprueba;

import componentes.pmp.VistaPMP;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.*;
import entidades.*;
import java.math.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.logging.Level;
import javax.faces.context.FacesContext;

/**
 *
 * @author Zaba
 */
@Stateless
public class GestorDatosPrueba {

    @PersistenceContext
    EntityManager em;
    
    public void cargarDatosPrueba2() {
    }
    
/*
    public void cargarDatosPrueba() {
        Proveedor proveedor1 = new Proveedor();
        Cliente cliente1 = new Cliente();
        Cliente cliente2 = new Cliente();
        Factura factura1 = new Factura();
        Factura factura2 = new Factura();
        Pedido pedido1 = new Pedido();
        Pedido pedido2 = new Pedido();
        Presupuesto presupuesto1 = new Presupuesto();
        Presupuesto presupuesto2 = new Presupuesto();
        Localidad l = em.find(Localidad.class, new Integer(1538)); // COLONIA CAROYA //1560 cordoba
        Direccion d = new Direccion();
        d.setCalle("Calle 4 (S)");
        d.setLocalidad(l);
        d.setTipo("Casa");
        d.setNumero(179);
        d.setEntidad(proveedor1);
        ArrayList<Direccion> direcciones = new ArrayList<Direccion>();
        direcciones.add(d);
        Telefono t = new Telefono();
        t.setNumero("(03525) 463190");
        t.setTipo("Casa");
        t.setEntidad(proveedor1);
        ArrayList<Telefono> telefonos = new ArrayList<Telefono>();
        proveedor1.setDirecciones(direcciones);
        proveedor1.setTelefonos(telefonos);
        proveedor1.setNombre("Gian Franco Zabarino");
        proveedor1.setNroCUIT("20-93368131-9");
        proveedor1.setHabilitado(true);
        proveedor1.setTiempoEntregaEnDias(10);
        Proveedor proveedor2 = new Proveedor();
        proveedor2.setNombre("Tiziano Zabarino");
        proveedor2.setNroCUIT("20-93371471-4");
        proveedor2.setHabilitado(true);
        proveedor2.setTiempoEntregaEnDias(15);
        LinkedHashSet<ArticuloPresentacionPrecioProveedor> apps1 = new LinkedHashSet<ArticuloPresentacionPrecioProveedor>();
        Set<ArticuloPresentacionPrecioProveedor> apps2 = new HashSet<ArticuloPresentacionPrecioProveedor>();
        for (int i = 0; i < 100; i++) {
            Insumo art = new Insumo();
            art.setDescripcion("Este es el Insumo " + i);
            art.setNombre("Insumo " + i);
            art.setStockActual(100 - i);
            art.setStockReposicion(i > 10 ? i : 10);
            art.setStockMinimo(5);
            em.persist(art);
            ArticuloPresentacionPrecioProveedor app = new ArticuloPresentacionPrecioProveedor();
            app.setArticulo(art);
            app.setPrecio(new BigDecimal(i * 2 + 100).setScale(2));
            if (i % 2 == 0) {
                app.setProveedor(proveedor1);
                apps1.add(app);
            } else {
                app.setProveedor(proveedor2);
                apps2.add(app);
            }
        }
        proveedor1.setPrecioArticulos(apps1);
        proveedor2.setPrecioArticulos(apps2);
        em.persist(proveedor1);
        em.persist(proveedor2);
        em.persist(d);
        em.persist(t);
        // datos para probar lo de madera

        MaderaTipo maderaTipo = new MaderaTipo();
        maderaTipo.setNombre("Pino");
        em.persist(maderaTipo);
        Madera m = new Madera();
        m.setAlto(1);
        m.setAncho(2);
        m.setLargoOriginal(8);
        m.setMaderaTipo(maderaTipo);
        em.persist(m);
        m = new Madera();
        m.setAlto(1);
        m.setAncho(6);
        m.setLargoOriginal(16);
        m.setMaderaTipo(maderaTipo);
        em.persist(m);
        Set<MaderaTipoPrecioProveedor> maderaTipoPrecioProveedores = new HashSet<MaderaTipoPrecioProveedor>();
        MaderaTipoPrecioProveedor maderaTipoPrecioProveedor = new MaderaTipoPrecioProveedor();
        maderaTipoPrecioProveedor.setPrecioPieCubico(new BigDecimal(50));
        MaderaTipoPrecioProveedorPk id = new MaderaTipoPrecioProveedorPk();
        id.setProveedor(proveedor1);
        id.setMaderaTipo(maderaTipo);
        maderaTipoPrecioProveedor.setId(id);
        maderaTipoPrecioProveedores.add(maderaTipoPrecioProveedor);
        proveedor1.setPrecioTiposMadera(maderaTipoPrecioProveedores);
        ArticuloPresentacionPrecioProveedor articuloPrecioProveedor = new ArticuloPresentacionPrecioProveedor();
        articuloPrecioProveedor.setProveedor(proveedor1);
        articuloPrecioProveedor.setArticulo(m);
        articuloPrecioProveedor.setPrecio(m.calcularPiesCubicos().multiply(maderaTipoPrecioProveedor.getPrecioPieCubico()));
        proveedor1.getPrecioArticulos().add(articuloPrecioProveedor);
        // datos para definir unas cuantas ordenes de producción ya hechas

        Fabrica fabrica = new Fabrica();
        fabrica.setNombre("Fabrica de Muebles Interna");
        fabrica.setEncargado(em.createQuery("select e from Entidad e where e.nombre = 'Tiziano Zabarino'", Entidad.class).getSingleResult());
        fabrica.setInterna(true);
        em.persist(fabrica);

        OrdenDeProduccion pp = new OrdenDeProduccion();
        pp.setFabrica(fabrica);
        try {
            pp.setFechaInicioEstimada(new SimpleDateFormat("dd/MM/yyyy").parse("01/10/2010"));
            pp.setFechaFinEstimada(new SimpleDateFormat("dd/MM/yyyy").parse("31/10/2010"));
        } catch (ParseException ex) {
            Logger.getLogger(GestorDatosPrueba.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<OrdenDeProduccionDetalle> ordenDeProduccionDetalles = new ArrayList<OrdenDeProduccionDetalle>();

        MuebleTipo muebleTipo = new MuebleTipo();
        muebleTipo.setNombre("Silla");
        em.persist(muebleTipo);

        pp.setMuebleTipo(muebleTipo);

        MuebleModelo muebleModelo = new MuebleModelo();
        muebleModelo.setNombre("Silla S-MN-01");
        muebleModelo.setPrecioVenta(new BigDecimal("99"));
        muebleModelo.setDescripcion("");
        muebleModelo.setMuebleTipo(muebleTipo);

        Collection<RequerimientoMaterial> requerimientosMateriales1 = new ArrayList<RequerimientoMaterial>();
        Collection<RequerimientoMaterial> requerimientosMateriales2 = new ArrayList<RequerimientoMaterial>();
        
        MuebleParte muebleParte = new MuebleParte();
        muebleParte.setNombre("parte1");
        
        Articulo articulo = (Articulo) em.createQuery("select a from Articulo a where a.nombre = 'Insumo 0'").getSingleResult();
        RequerimientoMaterial requerimientoMaterial = new RequerimientoMaterial();
        requerimientoMaterial = new RequerimientoMaterial();
        requerimientoMaterial.setItemRequerible(articulo);
        requerimientoMaterial.setCantidadRequerida(5);
        requerimientosMateriales2.add(requerimientoMaterial);
        articulo = (Articulo) em.createQuery("select a from Articulo a where a.nombre = 'Insumo 3'").getSingleResult();
        requerimientoMaterial = new RequerimientoMaterial();
        requerimientoMaterial.setItemRequerible(articulo);
        requerimientoMaterial.setCantidadRequerida(5);
        requerimientosMateriales2.add(requerimientoMaterial);

        muebleParte.setRequerimientosMateriales(requerimientosMateriales2);

        requerimientoMaterial = new RequerimientoMaterial();
        requerimientoMaterial.setCantidadRequerida(3);
        requerimientoMaterial.setItemRequerible(muebleParte);
        requerimientosMateriales1.add(requerimientoMaterial);

        requerimientoMaterial = new RequerimientoMaterial();
        requerimientoMaterial.setItemRequerible(articulo);
        requerimientoMaterial.setCantidadRequerida(5);
        requerimientosMateriales1.add(requerimientoMaterial);
        muebleModelo.setRequerimientosMateriales(requerimientosMateriales1);
        
        em.persist(muebleModelo);
        OrdenDeProduccionDetalle ordenDeProduccionDetalle = new OrdenDeProduccionDetalle();
        ordenDeProduccionDetalle.setCantidad(5);
        ordenDeProduccionDetalle.setMuebleModelo(muebleModelo);
        ordenDeProduccionDetalles.add(ordenDeProduccionDetalle);
        muebleModelo = new MuebleModelo();
        muebleModelo.setNombre("Silla S-MN-02");
        muebleModelo.setPrecioVenta(new BigDecimal("199.99"));
        muebleModelo.setDescripcion("");
        muebleModelo.setMuebleTipo(muebleTipo);
        em.persist(muebleModelo);
        ordenDeProduccionDetalle = new OrdenDeProduccionDetalle();
        ordenDeProduccionDetalle.setCantidad(10);
        ordenDeProduccionDetalle.setMuebleModelo(muebleModelo);
        ordenDeProduccionDetalles.add(ordenDeProduccionDetalle);
        pp.setDetalles(ordenDeProduccionDetalles);

        ArrayList<RequerimientoMaterialOrdenDeProduccion> requerimientoMateriales = new ArrayList<RequerimientoMaterialOrdenDeProduccion>();

        RequerimientoMaterialOrdenDeProduccion rm = new RequerimientoMaterialOrdenDeProduccion();
        rm.setCantidadRequerida(10);
        rm.setItemRequerible((Articulo) em.createQuery("select m from Madera m where m.alto = 1 and m.ancho = 6 and m.largoOriginal = 16").getSingleResult());
        requerimientoMateriales.add(rm);
        rm = new RequerimientoMaterialOrdenDeProduccion();
        rm.setCantidadRequerida(20);
        rm.setItemRequerible((Articulo) em.createQuery("select m from Madera m where m.alto = 1 and m.ancho = 2 and m.largoOriginal = 8").getSingleResult());
        requerimientoMateriales.add(rm);
        pp.setRequerimientoMateriales(requerimientoMateriales);
        em.persist(pp);

        fabrica = new Fabrica();
        fabrica.setNombre("Fabrica de Muebles Externa nº 1");
        fabrica.setEncargado(em.createQuery("select e from Entidad e where e.nombre = 'Gian Franco Zabarino'", Entidad.class).getSingleResult());
        em.persist(fabrica);
        FabricaMuebleTipoCapacidadMaxima fabricaMuebleTipoCapacidadMaxima = new FabricaMuebleTipoCapacidadMaxima();
        fabricaMuebleTipoCapacidadMaxima.setFabrica(fabrica);
        fabricaMuebleTipoCapacidadMaxima.setMuebleTipo(muebleTipo);
        fabricaMuebleTipoCapacidadMaxima.setCapacidadMaxima(50);
        em.persist(fabricaMuebleTipoCapacidadMaxima);
        pp = new OrdenDeProduccion();
        ((OrdenDeProduccion)pp).setFabrica(fabrica);
        try {
            pp.setFechaInicioEstimada(new SimpleDateFormat("dd/MM/yyyy").parse("01/10/2010"));
            pp.setFechaFinEstimada(new SimpleDateFormat("dd/MM/yyyy").parse("31/11/2010"));
        } catch (ParseException ex) {
            Logger.getLogger(GestorDatosPrueba.class.getName()).log(Level.SEVERE, null, ex);
        }

        ordenDeProduccionDetalles = new ArrayList<OrdenDeProduccionDetalle>();

        ordenDeProduccionDetalle = new OrdenDeProduccionDetalle();
        ordenDeProduccionDetalle.setCantidad(20);
        ordenDeProduccionDetalle.setMuebleModelo(em.createQuery("select mm from MuebleModelo mm where mm.nombre = 'Silla S-MN-01'", MuebleModelo.class).getSingleResult());
        ordenDeProduccionDetalles.add(ordenDeProduccionDetalle);
        pp.setDetalles(ordenDeProduccionDetalles);

        pp.setMuebleTipo(muebleTipo);

        pp.generarYGuardarRequerimientosMateriales();
        em.persist(pp);

        // datos prueba remito

        Remito r = new Remito();
        r.setEntidad(proveedor1);
        try {
            r.setFechaEmision(new SimpleDateFormat("dd/MM/yyyy").parse("01/09/2010"));
        } catch (ParseException ex) {
            Logger.getLogger(GestorDatosPrueba.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<RemitoDetalle> remitoDetalles = new ArrayList<RemitoDetalle>();
        RemitoDetalle rd = new RemitoDetalleCompra();
        rd.setCantidad(15);
        ((RemitoDetalleCompra) rd).setArticulo(m);
        remitoDetalles.add(rd);
        r.setDetalles(remitoDetalles);
        r.setTipoCompra();
        r.setEstado(Remito.ESTADO_NO_FACTURADO);
        r.setNumero("1");
        em.persist(r);
        r = new Remito();
        r.setEntidad(proveedor1);
        try {
            r.setFechaEmision(new SimpleDateFormat("dd/MM/yyyy").parse("01/09/2010"));
        } catch (ParseException ex) {
            Logger.getLogger(GestorDatosPrueba.class.getName()).log(Level.SEVERE, null, ex);
        }
        remitoDetalles = new ArrayList<RemitoDetalle>();
        rd = new RemitoDetalleCompra();
        rd.setCantidad(20);
        ((RemitoDetalleCompra) rd).setCantidadFacturada(5);
        ((RemitoDetalleCompra) rd).setArticulo(m);
        remitoDetalles.add(rd);
        r.setDetalles(remitoDetalles);
        r.setTipoCompra();
        r.setEstado(Remito.ESTADO_FACTURADO_PARCIAL);
        r.setNumero("2");
        em.persist(r);


        /////////////////////////////////

        cliente1.setDirecciones(direcciones);
        cliente1.setTelefonos(telefonos);
        cliente1.setNombre("Cristian Borges");
        cliente1.setNroCUIT("20-28196504-9");
        cliente1.setHabilitado(true);
        cliente1.setDni("28196504");
        em.persist(cliente1);

        cliente2.setDirecciones(direcciones);
        cliente2.setTelefonos(telefonos);
        cliente2.setNombre("Fernando Borges");
        cliente2.setNroCUIT("20-29658986-9");
        cliente2.setHabilitado(true);
        cliente2.setDni("29658986");
        em.persist(cliente2);


        MuebleModelo muebleModelo1 = new MuebleModelo();
        muebleModelo1.setNombre("Silla S-MN-05");
        muebleModelo1.setPrecioVenta(new BigDecimal("199.99"));
        muebleModelo1.setStockActual(15);
        muebleModelo1.setDescripcion("");
        muebleModelo1.setMuebleTipo(muebleTipo);
        em.persist(muebleModelo1);
        muebleTipo = new MuebleTipo();
        muebleTipo.setNombre("Mesa");
        em.persist(muebleTipo);
        MuebleModelo muebleModelo2 = new MuebleModelo();
        muebleModelo2.setNombre("Mesa S-MN-12");
        muebleModelo2.setPrecioVenta(new BigDecimal("245.5"));
        muebleModelo2.setStockActual(10);
        muebleModelo2.setDescripcion("");
        muebleModelo2.setMuebleTipo(muebleTipo);
        em.persist(muebleModelo2);

        fabricaMuebleTipoCapacidadMaxima = new FabricaMuebleTipoCapacidadMaxima();
        fabricaMuebleTipoCapacidadMaxima.setFabrica(fabrica);
        fabricaMuebleTipoCapacidadMaxima.setMuebleTipo(muebleTipo);
        fabricaMuebleTipoCapacidadMaxima.setCapacidadMaxima(30);
        em.persist(fabricaMuebleTipoCapacidadMaxima);
        factura1.setTipoVenta();
        factura1.setEntidad(cliente1);
        try {
            factura1.setFechaCreacion(new SimpleDateFormat("dd/MM/yyyy").parse("01/09/2010"));
        } catch (ParseException ex) {
            Logger.getLogger(GestorDatosPrueba.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<FacturaDetalle> facturaDetalles = new ArrayList<FacturaDetalle>();
        FacturaDetalle facturaVenta = new FacturaDetalleVenta();
        facturaVenta.setCantidad(2);
        facturaVenta.setPrecio(muebleModelo1.getPrecioVenta());
        ((FacturaDetalleVenta) facturaVenta).setMuebleModelo(muebleModelo1);
        facturaDetalles.add(facturaVenta);
        factura1.setDetalles(facturaDetalles);
        factura1.calcularTotal();
        factura1.setNumero("0001-00000001");
        em.persist(factura1);

        factura2.setTipoVenta();
        factura2.setEntidad(cliente1);
        try {
            factura2.setFechaCreacion(new SimpleDateFormat("dd/MM/yyyy").parse("04/09/2010"));
        } catch (ParseException ex) {
            Logger.getLogger(GestorDatosPrueba.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<FacturaDetalle> facturaDetalles2 = new ArrayList<FacturaDetalle>();
        FacturaDetalle facturaVenta2 = new FacturaDetalleVenta();
        facturaVenta2.setCantidad(6);
        facturaVenta2.setPrecio(muebleModelo2.getPrecioVenta());
        ((FacturaDetalleVenta) facturaVenta2).setMuebleModelo(muebleModelo2);
        facturaDetalles2.add(facturaVenta2);
        factura2.setDetalles(facturaDetalles2);
        factura2.calcularTotal();
        factura2.setNumero("0001-00000002");
        em.persist(factura2);



        pedido1.setEntidad(cliente1);
        try {
            pedido1.setFechaCreacion(new SimpleDateFormat("dd/MM/yyyy").parse("04/09/2010"));
        } catch (ParseException ex) {
            Logger.getLogger(GestorDatosPrueba.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<PedidoDetalle> pedidoDetalles1 = new ArrayList<PedidoDetalle>();
        PedidoDetalle pedidoVenta1 = new PedidoDetalle();
        pedidoVenta1.setCantidad(6);
        pedidoVenta1.setPrecioUnitario(muebleModelo2.getPrecioVenta());
        pedidoVenta1.setMuebleModelo(muebleModelo2);
        pedidoVenta1.setPedido(pedido1);
        //pedidoVenta1.setPedido(pedido1);
        pedidoDetalles1.add(pedidoVenta1);
        pedido1.setDetalles(pedidoDetalles1);
        pedido1.calcularTotal();
        pedido1.setNumero(124);
        pedido1.setPrioridad(15);
        em.persist(pedido1);

        VistaPMP vistaPMP = (VistaPMP) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaPMP");
        if (vistaPMP == null) {
            vistaPMP = new VistaPMP();
            vistaPMP.instanciarGestor();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("vistaPMP", vistaPMP);
        }
        vistaPMP.agregarPedidosDetalleAPMP(pedido1.getDetalles());

/////////////Pedido2
         pedido2.setEntidad(cliente2);
        try {
            pedido2.setFechaCreacion(new SimpleDateFormat("dd/MM/yyyy").parse("04/09/2010"));
        } catch (ParseException ex) {
            Logger.getLogger(GestorDatosPrueba.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<PedidoDetalle> pedidoDetalles2 = new ArrayList<PedidoDetalle>();
        PedidoDetalle pedidoVenta2 = new PedidoDetalle();
        pedidoVenta2.setCantidad(3);
        pedidoVenta2.setPrecioUnitario(muebleModelo1.getPrecioVenta());
        pedidoVenta2.setMuebleModelo(muebleModelo1);
        pedidoVenta2.setPedido(pedido2);
        //pedidoVenta2.setPedido(pedido2);
        pedidoDetalles2.add(pedidoVenta2);
        pedido2.setDetalles(pedidoDetalles2);
        pedido2.calcularTotal();
        pedido2.setNumero(14);
        em.persist(pedido2);

        vistaPMP.agregarPedidosDetalleAPMP(pedido2.getDetalles());

        //////////presupuesto
        presupuesto1.setEntidad(cliente1);
        try {
            presupuesto1.setFechaCreacion(new SimpleDateFormat("dd/MM/yyyy").parse("04/09/2010"));
        } catch (ParseException ex) {
            Logger.getLogger(GestorDatosPrueba.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<PresupuestoDetalle> presupuestoDetalles1 = new ArrayList<PresupuestoDetalle>();
        PresupuestoDetalle presupuestoVenta1 = new PresupuestoDetalle();
        presupuestoVenta1.setCantidad(3);
        presupuestoVenta1.setPrecioUnitario(muebleModelo2.getPrecioVenta());
        presupuestoVenta1.setMuebleModelo(muebleModelo2);
        presupuestoDetalles1.add(presupuestoVenta1);
        presupuesto1.setDetalles(presupuestoDetalles1);
        presupuesto1.calcularTotal();
        presupuesto1.setNumero(1);
        try {
            presupuesto1.setFechaEntregaEstimada(new SimpleDateFormat("dd/MM/yyyy").parse("25/09/2010"));
            presupuesto1.setFechaVigencia(new SimpleDateFormat("dd/MM/yyyy").parse("01/12/2010"));
        } catch (ParseException ex) {
            Logger.getLogger(GestorDatosPrueba.class.getName()).log(Level.SEVERE, null, ex);
        }
        presupuesto1.setUsado(Boolean.FALSE);
        em.persist(presupuesto1);

           //////////presupuesto2
        presupuesto2.setEntidad(cliente2);
        try {
            presupuesto2.setFechaCreacion(new SimpleDateFormat("dd/MM/yyyy").parse("04/09/2010"));
        } catch (ParseException ex) {
            Logger.getLogger(GestorDatosPrueba.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<PresupuestoDetalle> presupuestoDetalles2 = new ArrayList<PresupuestoDetalle>();
        PresupuestoDetalle presupuestoVenta2 = new PresupuestoDetalle();
        presupuestoVenta2.setCantidad(4);
        presupuestoVenta2.setPrecioUnitario(muebleModelo1.getPrecioVenta());
        presupuestoVenta2.setMuebleModelo(muebleModelo1);
        presupuestoDetalles2.add(presupuestoVenta2);
        presupuesto2.setDetalles(presupuestoDetalles2);
        presupuesto2.calcularTotal();
        presupuesto2.setNumero(5);
        try {
            presupuesto2.setFechaEntregaEstimada(new SimpleDateFormat("dd/MM/yyyy").parse("25/09/2010"));
            presupuesto2.setFechaVigencia(new SimpleDateFormat("dd/MM/yyyy").parse("01/12/2010"));
        } catch (ParseException ex) {
            Logger.getLogger(GestorDatosPrueba.class.getName()).log(Level.SEVERE, null, ex);
        }
        presupuesto2.setUsado(Boolean.TRUE);
        em.persist(presupuesto2);

    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////
    public void cargarDatosPrueba2(){
        Proveedor proveedor1 = new Proveedor();
        Cliente cliente1 = new Cliente();
        Cliente cliente2 = new Cliente();
        Factura factura1 = new Factura();
        Factura factura2 = new Factura();
        Pedido pedido1 = new Pedido();
        Pedido pedido2 = new Pedido();
        Presupuesto presupuesto1 = new Presupuesto();
        Presupuesto presupuesto2 = new Presupuesto();
        Insumo insumo = new Insumo();
        Localidad l = em.find(Localidad.class, new Integer(1560)); //1538 COLONIA CAROYA //1560 cordoba
        Direccion d = new Direccion();
       
        d.setCalle("Jujuy");
        d.setLocalidad(l);
        d.setTipo("Trabajo");
        d.setNumero(2921);
        d.setEntidad(proveedor1);
        ArrayList<Direccion> direcciones = new ArrayList<Direccion>();
        direcciones.add(d);
        Telefono t = new Telefono();
        t.setNumero("(0351) 4710632");
        t.setTipo("Oficina");
        t.setEntidad(proveedor1);
        ArrayList<Telefono> telefonos = new ArrayList<Telefono>();
        proveedor1.setDirecciones(direcciones);
        proveedor1.setTelefonos(telefonos);
        proveedor1.setNombre("Pampa Group S.A.");
        proveedor1.setNroCUIT("30-71027735-0");
        proveedor1.setHabilitado(true);
        proveedor1.setTiempoEntregaEnDias(10);
        LinkedHashSet<ArticuloPresentacionPrecioProveedor> apps = new LinkedHashSet<ArticuloPresentacionPrecioProveedor>();
        proveedor1.setPrecioArticulos(apps);
        em.persist(proveedor1);
        em.persist(d);
        em.persist(t);

        ////************
        ArticuloPresentacionPrecioProveedor articuloPrecioProveedor = new ArticuloPresentacionPrecioProveedor();
        Set<MaderaTipoPrecioProveedor> maderaTipoPrecioProveedores = new HashSet<MaderaTipoPrecioProveedor>();
        MaderaTipo maderaTipo = new MaderaTipo();
        maderaTipo.setNombre("Pino");
        em.persist(maderaTipo);
        Madera m = new Madera();
        m.setAlto(1);
        m.setAncho(3);
        m.setLargoOriginal(10);
        m.setMaderaTipo(maderaTipo);
        em.persist(m);
        //
        MaderaTipoPrecioProveedor maderaTipoPrecioProveedor = new MaderaTipoPrecioProveedor();
        maderaTipoPrecioProveedor.setPrecioPieCubico(new BigDecimal("2.20"));
        MaderaTipoPrecioProveedorPk id = new MaderaTipoPrecioProveedorPk();
        id.setProveedor(proveedor1);
        id.setMaderaTipo(maderaTipo);
        maderaTipoPrecioProveedor.setId(id);
        maderaTipoPrecioProveedores.add(maderaTipoPrecioProveedor);
        proveedor1.setPrecioTiposMadera(maderaTipoPrecioProveedores);
        articuloPrecioProveedor.setProveedor(proveedor1);
        articuloPrecioProveedor.setArticulo(m);
        articuloPrecioProveedor.setPrecio(m.calcularPiesCubicos().multiply(maderaTipoPrecioProveedor.getPrecioPieCubico()));
        proveedor1.getPrecioArticulos().add(articuloPrecioProveedor);
        //
        m = new Madera();
        m.setAlto(4);
        m.setAncho(4);
        m.setLargoOriginal(10);
        m.setMaderaTipo(maderaTipo);
        em.persist(m);
        ////////
        articuloPrecioProveedor = new ArticuloPresentacionPrecioProveedor();
        articuloPrecioProveedor.setProveedor(proveedor1);
        articuloPrecioProveedor.setArticulo(m);
        articuloPrecioProveedor.setPrecio(m.calcularPiesCubicos().multiply(maderaTipoPrecioProveedor.getPrecioPieCubico()));
        proveedor1.getPrecioArticulos().add(articuloPrecioProveedor);
        /////////
        m = new Madera();
        m.setAlto(1);
        m.setAncho(6);
        m.setLargoOriginal(9);
        m.setMaderaTipo(maderaTipo);
        em.persist(m);
        articuloPrecioProveedor = new ArticuloPresentacionPrecioProveedor();
        articuloPrecioProveedor.setProveedor(proveedor1);
        articuloPrecioProveedor.setArticulo(m);
        articuloPrecioProveedor.setPrecio(m.calcularPiesCubicos().multiply(maderaTipoPrecioProveedor.getPrecioPieCubico()));
        proveedor1.getPrecioArticulos().add(articuloPrecioProveedor);
        /////
        m = new Madera();
        m.setAlto(1);
        m.setAncho(8);
        m.setLargoOriginal(9);
        m.setMaderaTipo(maderaTipo);
        em.persist(m);
        articuloPrecioProveedor = new ArticuloPresentacionPrecioProveedor();
        articuloPrecioProveedor.setProveedor(proveedor1);
        articuloPrecioProveedor.setArticulo(m);
        articuloPrecioProveedor.setPrecio(m.calcularPiesCubicos().multiply(maderaTipoPrecioProveedor.getPrecioPieCubico()));
        proveedor1.getPrecioArticulos().add(articuloPrecioProveedor);
        
       
        /////************
     
        Proveedor proveedor2 = new Proveedor();
        /////////////
        d=new Direccion();
        l = em.find(Localidad.class, new Integer(1560)); //1538 COLONIA CAROYA //1560 cordoba
        d.setCalle("Av. Alem");
        d.setLocalidad(l);
        d.setTipo("Trabajo");
        d.setNumero(1666);
        d.setEntidad(proveedor2);
        direcciones = new ArrayList<Direccion>();
        direcciones.add(d);
        t = new Telefono();
        t.setNumero("(0351) 4749410");
        t.setTipo("Oficina");
        t.setEntidad(proveedor2);
        telefonos = new ArrayList<Telefono>();
        proveedor2.setDirecciones(direcciones);
        proveedor2.setTelefonos(telefonos);
        proveedor2.setNombre("Szumik");
        proveedor2.setNroCUIT("30-51767659-0");
        proveedor2.setHabilitado(true);
        proveedor2.setTiempoEntregaEnDias(15);
         //carga de insumo

        LinkedHashSet<ArticuloPresentacionPrecioProveedor> apps1 = new LinkedHashSet<ArticuloPresentacionPrecioProveedor>();
        Set<ArticuloPresentacionPrecioProveedor> apps2 = new HashSet<ArticuloPresentacionPrecioProveedor>();
        Insumo art = new Insumo();
        art.setDescripcion("Lustrado de muebles");
        art.setNombre("Pintura asfáltica en cc");
        art.setStockActual(10);
        art.setStockReposicion(2);
        art.setStockMinimo(5);
        em.persist(art);
        ArticuloPresentacionPrecioProveedor app = new ArticuloPresentacionPrecioProveedor();
        app.setArticulo(art);
        app.setPrecio(new BigDecimal("14.4").setScale(2));
        app.setProveedor(proveedor2);
        apps1.add(app);
        art = new Insumo();
        art.setDescripcion("Lijado fino prelustrado");
        art.setNombre("Lija al agua n.120 en unidades");
        art.setStockActual(10);
        art.setStockReposicion(2);
        art.setStockMinimo(5);
        em.persist(art);
        app = new ArticuloPresentacionPrecioProveedor();
        app.setArticulo(art);
        app.setPrecio(new BigDecimal("2.10").setScale(2));
        app.setProveedor(proveedor2);
        apps1.add(app);
        proveedor2.setPrecioArticulos(apps1);
        em.persist(proveedor2);
        em.persist(d);
        em.persist(t);
        
        /////////////
        proveedor2 = new Proveedor();
        d=new Direccion();
        l = em.find(Localidad.class, new Integer(1560)); //1538 COLONIA CAROYA //1560 cordoba
        d.setCalle("David Luque");
        d.setLocalidad(l);
        d.setTipo("Trabajo");
        d.setNumero(1400);
        d.setEntidad(proveedor2);
        direcciones = new ArrayList<Direccion>();
        direcciones.add(d);
        t = new Telefono();
        t.setNumero("(0351) 4284728");
        t.setTipo("Oficina");
        t.setEntidad(proveedor2);
        telefonos = new ArrayList<Telefono>();
        proveedor2.setDirecciones(direcciones);
        proveedor2.setTelefonos(telefonos);
        proveedor2.setNombre("Battello");
        proveedor2.setNroCUIT("30-70790443-3");
        proveedor2.setHabilitado(true);
        proveedor2.setTiempoEntregaEnDias(15);
        /////////////carga de articulos
        apps1 = new LinkedHashSet<ArticuloPresentacionPrecioProveedor>();
        apps2 = new HashSet<ArticuloPresentacionPrecioProveedor>();
            art = new Insumo();
            art.setDescripcion("Filtro de partículas descartable para utilizar con mascara");
            art.setNombre("Prefiltro para polvos y niebla en unidades");
            art.setStockActual(10);
            art.setStockReposicion(2);
            art.setStockMinimo(5);
            em.persist(art);
            app = new ArticuloPresentacionPrecioProveedor();
            app.setArticulo(art);
            app.setPrecio(new BigDecimal("7.70").setScale(2));
            app.setProveedor(proveedor2);
            apps1.add(app);
            art = new Insumo();
            art.setDescripcion("Lubricante y anticorrosivo para herramientas");
            art.setNombre("Penetril en ml");
            art.setStockActual(8);
            art.setStockReposicion(2);
            art.setStockMinimo(5);
            em.persist(art);
            app = new ArticuloPresentacionPrecioProveedor();
            app.setArticulo(art);
            app.setPrecio(new BigDecimal("27.68").setScale(2));
            app.setProveedor(proveedor2);
            apps1.add(app);
            proveedor2.setPrecioArticulos(apps1);

        em.persist(proveedor2);
        em.persist(d);
        em.persist(t);

         proveedor2 = new Proveedor();
        d=new Direccion();
        l = em.find(Localidad.class, new Integer(1560)); //1538 COLONIA CAROYA //1560 cordoba
        d.setCalle("Gangora");
        d.setLocalidad(l);
        d.setTipo("Trabajo");
        d.setNumero(1632);
        d.setEntidad(proveedor2);
        direcciones = new ArrayList<Direccion>();
        direcciones.add(d);
        t = new Telefono();
        t.setNumero("(0351) 4512511");
        t.setTipo("Oficina");
        t.setEntidad(proveedor2);
        telefonos = new ArrayList<Telefono>();
        proveedor2.setDirecciones(direcciones);
        proveedor2.setTelefonos(telefonos);
        proveedor2.setNombre("Albino Maderas");
        proveedor2.setNroCUIT("30-71013299-9");
        proveedor2.setHabilitado(true);
        proveedor2.setTiempoEntregaEnDias(15);
        apps = new LinkedHashSet<ArticuloPresentacionPrecioProveedor>();
        proveedor2.setPrecioArticulos(apps);
        em.persist(proveedor2);
        em.persist(d);
        em.persist(t);
        //cargo la ultima madera que se guardo arriba
        articuloPrecioProveedor = new ArticuloPresentacionPrecioProveedor();
        maderaTipoPrecioProveedores = new HashSet<MaderaTipoPrecioProveedor>();
        maderaTipoPrecioProveedor = new MaderaTipoPrecioProveedor();
        maderaTipoPrecioProveedor.setPrecioPieCubico(new BigDecimal("2.35"));
        id = new MaderaTipoPrecioProveedorPk();
        id.setProveedor(proveedor2);
        id.setMaderaTipo(maderaTipo);
        maderaTipoPrecioProveedor.setId(id);
        maderaTipoPrecioProveedores.add(maderaTipoPrecioProveedor);
        proveedor2.setPrecioTiposMadera(maderaTipoPrecioProveedores);
        articuloPrecioProveedor.setProveedor(proveedor2);
        articuloPrecioProveedor.setArticulo(m);
        articuloPrecioProveedor.setPrecio(m.calcularPiesCubicos().multiply(maderaTipoPrecioProveedor.getPrecioPieCubico()));
        proveedor2.getPrecioArticulos().add(articuloPrecioProveedor);
        em.merge(proveedor2);

        
        proveedor2 = new Proveedor();
        d=new Direccion();
        l = em.find(Localidad.class, new Integer(1560)); //1538 COLONIA CAROYA //1560 cordoba
        d.setCalle("Francisco de Toledo");
        d.setLocalidad(l);
        d.setTipo("Trabajo");
        d.setNumero(1632);
        d.setEntidad(proveedor2);
        direcciones = new ArrayList<Direccion>();
        direcciones.add(d);
        t = new Telefono();
        t.setNumero("(0351) 4734794");
        t.setTipo("Oficina");
        t.setEntidad(proveedor2);
        telefonos = new ArrayList<Telefono>();
        proveedor2.setDirecciones(direcciones);
        proveedor2.setTelefonos(telefonos);
        proveedor2.setNombre("Lencisa Herrajes");
        proveedor2.setNroCUIT("30-70125682-3");
        proveedor2.setHabilitado(true);
        proveedor2.setTiempoEntregaEnDias(15);
        /////////////carga de articulos
        apps1 = new LinkedHashSet<ArticuloPresentacionPrecioProveedor>();
        apps2 = new HashSet<ArticuloPresentacionPrecioProveedor>();
            art = new Insumo();
            art.setDescripcion("Encolado de piezas para su union ");
            art.setNombre("Adhesivo vinilico en ml");
            art.setStockActual(20000);
            art.setStockReposicion(2);
            art.setStockMinimo(5);
            em.persist(art);
            app = new ArticuloPresentacionPrecioProveedor();
            app.setArticulo(art);
            app.setPrecio(new BigDecimal(44).setScale(2));
            app.setProveedor(proveedor2);
            apps1.add(app);

            art = new Insumo();
            art.setDescripcion("Clavo de 75 mm en cajas de 100 unidades");
            art.setNombre("Clavo de 75 mm en unidades");
            art.setStockActual(5000);
            art.setStockReposicion(2);
            art.setStockMinimo(5);
            em.persist(art);
            app = new ArticuloPresentacionPrecioProveedor();
            app.setArticulo(art);
            app.setPrecio(new BigDecimal(25).setScale(2));
            app.setProveedor(proveedor2);
            apps1.add(app);
           
        proveedor2.setPrecioArticulos(apps1);
        em.persist(proveedor2);
        em.persist(d);
        em.persist(t);
        

        //datos para encargado de fabrica
        Encargado en = new Encargado();
        en.setNombre("Jose Quinteros");
        en.setDni("24568923");
        em.persist(en);
        en = new Encargado();
        en.setNombre("Alfredo Chilenato");
        en.setDni("30264895");
       
        // datos para definir unas cuantas ordenes de producción ya hechas

        Fabrica fabrica = new Fabrica();
        fabrica.setNombre("Fabrica de Muebles Interna");
        fabrica.setEncargado(em.createQuery("select e from Entidad e where e.nombre = 'Jose Quinteros'", Entidad.class).getSingleResult());
        fabrica.setInterna(true);
        em.persist(fabrica);

        OrdenDeProduccion pp = new OrdenDeProduccion();
        pp.setFabrica(fabrica);
        try {
            pp.setFechaInicioEstimada(new SimpleDateFormat("dd/MM/yyyy").parse("10/11/2010"));
            pp.setFechaFinEstimada(new SimpleDateFormat("dd/MM/yyyy").parse("31/11/2010"));
        } catch (ParseException ex) {
            Logger.getLogger(GestorDatosPrueba.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<OrdenDeProduccionDetalle> ordenDeProduccionDetalles = new ArrayList<OrdenDeProduccionDetalle>();

        MuebleTipo muebleTipo = new MuebleTipo();
        muebleTipo.setNombre("Cama");
        em.persist(muebleTipo);
        MuebleModelo muebleModelo = new MuebleModelo();
        muebleModelo.setNombre("Cama Monterrey 4x4");
        muebleModelo.setPrecioVenta(new BigDecimal("580"));
        muebleModelo.setDescripcion("Cama de 1,40mts de ancho y 1,90mts de largo, con patas de 4x4 pulgadas y travesaños de 2x4 pulgadas, con tableros y parrilla de palo amarillo de 1,5 pulgadas, con bulones incluidos.");
        muebleModelo.setMuebleTipo(muebleTipo);
        muebleModelo.setStockActual(0);
        em.persist(muebleModelo);
        muebleTipo = new MuebleTipo();
        muebleTipo.setNombre("Mesa");
        em.persist(muebleTipo);
        muebleModelo = new MuebleModelo();
        muebleModelo.setNombre("Mesa patas rectas de 1,20mts de largo");
        muebleModelo.setPrecioVenta(new BigDecimal("290"));
        muebleModelo.setDescripcion("Mesa de 0,80mts de altura, 0,75mts de ancho y 1,20mts de largo, con patas de 4x4 pulgadas y tapa de 1,5 pulgadas.");
        muebleModelo.setMuebleTipo(muebleTipo);
        muebleModelo.setStockActual(0);
        em.persist(muebleModelo);
        muebleTipo = new MuebleTipo();
        muebleTipo.setNombre("Biblioteca");
        em.persist(muebleTipo);
        muebleModelo = new MuebleModelo();
        muebleModelo.setNombre("Biblioteca de 0,60mts de ancho");
        muebleModelo.setPrecioVenta(new BigDecimal("380"));
        muebleModelo.setDescripcion("Biblioteca de 1,90mts de altura, 0,60mts de ancho y 0,30 de profundidad, con 3 estantes y 4 cajones.");
        muebleModelo.setMuebleTipo(muebleTipo);
        muebleModelo.setStockActual(4);
        em.persist(muebleModelo);
        muebleTipo = new MuebleTipo();
        muebleTipo.setNombre("Chifonier");
        em.persist(muebleTipo);
        muebleModelo = new MuebleModelo();
        muebleModelo.setNombre("Chifonier con 6 cajones ");
        muebleModelo.setPrecioVenta(new BigDecimal("450"));
        muebleModelo.setDescripcion("Chifonier de 1,50mts de altura, 0,70mts de ancho y 0,50mts de profundidad, con 6 cajones, tableros en los laterales y zócalo en la base.");
        muebleModelo.setMuebleTipo(muebleTipo);
        muebleModelo.setStockActual(14);
        em.persist(muebleModelo);
        muebleTipo = new MuebleTipo();
        muebleTipo.setNombre("Comoda");
        em.persist(muebleTipo);
        muebleModelo = new MuebleModelo();
        muebleModelo.setNombre("Comoda con 4 cajones y 2 puertas ");
        muebleModelo.setPrecioVenta(new BigDecimal("450"));
        muebleModelo.setDescripcion("Cómoda de 0,90mts de altura, 1,40mts de ancho y 0,50mts de profundidad, con 4 cajones y 2 puertas con tableros y bisagras regulables, con tableros en los laterales y zócalo en la base.");
        muebleModelo.setMuebleTipo(muebleTipo);
        muebleModelo.setStockActual(20);
        em.persist(muebleModelo);
        muebleTipo = new MuebleTipo();
        muebleTipo.setNombre("Silla");
        em.persist(muebleTipo);

        pp.setMuebleTipo(muebleTipo);

        muebleModelo = new MuebleModelo();
        muebleModelo.setNombre("Silla S-MN-01");
        muebleModelo.setPrecioVenta(new BigDecimal("99"));
        muebleModelo.setDescripcion("");
        muebleModelo.setMuebleTipo(muebleTipo);

        Collection<RequerimientoMaterial> requerimientosMateriales1 = new ArrayList<RequerimientoMaterial>();
        Collection<RequerimientoMaterial> requerimientosMateriales2 = new ArrayList<RequerimientoMaterial>();

        MuebleParte muebleParte = new MuebleParte();
        muebleParte.setNombre("parte1");

        Articulo articulo = (Articulo) em.createQuery("select a from Articulo a where a.nombre = 'Lija al agua n.120 en unidades'").getSingleResult();
        RequerimientoMaterial requerimientoMaterial = new RequerimientoMaterial();
        requerimientoMaterial = new RequerimientoMaterial();
        requerimientoMaterial.setItemRequerible(articulo);
        requerimientoMaterial.setCantidadRequerida(5);
        requerimientosMateriales2.add(requerimientoMaterial);
        requerimientosMateriales1.add(requerimientoMaterial);
        articulo = (Articulo) em.createQuery("select a from Articulo a where a.nombre = 'Adhesivo vinilico en ml'").getSingleResult();
        requerimientoMaterial = new RequerimientoMaterial();
        requerimientoMaterial.setItemRequerible(articulo);
        requerimientoMaterial.setCantidadRequerida(250);
        requerimientosMateriales2.add(requerimientoMaterial);
        requerimientosMateriales1.add(requerimientoMaterial);
        //
        articulo = (Articulo) em.createQuery("select m from Madera m where m.alto = 4 and m.ancho = 4 and m.largoOriginal = 10").getSingleResult();
        requerimientoMaterial = new RequerimientoMaterial();
        requerimientoMaterial.setItemRequerible(articulo);
        requerimientoMaterial.setCantidadRequerida(2);
        requerimientosMateriales2.add(requerimientoMaterial);
        requerimientosMateriales1.add(requerimientoMaterial);
        //
        articulo = (Articulo) em.createQuery("select m from Madera m where m.alto = 1 and m.ancho = 6 and m.largoOriginal = 9").getSingleResult();
        requerimientoMaterial = new RequerimientoMaterial();
        requerimientoMaterial.setItemRequerible(articulo);
        requerimientoMaterial.setCantidadRequerida(2);
        requerimientosMateriales2.add(requerimientoMaterial);
        requerimientosMateriales1.add(requerimientoMaterial);
        //
        articulo = (Articulo) em.createQuery("select a from Articulo a where a.nombre = 'Clavo de 75 mm en unidades'").getSingleResult();
        requerimientoMaterial = new RequerimientoMaterial();
        requerimientoMaterial.setItemRequerible(articulo);
        requerimientoMaterial.setCantidadRequerida(15);
        requerimientosMateriales2.add(requerimientoMaterial);
        requerimientosMateriales1.add(requerimientoMaterial);
        muebleModelo.setRequerimientosMateriales(requerimientosMateriales1);
        em.persist(muebleModelo);//guarda la Silla S-MN-01
        muebleModelo = new MuebleModelo();
        muebleModelo = (MuebleModelo) em.createQuery("select m from MuebleModelo m where m.nombre = 'Mesa patas rectas de 1,20mts de largo'").getSingleResult();
        muebleModelo.setRequerimientosMateriales(requerimientosMateriales2);
        //////////------
         ///////////herramientas
        HerramientaTipo h = new HerramientaTipo();
        h.setNombre("Destornillador Phillips 5mm");
        h.setDescripcion("Introducción y extracción de tornillos.");
        em.persist(h);
        h = new HerramientaTipo();
        h.setNombre("Formon 16mm");
        h.setDescripcion("Herramienta de corte y para ahuecar con mango y hoja de extremo cortante.");
        em.persist(h);
        h = new HerramientaTipo();
        h.setNombre("Taladro 10mm");
        h.setDescripcion("Mediante la inserción de distintos elementos acoplados,se transforma en una herramienta polivalente");
        em.persist(h);
        h = new HerramientaTipo();
        h.setNombre("Prensa");
        h.setDescripcion("Presionar o sujetar piezas");
        em.persist(h);
        h = new HerramientaTipo();
        h.setNombre("Tenaza forjada");
        h.setDescripcion("Sujetar piezas, cortar alambre, extraer clavos etc.");
        em.persist(h);
        h = new HerramientaTipo();
        h.setNombre("Martillo");
        h.setDescripcion("Herramienta utilizada para golpear una pieza, causando su desplazamiento o deformación");
        em.persist(h);
         ///////maquinaria
        MaquinaTipo ma = new MaquinaTipo();
        ma.setNombre("Garlopa");
        ma.setDescripcion("Escuadrar cara y canto de la madera");
        em.persist(ma);
        ma = new MaquinaTipo();
        ma.setNombre("Cepilladora");
        ma.setDescripcion("Dar el grosor a la cada de la madera");
        em.persist(ma);
        ma = new MaquinaTipo();
        ma.setNombre("Escuadradora");
        ma.setDescripcion("Dar ancho y largo a la madera");
        em.persist(ma);
        ma = new MaquinaTipo();
        ma.setNombre("Lijadora");
        ma.setDescripcion("Lijar la superficie de la madera");
        em.persist(ma);
        ma = new MaquinaTipo();
        ma.setNombre("Tupi");
        ma.setDescripcion("Realizar fresado, canales y espiga a la madera");
        em.persist(ma);
        //////////------

        //definicion y carga de actividades para el mueble mesa patas rectas de 1,20 de largo
        Collection<ActividadEspecifica> actividades = new ArrayList<ActividadEspecifica>();
        Collection<HerramientaTipo> herramientas = new ArrayList<HerramientaTipo>();
        ActividadEspecifica actividad = new ActividadEspecifica();
        actividad.setNombre("Garlopeado");
        actividad.setDescripcion("");
        actividad.setDuracionEnMinutos(18);
        MaquinaTipo maquina= new MaquinaTipo();
        HerramientaTipo herramienta = new HerramientaTipo();
        maquina = (MaquinaTipo)em.createQuery("select m from MaquinaTipo m where m.nombre = 'Garlopa'").getSingleResult();
        actividad.setMaquinaTipo(maquina);
        actividad.setSecuencia(1);
        em.persist(actividad);
        actividades.add(actividad);
        
        //actividad 2
        actividad = new ActividadEspecifica();
        actividad.setNombre("Cepillado");
        actividad.setDescripcion("");
        actividad.setDuracionEnMinutos(5);
        maquina= new MaquinaTipo();
        maquina = (MaquinaTipo)em.createQuery("select m from MaquinaTipo m where m.nombre = 'Cepilladora'").getSingleResult();
        actividad.setMaquinaTipo(maquina);
        actividad.setSecuencia(2);
        em.persist(actividad);
        actividades.add(actividad);
        
        //actividad 3
        actividad = new ActividadEspecifica();
        actividad.setNombre("Corte largo y ancho");
        actividad.setDescripcion("");
        actividad.setDuracionEnMinutos(10);
        maquina= new MaquinaTipo();
        maquina = (MaquinaTipo)em.createQuery("select m from MaquinaTipo m where m.nombre = 'Escuadradora'").getSingleResult();
        actividad.setMaquinaTipo(maquina);
        actividad.setSecuencia(3);
        em.persist(actividad);
        actividades.add(actividad);
        //actividad 4
        actividad = new ActividadEspecifica();
        actividad.setNombre("Preensamblado");
        actividad.setDescripcion("");
        actividad.setDuracionEnMinutos(18);
        herramienta = new HerramientaTipo();
        herramienta = (HerramientaTipo)em.createQuery("select e from HerramientaTipo e where e.nombre = 'Prensa'").getSingleResult();
        herramientas.add(herramienta);
        herramienta = new HerramientaTipo();
        herramienta = (HerramientaTipo)em.createQuery("select e from HerramientaTipo e where e.nombre = 'Martillo'").getSingleResult();
        herramientas.add(herramienta);
        actividad.setHerramientasTipo(herramientas);
        actividad.setSecuencia(4);
        em.persist(actividad);
        actividades.add(actividad);
        //actividad 5
        actividad = new ActividadEspecifica();
        actividad.setNombre("Fresado");
        actividad.setDescripcion("");
        actividad.setDuracionEnMinutos(20);
        maquina= new MaquinaTipo();
        maquina = (MaquinaTipo)em.createQuery("select m from MaquinaTipo m where m.nombre = 'Tupi'").getSingleResult();
        actividad.setMaquinaTipo(maquina);
        actividad.setSecuencia(5);
        em.persist(actividad);
        actividades.add(actividad);
        //actividad 6
        actividad = new ActividadEspecifica();
        actividad.setNombre("Lijado");
        actividad.setDescripcion("");
        actividad.setDuracionEnMinutos(15);
        maquina= new MaquinaTipo();
        maquina = (MaquinaTipo)em.createQuery("select m from MaquinaTipo m where m.nombre = 'Lijadora'").getSingleResult();
        actividad.setMaquinaTipo(maquina);
        actividad.setSecuencia(6);
        em.persist(actividad);
        actividades.add(actividad);
        //actividad 7
        actividad = new ActividadEspecifica();
        actividad.setNombre("Ensamblado");
        actividad.setDescripcion("");
        actividad.setDuracionEnMinutos(18);
        herramienta = new HerramientaTipo();
        herramienta = (HerramientaTipo)em.createQuery("select e from HerramientaTipo e where e.nombre = 'Prensa'").getSingleResult();
        herramientas.add(herramienta);
        herramienta = new HerramientaTipo();
        herramienta = (HerramientaTipo)em.createQuery("select e from HerramientaTipo e where e.nombre = 'Martillo'").getSingleResult();
        herramientas.add(herramienta);
        actividad.setHerramientasTipo(herramientas);
        actividad.setSecuencia(4);
        em.persist(actividad);
        actividades.add(actividad);

        muebleModelo.setActividades(actividades);//seteo de actividades para el mueble
        em.merge(muebleModelo);//guardo la estructura para la mesa patas rectas de 1,20 de largo

        OrdenDeProduccionDetalle ordenDeProduccionDetalle = new OrdenDeProduccionDetalle();
        ordenDeProduccionDetalle.setCantidad(5);
        ordenDeProduccionDetalle.setMuebleModelo(muebleModelo);
        ordenDeProduccionDetalles.add(ordenDeProduccionDetalle);
        muebleModelo = new MuebleModelo();
        muebleModelo.setNombre("Silla S-MN-02");
        muebleModelo.setPrecioVenta(new BigDecimal("199.99"));
        muebleModelo.setDescripcion("");
        muebleModelo.setMuebleTipo(muebleTipo);
        em.persist(muebleModelo);
        ordenDeProduccionDetalle = new OrdenDeProduccionDetalle();
        ordenDeProduccionDetalle.setCantidad(10);
        ordenDeProduccionDetalle.setMuebleModelo(muebleModelo);
        ordenDeProduccionDetalles.add(ordenDeProduccionDetalle);
        pp.setDetalles(ordenDeProduccionDetalles);

        ArrayList<RequerimientoMaterialOrdenDeProduccion> requerimientoMateriales = new ArrayList<RequerimientoMaterialOrdenDeProduccion>();

        RequerimientoMaterialOrdenDeProduccion rm = new RequerimientoMaterialOrdenDeProduccion();
        rm.setCantidadRequerida(2);
        rm.setItemRequerible((Articulo) em.createQuery("select m from Madera m where m.alto = 4 and m.ancho = 4 and m.largoOriginal = 10").getSingleResult());
        requerimientoMateriales.add(rm);
        rm = new RequerimientoMaterialOrdenDeProduccion();
        rm.setCantidadRequerida(3);
        rm.setItemRequerible((Articulo) em.createQuery("select m from Madera m where m.alto = 1 and m.ancho = 6 and m.largoOriginal = 9").getSingleResult());
        requerimientoMateriales.add(rm);
        //pp.setRequerimientoMateriales(requerimientoMateriales);
        pp.generarYGuardarRequerimientosMateriales();
        //em.persist(pp);


        fabrica = new Fabrica();
        fabrica.setNombre("Fabrica de Muebles Externa nº 1");
        fabrica.setEncargado(em.createQuery("select e from Entidad e where e.nombre = 'Pampa Group S.A.'", Entidad.class).getSingleResult());
        em.persist(fabrica);
        FabricaMuebleTipoCapacidadMaxima fabricaMuebleTipoCapacidadMaxima = new FabricaMuebleTipoCapacidadMaxima();
        fabricaMuebleTipoCapacidadMaxima.setFabrica(fabrica);
        fabricaMuebleTipoCapacidadMaxima.setMuebleTipo(muebleTipo);
        fabricaMuebleTipoCapacidadMaxima.setCapacidadMaxima(50);
        em.persist(fabricaMuebleTipoCapacidadMaxima);
        pp = new OrdenDeProduccion();
        ((OrdenDeProduccion)pp).setFabrica(fabrica);
        try {
            pp.setFechaInicioEstimada(new SimpleDateFormat("dd/MM/yyyy").parse("15/11/2010"));
            pp.setFechaFinEstimada(new SimpleDateFormat("dd/MM/yyyy").parse("31/11/2010"));
        } catch (ParseException ex) {
            Logger.getLogger(GestorDatosPrueba.class.getName()).log(Level.SEVERE, null, ex);
        }

        ordenDeProduccionDetalles = new ArrayList<OrdenDeProduccionDetalle>();

        ordenDeProduccionDetalle = new OrdenDeProduccionDetalle();
        ordenDeProduccionDetalle.setCantidad(47);
        ordenDeProduccionDetalle.setMuebleModelo(em.createQuery("select mm from MuebleModelo mm where mm.nombre = 'Silla S-MN-01'", MuebleModelo.class).getSingleResult());
        ordenDeProduccionDetalles.add(ordenDeProduccionDetalle);
        pp.setDetalles(ordenDeProduccionDetalles);

        pp.setMuebleTipo(muebleTipo);

        pp.generarYGuardarRequerimientosMateriales();
        em.persist(pp);

        // datos prueba remito

        Remito r = new Remito();
        r.setEntidad(proveedor1);
        try {
            r.setFechaEmision(new SimpleDateFormat("dd/MM/yyyy").parse("01/09/2010"));
        } catch (ParseException ex) {
            Logger.getLogger(GestorDatosPrueba.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<RemitoDetalle> remitoDetalles = new ArrayList<RemitoDetalle>();
        RemitoDetalle rd = new RemitoDetalleCompra();
        rd.setCantidad(15);
        ((RemitoDetalleCompra) rd).setArticulo(m);
        remitoDetalles.add(rd);
        r.setDetalles(remitoDetalles);
        r.setTipoCompra();
        r.setEstado(Remito.ESTADO_NO_FACTURADO);
        r.setNumero("1");
        em.persist(r);
        r = new Remito();
        r.setEntidad(proveedor1);
        try {
            r.setFechaEmision(new SimpleDateFormat("dd/MM/yyyy").parse("01/09/2010"));
        } catch (ParseException ex) {
            Logger.getLogger(GestorDatosPrueba.class.getName()).log(Level.SEVERE, null, ex);
        }
        remitoDetalles = new ArrayList<RemitoDetalle>();
        rd = new RemitoDetalleCompra();
        rd.setCantidad(20);
        ((RemitoDetalleCompra) rd).setCantidadFacturada(5);
        ((RemitoDetalleCompra) rd).setArticulo(m);
        remitoDetalles.add(rd);
        r.setDetalles(remitoDetalles);
        r.setTipoCompra();
        r.setEstado(Remito.ESTADO_FACTURADO_PARCIAL);
        r.setNumero("2");
        em.persist(r);


        /////////////////////////////////
        d=new Direccion();
        l = em.find(Localidad.class, new Integer(1560)); //1538 COLONIA CAROYA //1560 cordoba
        d.setCalle("Tissera");
        d.setLocalidad(l);
        d.setTipo("Trabajo");
        d.setNumero(652);
        d.setEntidad(cliente1);
        direcciones = new ArrayList<Direccion>();
        direcciones.add(d);
        t = new Telefono();
        t.setNumero("(0351) 4720711");
        t.setTipo("Casa");
        t.setEntidad(cliente1);
        telefonos = new ArrayList<Telefono>();
        cliente1.setDirecciones(direcciones);
        cliente1.setTelefonos(telefonos);
        cliente1.setNombre("Julio Macheto");
        cliente1.setNroCUIT("20-28196504-9");
        cliente1.setHabilitado(true);
        cliente1.setDni("23850159");
        em.persist(cliente1);
        em.persist(t);
        em.persist(d);

        cliente1 = new Cliente();
        d=new Direccion();
        l = em.find(Localidad.class, new Integer(1560)); //1538 COLONIA CAROYA //1560 cordoba
        d.setCalle("Charcas");
        d.setLocalidad(l);
        d.setTipo("Trabajo");
        d.setNumero(423);
        d.setEntidad(cliente1);
        direcciones = new ArrayList<Direccion>();
        direcciones.add(d);
        t = new Telefono();
        t.setNumero("(0351) 4332549");
        t.setTipo("Casa");
        t.setEntidad(cliente1);
        telefonos = new ArrayList<Telefono>();
        cliente1.setDirecciones(direcciones);
        cliente1.setTelefonos(telefonos);
        cliente1.setNombre("Cristian Ludueña");
        cliente1.setNroCUIT("20-28196504-9");
        cliente1.setHabilitado(true);
        cliente1.setDni("20588375");
        em.persist(cliente1);
        em.persist(t);
        em.persist(d);

        cliente1 = new Cliente();
        d=new Direccion();
        l = em.find(Localidad.class, new Integer(1560)); //1538 COLONIA CAROYA //1560 cordoba
        d.setCalle("Panaholma");
        d.setLocalidad(l);
        d.setTipo("Trabajo");
        d.setNumero(1563);
        d.setEntidad(cliente1);
        direcciones = new ArrayList<Direccion>();
        direcciones.add(d);
        t = new Telefono();
        t.setNumero("(0351) 4739898");
        t.setTipo("Casa");
        t.setEntidad(cliente1);
        telefonos = new ArrayList<Telefono>();
        cliente1.setDirecciones(direcciones);
        cliente1.setTelefonos(telefonos);
        cliente1.setNombre("Diego Carreño");
        cliente1.setNroCUIT("20-28196504-9");
        cliente1.setHabilitado(true);
        cliente1.setDni("30970063");
        em.persist(cliente1);
        em.persist(t);
        em.persist(d);

        cliente1 = new Cliente();
        d=new Direccion();
        l = em.find(Localidad.class, new Integer(1560)); //1538 COLONIA CAROYA //1560 cordoba
        d.setCalle("Catamarca");
        d.setLocalidad(l);
        d.setTipo("Trabajo");
        d.setNumero(196);
        d.setEntidad(cliente1);
        direcciones = new ArrayList<Direccion>();
        direcciones.add(d);
        t = new Telefono();
        t.setNumero("(0351) 4265841");
        t.setTipo("Casa");
        t.setEntidad(cliente1);
        telefonos = new ArrayList<Telefono>();
        cliente1.setDirecciones(direcciones);
        cliente1.setTelefonos(telefonos);
        cliente1.setNombre("Cristian Fernandez");
        cliente1.setNroCUIT("20-28196504-9");
        cliente1.setHabilitado(true);
        cliente1.setDni("27352159");
        em.persist(cliente1);
        em.persist(t);
        em.persist(d);

        cliente2.setDirecciones(direcciones);
        cliente2.setTelefonos(telefonos);
        cliente2.setNombre("Fernando Borges");
        cliente2.setNroCUIT("20-29658986-9");
        cliente2.setHabilitado(true);
        cliente2.setDni("29658986");
        em.persist(cliente2);


        MuebleModelo muebleModelo1 = new MuebleModelo();
        muebleModelo1.setNombre("Silla S-MN-05");
        muebleModelo1.setPrecioVenta(new BigDecimal("199.99"));
        muebleModelo1.setStockActual(15);
        muebleModelo1.setDescripcion("");
        muebleModelo1.setMuebleTipo(muebleTipo);
        em.persist(muebleModelo1);
        muebleTipo = (MuebleTipo) em.createQuery("select mt from MuebleTipo mt where mt.nombre = 'Mesa'").getSingleResult();
        MuebleModelo muebleModelo2 = new MuebleModelo();
        muebleModelo2.setNombre("Mesa S-MN-12");
        muebleModelo2.setPrecioVenta(new BigDecimal("245.5"));
        muebleModelo2.setStockActual(10);
        muebleModelo2.setDescripcion("");
        muebleModelo2.setMuebleTipo(muebleTipo);
        em.persist(muebleModelo2);

        fabricaMuebleTipoCapacidadMaxima = new FabricaMuebleTipoCapacidadMaxima();
        fabricaMuebleTipoCapacidadMaxima.setFabrica(fabrica);
        fabricaMuebleTipoCapacidadMaxima.setMuebleTipo(muebleTipo);
        fabricaMuebleTipoCapacidadMaxima.setCapacidadMaxima(30);
        em.persist(fabricaMuebleTipoCapacidadMaxima);
        factura1.setTipoVenta();
        factura1.setEntidad(cliente1);
        try {
            factura1.setFechaCreacion(new SimpleDateFormat("dd/MM/yyyy").parse("01/09/2010"));
        } catch (ParseException ex) {
            Logger.getLogger(GestorDatosPrueba.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<FacturaDetalle> facturaDetalles = new ArrayList<FacturaDetalle>();
        FacturaDetalle facturaVenta = new FacturaDetalleVenta();
        facturaVenta.setCantidad(2);
        facturaVenta.setPrecio(muebleModelo1.getPrecioVenta());
        ((FacturaDetalleVenta) facturaVenta).setMuebleModelo(muebleModelo1);
        facturaDetalles.add(facturaVenta);
        factura1.setDetalles(facturaDetalles);
        factura1.calcularTotal();
        factura1.setNumero("0001-00000001");
        em.persist(factura1);

        factura2.setTipoVenta();
        factura2.setEntidad(cliente1);
        try {
            factura2.setFechaCreacion(new SimpleDateFormat("dd/MM/yyyy").parse("04/09/2010"));
        } catch (ParseException ex) {
            Logger.getLogger(GestorDatosPrueba.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<FacturaDetalle> facturaDetalles2 = new ArrayList<FacturaDetalle>();
        FacturaDetalle facturaVenta2 = new FacturaDetalleVenta();
        facturaVenta2.setCantidad(6);
        facturaVenta2.setPrecio(muebleModelo2.getPrecioVenta());
        ((FacturaDetalleVenta) facturaVenta2).setMuebleModelo(muebleModelo2);
        facturaDetalles2.add(facturaVenta2);
        factura2.setDetalles(facturaDetalles2);
        factura2.calcularTotal();
        factura2.setNumero("0001-00000002");
        em.persist(factura2);



        pedido1.setEntidad(cliente1);
        try {
            pedido1.setFechaCreacion(new SimpleDateFormat("dd/MM/yyyy").parse("04/09/2010"));
        } catch (ParseException ex) {
            Logger.getLogger(GestorDatosPrueba.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<PedidoDetalle> pedidoDetalles1 = new ArrayList<PedidoDetalle>();
        PedidoDetalle pedidoVenta1 = new PedidoDetalle();
        pedidoVenta1.setCantidad(6);
        pedidoVenta1.setPrecioUnitario(muebleModelo2.getPrecioVenta());
        pedidoVenta1.setMuebleModelo(muebleModelo2);
        pedidoVenta1.setPedido(pedido1);
        //pedidoVenta1.setPedido(pedido1);
        pedidoDetalles1.add(pedidoVenta1);
        pedido1.setDetalles(pedidoDetalles1);
        pedido1.calcularTotal();
        pedido1.setNumero(124);
        pedido1.setPrioridad(15);
        em.persist(pedido1);

        VistaPMP vistaPMP = (VistaPMP) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaPMP");
        if (vistaPMP == null) {
            vistaPMP = new VistaPMP();
            vistaPMP.instanciarGestor();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("vistaPMP", vistaPMP);
        }
        vistaPMP.agregarPedidosDetalleAPMP(pedido1.getDetalles());

/////////////Pedido2
         pedido2.setEntidad(cliente2);
        try {
            pedido2.setFechaCreacion(new SimpleDateFormat("dd/MM/yyyy").parse("04/09/2010"));
        } catch (ParseException ex) {
            Logger.getLogger(GestorDatosPrueba.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<PedidoDetalle> pedidoDetalles2 = new ArrayList<PedidoDetalle>();
        PedidoDetalle pedidoVenta2 = new PedidoDetalle();
        pedidoVenta2.setCantidad(3);
        pedidoVenta2.setPrecioUnitario(muebleModelo1.getPrecioVenta());
        pedidoVenta2.setMuebleModelo(muebleModelo1);
        pedidoVenta2.setPedido(pedido2);
        //pedidoVenta2.setPedido(pedido2);
        pedidoDetalles2.add(pedidoVenta2);
        pedido2.setDetalles(pedidoDetalles2);
        pedido2.calcularTotal();
        pedido2.setNumero(14);
        em.persist(pedido2);

        vistaPMP.agregarPedidosDetalleAPMP(pedido2.getDetalles());

        //////////presupuesto
        presupuesto1.setEntidad(cliente1);
        try {
            presupuesto1.setFechaCreacion(new SimpleDateFormat("dd/MM/yyyy").parse("04/09/2010"));
        } catch (ParseException ex) {
            Logger.getLogger(GestorDatosPrueba.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<PresupuestoDetalle> presupuestoDetalles1 = new ArrayList<PresupuestoDetalle>();
        PresupuestoDetalle presupuestoVenta1 = new PresupuestoDetalle();
        presupuestoVenta1.setCantidad(3);
        presupuestoVenta1.setPrecioUnitario(muebleModelo2.getPrecioVenta());
        presupuestoVenta1.setMuebleModelo(muebleModelo2);
        presupuestoDetalles1.add(presupuestoVenta1);
        presupuesto1.setDetalles(presupuestoDetalles1);
        presupuesto1.calcularTotal();
        presupuesto1.setNumero(1);
        try {
            presupuesto1.setFechaEntregaEstimada(new SimpleDateFormat("dd/MM/yyyy").parse("25/09/2010"));
            presupuesto1.setFechaVigencia(new SimpleDateFormat("dd/MM/yyyy").parse("01/12/2010"));
        } catch (ParseException ex) {
            Logger.getLogger(GestorDatosPrueba.class.getName()).log(Level.SEVERE, null, ex);
        }
        presupuesto1.setUsado(Boolean.FALSE);
        em.persist(presupuesto1);

           //////////presupuesto2
        presupuesto2.setEntidad(cliente2);
        try {
            presupuesto2.setFechaCreacion(new SimpleDateFormat("dd/MM/yyyy").parse("04/09/2010"));
        } catch (ParseException ex) {
            Logger.getLogger(GestorDatosPrueba.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<PresupuestoDetalle> presupuestoDetalles2 = new ArrayList<PresupuestoDetalle>();
        PresupuestoDetalle presupuestoVenta2 = new PresupuestoDetalle();
        presupuestoVenta2.setCantidad(4);
        presupuestoVenta2.setPrecioUnitario(muebleModelo1.getPrecioVenta());
        presupuestoVenta2.setMuebleModelo(muebleModelo1);
        presupuestoDetalles2.add(presupuestoVenta2);
        presupuesto2.setDetalles(presupuestoDetalles2);
        presupuesto2.calcularTotal();
        presupuesto2.setNumero(5);
        try {
            presupuesto2.setFechaEntregaEstimada(new SimpleDateFormat("dd/MM/yyyy").parse("25/09/2010"));
            presupuesto2.setFechaVigencia(new SimpleDateFormat("dd/MM/yyyy").parse("01/12/2010"));
        } catch (ParseException ex) {
            Logger.getLogger(GestorDatosPrueba.class.getName()).log(Level.SEVERE, null, ex);
        }
        presupuesto2.setUsado(Boolean.TRUE);
        em.persist(presupuesto2);
/*
        ///////////herramientas
        HerramientaTipo h = new HerramientaTipo();
        h.setNombre("Destornillador Phillips 5mm");
        h.setDescripcion("Introducción y extracción de tornillos.");
        em.persist(h);
        h = new HerramientaTipo();
        h.setNombre("Formon 16mm");
        h.setDescripcion("Herramienta de corte y para ahuecar con mango y hoja de extremo cortante.");
        em.persist(h);
        h = new HerramientaTipo();
        h.setNombre("Taladro 10mm");
        h.setDescripcion("Mediante la inserción de distintos elementos acoplados,se transforma en una herramienta polivalente");
        em.persist(h);
        h = new HerramientaTipo();
        h.setNombre("Prensa");
        h.setDescripcion("Presionar o sujetar piezas");
        em.persist(h);
        h = new HerramientaTipo();
        h.setNombre("Tenaza forjada");
        h.setDescripcion("Sujetar piezas, cortar alambre, extraer clavos etc.");
        em.persist(h);

        ///////maquinaria
        /*
        MaquinaTipo ma = new MaquinaTipo();
        ma.setNombre("Garlopa");
        ma.setDescripcion("Escuadrar cara y canto de la madera");
        em.persist(ma);
        ma = new MaquinaTipo();
        ma.setNombre("Cepilladora");
        ma.setDescripcion("Dar el grosor a la cada de la madera");
        em.persist(ma);
        ma = new MaquinaTipo();
        ma.setNombre("Escuadradora");
        ma.setDescripcion("Dar ancho y largo a la madera");
        em.persist(ma);
        ma = new MaquinaTipo();
        ma.setNombre("Lijadora");
        ma.setDescripcion("Lijar la superficie de la madera");
        em.persist(ma);
        ma = new MaquinaTipo();
        ma.setNombre("Tupi");
        ma.setDescripcion("Realizar fresadom, canales y espiga a la madera");
        em.persist(ma);


    } 
*/
}
