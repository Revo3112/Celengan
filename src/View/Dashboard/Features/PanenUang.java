package View.Dashboard.Features;

import Controller.SceneController;
import Model.BatasKritis;
import Model.HapusTarget;
import Model.LoginModel;
import Model.PantauPemasukanPengeluaran;
import Model.PieChartData;
import Model.RefreshViewDashboard;
import Model.TambahTarget;
import Model.TampilkanSemuaTarget;
import Utils.AlertHelper;
import View.Dashboard.DashboardPage.CustomItem.CustomItemConverter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.util.StringConverter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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
public class PanenUang {

    private Stage stage; // Property stage sebaiknya dideklarasikan sebagai final
    private String username;
    // private TextField namaTarget;
    // private TextField nominalTarget;
    // private TextField keteranganBarang;
    private static TampilkanSemuaTarget model = new TampilkanSemuaTarget();
    private static List<String> namaTargetList = model.getNamaTargetList();
    private static List<Double> nominalTargetList = model.getNominalTargetList();
    private static List<String> keteranganBarangList = model.getKeteranganBarangList();
    private SceneController sceneController; // Tambahkan property SceneController\
    private double saldo;
    private int userId;
    private Tooltip tooltip = new Tooltip();
    private RefreshViewDashboard refreshViewDashboard;
    private List<Double> nominalBarangList;
    private List<String> tipeBarangList;
    private List<String> tanggalBarangList;
    public StackPane root = new StackPane();
    private VBox scrollContentBox;
    StackPane mainPane = new StackPane();
    // private ScrollPane scrollPanes;

    public PanenUang(Stage stage) {
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
        ImageLinkPane imageLinkPane = new ImageLinkPane(this); // Mengirim referensi DashboardPage ke ImageLinkPane
        VBox sideBar = imageLinkPane.createImageLinkVBox(this.stage, sceneController);
        sideBar.setAlignment(Pos.CENTER_RIGHT);
        VBox.setVgrow(sideBar, Priority.ALWAYS);

        // ------------------------------------------------------------------------------------------------------------//
        // KONTEN ATAS
        // ------------------------------------------------------------------------------------------------------------//

        Text judulAtas = createText("Lihat dan Panen\n", "-fx-font: 40 'Poppins Regular'; -fx-fill: #FFFFFF;", 0, 0);
        Text judulBawah = createText("Semua Targetmu!", "-fx-font: 40 'Poppins SemiBold'; -fx-fill: #FFFFFF;", 0, 20);

        // StackPane untuk menampung teks
        StackPane textPane = new StackPane(judulAtas, judulBawah);
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

        // ------------------------------------------------------------------------------
        // \\
        // KONTEN TENGAH
        // ------------------------------------------------------------------------------
        // \\

        // Membuat title
        Text titleKontenTengah = createText("Target Keuanganmu", "-fx-font: 30 'Poppins Medium'; -fx-fill: #FFFFFF;", 0,
                0);

        // Menambah target
        Hyperlink tambahTargetHyperlink = new Hyperlink();
        tambahTargetHyperlink.setGraphic(new ImageView(new Image("file:src/Assets/View/Dashboard/Tambah.png")));

        HBox kontenTengahAtas = new HBox(titleKontenTengah, tambahTargetHyperlink);
        // kontenTengahAtas.setPadding(new Insets(0, 0, 0, 20)); // Memberi padding di
        // kiri
        kontenTengahAtas.setSpacing(20);

        // ----------- Membuat konten di dalam kotak input ----------- \\

        // Bagian atas kotak input.
        Text titleKotakInput = new Text("Tambah Target");
        titleKotakInput.setStyle("-fx-font: 24 'Poppins Bold'; -fx-fill: #FFFFFF");
        // titleKotakInput.setFill(Color.valueOf("#FFFFFF"));
        Hyperlink backHyperlink = new Hyperlink();
        backHyperlink.setGraphic(new ImageView(new Image("file:src/Assets/View/Dashboard/Back.png")));
        HBox hboxTitleKotakInput = new HBox(backHyperlink, titleKotakInput);
        hboxTitleKotakInput.setSpacing(20);

        // Nama Target
        Label labelNamaTarget = new Label("Nama Target:");
        labelNamaTarget.setStyle("-fx-font: 16px 'Poppins SemiBold'; -fx-text-fill: #FFFFFF;");
        TextField fieldNamaTarget = new TextField();
        // Memecah hbox field supaya sejajar
        HBox hboxLabelNamaTarget = new HBox(labelNamaTarget);
        HBox hboxNamaTarget = new HBox(hboxLabelNamaTarget, fieldNamaTarget);
        HBox.setHgrow(hboxLabelNamaTarget, Priority.ALWAYS);
        hboxNamaTarget.setSpacing(20);

        // Nominal Target
        Label labelNominalTarget = new Label("Nominal:");
        labelNominalTarget.setStyle("-fx-font: 16px 'Poppins SemiBold'; -fx-text-fill: #FFFFFF;");
        TextField fieldNominalTarget = new TextField();
        // Memecah hbox field supaya sejajar
        HBox hboxLabelNominalTarget = new HBox(labelNominalTarget);
        HBox hboxNominalTarget = new HBox(hboxLabelNominalTarget, fieldNominalTarget);
        HBox.setHgrow(hboxLabelNominalTarget, Priority.ALWAYS);
        hboxNominalTarget.setSpacing(20);

        // Keterangan Target
        Label labelKeteranganTarget = new Label("Keterangan:");
        labelKeteranganTarget.setStyle("-fx-font: 16px 'Poppins SemiBold'; -fx-text-fill: #FFFFFF;");
        TextField fieldKeteranganTarget = new TextField();
        // Memecah hbox field supaya sejajar
        HBox hboxLabelKeteranganTarget = new HBox(labelKeteranganTarget);
        HBox hboxKeteranganTarget = new HBox(hboxLabelKeteranganTarget, fieldKeteranganTarget);
        HBox.setHgrow(hboxLabelKeteranganTarget, Priority.ALWAYS);
        hboxKeteranganTarget.setSpacing(20);

        Button buttonBatalTarget = new Button("Batal"); // Tombol untuk membatalkan penambahan target
        Hyperlink tambahHyperlink = new Hyperlink();
        ImageView imageTambah = new ImageView(new Image("file:src/Assets/View/Dashboard/TambahPanen.png"));
        imageTambah.setFitWidth(100);
        imageTambah.setFitHeight(80);
        imageTambah.setPreserveRatio(true);
        tambahHyperlink.setGraphic(imageTambah);

        // Button buttonTambahTarget = new Button("Tambah"); // Tombol untuk menambahkan
        // target kedalam databases
        tambahHyperlink.setOnMouseClicked(e -> {
            LoginModel loginModel = new LoginModel();
            setUserTarget(loginModel.getUserId(), fieldNamaTarget.getText(),
                    Double.parseDouble(fieldNominalTarget.getText().replace(",", "")),
                    fieldKeteranganTarget.getText());
            fieldNamaTarget.clear();
            fieldNominalTarget.clear();
            fieldKeteranganTarget.clear();
        });

        // VBox untuk konten form kotak input
        VBox kontenFormKotakInput = new VBox(hboxNamaTarget, hboxNominalTarget, hboxKeteranganTarget);

        // VBox untuk button kotak input
        HBox buttonKotakInput = new HBox(tambahHyperlink);
        buttonKotakInput.setAlignment(Pos.CENTER);
        buttonKotakInput.setSpacing(20);

        Image iconSilang = new Image("file:src/Assets/View/Login_Register/icons8-cross-mark-48.png");
        ImageView iconSilangView = new ImageView(iconSilang);

        // Supaya ada efek koma ketika user memasukan input kedalam field nominal target
        fieldNominalTarget.textProperty().addListener(new ChangeListener<String>() {
            private String codeFormat = ",##0";
            private int codeLen = 4;

            @Override
            // ObservableValue = StringProperty (Represent an object that has changed its
            // state)
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Hapus listener sementara

                fieldNominalTarget.textProperty().removeListener(this);
                if (newValue.replace(",", "").matches("\\d*")) { // Check inputan angka atau tidak
                    if (newValue.length() > oldValue.length()) {
                        if (newValue.length() > 13) {
                            fieldNominalTarget.setText(oldValue);
                        } else {
                            updateCodeFormat(newValue);
                            formatAndSet(newValue, this.codeFormat);
                        }
                    }
                } else {
                    fieldNominalTarget.setText(oldValue);
                }

                // Tambahkan kembali listener setelah pembaruan teks
                fieldNominalTarget.textProperty().addListener(this);
            }

            private void formatAndSet(String text, String format) {
                DecimalFormat decimalFormat = new DecimalFormat(format);
                double value = Double.parseDouble(text.replace(",", ""));
                System.out.println(value);
                fieldNominalTarget.setText("");
                fieldNominalTarget.setText(decimalFormat.format(value));
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

        // Membuat VBox untuk menaruh item-item yang ada nantinya dapat di scroll
        scrollContentBox = new VBox();
        scrollContentBox.setSpacing(20);

        // Logika untuk membuat vbox vbox yang nantinya dapa di scroll
        for (int i = 0; i < mengambilBanyakDataDiTarget(); i++) {
            String namaTarget = namaTargetList.get(i);
            double nominalTarget = nominalTargetList.get(i);
            String keteranganBarang = keteranganBarangList.get(i);

            // ------------------------------------------------------------------------------------------------------------//
            // KONTEN ITEM KIRI
            // ------------------------------------------------------------------------------------------------------------//

            // Membuat nama target
            Text textNamaTarget = new Text(namaTarget);
            textNamaTarget.setStyle("-fx-font: 20 'Poppins SemiBold'; -fx-fill: #FFFFFF");

            // Membuat keterangan target

            // Membuat efek tooltip
            String tampilanKeterangan = keteranganBarang.length() > 25
                    ? keteranganBarang.substring(0, 25) + "..."
                    : keteranganBarang;

            Label labelKeterangan = new Label(tampilanKeterangan);
            labelKeterangan.setStyle("-fx-font: 14 'Poppins SemiBold'; -fx-text-fill: #FFFFFF"); // Set the style

            // Tambahkan tooltip yang menampilkan saldo lengkap
            Tooltip tooltipKeterangan = new Tooltip(keteranganBarang);
            Tooltip.install(labelKeterangan, tooltipKeterangan);

            // Layout konten item kiri
            VBox kontenItemKiri = new VBox(textNamaTarget, labelKeterangan);
            kontenItemKiri.setSpacing(10);
            kontenItemKiri.setPadding(new Insets(0, 0, 0, 20));

            // ------------------------------------------------------------------------------------------------------------//
            // KONTEN ITEM TENGAH
            // ------------------------------------------------------------------------------------------------------------//

            // Membulatkan tampilan uang agar tidak muncul E
            long roundedValue = Math.round(nominalTarget);

            // Menampilkan progress text
            Text progressText = new Text(
                    formatDuit(mendapatkanSaldoUntukMembeliBarang()) + " / " + formatDuit(roundedValue));
            progressText.setStyle("-fx-font: 18 'Poppins SemiBold'; -fx-fill: #798F97");

            // Untuk membuat progres bar
            ProgressBar progressBar = new ProgressBar();
            progressBar.setProgress(kemajuanProgres(namaTarget));
            // progressBar.setMinWidth(400);
            progressBar.getStylesheets()
                    .add(getClass().getResource("/Utils/progressBarPanenUang.css").toExternalForm());

            // Layout konten item tengah
            VBox kontenItemTengah = new VBox(progressText, progressBar);
            kontenItemTengah.setSpacing(10);
            kontenItemTengah.setAlignment(Pos.CENTER);

            // ------------------------------------------------------------------------------------------------------------//
            // KONTEN ITEM KANAN
            // ------------------------------------------------------------------------------------------------------------//

            // Icon untuk menghapus item
            Hyperlink deleteHyperlink = new Hyperlink();
            deleteHyperlink.setGraphic(new ImageView(new Image("file:src/Assets/View/Dashboard/Delete.png")));
            deleteHyperlink.setOnAction(e -> {
                HapusTarget hapusTargetModel = new HapusTarget(namaTarget);
                if (hapusTargetModel.start()) {
                    AlertHelper.info("Target berhasil dihapus!");
                    refreshView();
                } else {
                    AlertHelper.alert("Target gagal dihapus!");
                }
                refreshView();
            });

            // Layout untuk konten item kanan
            VBox kontenItemKanan = new VBox(deleteHyperlink);

            // ------------------------------------------------------------------------------------------------------------//
            // PENGGABUNGAN KONTEN
            // ------------------------------------------------------------------------------------------------------------//

            StackPane mainPaneItemBox = new StackPane();
            mainPaneItemBox.setStyle("-fx-background-color: #ff002b; -fx-background-radius: 20px"); // Red

            HBox kontenItemBox = new HBox(kontenItemKiri, kontenItemTengah, kontenItemKanan);
            HBox.setHgrow(kontenItemKiri, Priority.ALWAYS);
            kontenItemBox.setStyle("-fx-background-color: #213339; -fx-background-radius: 20px;"); // Blue
            kontenItemBox.setPadding(new Insets(10, 0, 10, 0));
            kontenItemBox.setSpacing(20);

            // Digunakan untuk menaruh itemBox ke scrollContentBox
            scrollContentBox.getChildren().add(kontenItemBox); // Add VBox to scrollContentBox
        }

        // Membuat layout VBox untuk konten tengah bagian tengah
        VBox kontenTengahTengah = new VBox();

        Image icon = new Image("file:src/Assets/View/Login_Register/icons8-plus-50.png");
        ImageView iconView = new ImageView(icon);
        iconView.toFront();

        // ------------------------------------------------------------------------------------------------------------//
        // PENGGABUNGAN KONTEN ATAS dan TENGAH
        // ------------------------------------------------------------------------------------------------------------//

        // Membuat konten bagian tengah untuk main pane
        VBox kontenTengahPane = new VBox(kontenTengahAtas, kontenTengahTengah);
        kontenTengahPane.setPadding(new Insets(0, 0, 0, 30));
        kontenTengahPane.setSpacing(10);

        // VBox untuk menampung konten atas, tengah, dan bawah
        VBox kontenVBox = new VBox(kontenAtasPane, kontenTengahPane);
        kontenVBox.setSpacing(2);
        kontenVBox.setPadding(new Insets(0, 10, 0, 0));
        kontenVBox.setStyle("-fx-background-color: #141F23;");
        kontenVBox.setMaxHeight(this.stage.getHeight());

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(kontenVBox);
        scrollPane.setFitToHeight(true);
        scrollPane.setMaxHeight(this.stage.getHeight() - 100);
        scrollPane.setMaxWidth(this.stage.getWidth() - 100);
        scrollPane.getStylesheets().add(getClass().getResource("/Utils/ScrollBar.css").toExternalForm());

        kontenTengahTengah.getChildren().addAll(scrollContentBox);
        kontenTengahTengah.setMaxSize(this.stage.getWidth() - 200, this.stage.getHeight() - 100);
        kontenTengahTengah.setStyle("-fx-background-color: #141F23; -fx-background-radius: 20;");
        kontenTengahTengah.setPadding(new Insets(10, 10, 10, 10));
        kontenTengahTengah.setAlignment(javafx.geometry.Pos.CENTER);

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
        mainPane.setMaxWidth(this.stage.getWidth() + 400);
        mainPane.setPadding(new Insets(0, 10, 0, 0));
        mainPane.setAlignment(Pos.CENTER);

        // Vbox untuk konten kotak input
        VBox kontenKotakInput = new VBox(hboxTitleKotakInput, kontenFormKotakInput, buttonKotakInput);

        // Popup untuk kotak input
        StackPane kotakInput = new StackPane();
        kotakInput.getChildren().addAll(kontenKotakInput);
        kotakInput.setMaxSize(mainPane.getMaxWidth() - 1250, mainPane.getMaxHeight() - 400);
        kotakInput.setStyle("-fx-background-color: #263940; -fx-background-radius: 20;");

        // Mengatur ukuran konten kotak input
        kontenKotakInput.setMaxSize(kotakInput.getMaxWidth() - 50, kotakInput.getMaxHeight() - 20);
        kontenKotakInput.setSpacing(40);
        kontenKotakInput.setAlignment(Pos.CENTER);

        // Mengatur ukuran dari form kotak input dan button kotak input
        kontenFormKotakInput.setMaxSize(kotakInput.getMaxWidth() - 225, kotakInput.getMaxHeight());
        kontenFormKotakInput.setSpacing(20);

        // Menjalankan fungsi tambah target
        tambahTargetHyperlink.setOnMouseClicked(e -> {
            mainPane.getChildren().add(kotakInput);
        });

        // Menjalankan fungsi batal di kotak input
        buttonBatalTarget.setOnMouseClicked(e -> {
            fieldNamaTarget.clear();
            fieldNominalTarget.clear();
            fieldKeteranganTarget.clear();
            mainPane.getChildren().remove(kotakInput);

            // merefresh tampilan
            refreshView();
        });

        // Menjalankan fungsi back di konten input target
        backHyperlink.setOnMouseClicked(e -> {
            fieldNamaTarget.clear();
            fieldNominalTarget.clear();
            fieldKeteranganTarget.clear();
            mainPane.getChildren().remove(kotakInput);

            // merefresh tampilan
            refreshView();
        });

        RightBar rightBar = new RightBar(this.saldo, this.userId);
        HBox Rightbar = rightBar.createRightBar(this.stage, sceneController);

        // Mengatur binding fitToHeight dan fitToWidth
        scrollPane.setFitToWidth(true);

        HBox penggabunganMainPanedenganSideBar = new HBox(sideBar, mainPane);
        penggabunganMainPanedenganSideBar.setStyle("-fx-background-color: #0B1214;");
        penggabunganMainPanedenganSideBar.setPadding(new Insets(10, 0, 0, 0));
        HBox fullPane = new HBox(penggabunganMainPanedenganSideBar, Rightbar);
        fullPane.setStyle("-fx-background-color: #0B1214");

        root.getChildren().add(fullPane);

        Scene scene = new Scene(root, this.stage.getWidth(), this.stage.getHeight());

        this.stage.setScene(scene);
        this.stage.show(); // Tetapkan setelah styling selesai
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

    // Fungsi untuk mengambil data yang paling pertama di database target
    private String getTarget() {
        TampilkanSemuaTarget target1 = new TampilkanSemuaTarget();
        return target1.ambilDataNamaTargetPertama(this.userId);
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

    public StackPane getRoot() {
        return this.root;
    }

    // Digunakan untuk menambahkan target kedalam databases
    private boolean setUserTarget(int userId, String namaTarget, double nominalTarget, String keteranganBarang) {
        TambahTarget tambahTarget = new TambahTarget(userId, namaTarget, nominalTarget, keteranganBarang);
        if (tambahTarget.start()) {
            AlertHelper.info("Penyetelan " + namaTarget + " berhasil!" + namaTarget + " berhasil ditambahkan!");
            return true;
        }
        return false;
    }

    // Digunakan untuk mengambilbanyaknama target
    private int mengambilBanyakDataDiTarget() {
        TampilkanSemuaTarget tampilkanSemuaTarget = new TampilkanSemuaTarget();
        return tampilkanSemuaTarget.mendapatkanBanyakDataDiTarget();
    }

    // Digunakan untuk mengembalikan kemajuan progress bar
    private double kemajuanProgres(String namaTarget) {
        TampilkanSemuaTarget tampilkanSemuaTarget = new TampilkanSemuaTarget();
        return tampilkanSemuaTarget.mendapatkanHargaTarget(namaTarget);
    }

    // Digunakan untuk mengembalikan saldo untuk membeli barang
    private long mendapatkanSaldoUntukMembeliBarang() {
        TampilkanSemuaTarget tampilkanSemuaTarget = new TampilkanSemuaTarget();
        return tampilkanSemuaTarget.ambilSaldodanBatasKritis();
    }

    // Digunakan untuk mereset ulang tampilan baik itu ketika menambah barang maupun
    // mengahapus barang (target)
    private void refreshView() {
        scrollContentBox.getChildren().clear(); // Menghapus semua item yang ada
        PanenUang.model = new TampilkanSemuaTarget();
        PanenUang.namaTargetList = model.getNamaTargetList();
        PanenUang.nominalTargetList = model.getNominalTargetList();
        PanenUang.keteranganBarangList = model.getKeteranganBarangList();
        // Menambahkan ulang item-item dari data baru
        for (int i = 0; i < mengambilBanyakDataDiTarget(); i++) {
            String namaTarget = namaTargetList.get(i);
            double nominalTarget = nominalTargetList.get(i);
            String keteranganBarang = keteranganBarangList.get(i);

            // ------------------------------------------------------------------------------------------------------------//
            // KONTEN ITEM KIRI
            // ------------------------------------------------------------------------------------------------------------//

            // Membuat nama target
            Text textNamaTarget = new Text(namaTarget);
            textNamaTarget.setStyle("-fx-font: 20 'Poppins SemiBold'; -fx-fill: #FFFFFF");

            // Membuat keterangan target

            // Membuat efek tooltip
            String tampilanKeterangan = keteranganBarang.length() > 25
                    ? keteranganBarang.substring(0, 25) + "..."
                    : keteranganBarang;

            Label labelKeterangan = new Label(tampilanKeterangan);
            labelKeterangan.setStyle("-fx-font: 14 'Poppins SemiBold'; -fx-text-fill: #FFFFFF"); // Set the style

            // Tambahkan tooltip yang menampilkan saldo lengkap
            Tooltip tooltipKeterangan = new Tooltip(keteranganBarang);
            Tooltip.install(labelKeterangan, tooltipKeterangan);

            // Layout konten item kiri
            VBox kontenItemKiri = new VBox(textNamaTarget, labelKeterangan);
            kontenItemKiri.setSpacing(10);
            kontenItemKiri.setPadding(new Insets(0, 0, 0, 20));

            // ------------------------------------------------------------------------------------------------------------//
            // KONTEN ITEM TENGAH
            // ------------------------------------------------------------------------------------------------------------//

            // Membulatkan tampilan uang agar tidak muncul E
            long roundedValue = Math.round(nominalTarget);

            // Menampilkan progress text
            Text progressText = new Text(
                    formatDuit(mendapatkanSaldoUntukMembeliBarang()) + " / " + formatDuit(roundedValue));
            progressText.setStyle("-fx-font: 18 'Poppins SemiBold'; -fx-fill: #798F97");

            // Untuk membuat progres bar
            ProgressBar progressBar = new ProgressBar();
            progressBar.setProgress(kemajuanProgres(namaTarget));
            // progressBar.setMinWidth(400);
            progressBar.getStylesheets()
                    .add(getClass().getResource("/Utils/progressBarPanenUang.css").toExternalForm());

            // Layout konten item tengah
            VBox kontenItemTengah = new VBox(progressText, progressBar);
            kontenItemTengah.setSpacing(10);
            kontenItemTengah.setAlignment(Pos.CENTER);

            // ------------------------------------------------------------------------------------------------------------//
            // KONTEN ITEM KANAN
            // ------------------------------------------------------------------------------------------------------------//

            // Icon untuk menghapus item
            Hyperlink deleteHyperlink = new Hyperlink();
            deleteHyperlink.setGraphic(new ImageView(new Image("file:src/Assets/View/Dashboard/Delete.png")));
            deleteHyperlink.setOnAction(e -> {
                HapusTarget hapusTargetModel = new HapusTarget(namaTarget);
                if (hapusTargetModel.start()) {
                    AlertHelper.info("Target berhasil dihapus!");
                    refreshView();
                } else {
                    AlertHelper.alert("Target gagal dihapus!");
                }
                refreshView();
            });

            // Layout untuk konten item kanan
            VBox kontenItemKanan = new VBox(deleteHyperlink);

            // ------------------------------------------------------------------------------------------------------------//
            // PENGGABUNGAN KONTEN
            // ------------------------------------------------------------------------------------------------------------//

            StackPane mainPaneItemBox = new StackPane();
            mainPaneItemBox.setStyle("-fx-background-color: #ff002b; -fx-background-radius: 20px"); // Red

            HBox kontenItemBox = new HBox(kontenItemKiri, kontenItemTengah, kontenItemKanan);
            HBox.setHgrow(kontenItemKiri, Priority.ALWAYS);
            kontenItemBox.setStyle("-fx-background-color: #213339; -fx-background-radius: 20px;"); // Blue
            kontenItemBox.setPadding(new Insets(10, 0, 10, 0));
            kontenItemBox.setSpacing(20);

            // Digunakan untuk menaruh itemBox ke scrollContentBox
            scrollContentBox.getChildren().add(kontenItemBox); // Add VBox to scrollContentBox
        }

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

            HBox gambarKondisidanNominal = new HBox(nominalText, kondisi);
            gambarKondisidanNominal.setSpacing(10);
            VBox nominalStackPane = new VBox(gambarKondisidanNominal);
            nominalStackPane.setAlignment(Pos.CENTER);

            Text tanggalText = createText(formatTanggal(tanggal), "-fx-font: 15 'Poppins Bold'; -fx-fill: #798F97;",
                    0, 0);

            VBox tanggalTextPane = new VBox(tanggalText);
            tanggalTextPane.setAlignment(Pos.CENTER_RIGHT);

            HBox kontenHistoriKeuanganBarang = new HBox();
            kontenHistoriKeuanganBarang.getChildren().addAll(keteranganStackPane, nominalStackPane,
                    gambarKondisidanNominal,
                    tanggalTextPane);

            kontenHistoriKeuanganBarang.setSpacing(80);
            kontenHistoriKeuanganBarang.setPadding(new Insets(10, 0, 10, 25));
            kontenHistoriKeuanganBarang.setAlignment(Pos.CENTER);
            kontenHistoriKeuanganBarang.setStyle("-fx-background-color: #213339; -fx-background-radius: 30px");

            kontenHistoriKeuanganBarangfull.getChildren().add(kontenHistoriKeuanganBarang);
            keteranganStackPane.setMaxWidth(200);
            nominalStackPane.setMaxWidth(200);
            tanggalTextPane.setMaxWidth(200);
        }
        return kontenHistoriKeuanganBarangfull;
    }

    public void popUpUntukModeUser() {
        RightBarPantauUang rightBar = new RightBarPantauUang(this.saldo, this.userId);
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

        StackPane Circle1 = new StackPane(backgroundProfileCircle1, profileCircle1);
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

        StackPane Circle2 = new StackPane(backgroundProfileCircle2, profileCircle2);
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

        StackPane Circle3 = new StackPane(backgroundProfileCircle3, profileCircle3);
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
        PanenUang PanenUangPage;

        public ImageLinkPane(PanenUang PanenUangPage) {
            this.PanenUangPage = PanenUangPage;
        }

        public VBox createImageLinkVBox(Stage stage, SceneController sceneController) {
            // Gunakan ImageView untuk semua pilihan di Sidebar
            ImageView logoImageView = new ImageView(new Image("file:src/Assets/View/Dashboard/Logo.png"));
            logoImageView.setFitWidth(220);
            logoImageView.setFitHeight(50);
            logoImageView.setPreserveRatio(true);

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
            logOutHyperlink.setOnMouseClicked(e -> sceneController.switchToLogin());
            modeUserHyperlink.setOnMouseClicked(e -> PanenUangPage.popUpUntukModeUser());

            StackPane aktifGroup = new StackPane(panenUangHyperlink);
            aktifGroup.setAlignment(Pos.CENTER);
            aktifGroup.setStyle("-fx-background-radius: 30 0 0 30; -fx-background-color: #141F23;");
            aktifGroup.setMinSize(220, 85);
            aktifGroup.setMaxSize(220, 85);

            // Membuat masing - masing Vbox dan menambahkan Hyperlink ke dalamnya
            StackPane image = new StackPane(logoImageView);
            image.setAlignment(Pos.TOP_CENTER);
            VBox homeVBox = new VBox(homeHyperlink);
            homeVBox.setAlignment(Pos.CENTER);
            VBox tanamUangVBox = new VBox(tanamUangHyperlink);
            tanamUangVBox.setAlignment(Pos.CENTER);
            VBox pantauUangVBox = new VBox(pantauUangHyperlink);
            pantauUangVBox.setAlignment(Pos.CENTER);
            VBox panenUangVBox = new VBox(aktifGroup);
            panenUangVBox.setAlignment(Pos.CENTER);
            VBox modeUserVBox = new VBox(modeUserHyperlink);
            modeUserVBox.setAlignment(Pos.CENTER);
            VBox logOutVBox = new VBox(logOutHyperlink);
            logOutVBox.setAlignment(Pos.CENTER);

            // Membuat VBox dan menambahkan Hyperlink ke dalamnya
            VBox kontenSide = new VBox(image, homeVBox, tanamUangVBox, pantauUangVBox, panenUangVBox,
                    modeUserVBox,
                    logOutVBox);
            kontenSide.setSpacing(50);
            kontenSide.setAlignment(Pos.CENTER_LEFT);
            kontenSide.setPadding(new Insets(20, 0, 0, 20));

            return kontenSide;

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
}