package com.mkr.dao;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@Stateless
@Local
public class AbstractDAO<T> {

    @PersistenceContext(name = "MySqlDb")
    protected EntityManager em;

    protected Class<T> getEntityClass() {
        return null;
    }

    public T add(T obj) {
        em.persist(obj);
        return obj;
    }

    public void deleteByID(int id) {
        delete(find(id));
    }

    private void delete(T obj) {
        em.remove(obj);
    }

    public void update(T obj) {
        em.merge(obj);
    }

    public T find(int id) {
        return em.find(getEntityClass(), id);
    }

    public List<T> getAll() {
        Class<T> entityClass = getEntityClass();
        TypedQuery<T> namedQuery = em.createNamedQuery(entityClass.getSimpleName() + ".selectAll", entityClass);
        return namedQuery.getResultList();
    }
}

