package View.Dashboard;

import Model.KategoriModel;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// class DashboardPage digunakan untuk menampilkan halaman dashboard
public class TanamUangPage {
    private Stage stage; // Deklarasi property stage

    // Melakukan inisiasi class Tanamuang dengan parameter stage
    public TanamUangPage(Stage stage) {
        this.stage = stage; // Instansiasi property stage dengan parameter stage
    }

    // Menampilkan halaman Tanam Uang
    public void start(String kategori[]) {
        Text title = new Text("This is Tanam Uang Page"); // Membuat objek text dengan isi "This is Tanam Uang Page"
        title.setFont(Font.font("Verdana", 20)); // Mengatur font dari text
        title.setFill(Color.BLACK); // Mengatur warna dari text

        Label labelTanggal = new Label("Tanggal");
        Label labelKategori = new Label("Kategori");
        Label labelJumlah = new Label("Jumlah");
        Label labelKeterangan = new Label("Keterangan");

        DatePicker datePickerTanggal = new DatePicker();

        // ComboBox digunakan untuk menampilkan pilihan
        ComboBox combobox = new ComboBox(FXCollections.observableArrayList(kategori));
        combobox.setTranslateY(50);

        StackPane root = new StackPane(title, labelTanggal, datePickerTanggal, combobox); // Memasukkan node title dan
                                                                                          // combobox ke dalam root node
        Scene scene = new Scene(root); // Memasukkan root node ke dalam scene
        this.stage.setScene(scene); // Memasukkan scene ke dalam stage
        this.stage.show(); // Menampilkan stage
    }
}
