/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes.articulo;

import componentes.articuloproveedor.VistaArticuloProveedor;
import entidades.*;
import java.util.*;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;

/**
 *
 * @author Zaba
 */
@ManagedBean
@SessionScoped
public class VistaArticulo implements java.io.Serializable {

    @EJB
    private GestorArticulo gestorArticulo;
    private Articulo articuloSeleccionado;
    private MaderaTipo maderaTipoSeleccionada;
    private Map<String, MaderaTipo> mapaMaderaTipos;
    private boolean seApretoBotonActualizar = false;

    public void setMaderaNueva(Madera maderaNueva) {
        this.articuloSeleccionado = maderaNueva;
    }

    public Madera getMaderaNueva() {
        if (this.articuloSeleccionado == null) {
            this.articuloSeleccionado = new Madera();
        }
        return (Madera) this.articuloSeleccionado;
    }

    public void setInsumoNuevo(Insumo insumoNuevo) {
        this.articuloSeleccionado = insumoNuevo;
    }

    public Insumo getInsumoNuevo() {
        if (this.articuloSeleccionado == null) {
            this.articuloSeleccionado = new Insumo();
        }
        return (Insumo) this.articuloSeleccionado;
    }

    public void registrarArticuloNuevo() {
        if (this.articuloSeleccionado.getTipo().equalsIgnoreCase("Madera")) {
            if (this.maderaTipoSeleccionada != null) {
                ((Madera)this.articuloSeleccionado).setMaderaTipo(this.maderaTipoSeleccionada);
            }
        }
        gestorArticulo.registrarArticulo(this.articuloSeleccionado);
    }

    public Collection<Articulo> devolverArticulos() {
        return gestorArticulo.devolverArticulos();
    }

    public Articulo getArticuloSeleccionado() {
        return articuloSeleccionado;
    }

    public void setArticuloSeleccionado(Articulo articuloSeleccionado) {
        this.articuloSeleccionado = articuloSeleccionado;
    }

    public void actualizarArticuloSeleccionado() {
        gestorArticulo.guardarCambiosArticulo(articuloSeleccionado);
        VistaArticuloProveedor vistaArticuloProveedor = (VistaArticuloProveedor) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vistaArticuloProveedor");
        if (vistaArticuloProveedor == null) {
            vistaArticuloProveedor = new VistaArticuloProveedor();
            vistaArticuloProveedor.instanciarGestor();
        }
        Collection<ArticuloPresentacionPrecioProveedor> articuloPrecioProveedores = gestorArticulo.devolverProveedoresPrecios(articuloSeleccionado);
        for (ArticuloPresentacionPrecioProveedor articuloPrecioProveedor : articuloPrecioProveedores) {
            vistaArticuloProveedor.actualizarArticuloPrecioProveedor(articuloPrecioProveedor, VistaArticuloProveedor.ACTUALIZAR_PRECIO_MADERA);
        }
    }

    public void descartarCambiosArticuloSeleccionado() {
        gestorArticulo.recuperarArticulo(articuloSeleccionado);
    }

    public Collection<String> devolverMaderaTiposCombo() {
        Collection<MaderaTipo> maderaTipos = gestorArticulo.devolverMaderaTipos();
        ArrayList<String> lista = new ArrayList<String>();
        mapaMaderaTipos = new HashMap<String, MaderaTipo>();
        for (MaderaTipo maderaTipo : maderaTipos) {
            lista.add(maderaTipo.getNombre());
            mapaMaderaTipos.put(maderaTipo.getNombre(), maderaTipo);
        }
        return lista;
    }

    public Collection<MaderaTipo> devolverMaderaTipos() {
        return gestorArticulo.devolverMaderaTipos();
    }

    public void setMaderaTipoSeleccionadaCombo(String maderaTipoSeleccionada) {
        this.maderaTipoSeleccionada = mapaMaderaTipos.get(maderaTipoSeleccionada);
    }

    public String getMaderaTipoSeleccionadaCombo() {
        String maderaTipo = null;
        if (this.maderaTipoSeleccionada != null) {
            maderaTipo = this.maderaTipoSeleccionada.getNombre();
        }
        return maderaTipo;
    }

    public MaderaTipo getMaderaTipoSeleccionada() {
        return maderaTipoSeleccionada;
    }

    public void setMaderaTipoSeleccionada(MaderaTipo maderaTipoSeleccionada) {
        this.maderaTipoSeleccionada = maderaTipoSeleccionada;
    }

    public MaderaTipo getMaderaTipoNueva() {
        if (this.maderaTipoSeleccionada == null) {
            this.maderaTipoSeleccionada = new MaderaTipo();
        }
        return maderaTipoSeleccionada;
    }

    public void setMaderaTipoNueva(MaderaTipo maderaTipoNueva) {
        this.maderaTipoSeleccionada = maderaTipoNueva;
    }

    public void clickBotonActualizar() {
        this.seApretoBotonActualizar = true;
    }

    public String accionActualizar() {
        String scriptResultado;
        if (this.seApretoBotonActualizar) {
            if (gestorArticulo.tieneProveedoresAsignados(articuloSeleccionado)) {
                scriptResultado = "dialogoAdvertencia.show();";
            } else {
                actualizarArticuloSeleccionado();
                scriptResultado = new StringBuilder("location='mostrarDatos").append(articuloSeleccionado.getTipo()).append(".xhtml';").toString();
            }
            this.seApretoBotonActualizar = false;
        } else {
            scriptResultado = "";
        }
        return scriptResultado;
    }

    public void descartarCambiosMaderaTipoSeleccionada() {
        gestorArticulo.recuperarMaderaTipo(maderaTipoSeleccionada);
    }

    public void actualizarMaderaTipoSeleccionada() {
        gestorArticulo.guardarCambiosMaderaTipo(maderaTipoSeleccionada);
    }

    public void registrarMaderaTipoNueva() {
        gestorArticulo.registrarMaderaTipo(maderaTipoSeleccionada);
    }
}
