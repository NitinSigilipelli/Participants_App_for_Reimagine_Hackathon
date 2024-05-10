package com.example.reimagine;

import java.sql.Timestamp;

public class TimeLineEvent {
    private Timestamp startTime;
    private Timestamp endTime;
    private String eventName;

    public TimeLineEvent(Timestamp startTime, Timestamp endTime, String eventName) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventName = eventName;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
