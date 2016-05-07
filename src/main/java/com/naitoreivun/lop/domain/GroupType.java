package com.naitoreivun.lop.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "group_types")
public class GroupType {
    @Id
    @GeneratedValue
    private Long id;

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
