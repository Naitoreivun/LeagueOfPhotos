package com.naitoreivun.lop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "groups")
public class Group {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;
    private DateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "group_type_id")
    private GroupType groupType;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "group", cascade = CascadeType.PERSIST)
    private Set<UserGroup> usersGroups;

    @JsonIgnore
    @OneToMany(mappedBy = "group")
    private Set<Season> seasons;

    public Group() {
    }

    public Group(String name, GroupType groupType) {
        this(name, groupType, "");
    }

    public Group(String name, GroupType groupType, String description) {
        this.name = name;
        this.groupType = groupType;
        this.description = description;
        this.creationDate = DateTime.now();
        this.usersGroups = new HashSet<>();
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

    public Set<UserGroup> getUsersGroups() {
        return usersGroups;
    }

    public Set<Season> getSeasons() {
        return seasons;
    }

    public String getDescription() {
        return description;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }
}
