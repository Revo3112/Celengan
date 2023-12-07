package View.Dashboard;

import Controller.SceneController;
import Model.LoginModel;
import Model.PantauPemasukanPengeluaran;
import Model.PieChartData;
import Model.TampilkanSemuaTarget;
import javafx.animation.*;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
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
import java.util.List;
import java.util.Locale;

import javax.swing.text.BoxView;

import javafx.stage.Stage;
import javafx.util.Duration;

// class DashboardPage digunakan untuk menampilkan halaman dashboard
public class DashboardPage {

    private Stage stage; // Property stage sebaiknya dideklarasikan sebagai final
    private String username;
    private SceneController sceneController; // Tambahkan property SceneController\
    private double saldo;
    private int userId;
    private Tooltip tooltip = new Tooltip();
    private Scale pieChartScale = new Scale(1, 1);
    private static PantauPemasukanPengeluaran model = new PantauPemasukanPengeluaran();
    private static List<String> keteranganBarangList = model.getKeteranganBarangList();
    private static List<Double> nominalBarangList = model.getNominalBarangList();
    private static List<String> tipeBarangList = model.getTipeBarangList();
    private static List<String> tanggalBarangList = model.getTanggalBarangList();

    public DashboardPage(Stage stage) {
        this.stage = stage;
        this.username = getUsername();
        this.sceneController = new SceneController(stage); // Inisialisasi SceneController
        this.saldo = getSaldo();
        LoginModel loginModel = new LoginModel();
        this.userId = loginModel.getUserId();
    }

    // Menampilkan halaman dashboard
    public void start() {
        // Membuat side bar
        VBox sideBar = ImageLinkPane.createImageLinkVBox(this.stage, sceneController);
        // sideBar.setTranslateX(-stage.getWidth() / 2 + 40);
        sideBar.setAlignment(Pos.CENTER_RIGHT);
        VBox.setVgrow(sideBar, Priority.ALWAYS);

        // Membuat teks welcome
        Text welcome = createText("Selamat Datang,\n", "-fx-font: 40 'Poppins-Regular'; -fx-fill: #FFFFFF;", 0, 0);
        Text name = createText(this.username, "-fx-font: 40 'Poppins-SemiBold'; -fx-fill: #FFFFFF;", 0, 10);
        Text welcome2 = createText("!", "-fx-font: 40 'Poppins-Regular'; -fx-fill: #FFFFFF;", 88, 10);

        // StackPane untuk menampung teks
        StackPane textPane = new StackPane(welcome, name, welcome2);
        textPane.setAlignment(Pos.CENTER_LEFT);
        textPane.setPadding(new Insets(0, 0, 10, 10));

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
        Text Fitur = createText("Fitur - Fitur Celengan", "-fx-font: 30 'Poppins Regular'; -fx-fill: #FFFFFF;", 0, 0);

        // Membuat vbox atas untuk konten tengah
        VBox kontenTengahAtas = new VBox(Fitur);
        kontenTengahAtas.setAlignment(Pos.CENTER_LEFT);
        kontenTengahAtas.setPadding(new Insets(0, 0, 20, 0));

        // Konten kiri
        ImageView Gambarduit = new ImageView(new Image("file:src/Assets/View/Dashboard/Gambarduit.png"));
        Gambarduit.setFitWidth(220);
        Gambarduit.setFitHeight(43);
        Gambarduit.setPreserveRatio(true);

        VBox vboxKiriAtas = new VBox(Gambarduit);
        vboxKiriAtas.setAlignment(Pos.TOP_LEFT);
        vboxKiriAtas.setPadding(new Insets(0, 0, 0, 20));

        // Membulatkan tampilan uang agar tidak muncul E
        long roundedValue = Math.round(this.saldo);

        Text Saldoawal = createText("Saldo Awal", "-fx-font: 15 'Poppins-Regular'; -fx-fill: #064D00;", 0, 0);
        Text Saldoawal2 = createText(formatDuit(roundedValue), "-fx-font: 30 'Poppins SemiBold'; -fx-fill: #ffffff;", 0,
                -6);
        Saldoawal2.setWrappingWidth(200); // Wrap the Saldoawal2 to a certain width
        Saldoawal2.setVisible(true); // Hide the Saldoawal2 initially

        // Create a timeline for the rolling animation
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10),
                new KeyValue(Saldoawal2.layoutYProperty(), -Saldoawal2.getLayoutBounds().getHeight())));
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Trigger the animation when the Saldoawal2 is hovered
        Saldoawal2.setOnMouseEntered(e -> {
            Saldoawal2.setVisible(true);
            timeline.play();
        });

        // Stop the animation and hide the Saldoawal2 when the mouse is not hovering
        Saldoawal2.setOnMouseExited(e -> {
            timeline.stop();
            Saldoawal2.setVisible(false);
        });
        VBox vboxKiriTengah = new VBox(Saldoawal, Saldoawal2);
        vboxKiriTengah.setAlignment(Pos.CENTER_LEFT);
        vboxKiriTengah.setPadding(new Insets(0, 0, 0, 20));

        ImageView UbahSaldo = new ImageView(new Image("file:src/Assets/View/Dashboard/UbahSaldo.png"));
        UbahSaldo.setFitWidth(220);
        UbahSaldo.setFitHeight(35);
        UbahSaldo.setPreserveRatio(true);

        Hyperlink UbahSaldoHyperlink = new Hyperlink();
        UbahSaldoHyperlink.setGraphic(UbahSaldo);
        UbahSaldoHyperlink.setTranslateY(10);
        UbahSaldoHyperlink.setOnMouseClicked(e -> sceneController.switchToTanamUang());

        VBox vboxKiriBawah = new VBox(UbahSaldoHyperlink);
        vboxKiriBawah.setAlignment(Pos.BOTTOM_CENTER);

        VBox penyatuanKonten = new VBox(vboxKiriAtas, vboxKiriTengah, vboxKiriBawah);
        penyatuanKonten.setPadding(new Insets(20, 0, 0, 0));
        penyatuanKonten.setSpacing(4);
        // Saldoawal2.wrappingWidthProperty().bind(penyatuanKonten.widthProperty());

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
            pieChart.setStartAngle(90); // Mengatur sudut awal pie chart
            pieChart.setClockwise(false); // Mengatur arah jarum jam
            pieChart.setMaxSize(350, 100);
            pieChart.setLabelLineLength(0); // Menetapkan panjang garis label
            pieChart.setLegendSide(Side.RIGHT);
            pieChart.setLegendVisible(true);
            pieChart.setLabelsVisible(false);
            pieChart.setAnimated(true);
            pieChartScale.setX(1);
            pieChartScale.setY(1);
            pieChart.setTranslateX(-25);
            pieChart.setTranslateY(0);
            pieChart.getTransforms().add(pieChartScale);

            VBox kontenPieChart = new VBox(pieChart);
            kontenPieChart.setAlignment(Pos.CENTER);

            // Membuat image yang bisa ditekan untuk menuju pantau uang
            ImageView gotoPantauUang = new ImageView(new Image("file:src/Assets/View/Dashboard/GotoPantauUang.png"));
            gotoPantauUang.setFitWidth(40);
            gotoPantauUang.setFitHeight(40);
            gotoPantauUang.setPreserveRatio(true);

            Hyperlink gotoPantauUangHyperlink = new Hyperlink();
            gotoPantauUangHyperlink.setGraphic(gotoPantauUang);
            gotoPantauUangHyperlink.setOnMouseClicked(e -> sceneController.switchToPantauUang());

            VBox kontenGotoPantauUang = new VBox(gotoPantauUangHyperlink);
            kontenGotoPantauUang.setAlignment(Pos.CENTER);
            kontenGotoPantauUang.setPadding(new Insets(0, 0, 0, 0));

            VBox kontenPieChartdanDuit = new VBox(kontenPieChart, kontenGotoPantauUang);
            kontenPieChartdanDuit.setAlignment(Pos.CENTER);

            VBox penyatuanKontenTengah = new VBox(kontenPieChartdanDuit);

            StackPane KontenTengah = new StackPane(penyatuanKontenTengah);
            KontenTengah.setStyle("-fx-background-color: #6E94FA; -fx-background-radius: 20px");

            // Konten Bagian Tengah dari main pane namun untuk Pantau Uang

            Text PantauUang = createText(getTarget(), "-fx-font: 30 'Poppins Bold'; -fx-fill: #FFFFFF;", 0, 0);

            VBox kontenPantauUangTextBVBox = new VBox(PantauUang);
            kontenPantauUangTextBVBox.setAlignment(Pos.CENTER);

            long roundedValuePantauUang = Math.round(getFirstTargetHarga());
            long roundedValuePantauUang2 = Math.round(mendapatkanSaldoUntukMembeliBarang());
            Text progressTextPantauUang = new Text(
                    formatDuit(roundedValuePantauUang2) + " / " + formatDuit(roundedValuePantauUang));
            progressTextPantauUang.setStyle("-fx-font: 14 'Poppins Regular'; -fx-fill: #FFFFFF;");

            VBox taksUang = new VBox(progressTextPantauUang);
            taksUang.setAlignment(Pos.CENTER);

            ProgressBar progressBarPantauUang = new ProgressBar(
                    mendapatkanSaldoUntukMembeliBarang() / getFirstTargetHarga());

            progressBarPantauUang.getStylesheets()
                    .add(getClass().getResource("/Utils/progresBar.css").toExternalForm());

            // Mengatur lebar preferensi ProgressBar
            progressBarPantauUang.setPrefWidth(200);

            // Mengatur style pada VBox yang berisi ProgressBar
            VBox progressUang = new VBox(progressBarPantauUang);
            progressUang.setAlignment(Pos.CENTER);
            progressUang.setPadding(new Insets(5, 0, 0, 0));

            ImageView mainKontenPanenUang = new ImageView(
                    new Image("file:src/Assets/View/Dashboard/KontenPanenUang.png"));
            mainKontenPanenUang.setFitWidth(220);
            mainKontenPanenUang.setFitHeight(43);
            mainKontenPanenUang.setPreserveRatio(true);

            Hyperlink mainKontenPanenUangHyperlink = new Hyperlink();
            mainKontenPanenUangHyperlink.setGraphic(mainKontenPanenUang);
            mainKontenPanenUangHyperlink.setOnMouseClicked(e -> sceneController.switchToPanenUang());

            VBox mainKontenPanenUangVBox = new VBox(mainKontenPanenUangHyperlink);
            mainKontenPanenUangVBox.setAlignment(Pos.BOTTOM_CENTER);
            mainKontenPanenUangVBox.setPadding(new Insets(10, 0, 0, 0));

            VBox kontenPantauUang = new VBox(kontenPantauUangTextBVBox, taksUang, progressUang,
                    mainKontenPanenUangVBox);
            kontenPantauUang.setSpacing(10);
            kontenPantauUang.setPadding(new Insets(10, 0, 0, 0));

            StackPane KontenKanan = new StackPane(kontenPantauUang);
            KontenKanan.setStyle("-fx-background-color: #395FE7; -fx-background-radius: 20px");
            // Membuat bagian bagian konten tangah bawah
            HBox kontenFiturCelengan = new HBox(Kontenkiri, KontenTengah, KontenKanan);
            kontenFiturCelengan.setSpacing(10);

            // Membuat konten tengah bawah untuk konten tengah
            VBox kontenTengahBawah = new VBox(kontenFiturCelengan);

            // Membuat konten bagian tengah untuk main pane
            VBox kontenTengahPane = new VBox(kontenTengahAtas, kontenTengahBawah);
            kontenTengahPane.setPadding(new Insets(0, 0, 0, 30));
            kontenTengahPane.setSpacing(10);

            // ------------------------------------------------------------------------------------------------------------//

            // Konten Tulisan
            Text historiKeuanganmu = createText("Histori Keuanganmu",
                    "-fx-font: 30 'Poppins Regular'; -fx-fill: #FFFFFF;",
                    0, 0);

            ImageView plusIcon = new ImageView("file:src/Assets/View/Dashboard/Plus Sign.png");
            plusIcon.setFitWidth(30);
            plusIcon.setFitHeight(30);
            plusIcon.setPreserveRatio(true);

            Hyperlink plusIconHypleHyperlink = new Hyperlink();
            plusIconHypleHyperlink.setGraphic(plusIcon);
            plusIconHypleHyperlink.setOnMouseClicked(null);

            ImageView pengeluaranImage = new ImageView("file:src/Assets/View/Dashboard/Pengeluaran.png");
            pengeluaranImage.setFitWidth(110);
            pengeluaranImage.setFitHeight(30);
            pengeluaranImage.setPreserveRatio(true);

            ImageView pemasukanImage = new ImageView("file:src/Assets/View/Dashboard/Pemasukan.png");
            pemasukanImage.setFitWidth(110);
            pemasukanImage.setFitHeight(30);
            pemasukanImage.setPreserveRatio(true);

            HBox isiKontenTulisan = new HBox(historiKeuanganmu, plusIconHypleHyperlink, pengeluaranImage,
                    pemasukanImage);
            isiKontenTulisan.setSpacing(15);
            isiKontenTulisan.setPadding(new Insets(5, 0, 0, 25));
            isiKontenTulisan.setAlignment(Pos.CENTER_LEFT);

            VBox kontenTulisan = new VBox(isiKontenTulisan);

            // Membuat konten histori keuangan
            // Yang perlu diambil di database adalah keterangan, nominal, tipe menggunakan
            // gambar, dan tanggal

            VBox kontenHistoriKeuangan = new VBox();
            kontenHistoriKeuangan.setSpacing(10);
            kontenHistoriKeuangan.setSpacing(10);
            for (int i = 0; i < getTotalBarangyangDIbeli(); i++) {
                String keterangan = keteranganBarangList.get(i);
                double nominal = nominalBarangList.get(i);
                String tipe = tipeBarangList.get(i);
                String tanggal = tanggalBarangList.get(i);

                Text keteranganText = createText(keterangan, "-fx-font: 15 'Poppins Bold'; -fx-fill: #FFFFFF;",
                        0, 0);

                VBox keteranganStackPane = new VBox(keteranganText);
                keteranganStackPane.setAlignment(Pos.CENTER_LEFT);

                Long roundedValueNominal = Math.round(nominal);
                Text nominalText = createText(formatDuit(roundedValueNominal),
                        "-fx-font: 15 'Poppins Bold'; -fx-fill: #798F97;", 0, 0);

                ImageView kondisi = new ImageView();
                if (tipe.equals("pemasukan")) {
                    kondisi = new ImageView("file:src/Assets/View/Dashboard/PemasukanKondisi.png");
                    kondisi.setFitHeight(35);
                    kondisi.setFitWidth(100);
                    kondisi.setPreserveRatio(true);
                } else {
                    kondisi = new ImageView("file:src/Assets/View/Dashboard/PengeluaranKondisi.png");
                    kondisi.setFitHeight(35);
                    kondisi.setFitWidth(100);
                    kondisi.setPreserveRatio(true);
                }

                HBox gambarKondisidanNominal = new HBox(nominalText, kondisi);
                gambarKondisidanNominal.setSpacing(2);
                VBox nominalStackPane = new VBox(gambarKondisidanNominal);
                nominalStackPane.setAlignment(Pos.CENTER);

                Text tanggalText = createText(tanggal, "-fx-font: 15 'Poppins Bold'; -fx-fill: #798F97;", 0, 0);

                VBox tanggalTextPane = new VBox(tanggalText);
                tanggalTextPane.setAlignment(Pos.CENTER_RIGHT);

                HBox kontenHistoriKeuanganBarang = new HBox(keteranganStackPane, nominalStackPane,
                        gambarKondisidanNominal,
                        tanggalTextPane);
                kontenHistoriKeuanganBarang.setSpacing(80);
                kontenHistoriKeuanganBarang.setPadding(new Insets(10, 0, 10, 25));
                kontenHistoriKeuanganBarang.setAlignment(Pos.CENTER);
                kontenHistoriKeuanganBarang.setStyle("-fx-background-color: #213339; -fx-background-radius: 30px");

                kontenHistoriKeuangan.getChildren().add(kontenHistoriKeuanganBarang);
            }

            // Membuat konten bagian bawah untuk main pane
            VBox kontenBawahPane = new VBox(kontenTulisan, kontenHistoriKeuangan);
            kontenBawahPane.setPadding(new Insets(0, 0, 0, 10));
            kontenBawahPane.setSpacing(10);

            // VBox untuk menampung konten atas, tengah, dan bawah
            VBox kontenVBox = new VBox(kontenAtasPane, kontenTengahPane, kontenBawahPane);
            kontenVBox.setSpacing(2);
            kontenVBox.setPadding(new Insets(0, 10, 0, 0));
            kontenVBox.setStyle("-fx-background-color: #141F23;");
            kontenVBox.setMaxHeight(this.stage.getHeight());

            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(kontenVBox);
            scrollPane.setFitToHeight(true);
            scrollPane.setMaxHeight(this.stage.getHeight() + 100);
            scrollPane.setMaxWidth(this.stage.getWidth() - 100);
            scrollPane.getStylesheets().add(getClass().getResource("/Utils/ScrollBar.css").toExternalForm());

            String scrollbarStyle = "-fx-background-color: #141F23;";
            scrollbarStyle += "-fx-background-color: #0B1214;";
            scrollbarStyle += "-fx-background-insets: 0;";
            scrollbarStyle += "-fx-padding: 0;";
            scrollbarStyle += "-fx-background-radius: 20px;";

            scrollPane.setId("custom-scrollbar");
            // scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollPane.setStyle(scrollbarStyle);

            StackPane mainPane = new StackPane(scrollPane);
            mainPane.setStyle("-fx-background-color: #141F23;-fx-background-radius: 30px;");
            mainPane.setMaxHeight(this.stage.getHeight() + 240);
            mainPane.setMaxWidth(this.stage.getWidth() + 400);
            mainPane.setPadding(new Insets(0, 10, 0, 0));
            mainPane.setAlignment(Pos.CENTER);

            HBox rightBar = new HBox();

            // Mengatur binding fitToHeight dan fitToWidth
            scrollPane.setFitToWidth(true);

            HBox penggabunganMainPanedenganSideBar = new HBox(sideBar, mainPane);
            penggabunganMainPanedenganSideBar.setStyle("-fx-background-color: #0B1214;");
            HBox fullPane = new HBox(penggabunganMainPanedenganSideBar, rightBar);
            // Set horizontal grow priority for mainPane
            // HBox.setHgrow(mainPaneHBox, Priority.ALWAYS);
            fullPane.setStyle("-fx-background-color: #0B1214");

            Scene scene = new Scene(fullPane, this.stage.getWidth(), this.stage.getHeight());

            this.stage.setScene(scene);
            this.stage.setMaximized(true);
            this.stage.show(); // Tetapkan setelah styling selesai

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private void updateTooltipPosition(double x, double y) {
        tooltip.setX(x + 10); // Sesuaikan posisi X agar tidak menutupi cursor
        tooltip.setY(y - 20); // Sesuaikan posisi Y agar tidak menutupi cursor
    }

    private Text createText(String text, String style, double translateX, double translateY) {
        Text welcome = new Text(text);
        welcome.setStyle(style);
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

    private double getFirstTargetHarga() {
        TampilkanSemuaTarget tampilkanSemuaTarget = new TampilkanSemuaTarget();
        return tampilkanSemuaTarget.ambilHargaDataPertama(this.userId);

    }

    private int getTotalBarangyangDIbeli() {
        return model.banyakDatadiTransac();
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

    // Fungsi untuk mengambil data yang paling pertama di database target
    private String getTarget() {
        TampilkanSemuaTarget target1 = new TampilkanSemuaTarget();
        return target1.ambilDataNamaTargetPertama(this.userId);
    }

    private long mendapatkanSaldoUntukMembeliBarang() {
        TampilkanSemuaTarget tampilkanSemuaTarget = new TampilkanSemuaTarget();
        return tampilkanSemuaTarget.ambilSaldodanBatasKritis();

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
    public static VBox createImageLinkVBox(Stage stage, SceneController sceneController) {
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
        homePageImageView.setPreserveRatio(true);
        homePageImageView.setTranslateX(5);
        tanamUangImageView.setFitWidth(190);
        tanamUangImageView.setFitHeight(35);
        tanamUangImageView.setPreserveRatio(true);
        pantauUangImageView.setFitWidth(190);
        pantauUangImageView.setFitHeight(35);
        pantauUangImageView.setPreserveRatio(true);
        panenUangImageView.setFitWidth(190);
        panenUangImageView.setFitHeight(35);
        panenUangImageView.setPreserveRatio(true);
        modeUser.setFitWidth(190);
        modeUser.setFitHeight(35);
        modeUser.setPreserveRatio(true);
        logOut.setFitWidth(200);
        logOut.setFitHeight(60);
        logOut.setPreserveRatio(true);

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

        Rectangle region = new Rectangle(220, 85);
        region.setStyle("-fx-background-radius: 30 0 0 30;");
        region.setTranslateY(-20);
        region.setFill(Color.valueOf("#141F23"));
        homeHyperlink.setTranslateX(10);
        Group aktifGroup = new Group(region, homeHyperlink);
        aktifGroup.setTranslateX(10);
        // Membuat masing - masing Vbox dan menambahkan Hyperlink ke dalamnya
        VBox homeVBox = new VBox(aktifGroup);
        homeVBox.setAlignment(Pos.CENTER_RIGHT);
        VBox tanamUangVBox = new VBox(tanamUangHyperlink);
        tanamUangVBox.setAlignment(Pos.CENTER);
        VBox pantauUangVBox = new VBox(pantauUangHyperlink);
        pantauUangVBox.setAlignment(Pos.CENTER);
        VBox panenUangVBox = new VBox(panenUangHyperlink);
        panenUangVBox.setAlignment(Pos.CENTER);
        VBox modeUserVBox = new VBox(modeUserHyperlink);
        modeUserVBox.setAlignment(Pos.CENTER);
        VBox logOutVBox = new VBox(logOutHyperlink);
        logOutVBox.setAlignment(Pos.CENTER);
        // Membuat VBox dan menambahkan Hyperlink ke dalamnya
        VBox kontenSide = new VBox(logoImageView, homeVBox, tanamUangVBox, pantauUangVBox, panenUangVBox,
                modeUserVBox,
                logOutVBox);
        kontenSide.setSpacing(50);
        kontenSide.setAlignment(Pos.CENTER_RIGHT);
        kontenSide.setPadding(new Insets(40, 0, 0, 60));

        return kontenSide;

    }

    private static Hyperlink createHyperlinkWithImageView(ImageView imageView) {
        Hyperlink hyperlink = new Hyperlink();
        hyperlink.setGraphic(imageView);
        return hyperlink;
    }

}

class rightBar {
    public static HBox createRightBar(Stage stage, SceneController sceneController) {
        return null;
    }
}