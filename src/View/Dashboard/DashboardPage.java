package View.Dashboard;

import View.Dashboard.Section.MainSect;
import Controller.SceneController;
import Model.LoginModel;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// class DashboardPage digunakan untuk menampilkan halaman dashboard
public class DashboardPage {

    private Stage stage; // Property stage sebaiknya dideklarasikan sebagai final
    private String username;

    // Melakukan inisiasi class DashboardPage dengan parameter stage
    public DashboardPage(Stage stage) {
        this.stage = stage; // Instansiasi property stage dengan parameter stage
        this.username = getUsername();
    }

    // Menampilkan halaman dashboard
    public void start() {
        // membuat text
        Text welcome = createText("Selamat Datang,\n" + this.username, "-fx-font: 30 'Poppins-Regular';", "#FFFFFF",
                -250, -143);
        Button Tanamuang = new Button("Tanam Uang");
        Tanamuang.setTranslateY(80);
        Tanamuang.setOnMouseClicked(e -> {
            SceneController sceneController = new SceneController(this.stage);
            sceneController.switchToTanamUang();
        });

        Button PanenUang = new Button("Panen Uang");
        PanenUang.setTranslateY(80);
        PanenUang.setTranslateX(100);
        PanenUang.setOnMouseClicked(e -> {
            SceneController sceneController = new SceneController(this.stage);
            sceneController.switchToPanenUang();
        });
        // membuat main pane
        StackPane mainPane = new StackPane();
        mainPane.getChildren().addAll(welcome, Tanamuang, PanenUang);
        mainPane.setMaxSize(this.stage.getWidth() - 200, this.stage.getHeight() - 100);
        mainPane.setStyle("-fx-background-color: #141F23; -fx-background-radius: 20;");
        mainPane.setPadding(new Insets(10, 10, 10, 10));
        StackPane welcomePage = new StackPane(mainPane);
        StackPane.setAlignment(mainPane, javafx.geometry.Pos.CENTER);
        Scene scene = new Scene(welcomePage, 750, 500);
        scene.getStylesheets().addAll(
                "https://fonts.googleapis.com/css2?family=Poppins:wght@500;600;700&display=swap");

        // Menanggapi perubahan ukuran layar

        // membuat pane sesi welcome
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
            double stageWidth = this.stage.getWidth();
            double stageHeight = this.stage.getHeight();

            // Menyesuaikan ukuran dan posisi StackPane
            mainPane.setMaxSize(stageWidth - 200, stageHeight - 100);

            welcome.setTranslateY(-stageHeight / 2 + 107); // Menggunakan proporsi dengan tinggi awal 600
            welcome.setTranslateX(-stageWidth / 2 + 250); // Menggunakan proporsi dengan lebar awal 750

            // Tanamuang.setTranslateX(-stageWidth / 2 + 500);
            Tanamuang.setTranslateY(-stageHeight / 2 + 330);

            PanenUang.setTranslateY(-stageHeight / 2 + 330);

            System.out.println("Tanamuang position: " + Tanamuang.getTranslateX() + ", " + Tanamuang.getTranslateY());
            System.out.println("Welcome position: " + welcome.getTranslateX() + ", " + welcome.getTranslateY());
            System.out.println("Stage size: " + stageWidth + ", " + stageHeight);
        };

        // Menambahkan listener ke lebar dan tinggi stage
        this.stage.widthProperty().addListener(stageSizeListener);
        this.stage.heightProperty().addListener(stageSizeListener);

        this.stage.setScene(scene);
        this.stage.setMinHeight(500);
        this.stage.setMinWidth(750);
        this.stage.show();
        setOnMouseClicked(welcomePage, welcome);
    }

    // fungsi untuk membuat text dengan return Text
    public Text createText(String text, String style, String color, double transX, double transY) {
        Text newText = new Text(text);
        newText.setStyle(style); // menetapkan style text
        newText.setFill(Color.valueOf(color)); // menetapkan warna text
        newText.setTranslateX(transX); // menetapkan posisi x pada text
        newText.setTranslateY(transY); // menetapkan posisi y pada text
        return newText;
    }

    // Fungsi untuk mendapatkan username
    private String getUsername() {
        LoginModel loginModel = new LoginModel();
        return loginModel.getLastActiveUsers();
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
