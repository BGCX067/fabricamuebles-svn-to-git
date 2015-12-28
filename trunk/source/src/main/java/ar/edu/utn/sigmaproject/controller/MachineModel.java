/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.utn.sigmaproject.controller;

import ar.edu.utn.sigmaproject.domain.Machine;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;

/**
 *
 * @author DanielRH
 */
@Controller("machineModel")
@Scope(value = "flow", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MachineModel extends AbstractModel<Machine> {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
