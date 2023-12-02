package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Utils.DBConnection;

public class TampilkanSemuaTarget {
    private int userId;

    public TampilkanSemuaTarget() {
        LoginModel loginModel = new LoginModel();
        this.userId = loginModel.getUserId();
    }

    public List<String> getNamaTargetList() {
        List<String> namaTargetList = new ArrayList<>();
        try {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();

            // Gunakan PreparedStatement untuk melindungi dari SQL injection
            String query = "SELECT nama_target FROM target WHERE user_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, this.userId);

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String namaTarget = resultSet.getString("nama_target");
                    namaTargetList.add(namaTarget);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return namaTargetList;
    }

    public List<Double> getNominalTargetList() {
        List<Double> nominalTargetList = new ArrayList<>();
        try {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();

            // Gunakan PreparedStatement untuk melindungi dari SQL injection
            String query = "SELECT harga_barang FROM target WHERE user_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, this.userId);

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    double nominalTarget = resultSet.getDouble("harga_barang");
                    nominalTargetList.add(nominalTarget);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nominalTargetList;
    }

    public List<String> getKeteranganBarangList() {
        List<String> keteranganBarangList = new ArrayList<>();
        try {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();

            // Gunakan PreparedStatement untuk melindungi dari SQL injection
            String query = "SELECT keterangan FROM target WHERE user_id = ?";
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

    public int mendapatkanBanyakDataDiTarget() {
        try {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();

            // Gunakan PreparedStatement untuk melindungi dari SQL injection
            String query = "SELECT COUNT(*) FROM target WHERE user_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, this.userId);

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
}
