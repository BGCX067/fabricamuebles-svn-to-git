package ar.edu.utn.sigmaproject.domain.filter;

import ar.edu.utn.sigmaproject.domain.Wood;
import ar.edu.utn.sigmaproject.domain.WoodType;

/**
 * User: zaba
 * Date: 25/07/12
 */
public class WoodFilter extends ProductFilter<Wood> {
    private WoodType woodType;

    public WoodType getWoodType() {
        return woodType;
    }

    public void setWoodType(WoodType woodType) {
        this.woodType = woodType;
    }
}
