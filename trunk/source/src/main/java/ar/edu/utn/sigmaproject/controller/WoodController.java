package ar.edu.utn.sigmaproject.controller;

import ar.edu.utn.sigmaproject.domain.Wood;
import ar.edu.utn.sigmaproject.domain.WoodType;
import ar.edu.utn.sigmaproject.domain.filter.WoodFilter;
import ar.edu.utn.sigmaproject.service.AbstractService;
import ar.edu.utn.sigmaproject.service.WoodService;
import ar.edu.utn.sigmaproject.service.WoodTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * User: zaba
 * Date: 25/07/12
 */
@Controller("woodController")
@Scope
public class WoodController extends AbstractController<Wood> {

    @Autowired
    private WoodService service;

    @Autowired
    private WoodModel model;

    @Autowired
    private WoodTypeService woodTypeService;

    @Override
    public void init() {
        super.init();
        model.setFilter(new WoodFilter());
        model.setWoodTypeList(service.getAllWoodTypes());
    }

    public void prepareEditWoodType() {
        WoodType editedWoodType = model.getWoodType();
        service.markForRetrieveFromDB(editedWoodType);
    }

    public void setResultWoodTypeWithUid(String uid) {
        model.setWoodType(woodTypeService.findByUid(uid));
    }

    public void populateNonEntityPropertiesToModel() {
        model.setWoodTypeList(service.getAllWoodTypes());
    }

    protected void bindModelToEntity() {
        Wood editedWood = model.getEditedEntity();
        editedWood.setDescription(model.getDescription());
        editedWood.setDepth(model.getDepth());
        editedWood.setHeight(model.getHeight());
        editedWood.setWidth(model.getWidth());
        editedWood.setWoodType(model.getWoodType());
    }

    protected void bindEntityToModel() {
        Wood editedWood = model.getEditedEntity();
        model.setDescription(editedWood.getDescription());
        model.setWidth(editedWood.getWidth());
        model.setHeight(editedWood.getHeight());
        model.setDepth(editedWood.getDepth());
        model.setWoodType(editedWood.getWoodType());
    }

    @Override
    protected Class<Wood> getEntityClass() {
        return Wood.class;
    }

    @Override
    protected AbstractModel<Wood> getModel() {
        return model;
    }

    @Override
    protected AbstractService<Wood> getService() {
        return service;
    }
}
