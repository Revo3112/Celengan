package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;

import Utils.DBConnection;

public class HapusTarget {
    private String nama_target;

    public HapusTarget(String nama_target) {
        this.nama_target = nama_target;
    }

    public boolean start() {
        try {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();

            // Gunakan PreparedStatement untuk melindungi dari SQL injection
            String query = "DELETE FROM target WHERE nama_target = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, this.nama_target);

                int rowsAffected = preparedStatement.executeUpdate();
                System.out.println("Rows affected: " + rowsAffected);
                // Periksa apakah baris berhasil dimasukkan
                if (rowsAffected > 0) {
                    System.out.println("Hapus target berhasil");
                    return true;
                } else {
                    System.out.println("Hapus target gagal");
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Ubah ini menjadi log atau tangani kesalahan dengan cara yang sesuai di
                                 // produksi
        }
        return false;
    }
}
