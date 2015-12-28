/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes.telefono;

import componentes.VistaNombreComponente;
import entidades.*;
import java.util.*;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.CloseEvent;
import org.primefaces.util.ComponentUtils;

/**
 *
 * @author Zaba
 */
@ManagedBean
@SessionScoped
public class VistaTelefono implements java.io.Serializable {

    @EJB
    private GestorTelefono gestorTelefono;
    private Boolean estaAgregando = false;
    private Telefono telefonoSeleccionado;
    private String tipoSeleccionado;
    private String numero;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Telefono getTelefonoSeleccionado() {
        return this.telefonoSeleccionado;
    }

    public void setTelefonoSeleccionado(Telefono telefonoSeleccionado) {
        this.telefonoSeleccionado = telefonoSeleccionado;
        if (this.telefonoSeleccionado != null && this.telefonoSeleccionado.getTipo() != null) {
            this.numero = this.telefonoSeleccionado.getNumero();
            this.tipoSeleccionado = this.telefonoSeleccionado.getTipo();
        } else {
            this.numero = null;
            this.tipoSeleccionado = null;
        }
    }

    public String getTipoSeleccionado() {
        return tipoSeleccionado;
    }

    public void setTipoSeleccionado(String tipoSeleccionado) {
        this.tipoSeleccionado = tipoSeleccionado;
    }

    public Collection<String> devolverTipos() {
        List<String> lista = new ArrayList<String>();
        lista.add("Casa");
        lista.add("Trabajo");
        lista.add("Celular");
        return lista;
    }

    public void crearNuevoTelefono(Entidad entidad) {
        this.estaAgregando = true;
        this.telefonoSeleccionado = new Telefono();
        telefonoSeleccionado.setEntidad(entidad);
        setTelefonoSeleccionado(telefonoSeleccionado);
    }

    public void guardarDatos() {
        this.telefonoSeleccionado.setNumero(this.numero);
        this.telefonoSeleccionado.setTipo(this.tipoSeleccionado);
        if (this.estaAgregando) {
            gestorTelefono.agregarTelefono(telefonoSeleccionado);
        }
    }

    public void quitarTelefono(Telefono telefono) {
        gestorTelefono.quitarTelefono(telefono);
    }

    public GestorTelefono getGestorTelefono() {
        return gestorTelefono;
    }

    public String getValueAgregarOActualizar() {
        return this.estaAgregando ? "Agregar" : "Actualizar";
    }

    public void cierreDialogo(CloseEvent evt) {
        this.estaAgregando = false;
    }

    public Boolean reiniciar() {
        gestorTelefono.reiniciar();
        this.estaAgregando = false;
        this.numero = null;
        this.telefonoSeleccionado = null;
        this.tipoSeleccionado = null;
        setTelefonoSeleccionado(null);
        return false;
    }
}
