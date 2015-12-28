/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes.actividad;

import entidades.*;
import entidades.ordenamiento.ComparadorActividadEspecificaPorSecuencia;
import java.math.BigDecimal;
import java.util.*;
import java.util.ArrayList;
import java.util.logging.*;
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.*;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Zaba
 */
@ManagedBean
@SessionScoped
public class VistaActividad implements java.io.Serializable {

    @EJB
    private GestorActividad gestorActividad;
    private ActividadEspecifica actividadEspecificaSeleccionada;
    private ActividadGenerica actividadGenericaSeleccionada;
    private Fabricable fabricableSeleccionado;
    private Fabricable muebleSeleccionado;
    private Requerible requeribleSeleccionado;
    private BigDecimal cantidad;
    private String nombreActividad;
    private String descripcionParte;
    private String nombreParte;
    private Integer secuenciaActividad;
    private HerramientaTipo herramientaTipoSeleccionada;
    private MaquinaTipo maquinaTipoSeleccionada;
    private TreeNode nodoRaizActividades;
    private TreeNode nodoRaizRM;
    private Integer queMuestro = MUESTRO_RM;
    private static final Integer MUESTRO_RM = 0;
    private static final Integer MUESTRO_ACTIVIDADES = 1;
    private Collection<Requerible> requeriblesAAgregar;

    public Integer getQueMuestro() {
        return queMuestro;
    }

    public void setQueMuestro(Integer queMuestro) {
        this.queMuestro = queMuestro;
        construirArbolEstructuraProducto();
    }

    public void instanciarGestor() {
        if (this.gestorActividad == null) {
            try {
                InitialContext ic = new InitialContext();
                this.gestorActividad = (GestorActividad) ic.lookup("java:global/SigmaProject/GestorActividad");
            } catch (NamingException ex) {
                Logger.getLogger(VistaActividad.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Collection<Requerible> getRequeriblesAAgregar() {
        return requeriblesAAgregar;
    }

    public void setRequeriblesAAgregar(Collection<Requerible> requeriblesAAgregar) {
        this.requeriblesAAgregar = requeriblesAAgregar;
    }

    public String getNombreParte() {
        return nombreParte;
    }

    public void setNombreParte(String nombreParte) {
        this.nombreParte = nombreParte;
    }

    public String getDescripcionParte() {
        return descripcionParte;
    }

    public void setDescripcionParte(String descripcionParte) {
        this.descripcionParte = descripcionParte;
    }

    public TreeNode getNodoRaizActividades() {
        return nodoRaizActividades;
    }

    public void setNodoRaizActividades(TreeNode nodoRaizActividades) {
        this.nodoRaizActividades = nodoRaizActividades;
    }

    public TreeNode getNodoRaizRM() {
        return nodoRaizRM;
    }

    public void setNodoRaizRM(TreeNode nodoRaizRM) {
        this.nodoRaizRM = nodoRaizRM;
    }

    public Fabricable getMuebleSeleccionado() {
        return muebleSeleccionado;
    }

    public void setMuebleSeleccionado(Fabricable muebleSeleccionado) {
        this.muebleSeleccionado = muebleSeleccionado;
        setFabricableSeleccionado(muebleSeleccionado);
        construirArbolEstructuraProducto();
    }

    public MaquinaTipo getMaquinaTipoSeleccionada() {
        return maquinaTipoSeleccionada;
    }

    public void setMaquinaTipoSeleccionada(MaquinaTipo maquinaTipoSeleccionada) {
        this.maquinaTipoSeleccionada = maquinaTipoSeleccionada;
        seleccionarMaquinaTipo();
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
        this.secuenciaActividad = null;
    }

    public HerramientaTipo getHerramientaTipoSeleccionada() {
        return herramientaTipoSeleccionada;
    }

    public void setHerramientaTipoSeleccionada(HerramientaTipo herramientaTipoSeleccionada) {
        this.herramientaTipoSeleccionada = herramientaTipoSeleccionada;
        agregarHerramientaTipo();
    }

    public Integer getSecuenciaActividad() {
        return secuenciaActividad;
    }

    public void setSecuenciaActividad(Integer secuenciaActividad) {
        this.secuenciaActividad = secuenciaActividad;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public Requerible getRequeribleSeleccionado() {
        return requeribleSeleccionado;
    }

    public void setRequeribleSeleccionado(Requerible requeribleSeleccionado) {
        this.requeribleSeleccionado = requeribleSeleccionado;
        this.cantidad = null;
    }

    public ActividadEspecifica getActividadEspecificaSeleccionada() {
        return actividadEspecificaSeleccionada;
    }

    public void setActividadEspecificaSeleccionada(ActividadEspecifica actividadEspecificaSeleccionada) {
        this.actividadEspecificaSeleccionada = actividadEspecificaSeleccionada;
    }

    public ActividadGenerica getActividadGenericaSeleccionada() {
        return actividadGenericaSeleccionada;
    }

    public void setActividadGenericaSeleccionada(ActividadGenerica actividadGenericaSeleccionada) {
        this.actividadGenericaSeleccionada = actividadGenericaSeleccionada;
        this.secuenciaActividad = null;
    }

    public Fabricable getFabricableSeleccionado() {
        return fabricableSeleccionado;
    }

    public void setFabricableSeleccionado(Fabricable fabricableSeleccionado) {
        this.fabricableSeleccionado = fabricableSeleccionado;
    }

    public void construirArbolEstructuraProducto() {
        if (queMuestro.equals(MUESTRO_ACTIVIDADES)) {
            nodoRaizActividades = new DefaultTreeNode("Raiz", null);
            nodoRaizActividades.setExpanded(true);
            TreeNode nodo = new DefaultTreeNode("Fabricable", muebleSeleccionado, nodoRaizActividades);
            //agregarHijosANodoActividades(nodo);
        } else if (queMuestro.equals(MUESTRO_RM)) {
            nodoRaizRM = new DefaultTreeNode("Raiz", null);
            nodoRaizRM.setExpanded(true);
            TreeNode nodo = new DefaultTreeNode("MuebleModelo", muebleSeleccionado, nodoRaizRM);
            //agregarHijosANodoRM(nodo);
        }
    }

    private void agregarHijosANodoRM(TreeNode padre) {
        padre.setExpanded(true);
        if (padre.getData() instanceof MuebleModelo) {
            MuebleModelo muebleModelo = (MuebleModelo) padre.getData();
            Collection<RequerimientoMaterial> requerimientosMaterial = muebleModelo.getRequerimientosMateriales();
            if (requerimientosMaterial != null) {
                for (RequerimientoMaterial requerimientoMaterial : requerimientosMaterial) {
                    new DefaultTreeNode("RM", requerimientoMaterial, padre);
                }
            }
        }
    }

    private void agregarHijosANodoActividades(TreeNode padre) {
        padre.setExpanded(true);
        if (padre.getData() instanceof ActividadEspecifica) {
            ActividadEspecifica actividadEspecifica = (ActividadEspecifica) padre.getData();
            Collection<RequerimientoMaterial> requerimientosMaterial = actividadEspecifica.getRequerimientosMaterial();
            if (requerimientosMaterial != null) {
                for (RequerimientoMaterial requerimientoMaterial : requerimientosMaterial) {
                    TreeNode nodo = null;
                    if (requerimientoMaterial.getItemRequerible() instanceof Fabricable) {
                        nodo = new DefaultTreeNode("Fabricable", requerimientoMaterial.getItemRequerible(), padre);
                    } else {
                        nodo = new DefaultTreeNode("NoFabricable", requerimientoMaterial.getItemRequerible(), padre);
                    }
                    agregarHijosANodoActividades(nodo);
                }
            }
        } else if (padre.getData() instanceof Fabricable) {
            Fabricable fabricable = (Fabricable) padre.getData();
            Collection<ActividadEspecifica> actividades = fabricable.getActividades();
            if (actividades != null) {
                ActividadEspecifica[] actividadesEspecificas = actividades.toArray(new ActividadEspecifica[0]);
                Arrays.sort(actividadesEspecificas, new ComparadorActividadEspecificaPorSecuencia());
                for (ActividadEspecifica actividadEspecifica : actividadesEspecificas) {
                    TreeNode nodo = new DefaultTreeNode("Actividad", actividadEspecifica, padre);
                    agregarHijosANodoActividades(nodo);
                }
            }
        }
    }

    public void agregarRequerimientoMaterial() {
        RequerimientoMaterial requerimientoMaterial = new RequerimientoMaterial();
        requerimientoMaterial.setCantidadRequerida(cantidad);
        requerimientoMaterial.setItemRequerible(requeribleSeleccionado);
        if (((MuebleModelo)muebleSeleccionado).getRequerimientosMateriales() == null) {
            ((MuebleModelo) muebleSeleccionado).setRequerimientosMateriales(new ArrayList<RequerimientoMaterial>());
        }
        ((MuebleModelo)muebleSeleccionado).getRequerimientosMateriales().add(requerimientoMaterial);
        cantidad = null;
        requeribleSeleccionado = null;
        construirArbolEstructuraProducto();
    }

    public void agregarActividadNuevaAFabricableSeleccionado() {
        if (this.fabricableSeleccionado != null) {
            if (fabricableSeleccionado.getActividades() == null) {
                fabricableSeleccionado.setActividades(new ArrayList<ActividadEspecifica>());
            }
            ActividadEspecifica actividadEspecifica = new ActividadEspecifica();
            actividadEspecifica.setNombre(nombreActividad);
            actividadEspecifica.setSecuencia(secuenciaActividad);
            this.actividadEspecificaSeleccionada = actividadEspecifica;
            fabricableSeleccionado.getActividades().add(actividadEspecifica);
            nombreActividad = null;
            secuenciaActividad = null;
            construirArbolEstructuraProducto();
        }
    }

    public void agregarHerramientaTipo() {
        if (herramientaTipoSeleccionada != null) {
            if (actividadEspecificaSeleccionada.getHerramientasTipo() == null) {
                actividadEspecificaSeleccionada.setHerramientasTipo(new ArrayList<HerramientaTipo>());
            }
            actividadEspecificaSeleccionada.getHerramientasTipo().add(herramientaTipoSeleccionada);
            herramientaTipoSeleccionada = null;
        }
    }

    public void agregarParteMueble() {
        if (this.nombreParte != null) {
            MuebleParte muebleParte = new MuebleParte();
            muebleParte.setNombre(nombreParte);
            muebleParte.setDescripcion(descripcionParte);
            nombreParte = null;
            descripcionParte = null;
            RequerimientoMaterial requerimientoMaterial = new RequerimientoMaterial();
            requerimientoMaterial.setItemRequerible(muebleParte);
            requerimientoMaterial.setCantidadRequerida(cantidad);
            cantidad = null;
            if (this.actividadEspecificaSeleccionada.getRequerimientosMaterial() == null) {
                this.actividadEspecificaSeleccionada.setRequerimientosMaterial(new ArrayList<RequerimientoMaterial>());
            }
            this.actividadEspecificaSeleccionada.getRequerimientosMaterial().add(requerimientoMaterial);
            construirArbolEstructuraProducto();
        }
    }

    public void seleccionarMaquinaTipo() {
        if (maquinaTipoSeleccionada != null) {
            actividadEspecificaSeleccionada.setMaquinaTipo(maquinaTipoSeleccionada);
            maquinaTipoSeleccionada = null;
        }
    }

    public Collection<ActividadEspecifica> devolverActividadesFabricableSeleccionado() {
        return gestorActividad.devolverActividadesFabricableSeleccionado(fabricableSeleccionado);
    }

    public Collection<ActividadGenerica> devolverActividadesGenericas() {
        return gestorActividad.devolverActividadesGenericas();
    }

    public Collection<Requerible> devolverRequeriblesAAgregar() {
        return gestorActividad.devolverRequeriblesAAgregar((MuebleModelo) muebleSeleccionado);
    }

    public Collection<Herramienta> devolverHerramientasTipoAAgregar() {
        return gestorActividad.devolverHerramientasTipoAAgregar(actividadEspecificaSeleccionada);
    }

    public Collection<MaquinaTipo> devolverMaquinasTipo() {
        return gestorActividad.devolverMaquinasTipo();
    }

    public void seleccionNodoEstructuraProducto(NodeSelectEvent evt) {
        Object o = evt.getTreeNode().getData();
        if (o instanceof Fabricable) {
            this.fabricableSeleccionado = (Fabricable) o;
            this.actividadEspecificaSeleccionada = null;
        } else if (o instanceof ActividadEspecifica) {
            this.actividadEspecificaSeleccionada = (ActividadEspecifica) o;
            this.fabricableSeleccionado = (Fabricable) evt.getTreeNode().getParent().getData();
        } else if (o instanceof Articulo) {
            this.actividadEspecificaSeleccionada = (ActividadEspecifica) evt.getTreeNode().getParent().getData();
            this.fabricableSeleccionado = (Fabricable) evt.getTreeNode().getParent().getParent().getData();
        }
    }

    public void actualizarListaRequeriblesAAgregar() {
        this.requeriblesAAgregar = gestorActividad.devolverRequeriblesAAgregar((MuebleModelo) this.muebleSeleccionado);
    }
}
