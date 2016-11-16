package com.mkr.dao;

import com.mkr.entity.Order;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class OrdersDAO extends AbstractDAO<Order> {
    @Override
    protected Class<Order> getEntityClass() {
        return Order.class;
    }

    public List<Order> getByCarIdSorted(int carId) {
        TypedQuery<Order> namedQuery = em.createNamedQuery(getEntityClass().getSimpleName() + ".selectByCar", getEntityClass()).setParameter("id", carId);
        return namedQuery.getResultList();
    }

    public List<Order> getByClientIdSorted(int clientId) {
        TypedQuery<Order> namedQuery = em.createNamedQuery(getEntityClass().getSimpleName() + ".selectByClient", getEntityClass()).setParameter("id", clientId);
        return namedQuery.getResultList();
    }
}
