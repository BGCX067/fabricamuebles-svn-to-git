/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.utn.sigmaproject.dao;

import ar.edu.utn.sigmaproject.domain.Machine;
import ar.edu.utn.sigmaproject.domain.filter.EntityFilter;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

/**
 *
 * @author DanielRH
 */
@Repository
public class MachineDao extends HibernateDao<Machine> {

    @Override
    protected Class<Machine> getType() {
        return Machine.class;
    }

    @Override
    protected DetachedCriteria fillCriteria(EntityFilter<? extends Machine> entityFilter, DetachedCriteria criteria) {
        return criteria;
    }
}
