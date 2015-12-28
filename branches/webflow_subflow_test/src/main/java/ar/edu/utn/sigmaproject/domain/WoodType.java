package ar.edu.utn.sigmaproject.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * User: zaba
 * Date: 25/07/12
 */
@Entity
public class WoodType extends IdentificableEntity {
    private String name;

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
