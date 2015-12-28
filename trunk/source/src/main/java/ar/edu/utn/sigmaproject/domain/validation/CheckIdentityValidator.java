package ar.edu.utn.sigmaproject.domain.validation;

import ar.edu.utn.sigmaproject.domain.Identity;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * User: Gian Franco Zabarino
 * Date: 23/08/12
 */
public class CheckIdentityValidator implements ConstraintValidator<CheckIdentity, Identity> {

    @Override
    public void initialize(CheckIdentity constraintAnnotation) {

    }

    @Override
    public boolean isValid(Identity value, ConstraintValidatorContext context) {
        if (value.getIdentityType() == null || !StringUtils.hasText(value.getIdentityNumber())) {
            return false;
        }
        if (value.getIdentityType().equals(Identity.IdentityType.CUIL) || value.getIdentityType().equals(Identity.IdentityType.CUIT)) {
            String identityNumber = value.getIdentityNumber();
            if (identityNumber == null) {
                return false;
            } else if (identityNumber.matches("[ˆ0-9\\-]")) {
                return false;
            } else if (identityNumber.length() != 13) {
                return false;
            } else {
                String onlyNumbers = identityNumber.replaceAll("\\-", "");
                if (!isNumeric(onlyNumbers)) {
                    return false;
                }
                int[] multipliers = {5, 4, 3, 2, 7, 6, 5, 4, 3, 2};
                int i = 0;
                int accumulator = 0;
                for (char c : onlyNumbers.toCharArray()) {
                    accumulator += Integer.parseInt(new String(new char[] {c})) * multipliers[i++];
                    if (i == 10) {
                        break;
                    }
                }
                int mod = accumulator % 11;
                int verifier = 11 - mod;
                if (verifier == 11) {
                    verifier = 0;
                } else if (verifier == 10){
                    verifier = 9;
                }
                return (verifier == Integer.parseInt(new String(new char[] {onlyNumbers.charAt(10)})));
            }
        } else if (value.getIdentityType().equals(Identity.IdentityType.DNI) && isNumeric(value.getIdentityNumber())) {
            return value.getIdentityNumber().length() >= 7;
        } else if (isNumeric(value.getIdentityNumber())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isNumeric(String s) {
        final String numbers = "0123456789";
        for (char c : s.toCharArray()) {
            if (numbers.indexOf(c) == -1) {
                return false;
            }
        }
        return true;
    }
}
