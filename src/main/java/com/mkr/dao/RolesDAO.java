package com.mkr.dao;

import com.mkr.entity.Role;
import org.apache.logging.log4j.LogManager;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
@Local
public class RolesDAO {

    @PersistenceContext(name = "MySqlDb")
    private EntityManager em;

    public Role getRole(String name) {
        LogManager.getLogger().error(em.createNamedQuery("Role.selectRole").setParameter("role", name).toString());
        return (Role) em.createNamedQuery("Role.selectRole").setParameter("role", name).getSingleResult();
    }
}
