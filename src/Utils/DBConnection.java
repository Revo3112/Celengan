package Utils;
import java.sql.*;

public class DBConnection {
    private Connection connection; // Deklarasi variabel connection
    private static DBConnection dbc; // Deklarasi variabel dbc
                                    // dbc = database connection

    public DBConnection() {
        try {
            // Inisialisasi field connection dengan method getConnection agar mendapat koneksi ke database
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/celengan", "root", "");
        } catch (SQLException e) { // Menangkap error SQLException
            System.out.println("Connection failed: " + e.getMessage()); // Menampilkan error SQLException
        }
    }

    public static DBConnection getDatabaseConnection() {
        if (dbc == null) { // Jika field dbc bernilai null, maka:
            dbc = new DBConnection(); // Instansiasi field dbc dengan class DBConnection
        }
        return dbc; // Mengembalikan nilai dari field dbc
    }

    public Connection getConnection() {
        return this.connection; // Mengembalikan nilai dari field connection
    }

}
