package Model;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import java.util.ArrayList;

import Utils.DBConnection;

public class TanamUangModel {

    // Fungsi untuk menyimpan catatan keuangan user
    public static boolean simpanTanamUang(String tanggal, String kategori, int kategoriId, double jumlah, double saldo,
            String tipePembayaran, String keterangan, String tipeTU, boolean isDefault) {

        // Memubat koneksi ke database
        DBConnection dbc = DBConnection.getDatabaseConnection();
        Connection connection = dbc.getConnection();
        String tipeTanamUang = tipeTU.toLowerCase();
        int tipeKategori = 0;

        LoginModel user = new LoginModel();
        int userId = user.getUserId();
        try {

            // Cek apakah kategori yang dipilih kategori default atau tidak
            if (isDefault) {
                tipeKategori = 1;
            }

            // Menambah atau mengurangi saldo
            if (tipeTanamUang.equals("pengeluaran")) {
                saldo = saldo - jumlah;
            } else if (tipeTanamUang.equals("pemasukan")) {
                saldo = saldo + jumlah;
            }

            // Menambah data ke table transac
            String sqlInsert = String.format(
                    "INSERT INTO transac(user_id, nominal, keterangan, kategori_id, tipe_kategori, tipe_pembayaran, date, tipe_transaksi) VALUES(%d, %f, '%s', %d, %d, '%s', '%s', '%s')",
                    userId, jumlah, keterangan, kategoriId, tipeKategori, tipePembayaran, tanggal, tipeTanamUang);
            // Mengupdate saldo
            String sqlUpdate = String.format("UPDATE saldo SET balance=%f WHERE user_id=%d", saldo, userId);

            // Eksekusi sql statement
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlInsert);
            statement.executeUpdate(sqlUpdate);

            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    // Fungsi untuk menyimpan nama kategori
    public static boolean simpanNamaKategori(String namaKategoriNew, String namaKategoriOld, boolean kategoriDefault,
            String tipeKategori, int idKategoriDefault) {

        // Membuat koneksi ke database
        DBConnection dbc = DBConnection.getDatabaseConnection();
        Connection connection = dbc.getConnection();
        String sql = "", sql_2 = "";
        LoginModel loginModel = new LoginModel();
        int userId = loginModel.getUserId();

        // Cek apakah kategori default
        if (kategoriDefault) {
            // Menambah data ke table user_kategori
            sql = String.format(
                    "INSERT INTO user_kategori(user_id, name, tipe, transac_kategori_id) VALUES(%d, '%s', '%s', %d)",
                    userId, namaKategoriNew, tipeKategori, idKategoriDefault);
            sql_2 = String.format("INSERT INTO hapus_kategori(user_id, kategori_id) VALUES(%d, %d)", userId,
                    getIdKategoriDefault(namaKategoriOld));
        } else {
            // Cek apakah nama kategori yang disimpan merupakan kategori default
            if (_isKategoriDefault(namaKategoriNew)) {
                sql = String.format("DELETE FROM user_kategori WHERE id=%d", getIdKategoriUser(namaKategoriOld));
                sql_2 = String.format("DELETE FROM hapus_kategori WHERE kategori_id=%d",
                        getIdKategoriDefault(namaKategoriNew));
            } else {
                sql = String.format("UPDATE user_kategori SET name='%s' WHERE id=%d", namaKategoriNew,
                        getIdKategoriUser(namaKategoriOld));
            }
            System.out.println(namaKategoriNew);
            System.out.println("Kategori ID: " + getIdKategoriUser(namaKategoriOld));
        }

        // Eksekusi sql statement
        try {
            PreparedStatement updateStatement = connection.prepareStatement(sql);
            if (!sql_2.equals("")) {
                PreparedStatement insertStatement = connection.prepareStatement(sql_2);
                insertStatement.executeUpdate();
            }

            updateStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Fungsi untuk mengecek apakah kategori yang dipilih merupakan kategori default
    private static boolean _isKategoriDefault(String namaKategori) {
        String[] listKategoriDefault = getKategoriDefault();
        for (int i = 0; i < listKategoriDefault.length; i++) {
            if (listKategoriDefault[i].equals(namaKategori)) {
                return true;
            }
        }
        return false;
    }

    public static boolean getIsKategoriDefault(String namaKategori) {
        return _isKategoriDefault(namaKategori);
    }

    // Fungsi untuk mendapatkan kategori default
    private static int _idKategoriDefault(String namaKategori) {
        // Membuat koneksi ke database
        DBConnection dbc = DBConnection.getDatabaseConnection();
        Connection connection = dbc.getConnection();

        try {
            // Mengambil id dari table transac_kategori
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

    // Fungsi untuk mengambil id kategori user
    private static int _idKategoriUser(String namaKategori) {
        // Membuat koneksi ke database
        DBConnection dbc = DBConnection.getDatabaseConnection();
        Connection connection = dbc.getConnection();

        try {
            // Mengambil id dari table user_kategori
            String sql = String.format("SELECT id FROM user_kategori WHERE name='%s'", namaKategori);

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            result.next();
            return result.getInt("id");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return 0;
    }

    public static int getIdKategoriUser(String namaKategori) {
        return _idKategoriUser(namaKategori);
    }

    // Fungsi untuk mendapatkan semua kategori default
    private static String[] getKategoriDefault() {
        // Membuat koneksi ke database
        DBConnection dbc = DBConnection.getDatabaseConnection();
        Connection connection = dbc.getConnection();
        List<String> listKategoriDefault = new ArrayList<>();

        try {
            // Mengambil semua data dari table transac_kategori
            String sql = String.format("SELECT * FROM transac_kategori");

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                listKategoriDefault.add(result.getString("name"));
            }

            String[] kategori = listKategoriDefault.toArray(new String[0]);

            return kategori;

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return new String[0];
    }

    // Fungsi untuk mengambil semua kategori berdasarkan tipe tanam uang (pemasukan
    // / pengeluaran)
    private static String[] _kategoriTanamUang(String tipeTU) {
        DBConnection dbc = DBConnection.getDatabaseConnection(); // Deklarasi dan inisialisasi variabel dbc
                                                                 // dengan nilai dari method getDatabaseConnection().
                                                                 // Berguna untuk mendapat koneksi ke database
        Connection connection = dbc.getConnection(); // Inisialisasi variabel connection dengan method getConnection()
                                                     // dari object dbc

        // Membuat kategori list
        List<String> kategoriList = new ArrayList<>();

        // Membuat kategori list sementara untuk menampung kategori yang belum difilter
        // (yang dihapus oleh user)
        List<String> kategoriListTemp = new ArrayList<>();

        // Membuat array kategori hapus
        String[] arrKategoriHapus = getKategoriHapus();

        // Membuat list kategori default dan kategori user
        List<String> listKategoriDefault = new ArrayList<>();
        List<String> listKategoriUser = new ArrayList<>();
        List<String> listKategoriUserNonDefault = new ArrayList<>();

        // Membuat list id dari kategori default dan kategori default yang sudah dihapus
        // oleh user
        List<Integer> listIdKategoriDefault = new ArrayList<>();
        List<Integer> listIdKategoriUser = new ArrayList<>();

        String tipeTanamUang = tipeTU.toLowerCase();

        LoginModel user = new LoginModel();
        int userId = user.getUserId();

        try {
            // Mengambil data kategori default yang tidak ada di table hapus_kategori
            String sql_1 = String.format("SELECT * FROM transac_kategori AS tk WHERE tk.tipe = '%s'", tipeTanamUang);

            // Mengambil kategori user (Konteks: ketika user ingin mengubah kategori
            // default, maka id dari kategori default harus disimpan)
            String sql_2 = String.format(
                    "SELECT uk.name, uk.transac_kategori_id FROM user_kategori AS uk WHERE user_id=%d AND tipe='%s' AND transac_kategori_id != 0;",
                    userId, tipeTanamUang);
            System.out.println(userId);

            // Mengambil semua kategori user (bukan default, kategori custom dari user)
            String sql_3 = String.format(
                    "SELECT name FROM user_kategori WHERE user_id=%d AND transac_kategori_id = 0 AND tipe='%s'",
                    userId, tipeTanamUang);

            Statement statement_1 = connection.createStatement(); // Membuat statement dari method createStatement()
            ResultSet resultKategori = statement_1.executeQuery(sql_1); // Execute query sql menggunakan method
                                                                        // executeQuery dan dimasukkan ke dalam variabel
                                                                        // result

            Statement statement_2 = connection.createStatement(); // Membuat statement dari method createStatement()
            ResultSet resultUserKategori = statement_2.executeQuery(sql_2); // Execute query sql menggunakan method
                                                                            // executeQuery dan dimasukkan ke dalam
                                                                            // variabel result

            Statement statement_3 = connection.createStatement(); // Membuat statement dari method createStatement()
            ResultSet resultUserKategoriNonDefault = statement_3.executeQuery(sql_3); // Execute query sql menggunakan
                                                                                      // method executeQuery dan
                                                                                      // dimasukkan ke dalam
                                                                                      // variabel result

            // Mengambil data dari ResultSet
            System.out.println("HAPUS KATEGORI:");
            while (resultKategori.next()) {
                listKategoriDefault.add(resultKategori.getString("name"));
                System.out.println(resultKategori.getString("name"));
                listIdKategoriDefault.add(resultKategori.getInt("id"));
            }
            while (resultUserKategori.next()) {
                listKategoriUser.add(resultUserKategori.getString("name"));
                listIdKategoriUser.add(resultUserKategori.getInt("transac_kategori_id"));
            }
            while (resultUserKategoriNonDefault.next()) {
                listKategoriUserNonDefault.add(resultUserKategoriNonDefault.getString("name"));
            }

            // Memasukkan list ke dalam array
            String[] arrKategoriDefault = listKategoriDefault.toArray(new String[0]);
            String[] arrKategoriUser = listKategoriUser.toArray(new String[0]);
            String[] arrKategoriUserNonDefault = listKategoriUserNonDefault.toArray(new String[0]);
            Integer[] arrIdKategoriDefault = listIdKategoriDefault.toArray(new Integer[0]);
            Integer[] arrIdKategoriUser = listIdKategoriUser.toArray(new Integer[0]);
            // Integer[] arrIdKategoriHapus = listIdKategoriHapus.toArray(new Integer[0]);

            boolean isDefault;

            // Mengganti kategori default dengan kategori user (jika ada)
            for (int i = 0; i < arrKategoriDefault.length; i++) {
                isDefault = false;
                for (int j = 0; j < arrKategoriUser.length; j++) {
                    // Cek apakah ada kategori default yang diganti namanya oleh user.
                    // Jika ada, masukkan ke dalam list kategori untuk mengganti urutan dari
                    // kategori default
                    if (arrIdKategoriDefault[i] == arrIdKategoriUser[j]) {
                        kategoriListTemp.add(arrKategoriUser[j]);
                        isDefault = true;
                    }
                }

                // Jika tidak ada kategori default yang diganti manya oleh user, masukkan
                // kategori default ke dalam list kategori
                if (!isDefault) {
                    kategoriListTemp.add(arrKategoriDefault[i]);
                }
            }

            // Kategori sementara sebelum difilter mana yang dihapus oleh user
            String[] kategoriTemp = kategoriListTemp.toArray(new String[0]); // Mengubah kategoriList menjadi array

            // Filter kategori default yang sudah dihapus oleh user
            boolean isHapus;
            for (int i = 0; i < kategoriTemp.length; i++) {
                isHapus = false;
                for (int j = 0; j < arrKategoriHapus.length; j++) {
                    if (kategoriTemp[i].equals(arrKategoriHapus[j])) {
                        isHapus = true;
                        break;
                    }
                }

                if (!isHapus) {
                    kategoriList.add(kategoriTemp[i]);
                }
            }

            // Memasukkan semua kategori non default ke dalam list kategori
            for (int i = 0; i < arrKategoriUserNonDefault.length; i++) {
                kategoriList.add(arrKategoriUserNonDefault[i]);
            }

            String[] kategori = kategoriList.toArray(new String[0]);

            return kategori; // Mengembalikan array kategori

        } catch (SQLException e) { // Menangkap error SQLException
            System.out.println("Query failed: " + e.getMessage()); // Menampilkan error SQLException
        }

        return new String[0]; // Mengembalikan string kosong jika terjadi kesalahan
    }

    public static String[] getKategoriTanamUang(String tipeTanamUang) {
        return _kategoriTanamUang(tipeTanamUang);
    }

    // Fungsi untuk mengambil data kategori default yang telah dihapus oleh user
    private static String[] getKategoriHapus() {
        // Membuat koneksi ke database
        DBConnection dbc = DBConnection.getDatabaseConnection();
        Connection connection = dbc.getConnection();

        // Mengambil id user
        LoginModel user = new LoginModel();
        int userId = user.getUserId();
        List<String> listKategoriHapus = new ArrayList<>();

        try {
            // Mengambil data kategori default yang telah dihapus user
            String sql = String.format(
                    "SELECT tk.name FROM hapus_kategori AS kh JOIN transac_kategori as tk ON (kh.kategori_id = tk.id) WHERE kh.user_id=%d;",
                    userId);
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                listKategoriHapus.add(result.getString("name"));
            }

            String[] arrKategoriHapus = listKategoriHapus.toArray(new String[0]);

            return arrKategoriHapus;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return new String[0];
    }

    // Fungsi untuk menghapus kategori
    private static boolean _hapusKategori(String namaKategori, boolean isDefault) {
        DBConnection dbc = DBConnection.getDatabaseConnection(); // Deklarasi dan inisialisasi variabel dbc
                                                                 // dengan nilai dari method getDatabaseConnection().
                                                                 // Berguna untuk mendapat koneksi ke database
        Connection connection = dbc.getConnection(); // Inisialisasi variabel connection dengan method getConnection()
                                                     // dari object dbc }

        LoginModel user = new LoginModel();
        int userId = user.getUserId();
        String sql = "";
        System.out.println("Id Kategori: " + getIdKategoriUser(namaKategori));

        try {
            // Cek apakah merupakan kategori default atau tidak
            if (isDefault) {
                // Jika kategori default, masukkan ke dalam table hapus_kategori
                sql = String.format("INSERT INTO hapus_kategori(user_id, kategori_id) VALUES(%d, %d)", userId,
                        getIdKategoriDefault(namaKategori));
            } else {
                // Jika kategori custom, langsung hapus dari table user_kategori
                sql = String.format("DELETE FROM user_kategori WHERE id=%d", getIdKategoriUser(namaKategori));
            }
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return false;
    }

    public static boolean hapusKategori(String namaKategori, boolean isDefault) {
        return _hapusKategori(namaKategori, isDefault);
    }

    // Fungsi untuk menambah kategori
    private static boolean _tambahKategori(String namaKategori, String tipeTU) {
        DBConnection dbc = DBConnection.getDatabaseConnection(); // Deklarasi dan inisialisasi variabel dbc
                                                                 // dengan nilai dari method getDatabaseConnection().
                                                                 // Berguna untuk mendapat koneksi ke database
        Connection connection = dbc.getConnection(); // Inisialisasi variabel connection dengan method getConnection()
                                                     // dari object dbc }

        LoginModel user = new LoginModel();
        int userId = user.getUserId();

        String tipeTanamUang = tipeTU.toLowerCase();

        String sql = "";
        String sql2 = "";
        try {
            // Menambah kategori ke table user_kategori
            if (!_isKategoriDefault(namaKategori.replace(" ", ""))) {
                sql = String.format(
                        "INSERT INTO user_kategori(user_id, name, tipe, transac_kategori_id) VALUES(%d, '%s', '%s', 0)",
                        userId, namaKategori, tipeTanamUang);
            } else {
                sql = String.format(
                        "DELETE FROM hapus_kategori WHERE user_id=%d AND kategori_id=%d;",
                        userId, getIdKategoriDefault(namaKategori));
                sql2 = String.format(
                        "UPDATE user_kategori SET transac_kategori_id = 0 where user_id=%d AND transac_kategori_id = %d;",
                        userId, getIdKategoriDefault(namaKategori));
            }
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            if (!sql2.equals("")) {
                statement.executeUpdate(sql2);
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return false;
    }

    public static boolean tambahKategori(String namaKategori, String tipeTU) {
        return _tambahKategori(namaKategori, tipeTU);
    }
}