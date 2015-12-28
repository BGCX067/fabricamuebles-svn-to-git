package ar.edu.utn.sigmaproject.view.converter;

import ar.edu.utn.sigmaproject.domain.IdentificableEntity;
import ar.edu.utn.sigmaproject.view.util.SelectItemsUtils;
import org.springframework.util.Assert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * User: franco
 * Date: 03/10/12
 * Time: 12:28
 */
public class IdentificableEntityConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return SelectItemsUtils.findValueByStringConversion(context, component, value, this);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Assert.isTrue(value instanceof IdentificableEntity);
        IdentificableEntity identificableEntity = (IdentificableEntity) value;
        return identificableEntity.getUid();
    }
}
