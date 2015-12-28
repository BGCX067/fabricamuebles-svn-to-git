/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Zaba
 */

@Entity
@DiscriminatorValue(value="Produccion")
public class RemitoDetalleProduccion extends RemitoDetalle {

    @ManyToOne
    @JoinColumn(name = "idArticulo")
    @NotNull
    private Familia familia;

    public Familia getFamilia() {
        return familia;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }

    @Override
    public String getTipo() {
        return "Produccion";
    }

}
