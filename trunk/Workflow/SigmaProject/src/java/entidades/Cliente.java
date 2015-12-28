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
@DiscriminatorValue(value="Cliente")
public class Cliente extends Entidad {
    private Boolean habilitado = true;
    @NotNull
    private String dni;
    private String nroCUIT;


    public String getDni(){
        return dni;
    }
    public void setDni(String dni){
        this.dni = dni;
    }
    public String getNroCUIT() {
        return nroCUIT;
    }

    public void setNroCUIT(String nroCUIT) {
        this.nroCUIT = nroCUIT;
    }


    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean h) {
        this.habilitado = h;
    }


}
