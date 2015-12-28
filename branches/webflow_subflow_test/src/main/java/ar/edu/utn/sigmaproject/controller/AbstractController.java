package ar.edu.utn.sigmaproject.controller;


import ar.edu.utn.sigmaproject.domain.IdentificableEntity;
import ar.edu.utn.sigmaproject.service.AbstractService;
import ar.edu.utn.sigmaproject.view.datamodel.ListDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public abstract class AbstractController<E extends IdentificableEntity> {

    @Autowired
    protected MessageSource messageSource;

    protected abstract AbstractModel<E> getModel();
    protected abstract AbstractService<E> getService();
    protected abstract void bindModelToEntity();
    protected abstract void bindEntityToModel();
    protected abstract Class<E> getEntityClass();

	protected final static String CONFIRMATION_MESSAGE_KEY = "confirmationMessage";

    public void init() {
        getModel().setMode(UseCaseMode.SEARCH);
    }

    public void populateNonEntityPropertiesToModel() {

    }

    @Transactional(readOnly = true)
    public void search() {
        List<E> entityList = getService().getList(getModel().getFilter());
        getModel().setList(new ListDataModel<E>(entityList));
        getModel().setPageNumber(1);
    }

    @Transactional(readOnly = true)
    public void prepareEntityRender(String uid, UseCaseMode useCaseMode) throws InstantiationException,
			IllegalAccessException {
        if (((useCaseMode.equals(UseCaseMode.EDIT) || useCaseMode.equals(UseCaseMode.CONSULT)) && !StringUtils.hasText(uid)) ||
                (useCaseMode.equals(UseCaseMode.NEW) && StringUtils.hasText(uid)) ||
                useCaseMode.equals(UseCaseMode.SEARCH)) {
            throw new IllegalStateException();
        }
        getModel().setMode(useCaseMode);
        switch (useCaseMode) {
            case NEW:
            getModel().setEditedEntity(getEntityClass().newInstance());
                break;
            case EDIT: case CONSULT:
                getModel().setEditedEntity(getService().findByUid(uid));
        }
        bindEntityToModel();
    }

	@Transactional(readOnly = true)
    public boolean confirmSave(MessageContext messageContext, Locale locale) {
        bindModelToEntity();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<E>> constraintViolations = validator.validate(getModel().getEditedEntity());
        if (constraintViolations != null && constraintViolations.size() > 0) {
            for (ConstraintViolation constraintViolation : constraintViolations) {
                messageContext.addMessage(new MessageBuilder()
                        .error()
                        .source(constraintViolation.getPropertyPath().toString().replace('.','_'))
                        .defaultText(constraintViolation.getMessage())
                        .build());
            }
            return false;
        }
        save();
		messageContext.addMessage(new MessageBuilder()
				.info()
				.defaultText(messageSource.getMessage(CONFIRMATION_MESSAGE_KEY, null, locale))
				.build());
        return true;
    }

    protected void save() {
        getService().saveEntity(getModel().getEditedEntity());
    }

	@Transactional(readOnly = true)
    public void delete() {
        performDelete();
    }

    private void performDelete() {
        getService().deleteEntity(getModel().getEditedEntity());
    }

}
