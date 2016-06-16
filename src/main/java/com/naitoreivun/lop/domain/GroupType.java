package com.naitoreivun.lop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name = "group_types")
public class GroupType {
    @Transient
    @JsonIgnore
    public static final String PRIVATE = "PRIVATE";
    @Transient
    @JsonIgnore
    public static final String PUBLIC = "PUBLIC";

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String type;

    public GroupType() {
    }

    public GroupType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
