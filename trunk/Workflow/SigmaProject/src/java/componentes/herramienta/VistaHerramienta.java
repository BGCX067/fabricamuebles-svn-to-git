/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package componentes.herramienta;

import entidades.Herramienta;
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
public class VistaHerramienta implements java.io.Serializable {

    @EJB
    private GestorHerramienta gestorHerramienta;
    private Herramienta herramientaSeleccionada;

    public void instanciarGestor() {
        if (this.gestorHerramienta == null) {
            try {
                InitialContext ic = new InitialContext();
                this.gestorHerramienta = (GestorHerramienta) ic.lookup("java:global/SigmaProject/GestorHerramienta");
            } catch (NamingException ex) {
                Logger.getLogger(VistaHerramienta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setHerramientaSeleccionada(Herramienta herramienta) {
        this.herramientaSeleccionada = herramienta;
    }

    public Herramienta getHerramientaSeleccionada() {
        return herramientaSeleccionada;
    }

    public void setHerramientaNueva(Herramienta herramientaNueva) {
        this.herramientaSeleccionada = herramientaNueva;
    }

    public Herramienta getHerramientaNueva() {
        return gestorHerramienta.getNuevaHerramienta();
    }

    public void registrarHerramientaNueva() {

        gestorHerramienta.registrarHerramienta();
    }

    public Collection<Herramienta> devolverHerramientas() {
        return gestorHerramienta.devolverHerramientas();
    }

    public void descartarCambiosHerramienta(Herramienta herramienta) {
        gestorHerramienta.descartarCambiosHerramienta(herramienta);
    }

    public void actualizarHerramienta(Herramienta herramienta) {
        gestorHerramienta.actualizarHerramienta(herramienta);
    }

}