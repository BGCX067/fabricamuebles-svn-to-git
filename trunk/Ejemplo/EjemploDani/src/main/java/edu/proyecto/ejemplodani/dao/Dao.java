/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.proyecto.ejemplodani.dao;

import java.util.List;

/**
 *
 * @author Zaba
 */
public interface Dao<E> {
    public void add(E entity);
    public E get(Long id);
    public void save(E entity);
    public void remove(E entity);
    public List<E> listAll();
    public List<E> listFilter(String filter);
    public List<E> listFilterParam(String filter, List<String> nombresParametros, List<Object> valoresParametros);
}
