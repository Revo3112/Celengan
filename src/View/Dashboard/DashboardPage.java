package View.Dashboard;

import Controller.SceneController;
import Model.LoginModel;
import Model.PieChartData;
import javafx.animation.ScaleTransition;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import java.awt.MouseInfo;

import javafx.stage.Stage;
import javafx.util.Duration;

// class DashboardPage digunakan untuk menampilkan halaman dashboard
public class DashboardPage {

    private Stage stage; // Property stage sebaiknya dideklarasikan sebagai final
    private String username;
    private SceneController sceneController; // Tambahkan property SceneController\
    private double saldo;
    private Tooltip tooltip = new Tooltip();

    public DashboardPage(Stage stage) {
        this.stage = stage;
        this.username = getUsername();
        this.sceneController = new SceneController(stage); // Inisialisasi SceneController
        this.saldo = getSaldo();
    }

    // Menampilkan halaman dashboard
    public void start() {
        // Membuat side bar
        HBox sideBar = ImageLinkPane.createImageLinkVBox(this.stage, sceneController);
        sideBar.setTranslateX(-stage.getWidth() / 2 + 40);

        // Membuat teks welcome
        Text welcome = createText("Selamat Datang,\n" + this.username + "!", "-fx-font: 40 'Poppins-Regular';",
                "#FFFFFF", 0, 0);

        // StackPane untuk menampung teks
        StackPane textPane = new StackPane(welcome);
        textPane.setAlignment(Pos.CENTER_LEFT);
        textPane.setPadding(new Insets(0, 0, 0, 10));

        // Menambahkan gambar
        ImageView contentImageView = new ImageView(new Image("file:src/Assets/View/Dashboard/Content.png"));
        contentImageView.setFitWidth(400);
        contentImageView.setFitHeight(300);

        // StackPane untuk menampung gambar
        StackPane contentImagePane = new StackPane(contentImageView);
        contentImagePane.setAlignment(Pos.CENTER_RIGHT);
        contentImagePane.setPadding(new Insets(0, 0, 0, 0));

        // Konten bagian atas untuk main pane
        HBox kontenAtasPane = new HBox(textPane, contentImagePane);
        HBox.setHgrow(textPane, Priority.ALWAYS);
        textPane.setTranslateX(20);

        // ------------------------------------------------------------------------------------------------------------//

        // Membuat teks fitur - fitur celengan
        Text Fitur = createText("Fitur - Fitur Celengan", "-fx-font: 30 'Poppins-Regular';",
                "#FFFFFF", 0, 0);
        // Membuat vbox atas untuk konten tengah
        VBox kontenTengahAtas = new VBox(Fitur);
        kontenTengahAtas.setAlignment(Pos.CENTER_LEFT);

        // Konten kiri
        ImageView Gambarduit = new ImageView(new Image("file:src/Assets/View/Dashboard/Gambarduit.png"));
        Gambarduit.setFitWidth(120);
        Gambarduit.setFitHeight(40);

        VBox vboxKiriAtas = new VBox(Gambarduit);
        vboxKiriAtas.setAlignment(Pos.TOP_LEFT);
        vboxKiriAtas.setPadding(new Insets(0, 0, 0, 20));

        Text Saldoawal = createText("Saldo Awal", "-fx-font: 20 'Poppins-Regular';",
                "#064D00", 0, 0);
        Text Saldoawal2 = createText("Rp. " + this.saldo, "-fx-font: 50 'Poppins-bold';",
                "#ffffff", 0, -10);
        VBox vboxKiriTengah = new VBox(Saldoawal, Saldoawal2);
        vboxKiriTengah.setAlignment(Pos.CENTER_LEFT);
        vboxKiriTengah.setPadding(new Insets(0, 0, 0, 20));

        ImageView UbahSaldo = new ImageView(new Image("file:src/Assets/View/Dashboard/UbahSaldo.png"));
        UbahSaldo.setFitWidth(268);
        UbahSaldo.setFitHeight(53);

        Hyperlink UbahSaldoHyperlink = new Hyperlink();
        UbahSaldoHyperlink.setGraphic(UbahSaldo);
        UbahSaldoHyperlink.setOnMouseClicked(e -> sceneController.switchToTanamUang());

        VBox vboxKiriBawah = new VBox(UbahSaldoHyperlink);
        vboxKiriBawah.setAlignment(Pos.BOTTOM_CENTER);

        VBox penyatuanKonten = new VBox(vboxKiriAtas, vboxKiriTengah, vboxKiriBawah);
        penyatuanKonten.setPadding(new Insets(10, 0, 10, 0));

        StackPane Kontenkiri = new StackPane(penyatuanKonten);
        Kontenkiri.setStyle("-fx-background-color: #39B200; -fx-background-radius: 20px");
        Kontenkiri.setAlignment(Pos.CENTER);

        // Konten Tengah

        // Membuat pie chart
        try {
            PieChart pieChart = new PieChart();
            ObservableList<PieChart.Data> pieChartData = PieChartData.pieChartData();
            pieChart.setData(pieChartData);
            for (PieChart.Data data : pieChart.getData()) {

                Tooltip.install(data.getNode(), tooltip);

                data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                    tooltip.setText(String.format("%.1f%%", data.getPieValue()));
                    updateTooltipPosition(e.getScreenX(), e.getScreenY());
                    tooltip.show(data.getNode(), e.getScreenX(), e.getScreenY());
                });

                data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                    tooltip.hide();
                });

                data.getNode().addEventHandler(MouseEvent.MOUSE_MOVED, e -> {
                    updateTooltipPosition(e.getScreenX(), e.getScreenY());
                });
                data.getNode().setOnMouseEntered(e -> {
                    data.getNode().setScaleX(1.1);
                    data.getNode().setScaleY(1.1);
                });

                data.getNode().setOnMouseExited(e -> {
                    data.getNode().setScaleX(1);
                    data.getNode().setScaleY(1);
                });

                data.getNode().setStyle("-fx-label-line-length: 10px;");
            }
            pieChart.setLegendVisible(false);

            StackPane kontenPieChart = new StackPane(pieChart);

            HBox kontenPieChartdanDuit = new HBox(kontenPieChart);

            // Vbox atas pada tengah
            VBox vboxTengahAtas = new VBox(kontenPieChartdanDuit);

            // Vbox bawah pada tengah
            VBox vBoxTengahBawah = new VBox();

            VBox penyatuanKontenTengah = new VBox(vboxTengahAtas, vBoxTengahBawah);

            StackPane KontenTengah = new StackPane(penyatuanKontenTengah);
            KontenTengah.setStyle("-fx-background-color: #6E94FA; -fx-background-radius: 20px");

            StackPane KontenKanan = new StackPane();

            // Membuat bagian bagian konten tangah bawah
            HBox kontenFiturCelengan = new HBox(Kontenkiri, KontenTengah, KontenKanan);
            kontenFiturCelengan.setSpacing(20);

            // Membuat konten tengah bawah untuk konten tengah
            VBox kontenTengahBawah = new VBox(kontenFiturCelengan);

            // Membuat konten bagian tengah untuk main pane
            VBox kontenTengahPane = new VBox(kontenTengahAtas, kontenTengahBawah);
            kontenTengahPane.setPadding(new Insets(0, 0, 0, 30));
            kontenTengahPane.setSpacing(10);

            // ------------------------------------------------------------------------------------------------------------//

            // Membuat konten bagian bawah untuk main pane
            VBox kontenBawahPane = new VBox();

            // VBox untuk menampung konten atas, tengah, dan bawah
            VBox kontenVBox = new VBox(kontenAtasPane, kontenTengahPane, kontenBawahPane);
            kontenVBox.setSpacing(2);
            kontenVBox.setStyle("-fx-background-color: #141F23; -fx-background-radius: 20px");

            // Membuat konten bagian tengah
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(kontenVBox);
            scrollPane.setFitToWidth(true);
            scrollPane.setMaxHeight(this.stage.getHeight() + 250);
            scrollPane.setMaxWidth(this.stage.getWidth() - 100);
            scrollPane.setTranslateX(70);
            scrollPane.setTranslateY(0);

            // Menentukan gaya css untuk scroll bar
            String scrollbarStyle = "-fx-background-color: #141F23;"; // Warna latar belakang
            scrollbarStyle += "-fx-background: #0B1214;"; // Warna slider
            scrollbarStyle += "-fx-background-insets: 0;"; // Menghapus bayangan
            scrollbarStyle += "-fx-padding: 0;"; // Menghapus padding

            // Menetapkan ID agar dapat diakses menggunakan lookup
            scrollPane.setId("custom-scrollbar");
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollPane.setStyle(scrollbarStyle); // Mengubah gaya css scroll bar
            // menghilangkan border dan mengatur warna background scrollPane
            scrollPane.setStyle("-fx-background: #0D1416; -fx-border-color: #0D1416;");

            kontenVBox.setMaxHeight(scrollPane.getMaxHeight() - 10);
            kontenVBox.setMaxWidth(scrollPane.getMaxWidth() - 10);

            // Membuat main pane
            StackPane mainPane = new StackPane(sideBar, scrollPane);
            mainPane.setStyle("-fx-background-color: #0D1416;");

            Scene scene = new Scene(mainPane, 750, 500);
            scene.setFill(Color.TRANSPARENT);

            // Menanggapi perubahan ukuran layar
            ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
                double stageWidth = stage.getWidth();
                double stageHeight = stage.getHeight();
                // mainPane.setMaxSize(stage.getWidth() - 400, stage.getHeight() - 30);
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
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private void updateTooltipPosition(double x, double y) {
        tooltip.setX(x + 10); // Sesuaikan posisi X agar tidak menutupi cursor
        tooltip.setY(y - 20); // Sesuaikan posisi Y agar tidak menutupi cursor
    }

    private Text createText(String text, String font, String color, double translateX, double translateY) {
        Text welcome = new Text(text);
        welcome.setStyle(font + "-fx-fill: " + color + ";");
        welcome.setTranslateX(translateX);
        welcome.setTranslateY(translateY);
        welcome.toFront();
        return welcome;
    }

    // Fungsi untuk mendapatkan username
    private String getUsername() {
        LoginModel loginModel = new LoginModel();
        return loginModel.getLastActiveUsers();
    }

    private double getSaldo() {
        LoginModel loginModel = new LoginModel();
        return loginModel.getUserSaldo();
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
