// package Model;

// import java.sql.*;

// // Class Register digunakan untuk menyimpan data user yang akan melakukan
// registrasi
// class Register {
// private String user; // Deklarasi property user
// private String password; // Deklarasi property password

// // Getter
// public String get_user() {
// return user; // Mengembalikan nilai dari property user
// }

// public String get_password() {
// return password; // Mengembalikan nilai dari property password
// }

// // Setter
// public void set_user(String user) {
// this.user = user; // Instansiasi property user dengan parameter user
// }

// public void set_password(String password) {
// this.password = password; // Instansiasi property password dengan parameter
// password
// }
// }

// // Class RegisterDatabase digunakan untuk menyimpan data user yang akan
// // melakukan registrasi ke dalam database
// public class RegisterDatabase {
// public void sql_register() {
// // Mengatur koneksi ke database
// Connection connection = null; // Deklarasi variabel connection
// try {
// // Mencoba mengkoneksikan ke database celengan dengan username root dan
// password
// // kosong (tidak ada password)
// connection =
// DriverManager.getConnection("jdbc:mysql://localhost:3306/celengan", "root",
// "");
// } catch (SQLException e) { // Menangkap error jika gagal mengkoneksikan ke
// database
// // Menampilkan pesan error ke console
// System.out.println("Connection failed: " + e.getMessage());
// } finally { // Menjalankan kode di dalam finally setelah try-catch selesai
// dijalankan
// // Menjalankan kode di dalam if setelah try-catch selesai dijalankan
// if (connection != null) {
// // Query untuk menampilkan semua data dari tabel users
// String sql = "Select * from users";
// try {
// java.sql.Statement statement = connection.createStatement(); // Membuat objek
// statement
// // Menjalankan query dan menyimpan hasilnya ke dalam variabel result
// ResultSet result = statement.executeQuery(sql);
// System.out.println("Connection closed." + result); // Menampilkan pesan ke
// console
// } catch (SQLException e) { // Menangkap error jika gagal mengeksekusi query
// // Menampilkan pesan error ke console
// System.out.println("Failed to execute query: " + e.getMessage());
// } finally { // Menjalankan kode di dalam finally setelah try-catch selesai
// dijalankan
// // Menjalankan kode di dalam if setelah try-catch selesai dijalankan
// if (connection != null) { // Jika variabel connection tidak kosong
// try {
// connection.close(); // Menutup koneksi ke database
// } catch (SQLException e) {
// // Menampilkan pesan error ke console
// System.out.println("Failed to close connection: " + e.getMessage());
// }
// }
// }

// }
// }
// }
// }
