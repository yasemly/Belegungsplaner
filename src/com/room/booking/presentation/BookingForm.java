package com.room.booking.presentation;

import com.room.booking.model.Room;
import com.room.booking.model.RoomDTO;
import com.room.booking.model.User;
import com.room.booking.service.BookingServiceImpl;
import com.room.booking.service.RoomServiceImpl;
import com.room.booking.service.UserServiceImpl;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class BookingForm extends JFrame {

    private final RoomServiceImpl roomService;

    private final BookingServiceImpl bookingService;

    private final UserServiceImpl userService;

    public BookingForm() {
        this.roomService = new RoomServiceImpl();
        this.bookingService = new BookingServiceImpl();
        this.userService = new UserServiceImpl();
        setTitle("Booking Form");
        setSize(600, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel roomLabel = new JLabel("Select Room:");
        roomLabel.setBounds(10, 10, 100, 30);
        add(roomLabel);

        JComboBox<String> roomDropdown = new JComboBox<>();
        roomDropdown.setBounds(120, 10, 150, 30);
        add(roomDropdown);

        final List<RoomDTO> roomDTOS = roomService.fetchAllRooms();
        final List<String> roomList = roomDTOS.stream().map(RoomDTO::roomName).toList();
        roomList.forEach(room -> roomDropdown.addItem(room));

        JLabel bookerLabel = new JLabel("BookerName:");
        bookerLabel.setBounds(10, 50, 100, 30);
        add(bookerLabel);

        JTextField userField = new JTextField();
        userField.setBounds(120, 50, 150, 30);
        add(userField);


        JLabel errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        errorLabel.setBounds(280, 50, 400, 30); // Adjust bounds to fit the frame


        // Add a DocumentListener to the text field
        userField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validateInput();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateInput();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateInput();
            }

            private void validateInput() {
                String text = userField.getText();
                if (text.matches("[a-zA-Z0-9]*")) {
                    errorLabel.setText("");  // Clear error message if input is valid
                } else {
                    errorLabel.setText("Please enter only letters and numbers");
                }
            }

        });

        add(errorLabel);
        JLabel startDateLabel = new JLabel("Start Date:");
        startDateLabel.setBounds(10, 90, 100, 30);
        add(startDateLabel);

        JSpinner startDateSpinner = new JSpinner(new SpinnerDateModel());
        startDateSpinner.setBounds(120, 90, 150, 30);
        add(startDateSpinner);



        JLabel endDateLabel = new JLabel("End Date:");
        endDateLabel.setBounds(10, 130, 100, 30);
        add(endDateLabel);

        JSpinner endDateSpinner = new JSpinner(new SpinnerDateModel());
        endDateSpinner.setBounds(120, 130, 150, 30);
        add(endDateSpinner);

        JLabel purposeLabel = new JLabel("Purpose: ");
        purposeLabel.setBounds(10, 170, 100, 30);
        add(purposeLabel);

        JTextField purposeField = new JTextField();
        purposeField.setBounds(120, 170, 150, 30);
        add(purposeField);


        JButton createBookingBtn = new JButton();
        createBookingBtn.setText("Book this room");
        createBookingBtn.setBounds(50, 200, 150, 30);

        createBookingBtn.addActionListener(createBooking -> {
            String selectedRoom = (String) roomDropdown.getSelectedItem();
            String bookerName = userField.getText();
            Date startDate = (Date) startDateSpinner.getValue();
            Date endDate = (Date) endDateSpinner.getValue();
            final String purpose = purposeField.getText();
            if (selectedRoom != null && !bookerName.isEmpty() && !purpose.isEmpty() & errorLabel.getText().isEmpty()) {
                if (startDate.before(endDate)) {
                    final Room room = roomService.getRoomByName(selectedRoom);
                    // we will change this implementation later by using session
                    final User user= userService.getUserByName(bookerName);
                    bookingService.createBooking(room.getRoom_id(), user.getUserId(), room.getRoom_name(), bookerName, startDate, endDate, purpose);
                    JOptionPane.showMessageDialog(this, "Room booked successfully!");
                }
            }
        });
        add(createBookingBtn);
        setVisible(true);
    }
}

