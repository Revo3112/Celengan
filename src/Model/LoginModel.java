package Model;

import Utils.AlertHelper;
import Utils.DBConnection;
import Utils.hashingregister;

import java.sql.*;

public class LoginModel {

    private String username, password, lastActiveUsers; // Deklarasi property username dan password dengan itpe data
                                                        // String
    private boolean rememberMe;
    private int userId;

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
        DBConnection dbc = DBConnection.getDatabaseConnection();
        Connection connection = dbc.getConnection();
        System.out.println("Masuk ke dalam check last active user");
        // Mendapatkan username yang paling terakhir active
        String lastActiveUsers = getLastActiveUser(connection);

        // Periksa apakah lastActiveUsers tidak kosong
        if (!lastActiveUsers.isEmpty()) {
            String lastPassword = getPasswordFromLastUser(connection, lastActiveUsers);
            this.rememberMe = getRememberMeFromUsername(connection, lastActiveUsers);
            if (this.rememberMe) {
                masuk = isValidated(lastActiveUsers, lastPassword, this.rememberMe);
                System.out.println("Masuk ke dalam remember me");
                System.out.println("Username: " + lastActiveUsers);
            }
        } else {
            System.out.println("Tidak ada last active user");
        }

        return masuk;
    }

    public String getLastActiveUsers() {
        DBConnection dbc = DBConnection.getDatabaseConnection();
        Connection connection = dbc.getConnection();
        return getLastActiveUser(connection);
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

    private int _userId(Connection connection) {
        try {
            String sql = "SELECT id FROM users WHERE last_edited = (SELECT MAX(last_edited) FROM users)";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            System.out.println("Masuk ke dalam _userId" + result);
            if (result.next()) {
                this.userId = result.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        return this.userId;
    }

    public int getUserId() {
        DBConnection dbc = DBConnection.getDatabaseConnection();
        Connection connection = dbc.getConnection();

        return _userId(connection);
    }

    public double getUserSaldo() {
        DBConnection dbc = DBConnection.getDatabaseConnection();
        Connection connection = dbc.getConnection();
        double saldo = 0;
        try {
            String sql = String.format("SELECT balance FROM saldo WHERE user_id='%s'", getUserId());
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            if (result.next()) {
                saldo = result.getDouble("balance");
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        return saldo;
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

    public boolean registerAccount(String username, String password, String pinCode) { // Membuat method untuk

        String salt = new hashingregister().setkey();
        // Deklarasi dan inisialisasi variabel hashedPassword dengan nilai dari method
        // hash() dari class hashingregister
        String hashedPassword = new hashingregister().hash(password, salt);
        String hashedpin = new hashingregister().hash(pinCode, salt);
        try {
            DBConnection dbc = DBConnection.getDatabaseConnection(); // Deklarasi dan inisialisasi variabel dbc
            // dengan nilai dari method getDatabaseConnection().
            // Berguna untuk mendapat koneksi ke database
            Connection connection = dbc.getConnection(); // Inisialisasi variabel connection dengan method
                                                         // getConnection() dari object dbc

            String sql = String.format(
                    "INSERT INTO users(username, password, last_edited, hash, pincode) VALUES('%s', '%s',       CURRENT_TIMESTAMP, '%s', '%s')",
                    username, hashedPassword, salt, hashedpin); // Menambah data pada tabel users

            Statement statement = connection.createStatement(); // Membuat statement
            int result = statement.executeUpdate(sql); // Execute query

            if (result == 1) { // Jika proses query berhasil
                if (pembuatanSaldo(getUserId())) {
                    System.out.println("Saldo berhasil dibuat");
                    return true; // Mengembalikan nilai true
                }
            }

        } catch (SQLException e) { // Menangkap error SQLException
            System.out.println("Query Failed: " + e.getMessage()); // Menampikan error SQLException
        }

        return false; // Mengembalikan nilai false
    }

    public boolean pembuatanSaldo(int userId) {
        try {
            DBConnection dbc = DBConnection.getDatabaseConnection(); // Deklarasi dan inisialisasi variabel dbc
            // dengan nilai dari method getDatabaseConnection().
            // Berguna untuk mendapat koneksi ke database
            Connection connection = dbc.getConnection(); // Inisialisasi variabel connection dengan method
                                                         // getConnection() dari object dbc
            // Menambah datapada tabel users
            String sql = String.format("INSERT INTO saldo(user_id) VALUES('%s')", userId);
            Statement statement = connection.createStatement(); // Membuat statement
            int result = statement.executeUpdate(sql); // Execute query
            if (result > 0) {
                return true;
            }
        } catch (SQLException e) { // Menangkap error SQLException
            System.out.println("Query Failed: " + e.getMessage()); // Menampikan error SQLException
        }
        return false;
    }

    public boolean penentuApakahSudahAdaSaldo() {
        try {
            DBConnection dbc = DBConnection.getDatabaseConnection(); // Deklarasi dan inisialisasi variabel dbc
            // dengan nilai dari method getDatabaseConnection().
            // Berguna untuk mendapat koneksi ke database
            Connection connection = dbc.getConnection(); // Inisialisasi variabel connection dengan method
                                                         // getConnection() dari object dbc
            // Menambah datapada tabel users
            String sql = String.format("SELECT * FROM saldo WHERE user_id='%s'", getUserId());
            Statement statement = connection.createStatement(); // Membuat statement
            ResultSet result = statement.executeQuery(sql); // Execute query
            if (result.next()) {
                int status = result.getInt("kritis");
                if (status == 0) {
                    System.out.println("Tidak ada saldo");
                    return false;
                } else {
                    System.out.println("Ada saldo");
                    return true;
                }
            }
        } catch (SQLException e) { // Menangkap error SQLException
            System.out.println("Query Failed: " + e.getMessage()); // Menampikan error SQLException
        }
        return false;
    }

    public boolean mengaturRememberMeMenjadiFalse() {
        try {
            DBConnection dbc = DBConnection.getDatabaseConnection(); // Deklarasi dan inisialisasi variabel dbc
            // dengan nilai dari method getDatabaseConnection().
            // Berguna untuk mendapat koneksi ke database
            Connection connection = dbc.getConnection(); // Inisialisasi variabel connection dengan method
                                                         // getConnection() dari object dbc
            // Menambah datapada tabel users
            String sql = String.format("UPDATE users SET remember_me=? WHERE id = ?");
            PreparedStatement statement = connection.prepareStatement(sql); // Membuat statement
            statement.setBoolean(1, false);
            statement.setInt(2, getUserId());
            int result = statement.executeUpdate(); // Execute query
            if (result > 0) {
                return true;
            }
        } catch (SQLException e) { // Menangkap error SQLException
            System.out.println("Query Failed: " + e.getMessage()); // Menampikan error SQLException
        }
        return false;
    }
}
