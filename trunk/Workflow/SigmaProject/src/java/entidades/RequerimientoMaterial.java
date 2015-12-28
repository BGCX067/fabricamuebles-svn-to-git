/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 *
 * @author Zaba
 */
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="Tipo")
@DiscriminatorValue(value="General")
public class RequerimientoMaterial implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="idFamilia")
    private Familia familia;
    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name="idMuebleParte")
    private MuebleParte muebleParte;
    @Min(value=1)
    private BigDecimal cantidadRequerida = new BigDecimal(0);

    public Integer getId() {
        return id;
    }

    public Requerible getItemRequerible() {
        if (familia != null) {
            return familia;
        } else if (muebleParte != null) {
            return muebleParte;
        }
        return null;
    }

    public void setItemRequerible(Requerible itemRequerible) {
        if (itemRequerible instanceof Articulo) {
            this.familia = (Familia) itemRequerible;
            this.muebleParte = null;
        } else if (itemRequerible instanceof MuebleParte) {
            this.familia = null;
            this.muebleParte = (MuebleParte) itemRequerible;
        }
    }

    public BigDecimal getCantidadRequerida() {
        return cantidadRequerida;
    }

    public void setCantidadRequerida(BigDecimal cantidadRequerida) {
        this.cantidadRequerida = cantidadRequerida;
    }

    public void agregarCantidadRequerida(BigDecimal cantidadAAgregar) {
        if (this.cantidadRequerida == null) {
            this.cantidadRequerida = new BigDecimal(0);
        }
        this.cantidadRequerida = this.cantidadRequerida.add(cantidadAAgregar);
    }

    public void quitarCantidadRequerida(BigDecimal cantidadAQuitar) {
        if (this.cantidadRequerida == null) {
            this.cantidadRequerida = new BigDecimal(0);
        }
        if (this.cantidadRequerida.subtract(cantidadAQuitar).signum() == -1) {
            throw new AssertionError("Se pidio quitar una cantidad requerida mayor a la actual.");
        }
        this.cantidadRequerida = this.cantidadRequerida.subtract(cantidadAQuitar);
    }
}
