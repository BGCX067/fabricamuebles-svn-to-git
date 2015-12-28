package ar.edu.utn.sigmaproject.domain;

import ar.edu.utn.sigmaproject.domain.validation.CheckIdentity;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * User: Gian Franco Zabarino
 * Date: 31/08/12
 */
@Embeddable
@CheckIdentity
public class Identity implements Serializable {
    private IdentityType identityType = IdentityType.CUIT;
    private String identityNumber;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    @NotNull
    public IdentityType getIdentityType() {
        return identityType;
    }

    public void setIdentityType(IdentityType identityType) {
        this.identityType = identityType;
    }

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber.trim();
    }

    public enum IdentityType {
        DNI, CUIT, CUIL;
    }
}
