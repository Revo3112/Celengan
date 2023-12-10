package Model;

import java.sql.Connection;
import java.sql.ResultSet;
import Utils.DBConnection;
import Utils.hashingregister;
import java.sql.Statement;

public class RequestNewPassword {
    private String pinCode, salt, username;
    hashingregister hashing = new hashingregister();
    private int user_ID;

    public RequestNewPassword() {
        LoginModel loginModel = new LoginModel();
        this.user_ID = loginModel.getUserId();
    }

    public boolean updateNewPassword(String keyUser, String newPassword) throws Exception {
        boolean status = false;
        String hashedPassword = "";
        String hashedKeyUser = "";
        String userIDSalt = getSalt(this.user_ID);

        hashingregister hashingRegister = new hashingregister();
        hashedPassword = hashingRegister.hash(newPassword, userIDSalt);
        hashedKeyUser = hashingRegister.hash(keyUser, userIDSalt);
        System.out.println(userIDSalt);

        try {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();
            String sql = "UPDATE users SET password = '%s', last_edited=NOW() WHERE pincode='%s'";
            sql = String.format(sql, hashedPassword, hashedKeyUser);
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            status = true;
            System.out.println(hashedPassword);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("Status pada Update new password " + status);
        return status;
    }

    private String getSalt(int userID) {
        String salt = "";
        DBConnection dbc = DBConnection.getDatabaseConnection();
        Connection connection = dbc.getConnection();
        try {
            String sql = String.format("SELECT hash FROM users WHERE id=%d", userID);
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            result.next();

            salt = result.getString("hash");

            return salt;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return salt;
    }

    public boolean checkData(String inputUsername, String keyUser, String newPassword) throws Exception {
        boolean status = false;
        try {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection con = dbc.getConnection();
            String sql = "SELECT * FROM users WHERE username = '%s'";
            sql = String.format(sql, inputUsername); // Menggunakan inputUsername untuk query
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(sql);
            if (result.next()) {
                this.pinCode = result.getString("pincode");
                this.salt = result.getString("hash");
                this.username = result.getString("username");
                String hashedPinCode = hashing.hash(keyUser, salt);
                System.out.println(hashedPinCode);
                if (hashedPinCode.equals(pinCode) && inputUsername.equals(this.username)) {
                    status = updateNewPassword(keyUser, newPassword);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return status;
    }
}