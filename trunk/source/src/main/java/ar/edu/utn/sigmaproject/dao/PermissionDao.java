package ar.edu.utn.sigmaproject.dao;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import ar.edu.utn.sigmaproject.domain.filter.EntityFilter;
import ar.edu.utn.sigmaproject.domain.security.Permission;

@Repository
public class PermissionDao extends HibernateDao<Permission> {

    @Override
    protected Class<Permission> getType() {
        return Permission.class;
    }

    @Override
    protected DetachedCriteria fillCriteria(EntityFilter<? extends Permission> entityFilter, DetachedCriteria criteria) {
        return criteria;
    }
}
