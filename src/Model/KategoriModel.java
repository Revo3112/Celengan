package Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import Utils.DBConnection;

public class KategoriModel {
    
    public ResultSet userKategori() {
        DBConnection db = DBConnection.getDatabaseConnection();
        Connection connection = db.getConnection();
    
        try {
            String query = "SELECT * FROM transac_kategori AS t";
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        } 
    } 
}
