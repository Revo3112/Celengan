package Model;

import Utils.AlertHelper;
import java.sql.*;

public class LoginModel {

    private String username, password;

    public int checkData() throws SQLException {
        DBConnection dbc = DBConnection.getDatabaseConnection();
        Connection connection = dbc.getConnection();
        int count = 0;

        try {
                String sql = "SELECT COUNT(*) FROM users";
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(sql);
                if (result.next()) {
                    count = result.getInt(1);
                }
                System.out.println("Connection closed." + result);        

                result.close();
                statement.close();
                connection.close();

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }

        return count;
    }

    public boolean isValidated(String username, String password) {
        DBConnection dbc = DBConnection.getDatabaseConnection();
        Connection connection = dbc.getConnection();

        try {
            String sql = String.format("SELECT * FROM users WHERE username='%s' AND password='%s'", username, password);
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            result.next();
            this.username = result.getString("username");
            this.password = result.getString("password");

            if (this.username.equals(username) && this.password.equals(password)) {
                result.close();
                statement.close();
                connection.close();

                return true;
            }
        } catch (SQLException e) {
            System.out.println("Query Failed: " + e.getMessage());
        }

        return false;
    } 

    public boolean registerAccount(String username, String password, boolean rememberMe) {

        if (username.equals("") && password.equals("")) {
            AlertHelper.alert("Kolom Username dan Password tidak boleh kosong!");
        }
        else if (username.equals("")) {
            AlertHelper.alert("Kolom Username tidak boleh kosong!");
        } 
        else if (password.equals("")) {
            AlertHelper.alert("Kolom Password tidak boleh kosong!");            
        }
        else {
            try {
                DBConnection dbc = DBConnection.getDatabaseConnection();
                Connection connection = dbc.getConnection();

                String sql = String.format("INSERT INTO users(username, password, last_edited, remember_me) VALUES('%s', '%s', CURRENT_TIMESTAMP, '%s')", username, password, rememberMe);

                Statement statement = connection.createStatement();
                int result = statement.executeUpdate(sql);

                if (result == 1) {
                    statement.close();
                    connection.close();

                    return true;
                }

            } catch (SQLException e) {
                System.out.println("Query Failed: " + e.getMessage());
            }
        }
        return false;        

    }
}
