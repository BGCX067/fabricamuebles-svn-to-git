package ar.edu.utn.sigmaproject.controller;

import ar.edu.utn.sigmaproject.domain.WoodType;
import ar.edu.utn.sigmaproject.service.AbstractService;
import ar.edu.utn.sigmaproject.service.WoodTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * User: zaba
 * Date: 25/07/12
 */
@Controller("woodTypeController")
@Scope
public class WoodTypeController extends AbstractController<WoodType> {
    @Autowired
    private WoodTypeService service;

    @Autowired
    private WoodTypeModel model;

    protected void bindModelToEntity() {
        WoodType editedWoodType = model.getEditedEntity();
        editedWoodType.setName(model.getName());
    }

    protected void bindEntityToModel() {
        WoodType editedWoodType = model.getEditedEntity();
        model.setName(editedWoodType.getName());
    }

    @Override
    protected Class<WoodType> getEntityClass() {
        return WoodType.class;
    }

    @Override
    protected AbstractModel<WoodType> getModel() {
        return model;
    }

    @Override
    protected AbstractService<WoodType> getService() {
        return service;
    }
}
