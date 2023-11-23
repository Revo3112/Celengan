package Model;

import Utils.AlertHelper;
import Utils.DBConnection;
import javafx.scene.control.TextField;

import java.sql.*;

public class LoginModel {

    private String username, password; // Deklarasi property username dan password dengan itpe data String

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

            System.out.println("Connection closed." + result); // Menampilkan hasil dari variabel result

        } catch (SQLException e) { // Menangkap error dari SQLException
            System.out.println("Query failed: " + e.getMessage()); // Menampilkan error pada variabel e
        }

        return count; // Mengembalikan nilai count
    }

    public boolean isValidated(String username, String password, boolean rememberMe) {

        DBConnection dbc = DBConnection.getDatabaseConnection(); // Deklarasi dan inisialisasi variabel dbc
                                                                 // dengan nilai dari method getDatabaseConnection().
                                                                 // Berguna untuk mendapat koneksi ke database
        Connection connection = dbc.getConnection(); // Inisialisasi variabel connection dengan method getConnection()
                                                     // dari object dbc

        try {
            String sql = String.format("SELECT * FROM users WHERE username='%s' AND password='%s'", username,
                    password); // Inisialisasi variable sql dengan query ke database yaitu mengambil data dari
                               // tabel users berdasarkan nilai dari variabel username dan password

            Statement statement = connection.createStatement(); // Membuat statement dari method createStatement()
            ResultSet result = statement.executeQuery(sql); // Execute query sql menggunakan method executeQuery dan
                                                            // dimasukkan ke dalam variabel result

            result.next(); // Memindahkan pointer ke baris kedua dari result set
            this.username = result.getString("username"); // Inisialisasi property username dengan nilai dari kolom
                                                          // username
            this.password = result.getString("password"); // Inisialisasi property password dengan nilai dari kolom
                                                          // password

            if (this.username.equals(username) && this.password.equals(password)) { // Jika nilai dari property username
                                                                                    // dan password sama dengan input
                                                                                    // yang dimasukkan oleh user, maka:

                if (rememberMe) { // Jika user menekan remember me, maka:
                    String updateSql = String.format(
                            "UPDATE users SET remember_me=%b WHERE username='%s' AND password='%s'", rememberMe,
                            username, password); // Update kolom remember_me di database berdasarkan username dan
                                                 // password user
                    Statement updateStatement = connection.createStatement(); // Membuat statement
                    updateStatement.executeUpdate(updateSql); // Execute query sql
                    updateStatement.close(); // Menutup statement
                } else { // Jika user tidak menekan remember me, maka:
                    String updateSql = String.format(
                            "UPDATE users SET remember_me=%b WHERE username='%s' AND password='%s'", rememberMe,
                            username, password); // Update kolom remember_me di database berdasarkan username dan
                                                 // password user
                    Statement updateStatement = connection.createStatement(); // Membuat statement
                    updateStatement.executeUpdate(updateSql); // Execute query sql
                    updateStatement.close(); // Menutup statement
                }

                return true; // Mengembalikan nilai true
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
            try {
                DBConnection dbc = DBConnection.getDatabaseConnection(); // Deklarasi dan inisialisasi variabel dbc
                // dengan nilai dari method getDatabaseConnection().
                // Berguna untuk mendapat koneksi ke database
                Connection connection = dbc.getConnection(); // Inisialisasi variabel connection dengan method
                                                             // getConnection() dari object dbc

                String sql = String.format(
                        "INSERT INTO users(username, password, last_edited) VALUES('%s', '%s', CURRENT_TIMESTAMP)",
                        username, password); // Menambah data pada tabel users

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
