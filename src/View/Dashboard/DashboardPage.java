package View.Dashboard;

import Controller.SceneController;
import Model.LoginModel;
import Model.PieChartData;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Hyperlink;
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
import javafx.scene.transform.Scale;
import java.text.NumberFormat;
import java.util.Locale;

import javafx.stage.Stage;
import javafx.stage.StageStyle;

// class DashboardPage digunakan untuk menampilkan halaman dashboard
public class DashboardPage {

    private Stage stage; // Property stage sebaiknya dideklarasikan sebagai final
    private String username;
    private SceneController sceneController; // Tambahkan property SceneController\
    private double saldo;
    private Tooltip tooltip = new Tooltip();
    private Scale pieChartScale = new Scale(1.2, 1.2);

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
        // sideBar.setTranslateX(-stage.getWidth() / 2 + 40);
        sideBar.setAlignment(Pos.CENTER_RIGHT);
        sideBar.setPadding(new Insets(0, 0, 0, 50));
        // Membuat teks welcome
        Text welcome = createText("Selamat Datang,\n", "-fx-font: 40 'Poppins-Regular';",
                "#FFFFFF", 0, 0);
        Text name = createText(this.username, "-fx-font: 40 'Poppins-SemiBold'", "#ffffff", 0, 0);
        Text welcome2 = createText("!", "-fx-font: 40 'Poppins-Reguler", "#ffffff", 0, 0);

        // StackPane untuk menampung teks
        StackPane textPane = new StackPane(welcome, name, welcome2);
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

        // Membulatkan tampilan uang agar tidak muncul E
        long roundedValue = Math.round(this.saldo);

        Text Saldoawal = createText("Saldo Awal", "-fx-font: 15 'Poppins-Regular';",
                "#064D00", 0, 0);
        Text Saldoawal2 = createText(formatDuit(roundedValue), "-fx-font: 30 'Poppins-bold';",
                "#ffffff", 0, -10);
        VBox vboxKiriTengah = new VBox(Saldoawal, Saldoawal2);
        vboxKiriTengah.setAlignment(Pos.CENTER_LEFT);
        vboxKiriTengah.setPadding(new Insets(0, 0, 0, 20));

        ImageView UbahSaldo = new ImageView(new Image("file:src/Assets/View/Dashboard/UbahSaldo.png"));
        UbahSaldo.setFitWidth(220);
        UbahSaldo.setFitHeight(43);

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

                data.getNode().setStyle("-fx-label-line-length: -100px;");
            }
            pieChart.setLegendVisible(true);
            pieChart.setStartAngle(90); // Mengatur sudut awal pie chart
            pieChart.setClockwise(false); // Mengatur arah jarum jam
            pieChart.setMaxSize(500, 500);
            pieChart.setLabelLineLength(0); // Menetapkan panjang garis label
            pieChart.setLabelsVisible(true); // Menyembunyikan label
            pieChart.setLegendSide(Side.RIGHT);

            VBox kontenPieChart = new VBox(pieChart);
            kontenPieChart.setAlignment(Pos.CENTER_LEFT);

            // Membuat image yang bisa ditekan untuk menuju pantau uang
            ImageView gotoPantauUang = new ImageView(new Image("file:src/Assets/View/Dashboard/GotoPantauUang.png"));
            gotoPantauUang.setFitWidth(40);
            gotoPantauUang.setFitHeight(40);
            Hyperlink gotoPantauUangHyperlink = new Hyperlink();
            gotoPantauUangHyperlink.setGraphic(gotoPantauUang);
            gotoPantauUangHyperlink.setOnMouseClicked(e -> sceneController.switchToPantauUang());

            VBox kontenGotoPantauUang = new VBox(gotoPantauUangHyperlink);
            kontenGotoPantauUang.setAlignment(Pos.TOP_RIGHT);
            kontenGotoPantauUang.setPadding(new Insets(10, 10, 10, 10));

            HBox kontenPieChartdanDuit = new HBox(kontenPieChart, kontenGotoPantauUang);
            kontenPieChartdanDuit.setAlignment(Pos.CENTER);
            HBox.setHgrow(kontenPieChart, Priority.ALWAYS);

            VBox penyatuanKontenTengah = new VBox(kontenPieChartdanDuit);

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
            kontenVBox.setStyle("-fx-background-color: #141F23; -fx-border-color: none;-fx-background-radius: 20px;");

            // Membuat konten bagian tengah
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(kontenVBox);
            scrollPane.setFitToWidth(true);
            scrollPane.setTranslateX(70);
            scrollPane.setTranslateY(0);
            scrollPane.setStyle("-fx-background-color: #0D1416; -fx-border-color: #0D1416;");

            // Menentukan gaya css untuk scroll bar
            // warna scroll bar
            String scrollbarStyle = "-fx-background-color: #141F23;-fx-background-radius: 20px;";
            scrollbarStyle += "-fx-background-color: #0B1214;"; // Warna slider
            scrollbarStyle += "-fx-background-insets: 0;"; // Menghapus bayangan
            scrollbarStyle += "-fx-padding: 0;"; // Menghapus padding

            // Menetapkan ID agar dapat diakses menggunakan lookup
            scrollPane.setId("custom-scrollbar");
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollPane.setStyle(scrollbarStyle); // Mengubah gaya css scroll bar

            // Membuat main pane
            StackPane mainPane = new StackPane(scrollPane);
            mainPane.setStyle(
                    "-fx-background-color: #0D1416; -fx-background-radius: 20px;");
            mainPane.setPadding(new Insets(20, 0, 0, -80));

            HBox mainPaneHBox = new HBox(mainPane);
            HBox.setHgrow(mainPane, Priority.ALWAYS);

            HBox rightBar = new HBox();

            HBox fullPane = new HBox(sideBar, mainPaneHBox, rightBar);
            // Set horizontal grow priority for mainPane
            HBox.setHgrow(mainPaneHBox, Priority.ALWAYS);
            HBox.setHgrow(rightBar, Priority.ALWAYS);

            VBox layout = new VBox(fullPane);
            layout.setStyle("-fx-background-color: #0D1416; -fx-border-color: none;");

            Scene scene = new Scene(layout, this.stage.getWidth(), this.stage.getHeight());

            this.stage.setScene(scene);
            this.stage.setMaximized(true);
            this.stage.initStyle(StageStyle.TRANSPARENT);

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
    }

    private static String formatDuit(double nilai) {
        // Membuat instance NumberFormat untuk mata uang Indonesia (IDR)
        Locale locale = Locale.forLanguageTag("id-ID");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        // Mengatur nilai
        String formattedValue = numberFormat.format(nilai);

        return formattedValue;
    }
}

class ImageLinkPane {
    public static HBox createImageLinkVBox(Stage stage, SceneController sceneController) {
        // Gunakan ImageView untuk semua pilihan di Sidebar
        ImageView logoImageView = new ImageView(new Image("file:src/Assets/View/Dashboard/Logo.png"));
        logoImageView.setFitWidth(200);
        logoImageView.setFitHeight(50);
        logoImageView.setTranslateY(-stage.getHeight() / 2 + 270);
        logoImageView.setTranslateX(-stage.getWidth() / 2 + 485);

        ImageView homePageImageView = new ImageView(new Image("file:src/Assets/View/Dashboard/HomePage.png"));
        ImageView tanamUangImageView = new ImageView(new Image("file:src/Assets/View/Dashboard/Tanam Uang.png"));
        ImageView pantauUangImageView = new ImageView(new Image("file:src/Assets/View/Dashboard/Pantau Uang.png"));
        ImageView panenUangImageView = new ImageView(new Image("file:src/Assets/View/Dashboard/Panen Uang.png"));
        ImageView modeUser = new ImageView("file:src/Assets/View/Dashboard/Mode User.png");
        ImageView logOut = new ImageView("file:src/Assets/View/Dashboard/Log Out.png");

        // Menyesuaikan ukuran ImageView
        homePageImageView.setFitWidth(190);
        homePageImageView.setFitHeight(35);
        tanamUangImageView.setFitWidth(190);
        tanamUangImageView.setFitHeight(35);
        pantauUangImageView.setFitWidth(190);
        pantauUangImageView.setFitHeight(35);
        panenUangImageView.setFitWidth(190);
        panenUangImageView.setFitHeight(35);
        modeUser.setFitWidth(190);
        modeUser.setFitHeight(35);
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

        Rectangle region = new Rectangle(200, 85);
        region.setArcHeight(30);
        region.setArcWidth(30);
        region.setTranslateY(-20);
        region.setFill(Color.valueOf("#141F23"));
        Group aktifGroup = new Group(region, homeHyperlink);
        // Membuat masing - masing Vbox dan menambahkan Hyperlink ke dalamnya
        VBox homeVBox = new VBox(aktifGroup);
        homeVBox.setAlignment(Pos.CENTER);
        VBox tanamUangVBox = new VBox(tanamUangHyperlink);
        tanamUangVBox.setAlignment(Pos.TOP_CENTER);
        VBox pantauUangVBox = new VBox(pantauUangHyperlink);
        pantauUangVBox.setAlignment(Pos.TOP_CENTER);
        VBox panenUangVBox = new VBox(panenUangHyperlink);
        panenUangVBox.setAlignment(Pos.TOP_CENTER);
        VBox modeUserVBox = new VBox(modeUserHyperlink);
        modeUserVBox.setAlignment(Pos.TOP_CENTER);
        VBox logOutVBox = new VBox(logOutHyperlink);
        logOutVBox.setAlignment(Pos.TOP_CENTER);
        // Membuat VBox dan menambahkan Hyperlink ke dalamnya
        VBox kontenSide = new VBox(logoImageView, homeVBox, tanamUangVBox, pantauUangVBox, panenUangVBox,
                modeUserVBox,
                logOutVBox);
        kontenSide.setSpacing(50);
        kontenSide.setAlignment(Pos.TOP_CENTER);
        kontenSide.setPadding(new Insets(40, 2, 0, 0));

        // Membuat HBox dan menambahkan VBox ke dalamnya
        HBox SideBar = new HBox(kontenSide);
        SideBar.setAlignment(Pos.CENTER);

        return SideBar;

    }

    private static Hyperlink createHyperlinkWithImageView(ImageView imageView) {
        Hyperlink hyperlink = new Hyperlink();
        hyperlink.setGraphic(imageView);
        return hyperlink;
    }

}
