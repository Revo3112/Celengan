package Model;

import java.sql.*;

public class CheckingData {
    public int Checkdata() throws SQLException {
        Connection connection = null;
        int count = 0;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/celengan", "root", "");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        } finally {
            if (connection != null) {
                String sql = "SELECT COUNT(*) FROM users";
                java.sql.Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(sql);
                if (result.next()) {
                    count = result.getInt(1);
                }
                System.out.println("Connection closed." + result);
            }
        }
        return count;
    }
}
