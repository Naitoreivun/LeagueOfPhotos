package com.naitoreivun.lop.domain.dto;

import org.joda.time.DateTime;

public class NewSeason {
    private Long groupId;
    private String name;
    private String description;
    private DateTime startDate;
    private DateTime finishDate;

    public NewSeason() {
    }

    public NewSeason(Long groupId, String name, String description, DateTime startDate, DateTime finishDate) {
        this.groupId = groupId;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public Long getGroupId() {
        return groupId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public DateTime getFinishDate() {
        return finishDate;
    }
}
