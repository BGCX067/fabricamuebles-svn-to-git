package ar.edu.utn.sigmaproject.controller.administration;

import ar.edu.utn.sigmaproject.controller.AbstractController;
import ar.edu.utn.sigmaproject.controller.AbstractModel;
import ar.edu.utn.sigmaproject.domain.filter.UserFilter;
import ar.edu.utn.sigmaproject.domain.security.Role;
import ar.edu.utn.sigmaproject.domain.security.User;
import ar.edu.utn.sigmaproject.service.AbstractService;
import ar.edu.utn.sigmaproject.service.UserService;
import ar.edu.utn.sigmaproject.view.datamodel.DefaultSelectionDataModelListener;
import ar.edu.utn.sigmaproject.view.datamodel.SelectableDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Controller("userController")
@Scope
public class UserController extends AbstractController<User> {

	@Autowired
	private UserService service;

	@Autowired
	private UserModel model;

    @Override
	public void init() {
        super.init();
		model.setFilter(new UserFilter());
	}

    @Override
    protected void bindModelToEntity() {
        User editedUser = model.getEditedEntity();
        editedUser.setFirstName(model.getFirstName());
        editedUser.setLastName(model.getLastName());
        editedUser.setEmail(model.getEmail());
        editedUser.setUsername(model.getUsername());
        if (model.isNewMode()) {
            editedUser.setPassword(model.getPassword());
        }
        editedUser.getRoles().clear();
        editedUser.getRoles().addAll(
                model.getRolesSelectionListener().getSelectedElements());
    }

    @Override
    protected void bindEntityToModel() {
        User editedUser = model.getEditedEntity();
        model.setFirstName(editedUser.getFirstName());
        model.setLastName(editedUser.getLastName());
        model.setEmail(editedUser.getEmail());
        model.setUsername(editedUser.getUsername());
        model.setPassword(editedUser.getPassword());
        DefaultSelectionDataModelListener<Role> selectionListener = new DefaultSelectionDataModelListener<Role>(
                new HashSet<Role>(editedUser.getRoles()));
        SelectableDataModel<Role> permissionDataModel = new SelectableDataModel<Role>(
                service.getAllRoles(), selectionListener);
        model.setRolesDataModel(permissionDataModel);
        model.setRolesSelectionListener(selectionListener);
    }

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    public void save() {
		service.saveUser(model.getEditedEntity(), model.isNewMode());
	}

	public void prepareResetPassword() {
		model.setPassword(null);
		model.setConfirmPassword(null);
	}

    @Transactional(readOnly = true)
	public void confirmResetPassword() {
		User editedUser = model.getEditedEntity();
		editedUser.setPassword(model.getPassword());
        // The password should be hashed, so true is passed as argument
		service.saveUser(editedUser, true);
	}

    @Override
    protected AbstractModel getModel() {
        return model;
    }

    @Override
    protected AbstractService getService() {
        return service;
    }
}
