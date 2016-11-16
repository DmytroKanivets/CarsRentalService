package com.mkr.entity;

import javax.inject.Named;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="users")
@NamedQueries({
    @NamedQuery(name = "Client.selectAll", query = "SELECT c FROM Client c"),
    @NamedQuery(name = "Client.getByLogin", query = "SELECT c FROM Client c WHERE c.email = :login")
})
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="user_roles",
            joinColumns=@JoinColumn(name="user"),
            inverseJoinColumns=@JoinColumn(name="role"))
    private List<Role> roles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
