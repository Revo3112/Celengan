package View.Dashboard;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// class DashboardPage digunakan untuk menampilkan halaman dashboard
public class DashboardPage {

    private Stage stage; // Deklarasi property stage

    // Melakukan inisiasi class DashboardPage dengan parameter stage
    public DashboardPage(Stage stage) {
        this.stage = stage; // Instansiasi property stage dengan parameter stage
    }

    // Menampilkan halaman dashboard
    public void start() {
        Text title = new Text("Welcome to Dasboard page."); // Membuat objek text dengan isi "Welcome to Dasboard
                                                            // page."
        title.setFont(Font.font("Verdana", 20)); // Mengatur font dari text
        title.setFill(Color.BLACK); // Mengatur warna dari text

        StackPane root = new StackPane(title); // Membuat objek StackPane dengan parameter title
        Scene scene = new Scene(root, 600, 600); // Membuat objek Scene dengan parameter root dan ukuran 600x600

        this.stage.setTitle("Register"); // Mengatur title dari stage
        this.stage.setScene(scene); // Mengatur scene dari stage
        this.stage.show(); // Menampilkan stage
    }
}
