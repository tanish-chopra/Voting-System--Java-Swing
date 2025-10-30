import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class VotingForm extends JFrame {
    private JComboBox<String> cmbCandidates;
    private JButton btnVote, btnLogout;
    private String username;

    public VotingForm(String username) {
        this.username = username;
        setTitle("Vote");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cmbCandidates = new JComboBox<>();
        loadCandidates();
        btnVote = new JButton("Vote");
        btnLogout = new JButton("Logout");

        JPanel panel = new JPanel();
        panel.add(new JLabel("Select Candidate:"));
        panel.add(cmbCandidates);
        panel.add(btnVote);
        panel.add(btnLogout);

        add(panel);

        btnVote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                vote();
            }
        });

        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LoginForm().setVisible(true);
                dispose();
            }
        });
    }

    private void loadCandidates() {
        try (Connection con = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM candidates";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                cmbCandidates.addItem(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void vote() {
        String selectedCandidate = (String) cmbCandidates.getSelectedItem();

        try (Connection con = DatabaseConnection.getConnection()) {
            // Update votes table
            String query = "INSERT INTO votes (user_id, candidate_id) VALUES ((SELECT id FROM users WHERE username=?), (SELECT id FROM candidates WHERE name=?))";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, selectedCandidate);
            ps.executeUpdate();

            // Update candidate's vote count
            String updateQuery = "UPDATE candidates SET votes = votes + 1 WHERE name = ?";
            ps = con.prepareStatement(updateQuery);
            ps.setString(1, selectedCandidate);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Vote cast successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
