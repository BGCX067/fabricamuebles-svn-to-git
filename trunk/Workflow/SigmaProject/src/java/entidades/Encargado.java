/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Cristian
 */
@Entity
@DiscriminatorValue (value="Encargado")
public class Encargado extends Entidad {
    private Boolean habilitado = true;
    @NotNull
    private String dni;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

}
