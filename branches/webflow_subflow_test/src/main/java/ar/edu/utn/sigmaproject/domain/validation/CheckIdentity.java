package ar.edu.utn.sigmaproject.domain.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Check de identity number of a Person, if the identity type is CUIL or CUIT. Otherwise,
 * the Person will be valid regarding this constraint.<br/><br/>
 *
 * User: Gian Franco Zabarino
 * Date: 23/08/12
 */

@Target( { TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = CheckIdentityValidator.class)
public @interface CheckIdentity {
    String message() default "{ar.edu.utn.sigmaproject.constraints.checkidentity}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
