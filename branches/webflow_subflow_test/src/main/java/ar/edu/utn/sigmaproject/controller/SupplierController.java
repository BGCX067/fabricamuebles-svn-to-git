package ar.edu.utn.sigmaproject.controller;

import ar.edu.utn.sigmaproject.domain.City;
import ar.edu.utn.sigmaproject.domain.IdentificableEntity;
import ar.edu.utn.sigmaproject.domain.State;
import ar.edu.utn.sigmaproject.domain.Supplier;
import ar.edu.utn.sigmaproject.domain.filter.SupplierFilter;
import ar.edu.utn.sigmaproject.service.AbstractService;
import ar.edu.utn.sigmaproject.service.AddressService;
import ar.edu.utn.sigmaproject.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import java.util.List;

/**
 * User: Gian Franco Zabarino
 * Date: 23/08/12
 */
@Controller("supplierController")
public class SupplierController extends AbstractController<Supplier> implements Converter {

    private final static String STATE_TYPE_ENTITY_TYPE = "s";
    private final static String CITY_TYPE_ENTITY_TYPE = "c";

    @Autowired
    private SupplierModel model;

    @Autowired
    private PersonService<Supplier> service;

    @Autowired
    private AddressService addressService;

    @Override
    public void init() {
        super.init();
        model.setFilter(new SupplierFilter());
        model.setIdentityTypes(service.getEntityTypes());
    }

    public void populateNonEntityPropertiesToModel() {
        model.setIdentityTypes(service.getEntityTypes());
        List<State> states = addressService.findAllStates();
        model.setStates(states);
        /* If the view is rendered for the first time, the model's state will not be set.
         * We set it here, so the view can render it's cities
         */
        if (model.getState() == null && !CollectionUtils.isEmpty(states)) {
            model.setState(states.get(0));
        }
        updateStateCities();
    }

    public void prepareEditCity() {
        addressService.markCityForUpdate(model.getCity());
    }

    public void prepareEditState() {
        addressService.markStateForUpdate(model.getState());
    }

    public void setCityWithUid(String uid) {
        model.setCity(addressService.findCityByUid(uid));
    }

    public void setStateWithUid(String uid) {
        model.setState(addressService.findStateByUid(uid));
    }

    public void updateStateCities() {
        if (model.getState() != null) {
            List<City> cities = addressService.findStateCities(model.getState());
            model.setCities(cities);
            if (!CollectionUtils.isEmpty(cities)) {
                if (model.getCity() == null || !(model.getCity().getState().equals(model.getState()))) {
                    model.setCity(cities.get(0));
                }
            } else {
                model.setCity(null);
            }
        }
    }

    @Override
    protected AbstractModel<Supplier> getModel() {
        return model;
    }

    @Override
    protected AbstractService<Supplier> getService() {
        return service;
    }

    @Override
    protected void bindModelToEntity() {
        Supplier editedSupplier = model.getEditedEntity();
        editedSupplier.getAddress().setApartment(model.getApartment());
        editedSupplier.getAddress().setCity(model.getCity());
        editedSupplier.setEmail(model.getEmail());
        editedSupplier.getName().setFirst(model.getFirstName());
        editedSupplier.getAddress().setFloor(model.getFloor());
        editedSupplier.getIdentity().setIdentityNumber(model.getIdentityNumber());
        editedSupplier.getIdentity().setIdentityType(model.getIdentityType());
        editedSupplier.getName().setLast(model.getLastName());
        editedSupplier.getAddress().setNumber(model.getNumber());
        editedSupplier.getAddress().setStreet(model.getStreet());
        editedSupplier.setPhoneNumber(model.getPhoneNumber());
    }

    @Override
    protected void bindEntityToModel() {
        Supplier editedSupplier = model.getEditedEntity();
        model.setApartment(editedSupplier.getAddress().getApartment());
        model.setCity(editedSupplier.getAddress().getCity());
        model.setEmail(editedSupplier.getEmail());
        model.setFirstName(editedSupplier.getName().getFirst());
        model.setFloor(editedSupplier.getAddress().getFloor());
        model.setIdentityNumber(editedSupplier.getIdentity().getIdentityNumber());
        model.setIdentityType(editedSupplier.getIdentity().getIdentityType());
        model.setLastName(editedSupplier.getName().getLast());
        model.setNumber(editedSupplier.getAddress().getNumber());
        model.setState(editedSupplier.getAddress().getCity() != null ? editedSupplier.getAddress().getCity().getState() : null);
        model.setStreet(editedSupplier.getAddress().getStreet());
        model.setPhoneNumber(editedSupplier.getPhoneNumber());
    }

    @Override
    protected Class<Supplier> getEntityClass() {
        return Supplier.class;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value.contains(";")) {
            final String entityType = value.substring(0, value.indexOf(";"));
            final String entityUid = value.substring(value.indexOf(";") + 1);
            if (entityType.equals(STATE_TYPE_ENTITY_TYPE)) {
                return addressService.findStateByUid(entityUid);
            } else if (entityType.equals(CITY_TYPE_ENTITY_TYPE)) {
                return addressService.findCityByUid(entityUid);
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof IdentificableEntity) {
            IdentificableEntity entity = (IdentificableEntity) value;
            if (value instanceof State) {
                return STATE_TYPE_ENTITY_TYPE + ";" + entity.getUid();
            } else if (value instanceof City) {
                return CITY_TYPE_ENTITY_TYPE + ";" + entity.getUid();
            }
        }
        return null;
    }
}
