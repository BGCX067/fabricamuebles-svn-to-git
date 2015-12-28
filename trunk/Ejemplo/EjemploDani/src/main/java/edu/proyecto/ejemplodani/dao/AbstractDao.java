/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.proyecto.ejemplodani.dao;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Zaba
 */

public class AbstractDao<E> implements Dao<E> {
    @PersistenceContext
    EntityManager em;
    
    protected Class getModelClass() {
        throw new RuntimeException("El metodo getModelClass deberia ser redefinido devolviendo el Clase.class");
    }
    
    @Override
    public void add(E entity) {
        em.persist(entity);
    }

    @Override
    public E get(Long id) {
        return (E) em.find(getModelClass(), id);
    }

    @Override
    public void save(E entity) {
        em.merge(entity);
    }

    @Override
    public void remove(E entity) {
        em.remove(em.merge(entity));
    }

    @Override
    public List<E> listAll() {
        TypedQuery<E> query = (TypedQuery<E>) em.createQuery("select e from " + getModelClass().getSimpleName() + " e", getModelClass());
        return query.getResultList();
    }

    /**
     * A la hora de armar el filtro recordar que el alias de la "tabla" es e
     */
    @Override
    public List<E> listFilter(String filter) {
        TypedQuery<E> query = (TypedQuery<E>) em.createQuery("select e from " + getModelClass().getSimpleName() + " e where " + filter, getModelClass());
        return query.getResultList();
    }

    @Override
    public List<E> listFilterParam(String filter, List<String> nombresParametros, List<Object> valoresParametros) {
        if (nombresParametros.size() == valoresParametros.size()) {
            TypedQuery<E> query = (TypedQuery<E>) em.createQuery("select e from " + getModelClass().getSimpleName() + " e where " + filter, getModelClass());
            Iterator<String> itNombresParametros = nombresParametros.iterator();
            Iterator<Object> itValoresParametros = valoresParametros.iterator();
            while (itNombresParametros.hasNext() && itValoresParametros.hasNext()) {
                query.setParameter(itNombresParametros.next(), itValoresParametros.next());
            }
            return query.getResultList();
        } else {
            throw new IllegalArgumentException("Ambas listas debes ser de la misma longitud.");
        }
    }
        
}
