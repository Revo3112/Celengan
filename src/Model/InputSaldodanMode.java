package Model;

import java.sql.Connection;

import Utils.DBConnection;

public class InputSaldodanMode {
    private int saldo;
    private int modeKritis;

    public InputSaldodanMode(int saldo, int modeKritis) {
        this.saldo = saldo;
        this.modeKritis = modeKritis;
    }

    public void setSaldo() {
        this.saldo = saldo;
        this.modeKritis = modeKritis;
        try {
            DBConnection dbConnection = new DBConnection();
            Connection connection = dbConnection.getConnection();
            String query = String.format("INSERT INTO `saldo` (`saldo`, `modeKritis`) VALUES ('%d', '%d')", saldo,
                    modeKritis);
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
    }
}
