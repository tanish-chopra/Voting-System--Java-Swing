import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Register the MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish a connection to the MySQL database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/voting_system", "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
