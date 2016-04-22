package com.naitoreivun.domain;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "app_roles")
public class AppRole {
    @Id
    @GeneratedValue
    private Long id;

    private String role;

    public AppRole() {
    }

    public AppRole(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }
}
