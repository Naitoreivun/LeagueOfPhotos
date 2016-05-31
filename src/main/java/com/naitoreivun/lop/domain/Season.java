package com.naitoreivun.lop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.naitoreivun.lop.domain.dto.NewSeason;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "seasons")
public class Season {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;
    private DateTime creationDate;
    private DateTime startDate;
    private DateTime finishDate;

    @JsonIgnore
    @OneToMany(mappedBy = "season")
    private Set<Contest> contests;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    public Season() {
    }

    public Season(String name, Group group, DateTime startDate, DateTime finishDate) {
        this(name, group, "", startDate, finishDate);
    }

    public Season(String name, Group group, String description, DateTime startDate, DateTime finishDate) {
        this.name = name;
        this.group = group;
        this.description = description;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.creationDate = DateTime.now();
    }

    public Season(NewSeason newSeason, Group group) {
        this(newSeason.getName(), group, newSeason.getDescription(),
                newSeason.getStartDate(), newSeason.getFinishDate());
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

    public DateTime getCreationDate() {
        return creationDate;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public DateTime getFinishDate() {
        return finishDate;
    }

    @Override
    public String toString() {
        return "Season{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}