package com.naitoreivun.lop.domain.dto;

import org.joda.time.DateTime;

public enum SeasonStatus {
    AVAILABLE_SOON,
    OPEN,
    CLOSED;

    static public SeasonStatus getStatus(DateTime startDate, DateTime finishDate) {
//        System.out.println("start date -> " + startDate); //todo synchro z baza
//        System.out.println("finish date -> " + finishDate);
//        System.out.println("now -> " + DateTime.now());
//        System.out.println();
        if (startDate.isAfterNow()) {
            return AVAILABLE_SOON;
        }
        if(finishDate.isAfterNow()) {
            return OPEN;
        }
        return CLOSED;
    }
}
