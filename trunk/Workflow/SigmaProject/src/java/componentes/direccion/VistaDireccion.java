/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes.direccion;

import entidades.*;
import java.util.*;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.event.CloseEvent;

/**
 *
 * @author Zaba
 */
@ManagedBean
@SessionScoped
public class VistaDireccion implements java.io.Serializable {

    @EJB
    private GestorDireccion gestorDireccion;
    private Map<String, Provincia> mapaProvincias;
    private Map<String, Departamento> mapaDepartamentos;
    private Map<String, Localidad> mapaLocalidades;
    private Boolean agregarProvincia = false;
    private Boolean agregarDepartamento = false;
    private Boolean agregarLocalidad = false;
    private Boolean estaAgregando = false;
    private Provincia provinciaSeleccionada;
    private Departamento departamentoSeleccionado;
    private Localidad localidadSeleccionada;
    private Direccion direccionSeleccionada;
    private String tipoSeleccionado;
    private String calle;
    private String numero;
    private String piso;
    private String dpto;

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getDpto() {
        return dpto;
    }

    public void setDpto(String dpto) {
        this.dpto = dpto;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public Direccion getDireccionSeleccionada() {
        return this.direccionSeleccionada;
    }

    public void setDireccionSeleccionada(Direccion direccionSeleccionada) {
        this.direccionSeleccionada = direccionSeleccionada;
        if (this.direccionSeleccionada != null && this.direccionSeleccionada.getLocalidad() != null) {
            this.localidadSeleccionada = this.direccionSeleccionada.getLocalidad();
            this.departamentoSeleccionado = this.localidadSeleccionada.getDepartamento();
            this.provinciaSeleccionada = this.departamentoSeleccionado.getProvincia();
            this.tipoSeleccionado = this.direccionSeleccionada.getTipo();
            this.calle = this.direccionSeleccionada.getCalle();
            this.numero = this.direccionSeleccionada.getNumero();
            this.piso = this.direccionSeleccionada.getPiso();
            this.dpto = this.direccionSeleccionada.getDpto();
        } else {
            this.localidadSeleccionada = null;
            this.departamentoSeleccionado = null;
            this.provinciaSeleccionada = null;
            this.tipoSeleccionado = null;
            this.calle = null;
            this.numero = null;
            this.piso = null;
            this.dpto = null;
        }
    }

    public String getTipoSeleccionado() {
        return tipoSeleccionado;
    }

    public void setTipoSeleccionado(String tipoSeleccionado) {
        this.tipoSeleccionado = tipoSeleccionado;
    }

    public String getProvinciaSeleccionada() {
        if (this.provinciaSeleccionada != null) {
            return this.provinciaSeleccionada.getNombre();
        }
        return "seleccion";
    }

    public void setProvinciaSeleccionada(String nombreProvinciaSeleccionada) {
        if (nombreProvinciaSeleccionada != null && !nombreProvinciaSeleccionada.equals("seleccion") &&
                !nombreProvinciaSeleccionada.equals("agregar")) {
            this.provinciaSeleccionada = mapaProvincias.get(nombreProvinciaSeleccionada);
        } else if (nombreProvinciaSeleccionada != null && nombreProvinciaSeleccionada.equals("agregar")) {
            this.agregarProvincia = true;
            this.provinciaSeleccionada = null;
        } else if (nombreProvinciaSeleccionada != null && nombreProvinciaSeleccionada.equals(-1)) {
            this.provinciaSeleccionada = null;
            setDepartamentoSeleccionado("seleccion");
        }
    }

    public String getDepartamentoSeleccionado() {
        if (this.departamentoSeleccionado != null) {
            return this.departamentoSeleccionado.getNombre();
        }
        return "seleccion";
    }

    public void setDepartamentoSeleccionado(String nombreDepartamentoSeleccionado) {
        if (nombreDepartamentoSeleccionado != null && !nombreDepartamentoSeleccionado.equals("seleccion") &&
                !nombreDepartamentoSeleccionado.equals("agregar")) {
            this.departamentoSeleccionado = mapaDepartamentos.get(nombreDepartamentoSeleccionado);
            this.agregarDepartamento = false;
        } else if (nombreDepartamentoSeleccionado != null && nombreDepartamentoSeleccionado.equals("agregar")) {
            this.agregarDepartamento = true;
            this.departamentoSeleccionado = null;
        } else {
            this.departamentoSeleccionado = null;
            this.agregarDepartamento = false;
        }
        if (nombreDepartamentoSeleccionado != null && (nombreDepartamentoSeleccionado.equals("agregar") || nombreDepartamentoSeleccionado.equals("seleccion"))) {
            setLocalidadSeleccionada("seleccion");
        }
    }

    public String getLocalidadSeleccionada() {
        if (this.localidadSeleccionada != null) {
            return this.localidadSeleccionada.getNombre();
        }
        return "seleccion";
    }

    public void setLocalidadSeleccionada(String nombreLocalidadSeleccionada) {
        if (nombreLocalidadSeleccionada != null && !nombreLocalidadSeleccionada.equals("seleccion") &&
                !nombreLocalidadSeleccionada.equals("agregar")) {
            this.localidadSeleccionada = mapaLocalidades.get(nombreLocalidadSeleccionada);
            this.agregarLocalidad = false;
        } else if (nombreLocalidadSeleccionada != null && nombreLocalidadSeleccionada.equals("agregar")) {
            this.agregarLocalidad = true;
            this.localidadSeleccionada = null;
        } else {
            this.localidadSeleccionada = null;
            this.agregarLocalidad = false;
        }
    }

    public Collection<String> devolverTipos() {
        List<String> lista = new ArrayList<String>();
        lista.add("Casa");
        lista.add("Trabajo");
        return lista;
    }

    public Collection<SelectItem> devolverProvincias() {
        Collection<Provincia> provincias = gestorDireccion.devolverProvincias();
        mapaProvincias = new LinkedHashMap<String, Provincia>();
        List<SelectItem> lista = new ArrayList<SelectItem>();
        for (Provincia provincia : provincias) {
            lista.add(new SelectItem(provincia.getNombre(), provincia.getNombre()));
            mapaProvincias.put(provincia.getNombre(), provincia);
        }
        return lista;
    }

    public Collection<SelectItem> devolverDepartamentos() {
        List<SelectItem> lista = new ArrayList<SelectItem>();
        String p = getProvinciaSeleccionada() == null ? "seleccion" : getProvinciaSeleccionada();
        if (!p.equals("seleccion")) {
            Collection<Departamento> departamentos = gestorDireccion.devolverDepartamentos(mapaProvincias.get(p));
            mapaDepartamentos = new LinkedHashMap<String, Departamento>();
            for (Departamento departamento : departamentos) {
                lista.add(new SelectItem(departamento.getNombre(), departamento.getNombre()));
                mapaDepartamentos.put(departamento.getNombre(), departamento);
            }
        }
        return lista;
    }

    public Collection<SelectItem> devolverLocalidades() {
        List<SelectItem> lista = new ArrayList<SelectItem>();
        String d = getDepartamentoSeleccionado() == null ? "seleccion" : getDepartamentoSeleccionado();
        if (!d.equals("seleccion")) {
            Collection<Localidad> localidades = gestorDireccion.devolverLocalidades(mapaDepartamentos.get(d));
            mapaLocalidades = new LinkedHashMap<String, Localidad>();
            for (Localidad localidad : localidades) {
                lista.add(new SelectItem(localidad.getNombre(), localidad.getNombre()));
                mapaLocalidades.put(localidad.getNombre(), localidad);
            }
        }
        return lista;
    }
    
    public Boolean agregarProvincia() {
        return this.agregarProvincia;
    }

    public Boolean agregarDepartamento() {
        return this.agregarDepartamento;
    }

    public Boolean agregarLocalidad() {
        return this.agregarLocalidad;
    }
    
    public String getProvinciaNueva() {
        return "";
    }
    
    public void setProvinciaNueva(String nombre) {
        if (nombre != null && !nombre.isEmpty()) {
            gestorDireccion.agregarProvincia(nombre);
        }
        this.agregarProvincia = false;
    }

    public String getDepartamentoNuevo() {
        return "";
    }

    public void setDepartamentoNuevo(String nombre) {
        if (nombre != null && !nombre.isEmpty()) {
            gestorDireccion.agregarDepartamento(nombre, provinciaSeleccionada);
        }
        this.agregarDepartamento = false;
    }

    public String getLocalidadNueva() {
        return "";
    }

    public void setLocalidadNueva(String nombre) {
        if (nombre != null && !nombre.isEmpty()) {
            gestorDireccion.agregarLocalidad(nombre, departamentoSeleccionado);
        }
        this.agregarLocalidad = false;
    }

    public void crearNuevaDireccion(Entidad entidad) {
        this.estaAgregando = true;
        this.direccionSeleccionada = new Direccion();
        direccionSeleccionada.setEntidad(entidad);
        setDireccionSeleccionada(direccionSeleccionada);
    }

    public void guardarDatos() {
        this.direccionSeleccionada.setLocalidad(localidadSeleccionada);
        this.direccionSeleccionada.setTipo(tipoSeleccionado);
        this.direccionSeleccionada.setCalle(this.calle);
        this.direccionSeleccionada.setNumero(this.numero);
        this.direccionSeleccionada.setPiso(this.piso);
        this.direccionSeleccionada.setDpto(this.dpto);
        if (this.estaAgregando) {
            gestorDireccion.agregarDireccion(direccionSeleccionada);
        }
    }

    public void quitarDireccion(Direccion direccion) {
        gestorDireccion.quitarDireccion(direccion);
    }

    public GestorDireccion getGestorDireccion() {
        return gestorDireccion;
    }

    public String getValueAgregarOActualizar() {
        return this.estaAgregando ? "Agregar" : "Actualizar";
    }

    public void cierreDialogo(CloseEvent evt) {
        this.estaAgregando = false;
    }

    public String getNombreProvincia() {
        return this.provinciaSeleccionada == null ? "" : this.provinciaSeleccionada.getNombre();
    }

    public String getNombreDepartamento() {
        return this.departamentoSeleccionado == null ? "" : this.departamentoSeleccionado.getNombre();
    }

    public String getNombreLocalidad() {
        return this.localidadSeleccionada == null ? "" : this.localidadSeleccionada.getNombre();
    }

    public Boolean reiniciar() {
        gestorDireccion.reiniciar();
        this.agregarDepartamento = false;
        this.agregarLocalidad = false;
        this.mapaDepartamentos = null;
        this.mapaLocalidades = null;
        this.mapaProvincias = null;
        this.provinciaSeleccionada = null;
        this.departamentoSeleccionado = null;
        this.localidadSeleccionada = null;
        setDireccionSeleccionada(null);
        return false;
    }
}
