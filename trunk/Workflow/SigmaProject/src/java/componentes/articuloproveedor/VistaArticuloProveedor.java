/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes.articuloproveedor;

import entidades.Articulo;
import entidades.ArticuloPresentacion;
import entidades.ArticuloPresentacionPrecioProveedor;
import entidades.Insumo;
import entidades.Madera;
import entidades.MaderaTipo;
import entidades.MaderaTipoPrecioProveedor;
import entidades.MaderaTipoPrecioProveedorPk;
import entidades.Proveedor;
import generico.VistaGenerico;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Zaba
 */
@ManagedBean
@SessionScoped
public class VistaArticuloProveedor implements java.io.Serializable {

    @EJB
    private GestorArticuloProveedor gestorArticuloProveedor;
    private BigDecimal precio;
    private ArticuloPresentacionPrecioProveedor articuloPrecioProveedorSeleccionado;
    private ArticuloPresentacion articuloPresentacionAAgregarSeleccionado;
    private MaderaTipo tipoMaderaSeleccionada;
    private MaderaTipoPrecioProveedor maderaTipoPrecioProveedorSeleccionado;
    private Boolean tienePrecioMaderaTipoProveedor = false;
    private Boolean renderScript = false;
    public final static Boolean ACTUALIZAR_PRECIO_PIE_CUBICO = false;
    public final static Boolean ACTUALIZAR_PRECIO_MADERA = true;

    public BigDecimal getPrecio() {
        return precio;
    }

    public void instanciarGestor() {
        if (gestorArticuloProveedor == null) {
            try {
                InitialContext ic = new InitialContext();
                this.gestorArticuloProveedor = (GestorArticuloProveedor) ic.lookup("java:global/SigmaProject/GestorArticuloProveedor");
            } catch (NamingException ex) {
                Logger.getLogger(VistaArticuloProveedor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public MaderaTipo getTipoMaderaSeleccionada() {
        return tipoMaderaSeleccionada;
    }

    public void setTipoMaderaSeleccionada(MaderaTipo tipoMaderaSeleccionada) {
        this.tipoMaderaSeleccionada = tipoMaderaSeleccionada;
    }

    public MaderaTipoPrecioProveedor getMaderaTipoPrecioProveedorSeleccionado() {
        return maderaTipoPrecioProveedorSeleccionado;
    }

    public void setMaderaTipoPrecioProveedorSeleccionado(MaderaTipoPrecioProveedor maderaTipoPrecioProveedorSeleccionado) {
        this.maderaTipoPrecioProveedorSeleccionado = maderaTipoPrecioProveedorSeleccionado;
    }

    public ArticuloPresentacion getArticuloPresentacionAAgregarSeleccionado() {
        return articuloPresentacionAAgregarSeleccionado;
    }

    public void setArticuloPresentacionAAgregarSeleccionado(ArticuloPresentacion articuloPresentacionAAgregarSeleccionado) {
        this.articuloPresentacionAAgregarSeleccionado = articuloPresentacionAAgregarSeleccionado;
        if (this.articuloPresentacionAAgregarSeleccionado.getArticulo().getTipo().equalsIgnoreCase("Madera")) {
            this.tipoMaderaSeleccionada = ((Madera) this.articuloPresentacionAAgregarSeleccionado.getArticulo()).getMaderaTipo();
        }
        this.precio = null;
        System.out.println("setArticuloPresentacionAAgregarSeleccionado: " + articuloPresentacionAAgregarSeleccionado.getArticulo().getTipo());
    }

    public void setArticuloPrecioProveedorSeleccionado(ArticuloPresentacionPrecioProveedor articuloPrecioProveedor) {
        this.articuloPrecioProveedorSeleccionado = articuloPrecioProveedor;
        if (this.articuloPrecioProveedorSeleccionado != null && this.articuloPrecioProveedorSeleccionado.getPresentacion().getArticulo() != null
                && this.articuloPrecioProveedorSeleccionado.getPresentacion().getArticulo().getTipo().equalsIgnoreCase("Insumo")) {
            this.precio = this.articuloPrecioProveedorSeleccionado.getPrecio();
        } else if (this.articuloPrecioProveedorSeleccionado != null && this.articuloPrecioProveedorSeleccionado.getPresentacion().getArticulo() != null
                && this.articuloPrecioProveedorSeleccionado.getPresentacion().getArticulo().getTipo().equalsIgnoreCase("Madera")) {
            this.tipoMaderaSeleccionada = ((Madera) this.articuloPrecioProveedorSeleccionado.getPresentacion().getArticulo()).getMaderaTipo();
            Proveedor p = this.articuloPrecioProveedorSeleccionado.getProveedor();
            Set<MaderaTipoPrecioProveedor> precioTiposMadera = gestorArticuloProveedor.devolverPrecioTiposMaderas(p);
            Iterator<MaderaTipoPrecioProveedor> iterator = precioTiposMadera.iterator();
            while (iterator.hasNext()) {
                MaderaTipoPrecioProveedor maderaTipoPrecioProveedor = iterator.next();
                if (maderaTipoPrecioProveedor.getId().getMaderaTipo().equals(this.tipoMaderaSeleccionada)) {
                    this.maderaTipoPrecioProveedorSeleccionado = maderaTipoPrecioProveedor;
                    break;
                }
            }
        }
    }

    public void actualizarArticuloPrecioProveedor(ArticuloPresentacionPrecioProveedor articuloPrecioProveedor, Boolean queActualiza) {
        if (articuloPrecioProveedor != null && articuloPrecioProveedor.getPresentacion().getArticulo() != null
                && articuloPrecioProveedor.getPresentacion().getArticulo().getTipo().equalsIgnoreCase("Insumo")) {
            gestorArticuloProveedor.actualizarArticuloProveedorSeleccionado(articuloPrecioProveedor);
        } else if (articuloPrecioProveedor != null && articuloPrecioProveedor.getPresentacion().getArticulo() != null
                && articuloPrecioProveedor.getPresentacion().getArticulo().getTipo().equalsIgnoreCase("Madera")) {
            Madera madera = (Madera)articuloPrecioProveedor.getPresentacion().getArticulo();
            MaderaTipo maderaTipo = ((Madera) articuloPrecioProveedor.getPresentacion().getArticulo()).getMaderaTipo();
            Proveedor p = articuloPrecioProveedor.getProveedor();
            Set<MaderaTipoPrecioProveedor> precioTiposMadera = gestorArticuloProveedor.devolverPrecioTiposMaderas(p);
            Iterator<MaderaTipoPrecioProveedor> iterator = precioTiposMadera.iterator();
            while (iterator.hasNext()) { 
                MaderaTipoPrecioProveedor maderaTipoPrecioProveedor = iterator.next();
                if (maderaTipoPrecioProveedor.getId().getMaderaTipo().equals(maderaTipo)) {
                    if (queActualiza.equals(ACTUALIZAR_PRECIO_PIE_CUBICO)) {
                        maderaTipoPrecioProveedor.setPrecioPieCubico(articuloPrecioProveedor.getPrecio().divide(madera.calcularPiesCubicos(), RoundingMode.HALF_EVEN));
                    } else if (queActualiza.equals(ACTUALIZAR_PRECIO_MADERA)) {
                        articuloPrecioProveedor.setPrecio(((Madera)articuloPrecioProveedor.getPresentacion().getArticulo()).calcularPiesCubicos().multiply(maderaTipoPrecioProveedor.getPrecioPieCubico()).setScale(2, RoundingMode.HALF_EVEN));
                    }
                    gestorArticuloProveedor.actualizarPrecioPieCubicoProveedorSeleccionado(maderaTipoPrecioProveedor);
                    break;
                }
            }
        }

    }

    public void quitarArticuloProveedor(ArticuloPresentacionPrecioProveedor articuloPrecioProveedor) {
        gestorArticuloProveedor.quitarArticuloProveedor(articuloPrecioProveedor);
    }

    public void actualizarInsumoArticuloProveedorSeleccionado() {
        this.articuloPrecioProveedorSeleccionado.setPrecio(this.precio);
        gestorArticuloProveedor.actualizarArticuloProveedorSeleccionado(this.articuloPrecioProveedorSeleccionado);
        setArticuloPrecioProveedorSeleccionado(null);
    }

    public void actualizarMaderaArticuloProveedorSeleccionado() {
        gestorArticuloProveedor.actualizarPrecioPieCubicoProveedorSeleccionado(this.maderaTipoPrecioProveedorSeleccionado);
        setArticuloPrecioProveedorSeleccionado(null);
    }

    public Collection<ArticuloPresentacionPrecioProveedor> devolverArticulosProveedor(Proveedor proveedor) {
        return gestorArticuloProveedor.devolverArticulosProveedor(proveedor);
    }

    public void agregarArticulo(Proveedor proveedor) {
        this.articuloPrecioProveedorSeleccionado = new ArticuloPresentacionPrecioProveedor();
        articuloPrecioProveedorSeleccionado.setProveedor(proveedor);
    }

    public Collection<Insumo> devolverInsumosNoProveidos(Proveedor proveedor) {
        return gestorArticuloProveedor.devolverInsumosNoProveidos(proveedor);
    }

    public Collection<Madera> devolverMaderasNoProveidas(Proveedor proveedor) {
        return gestorArticuloProveedor.devolverMaderasNoProveidas(proveedor);
    }

    public void crearArticuloProveedorSeleccionado() {
        if (this.articuloPresentacionAAgregarSeleccionado.getArticulo().getTipo().equalsIgnoreCase("Madera")
                && !tienePrecioMaderaTipoProveedor) {
            MaderaTipoPrecioProveedor maderaTipoPrecioProveedor = new MaderaTipoPrecioProveedor();
            MaderaTipoPrecioProveedorPk id = new MaderaTipoPrecioProveedorPk();
            id.setProveedor(((VistaGenerico) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("generico")).getProveedorSeleccionado());
            id.setMaderaTipo(((Madera) this.articuloPresentacionAAgregarSeleccionado.getArticulo()).getMaderaTipo());
            maderaTipoPrecioProveedor.setId(id);
            maderaTipoPrecioProveedor.setPrecioPieCubico(this.precio);
            gestorArticuloProveedor.crearMaderaTipoProveedor(maderaTipoPrecioProveedor);
            // calculo el precio (cant Pies Cubicos x precio) para que lo meta en articuloPrecioProveedorSeleccionado mas abajo
            this.precio = ((Madera) this.articuloPresentacionAAgregarSeleccionado.getArticulo()).calcularPiesCubicos().multiply(this.precio);
        } else if (this.articuloPresentacionAAgregarSeleccionado.getArticulo().getTipo().equalsIgnoreCase("Madera")) {
            Proveedor proveedorSeleccionado = ((VistaGenerico) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("generico")).getProveedorSeleccionado();
            Iterator<MaderaTipoPrecioProveedor> iterator = gestorArticuloProveedor.devolverPrecioTiposMaderas(proveedorSeleccionado).iterator();
            while (iterator.hasNext()) {
                MaderaTipoPrecioProveedor maderaTipoPrecioProveedor = iterator.next();
                if (maderaTipoPrecioProveedor.getId().getMaderaTipo().equals(((Madera) this.articuloPresentacionAAgregarSeleccionado.getArticulo()).getMaderaTipo())) {
                    this.precio = maderaTipoPrecioProveedor.getPrecioPieCubico().multiply(((Madera) this.articuloPresentacionAAgregarSeleccionado.getArticulo()).calcularPiesCubicos());
                }
            }
        }
        if (this.precio != null && this.articuloPresentacionAAgregarSeleccionado != null && this.articuloPrecioProveedorSeleccionado != null) {
            this.articuloPrecioProveedorSeleccionado.setPrecio(this.precio);
            this.articuloPrecioProveedorSeleccionado.setPresentacion(this.articuloPresentacionAAgregarSeleccionado);
            gestorArticuloProveedor.crearArticuloProveedorSeleccionado(this.articuloPrecioProveedorSeleccionado);
            setArticuloPrecioProveedorSeleccionado(null);
        }
    }

    public void comprobarPrecioMaderaTipoProveedor(Proveedor proveedor) {
        if (((Madera) this.articuloPresentacionAAgregarSeleccionado.getArticulo()) != null) {
            Set<MaderaTipoPrecioProveedor> precioTiposMadera = gestorArticuloProveedor.devolverPrecioTiposMaderas(proveedor);
            if (precioTiposMadera != null) {
                Iterator<MaderaTipoPrecioProveedor> iterator = precioTiposMadera.iterator();
                tienePrecioMaderaTipoProveedor = false;
                while (iterator.hasNext()) {
                    MaderaTipoPrecioProveedor maderaTipoPrecioProveedor = iterator.next();
                    if (maderaTipoPrecioProveedor.getId().getMaderaTipo().equals(((Madera) this.articuloPresentacionAAgregarSeleccionado.getArticulo()).getMaderaTipo())) {
                        tienePrecioMaderaTipoProveedor = true;
                        crearArticuloProveedorSeleccionado();
                        break;
                    }
                }
            }
            renderScript = true;
        }
    }

    public String accionDespuesAgregarMadera() {
        System.out.println("renderScript: " + renderScript.toString());
        if (renderScript) {
            StringBuilder script = new StringBuilder("<script type=\"text/javascript\">");
            if (this.tienePrecioMaderaTipoProveedor) {
                script.append("location.reload();");
            } else {
                this.precio = null;
                script.append("dialogoSetearPrecioNuevaMadera.show();");
            }
            script.append("</script>");
            renderScript = false;
            return script.toString();
        }
        return "";
    }

    public Collection<Proveedor> devolverProveedores() {
        return gestorArticuloProveedor.devolverProveedores();
    }

    public Collection<Proveedor> devolverProveedoresArticulo(Articulo articulo) {
        return gestorArticuloProveedor.devolverProveedoresArticulo(articulo);
    }

    public String seleccionProveedor(SelectEvent evento) {
        return "/compras/Proveedores/Articulos/consultarArticulosDesdeMenuPrincipal?faces-redirect=true";
    }

    public Boolean reiniciar() {
        this.articuloPresentacionAAgregarSeleccionado = null;
        this.articuloPrecioProveedorSeleccionado = null;
        this.maderaTipoPrecioProveedorSeleccionado = null;
        this.precio = null;
        this.renderScript = false;
        this.tienePrecioMaderaTipoProveedor = false;
        this.tipoMaderaSeleccionada = null;
        gestorArticuloProveedor.reiniciar();
        return false;
    }

    public Set<ArticuloPresentacionPrecioProveedor> devolverPrecioArticulos(Proveedor p) {
        return gestorArticuloProveedor.devolverPrecioArticulos(p);
    }
}
