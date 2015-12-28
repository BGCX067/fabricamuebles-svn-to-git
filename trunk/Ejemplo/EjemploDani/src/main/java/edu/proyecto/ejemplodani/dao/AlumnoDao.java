/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.proyecto.ejemplodani.dao;

import edu.proyecto.ejemplodani.model.Alumno;
import javax.ejb.Stateless;
import javax.inject.Named;

@Stateless
@Named
public class AlumnoDao extends AbstractDao<Alumno> {

    @Override
    public Class getModelClass() {
        return Alumno.class;
    }
    
}
