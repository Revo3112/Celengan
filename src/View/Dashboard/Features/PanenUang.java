package View.Dashboard.Features;

import Controller.SceneController;
import Model.HapusTarget;
import Model.LoginModel;
import Model.PantauPemasukanPengeluaran;
import Model.PieChartData;
import Model.TampilkanSemuaTarget;
import Model.TanamUangModel;
import Utils.AlertHelper;
import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.text.BoxView;

import javafx.stage.Stage;
import javafx.util.Duration;

// class DashboardPage digunakan untuk menampilkan halaman dashboard
public class PanenUang {

    private Stage stage;
    private int userId;
    private TextField namaTarget;
    private TextField nominalTarget;
    private TextField keteranganBarang;
    // Pokoknya dibawah sini itu untuk ngedapetin kupulan data dari database
    private static TampilkanSemuaTarget model = new TampilkanSemuaTarget();
    private static List<String> namaTargetList = model.getNamaTargetList();
    private static List<Double> nominalTargetList = model.getNominalTargetList();
    private static List<String> keteranganBarangList = model.getKeteranganBarangList();
    private VBox scrollContetBox;
    private ScrollPane scrollPane;
    public DecimalFormat formatRupiah;
    

    private StackPane root = new StackPane();

    private ComboBox<String> combobox = new ComboBox<String>();
    private Text title = new Text("Pengeluaran");
    private String tipeTanamUang = "pengeluaran";
    private TextField fieldJumlah = new TextField();
    private TextField fieldKeterangan = new TextField();
    private RadioButton radioButtonCash = new RadioButton("Cash");
    private RadioButton radioButtonTransfer = new RadioButton("Transfer");
    private DatePicker datePickerTanggal = new DatePicker();
    private SceneController sceneController;

    public PanenUang(Stage stage) {
        this.stage = stage;
        LoginModel loginModel = new LoginModel();
        this.userId = loginModel.getUserId();
        this.sceneController = new SceneController(stage);
    }

    // Menampilkan halaman dashboard
    public void start() {
        // Membuat side bar
        VBox sideBar = ImageLinkPane.createImageLinkVBox(this.stage, sceneController);
        // sideBar.setTranslateX(-stage.getWidth() / 2 + 40);
        sideBar.setAlignment(Pos.CENTER_RIGHT);
        VBox.setVgrow(sideBar, Priority.ALWAYS);

        // Membuat teks welcome
        Text welcome = createText("Panen Hasil dari\n", "-fx-font: 35 'Poppins Regular'; -fx-fill: #FFFFFF;", 0, 0);
        Text name = createText("Target-targetmu!", "-fx-font: 35 'Poppins SemiBold'; -fx-fill: #FFFFFF;", 0, 10);

        // StackPane untuk menampung teks
        StackPane textPane = new StackPane(welcome, name);
        textPane.setAlignment(Pos.CENTER_LEFT);
        textPane.setPadding(new Insets(0, 0, 20, 10));

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
        kontenAtasPane.setSpacing(100);
        // HBox.setHgrow(textPane, Priority.ALWAYS);
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

        namaTarget = new TextField();
        namaTarget.setMaxSize(120, 40);
        namaTarget.setPromptText("Nama Target");
        namaTarget.setTranslateY(-80);

        nominalTarget = new TextField();
        nominalTarget.setPromptText("Nominal Target");
        nominalTarget.setMaxSize(120, 40);
        nominalTarget.setTranslateY(0);

        keteranganBarang = new TextField();
        keteranganBarang.setPromptText("Keterangan Barang");
        keteranganBarang.setTranslateY(80);
        keteranganBarang.setMaxHeight(80);
        keteranganBarang.setMaxWidth(160);

        Label labelTarget = new Label("Nama Target");
        labelTarget.setTranslateY(-87);
        labelTarget.setTranslateX(-149);

        Label labelnominal = new Label("Nominal Target");
        labelnominal.setTranslateY(-7);
        labelnominal.setTranslateX(-149);

        Label labelBarang = new Label("Keterangan Barang");
        labelBarang.setTranslateY(80);
        labelBarang.setTranslateX(-149);

        Image iconSilang = new Image("file:src/Assets/View/Login_Register/icons8-cross-mark-48.png");
        ImageView iconSilangView = new ImageView(iconSilang);
        iconSilangView.toFront();

        // Tombol untuk menutup kotak input
        Button menutupKotakInput = new Button();
        menutupKotakInput.setTranslateY(-104);
        menutupKotakInput.setTranslateX(208);
        menutupKotakInput.setGraphic(iconSilangView);
        menutupKotakInput.setMinSize(20, 20);
        menutupKotakInput.toFront();

        // Tombol untuk menambahkan target kedalam databases
        Button menambahkanTarget = new Button("Tambah Target");
        menambahkanTarget.setTranslateY(84);
        menambahkanTarget.setTranslateX(201);
        menambahkanTarget.setMinSize(20, 20);
        menambahkanTarget.toFront();
        menambahkanTarget.setOnAction(e -> {
            LoginModel loginModel = new LoginModel();
            setUserTarget(loginModel.getUserId(), namaTarget.getText(),
                    Double.parseDouble(nominalTarget.getText().replace(",", "")),
                    keteranganBarang.getText());
            namaTarget.clear();
            nominalTarget.clear();
            keteranganBarang.clear();
        });

        // Supaya ada efek koma ketika user memasukan input kedalam field nominal target
        nominalTarget.textProperty().addListener(new ChangeListener<String>() {
            private String codeFormat = ",##0";
            private int codeLen = 4;

            @Override
            // ObservableValue = StringProperty (Represent an object that has changed its
            // state)
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Hapus listener sementara

                nominalTarget.textProperty().removeListener(this);
                if (newValue.replace(",", "").matches("\\d*")) { // Check inputan angka atau tidak
                    if (newValue.length() > oldValue.length()) {
                        if (newValue.length() > 13) {
                            nominalTarget.setText(oldValue);
                        } else {
                            updateCodeFormat(newValue);
                            formatAndSet(newValue, this.codeFormat);
                        }
                    }
                } else {
                    nominalTarget.setText(oldValue);
                }

                // Tambahkan kembali listener setelah pembaruan teks
                nominalTarget.textProperty().addListener(this);
            }

            private void formatAndSet(String text, String format) {
                DecimalFormat decimalFormat = new DecimalFormat(format);
                double value = Double.parseDouble(text.replace(",", ""));
                System.out.println(value);
                nominalTarget.setText("");
                nominalTarget.setText(decimalFormat.format(value));
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

        // Popup untuk kotak input
        StackPane kotakInput = new StackPane();
        kotakInput.getChildren().addAll(namaTarget, nominalTarget, keteranganBarang, labelTarget, labelnominal,
                labelBarang, menutupKotakInput, menambahkanTarget);
        kotakInput.setMaxSize(this.stage.getWidth() - 400, this.stage.getHeight() - 150);
        kotakInput.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 20;");
        kotakInput.setPadding(new Insets(10, 10, 10, 10));
        kotakInput.setAlignment(javafx.geometry.Pos.CENTER);

        // Membuat VBox untuk menaruh item-item yang ada nantinya dapat di scroll
        scrollContetBox = new VBox();
        scrollContetBox.setSpacing(10);
        scrollContetBox.toFront();

        // Untuk scroll pane agar vbox vbox tersebut dapat di scroll
        scrollPane = new ScrollPane(scrollContetBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setMaxSize(stage.getWidth() - 350, stage.getHeight() - 250);
        scrollPane.toFront();

        // Memaksimalkan ukuran vbox dengan scrollpane
        scrollContetBox.setMaxHeight(scrollPane.getMaxHeight());
        scrollContetBox.setMaxWidth(scrollPane.getMaxWidth() - 20);

        // Logika untuk membuat vbox vbox yang nantinya dapa di scroll
        for (int i = 0; i < mengambilBanyakDataDiTarget(); i++) {
            String namaTarget = namaTargetList.get(i);
            double nominalTarget = nominalTargetList.get(i);
            String keteranganBarang = keteranganBarangList.get(i);

            // Membulatkan tampilan uang agar tidak muncul E
            long roundedValue = Math.round(nominalTarget);

            // Membuat tampilan untuk menampilkan data yang ada
            VBox itemBox = new VBox(); // Membuat VBox baru untuk setiap item
            // Warna biru terang
            itemBox.setStyle("-fx-background-color: #ADD8E6; -fx-padding: 10; -fx-spacing: 5; -fx-border-radius: 10;");
            itemBox.setMaxHeight(150);
            itemBox.setPrefWidth(scrollPane.getMaxWidth() - 20);

            // Membuat tampilan untuk menampilkan data yang ada
            String itemtext = "Nama Target: " + namaTarget + "\nNominal Target: " + formatDuit(roundedValue)
                    + "\nKeterangan Barang: " + keteranganBarang;

            Label itemLabel = new Label(itemtext);

            // Untuk membuat progres bar
            ProgressBar progressBar = new ProgressBar();
            progressBar.setProgress(kemajuanProgres(namaTarget));

            // Menampilkan progress text
            Text progressText = new Text(
                    formatDuit(mendapatkanSaldoUntukMembeliBarang()) + " / " + formatDuit(roundedValue));
            progressText.setStyle("-fx-font: 15 'Poppins-Regular';");

            // Membuat Vbox untuk menangani progress bar dan progress text
            VBox progressBox = new VBox();
            progressBox.getChildren().addAll(progressBar, progressText);
            progressBox.setSpacing(2);
            progressBar.setMaxWidth(itemBox.getPrefWidth() - 20);
            progressBox.setAlignment(Pos.CENTER);

            // Ini digunakan untuk menghapus target yang ada di tampilan
            Button hapusTarget = new Button("Hapus Target");
            hapusTarget.setOnAction(e -> {
                HapusTarget hapusTargetModel = new HapusTarget(namaTarget);
                if (hapusTargetModel.start()) {
                    AlertHelper.info("Target berhasil dihapus!");
                    refreshView();
                } else {
                    AlertHelper.alert("Target gagal dihapus!");
                }
                refreshView();
            });

            mainPane.getChildren().addAll(welcome, tambahTarget, scrollPane);
            mainPane.setMaxSize(this.stage.getWidth() - 200, this.stage.getHeight() - 100);
            mainPane.setStyle("-fx-background-color: #141F23; -fx-background-radius: 20;");
            mainPane.setPadding(new Insets(10, 10, 10, 10));
            mainPane.setAlignment(javafx.geometry.Pos.CENTER);
    
            StackPane backgroundUtamaPane = new StackPane(mainPane);
            StackPane.setAlignment(mainPane, javafx.geometry.Pos.CENTER);
            backgroundUtamaPane.setStyle("-fx-background-color: #141F23;");
            Scene scene = new Scene(backgroundUtamaPane, stage.getWidth(), stage.getHeight());
            scene.setFill(Color.valueOf("#141F23"));
            scene.getStylesheets().addAll(
                    "https://fonts.googleapis.com/css2?family=Poppins:wght@500;600;700&display=swap");
    
            ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
                double stageWidth = this.stage.getWidth();
                double stageHeight = this.stage.getHeight();
    
                // Menyesuaikan ukuran dan posisi StackPane
                mainPane.setMaxSize(stageWidth - 200, stageHeight - 100);
                kotakInput.setMaxSize(this.stage.getWidth() - 500, this.stage.getHeight() - 200);
                scrollPane.setMaxSize(stage.getWidth() - 350, stage.getHeight() - 250);
                scrollContetBox.setMaxHeight(10 * scrollPane.getMaxHeight());
                scrollContetBox.setMaxWidth(scrollPane.getMaxWidth() - 20);
    
                welcome.setTranslateY(-stageHeight / 2 + 83); // Menggunakan proporsi dengan tinggi awal 600
                welcome.setTranslateX(-stageWidth / 2 + 210); // Menggunakan proporsi dengan lebar awal 750
    
                tambahTarget.setTranslateY(stageHeight / 2 - 90);
                tambahTarget.setTranslateX(stageWidth / 2 - 150);
    
                kotakInput.setTranslateY(stageHeight / 2 - 250);
                kotakInput.setTranslateX(stageWidth / 2 - 500);
            };

            // Digunakan untuk tempat menaruh item label
            StackPane paneItemLabel = new StackPane();
            paneItemLabel.getChildren().add(itemLabel);
            paneItemLabel.setAlignment(Pos.CENTER_LEFT);
            paneItemLabel.setPadding(new Insets(0, 0, 0, 10));

            // Digunakan untuk tempat menaruh button hapus target
            StackPane paneHapusTarget = new StackPane();
            paneHapusTarget.getChildren().add(hapusTarget);
            paneHapusTarget.setAlignment(Pos.BOTTOM_RIGHT);

            // Digunakan untuk menaruh item label dan button hapus target
            VBox itemBoxContent = new VBox();
            itemBoxContent.getChildren().addAll(paneItemLabel, paneHapusTarget);
            itemBoxContent.setSpacing(10); // Sesuaikan dengan spasi yang diinginkan

            // Digunakan untuk menaruh dan menyatukan itemBoxContent dan progressBox
            itemBox.getChildren().addAll(itemBoxContent, progressBox); // Add HBox to VBox

            // Digunakan untuk menaruh itemBox ke scrollContentBox
            scrollContetBox.getChildren().add(itemBox); // Add VBox to scrollContetBox
        }

        StackPane mainPane = new StackPane();

        Image icon = new Image("file:src/Assets/View/Login_Register/icons8-plus-50.png");
        ImageView iconView = new ImageView(icon);
        iconView.toFront();

        Button tambahTarget = new Button();
        tambahTarget.setTranslateY(-69);
        tambahTarget.setTranslateX(266);
        tambahTarget.setGraphic(iconView);
        tambahTarget.setMinSize(30, 30); // Sesuaikan ukuran sesuai kebutuhan
        tambahTarget.setOnMouseClicked(e -> {
            mainPane.getChildren().add(kotakInput);
            kotakInput.setAlignment(javafx.geometry.Pos.CENTER);

        });

        // Menjalankan fungsi button menutup kotak input
        menutupKotakInput.setOnAction(e -> {
            namaTarget.clear();
            nominalTarget.clear();
            keteranganBarang.clear();
            mainPane.getChildren().remove(kotakInput);

            // mereferesh tampilan
            refreshView();
        });
        // VBox untuk menampung konten atas, tengah, dan bawah
        // VBox kontenVBox = new VBox(kontenAtasPane, kontenTengahPane, kontenBawahPane);
        VBox kontenVBox = new VBox();
        kontenVBox.getChildren().addAll(kontenAtasPane, kontenTengahPane);
        kontenVBox.setSpacing(2);
        kontenVBox.setPadding(new Insets(0, 10, 0, 0));
        kontenVBox.setStyle("-fx-background-color: #141F23;");
        kontenVBox.setMaxHeight(this.stage.getHeight());

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(kontenVBox);
        scrollPane.setFitToHeight(true);
        scrollPane.setMaxHeight(this.stage.getHeight() - 100);
        scrollPane.setMaxWidth(this.stage.getWidth());
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
        // mainPane.setMaxWidth(this.stage.getWidth());
        mainPane.setPadding(new Insets(0, 10, 0, 0));
        mainPane.setAlignment(Pos.CENTER);

        HBox rightBar = new HBox();

        // Mengatur binding fitToHeight dan fitToWidth
        scrollPane.setFitToWidth(true);

        HBox penggabunganMainPanedenganSideBar = new HBox(sideBar, mainPane);
        penggabunganMainPanedenganSideBar.setStyle("-fx-background-color: #0B1214;");
        penggabunganMainPanedenganSideBar.setPadding(new Insets(10, 0, 0, 0));
        HBox fullPane = new HBox(penggabunganMainPanedenganSideBar, rightBar);
        // Set horizontal grow priority for mainPane
        // HBox.setHgrow(mainPaneHBox, Priority.ALWAYS);
        fullPane.setStyle("-fx-background-color: #0B1214");

        Scene scene = new Scene(fullPane, this.stage.getWidth(), this.stage.getHeight());

        this.stage.setScene(scene);
        this.stage.setMaximized(true);
        this.stage.show(); // Tetapkan setelah styling selesai
    }

    private void refreshView(ScrollPane scrollPane, VBox scrollContentBox, StackPane mainPane) {
        scrollContentBox.getChildren().clear();
        
        VBox contentPane = new VBox();
        contentPane.setStyle("-fx-background-radius: 20");
        contentPane.setMaxSize(mainPane.getMaxWidth(), mainPane.getMaxHeight() - 20);
        contentPane.setSpacing(20);

        VBox scrollPaneContent = createScrollPaneContent(scrollPane, mainPane);

        // scrollPaneContent.setStyle("-fx-background-color: #213339;");
        // scrollPane.setContent(scrollPaneContent);
        // scrollPane.setStyle("-fx-background-color: #213339;");

        scrollPaneContent.setSpacing(15);
        scrollPaneContent.setAlignment(Pos.CENTER);
        scrollPaneContent.setStyle("-fx-background-color: #141F23");

        scrollPane.setContent(scrollPaneContent);
    }

    private VBox createScrollPaneContent(ScrollPane scrollPane, StackPane mainPane) {
        VBox scrollPaneContent = new VBox();
        String[] listKategori = TanamUangModel.getKategoriTanamUang(this.tipeTanamUang);

        for (int i = 0; i < listKategori.length; i++) {
            Text namaKategori = createText(listKategori[i], "-fx-font: 16 Poppins;", "#FFFFFF");
            Hyperlink editHyperlink = new Hyperlink();
            editHyperlink.setGraphic(new ImageView(new Image("file:src/Assets/View/Dashboard/Edit.png")));
            Hyperlink deleteHyperlink = new Hyperlink();
            deleteHyperlink.setGraphic(new ImageView(new Image("file:src/Assets/View/Dashboard/Delete.png")));

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

            StackPane paneHapus = new StackPane();
            StackPane backgroundPaneHapus = new StackPane();

            deleteHyperlink.setOnMouseClicked(e -> {
                paneHapus.setMaxSize(mainPane.getWidth() - 400, mainPane.getHeight() - 200);
                paneHapus.setStyle("-fx-background-radius: 20; -fx-background-color: #505e63");

                backgroundPaneHapus.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8); -fx-background-radius: 30px;");

                Text titleHapus = createText("Hapus Kategori", "-fx-font: 24 'Poppins Bold';", "#000000");
                Text titleYakinHapus = createText("Yakin ingin menghapus kategori ", "-fx-font: 18 'Poppins Medium';", "#FFFFFF");
                Text titleNamaKategoriHapus = createText(listKategori[index], "-fx-font: bold 18 Poppins;", "#ff424c");
                Text titleTandaTanya = createText("?", "-fx-font: 18 'Poppins Medium';", "#FFFFFF");

                HBox hboxTitleHapus = new HBox(titleYakinHapus, titleNamaKategoriHapus, titleTandaTanya);

                Button buttonHapus = new Button("Hapus");
                Button buttonHapusBatal = new Button("Batal");

                buttonHapusBatal.setOnAction(f -> {
                    if (mainPane.getParent() != null) {
                        mainPane.getChildren().remove(paneHapus);
                        mainPane.getChildren().remove(backgroundPaneHapus);
                    }
                });

                buttonHapus.setOnAction(f -> {
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

                HBox hboxButtonHapus = new HBox(buttonHapusBatal, buttonHapus);
                hboxButtonHapus.setSpacing(20);
                hboxButtonHapus.setAlignment(Pos.CENTER);

                VBox itemContentHapus = new VBox(titleHapus, hboxTitleHapus, hboxButtonHapus);
                itemContentHapus.setSpacing(20);
                itemContentHapus.setMaxSize(paneHapus.getMaxWidth() - 100, paneHapus.getMaxHeight() - 30);
                VBox.setMargin(hboxTitleHapus, new Insets(0, 0, 20, 0));

                paneHapus.getChildren().add(itemContentHapus);

                if (!mainPane.getChildren().contains(paneHapus)) {
                    mainPane.getChildren().addAll(backgroundPaneHapus, paneHapus);
                }

            });

            StackPane paneEdit = new StackPane(); // agar tidak duplicate
            StackPane backgroundPaneEdit = new StackPane();

            editHyperlink.setOnMouseClicked(e -> {
                
                paneEdit.setMaxSize(mainPane.getWidth() - 200, mainPane.getHeight() - 200);
                paneEdit.setStyle("-fx-background-radius: 20; -fx-background-color: #141F23");

                backgroundPaneEdit.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8); -fx-background-radius: 30px;");
                Hyperlink editBackHyperlink = new Hyperlink();
                editBackHyperlink.setGraphic(new ImageView(new Image("file:src/Assets/View/Dashboard/Back.png")));

                editBackHyperlink.setOnMouseClicked(f -> {
                    refreshView(scrollPane, scrollPaneContent, mainPane);
                    mainPane.getChildren().remove(paneEdit);
                    mainPane.getChildren().remove(backgroundPaneEdit);
                });

                Text titleEdit = createText("Ubah Kategori", "-fx-font: 20 Poppins;", "#FFFFFF");
                Label labelEditNamaKategori = new Label("Nama:");
                labelEditNamaKategori.setStyle("-fx-font: 16 'Poppins Regular'");
                labelEditNamaKategori.setTextFill(Color.valueOf("#FFFFFF"));
                TextField fieldEditNamaKategori = new TextField(listKategori[index]);
                fieldEditNamaKategori.setStyle(
                    "-fx-background-radius: 20px; -fx-background-color: #0D1416; " +
                    "-fx-border-color: red; -fx-border-radius: 20px; " + 
                    "-fx-bord"
                );
                Button editButtonSimpanKategori = new Button("Simpan");
                Button editButtonBatalKategori = new Button("Batal");

                editButtonBatalKategori.setOnMouseClicked(g -> {
                    if (mainPane.getParent() != null) {
                        mainPane.getChildren().remove(paneEdit);
                        mainPane.getChildren().remove(backgroundPaneEdit);
                    }
                });

                String nama_kategori = namaKategori.getText();

                editButtonSimpanKategori.setOnMouseClicked(h -> {
                    String valueFieldEditNamaKategori = fieldEditNamaKategori.getText();

                    if (isKategoriDefault) {
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

                HBox editHBox = new HBox(labelEditNamaKategori, fieldEditNamaKategori);
                editHBox.setSpacing(20);
                // editHBox.setAlignment(Pos.CENTER);

                HBox editTitleHBox = new HBox(editBackHyperlink, titleEdit);
                Region regionEditButton = new Region();

                HBox editButtonHBox = new HBox(regionEditButton, editButtonBatalKategori, editButtonSimpanKategori);
                HBox.setHgrow(regionEditButton, Priority.ALWAYS);
                editButtonHBox.setSpacing(20);
                // editButtonHBox.setAlignment(Pos.CENTER);

                VBox editVBox = new VBox(editTitleHBox, editHBox, editButtonHBox);
                editVBox.setMaxSize(paneEdit.getMaxWidth() - 20, paneEdit.getHeight() - 20);
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
        System.out.println("Radio button: " + (this.radioButtonCash.isSelected() || this.radioButtonTransfer.isSelected()));

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
        // logoImageView.setTranslateY(-stage.getHeight() / 2 + 270);
        // logoImageView.setTranslateX(-stage.getWidth() / 2 + 485);

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
        Group aktifGroup = new Group(region, tanamUangHyperlink);
        aktifGroup.setTranslateX(10);
        // Membuat masing - masing Vbox dan menambahkan Hyperlink ke dalamnya
        VBox homeVBox = new VBox(homeHyperlink);
        homeVBox.setAlignment(Pos.CENTER);
        VBox tanamUangVBox = new VBox(aktifGroup);
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