package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Utils.DBConnection;

public class BatasKritis {
    private static int userId;

    public BatasKritis(int userId) {
        this.userId = userId;
    }

    // Mengambil data batas kritis dari database
    public double getBatasKritis() {
        try {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();

            String query = "SELECT kritis FROM saldo WHERE user_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getDouble(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Mengubah data batas kritis di database
    public boolean setBatasKritis(double batasKritis) {
        try {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();

            String query = "UPDATE saldo SET kritis = ? WHERE user_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setDouble(1, batasKritis);
                preparedStatement.setInt(2, userId);
                preparedStatement.executeUpdate();
                if (preparedStatement.executeUpdate() > 0) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
