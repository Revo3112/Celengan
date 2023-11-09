package Utils;
import java.sql.*;

public class DBConnection {
    private Connection connection;
    private static DBConnection dbc; // dbc = database connection

    public DBConnection() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/celengan", "root", "");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }

    public static DBConnection getDatabaseConnection() {
        if (dbc == null) {
            dbc = new DBConnection();
        }
        return dbc;
    }

    public Connection getConnection() {
        return this.connection;
    }

}
