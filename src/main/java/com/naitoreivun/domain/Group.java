package com.naitoreivun.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "groups")
public class Group {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "group_type_id")
    private GroupType groupType;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private Set<UserGroup> userGroups;

    @OneToMany(mappedBy = "group")
    private Set<Season> seasons;

    public Group() {
    }

    public Group(String name, GroupType groupType) {
        this.name = name;
        this.groupType = groupType;
        this.userGroups = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public Set<UserGroup> getUserGroups() {
        return userGroups;
    }

    public Set<Season> getSeasons() {
        return seasons;
    }
}
