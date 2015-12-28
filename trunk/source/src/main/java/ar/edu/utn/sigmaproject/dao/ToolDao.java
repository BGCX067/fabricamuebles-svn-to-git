/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.utn.sigmaproject.dao;

import ar.edu.utn.sigmaproject.domain.Tool;
import ar.edu.utn.sigmaproject.domain.filter.EntityFilter;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

/**
 *
 * @author DanielRH
 */
@Repository
public class ToolDao extends HibernateDao<Tool> {
    
    @Override
    protected Class<Tool> getType() {
        return Tool.class;
    }

    @Override
    protected DetachedCriteria fillCriteria(EntityFilter<? extends Tool> entityFilter, DetachedCriteria criteria) {
        return criteria;
    }
}