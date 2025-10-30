import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class RegistrationForm extends JFrame {
    private JTextField txtFirstName, txtLastName, txtUsername;
    private JPasswordField txtPassword, txtConfirmPassword;
    private JButton btnRegister, btnBack;

    public RegistrationForm() {
        setTitle("Register");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        txtFirstName = new JTextField(15);
        txtLastName = new JTextField(15);
        txtUsername = new JTextField(15);
        txtPassword = new JPasswordField(15);
        txtConfirmPassword = new JPasswordField(15);
        btnRegister = new JButton("Register");
        btnBack = new JButton("Back to Login");

        JPanel panel = new JPanel();
        panel.add(new JLabel("First Name:"));
        panel.add(txtFirstName);
        panel.add(new JLabel("Last Name:"));
        panel.add(txtLastName);
        panel.add(new JLabel("Username:"));
        panel.add(txtUsername);
        panel.add(new JLabel("Password:"));
        panel.add(txtPassword);
        panel.add(new JLabel("Confirm Password:"));
        panel.add(txtConfirmPassword);
        panel.add(btnRegister);
        panel.add(btnBack);

        add(panel);

        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });

        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LoginForm().setVisible(true);
                dispose();
            }
        });
    }

    private void register() {
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String username = txtUsername.getText();
        String password = String.valueOf(txtPassword.getPassword());
        String confirmPassword = String.valueOf(txtConfirmPassword.getPassword());

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.");
            return;
        }

        try (Connection con = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO users (first_name, last_name, username, password) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, username);
            ps.setString(4, password);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Registration successful!");
            new LoginForm().setVisible(true);
            dispose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
