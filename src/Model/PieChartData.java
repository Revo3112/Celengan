package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Utils.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

/*
 * Kelas PieChartData untuk mengatur data pie chart
 */
public class PieChartData {
    // Atribut
    static LoginModel loginModel = new LoginModel();
    private static int user_id = loginModel.getUserId();

    /*
     * Mengambil data pie chart dari database
     */
    public static ObservableList<PieChart.Data> pieChartData() throws SQLException {
        // Membuat list data pie chart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        // Menghapus data sebelumnya
        pieChartData.clear();
        // Membuat koneksi ke database
        DBConnection dbConnection = new DBConnection();
        // Membuat koneksi
        try (Connection connection = dbConnection.getConnection()) {
            String query = "SELECT kategori_id, tipe_kategori, SUM(nominal) AS total_nominal FROM transac WHERE tipe_transaksi = 'pengeluaran' and user_id = ? GROUP BY kategori_id, tipe_kategori";

            // Mencegah sql injection
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                // Set user_id as a parameter
                statement.setInt(1, user_id);

                // Menjalankan query untuk mengambil data dari database yaitu kategori_id,
                // tipe_kategori, dan total_nominal
                try (ResultSet result = statement.executeQuery()) {
                    while (result.next()) {
                        int kategori_id = result.getInt("kategori_id");
                        int tipe_kategori = result.getInt("tipe_kategori");
                        double total_nominal_kategori = result.getDouble("total_nominal");
                        // Jika tipe kategori 0 maka kategori user
                        // Jika tipe kategori 1 maka kategori default
                        if (tipe_kategori == 0) {
                            // Menambahkan data ke pie chart data dengan nama kategori dan total nominal
                            // kategori dibagi total pengeluaran
                            pieChartData.add(new PieChart.Data(getKategoriNameUser(kategori_id),
                                    total_nominal_kategori / totalpengeluaran()));
                        } else {
                            // Menambahkan data ke pie chart data dengan nama kategori dan total nominal
                            // kategori dibagi total pengeluaran
                            pieChartData.add(new PieChart.Data(getKategoriNameByDefault(kategori_id),
                                    total_nominal_kategori / totalpengeluaran()));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            // Log or rethrow the exception
            e.printStackTrace();
            throw e;
        }
        return pieChartData;
    }

    /*
     * Mengambil nama kategori dari database berdasarkan id untuk kategori default
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
     * Mengambil nama kategori dari database berdasarkan id untuk kategori user
     */
    private static String getKategoriNameUser(int id) throws SQLException {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConnection();
        String query = "SELECT name FROM user_kategori WHERE id = ? and user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.setInt(2, user_id);
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
     * Mengambil total pengeluaran dari database
     */
    public static double totalpengeluaran() {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConnection();
        String query = "SELECT SUM(nominal) FROM transac where user_id = ? and tipe_transaksi = 'pengeluaran';";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user_id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getDouble(1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
