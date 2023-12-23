package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Utils.DBConnection;
import java.util.List;
import java.util.ArrayList;

/*
 * Kelas PantauPemasukanPengeluaran untuk mengatur pemasukan dan pengeluaran
 */
public class PantauPemasukanPengeluaran {
    // Atribut
    public int userId;

    /*
     * Konstruktor PantauPemasukanPengeluaran
     */
    public PantauPemasukanPengeluaran() {
        LoginModel loginModel = new LoginModel();
        this.userId = loginModel.getUserId();
    }

    /*
     * Mengambil jumlah banyak data di transac
     */
    public int banyakDatadiTransac() {
        try {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();

            // Gunakan PreparedStatement untuk melindungi dari SQL injection
            String query = "SELECT COUNT(*) FROM transac WHERE user_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, this.userId);

                /*
                 * Mengambil banyak data di transac dengan userId yang sesuai
                 * dan dilakukan loop untuk mengambil banyak data
                 */
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int banyakData = resultSet.getInt("COUNT(*)");
                    return banyakData;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Yang perlu diambil di database adalah Kategori, Keterangan, Nominal, Tipe,
    // jenis Transfer, Tanggal
    /*
     * Mengambil kategori barang dari database
     */
    public List<String> kategoriBarangList() {
        List<String> kategoriBarangList = new ArrayList<>();
        try {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();

            String query = "SELECT kategori_id, tipe_kategori FROM transac WHERE user_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, this.userId);

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    // Mengambil kategori_id dan tipe_kategori dari database
                    int kategori_id = resultSet.getInt("kategori_id");
                    int tipe_kategori = resultSet.getInt("tipe_kategori");
                    // Jika tipe_kategori = 0, maka kategoriBarangList akan mengambil kategori
                    // dari user_kategori
                    // Jika tipe_kategori = 1, maka kategoriBarangList akan mengambil kategori
                    // dari transac_kategori
                    if (tipe_kategori == 0) {
                        kategoriBarangList.add(getKategoriNameUser(kategori_id));
                    } else {
                        kategoriBarangList.add(getKategoriNameByDefault(kategori_id));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kategoriBarangList; // Corrected return statement
    }

    /*
     * Method untuk mengambil data nama dari transac_kategori yang merupakan
     * kategori default
     */
    private static String getKategoriNameByDefault(int id) throws SQLException {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConnection();
        String query = "SELECT name FROM transac_kategori WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getString(1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /*
     * Method untuk mengambil data nama dari user_kategori yang merupakan kategori
     * user
     */
    private String getKategoriNameUser(int id) throws SQLException {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConnection();
        String query = "SELECT name FROM user_kategori WHERE id = ? and user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.setInt(2, userId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getString(1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /*
     * Mengambil keterangan barang dari database
     */
    public List<String> getKeteranganBarangList() {
        List<String> keteranganBarangList = new ArrayList<>();
        try {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();

            // Gunakan PreparedStatement untuk melindungi dari SQL injection
            String query = "SELECT keterangan FROM transac WHERE user_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, this.userId);

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String keteranganBarang = resultSet.getString("keterangan");
                    keteranganBarangList.add(keteranganBarang);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keteranganBarangList;

    }

    /*
     * Mengambil nominal barang dari database
     */
    public List<Double> getNominalBarangList() {
        List<Double> nominalBarangList = new ArrayList<>();
        try {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();

            // Gunakan PreparedStatement untuk melindungi dari SQL injection
            String query = "SELECT nominal FROM transac WHERE user_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, this.userId);

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    double nominalBarang = resultSet.getDouble("nominal");
                    nominalBarangList.add(nominalBarang);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nominalBarangList;
    }

    /*
     * Mengambil tipe barang dari database
     */
    public List<String> getTipeBarangList() {
        List<String> tipeBarangList = new ArrayList<>();
        try {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();

            // Gunakan PreparedStatement untuk melindungi dari SQL injection
            String query = "SELECT tipe_transaksi FROM transac WHERE user_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, this.userId);

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String tipeBarang = resultSet.getString("tipe_transaksi");
                    tipeBarangList.add(tipeBarang);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tipeBarangList;
    }

    /*
     * Mengambil Tanggal barang dari database
     */
    public List<String> getTanggalBarangList() {
        List<String> tanggalBarangList = new ArrayList<>();
        try {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();

            // Gunakan PreparedStatement untuk melindungi dari SQL injection
            String query = "SELECT date FROM transac WHERE user_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, this.userId);

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String tanggalBarang = resultSet.getString("date");
                    tanggalBarangList.add(tanggalBarang);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tanggalBarangList;
    }

    /*
     * Mengambil Tipe Pembayaran barang dari database
     */
    public List<String> listTipePembayaran() {
        List<String> tipePembayaranList = new ArrayList<>();
        try {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();

            // Gunakan PreparedStatement untuk melindungi dari SQL injection
            String query = "SELECT tipe_pembayaran FROM transac WHERE user_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, this.userId);

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String tipePembayaran = resultSet.getString("tipe_pembayaran");
                    tipePembayaranList.add(tipePembayaran);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tipePembayaranList;
    }

    /*
     * Khusus dengan kategori
     */

    /*
     * Mengambil banyak data di transac dengan kategori tertentu
     */
    public int banyakDatadiTransacDenganKategori(String kategori) {
        try {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();

            // Gunakan PreparedStatement untuk melindungi dari SQL injection
            String query = "SELECT COUNT(*) FROM transac WHERE user_id = ? and tipe_transaksi = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, this.userId);
                preparedStatement.setString(2, kategori);

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int banyakData = resultSet.getInt("COUNT(*)");
                    return banyakData;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /*
     * Mengambil keterangan barang dari database dengan kategori tertentu
     */
    public List<String> getKeteranganBarangListDenganTipeTransaksi(String kategori) {
        List<String> keteranganBarangList = new ArrayList<>();
        try {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();

            // Gunakan PreparedStatement untuk melindungi dari SQL injection
            String query = "SELECT keterangan FROM transac WHERE user_id = ? and tipe_transaksi = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, this.userId);
                preparedStatement.setString(2, kategori);

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String keteranganBarang = resultSet.getString("keterangan");
                    keteranganBarangList.add(keteranganBarang);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keteranganBarangList;

    }

    /*
     * Mengambil nominal barang dari database dengan kategori tertentu
     */
    public List<Double> getNominalBarangListDenganTipeTransaksi(String kategori) {
        List<Double> nominalBarangList = new ArrayList<>();
        try {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();

            // Gunakan PreparedStatement untuk melindungi dari SQL injection
            String query = "SELECT nominal FROM transac WHERE user_id = ? and tipe_transaksi = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, this.userId);
                preparedStatement.setString(2, kategori);

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    double nominalBarang = resultSet.getDouble("nominal");
                    nominalBarangList.add(nominalBarang);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nominalBarangList;
    }

    /*
     * Mengambil kategori barang dari database dengan kategori tertentu
     */
    public List<String> kategoriBarangListDenganTipeTransaksi(String kategori) {
        List<String> kategoriBarangList = new ArrayList<>();
        try {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();

            String query = "SELECT kategori_id, tipe_kategori FROM transac WHERE user_id = ? and tipe_transaksi = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, this.userId);
                preparedStatement.setString(2, kategori);

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    // Mengambil kategori_id dan tipe_kategori dari database
                    int kategori_id = resultSet.getInt("kategori_id");
                    int tipe_kategori = resultSet.getInt("tipe_kategori");
                    // Jika tipe_kategori = 0, maka kategoriBarangList akan mengambil kategori
                    // dari user_kategori
                    // Jika tipe_kategori = 1, maka kategoriBarangList akan mengambil kategori
                    // dari transac_kategori
                    if (tipe_kategori == 0) {
                        kategoriBarangList.add(getKategoriNameUser(kategori_id));
                    } else {
                        kategoriBarangList.add(getKategoriNameByDefault(kategori_id));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kategoriBarangList; // Corrected return statement
    }

    /*
     * Mengambil Tanggal barang dari database dengan kategori tertentu
     */
    public List<String> getTanggalBarangListDenganTipeTransaksi(String kategori) {
        List<String> tanggalBarangList = new ArrayList<>();
        try {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();

            // Gunakan PreparedStatement untuk melindungi dari SQL injection
            String query = "SELECT date FROM transac WHERE user_id = ? and tipe_transaksi = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, this.userId);
                preparedStatement.setString(2, kategori);

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String tanggalBarang = resultSet.getString("date");
                    tanggalBarangList.add(tanggalBarang);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tanggalBarangList;
    }

    /*
     * Mengambil Tipe Pembayaran barang dari database dengan kategori tertentu
     */
    public List<String> getTipePembayaranListDenganTipeTransaksi(String kategori) {
        List<String> tipePembayaranList = new ArrayList<>();
        try {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();

            // Gunakan PreparedStatement untuk melindungi dari SQL injection
            String query = "SELECT tipe_pembayaran FROM transac WHERE user_id = ? and tipe_transaksi = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, this.userId);
                preparedStatement.setString(2, kategori);

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String tipePembayaran = resultSet.getString("tipe_pembayaran");
                    tipePembayaranList.add(tipePembayaran);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tipePembayaranList;
    }

}
