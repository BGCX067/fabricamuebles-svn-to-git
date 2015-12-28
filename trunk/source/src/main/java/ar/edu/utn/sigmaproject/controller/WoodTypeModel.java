package ar.edu.utn.sigmaproject.controller;

import ar.edu.utn.sigmaproject.domain.WoodType;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;

/**
 * User: zaba
 * Date: 25/07/12
 */
@Controller("woodTypeModel")
@Scope(value = "flow", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class WoodTypeModel extends AbstractModel<WoodType> {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
