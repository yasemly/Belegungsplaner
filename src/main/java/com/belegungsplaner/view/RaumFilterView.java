package main.java.com.belegungsplaner.view;

import main.java.com.belegungsplaner.model.Raum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class RaumFilterView extends JFrame {
    private JTextField flaecheField;
    private JTextField sitzplaetzeField;
    private JCheckBox wlanCheckBox;
    private final JCheckBox klimatisierungCheckBox;
    private JButton filterButton;
    private JTextArea resultArea;

    public RaumFilterView(List<Raum> raeume) {
        setTitle("Raum Filter");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel filterPanel = new JPanel(new GridLayout(5, 2));

        filterPanel.add(new JLabel("Mindestfläche:"));
        flaecheField = new JTextField();
        filterPanel.add(flaecheField);

        filterPanel.add(new JLabel("Mindestanzahl Sitzplätze:"));
        sitzplaetzeField = new JTextField();
        filterPanel.add(sitzplaetzeField);

        filterPanel.add(new JLabel("WLAN verfügbar:"));
        wlanCheckBox = new JCheckBox();
        filterPanel.add(wlanCheckBox);

        filterPanel.add(new JLabel("Klimatisierung vorhanden:"));
        klimatisierungCheckBox = new JCheckBox();
        filterPanel.add(klimatisierungCheckBox);

        filterButton = new JButton("Filter anwenden");
        filterPanel.add(filterButton);

        add(filterPanel, BorderLayout.NORTH);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double minFlaeche = flaecheField.getText().isEmpty() ? 0 : Double.parseDouble(flaecheField.getText());
                int minSitzplaetze = sitzplaetzeField.getText().isEmpty() ? 0 : Integer.parseInt(sitzplaetzeField.getText());
                boolean wlan = wlanCheckBox.isSelected();
                boolean klimatisierung = klimatisierungCheckBox.isSelected();

                List<Raum> gefilterteRaeume = raeume.stream()
                        .filter(r -> r.getFlaeche() >= minFlaeche)
                        .filter(r -> r.getSitzplaetze() >= minSitzplaetze)
                        .filter(r -> r.isWlanVerfuegbar() == wlan)
                        .filter(r -> r.isKlimatisierung() == klimatisierung)
                        .collect(Collectors.toList());

                resultArea.setText("");
                for (Raum raum : gefilterteRaeume) {
                    resultArea.append(raum.toString() + "\n");
                }
            }
        });
    }

    public static void main(String[] args) {
        // Beispiel für Raumliste
        List<Raum> raeume = List.of(
                new Konferenzraum("Raum A", 50.0, 20, 5, "Gebäude 1", true, true, true),
                new Konferenzraum("Raum B", 30.0, 15, 3, "Gebäude 2", false, false, false)
        );

        SwingUtilities.invokeLater(() -> {
            RaumFilterView view = new RaumFilterView(raeume);
            view.setVisible(true);
        });
    }
}
