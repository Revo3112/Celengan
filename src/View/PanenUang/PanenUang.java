package View.PanenUang;

import java.util.List;
import Controller.SceneController;
import Model.HapusTarget;
import Model.LoginModel;
import Model.TambahTarget;
import Model.TampilkanSemuaTarget;
import Utils.AlertHelper;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PanenUang {
    private Stage stage;
    private int userId;
    private TextField namaTarget;
    private TextField nominalTarget;
    private TextField keteranganBarang;
    private static TampilkanSemuaTarget model = new TampilkanSemuaTarget();
    private static List<String> namaTargetList = model.getNamaTargetList();
    private static List<Double> nominalTargetList = model.getNominalTargetList();
    private static List<String> keteranganBarangList = model.getKeteranganBarangList();
    private VBox scrollContetBox;
    private ScrollPane scrollPane;

    public PanenUang(Stage stage) {
        this.stage = stage;
    }

    public void start() {
        Text welcome = createText("Panen Uang", "-fx-font: 30 'Poppins-Regular';", "#FFFFFF", -290,
                -167);
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

        Button menutupKotakInput = new Button();
        menutupKotakInput.setTranslateY(-104);
        menutupKotakInput.setTranslateX(208);
        menutupKotakInput.setGraphic(iconSilangView);
        menutupKotakInput.setMinSize(20, 20);
        menutupKotakInput.toFront();

        Button menambahkanTarget = new Button("Tambah Target");
        menambahkanTarget.setTranslateY(84);
        menambahkanTarget.setTranslateX(201);
        menambahkanTarget.setMinSize(20, 20);
        menambahkanTarget.toFront();
        menambahkanTarget.setOnAction(e -> {
            LoginModel loginModel = new LoginModel();
            setUserTarget(loginModel.getUserId(), namaTarget.getText(), Double.parseDouble(nominalTarget.getText()),
                    keteranganBarang.getText());
            namaTarget.clear();
            nominalTarget.clear();
            keteranganBarang.clear();
        });

        LoginModel loginModel = new LoginModel();

        StackPane kotakInput = new StackPane();
        kotakInput.getChildren().addAll(namaTarget, nominalTarget, keteranganBarang, labelTarget, labelnominal,
                labelBarang, menutupKotakInput, menambahkanTarget);
        kotakInput.setMaxSize(this.stage.getWidth() - 400, this.stage.getHeight() - 150);
        kotakInput.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 20;");
        kotakInput.setPadding(new Insets(10, 10, 10, 10));
        kotakInput.setAlignment(javafx.geometry.Pos.CENTER);

        scrollContetBox = new VBox();
        scrollContetBox.setSpacing(10);
        scrollContetBox.toFront();

        scrollPane = new ScrollPane(scrollContetBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setMaxSize(stage.getWidth() - 350, stage.getHeight() - 250);
        scrollPane.toFront();

        // Memaksimalkan ukuran vbox dengan scrollpane
        scrollContetBox.setMaxHeight(scrollPane.getMaxHeight());
        scrollContetBox.setMaxWidth(scrollPane.getMaxWidth() - 20);

        for (int i = 0; i < mengambilBanyakDataDiTarget(); i++) {
            String namaTarget = namaTargetList.get(i);
            double nominalTarget = nominalTargetList.get(i);
            String keteranganBarang = keteranganBarangList.get(i);

            // Membuat tampilan untuk menampilkan data yang ada
            VBox itemBox = new VBox(); // Membuat VBox baru untuk setiap item
            itemBox.setStyle("-fx-background-color: #ADD8E6; -fx-padding: 10; -fx-spacing: 5;"); // Warna biru terang
            itemBox.setMaxHeight(100);
            itemBox.setPrefWidth(scrollPane.getMaxWidth() - 20);

            // Membuat tampilan untuk menampilkan data yang ada
            String itemtext = "Nama Target: " + namaTarget + "\nNominal Target: " + nominalTarget
                    + "\nKeterangan Barang: " + keteranganBarang;
            Label itemLabel = new Label(itemtext);

            Button hapusTarget = new Button("Hapus Target");
            hapusTarget.setOnAction(e -> {
                HapusTarget hapusTargetModel = new HapusTarget(namaTarget);
                if (hapusTargetModel.start()) {
                    AlertHelper.info("Target berhasil dihapus!");
                } else {
                    AlertHelper.alert("Target gagal dihapus!");
                }
                refreshView();
            });
            // Create an HBox to hold the label and button
            HBox itemBoxContent = new HBox();

            // Create panes to hold label and button
            StackPane labelPane = new StackPane(itemLabel);
            StackPane buttonPane = new StackPane(hapusTarget);

            // Add panes to HBox
            itemBoxContent.getChildren().addAll(labelPane, buttonPane);

            // Set alignment and constraints
            HBox.setHgrow(labelPane, Priority.ALWAYS); // Label takes up available space

            // Set alignment within StackPanes
            StackPane.setAlignment(itemLabel, Pos.CENTER_LEFT); // Label is aligned to the left
            StackPane.setAlignment(hapusTarget, Pos.BOTTOM_RIGHT); // Button is in the bottom right corner

            itemBox.getChildren().add(itemBoxContent); // Add HBox to VBox
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

        mainPane.getChildren().addAll(welcome, tambahTarget, scrollPane);
        mainPane.setMaxSize(this.stage.getWidth() - 200, this.stage.getHeight() - 100);
        mainPane.setStyle("-fx-background-color: #141F23; -fx-background-radius: 20;");
        mainPane.setPadding(new Insets(10, 10, 10, 10));
        mainPane.setAlignment(javafx.geometry.Pos.CENTER);

        StackPane backgroundUtamaPane = new StackPane(mainPane);
        StackPane.setAlignment(mainPane, javafx.geometry.Pos.CENTER);
        Scene scene = new Scene(backgroundUtamaPane, 750, 500);
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

        // Menambahkan listener ke lebar dan tinggi stage
        this.stage.widthProperty().addListener(stageSizeListener);
        this.stage.heightProperty().addListener(stageSizeListener);

        this.stage.setScene(scene);
        this.stage.setMinHeight(500);
        this.stage.setMinWidth(750);
        setOnMouseClicked(mainPane, welcome);
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

    private boolean setUserTarget(int userId, String namaTarget, double nominalTarget, String keteranganBarang) {
        TambahTarget tambahTarget = new TambahTarget(userId, namaTarget, nominalTarget, keteranganBarang);
        if (tambahTarget.start()) {
            AlertHelper.info("Penyetelan " + namaTarget + " berhasil!" + namaTarget + " berhasil ditambahkan!");
            return true;
        }
        return false;
    }

    private int mengambilBanyakDataDiTarget() {
        TampilkanSemuaTarget tampilkanSemuaTarget = new TampilkanSemuaTarget();
        return tampilkanSemuaTarget.mendapatkanBanyakDataDiTarget();
    }

    private void refreshView() {
        scrollContetBox.getChildren().clear(); // Menghapus semua item yang ada
        PanenUang.model = new TampilkanSemuaTarget();
        PanenUang.namaTargetList = model.getNamaTargetList();
        PanenUang.nominalTargetList = model.getNominalTargetList();
        PanenUang.keteranganBarangList = model.getKeteranganBarangList();
        // Menambahkan ulang item-item dari data baru
        for (int i = 0; i < mengambilBanyakDataDiTarget(); i++) {
            String namaTarget = namaTargetList.get(i);
            double nominalTarget = nominalTargetList.get(i);
            String keteranganBarang = keteranganBarangList.get(i);

            // Membuat tampilan untuk menampilkan data yang ada
            VBox itemBox = new VBox(); // Membuat VBox baru untuk setiap item
            itemBox.setStyle("-fx-background-color: #ADD8E6; -fx-padding: 10; -fx-spacing: 5;"); // Warna biru terang
            itemBox.setMaxHeight(100);
            itemBox.setPrefWidth(scrollPane.getMaxWidth() - 20);

            // Membuat tampilan untuk menampilkan data yang ada
            String itemtext = "Nama Target: " + namaTarget + "\nNominal Target: " + nominalTarget
                    + "\nKeterangan Barang: " + keteranganBarang;
            Label itemLabel = new Label(itemtext);

            Button hapusTarget = new Button("Hapus Target");
            hapusTarget.setOnAction(e -> {
                HapusTarget hapusTargetModel = new HapusTarget(namaTarget);
                if (hapusTargetModel.start()) {
                    AlertHelper.info("Target berhasil dihapus!");
                } else {
                    AlertHelper.alert("Target gagal dihapus!");
                }
                refreshView();
            });
            // Create an HBox to hold the label and button
            HBox itemBoxContent = new HBox();

            // Create panes to hold label and button
            StackPane labelPane = new StackPane(itemLabel);
            StackPane buttonPane = new StackPane(hapusTarget);

            // Add panes to HBox
            itemBoxContent.getChildren().addAll(labelPane, buttonPane);

            // Set alignment and constraints
            HBox.setHgrow(labelPane, Priority.ALWAYS); // Label takes up available space

            // Set alignment within StackPanes
            StackPane.setAlignment(itemLabel, Pos.CENTER_LEFT); // Label is aligned to the left
            StackPane.setAlignment(hapusTarget, Pos.BOTTOM_RIGHT); // Button is in the bottom right corner

            itemBox.getChildren().add(itemBoxContent); // Add HBox to VBox
            scrollContetBox.getChildren().add(itemBox); // Add VBox to scrollContetBox
        }
    }

}
