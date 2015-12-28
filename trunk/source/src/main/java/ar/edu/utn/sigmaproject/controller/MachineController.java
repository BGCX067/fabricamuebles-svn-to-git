/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.utn.sigmaproject.controller;

import ar.edu.utn.sigmaproject.domain.Machine;
import ar.edu.utn.sigmaproject.service.AbstractService;
import ar.edu.utn.sigmaproject.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author DanielRH
 */
@Controller("machineController")
@Scope
public class MachineController extends AbstractController<Machine> {
    @Autowired
    private MachineService service;

    @Autowired
    private MachineModel model;

    protected void bindModelToEntity() {
        Machine editedMachine = model.getEditedEntity();
        editedMachine.setName(model.getName());
    }

    protected void bindEntityToModel() {
        Machine editedMachine = model.getEditedEntity();
        model.setName(editedMachine.getName());
    }

    @Override
    protected Class<Machine> getEntityClass() {
        return Machine.class;
    }

    @Override
    protected AbstractModel<Machine> getModel() {
        return model;
    }

    @Override
    protected AbstractService<Machine> getService() {
        return service;
    }
}
