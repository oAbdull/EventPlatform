package org.ticketservice.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Booking implements Serializable {
    private String userId;
    private String bookingId;
    private LocalDateTime bookingDate;
    private String eventId;
    private String location;
    private LocalDate date;
    private int price;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "userId='" + userId + '\'' +
                ", bookingId='" + bookingId + '\'' +
                ", bookingDate=" + bookingDate +
                '}';
    }
}
