package com.naitoreivun.lop.domain.dto;

import com.naitoreivun.lop.domain.Season;
import org.joda.time.DateTime;

public class SeasonDTO {
    private Long id;
    private String name;
    private String description;
    private DateTime creationDate;
    private DateTime startDate;
    private DateTime finishDate;
    private SeasonStatus seasonStatus;

    public SeasonDTO() {
    }

    public SeasonDTO(Season season) {
        this.id = season.getId();
        this.name = season.getName();
        this.description = season.getDescription();
        this.creationDate = season.getCreationDate();
        this.startDate = season.getStartDate();
        this.finishDate = season.getFinishDate();
        this.seasonStatus = SeasonStatus.getStatus(startDate, finishDate);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public SeasonStatus getSeasonStatus() {
        return seasonStatus;
    }
}
