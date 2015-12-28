package ar.edu.utn.sigmaproject.controller;

import ar.edu.utn.sigmaproject.domain.*;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * User: Gian Franco Zabarino
 * Date: 23/08/12
 */
@Controller("supplierModel")
@Scope(value = "flow", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SupplierModel extends AbstractModel<Supplier> {

    private String firstName;
    private String lastName;
    private List<Identity.IdentityType> identityTypes;
    private Identity.IdentityType identityType;
    private String identityNumber;
    private String phoneNumber;
    private String email;
    private List<State> states;
    private State state;
    private String stateName;
    private List<City> cities;
    private City city;
    private String street;
    private String number;
    private String floor;
    private String apartment;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Identity.IdentityType> getIdentityTypes() {
        return identityTypes;
    }

    public void setIdentityTypes(List<Identity.IdentityType> identityTypes) {
        this.identityTypes = identityTypes;
    }

    public Identity.IdentityType getIdentityType() {
        return identityType;
    }

    public void setIdentityType(Identity.IdentityType identityType) {
        this.identityType = identityType;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

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

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }
}
