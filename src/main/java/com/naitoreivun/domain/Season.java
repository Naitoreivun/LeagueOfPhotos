package com.naitoreivun.domain;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "seasons")
public class Season {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "season")
    private Set<Contest> contests;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    public Season() {
    }

    public Season(String name) {
        this.name = name;
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
}
