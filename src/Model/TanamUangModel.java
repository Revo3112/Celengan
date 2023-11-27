package Model;

import java.time.LocalDate;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import Utils.DBConnection;
import javafx.scene.control.DatePicker;

public class TanamUangModel {

    public static boolean simpanPengeluaran(String tanggal, String kategori, int kategoriId, int jumlah, String tipePembayaran, String keterangan) {
        DBConnection dbc = DBConnection.getDatabaseConnection();
        Connection connection = dbc.getConnection();

        try {
            LoginModel user = new LoginModel();
            int userId = user.getUserId();

            String sql = String.format("INSERT INTO transac(user_id, nominal, keterangan, kategori_id, tipe_pembayaran, date, tipe_transaksi) VALUES(%d, %d, '%s', %d, '%s', '%s', 'pengeluaran')", userId, jumlah, keterangan, kategoriId, tipePembayaran, tanggal);

            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    public static String[] getKategoriPemasukan() {
        DBConnection dbc = DBConnection.getDatabaseConnection(); // Deklarasi dan inisialisasi variabel dbc 
                                                                // dengan nilai dari method getDatabaseConnection().
                                                                // Berguna untuk mendapat koneksi ke database
        Connection connection = dbc.getConnection(); // Inisialisasi variabel connection dengan method getConnection() dari object dbc
        List<String> kategoriList = new ArrayList<>();

        try {               
            String sql_1 = "SELECT * FROM transac_kategori AS tk WHERE tipe='pemasukan' "; // Membuat query untuk mengambil data kategori default
            String sql_2 = "SELECT COUNT(*) AS hasil FROM user_kategori AS uk JOIN users AS u ON (u.id = uk.user_id)"; // Membuat query untuk mengambil data kategori yang ditambahkan user.

            Statement statement_1 = connection.createStatement(); // Membuat statement dari method createStatement()
            ResultSet result_kategori = statement_1.executeQuery(sql_1); // Execute query sql menggunakan method executeQuery dan dimasukkan ke dalam variabel result

            Statement statement_2 = connection.createStatement(); // Membuat statement dari method createStatement()
            ResultSet result_user_kategori = statement_2.executeQuery(sql_2); // Execute query sql menggunakan method executeQuery dan dimasukkan ke dalam variabel result

            while (result_kategori.next()) { // Loop hingga semua data terambil
                kategoriList.add(result_kategori.getString("name")); // Menambah data kategori default ke dalam kategoriList 
            }      

            result_user_kategori.next(); // Mengubah pointer pada result set menjadi baris kedua
            if (result_user_kategori.getInt("hasil") != 0) { // Jika user menambahkan kategori, maka
                while (result_user_kategori.next()) { // Loop hingga semua data terambil
                    kategoriList.add(result_user_kategori.getString("name")); // Menambah data kategori custom ke dalam kategoriList
                }
            }

            String[] kategori = kategoriList.toArray(new String[0]); // Mengubah kategoriList menjadi array

            return kategori; // Mengembalikan array kategori
            
        } catch (SQLException e) { // Menangkap error SQLException
            System.out.println("Query failed: " + e.getMessage()); // Menampilkan error SQLException
        } 

        return new String[0]; // Mengembalikan string kosong jika terjadi kesalahan
    }

    public static String[] getKategoriPengeluaran() {
        DBConnection dbc = DBConnection.getDatabaseConnection(); // Deklarasi dan inisialisasi variabel dbc 
                                                                // dengan nilai dari method getDatabaseConnection().
                                                                // Berguna untuk mendapat koneksi ke database
        Connection connection = dbc.getConnection(); // Inisialisasi variabel connection dengan method getConnection() dari object dbc
        List<String> kategoriList = new ArrayList<>();

        try {               
            String sql_1 = "SELECT * FROM transac_kategori AS tk WHERE tipe='pengeluaran' "; // Membuat query untuk mengambil data kategori default
            String sql_2 = "SELECT COUNT(*) AS hasil FROM user_kategori AS uk JOIN users AS u ON (u.id = uk.user_id)"; // Membuat query untuk mengambil data kategori yang ditambahkan user.

            Statement statement_1 = connection.createStatement(); // Membuat statement dari method createStatement()
            ResultSet result_kategori = statement_1.executeQuery(sql_1); // Execute query sql menggunakan method executeQuery dan dimasukkan ke dalam variabel result

            Statement statement_2 = connection.createStatement(); // Membuat statement dari method createStatement()
            ResultSet result_user_kategori = statement_2.executeQuery(sql_2); // Execute query sql menggunakan method executeQuery dan dimasukkan ke dalam variabel result

            while (result_kategori.next()) { // Loop hingga semua data terambil
                kategoriList.add(result_kategori.getString("name")); // Menambah data kategori default ke dalam kategoriList 
            }      

            result_user_kategori.next(); // Mengubah pointer pada result set menjadi baris kedua
            if (result_user_kategori.getInt("hasil") != 0) { // Jika user menambahkan kategori, maka
                while (result_user_kategori.next()) { // Loop hingga semua data terambil
                    kategoriList.add(result_user_kategori.getString("name")); // Menambah data kategori custom ke dalam kategoriList
                }
            }

            String[] kategori = kategoriList.toArray(new String[0]); // Mengubah kategoriList menjadi array

            return kategori; // Mengembalikan array kategori
            
        } catch (SQLException e) { // Menangkap error SQLException
            System.out.println("TANAM UANG MODEL WOI");
            System.out.println("Query failed: " + e.getMessage()); // Menampilkan error SQLException
        } 

        return new String[0]; // Mengembalikan string kosong jika terjadi kesalahan
    } 
}
