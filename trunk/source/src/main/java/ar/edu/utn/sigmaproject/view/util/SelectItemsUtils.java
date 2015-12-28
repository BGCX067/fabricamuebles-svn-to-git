package ar.edu.utn.sigmaproject.view.util;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import java.util.*;

public final class SelectItemsUtils {

	private SelectItemsUtils() {}

	public static Object findValueByStringConversion(FacesContext context, UIComponent component, String value, Converter converter) {
		return findValueByStringConversion(context, component, createSelectItems(component), value,
				converter);
	}

	private static Object findValueByStringConversion(FacesContext context, UIComponent component, List<SelectItem> items, String value, Converter converter) {
		for (SelectItem item : items) {
			if (item instanceof SelectItemGroup) {
				SelectItem subitems[] = ((SelectItemGroup) item).getSelectItems();
				if (!isEmpty(subitems)) {
					Object object = findValueByStringConversion(context, component, Arrays.asList(subitems), value, converter);
					if (object != null) {
						return object;
					}
				}
			} else if (!item.isNoSelectionOption() && value.equals(converter.getAsString(context, component, item.getValue()))) {
				return item.getValue();
			}
		}
		return null;
	}

	public static boolean isEmpty(Object[] array) {
		return array == null || array.length == 0;
	}

    public static List<SelectItem> createSelectItems(UIComponent component) {
        List<SelectItem> items = new ArrayList<SelectItem>();
        Iterator<UIComponent> children = component.getChildren().iterator();

        while(children.hasNext()) {
            UIComponent child = children.next();

            if(child instanceof UISelectItem) {
                UISelectItem selectItem = (UISelectItem) child;

                items.add(new SelectItem(selectItem.getItemValue(), selectItem.getItemLabel()));
            } else if(child instanceof UISelectItems) {
                Object selectItems = ((UISelectItems) child).getValue();

                if(selectItems instanceof SelectItem[]) {
                    SelectItem[] itemsArray = (SelectItem[]) selectItems;

                    for(SelectItem item : itemsArray)
                        items.add(new SelectItem(item.getValue(), item.getLabel()));

                } else if(selectItems instanceof Collection) {
                    Collection collection = (Collection) selectItems;

                    for(Object item : collection)
                        items.add(new SelectItem(item));
                }
            }
        }

        return items;
    }
}