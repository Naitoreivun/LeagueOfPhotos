package com.naitoreivun.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity(name = "group_types")
public class GroupType {
    @Id
    @GeneratedValue
    private Long id;

    private String type;

    @OneToMany(mappedBy = "groupType")
    private Set<Group> groups;

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

    public Set<Group> getGroups() {
        return groups;
    }
}
