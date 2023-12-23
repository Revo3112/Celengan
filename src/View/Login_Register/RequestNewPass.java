package View.Login_Register;

import Controller.SceneController;
import Model.RequestNewPassword;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RequestNewPass {
    private Stage stage; // Deklarasi property stage
    SceneController sceneController; // sceneController utk pages switching

    // cursors
    private Cursor hand = Cursor.cursor("HAND");
    private Cursor closedHand = Cursor.cursor("CLOSED_HAND");
    private Cursor defaultCursor = Cursor.cursor("DEFAULT");

    private boolean isMousePressed = false;
    private boolean isWindowMax = true;
    private double xOffset = 0;
    private double yOffset = 0;

    private boolean animationPlayed = false;

    // error status
    boolean hLogErrStat = false;

    // panes
    StackPane mainContent;

    // timelines
    Timeline welcomeRubah_IN;
    Timeline errorChat_IN;

    ImageView errorChat;

    // deklarasi path asset error
    String logRegPath = "/Assets/View/Login_Register/";

    StackPane root = new StackPane(); // stackpane root

    public RequestNewPass(Stage stage) {
        this.stage = stage;

        sceneController = new SceneController(stage);

        // Set the application icon
        Image icon = new Image("Assets/View/Splash_Screen/images/logo/celengan_image_logo.png");
        this.stage.getIcons().add(icon); // Menambahkan icon ke dalam stage
        this.stage.setTitle("Celengan");
    }

    public void start() {
        /* UI/UX : NULLPTR */
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

        /* BAGIAN DROPDOWN LOGIN */
        // membuat dropdown button
        ComboBox<String> dropDownBtn = new ComboBox<>();
        dropDownBtn.setMinWidth(20);
        dropDownBtn.setMinHeight(40);
        dropDownBtn.setPromptText("Request New Password");
        dropDownBtn.setItems(FXCollections.observableArrayList("Login Page", "Register Page", "Request New Password"));
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
                if ("Register Page".equals(selectedItem) || "Login Page".equals(selectedItem)) {
                    // membuat rectangle base loading
                    Rectangle baseKukooLoading = new Rectangle(root.getWidth(), root.getHeight(),
                            Color.valueOf("#101C22"));
                    // asset gif
                    ImageView kukooAnim = createImage(logRegPath + "celengan_loading_anim.gif", 400, 400);
                    kukooAnim.setTranslateY(kukooAnim.getTranslateY() - 90);

                    Text loadingText = createText("LOADING ...", 20, "Poppins", "376675");
                    loadingText.setTranslateY(loadingText.getTranslateY() + 70);

                    Text tipsText = createText("Penting untuk jaga keuangan dan kesehatanmu!", 20,
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

                    FadeTransition fadeIn = new FadeTransition(Duration.millis(500), loadingAnimation);
                    fadeIn.setFromValue(0.0);
                    fadeIn.setToValue(1.0);

                    Timeline timeline = new Timeline(
                            new KeyFrame(Duration.seconds(2), event -> {
                                // Code to execute after approximately 2 seconds
                                switch (selectedItem) {
                                    case "Register Page":
                                        sceneController.switchToRegistration();
                                        break;
                                    case "Login Page":
                                        sceneController.switchToLogin();
                                        break;
                                    default:
                                        break;
                                }
                            }));
                    timeline.setCycleCount(1); // Set to 1 for a single loop

                    fadeIn.setOnFinished(event -> {
                        timeline.play();
                    });

                    fadeIn.play();
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
        Rectangle backgroundBase = createRect(490, 440, 60, 60, "0F181B");
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
        Text username = createText("username", 25, "Poppins", "77F7FF");
        ImageView usr_icon = createImage("/Assets/View/Login_Register/RNP_usernameIcon.png", 40, 40);
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

        // KEY TEXT: bagian informasi text key
        Text keyUser = createText("key user", 25, "Poppins", "77F7FF");
        ImageView key_icon = createImage(logRegPath + "RNP_keyIcon.png", 40, 40);
        // menambah masing-masing key user elements ke hbox
        HBox keyText = new HBox(5);
        keyText.getChildren().addAll(key_icon, keyUser);
        keyText.setStyle("-fx-padding: 0 0 10 40;");
        // KEY USER FIELD: bagian yang dapat diinput key user
        TextField inputKeyUser = new TextField();
        inputKeyUser.setMaxWidth(mainBase.getWidth() - 80);
        inputKeyUser.setMaxHeight(120);
        inputKeyUser.setStyle(
                "-fx-background-radius: 30;" +
                        "-fx-background-color: #141F23;" +
                        "-fx-font: 23 Poppins;" +
                        "-fx-text-fill: white;");

        // PASSWORD TEXT: bagian informasi text password dan icon
        Text password = createText("password baru", 25, "Poppins", "77F7FF");
        ImageView pwd_icon = createImage("/Assets/View/Login_Register/RNP_passwordIcon.png", 40, 40);
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

        // BUTTON LOGIN: seluruh tombol masuk dan belum punya akun?
        // button login
        Button gantiPassword = createButton(230, 50, "Ganti Password", "77F7FF", 22, "Poppins", 30, "141F23");
        Rectangle gantiPasswordBG = new Rectangle();

        // menyatukan elemen login ke satu group
        Group gantiPassButton = new Group(gantiPassword, gantiPasswordBG);

        // LOGIN BUTTON EVENTS: saat di hover atau di klik
        // login button
        gantiPassword.setOnMouseEntered(e -> {
            gantiPassword.getScene().setCursor(hand);
            System.out.println("Username: " + inputUsername.getText());
            System.out.println("Password: " + inputPassword.getText());
            updateButton(gantiPassword, 230, 50, "Ganti Password", "338489", 22, "Poppins", 30, "0A0F11");
        });
        gantiPassword.setOnMouseExited(e -> {
            gantiPassword.getScene().setCursor(defaultCursor);
            updateButton(gantiPassword, 230, 50, "Ganti Password", "77F7FF", 22, "Poppins", 30, "141F23");
        });
        gantiPassword.setOnAction(e -> {
            System.out.println("Login button clicked!");
            if (inputPassword.getText().length() < 8 || !isContainDigit(inputPassword.getText())) {
                inputPassword.setStyle(
                "-fx-background-radius: 90;" +
                        "-fx-background-color: #141F23;" +
                        "-fx-font: 23 Poppins;" +
                        "-fx-text-fill: white;" +
                        "-fx-border-color: #FF4646;" +
                        "-fx-border-radius: 90");
            } else {
                try {
                    RequestNewPassword requestNewPassword = new RequestNewPassword();
                    boolean status = requestNewPassword.checkData(inputUsername.getText(), inputKeyUser.getText(),
                            inputPassword.getText());
                    System.out.println(status);
                    if (status) {
                        SceneController sceneController = new SceneController(this.stage);
                        sceneController.switchToLogin();
                    } else {
                        System.out.println("Username atau Pin Code salah");
                        if (mainContent.getChildren().contains(errorChat)) {
                            mainContent.getChildren().remove(errorChat);
                        }
                        mainContent.getChildren().add(errorChat);
                        chatErrorTimeline_IN(errorChat);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        // memasukkan gantiPassButton ke HBox
        HBox gantiPassBtn = new HBox();
        gantiPassBtn.getChildren().add(gantiPassButton);
        gantiPassBtn.setAlignment(Pos.CENTER);
        gantiPassBtn.setStyle("-fx-padding: 20 0 20 0;");

        // menggabungkan seluruh elements
        VBox contentElements = new VBox();
        contentElements.getChildren().addAll(usernameText, inputUsername, keyText, inputKeyUser, passwordText,
                inputPassword,
                gantiPassBtn);
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

        // welcome rubah
        // Memainkan welcoming greet dari rubah polisi
        ImageView welcomeRubah = createImage(logRegPath + "rubahPolisiWelcome.png", 350, 350);
        welcomeRubah.setTranslateY(-60);
        double welcomeRubahOriginX = welcomeRubah.getTranslateX();
        double targetRubahOriginX = welcomeRubahOriginX + 350;
        // animasi Rubah welcome greet
        welcomeRubah_IN = new Timeline(
                new KeyFrame(Duration.millis(300),
                        new KeyValue(welcomeRubah.translateXProperty(), welcomeRubahOriginX, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(1000),
                        new KeyValue(welcomeRubah.translateXProperty(), targetRubahOriginX)));

        // warning key ata username salah
        errorChat = createImage(logRegPath + "usernameKeySalah.png", 300, 300);

        // memasukkan seluruh elemen ke dalam stackPane mainContent
        mainContent = new StackPane(errorChat, welcomeRubah, base, contentElements, logoCelengan);
        mainContent.setMaxSize(600, 300);
        // GENERAL EVENTS: events yang mencakup semua
        root.setOnMouseEntered(e -> {
            if (!animationPlayed) {
                System.out.println("playing animation...");
                welcomeRubah_IN.play();
                System.out.println("playing out...");
                animationPlayed = true;
                System.out.println("passed out...");
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

        Text tipsText = createText("Sedikit demi sedikit, lama - lama menjadi bukit!", 20,
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

    private void chatErrorTimeline_IN(ImageView chatError) {
        errorChat.setTranslateX(400);
        errorChat.setOpacity(0);
        // timeline animasi untuk chat ayam fade in
        Timeline fadeChatError_IN = new Timeline(
                new KeyFrame(Duration.millis(700),
                        new KeyValue(chatError.opacityProperty(), 1)),
                new KeyFrame(Duration.millis(400),
                        new KeyValue(chatError.translateXProperty(), 500,
                                Interpolator.EASE_BOTH)));
        if (fadeChatError_IN != null) {
            fadeChatError_IN.stop(); // Stop the animation if it's already running
            chatError.translateXProperty().set(400); // Reset translateY to original Y
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

    // method untuk mengecek apakah ada digit
    private boolean isContainDigit(String s) {
        return s != null && s.chars().anyMatch(Character::isDigit);
    }
}