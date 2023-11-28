package Model;

import java.sql.Connection;
import java.sql.Statement;

import Utils.DBConnection;

public class InputSaldodanMode {
    private int saldo;
    private int modeKritis;
    private int user_id;

    public InputSaldodanMode(int saldo, int modeKritis, int user_id) {
        this.saldo = saldo;
        this.modeKritis = modeKritis;
        this.user_id = user_id;
    }

    public boolean setSaldo() {
        try {
            DBConnection dbConnection = new DBConnection();
            Connection connection = dbConnection.getConnection();
            String query = String.format(
                    "UPDATE saldo SET balance = '%d', kritis = '%d' WHERE user_id = '%d'",
                    this.saldo, this.modeKritis, this.user_id);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            return true;
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
        return false;
    }
}
