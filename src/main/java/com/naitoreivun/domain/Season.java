package com.naitoreivun.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "seasons")
public class Season {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;
    // TODO: 2016-04-21 data_rozp, data_zak, data_stworzenia

    @JsonIgnore
    @OneToMany(mappedBy = "season")
    private Set<Contest> contests;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    public Season() {
    }

    public Season(String name, Group group) {
        this(name, group, "");
    }

    public Season(String name, Group group, String description) {
        this.name = name;
        this.group = group;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Contest> getContests() {
        return contests;
    }

    public Group getGroup() {
        return group;
    }

    public String getDescription() {
        return description;
    }
}