package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Utils.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

public class PieChartData {
    private static int user_id;

    public PieChartData(int user_id) {
        this.user_id = user_id;
    }

    public static ObservableList<PieChart.Data> pieChartData() throws SQLException {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConnection();
        try {
            String query = "SELECT kategori_id, tipe_kategori, SUM(nominal) AS total_nominal FROM transac WHERE tipe_transaksi = 'pengeluaran' and user_id = ? GROUP BY kategori_id, tipe_kategori";

            // Mencegah sql injection
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                // Set user_id as a parameter
                statement.setInt(1, user_id); // Replace 'yourUserId' with the actual user ID

                // Menjalankan query
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    int kategori_id = result.getInt("kategori_id");
                    int tipe_kategori = result.getInt("tipe_kategori");
                    double total_nominal = result.getDouble("total_nominal");

                    if (tipe_kategori == 0) {
                        pieChartData.add(new PieChart.Data(getKategoriNameUser(kategori_id),
                                total_nominal / totalpengeluaran() * 100));
                    } else {
                        pieChartData.add(new PieChart.Data(getKategoriNameByDefault(kategori_id),
                                total_nominal / totalpengeluaran() * 100));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return pieChartData;
    };

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

    private static String getKategoriNameUser(int id) throws SQLException {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConnection();
        String query = "SELECT name FROM user_kategori WHERE transac_kategori_id = ? and user_id = ?";
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

    private static double totalpengeluaran() {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConnection();
        String query = "SELECT SUM(nominal) FROM transac group by tipe_transaksi = 'pengeluaran'";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
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
