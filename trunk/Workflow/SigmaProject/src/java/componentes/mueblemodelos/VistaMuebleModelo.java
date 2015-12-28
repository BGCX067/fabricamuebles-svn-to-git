/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes.mueblemodelos;

import entidades.*;
import java.util.Collection;
import java.util.logging.*;
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.*;

/**
 *
 * @author Cristian
 */
@ManagedBean
@SessionScoped
public class VistaMuebleModelo implements java.io.Serializable {

    @EJB
    GestorMuebleModelo gestorMuebleModelo;
    MuebleModelo muebleModeloSeleccionado;

    public void instanciarGestor() {
        if (this.gestorMuebleModelo == null) {
            try {
                InitialContext ic = new InitialContext();
                this.gestorMuebleModelo = (GestorMuebleModelo) ic.lookup("java:global/SigmaProject/GestorMuebleModelo");
            } catch (NamingException ex) {
                Logger.getLogger(VistaMuebleModelo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Collection<MuebleModelo> devolverMueblesModelo() {

        return gestorMuebleModelo.devolverMueblesModelo();
    }
    public Collection<MuebleModelo> devolverMueblesModeloAgregar(Collection<MuebleModelo> mm){
        return gestorMuebleModelo.devolverMueblesModeloAgregar(mm);
    }

    public Collection<MuebleModelo> devolverMueblesModeloSinEstructura() {
        return gestorMuebleModelo.devolverMueblesModeloSinEstructura();
    }

    public Collection<MuebleModelo> devolverMueblesModeloConEstructura() {
        return gestorMuebleModelo.devolverMueblesModeloConEstructura();
    }

    public Collection<MuebleModelo> devolverMueblesModeloConCantidadDisponible() {
        return gestorMuebleModelo.devolverMueblesModeloConCantidadDisponible();
    }

    public MuebleModelo getMuebleModeloSeleccionado() {
        return muebleModeloSeleccionado;
    }

    public void setMuebleSeleccionado(MuebleModelo muebleModeloSeleccionado) {
        this.muebleModeloSeleccionado = muebleModeloSeleccionado;
    }
    
    public void registrarEstructuraMuebleModelo(MuebleModelo muebleModelo) {
        gestorMuebleModelo.registrarEstructuraMuebleModelo(muebleModelo);
    }
     public Collection<MuebleModelo> devolverMueblesModeloPorTipo(MuebleTipo muebleTipo) {
        return gestorMuebleModelo.devolverMueblesModeloPorTipo(muebleTipo);
    }
   
    
}
