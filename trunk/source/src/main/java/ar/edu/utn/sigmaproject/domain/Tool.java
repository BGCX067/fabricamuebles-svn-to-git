/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.utn.sigmaproject.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 *
 * @author DanielRH
 */
@Entity
public class Tool extends IdentificableEntity {
    private String name;

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
