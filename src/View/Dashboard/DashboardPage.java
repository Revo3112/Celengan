package View.Dashboard;

import View.Dashboard.Section.MainSect;
import Controller.SceneController;
import Model.LoginModel;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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

        Button PantauUang = new Button("Pantau Uang");
        PantauUang.setTranslateY(80);
        PantauUang.setTranslateX(-100);
        PantauUang.setOnMouseClicked(e -> {
            SceneController sceneController = new SceneController(this.stage);
            sceneController.switchToPantauUang();
        });

        // Membuat ImageView untuk menampilkan gambar
        ImageView Logo = new ImageView(new Image("file:src/Assets/View/Dashboard/Logo.png"));
        Logo.setFitWidth(200); // Sesuaikan lebar gambar
        Logo.setFitHeight(48); // Sesuaikan tinggi gambar
        Logo.setTranslateX(-522);
        Logo.setTranslateY(-361);

        ImageView HomePage = new ImageView(new Image("file:src/Assets/View/Dashboard/HomePage.png"));
        HomePage.setFitWidth(200); // Sesuaikan lebar gambar
        HomePage.setFitHeight(48); // Sesuaikan tinggi gambar

        // membuat main pane
        StackPane isiScrollPane = new StackPane();
        isiScrollPane.getChildren().addAll(welcome, Tanamuang, PanenUang, PantauUang);
        isiScrollPane.setStyle("-fx-background-color: #141F23;");

        ScrollPane scrollMainPane = new ScrollPane(isiScrollPane);
        scrollMainPane.setFitToWidth(true);
        scrollMainPane.setFitToHeight(true);
        scrollMainPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollMainPane.setStyle("-fx-background-color: #141F23;");

        StackPane mainPane = new StackPane(scrollMainPane);
        mainPane.setMaxSize(this.stage.getWidth() - 400, this.stage.getHeight() - 30);
        mainPane.setTranslateX(70);
        mainPane.setTranslateY(0);
        mainPane.setStyle("-fx-background-color: #141F23; -fx-background-radius: 30px;");
        mainPane.setPadding(new Insets(10, 10, 10, 10));

        StackPane welcomePage = new StackPane(mainPane, Logo);
        welcomePage.setStyle("-fx-background-color: #0D1416;");

        Scene scene = new Scene(welcomePage, 750, 500);
        scene.getStylesheets().addAll(
                "https://fonts.googleapis.com/css2?family=Poppins:wght@500;600;700&display=swap");

        // Menanggapi perubahan ukuran layar

        // membuat pane sesi welcome
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
            double stageWidth = this.stage.getWidth();
            double stageHeight = this.stage.getHeight();

            // Menyesuaikan ukuran dan posisi StackPane
            mainPane.setMaxSize(this.stage.getWidth() - 400, this.stage.getHeight() - 30);

            // welcome.setTranslateY(-stageHeight / 2 + 107); // Menggunakan proporsi dengan
            // tinggi awal 600
            // welcome.setTranslateX(-stageWidth / 2 + 250); // Menggunakan proporsi dengan
            // lebar awal 750

            // // Tanamuang.setTranslateX(-stageWidth / 2 + 500);
            // Tanamuang.setTranslateY(-stageHeight / 2 + 330);

            // PanenUang.setTranslateY(-stageHeight / 2 + 330);

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
        this.stage.setFullScreen(true);
        this.stage.show();
        setOnMouseClicked(welcomePage, Logo);
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
