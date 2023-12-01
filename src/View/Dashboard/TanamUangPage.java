package View.Dashboard;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.UnaryOperator;

import Controller.SceneController;
import Model.TanamUangModel;
import Utils.AlertHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// class DashboardPage digunakan untuk menampilkan halaman dashboard
public class TanamUangPage {
    private Stage stage; // Deklarasi property stage

    public DecimalFormat formatRibuan;
    // public DecimalFormat formatPuluhanRibu;
    public DecimalFormat formatRatusanRibu;
    public DecimalFormat formatJutaan;
    public DecimalFormat formatPuluhanJuta;
    public DecimalFormat formatRatusanJuta;
    public DecimalFormat formatMiliaran;

    public DecimalFormat formatRupiah;

    private ComboBox<String> combobox = new ComboBox<String>();
    private Text title = new Text("Pengeluaran");
    private String tipeTanamUang = "";

    // Melakukan inisiasi class Tanamuang dengan parameter stage
    public TanamUangPage(Stage stage) {
        this.stage = stage; // Instansiasi property stage dengan parameter stage
    }

    // Menampilkan halaman Tanam Uang
    public void start(String listKategoriPemasukan[], String listKategoriPengeluaran[]) {

        System.out.println(listKategoriPengeluaran);
        this.combobox.setItems(FXCollections.observableArrayList(listKategoriPengeluaran));
        this.combobox.setTranslateX(60);
        this.combobox.setTranslateY(40);
        // Form kategori
        Label labelKategori = new Label("Kategori:");
        labelKategori.setTranslateX(-50);
        labelKategori.setTranslateY(40);
        // ComboBox digunakan untuk menampilkan pilihan
        // ComboBox combobox = new ComboBox(FXCollections.observableArrayList(listKategoriPemasukan));

        Button btnPemasukan = new Button("Pemasukan");
        btnPemasukan.setTranslateX(-50);
        btnPemasukan.setTranslateY(-80);

        btnPemasukan.setOnMouseClicked(e -> {
            this.combobox.setItems(FXCollections.observableArrayList(listKategoriPemasukan));
            this.title.setText("Pemasukan");
            this.tipeTanamUang = "Pemasukan";
        });

        Button btnPengeluaran = new Button("Pengeluaran");

        btnPengeluaran.setTranslateX(50);
        btnPengeluaran.setTranslateY(-80);
        btnPengeluaran.setOnMouseClicked(e -> {
            this.combobox.setItems(FXCollections.observableArrayList(listKategoriPengeluaran));
            this.title.setText("Pengeluaran");
            this.tipeTanamUang = "Pengeluaran";
        });

        // this.title = new Text(); // Membuat objek text dengan isi "This is Tanam Uang Page"
        this.title.setFont(Font.font("Verdana", 20)); // Mengatur font dari text
        this.title.setFill(Color.BLACK); // Mengatur warna dari text
        this.title.setTranslateY(-40);

        // Form Tanggal
        Label labelTanggal = new Label("Tanggal:");
        labelTanggal.setTranslateX(-50);
        DatePicker datePickerTanggal = new DatePicker();
        datePickerTanggal.setTranslateX(90);

        // Form jumlah
        Label labelJumlah = new Label("Jumlah:");
        labelJumlah.setTranslateX(-50);
        labelJumlah.setTranslateY(80);
        TextField fieldJumlah = new TextField();
        fieldJumlah.setMaxWidth(200);
        fieldJumlah.setTranslateX(100);
        fieldJumlah.setTranslateY(80);

        fieldJumlah.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("Text changed: " + newValue);
                // Hapus listener sementara

                fieldJumlah.textProperty().removeListener(this);
                // if (newValue.length() >= 13) {
                // System.out.println("sudah lebih dari 13");

                // // newValue = newValue.substring(0, 13);
                // newValue.consume();
                // }

                // Lakukan pembaruan teks
                switch (newValue.length()) {
                    case 4:
                        formatAndSet(newValue, "#.##0");
                        break;
                    case 5:
                        formatAndSet(newValue, "##.##0");
                        break;
                    case 6:
                        formatAndSet(newValue, "###.##0");
                        break;
                    case 7:
                        formatAndSet(newValue, "#.###.##0");
                        break;
                    case 8:
                        formatAndSet(newValue, "##.###.##0");
                        break;
                    case 9:
                        formatAndSet(newValue, "###.###.##0");
                        break;
                    case 10:
                        formatAndSet(newValue, "#.###.###.##0");
                        break;
                    case 11:
                        formatAndSet(newValue, "##.###.###.##0");
                        break;
                    case 12:
                        formatAndSet(newValue, "###.###.###.##0");
                        break;
                    case 13:
                        formatAndSet(newValue, "#.###.###.###.##0");
                        break;
                    default:
                        break;
                }

                // Tambahkan kembali listener setelah pembaruan teks
                fieldJumlah.textProperty().addListener(this);
            }

            private void formatAndSet(String text, String format) {
                DecimalFormat decimalFormat = new DecimalFormat(format);
                fieldJumlah.setText(decimalFormat.format(Double.valueOf(text.replace(",", ""))));
            }
        });

        // Form tipe pembayaran
        Label labelTipePembayaran = new Label("Tipe Pembayaran:");
        labelTipePembayaran.setTranslateX(-50);
        labelTipePembayaran.setTranslateY(120);
        RadioButton radioBtnCash = new RadioButton("Cash");
        radioBtnCash.setTranslateX(60);
        radioBtnCash.setTranslateY(120);
        RadioButton radioBtnTransfer = new RadioButton("Transfer");
        radioBtnTransfer.setTranslateX(120);
        radioBtnTransfer.setTranslateY(120);
        ToggleGroup toggleGroup = new ToggleGroup();
        radioBtnCash.setToggleGroup(toggleGroup);
        radioBtnTransfer.setToggleGroup(toggleGroup);

        // Form keterangan
        Label labelKeterangan = new Label("Keterangan:");
        labelKeterangan.setTranslateX(-50);
        labelKeterangan.setTranslateY(160);
        TextField fieldKeterangan = new TextField();
        fieldKeterangan.setMaxWidth(200);
        fieldKeterangan.setTranslateX(100);
        fieldKeterangan.setTranslateY(160);

        // Button Simpan
        Button btnSimpan = new Button("Simpan");
        btnSimpan.setTranslateY(200);

        btnSimpan.setOnMouseClicked(e -> {
            LocalDate selectedTanggal = datePickerTanggal.getValue();
            DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("YYYY-MM-d");

            String tanggal = selectedTanggal.format(formatTanggal).toString();
            String kategori = combobox.getValue().toString();
            int kategoriId = 0;
            int jumlah = Integer.parseInt(fieldJumlah.getText().replace(",", ""));
            String keterangan = fieldKeterangan.getText();
            String tipePembayaran = "";

            for (int i = 0; i < listKategoriPemasukan.length; i++) {
                if (listKategoriPemasukan[i] == kategori) {
                    kategoriId = i + 1;
                }
            }

            if (radioBtnCash.isSelected()) {
                tipePembayaran = radioBtnCash.getText();
            } else if (radioBtnTransfer.isSelected()) {
                tipePembayaran = radioBtnTransfer.getText();
            }

            if (tipeTanamUang.toLowerCase() == "pengeluaran") {
                if (TanamUangModel.simpanPengeluaran(tanggal, kategori, kategoriId, jumlah, tipePembayaran, keterangan)) {
                    datePickerTanggal.setValue(null);
                    combobox.getSelectionModel().clearSelection();
                    fieldJumlah.setText("");
                    fieldKeterangan.setText("");
                    radioBtnCash.setSelected(false);
                    radioBtnTransfer.setSelected(false);
                    AlertHelper.info("Pengeluaran telah tercatat");
                }
            } else {
                if (TanamUangModel.simpanPemasukan(tanggal, kategori, kategoriId, jumlah, tipePembayaran, keterangan)) {
                    datePickerTanggal.setValue(null);
                    combobox.getSelectionModel().clearSelection();
                    fieldJumlah.setText("");
                    fieldKeterangan.setText("");
                    radioBtnCash.setSelected(false);
                    radioBtnTransfer.setSelected(false);
                    AlertHelper.info("Pemasukan telah tercatat");
                }
            }
        });

       // StackPane root = new StackPane(btnPemasukan, title, labelTanggal, datePickerTanggal, labelKategori, this.combobox,
                //labelJumlah, fieldJumlah, labelKeterangan, fieldKeterangan, labelTipePembayaran, radioBtnCash,
                //radioBtnTransfer, btnSimpan); // Memasukkan semua child node
        StackPane root = new StackPane(btnPengeluaran, btnPemasukan, this.title, labelTanggal, datePickerTanggal, labelKategori, this.combobox,
                labelJumlah, fieldJumlah, labelKeterangan, fieldKeterangan, labelTipePembayaran, radioBtnCash,
                radioBtnTransfer, btnSimpan); // Memasukkan semua child node
        // combobox ke dalam root node
        Scene scene = new Scene(root); // Memasukkan root node ke dalam scene
        this.stage.setScene(scene); // Memasukkan scene ke dalam stage
        // this.stage.show(); // Menampilkan stage
    }
}
