package ar.edu.utn.sigmaproject.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * User: zaba
 * Date: 25/07/12
 */
@Entity
public class WoodType extends IdentificableEntity {
    private String name;

    @Column(nullable = false, unique = true)
    @NotEmpty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
