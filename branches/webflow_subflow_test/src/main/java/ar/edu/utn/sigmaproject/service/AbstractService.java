package ar.edu.utn.sigmaproject.service;

import ar.edu.utn.sigmaproject.dao.HibernateDao;
import ar.edu.utn.sigmaproject.domain.filter.EntityFilter;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * User: zaba
 * Date: 31/07/12
 */
public abstract class AbstractService<E> {

    protected abstract HibernateDao<E> getServiceDao();

    @Transactional(readOnly = true)
    public List<E> getList() {
        return getServiceDao().getAll((Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    @Transactional(readOnly = true)
    public List<E> getList(EntityFilter<E> filter) {
        return getServiceDao().getList(filter);
    }

    @Transactional(readOnly = true)
    public E findByUid(String uid) {
        return getServiceDao().getByUid(uid);
    }

    @Transactional
    public void saveEntity(E entity) {
        getServiceDao().save(entity);
    }

    @Transactional
    public void deleteEntity(E entity) {
        getServiceDao().delete(entity);
    }

    @Transactional(readOnly = true)
    public void markForRetrieveFromDB(E entity) {
        getServiceDao().evict(entity);
    }
}
