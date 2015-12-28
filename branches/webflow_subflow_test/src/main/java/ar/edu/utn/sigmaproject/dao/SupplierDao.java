package ar.edu.utn.sigmaproject.dao;

import ar.edu.utn.sigmaproject.domain.Supplier;
import ar.edu.utn.sigmaproject.domain.filter.EntityFilter;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

/**
 * User: Gian Franco Zabarino
 * Date: 23/08/12
 */
@Repository
public class SupplierDao extends PersonDao<Supplier> {

    @Override
    protected Class<Supplier> getType() {
        return Supplier.class;
    }

    @Override
    protected DetachedCriteria fillCriteria(EntityFilter<? extends Supplier> entityFilter, DetachedCriteria criteria) {
        return super.fillCriteria(entityFilter, criteria);
    }
}
