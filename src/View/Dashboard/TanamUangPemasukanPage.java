package View.Dashboard;

import Controller.SceneController;
import Model.TanamUangModel;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// class DashboardPage digunakan untuk menampilkan halaman dashboard
public class TanamUangPemasukanPage {
    private Stage stage; // Deklarasi property stage

    // Melakukan inisiasi class Tanamuang dengan parameter stage
    public TanamUangPemasukanPage(Stage stage) {
        this.stage = stage; // Instansiasi property stage dengan parameter stage
    }

    // Menampilkan halaman Tanam Uang
    public void start(String kategori[]) {
        Button btnPengeluaran = new Button("Go to Pengeluaran Page");
        btnPengeluaran.setTranslateY(-80);
        btnPengeluaran.setOnMouseClicked(e -> {
            SceneController sceneController = new SceneController(this.stage);
            sceneController.switchToTanamUangPengeluaran();
        });

        Text title = new Text("Pemasukan"); // Membuat objek text dengan isi "This is Tanam Uang Page"
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
        ComboBox combobox = new ComboBox(FXCollections.observableArrayList(kategori));
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

        // Form keterangan
        Label labelKeterangan = new Label("Keterangan:");
        labelKeterangan.setTranslateX(-50);
        labelKeterangan.setTranslateY(120);
        TextField fieldKeterangan = new TextField();
        fieldKeterangan.setMaxWidth(200);
        fieldKeterangan.setTranslateX(100);
        fieldKeterangan.setTranslateY(120);

        Button btnSimpan = new Button("Simpan");
        btnSimpan.setTranslateY(160);

        StackPane root = new StackPane(btnPengeluaran, title, labelTanggal, datePickerTanggal, labelKategori, combobox, labelJumlah, fieldJumlah, labelKeterangan, fieldKeterangan, btnSimpan); // Memasukkan semua child node
                                                                                          // combobox ke dalam root node
        Scene scene = new Scene(root); // Memasukkan root node ke dalam scene
        this.stage.setScene(scene); // Memasukkan scene ke dalam stage
        this.stage.show(); // Menampilkan stage
    }
}
