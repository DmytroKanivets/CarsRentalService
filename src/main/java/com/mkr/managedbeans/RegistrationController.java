package com.mkr.managedbeans;

import com.mkr.dao.ClientsDAO;
import com.mkr.dao.PostsDAO;
import com.mkr.dao.RolesDAO;
import com.mkr.entity.Client;
import com.mkr.entity.Role;
import org.apache.logging.log4j.LogManager;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

@ManagedBean(name = "registration")
@ApplicationScoped
public class RegistrationController {

    private String email;
    private String name;
    private String password;

    private String generateHash(String str) {
        MessageDigest md = null;
        byte[] hash = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
            hash = md.digest(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new EJBException(e);
        }
        return convertToHex(hash);
    }

    private String convertToHex(byte[] raw) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < raw.length; i++) {
            sb.append(Integer.toString((raw[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    @EJB
    ClientsDAO clientsDAO;
    @EJB
    RolesDAO rolesDAO;

    public void register() {
        Client c = new Client();

        c.setEmail(email);
        c.setName(name);
        c.setPassword(generateHash(password));
        List<Role> roles = new LinkedList<>();
        /*
        if (rolesDAO == null) {
            LogManager.getLogger().error("NULL");
            return;
        }*/
        Role r = rolesDAO.getRole("user");
        roles.add(r);
        c.setRoles(roles);

        clientsDAO.add(c);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
