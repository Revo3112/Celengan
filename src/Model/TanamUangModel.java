    package Model;

    import java.time.LocalDate;

    import java.sql.Statement;
    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.util.List;
    import java.util.ArrayList;

    import Utils.DBConnection;
    import javafx.scene.control.DatePicker;

    public class TanamUangModel {

        public static boolean simpanTanamUang(String tanggal, String kategori, int kategoriId, double jumlah, double saldo,
                String tipePembayaran, String keterangan, String tipeTU, boolean isDefault) {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();
            String tipeTanamUang = tipeTU.toLowerCase();
            int tipeKategori = 0;

            LoginModel user = new LoginModel();
            int userId = user.getUserId();
            try {

                if (isDefault) {
                    tipeKategori = 1;
                }

                if (tipeTanamUang.equals("pengeluaran")) {
                    saldo = saldo - jumlah;
                } else if (tipeTanamUang.equals("pemasukan")) {
                    saldo = saldo + jumlah;
                }

                String sqlInsert = String.format(
                        "INSERT INTO transac(user_id, nominal, keterangan, kategori_id, tipe_kategori, tipe_pembayaran, date, tipe_transaksi) VALUES(%d, %f, '%s', %d, %d, '%s', '%s', '%s')",
                        userId, jumlah, keterangan, kategoriId, tipeKategori, tipePembayaran, tanggal, tipeTanamUang);
                String sqlUpdate = String.format("UPDATE saldo SET balance=%f WHERE user_id=%d", saldo, userId);

                Statement statement = connection.createStatement();
                statement.executeUpdate(sqlInsert);
                statement.executeUpdate(sqlUpdate);

                return true;
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
            return false;
        }

        public static boolean simpanNamaKategori(String namaKategoriNew, String namaKategoriOld, boolean kategoriDefault,
                String tipeKategori, int idKategoriDefault) {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();
            String sql = "";
            LoginModel loginModel = new LoginModel();
            int userId = loginModel.getUserId();

            if (kategoriDefault) {
                sql = String.format(
                        "INSERT INTO user_kategori(user_id, name, tipe, transac_kategori_id) VALUES(%d, '%s', '%s', %d)",
                        userId, namaKategoriNew, tipeKategori, idKategoriDefault);
            } else {
                if (isKategoriDefault(namaKategoriNew)) {
                    sql = String.format("DELETE FROM user_kategori WHERE id=%d", getIdKategoriUser(namaKategoriOld));
                } else {
                    sql = String.format("UPDATE user_kategori SET name='%s' WHERE id=%d", namaKategoriNew,
                            getIdKategoriUser(namaKategoriOld));
                }
                System.out.println("UPDATEEEEEEE!!");
                System.out.println(namaKategoriNew);
                System.out.println("Kategori ID: " + getIdKategoriUser(namaKategoriOld));
            }

            try (PreparedStatement updateStatement = connection.prepareStatement(sql)) {
                updateStatement.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }

        private static boolean isKategoriDefault(String namaKategori) {
            String[] listKategoriDefault = getKategoriDefault();
            for (int i = 0; i < listKategoriDefault.length; i++) {
                if (listKategoriDefault[i].equals(namaKategori)) {
                    return true;
                }
            }
            return false;
        }

        public static boolean getIsKategoriDefault(String namaKategori) {
            return isKategoriDefault(namaKategori);
        }

        private static int _idKategoriDefault(String namaKategori) {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();

            try {
                String sql = String.format("SELECT id FROM transac_kategori WHERE name='%s'", namaKategori);

                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(sql);

                result.next();
                return result.getInt(1);

            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
            return 0;
        }

        public static int getIdKategoriDefault(String namaKategori) {
            return _idKategoriDefault(namaKategori);
        }

        private static int _idKategoriUser(String namaKategori) {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();

            try {
                String sql = String.format("SELECT id FROM user_kategori WHERE name='%s'", namaKategori);

                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(sql);

                result.next();
                return result.getInt("id");

            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
            return 0;
        }

        public static int getIdKategoriUser(String namaKategori) {
            return _idKategoriUser(namaKategori);
        }

        private static String[] getKategoriDefault() {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();
            List<String> listKategoriDefault = new ArrayList<>();

            try {
                String sql = String.format("SELECT * FROM transac_kategori");

                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(sql);

                while (result.next()) {
                    listKategoriDefault.add(result.getString("name"));
                }

                String[] kategori = listKategoriDefault.toArray(new String[0]);

                return kategori;

            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }

            return new String[0];
        }

        public int getJumlahKategoriPemasukan() {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();

            try {
                String sql = String.format("SELECT COUNT(*) FROM transac_kategori WHERE tipe='pemasukan'");
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(sql);
                result.next();

                return result.getInt(1);
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }

            return 0;
        }

        private static String[] _kategoriTanamUang(String tipeTU) {
            DBConnection dbc = DBConnection.getDatabaseConnection(); // Deklarasi dan inisialisasi variabel dbc
                                                                    // dengan nilai dari method getDatabaseConnection().
                                                                    // Berguna untuk mendapat koneksi ke database
            Connection connection = dbc.getConnection(); // Inisialisasi variabel connection dengan method getConnection()
                                                        // dari object dbc

            List<String> kategoriListTemp = new ArrayList<>();
            List<String> kategoriList = new ArrayList<>();

            List<String> listKategoriDefault = new ArrayList<>();
            List<Integer> listIdKategoriDefault = new ArrayList<>();
            List<String> listKategoriUser = new ArrayList<>();
            List<Integer> listIdKategoriUser = new ArrayList<>();
            List<String> listKategoriUserNonDefault = new ArrayList<>();

            String tipeTanamUang = tipeTU.toLowerCase();
            String[] arrKategoriHapus = getKategoriHapus();

            LoginModel user = new LoginModel();
            int userId = user.getUserId();

            try {
                String sql_1 = String.format(
                        "SELECT tk.* " +
                                "FROM transac_kategori AS tk " +
                                "WHERE NOT EXISTS ( " +
                                "SELECT 1 " +
                                "FROM hapus_kategori AS kh " +
                                "WHERE tk.id = kh.id) " +
                                "AND tk.tipe = '%s';",
                        tipeTanamUang);

                String sql_2 = String.format(
                        "SELECT uk.name, uk.transac_kategori_id FROM user_kategori AS uk WHERE user_id=%d AND tipe='%s' AND transac_kategori_id != 0;",
                        userId, tipeTanamUang);

                String sql_3 = String.format("SELECT name FROM user_kategori WHERE transac_kategori_id = 0 AND tipe='%s'", tipeTanamUang);

                Statement statement_1 = connection.createStatement(); // Membuat statement dari method createStatement()
                ResultSet resultKategori = statement_1.executeQuery(sql_1); // Execute query sql menggunakan method
                                                                            // executeQuery dan dimasukkan ke dalam variabel
                                                                            // result

                Statement statement_2 = connection.createStatement(); // Membuat statement dari method createStatement()
                ResultSet resultUserKategori = statement_2.executeQuery(sql_2); // Execute query sql menggunakan method
                                                                                // executeQuery dan dimasukkan ke dalam
                                                                                // variabel result

                Statement statement_3 = connection.createStatement(); // Membuat statement dari method createStatement()
                ResultSet resultUserKategoriNonDefault = statement_3.executeQuery(sql_3); // Execute query sql menggunakan
                                                                                        // method executeQuery dan
                                                                                        // dimasukkan ke dalam variabel
                                                                                        // result

                while (resultKategori.next()) {
                    listKategoriDefault.add(resultKategori.getString("name"));
                    listIdKategoriDefault.add(resultKategori.getInt("id"));
                }
                while (resultUserKategori.next()) {
                    listKategoriUser.add(resultUserKategori.getString("name"));
                    listIdKategoriUser.add(resultUserKategori.getInt("transac_kategori_id"));
                }
                while (resultUserKategoriNonDefault.next()) {
                    listKategoriUserNonDefault.add(resultUserKategoriNonDefault.getString("name"));
                }

                String[] arrKategoriDefault = listKategoriDefault.toArray(new String[0]);
                String[] arrKategoriUser = listKategoriUser.toArray(new String[0]);
                String[] arrKategoriUserNonDefault = listKategoriUserNonDefault.toArray(new String[0]);
                Integer[] arrIdKategoriDefault = listIdKategoriDefault.toArray(new Integer[0]);
                Integer[] arrIdKategoriUser = listIdKategoriUser.toArray(new Integer[0]);

                boolean isDefault;
                for (int i = 0; i < arrKategoriDefault.length; i++) {
                    isDefault = false;
                    for (int j = 0; j < arrKategoriUser.length; j++) {
                        if (arrIdKategoriDefault[i] == arrIdKategoriUser[j]) {
                            kategoriListTemp.add(arrKategoriUser[j]);
                            isDefault = true;
                        }
                    }
                    if (!isDefault) {
                        kategoriListTemp.add(arrKategoriDefault[i]);
                    }
                }

                String[] kategoriTemp = kategoriListTemp.toArray(new String[0]); // Mengubah kategoriList menjadi array

                boolean isHapus;
                for (int i = 0; i < kategoriTemp.length; i++) {
                    isHapus = false;
                    for (int j = 0; j < arrKategoriHapus.length; j++) {
                        if (kategoriTemp[i].equals(arrKategoriHapus[j])) {
                            isHapus = true;
                            break;
                        }
                    }

                    if (!isHapus) {
                        kategoriList.add(kategoriTemp[i]);
                    }
                }

                for (int i = 0; i < arrKategoriUserNonDefault.length; i++) {
                    kategoriList.add(arrKategoriUserNonDefault[i]);
                }

                String[] kategori = kategoriList.toArray(new String[0]);

                return kategori; // Mengembalikan array kategori

            } catch (SQLException e) { // Menangkap error SQLException
                System.out.println("Query failed: " + e.getMessage()); // Menampilkan error SQLException
            }

            return new String[0]; // Mengembalikan string kosong jika terjadi kesalahan
        }

        public static String[] getKategoriTanamUang(String tipeTanamUang) {
            return _kategoriTanamUang(tipeTanamUang);
        }

        private static String[] getKategoriHapus() {
            DBConnection dbc = DBConnection.getDatabaseConnection();
            Connection connection = dbc.getConnection();

            LoginModel user = new LoginModel();
            int userId = user.getUserId();
            List<String> listKategoriHapus = new ArrayList<>();

            try {
                String sql = String.format(
                        "SELECT tk.name FROM hapus_kategori AS kh JOIN transac_kategori as tk ON (kh.kategori_id = tk.id) WHERE kh.user_id=%d;",
                        userId);
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(sql);

                while (result.next()) {
                    listKategoriHapus.add(result.getString("name"));
                }

                String[] arrKategoriHapus = listKategoriHapus.toArray(new String[0]);

                return arrKategoriHapus;
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }

            return new String[0];
        }

        private static boolean _hapusKategori(String namaKategori, boolean isDefault) {
            DBConnection dbc = DBConnection.getDatabaseConnection(); // Deklarasi dan inisialisasi variabel dbc
                                                                    // dengan nilai dari method getDatabaseConnection().
                                                                    // Berguna untuk mendapat koneksi ke database
            Connection connection = dbc.getConnection(); // Inisialisasi variabel connection dengan method getConnection()
                                                        // dari object dbc }

            LoginModel user = new LoginModel();
            int userId = user.getUserId();
            String sql = "";
            System.out.println("Id Kategori: " + getIdKategoriUser(namaKategori));

            try {
                if (isDefault) {
                    sql = String.format("INSERT INTO hapus_kategori(user_id, kategori_id) VALUES(%d, %d)", userId,
                            getIdKategoriDefault(namaKategori));
                } else {
                    sql = String.format("DELETE FROM user_kategori WHERE id=%d", getIdKategoriUser(namaKategori));
                }
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);

                return true;
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }

            return false;
        }

        public static boolean hapusKategori(String namaKategori, boolean isDefault) {
            return _hapusKategori(namaKategori, isDefault);
        }

        private static boolean _tambahKategori(String namaKategori, String tipeTU) {
            DBConnection dbc = DBConnection.getDatabaseConnection(); // Deklarasi dan inisialisasi variabel dbc
                                                                    // dengan nilai dari method getDatabaseConnection().
                                                                    // Berguna untuk mendapat koneksi ke database
            Connection connection = dbc.getConnection(); // Inisialisasi variabel connection dengan method getConnection()
                                                        // dari object dbc }

            LoginModel user = new LoginModel();
            int userId = user.getUserId();

            String tipeTanamUang = tipeTU.toLowerCase();

            try {
                String sql = String.format(
                        "INSERT INTO user_kategori(user_id, name, tipe, transac_kategori_id) VALUES(%d, '%s', '%s', 0)",
                        userId, namaKategori, tipeTanamUang);
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);

                return true;
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }

            return false;
        }

        public static boolean tambahKategori(String namaKategori, String tipeTU) {
            return _tambahKategori(namaKategori, tipeTU);
        }
    }
