package com.naitoreivun.lop.domain.dto;

import org.joda.time.DateTime;

public class NewContest {
    private Long seasonId;
    private String name;
    private String description;
    private DateTime startDate;
    private DateTime finishUploadingDate;
    private DateTime finishVotingDate;

    public NewContest() {
    }

    public NewContest(Long seasonId, String name, String description, DateTime startDate, DateTime finishUploadingDate, DateTime finishVotingDate) {
        this.seasonId = seasonId;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.finishUploadingDate = finishUploadingDate;
        this.finishVotingDate = finishVotingDate;
    }

    public Long getSeasonId() {
        return seasonId;
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

    public DateTime getFinishUploadingDate() {
        return finishUploadingDate;
    }

    public DateTime getFinishVotingDate() {
        return finishVotingDate;
    }
}
