/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package componentes.maquinatipo;

import entidades.MaquinaTipo;
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
public class VistaMaquinaTipo implements java.io.Serializable {

    @EJB
    private GestorMaquinaTipo gestorMaquinaTipo;
    private MaquinaTipo maquinaTipoSeleccionada;

    public void instanciarGestor() {
        if (this.gestorMaquinaTipo == null) {
            try {
                InitialContext ic = new InitialContext();
                this.gestorMaquinaTipo = (GestorMaquinaTipo) ic.lookup("java:global/SigmaProject/GestorMaquinaTipo");
            } catch (NamingException ex) {
                Logger.getLogger(VistaMaquinaTipo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setMaquinaTipoSeleccionada(MaquinaTipo maquinaTipo) {
        this.maquinaTipoSeleccionada = maquinaTipo;
    }

    public MaquinaTipo getMaquinaTipoSeleccionada() {
        return maquinaTipoSeleccionada;
    }

    public void setMaquinaTipoNueva(MaquinaTipo maquinaTipoNueva) {
        this.maquinaTipoSeleccionada = maquinaTipoNueva;
    }

    public MaquinaTipo getMaquinaTipoNueva() {
        if (this.maquinaTipoSeleccionada == null) {
            this.maquinaTipoSeleccionada = new MaquinaTipo();
        }
        return this.maquinaTipoSeleccionada;
    }

    public void registrarMaquinaTipoNueva() {        
        gestorMaquinaTipo.registrarMaquinaTipo(this.maquinaTipoSeleccionada);
    }

    public Collection<MaquinaTipo> devolverMaquinasTipo() {
        return gestorMaquinaTipo.devolverMaquinasTipo();
    }

    public void descartarCambiosMaquinaTipo(MaquinaTipo maquinaTipo) {
        gestorMaquinaTipo.descartarCambiosMaquinaTipo(maquinaTipo);
    }

    public void actualizarMaquinaTipo(MaquinaTipo maquinaTipo) {
        gestorMaquinaTipo.actualizarMaquinaTipo(maquinaTipo);
    }
}
