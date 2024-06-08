package main.java.com.belegungsplaner.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BuchungsFormular extends JFrame {
    private JTextField benutzerFeld;
    private JTextField datumFeld;
    private JTextField uhrzeitFeld;
    private JComboBox<String> raumComboBox;
    private JButton buchenButton;

    public BuchungsFormular() {
        setTitle("Raum buchen");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2));

        panel.add(new JLabel("Benutzer:"));
        benutzerFeld = new JTextField();
        panel.add(benutzerFeld);

        panel.add(new JLabel("Datum:"));
        datumFeld = new JTextField();
        panel.add(datumFeld);

        panel.add(new JLabel("Uhrzeit:"));
        uhrzeitFeld = new JTextField();
        panel.add(uhrzeitFeld);

        panel.add(new JLabel("Raum:"));
        raumComboBox = new JComboBox<>(new String[]{"Raum A", "Raum B", "Raum C"});
        panel.add(raumComboBox);

        buchenButton = new JButton("Buchen");
        buchenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String benutzer = benutzerFeld.getText();
                String datum = datumFeld.getText();
                String uhrzeit = uhrzeitFeld.getText();
                String raum = (String) raumComboBox.getSelectedItem();

                // Hier Buchungslogik hinzufügen, z.B.:
                // main.java.com.belegungsplaner.controller.BelegungsplanerController.buchungErstellen(new Benutzer(benutzer, "email@example.com"), raum, datum, uhrzeit);

                JOptionPane.showMessageDialog(BuchungsFormular.this, "Buchung erstellt!");
            }
        });
        panel.add(buchenButton);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BuchungsFormular formular = new BuchungsFormular();
            formular.setVisible(true);
        });
    }
}
