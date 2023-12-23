package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;

import Utils.DBConnection;

/*
 * Kelas TambahTarget untuk menambahkan target
 */
public class TambahTarget {
    // Atribut
    private int userId;
    private String namaTarget;
    private double nominalTarget;
    private String keteranganBarang;

    /*
     * Konstruktor TambahTarget
     */
    public TambahTarget(int userId, String namaTarget, double nominalTarget, String keteranganBarang) {
        this.userId = userId;
        this.namaTarget = namaTarget;
        this.nominalTarget = nominalTarget;
        this.keteranganBarang = keteranganBarang;
    }

    /*
     * Menambahkan target ke database
     */
    public boolean start() {
        try {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();

            // Gunakan PreparedStatement untuk melindungi dari SQL injection
            String query = "INSERT INTO target (user_id, nama_target, harga_barang, keterangan) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, this.userId);
                preparedStatement.setString(2, this.namaTarget);
                preparedStatement.setDouble(3, this.nominalTarget);
                preparedStatement.setString(4, this.keteranganBarang);

                int rowsAffected = preparedStatement.executeUpdate();
                System.out.println("Rows affected: " + rowsAffected);
                // Periksa apakah baris berhasil dimasukkan
                if (rowsAffected > 0) {
                    System.out.println("Penambahan target berhasil");
                    return true;
                } else {
                    System.out.println("Penambahan target gagal");
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Ubah ini menjadi log atau tangani kesalahan dengan cara yang sesuai di
                                 // produksi
        }
        return false;
    }
}
