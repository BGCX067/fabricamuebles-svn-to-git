/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package componentes.fabrica;

import entidades.*;
import java.util.ArrayList;
import java.util.Collection;
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
public class VistaFabrica implements java.io.Serializable {
    @EJB
    private GestorFabrica gestorFabrica;
    private Fabrica fabricaSeleccionada;
    private Collection<FabricaMuebleTipoCapacidadMaxima> fabricasMueblesTipoCapacidadMaximaAgregadas;
    private MuebleTipo muebleTipoSeleccionado;
    private FabricaMuebleTipoCapacidadMaxima fabricaMuebleTipoCapacidadMaximaActual;
    private Integer cantidadMaxima;
    private String error;

    public void instanciarGestor() {
        if (gestorFabrica == null) {
            try {
                InitialContext ic = new InitialContext();
                gestorFabrica = (GestorFabrica) ic.lookup("java:global/SigmaProject/GestorFabrica");
            } catch (NamingException ex) {
                Logger.getLogger(VistaFabrica.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String getError() {
        if (error == null) {
            error = "";
        }
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Fabrica nuevaFabrica(){
        this.fabricaSeleccionada = new Fabrica();
        return this.fabricaSeleccionada;
    }
    public void setFabricaSeleccionada(Fabrica Fabrica){
        this.fabricaSeleccionada = Fabrica;
    }
    public Fabrica getFabricaSeleccionada(){
        return this.fabricaSeleccionada;
    }
    public void registrarNuevaFabrica(){
        this.gestorFabrica.registrarNuevaFabrica(this.fabricaSeleccionada);
    }
    public void actualizarFabricaSeleccionada(){
        this.gestorFabrica.actualizarFabricaSeleccionada(this.fabricaSeleccionada);
    }
    public Collection<Fabrica> devolverFabricas() {
        return gestorFabrica.devolverFabricas();
    }
    public void descartarCambiosFabrica(Fabrica fabrica) {
        gestorFabrica.descartarCambiosFabrica(fabrica);
    }
    public MuebleTipo getMuebleTipoSeleccionado() {
        return muebleTipoSeleccionado;
    }

    public void setMuebleTipoSeleccionado(MuebleTipo muebleTipoSeleccionado) {
        System.out.print("setMuebleModeloSeleccionado////////////");
        this.muebleTipoSeleccionado = muebleTipoSeleccionado;

    }
    public void seleccionFabricaMuebleTipoCapacidadMaxima(FabricaMuebleTipoCapacidadMaxima fabricaMuebleTipoCapacidadMaxima) {
        this.fabricaMuebleTipoCapacidadMaximaActual = fabricaMuebleTipoCapacidadMaxima;
    }
    public void quitarFabricaMuebleTipoCapacidadMaxima(FabricaMuebleTipoCapacidadMaxima fabricaMuebleTipoCapacidadMaxima) {
        this.fabricaSeleccionada.getMuebleTipoCapacidadesMaximas().remove(fabricaMuebleTipoCapacidadMaxima);
    }
    public void accionAgregar() {
        this.fabricaMuebleTipoCapacidadMaximaActual = null;
        this.cantidadMaxima = null;
    }
    public String siguienteSeleccionarMueblesTipo() {
        if (fabricaSeleccionada.getMuebleTipoCapacidadesMaximas() != null && !fabricaSeleccionada.getMuebleTipoCapacidadesMaximas().isEmpty()) {
            this.error = "";
            return "confirmarAsignacionMueblesTipo?faces-redirect=true";
        } else {
            this.error = "Por favor, agregue por lo menos un tipo de mueble a la fabrica.";
            return null;
        }
    }
    public Integer getCantidadMaxima() {
        return cantidadMaxima;
    }

    public void setCantidadMaxima(Integer cantidad) {
        this.cantidadMaxima = cantidad;

    }
     public void agregarFabricaMuebleTipoCapacidadMaxima() {
        Boolean yaAgregado=false;

        if (fabricaSeleccionada.getMuebleTipoCapacidadesMaximas() == null) {

            fabricaSeleccionada.setMuebleTipoCapacidadesMaximas(new ArrayList<FabricaMuebleTipoCapacidadMaxima>());
        }
        FabricaMuebleTipoCapacidadMaxima fabricaMuebleTipoCapacidadMaxima = new FabricaMuebleTipoCapacidadMaxima();
        fabricaMuebleTipoCapacidadMaxima.setCapacidadMaxima(cantidadMaxima);
        fabricaMuebleTipoCapacidadMaxima.setFabrica(fabricaSeleccionada);
        System.out.print("el nobre del mueble es "+muebleTipoSeleccionado.getNombre());
        fabricaMuebleTipoCapacidadMaxima.setMuebleTipo(muebleTipoSeleccionado);
        for (FabricaMuebleTipoCapacidadMaxima fabricaMuebleTipoCM : this.fabricaSeleccionada.getMuebleTipoCapacidadesMaximas()){
            if(fabricaMuebleTipoCM.getMuebleTipo().getNombre().equalsIgnoreCase(fabricaMuebleTipoCapacidadMaxima.getMuebleTipo().getNombre())){
                yaAgregado = true;
            }
        }
        if(yaAgregado==false){
        this.fabricaSeleccionada.getMuebleTipoCapacidadesMaximas().add(fabricaMuebleTipoCapacidadMaxima);

        }
        this.error = "";
    }
     public FabricaMuebleTipoCapacidadMaxima getFabricaMuebleTipoCapacidadMaximaSeleccionada() {
        return this.fabricaMuebleTipoCapacidadMaximaActual;

    }
     public void confirmarMueblesTipoAgregados(){
           gestorFabrica.actualizarFabricaSeleccionada(fabricaSeleccionada);
           
       }


    //TODO agregar a la fabrica la baja logica, y el metodo necesario para que liste solo las habilitadas

}
