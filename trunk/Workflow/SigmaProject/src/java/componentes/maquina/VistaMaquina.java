/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package componentes.maquina;

import entidades.Maquina;
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
public class VistaMaquina implements java.io.Serializable {

    @EJB
    private GestorMaquina gestorMaquina;
    private Maquina maquinaSeleccionada;

    public void instanciarGestor() {
        if (this.gestorMaquina == null) {
            try {
                InitialContext ic = new InitialContext();
                this.gestorMaquina = (GestorMaquina) ic.lookup("java:global/SigmaProject/GestorMaquina");
            } catch (NamingException ex) {
                Logger.getLogger(VistaMaquina.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setMaquinaSeleccionada(Maquina maquina) {
        this.maquinaSeleccionada = maquina;
    }

    public Maquina getMaquinaSeleccionada() {
        return maquinaSeleccionada;
    }

    public void setMaquinaNueva(Maquina maquinaNueva) {
        this.maquinaSeleccionada = maquinaNueva;
    }

    public Maquina getMaquinaNueva() {
        return gestorMaquina.getNuevaMaquina();
    }

    public void registrarMaquinaNueva() {
        
        gestorMaquina.registrarMaquina();
    }

    public Collection<Maquina> devolverMaquinas() {
        return gestorMaquina.devolverMaquinas();
    }

    public void descartarCambiosMaquina(Maquina maquina) {
        gestorMaquina.descartarCambiosMaquina(maquina);
    }

    public void actualizarMaquina(Maquina maquina) {
        gestorMaquina.actualizarMaquina(maquina);
    }
}
