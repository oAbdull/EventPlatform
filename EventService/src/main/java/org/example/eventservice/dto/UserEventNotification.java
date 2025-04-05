package org.example.eventservice.dto;

public class UserEventNotification {
    private String userId;
    private String eventId;
    private String eventName;

    public UserEventNotification(String userId, String eventId, String eventName) {
        this.userId = userId;
        this.eventId = eventId;
        this.eventName = eventName;
    }

    // Getters and setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }
}