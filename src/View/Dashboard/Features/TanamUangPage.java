package View.Dashboard.Features;

import Controller.SceneController;
import Model.BatasKritis;
import Model.LoginModel;
import Model.PantauPemasukanPengeluaran;
import Model.TampilkanSemuaTarget;
import Model.TanamUangModel;
import Utils.AlertHelper;
import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import javafx.stage.Stage;
import javafx.util.Duration;

// class DashboardPage digunakan untuk menampilkan halaman dashboard
public class TanamUangPage {

    private Stage stage; // Property stage sebaiknya dideklarasikan sebagai final
    private SceneController sceneController; // Tambahkan property SceneController\
    private double saldo;
    private int userId;
    private Tooltip tooltip = new Tooltip();
    private static PantauPemasukanPengeluaran model = new PantauPemasukanPengeluaran();

    public DecimalFormat formatRupiah;
    private ComboBox<String> combobox = new ComboBox<String>();
    private Text title = new Text("Pengeluaran");
    private String tipeTanamUang = "pengeluaran";
    private TextField fieldJumlah = new TextField();
    private TextField fieldKeterangan = new TextField();
    private RadioButton radioButtonCash = new RadioButton("Cash");
    private RadioButton radioButtonTransfer = new RadioButton("Transfer");
    private DatePicker datePickerTanggal = new DatePicker();
    private static Cursor hand = Cursor.cursor("HAND");
    private static Cursor defaultCursor = Cursor.cursor("DEFAULT");
    StackPane mainPane = new StackPane();
    public StackPane root = new StackPane();

    public TanamUangPage(Stage stage) {
        this.stage = stage;
        // this.username = getUsername();
        this.sceneController = new SceneController(stage); // Inisialisasi SceneController
        this.saldo = getSaldo();
        LoginModel loginModel = new LoginModel();
        this.userId = loginModel.getUserId();
    }

    // Menampilkan halaman dashboard
    public void start(String[] listKategoriPemasukan, String[] listKategoriPengeluaran) {
        // Membuat side bar
        ImageLinkPane imageLinkPane = new ImageLinkPane(this); // Mengirim referensi DashboardPage ke ImageLinkPane
        VBox sideBar = imageLinkPane.createImageLinkVBox(this.stage, sceneController);
        sideBar.setAlignment(Pos.CENTER);
        sideBar.setMinWidth(242);
        VBox.setVgrow(sideBar, Priority.ALWAYS);
        // Membuat teks welcome
        Text welcome = createText("Catat Keuanganmu\n", "-fx-font: 35 'Poppins Regular'; -fx-fill: #FFFFFF;", 0, 0);
        Text name = createText("Dengan Lengkap!", "-fx-font: 35 'Poppins SemiBold'; -fx-fill: #FFFFFF;", 0, 10);

        // StackPane untuk menampung teks
        StackPane textPane = new StackPane(welcome, name);
        textPane.setAlignment(Pos.CENTER_LEFT);
        textPane.setPadding(new Insets(0, 0, 20, 10));

        // Menambahkan gambar
        ImageView contentImageView = new ImageView(new Image("/Assets/View/Dashboard/LogoTanamUang.png"));
        contentImageView.setFitWidth(400);
        contentImageView.setFitHeight(300);

        // StackPane untuk menampung gambar
        StackPane contentImagePane = new StackPane(contentImageView);
        contentImagePane.setAlignment(Pos.CENTER_RIGHT);
        contentImagePane.setPadding(new Insets(0, 0, 0, 0));

        // Konten bagian atas untuk main pane
        HBox kontenAtasPane = new HBox(textPane, contentImagePane);
        kontenAtasPane.setSpacing(100);
        // HBox.setHgrow(textPane, Priority.ALWAYS);
        textPane.setTranslateX(20);

        // ------------------------------------------------------------------------------------------------------------//

        // Membuat vbox atas untuk konten tengah
        // VBox kontenTengahAtas = new VBox(Fitur);
        // kontenTengahAtas.setAlignment(Pos.CENTER_LEFT);
        // kontenTengahAtas.setPadding(new Insets(0, 0, 20, 0));

        // Konten kiri
        ImageView Gambarduit = new ImageView(new Image("/Assets/View/Dashboard/Gambarduit.png"));
        Gambarduit.setFitWidth(220);
        Gambarduit.setFitHeight(40);
        Gambarduit.setPreserveRatio(true);

        VBox vboxKiriAtas = new VBox(Gambarduit);
        vboxKiriAtas.setAlignment(Pos.TOP_RIGHT);
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

        ImageView UbahSaldo = new ImageView(new Image("/Assets/View/Dashboard/UbahSaldo .png"));
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

        Button buttonPemasukan = new Button("Pemasukan");
        Button buttonPengeluaran = new Button("Pengeluaran");
        Rectangle buttonBG = new Rectangle();
        Rectangle buttonActive = new Rectangle();

        buttonPemasukan.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-font: 22 'Poppins SemiBold';" +
                        "-fx-text-fill: #FFFFFF");
        buttonPengeluaran.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-font: 22 'Poppins SemiBold';" +
                        "-fx-text-fill: #FFFFFF");
        this.title = new Text("Pengeluaran");
        this.title.setStyle("-fx-font: 24 Poppins;");
        this.title.setFill(Color.valueOf("#FFFFFF"));

        Label labelTanggal = new Label("Tanggal");
        labelTanggal.setStyle("-fx-font: 20 'Poppins Medium'");
        labelTanggal.setTextFill(Color.valueOf("#FFFFFF"));
        this.datePickerTanggal.getStylesheets().add(getClass().getResource("/Utils/DatePicker.css").toExternalForm());
        this.datePickerTanggal.setPrefWidth(180);

        Label labelKategori = new Label("Kategori");
        labelKategori.setStyle("-fx-font: 20 'Poppins Medium'");
        labelKategori.setTextFill(Color.valueOf("#FFFFFF"));
        this.combobox = new ComboBox(FXCollections.observableArrayList(listKategoriPengeluaran));
        this.combobox.setMaxWidth(this.datePickerTanggal.getPrefWidth());
        this.combobox.getStylesheets().add(getClass().getResource("/Utils/ComboBoxTanamUang.css").toExternalForm());

        Timeline rectKeKiri = new Timeline(
                new KeyFrame(Duration.millis(200),
                        new KeyValue(buttonActive.translateXProperty(), buttonActive.getTranslateX(),
                                Interpolator.EASE_BOTH)));

        Timeline rectKeKanan = new Timeline(
                new KeyFrame(Duration.millis(200),
                        new KeyValue(buttonActive.translateXProperty(), 190, Interpolator.EASE_BOTH)));

        buttonPemasukan.setOnMouseClicked(e -> {
            this.tipeTanamUang = "pemasukan";
            buttonActive.setFill(Color.valueOf("#477619"));
            // StackPane.setAlignment(buttonActive, javafx.geometry.Pos.CENTER_RIGHT);
            rectKeKanan.play();
            this.combobox.setItems(FXCollections.observableArrayList(listKategoriPemasukan));
            TanamUangModel.getKategoriTanamUang(this.tipeTanamUang);
        });
        buttonPemasukan.setOnMouseEntered(e -> {
            buttonPemasukan.getScene().setCursor(Cursor.cursor("HAND"));
        });

        buttonPengeluaran.setOnMouseClicked(e -> {
            this.tipeTanamUang = "pengeluaran";
            buttonActive.setFill(Color.valueOf("#761E19"));
            // StackPane.setAlignment(buttonActive, javafx.geometry.Pos.CENTER_LEFT);
            rectKeKiri.play();
            this.combobox.setItems(FXCollections.observableArrayList(listKategoriPengeluaran));
            TanamUangModel.getKategoriTanamUang(this.tipeTanamUang);
        });
        buttonPengeluaran.setOnMouseEntered(e -> {
            buttonPengeluaran.getScene().setCursor(Cursor.cursor("HAND"));
        });

        Hyperlink hyperlinkEdit = new Hyperlink();
        hyperlinkEdit.setGraphic(new ImageView(new Image("/Assets/View/Dashboard/EditTanamUang.png")));

        hyperlinkEdit.setOnMouseClicked(e -> {
            double paneWidth = this.stage.getWidth() - 350;
            double paneHeight = this.stage.getHeight() - 200;

            StackPane backgroundMainPane = new StackPane();
            backgroundMainPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8); -fx-background-radius: 20");

            StackPane editMainPane = new StackPane();
            editMainPane.setMaxSize(paneWidth - 400, paneHeight - 200);
            editMainPane.setStyle("-fx-background-color: #141F23; -fx-background-radius: 20");

            VBox contentPane = new VBox();
            contentPane.setStyle("-fx-background-radius: 20");
            contentPane.setMaxSize(editMainPane.getMaxWidth() - 80, editMainPane.getMaxHeight() - 20);
            contentPane.setSpacing(20);

            HBox topBar = new HBox();
            topBar.setSpacing(10);
            topBar.setAlignment(Pos.CENTER_LEFT);

            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setFitToWidth(true);
            scrollPane.setMaxSize(contentPane.getMaxWidth(), contentPane.getMaxHeight() - 100);

            VBox scrollPaneContent = createScrollPaneContent(scrollPane, editMainPane);

            String firstCapitalLetter = this.tipeTanamUang.substring(0, 1).toUpperCase()
                    + this.tipeTanamUang.substring(1);
            Text titleKategoriPengeluaran = createText("Kategori " + firstCapitalLetter, "-fx-font: 20 'Poppins Bold';",
                    "#FFFFFF");
            Hyperlink backHyperlink = new Hyperlink();
            backHyperlink.setGraphic(new ImageView(new Image("/Assets/View/Dashboard/Back.png")));

            backHyperlink.setOnMouseClicked(f -> {
                this.combobox.setItems(
                        FXCollections.observableArrayList(TanamUangModel.getKategoriTanamUang(this.tipeTanamUang)));
                mainPane.getChildren().remove(editMainPane);
                mainPane.getChildren().remove(backgroundMainPane);
            });

            Hyperlink tambahHyperlink = new Hyperlink();
            tambahHyperlink.setGraphic(new ImageView(new Image("/Assets/View/Dashboard/Tambah.png")));

            tambahHyperlink.setOnMouseClicked(f -> {
                StackPane tambahPane = new StackPane();
                StackPane backgroundTambahPane = new StackPane();
                tambahPane.setMaxSize(editMainPane.getMaxWidth() - 200, editMainPane.getMaxHeight() - 180);
                tambahPane.setStyle("-fx-background-color: #141F23; -fx-background-radius: 20;");

                backgroundTambahPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8);");

                Text titleTambah = createText("Tambah Kategori", "-fx-font: 20 'Poppins Bold'", "#FFFFFF");
                Hyperlink tambahBackHyperlink = new Hyperlink();
                tambahBackHyperlink.setGraphic(new ImageView(new Image("/Assets/View/Dashboard/Back.png")));

                Hyperlink hyperlinkSimpanTambahKategori = createButtonKategori("SimpanTanamUang.png", 150, 40, 62, 12, 30, 30);

                StackPane stackHyperlinkSimpan = new StackPane(hyperlinkSimpanTambahKategori);

                tambahBackHyperlink.setOnMouseClicked(g -> {
                    refreshView(scrollPane, scrollPaneContent, mainPane);
                    editMainPane.getChildren().remove(tambahPane);
                    editMainPane.getChildren().remove(backgroundTambahPane);
                });

                HBox hboxTitleTambah = new HBox(tambahBackHyperlink, titleTambah);
                hboxTitleTambah.setSpacing(20);

                Label labelTambah = new Label("Nama:");
                labelTambah.setStyle("-fx-font: 16 'Poppins Medium'");
                labelTambah.setTextFill(Color.valueOf("#FFFFFF"));

                TextField fieldTambah = new TextField();
                fieldTambah.getStylesheets().add(getClass().getResource("/Utils/TextField.css").toExternalForm());

                hyperlinkSimpanTambahKategori.setOnMouseClicked(g -> {
                    if (!fieldTambah.getText().equals("")) {
                        if (TanamUangModel.tambahKategori(fieldTambah.getText(), tipeTanamUang)) {
                            refreshView(scrollPane, scrollPaneContent, editMainPane);
    
                            editMainPane.getChildren().remove(tambahPane);
                            editMainPane.getChildren().remove(backgroundTambahPane);
    
                            AlertHelper.info("Kategori " + fieldTambah.getText() + " berhasil ditambahkan!");
                        } else {
                            AlertHelper.alert("Kategori gagal ditambahkan!");
                        }
                    }
                    else {
                        AlertHelper.alert("Mohon isi nama kategori.");
                    }
                });

                HBox hboxFormTambah = new HBox(labelTambah, fieldTambah);
                hboxFormTambah.setSpacing(20);
                hboxFormTambah.setAlignment(Pos.CENTER);

                VBox vboxTambah = new VBox(hboxTitleTambah, hboxFormTambah, stackHyperlinkSimpan);

                vboxTambah.setMaxSize(tambahPane.getMaxWidth() - 50, tambahPane.getMaxHeight() - 80);
                vboxTambah.setSpacing(20);

                tambahPane.getChildren().add(vboxTambah);

                editMainPane.getChildren().addAll(backgroundTambahPane, tambahPane);
            });

            scrollPane.setContent(scrollPaneContent);
            String scrollbarStyle = "-fx-background-color: #141F23;";
            scrollbarStyle += "-fx-background-color: #0B1214;";
            scrollbarStyle += "-fx-background-insets: 0;";
            scrollbarStyle += "-fx-padding: 0;";
            scrollbarStyle += "-fx-background-radius: 20px;";

            scrollPane.setStyle(scrollbarStyle);
            scrollPane.getStylesheets().add(getClass().getResource("/Utils/ScrollBar.css").toExternalForm());

            topBar.getChildren().addAll(backHyperlink, titleKategoriPengeluaran, tambahHyperlink);
            contentPane.getChildren().addAll(topBar, scrollPane);
            editMainPane.getChildren().add(contentPane);
            mainPane.getChildren().addAll(backgroundMainPane, editMainPane);
        });

        Label labelJumlah = new Label("Jumlah");
        labelJumlah.setStyle("-fx-font: 20 'Poppins Medium'");
        labelJumlah.setTextFill(Color.valueOf("#FFFFFF"));
        this.fieldJumlah.setMaxWidth(this.datePickerTanggal.getPrefWidth());
        this.fieldJumlah.getStylesheets().add(getClass().getResource("/Utils/TextField.css").toExternalForm());

        Label labelTipeTransaksi = new Label("Tipe Transaksi");
        labelTipeTransaksi.setStyle("-fx-font: 20 'Poppins Medium'");
        labelTipeTransaksi.setTextFill(Color.valueOf("#FFFFFF"));
        this.radioButtonCash = new RadioButton("Cash");
        this.radioButtonCash.getStylesheets().add(getClass().getResource("/Utils/RadioButton.css").toExternalForm());
        this.radioButtonTransfer = new RadioButton("Transfer");
        this.radioButtonTransfer.getStylesheets()
                .add(getClass().getResource("/Utils/RadioButton.css").toExternalForm());

        ToggleGroup toggleGroup = new ToggleGroup();
        this.radioButtonCash.setToggleGroup(toggleGroup);
        this.radioButtonTransfer.setToggleGroup(toggleGroup);

        Label labelKeterangan = new Label("Keterangan");
        labelKeterangan.setStyle("-fx-font: 20 'Poppins Medium'");
        labelKeterangan.setTextFill(Color.valueOf("#FFFFFF"));
        // TextField fieldKeterangan = new TextField();
        this.fieldKeterangan.setMaxWidth(200);
        this.fieldKeterangan.getStylesheets().add(getClass().getResource("/Utils/TextField.css").toExternalForm());

        Text historiKeuanganmu = createText("Histori Keuanganmu",
                "-fx-font: 30 'Poppins Regular'; -fx-fill: #FFFFFF;",
                0, 0);

        // Membuat konten histori keuangan
        // Yang perlu diambil di database adalah keterangan, nominal, tipe menggunakan
        // gambar, dan tanggal

        VBox kontenHistoriKeuangan = kontenHistoriKeuangan();
        kontenHistoriKeuangan.setSpacing(10);

        HBox isiKontenTulisan = new HBox(historiKeuanganmu);
        isiKontenTulisan.setSpacing(15);
        isiKontenTulisan.setPadding(new Insets(5, 0, 0, 25));
        isiKontenTulisan.setAlignment(Pos.CENTER_LEFT);

        VBox kontenTulisan = new VBox(isiKontenTulisan);

        VBox kontenBawahPane = new VBox(kontenTulisan, kontenHistoriKeuangan);
        kontenBawahPane.setPadding(new Insets(0, 0, 0, 10));
        kontenBawahPane.setSpacing(10);

        // HBOX
        StackPane stackPaneButton = new StackPane(buttonBG, buttonActive, buttonPengeluaran, buttonPemasukan);
        HBox hboxButtonTU = new HBox(stackPaneButton);

        StackPane.setAlignment(buttonPengeluaran, javafx.geometry.Pos.CENTER_LEFT);
        StackPane.setAlignment(buttonPemasukan, javafx.geometry.Pos.CENTER_RIGHT);

        stackPaneButton.setMinSize(hboxButtonTU.getMaxWidth() + 50, hboxButtonTU.getMaxHeight() + 50);
        buttonBG.setFill(Color.valueOf("#0D1416"));
        buttonBG.setWidth(360);
        buttonBG.setHeight(60);
        buttonBG.setArcWidth(50);
        buttonBG.setArcHeight(50);

        buttonActive.setFill(Color.valueOf("#761E19"));
        buttonActive.setWidth(170);
        buttonActive.setHeight(60);
        buttonActive.setArcWidth(50);
        buttonActive.setArcHeight(50);
        StackPane.setAlignment(buttonActive, javafx.geometry.Pos.CENTER_LEFT);

        HBox formTanggal = new HBox(labelTanggal, datePickerTanggal);
        formTanggal.setSpacing(20);

        HBox formKategori = new HBox(labelKategori, this.combobox, hyperlinkEdit);
        formKategori.setSpacing(20);

        HBox formNominal = new HBox(labelJumlah, fieldJumlah);
        formNominal.setSpacing(20);

        HBox formTipeTransaksi = new HBox(labelTipeTransaksi, radioButtonCash, radioButtonTransfer);
        formTipeTransaksi.setSpacing(20);
        formTipeTransaksi.setAlignment(Pos.CENTER_LEFT);

        HBox formKeterangan = new HBox(labelKeterangan, fieldKeterangan);
        formKeterangan.setSpacing(20);
        // formKeterangan.setAlignment(Pos.CENTER);

        // HBox hboxButtonSimpan = new HBox(buttonSimpan);
        // Menambahkan gambar

        Hyperlink hyperlinkSimpan = createButtonKategori("SimpanTanamUang.png", 200, 50, 88, 14, 40, 40);
        hyperlinkSimpan.setOnMouseClicked(e -> {
            if (isFormFilled()) {
                LocalDate selectedTanggal = this.datePickerTanggal.getValue();
                DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("YYYY-MM-d");
                String tanggal = selectedTanggal.format(formatTanggal).toString();

                String selectedKategori = this.combobox.getValue().toString();

                int kategoriId = 0;

                double jumlah = Double.parseDouble(fieldJumlah.getText().replace(",", ""));
                String keterangan = fieldKeterangan.getText();
                String tipePembayaran = "";

                if (radioButtonCash.isSelected()) {
                    tipePembayaran = radioButtonCash.getText();
                } else if (radioButtonTransfer.isSelected()) {
                    tipePembayaran = radioButtonTransfer.getText();
                }

                boolean isDefault = TanamUangModel.getIsKategoriDefault(selectedKategori);

                if (isDefault) {
                    kategoriId = TanamUangModel.getIdKategoriDefault(selectedKategori);
                } else {
                    kategoriId = TanamUangModel.getIdKategoriUser(selectedKategori);
                }

                if (TanamUangModel.simpanTanamUang(tanggal, selectedKategori, kategoriId, jumlah, getSaldo(),
                        tipePembayaran, keterangan, this.tipeTanamUang, isDefault)) {
                    if (this.tipeTanamUang.toLowerCase().equals("pengeluaran")) {
                        clearSelectionTanamUang("Pengeluaran telah tercatat");
                    } else {
                        clearSelectionTanamUang("Pemasukan telah tercatat");
                    }

                    kontenHistoriKeuangan.getChildren().add(kontenHistoriKeuangan());
                } else {
                    AlertHelper.alert("Mohon isi data Anda.");
                }
            } else {
                AlertHelper.alert("Mohon isi data Anda");
            }
        });

        HBox hboxKategori = new HBox(this.combobox, hyperlinkEdit);
        hboxKategori.setSpacing(10);

        VBox vboxKontenKiriKiri = new VBox(labelTanggal, labelKategori, labelJumlah);
        vboxKontenKiriKiri.setSpacing(18);
        vboxKontenKiriKiri.setAlignment(Pos.CENTER_LEFT);
        VBox vboxKontenKiriKanan = new VBox(this.datePickerTanggal, hboxKategori, this.fieldJumlah);
        vboxKontenKiriKanan.setSpacing(10);
        vboxKontenKiriKanan.setAlignment(Pos.CENTER_LEFT);

        HBox hboxGabunganKontenKiri = new HBox(vboxKontenKiriKiri, vboxKontenKiriKanan);
        hboxGabunganKontenKiri.setSpacing(20);
        hboxGabunganKontenKiri.setAlignment(Pos.CENTER_LEFT);

        HBox hboxRadioButton = new HBox(this.radioButtonCash, this.radioButtonTransfer);
        hboxRadioButton.setSpacing(20);
        hboxRadioButton.setAlignment(Pos.CENTER);

        VBox vboxKontenKananKiri = new VBox(labelTipeTransaksi, labelKeterangan);
        vboxKontenKananKiri.setSpacing(18);
        VBox vboxKontenKananKanan = new VBox(hboxRadioButton, fieldKeterangan, hyperlinkSimpan);
        vboxKontenKananKanan.setSpacing(10);

        HBox hboxGabunganKontenKanan = new HBox(vboxKontenKananKiri, vboxKontenKananKanan);
        hboxGabunganKontenKanan.setSpacing(20);
        hboxGabunganKontenKanan.setAlignment(Pos.CENTER_LEFT);

        HBox hboxKontenGabungan = new HBox(hboxGabunganKontenKiri, hboxGabunganKontenKanan);
        hboxKontenGabungan.setSpacing(60);

        VBox kontenTengah = new VBox(hboxButtonTU, hboxKontenGabungan);
        kontenTengah.setSpacing(20);

        // Membuat konten bagian tengah untuk main pane
        VBox kontenTengahPane = new VBox(kontenTengah);
        kontenTengahPane.setPadding(new Insets(0, 0, 0, 30));
        kontenTengahPane.setSpacing(10);

        fieldJumlah.textProperty().addListener(new ChangeListener<String>() {
            private String codeFormat = ",##0";
            private int codeLen = 4;

            @Override
            // ObservableValue = StringProperty (Represent an object that has changed its
            // state)
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Hapus listener sementara

                fieldJumlah.textProperty().removeListener(this);
                if (newValue.replace(",", "").matches("\\d*")) { // Check inputan angka atau tidak
                    if (newValue.length() > oldValue.length()) {
                        if (newValue.length() > 13) {
                            fieldJumlah.setText(oldValue);
                        } else {
                            updateCodeFormat(newValue);
                            formatAndSet(newValue, this.codeFormat);
                        }
                    }
                } else {
                    fieldJumlah.setText(oldValue);
                }

                // Tambahkan kembali listener setelah pembaruan teks
                fieldJumlah.textProperty().addListener(this);
            }

            private void formatAndSet(String text, String format) {
                DecimalFormat decimalFormat = new DecimalFormat(format);
                double value = Double.parseDouble(text.replace(",", ""));
                System.out.println(value);
                fieldJumlah.setText("");
                fieldJumlah.setText(decimalFormat.format(value));
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
        // VBox untuk menampung konten atas, tengah, dan bawah
        // VBox kontenVBox = new VBox(kontenAtasPane, kontenTengahPane,
        // kontenBawahPane);
        VBox kontenVBox = new VBox();
        kontenVBox.getChildren().addAll(kontenAtasPane, kontenTengahPane, kontenBawahPane);
        VBox.setMargin(kontenBawahPane, new Insets(5, 0, 0, 0));
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

        RightBarPantauUang rightBar = new RightBarPantauUang(this.saldo, this.userId);
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
    }

    private Hyperlink createButtonKategori(String imageName, double imageWidth, double imageHeight, double rectWidth, double rectHeight, double rectArcWidth, double rectArcHeight) {
        ImageView imageSimpan = new ImageView(new Image("/Assets/View/Dashboard/" + imageName));
        imageSimpan.setFitWidth(imageWidth);
        imageSimpan.setFitHeight(imageHeight);
        imageSimpan.setPreserveRatio(true);
        Rectangle hoverSimpan = new Rectangle(imageSimpan.getFitWidth() - rectWidth, imageSimpan.getFitHeight() - rectHeight);
        hoverSimpan.setFill(new Color(0, 0, 0, 0.5));
        hoverSimpan.setArcWidth(rectArcWidth);
        hoverSimpan.setArcHeight(rectArcHeight);
        hoverSimpan.setVisible(false);

        StackPane stackHoverSimpan = new StackPane(imageSimpan, hoverSimpan);
        stackHoverSimpan.setAlignment(Pos.CENTER);

        Hyperlink hyperlinkSimpan = new Hyperlink();
        hyperlinkSimpan.setGraphic(stackHoverSimpan);

        hyperlinkSimpan.setOnMouseEntered(g -> {
            hyperlinkSimpan.getScene().setCursor(Cursor.cursor("HAND"));
            hoverSimpan.setVisible(true);
        });
        hyperlinkSimpan.setOnMouseExited(g -> {
            hyperlinkSimpan.getScene().setCursor(Cursor.cursor("DEFAULT"));
            hoverSimpan.setVisible(false);
        });

        return hyperlinkSimpan;
    }

    private void refreshView(ScrollPane scrollPane, VBox scrollContentBox, StackPane mainPane) {
        scrollContentBox.getChildren().clear();

        VBox contentPane = new VBox();
        contentPane.setStyle("-fx-background-radius: 20");
        contentPane.setMaxSize(mainPane.getMaxWidth(), mainPane.getMaxHeight() - 20);
        contentPane.setSpacing(20);

        VBox scrollPaneContent = createScrollPaneContent(scrollPane, mainPane);

        scrollPaneContent.setSpacing(15);
        scrollPaneContent.setAlignment(Pos.CENTER);
        scrollPaneContent.setStyle("-fx-background-color: #141F23");

        scrollPane.setContent(scrollPaneContent);
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

    private VBox createScrollPaneContent(ScrollPane scrollPane, StackPane mainPane) {
        VBox scrollPaneContent = new VBox();
        String[] listKategori = TanamUangModel.getKategoriTanamUang(this.tipeTanamUang);

        for (int i = 0; i < listKategori.length; i++) {
            Text namaKategori = createText(listKategori[i], "-fx-font: 16 Poppins;", "#FFFFFF");
            Hyperlink editHyperlink = new Hyperlink();
            editHyperlink.setGraphic(new ImageView(new Image("/Assets/View/Dashboard/Edit.png")));
            Hyperlink deleteHyperlink = new Hyperlink();
            deleteHyperlink.setGraphic(new ImageView(new Image("/Assets/View/Dashboard/Delete.png")));

            HBox hboxNamaKategori = new HBox(namaKategori);
            HBox hboxEditHyperlink = new HBox(editHyperlink);
            HBox hboxDeleteHyperlink = new HBox(deleteHyperlink);

            HBox.setHgrow(hboxNamaKategori, Priority.ALWAYS);
            hboxNamaKategori.setAlignment(Pos.CENTER_LEFT);
            hboxEditHyperlink.setAlignment(Pos.CENTER_LEFT);
            hboxDeleteHyperlink.setAlignment(Pos.CENTER_LEFT);

            hboxNamaKategori.setPadding(new Insets(0, 0, 0, 20));

            HBox itemContent = new HBox(hboxNamaKategori, hboxEditHyperlink, hboxDeleteHyperlink);

            int index = i;
            boolean isKategoriDefault = TanamUangModel.getIsKategoriDefault(namaKategori.getText());

            deleteHyperlink.setOnMouseClicked(e -> {
                StackPane paneHapus = new StackPane();
                StackPane backgroundPaneHapus = new StackPane();

                // paneHapus.setMaxSize(mainPane.getWidth() - 400, mainPane.getHeight() - 400);
                paneHapus.setMaxSize(450, 200);
                paneHapus.setStyle("-fx-background-radius: 20; -fx-background-color: #141F23");
                backgroundPaneHapus.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8);");

                Text titleHapus = createText("Hapus Kategori", "-fx-font: 20 'Poppins Bold';", "#FFFFFF");
                Hyperlink deleteBackHyperlink = new Hyperlink();
                deleteBackHyperlink.setGraphic(new ImageView(new Image("/Assets/View/Dashboard/Back.png")));

                deleteBackHyperlink.setOnMouseClicked(f -> {
                    refreshView(scrollPane, scrollPaneContent, mainPane);
                    mainPane.getChildren().remove(paneHapus);
                    mainPane.getChildren().remove(backgroundPaneHapus);
                });

                Text titleYakinHapus = createText("Apakah Anda yakin ingin menghapus kategori ",
                        "-fx-font: 18 'Poppins Medium';", "#FFFFFF");
                Text titleNamaKategoriHapus = createText(listKategori[index], "-fx-font: bold 18 Poppins;", "#ff424c");
                Text titleTandaTanya = createText("?", "-fx-font: 18 'Poppins Medium';", "#FFFFFF");

                HBox hboxTitleHapus = new HBox(deleteBackHyperlink, titleHapus);
                hboxTitleHapus.setSpacing(20);
                hboxTitleHapus.setAlignment(Pos.CENTER_LEFT);

                HBox hboxSubTitleHapus = new HBox(titleYakinHapus, titleNamaKategoriHapus, titleTandaTanya);
                hboxSubTitleHapus.setAlignment(Pos.CENTER);
                hboxSubTitleHapus.setPadding(new Insets(0, 20, 0, 20));

                ImageView imageHapusKategori = new ImageView(new Image("/Assets/View/Dashboard/Hapus.png"));
                imageHapusKategori.setFitWidth(200);
                imageHapusKategori.setFitHeight(50);
                imageHapusKategori.setPreserveRatio(true);
                Rectangle hoverHapusKategori = new Rectangle(imageHapusKategori.getFitWidth() - 105, imageHapusKategori.getFitHeight() - 15);
                hoverHapusKategori.setFill(new Color(0, 0, 0, 0.5));
                hoverHapusKategori.setArcWidth(40);
                hoverHapusKategori.setArcHeight(40);
                hoverHapusKategori.setVisible(false);

                StackPane stackHoverHapusKategori = new StackPane(imageHapusKategori, hoverHapusKategori);
                stackHoverHapusKategori.setAlignment(Pos.CENTER);
                StackPane.setMargin(hoverHapusKategori, new Insets(10, 0, 0, 0));

                Hyperlink hyperlinkHapusKategori = new Hyperlink();
                hyperlinkHapusKategori.setGraphic(stackHoverHapusKategori);

                hyperlinkHapusKategori.setOnMouseEntered(g -> {
                    hyperlinkHapusKategori.getScene().setCursor(Cursor.cursor("HAND"));
                    hoverHapusKategori.setVisible(true);
                });
                hyperlinkHapusKategori.setOnMouseExited(g -> {
                    hyperlinkHapusKategori.getScene().setCursor(Cursor.cursor("DEFAULT"));
                    hoverHapusKategori.setVisible(false);
                });

                StackPane stackHyperlinkHapusKategori = new StackPane(hyperlinkHapusKategori);

                hyperlinkHapusKategori.setOnAction(f -> {
                    if (isKategoriDefault) {
                        if (TanamUangModel.hapusKategori(listKategori[index], isKategoriDefault)) {
                            refreshView(scrollPane, scrollPaneContent, mainPane);

                            if (mainPane.getParent() != null) {
                                mainPane.getChildren().remove(paneHapus);
                                mainPane.getChildren().remove(backgroundPaneHapus);
                            }
                            AlertHelper.info("Kategori " + listKategori[index] + " berhasil dihapus!");
                        } else {
                            AlertHelper.alert("Kategori gagal dihapus!");
                        }
                    } else {
                        if (TanamUangModel.hapusKategori(listKategori[index], isKategoriDefault)) {
                            refreshView(scrollPane, scrollPaneContent, mainPane);

                            if (mainPane.getParent() != null) {
                                mainPane.getChildren().remove(paneHapus);
                                mainPane.getChildren().remove(backgroundPaneHapus);
                            }
                            AlertHelper.info("Kategori " + listKategori[index] + " berhasil dihapus!");
                        } else {
                            AlertHelper.alert("Kategori gagal dihapus!");
                        }
                    }
                });

                VBox itemContentHapus = new VBox(hboxTitleHapus, hboxSubTitleHapus, stackHyperlinkHapusKategori);
                itemContentHapus.setSpacing(20);
                itemContentHapus.setMaxSize(paneHapus.getMaxWidth() - 100, paneHapus.getMaxHeight() - 30);
                itemContentHapus.setPadding(new Insets(10, 0, 0, 10));

                paneHapus.getChildren().add(itemContentHapus);

                if (!mainPane.getChildren().contains(paneHapus)) {
                    mainPane.getChildren().addAll(backgroundPaneHapus, paneHapus);
                }
            });

            editHyperlink.setOnMouseClicked(e -> {
                StackPane paneEdit = new StackPane();
                StackPane backgroundPaneEdit = new StackPane();
                paneEdit.setMaxSize(400, 190);
                paneEdit.setStyle("-fx-background-radius: 20; -fx-background-color: #141F23");

                backgroundPaneEdit.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8);");
                Hyperlink editBackHyperlink = new Hyperlink();
                editBackHyperlink.setGraphic(new ImageView(new Image("/Assets/View/Dashboard/Back.png")));

                editBackHyperlink.setOnMouseClicked(f -> {
                    refreshView(scrollPane, scrollPaneContent, mainPane);
                    mainPane.getChildren().remove(paneEdit);
                    mainPane.getChildren().remove(backgroundPaneEdit);
                });

                Text titleEdit = createText("Ubah Kategori", "-fx-font: 20 'Poppins Bold';", "#FFFFFF");
                Label labelEditNamaKategori = new Label("Nama:");
                labelEditNamaKategori.setStyle("-fx-font: 16 'Poppins Medium'");
                labelEditNamaKategori.setTextFill(Color.valueOf("#FFFFFF"));
                TextField fieldEditNamaKategori = new TextField(listKategori[index]);
                fieldEditNamaKategori.getStylesheets().add(getClass().getResource("/Utils/TextField.css").toExternalForm());

                Hyperlink hyperlinkSimpanEditKategori = createButtonKategori("SimpanTanamUang.png", 150, 40, 62, 12, 30, 30);
                StackPane stackHyperlinkSimpanEditKategori = new StackPane(hyperlinkSimpanEditKategori);
                String nama_kategori = namaKategori.getText();

                hyperlinkSimpanEditKategori.setOnMouseClicked(f -> {
                    String valueFieldEditNamaKategori = fieldEditNamaKategori.getText();
                    
                    if (listKategori[index].equals(valueFieldEditNamaKategori)) {
                        if (mainPane.getParent() != null) {
                            mainPane.getChildren().remove(paneEdit);
                            mainPane.getChildren().remove(backgroundPaneEdit);
                        }
                    } else if (isKategoriDefault) {
                        int idKategoriDefault = TanamUangModel.getIdKategoriDefault(nama_kategori);
                        boolean statusSimpan = TanamUangModel.simpanNamaKategori(valueFieldEditNamaKategori,
                                namaKategori.getText(),
                                isKategoriDefault, this.tipeTanamUang, idKategoriDefault);

                        if (statusSimpan) {
                            refreshView(scrollPane, scrollPaneContent, mainPane);

                            if (mainPane.getParent() != null) {
                                mainPane.getChildren().remove(paneEdit);
                                mainPane.getChildren().remove(backgroundPaneEdit);
                            }

                            AlertHelper.info("Berhasil mengubah nama kategori!");
                        } else {
                            AlertHelper.alert("Gagal mengubah nama kategori!");
                        }
                    } else {
                        boolean statusSimpan = TanamUangModel.simpanNamaKategori(valueFieldEditNamaKategori,
                                namaKategori.getText(),
                                isKategoriDefault, this.tipeTanamUang, 0);
                        if (statusSimpan) {
                            refreshView(scrollPane, scrollPaneContent, mainPane);

                            if (mainPane.getParent() != null) {
                                mainPane.getChildren().remove(paneEdit);
                                mainPane.getChildren().remove(backgroundPaneEdit);
                            }

                            AlertHelper.info("Berhasil mengubah nama kategori!");
                        } else {
                            AlertHelper.alert("Gagal mengubah nama kategori!");
                        }
                    }
                });

                HBox editTitleHBox = new HBox(editBackHyperlink, titleEdit);
                editTitleHBox.setSpacing(20);
                editTitleHBox.setAlignment(Pos.CENTER_LEFT);
                
                HBox editHBox = new HBox(labelEditNamaKategori, fieldEditNamaKategori);
                editHBox.setSpacing(20);
                editHBox.setAlignment(Pos.CENTER);

                VBox editVBox = new VBox(editTitleHBox, editHBox, stackHyperlinkSimpanEditKategori);
                editVBox.setMaxSize(paneEdit.getMaxWidth() - 50, paneEdit.getMaxHeight() - 50);
                editVBox.setSpacing(20);
                editVBox.setAlignment(Pos.CENTER);

                paneEdit.getChildren().add(editVBox);

                if (!mainPane.getChildren().contains(paneEdit)) {
                    mainPane.getChildren().add(backgroundPaneEdit);
                    mainPane.getChildren().add(paneEdit);
                }
            });

            VBox itemBox = new VBox();
            itemBox.setMaxSize(scrollPane.getMaxWidth() - 20, 400);
            itemBox.getChildren().add(itemContent);
            itemBox.setAlignment(Pos.CENTER);
            itemBox.setStyle("-fx-background-color: #213339; -fx-background-radius: 10px;");

            scrollPaneContent.getChildren().add(itemBox);
        }

        scrollPaneContent.setSpacing(15);
        // scrollPaneContent.setMaxSize(mainPane.getWidth(), mainPane.getHeight() - 20);
        scrollPaneContent.setAlignment(Pos.CENTER);
        scrollPaneContent.setStyle("-fx-background-color: #141F23");
        // scrollPaneContent.setStyle("-fx-background-color: #C21292");
        return scrollPaneContent;
    }

    // fungsi untuk membuat text dengan return Text
    private Text createText(String text, String style, String color) {
        Text newText = new Text(text);
        newText.setStyle(style); // menetapkan style text
        newText.setFill(Color.valueOf(color)); // menetapkan warna text
        return newText;
    }

    private void clearSelectionTanamUang(String message) {
        this.datePickerTanggal.setValue(null);
        this.combobox.getSelectionModel().clearSelection();
        this.fieldJumlah.setText("");
        this.fieldKeterangan.setText("");
        this.radioButtonCash.setSelected(false);
        this.radioButtonTransfer.setSelected(false);

        AlertHelper.info(message);
    }

    private boolean isFormFilled() {
        System.out.println("DatePicker: " + (this.datePickerTanggal.getValue() != null));
        System.out.println("ComboBox: " + (this.combobox.getValue() != null));
        System.out.println("Field Jumlah: " + (!this.fieldJumlah.getText().isEmpty()));
        System.out.println("field Keterangan: " + (!this.fieldKeterangan.getText().isEmpty()));
        System.out.println(
                "Radio button: " + (this.radioButtonCash.isSelected() || this.radioButtonTransfer.isSelected()));

        return this.datePickerTanggal.getValue() != null &&
                this.combobox.getValue() != null &&
                !this.fieldJumlah.getText().isEmpty() &&
                !this.fieldKeterangan.getText().isEmpty() &&
                (this.radioButtonCash.isSelected() || this.radioButtonTransfer.isSelected());
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

    private VBox kontenHistoriKeuangan() {
        VBox kontenHistoriKeuangan = new VBox();
        kontenHistoriKeuangan.setSpacing(10);
        
        List<String> keteranganBarangList = model.getKeteranganBarangList();
        List<Double> nominalBarangList = model.getNominalBarangList();
        List<String> tipeBarangList = model.getTipeBarangList();
        List<String> tanggalBarangList = model.getTanggalBarangList();

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

            Label labelKeteranganHistori = new Label(tampilanKeterangan);
            labelKeteranganHistori.setStyle("-fx-font: 15 'Poppins SemiBold'; -fx-text-fill: #ffffff;"); // Set the
                                                                                                         // style

            // Tambahkan tooltip yang menampilkan saldo lengkap
            Tooltip tooltipKeterangan = new Tooltip(keteranganText.getText());
            Tooltip.install(labelKeteranganHistori, tooltipKeterangan);

            VBox keteranganStackPane = new VBox(labelKeteranganHistori);
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
            gambarKondisidanNominal.setSpacing(10);
            VBox nominalStackPane = new VBox(gambarKondisidanNominal);
            nominalStackPane.setAlignment(Pos.CENTER);

            Text tanggalText = createText(formatTanggal(tanggal), "-fx-font: 15 'Poppins Bold'; -fx-fill: #798F97;",
                    0, 0);

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
            keteranganStackPane.setMaxWidth(200);
            nominalStackPane.setMaxWidth(200);
            tanggalTextPane.setMaxWidth(200);

        }
        return kontenHistoriKeuangan;
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

    public void popUpUntukModeUser() {
        RightBarTanamUang rightBar = new RightBarTanamUang(this.saldo, this.userId);
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

        Button closeButton = createButton(28, 28, "X", "FF4646", 15, "Poppins", 30, "0F181B");
        // BUTTON EVENTS
        // saat di hover maka cursor berbeda
        closeButton.setOnMouseEntered(closeEvent -> {
            closeButton.getScene().setCursor(hand);
            updateButton(closeButton, 28, 28, "X", "6A1B1B", 15, "Poppins", 40, "0F181B");
        });
        closeButton.setOnMouseExited(closeEvent -> {
            closeButton.getScene().setCursor(defaultCursor);
            updateButton(closeButton, 28, 28, "X", "FF4646", 15, "Poppins", 40, "0F181B");
        });

        StackPane konteneditBack = new StackPane(closeButton);
        konteneditBack.setAlignment(Pos.TOP_RIGHT);
        konteneditBack.setPadding(new Insets(0, 20, 0, 0));
        StackPane konteneditTitle = new StackPane(titleEdit);
        konteneditTitle.setAlignment(Pos.CENTER);
        konteneditTitle.setPadding(new Insets(20, 0, 0, 26));

        HBox editTitleHBox = new HBox(konteneditTitle, konteneditBack);
        HBox.setHgrow(konteneditTitle, Priority.ALWAYS);
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
        ImageView profileImage1 = new ImageView(new Image("/Assets/View/Dashboard/profile.png"));
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

        // Membuat StackPane
        StackPane teksKritisPane = new StackPane(labelKeteranganKritis);
        teksKritisPane.setAlignment(Pos.CENTER);
        teksKritisPane.setStyle("-fx-background-radius: 20; -fx-background-color: #141F23");
        teksKritisPane.setMinWidth(120);
        teksKritisPane.setMaxHeight(100);

        // Membuat tooltip
        Tooltip tooltipKritis = createCustomTooltip(teksKritis.getText());

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

        kontenKritis.setOnMouseMoved(event -> {
            updateTooltipPosition(tooltipKritis, event.getScreenX(), event.getScreenY());
        });

        // Konten tengah tengah karantina
        Circle backgroundProfileCircle2 = new Circle(40);
        backgroundProfileCircle2.setFill(Color.valueOf("#FD9C3D"));
        Circle profileCircle2 = new Circle(35);
        profileCircle2.setFill(Color.valueOf("#141F23"));
        ImageView profileImage2 = new ImageView(new Image("/Assets/View/Dashboard/profile.png"));
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

        // Membuat StackPane
        StackPane teksKarantinaPane = new StackPane(labelKeteranganKarantina);
        teksKarantinaPane.setAlignment(Pos.CENTER);
        teksKarantinaPane.setStyle("-fx-background-radius: 20; -fx-background-color: #141F23");
        teksKarantinaPane.setMinWidth(140);
        teksKarantinaPane.setMaxHeight(100);

        Tooltip tooltipKarantina = createCustomTooltip(teksKarantina.getText());

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

        kontenKarantina.setOnMouseMoved(event -> {
            updateTooltipPosition(tooltipKarantina, event.getScreenX(), event.getScreenY());
        });

        // Konten kanan tengah sehat
        Circle backgroundProfileCircle3 = new Circle(40);
        backgroundProfileCircle3.setFill(Color.valueOf("#7AFF64"));
        Circle profileCircle3 = new Circle(35);
        profileCircle3.setFill(Color.valueOf("#141F23"));
        ImageView profileImage3 = new ImageView(new Image("/Assets/View/Dashboard/profile.png"));
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

        // Membuat StackPane
        StackPane teksSehatPane = new StackPane(labelKeteranganSehat);
        teksSehatPane.setAlignment(Pos.CENTER);
        teksSehatPane.setStyle("-fx-background-radius: 20; -fx-background-color: #141F23");
        teksSehatPane.setMinWidth(120);
        teksSehatPane.setMaxHeight(100);

        Tooltip tooltipSehat = createCustomTooltip(teksSehat.getText());

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

        kontenSehat.setOnMouseMoved(event -> {
            updateTooltipPosition(tooltipSehat, event.getScreenX(), event.getScreenY());
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

        Text titleKontenKiri = createText("Atur Batas Kritis,", "-fx-font: 25 'Poppins Regular'; -fx-fill: #FFFFFF;", 0,
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

        ImageView saveImage = new ImageView("/Assets/View/Dashboard/Simpan.png");
        saveImage.setFitWidth(200);
        saveImage.setFitHeight(60);
        saveImage.setPreserveRatio(true);

        Hyperlink saveHyperlink = new Hyperlink();
        saveHyperlink.setGraphic(saveImage);
        saveHyperlink.setOnMouseClicked(e -> {
            if (updateKritis(
                    inputKritis.getText().isEmpty() ? 0 : Double.parseDouble(inputKritis.getText().replace(",", "")))) {
                this.mainPane.getChildren().remove(backgroundPaneModeUserPane);
                refreshViewAll();
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

        closeButton.setOnMouseClicked(e -> {
            this.mainPane.getChildren().remove(backgroundPaneModeUserPane);
        });

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

    private void updateTooltipPosition(Tooltip tooltip, double x, double y) {
        tooltip.setX(x + 10); // Sesuaikan posisi X agar tidak menutupi cursor
        tooltip.setY(y - 20); // Sesuaikan posisi Y agar tidak menutupi cursor
    }

    private Tooltip createCustomTooltip(String content) {
        Tooltip tooltip = new Tooltip(content);
        tooltip.setStyle("-fx-font: 15 'Poppins'; -fx-background-color: #141F23;");
        tooltip.setAutoHide(false);
        return tooltip;
    }

    private boolean updateKritis(double inputKritis) {
        BatasKritis batasKritis = new BatasKritis(this.userId);
        return batasKritis.setBatasKritis(inputKritis);
    }

    private void refreshViewAll() {
        mainPane.getChildren().clear();
        start(TanamUangModel.getKategoriTanamUang("pemasukan"),
                TanamUangModel.getKategoriTanamUang("pengeluaran"));
    }

    class ImageLinkPane {
        TanamUangPage TanamUangPage;

        public ImageLinkPane(TanamUangPage TanamUangPage) {
            this.TanamUangPage = TanamUangPage;
        }

        public VBox createImageLinkVBox(Stage stage, SceneController sceneController) {
            // Gunakan ImageView untuk semua pilihan di Sidebar
            ImageView logoImageView = new ImageView(new Image("/Assets/View/Dashboard/Logo.png"));
            logoImageView.setFitWidth(240);
            logoImageView.setFitHeight(70);
            logoImageView.setPreserveRatio(true);

            ImageView homePageImageView = new ImageView(new Image("/Assets/View/Dashboard/HomePage.png"));
            ImageView tanamUangImageView = new ImageView(new Image("/Assets/View/Dashboard/Tanam Uang.png"));
            ImageView pantauUangImageView = new ImageView(new Image("/Assets/View/Dashboard/Pantau Uang.png"));
            ImageView panenUangImageView = new ImageView(new Image("/Assets/View/Dashboard/Panen Uang.png"));
            ImageView modeUser = new ImageView("/Assets/View/Dashboard/Mode User.png");
            ImageView MulaiMencatatSekarang = new ImageView(
                    "/Assets/View/Dashboard/MulaiMencatatSekarang!.png");
            ImageView logOut = new ImageView("/Assets/View/Dashboard/Log Out.png");

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

            Text tanamUang = createText("Tanam Uang", "-fx-font: 20 'Poppins'; -fx-fill: #ffffff;", 0, 0);
            StackPane tanamUangPane = new StackPane(tanamUang);
            tanamUangPane.setAlignment(Pos.CENTER_LEFT);
            HBox tanamUangHBox = new HBox(tanamUangImageView, tanamUangPane);
            tanamUangHBox.setSpacing(10);
            tanamUangHBox.setAlignment(Pos.CENTER_LEFT);

            // Membuat Hyperlink dengan menggunakan HyperlinkText
            HyperlinkText panenUangHyperlink = new HyperlinkText("Panen Uang");
            panenUangHyperlink.setOnAction(e -> sceneController.switchToPanenUang());
            panenUangHyperlink.setBorder(Border.EMPTY);
            HBox panenUangHBox = new HBox(panenUangImageView, panenUangHyperlink);
            panenUangHBox.setSpacing(10);
            panenUangHBox.setAlignment(Pos.CENTER_LEFT);

            HyperlinkText homeHyperlinkText = new HyperlinkText("Home");
            homeHyperlinkText.setOnAction(e -> sceneController.switchToDashboard());
            homeHyperlinkText.setBorder(Border.EMPTY);
            HBox homePageHBox = new HBox(homePageImageView, homeHyperlinkText);
            homePageHBox.setSpacing(10);
            homePageHBox.setAlignment(Pos.CENTER_LEFT);

            HyperlinkText pantauUangHyperlink = new HyperlinkText("Pantau Uang");
            pantauUangHyperlink.setOnAction(e -> sceneController.switchToPantauUang());
            pantauUangHyperlink.setBorder(Border.EMPTY);
            HBox pantauUangHBox = new HBox(pantauUangImageView, pantauUangHyperlink);
            pantauUangHBox.setSpacing(10);
            pantauUangHBox.setAlignment(Pos.CENTER_LEFT);

            HyperlinkText modeUserHyperlink = new HyperlinkText("Mode User");
            modeUserHyperlink.setOnAction(e -> TanamUangPage.popUpUntukModeUser());
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
            VBox kontenSideAtas = new VBox(homePageHBox, tanamUangHBox, pantauUangHBox, panenUangHBox,
                    modeUserHBox);
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

        private static Hyperlink createHyperlinkWithImageView(ImageView imageView) {
            Hyperlink hyperlink = new Hyperlink();
            hyperlink.setGraphic(imageView);
            return hyperlink;
        }

        // metode untukpopup mode user

        private Text createText(String text, String style, double translateX, double translateY) {
            Text customText = new Text(text);
            customText.setStyle(style);
            customText.setTranslateX(translateX);
            customText.setTranslateY(translateY);
            customText.toFront();
            return customText;
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

class RightBarTanamUang {
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

    public RightBarTanamUang(double saldo, int userId) {
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
        ImageView profileImage = new ImageView(new Image("/Assets/View/Dashboard/Profile.png"));
        profileImage.setFitWidth(50);
        profileImage.setFitHeight(50);
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
        mmcCombineLevel.setAlignment(Pos.TOP_CENTER);
        VBox.setVgrow(progressStackPane, Priority.ALWAYS);
        VBox.setMargin(progressStackPane, new Insets(0, 10, 0, 10));

        HBox allCombine = new HBox(mmcCombineLevel);
        allCombine.setStyle("-fx-background-color: #141F23;");
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