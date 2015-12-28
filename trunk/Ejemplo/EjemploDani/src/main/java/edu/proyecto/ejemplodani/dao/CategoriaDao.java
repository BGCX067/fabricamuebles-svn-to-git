/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.proyecto.ejemplodani.dao;

import edu.proyecto.ejemplodani.model.Categoria;
import javax.ejb.Stateless;
import javax.inject.Named;

/**
 *
 * @author Zaba
 */

@Stateless
@Named
public class CategoriaDao extends AbstractDao<Categoria> {

    @Override
    public Class getModelClass() {
        return Categoria.class;
    }
    
}
