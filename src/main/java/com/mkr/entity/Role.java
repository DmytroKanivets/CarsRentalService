package com.mkr.entity;


import javax.persistence.*;

@Entity
@Table(name="roles")
@NamedQuery(name="Role.selectRole", query="SELECT r FROM Role r WHERE r.roleName=:role")
public class Role {
    @Id
    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
