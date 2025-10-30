import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class LoginForm extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnRegister;

    public LoginForm() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        txtUsername = new JTextField(15);
        txtPassword = new JPasswordField(15);
        btnLogin = new JButton("Login");
        btnRegister = new JButton("Register");

        JPanel panel = new JPanel();
        panel.add(new JLabel("Username:"));
        panel.add(txtUsername);
        panel.add(new JLabel("Password:"));
        panel.add(txtPassword);
        panel.add(btnLogin);
        panel.add(btnRegister);

        add(panel);
        
        // Action Listeners
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new RegistrationForm().setVisible(true);
                dispose();
            }
        });
    }

    private void login() {
        String username = txtUsername.getText();
        String password = String.valueOf(txtPassword.getPassword());
        
        try (Connection con = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                new VotingForm(username).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new LoginForm().setVisible(true);
    }
}
