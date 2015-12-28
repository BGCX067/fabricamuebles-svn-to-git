package ar.edu.utn.sigmaproject.controller.administration;

import ar.edu.utn.sigmaproject.controller.AbstractController;
import ar.edu.utn.sigmaproject.controller.AbstractModel;
import ar.edu.utn.sigmaproject.domain.filter.RoleFilter;
import ar.edu.utn.sigmaproject.domain.security.Permission;
import ar.edu.utn.sigmaproject.domain.security.Role;
import ar.edu.utn.sigmaproject.service.AbstractService;
import ar.edu.utn.sigmaproject.service.RoleService;
import ar.edu.utn.sigmaproject.view.datamodel.DefaultSelectionDataModelListener;
import ar.edu.utn.sigmaproject.view.datamodel.SelectableDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.HashSet;

@Controller("roleController")
@Scope
public class RoleController extends AbstractController<Role> {

	@Autowired
	private RoleService service;

	@Autowired
	private RoleModel model;

    @Override
	public void init() {
        super.init();
		model.setFilter(new RoleFilter());
	}

    @Override
    protected void bindModelToEntity() {
        Role editedRole = model.getEditedEntity();
        editedRole.setName(model.getName());
        editedRole.setDescription(model.getDescription());
        editedRole.getPermissions().clear();
        editedRole.getPermissions().addAll(
                model.getPermissionSelectionListener()
                        .getSelectedElements());
    }

    @Override
    protected void bindEntityToModel() {
        Role editedRole = model.getEditedEntity();
        model.setName(editedRole.getName());
        model.setDescription(editedRole.getDescription());

        DefaultSelectionDataModelListener<Permission> selectionListener = new DefaultSelectionDataModelListener<Permission>(
                new HashSet<Permission>(editedRole.getPermissions()));
        SelectableDataModel<Permission> permissionDataModel = new SelectableDataModel<Permission>(
                service.getAllPermissions(), selectionListener);
        model.setPermissionsDataModel(permissionDataModel);
        model.setPermissionSelectionListener(selectionListener);
    }

    @Override
    protected Class<Role> getEntityClass() {
        return Role.class;
    }

    @Override
    protected AbstractModel<Role> getModel() {
        return model;
    }

    @Override
    protected AbstractService<Role> getService() {
        return service;
    }
}
