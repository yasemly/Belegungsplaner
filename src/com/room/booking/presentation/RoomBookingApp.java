package com.room.booking.presentation;

import javax.swing.*;

public class RoomBookingApp extends JFrame {

    //1) create a Jframe
    //2) we need a button called Book a room button
    //3) add the button to the frame

    RoomBookingApp () {
        setTitle("Room Booking System");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();

        JButton bookRoomButton = new JButton();
        bookRoomButton.setText("Book a Room");

        panel.add(bookRoomButton);
        add(panel);

        JButton addRoomButton = new JButton();
        addRoomButton.setText("Add a Room");

        panel.add(addRoomButton);

        bookRoomButton.addActionListener(bookRoomBtn -> {
            BookingForm bookingForm = new BookingForm();
            bookingForm.setVisible(true);
        });


    }


    public static void main(String[] args) {
        RoomBookingApp app = new RoomBookingApp();
        app.setVisible(true);
    }
}
