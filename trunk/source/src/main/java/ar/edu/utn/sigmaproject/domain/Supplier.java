package ar.edu.utn.sigmaproject.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * User: Gian Franco Zabarino
 * Date: 19/08/12
 */
@Entity
@DiscriminatorValue("supplier")
public class Supplier extends Person {

}
