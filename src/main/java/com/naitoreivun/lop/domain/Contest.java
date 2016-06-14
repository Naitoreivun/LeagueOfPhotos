package com.naitoreivun.lop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.naitoreivun.lop.domain.dto.NewContest;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "contests")
public class Contest {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;
    private DateTime creationDate;
    private DateTime startDate;
    private DateTime finishUploadingDate;
    private DateTime finishVotingDate;


    @ManyToOne
    @JoinColumn(name = "season_id")
    private Season season;

    @JsonIgnore
    @OneToMany(mappedBy = "contest", cascade = CascadeType.ALL)
    private List<Image> images;

    public Contest() {
    }

    public Contest(String name, Season season, DateTime startDate, DateTime finishUploadingDate, DateTime finishVotingDate) {
        this(name, season, "", startDate, finishUploadingDate, finishVotingDate);
    }

    public Contest(String name, Season season, String description, DateTime startDate, DateTime finishUploadingDate, DateTime finishVotingDate) {
        this.season = season;
        setDetails(name, description, startDate, finishUploadingDate, finishVotingDate);
        this.creationDate = DateTime.now();
        this.images = new ArrayList<>();
    }

    public Contest(NewContest newContest, Season season) {
        this(newContest.getName(), season, newContest.getDescription(), newContest.getStartDate(),
                newContest.getFinishUploadingDate(), newContest.getFinishVotingDate());
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

    public List<Image> getImages() {
        return images;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public DateTime getFinishUploadingDate() {
        return finishUploadingDate;
    }

    public DateTime getFinishVotingDate() {
        return finishVotingDate;
    }

    public void setNewDetails(NewContest newContest) {
        setDetails(newContest.getName(), newContest.getDescription(), newContest.getStartDate(),
                newContest.getFinishUploadingDate(), newContest.getFinishVotingDate());
    }

    private void setDetails(String name, String description, DateTime startDate,
                            DateTime finishUploadingDate, DateTime finishVotingDate) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.finishUploadingDate = finishUploadingDate;
        this.finishVotingDate = finishVotingDate;
    }
}
