import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AdminPanelForm extends JFrame {
    private JTable tblResults;
    private JButton btnLogout;

    public AdminPanelForm() {
        setTitle("Admin Panel - Voting Results");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        tblResults = new JTable();
        btnLogout = new JButton("Logout");

        // Load results into the table
        loadVotingResults();

        // Set layout and add components
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(tblResults), BorderLayout.CENTER);
        panel.add(btnLogout, BorderLayout.SOUTH);
        add(panel);

        // Logout action
        btnLogout.addActionListener(e -> {
            new LoginForm().setVisible(true);
            dispose(); // Close admin panel
        });
    }

    private void loadVotingResults() {
        try (Connection con = DatabaseConnection.getConnection()) {
            String query = "SELECT c.name AS candidate_name, COUNT(v.id) AS votes " +
                           "FROM candidates c LEFT JOIN votes v ON c.id = v.candidate_id " +
                           "GROUP BY c.id";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            String[] columnNames = {"Candidate Name", "Votes"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            while (rs.next()) {
                String candidateName = rs.getString("candidate_name");
                int votes = rs.getInt("votes");
                model.addRow(new Object[]{candidateName, votes});
            }

            tblResults.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading results: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminPanelForm().setVisible(true));
    }
}
