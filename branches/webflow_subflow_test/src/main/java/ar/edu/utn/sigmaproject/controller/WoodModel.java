package ar.edu.utn.sigmaproject.controller;

import ar.edu.utn.sigmaproject.domain.Wood;
import ar.edu.utn.sigmaproject.domain.WoodType;
import ar.edu.utn.sigmaproject.domain.filter.WoodFilter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.List;

/**
 * User: zaba
 * Date: 25/07/12
 */
@Controller("woodModel")
@Scope(value = "flow", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class WoodModel extends AbstractModel<Wood> {
    private WoodFilter filter;
    private String description;
    private WoodType woodType;
    private BigDecimal height = BigDecimal.ZERO;
    private BigDecimal width = BigDecimal.ZERO;
    private BigDecimal depth = BigDecimal.ZERO;
    private List<WoodType> woodTypeList;

    public WoodFilter getFilter() {
        return filter;
    }

    public void setFilter(WoodFilter filter) {
        this.filter = filter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public WoodType getWoodType() {
        return woodType;
    }

    public void setWoodType(WoodType woodType) {
        this.woodType = woodType;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getDepth() {
        return depth;
    }

    public void setDepth(BigDecimal depth) {
        this.depth = depth;
    }

    public List<WoodType> getWoodTypeList() {
        return woodTypeList;
    }

    public void setWoodTypeList(List<WoodType> woodTypeList) {
        this.woodTypeList = woodTypeList;
    }
}
