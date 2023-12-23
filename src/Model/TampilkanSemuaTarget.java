package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Utils.DBConnection;

/*
 * Class TampilkanSemuaTarget untuk menampilkan semua target
 */
public class TampilkanSemuaTarget {
    // Atribut
    private int userId;

    /*
     * Konstruktor TampilkanSemuaTarget
     */
    public TampilkanSemuaTarget() {
        LoginModel loginModel = new LoginModel();
        this.userId = loginModel.getUserId();
    }

    /*
     * Mengambil nama target dari database
     */
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

    /*
     * Mengambil nominal target dari database
     */
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

    /*
     * Mengambil keterangan barang dari database
     */
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

    /*
     * Mengambil banyak data di target dari database
     */
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

    /*
     * Mengambil harga target dari database
     */
    public double mendapatkanHargaTarget(String namaTarget) {
        double progres = 0;
        try {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();

            // Gunakan PreparedStatement untuk melindungi dari SQL injection
            String query = "SELECT harga_barang FROM target join saldo WHERE nama_target = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, namaTarget);

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    double totalTarget = resultSet.getDouble("harga_barang");
                    progres = ambilSaldodanBatasKritis() / totalTarget;
                    if (progres < 0) {
                        progres = 0;
                    } else if (progres > 1) {
                        progres = 1;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return progres;
    }

    /*
     * Mengambil saldo dan batas kritis dari database
     */
    public long ambilSaldodanBatasKritis() {
        long TotalDuitUntukProgress = 0;
        long saldo = 0;
        long kritis = 0;
        try {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();

            String query = "SELECT balance, kritis FROM saldo WHERE user_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, this.userId);

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    saldo = resultSet.getLong("balance");
                    kritis = resultSet.getLong("kritis");
                    TotalDuitUntukProgress = saldo - 2 * kritis;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TotalDuitUntukProgress;
    }

    /*
     * Mengambil nama target pertama dari database
     */
    public String ambilDataNamaTargetPertama(int user_id) {
        String namaTarget = "";
        try {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();

            String query = "SELECT nama_target FROM target WHERE user_id = ? LIMIT 1";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, user_id);

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    namaTarget = resultSet.getString("nama_target");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return namaTarget;
    }

    /*
     * Mengambil harga target pertama dari database
     */
    public double ambilHargaDataPertama(int user_id) {
        double hargaTarget = 0;
        try {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();

            String query = "SELECT harga_barang FROM target WHERE user_id = ? LIMIT 1";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, user_id);

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    hargaTarget = resultSet.getDouble("harga_barang");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return hargaTarget;
    }
}
