package ar.edu.utn.sigmaproject.domain;

import org.hibernate.validator.constraints.Email;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.validation.Valid;

/**
 * User: Gian Franco Zabarino
 * Date: 23/08/12
 */
@Entity
@DiscriminatorColumn(name = "kind")
public class Person extends IdentificableEntity {

    private Name name = new Name();
    private Identity identity = new Identity();
    private Address address = new Address();
    private String phoneNumber;
    private String email;

    @Embedded
    @Valid
    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    @Embedded
    @Valid
    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    @Embedded
    @Valid
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Column
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column(length = 255)
    @Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
