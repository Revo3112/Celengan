package View.Login_Register;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import Controller.SceneController;
import Model.*;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

// membuat class LoginPage
public class LoginPage {
    private Stage stage; // Deklarasi property stage
    SceneController sceneController; // sceneController utk pages switching

    // cursors
    private Cursor hand = Cursor.cursor("HAND");
    private Cursor closedHand = Cursor.cursor("CLOSED_HAND");
    private Cursor defaultCursor = Cursor.cursor("DEFAULT");

    // kondisi
    private boolean isMousePressed = false; // apakah mouse ditekan
    private boolean isWindowMax = true; // apakah window maximized

    // inisiasi koordinat offset x y mouse
    private double xOffset = 0; // track x
    private double yOffset = 0; // track y

    // error status
    boolean hLogErrStat = false;

    // main pane inisiasi
    StackPane mainContent;

    // timelines
    Timeline hopAyamBetina_IN; // ayam netral melompat ke dalam
    Timeline hopAyamBetina_OUT; // ayam netral keluar (selesai)
    Timeline ayamError_IN; // ayam error melompat ke dalam
    Timeline ayamError_OUT; // ayam error keluar (selesai)
    Timeline awanError_IN; // ayam error masuk
    Timeline ayamHand_IN; // tangan ayam untuk password ke dalam
    Timeline ayamHand_OUT; // tangan ayam untuk password ke luar
    Timeline fadeChatAyam_IN; // chat ayam netral ke dalam
    Timeline fadeChatAyam_OUT; // chat ayam netral keluar (selesai)

    // Lists timeline
    List<Timeline> listAyamNetral_IN;
    List<Timeline> listAyamNetral_OUT;
    List<Timeline> listAyamError_IN;
    List<Timeline> listAyamError_OUT;

    // deklarasi path asset dari login_register
    String logRegPath = "/Assets/View/Login_Register/";

    // animasi ayam error/deklarasi image
    ImageView ayamError = createImage(logRegPath + "ayamMarah.png", 180, 180);
    ImageView awanError = createImage(logRegPath + "awan.png", 350, 350);

    // deklarasi durasi animasi ayamError hop
    Duration ayamErrorHopDur = Duration.millis(600);
    Duration awanErrorHopDur = Duration.millis(2300);

    // deklarasi koordinat awal Y dan akhir untuk animasi ayamError
    double ayamErrorOriginY = ayamError.getTranslateY();
    double ayamErrorTargetY = ayamErrorOriginY - 250;
    double awanErrorOriginY = awanError.getTranslateY();
    double awanErrorTargetY = awanErrorOriginY - 500;

    // List image chat error ayam
    List<ImageView> listChatErrorAyam;

    // inisiasi variabel user_id utk database
    private int user_id;

    // inisiasi pane root
    StackPane root = new StackPane(); // stackpane root

    // Melakukan inisiasi class LoginPage dengan parameter stage
    public LoginPage(Stage stage) {
        this.stage = stage; // Instansiasi property stage dengan parameter stage
        sceneController = new SceneController(stage); // instansiasi sceneController
        // instansiasi objek user dari class login model
        LoginModel user = new LoginModel();
        this.user_id = user.getUserId(); // menampung user id dari method getUserId()
        // menetapkan icon aplikasi
        Image icon = new Image("Assets/View/Splash_Screen/images/logo/celengan_image_logo.png");
        this.stage.getIcons().add(icon); // Menambahkan icon ke dalam stage
        this.stage.setTitle("Celengan"); // membuat title aplikasi
    }

    // fungsi untuk menentukan apakah user langsung dashboard atau tidak
    public void penentuApakahStartAtauLangsungDashboard() {
        Boolean penentuAkhir = false; // trigger penentu user
        LoginModel login = new LoginModel(); // Membuat objek login
        penentuAkhir = login.penentuBagianLastUser(); // menampung penentuAkhir dari method penentuBagiLastUser()
        if (penentuAkhir) {
            sceneController.switchToDashboard(); // Merubah scene menuju dashboard
        } else {
            this.start(); // Menjalankan method start()
        }
    }

    // Menampilkan halaman login
    public void start() {
        /* UI/UX : NULLPTR */
        /* BAGIAN MMC (MINIMIZE, MAXIMIZE, CLOSE) */
        // membuat closeButton
        Button closeButton = createButton(35, 35, "X", "FF4646", 23, "Poppins", 30, "0F181B");

        // membuat maximizeButton
        Button maximizeButton = createButton(35, 35, " ", "FFFFFF", 23, "Poppins", 30, "0F181B");

        // membuat icon maximize
        Rectangle maxIcon = new Rectangle(14, 14);
        maxIcon.setFill(Color.TRANSPARENT); // warna transparen
        maxIcon.setStroke(Color.valueOf("#0F181B")); // stroke berwarna
        maxIcon.setStrokeWidth(4); // ketebalan stroke 4
        maxIcon.setTranslateX(maximizeButton.getTranslateX() + 5); // posisi koordinat x icon
        maximizeButton.setGraphic(maxIcon); // menetapkan grafik icon dalam maximizeButton

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
        // saat selesai dihover maka cursor kembali ke default
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
                root.setOnMousePressed(e -> {
                    if (e.isPrimaryButtonDown() && e.getClickCount() == 2 && !isWindowMax) {
                        root.getScene().setCursor(closedHand);
                        isMousePressed = true;
                        xOffset = e.getSceneX();
                        yOffset = e.getSceneY();
                    }
                });
                // saat dilepas
                root.setOnMouseReleased(e -> {
                    root.getScene().setCursor(defaultCursor);
                    isMousePressed = false;
                });
                // saat ditarik
                root.setOnMouseDragged(e -> {
                    if (isMousePressed && !isWindowMax) {
                        root.getScene().setCursor(closedHand);
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
        // saat mouse keluar maka maximizeButton akan kembali ke default dan warna
        // default
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
        // saat tidak di hover maka cursor kembali ke default dan warna minimzeButton
        // balik
        minimizeButton.setOnMouseExited(closeEvent -> {
            minimizeButton.getScene().setCursor(defaultCursor);
            updateButton(minimizeButton, 35, 35, "-", "FFFFFF", 23, "Poppins", 30, "0F181B");
        });

        /* BAGIAN DROPDOWN LOGIN */
        // membuat dropdown button
        ComboBox<String> dropDownBtn = new ComboBox<>(); // menetapkan inisiasi objeck dropDownBtn dr class ComboBox()
        dropDownBtn.setMinWidth(20);
        dropDownBtn.setMinHeight(40);
        dropDownBtn.setPromptText("Login Page");
        dropDownBtn.setItems(FXCollections.observableArrayList("Login Page", "Register Page", "Request New Password")); // menambahkan
                                                                                                                        // items
                                                                                                                        // yang
                                                                                                                        // ada
                                                                                                                        // di
                                                                                                                        // cell
                                                                                                                        // dropdown
        dropDownBtn.setStyle(
                "-fx-background-radius: 130;" +
                        "-fx-background-color: #FFFFFF;" +
                        "-fx-font: 16 Poppins;" +
                        "-fx-border-color: #000000; " + // Set the stroke color
                        "-fx-border-width: 3px;" +
                        "-fx-border-radius: 90;");
        // logic dropdownButton
        dropDownBtn.setOnAction(e -> {
            String selectedItem = dropDownBtn.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                if ("Register Page".equals(selectedItem) || "Request New Password".equals(selectedItem)) {
                    // membuat rectangle base loading
                    Rectangle baseKukooLoading = new Rectangle(root.getWidth(), root.getHeight(),
                            Color.valueOf("#101C22"));
                    // asset gif
                    ImageView kukooAnim = createImage(logRegPath + "celengan_loading_anim.gif", 400, 400);
                    kukooAnim.setTranslateY(kukooAnim.getTranslateY() - 90);

                    Text loadingText = createText("LOADING ...", 20, "Poppins", "376675");
                    loadingText.setTranslateY(loadingText.getTranslateY() + 70);

                    Text tipsText = createText("Nabung Celengan setiap hari membuatmu punya tabungan darurat!", 20,
                            "Poppins; -fx-font-weight: 500;", "ffffff");
                    tipsText.setTranslateY(loadingText.getTranslateY() + 60);

                    Rectangle border = new Rectangle(400, 400);
                    border.setFill(Color.TRANSPARENT);
                    border.setStroke(Color.valueOf("#101C22"));
                    border.setStrokeWidth(5);
                    border.setTranslateY(kukooAnim.getTranslateY());

                    StackPane loadingAnimation = new StackPane();
                    loadingAnimation.getChildren().addAll(baseKukooLoading, kukooAnim, border, loadingText, tipsText);

                    root.getChildren().add(loadingAnimation);

                    FadeTransition fadeInSaldo = new FadeTransition(Duration.millis(500), loadingAnimation);
                    fadeInSaldo.setFromValue(0.0);
                    fadeInSaldo.setToValue(1.0);

                    Timeline timeline = new Timeline(
                            new KeyFrame(Duration.seconds(2), event -> {
                                // Code to execute after approximately 2 seconds
                                switch (selectedItem) {
                                    case "Register Page":
                                        sceneController.switchToRegistration();
                                        break;
                                    case "Request New Password":
                                        sceneController.switchToRequestNewPassword();
                                        break;
                                    default:
                                        break;
                                }
                            }));
                    timeline.setCycleCount(1); // Set to 1 for a single loop

                    fadeInSaldo.setOnFinished(event -> {
                        timeline.play();
                    });

                    fadeInSaldo.play();
                }
            }
        });

        // BUTTON ORGANIZER
        // memasukkan button ke dalam HBox
        HBox mmcButton = new HBox(5); // 5 spacing
        mmcButton.getChildren().addAll(minimizeButton, maximizeButton, closeButton);
        mmcButton.setMaxWidth(150);
        mmcButton.setAlignment(Pos.TOP_RIGHT);
        // memasukkan dropdown ke dalam HBox
        HBox dropDownLogin = new HBox(20);
        dropDownLogin.getChildren().add(dropDownBtn);
        dropDownLogin.setAlignment(Pos.TOP_LEFT);
        // menggabungkan kedua HBox ke dalam satu HBox
        HBox topElements = new HBox(500);
        topElements.getChildren().addAll(dropDownLogin, mmcButton);
        topElements.setStyle("-fx-padding: 16, 16, 0, 16;"); // padding: top left bottom right
        HBox.setHgrow(dropDownLogin, Priority.ALWAYS);
        topElements.setAlignment(Pos.TOP_RIGHT);
        // menggabungkan topElements ke section
        VBox topSection = new VBox(topElements);

        /* BAGIAN MAIN CONTENT */
        // membuat base main content
        Rectangle backgroundBase = createRect(490, 360, 60, 60, "0F181B");
        Rectangle mainBase = createRect(backgroundBase.getWidth(), backgroundBase.getHeight(), 60, 60,
                "263940");
        // menambahkan stroke pada mainBase
        mainBase.setStroke(Color.valueOf("#0F181B"));
        mainBase.setStrokeWidth(3);
        // mengubah posisi mainBase agar turun sedikit dari backgroundBase
        mainBase.setTranslateY(backgroundBase.getTranslateY() - 15);
        // menggabungkan backgroundBase dengan mainBase menjadi satu group
        Group base = new Group(backgroundBase, mainBase);
        // MAIN ELEMENTS: elemen di dalam main base
        // USERNAME TEXT: bagian informasi text username dan icon
        Text username = createText("username", 25, "Poppins", "AD7AFF");
        ImageView usr_icon = createImage("/Assets/View/Login_Register/username_icon.png", 40, 40);
        // menambah masing-masing username elements ke hbox
        HBox usernameText = new HBox(5);
        usernameText.getChildren().addAll(usr_icon, username);
        usernameText.setStyle("-fx-padding: 0 0 10 40;");
        // USERNAME FIELD: bagian yang dapat diinput username
        TextField inputUsername = new TextField();
        inputUsername.setMaxWidth(mainBase.getWidth() - 80);
        inputUsername.setMaxHeight(120);
        inputUsername.setStyle(
                "-fx-background-radius: 30;" +
                        "-fx-background-color: #141F23;" +
                        "-fx-font: 23 Poppins;" +
                        "-fx-text-fill: white;");
        // PASSWORD TEXT: bagian informasi text password dan icon
        Text password = createText("password", 25, "Poppins", "AD7AFF");
        ImageView pwd_icon = createImage("/Assets/View/Login_Register/password_icon.png", 40, 40);
        // menambah masing-masing elemen password elements ke hbox
        HBox passwordText = new HBox(5);
        passwordText.getChildren().addAll(pwd_icon, password);
        passwordText.setStyle("-fx-padding: 10 0 10 40;");
        // PASSWORD FIELD: bagian yang dapat diinput password
        PasswordField inputPassword = new PasswordField();
        inputPassword.setMaxWidth(mainBase.getWidth() - 90);
        inputPassword.setMaxHeight(40);
        inputPassword.setStyle(
                "-fx-background-radius: 90;" +
                        "-fx-background-color: #141F23;" +
                        "-fx-font: 23 Poppins;" +
                        "-fx-text-fill: white;");

        // ADDITIONAL OPTIONS: berisi lupa password? dan ingatkan saya
        Hyperlink lupaPassword = new Hyperlink("Lupa Password?");
        lupaPassword.setStyle(
                "-fx-text-fill: #FF7474;" +
                        "-fx-font: 14 Poppins;");
        // kondisi hyperlink
        lupaPassword.setOnAction(e -> {
            sceneController.switchToRequestNewPassword();
        });
        CheckBox ingatkanSaya = new CheckBox("Ingatkan Saya");
        ingatkanSaya.setStyle(
                "-fx-text-fill: #AD7AFF;" +
                        "-fx-font: 14 Poppins;" +
                        "-fx-mark-color: #AD7AFF;");
        // menggabungkan elemen addoptions
        HBox addOption = new HBox(150);
        addOption.getChildren().addAll(lupaPassword, ingatkanSaya);
        addOption.setStyle("-fx-padding: 10 0 10 40;");
        // BUTTON LOGIN: seluruh tombol masuk dan belum punya akun?
        // button login
        Button masukLogin = createButton(120, 50, "Masuk", "AB77FF", 22, "Poppins", 30, "141F23");
        Rectangle masukLoginBG = new Rectangle();

        // button register
        Button registButton = createButton(280, 50, "Belum Punya Akun?", "141F23", 22, "Poppins", 30, "AB77FF");
        Rectangle registButtonBG = new Rectangle();

        // menyatukan elemen login ke satu group
        Group loginButton = new Group(masukLogin, masukLoginBG);
        // LOGIN BUTTON EVENTS: saat di hover atau di klik
        // login button
        masukLogin.setOnMouseEntered(e -> {
            masukLogin.getScene().setCursor(hand);
            System.out.println("Username: " + inputUsername.getText());
            System.out.println("Password: " + inputPassword.getText());
            updateButton(masukLogin, 120, 50, "Masuk", "56348D", 22, "Poppins", 30, "0A0F11");
        });
        masukLogin.setOnMouseExited(e -> {
            masukLogin.getScene().setCursor(defaultCursor);
            updateButton(masukLogin, 120, 50, "Masuk", "AB77FF", 22, "Poppins", 30, "141F23");
        });
        masukLogin.setOnAction(e -> {
            if (awanError_IN != null) {
                awanError_IN.stop(); // Stop the animation if it's already running
                awanError.translateYProperty().set(awanErrorOriginY); // Reset translateY to original Y position
            }
            System.out.println("Login button clicked!");
            System.out.println(
                    "Before handleLogin: Stage Width = " + stage.getWidth() + ", Height = " + stage.getHeight());
            hLogErrStat = handleLogin(
                    inputUsername.getText(),
                    inputPassword.getText(),
                    ingatkanSaya,
                    inputUsername,
                    inputPassword,
                    listAyamNetral_OUT,
                    listChatErrorAyam,
                    mainContent,
                    ayamError_IN,
                    awanError_IN);
            System.out.println(
                    "After handleLogin: Stage Width = " + stage.getWidth() + ", Height = " + stage.getHeight());

            awanError_IN.play();
        });
        // Register Button
        registButton.setOnMouseEntered(e -> {
            registButton.getScene().setCursor(hand);
            updateButton(registButton, 280, 50, "Belum Punya Akun?", "040707", 22, "Poppins", 30, "3E2862");
        });
        registButton.setOnMouseExited(e -> {
            registButton.getScene().setCursor(defaultCursor);
            updateButton(registButton, 280, 50, "Belum Punya Akun?", "141F23", 22, "Poppins", 30, "AB77FF");
        });
        registButton.setOnAction(e -> {
            // pergi ke registration page
            sceneController.switchToRegistration();
        });

        // menyatukan elemen register ke satu group
        Group registerButton = new Group(registButton, registButtonBG);
        // memasukkan loginButton ke HBox
        HBox loginBtn = new HBox();
        loginBtn.getChildren().add(loginButton);
        // memasukkan registerButton ke HBox
        HBox registBtn = new HBox();
        registBtn.getChildren().add(registerButton);
        // menggabungkan keduanya ke VBox
        HBox loginRegistButton = new HBox(10);
        loginRegistButton.getChildren().addAll(loginBtn, registBtn);
        loginRegistButton.setStyle("-fx-padding: 10 0 10 40;");

        // menggabungkan seluruh elements
        VBox contentElements = new VBox();
        contentElements.getChildren().addAll(usernameText, inputUsername, passwordText, inputPassword, addOption,
                loginRegistButton);
        contentElements.setAlignment(Pos.CENTER);
        contentElements.setMaxWidth(mainBase.getWidth());
        contentElements.setMaxHeight(200);

        // LOGO: logo celengan
        // inisiasi logo
        ImageView logo = createImage("/Assets/View/Splash_Screen/images/logo/celengan_image_logo.png", 60, 60);
        // inisiasi logo text
        Text logoText = createText("Celengan", 40, "Poppins", "FFFFFF");
        // memasukkan keduanya di HBox
        HBox logoElements = new HBox(5);
        logoElements.getChildren().addAll(logo, logoText);
        logoElements.setAlignment(Pos.BOTTOM_CENTER);
        // menggabungkan semua elemen logo ke stackPane
        StackPane logoCelengan = new StackPane(logoElements);
        logoCelengan.setMaxHeight(50);
        logoCelengan.setTranslateY(logoCelengan.getTranslateY() + 270);

        // AYAM: animasi ayam
        ImageView ayamBetina = createImage("/Assets/View/Login_Register/ayamBetina.png", 180, 180);
        double ayamOriginY = ayamBetina.getTranslateY();
        // timeline animasi untuk hop in ayam betina
        hopAyamBetina_IN = new Timeline(
                new KeyFrame(Duration.millis(600),
                        new KeyValue(ayamBetina.translateYProperty(), ayamOriginY - 250, Interpolator.EASE_BOTH)));
        // timeline animasi untuk hop out ayam betina
        hopAyamBetina_OUT = new Timeline(
                new KeyFrame(Duration.millis(600),
                        new KeyValue(ayamBetina.translateYProperty(), ayamOriginY, Interpolator.EASE_BOTH)));
        // CHAT AYAM: animasi chat ayam
        ImageView chatAyam = createImage("/Assets/View/Login_Register/ayamChat.png", 250, 250);
        chatAyam.setTranslateY(chatAyam.getTranslateY() - 280);
        chatAyam.setTranslateX(chatAyam.getTranslateX() - 210);
        chatAyam.setOpacity(0);
        // timeline animasi untuk chat ayam fade in
        fadeChatAyam_IN = new Timeline(
                new KeyFrame(Duration.millis(700),
                        new KeyValue(chatAyam.opacityProperty(), 1)));
        // timeline animasi untuk chat ayam fade out
        fadeChatAyam_OUT = new Timeline(
                new KeyFrame(Duration.millis(300),
                        new KeyValue(chatAyam.opacityProperty(), 0)));
        // TANGAN AYAM: animasi tangan ayam saat password field diketik
        ImageView ayamHand = createImage("/Assets/View/Login_Register/ayamHand.png", 170, 170);
        double ayamHandOriginY = ayamHand.getTranslateY();
        ayamHand_IN = new Timeline(
                new KeyFrame(Duration.millis(400),
                        new KeyValue(ayamHand.translateYProperty(), ayamHandOriginY - 220, Interpolator.EASE_BOTH)));
        ayamHand_OUT = new Timeline(
                new KeyFrame(Duration.millis(500),
                        new KeyValue(ayamHand.translateYProperty(), ayamHandOriginY, Interpolator.EASE_BOTH)));
        // ANIMASI ERROR AYAM:
        // animasi ayam error masuk
        ayamError_IN = new Timeline(
                new KeyFrame(ayamErrorHopDur,
                        new KeyValue(ayamError.translateYProperty(), ayamErrorTargetY, Interpolator.EASE_BOTH)));
        // animasi ayam error keluar
        ayamError_OUT = new Timeline(
                new KeyFrame(ayamErrorHopDur,
                        new KeyValue(ayamError.translateYProperty(), ayamErrorOriginY, Interpolator.EASE_BOTH)));
        // animasi awan error masuk
        awanError_IN = new Timeline(
                new KeyFrame(Duration.millis(500),
                        new KeyValue(awanError.translateYProperty(), awanErrorOriginY)),
                new KeyFrame(awanErrorHopDur,
                        new KeyValue(awanError.translateYProperty(), awanErrorTargetY, Interpolator.EASE_BOTH)));
        // // saat selesai di reset agar tidak stack
        // awanError_IN.setOnFinished(e -> {
        // awanError_IN = null;
        // });

        // animasi text ayam error
        listChatErrorAyam = new ArrayList<>();
        // menambahkan error password (chat)
        listChatErrorAyam.add(createImage(logRegPath + "passwordKosong.png", 200, 200)); // password kosong
        listChatErrorAyam.add(createImage(logRegPath + "passwordSalah.png", 200, 200)); // password salah
        // menambahkan error username (chat)
        listChatErrorAyam.add(createImage(logRegPath + "usernameKosong.png", 200, 200)); // username kosong
        listChatErrorAyam.add(createImage(logRegPath + "usernameSalah.png", 200, 200)); // username salah
        // menambahkan error keduanya
        listChatErrorAyam.add(createImage(logRegPath + "usernamePasswordKosong.png", 200, 200)); // username dan
                                                                                                 // password
        // kosong 4
        listChatErrorAyam.add(createImage(logRegPath + "usernamePasswordSalah.png", 200, 200)); // username dan password
                                                                                                // 5
        // kosong
        // List ayam-ayam netral IN
        listAyamNetral_IN = new ArrayList<>();
        listAyamNetral_IN.add(hopAyamBetina_IN);
        listAyamNetral_IN.add(fadeChatAyam_IN);
        listAyamNetral_IN.add(ayamHand_IN);
        // List ayam-ayam netral OUT
        listAyamNetral_OUT = new ArrayList<>();
        listAyamNetral_OUT.add(hopAyamBetina_OUT);
        listAyamNetral_OUT.add(fadeChatAyam_OUT);
        listAyamNetral_OUT.add(ayamHand_OUT);
        // List ayam-ayam error IN
        listAyamError_IN = new ArrayList<>();
        listAyamError_IN.add(ayamError_IN);
        // List ayam-ayam error OUT
        listAyamError_OUT = new ArrayList<>();
        listAyamError_OUT.add(ayamError_OUT);

        // memasukkan seluruh elemen ke dalam stackPane mainContent
        mainContent = new StackPane(chatAyam, ayamBetina, ayamHand, ayamError, awanError, base,
                contentElements, logoCelengan);
        mainContent.setMaxSize(600, 300);
        // mainContent.setStyle("-fx-background-color: red");
        // GENERAL EVENTS: events yang mencakup semua
        inputPassword.setOnKeyTyped(e -> {
            if (!hLogErrStat) {
                ayamHand_IN.play();
            }
        });
        inputUsername.setOnMousePressed(e -> {
            ayamHand_OUT.play();
        });
        mainContent.setOnMouseEntered(e -> {
            if (!hLogErrStat) {
                hopAyamBetina_IN.play();
                fadeChatAyam_IN.play();
            }
        });
        root.setOnMouseClicked(e -> {
            if (e.getClickCount() == 1) {
                hopAyamBetina_OUT.play();
                fadeChatAyam_OUT.play();
                ayamHand_OUT.play();
            }
        });

        // root stackPane
        root.getChildren().addAll(topSection, mainContent);
        root.setStyle("-fx-background-color: #141F23;");

        Scene scene = new Scene(root, this.stage.getWidth(), this.stage.getHeight());

        // membuat rectangle base loading
        Rectangle baseKukooLoading = new Rectangle(root.getWidth(), root.getHeight(), Color.valueOf("#101C22"));
        // asset gif
        ImageView kukooAnim = createImage(logRegPath + "celengan_loading_anim.gif", 400, 400);
        kukooAnim.setTranslateY(kukooAnim.getTranslateY() - 90);

        Text loadingText = createText("LOADING ...", 20, "Poppins", "376675");
        loadingText.setTranslateY(loadingText.getTranslateY() + 70);

        Text tipsText = createText("Nabung Celengan setiap hari membuatmu punya tabungan darurat!", 20,
                "Poppins; -fx-font-weight: 500;", "ffffff");
        tipsText.setTranslateY(loadingText.getTranslateY() + 60);

        Rectangle border = new Rectangle(400, 400);
        border.setFill(Color.TRANSPARENT);
        border.setStroke(Color.valueOf("#101C22"));
        border.setStrokeWidth(5);
        border.setTranslateY(kukooAnim.getTranslateY());

        StackPane loadingAnimation = new StackPane();
        loadingAnimation.getChildren().addAll(baseKukooLoading, kukooAnim, border, loadingText, tipsText);

        // agar clickable
        loadingAnimation.setMouseTransparent(true);
        root.getChildren().add(loadingAnimation);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), loadingAnimation);
        fadeIn.setFromValue(1.0);
        fadeIn.setToValue(0.0);
        fadeIn.play();

        stage.setScene(scene); // menetapkan scene dari sebuah stage
        // Membuat Scene dengan latar belakang transparent
        scene.getStylesheets()
                .addAll("https://fonts.googleapis.com/css2?family=Poppins:wght@500;600;700&display=swap");
        stage.setMaximized(true);
        /* END OF UI/UX */
    }

    /* SALDO DAN MODE KRITIS */
    private void handleInputSaldodanMode(int saldo, int modeKritis) {
        InputSaldodanMode inputSaldodanMode = new InputSaldodanMode(saldo, modeKritis, this.user_id);
        SceneController sceneController = new SceneController(this.stage);
        if (inputSaldodanMode.setSaldo()) {
            sceneController.switchToDashboard();
        } else {
            System.out.println("Gagal");
        }
    }

    /* BUTTON METHODS */
    // method untuk mengupdate button
    private void updateButton(Button btn, int width, int height, String text, String bgColor,
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

    // method untuk membuat button
    private Button createButton(int width, int height, String text, String bgColor, int fontSize,
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

    /* MAIN CONTENT METHODS */
    // method membuat Image
    private ImageView createImage(String imgPath, double width, double height) {

        ImageView imageView = new ImageView();
        Image imageImg = new Image(imgPath);

        imageView.setImage(imageImg); // menetapkan gambar
        imageView.setFitWidth(width); // menetapkan panjang gambar
        imageView.setFitHeight(height); // menetapkan tinggi gambar

        return imageView;
    }

    // method membuat text
    private Text createText(String text, int textSize, String textFont, String textFill) {
        Text txt = new Text(text);
        txt.setStyle("-fx-font: " + textSize + " " + textFont + ";");
        txt.setFill(Color.valueOf("#" + textFill));
        return txt;
    }

    // method membuat rectangle
    private Rectangle createRect(double width, double height, double arcWidth, double arcHeight,
            String fill) {
        Rectangle rect = new Rectangle(width, height);
        rect.setArcWidth(arcWidth);
        rect.setArcHeight(arcHeight);
        rect.setFill(Color.valueOf("#" + fill));
        return rect;
    }

    // Metode untuk melakukan pengecekan ke database dan kondisi
    public boolean handleLogin(String username, String password, CheckBox checkBox, TextField usernameField,
            PasswordField passwordField, List<Timeline> animOut, List<ImageView> chatError, StackPane mainPane,
            Timeline... animIn) {
        LoginModel login = new LoginModel(); // Membuat objek login
        SceneController sceneController = new SceneController(this.stage); // Membuat objek sceneController

        // check error status
        boolean statusErr = false;

        // me-reset animasi lainnya
        for (Timeline playAnimOut : animOut) {
            playAnimOut.play();
        }

        // me-reset keduanya error style
        removeErrorStyle(usernameField, passwordField, 3);

        // Clear existing chatError nodes
        mainPane.getChildren().removeAll(chatError);

        if (login.isValidated(username, password, checkBox.isSelected())) {
            if (login.penentuApakahSudahAdaSaldo()) {
                sceneController.switchToDashboard();
            } else {
                // membuat dark background overlay
                Rectangle bgOverlay = new Rectangle(this.stage.getWidth(), this.stage.getHeight(), Color.BLACK);

                // membuat bg saldoModeInput
                Rectangle saldoModeInputBG = new Rectangle(800, 500, Color.valueOf("#AD7AFF"));
                saldoModeInputBG.setArcWidth(40);
                saldoModeInputBG.setArcHeight(40);

                // text keterangan
                Text headerText = createText("Tunggu Dulu!", 48, "Poppins; -fx-padding: 10 0 0 0;", "000000");
                // text deskripsi
                Text descText = new Text(
                        "Sebelum masuk ke halaman dashboard, kamu\nperlu untuk memasukkan saldo awal dari batas\nkritismu terlebih dahulu!");
                descText.setTextAlignment(TextAlignment.CENTER);
                descText.setFont(Font.font("Poppins Regular", FontWeight.NORMAL, 20));
                descText.setFill(Color.valueOf("000000"));

                // text saldo awal
                Text saldoAwalText = createText("Saldo Awal", 25, "Poppins", "000000");
                ImageView saldoIcon = createImage(logRegPath + "wallet.png", 35, 35);
                // satukan ke hbox
                HBox saldoElement = new HBox(5);
                saldoElement.getChildren().addAll(saldoIcon, saldoAwalText);
                saldoElement.setAlignment(Pos.CENTER);

                // membuat field untuk input saldo
                TextField fieldSaldo = new TextField();
                fieldSaldo.setMaxWidth(saldoModeInputBG.getWidth() - 400);
                fieldSaldo.setMaxHeight(120);
                fieldSaldo.setStyle(
                        "-fx-background-radius: 30;" +
                                "-fx-background-color: #35136C;" +
                                "-fx-font: 23 Poppins;" +
                                "-fx-border-color: #8A43FF;" +
                                "-fx-border-width: 3;" +
                                "-fx-border-radius: 30;" +
                                "-fx-text-fill: white;");

                // text batas kritis
                Text batasKritisText = createText("Batas Kritis", 25, "Poppins", "000000");
                ImageView batasIcon = createImage(logRegPath + "batas.png", 35, 35);
                // satukan ke hbox
                HBox batasKritisElement = new HBox(5);
                batasKritisElement.getChildren().addAll(batasIcon, batasKritisText);
                batasKritisElement.setAlignment(Pos.CENTER);

                // membuat field untuk input batas kritis
                TextField fieldModeKritis = new TextField();
                fieldModeKritis.setMaxWidth(saldoModeInputBG.getWidth() - 400);
                fieldModeKritis.setMaxHeight(120);
                fieldModeKritis.setStyle(
                        "-fx-background-radius: 30;" +
                                "-fx-background-color: #35136C;" +
                                "-fx-font: 23 Poppins;" +
                                "-fx-border-color: #8A43FF;" +
                                "-fx-border-width: 3;" +
                                "-fx-border-radius: 30;" +
                                "-fx-text-fill: white;");

                // field listener
                fieldSaldo.textProperty().addListener((ChangeListener<? super String>) new ChangeListener<String>() {
                    private String codeFormat = ",##0";
                    private int codeLen = 4;

                    @Override
                    // ObservableValue = StringProperty (Represent an object that has changed its
                    // state)
                    public void changed(ObservableValue<? extends String> observable, String oldValue,
                            String newValue) {
                        // Hapus listener sementara
                        fieldSaldo.textProperty().removeListener(this);
                        if (newValue.replace(",", "").matches("\\d*")) { // Check inputan angka atau tidak
                            if (newValue.length() > oldValue.length()) {
                                if (newValue.length() > 13) {
                                    fieldSaldo.setText(oldValue);
                                } else {
                                    updateCodeFormat(newValue);
                                    formatAndSet(newValue, this.codeFormat);
                                }
                            }
                        } else {
                            fieldSaldo.setText(oldValue);
                        }

                        // Tambahkan kembali listener setelah pembaruan teks
                        fieldSaldo.textProperty().addListener(this);
                    }

                    private void formatAndSet(String text, String format) {
                        DecimalFormat decimalFormat = new DecimalFormat(format);
                        double value = Double.parseDouble(text.replace(",", ""));
                        System.out.println(value);
                        fieldSaldo.setText("");
                        fieldSaldo.setText(decimalFormat.format(value));
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
                // field kritis listener
                fieldModeKritis.textProperty()
                        .addListener((ChangeListener<? super String>) new ChangeListener<String>() {
                            private String codeFormat = ",##0";
                            private int codeLen = 4;

                            @Override
                            // ObservableValue = StringProperty (Represent an object that has changed its
                            // state)
                            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                    String newValue) {
                                // Hapus listener sementara

                                fieldModeKritis.textProperty().removeListener(this);
                                if (newValue.replace(",", "").matches("\\d*")) { // Check inputan angka atau tidak
                                    if (newValue.length() > oldValue.length()) {
                                        if (newValue.length() > 13) {
                                            fieldModeKritis.setText(oldValue);
                                        } else {
                                            updateCodeFormat(newValue);
                                            formatAndSet(newValue, this.codeFormat);
                                        }
                                    }
                                } else {
                                    fieldModeKritis.setText(oldValue);
                                }

                                // Tambahkan kembali listener setelah pembaruan teks
                                fieldModeKritis.textProperty().addListener(this);
                            }

                            private void formatAndSet(String text, String format) {
                                DecimalFormat decimalFormat = new DecimalFormat(format);
                                double value = Double.parseDouble(text.replace(",", ""));
                                System.out.println(value);
                                fieldModeKritis.setText("");
                                fieldModeKritis.setText(decimalFormat.format(value));
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

                // button simpan
                Button simpanButton = createButton(150, 60, "Simpan",
                        "AD7AFF", 25, "Poppins", 90, "000000");
                simpanButton.setStyle(simpanButton.getStyle()
                        + "-fx-border-color: #000000; -fx-border-width: 3;-fx-border-radius: 30;");

                // wrap in vbox
                VBox saldoModePane = new VBox(10);
                saldoModePane.getChildren().addAll(headerText, descText, saldoElement,
                        fieldSaldo, batasKritisElement,
                        fieldModeKritis, simpanButton);
                saldoModePane.setAlignment(Pos.CENTER);

                // membuat field pane input
                StackPane saldoModeInputPane = new StackPane();
                saldoModeInputPane.getChildren().addAll(saldoModeInputBG, saldoModePane);
                saldoModeInputPane.setMaxWidth(saldoModeInputBG.getWidth());
                saldoModeInputPane.setMaxHeight(saldoModeInputBG.getHeight());

                // menambahkan elements
                StackPane penentuSaldoPane = new StackPane(bgOverlay, saldoModeInputPane);

                root.getChildren().add(penentuSaldoPane);

                // button events
                simpanButton.setOnAction(e -> {
                    int saldo = Integer.parseInt(fieldSaldo.getText().replace(",", ""));
                    int modeKritis = Integer.parseInt(fieldModeKritis.getText().replace(",", ""));
                    handleInputSaldodanMode(saldo, modeKritis);
                });

                FadeTransition fadeInSaldo = new FadeTransition(Duration.millis(300), bgOverlay);
                fadeInSaldo.setFromValue(0);
                fadeInSaldo.setToValue(0.7);
                fadeInSaldo.play();

                FadeTransition fadeOut = new FadeTransition(Duration.millis(300), bgOverlay);
                fadeOut.setFromValue(0.7);
                fadeOut.setToValue(0);

                bgOverlay.setOnMousePressed(e -> {
                    fadeOut.play();
                    fadeOut.setOnFinished(e2 -> {
                        root.getChildren().remove(penentuSaldoPane);
                    });
                });

                // handleInputSaldodanMode(saldo, modeKritis);
            }
        } else if (username.isEmpty() && password.isEmpty()) {
            chatErrorTimelineAll_OUT(chatError); // me-rese semua chat
            System.out.println("Chat before: " + mainPane.getChildren().contains(chatError.get(4)));
            mainPane.getChildren().add(chatError.get(4)); // menambahkan chat error
            System.out.println("Chat after: " + mainPane.getChildren().contains(chatError.get(4)));
            // Memanggil metode untuk mengimplementasikan error style
            statusErr = true;
            applyErrorStyle(usernameField, passwordField, 3);
            animIn[0].play(); // start timeline 1
            animIn[1].play(); // start timeline 2
            chatErrorTimeline_IN(chatError.get(4));
        } else if (username.isEmpty()) {
            chatErrorTimelineAll_OUT(chatError); // me-rese semua chat
            mainPane.getChildren().add(chatError.get(2)); // menambahkan chat error
            // Memanggil metode untuk mengimplementasikan error style
            statusErr = true;
            applyErrorStyle(usernameField, passwordField, 1);
            removeErrorStyle(usernameField, passwordField, 2); // remove error style for password
            animIn[0].play(); // start timeline 1
            animIn[1].play(); // start timeline 2
            chatErrorTimeline_IN(chatError.get(2));
        } else if (password.isEmpty()) {
            chatErrorTimelineAll_OUT(chatError); // me-rese semua chat
            mainPane.getChildren().add(chatError.get(0)); // menambahkan chat error
            // Memanggil metode untuk mengimplementasikan error style
            statusErr = true;
            applyErrorStyle(usernameField, passwordField, 2);
            removeErrorStyle(usernameField, passwordField, 1); // remove error style for username
            animIn[0].play(); // start timeline 1
            animIn[1].play(); // start timeline 2
            chatErrorTimeline_IN(chatError.get(0));
        } else {
            chatErrorTimelineAll_OUT(chatError); // me-rese semua chat
            mainPane.getChildren().add(chatError.get(5));
            // keduanya empty
            statusErr = true;
            applyErrorStyle(usernameField, passwordField, 3);
            animIn[0].play(); // start timeline 1
            animIn[1].play(); // start timeline 2
            chatErrorTimeline_IN(chatError.get(5));
        }
        return statusErr;
    }

    // Membuat metode untuk menerapkan error style
    private void applyErrorStyle(TextField textField, PasswordField passwordField, int switcher) { // Menampung berbagai
                                                                                                   // macam field
        if (switcher == 1) {
            textField.setStyle(
                    textField.getStyle() + "-fx-border-color: red; -fx-border-width: 2; -fx-border-radius: 30;");
        } else if (switcher == 2) {
            passwordField.setStyle(
                    passwordField.getStyle() + "-fx-border-color: red; -fx-border-width: 2; -fx-border-radius: 30;");
        } else {
            textField.setStyle(
                    textField.getStyle() + "-fx-border-color: red; -fx-border-width: 2; -fx-border-radius: 30;");
            passwordField.setStyle(
                    passwordField.getStyle() + "-fx-border-color: red; -fx-border-width: 2; -fx-border-radius: 30;");
        }
    }

    // membuat metode untuk menghapus error style
    private void removeErrorStyle(TextField textField, PasswordField passwordField, int switcher) { // Menampung
        // macam field
        if (switcher == 1) {
            textField.setStyle(
                    textField.getStyle() + "-fx-border-color: red; -fx-border-width: 0; -fx-border-radius: 30;");
        } else if (switcher == 2) {
            passwordField.setStyle(
                    passwordField.getStyle() + "-fx-border-color: red; -fx-border-width: 0; -fx-border-radius: 30;");
        } else {
            textField.setStyle(
                    textField.getStyle() + "-fx-border-color: red; -fx-border-width: 0; -fx-border-radius: 30;");
            passwordField.setStyle(
                    passwordField.getStyle() + "-fx-border-color: red; -fx-border-width: 0; -fx-border-radius: 30;");
        }
    }

    // memainkan timeline untuk chat error
    private void chatErrorTimeline_IN(ImageView chatError) {
        chatError.setTranslateY(-360);
        chatError.setTranslateX(-210);
        chatError.setOpacity(0);
        // timeline animasi untuk chat ayam fade in
        Timeline fadeChatError_IN = new Timeline(
                new KeyFrame(Duration.millis(700),
                        new KeyValue(chatError.opacityProperty(), 1)),
                new KeyFrame(Duration.millis(400),
                        new KeyValue(chatError.translateYProperty(), -280,
                                Interpolator.EASE_BOTH)));
        if (fadeChatError_IN != null) {
            fadeChatError_IN.stop(); // Stop the animation if it's already running
            chatError.translateYProperty().set(-360); // Reset translateY to original Y
            chatError.opacityProperty().set(0); // Reset translateY to original Y
            // position
        }
        fadeChatError_IN.setOnFinished(event -> {
            // Handle any additional actions after the fade-in animation is finished
        });

        // Stop the animation if it's already running
        if (fadeChatError_IN != null && fadeChatError_IN.getStatus() == Animation.Status.RUNNING) {
            fadeChatError_IN.stop();
        }
        fadeChatError_IN.play();
    }

    // memainkan dan mereset semua animasi yang ada
    private void chatErrorTimelineAll_OUT(List<ImageView> chatError) {
        for (ImageView errorChat : chatError) {
            Timeline fadeChatError_OUT = new Timeline(
                    new KeyFrame(Duration.millis(400),
                            new KeyValue(errorChat.opacityProperty(), 0)));
            fadeChatError_OUT.play();
        }
    }
}