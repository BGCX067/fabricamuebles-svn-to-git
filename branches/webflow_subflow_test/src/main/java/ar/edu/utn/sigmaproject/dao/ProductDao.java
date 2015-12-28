package ar.edu.utn.sigmaproject.dao;

import ar.edu.utn.sigmaproject.domain.Product;
import ar.edu.utn.sigmaproject.domain.Supplier;
import ar.edu.utn.sigmaproject.domain.filter.EntityFilter;
import ar.edu.utn.sigmaproject.domain.filter.ProductFilter;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

/**
 * Fills criteria with common product's properties.<br/><br/>
 *
 * User: Gian Franco Zabarino
 * Date: 23/08/12
 */
@Repository
public class ProductDao<T extends Product> extends HibernateDao<T> {

    @Override
    protected Class<T> getType() {
        return (Class<T>) Product.class;
    }

    @Override
    protected DetachedCriteria fillCriteria(EntityFilter<? extends T> entityFilter, DetachedCriteria criteria) {
        ProductFilter filter = (ProductFilter) entityFilter;
        if (filter != null) {
            final String name = filter.getName();
            if (StringUtils.hasText(name)) {
                criteria.add(Restrictions.eq("name", name));
            }
            final Supplier supplier = filter.getSupplier();
            if (supplier != null) {
                criteria.add(Restrictions.eq("supplier", supplier));
            }
        }
        return criteria;
    }
}
