package ar.edu.utn.sigmaproject.controller;

import ar.edu.utn.sigmaproject.domain.City;
import ar.edu.utn.sigmaproject.domain.State;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;

import java.io.Serializable;

/**
 * User: Gian Franco Zabarino
 * Date: 04/09/12
 */
@Controller("addressModel")
@Scope(value = "flow", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AddressModel implements Serializable {

    private State state;
    private String stateName;
    private City city;
    private String cityName;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
