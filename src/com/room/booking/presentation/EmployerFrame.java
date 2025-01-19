package com.room.booking.presentation;

import com.room.booking.model.Employer;

import javax.swing.*;
import java.awt.*;

public class EmployerFrame extends JFrame {

    private Employer employer;

    public EmployerFrame(Employer employer) {
        this.employer = employer;

        setTitle("Employer Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Welcome, " + employer.getFullName()
                + " (Dept: " + employer.getDepartment() + ")"));
        add(panel);

        // Possibly add a button to register more employers, manage rooms, etc.
    }
}
