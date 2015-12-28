package ar.edu.utn.sigmaproject.dao;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import ar.edu.utn.sigmaproject.domain.filter.EntityFilter;
import ar.edu.utn.sigmaproject.domain.filter.RoleFilter;
import ar.edu.utn.sigmaproject.domain.security.Role;

@Repository
public class RoleDao extends HibernateDao<Role> {

    @Override
    protected Class<Role> getType() {
        return Role.class;
    }

    @Override
    protected DetachedCriteria fillCriteria(EntityFilter<? extends Role> entityFilter, DetachedCriteria criteria) {
        final RoleFilter filter = (RoleFilter) entityFilter;

        if (filter != null) {
            if (StringUtils.hasText(filter.getName())) {
                criteria.add(Restrictions.ilike("name", filter.getName(), MatchMode.ANYWHERE));
            }

            if (StringUtils.hasText(filter.getDescription())) {
                criteria.add(Restrictions.ilike("description", filter.getDescription(), MatchMode.ANYWHERE));
            }
        }

        return criteria;
    }
}
