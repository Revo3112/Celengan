package View.Dashboard;

import Controller.SceneController;
import Model.BatasKritis;
import Model.LoginModel;
import Model.PantauPemasukanPengeluaran;
import Model.PieChartData;
import Model.RefreshViewDashboard;
import Model.TampilkanSemuaTarget;
import View.Dashboard.DashboardPage.CustomItem.CustomItemConverter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.util.StringConverter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import javafx.stage.Stage;
import javafx.util.Callback;

// class DashboardPage digunakan untuk menampilkan halaman dashboard
public class DashboardPage {

    private Stage stage; // Property stage sebaiknya dideklarasikan sebagai final
    private String username;
    private SceneController sceneController; // Tambahkan property SceneController\
    private double saldo;
    private int userId;
    private Tooltip tooltip = new Tooltip();
    private PantauPemasukanPengeluaran model;
    private RefreshViewDashboard refreshViewDashboard;
    private List<String> keteranganBarangList;
    private List<Double> nominalBarangList;
    private List<String> tipeBarangList;
    private List<String> tanggalBarangList;
    public StackPane root = new StackPane();
    StackPane mainPane = new StackPane();

    public DashboardPage(Stage stage) {
        this.stage = stage;
        this.username = getUsername();
        this.sceneController = new SceneController(stage); // Inisialisasi SceneController
        this.saldo = getSaldo();
        LoginModel loginModel = new LoginModel();
        this.userId = loginModel.getUserId();
        this.model = new PantauPemasukanPengeluaran();
        this.keteranganBarangList = model.getKeteranganBarangList();
        this.nominalBarangList = model.getNominalBarangList();
        this.tipeBarangList = model.getTipeBarangList();
        this.tanggalBarangList = model.getTanggalBarangList();
    }

    // Menampilkan halaman dashboard
    public void start() {
        // Membuat side bar
        ImageLinkPane imageLinkPane = new ImageLinkPane(this); // Mengirim referensi DashboardPage ke ImageLinkPane
        VBox sideBar = imageLinkPane.createImageLinkVBox(this.stage, sceneController);
        sideBar.setAlignment(Pos.CENTER);
        sideBar.setMinWidth(242);
        VBox.setVgrow(sideBar, Priority.ALWAYS);

        // Membuat teks welcome
        Text welcome = createText("Selamat Datang,", "-fx-font: 40 'Poppins Regular'; -fx-fill: #FFFFFF;", 0, 0);
        Text name = createText(this.username + "!", "-fx-font: 40 'Poppins SemiBold'; -fx-fill: #FFFFFF;", 0, 0);

        StackPane welcomeText = new StackPane(welcome);
        welcomeText.setAlignment(Pos.BOTTOM_LEFT);
        welcomeText.setPadding(new Insets(40, 0, 0, 0));
        welcomeText.setMaxHeight(20);

        StackPane nameText = new StackPane(name);
        nameText.setAlignment(Pos.TOP_LEFT);
        StackPane.setMargin(nameText, new Insets(0, 0, 20, 0));

        VBox penggabunganAntaraWelcomedanNama = new VBox(welcomeText, nameText);
        penggabunganAntaraWelcomedanNama.setAlignment(Pos.CENTER_LEFT);

        // StackPane untuk menampung teks
        StackPane textPane = new StackPane(penggabunganAntaraWelcomedanNama);
        textPane.setAlignment(Pos.CENTER_LEFT);
        textPane.setPadding(new Insets(0, 0, 10, 10));

        // Menambahkan gambar
        ImageView contentImageView = new ImageView(new Image("file:src/Assets/View/Dashboard/Content.png"));
        contentImageView.setFitWidth(400);
        contentImageView.setFitHeight(300);
        contentImageView.setPreserveRatio(true);

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
        Text Saldoawal2 = createText(formatDuit(roundedValue), "-fx-font: 25 'Poppins SemiBold'; -fx-fill: #ffffff;", 0,
                -6);

        String saldoTampilan = Saldoawal2.getText().length() > 13 ? Saldoawal2.getText().substring(0, 13) + "..."
                : Saldoawal2.getText();

        Label labelSaldo = new Label(saldoTampilan);
        labelSaldo.setStyle("-fx-font: 25 'Poppins SemiBold'; -fx-text-fill: #ffffff;"); // Set the style

        // Tambahkan tooltip yang menampilkan saldo lengkap
        Tooltip tooltipSaldo = new Tooltip(Saldoawal2.getText());
        Tooltip.install(labelSaldo, tooltipSaldo);

        VBox vboxKiriTengah = new VBox(Saldoawal, labelSaldo);
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
        Kontenkiri.setMaxHeight(200);
        // Konten Tengah

        // Membuat pie chart
        try {
            PieChart pieChart = new PieChart();
            ObservableList<PieChart.Data> pieChartData = PieChartData.pieChartData();
            pieChart.setData(pieChartData);
            for (PieChart.Data data : pieChartData) {
                Tooltip.install(data.getNode(), tooltip);
                System.out.println(data.getPieValue());

                data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                    double pieValue = data.getPieValue();
                    double nominalValue = PieChartData.totalpengeluaran() * ((pieValue));

                    // Format nominalValue as currency
                    String formattedNominal = formatDuit(nominalValue);

                    // Calculate the percentage based on the nominal value and the total saldo
                    double percentage = (pieValue * 100);

                    if (percentage < 0) {
                        percentage = percentage * -1;
                    }

                    String tooltipText = String.format("%.1f%% (%s)", percentage, formattedNominal);

                    tooltip.setText(tooltipText);
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

            pieChart.setStartAngle(90);
            pieChart.setClockwise(false);
            pieChart.setMaxSize(350, 100);
            pieChart.setLabelLineLength(10);
            pieChart.setLegendSide(Side.RIGHT);
            pieChart.setLegendVisible(false);
            pieChart.setLabelsVisible(true);
            pieChart.setAnimated(true);
            pieChart.setCenterShape(true);
            pieChart.getStylesheets()
                    .add(getClass().getResource("/Utils/PieChart.css").toExternalForm());
            // pieChart.setTranslateX(-50);
            // pieChart.setTranslateY(-20);

            Scale pieChartScale = new Scale(1, 1);
            pieChart.setTranslateX(0);
            pieChart.setTranslateY(0);
            pieChart.getTransforms().add(pieChartScale);

            StackPane pieChartPane = new StackPane(pieChart);
            pieChartPane.setAlignment(Pos.TOP_LEFT);
            pieChartPane.setPadding(new Insets(0, 0, 0, 0));
            pieChartPane.setMaxSize(330, 100);

            VBox kontenPieChart = new VBox(pieChartPane);
            kontenPieChart.setAlignment(Pos.CENTER);
            kontenPieChart.setPadding(new Insets(0, 0, 0, 0));

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
            KontenTengah.setStyle("-fx-background-color: #0D1416; -fx-background-radius: 20px");
            KontenTengah.setMaxHeight(300);

            // Konten Bagian Tengah dari main pane namun untuk Pantau Uang

            Text PantauUang = createText(getTarget(), "-fx-font: 30 'Poppins Bold'; -fx-fill: #FFFFFF;", 0, 0);

            VBox kontenPantauUangTextBVBox = new VBox(PantauUang);
            kontenPantauUangTextBVBox.setAlignment(Pos.CENTER);

            long roundedValuePantauUang = Math.round(getFirstTargetHarga());
            long roundedValuePantauUang2 = Math.round(mendapatkanSaldoUntukMembeliBarang());
            if (roundedValuePantauUang2 > roundedValuePantauUang) {
                roundedValuePantauUang2 = roundedValuePantauUang;
            } else if (roundedValuePantauUang2 < 0) {
                roundedValuePantauUang2 = 0;
            }
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
            progressBarPantauUang.setPrefWidth(210);

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
            KontenKanan.setMaxHeight(200);
            // Membuat bagian bagian konten tangah bawah
            HBox kontenFiturCelengan = new HBox(Kontenkiri, KontenTengah, KontenKanan);
            kontenFiturCelengan.setSpacing(10);
            HBox.setHgrow(kontenFiturCelengan, Priority.ALWAYS);
            HBox.setHgrow(Kontenkiri, Priority.ALWAYS);
            HBox.setHgrow(KontenTengah, Priority.ALWAYS);
            HBox.setHgrow(KontenKanan, Priority.ALWAYS);

            // Membuat konten tengah bawah untuk konten tengah
            VBox kontenTengahBawah = new VBox(kontenFiturCelengan);
            kontenTengahBawah.fillWidthProperty();
            // Membuat konten bagian tengah untuk main pane
            VBox kontenTengahPane = new VBox(kontenTengahAtas, kontenTengahBawah);
            kontenTengahPane.setPadding(new Insets(0, 0, 0, 30));
            kontenTengahPane.setSpacing(10);
            kontenTengahPane.setAlignment(Pos.CENTER_LEFT);

            // ------------------------------------------------------------------------------------------------------------//

            // Konten Tulisan
            Text historiKeuanganmu = createText("Histori Keuanganmu",
                    "-fx-font: 30 'Poppins Regular'; -fx-fill: #FFFFFF;",
                    0, 0);

            ImageView pengeluaranImage = new ImageView("file:src/Assets/View/Dashboard/Pengeluaran.png");
            pengeluaranImage.setFitWidth(110);
            pengeluaranImage.setFitHeight(30);
            pengeluaranImage.setPreserveRatio(true);

            ImageView pemasukanImage = new ImageView("file:src/Assets/View/Dashboard/Pemasukan.png");
            pemasukanImage.setFitWidth(110);
            pemasukanImage.setFitHeight(30);
            pemasukanImage.setPreserveRatio(true);

            // kontenHistorikeuangan

            VBox kontenHistoriKeuangan = new VBox();
            kontenHistoriKeuangan.setSpacing(10);
            kontenHistoriKeuangan.setSpacing(10);

            // Membuat combo box
            ComboBox<CustomItem> comboBox = new ComboBox<>();
            comboBox.getItems().addAll(
                    new CustomItem("Pengeluaran", Color.valueOf("#FB5050")),
                    new CustomItem("Pemasukan", Color.valueOf("#68FB50")));

            comboBox.setConverter(new CustomItemConverter());
            comboBox.setPromptText("Pilih kategori");
            comboBox.setStyle(
                    "-fx-font: 15 'Poppins Regular'; -fx-text-fill: #ffffff;");
            comboBox.setCellFactory(new Callback<>() {
                @Override
                public ListCell<CustomItem> call(javafx.scene.control.ListView<CustomItem> param) {
                    return new ListCell<>() {
                        private final HBox graphic = new HBox();

                        {
                            graphic.setSpacing(10);
                        }

                        @Override
                        protected void updateItem(CustomItem item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || item == null) {
                                setGraphic(null);
                                setText(null);
                                getStyleClass().removeAll("income", "expense");
                                setBackground(null);
                            } else {
                                setText(item.getText());
                                setFont(Font.font("Poppins", FontWeight.BOLD, 10));
                                getStyleClass().removeAll("income", "expense");
                                getStyleClass().add(item.getText().toLowerCase());

                                // Set background color based on item type
                                if ("Pemasukan".equals(item.getText())) {
                                    setBackground(
                                            new Background(new BackgroundFill(Color.valueOf("#68FB50"), null, null)));
                                } else if ("Pengeluaran".equals(item.getText())) {
                                    setBackground(
                                            new Background(new BackgroundFill(Color.valueOf("#FB5050"), null, null)));
                                }

                                setGraphic(graphic);
                            }
                        }
                    };
                }
            });

            comboBox.setOnAction(event -> {
                CustomItem selectedItem = comboBox.getSelectionModel().getSelectedItem();
                System.out.println("Selected Option: " + selectedItem.getText());

                // Clear existing stylesheets
                comboBox.getStylesheets().clear();

                kontenHistoriKeuangan.getChildren().clear();
                // Tambahkan gaya baru berdasarkan kategori yang dipilih
                if ("Pemasukan".equals(selectedItem.getText())) {
                    kontenHistoriKeuangan.getChildren().add(refreshViewHBox("Pemasukan"));
                    comboBox.getStylesheets().add(getClass().getResource("/Utils/ComboBox.css").toExternalForm());
                } else {
                    kontenHistoriKeuangan.getChildren().add(refreshViewHBox("Pengeluaran"));
                    comboBox.getStylesheets().add(getClass().getResource("/Utils/ComboBox2.css").toExternalForm());
                }
            });

            comboBox.getStylesheets().add(getClass().getResource("/Utils/ComboBoxIdle.css").toExternalForm());

            HBox isiKontenTulisan = new HBox(historiKeuanganmu, comboBox);
            isiKontenTulisan.setSpacing(15);
            isiKontenTulisan.setPadding(new Insets(5, 0, 0, 25));
            isiKontenTulisan.setAlignment(Pos.CENTER_LEFT);

            VBox kontenTulisan = new VBox(isiKontenTulisan);

            // Membuat konten histori keuangan
            // Yang perlu diambil di database adalah keterangan, nominal, tipe menggunakan
            // gambar, dan tanggal

            for (int i = 0; i < getTotalBarangyangDIbeli(); i++) {
                String keterangan = keteranganBarangList.get(i);
                double nominal = nominalBarangList.get(i);
                String tipe = tipeBarangList.get(i);
                String tanggal = tanggalBarangList.get(i);

                Text keteranganText = createText(keterangan, "-fx-font: 15 'Poppins Bold'; -fx-fill: #FFFFFF;",
                        0, 0);

                String tampilanKeterangan = keteranganText.getText().length() > 10
                        ? keteranganText.getText().substring(0, 10) + "..."
                        : keteranganText.getText();

                Label labelKeterangan = new Label(tampilanKeterangan);
                labelKeterangan.setStyle("-fx-font: 15 'Poppins SemiBold'; -fx-text-fill: #ffffff;"); // Set the style

                // Tambahkan tooltip yang menampilkan saldo lengkap
                Tooltip tooltipKeterangan = new Tooltip(keteranganText.getText());
                Tooltip.install(labelKeterangan, tooltipKeterangan);

                VBox keteranganStackPane = new VBox(labelKeterangan);
                keteranganStackPane.setAlignment(Pos.CENTER_LEFT);

                Long roundedValueNominal = Math.round(nominal);
                Text nominalText = createText(formatDuit(roundedValueNominal),
                        "-fx-font: 15 'Poppins Bold'; -fx-fill: #798F97;", 0, 0);

                StackPane nominalStackPane = new StackPane(nominalText);
                nominalStackPane.setAlignment(Pos.CENTER_RIGHT);
                nominalStackPane.setPadding(new Insets(0, 0, 0, 60));

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
                StackPane gambarKondisi = new StackPane(kondisi);
                gambarKondisi.setAlignment(Pos.CENTER_LEFT);
                gambarKondisi.setPadding(new Insets(0, 0, 0, 60));

                HBox gambarKondisidanNominal = new HBox(nominalStackPane, gambarKondisi);
                gambarKondisidanNominal.setSpacing(5);
                gambarKondisidanNominal.setAlignment(Pos.CENTER);
                HBox.setHgrow(gambarKondisidanNominal, Priority.ALWAYS);

                Text tanggalText = createText(formatTanggal(tanggal), "-fx-font: 15 'Poppins Bold'; -fx-fill: #798F97;",
                        0, 0);

                StackPane tanggalTextPane = new StackPane(tanggalText);
                tanggalTextPane.setAlignment(Pos.CENTER_RIGHT);

                HBox kontenHistoriKeuanganBarang = new HBox(keteranganStackPane, nominalStackPane,
                        gambarKondisidanNominal,
                        tanggalTextPane);

                kontenHistoriKeuanganBarang.setSpacing(50);
                kontenHistoriKeuanganBarang.setPadding(new Insets(10, 25, 10, 40));
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
            scrollPane.setFitToWidth(true);
            scrollPane.setMaxHeight(this.stage.getHeight() - 100);
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

            mainPane.getChildren().add(scrollPane);
            mainPane.setStyle("-fx-background-color: #141F23;-fx-background-radius: 30px;");
            mainPane.setMaxHeight(this.stage.getHeight() - 20);
            mainPane.setMinWidth(this.stage.getWidth() - 370);
            mainPane.setPadding(new Insets(0, 10, 0, 0));
            mainPane.setAlignment(Pos.CENTER);

            RightBar rightBar = new RightBar(this.saldo, this.userId);
            HBox Rightbar = rightBar.createRightBar(this.stage, sceneController);
            Rightbar.setAlignment(Pos.CENTER_RIGHT);
            // Mengatur binding fitToHeight dan fitToWidth

            HBox penggabunganMainPanedenganSideBar = new HBox(sideBar, mainPane);
            penggabunganMainPanedenganSideBar.setStyle("-fx-background-color: #0B1214;");
            penggabunganMainPanedenganSideBar.setPadding(new Insets(10, 0, 0, 0));
            HBox fullPane = new HBox(penggabunganMainPanedenganSideBar, Rightbar);
            // Set horizontal grow priority for mainPane
            HBox.setHgrow(penggabunganMainPanedenganSideBar, Priority.ALWAYS);
            fullPane.setStyle("-fx-background-color: #0B1214");

            root.getChildren().add(fullPane);

            Scene scene = new Scene(root, this.stage.getWidth(), this.stage.getHeight());

            this.stage.setScene(scene);
            this.stage.show(); // Tetapkan setelah styling selesai

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private void updateTooltipPosition(double x, double y) {
        tooltip.setX(x + 10); // Sesuaikan posisi X agar tidak menutupi cursor
        tooltip.setY(y - 20); // Sesuaikan posisi Y agar tidak menutupi cursor
    }

    private static Text createText(String text, String style, double translateX, double translateY) {
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

    private static String formatTanggal(String tanggal) {
        // Parse string tanggal dari database ke objek LocalDate
        LocalDate tanggalLocalDate = LocalDate.parse(tanggal);

        // Format ulang LocalDate ke dalam string dengan format yang diinginkan
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMMM-yyyy");
        String tanggalDiformat = tanggalLocalDate.format(formatter);

        // Gunakan hasil tanggal yang sudah diformat
        return tanggalDiformat;
    }

    public void popUpUntukModeUser() {
        RightBar rightBar = new RightBar(this.saldo, this.userId);
        StackPane modeUserPane = new StackPane(); // agar tidak duplicate
        StackPane backgroundPaneModeUserPane = new StackPane();

        double batasKritis = rightBar.batasKritis();
        double target = batasKritis * 2 * 4;
        double perkembangan = rightBar.kembangProgres();

        Text titleEdit = createText("Kondisi Keuangan Kamu", "-fx-font: 40 'Poppins'; -fx-fill: #FFFFFF;", 0, 0);
        Text kondisi;
        if (perkembangan <= batasKritis / target) {
            kondisi = createText("Kritis", "-fx-font: 30 'Poppins'; -fx-fill: #FF4040;", 0, 0);
        } else if (batasKritis / target <= perkembangan && perkembangan <= batasKritis * 2 / target) {
            kondisi = createText("Karantina", "-fx-font: 30 'Poppins'; -fx-fill: #FC820B;", 0, 0);
        } else {
            kondisi = createText("Sehat", "-fx-font: 30 'Poppins'; -fx-fill: #4AD334;", 0, 0);
        }

        modeUserPane.setMaxSize(this.mainPane.getWidth() - 200, this.mainPane.getHeight() - 200);
        modeUserPane.setStyle("-fx-background-radius: 20; -fx-background-color: #263940");

        backgroundPaneModeUserPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8); -fx-background-radius: 30px;");

        Hyperlink editBackHyperlink = new Hyperlink();
        editBackHyperlink.setGraphic(new ImageView(new Image("file:src/Assets/View/Dashboard/Back.png")));

        StackPane konteneditBack = new StackPane(editBackHyperlink);
        konteneditBack.setAlignment(Pos.CENTER);
        konteneditBack.setPadding(new Insets(20, 0, 0, 10));
        StackPane konteneditTitle = new StackPane(titleEdit);
        konteneditTitle.setAlignment(Pos.CENTER_RIGHT);
        konteneditTitle.setPadding(new Insets(20, 0, 0, 26));

        HBox editTitleHBox = new HBox(konteneditBack, konteneditTitle);
        StackPane kontenKondisi = new StackPane(kondisi);
        kontenKondisi.setAlignment(Pos.CENTER);
        kontenKondisi.setStyle("-fx-background-radius: 20; -fx-background-color: #141F23");
        kontenKondisi.setMaxSize(200, 150);

        VBox allKontenAtas = new VBox(editTitleHBox, kontenKondisi);
        allKontenAtas.setAlignment(Pos.TOP_CENTER);
        allKontenAtas.setSpacing(15);

        /*
         * Pembuatan konten tengah tengah
         */
        Circle backgroundProfileCircle1 = new Circle(40);
        backgroundProfileCircle1.setFill(Color.valueOf("#FF4040"));
        Circle profileCircle1 = new Circle(35);
        profileCircle1.setFill(Color.valueOf("#141F23"));
        ImageView profileImage1 = new ImageView(new Image("file:src/Assets/View/Dashboard/profile.png"));
        profileImage1.setFitWidth(80);
        profileImage1.setFitHeight(80);
        profileImage1.setPreserveRatio(true);

        StackPane Circle1 = new StackPane(backgroundProfileCircle1, profileCircle1, profileImage1);
        Circle1.setAlignment(Pos.CENTER);

        // Membuat teks
        Text teksKritis = createText(
                "Perhatian: Kondisi keuanganmu kritis! \nSegera perbaiki untuk menghindari masalah.\n Aplikasi memberi tahu dengan latar belakang merah \npada profil dan bar pemantauan uang.",
                "-fx-font: 20 'Poppins'; -fx-fill: #FFFFFF;", 0, 0);

        // Membuat label
        String keteranganTeksKritis = "Mode Kritis";
        Label labelKeteranganKritis = new Label(keteranganTeksKritis);
        labelKeteranganKritis.setStyle("-fx-font: 15 'Poppins'; -fx-text-fill: #ffffff;");

        // Membuat tooltip
        Tooltip tooltipKritis = new Tooltip(teksKritis.getText());
        tooltipKritis.setAutoHide(false);

        // Membuat StackPane
        StackPane teksKritisPane = new StackPane(labelKeteranganKritis);
        teksKritisPane.setAlignment(Pos.CENTER);
        teksKritisPane.setStyle("-fx-background-radius: 20; -fx-background-color: #141F23");
        teksKritisPane.setMinWidth(120);
        teksKritisPane.setMaxHeight(100);

        // Membuat VBox
        VBox kontenKritis = new VBox(Circle1, teksKritisPane);
        kontenKritis.setSpacing(10);

        // Memasang tooltip pada VBox
        Tooltip.install(kontenKritis, tooltipKritis);

        // Menampilkan tooltip saat mouse masuk dan menyembunyikannya saat mouse keluar
        kontenKritis.setOnMouseEntered(event -> {
            tooltipKritis.show(kontenKritis, event.getScreenX(), event.getScreenY());
        });
        kontenKritis.setOnMouseExited(event -> {
            tooltipKritis.hide();
        });

        // Konten tengah tengah karantina
        Circle backgroundProfileCircle2 = new Circle(40);
        backgroundProfileCircle2.setFill(Color.valueOf("#FD9C3D"));
        Circle profileCircle2 = new Circle(35);
        profileCircle2.setFill(Color.valueOf("#141F23"));
        ImageView profileImage2 = new ImageView(new Image("file:src/Assets/View/Dashboard/profile.png"));
        profileImage2.setFitWidth(80);
        profileImage2.setFitHeight(80);
        profileImage2.setPreserveRatio(true);

        StackPane Circle2 = new StackPane(backgroundProfileCircle2, profileCircle2, profileImage2);
        Circle2.setAlignment(Pos.CENTER);

        // Membuat teks
        Text teksKarantina = createText(
                "Perhatian: Kondisi keuanganmu karantina! \nSegera perbaiki untuk menghindari masalah.\n Aplikasi memberi tahu dengan latar belakang kuning \npada profil dan bar pemantauan uang.",
                "-fx-font: 15 'Poppins'; -fx-fill: #FFFFFF;", 0, 0);

        // Membuat label
        String keteranganTeksKarantina = "Mode Karantina";
        Label labelKeteranganKarantina = new Label(keteranganTeksKarantina);
        labelKeteranganKarantina.setStyle("-fx-font: 15 'Poppins'; -fx-text-fill: #ffffff;");

        // Membuat tooltip
        Tooltip tooltipKarantina = new Tooltip(teksKarantina.getText());
        tooltipKarantina.setAutoHide(false);

        // Membuat StackPane
        StackPane teksKarantinaPane = new StackPane(labelKeteranganKarantina);
        teksKarantinaPane.setAlignment(Pos.CENTER);
        teksKarantinaPane.setStyle("-fx-background-radius: 20; -fx-background-color: #141F23");
        teksKarantinaPane.setMinWidth(140);
        teksKarantinaPane.setMaxHeight(100);

        // Membuat VBox
        VBox kontenKarantina = new VBox(Circle2, teksKarantinaPane);
        kontenKarantina.setSpacing(10);

        // Memasang tooltip pada VBox
        Tooltip.install(kontenKarantina, tooltipKarantina);

        // Menampilkan tooltip saat mouse masuk dan menyembunyikannya saat mouse keluar
        kontenKarantina.setOnMouseEntered(event -> {
            tooltipKarantina.show(kontenKarantina, event.getScreenX(), event.getScreenY());
        });
        kontenKarantina.setOnMouseExited(event -> {
            tooltipKarantina.hide();
        });

        // Konten kanan tengah sehat
        Circle backgroundProfileCircle3 = new Circle(40);
        backgroundProfileCircle3.setFill(Color.valueOf("#7AFF64"));
        Circle profileCircle3 = new Circle(35);
        profileCircle3.setFill(Color.valueOf("#141F23"));
        ImageView profileImage3 = new ImageView(new Image("file:src/Assets/View/Dashboard/profile.png"));
        profileImage3.setFitWidth(80);
        profileImage3.setFitHeight(80);
        profileImage3.setPreserveRatio(true);

        StackPane Circle3 = new StackPane(backgroundProfileCircle3, profileCircle3, profileImage3);
        Circle3.setAlignment(Pos.CENTER);

        // Membuat teks
        Text teksSehat = createText(
                "Selamat: Kondisi keuanganmu sehat! \nTerus pertahankan kondisi keuanganmu.\n Aplikasi memberi tahu dengan latar belakang hijau \npada profil dan bar pemantauan uang.",
                "-fx-font: 15 'Poppins'; -fx-fill: #FFFFFF;", 0, 0);

        // Membuat label
        String keteranganTeksSehat = "Mode Sehat";
        Label labelKeteranganSehat = new Label(keteranganTeksSehat);
        labelKeteranganSehat.setStyle("-fx-font: 15 'Poppins'; -fx-text-fill: #ffffff;");

        // Membuat tooltip
        Tooltip tooltipSehat = new Tooltip(teksSehat.getText());
        tooltipSehat.setAutoHide(false);

        // Membuat StackPane
        StackPane teksSehatPane = new StackPane(labelKeteranganSehat);
        teksSehatPane.setAlignment(Pos.CENTER);
        teksSehatPane.setStyle("-fx-background-radius: 20; -fx-background-color: #141F23");
        teksSehatPane.setMinWidth(120);
        teksSehatPane.setMaxHeight(100);

        // Membuat VBox
        VBox kontenSehat = new VBox(Circle3, teksSehatPane);
        kontenSehat.setSpacing(10);

        // Memasang tooltip pada VBox
        Tooltip.install(kontenSehat, tooltipSehat);

        // Menampilkan tooltip saat mouse masuk dan menyembunyikannya saat mouse keluar
        kontenSehat.setOnMouseEntered(event -> {
            tooltipSehat.show(kontenSehat, event.getScreenX(), event.getScreenY());
        });
        kontenSehat.setOnMouseExited(event -> {
            tooltipSehat.hide();
        });

        /*
         * Ini merupakan bagian dari penataan layout dari konten tengah tengah
         */

        HBox allKontenTengah = new HBox(kontenKritis, kontenKarantina, kontenSehat);
        allKontenTengah.setSpacing(60);
        allKontenTengah.setAlignment(Pos.BOTTOM_CENTER);
        allKontenTengah.setMinHeight(130);
        allKontenTengah.setPadding(new Insets(15, 0, 0, 0));

        /*
         * Pembuatan untuk bagian konten bawah dari mode user
         */

        // Pembuatan konten kiri

        Text titleKontenKiri = createText("Atur Bata Kritis,", "-fx-font: 25 'Poppins Regular'; -fx-fill: #FFFFFF;", 0,
                0);

        StackPane kontenKiriAtas = new StackPane(titleKontenKiri);
        kontenKiriAtas.setAlignment(Pos.CENTER_LEFT);

        Text titleKontenKiri2 = createText("Status Keuanganmu!", "-fx-font: 25 'Poppins Bold'; -fx-fill: #FFFFFF;", 0,
                0);

        StackPane kontenKiriBawah = new StackPane(titleKontenKiri2);
        kontenKiriBawah.setAlignment(Pos.CENTER_LEFT);

        TextField inputKritis = new TextField();
        inputKritis.setMaxWidth(160);
        inputKritis.setMaxHeight(55);
        inputKritis.setStyle(
                "-fx-background-radius: 20;" +
                        "-fx-background-color: #141F23;" +
                        "-fx-font: 20 Poppins;" +
                        "-fx-text-fill: white;");

        inputKritis.textProperty().addListener(new ChangeListener<String>() {
            private String codeFormat = ",##0";
            private int codeLen = 4;

            @Override
            // ObservableValue = StringProperty (Represent an object that has changed its
            // state)
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Hapus listener sementara

                inputKritis.textProperty().removeListener(this);
                if (newValue.replace(",", "").matches("\\d*")) { // Check inputan angka atau tidak
                    if (newValue.length() > oldValue.length()) {
                        if (newValue.length() > 13) {
                            inputKritis.setText(oldValue);
                        } else {
                            updateCodeFormat(newValue);
                            formatAndSet(newValue, this.codeFormat);
                        }
                    }
                } else {
                    inputKritis.setText(oldValue);
                }

                // Tambahkan kembali listener setelah pembaruan teks
                inputKritis.textProperty().addListener(this);
            }

            private void formatAndSet(String text, String format) {
                DecimalFormat decimalFormat = new DecimalFormat(format);
                double value = Double.parseDouble(text.replaceAll(",", ""));
                System.out.println(value);
                inputKritis.setText("");
                inputKritis.setText(decimalFormat.format(value));
            }

            private void updateCodeFormat(String text) {
                int newLen = text.replace(",", "").length();
                if (newLen == this.codeLen + 3) {
                    this.codeFormat = "#," + this.codeFormat;
                    codeLen += 3;
                } else if (newLen >= 4) {
                    this.codeFormat = "#" + this.codeFormat;
                }
            }
        });

        ImageView saveImage = new ImageView("file:src/Assets/View/Dashboard/Simpan.png");
        saveImage.setFitWidth(200);
        saveImage.setFitHeight(60);
        saveImage.setPreserveRatio(true);

        Hyperlink saveHyperlink = new Hyperlink();
        saveHyperlink.setGraphic(saveImage);
        saveHyperlink.setOnMouseClicked(e -> {
            if (updateKritis(
                    inputKritis.getText().isEmpty() ? 0 : Double.parseDouble(inputKritis.getText().replace(",", "")))) {
                this.mainPane.getChildren().remove(backgroundPaneModeUserPane);
                refreshAllViewDashboard();
            }
        });

        StackPane hyperPane = new StackPane(saveHyperlink);
        hyperPane.setAlignment(Pos.TOP_CENTER);
        hyperPane.setMinHeight(80);

        HBox GabunganTextFieldSimpan = new HBox(inputKritis, hyperPane);
        GabunganTextFieldSimpan.setSpacing(5);
        GabunganTextFieldSimpan.setPadding(new Insets(0, 30, 0, 0));
        GabunganTextFieldSimpan.setAlignment(Pos.CENTER_RIGHT);

        /*
         * Penggabungan konten bawah kiri
         */
        VBox kontenKiri = new VBox(kontenKiriAtas, kontenKiriBawah, GabunganTextFieldSimpan);
        kontenKiri.setSpacing(5);

        /*
         * Pembuatan konten kanan
         */
        Text pemberitahuanBatasKritis = createText("Batas kritis kamu sekarang",
                "-fx-font: 25 'Poppins Regular'; -fx-fill: #FFFFFF;", 0, 0);

        StackPane kontenAtasPemberitahuan = new StackPane(pemberitahuanBatasKritis);
        kontenAtasPemberitahuan.setAlignment(Pos.CENTER_LEFT);

        Text jumlahBatasKritis = createText(formatDuit(Math.round(batasKritis)),
                "-fx-font: 30 'Poppins Bold'; -fx-fill: #FFFFFF;", 0, 0);

        StackPane kontenBawahPemberitahuan = new StackPane(jumlahBatasKritis);
        kontenBawahPemberitahuan.setAlignment(Pos.CENTER);

        VBox kontenKanan = new VBox(kontenAtasPemberitahuan, kontenBawahPemberitahuan);
        kontenKanan.setSpacing(10);

        /*
         * Penggabungan konten kanan dan kiri
         */
        HBox duaKontenBawah = new HBox(kontenKiri, kontenKanan);
        duaKontenBawah.setSpacing(10);
        duaKontenBawah.setPadding(new Insets(0, 0, 0, 20));

        /*
         * Ini penggabungan semua konten pop up mode user
         */
        VBox semuaKonten = new VBox(allKontenAtas, allKontenTengah, duaKontenBawah);
        semuaKonten.setSpacing(30);
        semuaKonten.setPadding(new Insets(20, 0, 0, 0));

        modeUserPane.getChildren().addAll(semuaKonten);
        modeUserPane.setStyle("-fx-background-color: #263940; -fx-background-radius: 20px;");

        backgroundPaneModeUserPane.getChildren().add(modeUserPane);
        backgroundPaneModeUserPane.setAlignment(Pos.CENTER);

        this.mainPane.getChildren().add(backgroundPaneModeUserPane);

        editBackHyperlink.setOnMouseClicked(e -> {
            this.mainPane.getChildren().remove(backgroundPaneModeUserPane);
        });

    }

    private boolean updateKritis(double inputKritis) {
        BatasKritis batasKritis = new BatasKritis(this.userId);
        return batasKritis.setBatasKritis(inputKritis);
    }

    private void refreshAllViewDashboard() {
        mainPane.getChildren().clear();
        start();
    }

    private VBox refreshViewHBox(String Kategori) {
        this.refreshViewDashboard = new RefreshViewDashboard();
        this.keteranganBarangList = refreshViewDashboard.getKeteranganBarangCatatList(Kategori);
        this.nominalBarangList = refreshViewDashboard.getNominalBarangList(Kategori);
        this.tanggalBarangList = refreshViewDashboard.getTanggalBarangList(Kategori);
        VBox kontenHistoriKeuanganBarangfull = new VBox();
        kontenHistoriKeuanganBarangfull.setSpacing(10);
        kontenHistoriKeuanganBarangfull.setSpacing(10);
        for (int i = 0; i < refreshViewDashboard.getTotalBarang(Kategori); i++) {
            String keterangan = keteranganBarangList.get(i);
            double nominal = nominalBarangList.get(i);
            String tipe = Kategori;
            String tanggal = tanggalBarangList.get(i);

            Text keteranganText = createText(keterangan, "-fx-font: 15 'Poppins Bold'; -fx-fill: #FFFFFF;",
                    0, 0);

            String tampilanKeterangan = keteranganText.getText().length() > 10
                    ? keteranganText.getText().substring(0, 10) + "..."
                    : keteranganText.getText();

            Label labelKeterangan = new Label(tampilanKeterangan);
            labelKeterangan.setStyle("-fx-font: 15 'Poppins SemiBold'; -fx-text-fill: #ffffff;"); // Set the style

            // Tambahkan tooltip yang menampilkan saldo lengkap
            Tooltip tooltipKeterangan = new Tooltip(keteranganText.getText());
            Tooltip.install(labelKeterangan, tooltipKeterangan);

            VBox keteranganStackPane = new VBox(labelKeterangan);
            keteranganStackPane.setAlignment(Pos.CENTER_LEFT);

            Long roundedValueNominal = Math.round(nominal);
            Text nominalText = createText(formatDuit(roundedValueNominal),
                    "-fx-font: 15 'Poppins Bold'; -fx-fill: #798F97;", 0, 0);

            StackPane nominalStackPane = new StackPane(nominalText);
            nominalStackPane.setAlignment(Pos.CENTER_RIGHT);
            nominalStackPane.setPadding(new Insets(0, 0, 0, 60));

            ImageView kondisi = new ImageView();
            if (tipe.equals("Pemasukan")) {
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

            StackPane gambarKondisi = new StackPane(kondisi);
            gambarKondisi.setAlignment(Pos.CENTER_LEFT);
            gambarKondisi.setPadding(new Insets(0, 0, 0, 60));

            HBox gambarKondisidanNominal = new HBox(nominalText, gambarKondisi);
            gambarKondisidanNominal.setSpacing(10);
            gambarKondisidanNominal.setAlignment(Pos.CENTER);
            HBox.setHgrow(gambarKondisidanNominal, Priority.ALWAYS);

            Text tanggalText = createText(formatTanggal(tanggal), "-fx-font: 15 'Poppins Bold'; -fx-fill: #798F97;",
                    0, 0);

            StackPane tanggalTextPane = new StackPane(tanggalText);
            tanggalTextPane.setAlignment(Pos.CENTER_RIGHT);

            HBox kontenHistoriKeuanganBarang = new HBox(keteranganStackPane, nominalStackPane,
                    gambarKondisidanNominal,
                    tanggalTextPane);

            kontenHistoriKeuanganBarang.setSpacing(50);
            kontenHistoriKeuanganBarang.setPadding(new Insets(10, 25, 10, 40));
            kontenHistoriKeuanganBarang.setAlignment(Pos.CENTER);
            kontenHistoriKeuanganBarang.setStyle("-fx-background-color: #213339; -fx-background-radius: 30px");

            kontenHistoriKeuanganBarangfull.getChildren().add(kontenHistoriKeuanganBarang);
        }
        return kontenHistoriKeuanganBarangfull;
    }

    // CustomItem class untuk menyimpan data yang akan ditampilkan di ComboBox
    public static class CustomItem {
        private final String text;
        private final Color color;

        public CustomItem(String text, Color color) {
            this.text = text;
            this.color = color;
        }

        public String getText() {
            return text;
        }

        public Color getColor() {
            return color;
        }

        public static class CustomItemConverter extends StringConverter<CustomItem> {

            @Override
            public String toString(CustomItem object) {
                return (object != null) ? object.getText() : null;
            }

            @Override
            public CustomItem fromString(String string) {
                // Tidak perlu mengimplementasikan ini kecuali jika Anda memerlukannya
                return null;
            }
        }
    }

    class ImageLinkPane {
        DashboardPage dashboardPage;

        public ImageLinkPane(DashboardPage dashboardPage) {
            this.dashboardPage = dashboardPage;
        }

        public VBox createImageLinkVBox(Stage stage, SceneController sceneController) {
            // Gunakan ImageView untuk semua pilihan di Sidebar
            ImageView logoImageView = new ImageView(new Image("file:src/Assets/View/Dashboard/Logo.png"));
            logoImageView.setFitWidth(240);
            logoImageView.setFitHeight(70);
            logoImageView.setPreserveRatio(true);

            ImageView homePageImageView = new ImageView(new Image("file:src/Assets/View/Dashboard/HomePage.png"));
            ImageView tanamUangImageView = new ImageView(new Image("file:src/Assets/View/Dashboard/Tanam Uang.png"));
            ImageView pantauUangImageView = new ImageView(new Image("file:src/Assets/View/Dashboard/Pantau Uang.png"));
            ImageView panenUangImageView = new ImageView(new Image("file:src/Assets/View/Dashboard/Panen Uang.png"));
            ImageView modeUser = new ImageView("file:src/Assets/View/Dashboard/Mode User.png");
            ImageView MulaiMencatatSekarang = new ImageView(
                    "file:src/Assets/View/Dashboard/MulaiMencatatSekarang!.png");
            ImageView logOut = new ImageView("file:src/Assets/View/Dashboard/Log Out.png");

            // Menyesuaikan ukuran ImageView
            homePageImageView.setFitWidth(30);
            homePageImageView.setFitHeight(30);
            homePageImageView.setPreserveRatio(true);

            tanamUangImageView.setFitWidth(30);
            tanamUangImageView.setFitHeight(30);
            tanamUangImageView.setPreserveRatio(true);

            pantauUangImageView.setFitWidth(30);
            pantauUangImageView.setFitHeight(30);
            pantauUangImageView.setPreserveRatio(true);

            panenUangImageView.setFitWidth(30);
            panenUangImageView.setFitHeight(30);
            panenUangImageView.setPreserveRatio(true);

            modeUser.setFitWidth(30);
            modeUser.setFitHeight(30);
            modeUser.setPreserveRatio(true);

            MulaiMencatatSekarang.setFitWidth(230);
            MulaiMencatatSekarang.setFitHeight(250);
            MulaiMencatatSekarang.setPreserveRatio(true);

            logOut.setFitWidth(200);
            logOut.setFitHeight(60);
            logOut.setPreserveRatio(true);

            // Membuat rectangle dengan sudut 30 derajat lalu lalu di set clip
            Rectangle clip = new Rectangle();
            clip.setArcWidth(30);
            clip.setArcHeight(30);
            clip.setWidth(230);
            clip.setHeight(220);
            MulaiMencatatSekarang.setClip(clip);

            // Membuat masing masing Text dan Image pada side bar dengan menggunakan HBOX

            Text homePageText = createText("Home", "-fx-font: 20 'Poppins'; -fx-fill: #ffffff;", 0, 0);
            HBox homePageHBox = new HBox(homePageImageView, homePageText);
            homePageHBox.setSpacing(10);
            homePageHBox.setAlignment(Pos.CENTER_LEFT);

            // Membuat Hyperlink dengan menggunakan HyperlinkText
            HyperlinkText tanamUangHyperlink = new HyperlinkText("Tanam Uang");
            tanamUangHyperlink.setOnAction(e -> sceneController.switchToTanamUang());
            tanamUangHyperlink.setBorder(Border.EMPTY);
            HBox tanamUangHBox = new HBox(tanamUangImageView, tanamUangHyperlink);
            tanamUangHBox.setSpacing(10);
            tanamUangHBox.setAlignment(Pos.CENTER_LEFT);

            HyperlinkText pantauUangHyperlink = new HyperlinkText("Pantau Uang");
            pantauUangHyperlink.setOnAction(e -> sceneController.switchToPantauUang());
            pantauUangHyperlink.setBorder(Border.EMPTY);
            HBox pantauUangHBox = new HBox(pantauUangImageView, pantauUangHyperlink);
            pantauUangHBox.setSpacing(10);
            pantauUangHBox.setAlignment(Pos.CENTER_LEFT);

            HyperlinkText panenUangHyperlink = new HyperlinkText("Panen Uang");
            panenUangHyperlink.setOnAction(e -> sceneController.switchToPanenUang());
            panenUangHyperlink.setBorder(Border.EMPTY);
            HBox panenUangHBox = new HBox(panenUangImageView, panenUangHyperlink);
            panenUangHBox.setSpacing(10);
            panenUangHBox.setAlignment(Pos.CENTER_LEFT);

            HyperlinkText modeUserHyperlink = new HyperlinkText("Mode User");
            modeUserHyperlink.setOnAction(e -> dashboardPage.popUpUntukModeUser());
            modeUserHyperlink.setBorder(Border.EMPTY);
            HBox modeUserHBox = new HBox(modeUser, modeUserHyperlink);
            modeUserHBox.setSpacing(10);
            modeUserHBox.setAlignment(Pos.CENTER_LEFT);

            Hyperlink logOutHyperlink = createHyperlinkWithImageView(logOut);
            logOutHyperlink.setBorder(Border.EMPTY);

            // Membuat Hyperlink dengan menggunakan ImageView
            logOutHyperlink.setOnMouseClicked(e -> {
                LoginModel loginModel = new LoginModel();
                if (loginModel.mengaturRememberMeMenjadiFalse()) {
                    sceneController.switchToLogin();
                }
            });

            // Membuat masing - masing Vbox dan menambahkan Hyperlink ke dalamnya
            StackPane image = new StackPane(logoImageView);
            image.setAlignment(Pos.TOP_CENTER);

            StackPane mulaiMencatatSekarang = new StackPane(MulaiMencatatSekarang);
            mulaiMencatatSekarang.setAlignment(Pos.CENTER);

            VBox logOutVBox = new VBox(logOutHyperlink);
            logOutVBox.setAlignment(Pos.TOP_CENTER);

            // Vbox untuk menggabungkan pilihan
            VBox kontenSideAtas = new VBox(homePageHBox, tanamUangHBox, pantauUangHBox, panenUangHBox, modeUserHBox);
            kontenSideAtas.setSpacing(30);

            // Membuat Stackpane untuk bagian atas bawah logo
            StackPane homeStackPane = new StackPane(kontenSideAtas);
            homeStackPane.setAlignment(Pos.CENTER);
            homeStackPane.setStyle("-fx-background-radius: 30; -fx-background-color: #141F23;");
            homeStackPane.setPadding(new Insets(10, 0, 10, 20));
            homeStackPane.setMaxHeight(300);

            VBox kontenSide = new VBox(image, homeStackPane, mulaiMencatatSekarang, logOutVBox);
            kontenSide.setSpacing(20);
            kontenSide.setAlignment(Pos.CENTER);
            kontenSide.setPadding(new Insets(20, 10, 10, 10));
            VBox.setVgrow(homeStackPane, Priority.ALWAYS);
            VBox.setVgrow(MulaiMencatatSekarang, Priority.ALWAYS);

            return kontenSide;

        }

        private static Hyperlink createHyperlinkWithImageView(ImageView imageView) {
            Hyperlink hyperlink = new Hyperlink();
            hyperlink.setGraphic(imageView);
            hyperlink.setBorder(Border.EMPTY);
            return hyperlink;
        }

    }

    class HyperlinkText extends Hyperlink {
        private Text text;

        public HyperlinkText(String linkText) {
            super(linkText);
            configure();
        }

        private void configure() {
            text = new Text(getText());
            setGraphic(text);
            setBorder(null);

            // CSS styling
            text.setStyle("-fx-font: 20 'Poppins'; -fx-fill: #ffffff;"); // Initial color
            setStyle("-fx-opacity: 0.5;"); // Initial opacity

            // Event handlers for hover effect
            setOnMouseEntered(event -> setStyle("-fx-opacity: 1;"));
            setOnMouseExited(event -> setStyle("-fx-opacity: 0.5;"));

            // Set contentDisplay to show only the graphic (Text)
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }
}

class VerticalProgress {
    private ProgressBar progressBar = new ProgressBar();
    private Group progressHolder = new Group(progressBar);

    public VerticalProgress(double width, double height) {
        progressBar.setMinSize(StackPane.USE_PREF_SIZE, StackPane.USE_PREF_SIZE - 100);
        progressBar.setPrefSize(height, width);
        progressBar.setMaxSize(StackPane.USE_PREF_SIZE, StackPane.USE_PREF_SIZE - 100);
        progressBar.getTransforms().setAll(
                new Translate(0, height),
                new Rotate(-90, 0, 0));
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public Group getProgressHolder() {
        return progressHolder;
    }
}

class RightBar {
    private static Cursor hand = Cursor.cursor("HAND");
    private static Cursor closedHand = Cursor.cursor("CLOSED_HAND");
    private static Cursor defaultCursor = Cursor.cursor("DEFAULT");
    private static boolean isMousePressed = false;
    private static boolean isWindowMax = true;
    private static double xOffset = 0;
    private static double yOffset = 0;
    StackPane rightbarroot = new StackPane(); // stackpane root
    private double user_saldo;
    private int userId;

    public RightBar(double saldo, int userId) {
        this.user_saldo = saldo;
        this.userId = userId;
    }

    public HBox createRightBar(Stage stage, SceneController sceneController) {
        /* BAGIAN MMC (MINIMIZE, MAXIMIZE, CLOSE) */
        // membuat closeButton
        Button closeButton = createButton(35, 35, "X", "FF4646", 23, "Poppins", 30, "0F181B");

        // membuat maximizeButton
        Button maximizeButton = createButton(35, 35, " ", "FFFFFF", 23, "Poppins", 30, "0F181B");
        // set graphic untuk label maximize
        Rectangle maxIcon = new Rectangle(14, 14);
        maxIcon.setFill(Color.TRANSPARENT);
        maxIcon.setStroke(Color.valueOf("#0F181B"));
        maxIcon.setStrokeWidth(4);
        maxIcon.setTranslateX(maximizeButton.getTranslateX() + 5);
        maximizeButton.setGraphic(maxIcon);

        // membuat minimizeButton
        Button minimizeButton = createButton(35, 35, "-", "FFFFFF", 23, "Poppins", 30, "0F181B");

        // BUTTON EVENTS
        // CLOSE BUTTON: saat dipencet maka close button akan menstop aplikasi
        closeButton.setOnMouseClicked(closeEvent -> stage.hide());
        // saat di hover maka cursor berbeda
        closeButton.setOnMouseEntered(closeEvent -> {
            closeButton.getScene().setCursor(hand);
            updateButton(closeButton, 35, 35, "X", "6A1B1B", 23, "Poppins", 30, "0F181B");
        });
        closeButton.setOnMouseExited(closeEvent -> {
            closeButton.getScene().setCursor(defaultCursor);
            updateButton(closeButton, 35, 35, "X", "FF4646", 23, "Poppins", 30, "0F181B");
        });

        // MAXIMIZE BUTTON: saat dipencet maka akan mengecilkan windows
        maximizeButton.setOnMouseClicked(maxEvent -> {
            if (stage.isMaximized()) {
                stage.setWidth(stage.getWidth() - 100);
                stage.setHeight(stage.getHeight() - 100);
                stage.setMaximized(false);
                isWindowMax = false;
                System.out.println("IsWindMax =" + isWindowMax);
                rightbarroot.setOnMousePressed(e -> {
                    if (e.isPrimaryButtonDown() && e.getClickCount() == 2 && !isWindowMax) {
                        rightbarroot.getScene().setCursor(closedHand);
                        isMousePressed = true;
                        xOffset = e.getSceneX();
                        yOffset = e.getSceneY();
                    }
                });
                // saat dilepas
                rightbarroot.setOnMouseReleased(e -> {
                    rightbarroot.getScene().setCursor(defaultCursor);
                    isMousePressed = false;
                });
                // saat ditarik
                rightbarroot.setOnMouseDragged(e -> {
                    if (isMousePressed && !isWindowMax) {
                        rightbarroot.getScene().setCursor(closedHand);
                        stage.setX(e.getScreenX() - xOffset);
                        stage.setY(e.getScreenY() - yOffset);
                    }
                });
            } else {
                isWindowMax = true;
                System.out.println("IsWindMax =" + isWindowMax);
                stage.setMaximized(true);
            }
        });

        // saat di hover maka cursor berbeda
        maximizeButton.setOnMouseEntered(closeEvent -> {
            maximizeButton.getScene().setCursor(hand);
            updateButton(maximizeButton, 35, 35, "", "959595", 23, "Poppins", 30, "0F181B");
            maxIcon.setTranslateX(maximizeButton.getTranslateX());
            maximizeButton.setGraphic(maxIcon);
        });
        maximizeButton.setOnMouseExited(closeEvent -> {
            maximizeButton.getScene().setCursor(defaultCursor);
            updateButton(maximizeButton, 35, 35, "", "FFFFFF", 23, "Poppins", 30, "0F181B");
            maxIcon.setTranslateX(maximizeButton.getTranslateX());
            maximizeButton.setGraphic(maxIcon);
        });

        // MINIMIZE BUTTON: saat dipencet akan menutup sementara window
        minimizeButton.setOnMouseClicked(maxEvent -> {
            stage.setIconified(true);
        });
        // saat di hover maka cursor berbeda
        minimizeButton.setOnMouseEntered(closeEvent -> {
            minimizeButton.getScene().setCursor(hand);
            updateButton(minimizeButton, 35, 35, "-", "959595", 23, "Poppins", 30, "0F181B");
        });
        minimizeButton.setOnMouseExited(closeEvent -> {
            minimizeButton.getScene().setCursor(defaultCursor);
            updateButton(minimizeButton, 35, 35, "-", "FFFFFF", 23, "Poppins", 30, "0F181B");
        });

        HBox mmcButton = new HBox(5); // 5 spacing
        mmcButton.getChildren().addAll(minimizeButton, maximizeButton, closeButton);
        mmcButton.setMaxWidth(150);
        mmcButton.setAlignment(Pos.TOP_RIGHT);

        // ------------------------------------------------------------------------------------------------------------//
        StackPane penggabunganBackgroundDenganProfile = new StackPane();
        Circle backgroundProfileCircle = new Circle(40);

        Circle profileCircle = new Circle(35);
        profileCircle.setFill(Color.valueOf("#141F23"));

        // Membuat foto profil
        ImageView profileImage = new ImageView(new Image("file:src/Assets/View/Dashboard/Profile.png"));
        profileImage.setFitWidth(80);
        profileImage.setFitHeight(80);
        profileImage.setPreserveRatio(true);

        penggabunganBackgroundDenganProfile.getChildren().addAll(backgroundProfileCircle, profileCircle, profileImage);
        penggabunganBackgroundDenganProfile.setAlignment(Pos.CENTER);
        penggabunganBackgroundDenganProfile
                .setStyle("-fx-background-color: #141F23; -fx-background-radius: 30 30 0 0;");
        // Membuat objek VerticalProgress
        VerticalProgress verticalProgress = new VerticalProgress(20, 100);

        // Mendapatkan ProgressBar dari objek VerticalProgress
        ProgressBar progressBar = verticalProgress.getProgressBar();

        double batasKritis = mengambilBatasKritis(this.userId);
        double target = batasKritis * 2 * 4;
        double perkembangan = perkembanganProgresBar(this.userId);
        // Mengatur gaya dan progres ProgressBar
        progressBar.setProgress(perkembangan);

        // Membuat kondisi ketika mencapai batas kritis dan lain lain
        progressBar.getStylesheets().clear();
        if (perkembangan <= batasKritis / target) {
            progressBar.getStylesheets().add(getClass().getResource("/Utils/KritisProgresBar.css").toExternalForm());
            backgroundProfileCircle.setFill(Color.valueOf("#FF4040"));
        } else if (batasKritis / target <= perkembangan && perkembangan <= batasKritis * 2 / target) {
            progressBar.getStylesheets().add(getClass().getResource("/Utils/KarantinaProgresBar.css").toExternalForm());
            backgroundProfileCircle.setFill(Color.valueOf("#FD9C3D"));
        } else {
            progressBar.getStylesheets().add(getClass().getResource("/Utils/Levelprogres.css").toExternalForm());
            backgroundProfileCircle.setFill(Color.valueOf("#7AFF64"));
        }

        // Mengatur lebar dan rotasi
        progressBar.setPrefWidth(600);
        progressBar.setMinWidth(20);

        // Membuat StackPane dan menambahkan ProgressBar ke dalamnya
        StackPane progressStackPane = new StackPane(verticalProgress.getProgressHolder());
        progressStackPane.setAlignment(Pos.CENTER);
        progressStackPane.setPadding(new Insets(0, 0, 0, 0));
        progressStackPane.setStyle("-fx-background-color: #141F23; -fx-background-radius: 0 0 30 30;");
        progressStackPane.setMaxHeight(620);

        VBox profileCombineMMC = new VBox(mmcButton, penggabunganBackgroundDenganProfile);
        profileCombineMMC.setFillWidth(true);
        profileCombineMMC.setSpacing(20);
        profileCombineMMC.setStyle("-fx-background-color: #0D1416;");
        profileCombineMMC.setAlignment(Pos.BOTTOM_CENTER);
        profileCombineMMC.setPadding(new Insets(20, 0, 0, 0));
        VBox.setMargin(penggabunganBackgroundDenganProfile, new Insets(0, 10, 0, 10));

        VBox mmcCombineLevel = new VBox(profileCombineMMC, progressStackPane);
        mmcCombineLevel.setSpacing(0);
        mmcCombineLevel.setStyle("-fx-background-color: #0D1416;");
        mmcCombineLevel.setAlignment(Pos.CENTER_RIGHT);
        VBox.setVgrow(progressStackPane, Priority.ALWAYS);
        VBox.setMargin(progressStackPane, new Insets(0, 10, 0, 10));

        HBox allCombine = new HBox(mmcCombineLevel);
        allCombine.setStyle("-fx-background-color: #0D1416;");
        allCombine.setAlignment(Pos.TOP_CENTER);
        return allCombine;
    }

    private static Button createButton(int width, int height, String text, String bgColor, int fontSize,
            String font,
            int radius, String textFill) {
        Button button = new Button();
        button.setPrefSize(width, height);
        button.setText(text);
        button.setStyle(
                "-fx-background-color: #" + bgColor + ", transparent; " +
                        "-fx-font: " + fontSize + " " + font + "; " +
                        "-fx-text-fill: #" + textFill + ";" +
                        "-fx-background-radius: " + radius + ";" +
                        "-fx-padding: 0;");
        return button;
    }

    private static void updateButton(Button btn, int width, int height, String text, String bgColor,
            int fontSize, String font,
            int radius, String textFill) {
        btn.setPrefSize(width, height);
        btn.setText(text);
        btn.setStyle(
                "-fx-background-color: #" + bgColor + ", transparent; " +
                        "-fx-font: " + fontSize + " " + font + "; " +
                        "-fx-text-fill: #" + textFill + ";" +
                        "-fx-background-radius: " + radius + ";" +
                        "-fx-padding: 0;");
    }

    private double perkembanganProgresBar(int userId) {
        BatasKritis bataskritis = new BatasKritis(userId);
        double batasKritis = bataskritis.getBatasKritis();
        System.out.println("Batas Kritis = " + bataskritis);
        double saldo = this.user_saldo;
        double target = batasKritis * 2 * 4;
        System.out.println("Target = " + target);
        double perkembangan = saldo / target;
        System.out.println("Perkembangan = " + perkembangan);
        return perkembangan;
    }

    private double mengambilBatasKritis(int userId) {
        BatasKritis bataskritis = new BatasKritis(userId);
        double batasKritis = bataskritis.getBatasKritis();
        return batasKritis;
    }

    public double batasKritis() {
        return mengambilBatasKritis(this.userId);
    }

    public double kembangProgres() {
        return perkembanganProgresBar(this.userId);
    }
}