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

    public static boolean simpanPemasukan(String tanggal, String kategori, int kategoriId, int jumlah, String tipePembayaran, String keterangan) {
        DBConnection dbc = DBConnection.getDatabaseConnection();
        Connection connection = dbc.getConnection();

        try {
            LoginModel user = new LoginModel();
            int userId = user.getUserId();

            String sql = String.format("INSERT INTO transac(user_id, nominal, keterangan, kategori_id, tipe_pembayaran, date, tipe_transaksi) VALUES(%d, %d, '%s', %d, '%s', '%s', 'pemasukan')", userId, jumlah, keterangan, kategoriId, tipePembayaran, tanggal);

            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    public static boolean simpanNamaKategori(String namaKategori, boolean kategoriDefault, String tipeKategori, int idKategoriDefault) {
        DBConnection dbc = DBConnection.getDatabaseConnection();
        Connection connection = dbc.getConnection();
        String sql = "";
        LoginModel loginModel = new LoginModel();
        int userId = loginModel.getUserId();

        try {
            if (kategoriDefault) {
                sql = String.format("INSERT INTO user_kategori(user_id, name, tipe, transac_kategori_id) VALUES(%d, '%s', '%s', %d)", userId, namaKategori, tipeKategori, idKategoriDefault);
            } else {
                sql = String.format("UPDATE user_kategori SET name='%s'");
            }
            
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    private static boolean isKategoriDefault(String namaKategori) {
        String[] listKategoriDefault = getKategoriDefault();

        for (int i = 0; i < listKategoriDefault.length; i++) {
            if (listKategoriDefault[i].equals(namaKategori)) {
                return true;
            }
        }
        return false;
    }
    public static boolean getIsKategoriDefault(String namaKategori) {
        return isKategoriDefault(namaKategori);
    }

    private static int _idKategoriDefault(String namaKategori) {
        DBConnection dbc = DBConnection.getDatabaseConnection();
        Connection connection = dbc.getConnection();

        try {
            String sql = String.format("SELECT id FROM transac_kategori WHERE name='%s'", namaKategori);

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql); 

            result.next();
            return result.getInt(1);

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return 0;
    }
    public static int getIdKategoriDefault(String namaKategori) {
        return _idKategoriDefault(namaKategori);
    }

    private int _idKategoriUser(String namaKategori) {
        DBConnection dbc = DBConnection.getDatabaseConnection();
        Connection connection = dbc.getConnection();

        try {
            String sql = String.format("SELECT id FROM user_kategori WHERE name='%s'", namaKategori);

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql); 

            result.next();
            return result.getInt(1);

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return 0;
    }
    public int getIdKategoriUser(String namaKategori) {
        return _idKategoriUser(namaKategori);
    }

    private static String[] getKategoriDefault() {
        DBConnection dbc = DBConnection.getDatabaseConnection();
        Connection connection = dbc.getConnection();
        List<String> listKategoriDefault = new ArrayList<>();

        try {
            String sql = String.format("SELECT * FROM transac_kategori");
            

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while(result.next()) {
                listKategoriDefault.add(result.getString("name"));
            }

            String[] kategori = listKategoriDefault.toArray(new String[0]);

            return kategori;

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return new String[0];
    }

    public int getJumlahKategoriPemasukan() {
        DBConnection dbc = DBConnection.getDatabaseConnection();
        Connection connection = dbc.getConnection();

        try {
            String sql = String.format("SELECT COUNT(*) FROM transac_kategori WHERE tipe='pemasukan'");
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            result.next();

            return result.getInt(1);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return 0;
    }
    
    public static String[] getKategoriPemasukan() {
        DBConnection dbc = DBConnection.getDatabaseConnection(); // Deklarasi dan inisialisasi variabel dbc 
                                                                // dengan nilai dari method getDatabaseConnection().
                                                                // Berguna untuk mendapat koneksi ke database
        Connection connection = dbc.getConnection(); // Inisialisasi variabel connection dengan method getConnection() dari object dbc
        List<String> kategoriDefault = new ArrayList<>();
        List<String> kategoriUser = new ArrayList<>();
        List<String> kategoriList = new ArrayList<>();

        try {               
            String sql_1 = "SELECT * FROM transac_kategori AS tk WHERE tipe='pemasukan' "; // Membuat query untuk mengambil data kategori default
            String sql_2 = "SELECT COUNT(*) AS hasil FROM user_kategori AS uk JOIN users AS u ON (u.id = uk.user_id)"; // Membuat query untuk mengambil data kategori yang ditambahkan user.

            Statement statement_1 = connection.createStatement(); // Membuat statement dari method createStatement()
            ResultSet result_kategori = statement_1.executeQuery(sql_1); // Execute query sql menggunakan method executeQuery dan dimasukkan ke dalam variabel result

            Statement statement_2 = connection.createStatement(); // Membuat statement dari method createStatement()
            ResultSet result_user_kategori = statement_2.executeQuery(sql_2); // Execute query sql menggunakan method executeQuery dan dimasukkan ke dalam variabel result

            while (result_kategori.next()) { // Loop hingga semua data terambil
                kategoriDefault.add(result_kategori.getString("name")); // Menambah data kategori default ke dalam kategoriDefault 
            }      

            while (result_user_kategori.next()) { // Loop hingga semua data terambil
                kategoriUser.add(result_user_kategori.getString("name")); // Menambah data kategori custom ke dalam kategoriList
            }
            for (String default_kategori : kategoriDefault) {
                for (String user_kategori : kategoriUser) {
                    if (user_kategori.equals(default_kategori)) {
                        
                    }
                }
            }

            String[] kategori = kategoriDefault.toArray(new String[0]); // Mengubah kategoriList menjadi array

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
        List<String> kategoriDefault = new ArrayList<>();
        List<String> kategoriUser = new ArrayList<>();

        try {               
            String sql_1 = "SELECT * FROM transac_kategori AS tk WHERE tipe='pengeluaran' "; // Membuat query untuk mengambil data kategori default
            // String sql_2 = "SELECT COUNT(*) AS hasil FROM user_kategori AS uk JOIN users AS u ON (u.id = uk.user_id)"; // Membuat query untuk mengambil data kategori yang ditambahkan user.
            String sql_2 = String.format("SELECT uk.name, tk.id FROM user_kategori AS uk JOIN transac_kategori AS tk ON (uk.transac_kategori_id = tk.id)");

            Statement statement_1 = connection.createStatement(); // Membuat statement dari method createStatement()
            ResultSet result_kategori = statement_1.executeQuery(sql_1); // Execute query sql menggunakan method executeQuery dan dimasukkan ke dalam variabel result

            Statement statement_2 = connection.createStatement(); // Membuat statement dari method createStatement()
            ResultSet result_user_kategori = statement_2.executeQuery(sql_2); // Execute query sql menggunakan method executeQuery dan dimasukkan ke dalam variabel result

            while (result_kategori.next()) { // Loop hingga semua data terambil
                if (result_user_kategori.next()) {
                    if (result_user_kategori.getInt("id") == result_kategori.getInt("id")) {
                        kategoriList.add(result_user_kategori.getString("name"));
                    }
                } else {
                    kategoriList.add(result_kategori.getString("name")); // Menambah data kategori default ke dalam kategoriList 
                }
            }
            
            String[] kategori = kategoriList.toArray(new String[0]); // Mengubah kategoriList menjadi array

            return kategori; // Mengembalikan array kategori
            
        } catch (SQLException e) { // Menangkap error SQLException
            System.out.println("Query failed: " + e.getMessage()); // Menampilkan error SQLException
        } 

        return new String[0]; // Mengembalikan string kosong jika terjadi kesalahan
    } 
}
