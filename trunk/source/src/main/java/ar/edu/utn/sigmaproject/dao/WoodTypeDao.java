package ar.edu.utn.sigmaproject.dao;

import ar.edu.utn.sigmaproject.domain.WoodType;
import ar.edu.utn.sigmaproject.domain.filter.EntityFilter;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

/**
 * User: zaba
 * Date: 25/07/12
 */
@Repository
public class WoodTypeDao extends HibernateDao<WoodType> {

    @Override
    protected Class<WoodType> getType() {
        return WoodType.class;
    }

    @Override
    protected DetachedCriteria fillCriteria(EntityFilter<? extends WoodType> entityFilter, DetachedCriteria criteria) {
        return criteria;
    }
}
