package Model;

import Utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/*
 * Pembaharuan kelas RefreshViewDashboard
 */
public class RefreshViewDashboard {
    // Atribut
    public int userId;

    // Konstruktor
    public RefreshViewDashboard() {
        LoginModel loginModel = new LoginModel();
        this.userId = loginModel.getUserId();
    }

    /*
     * Mengambil total barang yang ada di database berdasarkan tipe transaksi
     */
    public int getTotalBarang(String tipeTransaksi) {
        try {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();

            String query = "SELECT COUNT(*) FROM transac WHERE user_id = ? and tipe_transaksi = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, this.userId);
                preparedStatement.setString(2, tipeTransaksi);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /*
     * Mendapatkan keterangan barang dari database berdasarkan tipe transaksi
     */
    public List<String> getKeteranganBarangCatatList(String tipeTransaksi) {
        List<String> keteranganTargetList = new ArrayList<>();
        try {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();

            // Gunakan PreparedStatement untuk melindungi dari SQL injection
            String query = "SELECT keterangan FROM transac WHERE user_id = ? and tipe_transaksi = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, this.userId);
                preparedStatement.setString(2, tipeTransaksi);

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String namaTargetResult = resultSet.getString("keterangan");
                    keteranganTargetList.add(namaTargetResult);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keteranganTargetList;
    }

    /*
     * Mendapatkan nominal barang dari database berdasarkan tipe transaksi
     */
    public List<Double> getNominalBarangList(String tipeTransaksi) {
        List<Double> nominalBarangList = new ArrayList<>();
        try {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();

            // Gunakan PreparedStatement untuk melindungi dari SQL injection
            String query = "SELECT nominal FROM transac WHERE user_id = ? and tipe_transaksi = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, this.userId);
                preparedStatement.setString(2, tipeTransaksi);

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    double nominalTarget = resultSet.getDouble("nominal");
                    nominalBarangList.add(nominalTarget);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nominalBarangList;
    }

    /*
     * Mendapatkan tanggal barang dari database berdasarkan tipe transaksi
     */
    public List<String> getTanggalBarangList(String tipeTransaksi) {
        List<String> tanggalBarangList = new ArrayList<>();
        try {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();

            // Gunakan PreparedStatement untuk melindungi dari SQL injection
            String query = "SELECT date FROM transac WHERE user_id = ? and tipe_transaksi = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, this.userId);
                preparedStatement.setString(2, tipeTransaksi);

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String tanggalTarget = resultSet.getString("date");
                    tanggalBarangList.add(tanggalTarget);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tanggalBarangList;
    }

}
