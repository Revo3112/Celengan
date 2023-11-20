package View.Dashboard;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
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
    public void start() {
        Text title = new Text("This is Tanam Uang Page"); // Membuat objek text dengan isi "This is Tanam Uang Page"
        title.setFont(Font.font("Verdana", 20)); // Mengatur font dari text
        title.setFill(Color.BLACK); // Mengatur warna dari text

        // ComboBox digunakan untuk menampilkan pilihan
        String pil[] = {
                "pertama",
                "kedua",
                "ketiga",
                "keempat"
        };
        ComboBox combobox = new ComboBox(FXCollections.observableArrayList(pil));

        StackPane root = new StackPane(title, combobox);
        Scene scene = new Scene(root);
        this.stage.setScene(scene);
        this.stage.show();
    }
}
