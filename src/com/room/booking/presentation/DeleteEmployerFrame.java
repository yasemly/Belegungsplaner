package com.room.booking.presentation;

import com.room.booking.dao.EmployerDao;
import com.room.booking.dao.EmployerDaoImpl;
import com.room.booking.model.Employer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI-Frame zum Löschen eines Arbeitgebers anhand des Benutzernamens.
 */
public class DeleteEmployerFrame extends JFrame {

    private JTextField usernameField;
    private EmployerDao employerDao;

    public DeleteEmployerFrame() {
        employerDao = new EmployerDaoImpl();

        setTitle("Arbeitgeber löschen");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Benutzername:"));
        usernameField = new JTextField();
        add(usernameField);

        JButton deleteButton = new JButton("Löschen");
        add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteEmployer();
            }
        });

        JButton backButton = new JButton("Zurück zum Arbeitgeber-Fenster");
        add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openEmployerFrame();
            }
        });
    }

    private void deleteEmployer() {
        String username = usernameField.getText().trim();

        // Use the new method in EmployerDao
        boolean deleted = employerDao.deleteEmployerByUsername(username);
        if (deleted) {
            JOptionPane.showMessageDialog(this, "Arbeitgeber erfolgreich gelöscht!");
            openEmployerFrame();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Arbeitgeber nicht gefunden oder Löschen fehlgeschlagen.");
        }
        dispose();
    }

    private void openEmployerFrame() {
        // Just opens an example employer frame
        // In a real app, you'd pass the actual employer who is logged in, etc.
        EmployerFrame employerFrame = new EmployerFrame(
                new Employer(1, "john.doe@example.com", "John Doe",
                        "john.doe@example.com", "password", "IT"));
        employerFrame.setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DeleteEmployerFrame deleteEmployerFrame = new DeleteEmployerFrame();
            deleteEmployerFrame.setVisible(true);
        });
    }
}
