package ar.edu.utn.sigmaproject.domain.filter;

import ar.edu.utn.sigmaproject.domain.Identity;
import ar.edu.utn.sigmaproject.domain.Person;

/**
 * User: Gian Franco Zabarino
 * Date: 23/08/12
 */
public class PersonFilter<E extends Person> extends DefaultEntityFilter<E> {
    private String firstName;
    private String lastName;
    private Identity.IdentityType identityType;
    private String identityNumber;

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
}
