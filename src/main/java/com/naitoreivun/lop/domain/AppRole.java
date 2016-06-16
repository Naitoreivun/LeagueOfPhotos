package com.naitoreivun.lop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name = "app_roles")
public class AppRole {
    @Transient
    @JsonIgnore
    public static final String ADMIN = "ROLE_ADMIN";
    @Transient
    @JsonIgnore
    public static final String USER = "ROLE_USER";

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
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
