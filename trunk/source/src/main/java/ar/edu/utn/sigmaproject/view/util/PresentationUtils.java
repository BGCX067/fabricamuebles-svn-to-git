package ar.edu.utn.sigmaproject.view.util;

import ar.edu.utn.sigmaproject.domain.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;

/**
 * User: Gian Franco Zabarino
 * Date: 29/10/12
 */
public class PresentationUtils {

    @Autowired
    private MessageSource messageSource;

    public String formatAddress(Address address) {
        StringBuilder sb = new StringBuilder();
        sb.append(address.getStreet());
        if (address.getNumber() != null) {
            sb.append(' ').append(address.getNumber());
        }
        if (StringUtils.hasText(address.getFloor())) {
            sb.append(' ')
                    .append(messageSource.getMessage("address.admin.floor", null, LocaleContextHolder.getLocale()))
                    .append(' ')
                    .append(address.getFloor());
        }
        if (StringUtils.hasText(address.getApartment())) {
            sb.append(' ')
                    .append(messageSource.getMessage("address.admin.apartment", null, LocaleContextHolder.getLocale()))
                    .append(' ')
                    .append(address.getApartment());
        }
        sb.append(", ")
                .append(address.getCity().getName())
                .append(", ")
                .append(address.getCity().getState().getName());
        return sb.toString();
    }
}
