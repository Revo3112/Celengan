package View.Dashboard;

import java.beans.EventHandler;

import Controller.SceneController;
import javafx.beans.value.ChangeListener;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

// class DashboardPage digunakan untuk menampilkan halaman dashboard
public class DashboardPage {

    private Stage stage; // Deklarasi property stage
    private String username;

    // Melakukan inisiasi class DashboardPage dengan parameter stage
    public DashboardPage(Stage stage) {
        this.stage = stage; // Instansiasi property stage dengan parameter stage
    }

    // Menampilkan halaman dashboard
    public void start() {
        Text title = new Text("Selamat Datang,\n " + username + "!");
        title.setFont(Font.font("Verdana", 20));
        title.setFill(Color.WHITE);

        Button btnTanamUang = new Button("Tanam Uang Pengeluaran");
        btnTanamUang.setTranslateY(50);

        btnTanamUang.setOnMouseClicked(e -> {
            SceneController sceneController = new SceneController(this.stage);
            sceneController.switchToTanamUangPengeluaran();
        });

        StackPane stackpanenew = new StackPane();
        stackpanenew.setStyle("-fx-background-color: #141F23; -fx-background-radius: 20;");
        stackpanenew.setPadding(new Insets(10, 10, 10, 10));
        stackpanenew.setMaxSize(stage.getWidth() - 100, stage.getHeight() - 100);
        stackpanenew.getChildren().addAll(title, btnTanamUang);

        StackPane root = new StackPane(stackpanenew);
        root.setStyle("-fx-background-color: #0D1416;");

        Scene scene = new Scene(root, Color.TRANSPARENT);
        this.stage.setTitle("Register");
        this.stage.setScene(scene);

        // Menanggapi perubahan ukuran layar
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
            double stageWidth = this.stage.getWidth();
            double stageHeight = this.stage.getHeight();

            // Menyesuaikan ukuran dan posisi StackPane widht, height
            stackpanenew.setMaxSize(stage.getWidth() - 100, stage.getHeight() - 100);

            // Menyesuaikan font dan posisi teks
            title.setFont(Font.font("Verdana", stageWidth / 50));
            title.setTranslateY(-stageHeight / 8);

            // Menyesuaikan posisi tombol
            btnTanamUang.setTranslateY(-stageWidth / 8);
            btnTanamUang.setFont(Font.font("Verdana", stageWidth / 50));

        };

        // Menambahkan listener ke lebar dan tinggi stage
        this.stage.widthProperty().addListener(stageSizeListener);
        this.stage.heightProperty().addListener(stageSizeListener);

        // Menampilkan stage
        this.stage.show();

        // Mouse COORDINATES TRACKER: fungsi untuk mencetak koordinat x dan y dari
        // sebuah mouse yang diklik
        setOnMouseClicked(root, title);
    }

    // Mouse COORDINATES TRACKER: fungsi untuk mencetak koordinat x dan y dari
    // sebuah mouse yang diklik
    private void setOnMouseClicked(StackPane root, Node item) {
        root.setOnMouseClicked(new javafx.event.EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent me) {
                double x = me.getSceneX();
                double y = me.getSceneY();

                // mengkalkulasi posisi translasi x dan y untuk mendapatkan posisi yang ideal
                double itemX = x - (root.getWidth() / 2); // untuk menetapkan pada tengah node root
                double itemY = y - (root.getHeight() / 2); // untuk menetapkan pada tengah node root

                // menetapkan posisi baru untuk item
                item.setTranslateX(itemX); // posisi baru untuk koordinat x
                item.setTranslateY(itemY); // posisi baru untuk kooordinat y

                System.out.println("Item placed at X -> " + itemX);
                System.out.println("Item placed at Y -> " + itemY);
            }
        });

    }
}
