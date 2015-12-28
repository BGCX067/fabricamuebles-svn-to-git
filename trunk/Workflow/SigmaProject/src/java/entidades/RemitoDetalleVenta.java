/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Cristian
 */
@Entity
@DiscriminatorValue(value="Venta")
public class RemitoDetalleVenta extends RemitoDetalle implements Serializable{

     private String descripcion;

    @ManyToOne
    @JoinColumn(name="idMuebleModelo")
    @NotNull
    private MuebleModelo muebleModelo;
   /* @NotNull
    private MuebleLote muebleLote;*/


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public MuebleModelo getMuebleModelo() {
        return muebleModelo;
    }

    public void setMuebleModelo(MuebleModelo muebleModelo) {
        this.muebleModelo = muebleModelo;
    }

    public String getTipo() {
        return "Venta";
    }

}
