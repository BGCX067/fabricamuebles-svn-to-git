package ar.edu.utn.sigmaproject.dao;

import ar.edu.utn.sigmaproject.domain.Wood;
import ar.edu.utn.sigmaproject.domain.WoodType;
import ar.edu.utn.sigmaproject.domain.filter.EntityFilter;
import ar.edu.utn.sigmaproject.domain.filter.WoodFilter;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * User: zaba
 * Date: 25/07/12
 */
@Repository
public class WoodDao extends ProductDao<Wood> {

    @Override
    protected Class<Wood> getType() {
        return Wood.class;
    }

    @Override
    protected DetachedCriteria fillCriteria(EntityFilter<? extends Wood> woodEntityFilter, DetachedCriteria criteria) {
        DetachedCriteria parentCriteria = super.fillCriteria(woodEntityFilter, criteria);

        final WoodFilter filter = (WoodFilter) woodEntityFilter;

        if (filter != null) {
            final WoodType woodType = filter.getWoodType();
            if (woodType != null) {
                parentCriteria.add(Restrictions.eq("woodType", woodType));
            }
        }

        return parentCriteria;
    }
}
