package ar.edu.utn.sigmaproject.domain;

import ar.edu.utn.sigmaproject.view.util.RoundingUtils;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * User: zaba
 * Date: 25/07/12
 */
@Entity
@DiscriminatorValue("wood")
public class Wood extends Product {
    private WoodType woodType;
    private BigDecimal height;
    private BigDecimal width;
    private BigDecimal depth;

    @Transient
    @Override
    public String getPrintableName() {
        if (woodType != null) {
            return woodType.getName() + " " + height + "x" + width + "x" + depth;
        } else {
            return "empty wood";
        }
    }

    @ManyToOne(optional = false)
    @NotNull
    public WoodType getWoodType() {
        return woodType;
    }

    public void setWoodType(WoodType woodType) {
        this.woodType = woodType;
    }

    @Column(nullable = false)
    @DecimalMin(value = "0.1")
    @NotNull
    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = RoundingUtils.roundWoodSize(height);
    }

    @Column(nullable = false)
    @DecimalMin(value = "0.1")
    @NotNull
    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = RoundingUtils.roundWoodSize(width);
    }

    @Column(nullable = false)
    @DecimalMin(value = "0.1")
    @NotNull
    public BigDecimal getDepth() {
        return depth;
    }

    public void setDepth(BigDecimal depth) {
        this.depth = RoundingUtils.roundWoodSize(depth);
    }
}
