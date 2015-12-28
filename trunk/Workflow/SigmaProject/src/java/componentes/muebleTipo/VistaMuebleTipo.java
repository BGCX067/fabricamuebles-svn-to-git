/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package componentes.muebleTipo;


import entidades.*;
import java.util.*;
import java.util.logging.*;
import javax.ejb.EJB;
import javax.naming.*;
import javax.faces.bean.*;

/**
 *
 * @author Cristian
 */
@ManagedBean
@SessionScoped
public class VistaMuebleTipo {
    @EJB
    private GestorMuebleTipo gestorMuebleTipo;
    
    private MuebleTipo muebleTipoSeleccionado;
    
    public void instanciarGestor() {
        if (this.gestorMuebleTipo == null) {
            try {
                InitialContext ic = new InitialContext();
                this.gestorMuebleTipo = (GestorMuebleTipo) ic.lookup("java:global/SigmaProject/GestorMuebleTipo");
            } catch (NamingException ex) {
                Logger.getLogger(VistaMuebleTipo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Collection<MuebleTipo> devolverMueblesTipo() {
        return gestorMuebleTipo.devolverMueblesTipo();
    }

     public void setMuebleTipoSeleccionado(MuebleTipo muebleTipoSeleccionado) {
        this.muebleTipoSeleccionado = muebleTipoSeleccionado;
    }
     public MuebleTipo getMuebleTipoSeleccionado() {
        return muebleTipoSeleccionado;
    }

    public MuebleTipo getNuevoMuebleTipo() {
        this.muebleTipoSeleccionado = new MuebleTipo();
        return this.muebleTipoSeleccionado;
    }

    public void registrarNuevoMuebleTipo() {
        gestorMuebleTipo.registrarMuebleTipo(this.muebleTipoSeleccionado);
    }
    public void actualizarMuebleTipo() {
        gestorMuebleTipo.actualizarMuebleTipo(this.muebleTipoSeleccionado);
    }
   
}
