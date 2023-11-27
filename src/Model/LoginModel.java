package Model;

import Utils.AlertHelper;
import Utils.DBConnection;
import Utils.hashingregister;
import javafx.scene.control.TextField;

import java.sql.*;

public class LoginModel {

    private String username, password, lastActiveUsers; // Deklarasi property username dan password dengan itpe data
                                                        // String
    private boolean rememberMe;

    public int checkData() throws SQLException {
        DBConnection dbc = DBConnection.getDatabaseConnection(); // Deklarasi dan inisialisasi variabel dbc
                                                                 // dengan nilai dari method getDatabaseConnection().
                                                                 // Berguna untuk mendapat koneksi ke database
        Connection connection = dbc.getConnection(); // Inisialisasi variabel connection dengan method getConnection()
                                                     // dari object dbc
        int count = 0; // Deklarasi dan insisialisasi variabel count dengan nilai 0

        try {
            String sql = "SELECT COUNT(*) FROM users"; // Inisialisasi variable sql dengan query ke database yaitu
                                                       // "SELECT COUNT(*) FROM users"
            Statement statement = connection.createStatement(); // Inisialiasi variable statement dengan nilai dari
                                                                // method createStatement()

            ResultSet result = statement.executeQuery(sql); // Execute query sql menggunakan method executeQuery dan
                                                            // dimasukkan ke dalam variabel result

            if (result.next()) { // Memindahkan pointer ke baris kedua dari result set
                count = result.getInt(1); // Mengambil nilai dari kolom integer dan dimasukkan ke dalam variabel count
            }

            if (count != 0) {
                System.out.println("Connection closed." + result); // Menampilkan hasil dari
                // variabel result
            }
        } catch (SQLException e) { // Menangkap error dari SQLException
            System.out.println("Query failed: " + e.getMessage()); // Menampilkan error pada variabel e
        }

        return count; // Mengembalikan nilai count
    }

    public boolean penentuBagianLastUser() {
        boolean masuk = false;
        DBConnection dbc = DBConnection.getDatabaseConnection(); // Deklarasi dan inisialisasi variabel dbc
                                                                 // dengan nilai dari method getDatabaseConnection().
                                                                 // Berguna untuk mendapat koneksi ke database
        Connection connection = dbc.getConnection(); // Inisialisasi variabel connection dengan method getConnection()
                                                     // dari object dbc
        System.out.println("Masuk ke dalam check last active user");
        // Mendapatkan username yang paling terakhir active
        String lastActiveUser = getLastActiveUser(connection);
        String lastPassword = getPasswordFromLastUser(connection, lastActiveUser);
        this.rememberMe = getRememberMeFromUsername(connection, lastActiveUser);
        if (this.rememberMe == true) {
            masuk = isValidated(lastActiveUser, lastPassword, this.rememberMe);

        }
        return masuk;
    }

    private String getLastActiveUser(Connection connection) {
        this.lastActiveUsers = "";
        try {
            String sql = "SELECT username FROM users WHERE last_edited = (SELECT MAX(last_edited) FROM users)";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            if (result.next()) {
                this.lastActiveUsers = result.getString("username");
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        return this.lastActiveUsers;
    }

    private boolean getRememberMeFromUsername(Connection connection, String lastUser) {
        this.rememberMe = false;
        try {
            String sql = String.format("SELECT remember_me FROM users WHERE username='%s'", lastUser);
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            if (result.next()) {
                this.rememberMe = result.getBoolean("remember_me");
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        return this.rememberMe;
    }

    private String getPasswordFromLastUser(Connection connection, String LastUser) {
        String password = "";
        try {
            String sql = String.format("SELECT password FROM users WHERE username='%s'", LastUser);
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            if (result.next()) {
                password = result.getString("password");
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        return password;
    }

    public boolean isValidated(String username, String password, boolean rememberMe) {
        String salt = ""; // Deklarasi dan inisialisasi variabel salt dengan nilai kosong
        String hashedPassword = ""; // Deklarasi dan inisialisasi variabel hashedPassword dengan nilai kosong
        DBConnection dbc = DBConnection.getDatabaseConnection(); // Deklarasi dan inisialisasi variabel dbc
                                                                 // dengan nilai dari method getDatabaseConnection().
                                                                 // Berguna untuk mendapat koneksi ke database
        Connection connection = dbc.getConnection(); // Inisialisasi variabel connection dengan method getConnection()
                                                     // dari object dbc

        try {
            String sql = String.format("SELECT * FROM users WHERE username='%s'", username); // Inisialisasi variable
                                                                                             // sql dengan query ke
                                                                                             // database yaitu mengambil
                                                                                             // data dari
            // tabel users berdasarkan nilai dari variabel username dan password

            Statement statement = connection.createStatement(); // Membuat statement dari method createStatement()
            ResultSet result = statement.executeQuery(sql); // Execute query sql menggunakan method executeQuery dan
                                                            // dimasukkan ke dalam variabel result

            result.next(); // Memindahkan pointer ke baris kedua dari result set
            // Inisialisasi property username dengan nilai dari kolom username
            this.username = result.getString("username");
            // Inisialisasi property password dengan nilai dari kolom password
            this.password = result.getString("password");
            // Inisialisasi variabel salt dengan nilai dari kolom hash
            salt = result.getString("hash");
            // Deklarasi dan inisialisasi variabel hashing dengan nilai dari class
            // hashingregister
            hashingregister hashing = new hashingregister();
            // Inisialisasi variabel hashedPassword dengan nilai
            // dari method hash() dari class hashingregister
            if (this.rememberMe == true) {
                hashedPassword = password;
            } else {
                hashedPassword = hashing.hash(password, salt);
            }
            if (this.username.equals(username) && hashedPassword.equals(this.password)) {
                String updateSql = "UPDATE users SET remember_me=?, last_edited=NOW() WHERE username=? AND password=?";

                try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                    updateStatement.setBoolean(1, rememberMe);
                    updateStatement.setString(2, username);
                    updateStatement.setString(3, this.password);
                    updateStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                return true;
            }
        } catch (SQLException e) { // Menangkap error yang dihasilkan oleh SQLException
            System.out.println("Query Failed: " + e.getMessage()); // Menampilkan error SQLException
        }
        return false; // Mengembalikan nilai false
    }

    public boolean registerAccount(String username, String password) { // Membuat method untuk registrasi

        if (username.equals("") && password.equals("")) { // Jika kolom username dan password kosong, maka:
            AlertHelper.alert("Kolom Username dan Password tidak boleh kosong!"); // Tampilkan error dari method alert
                                                                                  // pada class AlertHelper
        } else if (username.equals("")) { // Jika kolom usernama kosong, maka:
            AlertHelper.alert("Kolom Username tidak boleh kosong!"); // Tampilkan error dari method alert pada class
                                                                     // AlertHelper
        } else if (password.equals("")) { // Jika kolom password kosong, maka:
            AlertHelper.alert("Kolom Password tidak boleh kosong!"); // Tampilkan error dari method alert pada class
                                                                     // AlertHelper
        } else { // Jika username dan password tidak kosong
            // Deklarasi dan inisialisasi variabel salt dengan nilai dari method setkey()
            String salt = new hashingregister().setkey();
            // Deklarasi dan inisialisasi variabel hashedPassword dengan nilai dari method
            // hash() dari class hashingregister
            String hashedPassword = new hashingregister().hash(password, salt);
            try {
                DBConnection dbc = DBConnection.getDatabaseConnection(); // Deklarasi dan inisialisasi variabel dbc
                // dengan nilai dari method getDatabaseConnection().
                // Berguna untuk mendapat koneksi ke database
                Connection connection = dbc.getConnection(); // Inisialisasi variabel connection dengan method
                                                             // getConnection() dari object dbc

                String sql = String.format(
                        "INSERT INTO users(username, password, last_edited, hash) VALUES('%s', '%s', CURRENT_TIMESTAMP, '%s')",
                        username, hashedPassword, salt); // Menambah data pada tabel users

                Statement statement = connection.createStatement(); // Membuat statement
                int result = statement.executeUpdate(sql); // Execute query

                if (result == 1) { // Jika proses query berhasil

                    return true; // Mengembalikan nilai true
                }

            } catch (SQLException e) { // Menangkap error SQLException
                System.out.println("Query Failed: " + e.getMessage()); // Menampikan error SQLException
            }
        }
        return false; // Mengembalikan nilai false

    }
}
