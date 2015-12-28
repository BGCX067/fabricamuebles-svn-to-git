/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilidades;

import java.util.Set;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Zaba
 */
@ManagedBean
public class Reiniciador implements java.io.Serializable {
    public void reiniciarTodos(ActionEvent event) {
        Set<String> keySet = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().keySet();
        for (String s : keySet) {
            Object obj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(s);
            if (obj.getClass().isAnnotationPresent(ManagedBean.class)) {
                System.out.println("Tiene la anotación ManagedBean: " + s);
                System.out.println("Quitando de la sesión: " + s);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(s);
            }
        }
    }
}
