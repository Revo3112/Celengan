package View.Dashboard;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Controller.SceneController;
import Model.TanamUangModel;
import Utils.AlertHelper;
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
public class TanamUangPengeluaranPage {
    private Stage stage; // Deklarasi property stage

    // Melakukan inisiasi class Tanamuang dengan parameter stage
    public TanamUangPengeluaranPage(Stage stage) {
        this.stage = stage; // Instansiasi property stage dengan parameter stage
    }

    // Menampilkan halaman Tanam Uang
    public void start(String listKategori[]) {
        Button btnPemasukan = new Button("Go to Pemasukan Page");
        btnPemasukan.setTranslateY(-80);
        btnPemasukan.setOnMouseClicked(e -> {
            SceneController sceneController = new SceneController(this.stage);
            sceneController.switchToTanamUangPemasukan();
        });

        Text title = new Text("Pengeluaran"); // Membuat objek text dengan isi "This is Tanam Uang Page"
        title.setFont(Font.font("Verdana", 20)); // Mengatur font dari text
        title.setFill(Color.BLACK); // Mengatur warna dari text
        title.setTranslateY(-40);

        // Form Tanggal
        Label labelTanggal = new Label("Tanggal:");
        labelTanggal.setTranslateX(-50);
        DatePicker datePickerTanggal = new DatePicker();
        datePickerTanggal.setTranslateX(90);

        // Form kategori
        Label labelKategori = new Label("Kategori:");
        labelKategori.setTranslateX(-50);
        labelKategori.setTranslateY(40);
        // ComboBox digunakan untuk menampilkan pilihan
        ComboBox combobox = new ComboBox(FXCollections.observableArrayList(listKategori));
        combobox.setTranslateX(60);
        combobox.setTranslateY(40);

        // Form jumlah
        Label labelJumlah = new Label("Jumlah:");
        labelJumlah.setTranslateX(-50);
        labelJumlah.setTranslateY(80);
        TextField fieldJumlah = new TextField();
        fieldJumlah.setMaxWidth(200);
        fieldJumlah.setTranslateX(100);
        fieldJumlah.setTranslateY(80);

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
            int jumlah = Integer.parseInt(fieldJumlah.getText());
            String keterangan = fieldKeterangan.getText();
            String tipePembayaran = "";

            for (int i = 0; i < listKategori.length; i++) {
                if (listKategori[i] == kategori) {
                    kategoriId = i + 1;
                } 
            }

            if (radioBtnCash.isSelected()) {
                tipePembayaran = radioBtnCash.getText();
            } else if (radioBtnTransfer.isSelected()) {
                tipePembayaran = radioBtnTransfer.getText();
            }
            
            if (TanamUangModel.simpanPengeluaran(tanggal, kategori, kategoriId, jumlah, tipePembayaran, keterangan)) {

                datePickerTanggal.setValue(null);
                combobox.getSelectionModel().clearSelection();
                fieldJumlah.setText("");
                fieldKeterangan.setText("");
                radioBtnCash.setSelected(false);
                radioBtnTransfer.setSelected(false);
                AlertHelper.alert("Pengeluaran telah tercatat");
            }
        });

        StackPane root = new StackPane(btnPemasukan, title, labelTanggal, datePickerTanggal, labelKategori, combobox, labelJumlah, fieldJumlah, labelKeterangan, fieldKeterangan, labelTipePembayaran, radioBtnCash, radioBtnTransfer, btnSimpan); // Memasukkan semua child node
                                                                                          // combobox ke dalam root node
        Scene scene = new Scene(root); // Memasukkan root node ke dalam scene
        this.stage.setScene(scene); // Memasukkan scene ke dalam stage
        this.stage.show(); // Menampilkan stage
    }
}
