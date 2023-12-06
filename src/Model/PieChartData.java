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
    public static ObservableList<PieChart.Data> pieChartData() throws SQLException {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConnection();
        try {
            String query = "SELECT kategori_id, SUM(nominal) FROM transac WHERE tipe_transaksi = 'pengeluaran' GROUP BY kategori_id";
            // Mencegah sql injection
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                // Menjalankan query
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    // Mengambil kategori_id
                    int kategori_id = result.getInt("kategori_id");
                    // Menambahkan data ke dalam pieChartData
                    pieChartData.add(new PieChart.Data(getKategoriName(kategori_id),
                            result.getDouble(2) / totalpengeluaran() * 100));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return pieChartData;
    };

    private static String getKategoriName(int id) throws SQLException {
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
