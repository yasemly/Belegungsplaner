package com.room.booking.presentation;

import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("My Swing Application");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter your name:");
        JTextField textField = new JTextField(20);
        JButton button = new JButton("Submit");

        panel.add(label);
        panel.add(textField);
        panel.add(button);

        add(panel);
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
    }
}

