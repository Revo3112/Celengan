package Model;

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

    /*
     * Method untuk mengecek apakah username dan password yang dimasukkan sudah
     * benar ketika user melakukan remember me
     */
    public boolean penentuBagianLastUser() {
        boolean masuk = false;
        DBConnection dbc = DBConnection.getDatabaseConnection();
        Connection connection = dbc.getConnection();

        // Mendapatkan username yang paling terakhir active
        String lastActiveUsers = getLastActiveUser(connection);

        // Periksa apakah lastActiveUsers tidak kosong
        if (!lastActiveUsers.isEmpty()) {
            String lastPassword = getPasswordFromLastUser(connection, lastActiveUsers);
            this.rememberMe = getRememberMeFromUsername(connection, lastActiveUsers);
            // Periksa apakah rememberMe bernilai true jika tidak maka akan mengembalikan
            // nilai false
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

    /*
     * Method untuk mendapatkan username yang paling terakhir active
     */
    public String getLastActiveUsers() {
        DBConnection dbc = DBConnection.getDatabaseConnection();
        Connection connection = dbc.getConnection();
        return getLastActiveUser(connection);
    }

    /*
     * Method untuk mendapatkan username dari username yang paling terakhir active
     */
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

    /*
     * Method untuk mendapatkan user id dari username yang paling terakhir active
     */
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

    /*
     * Method untuk mendapatkan user id dari username yang paling terakhir active
     */
    public int getUserId() {
        DBConnection dbc = DBConnection.getDatabaseConnection();
        Connection connection = dbc.getConnection();

        return _userId(connection);
    }

    /*
     * Method untuk mendapatkan saldo dari user yang sedang login
     */
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

    /*
     * Method untuk mendapatkan remember me dari username yang paling terakhir
     * active
     */
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

    /*
     * Method untuk mendapatkan password dari username yang paling terakhir active
     */
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

    /*
     * Method untuk mengecek apakah username dan password yang dimasukkan sudah
     * benar ketika user melakukan remember me maupun tidak ini merupakan method
     * untuk login
     */
    public boolean isValidated(String username, String password, boolean rememberMe) {
        // Deklarasi dan inisialisasi variabel salt dengan nilai kosong
        String salt = "";
        // Deklarasi dan inisialisasi variabel hashedPassword dengan nilai kosong
        String hashedPassword = "";

        /*
         * Deklarasi dan inisialisasi variabel dbc
         * dengan nilai dari method getDatabaseConnection().
         * Berguna untuk mendapat koneksi ke database
         */

        DBConnection dbc = DBConnection.getDatabaseConnection();
        /*
         * Inisialisasi variabel connection dengan method getConnection() dari object
         * dbc
         */
        Connection connection = dbc.getConnection();

        /*
         * Query ke database yaitu mengambil data dari tabel users berdasarkan nilai
         * dari
         * variabel username
         */
        try {
            /*
             * Inisialisasi variable
             * sql dengan query ke
             * database yaitu mengambil
             * data dari tabel users berdasarkan nilai dari variabel username dan password
             */
            String sql = String.format("SELECT * FROM users WHERE username='%s'", username);

            // Membuat statement dari method createStatement()
            Statement statement = connection.createStatement();
            /*
             * Execute query sql menggunakan method executeQuery dan
             * dimasukkan ke dalam variabel result
             */
            ResultSet result = statement.executeQuery(sql);

            // Memindahkan pointer ke baris kedua dari result set
            result.next();

            // Inisialisasi property username dengan nilai dari kolom username
            this.username = result.getString("username");

            // Inisialisasi property password dengan nilai dari kolom password
            this.password = result.getString("password");

            // Inisialisasi variabel salt dengan nilai dari kolom hash
            salt = result.getString("hash");

            // Inisialisasi variabel hashedPassword dengan nilai dari method hash() dari
            // class hashingregister
            hashingregister hashing = new hashingregister();

            /*
             * Periksa apakah rememberMe bernilai true jika tidak maka hashedPassword tidak
             * akan berisi dari password yang di hash
             * melainkan melakukan hash terlebih dahulu dengan nilai dari variabel salt lalu
             * di masukkan ke dalam variabel hashedPassword
             * jika true maka akan mengembalikan nilai hashedPassword dengan nilai password
             */
            if (this.rememberMe == true) {
                hashedPassword = password;
            } else {
                hashedPassword = hashing.hash(password, salt);
            }

            /*
             * Periksa apakah username dan password yang dimasukkan sudah benar jika benar
             * maka akan mengembalikan nilai true sekaligus mengubah nilai dari rememberMe
             * dan mengubah nilai dari last_edited menjadi NOW()
             * jika salah mengembalikan nilai false dan tidak mengubah nilai dari rememberMe
             * dan
             * last_edited
             */
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

    /*
     * Method untuk register akun baru
     */
    public boolean registerAccount(String username, String password, String pinCode) {

        /*
         * Deklarasi dan inisialisasi variabel salt dengan nilai dari method setkey()
         */
        String salt = new hashingregister().setkey();
        // Deklarasi dan inisialisasi variabel hashedPassword dengan nilai dari method
        // hash() dari class hashingregister
        String hashedPassword = new hashingregister().hash(password, salt);
        /*
         * Deklarasi dan inisialisasi variabel hashedpin dengan nilai dari method
         */
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

    /*
     * Method untuk membuat saldo awal ketika user melakukan register
     */
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

    /*
     * Method unutk mengecek apakah use ryang sekarang emmiliki saldo atau tidak
     */
    public boolean penentuApakahSudahAdaSaldo() {
        try {
            DBConnection dbc = DBConnection.getDatabaseConnection(); // Deklarasi dan inisialisasi variabel dbc
            // dengan nilai dari method getDatabaseConnection().
            // Berguna untuk mendapat koneksi ke database
            Connection connection = dbc.getConnection(); // Inisialisasi variabel connection dengan method
                                                         // getConnection() dari object dbc
            // Menambah datapada tabel users
            String sql = String.format("SELECT * FROM saldo WHERE user_id=%d", getUserId());
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

    /*
     * Method untuk mengatur remember me menjadi false
     */
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
