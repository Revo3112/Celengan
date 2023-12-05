package View.Dashboard;

import Controller.SceneController;
import Model.LoginModel;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// class DashboardPage digunakan untuk menampilkan halaman dashboard
public class DashboardPage {

    private Stage stage; // Property stage sebaiknya dideklarasikan sebagai final
    private String username;
    private SceneController sceneController; // Tambahkan property SceneController

    public DashboardPage(Stage stage) {
        this.stage = stage;
        this.username = getUsername();
        this.sceneController = new SceneController(stage); // Inisialisasi SceneController
    }

    // Menampilkan halaman dashboard
    public void start() {
        // membuat text
        Text welcome = createText("Selamat Datang,\n" + this.username, "-fx-font: 30 'Poppins-Regular';", "#FFFFFF",
                -this.stage.getWidth() / 2 + 204, -this.stage.getHeight() / 2 + 6);
        welcome.toFront();

        // membuat main pane
        StackPane isiScrollPane = new StackPane();
        isiScrollPane.getChildren().addAll(welcome);
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

        /// Membuat side bar
        HBox SideBar = ImageLinkPane.createImageLinkVBox(stage, sceneController);
        SideBar.setTranslateX(-stage.getWidth() / 2 + 40);

        StackPane welcomePage = new StackPane(mainPane, SideBar);
        welcomePage.setStyle("-fx-background-color: #0D1416;");

        Scene scene = new Scene(welcomePage, 750, 500);
        scene.getStylesheets().addAll(
                "https://fonts.googleapis.com/css2?family=Poppins:wght@500;600;700&display=swap");

        // Menanggapi perubahan ukuran layar

        // membuat pane sesi welcome
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
            double stageWidth = stage.getWidth();
            double stageHeight = stage.getHeight();

            // Menyesuaikan ukuran dan posisi StackPane
            mainPane.setMaxSize(stage.getWidth() - 400, stage.getHeight() - 30);

            // welcome.setTranslateY( -this.stage.getWidth() / 2 + 132);
            // welcome.setTranslateX(-this.stage.getHeight() / 2 + -268);

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
        // }
    }
}

class ImageLinkPane {
    public static HBox createImageLinkVBox(Stage stage, SceneController sceneController) {
        // Gunakan ImageView untuk semua pilihan di Sidebar
        ImageView logoImageView = new ImageView(new Image("file:src/Assets/View/Dashboard/Logo.png"));
        logoImageView.setFitWidth(200);
        logoImageView.setFitHeight(50);
        logoImageView.setTranslateY(-stage.getHeight() / 2 + 270);
        logoImageView.setTranslateX(-stage.getWidth() / 2 + 450);
        System.out.println("Logo position: " + -stage.getHeight() + ", " + -stage.getWidth());

        ImageView homePageImageView = new ImageView(new Image("file:src/Assets/View/Dashboard/HomePage.png"));
        ImageView tanamUangImageView = new ImageView(new Image("file:src/Assets/View/Dashboard/Tanam Uang.png"));
        ImageView pantauUangImageView = new ImageView(new Image("file:src/Assets/View/Dashboard/Pantau Uang.png"));
        ImageView panenUangImageView = new ImageView(new Image("file:src/Assets/View/Dashboard/Panen Uang.png"));
        ImageView modeUser = new ImageView("file:src/Assets/View/Dashboard/Mode User.png");
        ImageView logOut = new ImageView("file:src/Assets/View/Dashboard/Log Out.png");

        // Menyesuaikan ukuran ImageView
        homePageImageView.setFitWidth(200);
        homePageImageView.setFitHeight(40);
        tanamUangImageView.setFitWidth(200);
        tanamUangImageView.setFitHeight(40);
        pantauUangImageView.setFitWidth(200);
        pantauUangImageView.setFitHeight(40);
        panenUangImageView.setFitWidth(200);
        panenUangImageView.setFitHeight(40);
        modeUser.setFitWidth(200);
        modeUser.setFitHeight(40);
        logOut.setFitWidth(200);
        logOut.setFitHeight(60);

        // Membuat Hyperlink dengan menggunakan ImageView
        Hyperlink homeHyperlink = createHyperlinkWithImageView(homePageImageView);
        Hyperlink tanamUangHyperlink = createHyperlinkWithImageView(tanamUangImageView);
        Hyperlink pantauUangHyperlink = createHyperlinkWithImageView(pantauUangImageView);
        Hyperlink panenUangHyperlink = createHyperlinkWithImageView(panenUangImageView);
        Hyperlink modeUserHyperlink = createHyperlinkWithImageView(modeUser);
        Hyperlink logOutHyperlink = createHyperlinkWithImageView(logOut);

        // Menambahkan fungsi ketika hyperlink diklik
        homeHyperlink.setOnMouseClicked(e -> sceneController.switchToDashboard());
        tanamUangHyperlink.setOnMouseClicked(e -> sceneController.switchToTanamUang());
        pantauUangHyperlink.setOnMouseClicked(e -> sceneController.switchToPantauUang());
        panenUangHyperlink.setOnMouseClicked(e -> sceneController.switchToPanenUang());
        // modeUserHyperlink.setOnMouseClicked(e -> sceneController.switchToModeUser());
        logOutHyperlink.setOnMouseClicked(e -> sceneController.switchToLogin());

        Rectangle region = new Rectangle();
        region.setWidth(250);

        // Membuat masing - masing Vbox dan menambahkan Hyperlink ke dalamnya
        VBox homeVBox = new VBox(homeHyperlink, region);
        homeVBox.setTranslateX(-stage.getWidth() + 990);
        homeVBox.setTranslateY(-stage.getHeight() / 2 + 250);
        homeVBox.setStyle("-fx-background-color: #141F23; -fx-background-radius: 20px;");
        homeVBox.setMaxWidth(200); // Set maksimum lebar VBox
        homeVBox.setMaxHeight(40); // Set maksimum tinggi VBox

        VBox tanamUangVBox = new VBox(tanamUangHyperlink);
        tanamUangVBox.setTranslateX(-stage.getWidth() + 970);
        tanamUangVBox.setMaxWidth(200); // Set maksimum lebar VBox
        tanamUangVBox.setMaxHeight(40); // Set maksimum tinggi VBox

        VBox pantauUangVBox = new VBox(pantauUangHyperlink);
        pantauUangVBox.setTranslateX(-stage.getWidth() + 970);
        pantauUangVBox.setMaxWidth(200); // Set maksimum lebar VBox
        pantauUangVBox.setMaxHeight(40); // Set maksimum tinggi VBox

        VBox panenUangVBox = new VBox(panenUangHyperlink);
        panenUangVBox.setTranslateX(-stage.getWidth() + 970);
        panenUangVBox.setMaxWidth(200); // Set maksimum lebar VBox
        panenUangVBox.setMaxHeight(40); // Set maksimum tinggi VBox

        VBox modeUserVBox = new VBox(modeUserHyperlink);
        modeUserVBox.setTranslateX(-stage.getWidth() + 970);
        modeUserVBox.setMaxWidth(200); // Set maksimum lebar VBox
        modeUserVBox.setMaxHeight(40); // Set maksimum tinggi VBox

        VBox logOutVBox = new VBox(logOutHyperlink);
        logOutVBox.setTranslateX(-stage.getWidth() + 970);
        logOutVBox.setMaxWidth(200); // Set maksimum lebar VBox
        logOutVBox.setMaxHeight(60); // Set maksimum tinggi VBox

        // Membuat VBox dan menambahkan Hyperlink ke dalamnya
        VBox kontenSide = new VBox(logoImageView, homeVBox, tanamUangVBox, pantauUangVBox, panenUangVBox,
                modeUserVBox,
                logOutVBox);
        kontenSide.setSpacing(60);
        kontenSide.setAlignment(Pos.TOP_CENTER);
        kontenSide.setPadding(new Insets(0, 0, 0, 0));

        // Membuat HBox dan menambahkan VBox ke dalamnya
        HBox SideBar = new HBox(kontenSide);
        SideBar.setAlignment(Pos.CENTER_LEFT);
        SideBar.setMaxHeight(stage.getMaxHeight());
        SideBar.setMaxWidth(stage.getWidth() / 5 - 50);

        return SideBar;

    }

    private static Hyperlink createHyperlinkWithImageView(ImageView imageView) {
        Hyperlink hyperlink = new Hyperlink();
        hyperlink.setGraphic(imageView);
        return hyperlink;
    }

}
