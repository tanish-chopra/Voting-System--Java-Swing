# Voting System

A comprehensive Java Swing-based voting application with MySQL database integration, featuring secure user registration, authentication, and administrative capabilities. The system uses SQL for persistent data storage and management of users, candidates, and voting records.

## 📋 Features

- **User Registration**: New users can create accounts with personal information
- **User Authentication**: Secure login system for registered users
- **Voting Interface**: Users can cast votes for available candidates
- **Admin Panel**: Administrators can view voting results and statistics
- **Database Integration**: MySQL database for persistent data storage
- **Secure Voting**: Each user can vote only once (voting restrictions implemented)

## 🛠️ Technologies Used

- **Java Swing**: GUI framework for desktop application
- **MySQL**: Database for storing user data, candidates, and votes
- **JDBC**: Java Database Connectivity for database operations
- **Apache Ant**: Build automation tool (build.xml)
- **NetBeans**: IDE project structure

## 📁 Project Structure

```
Voting/
├── src/
│   ├── LoginForm.java          # Main login interface
│   ├── RegistrationForm.java   # User registration form
│   ├── VotingForm.java         # Voting interface for users
│   ├── AdminPanelForm.java     # Admin dashboard for results
│   └── DatabaseConnection.java # Database connection utility
├── nbproject/                  # NetBeans project files
├── build.xml                   # Apache Ant build script
├── manifest.mf                 # JAR manifest file
└── README.md                   # This file
```

## 🚀 Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- MySQL Server
- MySQL Connector/J driver
- Apache Ant (optional, for building)

### Database Setup

1. Install and start MySQL Server
2. Create a database named `voting_system`:
   ```sql
   CREATE DATABASE voting_system;
   USE voting_system;
   ```

3. Create the required tables:
   ```sql
   -- Users table
   CREATE TABLE users (
       id INT AUTO_INCREMENT PRIMARY KEY,
       first_name VARCHAR(50) NOT NULL,
       last_name VARCHAR(50) NOT NULL,
       username VARCHAR(50) UNIQUE NOT NULL,
       password VARCHAR(100) NOT NULL,
       is_admin BOOLEAN DEFAULT FALSE
   );

   -- Candidates table
   CREATE TABLE candidates (
       id INT AUTO_INCREMENT PRIMARY KEY,
       name VARCHAR(100) NOT NULL
   );

   -- Votes table
   CREATE TABLE votes (
       id INT AUTO_INCREMENT PRIMARY KEY,
       user_id INT,
       candidate_id INT,
       vote_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       FOREIGN KEY (user_id) REFERENCES users(id),
       FOREIGN KEY (candidate_id) REFERENCES candidates(id)
   );
   ```

4. Insert sample candidates:
   ```sql
   INSERT INTO candidates (name) VALUES 
   ('Candidate A'),
   ('Candidate B'),
   ('Candidate C');
   ```

5. Create an admin user (optional):
   ```sql
   INSERT INTO users (first_name, last_name, username, password, is_admin) 
   VALUES ('Admin', 'User', 'admin', 'admin123', TRUE);
   ```

### Installation

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd Voting
   ```

2. **Download MySQL Connector/J:**
   - Download the MySQL JDBC driver from [MySQL official website](https://dev.mysql.com/downloads/connector/j/)
   - Add the JAR file to your project classpath

3. **Configure Database Connection:**
   - Update the database connection details in `DatabaseConnection.java` if needed:
   ```java
   connection = DriverManager.getConnection(
       "jdbc:mysql://localhost:3306/voting_system", 
       "your_username", 
       "your_password"
   );
   ```

4. **Compile and Run:**
   
   **Using NetBeans:**
   - Open the project in NetBeans IDE
   - Right-click on the project and select "Run"

   **Using Command Line:**
   ```bash
   # Compile all Java files
   javac -cp ".:mysql-connector-java-8.0.33.jar" src/*.java
   
   # Run the application
   java -cp ".:mysql-connector-java-8.0.33.jar:src" LoginForm
   ```

   **Using Ant:**
   ```bash
   ant compile
   ant run
   ```

## 💻 Usage

### For Regular Users

1. **Registration:**
   - Launch the application
   - Click "Register" on the login form
   - Fill in your personal details
   - Create a unique username and password

2. **Voting:**
   - Log in with your credentials
   - Select a candidate from the dropdown menu
   - Click "Vote" to cast your ballot
   - You can only vote once per account

### For Administrators

1. **Admin Access:**
   - Log in with admin credentials
   - Access the admin panel automatically

2. **View Results:**
   - See real-time voting statistics
   - View vote counts for each candidate
   - Monitor voting activity

## 🔒 Security Features

- Password-based authentication
- Single vote per user restriction
- Admin role separation
- SQL injection prevention through prepared statements

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/new-feature`)
3. Commit your changes (`git commit -am 'Add new feature'`)
4. Push to the branch (`git push origin feature/new-feature`)
5. Create a Pull Request

## 📝 License

This project is open source and available under the [MIT License](LICENSE).

## 🐛 Known Issues

- Database connection credentials are hardcoded (should use configuration file)
- Basic UI design (can be enhanced with modern look and feel)
- Limited error handling for network connectivity issues

## 🔮 Future Enhancements

- [ ] Enhanced UI with modern themes
- [ ] Configuration file for database settings
- [ ] Email verification for registration
- [ ] Vote encryption for enhanced security
- [ ] Real-time vote counting dashboard
- [ ] Export voting results to PDF/Excel
- [ ] Multi-language support
- [ ] Web-based interface option

## 📞 Support

For support, please create an issue in the GitHub repository or contact the development team.

---

**Note:** This is an educational project demonstrating basic voting system concepts. For production use, additional security measures and features should be implemented.