/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades;

import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Zaba
 */
@Entity
@DiscriminatorValue(value="Proveedor")
public class Proveedor extends Entidad {
    private Boolean habilitado = true;
    @NotNull
    private String nroCUIT;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="idProveedor")
    private Set<ArticuloPresentacionPrecioProveedor> precioArticulos;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="idProveedor")
    private Set<MaderaTipoPrecioProveedor> precioTiposMadera;
    private Integer tiempoEntregaEnDias;

    public Integer getTiempoEntregaEnDias() {
        return tiempoEntregaEnDias;
    }

    public void setTiempoEntregaEnDias(Integer tiempoEntregaEnDias) {
        this.tiempoEntregaEnDias = tiempoEntregaEnDias;
    }

    public String getNroCUIT() {
        return nroCUIT;
    }

    public void setNroCUIT(String nroCUIT) {
        this.nroCUIT = nroCUIT;
    }

    public Set<ArticuloPresentacionPrecioProveedor> getPrecioArticulos() {
        return precioArticulos;
    }
    public Boolean getHabilitado() {
        return habilitado;
    }
    //este metodo fue creado debido a que en el compononte listadoProveedores no funcionaba de manera correcta
    //el metodo getHabilitado el cual retorna un boolean
    public String SHabilitado(){
        String a="Si";
        if(habilitado==true){
            a="Si";
        }
        else
        {a="No";}
        return a;
    }
    public void setHabilitado(Boolean h) {
        this.habilitado = h;
    }
    public void setPrecioArticulos(Set<ArticuloPresentacionPrecioProveedor> precioArticulos) {
        this.precioArticulos = precioArticulos;
    }

    public Set<MaderaTipoPrecioProveedor> getPrecioTiposMadera() {
        return precioTiposMadera;
    }

    public void setPrecioTiposMadera(Set<MaderaTipoPrecioProveedor> precioTiposMadera) {
        this.precioTiposMadera = precioTiposMadera;
    }
}
