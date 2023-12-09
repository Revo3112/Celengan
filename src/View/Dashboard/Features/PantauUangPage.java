package View.Dashboard.Features;

import Controller.SceneController;
import Model.LoginModel;
import Model.PantauPemasukanPengeluaran;
import Model.PieChartData;
import Model.TampilkanSemuaTarget;
import View.Dashboard.Features.PantauUangPage.CustomItemPantau.CustomItemPantauConverter;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import javafx.stage.Stage;
import javafx.util.Callback;

// class DashboardPage digunakan untuk menampilkan halaman dashboard
public class PantauUangPage {

    private Stage stage; // Property stage sebaiknya dideklarasikan sebagai final
    private String username;
    private SceneController sceneController; // Tambahkan property SceneController\
    private double saldo;
    private int userId;
    private Tooltip tooltip = new Tooltip();
    private PantauPemasukanPengeluaran model;
    private List<String> keteranganBarangList;
    private List<Double> nominalBarangList;
    private List<String> tipeBarangList;
    private List<String> tanggalBarangList;

    public PantauUangPage(Stage stage) {
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
        VBox sideBar = ImageLinkPanePantau.createImageLinkVBox(this.stage, sceneController);
        // sideBar.setTranslateX(-stage.getWidth() / 2 + 40);
        sideBar.setAlignment(Pos.CENTER_RIGHT);
        VBox.setVgrow(sideBar, Priority.ALWAYS);
        // Membuat teks welcome
        Text welcome = createText("Selamat Datang di Pantau Uang,\n", "-fx-font: 40 'Poppins Regular'; -fx-fill: #FFFFFF;", 0, 0);
        Text name = createText(this.username, "-fx-font: 40 'Poppins SemiBold'; -fx-fill: #FFFFFF;", 0, 10);

        // StackPane untuk menampung teks
        StackPane textPane = new StackPane(welcome, name);
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

        // Konten Tengah

        // Membuat pie chart
        try {

            // Membuat bagian bagian konten tangah bawah

            // Membuat konten tengah bawah untuk konten tengah
            // Membuat konten bagian tengah untuk main pane
            VBox kontenTengahPane = new VBox(kontenTengahAtas);
            kontenTengahPane.setPadding(new Insets(0, 0, 0, 30));
            kontenTengahPane.setSpacing(10);

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

            // Membuat combo box
            ComboBox<CustomItemPantau> comboBox = new ComboBox<>();
            comboBox.getItems().addAll(
                    new CustomItemPantau("Pengeluaran", Color.valueOf("#FB5050")),
                    new CustomItemPantau("Pemasukan", Color.valueOf("#68FB50")));

            comboBox.setConverter(new CustomItemPantauConverter());

            comboBox.setCellFactory(new Callback<>() {
                @Override
                public ListCell<CustomItemPantau> call(javafx.scene.control.ListView<CustomItemPantau> param) {
                    return new ListCell<>() {
                        private final HBox graphic = new HBox();

                        {
                            graphic.setSpacing(10);
                        }

                        @Override
                        protected void updateItem(CustomItemPantau item, boolean empty) {
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
                CustomItemPantau selectedItem = comboBox.getSelectionModel().getSelectedItem();
                System.out.println("Selected Option: " + selectedItem.getText());

                // Clear existing stylesheets
                comboBox.getStylesheets().clear();

                // Tambahkan gaya baru berdasarkan kategori yang dipilih
                if ("Pemasukan".equals(selectedItem.getText())) {
                    comboBox.getStylesheets().add(getClass().getResource("/Utils/ComboBox.css").toExternalForm());
                } else {
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

            VBox kontenHistoriKeuangan = new VBox();
            kontenHistoriKeuangan.setSpacing(10);
            kontenHistoriKeuangan.setSpacing(10);

            for (int i = 0; i < getTotalBarangyangDIbeli(); i++) {
                System.out.println(keteranganBarangList.get(i));
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
            scrollPane.setMaxHeight(this.stage.getHeight() - 100);
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
            mainPane.setMaxHeight(this.stage.getHeight() - 20);
            mainPane.setMaxWidth(this.stage.getWidth() + 400);
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

    // CustomItem class with Color property
    public static class CustomItemPantau {
        private final String text;
        private final Color color;

        public CustomItemPantau(String text, Color color) {
            this.text = text;
            this.color = color;
        }

        public String getText() {
            return text;
        }

        public Color getColor() {
            return color;
        }

        public static class CustomItemPantauConverter extends StringConverter<CustomItemPantau> {

            @Override
            public String toString(CustomItemPantau object) {
                return (object != null) ? object.getText() : null;
            }

            @Override
            public CustomItemPantau fromString(String string) {
                // Tidak perlu mengimplementasikan ini kecuali jika Anda memerlukannya
                return null;
            }
        }
    }
}

class ImageLinkPanePantau {
    public static VBox createImageLinkVBox(Stage stage, SceneController sceneController) {
        // Gunakan ImageView untuk semua pilihan di Sidebar
        ImageView logoImageView = new ImageView(new Image("file:src/Assets/View/Dashboard/Logo.png"));
        logoImageView.setFitWidth(200);
        logoImageView.setFitHeight(50);
        // logoImageView.setPreserveRatio(true);
        // logoImageView.setTranslateX(-10);

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
        // homeHyperlink.setTranslateX(10);
        Group aktifGroup = new Group(region, pantauUangHyperlink);
        aktifGroup.setTranslateX(10);

        // Membuat masing - masing Vbox dan menambahkan Hyperlink ke dalamnya
        VBox homeVBox = new VBox(homeHyperlink);
        homeVBox.setAlignment(Pos.CENTER);
        VBox tanamUangVBox = new VBox(tanamUangHyperlink);
        tanamUangVBox.setAlignment(Pos.CENTER);
        VBox pantauUangVBox = new VBox(aktifGroup);
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