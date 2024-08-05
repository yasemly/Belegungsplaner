package com.room.booking.presentation;

import com.room.booking.dao.BaseUserDao; // Use BaseUserDao for deletion
import com.room.booking.dao.BaseUserDaoImpl;
import com.room.booking.model.Employer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI-Frame zum Löschen eines Arbeitgebers.
 */
public class DeleteEmployerFrame extends JFrame {

    // Swing-Komponenten
    private JTextField usernameField;

    // Data Access Object für Benutzer
    private BaseUserDao userDao;

    /**
     * Konstruktor für das DeleteEmployerFrame.
     * Initialisiert die Komponenten und das Layout des Frames.
     */
    public DeleteEmployerFrame() {
        userDao = new BaseUserDaoImpl();

        setTitle("Arbeitgeber löschen");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        // Benutzername
        add(new JLabel("Benutzername:"));
        usernameField = new JTextField();
        add(usernameField);

        // Löschen-Button
        JButton deleteButton = new JButton("Löschen");
        add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteEmployer();
            }
        });

        // Zurück zum Arbeitgeber-Fenster Button
        JButton backButton = new JButton("Zurück zum Arbeitgeber-Fenster");
        add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openEmployerFrame();
            }
        });
    }

    /**
     * Löscht einen Arbeitgeber aus der Datenbank anhand des Benutzernamens
     */
    private void deleteEmployer() {
        String username = usernameField.getText().trim();

        // Löschen des Arbeitgebers mithilfe von BaseUserDaoImpl
        boolean deleted = userDao.deleteUserByUsername(username);

        if (deleted) {
            JOptionPane.showMessageDialog(this, "Arbeitgeber erfolgreich gelöscht!");
            openEmployerFrame(); // Weiterleitung zum Arbeitgeber-Frame nach dem Löschen
        } else {
            JOptionPane.showMessageDialog(this, "Arbeitgeber nicht gefunden oder Löschen fehlgeschlagen.");
        }

        dispose(); // Schließen des Lösch-Frames nach dem Löschen
    }

    /**
     * Öffnet das EmployerFrame und schließt das aktuelle Fenster
     */
    private void openEmployerFrame() {
        // Hier sollten Sie den tatsächlichen Benutzer übergeben, nicht einen neuen erstellen
        // Verwenden Sie Employer, wenn Sie spezifische Arbeitgeberfunktionen benötigen
        EmployerFrame employerFrame = new EmployerFrame(new Employer(1, "john.doe@example.com", "John Doe", "john.doe@example.com", "password", "IT"));
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
