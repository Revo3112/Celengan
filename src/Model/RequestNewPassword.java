package Model;

import java.sql.Connection;
import java.sql.ResultSet;
import Utils.DBConnection;
import Utils.hashingregister;
import java.sql.Statement;

/*
 * Kelas RequestNewPassword untuk mengatur permintaan
 */
public class RequestNewPassword {
    // Atribut
    private String pinCode, salt, username;
    hashingregister hashing = new hashingregister();
    private int user_ID;

    /*
     * Konstruktor RequestNewPassword
     */
    public RequestNewPassword() {
        LoginModel loginModel = new LoginModel();
        this.user_ID = loginModel.getUserId();
    }

    /*
     * Mengubah password baru pada akun pengguna di database
     */
    public boolean updateNewPassword(String keyUser, String newPassword) throws Exception {
        boolean status = false;
        String hashedPassword = "";
        String hashedKeyUser = "";
        String userIDSalt = getSalt(this.user_ID);

        // Hashing password dan keyUser
        hashingregister hashingRegister = new hashingregister();
        hashedPassword = hashingRegister.hash(newPassword, userIDSalt);
        hashedKeyUser = hashingRegister.hash(keyUser, userIDSalt);

        try {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();
            String sql = "UPDATE users SET password = '%s', last_edited=NOW() WHERE pincode='%s'";
            sql = String.format(sql, hashedPassword, hashedKeyUser);
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            status = true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return status;
    }

    /*
     * Mengambil salt dari database
     */
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

    /*
     * Memeriksa data yang dimasukkan pengguna
     */
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

                // Memeriksa apakah pinCode dan username sesuai
                if (hashedPinCode.equals(pinCode) && inputUsername.equals(this.username)) {
                    // Memperbarui password baru dan mengembalikan status true
                    status = updateNewPassword(keyUser, newPassword);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return status;
    }
}