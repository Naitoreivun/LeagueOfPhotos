package com.naitoreivun.lop.domain.dto;

import org.joda.time.DateTime;

public enum ContestStatus {
    AVAILABLE_SOON,
    UPLOADING,
    VOTING,
    CLOSED;

    static public ContestStatus getStatus(DateTime startDate,
                                          DateTime finishUploadingDate,
                                          DateTime finishVotingDate) {
        if(startDate.isAfterNow()) {
            return AVAILABLE_SOON;
        }
        if(finishUploadingDate.isAfterNow()) {
            return UPLOADING;
        }
        if (finishVotingDate.isAfterNow()) {
            return VOTING;
        }
        return CLOSED;
    }
}
