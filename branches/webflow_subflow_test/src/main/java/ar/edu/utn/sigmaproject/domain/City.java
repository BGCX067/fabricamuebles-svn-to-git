package ar.edu.utn.sigmaproject.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * User: Gian Franco Zabarino
 * Date: 24/08/12
 */
@Entity
public class City extends IdentificableEntity {
    private State state;
    private String name;

    @ManyToOne(optional = false)
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
