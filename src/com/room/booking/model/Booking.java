package com.room.booking.model;

public class Booking {
    private int userId;
    private int roomId;
    private int bookingStart;
    private int bookingEnd;
    private String purpose;
    private boolean booked;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getBookingStart() {
        return bookingStart;
    }

    public void setBookingStart(int bookingStart) {
        this.bookingStart = bookingStart;
    }

    public int getBookingEnd() {
        return bookingEnd;
    }

    public void setBookingEnd(int bookingEnd) {
        this.bookingEnd = bookingEnd;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }
}
