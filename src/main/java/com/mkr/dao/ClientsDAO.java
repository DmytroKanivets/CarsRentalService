package com.mkr.dao;

import com.mkr.entity.Client;

import javax.ejb.Stateless;


@Stateless
public class ClientsDAO extends AbstractDAO<Client> {
    @Override
    protected Class<Client> getEntityClass() {
        return Client.class;
    }

    public Client getClientByLogin(String login) {
        return (Client)em.createNamedQuery(getEntityClass().getSimpleName() + ".getByLogin").setParameter("login", login).getSingleResult();
    }
}
