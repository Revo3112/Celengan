package Model;

import java.time.LocalDate;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

    public static boolean simpanNamaKategori(String namaKategoriNew, String namaKategoriOld, boolean kategoriDefault, String tipeKategori, int idKategoriDefault) {
        DBConnection dbc = DBConnection.getDatabaseConnection();
        Connection connection = dbc.getConnection();
        String sql = "";
        LoginModel loginModel = new LoginModel();
        int userId = loginModel.getUserId();

        if (kategoriDefault) {
            sql = String.format("INSERT INTO user_kategori(user_id, name, tipe, transac_kategori_id) VALUES(%d, '%s', '%s', %d)", userId, namaKategoriNew, tipeKategori, idKategoriDefault);
        } else {
            if (isKategoriDefault(namaKategoriNew)) {
                sql = String.format("DELETE FROM user_kategori WHERE id=%d", getIdKategoriUser(namaKategoriOld));
            } else {
                sql = String.format("UPDATE user_kategori SET name='%s' WHERE id=%d", namaKategoriNew, getIdKategoriUser(namaKategoriOld));
            }
            System.out.println("UPDATEEEEEEE!!");
            System.out.println(namaKategoriNew);
            System.out.println("Kategori ID: " + getIdKategoriUser(namaKategoriOld));
        }

        try (PreparedStatement updateStatement = connection.prepareStatement(sql)) {
            updateStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
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


    private static int _idKategoriUser(String namaKategori) {
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
    public static int getIdKategoriUser(String namaKategori) {
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

    private static String[] _kategoriTanamUang(String tipeTU) {
        DBConnection dbc = DBConnection.getDatabaseConnection(); // Deklarasi dan inisialisasi variabel dbc 
                                                                // dengan nilai dari method getDatabaseConnection().
                                                                // Berguna untuk mendapat koneksi ke database
        Connection connection = dbc.getConnection(); // Inisialisasi variabel connection dengan method getConnection() dari object dbc

        List<String> kategoriList = new ArrayList<>();
        List<String> listKategoriDefault = new ArrayList<>();
        List<Integer> listIdKategoriDefault = new ArrayList<>();
        List<String> listKategoriUser = new ArrayList<>();
        List<Integer> listIdKategoriUser = new ArrayList<>();

        String tipeTanamUang = tipeTU.toLowerCase();

        LoginModel user = new LoginModel();
        int userId = user.getUserId();

        try {                   
            String sql_1 = String.format("SELECT * FROM transac_kategori AS tk WHERE tipe='%s'", tipeTanamUang); // Membuat query untuk mengambil data kategori default
            // String sql_2 = "SELECT COUNT(*) AS hasil FROM user_kategori AS uk JOIN users AS u ON (u.id = uk.user_id)"; // Membuat query untuk mengambil data kategori yang ditambahkan user.
            String sql_2 = String.format("SELECT uk.name, uk.transac_kategori_id FROM user_kategori AS uk WHERE user_id=%d AND tipe='%s';", userId, tipeTanamUang);

            Statement statement_1 = connection.createStatement(); // Membuat statement dari method createStatement()
            ResultSet resultKategori = statement_1.executeQuery(sql_1); // Execute query sql menggunakan method executeQuery dan dimasukkan ke dalam variabel result

            Statement statement_2 = connection.createStatement(); // Membuat statement dari method createStatement()
            ResultSet resultUserKategori = statement_2.executeQuery(sql_2); // Execute query sql menggunakan method executeQuery dan dimasukkan ke dalam variabel result
            
            while (resultKategori.next()) {
                listKategoriDefault.add(resultKategori.getString("name"));
                listIdKategoriDefault.add(resultKategori.getInt("id"));
            }
            while (resultUserKategori.next()) {
                listKategoriUser.add(resultUserKategori.getString("name"));
                listIdKategoriUser.add(resultUserKategori.getInt("transac_kategori_id"));
            }

            String[] arrKategoriDefault = listKategoriDefault.toArray(new String[0]);
            String[] arrKategoriUser = listKategoriUser.toArray(new String[0]);
            Integer[] arrIdKategoriDefault = listIdKategoriDefault.toArray(new Integer[0]);
            Integer[] arrIdKategoriUser = listIdKategoriUser.toArray(new Integer[0]);

            boolean isDefault;
            for (int i = 0; i < arrKategoriDefault.length; i++) {
                isDefault = false;
                for (int j = 0; j < arrKategoriUser.length; j++) {
                    if (arrIdKategoriDefault[i] == arrIdKategoriUser[j]) {
                        kategoriList.add(arrKategoriUser[j]);
                        isDefault = true;
                    }
                }
                if (!isDefault) {
                    kategoriList.add(arrKategoriDefault[i]);
                }
            }
            
            // boolean isDefault;
            // while (resultKategori.next()) {
            //     isDefault = false;
            //     while (resultUserKategori.next()) {
            //         if (resultUserKategori.getInt("id") == resultKategori.getInt("id")) {
            //             kategoriList.add(resultUserKategori.getString("name"));
            //             isDefault = true;
            //             break;
            //         }
            //     }
            //     if (!isDefault) {
            //         kategoriList.add(resultKategori.getString("name")); // Menambah data kategori default ke dalam kategoriList 
            //     }
            //     resultUserKategori.beforeFirst();
            // }
            
            String[] kategori = kategoriList.toArray(new String[0]); // Mengubah kategoriList menjadi array

            return kategori; // Mengembalikan array kategori
            
        } catch (SQLException e) { // Menangkap error SQLException
            System.out.println("Query failed: " + e.getMessage()); // Menampilkan error SQLException
        } 

        return new String[0]; // Mengembalikan string kosong jika terjadi kesalahan
    } 

    public static String[] getKategoriTanamUang(String tipeTanamUang) {
        return _kategoriTanamUang(tipeTanamUang);
    } 
}
