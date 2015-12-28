package ar.edu.utn.sigmaproject.controller;

import ar.edu.utn.sigmaproject.domain.City;
import ar.edu.utn.sigmaproject.domain.State;
import ar.edu.utn.sigmaproject.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Locale;

/**
 * User: Gian Franco Zabarino
 * Date: 04/09/12
 */
@Controller("addressController")
@Scope
public class AddressController {

    @Autowired
    private AddressModel addressModel;

    @Autowired
    private AddressService addressService;

    @Transactional(readOnly = true)
    public void populateModelWithCity(String uid, String stateUid) {
        if (!StringUtils.hasText(stateUid)) {
            throw new IllegalArgumentException();
        }
        City city = null;
        if (StringUtils.hasText(uid)) {
            city = addressService.findCityByUid(uid);
            if (city == null) {
                throw new IllegalArgumentException();
            }
            addressModel.setCityName(city.getName());
        } else {
            city = new City();
            State state = addressService.findStateByUid(stateUid);
            if (state == null) {
                throw new IllegalArgumentException();
            }
            city.setState(state);
            addressModel.setCityName("");
        }
        addressModel.setCity(city);
    }

    @Transactional(readOnly = true)
    public void populateModelWithState(String uid) {
        State state = null;
        if (StringUtils.hasText(uid)) {
            state = addressService.findStateByUid(uid);
            if (state == null) {
                throw new IllegalArgumentException();
            }
            addressModel.setStateName(state.getName());
        } else {
            state = new State();
            addressModel.setStateName("");
        }
        addressModel.setState(state);
    }

    @Transactional(readOnly = true)
    public void confirmCitySave(MessageContext messageContext, Locale locale) {
        City city = addressModel.getCity();
        city.setName(addressModel.getCityName());
        addressService.saveCity(city);
    }

    @Transactional(readOnly = true)
    public void confirmStateSave(MessageContext messageContext, Locale locale) {
        State state = addressModel.getState();
        state.setName(addressModel.getStateName());
        addressService.saveState(state);
    }
}
