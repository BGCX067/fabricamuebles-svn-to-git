/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package componentes.fabricamuebletipocapacidadmaxima;

import entidades.Fabrica;
import entidades.FabricaMuebleTipoCapacidadMaxima;
import entidades.MuebleTipo;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Cristian
 */
@ManagedBean
@SessionScoped
public class VistaFabricaMuebleTipoCapacidadMaxima {
    @EJB
    private GestorFabricaMuebleTipoCapacidadMaxima gestorFabricaMuebleTipoCapacidadMaxima;
    private FabricaMuebleTipoCapacidadMaxima fabricaMuebleTipoCapacidadMaximaSeleccionada;
    private MuebleTipo muebleTipoSeleccionado;
    private Collection<FabricaMuebleTipoCapacidadMaxima> fabricasMueblesTipoCapacidadMaximaAgregadas;

     public void instanciarGestor() {
        if (this.gestorFabricaMuebleTipoCapacidadMaxima == null) {
            try {
                InitialContext ic = new InitialContext();
                this.gestorFabricaMuebleTipoCapacidadMaxima = (GestorFabricaMuebleTipoCapacidadMaxima) ic.lookup("java:global/SigmaProject/GestorFabricaMuebleTipoCapacidadMaxima");
            } catch (NamingException ex) {
                Logger.getLogger(VistaFabricaMuebleTipoCapacidadMaxima.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
     public void setFabricaMuebleTipoCapacidadMaxima(FabricaMuebleTipoCapacidadMaxima fabricaMuebleTipoCapacidadMaximaSeleccionada){
        this.fabricaMuebleTipoCapacidadMaximaSeleccionada = fabricaMuebleTipoCapacidadMaximaSeleccionada;
     }
     public FabricaMuebleTipoCapacidadMaxima getFabricaMuebleTipoCapacidadMaxima() {
        return this.fabricaMuebleTipoCapacidadMaximaSeleccionada;
    }
    
     public Collection<FabricaMuebleTipoCapacidadMaxima> getFabricasMueblesTipoCapacidadMaxima (){
       return this.gestorFabricaMuebleTipoCapacidadMaxima.getFabricasMueblesTipoCapacidadMaxima();
     }
     public Collection<FabricaMuebleTipoCapacidadMaxima> devolverFabricasMueblesTipoCapacidadMaximaPorFabrica (Fabrica fabricaSeleccionada){
         return this.gestorFabricaMuebleTipoCapacidadMaxima.getFabricasMueblesTipoCapacidadMaximaPorFabrica(fabricaSeleccionada);
     }
     public Integer devolverCapacidadMaximaParaFabricaYTipo(Fabrica fabrica, MuebleTipo muebleTipo) {
         return gestorFabricaMuebleTipoCapacidadMaxima.devolverCapacidadMaximaParaFabricaYTipo(fabrica, muebleTipo);
     }
     public FabricaMuebleTipoCapacidadMaxima nuevaFabricaMuebleTipoCapacidadMaxima(){
         this.fabricaMuebleTipoCapacidadMaximaSeleccionada = new FabricaMuebleTipoCapacidadMaxima();
         return this.fabricaMuebleTipoCapacidadMaximaSeleccionada;
     }
     public void registrarFabricaMuebleTipoCapacidadMaxima(){
         this.gestorFabricaMuebleTipoCapacidadMaxima.registrarFabricaMuebleTipoCapacidadMaxima(this.fabricaMuebleTipoCapacidadMaximaSeleccionada);
     }
     public void actualizarFabricaMuebleTipoCapacidadMaxima(){
         this.gestorFabricaMuebleTipoCapacidadMaxima.actualizarFabricaMuebleTipoCapacidadMaxima(fabricaMuebleTipoCapacidadMaximaSeleccionada);
     }

      public void cargarFabricaMuebleTipoCapacidadMaxima(Fabrica fabrica) {
        this.fabricasMueblesTipoCapacidadMaximaAgregadas = devolverFabricasMueblesTipoCapacidadMaximaPorFabrica(fabrica);
                
    }
      public void setMuebleTipoSeleccionado(MuebleTipo muebleTipoSeleccionado) {
        System.out.print("setMuebleTipoSeleccionado////////////");
        this.muebleTipoSeleccionado = muebleTipoSeleccionado;
       
    }
      public MuebleTipo getMuebleTipoSeleccionado() {
        return muebleTipoSeleccionado;
    }
    public void quitarFabricaMuebleTipoCapacidadMaxima(FabricaMuebleTipoCapacidadMaxima fabricaMuebleTipoCapacidadMaximaSeleccionada) {
        this.fabricasMueblesTipoCapacidadMaximaAgregadas.remove(fabricaMuebleTipoCapacidadMaximaSeleccionada);
    }



}
