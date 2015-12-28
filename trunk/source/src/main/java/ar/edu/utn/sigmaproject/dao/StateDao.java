package ar.edu.utn.sigmaproject.dao;

import ar.edu.utn.sigmaproject.domain.City;
import ar.edu.utn.sigmaproject.domain.State;
import ar.edu.utn.sigmaproject.domain.filter.EntityFilter;
import ar.edu.utn.sigmaproject.domain.filter.SortCriteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Gian Franco Zabarino
 * Date: 29/08/12
 */
@Repository
public class StateDao extends HibernateDao<State> {

    @Override
    protected Class<State> getType() {
        return State.class;
    }

    @Override
    protected DetachedCriteria fillCriteria(EntityFilter<? extends State> entityFilter, DetachedCriteria criteria) {
        if (entityFilter != null) {
            List<SortCriteria> sortCriterias = entityFilter.getSortCriterias();
            if (sortCriterias != null && sortCriterias.size() > 0) {
                for (SortCriteria sortCriteria : sortCriterias) {
                    if (sortCriteria.getAsc()) {
                        criteria.addOrder(Order.asc(sortCriteria.getAttribute()));
                    } else {
                        criteria.addOrder(Order.desc(sortCriteria.getAttribute()));
                    }
                }
            }
        }
        return criteria;
    }

    public City findCityByUid(String uid) {
        return (City) getCurrentSession().createCriteria(City.class)
                .add(Restrictions.eq("uid", uid))
                .uniqueResult();
    }

    public void saveCity(City city) {
        getCurrentSession().saveOrUpdate(city);
    }

    public List<City> findStateCities(State state) {
        return getCurrentSession().createCriteria(City.class)
                .add(Restrictions.eq("state", state))
                .addOrder(Order.asc("name"))
                .list();
    }

    public void evictCity(City city) {
        getCurrentSession().evict(city);
    }
}
