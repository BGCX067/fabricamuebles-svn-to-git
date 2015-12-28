/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.utn.sigmaproject.controller;

import ar.edu.utn.sigmaproject.domain.Tool;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;

/**
 *
 * @author DanielRH
 */
@Controller("toolModel")
@Scope(value = "flow", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ToolModel extends AbstractModel<Tool> {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
