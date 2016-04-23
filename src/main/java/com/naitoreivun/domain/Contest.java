package com.naitoreivun.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "contests")
public class Contest {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;
    private DateTime creationDate;
    private DateTime startDate;
    private DateTime finishDate;
    private DateTime finishVotingDate;


    @ManyToOne
    @JoinColumn(name = "season_id")
    private Season season;

    @JsonIgnore
    @OneToMany(mappedBy = "contest")
    private Set<Image> images;

    public Contest() {
    }

    public Contest(String name, Season season, DateTime startDate, DateTime finishDate, DateTime finishVotingDate) {
        this(name, season, "", startDate, finishDate, finishVotingDate);
    }

    public Contest(String name, Season season, String description, DateTime startDate, DateTime finishDate, DateTime finishVotingDate) {
        this.name = name;
        this.season = season;
        this.description = description;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.finishVotingDate = finishVotingDate;
        this.creationDate = DateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Season getSeason() {
        return season;
    }

    public String getDescription() {
        return description;
    }

    public Set<Image> getImages() {
        return images;
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

    public DateTime getFinishVotingDate() {
        return finishVotingDate;
    }
}
