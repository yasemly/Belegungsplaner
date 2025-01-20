package com.room.booking.presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class MultiSelectFeaturePanel extends JPanel {

    private JTextField selectedFeaturesField;
    private JButton selectButton;
    private JPopupMenu popupMenu;
    private List<JCheckBox> checkBoxes;
    private String[] features = {"Beamer", "Whiteboard", "Klimaanlage", "Sound System", "WLAN"};

    public MultiSelectFeaturePanel() {
        setLayout(new BorderLayout(5, 5));

        // Nicht-editierbares Textfeld, in dem die ausgewählten Features angezeigt werden
        selectedFeaturesField = new JTextField();
        selectedFeaturesField.setEditable(false);
        add(selectedFeaturesField, BorderLayout.CENTER);

        // Button, um das Dropdown-Popup zu öffnen
        selectButton = new JButton("...");
        add(selectButton, BorderLayout.EAST);

        // Erstelle das PopupMenu
        popupMenu = new JPopupMenu();
        checkBoxes = new ArrayList<>();

        for (String feature : features) {
            JCheckBox checkBox = new JCheckBox(feature);
            checkBoxes.add(checkBox);
            popupMenu.add(checkBox);
            // Aktualisiere den Text, wenn sich die Auswahl ändert
            checkBox.addActionListener(e -> updateSelectedFeatures());
        }

        // Button zeigt beim Klick das Popup an
        selectButton.addActionListener(e -> {
            // Zeige das Popup unter dem Button an
            popupMenu.show(selectButton, 0, selectButton.getHeight());
        });
    }

    private void updateSelectedFeatures() {
        List<String> selected = new ArrayList<>();
        for (JCheckBox cb : checkBoxes) {
            if (cb.isSelected()) {
                selected.add(cb.getText());
            }
        }
        selectedFeaturesField.setText(String.join(", ", selected));
    }

    /**
     * Liefert die Liste der ausgewählten Features.
     */
    public List<String> getSelectedFeatures() {
        List<String> selected = new ArrayList<>();
        for (JCheckBox cb : checkBoxes) {
            if (cb.isSelected()) {
                selected.add(cb.getText());
            }
        }
        return selected;
    }
}
