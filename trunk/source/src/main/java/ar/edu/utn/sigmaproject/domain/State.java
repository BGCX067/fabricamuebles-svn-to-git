package ar.edu.utn.sigmaproject.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Gian Franco Zabarino
 * Date: 24/08/12
 */
@Entity
public class State extends IdentificableEntity {
    private String name;
    private List<City> cities;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "state")
    @OrderBy("name")
    public List<City> getCities() {
        if (cities == null) {
            cities = new ArrayList<City>();
        }
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
