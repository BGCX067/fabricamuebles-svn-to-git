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
import javax.validation.constraints.NotNull;

/**
 *
 * @author Zaba
 */
@Entity
public class Direccion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*
     * Puse todas esas cascadas para que no me borre la localidad cuando borro su direccion (falta CascadeType.REMOVE)
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "idLocalidad")
    @NotNull
    private Localidad localidad;

    /*
     * Puse todas esas cascadas para que no me borre la entidad cuando borro su direccion (falta CascadeType.REMOVE)
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "idEntidad")
    @NotNull
    private Entidad entidad;
    private String tipo;
    private String calle;
    private String numero;
    private String piso;
    private String dpto;

    public Integer getId() {
        return id;
    }

    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDpto() {
        return dpto;
    }

    public void setDpto(String dpto) {
        this.dpto = dpto;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.calle);
        if (this.numero != null) {
            sb.append(" N° ").append(this.numero);
        }
        sb.append(", ");
        if (this.piso != null && this.dpto != null && !this.dpto.isEmpty()) {
            sb.append(this.piso).append("° \"").append(this.dpto).append("\", ");
        } else if (this.piso != null) {
            sb.append(this.piso).append("°, ");
        }
        sb.append(this.localidad.getNombre()).append(", ").append(this.localidad.getDepartamento().getProvincia().getNombre());
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Direccion other = (Direccion) obj;
        if (this.id != null && other.id != null) {
            if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
                return false;
            }
        } else {
            if (this.localidad != other.localidad && (this.localidad == null || !this.localidad.equals(other.localidad))) {
                return false;
            }
            if (this.entidad != other.entidad && (this.entidad == null || !this.entidad.equals(other.entidad))) {
                return false;
            }
            if ((this.tipo == null) ? (other.tipo != null) : !this.tipo.equals(other.tipo)) {
                return false;
            }
            if ((this.calle == null) ? (other.calle != null) : !this.calle.equals(other.calle)) {
                return false;
            }
            if (this.numero != other.numero && (this.numero == null || !this.numero.equals(other.numero))) {
                return false;
            }
            if (this.piso != other.piso && (this.piso == null || !this.piso.equals(other.piso))) {
                return false;
            }
            if ((this.dpto == null) ? (other.dpto != null) : !this.dpto.equals(other.dpto)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 37 * hash + (this.localidad != null ? this.localidad.hashCode() : 0);
        hash = 37 * hash + (this.entidad != null ? this.entidad.hashCode() : 0);
        hash = 37 * hash + (this.tipo != null ? this.tipo.hashCode() : 0);
        hash = 37 * hash + (this.calle != null ? this.calle.hashCode() : 0);
        hash = 37 * hash + (this.numero != null ? this.numero.hashCode() : 0);
        hash = 37 * hash + (this.piso != null ? this.piso.hashCode() : 0);
        hash = 37 * hash + (this.dpto != null ? this.dpto.hashCode() : 0);
        return hash;
    }
}
