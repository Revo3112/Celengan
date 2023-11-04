package Model;

import java.sql.*;

class Register {
    private String user;
    private String password;

    // Getter
    public String get_user() {
        return user;
    }

    public String get_password() {
        return password;
    }

    // Setter
    public void set_user(String user) {
        this.user = user;
    }

    public void set_password(String password) {
        this.password = password;
    }
}

public class RegisterDatabase {
    public void sql_register() {
        // Create a connection to the database
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/celengan", "root", "");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        } finally {
            if (connection != null) {
                // String sql = "INSERT INTO users (username, password, last_edited,
                // remember_me) VALUES ('Revo', '2002071047', CUREENT_TIMESTAMP, 'True')";
                // statement.executeQuery(sql);

                String sql = "Select * from users";
                try {
                    java.sql.Statement statement = connection.createStatement();
                    ResultSet result = statement.executeQuery(sql);
                    System.out.println("Connection closed." + result);
                } catch (SQLException e) {
                    System.out.println("Failed to execute query: " + e.getMessage());
                } finally {
                    if (connection != null) {
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            System.out.println("Failed to close connection: " + e.getMessage());
                        }
                    }
                }

            }
        }
    }
}
