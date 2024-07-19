package com.room.booking.presentation;

        import com.room.booking.dao.UserDao;
        import com.room.booking.dao.UserDaoImpl;
        import com.room.booking.model.User;

        import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;

public class DeleteEmployerFrame extends JFrame {

    private JTextField usernameField;
    private UserDao userDao;

    public DeleteEmployerFrame() {
        userDao = new UserDaoImpl(); // Initialize UserDaoImpl

        setTitle("Delete Employer");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        // Username
        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        // Delete Button
        JButton deleteButton = new JButton("Delete");
        add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteEmployer();
            }
        });

        // Back to Employer Frame Button
        JButton backButton = new JButton("Back to Employer Frame");
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

        // Delete the employer using UserDaoImpl
        userDao.deleteUser(username);

        JOptionPane.showMessageDialog(this, "Employer deletion successful!");

        // Redirect to employer frame after deletion
        openEmployerFrame();

        // Optionally, close the deletion frame after deletion
        dispose();
    }

    private void openEmployerFrame() {
        User user = new User(1, "john.doe@example.com", "John Doe", "john.doe@example.com", "Admin", "password");
        EmployerFrame employerFrame = new EmployerFrame(user);
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
