package ar.edu.utn.sigmaproject.dao;

import ar.edu.utn.sigmaproject.domain.filter.EntityFilter;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class HibernateDao<T> {
	private SessionFactory sessionFactory;

	public HibernateDao() {
		super();
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public T getById(Serializable id) {
		return (T) getCurrentSession().get(getType(), id);
	}

    @SuppressWarnings("unchecked")
    public T getByUid(String uid) {
        return (T) getCurrentSession().createCriteria(getType())
                .add(Restrictions.eq("uid", uid)).uniqueResult();
    }

    public void evict(Object entity) {
        getCurrentSession().evict(entity);
    }

	@SuppressWarnings("unchecked")
	protected abstract Class<T> getType();

	public List<T> findByCriteria(DetachedCriteria criteria) {
		return (List<T>) this.findByCriteria(criteria, 0, 0);
	}

	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(final DetachedCriteria criteria, final int firstResult, final int maxResults) {
		Criteria executableCriteria = criteria.getExecutableCriteria(getCurrentSession());
		// prepareCriteria(executableCriteria);
		if (firstResult >= 0) {
			executableCriteria.setFirstResult(firstResult);
		}
		if (maxResults > 0) {
			executableCriteria.setMaxResults(maxResults);
		}
		return (List<T>) executableCriteria.list();
	}

	public T findUnique(String query, Map<String, Object> paramMap) {

		List<T> results = (List<T>) this.find(query, paramMap);

		if (results.isEmpty()) {
			return null;
		}
		if (results.size() > 1) {
			throw new IllegalStateException("Invalid Number of rows returned for unique call: " + results.size());
		}
		return (T) results.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<T> find(String query, Map<String, Object> paramMap) {
		final Query queryObject = getCurrentSession().createQuery(query);
		// prepareQuery(queryObject);
		if (!paramMap.isEmpty() && queryObject.getNamedParameters().length > 0) {
			for (int i = 0; i < queryObject.getNamedParameters().length; i++) {
				String paramName = queryObject.getNamedParameters()[i];
				Object paramValue = paramMap.get(queryObject.getNamedParameters()[i]);
				if (paramValue == null) {
					throw new IllegalStateException("Invalid Parameter value: " + paramName);
				}
				queryObject.setParameter(paramName, paramValue);
			}
		}

		return (List<T>) queryObject.list();
	}

	@SuppressWarnings("unchecked")
	public <T1> List<T1> getAll(Class<T1> entityClass) {
		Criteria criteria = getCurrentSession().createCriteria(entityClass);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		// prepareCriteria(criteria);
		return (List<T1>) criteria.list();
	}

	public void save(T object) {
		getCurrentSession().saveOrUpdate(object);
	}

	public void delete(T object) {
		getCurrentSession().delete(object);
	}
	
	@SuppressWarnings("unchecked")
	public int getCount(EntityFilter<T> filter) {
		List<Number> result = null;
				
		final Session session = getCurrentSession();
		
		final DetachedCriteria dCriteria = DetachedCriteria.forClass(getType());
		
		fillCriteria(filter, dCriteria);
		
		final Criteria executableCriteria = dCriteria.getExecutableCriteria(session);		
		executableCriteria.setProjection(Projections.rowCount());
		
		result = executableCriteria.list();
		return result.get(0).intValue();
	}

	public List<T> getList(EntityFilter<T> filter) {
		return getList(filter, -1, -1);
	}

	@SuppressWarnings("unchecked")
	public List<T> getList(EntityFilter<T> filter, int firstResult, int maxResults) {

		final Session session = getCurrentSession();
		
		final DetachedCriteria dCriteria = DetachedCriteria.forClass(getType());
		
		fillCriteria(filter, dCriteria);
		
		Criteria executableCriteria = dCriteria.getExecutableCriteria(session);
		
		// prepareCriteria(executableCriteria);
		if (firstResult >= 0) {
			executableCriteria.setFirstResult(firstResult);
		}
		if (maxResults > 0) {
			executableCriteria.setMaxResults(maxResults);
		}
		return executableCriteria.list();
	}
	   
	protected abstract DetachedCriteria fillCriteria (EntityFilter<? extends T> entityFilter, DetachedCriteria criteria);

	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
}