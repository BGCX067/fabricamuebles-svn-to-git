/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author Cristian
 */
@Entity
public class FabricaMuebleTipoCapacidadMaxima implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "idFabrica")
    private Fabrica fabrica;
    @ManyToOne(cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "idMuebleTipo")
    private MuebleTipo muebleTipo;
    private Integer capacidadMaxima;

    public void setFabrica(Fabrica fabrica){
        this.fabrica =  fabrica;
    }
    public Fabrica getFabrica(){
        return this.fabrica;
    }
    public void setMuebleTipo(MuebleTipo muebleTipo){
        this.muebleTipo = muebleTipo;
    }
    public MuebleTipo getMuebleTipo (){
        return this.muebleTipo;
    }
    public void setCapacidadMaxima(Integer capacidadMaxima){
        this.capacidadMaxima = capacidadMaxima;
    }
    public Integer getCapacidadMaxima(){
        return this.capacidadMaxima;
    }

}
