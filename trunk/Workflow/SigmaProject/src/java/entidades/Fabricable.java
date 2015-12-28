/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades;

import java.util.*;

/**
 *
 * @author Zaba
 */
public interface Fabricable {

    public Collection<ActividadEspecifica> getActividades();
    public void setActividades(Collection<ActividadEspecifica> actividadEspecificas);
}
