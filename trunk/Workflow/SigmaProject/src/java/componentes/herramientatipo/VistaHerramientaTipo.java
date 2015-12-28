/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package componentes.herramientatipo;

import entidades.HerramientaTipo;
import java.util.Collection;
import java.util.logging.*;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;



/**
 *
 * @author DanielH
 */
@ManagedBean
@SessionScoped
public class VistaHerramientaTipo implements java.io.Serializable {

    @EJB
    private GestorHerramientaTipo gestorHerramientaTipo;
    private HerramientaTipo herramientaTipoSeleccionada;

    public void instanciarGestor() {
        if (this.gestorHerramientaTipo == null) {
            try {
                InitialContext ic = new InitialContext();
                this.gestorHerramientaTipo = (GestorHerramientaTipo) ic.lookup("java:global/SigmaProject/GestorHerramientaTipo");
            } catch (NamingException ex) {
                Logger.getLogger(VistaHerramientaTipo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setHerramientaTipoSeleccionada(HerramientaTipo HerramientaTipo) {
        this.herramientaTipoSeleccionada = HerramientaTipo;
    }

    public HerramientaTipo getHerramientaTipoSeleccionada() {
        return herramientaTipoSeleccionada;
    }

    public void setHerramientaTipoNueva(HerramientaTipo herramientaTipoNueva) {
        this.herramientaTipoSeleccionada = herramientaTipoNueva;
    }

    public HerramientaTipo getHerramientaTipoNueva() {
        if (this.herramientaTipoSeleccionada == null) {
            this.herramientaTipoSeleccionada = new HerramientaTipo();
        }
        return this.herramientaTipoSeleccionada;
    }

    public void registrarHerramientaTipoNueva() {
        gestorHerramientaTipo.registrarHerramientaTipo(herramientaTipoSeleccionada);
    }

    public Collection<HerramientaTipo> devolverHerramientasTipo() {
        return gestorHerramientaTipo.devolverHerramientasTipo();
    }

    public void descartarCambiosHerramientaTipo(HerramientaTipo herramientaTipo) {
        gestorHerramientaTipo.descartarCambiosHerramientaTipo(herramientaTipo);
    }

    public void actualizarHerramientaTipo(HerramientaTipo herramientaTipo) {
        gestorHerramientaTipo.actualizarHerramientaTipo(herramientaTipo);
    }
}
