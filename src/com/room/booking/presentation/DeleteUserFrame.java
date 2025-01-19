package com.room.booking.presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Frame zum Löschen eines bestehenden Benutzers.
 */
public class DeleteUserFrame extends JFrame {

    private JTextField usernameField;

    public DeleteUserFrame() {
        setTitle("Benutzer löschen");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 10, 10));

        panel.add(new JLabel("Benutzername:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        JButton deleteButton = new JButton("Löschen");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });
        panel.add(deleteButton);

        add(panel);
    }

    private void deleteUser() {
        String username = usernameField.getText().trim();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bitte geben Sie einen gültigen Benutzernamen ein.");
            return;
        }
        // DB or service logic
        JOptionPane.showMessageDialog(this, "Benutzer mit dem Namen " + username + " gelöscht.");
        usernameField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DeleteUserFrame frame = new DeleteUserFrame();
            frame.setVisible(true);
        });
    }
}
