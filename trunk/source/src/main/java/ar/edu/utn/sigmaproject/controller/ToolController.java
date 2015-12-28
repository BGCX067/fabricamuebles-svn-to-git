/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.utn.sigmaproject.controller;

import ar.edu.utn.sigmaproject.domain.Tool;
import ar.edu.utn.sigmaproject.service.AbstractService;
import ar.edu.utn.sigmaproject.service.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author DanielRH
 */
@Controller("toolController")
@Scope
public class ToolController extends AbstractController<Tool> {
    @Autowired
    private ToolService service;

    @Autowired
    private ToolModel model;

    protected void bindModelToEntity() {
        Tool editedTool = model.getEditedEntity();
        editedTool.setName(model.getName());
    }

    protected void bindEntityToModel() {
        Tool editedTool = model.getEditedEntity();
        model.setName(editedTool.getName());
    }
    
    @Override
    protected Class<Tool> getEntityClass() {
        return Tool.class;
    }

    @Override
    protected AbstractModel<Tool> getModel() {
        return model;
    }

    @Override
    protected AbstractService<Tool> getService() {
        return service;
    }
    
}
