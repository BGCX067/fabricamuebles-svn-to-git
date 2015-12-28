/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Zaba
 */
@Entity
@DiscriminatorValue(value="Madera")
public class Madera extends Articulo implements Serializable {
    @NotNull
    private Integer alto;
    @NotNull
    private Integer ancho;
    @NotNull
    private Integer largoOriginal;
    @ManyToOne
    @JoinColumn(name="idMaderaTipo")
    @NotNull
    private MaderaTipo maderaTipo;

    public Integer getAlto() {
        return alto;
    }

    public void setAlto(Integer alto) {
        this.alto = alto;
    }

    public Integer getAncho() {
        return ancho;
    }

    public void setAncho(Integer ancho) {
        this.ancho = ancho;
    }

    public Integer getLargoOriginal() {
        return largoOriginal;
    }

    public void setLargoOriginal(Integer largoOriginal) {
        this.largoOriginal = largoOriginal;
    }

    public MaderaTipo getMaderaTipo() {
        return maderaTipo;
    }

    public void setMaderaTipo(MaderaTipo maderaTipo) {
        this.maderaTipo = maderaTipo;
    }

    @Override
    public String getNombre() {
        return new StringBuilder(maderaTipo.getNombre()).append(" ").append(alto).append("x").append(ancho).append("x").append(largoOriginal).toString();
    }

    /**
     * Calcula los pies cubicos de esta madera usando (ancho (pulg.) x alto (pulg.) x largo (pies)) / 12 .
     * @return Retorna un BigDecimal con 3 decimales redondeados.
     */
    public BigDecimal calcularPiesCubicos() {
        return new BigDecimal(this.alto.intValue() * this.ancho.intValue() * this.largoOriginal.intValue())
                .setScale(10, RoundingMode.HALF_EVEN)
                .divide(new BigDecimal(12), 10, RoundingMode.HALF_EVEN);
    }

    public String getTipo() {
        return "Madera";
    }
}