package com.naitoreivun.lop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private List<UserGroup> usersGroups;

    @JsonIgnore
    @OneToMany(mappedBy = "group")
    @Transient
    private List<Season> seasons;

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
        this.usersGroups = new ArrayList<>();
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

    public List<UserGroup> getUsersGroups() {
        return usersGroups;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public String getDescription() {
        return description;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
