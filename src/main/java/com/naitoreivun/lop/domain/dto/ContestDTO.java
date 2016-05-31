package com.naitoreivun.lop.domain.dto;

import com.naitoreivun.lop.domain.Contest;
import org.joda.time.DateTime;

public class ContestDTO {
    private Long id;
    private String name;
    private String description;
    private DateTime creationDate;
    private DateTime startDate;
    private DateTime finishUploadingDate;
    private DateTime finishVotingDate;
    private ContestStatus contestStatus;


    public ContestDTO() {
    }

    public ContestDTO(Contest contest) {
        this.id = contest.getId();
        this.name = contest.getName();
        this.description = contest.getDescription();
        this.creationDate = contest.getCreationDate();
        this.startDate = contest.getStartDate();
        this.finishUploadingDate = contest.getFinishUploadingDate();
        this.finishVotingDate = contest.getFinishVotingDate();
        this.contestStatus = ContestStatus.getStatus(startDate, finishUploadingDate, finishVotingDate);
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

    public DateTime getFinishUploadingDate() {
        return finishUploadingDate;
    }

    public DateTime getFinishVotingDate() {
        return finishVotingDate;
    }

    public ContestStatus getContestStatus() {
        return contestStatus;
    }
}
