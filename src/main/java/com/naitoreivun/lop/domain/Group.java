package com.naitoreivun.lop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.naitoreivun.lop.domain.dto.NewGroup;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "groups")
public class Group {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String name;

    @Column(nullable = false)
    private DateTime creationDate;
    private String description;

    @ManyToOne
    @JoinColumn(name = "group_type_id", nullable = false)
    private GroupType groupType;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "group")
    private List<UserGroup> usersGroups;

    @JsonIgnore
    @OneToMany(mappedBy = "group", cascade = CascadeType.REMOVE)
    private List<Season> seasons;

    public Group() {
    }

    public Group(String name, GroupType groupType) {
        this(name, groupType, "");
    }

    public Group(String name, GroupType groupType, String description) {
        setNewDetails(name, description, groupType);
        this.creationDate = DateTime.now();
        this.usersGroups = new ArrayList<>();
        this.seasons = new ArrayList<>();
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

    public void setNewDetails(String name, String description, GroupType groupType) {
        this.name = name;
        this.description = description;
        this.groupType = groupType;
    }

    public boolean isPrivate() {
        return GroupType.PRIVATE.equals(groupType.getType());
    }
}
